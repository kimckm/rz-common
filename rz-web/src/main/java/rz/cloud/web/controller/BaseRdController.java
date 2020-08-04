package rz.cloud.web.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseRdController<T, S extends IService<T>> {

    @Autowired
    protected S service;

    @GetMapping("/{id}")
    public Object getOne(@PathVariable String id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.removeById(id);
    }

    @GetMapping
    public Object page(@RequestParam Map<String, Object> params) {
        Wrapper<T> wrapper = this.doWrapper(params);

        Long pageSize = (Long) params.get("pageSize");
        if (Objects.isNull(pageSize)) {
            pageSize = 20L;
        }

        if (pageSize == 0L) { // pageSize为0, 表示不分页
            return service.list(wrapper);
        }

        Long current = (Long) params.get("current");
        if (Objects.isNull(current)) {
            current = 1L;
        }

        Page<T> resultPage = service.page(new Page<>(current, pageSize), wrapper);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("list", resultPage.getRecords());

        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("current", resultPage.getCurrent());
        paginationMap.put("pageSize", pageSize);
        paginationMap.put("total", resultPage.getTotal());
        responseMap.put("pagination", paginationMap);

        return responseMap;
    }

    protected Wrapper<T> doWrapper(Map<String, Object> params) {
        if (Objects.isNull(params)) {
            return new QueryWrapper<>();
        }

        QueryWrapper<T> qw = new QueryWrapper<>();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (Objects.nonNull(value)) {
                qw.eq(key, value);
            }
        }

        return qw;
    }

}
