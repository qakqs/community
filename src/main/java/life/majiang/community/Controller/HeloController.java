package life.majiang.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HeloController {

    @GetMapping("/")
    public String hello(){
        return "index";
    }
}
