package com.tcmis.internal.distribution.process;

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
import com.tcmis.internal.distribution.beans.SpecsToReceiveViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class ReceiptSpecProcess extends GenericProcess  {
	Log log = LogFactory.getLog(this.getClass());

	public ReceiptSpecProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection<SpecsToReceiveViewBean> getSearchResult(SpecsToReceiveViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SpecsToReceiveViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();

		if(null!=inputBean.getReceiptId())
			searchCriteria.addCriterion("receiptId", SearchCriterion.EQUALS, inputBean.getReceiptId().toString());


		Collection<SpecsToReceiveViewBean>  c = factory.select(searchCriteria,null,"specs_to_receive_view");

		return c;
	}



	public String updateReceipt(SpecsToReceiveViewBean  specBean, PersonnelBean personnelBean)  throws BaseException {

		/*		a_receipt_id receipt_spec.receipt_id%type,
		a_spec_name receipt_spec.spec_name%type,
		a_spec_library receipt_spec.spec_library%type,
		a_detail receipt_spec.detail%type,
		a_coc receipt_spec.coc%type,
		a_coa receipt_spec.coa%type,
		a_spec_version receipt_spec.spec_version%type,
		a_spec_amendment receipt_spec.spec_amendment%type,
		a_cert_reference receipt_spec.cert_reference%type,
		a_error out varchar2,
		a_commit char default 'Y
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Vector error  =null;
		String errorCode = "";

		Collection inArgs =
			buildProcedureInput(
					specBean.getReceiptId(),
					specBean.getSpecName(),
					specBean.getSpecLibrary(),
					specBean.getDetail(),
					"true".equalsIgnoreCase(specBean.getCoc())?"Y":"N",
							"true".equalsIgnoreCase(specBean.getCoa())?"Y":"N",
									specBean.getSpecVersion(),
									specBean.getSpecAmendment(),
									specBean.getCertReference()  );


		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		if (log.isDebugEnabled()) {
			log.debug("pkg_spec_utility.P_RECEIPT_SPEC_UPDATE inArgs:"+inArgs);
		}
		//unblock this for real run
		error = (Vector) factory.doProcedure("pkg_spec_utility.P_RECEIPT_SPEC_UPDATE", inArgs, outArgs);
		//Vector error = new Vector();
		errorCode = "";
		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}


		return errorCode;


	}



	public Object[] insertIntoReceipt(SpecsToReceiveViewBean  specBean, PersonnelBean personnelBean)  throws BaseException {

		/*
		a_receipt_id receipt_spec.receipt_id%type,
		a_spec_name receipt_spec.spec_name%type,
		a_spec_library receipt_spec.spec_library%type,
		a_detail receipt_spec.detail%type,
		a_coc receipt_spec.coc%type,
		a_coa receipt_spec.coa%type,
		a_spec_version receipt_spec.spec_version%type,
		a_spec_amendment receipt_spec.spec_amendment%type,
		a_cert_reference receipt_spec.cert_reference%type,
		a_error out varchar2,
		a_commit char default 'Y'
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Vector error  =null;
		String errorCode = "";
		BigDecimal specId = null;
		Collection inArgs =
			buildProcedureInput(
					specBean.getReceiptId(),
					specBean.getSpecName(),
					specBean.getSpecLibrary(),
					specBean.getDetail(),
					"true".equalsIgnoreCase(specBean.getCoc())?"Y":"N",
							"true".equalsIgnoreCase(specBean.getCoa())?"Y":"N",
									specBean.getSpecVersion(),
									specBean.getSpecAmendment(),
									specBean.getCertReference()  );


		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		if (log.isDebugEnabled()) {
			log.debug("pkg_spec_utility.P_RECEIPT_SPEC_INSERT inArgs:"+inArgs);
		}
		//unblock this for real run
		error = (Vector) factory.doProcedure("pkg_spec_utility.P_RECEIPT_SPEC_INSERT", inArgs, outArgs);
		//Vector error = new Vector();
		errorCode = "";
		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}

		//specId = (BigDecimal) error.get(0);
		Object[] objs = {errorCode,specId};
		return objs;
	}

	public String deleteReceipt(SpecsToReceiveViewBean  specBean, PersonnelBean personnelBean)  throws BaseException {

		/*
		a_receipt_id receipt_spec.receipt_id%type,
		a_spec_name receipt_spec.spec_name%type,
		a_spec_library receipt_spec.spec_library%type,
		a_detail receipt_spec.detail%type,
		a_spec_version receipt_spec.spec_version%type,
		a_spec_amendment receipt_spec.spec_amendment%type,
		a_error out varchar2,
		a_commit char default 'Y'
		 */
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Vector error  =null;
		String errorCode = "";

		Collection inArgs =
			buildProcedureInput(
					specBean.getReceiptId(),
					specBean.getSpecName(),
					specBean.getSpecLibrary(),
					specBean.getDetail(),
					specBean.getSpecVersion(),
					specBean.getSpecAmendment()						  );


		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		if (log.isDebugEnabled()) {
			log.debug("pkg_spec_utility.P_RECEIPT_SPEC_DELETE inArgs:"+inArgs);
		}
		//unblock this for real run
		error = (Vector) factory.doProcedure("pkg_spec_utility.P_RECEIPT_SPEC_DELETE", inArgs, outArgs);
		//Vector error = new Vector();
		errorCode = "";
		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}


		return errorCode;
	}

	public void callReceiptAllocate(SpecsToReceiveViewBean  specBean)  throws BaseException {
		Collection inArgs = buildProcedureInput(specBean.getReceiptId());

		if (log.isDebugEnabled()) {
			log.debug("p_receipt_allocate:"+inArgs);
		}
		//unblock this for real run
		factory.doProcedure("p_receipt_allocate", inArgs);
	}

}