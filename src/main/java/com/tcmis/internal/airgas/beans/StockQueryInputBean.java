package com.tcmis.internal.airgas.beans;

/******************************************************************************
 * CLASSNAME: PolchemNsnVerificationBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/

public class StockQueryInputBean {
	private String inventoryLocation;
	private String region;
	private String ticketPrinter;
	private String partNum;
	private String account;
	private String haaspo;
	public String getInventoryLocation() {
		return inventoryLocation;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public String getPartNum() {
		return partNum;
	}
	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTicketPrinter() {
		return ticketPrinter;
	}
	public void setTicketPrinter(String ticketPrinter) {
		this.ticketPrinter = ticketPrinter;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getHaaspo() {
		return haaspo;
	}
	public void setHaaspo(String haaspo) {
		this.haaspo = haaspo;
	}
}