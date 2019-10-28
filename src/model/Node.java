
public class Node{
    private String ID;
    private Node next;
    private UDPSocket in;
    private UDPSocket out;
    private PackManager packMan;
    private TokenManager tokenMan;
    private String config;
    private Token token;

    public Node(String ID){
        this.ID = ID;
        config = "config.txt";
    }

}