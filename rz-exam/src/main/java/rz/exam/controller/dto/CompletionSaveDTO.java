package rz.exam.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rz.exam.model.Completion;
import rz.exam.model.CompletionAudio;
import rz.exam.model.CompletionCorrect;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompletionSaveDTO extends Completion {

	private List<CompletionCorrect> correct;
	private List<CompletionAudio> audio;

	private Long examId;
	private Long catalogId;

}
