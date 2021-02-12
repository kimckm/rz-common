package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rz.exam.controller.dto.ExamSaveDTO;
import rz.exam.mapper.ExamMapper;
import rz.exam.mapper.ExamQuestionMapper;
import rz.exam.model.Exam;
import rz.exam.model.ExamQuestion;
import rz.exam.service.ExamService;
import rz.exam.util.SnowFlake;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamQuestionMapper examQuestionMapper;

	@Transactional
	@Override
	public long insertOne(ExamSaveDTO examSaveDTO) {
		examSaveDTO.setCreateAt(LocalDateTime.now());
		examSaveDTO.setId(SnowFlake.generateId());
		examMapper.insert(examSaveDTO);
		for (ExamQuestion examQuestion: examSaveDTO.getQuestions()) {
			examQuestion.setExamId(examSaveDTO.getId());
			examQuestion.setId(SnowFlake.generateId());
			examQuestionMapper.insert(examQuestion);
		}
		return examSaveDTO.getId();
	}

	@Transactional(readOnly = true)
	@Override
	public ExamSaveDTO findOne(Long id) {
		ExamSaveDTO dto = new ExamSaveDTO();
		Exam exam = examMapper.selectById(id);
		BeanUtils.copyProperties(exam, dto);

		dto.setQuestions(examQuestionMapper.selectList(Wrappers.<ExamQuestion>lambdaQuery()
			.eq(ExamQuestion::getExamId, id)
		));
		return dto;
	}
}
