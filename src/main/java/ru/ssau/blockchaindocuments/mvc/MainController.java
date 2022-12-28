package ru.ssau.blockchaindocuments.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ssau.blockchaindocuments.utils.BlockKeeper;

import java.io.IOException;

@Controller
public class MainController {
    BlockKeeper blockKeeper;

    @Autowired
    public MainController(BlockKeeper blockKeeper) {
        this.blockKeeper = blockKeeper;
    }

    @GetMapping("/")
    public String showMain() {
        return "main";
    }

    @PostMapping("/upload")
    public String receiveFile(@RequestPart MultipartFile file, RedirectAttributes attributes) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
        } else {
            try {
                blockKeeper.addBlock(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/";
    }

    @PostMapping("/check")
    public String checkFile(@RequestPart MultipartFile file, RedirectAttributes attributes) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
        } else {
            boolean check;
            try {
                check = blockKeeper.isFileValid(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(check)
                attributes.addFlashAttribute("message", "file is valid");
            else
                attributes.addFlashAttribute("message", "file is not valid");
        }
        return "redirect:/";
    }
}
