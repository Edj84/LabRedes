package controller;

public final class TokenManager {
    private Token token;

    public TokenManager() {
    }
    
    public boolean checkDoubleToken(){
        if(System.currentTimeMillis() - this.token.getTimeOfCreation() < 3000){
            return true;
        }
        this.token.renewTimeOfCreation();
        return false;
    }

    public boolean tokenTimeout(){
        if(System.currentTimeMillis() - this.token.getTimeOfCreation()> 12000){
            return true;
        }
        return false;
    }

}