package rz.topic.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 主题
 */
@Data
public class Topic {

	private long id;
	private String title;
	private LocalDateTime createdAt;

}
