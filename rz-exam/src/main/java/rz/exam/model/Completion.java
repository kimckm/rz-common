package rz.exam.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 填空题
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Completion {

	private long id;
	private String question;

	private LocalDateTime createAt;

}
