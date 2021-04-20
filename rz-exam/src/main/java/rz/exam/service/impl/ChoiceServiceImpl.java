package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rz.exam.controller.dto.ChoiceSaveDTO;
import rz.exam.mapper.ChoiceMapper;
import rz.exam.mapper.ChoiceOptionMapper;
import rz.exam.model.Choice;
import rz.exam.model.ChoiceOption;
import rz.exam.service.ChoiceService;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ChoiceServiceImpl extends ServiceImpl<ChoiceMapper, Choice> implements ChoiceService {

	@Autowired
	private ChoiceOptionMapper choiceOptionMapper;

	@Transactional
	@Override
	public long save(ChoiceSaveDTO choiceSaveDTO) {
		choiceSaveDTO.setCreatedAt(LocalDateTime.now());
		this.baseMapper.insert(choiceSaveDTO);

		if (Objects.nonNull(choiceSaveDTO.getChoiceOptions())) {
			for (int i = 0; i < choiceSaveDTO.getChoiceOptions().size(); i++) {
				ChoiceOption option = choiceSaveDTO.getChoiceOptions().get(i);
				option.setSeq(i);
				option.setChoiceId(choiceSaveDTO.getId());
				choiceOptionMapper.insert(option);
			}
		}

		return choiceSaveDTO.getId();
	}

	@Transactional
	@Override
	public void deleteOne(Long id) {
		if (Objects.isNull(id)) {
			return;
		}
		choiceOptionMapper.delete(Wrappers.lambdaQuery(ChoiceOption.class)
			.eq(ChoiceOption::getChoiceId, id)
		);
		this.baseMapper.deleteById(id);
	}

}
