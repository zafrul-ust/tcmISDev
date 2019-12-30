package com.tcmis.client.samsung.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.samsung.beans.OpenCustomerPOBean;
import com.tcmis.client.samsung.beans.OrderFulfillmentBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class OrderFulfillmentProcess extends GenericProcess {

	private ResourceLibrary				library							= null;
	private GenericSqlFactory			genericSqlFactory				= null;
	private DbManager						dbManager						= null;
	private Connection					connection						= null;
	private ShoppingCartProcess		shoppingCartProcess			= null;
	private MaterialRequestProcess	materialRequestProcess		= null;
	private MaterialRequestInputBean	materialRequestInputBean	= null;
	private PersonnelBean				personnelBean					= null;
	// if Samsung send us the same file then ignore the whole file and delete it
	// default to "true" and if any records is good then file is good.
	private boolean						wholeFileIsDuplicated		= true;

	public OrderFulfillmentProcess(String client, boolean withLibraryAndFactory) {
		super(client);
		if (withLibraryAndFactory) {
			library = new ResourceLibrary("samsungorderfulfillment");
			dbManager = new DbManager(getClient(), this.getLocale());
			genericSqlFactory = new GenericSqlFactory(dbManager);
		}
	} // end of method

	public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

	public void dailyInventoryCheck(String companyId) throws Exception {
		try {
			connection = dbManager.getConnection();
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
			criteria.addCriterion("allocateAfter", SearchCriterion.IS_DATE, new DateTime().withTimeAtStartOfDay().toDate());
			criteria.addCriterion("releaseDate", SearchCriterion.IS_NOT, null);
			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("prNumber");
			sortCriteria.addCriterion("lineItem");
			genericSqlFactory.setBeanObject(new ShoppingCartBean());
			Iterator iter = genericSqlFactory.select(criteria, sortCriteria, connection, "request_line_item").iterator();
			Hashtable emsg = new Hashtable();
			while (iter.hasNext()) {
				ShoppingCartBean bean = (ShoppingCartBean) iter.next();
				StringBuilder internalNote = new StringBuilder("");
				StringBuilder msg = new StringBuilder("");
				if (notEnoughInventoryForMrLine(bean.getPrNumber(), new BigDecimal(bean.getLineItem()), bean.getQuantity(), msg, internalNote, false)) {
					updateMrLineStatus(bean.getPrNumber(), new BigDecimal(bean.getLineItem()), "Pending Use Approval", "Open", internalNote.toString(), null);
					// organize message by pr_number
					if (emsg.containsKey(bean.getPrNumber())) {
						StringBuilder tmpMsg = (StringBuilder) emsg.get(bean.getPrNumber());
						tmpMsg.append("\n\n").append(bean.getPrNumber().toString()).append("-").append(bean.getLineItem()).append("\n");
						tmpMsg.append(msg);
					}
					else {
						StringBuilder tmpMsg = new StringBuilder(bean.getPrNumber().toString()).append("-").append(bean.getLineItem()).append("\n");
						tmpMsg.append(msg);
						emsg.put(bean.getPrNumber(), tmpMsg);
					}
				}
			}
			// since there is not enough inventory for one of the line on the MR, the whole MR has to send to Pending status and unallocate
			if (!emsg.isEmpty()) {
				// clear MR release date and unallocate
				Enumeration erun = emsg.keys();
				while (erun.hasMoreElements()) {
					BigDecimal prNumber = (BigDecimal) erun.nextElement();
					clearMrReleaseDate(prNumber);
					// unallocate MR
					unallocateMr(prNumber);
				}
				// send email notification
				sendNotification("Samsung Wesco Order", emsg, "Austin");
			}
		}
		catch (Exception e) {
			log.error("Error while running daily inventory check", e);
			throw e;
		}
		finally {
			dbManager.returnConnection(connection);
		}
	} // end of method

	private void clearMrReleaseDate(BigDecimal prNumber) throws Exception {
		try {
			StringBuilder query = new StringBuilder("update request_line_item set release_date = null");
			query.append(",last_updated = sysdate,last_updated_by = -1");
			query.append(" where pr_number = ").append(prNumber).append(" and release_date is not null");
			genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
		}
		catch (Exception e) {
			log.error("Error while running daily inventory check - send MR to Pending status", e);
			throw e;
		}
	} // end of method

	private void unallocateMr(BigDecimal prNumber) throws Exception {
		try {
			Collection inArgs = new Vector(1);
			inArgs.add(prNumber);
			genericSqlFactory.doProcedure(connection, "p_delete_allocation", inArgs);
		}
		catch (Exception dbe) {
			log.error("Error while running daily inventory check - unallocate MR:" + prNumber, dbe);
			throw dbe;
		}
	} // end of method

	public void processData() throws Exception {
		String localFilePath = library.getString("inbound.local.ftp.dir");
		String remoteFilePath = library.getString("inbound.processed.ftp.dir");
		try {
			if (!localFilePath.endsWith(File.separator)) {
				localFilePath += File.separator;
			}
			if (!remoteFilePath.endsWith(File.separator)) {
				remoteFilePath += File.separator;
			}
			// get unprocessed files
			File inboundFileDir = new File(localFilePath);
			String[] fileNameList = inboundFileDir.list();
			String emailSubject = "Samsung Wesco Order";
			String emailType = "Both";
			for (String inboundFile : inboundFileDir.list()) {
				// reset this variable for each file
				wholeFileIsDuplicated = true;
				Hashtable emsg = new Hashtable();
				if (inboundFile.startsWith("SAS_")) {
					emailSubject = "Samsung Wesco Order";
					emailType = "Both";
					// create MRs from file
					Hashtable orderDataH = readFile(localFilePath, inboundFile);
					Enumeration erun = orderDataH.keys();
					// create MRs from data
					connection = dbManager.getConnection();
					Vector mrWithError = new Vector();
					boolean firstTime = true;
					// the data was sorted by reservation number. Each reservation number is an Material Request
					while (erun.hasMoreElements()) {
						BigDecimal prNumber = null;
						int lineCount = 0;
						Iterator dataIter = ((Collection) orderDataH.get(erun.nextElement())).iterator();
						while (dataIter.hasNext()) {
							OrderFulfillmentBean bean = (OrderFulfillmentBean) dataIter.next();
							// only create MR for records with storage_loc = RC10 and it has to have reservation #
							if ("RC10".equals(bean.getStorageLoc()) && !StringHandler.isBlankString(bean.getReceiptDocNo()) && !isDuplicated(bean, emsg)) {
								BigDecimal lineItem = new BigDecimal(++lineCount);
								if (firstTime) {
									// create new processes and beans
									shoppingCartProcess = new ShoppingCartProcess(this.getClient(), this.getLocale());
									shoppingCartProcess.setFactoryConnection(genericSqlFactory, connection);
									shoppingCartProcess.setReturnConnection(false);
									materialRequestProcess = new MaterialRequestProcess(this.getClient(), this.getLocale());
									materialRequestProcess.setFactoryConnection(genericSqlFactory, connection);
									materialRequestInputBean = new MaterialRequestInputBean();
									personnelBean = new PersonnelBean();
									firstTime = false;
								}
								prNumber = createMrFromData(bean, prNumber, lineItem);
								// check specific data points (create method so it can be use by this process and new error page)(by mr-line)
								if (prNumber != null) {
									// there is a good record in file
									wholeFileIsDuplicated = false;
									// if all data are good then submit MR && allocate (create method so it can be use by this process and new error page)(by mr-line)
									StringBuilder internalNote = new StringBuilder("");
									if (validateMrData(prNumber, lineItem, emsg, internalNote)) {
										updateMrLineStatus(prNumber, lineItem, "In Progress", "Open", internalNote.toString(), null);
									}
									else {
										updateMrLineStatus(prNumber, lineItem, "Pending Use Approval", "Open", internalNote.toString(), null);
										if (!mrWithError.contains(prNumber))
											mrWithError.add(prNumber);
									}
								}
							} // end of create MR for valid lines
						} // end of looping thru each line
							 // release all good lines and allocate
						if (prNumber != null) {
							updateMrStatus(prNumber, "posubmit", null);
							// all lines are good then allocate MR. Otherwise MR-line has correct status but will not release until all lines are good to go
							if (!mrWithError.contains(prNumber)) {
								materialRequestInputBean.setPrNumber(prNumber);
								personnelBean.setPersonnelId(-1);
								materialRequestProcess.releaseMrToHaas(materialRequestInputBean, personnelBean);
							}
						}
					} // end of looping thru file
				} // end of files start with SAS_
				else if (inboundFile.startsWith("WESCO_OPEN_PO_")) {
					emailSubject = "Samsung Open PO import to Wesco from file " + inboundFile;
					emailType = "OpenPO";

					DbManager tcmDbMgr = new DbManager("TCM_OPS");
					Connection tcmopsConn = tcmDbMgr.getConnection();
					int successfulImports = 0;
					List<String> errors = new Vector<String>();
					List<String> notInCatalog = new Vector<String>();
					List<String> duplicates = new Vector<String>();
					try {
						CSV csvParser = new CSV(true, ',', new FileReader(localFilePath + inboundFile));
						while (csvParser.hasNext()) {
							List<String> line = csvParser.next();
							String firstCol = line.get(0);
							if (StringUtils.isBlank(firstCol) || firstCol.startsWith("Material")) {
								// Ignore header line and blank lines
							}
							else {
								OpenCustomerPOBean currentPO = new OpenCustomerPOBean(line);
								// Check whether this is for an item in the catalog
								String itemId = getItemIdforOpenCustomerPO(currentPO, tcmopsConn);
								if (StringUtils.isNotBlank(itemId)) {
									if (!customerPoExistsInCITR(currentPO, tcmopsConn)) {
										wholeFileIsDuplicated = false;
										String error = insertPoIntoCITR(currentPO, itemId, inboundFile, tcmopsConn);
										if (error.length() < 1) {
											successfulImports++;
										}
										else {
											errors.add(error);
										}
									}
									else {
										duplicates.add(currentPO.getDescription());
									}
								}
								else {
									notInCatalog.add(currentPO.getDescription());
								}
							}
						}
					}
					finally {
						tcmDbMgr.returnConnection(tcmopsConn);
					}

					StringBuilder msg = new StringBuilder();
					if (successfulImports > 0) {
						msg.append("Successfully imported ").append(successfulImports).append(" Open Customer POs from " + inboundFile + "\n");
						msg.append("\n");
					}

					if (!errors.isEmpty()) {
						msg.append("The following " + errors.size() + " Open POs were NOT imported due to errors: \n");
						for (String error : errors) {
							msg.append("\t").append(error).append("\n");
						}
					}
					if (!notInCatalog.isEmpty()) {
						msg.append("The following " + notInCatalog.size() + " Open POs were for materials that do not exist in Samsung's tcmIS catalog: \n");
						for (String po : notInCatalog) {
							msg.append("\t").append(po).append("\n");
						}
					}
					if (!duplicates.isEmpty()) {
						msg.append("The following " + duplicates.size() + " Open POs already exist in tcmIS: \n");
						for (String po : duplicates) {
							msg.append("\t").append(po).append("\n");
						}
					}
					emsg.put(inboundFile, msg);
				}// end of files start with WESCO_OPEN_PO_

				if (wholeFileIsDuplicated) {
					// delete duplicate file because Samsung does not know how to stop sending duplicate at there end
					File duplicateFile = new File(localFilePath + inboundFile);
					log.debug("Duplicate file uploaded, deleting");
					duplicateFile.delete();
				}
				else {
					// move file to processed directory
					String saveDirectory = remoteFilePath + new SimpleDateFormat("yyyy/MM/").format(new Date());
					File dir = new File(saveDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					FileHandler.move(localFilePath + inboundFile, saveDirectory + inboundFile);
					// send out email notification (create method so it can be use by this process and new error page)(by sendto, subject, body)
					sendNotification(emailSubject, emsg, emailType);
				}

			} // end of looping thru each unprocessed files
		}
		catch (Exception e) {
			log.error("Error loading Samsung data", e);
			throw e;
		}
		finally {
			dbManager.returnConnection(connection);
		}
	} // end of method

	public void updateMrLineStatus(BigDecimal prNumber, BigDecimal lineItem, String requestLineItemStatus, String categoryStatus, String internalNote, BigDecimal personnelId) throws Exception {
		try {
			// update request_line_item status
			StringBuilder query = new StringBuilder("update request_line_item set request_line_status = '").append(requestLineItemStatus).append("'");
			query.append(", category_status = '").append(categoryStatus).append("'");
			if (requestLineItemStatus.startsWith("Pending"))
				query.append(", release_date = null");
			if (!StringHandler.isBlankString(internalNote))
				query.append(", internal_note = '").append(internalNote).append("'");
			else
				query.append(", internal_note = null");
			if (personnelId != null)
				query.append(",last_updated = sysdate,last_updated_by = ").append(personnelId);
			query.append(" where pr_number = ").append(prNumber);
			if (lineItem != null)
				query.append(" and line_item = ").append(lineItem);
			genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
		}
		catch (Exception e) {
			log.error("Error while updating MR line status", e);
			throw e;
		}
	} // end of method

	private void updateMrStatus(BigDecimal prNumber, String prStatus, BigDecimal personnelId) throws Exception {
		try {
			// update purchase request status
			StringBuilder query = new StringBuilder("update purchase_request").append(" set pr_status = '").append(prStatus).append("'");
			if (personnelId == null)
				query.append(",submitted_date = sysdate,submitted_by = -1");
			else
				query.append(",last_updated = sysdate,last_updated_by = ").append(personnelId);
			query.append(" where pr_number = ").append(prNumber);
			genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
		}
		catch (Exception e) {
			log.error("Error while updating MR status", e);
			throw e;
		}
	} // end of method

	public boolean validateMrData(BigDecimal prNumber, BigDecimal lineItem, Hashtable emsg, StringBuilder internalNote) throws Exception {
		boolean hasError = false;
		try {
			// first get MR details so I can build email message
			StringBuilder query = new StringBuilder("select nvl(rli.customer_requisition_number,'XYZ') customer_requisition_number,rli.fac_part_no cat_part_no,rli.quantity");
			query.append(",fla.company_application work_area,rli.allocate_by_mfg_lot,rli.required_datetime date_needed,rli.notes");
			query.append(" from request_line_item rli, fac_loc_app fla where rli.pr_number = ").append(prNumber).append(" and rli.line_item = ").append(lineItem);
			query.append(" and rli.company_id = fla.company_id and rli.facility_id = fla.facility_id and rli.application = fla.application");
			ShoppingCartBean bean = new ShoppingCartBean();
			genericSqlFactory.setBeanObject(bean);
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			while (iter.hasNext()) {
				bean = (ShoppingCartBean) iter.next();
				break;
			}

			String tmpWorkArea = bean.getWorkArea();
			if ("Plantwide".equals(bean.getWorkArea())) {
				// I stored Samsung work area in notes field so we can report back to Samsung that they placed an order for a work area that is not in tcmIS
				tmpWorkArea = bean.getNotes().split(";")[0];
			}

			StringBuilder msg = new StringBuilder("Reservation No :\t\t\t").append(bean.getCustomerRequisitionNumber()).append("\n");
			msg.append("From S.Loc :\t\t\t\t").append("RC10").append("\n");
			msg.append("To S.Loc :\t\t\t\t").append(tmpWorkArea).append("\n");
			msg.append("Part No :\t\t\t\t").append(bean.getCatPartNo()).append("\n");
			msg.append("Quantity :\t\t\t\t").append(bean.getQuantity()).append("\n");
			msg.append("Lot No :\t\t\t\t").append(bean.getAllocateByMfgLot()).append("\n");
			SimpleDateFormat prefixFmt = new SimpleDateFormat("MMM-dd-yyyy");
			String tmpDate = prefixFmt.format(bean.getDateNeeded());
			msg.append("Delivery Date :\t\t\t\t").append(tmpDate).append("\n");
			msg.append("tcmIS MR-line :\t\t\t\t").append(prNumber).append("-").append(lineItem).append("\n");
			// check to see if part exists in catalog
			query = new StringBuilder("select count(*) from request_line_item rli, cat_part_view cpv ");
			query.append(" where rli.company_id = cpv.company_id and rli.catalog_company_id = cpv.catalog_company_id");
			query.append(" and rli.catalog_id = cpv.catalog_id and rli.fac_part_no = cpv.cat_part_no and rli.part_group_no = cpv.part_group_no");
			query.append(" and rli.inventory_group = cpv.inventory_group and rli.pr_number = ").append(prNumber).append(" and rli.line_item = ").append(lineItem);
			if (dataCountIsZero(query.toString())) {
				// report this MR-line as error
				if (hasError)
					msg.append("\n");
				msg.append("\tAbove Part No does not exist in tcmIS.");
				internalNote.append("Part No does not exist in tcmIS; ");
				hasError = true;
			}
			// check to see if work area exists in system
			if ("Plantwide".equals(bean.getWorkArea())) {
				// report this MR-line as error
				if (hasError)
					msg.append("\n");
				msg.append("\tAbove To S.Loc does not exist in tcmIS.");
				internalNote.append(tmpWorkArea).append(" does not exist in tcmIS; ");
				hasError = true;
			}
			// check to see if need date is valid
			query = new StringBuilder("select count(*) from request_line_item where to_char(required_datetime,'MM/dd/yyyy') >= to_char(sysdate,'MM/dd/yyyy')");
			query.append(" and pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
			if (dataCountIsZero(query.toString())) {
				// report this MR-line as error
				if (hasError)
					msg.append("\n");
				msg.append("\tAbove Delivery Date is in the past.");
				internalNote.append("Delivery Date is in the past; ");
				hasError = true;
			}
			// this line has to have the same date as line_item = 1
			if (lineItem.intValue() > 1) {
				query = new StringBuilder("select count(*) from request_line_item a, request_line_item b");
				query.append(" where a.company_id = b.company_id and a.pr_number = b.pr_number");
				query.append(" and a.pr_number = ").append(prNumber).append(" and a.line_item = 1");
				query.append(" and b.line_item = ").append(lineItem);
				query.append(" and a.required_datetime <> b.required_datetime");
				if (!dataCountIsZero(query.toString())) {
					// report this MR-line as error
					if (hasError)
						msg.append("\n");
					msg.append("\tAbove Delivery Date is different than previous line(s).");
					internalNote.append("Delivery Date is different than previous line(s); ");
					hasError = true;
				}
			}
			// have enough inventory to cover order
			hasError = notEnoughInventoryForMrLine(prNumber, lineItem, bean.getQuantity(), msg, internalNote, hasError);
			if (!hasError)
				msg.append("\tSuccessfully received and processed");
			// organize message by reservation no
			if (emsg.containsKey(bean.getCustomerRequisitionNumber())) {
				StringBuilder tmpMsg = (StringBuilder) emsg.get(bean.getCustomerRequisitionNumber());
				tmpMsg.append("\n\n").append(msg);
			}
			else
				emsg.put(bean.getCustomerRequisitionNumber(), msg);
		}
		catch (Exception e) {
			log.error("Error while validating MR data", e);
			throw e;
		}
		return !hasError;
	} // end of method

	private boolean notEnoughInventoryForMrLine(BigDecimal prNumber, BigDecimal lineItem, BigDecimal orderedQty, StringBuilder msg, StringBuilder internalNote, boolean hasError) {
		boolean result = hasError;
		try {
			StringBuilder query = new StringBuilder("select sum(x.quantity) qty from table(fx_alloc_qty_available(");
			query.append(prNumber).append(",").append(lineItem).append(",null,sysdate-1)) x").append(" where doc_type = 'OV'");
			String tmpQty = genericSqlFactory.selectSingleValue(query.toString(), connection);
			if (!StringHandler.isBlankString(tmpQty)) {
				BigDecimal aQty = new BigDecimal(tmpQty);
				if (orderedQty.floatValue() > aQty.floatValue()) {
					// have partial inventory
					// report this MR-line as error
					if (hasError)
						msg.append("\n");
					msg.append("\ttcmIS does not have enough inventory to fulfill this order.  Current inventory: ").append(tmpQty);
					internalNote.append("tcmIS does not have enough inventory to fulfill this order.  Current inventory: ").append(tmpQty).append("; ");
					result = true;
				}
			}
			else {
				// have not inventory
				// report this MR-line as error
				if (hasError)
					msg.append("\n");
				msg.append("\ttcmIS does not have enough inventory to fulfill this order.  Current inventory: 0");
				internalNote.append("tcmIS does not have enough inventory to fulfill this order.  Current inventory: 0");
				result = true;
			}
		}
		catch (Exception e) {
			log.error("Error while validating MR data", e);
		}
		return result;
	} // end of method

	private boolean isDuplicated(OrderFulfillmentBean bean, Hashtable emsg) {
		boolean result = false;
		// duplicate order in same file
		CatalogInputBean catalogInputBean = getHeaderInfo(bean);
		String application = getApplicationFromReqLineId(bean, catalogInputBean);
		StringBuilder querySameFile = new StringBuilder("select count(*) from request_line_item where customer_requisition_number = '").append(bean.getReceiptDocNo()).append("'");
		querySameFile.append(" and fac_part_no = '").append(bean.getMatCode()).append("'");
		querySameFile.append(" and application = '").append(application).append("'");
		if (!StringHandler.isBlankString(bean.getMatLotNo()))
			querySameFile.append(" and allocate_by_mfg_lot = '").append(bean.getMatLotNo()).append("'");
		else
			querySameFile.append(" and allocate_by_mfg_lot is null");

		// reservation number show up again in different file
		StringBuilder queryDifferentFile = new StringBuilder("select count(*) from request_line_item where customer_requisition_number = '").append(bean.getReceiptDocNo()).append("'");
		queryDifferentFile.append(" and nvl(source_file_name,'X') <> '").append(bean.getFileName()).append("'");

		// if reservation exists in same file or different file
		if (!dataCountIsZero(querySameFile.toString()) || !dataCountIsZero(queryDifferentFile.toString())) {
			result = true;
			StringBuilder msg = new StringBuilder("Reservation No :\t\t\t").append(bean.getReceiptDocNo()).append("\n");
			msg.append("From S.Loc :\t\t\t\t").append("RC10").append("\n");
			msg.append("To S.Loc :\t\t\t\t").append(bean.getReqLineId()).append("\n");
			msg.append("Part No :\t\t\t\t").append(bean.getMatCode()).append("\n");
			msg.append("Quantity :\t\t\t\t").append(bean.getReqQty()).append("\n");
			msg.append("Lot No :\t\t\t\t").append(bean.getMatLotNo()).append("\n");
			msg.append("Delivery Date :\t\t\t\t").append(bean.getReqDt()).append("\n");
			msg.append("\tAbove Reservation line duplicate, order line not created in tcmIS.");
			// organize message by reservation no
			if (emsg.containsKey(bean.getReceiptDocNo())) {
				StringBuilder tmpMsg = (StringBuilder) emsg.get(bean.getReceiptDocNo());
				tmpMsg.append("\n\n").append(msg);
			}
			else
				emsg.put(bean.getReceiptDocNo(), msg);
		}
		return result;
	} // end of method

	private boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			String tmpVal = genericSqlFactory.selectSingleValue(query, connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		}
		catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	private void sendNotification(String subject, Hashtable emsg, String option) {
		StringBuilder emailMessage = new StringBuilder();
		Enumeration erun = emsg.keys();
		while (erun.hasMoreElements()) {
			emailMessage.append((StringBuilder) emsg.get(erun.nextElement())).append("\n\n");
		}
		Vector toUserEmailAddress = new Vector();
		// Wesco Austin hub users
		toUserEmailAddress.addElement("austinchemicals@haasgroupintl.com");
		String[] cc = { "deverror@tcmis.com" };
		// Samsung email address(es)
		if ("Both".equals(option) || "Samsung".equals(option)) {
			toUserEmailAddress.addElement("a1.cook@samsung.com");
			toUserEmailAddress.addElement("a1.wright@samsung.com");
			toUserEmailAddress.addElement("a1.hernandez@samsung.com");
			toUserEmailAddress.addElement("adam.andel@partner.samsung.com");
			toUserEmailAddress.addElement("b.douglas@partner.samsung.com");
			toUserEmailAddress.addElement("b.black@samsung.com");
			toUserEmailAddress.addElement("benton.a@partner.samsung.com");
			toUserEmailAddress.addElement("c.2006.dao@samsung.com");
			toUserEmailAddress.addElement("g.gibson@samsung.com");
			toUserEmailAddress.addElement("g.guajardo@samsung.com");
			toUserEmailAddress.addElement("h.corl@partner.samsung.com");
			toUserEmailAddress.addElement("h.doty@samsung.com");
			toUserEmailAddress.addElement("j.coy@samsung.com");
			toUserEmailAddress.addElement("j.neugent@samsung.com");
			toUserEmailAddress.addElement("k.hodges@partner.samsung.com");
			toUserEmailAddress.addElement("k.merka@samsung.com ");
			toUserEmailAddress.addElement("m1.davis@partner.samsung.com");
			toUserEmailAddress.addElement("mark.b1@partner.samsung.com ");
			toUserEmailAddress.addElement("Michael.ryan@partner.samsung.com");
			toUserEmailAddress.addElement("mitchell.allen@samsung.com");
			toUserEmailAddress.addElement("p.self@samsung.com ");
			toUserEmailAddress.addElement("r.davis@partner.samsung.com ");
			toUserEmailAddress.addElement("s1.wilson@partner.samsung.com");
			toUserEmailAddress.addElement("t.solis@samsung.com");
		}
		else if ("OpenPO".equals(option)) {
			toUserEmailAddress.addElement("nicholas.domenech@wescoair.com");
		}

		String[] rec = new String[toUserEmailAddress.size()];
		toUserEmailAddress.toArray(rec);
		MailProcess.sendEmail(rec, cc, subject, emailMessage.toString(), false);
	} // end of method

	private String getApplicationFromReqLineId(OrderFulfillmentBean inputBean, CatalogInputBean catalogInputBean) {
		String application = "";
		try {
			StringBuilder query = new StringBuilder("select application from fac_loc_app where company_id = '").append(catalogInputBean.getCompanyId()).append("'");
			query.append(" and facility_id = '").append(catalogInputBean.getFacilityId()).append("'");
			if ("RC12".equals(inputBean.getReqLineId())) {
				// Samsung will indicate which fab RC12 is going to in the req_desc (first part) field
				// for example a person could enter "Fab 1" as FAB1, FAB 1, fab1, fab 1, etc.
				String[] tmp = inputBean.getReqDesc().split(";");
				String tmpWorkArea = tmp[0];
				String requestWorkArea = "";
				if (tmpWorkArea.equalsIgnoreCase("fab 1") || tmpWorkArea.equalsIgnoreCase("fab1"))
					requestWorkArea = "Fab 1";
				if (tmpWorkArea.equalsIgnoreCase("fab 2") || tmpWorkArea.equalsIgnoreCase("fab2"))
					requestWorkArea = "Fab 2";
				query.append(" and company_application = '").append(SqlHandler.validQuery(inputBean.getReqLineId())).append(" ").append(requestWorkArea).append("'");
			}
			else
				query.append(" and company_application = '").append(SqlHandler.validQuery(inputBean.getReqLineId())).append("'");
			application = genericSqlFactory.selectSingleValue(query.toString(), connection);
			if (StringHandler.isBlankString(application))
				application = "Plantwide";
		}
		catch (Exception e) {
			e.printStackTrace();
			application = "Plantwide";
		}
		return application;
	} // end of method

	private CatalogInputBean getHeaderInfo(OrderFulfillmentBean inputBean) {
		CatalogInputBean catalogInputBean = new CatalogInputBean();
		try {
			StringBuilder query = new StringBuilder("select pr.company_id,pr.facility_id,pr.account_sys_id,pr.charge_type,cf.catalog_company_id,cf.catalog_id");
			query.append(",pr.po_required,pr.pr_account_required");
			query.append(" from pr_rules pr, facility f, catalog_facility cf");
			query.append(" where pr.company_id = f.company_id and pr.facility_id = f.facility_id and pr.status = 'A' and pr.charge_type = 'd'");
			query.append(" and 'P'||f.company_facility_id = ").append(SqlHandler.delimitString(inputBean.getPlant()));
			query.append(" and f.company_id = cf.company_id and f.facility_id = cf.facility_id and cf.display = 'Y' and cf.mr_create_from_catalog = 'Y'");
			genericSqlFactory.setBeanObject(catalogInputBean);
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			while (iter.hasNext()) {
				catalogInputBean = (CatalogInputBean) iter.next();
				break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return catalogInputBean;
	} // end of method

	private BigDecimal createMrFromData(OrderFulfillmentBean inputBean, BigDecimal prNumber, BigDecimal lineItem) throws Exception {
		try {
			// get header info
			CatalogInputBean catalogInputBean = getHeaderInfo(inputBean);
			// get line info
			// mapping Samsung data into our
			ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
			String application = getApplicationFromReqLineId(inputBean, catalogInputBean);
			boolean partExisted = false;
			StringBuilder query = new StringBuilder("select a.company_id,a.catalog_company_id,a.catalog_id,a.cat_part_no,a.part_group_no,a.stocked stocking_method,");
			query.append(" a.inventory_group,a.catalog_price,a.unit_price base_line_price,b.packaging example_packaging,");
			query.append(" fla.facility_id, fla.application");
			query.append(" from cat_part_view a, catalog_item_view b, fac_loc_app fla");
			query.append(" where a.item_id = b.item_id and a.company_id = '").append(catalogInputBean.getCompanyId()).append("'");
			query.append(" and a.catalog_company_id = '").append(catalogInputBean.getCatalogCompanyId()).append("' and a.catalog_id = '").append(catalogInputBean.getCatalogId()).append("'");
			query.append(" and a.cat_part_no = ").append(SqlHandler.delimitString(inputBean.getMatCode())).append(" and a.inventory_group = fla.inventory_group");
			query.append(" and fla.company_id = a.company_id and fla.facility_id = '").append(catalogInputBean.getFacilityId()).append("'");
			query.append(" and fla.application = '").append(application).append("'");
			genericSqlFactory.setBeanObject(shoppingCartBean);
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			while (iter.hasNext()) {
				shoppingCartBean = (ShoppingCartBean) iter.next();
				partExisted = true;
				break;
			}
			shoppingCartBean.setPrNumber(prNumber);
			// if part does not match then use the following as default value so we can create a MR and CSR will manually talk with Samsung to get error(s) corrected
			if (!partExisted) {
				shoppingCartBean.setCatalogCompanyId(catalogInputBean.getCatalogCompanyId());
				shoppingCartBean.setCatalogId(catalogInputBean.getCatalogId());
				shoppingCartBean.setCatPartNo(inputBean.getMatCode());
				shoppingCartBean.setPartGroupNo(new BigDecimal(1));
				shoppingCartBean.setFacilityId(catalogInputBean.getFacilityId());
				shoppingCartBean.setApplication(application);
				shoppingCartBean.setInventoryGroup("Austin Samsung");
				shoppingCartBean.setStockingMethod("MM");
			}
			// continue with Samsun specific requirements
			shoppingCartBean.setChargeType(catalogInputBean.getChargeType());
			shoppingCartBean.setQuantity(new BigDecimal(inputBean.getReqQty()));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date needDate = (Date) formatter.parse(inputBean.getReqDt());
			shoppingCartBean.setDateNeeded(needDate);
			shoppingCartBean.setAllocateByMfgLot(inputBean.getMatLotNo());
			// if request date is not today or tomorrow then do not allocate until need date
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, 24);
			if (needDate.getTime() > calendar.getTime().getTime()) {
				if (!StringHandler.isBlankString(inputBean.getReqDesc())) {
					// if the request is for 1st truck then the allocate_after needs to be a day before need date
					if (inputBean.getReqDesc().contains("1st Truck")) {
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(needDate.getTime());
						cal.add(Calendar.HOUR, -24);
						needDate = cal.getTime();
					}
				}
				shoppingCartBean.setAllocateAfter(needDate);
			}
			shoppingCartBean.setAllOrNone("Y");
			// this is because Samsung send us a work area that does not currently exists in our system
			// I am saving it use later in reporting it back to Samsung
			if ("Plantwide".equals(application))
				shoppingCartBean.setNotes(inputBean.getReqLineId() + ";" + inputBean.getReqDesc());
			else
				shoppingCartBean.setNotes(inputBean.getReqDesc());
			shoppingCartBean.setCustomerRequisitionNumber(inputBean.getReceiptDocNo());
			shoppingCartBean.setSourceFileName(inputBean.getFileName());
			// put it all together
			Collection shoppingColl = new Vector(1);
			shoppingColl.add(shoppingCartBean);
			// create MR
			prNumber = shoppingCartProcess.buildRequest(catalogInputBean, shoppingColl, new BigDecimal(-1));

			// update the missing data fields
			query = new StringBuilder("update request_line_item set relax_shelf_life = 'y'");
			if ("p".equals(catalogInputBean.getPoRequired())) {
				query.append(",po_number = (select max(po_number)").append(getDirectedChargeQueryString(catalogInputBean, shoppingCartBean));
				query.append(")");
			}
			query.append(" where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
			genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
			if ("y".equals(catalogInputBean.getPrAccountRequired())) {
				query = new StringBuilder("insert into pr_account (pr_number, line_item, account_number, percentage, account_number2, company_id, account_number3, account_number4)");
				query.append(" select ").append(prNumber).append(",").append(lineItem);
				query.append(",charge_number_1, percent, charge_number_2, company_id, charge_number_3, charge_number_4");
				query.append(getDirectedChargeQueryString(catalogInputBean, shoppingCartBean));
				genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
			}
		}
		catch (Exception e) {
			log.error("Error while try to create MR from data", e);
			throw e;
		}
		return prNumber;
	} // end of method

	private StringBuilder getDirectedChargeQueryString(CatalogInputBean catalogInputBean, ShoppingCartBean shoppingCartBean) {
		StringBuilder query = new StringBuilder(" from table (pkg_directed_charge_util.fx_get_directed_charges('").append(catalogInputBean.getCompanyId()).append("'");
		query.append(",'").append(catalogInputBean.getFacilityId()).append("','").append(shoppingCartBean.getApplication()).append("','").append(shoppingCartBean.getApplication()).append("'");
		query.append(",'").append(catalogInputBean.getAccountSysId()).append("','").append(catalogInputBean.getChargeType()).append("','Material'");
		query.append(",'").append(shoppingCartBean.getCatalogCompanyId()).append("','").append(shoppingCartBean.getCatalogId()).append("'");
		query.append(",'").append(shoppingCartBean.getCatPartNo()).append("',").append(shoppingCartBean.getPartGroupNo()).append("))");
		return query;
	} // end of method

	// read data from file and organize it by reservation number
	private Hashtable readFile(String localFilePath, String fileName) throws Exception {
		Hashtable orderDataH = new Hashtable();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(localFilePath + fileName));
			if (bufferedReader != null) {
				String line = null;
				Calendar calendar = Calendar.getInstance();
				String tmpTodayDate = calendar.get((Calendar.YEAR)) + "";
				int tmpMonth = calendar.get((Calendar.MONTH)) + 1;
				if (tmpMonth < 10)
					tmpTodayDate += "0" + tmpMonth + "";
				else
					tmpTodayDate += tmpMonth + "";
				tmpTodayDate += calendar.get((Calendar.DATE)) + "";

				while ((line = bufferedReader.readLine()) != null) {
					OrderFulfillmentBean bean = new OrderFulfillmentBean();
					String[] lineData = line.split(",");
					// skip header row
					if ("ACT_DT".equals(lineData[0]))
						continue;
					// start loading data
					int dataIndex = 0;
					bean.setActDt(lineData[dataIndex++].trim());
					bean.setReqYear(lineData[dataIndex++].trim());
					bean.setPlant(lineData[dataIndex++].trim());
					bean.setStorageLoc(lineData[dataIndex++].trim());
					bean.setMatCode(lineData[dataIndex++].trim());
					bean.setValueType(lineData[dataIndex++].trim());
					bean.setReqLineId(lineData[dataIndex++].trim());
					String tmpRequestedDate = lineData[dataIndex++].trim();
					if (StringHandler.isBlankString(tmpRequestedDate))
						tmpRequestedDate = tmpTodayDate;
					bean.setReqDt(tmpRequestedDate);
					bean.setReqQty(lineData[dataIndex++].trim());
					bean.setReqUom(lineData[dataIndex++].trim());
					bean.setReceiptDocNo(lineData[dataIndex++].trim());
					bean.setLotItemNo1(lineData[dataIndex++].trim());
					bean.setReqDesc(lineData[dataIndex++].trim());
					bean.setMatLotNo(lineData[dataIndex++].trim());
					bean.setStockType(lineData[dataIndex++].trim());
					bean.setMatCylinNo(lineData[dataIndex++].trim());
					bean.setSpecialTypeRet(lineData[dataIndex++].trim());
					bean.setCreateDate(lineData[dataIndex++].trim());
					bean.setFileName(fileName);
					// put all line with same reservation number together
					if (orderDataH.containsKey(bean.getReceiptDocNo())) {
						Collection orderDataColl = (Collection) orderDataH.get(bean.getReceiptDocNo());
						// merge quantity for special stock and regular lines together
						if (!mergedSpecialStockQty(orderDataColl, bean))
							orderDataColl.add(bean);
					}
					else {
						Collection orderDataColl = new Vector();
						orderDataColl.add(bean);
						orderDataH.put(bean.getReceiptDocNo(), orderDataColl);
					}
				}
				bufferedReader.close();
			}
		}
		catch (Exception e) {
			throw e;
		}
		return orderDataH;
	} // end of method

	private boolean mergedSpecialStockQty(Collection orderDataColl, OrderFulfillmentBean currentBean) {
		boolean result = false;
		Iterator iter = orderDataColl.iterator();
		while (iter.hasNext()) {
			OrderFulfillmentBean orderBean = (OrderFulfillmentBean) iter.next();
			// merge quantity only if reservation line is the same and if current previous or current line has special type marked
			if (orderBean.getLotItemNo1().equals(currentBean.getLotItemNo1()) && orderBean.getMatLotNo().equals(currentBean.getMatLotNo()) &&
					(!StringHandler.isBlankString(orderBean.getSpecialTypeRet()) || !StringHandler.isBlankString(currentBean.getSpecialTypeRet()))) {
				BigDecimal orderQty = new BigDecimal(orderBean.getReqQty()).add(new BigDecimal(currentBean.getReqQty()));
				orderBean.setReqQty(orderQty.toString());
				result = true;
				break;
			}
		}
		return result;
	} // end of method

	private String getItemIdforOpenCustomerPO(OpenCustomerPOBean po, Connection conn) {
		try {
			return factory.selectSingleValue("SELECT item_id FROM catalog_part_item_group WHERE company_id = 'SAMSUNG' AND catalog_id = 'Austin' AND status = 'A' AND part_group_no = 1 AND cat_part_no = " + SqlHandler.delimitString(po.getMaterial()), conn);
		}
		catch (BaseException e) {
			log.error("Error retrieving item_id for SAMSUN Open PO", e);
		}
		return null;
	}

	private boolean customerPoExistsInCITR(OpenCustomerPOBean po, Connection conn) {
		String query = "SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT NULL FROM customer_inventory_to_receive " +
				"WHERE owner_company_id = 'SAMSUNG' AND inventory_group = 'Austin Samsung' AND po_number = " + SqlHandler.delimitString(po.getGeneratedPoNumber()) + ")";
		try {

			return "Y".equals(factory.selectSingleValue(query, conn));
		}
		catch (BaseException e) {
			log.error("Error checking to see if Samsung Open PO exists in CITR", e);
		}
		return false;
	}

	private String insertPoIntoCITR(OpenCustomerPOBean po, String itemId, String fileName, Connection conn) {
		String msg = "";
		String insert = "INSERT INTO customer_inventory_to_receive (" +
				"	owner_company_id," +
				"	po_number," +
				"	quantity_to_receive," +
				"	catalog_company_id," +
				"	catalog_id," +
				"	cat_part_no," +
				"	date_inserted," +
				"	expected_delivery_date," +
				"	inventory_group," +
				"	total_quantity_received," +
				"	item_id," +
				"	customer_po_no," +
				"	customer_po_line," +
				"	doc_num," +
				"	part_group_no," +
				"	status," +
				"	notes," +
				"	date_last_updated," +
				"	last_updated_by," +
				"	entered_by)" +
				" VALUES (" +
				"	'SAMSUNG'," +
				"	" + SqlHandler.delimitString(po.getGeneratedPoNumber()) + "," +
				"	" + po.getQuantity() + "," +
				"	'SAMSUNG'," +
				"	'Austin'," +
				"	" + SqlHandler.delimitString(po.getMaterial()) + "," +
				"	SYSDATE," +
				"	" + DateHandler.getOracleToDateFunction(po.getFdDate()) + "," +
				"	'Austin Samsung'," +
				"	0," +
				"	" + itemId + "," +
				"	" + SqlHandler.delimitString(po.getPurchaseDoc()) + "," +
				"	" + SqlHandler.delimitString(po.getItem()) + "," +
				"	global.customer_inventory_doc_num_seq.NEXTVAL," +
				"	1," +
				"	'Open'," +
				"	'Uploaded from " + fileName + " on ' || (TO_CHAR (SYSDATE, 'MM/DD/YYYY'))," +
				"	SYSDATE," +
				"	-1," +
				"	-1" +
				")";

		int rowsUpdated = 0;
		try {
			rowsUpdated = factory.deleteInsertUpdate(insert, conn);
		}
		catch (BaseException e) {
			log.error("Error inserting Samsung Open PO into CITR", e);
		}
		if (rowsUpdated < 1) {
			msg = "Unable to upload Open PO - " + po.getDescription();
		}
		return msg;
	}

} // end of class