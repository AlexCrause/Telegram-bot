package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CostTrackingMechanismService {

    private final SubscribersRepository subscribersRepository;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoBot cryptoBot;
    private double bitcoinPrice;


    // каждые 2 минуты
    @Scheduled(cron = "${telegram.bot.notify.delay.cronPrice}")
    public void getCostBitcoin() {
        log.info("[getCost]");
        try {
            bitcoinPrice = cryptoCurrencyService.getBitcoinPrice();
            log.info("[cost]: {}", bitcoinPrice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //каждые десять мин
    @Scheduled(cron = "${telegram.bot.notify.delay.cronSubscriber}")
    public void findSubscribersAndSendMessage() {
        log.info("find");
        List<Subscribers> subscribersList = subscribersRepository.findAll();
        for (Subscribers subscriber : subscribersList) {
            if (bitcoinPrice < Double.parseDouble(String.valueOf(subscriber.getPriceCrypto()))) {
                log.info("Подписчик с id: {} готов купить биткоин", subscriber.getId());
                cryptoBot.sendMessage(subscriber.getUserIdTelegram());
            }
        }
    }
}
