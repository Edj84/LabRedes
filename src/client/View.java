package client;

import java.util.Scanner;

public class View {
	
	public static void title() {
		System.out.println("####################");
		System.out.println("#                  #");
		System.out.println("#   NOME DO JOGO   #");
		System.out.println("#                  #");
		System.out.println("####################\n");
	}
	
	public static void welcome() {
		System.out.println("Bem-vindo(a) ao NOME DO JOGO!\n");
	}
	
	public static void goal() {
		System.out.println("Seu objetivo neste jogo é OBJETIVO DO JOGO...\n");
	}
	
	public static void login() {
		Scanner sc = new Scanner(System.in);
		String user = "";
		
		System.out.println("Para começar, insira seu nome de usuário.");
		System.out.print("Usuário: ");
		
		user = sc.next();
		
		System.out.println("\n\nSeu nome é... " + user);
	}
	
	public static void displayMessage(String message) {
		
	}

}
