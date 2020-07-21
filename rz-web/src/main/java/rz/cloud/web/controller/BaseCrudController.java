package rz.cloud.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.*;

public abstract class BaseCrudController<T, S extends IService<T>> extends BaseRdController<T, S> {

    @PostMapping
    public Object save(@RequestBody T t) {
        service.save(t);
        return t;
    }

    @PatchMapping("/{id}")
    public Object update(@PathVariable String id, @RequestBody T t) {
        service.update(new UpdateWrapper<>(t).eq("id", id));
        return service.getById(id);
    }

}
