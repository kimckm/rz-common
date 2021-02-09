package rz.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.CompletionSaveDTO;
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

	@PostMapping
	public Object save(@RequestBody CompletionSaveDTO completionSaveDTO) {
		return completionService.save(completionSaveDTO);
	}

	/**
	 * TODO 修改数据
	 */
	@PatchMapping("/{id}")
	public Object update() {
		return null;
	}

}
