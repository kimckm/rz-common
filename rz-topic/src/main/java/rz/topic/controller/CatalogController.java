package rz.topic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.topic.controller.dto.CatalogPageQueryDTO;
import rz.topic.controller.dto.PageResultDTO;
import rz.topic.model.Catalog;
import rz.topic.service.CatalogService;
import rz.topic.util.SnowFlake;

import java.util.Objects;

@RequestMapping("/catalogs")
@Slf4j
@RestController
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@PostMapping
	public Object saveOne(@RequestBody Catalog catalog) {
		catalog.setId(SnowFlake.generateId());
		return catalogService.save(catalog);
	}

	@GetMapping
	public Object find(CatalogPageQueryDTO pageQueryDTO) {
		IPage<Catalog> page = catalogService.page(
			new Page<>(pageQueryDTO.getCurrent(), pageQueryDTO.getSize()),
			Wrappers.lambdaQuery(Catalog.class)
				.eq(Objects.nonNull(pageQueryDTO.getTopicId()), Catalog::getTopicId, pageQueryDTO.getTopicId())
		);
		return new PageResultDTO<Catalog>()
			.list(page.getRecords())
			.current(page.getCurrent())
			.size(page.getSize())
			.total(page.getTotal());
	}

}
