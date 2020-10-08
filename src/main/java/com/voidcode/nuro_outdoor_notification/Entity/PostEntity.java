package com.voidcode.nuro_outdoor_notification.Entity;

import org.springframework.stereotype.Component;

@Component
public class PostEntity {
  private String serviceId;
  private String ispCnNo;
  private String outdrTransferFlg = "1";
  private String hopePeriodFrom;
  private String hopePeriodTo;
  private String weeklyDailyFlg;
  private String houseScheduleDay;
  private String houseHopeDay;
  private String firstSecondFlg = "1";

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getIspCnNo() {
    return ispCnNo;
  }

  public void setIspCnNo(String ispCnNo) {
    this.ispCnNo = ispCnNo;
  }

  public String getOutdrTransferFlg() {
    return outdrTransferFlg;
  }

  public void setOutdrTransferFlg(String outdrTransferFlg) {
    this.outdrTransferFlg = outdrTransferFlg;
  }

  public String getHopePeriodFrom() {
    return hopePeriodFrom;
  }

  public void setHopePeriodFrom(String hopePeriodFrom) {
    this.hopePeriodFrom = hopePeriodFrom;
  }

  public String getHopePeriodTo() {
    return hopePeriodTo;
  }

  public void setHopePeriodTo(String hopePeriodTo) {
    this.hopePeriodTo = hopePeriodTo;
  }

  public String getWeeklyDailyFlg() {
    return weeklyDailyFlg;
  }

  public void setWeeklyDailyFlg(String weeklyDailyFlg) {
    this.weeklyDailyFlg = weeklyDailyFlg;
  }

  public String getHouseScheduleDay() {
    return houseScheduleDay;
  }

  public void setHouseScheduleDay(String houseScheduleDay) {
    this.houseScheduleDay = houseScheduleDay;
  }

  public String getHouseHopeDay() {
    return houseHopeDay;
  }

  public void setHouseHopeDay(String houseHopeDay) {
    this.houseHopeDay = houseHopeDay;
  }

  public String getFirstSecondFlg() {
    return firstSecondFlg;
  }

  public void setFirstSecondFlg(String firstSecondFlg) {
    this.firstSecondFlg = firstSecondFlg;
  }
}
