package com.springboot.security.tasks;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import com.springboot.security.repositories.UserRepository;
import com.springboot.security.repositories.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author KMCruz
 * 7/4/2021
 */
@Service
@Transactional
public class TokenPurgeTask {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public TokenPurgeTask(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "${cron.scheduled.time}" )
    public void purgeToken(){
        LOGGER.info("Purging Expired Verification Tokens");

        Date now = Date.from(Instant.now());
        List<VerificationToken> tokenList = verificationTokenRepository.findAllByExpiryDateIsLessThanEqual(now);
        tokenList.stream().forEach(
                token->{
                    User user = token.getUser();
                    userRepository.delete(user);
                });

    }
}
