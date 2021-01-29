package rz.exam.model;

import lombok.Data;

@Data
public class CompletionCorrect {

	private long id;
	private long completionId;
	private String code;
	private String expected;

}
