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

    public void register(final String LOGIN, final String EMAIL, final String PASSWORD, final String PASSWORD2, final RetoursPHP rP){

        String url = "http://192.168.1.175/retourPHP/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PHP", response);
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        rP.toutOk("Votre compte a bien été créé");
                    }
                    else {
                        rP.pasOk(json.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.d("PHP", "passage dans le catch de register" + e);
                    rP.systemError("Une erreur est survenue.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if((error instanceof NetworkError)){
                    rP.systemError("Une erreur réseau s'est produite, \n\r impossible de joindre le serveur");
                } else if (error instanceof VolleyError) {
                    rP.systemError("Une errreur s'est produite, impossible de joindre le serveur");
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

    public void login(final String LOGIN, final String PASSWORD, final RetoursPHP rP) {
        String url = "http://192.168.1.175/retourPHP/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean("error");
                    if (!error) {
                        rP.toutOk("Connexion réussie!");
                    } else {
                        rP.pasOk("Identifiants invalides");
                    }
                } catch (JSONException e) {
                    Log.d("PHP", String.valueOf(e));
                    rP.systemError("Erreur lors de l'analyse de la réponse du serveur");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rP.systemError("Erreur de connexion au serveur");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", LOGIN);
                params.put("password", PASSWORD);
                return params;
            }
        };

        queue.add(request);
    }



    public interface RetoursPHP {
        void toutOk(String message);
        void pasOk(String message);
        void systemError(String message);
    }

}
