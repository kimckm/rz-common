package rz.cloud.order.model;

import lombok.Data;

/**
 * 快递明细
 */
@Data
public class ExpressItem {

	private int id;
	private int expressId;

	private int orderId;
	private int orderItemId;

}
