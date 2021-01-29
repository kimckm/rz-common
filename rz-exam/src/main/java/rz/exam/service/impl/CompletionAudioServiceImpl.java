package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.CompletionAudioMapper;
import rz.exam.model.CompletionAudio;
import rz.exam.service.CompletionAudioService;

@Service
public class CompletionAudioServiceImpl extends ServiceImpl<CompletionAudioMapper, CompletionAudio> implements CompletionAudioService {
}
