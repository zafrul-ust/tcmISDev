package com.tcmis.client.catalog.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.io.Writer;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.OrderTrackDetailHeaderViewBean;
import com.tcmis.client.catalog.beans.OrderTrackingInputBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackWebAllocationBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackWebNeededBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackWebPrOrderTrackDetailBean;
import com.tcmis.client.catalog.factory.OrderTrackDetailHeaderViewBeanFactory;
import com.tcmis.client.catalog.factory.PkgOrderTrackWebPrOrderTrackDetailBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.internal.distribution.beans.JdeOrderStageBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class MrAllocationReportProcess extends BaseProcess {

  Log log = LogFactory.getLog(this.getClass());

  public MrAllocationReportProcess(String client,String locale) {
    super(client,locale);
  }

  public OrderTrackDetailHeaderViewBean getOrderTrackDetailHeaderViewBean(String companyId, String mrNumber) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    Collection orderTrackDetailHeaderViewBeanColl = new Vector();
    OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean = new OrderTrackDetailHeaderViewBean();

    OrderTrackDetailHeaderViewBeanFactory factory = new OrderTrackDetailHeaderViewBeanFactory(dbManager);

    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
    criteria.addCriterion("prNumber", SearchCriterion.EQUALS, mrNumber);

    orderTrackDetailHeaderViewBeanColl = factory.select(criteria);

    Iterator i11 = orderTrackDetailHeaderViewBeanColl.iterator();
    String inventoryGroup = "";
    while (i11.hasNext()) {
      orderTrackDetailHeaderViewBean = (OrderTrackDetailHeaderViewBean) i11.next(); ;
    }

    return orderTrackDetailHeaderViewBean;
  }

	public Object[] getExtraInfo(Collection c) {
		//		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		Vector<PkgOrderTrackWebPrOrderTrackDetailBean> bv = (Vector<PkgOrderTrackWebPrOrderTrackDetailBean>) c; 
		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
	
		for (PkgOrderTrackWebPrOrderTrackDetailBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
		}
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = ""; 
			for (PkgOrderTrackWebPrOrderTrackDetailBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = ""; 
			for (PkgOrderTrackWebPrOrderTrackDetailBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}
		Object[] objs = {qualityIdLabelColumnHeader,catPartAttrColumnHeader};
		return objs;
	}

  public Collection getMrAllocationReport(String companyId, String mrNumber, String lineItem, String fromCustomerOrdertracking) throws BaseException,Exception {
    DbManager dbManager = new DbManager(getClient(),getLocale());

    PkgOrderTrackWebPrOrderTrackDetailBeanFactory factory = new PkgOrderTrackWebPrOrderTrackDetailBeanFactory(dbManager);

    Collection pkgOrderTrackWebPrOrderTrackDetailBeanCollection = factory.select(companyId,mrNumber,lineItem,fromCustomerOrdertracking);

    Collection finalpkgOrderTrackWebPrOrderTrackDetailBeanCollection = new Vector();
    String nextPartNumber = "";
    String nextLineItem = "";
    Date nextNeedDate = new Date();

    int samePartNumberLineCount = 0;
    Vector collectionVector = new Vector(pkgOrderTrackWebPrOrderTrackDetailBeanCollection);
    Vector neededDateV1 = new Vector();
    Vector allocatedQtyV1 = new Vector();
    for (int loop = 0; loop < collectionVector.size(); loop++) {
      PkgOrderTrackWebPrOrderTrackDetailBean currentPkgOrderTrackWebPrOrderTrackDetailBean = (PkgOrderTrackWebPrOrderTrackDetailBean) collectionVector.elementAt(loop);
      String currentPartNumber = currentPkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo();
      String currentLineItem = currentPkgOrderTrackWebPrOrderTrackDetailBean.getLineItem();
      Date currentNeedDate = currentPkgOrderTrackWebPrOrderTrackDetailBean.getRequiredDatetime();

      if (!((loop + 1) == collectionVector.size())) {
        PkgOrderTrackWebPrOrderTrackDetailBean nextPkgOrderTrackWebPrOrderTrackDetailBean = (PkgOrderTrackWebPrOrderTrackDetailBean) collectionVector.elementAt(loop + 1);

        nextPartNumber = nextPkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo();
        nextLineItem = nextPkgOrderTrackWebPrOrderTrackDetailBean.getLineItem();
        nextNeedDate = nextPkgOrderTrackWebPrOrderTrackDetailBean.getRequiredDatetime();

      } else {
        nextPartNumber = "";
        nextLineItem = "";
        nextNeedDate = new Date();
      }

      boolean samePartNumberLine = false;
      boolean sameNeedDate = false;

      if (currentPartNumber.equalsIgnoreCase(nextPartNumber) && currentLineItem.equalsIgnoreCase(nextLineItem)) {
        samePartNumberLine = true;
        samePartNumberLineCount++;
        if (nextNeedDate.equals(currentNeedDate) ) {
          sameNeedDate = true;
        }
      }

      PkgOrderTrackWebAllocationBean pkgOrderTrackWebAllocationBean = new PkgOrderTrackWebAllocationBean();
      pkgOrderTrackWebAllocationBean.setAllocatedQuantity(currentPkgOrderTrackWebPrOrderTrackDetailBean.getAllocatedQuantity());
      pkgOrderTrackWebAllocationBean.setAllocationReferenceDate(currentPkgOrderTrackWebPrOrderTrackDetailBean.getAllocationReferenceDate());
      pkgOrderTrackWebAllocationBean.setExpireDate(currentPkgOrderTrackWebPrOrderTrackDetailBean.getExpireDate());
      pkgOrderTrackWebAllocationBean.setLotStatus(currentPkgOrderTrackWebPrOrderTrackDetailBean.getLotStatus());
      pkgOrderTrackWebAllocationBean.setMfgLot(currentPkgOrderTrackWebPrOrderTrackDetailBean.getMfgLot());
      pkgOrderTrackWebAllocationBean.setRef(currentPkgOrderTrackWebPrOrderTrackDetailBean.getRef());
      pkgOrderTrackWebAllocationBean.setShipmentId(currentPkgOrderTrackWebPrOrderTrackDetailBean.getShipmentId());
      pkgOrderTrackWebAllocationBean.setCarrierName(currentPkgOrderTrackWebPrOrderTrackDetailBean.getCarrierName());
      pkgOrderTrackWebAllocationBean.setTrackingNumber(currentPkgOrderTrackWebPrOrderTrackDetailBean.getTrackingNumber());
		pkgOrderTrackWebAllocationBean.setReceiptDocument(currentPkgOrderTrackWebPrOrderTrackDetailBean.getReceiptDocument());
		pkgOrderTrackWebAllocationBean.setReceiptDocumentColl(currentPkgOrderTrackWebPrOrderTrackDetailBean.getReceiptDocumentColl());
		pkgOrderTrackWebAllocationBean.setRefType(currentPkgOrderTrackWebPrOrderTrackDetailBean.getRefType());
		pkgOrderTrackWebAllocationBean.setRefNumber(currentPkgOrderTrackWebPrOrderTrackDetailBean.getRefNumber());
		pkgOrderTrackWebAllocationBean.setMsdsColl(currentPkgOrderTrackWebPrOrderTrackDetailBean.getMsdsColl());
		pkgOrderTrackWebAllocationBean.setInvoiceColl(currentPkgOrderTrackWebPrOrderTrackDetailBean.getInvoiceColl());
		pkgOrderTrackWebAllocationBean.setReferenceDate(currentPkgOrderTrackWebPrOrderTrackDetailBean.getReferenceDate());
      pkgOrderTrackWebAllocationBean.setStatus(currentPkgOrderTrackWebPrOrderTrackDetailBean.getStatus());
      pkgOrderTrackWebAllocationBean.setNotes(currentPkgOrderTrackWebPrOrderTrackDetailBean.getNotes());
      pkgOrderTrackWebAllocationBean.setProgramId(currentPkgOrderTrackWebPrOrderTrackDetailBean.getProgramId());
      pkgOrderTrackWebAllocationBean.setOwnerSegmentId(currentPkgOrderTrackWebPrOrderTrackDetailBean.getOwnerSegmentId());

      allocatedQtyV1.add(pkgOrderTrackWebAllocationBean);

      if (sameNeedDate) {

      } else {
        PkgOrderTrackWebNeededBean pkgOrderTrackWebNeededBean = new PkgOrderTrackWebNeededBean();
        pkgOrderTrackWebNeededBean.setAllocationCollection((Vector) allocatedQtyV1.clone());
        allocatedQtyV1 = new Vector();

        pkgOrderTrackWebNeededBean.setOrderQuantity(currentPkgOrderTrackWebPrOrderTrackDetailBean.getOrderQuantity());
        pkgOrderTrackWebNeededBean.setRequiredDatetime(currentPkgOrderTrackWebPrOrderTrackDetailBean.getRequiredDatetime());

        neededDateV1.add(pkgOrderTrackWebNeededBean);
      }

      if (samePartNumberLine) {

      } else {
        currentPkgOrderTrackWebPrOrderTrackDetailBean.setNeedDateCollection((Vector) neededDateV1.clone());
        neededDateV1 = new Vector();
        currentPkgOrderTrackWebPrOrderTrackDetailBean.setRowSpan(new BigDecimal(samePartNumberLineCount + 1));

        finalpkgOrderTrackWebPrOrderTrackDetailBeanCollection.add(currentPkgOrderTrackWebPrOrderTrackDetailBean);
        samePartNumberLineCount = 0;

      }
    }

    //log.debug("Final collectionSize here " +finalpkgOrderTrackWebPrOrderTrackDetailBeanCollection.size() + "");
    return finalpkgOrderTrackWebPrOrderTrackDetailBeanCollection;
  }
  
  public Collection getJdeOrderInfo(BigDecimal sequenceNum) throws Exception {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	  GenericSqlFactory factory = new GenericSqlFactory(dbManager, new JdeOrderStageBean());

	  StringBuilder query = new StringBuilder("select * from JDE_ORDER_STAGE where JDE_ORDER_NO = ");
	  query.append(sequenceNum);
		
	  return factory.selectQuery(query.toString());
  }

  public ExcelHandler  getExcelReport(String companyId, String mrNumber, String lineItem, Locale locale, String fromCustomerOrdertracking,PersonnelBean personnel) throws NoDataException, BaseException, Exception {
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

   OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean = new OrderTrackDetailHeaderViewBean();
   orderTrackDetailHeaderViewBean = this.getOrderTrackDetailHeaderViewBean(companyId,mrNumber);

   Collection data = this.getMrAllocationReport(companyId,mrNumber, lineItem,fromCustomerOrdertracking);

   Iterator iterator = data.iterator();
   ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  pw.addRow();
	  pw.addCellKeyBold("label.facility");
   pw.addCell(orderTrackDetailHeaderViewBean.getFacilityId());
   pw.addCellKeyBold("label.mrnumber");
   pw.addCell(orderTrackDetailHeaderViewBean.getPrNumber());
   pw.addCellKeyBold("label.submitdate");
   pw.addCell(orderTrackDetailHeaderViewBean.getSubmittedDate());
   
   pw.addRow();
   pw.addCellKeyBold("label.requestor");
   pw.addCell(orderTrackDetailHeaderViewBean.getRequestorName());
   pw.addCellKeyBold("mrallocationreport.label.financeapprover");
   pw.addCell(orderTrackDetailHeaderViewBean.getFinanceApproverName());
   pw.addCellKeyBold("mrallocationreport.label.releaser");
   pw.addCell(orderTrackDetailHeaderViewBean.getReleaserName());
   
   boolean isDisplayChangeNoOwnerSegment = personnel.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL",companyId);

   //blank row
   pw.addRow();
   
   //result table
   
   //write column headers
   pw.addRow();
   
   ArrayList<String> hk = new ArrayList<String>();
   hk.add("mrallocationreport.label.mrline");
   hk.add("label.customerpo");
   hk.add("mrallocationreport.label.useapprover");
   hk.add("mrallocationreport.label.releasedate");
   hk.add("label.workarea");
   hk.add("label.shipto");
   hk.add("label.deliverto");
   hk.add("label.partnumber");
   hk.add("label.type");
   hk.add("label.partdesc");
   hk.add("label.packaging");
   hk.add("label.deliverytype");
   hk.add("label.needed");
   hk.add("label.orderedqty");
   hk.add("label.allocatedqty");
   hk.add("label.status");
   hk.add("mrallocationreport.label.ref");
   hk.add("label.carrier");
   hk.add("label.trackingno");
   hk.add("label.projecteddeliverydate");
   hk.add("mrallocationreport.label.lot");
   if(isDisplayChangeNoOwnerSegment)
   {
	   hk.add("label.owner");
	   hk.add("label.program");
   }
   
   hk.add("label.expdate");
   hk.add("label.notes");
   
	String [] headerkeys = new String[hk.size()];
	headerkeys = hk.toArray(headerkeys);
	
	ArrayList<Integer> t = new ArrayList<Integer>();
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_DATE);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
   
	int[] types = new int[t.size()];
	types = ArrayUtils.toPrimitive(t.toArray(new Integer[t.size()]));
	
	ArrayList<Integer> w = new ArrayList<Integer>();
	w.add(12);
	w.add(16);
	w.add(12);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(17);
	w.add(0);
	w.add(20);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(12);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	int[] widths = new int[w.size()];
	widths = ArrayUtils.toPrimitive(w.toArray(new Integer[w.size()]));
	
	ArrayList<Integer> ha = new ArrayList<Integer>();
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	int[] horizAligns  = new int[ha.size()];
	horizAligns = ArrayUtils.toPrimitive(ha.toArray(new Integer[ha.size()]));
	
    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

    Object[] results = getExtraInfo(data);
    String qualityIdLabelColumnHeader = (String)results[0];
    String catPartAttrColumnHeader = (String)results[1];
	   if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
			pw.addCellBold(qualityIdLabelColumnHeader);
	   if(!"--Hide--".equals(catPartAttrColumnHeader) )
			pw.addCellBold(catPartAttrColumnHeader);
   //now write data
	   PkgOrderTrackWebPrOrderTrackDetailBean pkgOrderTrackWebPrOrderTrackDetailBean = null;
   while (iterator.hasNext()) {
 //    int mainRowSpan = pkgOrderTrackWebPrOrderTrackDetailBean.getRowSpan().intValue();
	   pkgOrderTrackWebPrOrderTrackDetailBean = (PkgOrderTrackWebPrOrderTrackDetailBean) iterator.next();
     Collection needDateCollection = pkgOrderTrackWebPrOrderTrackDetailBean.getNeedDateCollection();

     pw.addRow();
// fill first segement, first level     
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
	  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
	  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());

  // fill second segement, middle level loop.
  // the code for no middle loop, fill with blanks. is right after the while loop.
     int neededDateCount = 0;
     Iterator i11NeedDate = needDateCollection.iterator();
     PkgOrderTrackWebNeededBean pkgOrderTrackWebNeededBean = null;
     while (i11NeedDate.hasNext()) {
       neededDateCount++;
       pkgOrderTrackWebNeededBean = (PkgOrderTrackWebNeededBean) i11NeedDate.next();
       // if more than one row, needs to fill the last segment, a new row before fill second segment data.
       if (neededDateCount > 1 ) {
    	 if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
      		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getQualityId());//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
         if(!"--Hide--".equals(catPartAttrColumnHeader) )
      		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getCatPartAttribute());
         pw.addRow();
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
			pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	  		pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
			pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());
       }
       Collection allocQtyCollection = pkgOrderTrackWebNeededBean.getAllocationCollection();
       int allocationSize = allocQtyCollection.size();
       pw.addCell(pkgOrderTrackWebNeededBean.getRequiredDatetime());
       pw.addCell(pkgOrderTrackWebNeededBean.getOrderQuantity());

       int allocationCount = 0;
       PkgOrderTrackWebAllocationBean pkgOrderTrackWebAllocationBean = null;
       Iterator i11Allocation = allocQtyCollection.iterator();
       while (i11Allocation.hasNext()) {
         allocationCount++;
         pkgOrderTrackWebAllocationBean = (PkgOrderTrackWebAllocationBean) i11Allocation.next();
         // if more than one row, needs to fill the last segment, a new row and first segment data before fill second segment data.
         if (allocationCount >1) {
           if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
        		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getQualityId());//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
           if(!"--Hide--".equals(catPartAttrColumnHeader) )
        		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getCatPartAttribute());
           pw.addRow();
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
			  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	  		  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
			  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());
           pw.addCell(pkgOrderTrackWebNeededBean.getRequiredDatetime());
           pw.addCell(pkgOrderTrackWebNeededBean.getOrderQuantity());
         }
         pw.addCell(pkgOrderTrackWebAllocationBean.getAllocatedQuantity());
         pw.addCell(pkgOrderTrackWebAllocationBean.getStatus());
         pw.addCell(pkgOrderTrackWebAllocationBean.getRef());
         pw.addCell(pkgOrderTrackWebAllocationBean.getCarrierName());
	     pw.addCell(pkgOrderTrackWebAllocationBean.getTrackingNumber());
	     pw.addCell(pkgOrderTrackWebAllocationBean.getAllocationReferenceDate());
         pw.addCell(pkgOrderTrackWebAllocationBean.getMfgLot());
		   if(isDisplayChangeNoOwnerSegment)
		   {
		        pw.addCell(pkgOrderTrackWebAllocationBean.getOwnerSegmentId());
		        pw.addCell(pkgOrderTrackWebAllocationBean.getProgramId());
		   }	
         pw.addCell(pkgOrderTrackWebAllocationBean.getExpireDate());
         pw.addCell(pkgOrderTrackWebAllocationBean.getNotes());
       } //end of inner loop
       // 	if no inner loop, fill with blanks. )
       if( pkgOrderTrackWebAllocationBean == null )
           fillInnerLoopBlanks(pw,isDisplayChangeNoOwnerSegment);
       // fill middle loop data after the inner loop here.
       // for our case, there is nothing.
       // at this point, the up to segement 3 ( to the end of inner loop ) are gauranteed filled. if there are any second level object 
     } //end of middle loop
     // if no middle loop, fill with blanks. ) 
     if( pkgOrderTrackWebNeededBean == null ) {
         pw.addCell("");
         pw.addCell("");
         fillInnerLoopBlanks(pw,isDisplayChangeNoOwnerSegment);
     }
     // at this point, all segment up to last segment are filled.
	 if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getQualityId());//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
	 if(!"--Hide--".equals(catPartAttrColumnHeader) )
		 pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getCatPartAttribute());
   } //end of outter loop
   return pw;
 } //end of method

  public void fillInnerLoopBlanks(ExcelHandler pw,boolean isDisplayChangeNoOwnerSegment) {
	  pw.addCell("");
	  pw.addCell("");
	  pw.addCell("");
			pw.addCell("");
	   pw.addCell("");
			pw.addCell("");
	  pw.addCell("");
		   if(isDisplayChangeNoOwnerSegment)
		   {
		        pw.addCell("");
		        pw.addCell("");
		   }	
	  pw.addCell("");
	  pw.addCell("");
  }
  public ExcelHandler  getExcelReportfromCustomerOrdertracking(String companyId, String mrNumber, String lineItem, Locale locale, String fromCustomerOrdertracking) throws NoDataException, BaseException, Exception {
	   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

	   OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean = new OrderTrackDetailHeaderViewBean();
	   orderTrackDetailHeaderViewBean = this.getOrderTrackDetailHeaderViewBean(companyId,mrNumber);

	   Collection data = this.getMrAllocationReport(companyId,mrNumber, lineItem,fromCustomerOrdertracking);

	   Iterator iterator = data.iterator();
	   ExcelHandler pw = new ExcelHandler(library);
		  
	   pw.addTable();

//		  write column headers
	   pw.addRow();
	   
	   pw.addCellKeyBold("label.mrnumber");
	   pw.addCell(orderTrackDetailHeaderViewBean.getPrNumber());
	   pw.addCellKeyBold("label.submitdate");
	   pw.addCell(orderTrackDetailHeaderViewBean.getSubmittedDate());
	   
	   //blank row
	   pw.addRow();
	   
	   //result table
	   
	   //write column headers
	   pw.addRow();
	   
	  	   String[] headerkeys = {
			      "mrallocationreport.label.mrline","label.customerpo","mrallocationreport.label.useapprover", "mrallocationreport.label.releasedate","label.workarea","label.shipto","label.deliverto",
			      "label.partnumber","label.type","label.partdesc", "label.packaging", "label.deliverytype",
			      "label.needed","label.orderedqty","label.allocatedqty","label.status","mrallocationreport.label.ref",
			      "label.carrier","label.trackingno",
			      "label.projecteddeliverydate","mrallocationreport.label.lot","label.expdate","label.notes"};
	   
			    /*This array defines the type of the excel column.
			    0 means default depending on the data type. */
			    int[] types = {
			      0,0,0,pw.TYPE_DATE,0,0,0,
			      0,0,0,0,0,
			      pw.TYPE_CALENDAR,0,0,0,0,
			      0,0,
			      pw.TYPE_CALENDAR,0,pw.TYPE_CALENDAR,0};
			    /*This array defines the default width of the column when the Excel file opens.
			    0 means the width will be default depending on the data type.*/
			    int[] widths = {
			      12,16,12,0,0,0,0,
			      17,0,20,20,0,
			      0,0,12,0,15,
			      0,0,
			      0,0,0,20};
			    /*This array defines the horizontal alignment of the data in a cell.
			    0 means excel defaults the horizontal alignemnt by the data type.*/
			    int[] horizAligns = {
			      0,0,0,0,0,0,0,
			      0,0,0,0,0,
			      0,0,0,0,0,
			      0,0,
			      0,0,0,0};
			      
			    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

	   //now write data
	   while (iterator.hasNext()) {
	     PkgOrderTrackWebPrOrderTrackDetailBean pkgOrderTrackWebPrOrderTrackDetailBean = (PkgOrderTrackWebPrOrderTrackDetailBean) iterator.next();
	     int mainRowSpan = pkgOrderTrackWebPrOrderTrackDetailBean.getRowSpan().intValue();
	     Collection needDateCollection = pkgOrderTrackWebPrOrderTrackDetailBean.getNeedDateCollection();

	     pw.addRow();
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
		  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
		  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
	     pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());

	     int neededDateCount = 0;
	     Iterator i11NeedDate = needDateCollection.iterator();
	     while (i11NeedDate.hasNext()) {
	       neededDateCount++;
	       PkgOrderTrackWebNeededBean pkgOrderTrackWebNeededBean = (PkgOrderTrackWebNeededBean) i11NeedDate.next();
	       if (neededDateCount > 1 && needDateCollection.size() > 1) {
	         pw.addRow();
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
				pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	  			pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
				pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
	         pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());
	       }
	       Collection allocQtyCollection = pkgOrderTrackWebNeededBean.getAllocationCollection();
	       int allocationSize = allocQtyCollection.size();
	       pw.addCell(pkgOrderTrackWebNeededBean.getRequiredDatetime());
	       pw.addCell(pkgOrderTrackWebNeededBean.getOrderQuantity());

	       int allocationCount = 0;
	       Iterator i11Allocation = allocQtyCollection.iterator();
	       while (i11Allocation.hasNext()) {
	         allocationCount++;
	         PkgOrderTrackWebAllocationBean pkgOrderTrackWebAllocationBean = (PkgOrderTrackWebAllocationBean) i11Allocation.next();
	         if (allocationCount > 1 && allocationSize > 1) {
	           pw.addRow();
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLineItem());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPoNumber());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getUseApproverName());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getReleaseDate());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getApplicationDesc());
				  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getLocationDesc());
	  			  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryPointDesc());
				  pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getFacPartNo());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getItemType());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPartDescription());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getPackaging());
	           pw.addCell(pkgOrderTrackWebPrOrderTrackDetailBean.getDeliveryType());
	           pw.addCell(pkgOrderTrackWebNeededBean.getRequiredDatetime());
	           pw.addCell(pkgOrderTrackWebNeededBean.getOrderQuantity());
	         }
	         pw.addCell(pkgOrderTrackWebAllocationBean.getAllocatedQuantity());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getStatus());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getRef());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getCarrierName());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getTrackingNumber());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getAllocationReferenceDate());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getMfgLot());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getExpireDate());
	         pw.addCell(pkgOrderTrackWebAllocationBean.getNotes());
	         if (allocationCount > 1 || allocationSize == 1) {
	           
	         }
	       } //end of inner loop
	     } //end of middle loop
	   } //end of outter loop
	   return pw;
	 } //end of method

  private boolean validateInput(OrderTrackingInputBean bean) {
    /*
     if (bean == null ||
     bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
     bean.getPersonnelId() == 0 ||
     bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
     bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
      return false;
     }
     else {
      return true;
     }
     */
    return true;
  }

}