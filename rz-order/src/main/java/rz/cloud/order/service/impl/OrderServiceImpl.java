package rz.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.cloud.order.mapper.OrderMapper;
import rz.cloud.order.model.Order;
import rz.cloud.order.service.OrderService;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
