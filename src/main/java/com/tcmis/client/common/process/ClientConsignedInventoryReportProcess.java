package com.tcmis.client.common.process;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.ArrayUtils;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.common.beans.ClientConsignedInventoryReportInputBean;
import com.tcmis.client.common.beans.ClientConsignedInventoryBean;

/******************************************************************************
 * Process for logistics
 * @version 1.0
 *****************************************************************************/
public class ClientConsignedInventoryReportProcess
    extends GenericProcess {

  public ClientConsignedInventoryReportProcess(String client) {
		super(client);
  }

  public ClientConsignedInventoryReportProcess(String client, String locale) {
		super(client, locale);
  }

	public Collection<ClientConsignedInventoryBean> getSearchResult(ClientConsignedInventoryReportInputBean inputBean) throws BaseException {
	  	DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ClientConsignedInventoryBean());
		StringBuilder query = new StringBuilder("select * from CLIENT_CONSIGN_INVENTORY_VIEW");
		boolean hasWhere = false;

		if( inputBean.getInventoryGroup() != null && !"All".equalsIgnoreCase(inputBean.getInventoryGroup()) ) {
			query.append(" where inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			hasWhere = true;
		}else if ("All".equalsIgnoreCase(inputBean.getInventoryGroup())) {
			query.append(" where inventory_group in (select inventory_group from facility_inventory_group where facility_id = '").append(inputBean.getFacilityId()).append("')");
			hasWhere = true;
		}  
		
		if( inputBean.getExpiresWithin() != null  ) {
			if (!hasWhere) {
				query.append(" where");
				hasWhere = true;
			}else {
				query.append(" and");
			}
			query.append(" expire_date <= sysdate + ").append(inputBean.getExpiresWithin());
	    }
		
		if( inputBean.getExpiresAfter() != null  ) {
			if (!hasWhere) {
				query.append(" where");
				hasWhere = true;
			}else {
				query.append(" and");
			}
			query.append(" expire_date >= sysdate + ").append(inputBean.getExpiresAfter());
	    }
	    
	    if(inputBean.getShowCustomerReturn() != null && "Y".equals(inputBean.getShowCustomerReturn())) {
			if (!hasWhere) {
				query.append(" where");
				hasWhere = true;
			}else {
				query.append(" and");
			}
			query.append(" return_pr_number is not null");
	    }
	    /*if (!hasWhere) {
			query.append(" where ");
			hasWhere = true;
		}else {
			query.append(" and ");
		}*/
		//query.append(" owner_company_id is not null");

	Vector<ClientConsignedInventoryBean> v = (Vector<ClientConsignedInventoryBean>)factory.selectQuery(query.toString());
	return v;
 }

 
  public ExcelHandler getExcelReport(ClientConsignedInventoryReportInputBean inputBean, Locale locale, PersonnelBean personnelBean) throws
  NoDataException, BaseException, Exception {
	  
	Collection<ClientConsignedInventoryBean> data = this.getSearchResult(inputBean);

	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);
	
	pw.addTable();
	//write column headers
	pw.addRow();
	/*Pass the header keys for the Excel.*/
	
	Vector<String> headerKeysV = new Vector<String>();
	Vector<Integer> typesV = new Vector<Integer>();
	Vector<Integer> widthsV = new Vector<Integer>();
	Vector<Integer> horizAlignsV = new Vector<Integer>();
	
    String[] headerkeys = null;
    int[] types = null;
    int[] widths = null;
    int[] horizAligns = null;
    boolean showQtyInCustomerUom = personnelBean.isFeatureReleased("ShowQtyInCustomerUom",inputBean.getFacilityId(),personnelBean.getCompanyId());
    boolean showTraceId = personnelBean.isFeatureReleased("DisplayChargeNoOwnerSeqment","ALL",personnelBean.getCompanyId());
    
    headerKeysV.add("label.inventorygroup");
    headerKeysV.add("label.part");
    headerKeysV.add("label.item");
    headerKeysV.add("label.description");
    headerKeysV.add("label.receiptid");
    if(showTraceId)
        headerKeysV.add("label.traceid");    	
    headerKeysV.add("label.qty");
    if (showQtyInCustomerUom) {
        headerKeysV.add("label.qtyinuom");
        headerKeysV.add("label.uom");
    }
    headerKeysV.add("label.currencyid");
    headerKeysV.add("label.unitprice");
    headerKeysV.add("label.extprice");
    headerKeysV.add("label.lot");
    headerKeysV.add("label.expiredate");
    headerKeysV.add("label.lotstatus");
    headerKeysV.add("label.chargeno1");
    headerKeysV.add("label.chargeno2");
    headerKeysV.add("label.chargeno3");
    headerKeysV.add("label.chargeno4");
    headerKeysV.add("label.owner");

    typesV.add(0);
    typesV.add(0);
    typesV.add(0);
    typesV.add(pw.TYPE_PARAGRAPH);
    typesV.add(0);
    if(showTraceId)
        typesV.add(0);
    typesV.add(pw.TYPE_NUMBER);
    if (showQtyInCustomerUom) {
        typesV.add(pw.TYPE_NUMBER);
        typesV.add(0);
    }
    typesV.add(0);
    typesV.add(pw.TYPE_NUMBER);
    typesV.add(pw.TYPE_NUMBER);
    typesV.add(0);
    typesV.add(pw.TYPE_CALENDAR);
    typesV.add(0);
    typesV.add(0);
    typesV.add(0);
    typesV.add(0);
    typesV.add(0);
    typesV.add(0);

    widthsV.add(0);
    widthsV.add(25);
    widthsV.add(10);
    widthsV.add(30);
    widthsV.add(12);
    if(showTraceId)
    	widthsV.add(12);
    widthsV.add(0);
    if (showQtyInCustomerUom) {
        widthsV.add(0);
        widthsV.add(0);
    }
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(14);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);
    widthsV.add(0);

    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    if(showTraceId)
        horizAlignsV.add(pw.ALIGN_RIGHT);
    horizAlignsV.add(0);
    if (showQtyInCustomerUom) {
        horizAlignsV.add(0);
        horizAlignsV.add(0);
    }
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    horizAlignsV.add(0);
    headerkeys = new String[headerKeysV.size()];
	headerkeys = headerKeysV.toArray(headerkeys);
	types = new int[typesV.size()];
    types =  ArrayUtils.toPrimitive(typesV.toArray(new Integer[typesV.size()]));
    widths = new int[widthsV.size()];
    widths = ArrayUtils.toPrimitive(widthsV.toArray(new Integer[widthsV.size()]));
    horizAligns = new int[horizAlignsV.size()];
    horizAligns = ArrayUtils.toPrimitive(horizAlignsV.toArray(new Integer[horizAlignsV.size()]));

	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	if(showQtyInCustomerUom && showTraceId)
	{
	    pw.setColumnDigit(10, 4);
	    pw.setColumnDigit(11, 2);
	}
	else if (showQtyInCustomerUom) {
	
	    pw.setColumnDigit(9, 4);
	    pw.setColumnDigit(10, 2);
    }
    else if(showTraceId)
    {
        pw.setColumnDigit(8, 4);
	    pw.setColumnDigit(9, 2);
    }
    else {
        pw.setColumnDigit(7, 4);
	    pw.setColumnDigit(8, 2);
    }

    String indefinite = library.getString("label.indefinite");
	SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
	
	for (ClientConsignedInventoryBean member : data) {
	  
	  pw.addRow();
	  pw.addCell(member.getInventoryGroupName());
	  pw.addCell(member.getCatPartNo());
	  pw.addCell(member.getItemId());
	  pw.addCell(member.getItemDesc());
	  pw.addCell(member.getReceiptId());
	  if(showTraceId)
		  pw.addCell(member.getCustomerReceiptId());
	  pw.addCell(member.getQuantity());
      if (showQtyInCustomerUom) {
        pw.addCell(member.getUosQuantity());
        pw.addCell(member.getUnitOfSale());
      }
      pw.addCell(member.getCurrencyId());
	  pw.addCell(member.getEstablishedUnitPrice());
	  pw.addCell(member.getExtPrice());
	  pw.addCell(member.getMfgLot());
	  if(member.getExpireDate() != null)
		  if("3000".equals(simpleDateformat.format(member.getExpireDate())))
			  pw.addCell(indefinite);
		  else
			  pw.addCell(member.getExpireDate());
	  else
		  pw.addCell("");
	  pw.addCell(member.getLotStatus());
	  pw.addCell(member.getAccountNumber());
	  pw.addCell(member.getAccountNumber2());
	  pw.addCell(member.getAccountNumber3());
	  pw.addCell(member.getAccountNumber4());
	  pw.addCell(member.getOwnerSegmentId());
	}
	return pw;
  }

}
