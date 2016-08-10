package com.bitwise.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bitwise.model.AddItems;

@Controller
public class CartController {
	private AddItems addItems = new AddItems();
	private ArrayList<AddItems> arrItems = new ArrayList<AddItems>();
	private ArrayList<AddItems> comboBoxItems = new ArrayList<AddItems>();
	private boolean firstRequest = false;

	@RequestMapping(value = "/AddCart", method = RequestMethod.GET)
	public String populateForm(ModelMap model, HttpServletRequest request) {
		// arrItems.clear();
		model.addAttribute("cart", new AddItems());
		if (firstRequest == false) {
			comboBoxItems = populateComboBox();
			System.out.println("first request:false");
		}
		model.put("itemsList", comboBoxItems);

		return "AddItemsToCart";
	}

	public ArrayList<AddItems> populateComboBox() {
		// items.add(new AddItems(-1, "Select"));
		arrItems.add(new AddItems(1, "Pen Drive", 5, 100, "red"));
		arrItems.add(new AddItems(2, "CD-ROM", 8, 1000, "yellow"));
		arrItems.add(new AddItems(3, "Mobile", 3, 102.5f, "orange"));
		return arrItems;
	}

	@RequestMapping(value = "/AddItems", method = RequestMethod.POST)
	public String addItemsToCart(@ModelAttribute("carts") AddItems items, ModelMap model,
			final RedirectAttributes redirectAttributes, HttpSession session) {

		boolean flag = false;
		firstRequest = true;
		// logic for checking out of stock and duplication goes here.
		for (AddItems item : arrItems) {
			if (item.getId() == items.getId()) {
				if (items.getQuantity() > item.getQuantity()) {
					flag = false;
					redirectAttributes.addFlashAttribute("message", "Item Qty. entered is out of stock.");
				} else {
					flag = true;
					redirectAttributes.addFlashAttribute("message", "Item Added.");
					item.setQuantity(item.getQuantity() - items.getQuantity());
				}
				break;
			}
		}
		// to increase quantity of duplicate items added.
		if (flag == true) {
			Map<Integer, Integer> cartItems = addItems.getCartItems();
			// if items in cart already present.
			if (!cartItems.isEmpty()) {
				Iterator<Entry<Integer, Integer>> it = cartItems.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Integer> pair = it.next();
					// if duplicate item present.
					if (items.getId() == (Integer) pair.getKey()) {
						flag = true;
						int quantity = (Integer) pair.getValue();
						quantity += items.getQuantity();
						addItems.removeItem(pair.getKey().toString());
						addItems.setCartItems(items.getId(), quantity);
						break;
					} else {
						flag = false;
					}
				} // if duplicate item not present.
				if (flag == false) {
					addItems.setCartItems(items.getId(), items.getQuantity());
				}
			} else
			// if item cart is empty.
			{
				addItems.setCartItems(items.getId(), items.getQuantity());
			}
		}

		return "redirect:AddCart";

	}

	@RequestMapping(value = "/ViewCart", method = RequestMethod.GET)
	public String viewCart(ModelMap model, final RedirectAttributes redirectAttributes, HttpSession session,
			HttpServletRequest request) {
		Map<Integer, Integer> cartItems = addItems.getCartItems();
		addItems.setSessionCart(request);
		model.put("itemList", cartItems);
		model.put("arrList", arrItems);
		return "ViewCart";
	}

	@RequestMapping(value = "/DeleteItems", method = RequestMethod.GET)
	public String deleteFromCart(ModelMap model, HttpServletRequest request) {
		ArrayList<AddItems> arr = new ArrayList<AddItems>();
		arr.add(new AddItems(1, "Pen Drive", 5, 100, "red"));
		arr.add(new AddItems(2, "CD-ROM", 8, 1000, "yellow"));
		arr.add(new AddItems(3, "Mobile", 3, 102.5f, "orange"));
		model.addAttribute("carts", new AddItems());
		model.put("itemsList", arr);
		// System.out.println("hello");
		// addItems.removeItem(id);
		return "DeleteItems";
	}

	@RequestMapping(value = "/DeleteCart", method = RequestMethod.POST)
	public String deleteItems(@ModelAttribute("carts") AddItems items, ModelMap model, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		int remainingQuantity = 0;
		boolean isItemPresent = false;
		boolean isRemQtyAppropriate = false;
		Map<Integer, Integer> cart = new HashMap<Integer, Integer>();
		cart = addItems.getCartItems();
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			if (items.getId() == entry.getKey()) {
				isItemPresent = true;
				remainingQuantity = entry.getValue() - items.getQuantity();
				if (remainingQuantity < 0)
					isRemQtyAppropriate = false;
				else if (remainingQuantity >= 0) {
					isRemQtyAppropriate = true;
				}
				break;
			} else {
				isItemPresent = false;
			}
		}
		if (isItemPresent == true) {
			if (isRemQtyAppropriate == true) {
				if (remainingQuantity == 0) {
					addItems.removeItem(items.getId());
					redirectAttributes.addFlashAttribute("message", "Item Deleted.");

				} else {
					addItems.removeItem(items.getId());
					addItems.setCartItems(items.getId(), remainingQuantity);
					redirectAttributes.addFlashAttribute("message", "Item Deleted.");
				}
			} else {
				redirectAttributes.addFlashAttribute("message",
						"Item Qty. to be deleted exceeds cart Qty.Please try again.");
			}
		} else {
			redirectAttributes.addFlashAttribute("message",
					"Item to be deleted is not present in the cart.Please try deleting another one.");
		}
		return "redirect:ViewCart";
	}

}
