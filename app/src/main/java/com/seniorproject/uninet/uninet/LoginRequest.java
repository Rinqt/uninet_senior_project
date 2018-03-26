package com.seniorproject.uninet.uninet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaany on 2.03.2018.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "webpage";
    private Map<String, String> params;


    public LoginRequest(String universityNumber, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("universityNumber", universityNumber);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

}
