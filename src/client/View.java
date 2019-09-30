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
	
	public static void welcome() {
		title();
		System.out.println("Bem-vindo(a) ao NOME DO JOGO!\n");
	}
	
	public static void goal() {
		System.out.println("Seu objetivo neste jogo é OBJETIVO DO JOGO...\n");
	}
	
	public static String login() {
		scan = new Scanner(System.in);
		
		System.out.println("Para começar, insira seu nome de usuário.");
		System.out.print("Usuário: ");
		
		String login = scan.nextLine();
		return "LOGIN " + login.toUpperCase().trim();		
	}
	
	public static String prompt() {
		scan = new Scanner(System.in);
		
		System.out.println("O que você quer fazer?");
		String text = scan.nextLine();
		
		return text.toUpperCase();		
	}
	
	public void show(String content) {
		System.out.println(content);
	}
	
}