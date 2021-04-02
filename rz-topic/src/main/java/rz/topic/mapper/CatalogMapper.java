package rz.topic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.topic.model.Catalog;

@Mapper
public interface CatalogMapper extends BaseMapper<Catalog> {
}
