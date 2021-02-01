package rz.exam.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rz.exam.model.Exam;
import rz.exam.model.ExamQuestion;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExamSaveDTO extends Exam {

	private List<ExamQuestion> questions;

}
