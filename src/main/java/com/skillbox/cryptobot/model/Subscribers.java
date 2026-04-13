package com.skillbox.cryptobot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "subscribers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscribers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Column(name = "user_id_telegram", nullable = false, unique = true)
    private Long userIdTelegram;

    @Column(name = "price_crypto")
    private Long priceCrypto;
}
