package rz.exam.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompletionPracticeDTO extends CompletionSaveDTO {

	private String type;

}
