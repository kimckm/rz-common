package rz.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rz.exam.model.Catalog;

@Mapper
public interface CatalogMapper extends BaseMapper<Catalog> {
}
