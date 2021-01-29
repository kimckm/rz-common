package rz.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.model.CompletionAudio;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionAudioService;

import java.util.Objects;

@RequestMapping("/completion_audios")
@RestController
public class CompletionAudioController {

	@Autowired
	private CompletionAudioService completionAudioService;

	@GetMapping
	public Object list(@RequestParam(required = false) Long completionId) {
		LambdaQueryWrapper<CompletionAudio> query = Wrappers.lambdaQuery(CompletionAudio.class);
		if (Objects.nonNull(completionId)) {
			query.eq(CompletionAudio::getCompletionId, completionId);
		}
		return completionAudioService.list(query);
	}

}
