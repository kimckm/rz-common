package rz.cloud.order.model;

import lombok.Data;

/**
 * 订单明细。
 */
@Data
public class OrderItem {

	private int id;
	private int orderId;

	private Integer basePrice;
	private int qty; // 商品数量
	private String productName;
	private int productId;

}
