package rz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.model.Completion;

public interface CompletionService extends IService<Completion> {

	long save(CompletionSaveDTO completionSaveDTO);

	CompletionSaveDTO findOne(Long id);

	boolean updateById(Long id, CompletionSaveDTO completionSaveDTO);

}
