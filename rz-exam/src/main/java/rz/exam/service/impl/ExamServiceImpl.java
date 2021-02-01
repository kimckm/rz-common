package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rz.exam.controller.dto.ExamSaveDTO;
import rz.exam.mapper.ExamMapper;
import rz.exam.mapper.ExamQuestionMapper;
import rz.exam.model.Exam;
import rz.exam.model.ExamQuestion;
import rz.exam.service.ExamService;

import java.time.LocalDateTime;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private ExamQuestionMapper examQuestionMapper;

	@Override
	public long insertOne(ExamSaveDTO examSaveDTO) {
		examSaveDTO.setCreateAt(LocalDateTime.now());
		examMapper.insert(examSaveDTO);
		for (ExamQuestion examQuestion: examSaveDTO.getQuestions()) {
			examQuestion.setExamId(examSaveDTO.getId());
			examQuestionMapper.insert(examQuestion);
		}
		return examSaveDTO.getId();
	}

}
