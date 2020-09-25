package ua.arlabunakty;

import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaulController {

    private final String appName;

    public DefaulController(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    /**
     * Greeting endpoint for Guest user.
     *
     * @return greeting configured model and view;
     */
    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("appName", appName);
        modelAndView.addObject("name", "Guest");
        modelAndView.setViewName("home");
        return modelAndView;
    }

    /**
     * Returns greeting template for user provided name with random delay.
     *
     * @param name  - user name from query parameters
     * @return greeting configured model and view
     * @throws InterruptedException when during sleep period thread was interrupted.
     */
    @GetMapping("/hello")
    public ModelAndView homePageWithUserGreeting(
            @RequestParam(name = "name", required = false, defaultValue = "") String name) throws InterruptedException {
        Thread.sleep(new Random().nextInt(5000));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("appName", appName);
        modelAndView.addObject("name", name);
        modelAndView.setViewName("home");

        return modelAndView;
    }
}

