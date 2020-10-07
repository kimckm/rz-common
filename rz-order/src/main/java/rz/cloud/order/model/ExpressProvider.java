package rz.cloud.order.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 快递服务商
 */
@Data
public class ExpressProvider {

	private int id;
	private String name;
	private int status; // 0=合作中, 1=已停用

	private LocalDateTime createAt;

}
