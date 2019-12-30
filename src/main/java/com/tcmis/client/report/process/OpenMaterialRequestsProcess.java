package com.tcmis.client.report.process;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.OpenMaterialRequestsInputBean;
import com.tcmis.client.report.beans.OpenMaterialRequestsViewBean;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

public class OpenMaterialRequestsProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());

    public OpenMaterialRequestsProcess(String client) {
        super(client);
    }

    public OpenMaterialRequestsProcess(String client, String locale) {
        super(client, locale);
    }

    public Collection<OpenMaterialRequestsViewBean> getOpenMaterialRequests(OpenMaterialRequestsInputBean inputSearchBean, PersonnelBean personnelBean)
            throws BaseException {

        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new OpenMaterialRequestsViewBean());
        SearchCriteria criteria = new SearchCriteria();
        if (!StringHandler.isBlankString(personnelBean.getCompanyId()))
            criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
        if (!StringHandler.isBlankString(inputSearchBean.getSearchText()))
            criteria.addCriterion("prNumber", SearchCriterion.LIKE, inputSearchBean.getSearchText(), SearchCriterion.IGNORE_CASE);
        if (!StringHandler.isBlankString(inputSearchBean.getFacilityId())) {
            criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputSearchBean.getFacilityId());
        } else {
            criteria.addCriterion("facilityId", SearchCriterion.IN, "select facility_id from user_facility where company_id = '" + personnelBean.getCompanyId() + " and personnel_id = " + personnelBean.getPersonnelId());
        }
        SortCriteria sort = new SortCriteria();
        sort.setSortAscending(true);
        sort.addCriterion("prNumber");
        sort.addCriterion("facilityId");
        return factory.select(criteria, sort, "incoming_mr_view");
    }

    public Collection<FacilityBean> getFacilities(PersonnelBean personnelBean)
            throws BaseException {

        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FacilityBean());

        String query = "select * from USER_FACILITY_VIEW where personnel_id = " + personnelBean.getPersonnelId();

        return factory.selectQuery(query);
    }


    public ExcelHandler getExcelReport(Collection<OpenMaterialRequestsViewBean> data, Locale locale,OpenMaterialRequestsInputBean inputSearchBean, PersonnelBean personnelBean) throws
            NoDataException, BaseException, Exception {

        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        ExcelHandler pw = new ExcelHandler(library);
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("MM/dd/yyyy");
        pw.addTable();
        pw.addRow();

        boolean isDisplayChangeNoOwnerSegment = personnelBean.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL",inputSearchBean.getCompanyId());
        boolean isShowQtyInCustomerUom = personnelBean.isFeatureReleased("ShowQtyInCustomerUom", inputSearchBean.getFacilityId(),inputSearchBean.getCompanyId());
        String[] headerkeys;
        int[] types;
        int[] widths;
        int[] horizAligns;
        if (isShowQtyInCustomerUom && isDisplayChangeNoOwnerSegment) {
            headerkeys = new String[16];
            types = new int[16];
            widths = new int[16];
            horizAligns = new int[16];
        }else if (isShowQtyInCustomerUom) {
            headerkeys = new String[15];
            types = new int[15];
            widths = new int[15];
            horizAligns = new int[15];
        }else if (isDisplayChangeNoOwnerSegment) {
            headerkeys = new String[13];
            types = new int[13];
            widths = new int[13];
            horizAligns = new int[13];
        }else {
            headerkeys = new String[12];
            types = new int[12];
            widths = new int[12];
            horizAligns = new int[12];
        }

        int count = 0;
        headerkeys[count++] = "label.facility";
        headerkeys[count++] = "label.mrnumber";
        headerkeys[count++] = "label.lineitem";
        headerkeys[count++] = "label.workarea";
        headerkeys[count++] = "label.partnumber";
        headerkeys[count++] = "label.partdesc";
        headerkeys[count++] = "label.price";
        headerkeys[count++] = "label.packaging";
        headerkeys[count++] = "label.orderqty";
        headerkeys[count++] = "label.allocatedqty";
        if (isShowQtyInCustomerUom) {
            headerkeys[count++] = "label.orderedqtyinuom";
            headerkeys[count++] = "label.allocatedqtyinuom";
            headerkeys[count++] = "label.uom";
        }
        headerkeys[count++] = "label.ref";
        headerkeys[count++] = "label.projecteddelivery";
        if (isDisplayChangeNoOwnerSegment) {
            headerkeys[count++] = "label.program";
           // headerkeys[count++] = "label.owner";
        }

        count = 0;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = pw.TYPE_PARAGRAPH;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = 0;
        types[count++] = 0;
        if (isShowQtyInCustomerUom) {
            types[count++] = 0;
            types[count++] = 0;
            types[count++] = 0;
        }
        types[count++] = 0;
        types[count++] = pw.TYPE_DATE;
        if (isDisplayChangeNoOwnerSegment) {
            types[count++] = 0;
            //types[count++] = 0;
        }

        count = 0;
        widths[count++] = 0;
        widths[count++] = 0;
        widths[count++] = 0;
        widths[count++] = 0;
        widths[count++] = 0;
        widths[count++] = 20;
        widths[count++] = 9;
        widths[count++] = 15;
        widths[count++] = 0;
        widths[count++] = 12;
        if (isShowQtyInCustomerUom) {
            widths[count++] = 0;
            widths[count++] = 0;
            widths[count++] = 0;
        }
        widths[count++] = 0;
        widths[count++] = 0;
        if (isDisplayChangeNoOwnerSegment) {
            widths[count++] = 0;
            //widths[count++] = 0;
        }

        count = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        if (isShowQtyInCustomerUom) {
            horizAligns[count++] = 0;
            horizAligns[count++] = 0;
            horizAligns[count++] = 0;
        }
        horizAligns[count++] = 0;
        horizAligns[count++] = 0;
        if (isDisplayChangeNoOwnerSegment) {
            horizAligns[count++] = 0;
            //horizAligns[count++] = 0;
        }

        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

        // format the numbers to the special columns
        
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (OpenMaterialRequestsViewBean member : data) {
            String tmpDocType = member.getDocType();
            if ("PR".equals(tmpDocType))
                tmpDocType = library.getString("label.bo");
            pw.addRow();
            pw.addCell(member.getFacilityName());
            pw.addCell(member.getPrNumber());
            pw.addCell(member.getLineItem());
            pw.addCell(member.getApplicationDesc());
            pw.addCell(member.getFacPartNo());
            pw.addCell(member.getPartDescription());
            pw.addCell(member.getCatalogPrice() != null ? (decimalFormat.format(member.getCatalogPrice().setScale(2, 4))) + " " + StringHandler.emptyIfNull(member.getCurrencyId()):"");
            pw.addCell(member.getPackaging());
            pw.addCell(member.getOrderQty());
            pw.addCell(member.getAllocatedQty());
            if (isShowQtyInCustomerUom) {
                pw.addCell(member.getUosOrderQty());
                pw.addCell(member.getUosAllocatedQty());
                pw.addCell(member.getUnitOfSale());
            }
            pw.addCell(tmpDocType + ": " + member.getDocNum());
            pw.addCell(member.getProjDeliverDate());
            if (isDisplayChangeNoOwnerSegment) {
                pw.addCell(member.getProgramId());
                //pw.addCell(member.getOwnerSegmentId());
            }

        }
        return pw;
    }

}

