package mail_sending.homework;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(User user);

    void registrationConfirm(String code);
}