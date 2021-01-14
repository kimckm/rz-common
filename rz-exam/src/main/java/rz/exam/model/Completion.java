package rz.exam.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 填空题
 */
@Data
public class Completion {

	private long id;
	private String question;

	private LocalDateTime createAt;

	private List<CompletionOption> correct;

}
