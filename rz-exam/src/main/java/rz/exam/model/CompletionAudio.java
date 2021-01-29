package rz.exam.model;

import lombok.Data;

@Data
public class CompletionAudio {

	private long id;
	private long completionId;
	private String name;
	private String src;

}
