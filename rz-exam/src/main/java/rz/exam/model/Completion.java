package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 填空题
 */
@Data
@TableName("t_completion")
public class Completion {

	private long id;
	private String question;

	@TableField("create_at")
	private LocalDateTime createAt;

}
