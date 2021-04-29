package de.presti.botbase.bot;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessage;

public class Webhook {

    public static void sendWebhook(WebhookMessage message, long channelid, String webhooktoken) {
        WebhookClient wcl = WebhookClient.withId(channelid, webhooktoken);
        wcl.send(message);
        wcl.close();
    }

}