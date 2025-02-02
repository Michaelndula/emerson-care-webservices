package emerson_care.emerson_care.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContentController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world!";
    }
}