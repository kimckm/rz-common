package rz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rz.exam.controller.dto.ChoiceSaveDTO;
import rz.exam.model.Choice;

public interface ChoiceService extends IService<Choice> {

	long save(ChoiceSaveDTO choiceSaveDTO);

	void deleteOne(Long id);

}
