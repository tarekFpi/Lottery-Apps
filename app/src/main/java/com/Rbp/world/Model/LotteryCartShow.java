package com.Rbp.world.Model;

public class LotteryCartShow {

   private String id;

   private String Date;

   private String Number;

   private String point;

   public LotteryCartShow(String id, String date, String number, String point) {
      this.id = id;
      Date = date;
      Number = number;
      this.point = point;
   }

   public String getPoint() {
      return point;
   }

   public void setPoint(String point) {
      this.point = point;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getDate() {
      return Date;
   }

   public void setDate(String date) {
      Date = date;
   }

   public String getNumber() {
      return Number;
   }

   public void setNumber(String number) {
      Number = number;
   }
}
