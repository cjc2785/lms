package com.ss.lms.view;

import java.util.Scanner;

public interface View {
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static int nextInt() {
		int num = scanner.nextInt();
		scanner.nextLine();
		return num;
	}
	
	public static String nextLine() {
		String next = scanner.nextLine();
		return next;
	}
	
	public void show();
}
