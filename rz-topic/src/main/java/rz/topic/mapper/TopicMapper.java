package rz.topic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.topic.model.Topic;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
}
