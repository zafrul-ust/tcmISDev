package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.admin.beans.*;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.factory.UserOpsEntityHubIgOvBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.distribution.beans.*;
import com.tcmis.internal.distribution.factory.*;

/******************************************************************************
 * Process for searching new customer requests
 * @version 1.0
 *****************************************************************************/
public class CustomerEntityPGProcess
    extends GenericProcess {

  public CustomerEntityPGProcess(TcmISBaseAction act) throws BaseException {
		    super(act);
	  }

  public String updateShipToPG(OpsEntityFacilityViewBean bean,BigDecimal personnelId) throws BaseException, Exception {
//	   setting business logic...
/*	   
*  Pgk_customer_setup.p_modify_ship_to_ent_prc_grp(   
*      a_facility_
      ,a_company_d
      ,a_ops_entity_
      ,a_price_group_id
	  ,a_inventory_group      IN      ops_entity_facility.inventory_group%type default null
   	   a_personnel_id,             
      ,a_error_message
   );


*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
	  String pkgCall = "Pkg_customer_setup.p_modify_ship_to_ent_prc_grp";
	  return getProcError(
				   buildProcedureInput(bean.getFacilityId(),bean.getCompanyId(),bean.getOpsEntityId(),bean.getPriceGroupId(),bean.getInventoryGroupDefault(),personnelId)
				   ,null,pkgCall);
  }
  public String removeShipToPG(OpsEntityFacilityViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*    PROCEDURE p_delete_ship_to_ent_prc_grp(
      a_facility_id          IN      ops_entity_facility.facility_id%type,
      a_company_id           IN      ops_entity_facility.company_id%type,
      a_ops_entity_id        IN      ops_entity_facility.ops_entity_id%type,
      a_error_message       OUT      VARCHAR2
   );
	   */	   // I don't have update routine now.
	  String pkgCall = "Pkg_customer_setup.p_delete_ship_to_ent_prc_grp";
	  return getProcError(
				   buildProcedureInput(bean.getFacilityId(),bean.getCompanyId(),bean.getOpsEntityId())
				   ,null,pkgCall);
  }
  public String addShipToPG(OpsEntityFacilityViewBean bean,BigDecimal personnelId) throws BaseException, Exception {
//	   setting business logic...
/*    PROCEDURE p_add_ship_to_ent_prc_grp(
      a_facility_id          IN      ops_entity_facility.facility_id%type,
      a_company_id           IN      ops_entity_facility.company_id%type,
      a_ops_entity_id        IN      ops_entity_facility.ops_entity_id%type,
      a_price_group_id       IN      ops_entity_facility.price_group_id%type,
      a_inventory_group      IN      ops_entity_facility.inventory_group%type,
 	 a_personnel_id,             
      a_error_message       OUT      VARCHAR2
   );   
*/
      String pkgCall = "";
      pkgCall = "Pkg_customer_setup.p_add_ship_to_ent_prc_grp";
      return getProcError(
			   buildProcedureInput(bean.getFacilityId(),bean.getCompanyId(),bean.getOpsEntityId(),bean.getPriceGroupId(),bean.getInventoryGroupDefault(),personnelId)
			   ,null,pkgCall);

  }

  public String updateBillToPG(CustomerEntPriceGrpViewBean bean,BigDecimal personnelId) throws BaseException, Exception {
//	   setting business logic...
/*	   
*    PROCEDURE p_modify_cust_ent_price_grp(
     a_customer_id,
     a_bill_to_company_id,
     a_ops_entity_id,
     a_ops_company_id,
     a_price_group_id,     
 	 a_personnel_id,             
     a_error_message

   );
*/	   // I don't have update routine now.
	 //,bean.getCurrencyId()
	     String pkgCall = "pkg_customer_setup.p_modify_cust_ent_price_grp";
	     return getProcError(
				   buildProcedureInput(bean.getCustomerId(),bean.getBillToCompanyId(),bean.getOpsEntityId(),bean.getPriceGroupId(),personnelId)
				   ,null,pkgCall);
 }
 public String removeBillToPG(CustomerEntPriceGrpViewBean bean) throws BaseException, Exception {
//	   setting business logic...
/*	
 PROCEDURE p_delete_cust_ent_price_grp(
     a_customer_id,
     a_error_message
   );       
   */	   // I don't have update routine now.
     String pkgCall = "pkg_customer_setup.p_delete_cust_ent_price_grp";
     return getProcError(
			   buildProcedureInput(bean.getCustomerId(),bean.getOpsEntityId())
			   ,null,pkgCall);
 }
 
 public String addBillToPG(CustomerEntPriceGrpViewBean bean,BigDecimal personnelId) throws BaseException, Exception {
//	   setting business logic...
/*	   PROCEDURE p_add_customer_ent_price_grp(
     a_customer_id,
     a_bill_to_company_id,
     a_ops_entity_id,
     a_ops_company_id,
     a_price_group_id,  
 	 a_personnel_id,             
     a_error_message
   );

*/
     String pkgCall = "pkg_customer_setup.p_add_customer_ent_price_grp";
     return getProcError(
			   buildProcedureInput(bean.getCustomerId(),bean.getBillToCompanyId(),bean.getOpsEntityId(),bean.getPriceGroupId(),personnelId)
			   ,null,pkgCall);

 }
 
 public String getpg(OpsEntityFacilityViewBean bean) throws BaseException, Exception {
// just using price group id to store customer id in this case.	 
	String query = "select fx_get_default_price_group( "+bean.getCustomerId()+",'"+bean.getFacilityId()+"','"+bean.getOpsEntityId()+"') from dual";
	log.debug(query);
	Object[] c = factory.selectIntoObjectArray(query);
	
	String priceGroup = "";
	if (((Vector)(c)[2]).get(0) != null && ((Object[])((Vector)(c)[2]).get(0))[0] != null)
		priceGroup = (String) ((Object[])((Vector)(c)[2]).get(0))[0].toString();
	return priceGroup;
 }

 public String getInvName(String inv) throws BaseException, Exception {
		String query = "select inventory_group_name from inventory_group_definition where inventory_group = '"+inv+"'";
        log.debug(query);
        Object[] c = factory.selectIntoObjectArray(query);
		String inventoryGroupName = "";
		if (((Vector)(c)[2]).get(0) != null && ((Object[])((Vector)(c)[2]).get(0))[0] != null)
			inventoryGroupName = (String) ((Object[])((Vector)(c)[2]).get(0))[0].toString();
		return inventoryGroupName;
 }
  
 public String getInv(OpsEntityFacilityViewBean bean) throws BaseException, Exception {
		String query = "select fx_get_default_inventory_group( '"+bean.getOpsEntityId()+"','"+bean.getCompanyId()+"','"+bean.getFacilityId()+"') from dual";//"','"+bean.getInventoryGroupDefault()+
        log.debug(query);
        Object[] c = factory.selectIntoObjectArray(query);
		String inventoryGroup = "";
		if (((Vector)(c)[2]).get(0) != null && ((Object[])((Vector)(c)[2]).get(0))[0] != null)
			inventoryGroup = (String) ((Object[])((Vector)(c)[2]).get(0))[0].toString();
		return inventoryGroup;
 }
 
 public String getpgName(String pg) throws BaseException, Exception {
	 	String priceGroupName = "";
	 	if (pg == null || "".equals(pg)) return priceGroupName;
		String query = "select price_group_name from price_group where price_group_id = '"+pg+"'";
        log.debug(query);
        Object[] c = factory.selectIntoObjectArray(query);
		
		if (((Vector)(c)[2]).get(0) != null && ((Object[])((Vector)(c)[2]).get(0))[0] != null)
			priceGroupName =(String) ((Object[])((Vector)(c)[2]).get(0))[0].toString();
		return priceGroupName;
 }
 
}