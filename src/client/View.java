package client;

import java.util.Scanner;

public class View {
	private static Scanner scan;
	
	public static void title() {
		System.out.println("####################");
		System.out.println("#                  #");
		System.out.println("#   NOME DO JOGO   #");
		System.out.println("#                  #");
		System.out.println("####################\n");
	}
	
	public void welcome() {
		title();
		System.out.println("Bem-vindo(a) ao NOME DO JOGO!\n");
	}
	
	public void goal() {
		System.out.println("Seu objetivo neste jogo é OBJETIVO DO JOGO...\n");
	}
	
	public String login() {
		scan = new Scanner(System.in);
		
		System.out.println("Para começar, insira seu nome de usuário.");
		System.out.print("Usuário: ");
		
		return scan.next();		
	}

}