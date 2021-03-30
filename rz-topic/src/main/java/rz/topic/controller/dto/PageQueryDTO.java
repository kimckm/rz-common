package rz.topic.controller.dto;

import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class PageQueryDTO {

	private long size = 10;
	private long current = 1;

}
