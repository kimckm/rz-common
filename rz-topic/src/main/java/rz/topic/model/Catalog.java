package rz.topic.model;

import lombok.Data;

/**
 * 目录
 */
@Data
public class Catalog {

	private long id;
	private String title;
	private Long pid;
	private int seq;

	private long topicId;

}
