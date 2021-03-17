package rz.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.controller.dto.PageQueryDTO;
import rz.exam.controller.dto.PageResultDTO;
import rz.exam.model.Completion;
import rz.exam.service.CompletionService;

@RequestMapping("/completions")
@RestController
public class CompletionController {

	@Autowired
	private CompletionService completionService;

	@GetMapping
	public Object list(PageQueryDTO pageQueryDTO) {
		IPage<Completion> page = completionService.page(
			new Page<>(pageQueryDTO.getCurrent(), pageQueryDTO.getSize()),
			Wrappers.lambdaQuery(Completion.class)
				.orderByDesc(Completion::getCreateAt)
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
