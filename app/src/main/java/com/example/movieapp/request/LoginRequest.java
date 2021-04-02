package com.example.movieapp.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    public static final String URL = "http://itb2021.dothome.co.kr/MovieLogin.php";
    private String ID,password;
    private Map<String,String> map; // 위 변수를 담아주는 공간als++

    public LoginRequest(String id, String password, Response.Listener<String> listener) {
        super(Method.POST, URL,listener,null);
        this.ID = id;
        this.password = password;
        map = new HashMap<String, String>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        map.put("ID", this.ID);
        map.put("password",this.password);
        return map;
    }

}
