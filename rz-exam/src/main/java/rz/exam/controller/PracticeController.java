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
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.model.*;
import rz.exam.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
			completionService.list(Wrappers.lambdaQuery(Completion.class)
				.select(Completion::getId)
			).forEach(eq -> stringRedisTemplate.opsForSet().add(allKey, String.valueOf(eq.getId())));

			// TODO
//			choiceService.list(Wrappers.lambdaQuery(Choice.class)
//				.select(Choice::getId)
//			).forEach(eq -> stringRedisTemplate.opsForSet().add(allKey, String.valueOf(eq.getId())));

			stringRedisTemplate.expire(allKey, 60, TimeUnit.MINUTES);
		}

		Set<String> completionIdSet = stringRedisTemplate.opsForSet().distinctRandomMembers(allKey, sum);
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
