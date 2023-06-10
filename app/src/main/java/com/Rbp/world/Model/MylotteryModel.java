package com.Rbp.world.Model;

public   class MylotteryModel {
    
 private String id;   
 private String code;   
 private double point;   
 private String date;   
 private String lotary_name;   
 private String winner_amount;
 private String prize_number;
 private String note_admin;   
 private String winn_status;


    public MylotteryModel(String id, String code, double point, String date, String lotary_name, String winner_amount, String prize_number, String note_admin, String winn_status) {
        this.id = id;
        this.code = code;
        this.point = point;
        this.date = date;
        this.lotary_name = lotary_name;
        this.winner_amount = winner_amount;
        this.prize_number = prize_number;
        this.note_admin = note_admin;
        this.winn_status = winn_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLotary_name() {
        return lotary_name;
    }

    public void setLotary_name(String lotary_name) {
        this.lotary_name = lotary_name;
    }

    public String getWinner_amount() {
        return winner_amount;
    }

    public void setWinner_amount(String winner_amount) {
        this.winner_amount = winner_amount;
    }

    public String getPrize_number() {
        return prize_number;
    }

    public void setPrize_number(String prize_number) {
        this.prize_number = prize_number;
    }

    public String getNote_admin() {
        return note_admin;
    }

    public void setNote_admin(String note_admin) {
        this.note_admin = note_admin;
    }

    public String getWinn_status() {
        return winn_status;
    }

    public void setWinn_status(String winn_status) {
        this.winn_status = winn_status;
    }
}
