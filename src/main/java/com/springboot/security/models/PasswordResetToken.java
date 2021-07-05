package com.springboot.security.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author KMCruz
 * 7/4/2021
 */
@Data
@Entity
public class PasswordResetToken {

    private final static int EXPIRATION = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resetToken;

    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public PasswordResetToken createPasswordResetToken(User user, String resetToken){
        this.user=user;
        this.resetToken=resetToken;
        this.expiryDate=calculateExpiryDate(EXPIRATION);
        return this;
    }

    private Date calculateExpiryDate(int expiryInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE,expiryInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
