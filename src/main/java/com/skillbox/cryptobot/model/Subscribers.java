package com.skillbox.cryptobot.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "subscribers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscribers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Column(name = "user_id_telegram", nullable = false, unique = true)
    private Long userIdTelegram;

    @Column(name = "price_crypto")
    private BigDecimal priceCrypto;
}
