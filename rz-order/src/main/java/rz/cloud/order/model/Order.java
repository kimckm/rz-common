package rz.cloud.order.model;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单，中台处理的最小单元。
 */
@Data
@TableName("t_order")
public class Order {

	private int id;

	@TableField(condition = SqlCondition.LIKE)
	private String no;

	private Integer basePrice;

	private LocalDateTime createAt;

	// 0=草稿 1=已提交 2=已确认 3=已配货 4=已发货 99=已取消
	private int status;

	@TableField(condition = SqlCondition.LIKE)
	private String remark;

	@TableField(exist = false)
	private List<OrderItem> orderItemList;

}
