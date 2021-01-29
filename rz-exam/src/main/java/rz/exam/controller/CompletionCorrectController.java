package rz.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionCorrectService;

import java.util.Objects;

@RequestMapping("/completion_correct")
@RestController
public class CompletionCorrectController {

	@Autowired
	private CompletionCorrectService completionCorrectService;

	@GetMapping
	public Object list(QueryDto queryDto) {
		LambdaQueryWrapper<CompletionCorrect> query = Wrappers.lambdaQuery(CompletionCorrect.class);
		if (Objects.nonNull(queryDto.getCompletionId())) {
			query.eq(CompletionCorrect::getCompletionId, queryDto.getCompletionId());
		}
		return completionCorrectService.list(query);
	}
}

@Data
class QueryDto {
	private Long completionId;
}
