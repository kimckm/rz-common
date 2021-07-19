package rz.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionCorrectService;

import java.util.Objects;

@RequestMapping("/completion_correct")
@RestController
public class CompletionCorrectController {

	@Autowired
	private CompletionCorrectService completionCorrectService;

	@GetMapping
	public Object list(@RequestParam(required = false) Long completionId) {
		LambdaQueryWrapper<CompletionCorrect> query = Wrappers.lambdaQuery(CompletionCorrect.class);
		if (Objects.nonNull(completionId)) {
			query.eq(CompletionCorrect::getCompletionId, completionId);
		}
		return completionCorrectService.list(query);
	}

	@DeleteMapping("/{id}")
	public Object deleteById(@PathVariable Long id) {
		return completionCorrectService.removeById(id);
	}

}

