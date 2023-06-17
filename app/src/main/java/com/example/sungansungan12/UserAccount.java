/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015040 강수
 * 최종작성일 : 2023.06. 03.
 * 함수설명 :  파이어베이스 고유 유저 식별자 발급용도
 ************************************************/

package com.example.sungansungan12;

public class UserAccount {

    public UserAccount(){}

    private String idToken;

    public  String getIdToken(){ return idToken; }

    public  void setIdToken(String idToken) {
        this.idToken =idToken;
    }

    private String emailId;

    public  String getEmailId(){ return emailId; }

    public  void setEmailId(String emailId) {
        this.emailId =emailId;
    }

    private String password;

    public  String getPassword(){ return password; }

    public  void setPassword(String password) {
        this.password =password;
    }





}