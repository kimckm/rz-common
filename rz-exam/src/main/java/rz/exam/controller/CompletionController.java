package rz.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.service.CompletionService;

@RequestMapping("/completions")
@RestController
public class CompletionController {

	@Autowired
	private CompletionService completionService;

	@GetMapping
	public Object list() {
		return completionService.list();
	}

}
