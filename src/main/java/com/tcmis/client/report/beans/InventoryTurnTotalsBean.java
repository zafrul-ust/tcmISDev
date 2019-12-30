package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class InventoryTurnTotalsBean extends BaseDataBean {
	
	 private BigDecimal stockingLevel = new BigDecimal("0");
	 private BigDecimal lastUsedQty = new BigDecimal("0");
	 private BigDecimal usedMonth1 = new BigDecimal("0");
	 private BigDecimal usedMonth2 = new BigDecimal("0");
	 private BigDecimal usedMonth3 = new BigDecimal("0");
	 private BigDecimal usedMonth4 = new BigDecimal("0");
	 private BigDecimal usedMonth5 = new BigDecimal("0");
	 private BigDecimal usedMonth6 = new BigDecimal("0");
	 private BigDecimal usedHalfYear = new BigDecimal("0");
	 private BigDecimal avgUsePerWeek = new BigDecimal("0");
	 private BigDecimal turnsLastYear = new BigDecimal("0");
	 
	 private String application;
	 private String applicationDesc;
	 private BigDecimal records = new BigDecimal("0");
	 
	 public InventoryTurnTotalsBean() {
	 }

	 

  public BigDecimal getStockingLevel() {
	return stockingLevel;
  }
  
  public void setStockingLevel(BigDecimal stockingLevel) {
		if (stockingLevel != null) {
		 this.stockingLevel = this.stockingLevel.add(stockingLevel);
		}
	 }
  public BigDecimal getLastUsedQty() {
		return lastUsedQty;
	  }
	  
  public void setLastUsedQty(BigDecimal lastUsedQty) {
		if (lastUsedQty != null) {
		 this.lastUsedQty = this.lastUsedQty.add(lastUsedQty);
		}
	 }
  public BigDecimal getUsedMonth1() {
		return usedMonth1;
	  }
	  
  public void setUsedMonth1(BigDecimal usedMonth1) {
		if (usedMonth1 != null) {
		 this.usedMonth1 = this.usedMonth1.add(usedMonth1);
		}
	 }
  public BigDecimal getUsedMonth2() {
		return usedMonth2;
	  }
			  
  public void setUsedMonth2(BigDecimal usedMonth2) {
		if (usedMonth2 != null) {
		 this.usedMonth2 = this.usedMonth2.add(usedMonth2);
		}
	 }
  
  public BigDecimal getUsedMonth3() {
		return usedMonth3;
	  }
	  
  public void setUsedMonth3(BigDecimal usedMonth3) {
		if (usedMonth3 != null) {
		 this.usedMonth3 = this.usedMonth3.add(usedMonth3);
		}
	 }
  public BigDecimal getUsedMonth4() {
		return usedMonth4;
	  }
	  
  public void setUsedMonth4(BigDecimal usedMonth4) {
		if (usedMonth4 != null) {
		 this.usedMonth4 = this.usedMonth4.add(usedMonth4);
		}
	 }
  public BigDecimal getUsedMonth5() {
		return usedMonth5;
	  }
	  
  public void setUsedMonth5(BigDecimal usedMonth5) {
		if (usedMonth5 != null) {
		 this.usedMonth5 = this.usedMonth5.add(usedMonth5);
		}
	 }
  public BigDecimal getUsedMonth6() {
		return usedMonth6;
	  }
			  
  public void setUsedMonth6(BigDecimal usedMonth6) {
		if (usedMonth6 != null) {
		 this.usedMonth6 = this.usedMonth6.add(usedMonth6);
		}
	 }
  
  public BigDecimal getUsedHalfYear() {
		return usedHalfYear;
	  }
  
  public void setUsedHalfYear(BigDecimal usedHalfYear) {
		if (usedHalfYear != null) {
		 this.usedHalfYear = this.usedHalfYear.add(usedHalfYear);
		}
	 }
  
  public BigDecimal getAvgUsePerWeek() {
		return avgUsePerWeek;
	  }

  public void setAvgUsePerWeek(BigDecimal avgUsePerWeek) {
		if (avgUsePerWeek != null) {
		 this.avgUsePerWeek = this.avgUsePerWeek.add(avgUsePerWeek);
		}
	 }

 public BigDecimal getTurnsLastYear() {
	return turnsLastYear;
  }

 public void setTurnsLastYear(BigDecimal turnsLastYear) {
	if (turnsLastYear != null) {
	 this.turnsLastYear = this.turnsLastYear.add(turnsLastYear);
	}
 }
  
  
	 public void setApplication(String application) {
			this.application = application;
		 }
	 
	 public String getApplication() {
			return application;
		  }

	public String getApplicationDesc() {
		return applicationDesc;
	}
	
	
	
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public BigDecimal getRecords() {
		return records;
	  }
	  
	  public void setRecords(BigDecimal records) {
			if (records != null) {
			 this.records = this.records.add(records);
			}
		 }

	
 
}
