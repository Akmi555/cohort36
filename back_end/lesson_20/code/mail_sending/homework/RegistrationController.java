package mail_sending.homework;

import de.ait_tr.lesson_17_mail_sending.exception_handling.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public Response register(@RequestBody User user) {
        service.register(user);
        return new Response("Registration complete. Please check your email.");
    }

    @GetMapping
    public Response registrationConfirm(@RequestParam String code) {
        service.registrationConfirm(code);
        return new Response("Registration confirmed successfully");
    }
}