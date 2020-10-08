package com.voidcode.nuro_outdoor_notification.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.voidcode.nuro_outdoor_notification.ConstDefine;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SendNotificationService {
  public void doSendNotification(String msg) {
    // Send push notification through ifttt
    if (!StringUtils.isEmpty(ConstDefine.iftttUri)) {
      HttpClient client = HttpClient.newBuilder().build();

      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(ConstDefine.iftttUri))
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString("{ \"value1\" : \"" + msg + "\" }"))
              .build();

      try {
        HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Send notification through telegram
    if (!StringUtils.isEmpty(ConstDefine.telegramBotToken)
        && !StringUtils.isEmpty(ConstDefine.telegramChatId)) {
      TelegramBot bot = new TelegramBot(ConstDefine.telegramBotToken);
      SendResponse response =
          bot.execute(new SendMessage(ConstDefine.telegramChatId, "Nuro: " + msg));
    }

    // Print out message on system console as well
    System.out.println(msg);
  }
}
