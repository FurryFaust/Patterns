package com.furryfaust.patterns.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Multiplayer {

    public final String api = "http://patterns.furryfaust.com/api/";
    public String temp;

    public Multiplayer() {
    }

    public void checkConnection(String username, String password) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "auth.php?username=" + username + "&password="
                + password);
        Net.HttpResponseListener response = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                temp = httpResponse.getResultAsString();
            }

            @Override
            public void failed(Throwable t) {
                temp = "";
            }

            @Override
            public void cancelled() {
                temp = "";
            }

        };
        Gdx.net.sendHttpRequest(request, response);
    }

    public void createAccount(String username, String password) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "createaccount.php?username=" + username + "&password="
                + password);


        Net.HttpResponseListener response = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                temp = httpResponse.getResultAsString();
            }

            @Override
            public void failed(Throwable t) {
                temp = "";
            }

            @Override
            public void cancelled() {
                temp = "";
            }

        };
        Gdx.net.sendHttpRequest(request, response);
    }

}
