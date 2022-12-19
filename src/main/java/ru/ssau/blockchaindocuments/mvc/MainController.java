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
    private final String UPLOAD_DIR = "src/main/resources/uploads/";
    BlockKeeper blockKeeper;

    @Autowired
    public MainController(BlockKeeper blockKeeper) {
        this.blockKeeper = blockKeeper;
    }

    @GetMapping("/")
    public String showMain(){
        return "main";
    }

    @PostMapping("/upload")
    public String receiveFile(@RequestPart MultipartFile file, RedirectAttributes attributes) throws IOException {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // normalize the file path
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
//        try {
//            Path path = Paths.get(UPLOAD_DIR + fileName);
//            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        blockKeeper.addBlock(file);
        // return success response
//        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }
}
