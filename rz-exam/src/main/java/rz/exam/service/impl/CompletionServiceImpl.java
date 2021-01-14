package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rz.exam.mapper.CompletionDoMapper;
import rz.exam.model.Completion;
import rz.exam.model.CompletionDO;
import rz.exam.service.CompletionService;

import java.util.List;
import java.util.stream.Collectors;

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
			// TODO 数据转换
			return c;
		}).collect(Collectors.toList());
	}

}
