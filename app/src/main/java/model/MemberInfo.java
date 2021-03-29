package model;

import com.example.movieapp.LoginDialog;

import java.util.Objects;

public class MemberInfo {
    private String image;
    private String name;
    private String phoneNumber;
    private String email;
    private String birth;
    private String gender;


    public MemberInfo(String image, String name, String phoneNumber, String email, String birth, String gender) {
        this.image = image;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MemberInfo){
            MemberInfo memberInfo = (MemberInfo) o;
            return this.phoneNumber.equals(memberInfo.phoneNumber) && this.email.equals(memberInfo.email);
        }
        return false;
    }

}
