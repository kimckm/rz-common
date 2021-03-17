package rz.exam.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 分页查询响应结果
 */
@Data
public class PageResultDTO<T> {

	private List<T> list;

	private long current;
	private long size;
	private long total;

	public PageResultDTO<T> list(List<T> list) {
		this.list = list;
		return this;
	}

	public PageResultDTO<T> current(long current) {
		this.current = current;
		return this;
	}

	public PageResultDTO<T> size(long size) {
		this.size = size;
		return this;
	}

	public PageResultDTO<T> total(long total) {
		this.total = total;
		return this;
	}

}
