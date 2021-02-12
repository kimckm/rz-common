package rz.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.model.ExamQuestion;
import rz.exam.service.ExamQuestionService;
import rz.exam.util.SnowFlake;

import java.util.Objects;

@Slf4j
@RequestMapping("/exam_questions")
@RestController
public class ExamQuestionController {

	@Autowired
	private ExamQuestionService examQuestionService;

	@GetMapping
	public Object find(@RequestParam Long examId) {
		LambdaQueryWrapper<ExamQuestion> query = Wrappers.lambdaQuery(ExamQuestion.class);
		if (Objects.nonNull(examId)) {
			query.eq(ExamQuestion::getExamId, examId);
		}

		return examQuestionService.list(query);
	}

	@PostMapping
	public Object saveOne(@RequestBody ExamQuestion examQuestion) {
		examQuestion.setId(SnowFlake.generateId());
		examQuestionService.save(examQuestion);
		return examQuestion.getId();
	}

}
