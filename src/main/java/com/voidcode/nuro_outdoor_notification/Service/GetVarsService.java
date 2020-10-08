package com.voidcode.nuro_outdoor_notification.Service;

import com.voidcode.nuro_outdoor_notification.ConstDefine;
import com.voidcode.nuro_outdoor_notification.Entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class GetVarsService {
  private PostEntity postEntity;

  @Autowired
  public void setPostEntity(PostEntity postEntity) {
    this.postEntity = postEntity;
  }

  public boolean doGetVars() {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(ConstDefine.initURI))
            .headers(ConstDefine.headerPost)
            .GET()
            .build();

    try {
      HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (!StringUtils.isEmpty(result.body())) {
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String, Object> resultMap = jsonParser.parseMap(result.body());
        postEntity.setServiceId(
            StringUtils.isEmpty(resultMap.get("serviceId"))
                ? ""
                : (String) resultMap.get("serviceId"));
        postEntity.setIspCnNo(
            StringUtils.isEmpty(resultMap.get("ispCnNo")) ? "" : (String) resultMap.get("ispCnNo"));
        postEntity.setHouseScheduleDay(
            StringUtils.isEmpty(resultMap.get("houseScheduleDay"))
                ? ""
                : (String) resultMap.get("houseScheduleDay"));
        ConstDefine.outdrScheduleDayCompareBase = (String) resultMap.get("outdrScheduleDay");
        if (StringUtils.isEmpty(ConstDefine.outdrScheduleDayCompareBase)) {
          System.out.println("No reserved construction date found, will report the nearest one.");
        } else {
          System.out.println(
              "Current reserved construction date is: "
                  + ConstDefine.outdrScheduleDayCompareBase
                  + ", will only report the ones prior to it.");
        }
        ConstDefine.outdrScheduleDayCompareBase = ConstDefine.outdrScheduleDayCompareBase.replace("/", "");

        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }
}
