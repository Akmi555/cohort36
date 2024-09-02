package de.ait_tr.g_36_shop.service;

import de.ait_tr.g_36_shop.domain.entity.User;
import de.ait_tr.g_36_shop.service.interfaces.ConfirmationService;
import de.ait_tr.g_36_shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    //fields
    private final JavaMailSender sender;
    private final freemarker.template.Configuration mailConfig;
    private final ConfirmationService confirmationService;

    // constructor
    public EmailServiceImpl(JavaMailSender sender, Configuration mailConfig, ConfirmationService confirmationService) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.confirmationService = confirmationService;

        mailConfig.setDefaultEncoding("UTF-8"); // кодировка письма
        mailConfig.setTemplateLoader(new ClassTemplateLoader(EmailServiceImpl.class, "/mail/")); // где лежит шаблон письма
    }

    @Override
    public void sendConfirmationEmail(User user) {

        MimeMessage message = sender.createMimeMessage(); // создаем объеккт
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8"); // помощник по созданию письма
        String emailText = generateEmailText(user); // получаем текст(шаблон) письма

        try {
            helper.setFrom("leonid.kleimann@ait-tr.de"); // ящик
            helper.setTo(user.getEmail()); // адресат
            helper.setSubject("Registration"); // тема
            helper.setText(emailText, true); // содержание и формат

            sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateEmailText(User user) {
        try {
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");
            String code = confirmationService.generateConfirmationCode(user); // получаем код
            // localhost:8080/register?code=87fdsf6sf-fsffsd-f87sdf -> ссылка для завершения регистрации
            String url = "http://localhost:8080/register?code=" + code; // формируем ссылку

            // Для добавления данных в шаблон создаём мап:
            // name -> Vasya
            // link -> localhost:8080/register?code=87fdsf6sf-fsffsd-f87sdf
            Map<String, Object> templateMap = new HashMap<>(); // укладываем пару значений User + Url
            templateMap.put("name", user.getUsername()); // берем пользователя
            templateMap.put("link", url); // берем ссылку

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap); // делаем письмо

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
