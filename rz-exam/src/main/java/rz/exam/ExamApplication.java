package rz.exam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;
import rz.exam.controller.CompletionController;
import rz.exam.controller.dto.CompletionSaveDTO;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ExamApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ExamApplication.class);
		CompletionController completionController = run.getBean(CompletionController.class);

		ObjectMapper om = new ObjectMapper();
		try {
			File file = ResourceUtils.getFile("classpath:data.json");
			List<CompletionSaveDTO> list = om.readValue(file, new TypeReference<List<CompletionSaveDTO>>() {});
			for (CompletionSaveDTO dto : list) {
				completionController.save(dto);
			}
		} catch (Exception e) {
			log.error("", e);
		}


	}

}
