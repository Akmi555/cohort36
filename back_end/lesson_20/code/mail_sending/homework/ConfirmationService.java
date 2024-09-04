package mail_sending.homework;

import de.ait_tr.lesson_17_mail_sending.user.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);

    User getUserByConfirmationCode(String code);
}