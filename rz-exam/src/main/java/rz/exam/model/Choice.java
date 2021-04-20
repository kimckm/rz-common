package rz.exam.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 选择题
 */
@Data
public class Choice {

	@TableId(type = IdType.AUTO)
	private long id;
	private String question;
	private LocalDateTime createdAt;

}
