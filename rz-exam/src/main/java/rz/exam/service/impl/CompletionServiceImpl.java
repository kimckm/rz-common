package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import rz.exam.controller.dto.CompletionSaveDTO;
import rz.exam.mapper.*;
import rz.exam.model.*;
import rz.exam.service.CompletionService;
import rz.exam.util.SnowFlake;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CompletionServiceImpl extends ServiceImpl<CompletionMapper, Completion> implements CompletionService {

	@Autowired
	private CompletionCorrectMapper completionCorrectMapper;
	@Autowired
	private CompletionAudioMapper completionAudioMapper;
	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamQuestionMapper examQuestionMapper;
	@Autowired
	private CatalogQuestionMapper catalogQuestionMapper;

	@Transactional
	@Override
	public long save(CompletionSaveDTO completionSaveDTO) {
		completionSaveDTO.setId(SnowFlake.generateId());
		completionSaveDTO.setCreatedAt(LocalDateTime.now());
		this.getBaseMapper().insert(completionSaveDTO);

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

		if (Objects.nonNull(completionSaveDTO.getExamId())) {
			Exam exam = examMapper.selectById(completionSaveDTO.getExamId());
			if (Objects.isNull(exam)) {
				throw new IllegalArgumentException("examId无效!");
			}
			ExamQuestion examQuestion = new ExamQuestion();
			examQuestion.setId(SnowFlake.generateId());
			examQuestion.setExamId(exam.getId());
			examQuestion.setQuestionId(completionSaveDTO.getId());
			examQuestionMapper.insert(examQuestion);
		}

		if (Objects.nonNull(completionSaveDTO.getCatalogId())) {
			CatalogQuestion catalogQuestion = new CatalogQuestion();
			catalogQuestion.setId(SnowFlake.generateId());
			catalogQuestion.setCatalogId(completionSaveDTO.getCatalogId());
			catalogQuestion.setQuestionId(completionSaveDTO.getId());
			catalogQuestionMapper.insert(catalogQuestion);
		}

		return completionSaveDTO.getId();
	}

	@Transactional(readOnly = true)
	@Override
	public CompletionSaveDTO findOne(Long id) {
		Completion completion = this.getBaseMapper().selectById(id);

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

	@Transactional
	@Override
	public boolean updateById(Long id, CompletionSaveDTO completionSaveDTO) {
		completionSaveDTO.setId(id);

		if (Objects.nonNull(completionSaveDTO.getCorrect())) {
			completionSaveDTO.getCorrect()
				.forEach(c -> {
					if (c.getId() > 0) {
						completionCorrectMapper.updateById(c);
					} else {
						c.setId(SnowFlake.generateId());
						c.setCompletionId(id);
						completionCorrectMapper.insert(c);
					}
				});
		}

		return this.updateById(completionSaveDTO);
	}
}
