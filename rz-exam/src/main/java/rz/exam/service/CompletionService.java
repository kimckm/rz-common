package rz.exam.service;

import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.model.Completion;

import java.util.List;

public interface CompletionService {

	List<Completion> list();

	long save(CompletionSaveDTO completionSaveDTO);

}
