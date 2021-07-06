package com.springboot.security.listeners;

import com.springboot.security.services.LoginAttemptService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KMCruz
 * 7/6/2021
 */
@Component
public class AuthenticationListener {
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;


    public AuthenticationListener(LoginAttemptService loginAttemptService, HttpServletRequest request) {
        this.loginAttemptService = loginAttemptService;
        this.request = request;
    }

    @EventListener
    public void  handlerAuthenticationFailureBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event){
        String xfHeader = request.getHeader("X-Forwarded-For");
        if(xfHeader==null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
        }else{
            String clientIp = xfHeader.split(",")[0];
            loginAttemptService.loginFailed(clientIp);
        }
    }

    @EventListener
    public void handlerAuthenticationSuccessEvent(AuthenticationSuccessEvent event){
        String xfHeader = request.getHeader("X-Forwarded-For");
        if(xfHeader==null) {
            loginAttemptService.loginOrResetPasswordSuccess(request.getRemoteAddr());
        }else{
            String clientIp = xfHeader.split(",")[0];
            loginAttemptService.loginOrResetPasswordSuccess(clientIp);
        }
    }
}
