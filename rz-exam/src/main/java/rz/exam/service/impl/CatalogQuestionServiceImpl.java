package rz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.exam.mapper.CatalogQuestionMapper;
import rz.exam.model.CatalogQuestion;
import rz.exam.service.CatalogQuestionService;

@Service
public class CatalogQuestionServiceImpl extends ServiceImpl<CatalogQuestionMapper, CatalogQuestion> implements CatalogQuestionService {
}
