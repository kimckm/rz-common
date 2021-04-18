package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.ChoiceOptionMapper;
import rz.exam.model.ChoiceOption;
import rz.exam.service.ChoiceOptionService;

@Service
public class ChoiceOptionServiceImpl extends ServiceImpl<ChoiceOptionMapper, ChoiceOption> implements ChoiceOptionService {
}
