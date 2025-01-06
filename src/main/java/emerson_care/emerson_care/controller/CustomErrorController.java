package emerson_care.emerson_care.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController {

    @RequestMapping("/public/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        model.addAttribute("status", statusCode);

        if (statusCode == 404) {
            return "public/error/404";
        }

        // Fallback to default error message if template is missing
        try {
            return "public/error/generic";
        } catch (Exception e) {
            return "error-default";
        }
    }
}