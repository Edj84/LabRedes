package model;

public class Token {

    private String token;
    private long timeOfCreation;


    public Token(String token, long timeOfCreation) {
        this.token = token;
        this.timeOfCreation = System.currentTimeMillis();
    }

    public void getToken() {
        return this.token;
    }
    public String setToken(String token) {
        return this.token = token;
    }
    public long getTimeOfCreation() {
        return this.timeOfCreation;
    }
    public long renewTimeOfCreation(long timeOfCreation) {
        return this.timeOfCreation = timeOfCreation;
    }

    public String toString () {
        return getToken;
    }
}