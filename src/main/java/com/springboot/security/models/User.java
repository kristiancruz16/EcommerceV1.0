package com.springboot.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author KMCruz
 * 7/1/2021
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
public class User {

    public User() {
        super();
        this.enabled=false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private PasswordResetToken passwordResetToken;

    public boolean hasPasswordResetToken(){
        return this.getPasswordResetToken()!=null;
    }
}
