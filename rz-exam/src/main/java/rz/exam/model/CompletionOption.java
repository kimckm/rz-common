package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 填空题答案
 */
@Data
@TableName("t_completion_option")
public class CompletionOption {

	private long id;
	private long fillId;

	private String code;
	private String answer;

}
