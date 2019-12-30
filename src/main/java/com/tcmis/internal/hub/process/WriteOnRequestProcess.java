package com.tcmis.internal.hub.process;

//import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.WriteOnRequestInputBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class WriteOnRequestProcess  extends GenericProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	public WriteOnRequestProcess(String client) 
	{
		super(client);
	}  

	public WriteOnRequestProcess(String client,String locale) {
		super(client,locale);
	}

	

	public Collection createRequest(WriteOnRequestInputBean bean, PersonnelBean personnelBean) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;		
		Vector errorMessages = new Vector();


		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if(bean!=null )
		{

			try {
				if( null!=bean.getReceiptId() )
				{
					/*The procedure expects a -ve value for write-on and a positive value for write-off*/
                    bean.setQuantity(bean.getQuantity().multiply(new BigDecimal(("-1"))));
					inArgs = buildProcedureInput(
					bean.getReceiptId(),
					new Date(),
					personnelBean.getPersonnelId(),
					bean.getQuantity(),
					bean.getRequestorComment());					
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));					
					if(log.isDebugEnabled()) {
						log.debug("Input Args for PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request  :" + inArgs);
					}			   
					Vector error = (Vector)factory.doProcedure("PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request", inArgs, outArgs);
					if( error.size()>0 && error.get(0) != null && !"ok".equalsIgnoreCase((String)error.get(0)))
					{
						errorCode = (String) error.get(0);
						log.info("Error in Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: "+bean.getReceiptId()+" Error Code "+errorCode+" ");
						errorMessages.add(errorCode);

					}			     

				}			
			} catch (Exception e) {
				errorMsg = "Error Adding New Request for Id: "+ bean.getReceiptId();
				errorMessages.add(errorMsg);
			}


		}  


		return errorMessages;

	}

		
}




