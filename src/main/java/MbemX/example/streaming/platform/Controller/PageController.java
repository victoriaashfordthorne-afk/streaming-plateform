package MbemX.example.streaming.platform.Controller;



import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/movies")
    public String movies(Model model) {

        model.addAttribute("movies", java.util.List.of(
                "Avengers",
                "Inception",
                "The Dark Knight"
        ));

        return "movies";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/verification")
    public String verification() {
        return "verification";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
    @GetMapping("/series")
    public String series(Model model) {

        model.addAttribute("series", java.util.List.of(
                "Stranger Things",
                "Breaking Bad",
                "The Last of Us"
        ));

        return "series";
    }
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
    @GetMapping("/movie-details")
    public String movieDetails() {
        return "movie-details";
    }
    @GetMapping("/watch")
    public String watch() {
        return "watch";
    }
    @GetMapping("/my-list")
    public String myList() {
        return "my-list";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

}

