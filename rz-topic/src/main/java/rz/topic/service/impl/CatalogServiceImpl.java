package rz.topic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.topic.mapper.CatalogMapper;
import rz.topic.model.Catalog;
import rz.topic.service.CatalogService;

@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {
}
