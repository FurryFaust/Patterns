package com.furryfaust.patterns.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Multiplayer {

    public boolean temp;

    public Multiplayer() {
    }

    public void checkConnection(String username, String password) {
        temp = false;
        
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://localhost:3000/api/auth?username=" + username + "&password="
                + password);
        Net.HttpResponseListener response = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                temp = Boolean.valueOf(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                temp = false;
            }

            @Override
            public void cancelled() {
                temp = false;
            }

        };
        Gdx.net.sendHttpRequest(request, response);
    }

}
