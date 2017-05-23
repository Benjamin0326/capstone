package com.android.capstone.yolo.model;

/**
 * Created by sung9 on 2017-05-23.
 */

public class UserInfo {
    private String name, email, password;
    public void setName(String _name){
        name = _name;
    }
    public void setEmail(String _email){
        email = _email;
    }
    public void setPassword(String _password){
        password = _password;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
