package com.fillefilip8.twitchapi;

import java.util.Scanner;

public class Testing {
public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	String cityName = scanner.next();
	System.out.println(cityName.toUpperCase()); //Print the city to all CAPS
	System.out.println(cityName.toLowerCase()); //Print the city in all lowercase
	System.out.println(cityName.substring(0, 1)); //Print the first character of the city
}
}
