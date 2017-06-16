package com.example.kiang.reportcard;

public class ReportCard {

    private String UserName;
    private String UserGrade;
    private String UserNanodegree;
    private int UserImage;

    public ReportCard(String uName, String uNanodegree, String uGrade, int uImage) {
        UserName = uName;
        UserNanodegree = uNanodegree;
        UserGrade = uGrade;
        UserImage = uImage;
    }

    public int getUserImage() {
        return this.UserImage;
    }

    public void setUserImage(int UserImage) {
        this.UserImage = UserImage;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserNanodegree() {
        return this.UserNanodegree;
    }

    public void setUserNanodegree(String UserNanodegree) {
        this.UserNanodegree = UserNanodegree;
    }

    public String getUserGrade() {
        return this.UserGrade;
    }

    public void setUserGrade(String UserGrade) {
        UserGrade = UserGrade;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "UserName='" + UserName + '\'' +
                ", UserGrade='" + UserGrade + '\'' +
                ", UserNanodegree='" + UserNanodegree + '\'' +
                ", UserImage=" + UserImage +
                '}';
    }


}


