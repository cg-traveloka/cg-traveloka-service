package com.cgtravelokaservice.entity.token;

import com.cgtravelokaservice.entity.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @Nullable
    private String refreshToken;
    @Nullable
    private Timestamp tokenIssuedAt;
    @Nullable
    private Timestamp tokenExpiredAt;
    private Timestamp codeExpiredAt;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;
}

