package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class SubscribersService {

    private final SubscribersRepository subscribersRepository;

    public void addSubscriber(Message message) {
        Long userIdTelegram = message.getFrom().getId();
        if (!subscribersRepository.existsByUserIdTelegram(userIdTelegram)) {
            Subscribers subscriber = Subscribers.builder()
                    .userIdTelegram(userIdTelegram)
                    .priceCrypto(null)
                    .build();
            subscribersRepository.save(subscriber);
        } else {
            throw new RuntimeException("Пользователь уже есть в базе данных");
        }
    }
}
