package rz.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.exam.model.Exam;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
}
