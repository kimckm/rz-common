package rz.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.exam.model.Choice;

@Mapper
public interface ChoiceMapper extends BaseMapper<Choice> {
}
