package rz.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.controller.dto.PageQueryDTO;
import rz.exam.controller.dto.PageResultDTO;
import rz.exam.model.Completion;
import rz.exam.model.CompletionAudio;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionAudioService;
import rz.exam.service.CompletionCorrectService;
import rz.exam.service.CompletionService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/completions")
@RestController
public class CompletionController {

	@Autowired
	private CompletionService completionService;
	@Autowired
	private CompletionCorrectService completionCorrectService;
	@Autowired
	private CompletionAudioService completionAudioService;

	@GetMapping
	public Object list(PageQueryDTO pageQueryDTO) {
		IPage<Completion> page = completionService.page(
			new Page<>(pageQueryDTO.getCurrent(), pageQueryDTO.getSize()),
			Wrappers.lambdaQuery(Completion.class)
				.orderByDesc(Completion::getCreatedAt)
		);
		return new PageResultDTO<Completion>()
			.list(page.getRecords())
			.current(page.getCurrent())
			.size(page.getSize())
			.total(page.getTotal());
	}

	@GetMapping("/{id}")
	public Object findOne(@PathVariable Long id) {
		return completionService.findOne(id);
	}

	@PostMapping("/batch")
	public Object batch(@RequestBody Map<String, Object> body) {
		String method = (String) body.get("method");

		if ("query".equals(method)) {
			List<?> data = (List<?>) body.get("data");

			List<Long> ids = data.stream()
				.map(Object::toString)
				.map(Long::valueOf)
				.collect(Collectors.toList());

			List<Completion> completions = completionService.listByIds(ids);

			return completions.stream().map(completion -> {
				CompletionSaveDTO dto = new CompletionSaveDTO();
				BeanUtils.copyProperties(completion, dto);
				dto.setCorrect(completionCorrectService.list(Wrappers.<CompletionCorrect>lambdaQuery()
					.eq(CompletionCorrect::getCompletionId, completion.getId())
				));
				dto.setAudio(completionAudioService.list(Wrappers.<CompletionAudio>lambdaQuery()
					.eq(CompletionAudio::getCompletionId, completion.getId())
				));
				return dto;
			}).collect(Collectors.toList());
		}
		return null;
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
