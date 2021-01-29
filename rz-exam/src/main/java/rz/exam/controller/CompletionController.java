package rz.exam.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.model.Completion;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionService;

import java.util.List;

@RequestMapping("/completions")
@RestController
public class CompletionController {

	@Autowired
	private CompletionService completionService;

	@GetMapping
	public Object list() {
		return completionService.list();
	}

	@PostMapping
	public Object save(@RequestBody CompletionSaveDTO completionSaveDTO) {
		return completionService.save(completionSaveDTO);
	}

}
