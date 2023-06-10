package com.Rbp.world.Model;

public class LotterySingleModel {

    private String id;

    private String UserName;

    private String UploadDate;

    private String lotteryNumber;

    private String amount;

    private String PhoneNumber;

    public LotterySingleModel(String id, String userName, String uploadDate, String lotteryNumber, String amount, String phoneNumber) {
        this.id = id;
        UserName = userName;
        UploadDate = uploadDate;
        this.lotteryNumber = lotteryNumber;
        this.amount = amount;
        PhoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }

    public String getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(String lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
