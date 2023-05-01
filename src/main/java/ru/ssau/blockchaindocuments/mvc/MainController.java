package ru.ssau.blockchaindocuments.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/auth")
    public String showAuth(){
        return "auth";
    }

    @GetMapping("/reg")
    public String showReg(){
        return "reg";
    }

    @GetMapping("/docblock")
    public String showMain() {
        return "docBlock";
    }
}
