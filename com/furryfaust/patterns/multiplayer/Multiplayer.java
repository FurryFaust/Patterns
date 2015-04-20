package com.furryfaust.patterns.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Multiplayer {

    public boolean isConnected;

    public Multiplayer() {
        checkConnection("Kevin", "bear");
    }

    public void checkConnection(String username, String password) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://localhost:3000/api/auth?username=" + username + "&password="
                + password);
        Net.HttpResponseListener response = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("Status Code", String.valueOf(httpResponse.getStatus().getStatusCode()));
                Gdx.app.log("Response", httpResponse.getResultAsString());
                isConnected = Boolean.valueOf(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                isConnected = false;
                Gdx.app.log("Status Code", "Failed");
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
