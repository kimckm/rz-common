package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_choice_option")
public class ChoiceOption {

	private long id;
	private long choiceId;

	@TableField("is_correct")
	private boolean correct;

	private int seq;

	private String value;

}
