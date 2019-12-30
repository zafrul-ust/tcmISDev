package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;


/**
* Change History --------- 04/20/09 - Shahzad Butt - Added new input variables.
* 
* 
*/
public class ResendXBuyInputBean extends BaseDataBean 
{
   
	private static final long serialVersionUID = -6048282702632099261L;
	
	
    private String searchArgument;
    private String action;
    
    
    public ResendXBuyInputBean()
	{
	}

 

    public String getSearchArgument() {
        return searchArgument;
    }

    public void setSearchArgument(String searchArgument) {
        this.searchArgument = searchArgument;
    }



	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}



	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
   



	
}