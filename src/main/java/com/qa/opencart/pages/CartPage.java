package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {

	@SuppressWarnings("unused")
	private By cartPage = By.id("title");

	public static void main(String[] args) {
		System.out.println("Cart Page");

		String s1 = "feature one";
		System.out.println(s1);
		
		String s2 = "feature one";
		System.out.println(s2);

	}

	public static void cartPage() {
		System.out.println("Cart Page");
	}
}
