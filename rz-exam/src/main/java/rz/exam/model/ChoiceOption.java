package rz.exam.model;

import lombok.Data;

/**
 * 选择题选项
 */
@Data
public class ChoiceOption {

	private long id;
	private String option;
	private int seq;
	private boolean correct;

	private long choiceId;

}
