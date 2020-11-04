package rz.cloud.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rz.cloud.order.controller.dto.OrderAddDto;
import rz.cloud.order.controller.dto.OrderQueryDto;
import rz.cloud.order.controller.dto.OrderUpdateDto;
import rz.cloud.order.model.Order;
import rz.cloud.order.service.OrderService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public Order add(@RequestBody OrderAddDto orderAddDto) {
		orderAddDto.setCreateAt(LocalDateTime.now());
		orderService.save(orderAddDto);
		return orderAddDto;
	}

	@PatchMapping("/{id}")
	public Order update(@PathVariable Integer id, @RequestBody OrderUpdateDto orderUpdateDto) {
		orderUpdateDto.setId(id);
		if (orderService.updateById(orderUpdateDto)) {
			return orderService.getById(id);
		}

		return null;
	}

	@GetMapping
	public IPage<Order> query(OrderQueryDto orderQueryDto) {
		IPage<Order> page = new Page<>();
		page.setCurrent(orderQueryDto.getCurrent());
		page.setSize(orderQueryDto.getSize());

		return orderService.page(page, new QueryWrapper<>(orderQueryDto));
	}

	@GetMapping("/{id}")
	public Order findOne(@PathVariable Integer id) {
		return orderService.getById(id);
	}

}
