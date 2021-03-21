package rz.exam.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import rz.exam.model.Completion;
import rz.exam.model.CompletionCorrect;
import rz.exam.model.ExamQuestion;
import rz.exam.service.CompletionCorrectService;
import rz.exam.service.CompletionService;
import rz.exam.service.ExamQuestionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private CompletionCorrectService completionCorrectService;
	@Autowired
	private ExamQuestionService examQuestionService;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 随机抽题。
	 *
	 * @param examId 试卷ID
	 * @param sum 题目数
	 * @return 题目集合
	 */
	@GetMapping("/random")
	public Object random(Long examId, @RequestParam(defaultValue = "10") Long sum) {
		String examKey = String.format("exam::%s", examId);
		if (!stringRedisTemplate.hasKey(examKey)) {
			examQuestionService.list(Wrappers.lambdaQuery(ExamQuestion.class)
				.eq(ExamQuestion::getExamId, examId)
			).forEach(eq -> stringRedisTemplate.opsForSet().add(examKey, String.valueOf(eq.getQuestionId())));
			stringRedisTemplate.expire(examKey, 60, TimeUnit.MINUTES);
		}

		List<String> completionIdList = stringRedisTemplate.opsForSet().randomMembers(examKey, sum);
		List<String> questionKeyList = completionIdList.stream()
			.map(id -> String.format("question::%s", id))
			.collect(Collectors.toList());
		List<String> cacheCompletionList = stringRedisTemplate.opsForValue().multiGet(questionKeyList);

		List<Long> ids = completionIdList.stream()
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
