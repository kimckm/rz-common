package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_exam")
public class Exam {

	private long id;
	private String title;

	@TableField("create_at")
	private LocalDateTime createAt;

}
