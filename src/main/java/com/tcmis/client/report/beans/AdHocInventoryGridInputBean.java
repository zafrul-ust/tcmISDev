package com.tcmis.client.report.beans;
import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class AdHocInventoryGridInputBean  extends BaseDataBean {

	private String listName;
	private String listId;
	private String constraint;
	private String operator;
	private String value;


		//constructor
		public AdHocInventoryGridInputBean() {
		}
		
		public void setListName(String listName) {
			this.listName = listName;
		}

		public void setListId(String listId) {
			this.listId = listId;
		}

		public void setConstraint(String constraint) {
			this.constraint = constraint;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public void setValue(String value) {
			this.value = value;
		}

		//getters
		
		public String getConstraint() {
			return constraint;
		}
		
		public String getOperator() {
			return operator;
		}
		
		public String getValue() {
			return value;
		}
		
		public String getListName() {
			return listName;
		}
		
		public String getListId() {
			return listId;
		}

	}
