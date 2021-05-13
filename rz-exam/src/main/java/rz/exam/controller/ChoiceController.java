package rz.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.exam.controller.dto.ChoiceSaveDTO;
import rz.exam.controller.dto.PageQueryDTO;
import rz.exam.controller.dto.PageResultDTO;
import rz.exam.model.Choice;
import rz.exam.model.ChoiceOption;
import rz.exam.service.ChoiceOptionService;
import rz.exam.service.ChoiceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequestMapping("/choices")
@RestController
@Slf4j
public class ChoiceController {

	@Autowired
	private ChoiceService choiceService;
	@Autowired
	private ChoiceOptionService choiceOptionService;

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
		Choice choice = choiceService.getById(id);
		ChoiceSaveDTO dto = new ChoiceSaveDTO();
		BeanUtils.copyProperties(choice, dto);
		List<ChoiceOption> choiceOptions = choiceOptionService.list(Wrappers.lambdaQuery(ChoiceOption.class)
			.eq(ChoiceOption::getChoiceId, id)
		);
		dto.setChoiceOptions(choiceOptions);
		return dto;
	}

	@PostMapping
	public Object save(@RequestBody ChoiceSaveDTO choiceSaveDTO) {
		return choiceService.save(choiceSaveDTO);
	}

	@DeleteMapping("/{id}")
	public Object deleteOne(@PathVariable Long id) {
		if (Objects.isNull(id)) {
			return false;
		}

		choiceService.deleteOne(id);
		return true;
	}

}
