package rz.cloud.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.cloud.order.model.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
