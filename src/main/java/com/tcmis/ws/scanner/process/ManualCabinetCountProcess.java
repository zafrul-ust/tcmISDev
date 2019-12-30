package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.CabinetItemMrCreateViewBean;
import com.tcmis.internal.hub.factory.CabinetMaterialRequestFactory;

/******************************************************************************
 * Process for ManualCabinetCount
 * 
 * @version 1.0
 *****************************************************************************/

public class ManualCabinetCountProcess extends CabinetCountProcess {
	Log			log				= LogFactory.getLog(this.getClass());
	String		attachmentPath	= "";
	Connection	connection		= null;
	String		SQL				= "insert into cabinet_part_inventory_ctstage(bin_id,count_datetime,company_id,personnel_id,count_quantity,upload_sequence,count_source) values (?, ?, ?, ?, ?, ?, ?) ";

	public ManualCabinetCountProcess(String client) {
		super(client);
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	public String doCabinetItemInventoryCountInsertsManually(String companyId, BigDecimal personnelId, BigDecimal nextupLoadSeq) throws BaseException, Exception {
		String mrsCreated = "";
		try {
			Vector args = new Vector(1);
			args.add(nextupLoadSeq);
			Vector outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Vector optArgs = new Vector(1);
			optArgs.add("Y");
			// todo need to update procedure call to move data from
			// cabinet_part_inventory to cabinet_item_inventory_count
			Vector error = (Vector) factory.doProcedure(connection, "pkg_cabinet_count.P_COPY_STAGE_TO_PART_COUNT", args, outArgs, optArgs);
			connection.setAutoCommit(true);

			// procedure return null if everything processed okay
			if (error.get(0) == null) {
				// Create MRs
				String errorMessage = "";
				HashMap createMrResult = new HashMap();
				Boolean createMrSuccess = new Boolean(false);
				try {
					createMrResult = createMaterialRequestForItemCount(companyId, personnelId, nextupLoadSeq);
					createMrSuccess = (Boolean) createMrResult.get("SUCESS");
					if (!createMrSuccess.booleanValue()) {
						errorMessage += (String) createMrResult.get("ERROR");
					}
					mrsCreated = (String) createMrResult.get("CREATEDMRLIST");
				}
				catch (Exception ex2) {
					errorMessage += ex2.getMessage();
				}

				if (!StringHandler.isBlankString(errorMessage)) {
					sendMRErrorEmail("Error while creating MR's for scanned/counted data ",
							"Error from Updating after creating MR's\n\n" + errorMessage + "\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   " + personnelId);
				}
			}
		}
		catch (Exception e) {
			log.error(e);
		}
		finally {
			connection.setAutoCommit(true);
		}
		return mrsCreated;
	} // end of method

	private Collection getBin(String companyId) {
		Collection result = new Vector(0);
		try {
			StringBuilder query = new StringBuilder("select bin_id from cabinet_inventory_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			query.append(" union all ");
			query.append(" select bin_id from cabinet_item_inventory_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			query.append(" union all ");
			query.append(" select bin_id from work_area_bin_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			factory.setBeanObject(new CabinetItemInventoryCountBean());
			result = factory.selectQuery(query.toString(), connection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String doCabinetItemInventoryCountInserts(Collection<CabinetItemInventoryCountBean> cabinetItemInventoryBeans, BigDecimal personnelId, BigDecimal uploadSeq)
			throws BaseException, Exception {
		String mrsCreated = "";
		try {
			String companyId = "";
			String binIdsScannedList = "";
			int count = 0;
			boolean readyToBeCopied = false;
			Vector itemError = null;
			Vector receiptItemError = null;
			StringBuilder query;
			connection.setAutoCommit(false);

			// insert new data for item or part counting
			for (CabinetItemInventoryCountBean bean : cabinetItemInventoryBeans) {
				if (!bean.isCountByReceiptItem()) {
					companyId = bean.getCompanyId();

					String queryDate = "";
					if (bean.getCountDatetime() != null)
						queryDate = DateHandler.getOracleToDateFunction(bean.getCountDatetime());
					else
						queryDate = "sysdate";
					query = new StringBuilder(
							"insert into cabinet_part_inventory_ctstage(application_id, bin_id, item_id, receipt_id, count_datetime,company_id,personnel_id,count_quantity,upload_sequence,waste_flag,count_source)");
					query.append(" values (");
					query.append(bean.isCountByReceiptItem() ? (bean.hasApplicationId() ? bean.getApplicationId() : bean.getBinId()) : null);
					query.append(",").append(bean.isCountByReceiptItem() ? null : bean.getBinId());
					query.append(",").append(bean.getItemId());
					if (StringHandler.isBlankString(bean.getReceiptId()))
						query.append(",''");
					else
						query.append(",").append(bean.getReceiptId());
					query.append(",").append(queryDate);
					query.append(",").append(SqlHandler.delimitString(bean.getCompanyId()));
					query.append(",").append(personnelId);
					query.append(",").append(bean.getCountQuantity());
					query.append(",").append(uploadSeq);
					query.append(",").append(bean.isItemCountStatusWaste() ? "'Y'" : "'N'");
					if (StringHandler.isBlankString(bean.getCountSource()))
						query.append(",'Manual'");
					else
						query.append(",").append(SqlHandler.delimitString(bean.getCountSource()));
					query.append(")");
					factory.deleteInsertUpdate(query.toString(), connection);
					if (count == 0) {
						readyToBeCopied = true;
						count++;
					}
					else {
						binIdsScannedList += ",";
					}
					binIdsScannedList += bean.getBinId();
				}
			}

			if (readyToBeCopied) {
				// -- Move item/part count data to item count table
				Vector args = new Vector(1);
				args.add(uploadSeq);
				Vector outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				Vector optArgs = new Vector(1);
				optArgs.add("Y");
				itemError = (Vector) factory.doProcedure(connection, "pkg_cabinet_count.P_COPY_STAGE_TO_PART_COUNT", args, outArgs, optArgs);
				readyToBeCopied = false;
			}

			if (itemError != null && itemError.get(0) == null) {
				MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com",
						"CabinetCountProcess Error encountered while calling pkg_cabinet_count.P_COPY_STAGE_TO_PART_COUNT:" + uploadSeq, (String) itemError.get(0));
			}

			// insert new data for item or part counting - FullScans first
			for (CabinetItemInventoryCountBean bean : cabinetItemInventoryBeans) {
				if (bean.isCountByReceiptItem() && bean.isFullScan()) {
					companyId = bean.getCompanyId();
					String queryDate = "";
					if (bean.getCountDatetime() != null)
						queryDate = DateHandler.getOracleToDateFunction(bean.getCountDatetime());
					else
						queryDate = "sysdate";
					query = new StringBuilder(
							"insert into cabinet_part_inventory_ctstage(application_id, bin_id, item_id, receipt_id, count_datetime,company_id,personnel_id,count_quantity,upload_sequence,count_source)");
					query.append(" values (");
					query.append(bean.isCountByReceiptItem() ? (bean.hasApplicationId() ? bean.getApplicationId() : bean.getBinId()) : null);
					query.append(",").append(bean.isCountByReceiptItem() ? null : bean.getBinId());
					query.append(",").append(bean.getItemId());
					query.append(",").append(bean.getReceiptId());
					query.append(",").append(queryDate);
					query.append(",").append(SqlHandler.delimitString(bean.getCompanyId()));
					query.append(",").append(personnelId);
					query.append(",").append(bean.getCountQuantity());
					query.append(",").append(uploadSeq);
					query.append(",").append(SqlHandler.delimitString(bean.getCountSource()));
					query.append(")");
					factory.deleteInsertUpdate(query.toString(), connection);
					if (count == 0) {
						readyToBeCopied = true;
						count++;
					}
					else {
						if (!readyToBeCopied) {
							readyToBeCopied = true;
						}
						binIdsScannedList += ",";
					}
					binIdsScannedList += bean.getBinId();
				}
			}
			if (readyToBeCopied) {
				Vector args = new Vector(2);
				args.add(uploadSeq);
				args.add("Y");
				factory.doProcedure(connection, "pkg_cabinet_count.P_COPY_STAGE_TO_ITEM_COUNT", args);
				count = 0;
				readyToBeCopied = false;
			}
			// insert new data for item or part counting - PartialScans
			for (CabinetItemInventoryCountBean bean : cabinetItemInventoryBeans) {
				if (bean.isCountByReceiptItem() && !bean.isFullScan()) {
					companyId = bean.getCompanyId();
					String queryDate = "";
					if (bean.getCountDatetime() != null)
						queryDate = DateHandler.getOracleToDateFunction(bean.getCountDatetime());
					else
						queryDate = "sysdate";
					query = new StringBuilder(
							"insert into cabinet_part_inventory_ctstage(application_id, bin_id, item_id, receipt_id, count_datetime,company_id,personnel_id,count_quantity,upload_sequence,count_source)");
					query.append(" values (");
					query.append(bean.isCountByReceiptItem() ? (bean.hasApplicationId() ? bean.getApplicationId() : bean.getBinId()) : null);
					query.append(",").append(bean.isCountByReceiptItem() ? null : bean.getBinId());
					query.append(",").append(bean.getItemId());
					query.append(",").append(bean.getReceiptId());
					query.append(",").append(queryDate);
					query.append(",").append(SqlHandler.delimitString(bean.getCompanyId()));
					query.append(",").append(personnelId);
					query.append(",").append(bean.getCountQuantity());
					query.append(",").append(uploadSeq);
					query.append(",").append(SqlHandler.delimitString(bean.getCountSource()));
					query.append(")");
					factory.deleteInsertUpdate(query.toString(), connection);
					if (count == 0) {
						readyToBeCopied = true;
						count++;
					}
					else {
						if (!readyToBeCopied) {
							readyToBeCopied = true;
						}
						binIdsScannedList += ",";
					}
					binIdsScannedList += bean.getBinId();
				}
			}
			if (readyToBeCopied) {
				Vector args = new Vector(2);
				args.add(uploadSeq);
				args.add("N");
				factory.doProcedure(connection, "pkg_cabinet_count.P_COPY_STAGE_TO_ITEM_COUNT", args);
			}
			connection.setAutoCommit(true);

			// procedure return null if everything processed okay

			// Create MRs
			String errorMessage = "";
			HashMap createMrResult = new HashMap();
			Boolean createMrSuccess = new Boolean(false);
			try {

				Vector<BigDecimal> inArgs = new Vector<BigDecimal>(1);
				inArgs.add(uploadSeq);
				Vector<Integer> outArgs2 = new Vector<Integer>(1);
				outArgs2.add(new Integer(java.sql.Types.VARCHAR));
				Vector error2 = (Vector) factory.doProcedure(connection, "pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs2);
				if (error2.size() > 1 && error2.get(0) != null) {
					MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com",
							"CabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:" + uploadSeq, (String) error2.get(0));
				}

				createMrResult = createMaterialRequestForItemCount(companyId, personnelId, uploadSeq);
				createMrSuccess = (Boolean) createMrResult.get("SUCESS");
				if (!createMrSuccess.booleanValue()) {
					errorMessage += (String) createMrResult.get("ERROR");
				}
				mrsCreated = (String) createMrResult.get("CREATEDMRLIST");
			}
			catch (Exception ex2) {
				errorMessage += ex2.getMessage();
			}

			if (!StringHandler.isBlankString(errorMessage)) {
				sendMRErrorEmail("Error while creating MR's for scanned/counted data ", "Error from Updating after creating MR's\n\n" + errorMessage + "\n\n uploadsequence : "
						+ uploadSeq + "\n\nPersonnel ID:   " + personnelId + "\nBinIdList:" + binIdsScannedList);
			}

		}
		catch (Exception e) {
			log.error("Error in doCabinetItemInventoryCountInserts", e);
		}
		finally {
			connection.setAutoCommit(true);
		}
		return mrsCreated;
	} // end of method

	public HashMap createMaterialRequestForItemCount(String companyId, BigDecimal personnelId, BigDecimal uploadSeq) throws Exception {
		boolean createMrSuccess = true;
		String errorMessage = "";

		CabinetMaterialRequestFactory cabinetMrFactory = new CabinetMaterialRequestFactory(dbManager);
		factory.setBeanObject(new CabinetItemMrCreateViewBean());
		Collection resultColl = null;
		StringBuilder query = new StringBuilder("select * from CABINET_ITEM_MR_CREATE_VIEW a where a.company_id = '").append(companyId).append("'");
		query.append(" and (exists (select null from cabinet_item_inventory_count where processing_status = 'PROCESSED' and bin_id = a.bin_id and upload_sequence = ")
				.append(uploadSeq).append(")");
		query.append(" or exists (select null from work_area_bin_count where processing_status = 'PROCESSED' and bin_id = a.bin_id and upload_sequence = ").append(uploadSeq)
				.append("))");
		String createdMrList = "";
		int count = 0;

		Iterator cabinetMrCreateViewIterator = factory.selectQuery(query.toString(), connection).iterator();
		while (cabinetMrCreateViewIterator.hasNext()) {
			CabinetItemMrCreateViewBean cabinetMrCreateViewBean = (CabinetItemMrCreateViewBean) cabinetMrCreateViewIterator.next();

			if (StringHandler.emptyIfNull(cabinetMrCreateViewBean.getDistributorOps()).equalsIgnoreCase("Y")) {
				if (StringHandler.emptyIfNull(cabinetMrCreateViewBean.getDistUnitPrice()).length() > 0) {
					try {
						resultColl = cabinetMrFactory.createMaterialRequest(connection, cabinetMrCreateViewBean, personnelId, "Cabinet Scan Replenishment");
					}
					catch (Exception ex4) {
						ex4.printStackTrace();
						createMrSuccess = false;
						errorMessage += ex4.getMessage();
					}
				}
				else {
					log.info("errorCode : No price setup for this part " + cabinetMrCreateViewBean.getCatPartNo() + "");
					errorMessage += "No price setup for this part " + cabinetMrCreateViewBean.getCatPartNo() + " in bin_id: " + cabinetMrCreateViewBean.getBinId() + " @ "
							+ cabinetMrCreateViewBean.getOrderingFacility() + " Work Area " + cabinetMrCreateViewBean.getOrderingApplication() + "\n";
					createMrSuccess = false;
				}
			}
			else if (!StringHandler.emptyIfNull(cabinetMrCreateViewBean.getDistributorOps()).equalsIgnoreCase("Y")) {
				try {
					resultColl = cabinetMrFactory.createMaterialRequest(connection, cabinetMrCreateViewBean, personnelId, "Cabinet Scan Replenishment");
				}
				catch (Exception ex4) {
					createMrSuccess = false;
					errorMessage += ex4.getMessage();
				}
			}

			if (resultColl != null) {
				Iterator resultIterator = resultColl.iterator();
				int resultCount = 0;
				BigDecimal prNumber = null;
				String lineItem = "";
				String errorCode = "";
				while (resultIterator.hasNext()) {
					if (resultCount == 0) {
						prNumber = (BigDecimal) resultIterator.next();;
					}
					else if (resultCount == 1) {
						lineItem = (String) resultIterator.next();;
					}
					else if (resultCount == 2) {
						errorCode = (String) resultIterator.next();;
						if (errorCode == null) {
							errorCode = "";
						}
					}
					resultCount++;
				}

				if (errorCode.length() > 0) {
					log.info("errorCode :" + errorCode + "");
					errorMessage += errorCode;
					createMrSuccess = false;
				}
				else {
					if (count > 0) {
						createdMrList += ",";
					}

					createdMrList += "" + prNumber + "";
					count++;
					lineItemAllocate(cabinetMrCreateViewBean.getCompanyId(), prNumber, lineItem);
				}
				log.info("MR is created " + prNumber + "-" + lineItem + "");
			}
		}

		HashMap result = new HashMap();
		result.put("CREATEDMRLIST", createdMrList);
		result.put("ERROR", errorMessage);
		result.put("SUCESS", new Boolean(createMrSuccess));
		return result;
	} // end of method

} // end of class
