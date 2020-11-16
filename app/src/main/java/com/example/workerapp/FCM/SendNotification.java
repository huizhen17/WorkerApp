package com.example.workerapp.FCM;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotification {
    public JsonObjectRequest specifUser(String id, String title, String body) {
        JSONObject mainObj = new JSONObject();
        String URL = "https://fcm.googleapis.com/fcm/send";
        try {
            mainObj.put("to", id);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", title);
            notificationObj.put("body", body);
            notificationObj.put("sound", "default");
            mainObj.put("data", notificationObj);
            JSONObject priority = new JSONObject();
            priority.put("priority", "high");
            mainObj.put("android", priority);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAAEVdAOBc:APA91bFfCDgYd6PdeU1Q9tcUQECHrNn3gC0HuXEGLRqeQdZm1B0TRE2Gcpi7A8rZwRX9OIYiuuekwKjltcBJN8RePQ2gfLVyhhFWHPxE4G65hp3vHW7dZPMVTgzT193PHYri7ti6ZlOr");
                    return header;
                }
            };

            return request;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
