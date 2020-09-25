package ua.arlabunakty;

import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaulController {

    private final String appName;

    public DefaulController(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("name", "Guest");
        return "home";
    }

    /**
     * Return greeting template for user provided name with random delay.
     *
     * @param name  - user name
     * @param model - template model attributes holder
     * @return template name
     * @throws InterruptedException when during sleep period thread was interrupted.
     */
    @GetMapping("/hello")
    public String homePageWithUserGreeting(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) throws InterruptedException {
        Thread.sleep(new Random().nextInt(5000));

        model.addAttribute("appName", appName);
        model.addAttribute("name", name);

        return "home";
    }
}

