package com.tcmis.client.het.beans;

import java.math.BigDecimal;


public class HetCartInputBean extends HetCartViewBean {
	private boolean active;
	private boolean cartItemDeleted;
	private boolean newCart;
	private boolean newCartItem;
	private String oldName;
	private BigDecimal oldSortOrder;
	private String oldStatus;

	public HetCartInputBean() {
	}

	public String getOldName() {
		return oldName;
	}

	public BigDecimal getOldSortOrder() {
		return oldSortOrder;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public boolean isCartItemDeleted() {
		return cartItemDeleted && !newCartItem;
	}

	public boolean isCartUpdated() {
		return !oldStatus.equals(getCartStatus()) || !oldName.equals(getCartName());
	}

	public boolean isNewCart() {
		return newCart;
	}

	public boolean isNewCartItem() {
		return newCartItem && !cartItemDeleted;
	}

	public boolean isSortOrderChanged() {
		if (getSortOrder() != null) {
			return oldSortOrder == null ? true : getSortOrder().compareTo(oldSortOrder) != 0;
		}
		return oldSortOrder != null;
	}

	public void setActive(boolean active) {
		this.active = active;
		setCartStatus(active ? "A" : "I");
	}

	public void setCartItemDeleted(boolean recipeItemDeleted) {
		this.cartItemDeleted = recipeItemDeleted;
	}

	public void setNewCart(boolean newRecipe) {
		this.newCart = newRecipe;
	}

	public void setNewCartItem(boolean newRecipeItem) {
		this.newCartItem = newRecipeItem;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public void setOldSortOrder(BigDecimal oldSortOrder) {
		this.oldSortOrder = oldSortOrder;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

}
