package rz.exam.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {

	private long id;

	private String title;
	private LocalDateTime createAt;

}
