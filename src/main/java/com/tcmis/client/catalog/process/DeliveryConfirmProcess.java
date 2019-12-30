package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.tcmis.client.catalog.beans.DeliveryConfirmInputBean;
import com.tcmis.client.catalog.beans.DeliveryConfirmViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;

public class DeliveryConfirmProcess extends GenericProcess {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	public DeliveryConfirmProcess(String client) {
		super(client);
	}

	public DeliveryConfirmProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection<DeliveryConfirmViewBean> getDeliveryConfirmations(DeliveryConfirmInputBean inputSearchBean, PersonnelBean user) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryConfirmViewBean());

		StringBuilder query = new StringBuilder(
				"select * from table (pkg_delivery_confirmation.fx_delivery_conf_search(");
		query.append(user.getPersonnelId());
		query.append(",").append(SqlHandler.delimitString(user.getCompanyId()));
		query.append(",").append(SqlHandler.delimitString(inputSearchBean.getFacilityId()));
		if (!StringHandler.isBlankString(inputSearchBean.getSearchArgument())) {
			query.append(",").append(SqlHandler.delimitString(inputSearchBean.getSearchField()));
			query.append(",").append(SqlHandler.delimitString(inputSearchBean.getSearchMode()));
			query.append(",").append(SqlHandler.delimitString(inputSearchBean.getSearchArgument()));
		} else {
			query.append(",''");
			query.append(",''");
			query.append(",''");
		}
		query.append(",").append(null != inputSearchBean.getShipFromDate() ? "TO_DATE(" + SqlHandler.delimitString(dateFormatter.format(inputSearchBean.getShipFromDate()))	+ ", 'MM/DD/RRRR hh24:mi:ss')" : "''");
		query.append(",").append(null != inputSearchBean.getShipToDate() ? "TO_DATE(" + SqlHandler.delimitString(dateFormatter.format(inputSearchBean.getShipToDate()))	+ ", 'MM/DD/RRRR hh24:mi:ss')" : "''");
		query.append(",").append(!StringHandler.isBlankString(inputSearchBean.getShowConfirmed()) ? SqlHandler.delimitString(inputSearchBean.getShowConfirmed()) : SqlHandler.delimitString("N"));
		query.append(",").append(SqlHandler.delimitString(inputSearchBean.getApplication()));
		query.append(")) order by shipment_id, pr_number, line_item, receipt_id");

		Vector<DeliveryConfirmViewBean> results = (Vector<DeliveryConfirmViewBean>) factory.selectQuery(query.toString());
		DeliveryConfirmViewBean currBean = null;
		if (results.size() > 0) {
			currBean = results.firstElement();
			currBean.setTotalQuantity(new BigDecimal(0));
		}
		for (DeliveryConfirmViewBean bean : results) {
			if (currBean.getShipmentId() != null && bean.getShipmentId() != null 
					&& currBean.getPrNumber() != null && bean.getPrNumber() != null 
					&& currBean.getShipmentId().equals(bean.getShipmentId())
					&& currBean.getPrNumber().equals(bean.getPrNumber())
					&& currBean.getLineItem().equals(bean.getLineItem())) {
				currBean.setTotalQuantity(currBean.getTotalQuantity().add(bean.getQuantityShipped()));
			} else {
				currBean = bean;
				currBean.setTotalQuantity((currBean.getQuantityShipped() != null ? currBean.getQuantityShipped() : new BigDecimal(0)));
			}
		}

		return results;
	}

	public String update(Collection<DeliveryConfirmViewBean> all, BigDecimal pid) throws BaseException {
		String result = "";
		String pkgCall = "";
		try {
			for (DeliveryConfirmViewBean bean : all) {
				if (!bean.getIsConfirmed().equalsIgnoreCase("N") && bean.getTotalQuantity() != null) {
					pkgCall = "pkg_delivery_confirmation.p_insert_delivery_confirmation";
					Vector outArgs = new Vector();
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					result = getProcError(buildProcedureInput(bean.getCompanyId(), bean.getPrNumber(), bean.getLineItem(), bean.getShipmentId(), bean.getShipDate(), bean.getTotalQuantity(), bean.getComments(), pid), outArgs, pkgCall);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ExcelHandler getExcelReport(Collection<DeliveryConfirmViewBean> data, Locale locale, PersonnelBean personnel, String showconfirmed) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();

		boolean isDisplayChangeNoOwnerSegment = personnel.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL", personnel.getCompanyId());

		ArrayList<String> hk = new ArrayList<String>();
		hk.add("label.shipmentid");
		hk.add("label.mrnumber");
		hk.add("label.lineitem");
		hk.add("label.workarea");
		hk.add("label.partnumber");
		hk.add("label.description");
		hk.add("label.qtyshipped");
		if (isDisplayChangeNoOwnerSegment) {
			hk.add("label.receipt");
			hk.add("label.lot");
			hk.add("label.owner");
			hk.add("label.program");
			hk.add("label.trace");
			hk.add("label.trackingnumber");
		}
		hk.add("label.shipdate");
		hk.add("label.confirmdate");
		if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
			hk.add("label.confirmedby");
		hk.add("label.comments");

		String[] headerkeys = new String[hk.size()];
		headerkeys = hk.toArray(headerkeys);

		ArrayList<Integer> t = new ArrayList<Integer>();
		t.add(pw.TYPE_NUMBER);
		t.add(0);
		t.add(0);
		t.add(0);
		t.add(0);
		t.add(pw.TYPE_PARAGRAPH);
		t.add(pw.TYPE_NUMBER);
		if (isDisplayChangeNoOwnerSegment) {
			t.add(0);
			t.add(0);
			t.add(0);
			t.add(0);
			t.add(0);
			t.add(0);
		}
		t.add(pw.TYPE_CALENDAR);
		t.add(pw.TYPE_CALENDAR);
		if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
			t.add(0);
		t.add(0);

		int[] types = new int[t.size()];
		types = ArrayUtils.toPrimitive(t.toArray(new Integer[t.size()]));

		ArrayList<Integer> w = new ArrayList<Integer>();
		w.add(10);
		w.add(10);
		w.add(6);
		w.add(10);
		w.add(15);
		w.add(25);
		w.add(10);
		if (isDisplayChangeNoOwnerSegment) {
			w.add(0);
			w.add(0);
			w.add(0);
			w.add(0);
			w.add(0);
			w.add(0);
		}
		w.add(10);
		if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
			w.add(0);
		w.add(12);
		w.add(12);

		int[] widths = new int[w.size()];
		widths = ArrayUtils.toPrimitive(w.toArray(new Integer[w.size()]));

		ArrayList<Integer> ha = new ArrayList<Integer>();

		for (int i = 0; i < 7; i++)
			ha.add(0);
		if (isDisplayChangeNoOwnerSegment) {
			ha.add(0);
			ha.add(0);
			ha.add(0);
			ha.add(0);
			ha.add(0);
			ha.add(0);
		}
		ha.add(0);
		if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
			ha.add(0);
		ha.add(0);
		ha.add(0);

		int[] horizAligns = new int[ha.size()];
		horizAligns = ArrayUtils.toPrimitive(ha.toArray(new Integer[ha.size()]));

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(7, 2);
		pw.setColumnDigit(8, 2);

		for (DeliveryConfirmViewBean member : data) {
			if (isDisplayChangeNoOwnerSegment) {
				pw.addRow();
				pw.addCell(member.getShipmentId());
				pw.addCell(member.getPrNumber());
				pw.addCell(member.getLineItem());
				pw.addCell(member.getApplicationDesc());
				pw.addCell(member.getPartNumber());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getQuantityShipped());
				pw.addCell(member.getReceiptId());
				pw.addCell(member.getMfgLot());
				pw.addCell(member.getOwnerSegmentId());
				pw.addCell(member.getProgramId());
				pw.addCell(member.getOriginalReceiptId());
				pw.addCell(member.getTrackingNumber());
				pw.addCell(member.getShipDate());
				pw.addCell(member.getConfirmationDate());
				if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
					pw.addCell(member.getConfirmedByName());
				pw.addCell(member.getComments());
			} else if (member.getTotalQuantity() != null) {
				pw.addRow();
				pw.addCell(member.getShipmentId());
				pw.addCell(member.getPrNumber());
				pw.addCell(member.getLineItem());
				pw.addCell(member.getApplicationDesc());
				pw.addCell(member.getPartNumber());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getTotalQuantity());
				pw.addCell(member.getShipDate());
				pw.addCell(member.getConfirmationDate());
				if (showconfirmed != null && showconfirmed.equalsIgnoreCase("Y"))
					pw.addCell(member.getConfirmedByName());
				pw.addCell(member.getComments());
			}

		}
		return pw;
	}

}
