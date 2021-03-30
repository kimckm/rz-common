package rz.topic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.topic.mapper.TopicMapper;
import rz.topic.model.Topic;
import rz.topic.service.TopicService;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
}
