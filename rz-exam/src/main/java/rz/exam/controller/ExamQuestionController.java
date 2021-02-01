package rz.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.model.ExamQuestion;
import rz.exam.service.ExamQuestionService;

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

}
