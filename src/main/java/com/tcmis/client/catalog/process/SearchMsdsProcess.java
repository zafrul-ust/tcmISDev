package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Process for SearchMsdsProcess
 * @version 1.0
 *****************************************************************************/
public class SearchMsdsProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public SearchMsdsProcess(String client,String locale) {
		super(client,locale);
	}

    public Collection searchMsds(CatalogInputBean bean) {
        Collection dataC = null;
        try {
            MsdsViewerProcess msdsProcess = new MsdsViewerProcess(getClient(),getLocale());
            MsdsViewerInputBean inputBean = new MsdsViewerInputBean();
            inputBean.setCompanyId(bean.getCompanyId());
            inputBean.setFacilityId(bean.getFacilityId());
            inputBean.setSimpleSearchText(bean.getSearchText());
            if("Full MSDS Database".equals(bean.getFacilityOrFullMsdsDatabase()))
                	inputBean.setFullDatabase("Y");
            inputBean.setSourcePage(bean.getSourcePage());
            dataC = msdsProcess.getSearchMsdsOnlyResults(inputBean);
            if (dataC != null) {
				if (dataC.size() > 0) {
					msdsProcess.calculatingNumberOfKitsPerMsds(dataC);
				}
			}
        }catch (Exception e) {
			e.printStackTrace();
			dataC = new Vector(0);
		}
        return dataC;
	}

    public ExcelHandler getMsdsExcelReport(CatalogInputBean inputbean, PersonnelBean personnel) throws NoDataException, BaseException, Exception {
		Vector<MsdsSearchDataTblBean> data = (Vector)this.searchMsds(inputbean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	 write column headers
		pw.addRow();

		boolean showMsds = personnel.isFeatureReleased("ShowKitMaterialMsds", "ALL",inputbean.getCompanyId());
        MsdsViewerProcess msdsProcess = new MsdsViewerProcess(this.getClient(),this.getLocale());
        boolean showApprovalCode = "Y".equals(msdsProcess.showFacilityUseCode(inputbean.getFacilityId()));

        /*Pass the header keys for the Excel.*/
        ArrayList<String> hk = new ArrayList<String>();
        if (showMsds) {
            hk.add("label.kitmsds");
            hk.add("label.kitdesc");
        }
        if (showApprovalCode)
            hk.add("label.approvalcode");
        if (showMsds)
            hk.add("label.msds");
        hk.add("label.materialdesc");
        if (showMsds) {
            hk.add("label.kitmixratio");
            hk.add("label.kitmixratiouom");
        }
        hk.add("label.materialid");
        hk.add("label.manufacturer");
        hk.add("label.tradename");


		/*This array defines the type of the excel column.
        0 means default depending on the data type. */
		int[] types = new int[hk.size()];
        int tmpIndex = 0;
        if (showMsds) {
            types[tmpIndex++] = 0;
            types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        }
        if (showApprovalCode)
            types[tmpIndex++] = 0;
        if (showMsds)
            types[tmpIndex++] = 0;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        if (showMsds) {
            types[tmpIndex++] = 0;
            types[tmpIndex++] = 0;
        }
        types[tmpIndex++] = 0;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;

        /*This array defines the default width of the column when the Excel file opens.
          0 means the width will be default depending on the data type.*/
		int[] widths = null;
		/*This array defines the horizontal alignment of the data in a cell.
          0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;

        String [] headerkeys = new String[hk.size()];
		headerkeys = hk.toArray(headerkeys);
        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for(MsdsSearchDataTblBean bean: data) {
			pw.addRow();
			if (showMsds) {
                pw.addCell(bean.getCustomerMixtureNumber());
                pw.addCell(bean.getMixtureDesc());
            }
            if (showApprovalCode)
                pw.addCell(bean.getApprovalCode());
            if (showMsds)
                pw.addCell(bean.getCustomerMsdsNumber());
            pw.addCell(bean.getMaterialDesc());
            if (showMsds) {
                pw.addCell(bean.getMixRatioAmount());
                pw.addCell(bean.getMixRatioSizeUnit());
            }
            pw.addCell(bean.getMaterialId());
            pw.addCell(bean.getMfgDesc());
            pw.addCell(bean.getTradeName());
		}
		return pw;
	}


} //end of class
