package de.ait_tr.g_36_shop.service;

import de.ait_tr.g_36_shop.domain.entity.ConfirmationCode;
import de.ait_tr.g_36_shop.domain.entity.User;
import de.ait_tr.g_36_shop.repository.ConfirmationCodeRepository;
import de.ait_tr.g_36_shop.service.interfaces.ConfirmationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }


    @Override
    public String generateConfirmationCode(User user) {

        // LocalDateTime expired = LocalDateTime.now().plusDays(1); // production

        // for testing
        LocalDateTime expired = LocalDateTime.now().plusMinutes(1);

        // generate code
        String code = UUID.randomUUID().toString();

        // create object
        ConfirmationCode entity = new ConfirmationCode(code, expired, user); // create new code

        // save to repository
        repository.save(entity);
        return code;
    }
}
