package com.example.movieapp.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class
RegisterRequest extends StringRequest {
    public static final String URL = "http://itb2021.dothome.co.kr/MovieRegister.php";
    private String ID,password,name,image,phone,email,birth,gender;
    private int userAge;
    private Map<String,String> map;

    public RegisterRequest(String ID, String password, String name, String phone, String email, String birth, String gender, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        this.ID = ID;
        this.password = password;
        this.name = name;
//        this.image = image;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.gender = gender;

    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        map.put("ID",ID);
        map.put("password",password);
        map.put("name",name);
//        map.put("image",image);
        map.put("phone",phone);
        map.put("email",email);
        map.put("birth",birth);
        map.put("gender",gender);
        return map;
    }
}
