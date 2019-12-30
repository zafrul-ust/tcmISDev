package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.CabinetMrCreateViewBean;
import com.tcmis.internal.hub.beans.WorkAreaBinCountBean;
import com.tcmis.internal.hub.factory.CabinetInventoryCountStageBeanFactory;
import com.tcmis.internal.hub.factory.CabinetMaterialRequestFactory;

/**
 * ***************************************************************************
 * Process for Receipt Document
 * 
 * @version 1.0
 *          *****************************************************************
 *          **********
 */

public class CabinetCountProcess extends GenericProcess {
	Log				log				= LogFactory.getLog(this.getClass());

	BulkMailProcess	bulkMail		= null;

	Connection		connection		= null;

	String			workAreaBinSQL	= "insert into work_area_bin_count_stage (upload_sequence, bin_id, count_datetime, receipt_id, container_number, company_id, count_type, personnel_id, count_quantity, container_open, container_expired, count_source, container_condition) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";
	// insert into work_area_bin_count_stage
	// (upload_sequence,
	// bin_id,
	// count_datetime,
	// receipt_id,
	// container_number,
	// company_id,
	// count_type,
	// personnel_id,
	// count_quantity,
	// container_open,
	// container_expired,
	// count_source,
	// container_condition) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
	BigDecimal		zero			= new BigDecimal("0");

	public CabinetCountProcess(String client, Locale locale) {
		super(client, locale);
		bulkMail = new BulkMailProcess(client);
	}

	/* Need this for some old code that is dependent */
	public CabinetCountProcess(String client) {
		super(client);
		bulkMail = new BulkMailProcess(client);
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	public static void sendErrorEmailAttach(String subject, String msg, String attachmentPath) {
		MailProcess.sendEmail("edierror@haasgroupintl.com", "", "edierror@haasgroupintl.com", subject, msg, "CabinetCount:" + attachmentPath, attachmentPath);
	}

	public static void sendErrorEmail(String subject, String msg) {
		MailProcess.sendEmail("edierror@haasgroupintl.com", "", "edierror@haasgroupintl.com", subject, msg);
	}

	public static void sendMRErrorEmail(String subject, String msg) {
		MailProcess.sendEmail("deverror@haasgroupintl.com", "edierror@haasgroupintl.com", "deverror@haasgroupintl.com", subject, msg);
		MailProcess.sendEmail("sbuffum@haastcm.com,jchang@haastcm.com,mboze@haastcm.com,dbertero@haastcm.com", "mbradford@haastcm.com", "deverror@haasgroupintl.com", subject, msg);
	}

	private BigDecimal sendBulkEmail(BigDecimal p, String s1, String s2, boolean b) throws BaseException {
		return bulkMail.sendBulkEmail(p, s1, s2, b);
	}

	// note: when use this method make sure to set connection, otherwise it will
	// not work correctly
	public String processKban(Vector<CabinetInventoryCountStageBean> countByKBeans) {
		String pkgCall = "pkg_cabinet_count.P_INSERT_CAB_KANBAN_INV_CT";
		String err = "";
		String mrList = "";
		String sep = "";
		Date dateProcessed = new Date();
		for (CabinetInventoryCountStageBean bean : countByKBeans) {
			try {
				bean.setDateProcessed(dateProcessed);
				Vector<BigDecimal> inArgs = new Vector<BigDecimal>();
				Vector<Integer> outArgs = new Vector<Integer>();
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				inArgs = buildProcedureInput(bean.getBinId(), bean.getCountDatetime(), bean.getCountQuantity(), //
						bean.getCompanyId(), bean.getPersonnelId(), bean.getDateProcessed(), bean.getUploadSequence());
				Collection coll = factory.doProcedure(connection, pkgCall, inArgs, outArgs);
				String errorMsg = (String) ((Vector) coll).get(1);
				if (errorMsg != null && errorMsg.length() > 0 && !errorMsg.equalsIgnoreCase("OK")) {
					err += "\nError BinId:" + bean.getBinId() + ":" + bean.getUploadSequence() + ":" + errorMsg;
					continue;
				}
				String mrStr = (String) ((Vector) coll).get(0);
				if (!this.isBlank(mrStr)) {
					mrList += sep + mrStr;
					sep = ",";
				}
			}
			catch (Exception ex) {
				err += "\nError BinId:" + bean.getBinId() + ":" + bean.getUploadSequence() + ":" + ex.getMessage();
			}
		}
		if (!"".equals(err)) {
			sendMRErrorEmail("KBan process errors", err);
		}
		return mrList;
	}

	public String[] processReceiptCount(Vector<String> binIdsScanned, Vector<CabinetInventoryCountStageBean> countByReceiptBeans, BigDecimal personnelId, BigDecimal nextupLoadSeq) {
		String[] result = new String[2];
		String errorMessage = "";
		String mrsCreated = "";
		CabinetInventoryCountStageBeanFactory cIFactory = new CabinetInventoryCountStageBeanFactory(dbManager);
		String companyId = "";
		try {
			for (CabinetInventoryCountStageBean countByReceiptbean : countByReceiptBeans) {
				// insert data into stage table
				try {
					companyId = countByReceiptbean.getCompanyId();
					cIFactory.insert(countByReceiptbean, connection);
				}
				catch (BaseException ex) {
					errorMessage = ex.getMessage();
					sendErrorEmail("Error from inserting" + " into cabinet_inventory_count_stage This count might already be entered", "Error from inserting into cabinet_inventory_count_stage This count might already be entered\n\n" + errorMessage
							+ "\n\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   " + personnelId + "");
				}
			}
			// delete Duplicate Scans
			try {
				cIFactory.deleteDuplicateScans(connection, personnelId);
			}
			catch (Exception ex1) {
				errorMessage = ex1.getMessage();
				sendErrorEmail("Error delete from cabinet_inventory_count_stage for duplicates", "Error delete from cabinet_inventory_count_stage for duplicates\n\n" + ex1.getMessage() + "\n\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   "
						+ personnelId + "");
			}
			// process data
			StringBuilder query = new StringBuilder("select count(*) from cabinet_inventory_count_stage where upload_sequence = ");
			query.append(nextupLoadSeq);
			String stageCount = factory.selectSingleValue(query.toString(), connection);
			if (!"0".equals(stageCount)) {
				String binIdsScannedList = "";
				int count = 0;
				Iterator<String> binIdsScannedIterator = binIdsScanned.iterator();
				while (binIdsScannedIterator.hasNext()) {
					String binId = binIdsScannedIterator.next();
					if (count > 0) {
						binIdsScannedList += ",";
					}
					binIdsScannedList += "'" + binId + "'";
					count++;
				}
				// Update date processed for any unprocessed scans from a
				// previous scan for the bins that were just uploaded.
				try {
					cIFactory.updateDateProcessedForPrevCounts(connection, binIdsScannedList, personnelId);
				}
				catch (Exception ex2) {
					errorMessage = ex2.getMessage();
					sendErrorEmail("Cabinet Scanner Count Error Update date processed for any unprocessed scans from a previous scan.",
							"Cabinet Scanner Count Error Update date processed for any unprocessed scans from a previous scan for the bins that were just uploaded.\n\n" + ex2.getMessage() + "\n\n" + binIdsScannedList + "\n\n" + errorMessage
									+ "\n\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   " + personnelId + "");
				}
				// consolidating the scans in to cabinet_inventory_count
				try {
					cIFactory.consolidateCabinetScan(connection, personnelId, nextupLoadSeq);
				}
				catch (Exception ex1) {
					errorMessage = ex1.getMessage();
					String s1 = "Error when moving to cabinet_inventory_count table from the stage table - New scanner";
					String s2 = "Error when moving to cabinet_inventory_count table from the stage table\n\n" + ex1.getMessage() + "\n\nPersonnel ID:   " + personnelId + "";
					String s3 = s2 + "\n\n\n uploadsequence : " + nextupLoadSeq;
					sendErrorEmail(s1, s3);
					sendBulkEmail(new BigDecimal("19143"), s1, s2, false);
					sendBulkEmail(new BigDecimal("11653"), s1, s2, false);
					sendBulkEmail(new BigDecimal("9422"), s1, s2, false);
					sendBulkEmail(
							personnelId,
							"Error in Cabinet Scanner Upload",
							"An error occured while uploading scanner data.\nSome of the data you are trying to upload has already been uploaded.\n This might have been caused if you did not download to the scanner before doing a scan.\n The Tech Center has been informed and will let you know when it is resolved.\n You don't need to upload the scan again.\nContact the Tech Center if you have any questions.",
							false);
				}

				if (errorMessage.length() == 0) {
					HashMap<String, Comparable> createMrResult = new HashMap<String, Comparable>();
					Boolean createMrSuccess = new Boolean(false);
					try {
						createMrResult = createMaterialRequestForReceiptCount(nextupLoadSeq, companyId, personnelId);
						createMrSuccess = (Boolean) createMrResult.get("SUCESS");
						if (!createMrSuccess.booleanValue()) {
							errorMessage += (String) createMrResult.get("ERROR");
						}
						String additionalMRs = (String) createMrResult.get("CREATEDMRLIST");
						if (!StringHandler.isBlankString(additionalMRs)) {
							if (mrsCreated.length() > 0) {
								mrsCreated += ",";
							}
							mrsCreated += additionalMRs;
						}
					}
					catch (Exception ex2) {
						errorMessage = ex2.getMessage();
						sendMRErrorEmail("Cabinet Scanner Count Error from creating MR's", "Error from Updating after creating MR's\n\n" + errorMessage + "\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   " + personnelId + "\nBinIdList:"
								+ binIdsScannedList);
					}
				}
			}
			// delete data from stage
			SearchCriteria deleteCriteria = new SearchCriteria();
			deleteCriteria.addCriterion("uploadSequence", SearchCriterion.EQUALS, "" + nextupLoadSeq + "");
			cIFactory.delete(deleteCriteria);
		}
		catch (Exception ex1) {
			errorMessage = ex1.getMessage();
			sendErrorEmail("Error while processing count data by receipt", "Error while processing count data by receipt\n\n" + ex1.getMessage() + "\n\n\n uploadsequence : " + nextupLoadSeq + "\n\nPersonnel ID:   " + personnelId + "");
		}
		// build return result data
		result[0] = mrsCreated;
		result[1] = errorMessage;
		return result;
	} // end of method

	public String processManually() throws BaseException {

		String errorMessage = "";
		String mrsCreated = "";
		try {
			connection = dbManager.getConnection();

			// todo fill the following data

			/*
			 * String companyId = "BELL"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/bell/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170651),new
			 * BigDecimal(170657)}; BigDecimal[] personnelIdA = {new
			 * BigDecimal(47229),new BigDecimal(47226)};
			 * 
			 * 
			 * String companyId = "BOEING_CO"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/boeing/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170046),new
			 * BigDecimal(170052),new BigDecimal(170083),new BigDecimal(170085),
			 * new BigDecimal(170329),new BigDecimal(170593),new
			 * BigDecimal(170767),new BigDecimal(170792), new
			 * BigDecimal(170795),new BigDecimal(170799),new
			 * BigDecimal(170805)}; BigDecimal[] personnelIdA = {new
			 * BigDecimal(55365),new BigDecimal(55514),new BigDecimal(55365),new
			 * BigDecimal(23450), new BigDecimal(55365),new
			 * BigDecimal(55514),new BigDecimal(55365),new BigDecimal(23450),
			 * new BigDecimal(55514),new BigDecimal(55365),new
			 * BigDecimal(23450)};
			 * 
			 * String companyId = "GKN"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/gkn/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170006),new
			 * BigDecimal(170049)}; BigDecimal[] personnelIdA = {new
			 * BigDecimal(37998),new BigDecimal(37998)};
			 * 
			 * String companyId = "LOCKHEED"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/lmco/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170100)};
			 * BigDecimal[] personnelIdA = {new BigDecimal(51154)};
			 * 
			 * String companyId = "ROLLS_ROYCE"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/rollsroyce/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170055)};
			 * BigDecimal[] personnelIdA = {new BigDecimal(52343)};
			 * 
			 * String companyId = "UTC"; String resultUrl =
			 * "https://www.tcmis.com/tcmIS/utc/customermrtracking.do?";
			 * BigDecimal[] nextupLoadSeqA = {new BigDecimal(170265)};
			 * BigDecimal[] personnelIdA = {new BigDecimal(33184)};
			 */
			String companyId = "WOODWARD";
			String resultUrl = "https://www.tcmis.com/tcmIS/woodward/customermrtracking.do?";
			BigDecimal[] nextupLoadSeqA = { new BigDecimal(170413) };
			BigDecimal[] personnelIdA = { new BigDecimal(51612) };

			for (int i = 0; i < nextupLoadSeqA.length; i++) {
				BigDecimal nextupLoadSeq = nextupLoadSeqA[i];
				BigDecimal personnelId = personnelIdA[i];
				// count by item/part
				try {
					ManualCabinetCountProcess countByItemProcess = new ManualCabinetCountProcess(getClient());
					countByItemProcess.setConnection(connection);
					mrsCreated = countByItemProcess.doCabinetItemInventoryCountInsertsManually(companyId, personnelId, nextupLoadSeq);
				}
				catch (Exception byItemEx) {
					errorMessage += byItemEx.getMessage();
					sendErrorEmail("Error countByItemProcess.doCabinetItemInventoryCountInserts", "Error countByItemProcess.doCabinetItemInventoryCountInserts\n\n" + byItemEx.getMessage() + "\n\n\n uploadsequence : " + nextupLoadSeq
							+ "\n\nPersonnel ID:   " + personnelId + "");
				}
				// running below process to process haas owned cabinets and
				// usage data
				Vector<BigDecimal> inArgs = new Vector<BigDecimal>(1);
				inArgs.add(nextupLoadSeq);
				Vector<Integer> outArgs = new Vector<Integer>(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				Vector error = (Vector) factory.doProcedure(connection, "pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs);
				if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "CabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:" + nextupLoadSeq, (String) error.get(0));
				}

				String message = "\n\nThe upload has been processed successfully.";
				if (mrsCreated.length() > 0) message += "\n\nPlease go to the URL below to look at the results.\n\n\n" + resultUrl + "UserAction=Search&mrslist=" + mrsCreated;
				message += "\n\n\nuploadsequence : " + nextupLoadSeq + "\n\n\nPersonnelId : " + personnelId;

				sendBulkEmail(personnelId, "Cabinet Scanner Upload Results", message, true);
				if (log.isDebugEnabled()) {
					log.debug(message);
				}
			}

		}
		catch (Exception e) {
			log.error(e);
		}
		finally {
			dbManager.returnConnection(connection);
			connection = null;
		}
		return errorMessage;
	} // end of method

	public String process(Vector<String> binIdsScanned, Vector<CabinetInventoryCountStageBean> countByReceiptBeans, Vector<CabinetItemInventoryCountBean> countByItemBeans, Vector<CabinetInventoryCountStageBean> countByKBeans, BigDecimal personnelId,
			String url, BigDecimal uploadSequence, Vector<WorkAreaBinCountBean> workAreaBinCountBeans) throws BaseException {

		String errorMessage = "";
		String mrsCreated = "";
		try {
			connection = dbManager.getConnection();

			// count by work area bin (this will handle count by CONTAINER,
			// RECEIPT_ID/RECEIPT, ADVRECEIPT and DISBURSEMENT)
			if (!workAreaBinCountBeans.isEmpty()) {
				mrsCreated += processWorkAreaBinCount(personnelId, workAreaBinCountBeans, uploadSequence);
			}

			// count by kanban
			if (!countByKBeans.isEmpty()) {
				if (!"".equals(mrsCreated)) mrsCreated += ",";
				mrsCreated += processKban(countByKBeans);
			}

			// count by item, part & receiptItem
			if (!countByItemBeans.isEmpty()) {
				try {
					ManualCabinetCountProcess countByItemProcess = new ManualCabinetCountProcess(getClient());
					countByItemProcess.setConnection(connection);
					if (!"".equals(mrsCreated)) mrsCreated += ",";
					mrsCreated += countByItemProcess.doCabinetItemInventoryCountInserts(countByItemBeans, personnelId, uploadSequence);
				}
				catch (Exception byItemEx) {
					errorMessage += byItemEx.getMessage();
					sendErrorEmail("Error countByItemProcess.doCabinetItemInventoryCountInserts", "Error countByItemProcess.doCabinetItemInventoryCountInserts\n\n" + byItemEx.getMessage() + "\n\n\n uploadsequence : " + uploadSequence
							+ "\n\nPersonnel ID:   " + personnelId + "");
				}
			} // end of count by item

			String resultUrl = url;
			String message = "\n\nThe upload has been processed successfully.";
			if (mrsCreated.length() > 0) message += "\n\nPlease go to the URL below to look at the results.\n\n\n" + resultUrl + "UserAction=Search&mrslist=" + mrsCreated;
			message += "\n\n\nuploadsequence : " + uploadSequence + "\n\n\nPersonnelId : " + personnelId;

			sendBulkEmail(personnelId, "Cabinet Scanner Upload Results", message, true);
			sendErrorEmail("Cabinet Scanner Upload Results", message + "\n\nScanned Bin Ids" + binIdsScanned.toString());
			if (log.isDebugEnabled()) {
				log.debug(message);
			}
		}
		catch (Exception e) {
			log.error(e);
		}
		finally {
			dbManager.returnConnection(connection);
			connection = null;
		}
		return errorMessage;
	} // end of method
	
	public void processCount(Connection connection, BigDecimal uploadSeq) throws BaseException {
		Vector<BigDecimal> inArgs = new Vector<BigDecimal>(1);
		inArgs.add(uploadSeq);
		Vector<Integer> outArgs = new Vector<Integer>(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		Vector error = (Vector) factory.doProcedure(connection, "pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs);
		if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
			MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "CabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:" + uploadSeq, (String) error.get(0));
		}
	}

	public HashMap<String, Comparable> createMaterialRequestForReceiptCount(BigDecimal nextupLoadSeq, String companyId, BigDecimal personnelId) throws Exception {
		boolean createMrSuccess = true;
		String errorMessage = "";

		CabinetMaterialRequestFactory cabinetMrFactory = new CabinetMaterialRequestFactory(dbManager);
		factory.setBeanObject(new CabinetMrCreateViewBean());
		Collection resultColl = null;
		StringBuilder query = new StringBuilder("select * from CABINET_MR_CREATE_VIEW a where a.company_id = '").append(companyId).append("'");
		query.append(" and (exists (select null from cabinet_inventory_count where date_processed is null and bin_id = a.bin_id");
		query.append(" and company_id ='").append(companyId).append("' and upload_sequence = ").append(nextupLoadSeq).append(")");
		query.append(" or exists (select null from work_area_bin_count where date_processed is null and bin_id = a.bin_id");
		query.append(" and company_id ='").append(companyId).append("' and upload_sequence = ").append(nextupLoadSeq).append("))");
		String createdMrList = "";
		int count = 0;

		Iterator<CabinetMrCreateViewBean> cabinetMrCreateViewIterator = factory.selectQuery(query.toString(), connection).iterator();
		while (cabinetMrCreateViewIterator.hasNext()) {
			CabinetMrCreateViewBean cabinetMrCreateViewBean = cabinetMrCreateViewIterator.next();
			try {
				resultColl = cabinetMrFactory.createMaterialRequest(connection, cabinetMrCreateViewBean, personnelId, "Cabinet Scan Replenishment");
			}
			catch (Exception ex4) {
				createMrSuccess = false;
				ex4.printStackTrace();
				errorMessage += ex4.getMessage();
			}

			if (resultColl != null) {
				Iterator resultIterator = resultColl.iterator();
				int resultCount = 0;
				BigDecimal prNumber = null;
				String lineItem = "";
				String errorCode = "";
				while (resultIterator.hasNext()) {
					if (resultCount == 0) {
						prNumber = (BigDecimal) resultIterator.next();
					}
					else if (resultCount == 1) {
						lineItem = (String) resultIterator.next();
					}
					else if (resultCount == 2) {
						errorCode = (String) resultIterator.next();
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
					log.info("MR is created " + prNumber + "-" + lineItem + "");
					lineItemAllocate(cabinetMrCreateViewBean.getCompanyId(), prNumber, lineItem);
				}
			}
		}

		HashMap<String, Comparable> result = new HashMap<String, Comparable>();
		result.put("CREATEDMRLIST", createdMrList);
		result.put("ERROR", errorMessage);
		result.put("SUCESS", new Boolean(createMrSuccess));
		return result;
	}

	protected void lineItemAllocate(String companyId, BigDecimal prNumber, String lineItem) throws BaseException {
		Vector<Comparable> args = new Vector<Comparable>(4);
		if ("TCM_OPS".equals(this.getClient())) {
			args.add(companyId);
			args.add(prNumber);
			args.add(lineItem);
			args.add("Y");
		}
		else {
			args.add(prNumber);
			args.add(lineItem);
			args.add("Y");
			args.add(companyId);
		}

		try {
			if (connection == null) {
				connection = dbManager.getConnection();
			}

			factory.doProcedure(connection, "p_line_item_allocate", args);
			// if (log.isDebugEnabled()) {
			// log.debug("called p_line_item_allocate for (companyId,pr,line): "
			// + prNumber + "," + lineItem);
			// }
		}
		catch (BaseException be2) {
			log.error("BaseException calling p_line_item_allocate(" + prNumber + "," + lineItem + "): " + be2);
		}
	} // end of method

	private Collection<CabinetMrCreateViewBean> getBin(String companyId) {
		Collection<CabinetMrCreateViewBean> result = new Vector<CabinetMrCreateViewBean>(0);
		try {
			StringBuilder query = new StringBuilder("select bin_id from cabinet_inventory_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			query.append(" union all ");
			query.append(" select bin_id from cabinet_item_inventory_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			query.append(" union all ");
			query.append(" select bin_id from work_area_bin_count where modified_timestamp > sysdate -2");
			query.append(" and company_id = '").append(companyId).append("'");
			factory.setBeanObject(new WorkAreaBinCountBean());
			result = factory.selectQuery(query.toString(), connection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String processWorkAreaBinCount(BigDecimal personnelId, Vector<WorkAreaBinCountBean> workAreaBinCountBeans, BigDecimal uploadSeq) {
		String mrsCreated = "";
		StringBuilder query = null;
		try {
			String companyId = "";
			String binIdsScannedList = "";
			int count = 0;
			boolean countByContainerOrAdvancedReceipt = false;
			boolean countbyReceipt = false;
			BigDecimal zero = new BigDecimal(0);

			for (WorkAreaBinCountBean bean : workAreaBinCountBeans) {
				// keep track if any record count by container or advanced
				// receipt
				if (!countByContainerOrAdvancedReceipt && (bean.isCountByContainer() || bean.isCountByAdvancedReceipt())) {
					countByContainerOrAdvancedReceipt = true;
				}
				// keep track if any record count by receipt
				if (!countbyReceipt && bean.isCountByReceipt()) {
					countbyReceipt = true;
				}

				// setting company_id
				if (count == 0) companyId = bean.getCompanyId();
				// put data into stage table
				query = new StringBuilder(
						"insert into work_area_bin_count_stage (upload_sequence,bin_id,count_datetime,receipt_id,container_number,company_id,count_type,personnel_id,count_quantity,container_open,container_expired,count_source,container_condition, DISBURSED_UNIT_OF_MEASURE)");
				query.append(" values (");
				query.append(bean.getUploadSequence()).append(",");
				query.append(bean.getBinId()).append(",");
				query.append(DateHandler.getOracleToDateFunction(bean.getCountDatetime())).append(",");
				query.append(bean.getReceiptId()).append(",");
				// considering container number = 0
				if (bean.getContainerNumber().equals(zero)) query.append("null,");
				else
					query.append(bean.getContainerNumber()).append(",");

				query.append("'").append(bean.getCompanyId()).append("',");
				query.append("'").append(bean.getCountType()).append("',").append(bean.getPersonnelId()).append(",");
				query.append(bean.getCountQuantity());
				if (StringHandler.isBlankString(bean.getContainerOpen())) {
					query.append(",'N'");
				}
				else {
					query.append(",'").append(bean.getContainerOpen()).append("'");
				}
				if (StringHandler.isBlankString(bean.getContainerExpired())) {
					query.append(",'N'");
				}
				else {
					query.append(",'").append(bean.getContainerExpired()).append("'");
				}
				if (StringHandler.isBlankString(bean.getCountSource())) {
					query.append(",'SCANNER'");
				}
				else {
					query.append(",'").append(bean.getCountSource()).append("'");
				}
				if ("Y".equals(bean.getContainerExpired()) || StringHandler.isBlankString(bean.getContainerCondition())) {
					query.append(",null");
				}
				else {
					query.append(",'").append(bean.getContainerCondition()).append("'");
				}
				query.append(",'").append(bean.getUnitOfMeasurement()).append("'");
				query.append(")");
				factory.deleteInsertUpdate(query.toString(), connection);
				// keeping track of scanned bins
				if (count > 0) binIdsScannedList += ",";
				binIdsScannedList += bean.getBinId();
				count++;
			}
			// pkg_work_area_bin_count.p_copy_stage_to_count (upload_sequence);
			Vector<BigDecimal> args = new Vector<BigDecimal>(1);
			args.add(uploadSeq);
			Vector<Integer> outArgs = new Vector<Integer>(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Vector error = (Vector) factory.doProcedure(connection, "pkg_work_area_bin_count.p_copy_stage_to_count", args, outArgs);

			if (error.get(0) == null) {
				// Create MRs
				String errorMessage = "";
				HashMap<String, Comparable> createMrResult = new HashMap<String, Comparable>();
				Boolean createMrSuccess = new Boolean(false);
				try {
					// replenishment MRs for receipts
					if (countbyReceipt) {
						createMrResult = createMaterialRequestForReceiptCount(uploadSeq, companyId, personnelId);
						createMrSuccess = (Boolean) createMrResult.get("SUCESS");
						if (!createMrSuccess.booleanValue()) {
							errorMessage += (String) createMrResult.get("ERROR");
						}
						mrsCreated += (String) createMrResult.get("CREATEDMRLIST");
					}

					// Handle Haas owned cabinets, usage data and count cleanup
					processCount(connection, uploadSeq);
					
					// replenishment MRs for containers
					if (countByContainerOrAdvancedReceipt) {
						createMrResult = createMRForContainerAndAdvancedReceiptCount(uploadSeq, companyId, personnelId);
						createMrSuccess = (Boolean) createMrResult.get("SUCESS");
						if (!createMrSuccess.booleanValue()) {
							errorMessage += (String) createMrResult.get("ERROR");
						}
						mrsCreated += (String) createMrResult.get("CREATEDMRLIST");
					}
				}
				catch (Exception ex2) {
					errorMessage += ex2.getMessage();
				}

				if (!StringHandler.isBlankString(errorMessage)) {
					sendMRErrorEmail("Error while creating MR's for scanned/counted data ", "Error from Updating after creating MR's\n\n" + errorMessage + "\n\n uploadsequence : " + uploadSeq + "\n\nPersonnel ID:   " + personnelId + "\nBinIdList:"
							+ binIdsScannedList);
				}
			}
			else {
				log.debug("*" + error.toString() + "*");
				MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "CabinetCountProcess Error encountered while calling pkg_work_area_bin_count.p_copy_stage_to_count:" + uploadSeq, (String) error.get(0));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.setAutoCommit(true);
			}
			catch (Exception ee) {
			}
		}

		return mrsCreated;
	} // end of method

	public HashMap<String, Comparable> createMRForContainerAndAdvancedReceiptCount(BigDecimal uploadSeq, String companyId, BigDecimal personnelId) throws Exception {
		boolean createMrSuccess = true;
		String errorMessage = "";

		CabinetMaterialRequestFactory cabinetMrFactory = new CabinetMaterialRequestFactory(dbManager);
		factory.setBeanObject(new CabinetMrCreateViewBean());
		Collection resultColl = null;
		// StringBuilder query = new
		// StringBuilder("select * from WORK_AREA_BIN_MR_CREATE_VIEW a where a.company_id = '").append(companyId).append("'");
		// query.append(" and exists (select null from work_area_bin_count where date_processed is null and bin_id = a.bin_id and upload_sequence = ").append(nextupLoadSeq).append(")");

		//		StringBuilder query = new StringBuilder("SELECT   /*+ LEADING(B) INDEX(B IX_WA_CT_UPLOAD_SEQ) */ a.* ");
		//		query.append(" FROM   WORK_AREA_BIN_MR_CREATE_VIEW a, work_area_bin_count b WHERE   b.company_id = '");
		//		query.append(companyId).append("'");
		//		query.append("and a.company_id = b.company_id and a.bin_id = b.bin_id and b.upload_sequence = ");
		//		query.append(uploadSeq).append(" and b.date_processed is null");

		StringBuilder query = new StringBuilder("select * from WORK_AREA_BIN_MR_CREATE_VIEW ");
		query.append(" where COMPANY_ID = '").append(companyId);
		query.append("' and BIN_ID in (select bin_id from WORK_AREA_BIN_COUNT WHERE PROCESSING_STATUS = 'PROCESSED' and UPLOAD_SEQUENCE = ");
		query.append(uploadSeq).append(")");

		String createdMrList = "";
		int count = 0;

		@SuppressWarnings("unchecked")
		Collection<CabinetMrCreateViewBean> mrBeans = factory.selectQuery(query.toString(), connection);
		for (CabinetMrCreateViewBean bean : mrBeans) {
			try {
				resultColl = cabinetMrFactory.createMaterialRequest(connection, bean, personnelId, "Cabinet Scan Replenishment");
			}
			catch (Exception ex4) {
				createMrSuccess = false;
				ex4.printStackTrace();
				errorMessage += ex4.getMessage();
			}

			if (resultColl != null) {
				Iterator resultIterator = resultColl.iterator();
				int resultCount = 0;
				BigDecimal prNumber = null;
				String lineItem = "";
				String errorCode = "";
				while (resultIterator.hasNext()) {
					if (resultCount == 0) {
						prNumber = (BigDecimal) resultIterator.next();
					}
					else if (resultCount == 1) {
						lineItem = (String) resultIterator.next();
					}
					else if (resultCount == 2) {
						errorCode = (String) resultIterator.next();
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
					log.info("MR is created " + prNumber + "-" + lineItem + "");
					if (count > 0) {
						createdMrList += ",";
					}

					createdMrList += "" + prNumber + "";
					count++;
					lineItemAllocate(bean.getCompanyId(), prNumber, lineItem);
				}
			}
		}

		HashMap<String, Comparable> result = new HashMap<String, Comparable>();
		result.put("CREATEDMRLIST", createdMrList);
		result.put("ERROR", errorMessage);
		result.put("SUCESS", new Boolean(createMrSuccess));
		return result;
	}

} // end of class
