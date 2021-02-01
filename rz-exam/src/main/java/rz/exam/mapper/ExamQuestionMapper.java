package rz.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.exam.model.ExamQuestion;

@Mapper
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
}
