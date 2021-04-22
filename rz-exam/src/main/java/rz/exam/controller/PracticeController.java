package rz.exam.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.controller.dto.ChoicePracticeDTO;
import rz.exam.controller.dto.ChoiceSaveDTO;
import rz.exam.controller.dto.CompletionPracticeDTO;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.model.*;
import rz.exam.service.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 练习业务。
 */
@Slf4j
@RestController
@RequestMapping("/practice")
public class PracticeController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private CompletionService completionService;
	@Autowired
	private ChoiceService choiceService;
	@Autowired
	private ChoiceOptionService choiceOptionService;
	@Autowired
	private CompletionCorrectService completionCorrectService;
	@Autowired
	private ExamQuestionService examQuestionService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private CatalogQuestionService catalogQuestionService;

	/**
	 * 随机抽题。
	 *
	 * @param examId 试卷ID
	 * @param topicId 主题ID
	 * @param sum 题目数
	 * @return 题目集合
	 */
	@GetMapping("/random")
	public Object random(Long examId, Long topicId, @RequestParam(defaultValue = "10") Long sum) {
		if (Objects.isNull(examId) && Objects.isNull(topicId)) {
			return allRandom(sum);
		}

		if (Objects.nonNull(topicId)) {
			return topicRandom(topicId, sum);
		}

		String examKey = String.format("exam::%s", examId);
		if (!stringRedisTemplate.hasKey(examKey)) {
			examQuestionService.list(Wrappers.lambdaQuery(ExamQuestion.class)
				.eq(ExamQuestion::getExamId, examId)
			).forEach(eq -> stringRedisTemplate.opsForSet().add(examKey, String.valueOf(eq.getQuestionId())));
			stringRedisTemplate.expire(examKey, 60, TimeUnit.MINUTES);
		}

		Set<String> completionIdSet = stringRedisTemplate.opsForSet().distinctRandomMembers(examKey, sum);
		List<String> questionKeyList = completionIdSet.stream()
			.map(id -> String.format("question::%s", id))
			.collect(Collectors.toList());
		List<String> cacheCompletionList = stringRedisTemplate.opsForValue().multiGet(questionKeyList);

		List<Long> ids = completionIdSet.stream()
			.map(Long::valueOf)
			.collect(Collectors.toList());

		List<CompletionSaveDTO> cacheCompletionSaveDTOList = cacheCompletionList.stream()
			.filter(StringUtils::hasLength)
			.map(str -> {
				try {
					return objectMapper.readValue(str, CompletionSaveDTO.class);
				} catch (IOException e) {
					log.error("", e);
				}
				return null;
			}).collect(Collectors.toList());
		ids.removeAll(cacheCompletionSaveDTOList.stream().map(CompletionSaveDTO::getId).collect(Collectors.toList()));

		List<Object> rsList = new ArrayList<>(cacheCompletionSaveDTOList);
		if (CollectionUtils.isEmpty(ids)) {
			return rsList;
		}

		List<Completion> completions = completionService.listByIds(ids);
		List<CompletionSaveDTO> completionSaveDTOList = completions.stream()
			.map(completion -> {
				CompletionSaveDTO dto = new CompletionSaveDTO();
				BeanUtils.copyProperties(completion, dto);
				dto.setCorrect(completionCorrectService.list(Wrappers.<CompletionCorrect>lambdaQuery()
					.eq(CompletionCorrect::getCompletionId, completion.getId())
				));
				return dto;
			})
			.peek(dto -> {
				try {
					String questionKey = String.format("question::%s", dto.getId());
					stringRedisTemplate.opsForValue().set(questionKey, objectMapper.writeValueAsString(dto), 3, TimeUnit.HOURS);
				} catch (Exception e) {
					log.error("", e);
				}
			})
			.collect(Collectors.toList());
		rsList.addAll(completionSaveDTOList);

		return rsList;
	}

	/**
	 * 全范围随机抽题
	 */
	private Object allRandom(Long sum) {
		String allKey = "all::question";
		if (!stringRedisTemplate.hasKey(allKey)) {
			// 填空题
			completionService.list(Wrappers.lambdaQuery(Completion.class)
				.select(Completion::getId)
			).forEach(c -> stringRedisTemplate.opsForSet().add(allKey, String.format("completion_%s", c.getId())));

			// 选择题
			choiceService.list(Wrappers.lambdaQuery(Choice.class)
				.select(Choice::getId)
			).forEach(c -> stringRedisTemplate.opsForSet().add(allKey, String.format("choice_%s", c.getId())));

			stringRedisTemplate.expire(allKey, 60, TimeUnit.MINUTES);
		}

		Set<String> questionIdSet = stringRedisTemplate.opsForSet().distinctRandomMembers(allKey, sum);

		List<Long> completionIdList = new ArrayList<>();
		List<Long> choiceIdList = new ArrayList<>();
		for (String key : Objects.requireNonNull(questionIdSet)) {
			String[] split = key.split("_");
			String type = split[0];
			String id = split[1];
			if ("completion".equals(type)) {
				completionIdList.add(Long.valueOf(id));
			} else {
				choiceIdList.add(Long.valueOf(id));
			}
		}

		// 取填空题缓存
		List<String> cacheCompletionList = stringRedisTemplate.opsForValue()
			.multiGet(
				completionIdList
					.stream()
					.map(id -> String.format("completion::%s", id))
					.collect(Collectors.toList())
			);

		List<CompletionPracticeDTO> cacheCompletionPracticeDTOList = Objects.requireNonNull(cacheCompletionList)
			.stream()
			.filter(StringUtils::hasLength)
			.map(key -> {
				try {
					CompletionPracticeDTO dto = objectMapper.readValue(key, CompletionPracticeDTO.class);
					dto.setType("completion");
					return dto;
				} catch (IOException e) {
					log.error("", e);
				}
				return null;
			}).collect(Collectors.toList());
		// 得出不在缓存中的ID
		completionIdList.removeAll(
			cacheCompletionPracticeDTOList
				.stream()
				.map(CompletionSaveDTO::getId)
				.collect(Collectors.toList())
		);

		Set<Object> rs = new HashSet<>(cacheCompletionPracticeDTOList);

		if (completionIdList.size() > 0) {
			completionService.listByIds(completionIdList)
				.stream()
				.map(completion -> {
					CompletionPracticeDTO dto = new CompletionPracticeDTO();
					BeanUtils.copyProperties(completion, dto);
					dto.setType("completion");
					dto.setCorrect(completionCorrectService.list(Wrappers.<CompletionCorrect>lambdaQuery()
						.eq(CompletionCorrect::getCompletionId, completion.getId())
					));
					return dto;
				})
				.peek(dto -> {
					try {
						String questionKey = String.format("completion::%s", dto.getId());
						stringRedisTemplate.opsForValue().set(questionKey, objectMapper.writeValueAsString(dto), 3, TimeUnit.HOURS);
					} catch (Exception e) {
						log.error("", e);
					}
				})
				.forEach(rs::add);
		}

		// 取选择题
		List<String> cacheChoiceList = stringRedisTemplate.opsForValue()
			.multiGet(
				choiceIdList
					.stream()
					.map(id -> String.format("choice::%s", id))
					.collect(Collectors.toList())
			);

		List<ChoicePracticeDTO> cacheChoiceSaveDTOList = Objects.requireNonNull(cacheChoiceList)
			.stream()
			.filter(StringUtils::hasLength)
			.map(key -> {
				try {
					ChoicePracticeDTO dto = objectMapper.readValue(key, ChoicePracticeDTO.class);
					dto.setType("choice");
					return dto;
				} catch (IOException e) {
					log.error("", e);
				}
				return null;
			}).collect(Collectors.toList());
		rs.addAll(cacheChoiceSaveDTOList);

		// 得出不在缓存中的ID
		choiceIdList.removeAll(
			cacheChoiceSaveDTOList
				.stream()
				.map(ChoiceSaveDTO::getId)
				.collect(Collectors.toList())
		);

		if (choiceIdList.size() > 0) {
			choiceService.listByIds(choiceIdList)
				.stream()
				.map(choice -> {
					ChoicePracticeDTO dto = new ChoicePracticeDTO();
					BeanUtils.copyProperties(choice, dto);
					dto.setType("choice");
					dto.setChoiceOptions(choiceOptionService.list(Wrappers.lambdaQuery(ChoiceOption.class)
						.eq(ChoiceOption::getChoiceId, choice.getId())
					));
					return dto;
				})
				.peek(dto -> {
					try {
						String questionKey = String.format("choice::%s", dto.getId());
						stringRedisTemplate.opsForValue().set(questionKey, objectMapper.writeValueAsString(dto), 3, TimeUnit.HOURS);
					} catch (Exception e) {
						log.error("", e);
					}
				})
				.forEach(rs::add);
		}

		return rs;
	}

	private Object topicRandom(Long topicId, Long sum) {
		String topicKey = String.format("topic::%s", topicId);
		if (!stringRedisTemplate.hasKey(topicKey)) {
			List<Catalog> catalogList = catalogService.list(Wrappers.lambdaQuery(Catalog.class)
				.eq(Catalog::getTopicId, topicId)
			);
			if (CollectionUtils.isEmpty(catalogList)) {
				return null;
			}
			catalogQuestionService.list(Wrappers.lambdaQuery(CatalogQuestion.class)
				.in(CatalogQuestion::getCatalogId, catalogList.stream().map(Catalog::getId).collect(Collectors.toList()))
			)
				.forEach(eq -> stringRedisTemplate.opsForSet().add(topicKey, String.valueOf(eq.getQuestionId())));
			stringRedisTemplate.expire(topicKey, 60, TimeUnit.MINUTES);
		}

		Set<String> completionIdSet = stringRedisTemplate.opsForSet().distinctRandomMembers(topicKey, sum);
		List<String> questionKeyList = completionIdSet.stream()
			.map(id -> String.format("question::%s", id))
			.collect(Collectors.toList());
		List<String> cacheCompletionList = stringRedisTemplate.opsForValue().multiGet(questionKeyList);

		List<Long> ids = completionIdSet.stream()
			.map(Long::valueOf)
			.collect(Collectors.toList());

		List<CompletionSaveDTO> cacheCompletionSaveDTOList = cacheCompletionList.stream()
			.filter(StringUtils::hasLength)
			.map(str -> {
				try {
					return objectMapper.readValue(str, CompletionSaveDTO.class);
				} catch (IOException e) {
					log.error("", e);
				}
				return null;
			}).collect(Collectors.toList());
		ids.removeAll(cacheCompletionSaveDTOList.stream().map(CompletionSaveDTO::getId).collect(Collectors.toList()));

		List<Object> rsList = new ArrayList<>(cacheCompletionSaveDTOList);
		if (CollectionUtils.isEmpty(ids)) {
			return rsList;
		}

		List<Completion> completions = completionService.listByIds(ids);
		List<CompletionSaveDTO> completionSaveDTOList = completions.stream()
			.map(completion -> {
				CompletionSaveDTO dto = new CompletionSaveDTO();
				BeanUtils.copyProperties(completion, dto);
				dto.setCorrect(completionCorrectService.list(Wrappers.<CompletionCorrect>lambdaQuery()
					.eq(CompletionCorrect::getCompletionId, completion.getId())
				));
				return dto;
			})
			.peek(dto -> {
				try {
					String questionKey = String.format("question::%s", dto.getId());
					stringRedisTemplate.opsForValue().set(questionKey, objectMapper.writeValueAsString(dto), 3, TimeUnit.HOURS);
				} catch (Exception e) {
					log.error("", e);
				}
			})
			.collect(Collectors.toList());
		rsList.addAll(completionSaveDTOList);

		return rsList;
	}

}
