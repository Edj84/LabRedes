package server;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;

public class GerenciadorDeLances {
	private HashMap<Integer, Integer> propostas;
	private int[] lanceMaisAlto;
	
	public GerenciadorDeLances() {
		propostas = new HashMap<Integer, Integer>();
		lanceMaisAlto = new int[2]; 
	}
	
	public boolean receberLance(DatagramPacket proposta) {
		
		byte[] dados = proposta.getData();
		String aux = new String(dados);
		
		System.out.println("Recebido " + aux);
				
		return true;		
	}
	
}
