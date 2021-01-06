package rz.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.service.ExamService;

@RequestMapping("exams")
@RestController
public class ExamController {

	@Autowired
	private ExamService examService;

	@GetMapping("/{id}")
	public Object loadOne(@PathVariable long id) {
		return examService.getById(id);
	}

}
