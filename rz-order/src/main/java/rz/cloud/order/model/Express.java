package rz.cloud.order.model;

import lombok.Data;

/**
 * 快递
 */
@Data
public class Express {

	private int id;

	private String no;
//	private String providerName;
	private int providerId;
	private Integer fee; // 快递费

	private String province;
	private String city;
	private String zone;
	private String address; // 详细地址

	private String consigneeName; // 收货人姓名
	private String consigneeMobile; // 收货人手机

	private int status; // 0=待配货, 1=已就绪, 2=已发货

}
