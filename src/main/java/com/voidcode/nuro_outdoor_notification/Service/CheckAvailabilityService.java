package com.voidcode.nuro_outdoor_notification.Service;

import com.google.gson.Gson;
import com.voidcode.nuro_outdoor_notification.ConstDefine;
import com.voidcode.nuro_outdoor_notification.Entity.PostEntity;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CheckAvailabilityService {
  public String doCheck(PostEntity postEntity) {
    Gson gson = new Gson();

    String postBody = gson.toJson(postEntity);

    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(ConstDefine.checkURL))
            .timeout(Duration.ofMinutes(1))
            .headers(ConstDefine.headerPost)
            .POST(HttpRequest.BodyPublishers.ofString(postBody))
            .build();
    try {
      HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
      // Parse result
      JsonParser jsonParser = JsonParserFactory.getJsonParser();
      Map<String, Object> resultMap = jsonParser.parseMap(result.body());
      System.out.println(
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
              + " ["
              + postEntity.getHopePeriodFrom()
              + " ~ "
              + postEntity.getHopePeriodTo()
              + "] "
              + resultMap.get("selectOutdrList"));
      // Check if selectOutdrList is empty
      if (resultMap.get("selectOutdrList") != null
          && resultMap.get("selectOutdrList").getClass().equals(ArrayList.class)) {
        @SuppressWarnings("unchecked")
        ArrayList<Map<String, String>> selectOutdrList =
            (ArrayList<Map<String, String>>) resultMap.get("selectOutdrList");

        if (selectOutdrList.size() > 0) {
          for (Map<String, String> outdr : selectOutdrList) {
            // Check if true then return msg
            if (StringUtils.isEmpty(ConstDefine.outdrScheduleDayCompareBase)
                || Integer.parseInt(outdr.get("issueDate").replace("/", ""))
                    < Integer.parseInt(ConstDefine.outdrScheduleDayCompareBase)) {
              if (outdr.get("issueNoFrameAm").equals("○")) {
                return outdr.get("issueDate") + " AM";
              }
              if (outdr.get("issueNoFramePm").equals("○")) {
                return outdr.get("issueDate") + " PM";
              }
            }
          }
        }
      } else {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
