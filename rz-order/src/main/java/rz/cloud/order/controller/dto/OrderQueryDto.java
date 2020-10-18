package rz.cloud.order.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rz.cloud.order.model.Order;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryDto extends Order {

	private Integer current;
	private Integer size;

}
