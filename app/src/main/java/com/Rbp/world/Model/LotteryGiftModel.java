package com.Rbp.world.Model;

public class LotteryGiftModel {

   private    String id;
   private   String lotary_name;
   private   String details;
   private   String logo;
   private   String status;
   private    double terget_amount;
   private   double total_bid;
   private    double total_bid_amount;
   private    String UploadDate;


   public LotteryGiftModel(String id, String lotary_name, String details, String logo, String status, double terget_amount, double total_bid, double total_bid_amount, String uploadDate) {
      this.id = id;
      this.lotary_name = lotary_name;
      this.details = details;
      this.logo = logo;
      this.status = status;
      this.terget_amount = terget_amount;
      this.total_bid = total_bid;
      this.total_bid_amount = total_bid_amount;
      UploadDate = uploadDate;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getLotary_name() {
      return lotary_name;
   }

   public void setLotary_name(String lotary_name) {
      this.lotary_name = lotary_name;
   }

   public String getDetails() {
      return details;
   }

   public void setDetails(String details) {
      this.details = details;
   }

   public String getLogo() {
      return logo;
   }

   public void setLogo(String logo) {
      this.logo = logo;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public double getTerget_amount() {
      return terget_amount;
   }

   public void setTerget_amount(double terget_amount) {
      this.terget_amount = terget_amount;
   }

   public double getTotal_bid() {
      return total_bid;
   }

   public void setTotal_bid(double total_bid) {
      this.total_bid = total_bid;
   }

   public double getTotal_bid_amount() {
      return total_bid_amount;
   }

   public void setTotal_bid_amount(double total_bid_amount) {
      this.total_bid_amount = total_bid_amount;
   }

   public String getUploadDate() {
      return UploadDate;
   }

   public void setUploadDate(String uploadDate) {
      UploadDate = uploadDate;
   }
}
