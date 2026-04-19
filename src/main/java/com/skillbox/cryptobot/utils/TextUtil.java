package com.skillbox.cryptobot.utils;

public class TextUtil {

    public static final String LIST_AVAILABLE_COMMANDS = """
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /get_price - получить стоимость биткоина
                 /subscribe [число] - подписаться на уведомления о цене
                 /get_subscription - возвращает текущую подписку
                 /unsubscribe - отменяет подписку пользователя
                """;

    public static final String CURRENT_PRICE_BITCOIN = "Текущая цена биткоина ";

    public static String toString(double value) {
        return String.format("%.3f", value);
    }
}
