/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015040 강수
 * 최종작성일 : 2023.06. 03.
 * 함수설명 :  User+UserAccount data 유저정보가 앱 내부에서 쓰이는 데이터
 ************************************************/

package com.example.sungansungan12;
public class UserUsing {
    //파베인증에서 가져옴
    private static UserUsing instance;
    private String email="초기값";
    private String uid ="초기값";
    //리얼타임에서 가져옴
    private String address ="초기값";
    private String birthdate ="초기값";
    private String birthday="초기값";
    private String birthyear ="초기값";
    private String name ="초기값";


    public UserUsing() {
    }
    public static UserUsing getInstance() {
        if (instance == null) {
            instance = new UserUsing();
        }
        return instance;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email =email;
    }

    public String getUid() {return uid;}
    public  void setUid(String uid) {this.uid =uid;}

    public String getAddress() {
        return address;
    }
    public  void setAddress(String address) {
        this.address =address;
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

    public String getBirthyear() {
        return birthyear;
    }
    public  void setBirthyear(String birthyear) {
        this.birthyear =birthyear;
    }

    public String getName() {
        return name;
    }
    public  void setName(String name) {
        this.name =name;
    }
}
