package backend.backend.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Con {
    @GetMapping("/")
    public String hoem() {
        return "index.html";
    }
}
