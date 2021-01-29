package rz.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rz.exam.mapper.CompletionMapper;
import rz.exam.model.Completion;
import rz.exam.service.CompletionService;

import java.util.List;

@Slf4j
@Service
public class CompletionServiceImpl implements CompletionService {

	@Autowired
	private CompletionMapper completionMapper;

	@Transactional(readOnly = true)
	@Override
	public List<Completion> list() {
		return completionMapper.selectList(Wrappers.emptyWrapper());
	}

}
