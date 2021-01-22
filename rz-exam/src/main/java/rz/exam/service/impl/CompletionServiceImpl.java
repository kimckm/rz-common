package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import rz.exam.mapper.CompletionDoMapper;
import rz.exam.model.Completion;
import rz.exam.model.CompletionDO;
import rz.exam.model.CompletionOption;
import rz.exam.model.Ext;
import rz.exam.service.CompletionService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompletionServiceImpl implements CompletionService {

	@Autowired
	private CompletionDoMapper completionDoMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Completion> list() {
		List<CompletionDO> doList = completionDoMapper.selectList(Wrappers.emptyWrapper());

		return doList.stream().map(d -> {
			Completion c = new Completion();
			BeanUtils.copyProperties(d, c);
			try {
				ObjectMapper om = new ObjectMapper();
				List<CompletionOption> completionOptionList = om.readValue(d.getCorrect(), new TypeReference<List<CompletionOption>>() {});
				c.setCorrect(completionOptionList);

				if (StringUtils.hasLength(d.getExt())) {
					Ext ext = om.readValue(d.getExt(), Ext.class);
					c.setExt(ext);
				}
			} catch (IOException e) {
				log.error("", e);
			}
			return c;
		}).collect(Collectors.toList());
	}

}
