package com.example.backend_prj.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
@Data
@Entity
public class ExpirationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    private boolean logged;

    private final static int EXPIRATION_TIME = 120;

    private Date expirationTime;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;

    public ExpirationToken(){}
    public ExpirationToken(User user){
        this.user = user;
        this.logged = true;
        this.expirationTime = calculateExpirationalDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationalDate(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }

}
