package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.Optional;

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

    public void subscribeUserToCryptocurrencyPrice(Message message){
        String text = message.getText();
        Long userIdTelegram = message.getFrom().getId();
        String stringCryptoPrice = text.substring(11).trim();
        String replaceStringCryptoPrice = stringCryptoPrice.replace(",", ".");
        double doubleCryptoPrice = Double.parseDouble(replaceStringCryptoPrice);
        BigDecimal bigDecimalCryptoPrice = BigDecimal.valueOf(doubleCryptoPrice);
        if (text.matches(".*\\b\\d+(?:[.,]\\d+)?\\b.*")) {
            Optional<Subscribers> subscriber = subscribersRepository.findByUserIdTelegram(userIdTelegram);
            if (subscriber.isPresent()){
                Subscribers subscribers = subscriber.get();
                subscribers.setPriceCrypto(bigDecimalCryptoPrice);
                subscribersRepository.save(subscribers);
            }
        } else {
            throw new RuntimeException("Некорректный ввод");
        }
    }

    public BigDecimal getSubscriptionUser(Message message) {
        Long userIdTelegram = message.getFrom().getId();
        Optional<Subscribers> subscriber = subscribersRepository.findByUserIdTelegram(userIdTelegram);
        if (subscriber.isPresent()){
            Subscribers subscribers = subscriber.get();
            return subscribers.getPriceCrypto();
        } else {
            throw new RuntimeException("Пользователя нет");
        }
    }

    public BigDecimal deleteSubscription(Message message) {
        Long userIdTelegram = message.getFrom().getId();
        Optional<Subscribers> subscriber = subscribersRepository.findByUserIdTelegram(userIdTelegram);
        if (subscriber.isPresent()){
            Subscribers subscribers = subscriber.get();
            if (subscribers.getPriceCrypto() != null ) {
                BigDecimal priceCrypto = subscribers.getPriceCrypto();
                subscribers.setPriceCrypto(null);
                subscribersRepository.save(subscribers);
                return priceCrypto;
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Пользователя нет");
        }
    }
}
