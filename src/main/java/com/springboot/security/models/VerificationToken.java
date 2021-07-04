package com.springboot.security.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Data
@Entity
public class VerificationToken {

    private static final int EXPIRATION = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationToken;

    private Date expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public VerificationToken createOrUpdateVerificationToken(User user, String registrationToken) {
        this.registrationToken = registrationToken;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        return this;
    }

    private Date calculateExpiryDate(int expiryInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE,expiryInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
