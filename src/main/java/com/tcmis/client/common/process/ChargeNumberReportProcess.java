package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.ChargeNumberInputBean;
import com.tcmis.client.common.beans.ChargeNumberReportBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

public class ChargeNumberReportProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ChargeNumberReportProcess(String client) {
		super(client);
	}

	public ChargeNumberReportProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	public Collection<ChargeNumberReportBean> getSearch(ChargeNumberInputBean input, PersonnelBean personnelBean) throws BaseException
	{
		factory.setBean(new ChargeNumberReportBean());
		StringBuilder query = new StringBuilder("select dcrv.* from directed_charge_report_view dcrv, user_application ua where ua.company_id ='").append(personnelBean.getCompanyId());
        query.append("' and ua.facility_id = '").append(input.getFacilityId()).append("' and dcrv.account_sys_id ='").append(input.getAccountSysId()).append("'");
        query.append(" and dcrv.company_id = ua.company_id and dcrv.facility_id = ua.facility_id and");
        query.append(" dcrv.application = decode(ua.application,'All',dcrv.application,ua.application)");
        query.append(" and ua.personnel_id = ").append(personnelBean.getPersonnelId());
        if(input.getAreaId().length() > 0)
			query.append("and area_id ='").append(input.getAreaId()).append("' ");
		if(input.getBuildingId().length() > 0)
			query.append("and building_id ='").append(input.getBuildingId()).append("' ");
		if(input.getFloorId().length() > 0)
			query.append("and floor_id ='").append(input.getFloorId()).append("' ");
		if(input.getRoomId().length() > 0)
			query.append("and room_id ='").append(input.getRoomId()).append("' ");
	    if(input.getReportingEntityId().length() > 0)
			query.append("and reporting_entity_id ='").append(input.getReportingEntityId()).append("' ");
	    if(input.getDeptId().length() > 0)
			query.append("and dept_id ='").append(input.getDeptId()).append("' ");

        query.append(" order by application_desc,charge_type");
		return factory.selectQuery(query.toString());
	}
	
	public Collection<ChargeNumberReportBean> getChargeNumberLabels(ChargeNumberInputBean input, PersonnelBean personnelBean) throws BaseException
	{
		factory.setBean(new ChargeNumberReportBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		criteria.addCriterion("accountSysId", SearchCriterion.EQUALS, input.getAccountSysId());
		return factory.select(criteria, new SortCriteria(), "directed_chrg_header_rpt_view");
	}
	
	public ExcelHandler getExcelReport(ChargeNumberInputBean input, PersonnelBean personnelBean, Collection<ChargeNumberReportBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		Vector<ChargeNumberReportBean> labels = (Vector<ChargeNumberReportBean>)getChargeNumberLabels(input,personnelBean);

		ChargeNumberReportBean dLabels = new ChargeNumberReportBean();
		ChargeNumberReportBean iLabels = new ChargeNumberReportBean();
		String poRequired = "", prAcctRequired = "";
		for(ChargeNumberReportBean bean:labels) {
			if(bean.getChargeType().equalsIgnoreCase("d"))
				dLabels = bean;
			else
				iLabels = bean;
			
			if (StringHandler.isBlankString(poRequired) && bean.getPoRequired().equalsIgnoreCase("p"))
				poRequired = "p";
			
			if (StringHandler.isBlankString(prAcctRequired) && bean.getPrAccountRequired().equalsIgnoreCase("y"))
				prAcctRequired = "y";
		}
		
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		
		Vector<Integer> t = new Vector<Integer>();
		Vector<Integer> w = new Vector<Integer>();
		Vector<Integer> ha = new Vector<Integer>();
		pw.addCellBold(library.getString("label.workarea"));
		t.add(0);
		w.add(14);
		ha.add(0);
        pw.addCellBold(library.getString("label.department"));
		t.add(0);
		w.add(14);
		ha.add(0);
        pw.addCellBold(library.getString("label.defaultchargetype"));
		t.add(0);
		w.add(15);
		ha.add(0);
		
		String direct = library.getString("label.direct");
		String inDirect = library.getString("label.indirect");
		if(poRequired.equalsIgnoreCase("p"))
		{
			String poNumber = library.getString("label.ponumber");
			String poLine = library.getString("label.poline");
			pw.addCellBold(poNumber + "(" + direct + ")");
			t.add(0);
			w.add(17);
			ha.add(0);
			pw.addCellBold(poLine + "(" + direct + ")");
			t.add(0);
			w.add(17);
			ha.add(0);
			pw.addCellBold(poNumber + "(" + inDirect + ")");
			t.add(0);
			w.add(17);
			ha.add(0);
			pw.addCellBold(poLine+ "(" + inDirect + ")");
			t.add(0);
			w.add(17);
			ha.add(0);
		}
		if(prAcctRequired.equalsIgnoreCase("y"))
		{
			if(dLabels.getChargeLabel1() != null && dLabels.getChargeLabel1().length() > 0)
			{
				pw.addCellBold(dLabels.getChargeLabel1() + "(" + direct + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(dLabels.getChargeLabel2() != null && dLabels.getChargeLabel2().length() > 0)
			{
				pw.addCellBold(dLabels.getChargeLabel2() + "(" + direct + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(dLabels.getChargeLabel3() != null && dLabels.getChargeLabel3().length() > 0)
			{
				pw.addCellBold(dLabels.getChargeLabel3() + "(" + direct + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(dLabels.getChargeLabel4() != null && dLabels.getChargeLabel4().length() > 0)
			{
				pw.addCellBold(dLabels.getChargeLabel4() + "(" + direct + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			
			if(iLabels.getChargeLabel1() != null && iLabels.getChargeLabel1().length() > 0)
			{
				pw.addCellBold(iLabels.getChargeLabel1() + "(" + inDirect + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(iLabels.getChargeLabel2() != null && iLabels.getChargeLabel2().length() > 0)
			{
				pw.addCellBold(iLabels.getChargeLabel2() + "(" + inDirect + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(iLabels.getChargeLabel3() != null && iLabels.getChargeLabel3().length() > 0)
			{
				pw.addCellBold(iLabels.getChargeLabel3() + "(" + inDirect + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
			if(iLabels.getChargeLabel4() != null && iLabels.getChargeLabel4().length() > 0)
			{
				pw.addCellBold(iLabels.getChargeLabel4() + "(" + inDirect + ")");
				t.add(0);
				w.add(20);
				ha.add(0);
			}
		}
		pw.addCellBold(library.getString("label.percent"));
		t.add(0);
		w.add(10);
		ha.add(0);
		
		String[] headerkeys = {};
		
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/

		int[] types = new int[t.size()];
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = new int[w.size()];
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = new int[ha.size()];
		int count = 0;
		for(Integer i: t)
		{
			types[count] = i.intValue();
			widths[count] = w.get(count);
			horizAligns[count] = ha.get(count);
			count++;
		}

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);


		for (ChargeNumberReportBean member : data) {

			pw.addRow();
			if(member.getCatPartNo() != null && member.getCatPartNo().length() > 0)
				pw.addCell(member.getApplicationDesc() + " (" + member.getCatPartNo() + ")");
			else
				pw.addCell(member.getApplicationDesc());

            pw.addCell(member.getDeptName());

            if(member.getChargeTypeDefault() != null)
				if(member.getChargeTypeDefault().equalsIgnoreCase("d"))
					pw.addCell(direct);
				else
					pw.addCell(inDirect);
			else
				pw.addCell("");
			
			if(poRequired.equalsIgnoreCase("p"))
				if(member.getChargeType().equalsIgnoreCase("d"))
				{
					pw.addCell(member.getPoNumber());
					pw.addCell(member.getPoLine());
					pw.addCell("");
					pw.addCell("");
				}
				else
				{
					pw.addCell("");
					pw.addCell("");
					pw.addCell(member.getPoNumber());
					pw.addCell(member.getPoLine());
				}

			if(prAcctRequired.equalsIgnoreCase("y"))
				if(member.getChargeType().equalsIgnoreCase("d"))
				{
					if(dLabels.getChargeLabel1() != null && dLabels.getChargeLabel1().length() > 0)
						pw.addCell(member.getChargeNumber1());
					if(dLabels.getChargeLabel2() != null && dLabels.getChargeLabel2().length() > 0)
						pw.addCell(member.getChargeNumber2());
					if(dLabels.getChargeLabel3() != null && dLabels.getChargeLabel3().length() > 0)
						pw.addCell(member.getChargeNumber3());
					if(dLabels.getChargeLabel4() != null && dLabels.getChargeLabel4().length() > 0)
						pw.addCell(member.getChargeNumber4());
					if(iLabels.getChargeLabel1() != null && iLabels.getChargeLabel1().length() > 0)
						pw.addCell("");
					if(iLabels.getChargeLabel2() != null && iLabels.getChargeLabel2().length() > 0)
						pw.addCell("");
					if(iLabels.getChargeLabel3() != null && iLabels.getChargeLabel3().length() > 0)
						pw.addCell("");
					if(iLabels.getChargeLabel4() != null && iLabels.getChargeLabel4().length() > 0)
						pw.addCell("");
					
				}
				else
				{
					if(dLabels.getChargeLabel1() != null && dLabels.getChargeLabel1().length() > 0)
						pw.addCell("");
					if(dLabels.getChargeLabel2() != null && dLabels.getChargeLabel2().length() > 0)
						pw.addCell("");
					if(dLabels.getChargeLabel3() != null && dLabels.getChargeLabel3().length() > 0)
						pw.addCell("");
					if(dLabels.getChargeLabel4() != null && dLabels.getChargeLabel4().length() > 0)
						pw.addCell("");
					if(iLabels.getChargeLabel1() != null && iLabels.getChargeLabel1().length() > 0)
						pw.addCell(member.getChargeNumber1());
					if(iLabels.getChargeLabel2() != null && iLabels.getChargeLabel2().length() > 0)
						pw.addCell(member.getChargeNumber2());
					if(iLabels.getChargeLabel3() != null && iLabels.getChargeLabel3().length() > 0)
						pw.addCell(member.getChargeNumber3());
					if(iLabels.getChargeLabel4() != null && iLabels.getChargeLabel4().length() > 0)
						pw.addCell(member.getChargeNumber4());
				}
			pw.addCell(member.getPercent());

		}
		return pw;
	}
}

