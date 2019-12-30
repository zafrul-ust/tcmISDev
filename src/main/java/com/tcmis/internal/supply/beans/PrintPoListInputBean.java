package com.tcmis.internal.supply.beans;


import com.tcmis.common.framework.HubBaseInputBean;


/******************************************************************************
 * CLASSNAME: PrintPoListInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class PrintPoListInputBean extends HubBaseInputBean {

        private String searchType;
	private String searchText;
        private String submitSearch;

	//constructor
	public PrintPoListInputBean() {
	}

	//setters
        public void setSearchType(String searchType) {
          this.searchType = searchType;
        }

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

        public void setSubmitSearch(String submitSearch) {
                this.submitSearch = submitSearch;
        }

	//getters
        public String getSearchType() {
          return searchType;
        }
	public String getSearchText() {
		return searchText;
	}
        public String getSubmitSearch() {
                return submitSearch;
        }

		@Override
		public void setHiddenFormFields() {
			// TODO Auto-generated method stub
			
		}
}