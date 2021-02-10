package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.mapper.CompletionAudioMapper;
import rz.exam.mapper.CompletionCorrectMapper;
import rz.exam.mapper.CompletionMapper;
import rz.exam.model.Completion;
import rz.exam.model.CompletionAudio;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionService;
import rz.exam.util.SnowFlake;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CompletionServiceImpl implements CompletionService {

	@Autowired
	private CompletionMapper completionMapper;
	@Autowired
	private CompletionCorrectMapper completionCorrectMapper;
	@Autowired
	private CompletionAudioMapper completionAudioMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Completion> list() {
		return completionMapper.selectList(Wrappers.emptyWrapper());
	}

	@Transactional
	@Override
	public long save(CompletionSaveDTO completionSaveDTO) {
		completionSaveDTO.setId(SnowFlake.generateId());
		completionSaveDTO.setCreateAt(LocalDateTime.now());
		completionMapper.insert(completionSaveDTO);

		if (Objects.nonNull(completionSaveDTO.getCorrect())) {
			for (CompletionCorrect completionCorrect : completionSaveDTO.getCorrect()) {
				completionCorrect.setCompletionId(completionSaveDTO.getId());
				completionCorrect.setId(SnowFlake.generateId());
				completionCorrectMapper.insert(completionCorrect);
			}
		}

		if (Objects.nonNull(completionSaveDTO.getAudio())) {
			for (CompletionAudio completionAudio : completionSaveDTO.getAudio()) {
				completionAudio.setCompletionId(completionSaveDTO.getId());
				completionAudio.setId(SnowFlake.generateId());
				completionAudioMapper.insert(completionAudio);
			}
		}

		return completionSaveDTO.getId();
	}

	@Transactional(readOnly = true)
	@Override
	public CompletionSaveDTO findOne(Long id) {
		Completion completion = completionMapper.selectById(id);

		CompletionSaveDTO completionSaveDTO = new CompletionSaveDTO();
		BeanUtils.copyProperties(completion, completionSaveDTO);
		completionSaveDTO.setCorrect(completionCorrectMapper.selectList(Wrappers.<CompletionCorrect>lambdaQuery()
			.eq(CompletionCorrect::getCompletionId, id)
		));
		completionSaveDTO.setAudio(completionAudioMapper.selectList(Wrappers.<CompletionAudio>lambdaQuery()
			.eq(CompletionAudio::getCompletionId, id)
		));
		return completionSaveDTO;
	}
}
