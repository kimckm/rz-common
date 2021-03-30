package rz.topic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rz.topic.controller.dto.PageQueryDTO;
import rz.topic.controller.dto.PageResultDTO;
import rz.topic.model.Topic;
import rz.topic.service.TopicService;

import java.time.LocalDateTime;

@RequestMapping("/topics")
@RestController
@Slf4j
public class TopicController {

	@Autowired
	private TopicService topicService;

	@PostMapping
	public Object saveOne(@RequestBody Topic topic) {
		topic.setCreatedAt(LocalDateTime.now());
		return topicService.save(topic);
	}

	@GetMapping
	public Object find(PageQueryDTO pageQueryDTO) {
		IPage<Topic> page = topicService.page(
			new Page<>(pageQueryDTO.getCurrent(), pageQueryDTO.getSize()),
			Wrappers.lambdaQuery(Topic.class)
				.orderByDesc(Topic::getCreatedAt)
		);
		return new PageResultDTO<Topic>()
			.list(page.getRecords())
			.current(page.getCurrent())
			.size(page.getSize())
			.total(page.getTotal());
	}

}
