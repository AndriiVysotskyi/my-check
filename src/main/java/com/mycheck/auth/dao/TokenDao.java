package com.mycheck.auth.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TOKENS")
@NoArgsConstructor
public class TokenDao {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(columnDefinition = "varchar(100)")
    String token;

    @Column(columnDefinition = "varchar(100)")
    String userId;

    LocalDateTime creationTimestamp;

    private TokenDao(String userId) {
        this.userId = userId;
    }

    public static TokenDao of(String userId) {
        return new TokenDao(userId);
    }
}
