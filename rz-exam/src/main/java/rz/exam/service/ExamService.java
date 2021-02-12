package rz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rz.exam.controller.dto.ExamSaveDTO;
import rz.exam.model.Exam;

public interface ExamService extends IService<Exam> {

	long insertOne(ExamSaveDTO examSaveDTO);

	ExamSaveDTO findOne(Long id);

}
