package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.MrIssueReceiptViewBean;
import com.tcmis.internal.hub.beans.PackShipConfirmInputBean;
import com.tcmis.internal.hub.beans.UsgovOiRequiredViewBean;
import com.tcmis.internal.hub.factory.LtlTlPalletOvBeanFactory;
import com.tcmis.internal.hub.factory.PalletDetailOvBeanFactory;
import com.tcmis.internal.hub.factory.ParcelOvBeanFactory;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process to build a web page for user to view and update package shipment.
 * @version 1.0
 *****************************************************************************/
public class PackShipConfirmProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PackShipConfirmProcess(String client) {
		super(client);
	}

	public PackShipConfirmProcess(String client,String locale) {
		super(client,locale);
	}

	public String updatePutShipmentOnPallet(PackShipConfirmInputBean inputBean) throws Exception {
		String result = "this is a test for Error";
		if (log.isDebugEnabled()) {
			log.debug(inputBean.getPrNumber()+"-"+inputBean.getLineItem());
		}
		return result;
	}

	public String getMrShipmentDetailResult(PackShipConfirmInputBean inputBean) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(inputBean.getPrNumber()+"-"+inputBean.getLineItem());
		}
		/*
	  DbManager dbManager = new DbManager(getClient());
		PalletDetailOvBeanFactory factory = new PalletDetailOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
	  criteria.addCriterion("palletId", SearchCriterion.EQUALS,inputBean.getPalletId());
		return factory.selectObject(criteria);
		 */
		return "test";
	} //end of method

	public String updatePalletDetail(PackShipConfirmInputBean inputBean, Collection beans) throws Exception {
		String result = "";
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		PalletDetailOvBeanFactory factory = new PalletDetailOvBeanFactory(dbManager);
		Iterator iter = beans.iterator();
		while (iter.hasNext()) {
			PackShipConfirmInputBean bean = (PackShipConfirmInputBean)iter.next();
			//skip if row not selected
			if (bean.getSelected() == null) continue;
			String tempPalletId = bean.getDifferentPalletId();
			if (!StringHandler.isBlankString(bean.getNewPalletId())) {
				tempPalletId = bean.getNewPalletId();
			}
			//test to make sure the pallet id is valid
			String temp = factory.validateUserInput("select fx_validate_pallet("+bean.getPackingGroupId()+",'"+tempPalletId+"') temp_val from dual");
			if (!"OK".equalsIgnoreCase(temp)) {
				if (result.length() > 0) {
					result += " <br> ";
				}
				result += temp;
				continue;
			}
			//call update procedure
			Vector inArgs = new Vector(4);
			inArgs.add(bean.getBoxId());
			inArgs.add(tempPalletId);
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");

			Vector outArgs = new Vector(1);
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
			Collection coll = procFactory.doProcedure("p_box_update", inArgs,outArgs);
			if( coll.size() == 1 ) {
				if (((Vector)coll).get(0) != null) {
					if (!"OK".equals(((Vector)coll).get(0).toString())) {
						if (result.length() > 0) {
							result += " <br> ";
						}
						result += ((Vector)coll).get(0).toString();
					}
				}
			}
		}
		return result;
	}

	public Collection getPalletDetailResult(PackShipConfirmInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		PalletDetailOvBeanFactory factory = new PalletDetailOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
		criteria.addCriterion("palletId", SearchCriterion.EQUALS,inputBean.getPalletId());
		return factory.selectObject(criteria);
	} //end of method

	public String confirmShipment(PackShipConfirmInputBean inputBean, Collection beans) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(inputBean.getDeliveredDate());
		}
		String result = "";
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Iterator iter = beans.iterator();
		while (iter.hasNext()) {
			PackShipConfirmInputBean bean = (PackShipConfirmInputBean)iter.next();
			//skip if row not selected
			if (bean.getSelected() == null) continue;
			Vector inArgs = new Vector(4);
			inArgs.add(bean.getCarrierCode());
			inArgs.add(bean.getLeadTrackingNumber());
			inArgs.add(new Timestamp(inputBean.getDeliveredDate().getTime()));
			inArgs.add(bean.getFacilityId());
			inArgs.add(inputBean.getUserId());
			inArgs.add(bean.getHazardous());
			inArgs.add(bean.getFedexParcelHazardousDocId());

			Vector outArgs = new Vector(1);
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
			if (log.isDebugEnabled()) {
				log.debug("p_ship_confirm_tracking_number   "+inArgs);
			}
			Collection coll = procFactory.doProcedure("p_ship_confirm_tracking_number", inArgs,outArgs);
			if( coll.size() == 1 ) {
				if (((Vector)coll).get(0) != null) {
					if (!"OK".equals(((Vector)coll).get(0).toString())) {
						if (result.length() > 0) {
							result += " <br> ";
						}
						result += ((Vector)coll).get(0).toString();
					}
				}
			}
		}
		return result;
	}

	public Collection getPackConfirmShipmentResult(PackShipConfirmInputBean inputBean) throws Exception {
		if ("ltltl".equals(inputBean.getTransportationMode())) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			LtlTlPalletOvBeanFactory factory = new LtlTlPalletOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
			return factory.selectDistinct(criteria);
		}else {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			ParcelOvBeanFactory factory = new ParcelOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
			criteria.addCriterion("transportationMode", SearchCriterion.EQUALS,"Parcel");
			return factory.selectDistinct(criteria);
		}
	} //end of method

	public Collection updateLtlTl(Collection<PackShipConfirmInputBean> beans)
	throws Exception {
		Vector errorMessages = new Vector();
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		for (PackShipConfirmInputBean bean : beans) {
			if (!bean.getLeadTrackingNumber().equals(
					bean.getOldLeadTrackingNumber())) {
				if (log.isDebugEnabled()) {
					log.debug(bean.getCarrierCode() + "-"
							+ bean.getLeadTrackingNumber() + "+"
							+ bean.getPalletId());
				}
				Vector inArgs = new Vector(2);
				inArgs.add(bean.getPalletId());
				inArgs.add(bean.getLeadTrackingNumber());
				Vector outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				Collection coll = factory.doProcedure(
						"p_update_pallet_tracking", inArgs, outArgs);

				if (coll.size() == 1) {
					if (((Vector) coll).get(0) != null) {
						if (!"OK".equals(((Vector) coll).get(0).toString())) {
							String errorMessage = "Error updating PalletId " +
							bean.getPalletId()+ ": "+((Vector)coll).get(0).toString();;
							errorMessages.add(errorMessage);
						}
					}
				}
			}
		}
		factory = null;
		dbManager = null;
		return errorMessages;
	}

	public void updateParcel (Collection beans) throws DbUpdateException,Exception {
		String result = "";
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		boolean firstTime = true;
		boolean rowIsSelected = false;
		BigDecimal selectedPackingGroupId = null;
		String selectedCarrierCode = "";
		String selectedLeadTrackingNumber = "";
		Iterator iter = beans.iterator();
		while (iter.hasNext()) {
			PackShipConfirmInputBean bean = (PackShipConfirmInputBean)iter.next();
			BigDecimal currentPackingGroupId = bean.getPackingGroupId();
			if (!currentPackingGroupId.equals(selectedPackingGroupId)) {
				if (!StringHandler.isBlankString(bean.getSelected())) {
					rowIsSelected = true;
					selectedPackingGroupId = bean.getPackingGroupId();
					selectedCarrierCode = bean.getCarrierCode();
					selectedLeadTrackingNumber = bean.getLeadTrackingNumber();
				}else {
					rowIsSelected = false;
				}
			}

			if (rowIsSelected) {
				//in data
				Vector inArgs = new Vector(7);
				inArgs.add(bean.getBoxId());
				inArgs.add("");
				inArgs.add(selectedLeadTrackingNumber);
				inArgs.add("");
				inArgs.add("");
				inArgs.add("");
				inArgs.add("");
				//out data
				Vector outArgs = new Vector(1);
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				//optional in data
				Vector optionalV = new Vector(7);
				optionalV.add("");
				optionalV.add("");
				optionalV.add("");
				optionalV.add("");
				optionalV.add("");
				optionalV.add("");
				optionalV.add(bean.getBoxTrackingNumber());
				if (log.isDebugEnabled()) {
					log.debug(inArgs+"="+optionalV);
				}
				Collection coll = procFactory.doProcedure("p_box_update", inArgs,outArgs,optionalV);
				if( coll.size() == 1 ) {
					if (((Vector)coll).get(0) != null) {
						if (!"OK".equals(((Vector)coll).get(0).toString())) {
							if (result.length() > 0) {
								result += " <br> ";
							}
							result += ((Vector)coll).get(0).toString();
						}
					}
				}
			}
			selectedPackingGroupId = currentPackingGroupId;
		} //end of while loop

		//check to see if there any error from procedure
		if (!StringHandler.isBlankString(result)) {
			throw new DbUpdateException(result);
		}
	} //end of method

	public void requestWAWFInsRequest(Collection<PackShipConfirmInputBean> beans) throws
	BaseException {
		Collection<UsgovOiRequiredViewBean> usgovOiRequiredViewColl = new Vector();
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UsgovOiRequiredViewBean());



		for(PackShipConfirmInputBean bean: beans) {
			if (bean.getSelected() == null) continue;
			SearchCriteria searchCriteria = new SearchCriteria();
			if (!isBlank(bean.getCarrierCode())) {
				searchCriteria.addCriterion("carrierCode",SearchCriterion.EQUALS,bean.getCarrierCode());
			}
			if (!isBlank(bean.getLeadTrackingNumber())) {
				searchCriteria.addCriterion("trackingNumber",SearchCriterion.EQUALS,bean.getLeadTrackingNumber());
			}

			usgovOiRequiredViewColl = factory.select(searchCriteria, null,"USGOV_OI_REQUIRED_VIEW");

			for(UsgovOiRequiredViewBean uBean: usgovOiRequiredViewColl) {
				Collection inArgs = new Vector();

				if( !isBlank(uBean.getPrNumber()))
					inArgs.add(uBean.getPrNumber());
				else
					inArgs.add("");

				if( !isBlank(uBean.getLineItem()))
					inArgs.add(uBean.getLineItem());
				else
					inArgs.add("");

				if( !isBlank(uBean.getPackingGroupId()))
					inArgs.add(uBean.getPackingGroupId());
				else
					inArgs.add("");

				if( !isBlank(uBean.getShipFromLocationId()))
					inArgs.add(uBean.getShipFromLocationId());
				else
					inArgs.add("");

				GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
				procFactory.doProcedure("p_generate_origin_insp_asn", inArgs);
			}
		}
	}

	public BigDecimal printHazardousGoodsManifest(Collection<PackShipConfirmInputBean> beans) throws
	BaseException {
		String result = "";
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		String createNewHazDocId = "Y";
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal hazDocId = null;

		boolean rowIsSelected = false;
		BigDecimal selectedPackingGroupId = null;
		String selectedHazardous = "";
		Iterator iter = beans.iterator();
		while (iter.hasNext()) {
			PackShipConfirmInputBean bean = (PackShipConfirmInputBean)iter.next();
			BigDecimal currentPackingGroupId = bean.getPackingGroupId();
			if (!currentPackingGroupId.equals(selectedPackingGroupId)) {
				if (!StringHandler.isBlankString(bean.getSelected())) {
					rowIsSelected = true;
					selectedPackingGroupId = bean.getPackingGroupId();
					selectedHazardous = bean.getHazardous();
				}else {
					rowIsSelected = false;
				}
			}

			if (rowIsSelected && "Y".equals(selectedHazardous)) {
				if("Y".equals(createNewHazDocId))
					hazDocId = new BigDecimal(dbManager.getOracleSequence("FEDEX_PARCEL_HAZ_DOC_ID"));
				createNewHazDocId = "N";
				//in data
				Vector inArgs = new Vector(2);
				inArgs.add(bean.getBoxId());
				inArgs.add(hazDocId);
				//out data
				Vector outArgs = new Vector(1);
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				Collection coll = procFactory.doProcedure("P_UPDATE_FEDX_HAZ_DOC_ID", inArgs,outArgs);
				if( coll.size() == 1 ) {
					if (((Vector)coll).get(0) != null) {
						if (!"OK".equals(((Vector)coll).get(0).toString())) {
							if (result.length() > 0) {
								result += " <br> ";
							}
							result += ((Vector)coll).get(0).toString();
						}
					}
				}
			}
			selectedPackingGroupId = currentPackingGroupId;
		} //end of while loop

		//check to see if there any error from procedure
		if (!StringHandler.isBlankString(result)) {
			throw new DbUpdateException(result);
		}
		return hazDocId;

	}


	public Collection getSearchResult(PackShipConfirmInputBean inputBean) throws Exception {
		if ("ltltl".equals(inputBean.getTransportationMode())) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			LtlTlPalletOvBeanFactory factory = new LtlTlPalletOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
			return factory.selectObject(criteria);
		}else {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			ParcelOvBeanFactory factory = new ParcelOvBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
			//criteria.addCriterion("transportationMode", SearchCriterion.EQUALS,"Parcel");
			return factory.selectObject(criteria);
		}
	} //end of method
	
	public Collection<MrIssueReceiptViewBean> listMrLines(PackShipConfirmInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Collection<MrIssueReceiptViewBean> mrlines = null;
		if (StringHandler.isBlankString(inputBean.getConsolidationNumber())) {
			mrlines = Collections.emptyList();
		}
		else {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,inputBean.getSourceHub());
			String query = "select rli.pr_number, rli.line_item from"
					+ " customer.request_line_item rli"
					+ ", box_shipment_setup b"
					+ " where b.consolidation_number = " + SqlHandler.delimitString(inputBean.getConsolidationNumber())
					+ " and b.company_id = rli.company_id"
					+ " and b.customer_po_no = rli.po_number"
					+ " group by rli.pr_number, rli.line_item"
					+ " order by rli.pr_number, rli.line_item";
			mrlines = factory.setBean(new MrIssueReceiptViewBean()).selectQuery(query);
		}
		return mrlines;
	}

} //end of class
