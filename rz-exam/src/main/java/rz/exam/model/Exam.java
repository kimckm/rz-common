package rz.exam.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {

	@TableId(type = IdType.AUTO)
	private long id;

	private String title;
	private LocalDateTime createAt;

}
