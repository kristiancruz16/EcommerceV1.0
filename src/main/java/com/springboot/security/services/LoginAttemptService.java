package com.springboot.security.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author KMCruz
 * 7/6/2021
 */
@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String,Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache= CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public void loginFailed(String key){
        int attempts = 0;
        try{
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        attempts++;
        attemptsCache.put(key,attempts);
    }
    public void loginOrResetPasswordSuccess(String key){
        attemptsCache.invalidate(key);
    }

    public boolean isIpBlocked(String key){
        try {
            int numberOfAttempts = attemptsCache.get(key);
           return numberOfAttempts>=MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
