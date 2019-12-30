package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.POSupplierContactInputBean;
import com.tcmis.internal.supply.beans.VvSupplierContactTypeBean;
import com.tcmis.internal.supply.factory.SupplierContactBeanFactory;

/******************************************************************************
 * Process used by POSupplierContactAction
 * @version 1.0
 *****************************************************************************/

public class POSupplierContactProcess  extends GenericProcess
{

	Log log = LogFactory.getLog(this.getClass());

	public POSupplierContactProcess(String client)
	{
		super(client);
	}

	public Collection getSupplierContactBeanCollection(POSupplierContactInputBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		SupplierContactBeanFactory factory = new SupplierContactBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		if (!StringHandler.isBlankString(inputBean.getSupplier()) )
		{
			searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBean.getSupplier());
		}
		if (null!=inputBean.getContactId() )
		{
			searchCriteria.addCriterion("contactId", SearchCriterion.EQUALS, inputBean.getContactId().toString());
		}
		searchCriteria.addCriterion("status", SearchCriterion.EQUALS, "Active",SearchCriterion.IGNORE_CASE);

		searchCriteria.addCriterion("contactType", SearchCriterion.NOT_EQUAL, "In Active",SearchCriterion.IGNORE_CASE);
        if("Y".equalsIgnoreCase(inputBean.getFromSupplierPriceList()))
        {
        	searchCriteria.addCriterion("contactType", SearchCriterion.IN, "'Wbuy','newWbuy'");
        }
		SortCriteria scriteria = new SortCriteria();
		scriteria.setSortAscending(true);
		scriteria.addCriterion("nickname");
		Collection c = factory.select(searchCriteria,scriteria );

		return c;
	}


	public Object[] addModifySupplierContact(	POSupplierContactInputBean inputBean,	PersonnelBean personnelBean)
	throws BaseException, Exception {
		Object[] obj = new Object[2];
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String errorCode = "";
		/*      a_supplier          IN       supplier_contact.supplier%TYPE,
			      a_contact_id        IN       supplier_contact.contact_id%TYPE,
			      a_contact_type      IN       supplier_contact.contact_type%TYPE,
			      a_first_name        IN       supplier_contact.first_name%TYPE,
			      a_last_name         IN       supplier_contact.last_name%TYPE,
			      a_nickname          IN       supplier_contact.nickname%TYPE,
			      a_phone             IN       supplier_contact.phone%TYPE,
			      a_phone_extension   IN       supplier_contact.phone_extension%TYPE,
			      a_fax               IN       supplier_contact.fax%TYPE,
			      a_email             IN       supplier_contact.email%TYPE,
			      a_error_message1    OUT      VARCHAR2
		 */

		Collection inArgs =
			buildProcedureInput(
					inputBean.getSupplier(),
					inputBean.getContactId(),
					inputBean.getContactType(),
					inputBean.getFirstName(),
					inputBean.getLastName(),
					inputBean.getNickname(),
					inputBean.getPhone(),
					inputBean.getPhoneExtension(),
					inputBean.getFax(),
					inputBean.getEmail()
			);


		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		outArgs.add( new Integer(java.sql.Types.NUMERIC) );

		if(log.isDebugEnabled()) {
			log.debug("inArgs for pkg_contract_setup.modify_supplier_contact:"+inArgs);
		}
		//unblock this for real run
		Vector results = (Vector) factory.doProcedure("pkg_contract_setup.modify_supplier_contact", inArgs, outArgs);
		//Vector error = new Vector();

		if(results.size()>0 && results.get(0) != null)
		{
			obj[0] = results.get(0).toString();
		}

		obj[1] = results.get(1);

		return obj;
	}



	public Collection<VvSupplierContactTypeBean> getSupplierContractTypeList(POSupplierContactInputBean inputBean) throws	BaseException, Exception
	{

		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvSupplierContactTypeBean());
        SearchCriteria searchCriteria = new SearchCriteria();
        if("Y".equalsIgnoreCase(inputBean.getFromSupplierPriceList()))
        {
            searchCriteria.addCriterion("contactType", SearchCriterion.EQUALS, "newWbuy",SearchCriterion.IGNORE_CASE);
        }
        else
        {
            searchCriteria.addCriterion("contactType", SearchCriterion.NOT_EQUAL, "Wbuy",SearchCriterion.IGNORE_CASE);
        }
        Collection <VvSupplierContactTypeBean> c =  factory.select(searchCriteria, null, SUPPLIER_CONTACT_TYPE_VIEW);

		return c;
	}

	private static final String SUPPLIER_CONTACT_TYPE_VIEW = "vv_supplier_contact_type";
}




