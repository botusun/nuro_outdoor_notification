package com.voidcode.nuro_outdoor_notification;

import org.springframework.stereotype.Component;

@Component
public class ConstDefine {
  // Check URI
  public static String checkURL =
      "https://hikarisvc.jp/const_web/cnstPrgrss/rest/outdrWeeklyCalendarSelect/outdrDailyCalendarSelect.json";

  // To get global vars
  public static String initURI =
      "https://hikarisvc.jp/const_web/cnstPrgrss/rest/cnstPrgrss/initial.json";

  // Whether the initialization succeeded
  public static boolean isInitiated = false;

  // How many weeks to check
  public static Integer checkWeeks;

  // Access key to Nuro website
  public static String nuroKey;

  // URI of IFTTT
  public static String iftttUri;

  // Current selection
  public static String outdrScheduleDayCompareBase;

  // Header define when doing Get action
  public static String[] headerGet =
      new String[] {
        "Accept",
        "application/json;charset=UTF-8",
        "X-Requested-By",
        "ajax",
        "User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36",
        "Sec-Fetch-Site",
        "same-origin",
        "Sec-Fetch-Mode",
        "cors",
        "Sec-Fetch-Dest",
        "empty"
      };

  // Header define when doing Post action
  public static String[] headerPost =
      new String[] {
        "Accept",
        "application/json;charset=UTF-8",
        "X-Requested-By",
        "ajax",
        "User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36",
        "Sec-Fetch-Site",
        "same-origin",
        "Sec-Fetch-Mode",
        "cors",
        "Sec-Fetch-Dest",
        "empty",
        "Content-Type",
        "application/json;charset=UTF-8",
        "Referer",
        "https://hikarisvc.jp/const_web/cnstPrgrss/?k="
      };

  // The delay of IFTTT push notification is huge, add Telegram support
  public static String telegramBotToken;
  public static long telegramChatId;
}
