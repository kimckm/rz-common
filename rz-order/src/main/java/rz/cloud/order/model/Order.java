package rz.cloud.order.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单，处理的最小单元。
 */
@Data
public class Order {

	private int id;

	private String no;
	private Integer basePrice;
	private LocalDateTime createAt;

	private int status;
	private String remark;

	private List<OrderItem> orderItemList;

}
