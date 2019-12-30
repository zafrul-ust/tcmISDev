<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--

<c:set var="allowCurrencyupdate" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="chgCurrency" facilityId="${POHeaderBean.hubName}">
     <c:set var="allowCurrencyupdate" value="Y" />
</tcmis:facilityPermission>

<c:set var="purchasing" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="Purchasing" facilityId="${POHeaderBean.hubName}">
	<c:set var="purchasing" value="Y" />
</tcmis:facilityPermission>
	
<c:set var="readonly" value="N" />
<c:if test="${readOnlyGrids != null && readOnlyGrids}">
    <c:set var="readonly" value="Y" />
</c:if>

<c:if test="${purchasing == 'N' }">
   <c:set var="readonly" value="Y" />
</c:if>

<c:if test="${POHeaderBean == null }">
    <c:set var="readonly" value="N" />
</c:if>

<c:set var="vouchered" value="${POHeaderBean.vouchered}"/>
<c:set var="vouchered" value ='${fn:toUpperCase(vouchered)}'/>

//allowCurrencyupdate1=<c:out value="${allowCurrencyupdate}"/>
//vouchered1=<c:out value="${vouchered}"/>


var relineItemJsonData = new Array();

<c:set var="lineItemDataCnt" value='${0}'/>
relineItemJsonData = {
        rows:[
        <c:forEach var="bean" items="${poLineDetailCol}" varStatus="listStatus">
        <fmt:formatDate var="needDate" value="${bean.needDate}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="promisedDate" value="${bean.vendorShipDate}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="projectedDate" value="${bean.promisedDate}" pattern="${dateFormatPattern}"/>       
        <fmt:formatDate var="lastConfirmed" value="${bean.lastConfirmed}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="dateItemVerified" value="${bean.dateItemVerified}" pattern="${dateFormatPattern}"/>
		<fmt:formatDate var="dateConfirmed" value="${bean.dateConfirmed}" pattern="${dateFormatPattern}"/>

        <c:set var="blnWriteAccess" value='Y'/>
        <c:if test="${bean.blnPoLineReadOnly == true}">
           <c:set var="blnWriteAccess" value='N'/>
        </c:if>
        <c:set var="blnItemIdEditable" value='Y'/>
        <c:if test="${bean.itemIdEditable == false}">
           <c:set var="blnItemIdEditable" value='N'/>
        </c:if>
        <c:set var="blnMaterialItem" value='N'/>
        <c:if test="${bean.isMaterialTypeItem == 'Y'}">
           <c:set var="blnMaterialItem" value='Y'/>
        </c:if>
        <c:set var="blnNotVouchered" value='Y'/>
        <c:if test="${bean.quantityVouchered != '' && bean.quantityVouchered > 0}">
            <c:set var="blnNotVouchered" value='N'/>
        </c:if>
        <c:set var="editCurrency" value="Y" />
        <c:if test="${readonly == 'Y' || allowCurrencyupdate == 'N' || vouchered == 'Y'}">
           <c:set var="editCurrency" value="N" />
        </c:if>
        
        <c:if test="${listStatus.index > 0}">,</c:if>
        {id:${listStatus.index+1},
         data:[
          '${blnWriteAccess}',
          '${bean.lineAmendmentNum}',   
          '${bean.ghsCompliant}',      
          '${bean.itemId}',
          '<c:if test="${blnItemIdEditable == 'Y'}"><input name="lookupItemId" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="getItemDetail();"></c:if>',
          '${bean.itemType}',
          '<c:choose><c:when test="${blnMaterialItem == 'Y' && blnWriteAccess == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
          '${needDate}',  //need date
          '<c:choose><c:when test="${blnMaterialItem == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
          '${promisedDate}', //promised date
          '<c:choose><c:when test="${blnMaterialItem == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
          '${projectedDate}',  //projected date
          //'${blnNotVouchered}',
          'Y', //Qty always editable
          '${bean.quantity}',
          '<tcmis:jsReplace value="${bean.packaging}"/>',
          '${blnNotVouchered}',
          '${bean.unitPrice}',
          //'<c:if test="${blnWriteAccess == 'N' && blnNotVouchered == 'N'}">${bean.unitPrice} ${bean.currencyId}</c:if><c:if test="${blnWriteAccess == 'Y' && blnNotVouchered == 'Y'}">${bean.unitPrice}</c:if>',
          '${bean.extendedPrice}',          
          //'<c:if test="${bean.extendedPrice > 0}"> ${bean.extendedPrice} ${bean.currencyId}</c:if>',
          '${editCurrency}', // currency permission
          '${bean.currencyId}',
          '${bean.quantityReceived}',
          '${bean.quantityVouchered}',
          '${bean.quantityReturned}',
          '${bean.status}',
          '${bean.amendment}',
          '${bean.poLineStatus}',
          '${bean.poLine}',                         //full po line number in 1000's
          '${bean.isMaterialTypeItem}',
          '${lastConfirmed}',
          '${bean.secondarySupplierAddress}',
          '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
          '${bean.mfgPartNo}',
          '${bean.supplierPartNo}',
          '${bean.dpasRating}',
          '${bean.remainingShelfLifePercent}',
          '${bean.shelfLifeBasisMsg}',
          '${bean.shipFromLocationId}',
          '${dateItemVerified}',
          '${bean.itemVerifiedBy}',
          '<tcmis:jsReplace value="${bean.poLineNote}" processMultiLines="true"/>',
          '<tcmis:jsReplace value="${bean.deliveryComments}" processMultiLines="true"/>',
          '${bean.everConfirmed}',
          '${bean.validItem}',
          '${bean.differentSupplierOnLine}',
          '${bean.secondarySupplierOnPo}',
          '${bean.changeSupplier}',
          '${bean.unitPrice}',
          '${bean.shipFromLocationId}',
          '<tcmis:jsReplace value="${bean.relatedPoLines}" processMultiLines="true"/>',
          '${bean.prShipTo}',
          '${bean.poLineNumber}',       //poline in units. not in 1000's.
          'N',
          '',
          '',
          '${bean.canAssignAddCharge}',
          '${bean.supplierQty}',
          '${bean.supplierUnitPrice}',
          '${bean.supplierExtPrice}',
          '${bean.lineTotalPrice}',
          '${bean.unitPrice}',
          '${bean.extendedPrice}',          
          '${needDate}',  //old need date
          '${promisedDate}', //old promised date
          '${projectedDate}',  //old projected date
          '${bean.msdsRequestedDate}', //send msds checkbox value
          'N',
          'N',
          'N',
          'N',
          '${bean.quantityVouchered}',
          '${bean.supplier}',
          '${bean.itemId}',
          '${bean.quantity}',
          '${bean.unitPrice}',
          '${bean.currencyId}',
          '${bean.supplierPartNo}',
          '${bean.dpasRating}',
          '${bean.remainingShelfLifePercent}',
          '${bean.currencyConversionRate}',
          '${bean.poLineClosed}',
          '${dateConfirmed}',
          '${lineItemDataCnt}',
          '${bean.amendment}'
          ]}
         <c:set var="lineItemDataCnt" value='${lineItemDataCnt+1}'/>
         </c:forEach>
        ]};

var newChargeLineItemJsonData = new Array();
<c:set var="poChrgLineItemListColCount" value='${0}'/>
var newChargeLineItemJsonData = {
        rows:[
        <c:forEach var="bean" items="${poLineItemListCol}" varStatus="poLineItemListStatus">                    
            <c:if test="${poLineItemListStatus.index > 0}">,</c:if>
            {id:${poLineItemListStatus.index+1},
             data:[
              'Y',
              false,         
              '${bean.poLineNumber}',
              '${bean.itemId}',
              '${bean.description}',
              '${bean.poLine}',
              '',
              'N'
              ]}
             <c:set var="poChrgLineItemListColCount" value='${poChrgLineItemListColCount+1}'/>
         </c:forEach>
        ]};

 parent.reloadPoLineData(relineItemJsonData,newChargeLineItemJsonData);
// -->
</script>
</head>
<body></body>
</html>

