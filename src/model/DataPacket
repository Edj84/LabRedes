package model;

public class DataPacket {

    private String dataPacket;
    private String errorControl;
    private String sourceNick;
    private String receiverNick;
    private int crc;
    private String message;


    public DataPacket (String dataPacket,String errorControl,String sourceNick,String receiverNick,int crc,String message) {
        this.dataPacket = dataPacket;
        this.errorControl = errorControl;
        this.sourceNick = sourceNick;
        this.receiverNick = receiverNick;
        this.crc = crc;
        this.message = message;
        
    }

    public String getDataPacket() {
        return this.dataPacket;
    }
    public String getErrorControl() {
        return this.errorControl;
    }
    public void setErrorControl(String errorControl){
        this.errorControl = errorControl;
    }
    public String getSourceNick() {
        return this.sourceNick;
    }
    public void setSourceNick(String sourceNick){
        this.sourceNick = sourceNick;
    }
    public String getReceiverNick() {
        return this.receiverNick;
    }
    public void setReceiverNick(String receiverNick){
        this.receiverNick = receiverNick;
    }
    public int getCrc() {
        return this.crc;
    }
    public void setCrc (String crc){
        this.crc = crc;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String toString () {
        return getDataPacket +";" + getErrorControl + ":" + getSourceNick + ":" + getReceiverNick + ":" + getCrc + ":" + getMessage;
    }

    
}