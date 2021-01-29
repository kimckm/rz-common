package rz.exam.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 填空题
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Completion {

	@TableId(type = IdType.AUTO)
	private long id;
	private String question;

	private LocalDateTime createAt;

}
