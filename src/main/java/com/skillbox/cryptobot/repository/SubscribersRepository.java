package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Subscribers;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscribersRepository extends JpaRepository<Subscribers, UUID> {

    boolean existsByUserIdTelegram(@NonNull Long id);
    Optional<Subscribers> findByUserIdTelegram(Long userId);
}
