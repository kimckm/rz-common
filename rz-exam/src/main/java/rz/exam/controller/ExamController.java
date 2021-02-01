package rz.exam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.ExamSaveDTO;
import rz.exam.service.ExamService;

@Slf4j
@RequestMapping("/exams")
@RestController
public class ExamController {

	@Autowired
	private ExamService examService;

	@GetMapping
	public Object list() {
		return examService.list();
	}

	@GetMapping("/{id}")
	public Object findOne(@PathVariable Long id) {
		return examService.getById(id);
	}

	@PostMapping
	public Object insertOne(@RequestBody ExamSaveDTO examSaveDTO) {
		return examService.insertOne(examSaveDTO);
	}

}
