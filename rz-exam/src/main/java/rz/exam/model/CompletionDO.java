package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_completion")
public class CompletionDO {

	private long id;
	private String question;

	@TableField("create_at")
	private LocalDateTime createAt;

	private String correct;

}
