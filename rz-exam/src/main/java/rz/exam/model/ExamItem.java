package rz.exam.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_exam_item")
public class ExamItem {

	private long id;
	private long examId;
	private long questionId;
	private int seq;

	@TableField("question_type")
	private QuestionTypeEnum questionType;

}
