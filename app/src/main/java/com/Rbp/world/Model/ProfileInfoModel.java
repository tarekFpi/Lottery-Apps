package com.Rbp.world.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileInfoModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("pin_Password")
    @Expose
    private Object pinPassword;
    @SerializedName("remember_token")
    @Expose
    private String rememberToken;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("mobile_No")
    @Expose
    private Object mobileNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("day")
    @Expose
    private Object day;
    @SerializedName("month")
    @Expose
    private Object month;
    @SerializedName("year")
    @Expose
    private Object year;
    @SerializedName("picture_Url")
    @Expose
    private Object pictureUrl;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("join_Date")
    @Expose
    private String joinDate;
    @SerializedName("user_Status")
    @Expose
    private Object userStatus;
    @SerializedName("votar_Id")
    @Expose
    private String votarId;
    @SerializedName("nomine_Name")
    @Expose
    private String nomineName;
    @SerializedName("nomine_Releation")
    @Expose
    private String nomineReleation;
    @SerializedName("nomine_Date_Of_Birth")
    @Expose
    private String nomineDateOfBirth;
    @SerializedName("father_Name")
    @Expose
    private String fatherName;
    @SerializedName("mother_Name")
    @Expose
    private String motherName;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("date_Of_Birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("UK")
    @Expose
    private Object uK;
    @SerializedName("reserve_Balance")
    @Expose
    private Object reserveBalance;
    @SerializedName("bank_Name")
    @Expose
    private Object bankName;
    @SerializedName("account_No")
    @Expose
    private Object accountNo;
    @SerializedName("occuption")
    @Expose
    private Object occuption;
    @SerializedName("bank_Transction")
    @Expose
    private Object bankTransction;
    @SerializedName("moblie_Transction")
    @Expose
    private Object moblieTransction;
    @SerializedName("agent_Id")
    @Expose
    private Object agentId;
    @SerializedName("unnion")
    @Expose
    private Object unnion;
    @SerializedName("thana")
    @Expose
    private Object thana;
    @SerializedName("district")
    @Expose
    private Object district;
    @SerializedName("division")
    @Expose
    private Object division;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("ward")
    @Expose
    private Object ward;
    @SerializedName("account_Present_Status")
    @Expose
    private Object accountPresentStatus;
    @SerializedName("balance")
    @Expose
    private Object balance;
    @SerializedName("blood_Group")
    @Expose
    private Object bloodGroup;
    @SerializedName("educational_Qualification")
    @Expose
    private Object educationalQualification;
    @SerializedName("due")
    @Expose
    private Object due;
    @SerializedName("total_Due")
    @Expose
    private Object totalDue;
    @SerializedName("pv")
    @Expose
    private Object pv;

    private String package_type;

    private String designation;


    public ProfileInfoModel() {
    }

    public ProfileInfoModel(String name, String username, String email, Object mobileNo, String address, String sex, String votarId, String nomineName, String nomineReleation, String nomineDateOfBirth, String fatherName, String motherName, String religion, String dateOfBirth) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.mobileNo = mobileNo;
        this.address = address;
        this.sex = sex;
        this.votarId = votarId;
        this.nomineName = nomineName;
        this.nomineReleation = nomineReleation;
        this.nomineDateOfBirth = nomineDateOfBirth;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.religion = religion;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getPinPassword() {
        return pinPassword;
    }

    public void setPinPassword(Object pinPassword) {
        this.pinPassword = pinPassword;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getDay() {
        return day;
    }

    public void setDay(Object day) {
        this.day = day;
    }

    public Object getMonth() {
        return month;
    }

    public void setMonth(Object month) {
        this.month = month;
    }

    public Object getYear() {
        return year;
    }

    public void setYear(Object year) {
        this.year = year;
    }

    public Object getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(Object pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Object getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Object userStatus) {
        this.userStatus = userStatus;
    }

    public String getVotarId() {
        return votarId;
    }

    public void setVotarId(String votarId) {
        this.votarId = votarId;
    }

    public String getNomineName() {
        return nomineName;
    }

    public void setNomineName(String nomineName) {
        this.nomineName = nomineName;
    }

    public String getNomineReleation() {
        return nomineReleation;
    }

    public void setNomineReleation(String nomineReleation) {
        this.nomineReleation = nomineReleation;
    }

    public String getNomineDateOfBirth() {
        return nomineDateOfBirth;
    }

    public void setNomineDateOfBirth(String nomineDateOfBirth) {
        this.nomineDateOfBirth = nomineDateOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getuK() {
        return uK;
    }

    public void setuK(Object uK) {
        this.uK = uK;
    }

    public Object getReserveBalance() {
        return reserveBalance;
    }

    public void setReserveBalance(Object reserveBalance) {
        this.reserveBalance = reserveBalance;
    }

    public Object getBankName() {
        return bankName;
    }

    public void setBankName(Object bankName) {
        this.bankName = bankName;
    }

    public Object getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Object accountNo) {
        this.accountNo = accountNo;
    }

    public Object getOccuption() {
        return occuption;
    }

    public void setOccuption(Object occuption) {
        this.occuption = occuption;
    }

    public Object getBankTransction() {
        return bankTransction;
    }

    public void setBankTransction(Object bankTransction) {
        this.bankTransction = bankTransction;
    }

    public Object getMoblieTransction() {
        return moblieTransction;
    }

    public void setMoblieTransction(Object moblieTransction) {
        this.moblieTransction = moblieTransction;
    }

    public Object getAgentId() {
        return agentId;
    }

    public void setAgentId(Object agentId) {
        this.agentId = agentId;
    }

    public Object getUnnion() {
        return unnion;
    }

    public void setUnnion(Object unnion) {
        this.unnion = unnion;
    }

    public Object getThana() {
        return thana;
    }

    public void setThana(Object thana) {
        this.thana = thana;
    }

    public Object getDistrict() {
        return district;
    }

    public void setDistrict(Object district) {
        this.district = district;
    }

    public Object getDivision() {
        return division;
    }

    public void setDivision(Object division) {
        this.division = division;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getWard() {
        return ward;
    }

    public void setWard(Object ward) {
        this.ward = ward;
    }

    public Object getAccountPresentStatus() {
        return accountPresentStatus;
    }

    public void setAccountPresentStatus(Object accountPresentStatus) {
        this.accountPresentStatus = accountPresentStatus;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public Object getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Object bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Object getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(Object educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public Object getDue() {
        return due;
    }

    public void setDue(Object due) {
        this.due = due;
    }

    public Object getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Object totalDue) {
        this.totalDue = totalDue;
    }

    public Object getPv() {
        return pv;
    }

    public void setPv(Object pv) {
        this.pv = pv;
    }
}
