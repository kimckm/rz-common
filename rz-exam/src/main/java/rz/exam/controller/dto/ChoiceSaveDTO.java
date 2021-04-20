package rz.exam.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rz.exam.model.Choice;
import rz.exam.model.ChoiceOption;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChoiceSaveDTO extends Choice {

	private List<ChoiceOption> choiceOptions;

}
