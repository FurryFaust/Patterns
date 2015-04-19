package com.furryfaust.patterns.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Multiplayer {

    public boolean isConnected;

    public Multiplayer() {
        checkConnection();
    }

    public void checkConnection() {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://furryfaust.com");
        Net.HttpResponseListener response = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                isConnected = true;
            }

            @Override
            public void failed(Throwable t) {
                isConnected = false;
            }

            @Override
            public void cancelled() {
                isConnected = false;
            }
        };
        Gdx.net.sendHttpRequest(request, response);
    }


    public void requestGame() {
        if (isConnected) {

        }
    }

}
