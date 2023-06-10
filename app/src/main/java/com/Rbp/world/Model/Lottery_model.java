package com.Rbp.world.Model;


public   class Lottery_model  {

   private String id;

   private String number;

   private String status;

   private String point;

   private String date;

   public Lottery_model(String id, String number, String status, String point, String date) {
      this.id = id;
      this.number = number;
      this.status = status;
      this.point = point;
      this.date = date;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getPoint() {
      return point;
   }

   public void setPoint(String point) {
      this.point = point;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }
}
