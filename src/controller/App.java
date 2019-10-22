public class App {

    public static void main(String args[]) { 
            
        try {
            IPAddress = InetAddress.getLocalHost();
        } 
        catch (UnknownHostException e) {
            System.out.println("Unable to get server IPAddress");
        }
        try {
            serverSocket = new DatagramSocket(8080);
        } catch (SocketException e) {
            System.out.println("Unable to start server socket");
        } 
        
        outBuffer = new ArrayList<DatagramPacket>();
        receiveData = new byte[1024];
        sendData = new byte[1024];
        commandManager = new CommandManager();
        
        Thread send = new Thread(new Runnable() { 
            @Override
            public void run() { 
                try { 
                    
                    while (true) { 
                        synchronized (this) { 
                            
                            if(!outBuffer.isEmpty())
                                send(outBuffer.remove(0));
                            
                            String serverMessage = new String(sendData); 
                            
                            // exit condition 
                            if((serverMessage).toUpperCase().equals("PARAR")) { 
                                System.out.println("O jogo acabou"); 
                                break; 
                            }
                        } 
                    } 
                } 
                catch (IOException e) { 
                    System.out.println("Exception occured" + e.getMessage()); 
                } 
            } 
        }); 

        Thread receive = new Thread(new Runnable() { 
            @Override
            public void run() { 
                try { 
                    while (true) { 
                        synchronized (this) { 
                            
                            System.out.println("Im listening");
                            receive();
                            process(receivePacket);
                            
                            // Exit condition 
                            if(clientMessage.toUpperCase().equals("SAIR")) { 
                                System.out.println("Cliente encerrou a conexão"); 
                                break; 
                            } 
                        } 
                    } 
                } 
                catch (IOException e) {
                    System.out.println("Exception occured"); 
                } 
            } 
        }); 

        send.start(); 
        receive.start();
        
        while(true) {
            send.run();
            receive.run();
        }
       
    } 

    private static void receive() throws IOException {
        receiveData = new byte[1024];
        receivePacket = new DatagramPacket(receiveData, receiveData.length); 
        serverSocket.receive(receivePacket);
        clientMessage = (new String(receiveData)).trim();
        System.out.println("Recebi do cliente " + receivePacket.getAddress() + ": " + clientMessage);
                
    }
        
    private static void process(DatagramPacket receivePacket) {
        
        ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();
        //try {
            packets.addAll(CommandManager.process(receivePacket));
        //}
        /*catch (NullPointerException e){
            try {
                errorPacket(receivePacket, "ERROR: Could not retrieve packets");
            } 
            
            catch (IOException e1) {
                System.out.println("Unable to send packet to client");
            }

        }
    */
        outBuffer = packets;
    }

    private static void send(DatagramPacket sendPacket) throws IOException {
        sendData = sendPacket.getData();
        serverSocket.send(sendPacket);
        String aux = new String(sendData).trim();
        System.out.println("Enviei ao cliente " + aux);
    }

    private static void errorPacket(DatagramPacket receivePacket, String errorMessage) throws IOException {
        
        sendData = new byte[1024];
        sendData = errorMessage.getBytes();
        InetAddress clientIPAddress = receivePacket.getAddress();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 7070);		
        send(sendPacket);
        
    }

}