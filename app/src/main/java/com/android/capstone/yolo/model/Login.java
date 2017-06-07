package com.android.capstone.yolo.model;

/**
 * Created by sung9 on 2017-05-23.
 */

public class Login {
    private String message, token;
    private int statusCode;

    public void setStatus_code(int _status_code){
        statusCode = _status_code;
    }
    public void setMessage(String _message){
        message = _message;
    }
    public void setUser_token(String _user_token){
        token = _user_token;
    }
    public int getStatus_code(){
        return statusCode;
    }
    public String getMessage(){
        return message;
    }
    public String getUser_token(){
        return token;
    }
}
