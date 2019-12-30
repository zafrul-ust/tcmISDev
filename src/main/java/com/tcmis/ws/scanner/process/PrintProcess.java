package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.ui.web.taglib.Environment;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.BillOfLadingViewBean;
import com.tcmis.internal.hub.beans.BoxLabelViewBean;
import com.tcmis.internal.hub.beans.ContainerLabelDetailViewBean;
import com.tcmis.internal.hub.beans.ContainerLabelMasterViewBean;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.PicklistViewBean;
import com.tcmis.internal.hub.factory.ContainerLabelDetailViewBeanFactory;
import com.tcmis.internal.hub.factory.ContainerLabelMasterViewBeanFactory;
import com.tcmis.internal.hub.process.PickingQcProcess;
import com.tcmis.internal.hub.process.ZplProcess;
import com.tcmis.internal.print.process.ShippingLabelProcess;
import com.tcmis.ws.scanner.beans.IataDotLabel;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.PickingUnit;
import com.tcmis.ws.scanner.beans.PrintDocument;
import com.tcmis.ws.scanner.beans.PrintInput;
import com.tcmis.ws.scanner.beans.RliLabel;
import com.tcmis.ws.scanner.beans.TabletShipment;

import radian.web.barcode.BolPicklistPage;
import radian.web.barcode.delvtktGenerator;

public class PrintProcess extends BaseScannerPickProcess {

	private static String	certQuery		= "SELECT rd.document_url, rd.document_type  FROM	picking_unit_issue pui, issue i, receipt_document rd"
			+ " WHERE i.issue_id = pui.issue_id AND rd.receipt_id = i.receipt_id AND rd.document_type in ('COA', 'COC') AND pui.picking_unit_id = ";

	private static String	iataDotQuery	= "SELECT distinct i.item_id, fx_part_iata (i.item_id) iata, fx_part_dot (i.item_id) dot"
			+ " FROM picking_unit_issue pui, issue i WHERE i.issue_id = pui.issue_id AND pui.picking_unit_id = ";

	private static String	lineFeed		= "";
	static {
		lineFeed += (char) (13);
		lineFeed += (char) (10);
	}

	public PrintProcess(String client) {
		super(client);
	}

	private Collection<PickingUnit> getAllPickingUnitsByTabletShipmentId(String tabletShipmentId, Connection conn) throws IOException, BaseException {
		String query = "select * from picking_unit WHERE tablet_shipment_id = '" + tabletShipmentId + "'";
		factory.setBean(new PickingUnit());
		return factory.selectQuery(query);
	}

	protected void addDocumentToShippingDocument(BigDecimal personnelId, String shipmentId, String fileName, Connection conn) throws IOException, BaseException {
		File originalFile = new File(fileName);
		String date = new SimpleDateFormat("yyyy/MM/").format(new Date());
		String destFilename = savePath + date + originalFile.getName();
		File destFile = new File(destFilename);
		destFile.getParentFile().mkdirs();
		FileUtils.copyFile(originalFile, destFile);
		String insert = "insert into shipment_document (shipment_id, document_url, document_name, document_type, entered_by)" + "values (" + shipmentId + ", "
				+ SqlHandler.delimitString(getRelativeUrlFromFilename(destFilename)) + ", " + "'Straight BOL', " + "'BOL', " + personnelId + ")";
		factory.deleteInsertUpdate(insert, conn);
	}

	private Collection<BoxLabelViewBean> getBoxLabelData(PrintInput input) throws BaseException {
		String query = "SELECT  blv.LAST_NAME, FIRST_NAME, END_USER, FACILITY_ID, APPLICATION, DELIVERY_POINT, BLV.PR_NUMBER, BLV.LINE_ITEM,"
				+ "BLV.COMPANY_ID, BLV.CAT_PART_NO, APPLICATION_DESC, DELIVERY_POINT_DESC, BLV.INVENTORY_GROUP, BLV.REQUESTOR_NAME, REQUESTOR_PHONE,"
				+ "REQUESTOR_FAX, BLV.REQUESTOR_EMAIL,PAYMENT_TERMS, PO_NUMBER, SHIP_TO_ADDRESS_LINE_1, SHIP_TO_ADDRESS_LINE_2, SHIP_TO_ADDRESS_LINE_3,"
				+ "SHIP_TO_ADDRESS_LINE_4, SHIP_TO_ADDRESS_LINE_5, SHIP_TO_PHONE, SHIP_TO_FAX, SHIP_FROM_ADDRESS_LINE_1, SHIP_FROM_ADDRESS_LINE_2,"
				+ "SHIP_FROM_ADDRESS_LINE_3, SHIP_FROM_ADDRESS_LINE_4, SHIP_FROM_ADDRESS_LINE_5, SHIP_FROM_FAX, SHIP_FROM_PHONE, SHIPPING_REFERENCE,"
				+ "FACILITY_NAME, REPLACE(string_agg(blv.receipt_list),',','; ') receipt_list, P.PICKING_UNIT_ID, NVL(p.box_count, 1) BOX_COUNT"
				+ " FROM box_label_view blv, issue i, picking_unit p, picking_unit_issue pui "
				+ " WHERE I.ISSUE_ID = PUI.ISSUE_ID AND pui.picking_unit_id = p.picking_unit_id AND p.picking_unit_id =" + input.getPickingUnitId()
				+ " and blv.PR_NUMBER = I.PR_NUMBER AND blv.LINE_ITEM = I.LINE_ITEM and blv.issue_id = pui.issue_id "
				+ "GROUP BY LAST_NAME, FIRST_NAME, END_USER, FACILITY_ID, APPLICATION, DELIVERY_POINT, BLV.PR_NUMBER, BLV.LINE_ITEM,"
				+ "BLV.COMPANY_ID, BLV.CAT_PART_NO, APPLICATION_DESC, DELIVERY_POINT_DESC, BLV.INVENTORY_GROUP, BLV.REQUESTOR_NAME, REQUESTOR_PHONE, REQUESTOR_FAX, BLV.REQUESTOR_EMAIL,"
				+ "PAYMENT_TERMS, PO_NUMBER, SHIP_TO_ADDRESS_LINE_1, SHIP_TO_ADDRESS_LINE_2, SHIP_TO_ADDRESS_LINE_3, SHIP_TO_ADDRESS_LINE_4,"
				+ "SHIP_TO_ADDRESS_LINE_5, SHIP_TO_PHONE, SHIP_TO_FAX, SHIP_FROM_ADDRESS_LINE_1, SHIP_FROM_ADDRESS_LINE_2, SHIP_FROM_ADDRESS_LINE_3,"
				+ "SHIP_FROM_ADDRESS_LINE_4, SHIP_FROM_ADDRESS_LINE_5, SHIP_FROM_FAX, SHIP_FROM_PHONE, SHIPPING_REFERENCE, FACILITY_NAME, P.PICKING_UNIT_ID, BOX_COUNT";
		factory.setBean(new BoxLabelViewBean());
		@SuppressWarnings("unchecked")
		Collection<BoxLabelViewBean> results = factory.selectQuery(query);
		if (input.hasBoxCount()) {
			BigDecimal countOverride = new BigDecimal(input.getBoxCount());
			for (BoxLabelViewBean result : results) {
				result.setBoxCount(countOverride);
			}
		}
		return results;
	}

	private Collection<ContainerLabelMasterViewBean> getContainerLabelData(PrintInput input) throws BaseException {
		String nestedSelectSubstring1 = "select issue_id from picking_unit_issue";
		String nestedSelectSubstring2 = "select receipt_id from issue";
		SearchCriteria pickingUnit = new SearchCriteria();
		pickingUnit.addCriterion("pickingUnitId", SearchCriterion.EQUALS, "" + input.getPickingUnitId());
		SearchCriteria issue = new SearchCriteria();
		issue.addNestedQuery("issueId", nestedSelectSubstring1, pickingUnit);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addNestedQuery("receiptId", nestedSelectSubstring2, issue);

		ContainerLabelMasterViewBeanFactory beanFactory = new ContainerLabelMasterViewBeanFactory(dbManager);
		@SuppressWarnings("unchecked")
		Collection<ContainerLabelMasterViewBean> results = beanFactory.select(criteria);

		String rliQuery = "SELECT rli.fac_part_no, i.issue_id, i.receipt_id FROM request_line_item rli, issue i, picking_unit_issue pui WHERE rli.pr_number = i.pr_number and rli.line_item = i.line_item AND I.ISSUE_ID = PUI.ISSUE_ID AND pui.picking_unit_id = "
				+ input.getPickingUnitId();
		factory.setBean(new RliLabel());
		@SuppressWarnings("unchecked")
		Collection<RliLabel> rliResults = factory.selectQuery(rliQuery);

		for (RliLabel rli : rliResults) {
			if (rli.hasFacPartNo()) {
				for (ContainerLabelMasterViewBean label : results) {
					if (label.getReceiptId().equals(rli.getReceiptId())) {
						label.replaceCatPartNo1(rli.getFacPartNo());
						// If we changed the printed partNo we need to make sure
						// we also print the spec for the correct partNo
						try {
							SearchCriteria labelDetailSearch = new SearchCriteria();
							labelDetailSearch.addCriterion("itemId", SearchCriterion.EQUALS, label.getItemId().toString());
							labelDetailSearch.addCriterion("hub", SearchCriterion.EQUALS, label.getHub());
							labelDetailSearch.addCriterion("inventoryGroup", SearchCriterion.EQUALS, label.getInventoryGroup());
							labelDetailSearch.addCriterion("catPartNo", SearchCriterion.EQUALS, rli.getFacPartNo());
							ContainerLabelDetailViewBeanFactory labelDetailFactory = new ContainerLabelDetailViewBeanFactory(dbManager);
							Collection<ContainerLabelDetailViewBean> labelDetails = labelDetailFactory.selectDistinctPartNumber(labelDetailSearch);
							for (ContainerLabelDetailViewBean labelDetail : labelDetails) {
								label.setSpecId(labelDetail.getSpecId());
								label.setSpecDisplay(labelDetail.getSpecDisplay());
								break;
							}
						}
						catch (Exception excDetail) {
							excDetail.printStackTrace();
						}
						break;
					}
				}
			}
		}

		return results;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private Vector getFormattedBOLPrintData(PickingUnit pick) {
		Vector printData = new Vector();
		Vector prnumbers = new Vector();
		Vector linenumber = new Vector();
		Vector picklistid = new Vector();
		Hashtable FResult = new Hashtable();

		prnumbers.addElement("" + pick.getPrNumber());
		linenumber.addElement("" + pick.getLineItem());
		picklistid.addElement("" + pick.getPicklistId());

		FResult.put("mr_number", prnumbers);
		FResult.put("line_number", linenumber);
		FResult.put("picklist_number", picklistid);
		printData.add(FResult);
		return printData;
	}

	private Vector getFormattedDeliveryTicketData(PrintInput pick, JSONArray errors) throws BaseException {
		factory.setBean(new PicklistViewBean());
		String select = "p.mr_line, p.item_id, p.picklist_id, p.receipt_id,p.facility_id,p.application,p.cat_part_no,p.quantity_picked,p.picklist_quantity,p.expire_date,p.application_desc,p.requestor,p.part_Description,p.ship_to_location_id,p.packaging, p.company_id,p.end_user,p.mfg_lot";
		String query = "SELECT " + select + " FROM table(pkg_pick.fx_picklist(NULL,(select picklist_id from picking_unit where picking_unit_id =" + pick.getPickingUnitId()
				+ "))) p, picking_unit pu where pu.picking_unit_id = " + pick.getPickingUnitId() + " AND p.pr_number = pu.pr_number AND p.line_item = pu.line_item";
		@SuppressWarnings("unchecked")
		Collection<PicklistViewBean> beans = factory.selectQuery(query);
		Vector results = new Vector();
		if (!beans.isEmpty()) {
			PickingQcProcess pickQcProcess = new PickingQcProcess(client);
			results = pickQcProcess.deliverTicket(beans);
			results.remove(0); // Remove the summary element, keep the data
		}
		else {
			errors.put(getError(null, "Error printing DeliveryTicket: No data found", pick.getId(), query));
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	private Collection<LabelFormatFieldDefinitionBean> getLabelFieldDefinitions(PrintInput input, LabelFormatTemplateFileBean template, JSONArray errors) {
		String query = "select * from label_format_field_definition where label_format = '" + template.getLabelFormat() + "' and PRINTER_RESOLUTION_DPI = "
				+ template.getPrinterResolutionDpi();
		try {
			factory.setBean(new LabelFormatFieldDefinitionBean());
			Collection<LabelFormatFieldDefinitionBean> results = (Collection<LabelFormatFieldDefinitionBean>) factory.selectQuery(query);

			if (results == null || results.isEmpty()) {
				errors.put(getError(null, "No field definitions found for label: ", input.getId(), query));
			}
			else {
				return results;
			}
		}
		catch (Exception e) {
			log.error("Error retrieving field definitions for label", e);
			errors.put(getError(e, "Error retrieving field definitions for label: ", input.getId(), query));
		}
		return null;
	}

	private LabelFormatTemplateFileBean getLabelTemplate(PrintInput input, String labelType, JSONArray errors) {
		String query = "SELECT y.*, z.label_template" + " FROM label_format_template_file y, label_format_template z" + "  WHERE EXISTS ("
				+ " 	SELECT label_format FROM inventory_group_label_format x" + " 	WHERE x.INVENTORY_GROUP IN ("
				+ " 		SELECT r.inventory_group FROM receipt r, issue i, picking_unit_issue pui WHERE r.receipt_id = i.receipt_id AND I.ISSUE_ID = PUI.ISSUE_ID AND pui.picking_unit_id = "
				+ input.getPickingUnitId() + " 	)" + " 	AND x.label_type = '" + labelType + "'" + " 	AND x.LABEL_FORMAT = y.LABEL_FORMAT)"
				+ " 	AND y.printer_resolution_dpi IN ("
				+ " 		SELECT	llp.printer_resolution_dpi FROM	printer p, location_label_printer llp WHERE llp.hub = p.hub AND llp.printer_path = P.PRINTER_PATH AND p.printer_id = "
				+ input.getPrinterId() + " 	)" + " 	AND y.label_template_filename = z.label_template_filename";
		factory.setBean(new LabelFormatTemplateFileBean());
		try {
			@SuppressWarnings("unchecked")
			Collection<LabelFormatTemplateFileBean> labels = factory.selectQuery(query);
			if (labels == null || labels.isEmpty()) {
				errors.put(getError(null, "No template found for label: ", input.getId(), query));
			}
			else {
				return labels.iterator().next();
			}
		}
		catch (Exception e) {
			log.error("Error  retrieving label template", e);
			errors.put(getError(e, "Error retrieving label template: ", input.getId(), query));
		}
		return null;
	}

	private PickingUnit getPick(PrintInput input) throws BaseException {
		factory.setBean(new PickingUnit());
		@SuppressWarnings("unchecked")
		Collection<PickingUnit> beans = factory.selectQuery("select * from picking_unit p where p.picking_unit_id = " + input.getPickingUnitId());
		return beans.iterator().next();
	}

	private String getPrinter(PrintInput input) throws BaseException {
		return getPrinterCupsName("" + input.getPrinterId());
	}

	@SuppressWarnings("unchecked")
	private Collection<LocationLabelPrinterBean> getPrinters(PrintInput input) throws BaseException {
		factory.setBean(new LocationLabelPrinterBean());
		return factory.selectQuery("select * from location_label_printer llp, printer p where LLP.PRINTER_PATH = P.PRINTER_PATH and LLP.label_stock = '31' and p.printer_id = "
				+ input.getPrinterId());
	}

	public String printBOL(PrintInput input, boolean longBOL, JSONArray errors) {
		try {
			PickingUnit pick = getPick(input);
			String generatedBOLUrl = new BolPicklistPage().GenerateBol(getFormattedBOLPrintData(pick), "" + pick.getPicklistId(), longBOL ? "Yes" : "No");
			if (input.hasPrinterId()) {
				String BOLfileName = fileGenerationPath + getFilenameFromUrl(generatedBOLUrl);
				String printer = getPrinter(input);
				if (!Environment.isProduction()) {
					log.debug("Printing file " + BOLfileName + " to printer " + printer);
				}
				for (int i = 0; i < input.getPrintQuantity(); i++) {
					PrintHandler.print(printer, BOLfileName);
				}
				addDocumentToPickingDocument(input.getPersonnelId(), "" + pick.getPickingUnitId(), BOLfileName, "BOL");
			}
			return generatedBOLUrl;
		}
		catch (Exception e) {
			log.error("Error printing BOL", e);
			errors.put(getError(e, "Error printing BOL: ", input.getId(), ""));
		}
		return "";
	}

	public JSONArray printCert(PrintInput input, JSONArray errors) {
		JSONArray urls = new JSONArray();
		try {
			String printer = input.hasPrinterId() ? getPrinter(input) : "";

			factory.setBean(new PrintDocument());
			@SuppressWarnings("unchecked")
			Collection<PrintDocument> documents = (Collection<PrintDocument>) factory.selectQuery(certQuery + input.getPickingUnitId());
			for (PrintDocument doc : documents) {
				if (StringUtils.isNotBlank(printer)) {
					if (!Environment.isProduction()) {
						log.debug("Printing file " + doc.getFilename() + " to printer " + printer);
					}
					PrintHandler.print(printer, doc.getFilename());
				}
				urls.put(doc.getDocumentUrl());
			}
		}
		catch (Exception e) {
			log.error("Error printing Cert", e);
			errors.put(getError(e, "Error printing Cert: ", input.getId(), ""));
		}
		return urls;
	}

	public JSONArray printQVR(PrintInput input, JSONArray errors) {
		JSONArray urls = new JSONArray();
		try {
			String printer = input.hasPrinterId() ? getPrinter(input) : "";

			String query = "select document_url from receipt_document where receipt_id = " + input.getReceiptId() + " and document_type = 'QVR-A' and document_id = "
					+ " (select max(document_id) from receipt_document where receipt_id = " + input.getReceiptId() + " and document_type = 'QVR-A')";
			factory.setBean(new PrintDocument());
			@SuppressWarnings("unchecked")
			Collection<PrintDocument> documents = (Collection<PrintDocument>) factory.selectQuery(query);
			if (documents == null || documents.isEmpty()) {
				query = "select document_url from receipt_document where receipt_id = " + input.getReceiptId() + " and document_type = 'QVR' and document_id = "
						+ " (select max(document_id) from receipt_document where receipt_id = " + input.getReceiptId() + " and document_type = 'QVR')";
				documents = (Collection<PrintDocument>) factory.selectQuery(query);
			}
			if (documents == null || documents.isEmpty()) {
				errors.put(getError(null, "No QVR found: ", input.getId(), ""));
			}
			else {
				for (PrintDocument doc : documents) {
					if (StringUtils.isNotBlank(printer)) {
						if (!Environment.isProduction()) {
							log.debug("Printing file " + doc.getFilename() + " to printer " + printer);
						}
						PrintHandler.print(printer, doc.getFilename());
					}
					urls.put(doc.getDocumentUrl());
				}
			}
		}
		catch (Exception e) {
			log.error("Error printing QVR", e);
			errors.put(getError(e, "Error printing QVR: ", input.getId(), ""));
		}
		return urls;
	}

	public void printContainerLabel(PrintInput input, JSONArray errors) {
		try {
			Collection<ContainerLabelMasterViewBean> data = getContainerLabelData(input);
			ZplProcess zplProcess = new ZplProcess(getClient());
			String label = zplProcess.buildReceiptZplString(data, "31", "", getPrinters(input), input.getPrintQuantity(), input.getQuantityReceipts(), input.isSkipKitCaseQty());
			if (!Environment.isProduction()) {
				log.debug("Printing label \n" + label);
			}
			PrintHandler.printString(getPrinter(input), label);
		}
		catch (Exception e) {
			log.error("Error printing Container Label", e);
			errors.put(getError(e, "Error printing Container Label: ", input.getId(), ""));
		}
	}

	public void printIataDotLabel(PrintInput input, String labelType, JSONArray errors) {
		try {
			factory.setBean(new IataDotLabel());
			@SuppressWarnings("unchecked")
			Collection<BaseDataBean> labelData = factory.selectQuery(iataDotQuery + input.getPickingUnitId());
			if (labelData != null && !labelData.isEmpty()) {
				printLabel(input, labelType, labelData, errors);
			}
			else {
				errors.put(getError(null, "Unable to print label, no data found: ", input.getId(), iataDotQuery + input.getPickingUnitId()));
			}
		}
		catch (Exception e) {
			log.error("Error printing Label", e);
			errors.put(getError(e, "Error printing Label: ", input.getId(), ""));
		}
	}

	private void printLabel(PrintInput input, String labelName, Collection<BaseDataBean> data, JSONArray errors) throws Exception {
		LabelFormatTemplateFileBean template = getLabelTemplate(input, labelName, errors);
		if (template != null) {
			Collection<LabelFormatFieldDefinitionBean> fieldDefinitions = getLabelFieldDefinitions(input, template, errors);

			// Start creating the ZPL by adding the template
			StringBuilder zpl = new StringBuilder();
			zpl.append(template.getLabelTemplate()).append(lineFeed);

			// Add the ZPL for each label
			for (BaseDataBean label : data) {
				addLabelZPL(zpl, input, template, fieldDefinitions, label);
			}

			String printer = getPrinter(input);

			if (!Environment.isProduction()) {
				log.debug("Printing label \n" + zpl.toString());
			}

			PrintHandler.printString(printer, zpl.toString());
		}
	}

	private void addLabelZPL(StringBuilder zpl, PrintInput input, LabelFormatTemplateFileBean template, Collection<LabelFormatFieldDefinitionBean> fieldDefinitions,
			BaseDataBean bean) throws BaseException {
		// Tell the ZPL printer what template we are using
		zpl.append("^XA").append(lineFeed);
		zpl.append("^XF").append(template.getLabelFormat()).append("^FS").append(lineFeed);
		zpl.append(lineFeed);
		// Add the data to the label
		for (LabelFormatFieldDefinitionBean field : fieldDefinitions) {
			zpl.append("^").append(field.getLabelFieldId()).append("^FD").append(BeanHandler.getField(bean, field.getLabelFieldContent().toLowerCase())).append("^FS")
					.append(lineFeed);
		}
		// Add the print quantity and finish the label
		zpl.append("^PQ").append(input.getPrintQuantity()).append(lineFeed);
		zpl.append("^XZ").append(lineFeed);
	}

	public void printDeliveryLabel(PrintInput input, JSONArray errors) {
		try {
			LabelFormatTemplateFileBean template = getLabelTemplate(input, "box", errors);
			if (template != null) {
				Collection<LabelFormatFieldDefinitionBean> fieldDefinitions = getLabelFieldDefinitions(input, template, errors);
				if (fieldDefinitions != null && !fieldDefinitions.isEmpty()) {
					Collection<BoxLabelViewBean> boxData = getBoxLabelData(input);

					StringBuilder zpl = new StringBuilder();
					// Add the template
					zpl.append(template.getLabelTemplate()).append(lineFeed);

					// Add the field data for each label
					for (BoxLabelViewBean box : boxData) {
						int boxes = box.getBoxCount().intValue();
						if (boxes < 1) {
							boxes = 1;
						}
						for (int i = 1; i <= boxes; i++) {
							box.setCurLabelCount(i);
							addLabelZPL(zpl, input, template, fieldDefinitions, box);
						}
					}

					String printer = getPrinter(input);

					if (!Environment.isProduction()) {
						log.debug("Printing label \n" + zpl.toString());
					}

					PrintHandler.printString(printer, zpl.toString());
				}
			}
		}
		catch (Exception e) {
			log.error("Error printing Delivery Label", e);
			errors.put(getError(e, "Error printing Delivery Label: ", input.getId(), ""));
		}
	}

	public String printDeliveryTicket(PrintInput input, JSONArray errors) {
		try {
			Vector deliveryTicketData = getFormattedDeliveryTicketData(input, errors);
			if (deliveryTicketData.size() > 0) {
				delvtktGenerator generator = new delvtktGenerator();
				String fileName = generator.generateDeliveryTicketPDF(deliveryTicketData, fileGenerationPath, "" + input.getPickingUnitId());

				if (input.hasPrinterId()) {
					String printer = getPrinter(input);
					if (!Environment.isProduction()) {
						log.debug("Printing file " + fileName + " to printer " + printer);
					}
					for (int i = 0; i < input.getPrintQuantity(); i++) {
						PrintHandler.print(printer, fileName);
					}
					addDocumentToPickingDocument(input.getPersonnelId(), "" + input.getPickingUnitId(), fileName, "DELIVERY_DOCUMENT");
				}
				return fileName;
			}
		}
		catch (Exception e) {
			log.error("Error printing DeliveryTicket", e);
			errors.put(getError(e, "Error printing DeliveryTicket: ", input.getId(), ""));
		}
		return "";
	}

	public String printHoldSheet(PrintInput input, JSONArray errors) {
		Connection conn = null;

		String pdfUrl = "";
		try {
			String url = "http://localhost/HaasReports/report/printConfigurableReport.do?pr_pickingUnitId=" + input.getPickingUnitId()
					+ "&rpt_ReportBeanId=HoldSheetReportDefinition&pr_copies=1&locale=en_US&pr_urlOnly=y";
			pdfUrl = NetHandler.getHttpPostResponse(url, null, null, "");
		}
		catch (Exception e) {
			errors.put(getError(e, "Error generating HOLDSHEET: ", input.getId(), ""));
			return "";
		}
		if (input.hasPrinterId()) {
			try {
				String file = getFullFilenameFromUrl(pdfUrl);
				conn = dbManager.getConnection();
				addDocumentToPickingDocument(null, "" + input.getPickingUnitId(), file, "HOLDSHEET", conn);
				String printer = getPrinter(input);
				if (!Environment.isProduction()) {
					log.debug("Printing file " + file + " to printer " + printer);
				}
				PrintHandler.print(printer, file);
			}
			catch (Exception e) {
				errors.put(getError(e, "Error saving HOLDSHEET: ", input.getId(), ""));
			}
			finally {
				try {
					dbManager.returnConnection(conn);
				}
				catch (DbReturnConnectionException e) {
					e.printStackTrace();
				}
			}
		}
		return pdfUrl;
	}

	public String printPVR(PrintInput input, JSONArray errors) {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			Pick pick = new Pick();
			pick.setId(input.getId());
			if (input.hasPrinterId()) {
				pick.setPdfPrinterId(new BigDecimal(input.getPrinterId()));
			}
			pick.setPickingUnitId(new BigDecimal(input.getPickingUnitId()));
			PickCompleteProcess pcProcess = new PickCompleteProcess(client);
			return pcProcess.generatePVR(pick, input.hasPrinterId(), conn, errors);
		}
		catch (Exception e) {
			log.error("Error printing PVR", e);
			errors.put(getError(e, "Error printing PVR: ", input.getId(), ""));
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public String printStraightBOL(PrintInput input, JSONArray errors) {
		Connection conn = null;

		String pdfUrl = "";

		try {
			conn = dbManager.getConnection();

			try {
				String url = "http://localhost/HaasReports/report/printConfigurableReport.do?pr_tabletShipmentId=" + input.getTabletShipmentId()
						+ "&rpt_ReportBeanId=StraightBOLReportDefinition&pr_copies=1&locale=en_US&pr_urlOnly=y";
				pdfUrl = NetHandler.getHttpPostResponse(url, null, null, "");
			}
			catch (Exception e) {
				errors.put(getError(e, "Error generating Straight BOL: ", input.getId(), ""));
				return "";
			}

			String file = getFullFilenameFromUrl(pdfUrl);
			Collection<PickingUnit> pickingUnits = getAllPickingUnitsByTabletShipmentId(input.getTabletShipmentId(), conn);
			for (PickingUnit pu : pickingUnits) {
				addDocumentToPickingDocument(input.getPersonnelId(), "" + pu.getPickingUnitId(), file, "BOL_STRAIGHT");
			}

			if (input.hasPrinterId()) {
				try {
					Collection<TabletShipment> crossRefs = new DepartureScanProcess(getClient()).getShipmentCrossReference(input.getTabletShipmentId(), conn);
					for (TabletShipment crossRef : crossRefs) {
						addDocumentToShippingDocument(input.getPersonnelId(), "" + crossRef.getShipmentId(), file, conn);
					}
					String printer = getPrinter(input);
					if (!Environment.isProduction()) {
						log.debug("Printing file " + file + " to printer " + printer);
					}
					PrintHandler.print(printer, file);
				}
				catch (Exception e) {
					errors.put(getError(e, "Error saving Straight BOL: ", input.getId(), ""));
				}
			}
		}
		catch (

		Exception e1) {
			errors.put(getError(e1, "Error generating Straight BOL: ", input.getId(), ""));
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				e.printStackTrace();
			}
		}

		return pdfUrl;
	}

	public String printStraightBOLFromTCMIS(String tabletShipmentId, BigDecimal personnelId, String printerLocation) {
		Connection conn = null;

		String pdfUrl = "";
		try {
			conn = dbManager.getConnection();

			String url = "http://localhost/HaasReports/report/printConfigurableReport.do?pr_tabletShipmentId=" + tabletShipmentId
					+ "&rpt_ReportBeanId=StraightBOLReportDefinition&pr_copies=1&locale=en_US&pr_urlOnly=y";
			pdfUrl = NetHandler.getHttpPostResponse(url, null, null, "");

			String file = getFullFilenameFromUrl(pdfUrl);
			Collection<PickingUnit> pickingUnits = getAllPickingUnitsByTabletShipmentId(tabletShipmentId, conn);
			for (PickingUnit pu : pickingUnits) {
				addDocumentToPickingDocument(personnelId, "" + pu.getPickingUnitId(), file, "BOL_STRAIGHT");
			}
			Collection<TabletShipment> crossRefs = new DepartureScanProcess(getClient()).getShipmentCrossReference(tabletShipmentId, conn);
			for (TabletShipment crossRef : crossRefs) {
				addDocumentToShippingDocument(personnelId, "" + crossRef.getShipmentId(), file, conn);
			}
		}
		catch (Exception e) {
			log.error("Error printing Straight BOL", e);
			return null;
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				e.printStackTrace();
			}
		}

		return pdfUrl;
	}

	private Collection<BillOfLadingViewBean> getShippingLabelData(PrintInput input, ShippingLabelProcess shippingProcess) throws BaseException {
		String query = "";
		if (input.hasTabletShipmentId()) {
			query = "select i.* from issue i, tablet_shipment ts where i.shipment_id = ts.shipment_id and ts.tablet_shipment_id = "
					+ SqlHandler.delimitString(input.getTabletShipmentId());
		}
		else {
			query = "select i.* from picking_unit_issue pui, issue i where i.issue_id = pui.issue_id and pui.picking_unit_id = " + input.getPickingUnitId();
		}
		factory.setBean(new IssueBean());
		@SuppressWarnings("unchecked")
		Collection<IssueBean> issues = factory.selectQuery(query);
		String prNumbers = "";
		String lineItems = "";
		boolean first = true;
		for (IssueBean issue : issues) {
			if (!first) {
				prNumbers += ",";
				lineItems += ",";
			}
			else {
				first = false;
			}
			prNumbers += issue.getPrNumber();
			lineItems += issue.getLineItem();
		}

		return shippingProcess.getLabelData(prNumbers, lineItems);
	}

	public void printShippingLabels(PrintInput input, JSONArray errors) {
		try {
			ShippingLabelProcess shippingProcess = new ShippingLabelProcess(getClient());

			Collection<BillOfLadingViewBean> labelData = getShippingLabelData(input, shippingProcess);

			Collection<LocationLabelPrinterBean> printers = getPrinters(input);
			String printerResolution = "";
			for (LocationLabelPrinterBean printer : printers) {
				printerResolution = "" + printer.getPrinterResolutionDpi();
			}

			String shippingLabelZPL = shippingProcess.getShippingLabelZPL(labelData, printerResolution);

			String printer = getPrinter(input);

			if (!Environment.isProduction()) {
				log.debug("Printing label \n" + shippingLabelZPL);
			}

			PrintHandler.printString(printer, shippingLabelZPL);

		}
		catch (Exception e) {
			log.error("Error printing Shipping Label", e);
			errors.put(getError(e, "Error printing Shipping Label: ", input.getId(), ""));
		}
	}

	public String printPackingList(PrintInput input, JSONArray errors) {
		Connection conn = null;

		String pdfUrl = "";

		try {
			conn = dbManager.getConnection();
			Collection<TabletShipment> crossRefs = new DepartureScanProcess(getClient()).getShipmentCrossReference(input.getTabletShipmentId(), conn);

			try {
				HashMap<String, String> packingListTemplates = new HashMap<String, String>();
				String templateToUse = "";
				for (TabletShipment crossRef : crossRefs) {
					//get shipment's inventory group
					StringBuilder query = new StringBuilder("select inventory_group from shipment");
					query.append(" where shipment_id = ").append(crossRef.getShipmentId());
					String inventoryGroup = factory.selectSingleValue(query.toString(), conn);
					
					//get inventory group's template
					if (packingListTemplates.containsKey(inventoryGroup))
						templateToUse = packingListTemplates.get(inventoryGroup);
					else {
						query = new StringBuilder("select packing_list_template from inventory_group_definition");
						query.append(" where inventory_group = ").append(SqlHandler.delimitString(inventoryGroup));
						templateToUse = factory.selectSingleValue(query.toString(), conn);
						if (StringHandler.isBlankString(templateToUse)) {
							errors.put(getError(null, "Error generating Packing List: ", input.getId(), "Template not Found: " + inventoryGroup));
							return "";
						} else
							packingListTemplates.put(inventoryGroup, templateToUse);
					}
					
					String url = "http://localhost/HaasReports/report/printConfigurableReport.do?"
							+ "pr_shipment_id=" + crossRef.getShipmentId()
							+ "&rpt_ReportBeanId=" + templateToUse
							+ "&pr_copies=1&locale=en_US&pr_urlOnly=y";
					pdfUrl = NetHandler.getHttpPostResponse(url, null, null, "");
				}
			}
			catch (Exception e) {
				errors.put(getError(e, "Error generating Packing List: ", input.getId(), ""));
				return "";
			}
			
			if (StringHandler.isBlankString(pdfUrl)) {
				errors.put(getError(null, "Error generating Packing List: ", input.getId(), "Shipment not Found: " + input.getTabletShipmentId()));
			} else {
				String file = getFullFilenameFromUrl(pdfUrl);

				if (input.hasPrinterId()) {
					try {
						String printer = getPrinter(input);
						if (!Environment.isProduction()) {
							log.debug("Printing file " + file + " to printer " + printer);
						}
						PrintHandler.print(printer, file);
					}
					catch (Exception e) {
						errors.put(getError(e, "Error saving Packing List: ", input.getId(), ""));
					}
				}
			}
		}
		catch (Exception e1) {
			errors.put(getError(e1, "Error generating Packing List: ", input.getId(), ""));
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				e.printStackTrace();
			}
		}

		return pdfUrl;
	}
}
