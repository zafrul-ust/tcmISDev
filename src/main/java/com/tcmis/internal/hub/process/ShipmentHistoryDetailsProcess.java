package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryDetailsViewBean;
import com.tcmis.internal.hub.factory.ShipmentBeanFactory;

public class ShipmentHistoryDetailsProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ShipmentHistoryDetailsProcess(String client, String locale) {
		super(client, locale);
	}

	public void updateShippingReference(Collection<ShipmentHistoryDetailsViewBean> beanCollection) throws BaseException {
		factory.setBean(new ShipmentHistoryDetailsViewBean());
		for (ShipmentHistoryDetailsViewBean bean:beanCollection) {
//			System.out.println(bean.getPrNumber()+":"+bean.getLineItem()+":"+bean.getShippingReference()+":"+bean.getOldShippingReference());
			if( !bean.getShippingReference().equals(bean.getOldShippingReference()))  {
			String query = "update rli set shipping_reference =" + 
			                this.getSqlString(bean.getShippingReference()) + 
			                " where pr_number = " + bean.getPrNumber()+ " and line_item = " + bean.getLineItem() ;
			this.factory.deleteInsertUpdate(query);
//			System.out.println(query);
			}
		}
	}
	public Collection<ShipmentHistoryDetailsViewBean> getShipmentHistoryDetails(String shipmentId, BigDecimal personnelId) throws BaseException {

		factory.setBean(new ShipmentHistoryDetailsViewBean());
		Collection<ShipmentHistoryDetailsViewBean> historyDetails = factory.selectQuery("select * from table (pkg_shipment_history.fx_shipment_history_detail(" + shipmentId + "," + personnelId + "))  order by pr_number, line_item, receipt_id");

		// Calculate the various rowspans
		ShipmentHistoryDetailsViewBean mrStartingLine = null;
		ShipmentHistoryDetailsViewBean itemStartingLine = null;
		ShipmentHistoryDetailsViewBean receiptStartingLine = null;
		int mrRowspan = 1;
		int itemRowspan = 1;
		int receiptRowspan = 1;
		for (ShipmentHistoryDetailsViewBean line : historyDetails) {
			if (mrStartingLine != null) {
				if (mrStartingLine.getPrNumber().equals(line.getPrNumber())) {
					mrRowspan++;
					line.setMrRowspan(0);
					if (itemStartingLine.getItemId().equals(line.getItemId())) {
						itemRowspan++;
						line.setItemRowspan(0);
						if (receiptStartingLine.getReceiptId().equals(line.getReceiptId())) {
							receiptRowspan++;
							line.setReceiptRowspan(0);
						}
						else {
							receiptStartingLine.setReceiptRowspan(receiptRowspan);
							receiptStartingLine = line;
							receiptRowspan = 1;
						}
					}
					else {
						itemStartingLine.setItemRowspan(itemRowspan);
						itemStartingLine = line;
						itemRowspan = 1;
						receiptStartingLine.setReceiptRowspan(receiptRowspan);
						receiptStartingLine = line;
						receiptRowspan = 1;
					}
				}
				else {
					mrStartingLine.setMrRowspan(mrRowspan);
					mrStartingLine = line;
					mrRowspan = 1;
					itemStartingLine.setItemRowspan(itemRowspan);
					itemStartingLine = line;
					itemRowspan = 1;
					receiptStartingLine.setReceiptRowspan(receiptRowspan);
					receiptStartingLine = line;
					receiptRowspan = 1;
				}
			}
			else {
				mrStartingLine = line;
				itemStartingLine = line;
				receiptStartingLine = line;
			}
		}
		if (mrRowspan > 1) {
			mrStartingLine.setMrRowspan(mrRowspan);
		}
		if (itemRowspan > 1) {
			itemStartingLine.setItemRowspan(itemRowspan);
		}
		if (receiptRowspan > 1) {
			receiptStartingLine.setReceiptRowspan(receiptRowspan);
		}
		return historyDetails;
	}

	public ExcelHandler getExcelReport(Collection<ShipmentHistoryDetailsViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.mr", "label.po", "label.customer", "label.shipto", "label.requestor", "label.csr", "label.shippingreferenece", "label.partnumber", "label.itemid", "label.itemdesc", "label.quantity", "label.receipt", "label.mfglot", "label.expiredate",
				"label.qcdate", "label.qcedby", "label.shipconfirmdate", "label.shipconfirmby", "label.picklistid" };
		/*
		 * This array defines the type of the excel column. 0 means
		 * default depending on the data type. pw.TYPE_PARAGRAPH
		 * defaults to 40 characters. pw.TYPE_CALENDAR set up the date
		 * with no time. pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { pw.TYPE_NUMBER, 0, 0, 0, 0, 0, 0 ,0 , pw.TYPE_NUMBER, pw.TYPE_PARAGRAPH, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_DATE, 0, pw.TYPE_DATE, 0, 0 };

		/*
		 * This array defines the default width of the column when the
		 * Excel file opens. 0 means the width will be default depending
		 * on the data type.
		 */
		int[] widths = { 8, 15, 15, 15, 15, 15, 15, 15, 8, 0, 5, 8, 10, 10, 18, 15, 18, 15, 8 };
		/*
		 * This array defines the horizontal alignment of the data in a
		 * cell. 0 means excel defaults the horizontal alignment by the
		 * data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(8, 2);
		pw.setColumnDigit(9, 2);

		for (ShipmentHistoryDetailsViewBean member : data) {

			pw.addRow();
			String mr = member.getPrNumber() + "-" + member.getLineItem();
			pw.addCell(mr);
			pw.addCell(member.getPoNumber());
			pw.addCell(member.getCustomerName());
			String address = member.getAddressLine1() + " " + member.getAddressLine2() + " " + member.getAddressLine3() + " " + member.getAddressLine4() + " " + member.getAddressLine5();
			pw.addCell(address);
			pw.addCell(member.getRequester());
			pw.addCell(member.getCustomerServiceRep());
			pw.addCell(member.getShippingReference());
			pw.addCell(member.getFacpartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getQuantityShipped());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getMfgLot());
			pw.addCell(member.getExpireDate());
			pw.addCell(member.getQcDate());
			pw.addCell(member.getQcBy());
			pw.addCell(member.getShipConfirmDate());
			pw.addCell(member.getShipConfirmBy());
			pw.addCell(member.getPickListId());

		}
		return pw;
	}

}
