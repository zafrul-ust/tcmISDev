package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.NewShipToAddressInputBean;

/******************************************************************************
 * Process for buy orders
 * @version 1.0
 *****************************************************************************/
public class NewShipToAddressProcess
extends GenericProcess {

	public NewShipToAddressProcess(TcmISBaseAction act) throws BaseException {
		super(act);
	}


	public Object[] callCreateShipTo(NewShipToAddressInputBean bean,PersonnelBean personnelBean)  throws BaseException {

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/*      a_customer_id                 IN       NUMBER,
      a_company_id                  IN       customer.LOCATION.company_id%TYPE,
      a_country_abbrev              IN       customer.LOCATION.country_abbrev%TYPE,
      a_state_abbrev                IN       customer.LOCATION.state_abbrev%TYPE,
      a_address_line1               IN       customer.LOCATION.address_line_1%TYPE,
      a_address_line2               IN       customer.LOCATION.address_line_2%TYPE,
      a_address_line3               IN       customer.LOCATION.address_line_3%TYPE,
      a_city                        IN       customer.LOCATION.city%TYPE,
      a_zip                         IN       customer.LOCATION.zip%TYPE,
      a_zip_plus                    IN       VARCHAR2,
      a_personnel_id                IN       GLOBAL.personnel.personnel_id%TYPE,
      a_ops_entity_id               IN       operating_entity.ops_entity_id%TYPE,
		 */
		// makre sure to remove the hard coded company_id later.
		Collection inArgs =
			buildProcedureInput(
					bean.getCustomerId(),
					bean.getBillToCompanyId(),
					bean.getRemitToCountryAbbrev(),
					bean.getRemitToStateAbbrev(),
					bean.getRemitToAddressLine1(),
					bean.getRemitToAddressLine2(),
					bean.getRemitToAddressLine3(),
					bean.getRemitToAddressLine4(),
					bean.getRemitToAddressLine5(),
					bean.getRemitToCity(),
					bean.getRemitToZip(),
					bean.getRemitToZipPlus(),
					bean.getCustomerName(),
					personnelBean.getPersonnelId(),
					bean.getOpsEntityId(),
					bean.getInventoryGroup(),
					bean.getSalesAgentId(),
					bean.getFieldSalesRepId(),
					bean.getPriceGroupId(),
					bean.getShiptoNotes(),
					bean.getMsdsLocaleOverride());

		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		outArgs.add( new Integer(java.sql.Types.NUMERIC) );
		if (log.isDebugEnabled()) {
			log.debug("inArgs:"+inArgs);
		}
		//unblock this for real run
		Vector error = (Vector) factory.doProcedure("pkg_customer_setup.add_customer_ship_to", inArgs, outArgs);
		//Vector error = new Vector();
		String errorCode = "";
		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}

		Object[] objs = {errorCode,error.get(1)};
		return objs;
	}

	/*  private void sendNotificationMail(NewShipToAddressInputBean bean,PersonnelBean personnelBean) {
    try {
      DbManager dbManager = new DbManager(this.getClient(),getLocale());
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      Collection c = null;
      String subject = null;
      String message = null;

        c = factory.selectDistinctUserByFacilityPermission("newShipTo");
        subject = "New Ship To Address for " + bean.getCustomerName() + ", Requested by " + personnelBean.getFullName() + " - Waiting Approval";
        message = "New Ship To Address for " + bean.getCustomerName() + ", Requested by " + personnelBean.getFullName() + " - Waiting Approval \n\n" +
                  "Please click on the link below to review and approve.\n\n" +
                  "https://www.tcmis.com/tcmIS/distribution/newshiptomain.do\n\n";

      String[] to = new String[c.size()];
      Iterator it = c.iterator();
      for (int i = 0; it.hasNext(); i++) {
        PersonnelBean b = (PersonnelBean) it.next();
        to[i] = b.getEmail();
      }
      String[] cc = new String[0];
      if(log.isDebugEnabled()) {
        for(int i=0; i<to.length;i++) {
          log.debug("Sending email to:" + to[i]);
        }
      }

      //MailProcess.sendEmail("mwennberg@haastcm.com", "", MailProcess.DEFAULT_FROM_EMAIL,subject, message);
      MailProcess.sendEmail(to, cc, subject, message, true);
    }
    catch(Exception e) {
      log.error("Error sending email.", e);
    }
  }*/

	public Collection getShipToAddress(NewShipToAddressInputBean inputBean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();


		if ( null!=inputBean.getCustomerId() )
			criteria.addCriterion("customerId", SearchCriterion.EQUALS,	inputBean.getCustomerId().toString());

		if ( null!=inputBean.getShipToLocationId() )
			criteria.addCriterion("shipToLocationId", SearchCriterion.EQUALS,	inputBean.getShipToLocationId().toString());

		if (log.isDebugEnabled()) {
			log.debug("customer_address_search_view"+criteria);
		}
		return  factory.setBean(new CustomerAddressSearchViewBean()).
		select(criteria,new SortCriteria("customerId"), "customer_address_search_view");
	}

} //end of class