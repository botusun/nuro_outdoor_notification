package com.voidcode.nuro_outdoor_notification;

import com.voidcode.nuro_outdoor_notification.Service.GetVarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class Initialization {
  private Environment env;
  private GetVarsService getVarsService;

  @Autowired
  public void setEnv(Environment env) {
    this.env = env;
  }

  @Autowired
  public void setGetVarsService(GetVarsService getVarsService) {
    this.getVarsService = getVarsService;
  }

  @PostConstruct
  public void readParameter() {
    if (!StringUtils.isEmpty(env.getProperty("nuroKey"))
        && !StringUtils.isEmpty(env.getProperty("iftttUri"))) {
      ConstDefine.isInitiated = true;
      ConstDefine.nuroKey = env.getProperty("nuroKey");
      ConstDefine.iftttUri = env.getProperty("iftttUri");
      ConstDefine.telegramBotToken = env.getProperty("telegramBotToken");
      ConstDefine.telegramChatId =
          Long.parseLong(Objects.requireNonNull(env.getProperty("telegramChatId")));
      ConstDefine.headerPost[ConstDefine.headerPost.length - 1] += ConstDefine.nuroKey;
      ConstDefine.checkWeeks =
          Integer.parseInt(Objects.requireNonNull(env.getProperty("checkWeeks")));
    } else {
      System.out.println("nuroKey is not defined.");
      System.exit(1);
    }

    // Continue to get other needed information if nuroKey is set
    while (!getVarsService.doGetVars()) {
      try {
        TimeUnit.SECONDS.sleep(60);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    ConstDefine.isInitiated = true;
  }
}
