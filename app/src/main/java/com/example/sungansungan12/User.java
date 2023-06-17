/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015040 강수
 * 최종작성일 : 2023.06. 03.
 * 함수설명 :  서비스에 사용할 유저 정보
 ************************************************/

package com.example.sungansungan12;
public class User {

    private String name="이름";
    private String address="주소";
    private String birthyear="년";
    private String birthdate="월";
    private String birthday="일";

    public User() {
    }


    public String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name =name;
    }

    public String getAddress() {
        return address;
    }

    public  void setAddress(String address) {
        this.address =address;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public  void setBirthyear(String birthyear) {
        this.birthyear =birthyear;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public  void setBirthdate(String birthdate) {
        this.birthdate =birthdate;
    }

    public String getBirthday() {
        return birthday;
    }

    public  void setBirthday(String birthday) {
        this.birthday =birthday;
    }
}