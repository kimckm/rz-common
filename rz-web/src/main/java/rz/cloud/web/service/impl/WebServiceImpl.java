package rz.cloud.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rz.cloud.web.mapper.WebMapper;
import rz.cloud.web.model.Web;
import rz.cloud.web.service.WebService;

@Service
public class WebServiceImpl extends ServiceImpl<WebMapper, Web> implements WebService {
}
