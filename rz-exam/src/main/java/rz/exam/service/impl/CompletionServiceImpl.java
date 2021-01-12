package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.CompletionMapper;
import rz.exam.model.Completion;
import rz.exam.service.CompletionService;

@Service
public class CompletionServiceImpl extends ServiceImpl<CompletionMapper, Completion> implements CompletionService {
}
