package rz.exam.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rz.exam.model.CompletionAudio;
import rz.exam.service.CompletionAudioService;

import java.util.Objects;

@RequestMapping("/completion_audios")
@RestController
public class CompletionAudioController {

	@Autowired
	private CompletionAudioService completionAudioService;

	@GetMapping
	public Object list(@RequestParam(required = false) Long completionId) {
		return completionAudioService.list(Wrappers.lambdaQuery(CompletionAudio.class)
			.eq(Objects.nonNull(completionId), CompletionAudio::getCompletionId, completionId)
		);
	}

}
