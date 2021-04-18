package rz.exam.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 选择题
 */
@Data
public class Choice {

	private long id;
	private String question;
	private LocalDateTime createdAt;

}
