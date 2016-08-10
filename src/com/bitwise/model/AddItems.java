package com.bitwise.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddItems {
	// This is a bean class...
	private int id;
	private String items;
	private int quantity;
	private String color;
	private float price;
	Map<Integer, Integer> cartItems = new HashMap<Integer, Integer>();

	public AddItems(int id, String items, int itemQuantity, float price, String color) {
		this.setId(id);
		this.setItems(items);
		this.setQuantity(itemQuantity);
		this.setPrice(price);
		this.setColor(color);

	}

	public AddItems() {
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setCartItems(int id, int quantity) {
		cartItems.put(id, quantity);
	}

	public Map<Integer, Integer> getCartItems() {
		return cartItems;
	}

	public void removeItem(int id) {
		cartItems.remove(id);
	}

	public void setSessionCart(HttpServletRequest request) {
		request.getSession(false).setAttribute("cart", cartItems);

	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void removeItem(String key) {
		cartItems.remove(key);

	}

}
