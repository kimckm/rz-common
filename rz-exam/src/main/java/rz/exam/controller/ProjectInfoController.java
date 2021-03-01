package rz.exam.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class ProjectInfoController {

	@Value("${app.version}")
	private String version;

	@GetMapping("/version")
	public Object version() {
		return version;
	}

}
