package rz.cloud.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rz.cloud.web.model.Web;
import rz.cloud.web.service.WebService;

@RequestMapping("/web")
@RestController
public class WebController extends BaseCrudController<Web, WebService> {

}
