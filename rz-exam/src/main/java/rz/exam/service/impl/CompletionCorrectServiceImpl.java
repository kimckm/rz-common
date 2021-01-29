package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.CompletionCorrectMapper;
import rz.exam.model.CompletionCorrect;
import rz.exam.service.CompletionCorrectService;

@Service
public class CompletionCorrectServiceImpl extends ServiceImpl<CompletionCorrectMapper, CompletionCorrect> implements CompletionCorrectService {
}
