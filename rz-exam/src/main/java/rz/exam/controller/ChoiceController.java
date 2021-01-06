package rz.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.service.ChoiceService;

@RequestMapping("/choices")
@RestController
public class ChoiceController {

	@Autowired
	private ChoiceService choiceService;

	@GetMapping
	public Object list() {
		return choiceService.list();
	}

}
