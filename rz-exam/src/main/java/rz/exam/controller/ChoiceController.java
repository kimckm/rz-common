package rz.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.ChoiceSaveDTO;
import rz.exam.controller.dto.PageQueryDTO;
import rz.exam.controller.dto.PageResultDTO;
import rz.exam.model.Choice;
import rz.exam.service.ChoiceService;

import java.time.LocalDateTime;

@RequestMapping("/choices")
@RestController
@Slf4j
public class ChoiceController {

	@Autowired
	private ChoiceService choiceService;

	@GetMapping
	public Object list(PageQueryDTO pageQueryDTO) {
		IPage<Choice> page = choiceService.page(
			new Page<>(pageQueryDTO.getCurrent(), pageQueryDTO.getSize()),
			Wrappers.lambdaQuery(Choice.class)
				.orderByDesc(Choice::getCreatedAt)
		);
		return new PageResultDTO<Choice>()
			.list(page.getRecords())
			.current(page.getCurrent())
			.size(page.getSize())
			.total(page.getTotal());
	}

	@GetMapping("/{id}")
	public Object findOne(@PathVariable Long id) {
		return choiceService.getById(id);
	}

	@PostMapping
	public Object save(@RequestBody ChoiceSaveDTO choiceSaveDTO) {
		return choiceService.save(choiceSaveDTO);
	}

}
