package com.furryfaust.apps.patterns.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.furryfaust.apps.patterns.Core;

public class Multiplayer {

    Core core;
    public final String api = "http://patterns.furryfaust.com/api/";
    public String usernameStore, passwordStore;
    public String temp;

    public Multiplayer(Core core) {
        this.core = core;
        usernameStore = passwordStore = temp = "";
    }

    public void checkConnection(String username, String password) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "auth.php?username=" + username + "&password="
                + password);
        request.setTimeOut(2000);
        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void createAccount(String username, String password) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "createaccount.php?username=" + username + "&password="
                + password);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void requestGames(String username, String password) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "games.php?username=" + username + "&password="
                + password);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void infoGames(String username, String password, String ids) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "game.php?username=" + username + "&password="
                + password + "&gameid=" + ids);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void startGame(String username, String password, String id) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "startgame.php?username=" + username + "&password="
                + password + "&gameid=" + id);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void submitGame(String username, String password, String id, String moves) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "submitgame.php?username=" + username + "&password="
                + password + "&gameid=" + id + "&moves=" + moves);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    public void requestNewGame(String username, String password, String challenged, String difficulty) {
        temp = "";

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(api + "requestgame.php?username=" + username + "&password=" + password
                + "&challenged=" + challenged + "&difficulty=" + difficulty);
        request.setTimeOut(2000);

        Gdx.net.sendHttpRequest(request, new ResponseListener());
    }

    class ResponseListener implements Net.HttpResponseListener {

        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            temp = httpResponse.getResultAsString();
        }

        @Override
        public void failed(Throwable t) {
            temp = "false - connection failed";
        }

        @Override
        public void cancelled() {
            temp = "false - cancelled";
        }
    }

}
