package com.android.capstone.yolo.model;

/**
 * Created by sung9 on 2017-05-23.
 */

public class Login {
    private String status_code, message, token;

    public void setStatus_code(String _status_code){
        status_code = _status_code;
    }
    public void setMessage(String _message){
        message = _message;
    }
    public void setUser_token(String _user_token){
        token = _user_token;
    }
    public String getStatus_code(){
        return status_code;
    }
    public String getMessage(){
        return message;
    }
    public String getUser_token(){
        return token;
    }
}
