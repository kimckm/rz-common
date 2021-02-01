package rz.exam.model;

import lombok.Data;

@Data
public class ExamQuestion {

	private long id;
	private long examId;
	private long questionId;

}
