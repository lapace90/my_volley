package com.example.myvolley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private Context context;
    private RequestQueue queue;


    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register(final String LOGIN, final String EMAIL, final String PASSWORD, final String PASSWORD2, final RetoursPHP callBack){

        String url = "http://192.168.1.175/retoucallBackHP/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PHP", response);
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        callBack.toutOk("Votre compte a bien été créé");
                    }
                    else {
                        callBack.pasOk(json.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.d("PHP", "passage dans le catch de register" + e);
                    callBack.systemError("Une erreur est survenue.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if((error instanceof NetworkError)){
                    callBack.systemError("Une erreur réseau s'est produite, \n\r impossible de joindre le serveur");
                } else if (error instanceof VolleyError) {
                    callBack.systemError("Une errreur s'est produite, impossible de joindre le serveur");
                }
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("login", LOGIN);
                map.put("email", EMAIL);
                map.put("password", PASSWORD);
                map.put("password2", PASSWORD2);
                Log.d("PHP", "Envoie du map " + map);
                return map;
            }
        };

        queue.add(request);
    }

    public void login(final String LOGIN, final String PASSWORD, final LoginCallBack callBack) {
        String url = "http://192.168.1.175/retoucallBackHP/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean("error");
                    if (!error) {
                        callBack.onSuccess("Connexion réussie!");
                    } else {
                        callBack.onError(jsonResponse.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.d("PHP", String.valueOf(e));
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if((error instanceof NetworkError)) {
                    callBack.systemError("Une erreur resau s'est produite, \n\r impossible de joindre le serveur");
                } else if(error instanceof  VolleyError) {
                    callBack.systemError("Une erreur s'est produite, impossible de joindre le serveur");
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login", LOGIN);
                map.put("password", PASSWORD);
                return map;
            }
        };

        queue.add(request);
    }



    public interface LoginCallBack {
        void onSuccess(String message);
        void onError(String message);
        void systemError(String message);

    }

}
