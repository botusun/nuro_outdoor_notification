package com.voidcode.nuro_outdoor_notification.ScheduledTasks;

import com.voidcode.nuro_outdoor_notification.ConstDefine;
import com.voidcode.nuro_outdoor_notification.Entity.PostEntity;
import com.voidcode.nuro_outdoor_notification.Service.CheckAvailabilityService;
import com.voidcode.nuro_outdoor_notification.Service.SendNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class CheckAvailabilityTask {
  // Inject notification service
  private SendNotificationService sendNotificationService;
  private CheckAvailabilityService checkAvailabilityService;
  private PostEntity postEntity;

  @Autowired
  public void setSendNotificationService(SendNotificationService sendNotificationService) {
    this.sendNotificationService = sendNotificationService;
  }

  @Autowired
  public void setCheckAvailabilityService(CheckAvailabilityService checkAvailabilityService) {
    this.checkAvailabilityService = checkAvailabilityService;
  }

  @Autowired
  public void setPostEntity(PostEntity postEntity) {
    this.postEntity = postEntity;
  }

  @Scheduled(fixedDelay = 300000) // Check every 5min
  public void doCheck() {
    // In case of not having been fully initialized
    if (ConstDefine.isInitiated) {
      // Iterator count left (in weeks)
      int iteratorCountLeft = ConstDefine.checkWeeks;
      int daysOffset = 7 * (iteratorCountLeft + 1);
      while (iteratorCountLeft > 0) {
        // Set date to postEntity
        postEntity.setHopePeriodFrom(
            LocalDate.now()
                .plusDays(daysOffset - 7 * iteratorCountLeft)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        postEntity.setHopePeriodTo(
            LocalDate.now()
                .plusDays(daysOffset + 6 - 7 * iteratorCountLeft)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        postEntity.setWeeklyDailyFlg("2");

        if (StringUtils.isEmpty(ConstDefine.outdrScheduleDayCompareBase)
            || Integer.parseInt(postEntity.getHopePeriodFrom())
                < Integer.parseInt(ConstDefine.outdrScheduleDayCompareBase)) {
          String checkResultString = checkAvailabilityService.doCheck(postEntity);
          if (!StringUtils.isEmpty(checkResultString)) {
            sendNotificationService.doSendNotification("Found an available option: " + checkResultString);
            ConstDefine.outdrScheduleDayCompareBase = checkResultString.split(" ")[0].replace("/", "");
          }
        }

        iteratorCountLeft--;

        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
