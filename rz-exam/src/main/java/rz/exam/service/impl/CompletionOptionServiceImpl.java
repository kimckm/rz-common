package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.CompletionOptionMapper;
import rz.exam.model.CompletionOption;
import rz.exam.service.CompletionOptionService;

@Service
public class CompletionOptionServiceImpl extends ServiceImpl<CompletionOptionMapper, CompletionOption> implements CompletionOptionService {
}
