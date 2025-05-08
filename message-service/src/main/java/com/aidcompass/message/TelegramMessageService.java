package com.aidcompass.message;

import com.aidcompass.message.models.MessageDto;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;


@Service
public class TelegramMessageService implements MessageService {

    @Value("${message_service.tg_bot.token}")
    private String botToken;


    @Async
    @Override
    public void sendMessage(MessageDto messageDto) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.telegram.org/bot" + botToken + "/sendMessage")).newBuilder();
        urlBuilder.addQueryParameter("chat_id", messageDto.recipient());
        urlBuilder.addQueryParameter("text", messageDto.subject() + " " + messageDto.text());
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).execute();
    }
}
