
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/poexpeditingresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

    <title>
        <fmt:message key="poexpediting.title"/>
    </title>
    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
        with(milonic=new menuname("history")){
         top="offset=2"
         style = contextStyle;
         margin=3
            aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPurchOrder();");
            aI("text=<fmt:message key="label.showhistory"/>;url=javascript:showHistory();");
            aI("text=<fmt:message key="label.showitemhistory"/>;url=javascript:showItemHistory();");
            aI("text=<fmt:message key="label.viewaddpodocuments"/>;url=javascript:showPODocuments();");
           
    }

    drawMenus();
// -->
    </script>
    <script language="JavaScript" type="text/javascript">
        <!--
        //add all the javascript messages here, this for internationalization of client side javascript messages
        var messagesData = new Array();
        messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            haaspoline:"<fmt:message key="label.haaspoline"/>",
            supplier:"<fmt:message key="label.supplier"/>",
            partnumber:"<fmt:message key="label.partnumber"/>",
            itemdescription:"<fmt:message key="label.itemdescription"/>",
            buyer:"<fmt:message key="label.buyer"/>",
            itemid:"<fmt:message key="label.itemid"/>",
            supplypath:"<fmt:message key="label.supplypath"/>",
            carrier:"<fmt:message key="label.carrier"/>",
            dateconfirmed:"<fmt:message key="label.dateconfirmed"/>",
            needdate:"<fmt:message key="label.needdate"/>",
            quantityopen:"<fmt:message key="label.quantityopen"/>",
            qtyalloctomrs:"<fmt:message key="label.qtyalloctomrs"/>",
            qtyreceived:"<fmt:message key="label.qtyreceived"/>",
            unitprice:"<fmt:message key="label.unitprice"/>",
            hub:"<fmt:message key="label.hub"/>",
            inventorygroup:"<fmt:message key="label.inventorygroup"/>",
            originalpromiseddate:"<fmt:message key="label.oriprojecteddelivery"/>",
            ok:"<fmt:message key="label.ok"/>",                       
            revisedpromisedate:"<fmt:message key="label.revprojecteddelivery"/>",
            dateofrevision:"<fmt:message key="label.dateofrevision"/>",
            expediteage:"<fmt:message key="label.expediteage"/>",
            comments:"<fmt:message key="label.comments"/>",
            company:"<fmt:message key="label.company"/>",
            laastupdateddate:"<fmt:message key="label.laastUpdatedDate"/>",
            lastupdatedby:"<fmt:message key="label.lastUpdatedBy"/>",
            maximum2000:"<fmt:message key="label.maximum2000"/>",    
            shipto:"<fmt:message key="label.shipto"/>",        
            customerpo:"<fmt:message key="label.customerpo"/>",
            purchaseorder:"<fmt:message key="label.purchaseorder"/>",
            dayslate:"<fmt:message key="label.daysLate"/>",
            vendorshipdate:"<fmt:message key="label.promised.ship.date"/>",
            revisedPromisedDate:"<fmt:message key="label.revprojecteddelivery"/>",
            pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
            credithold:"<fmt:message key="label.credithold"/>",
            supplierdateaccepted:"<fmt:message key="label.supplierdateaccepted"/>"
                };
        // -->
    </script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/poexpeditingresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>
<c:if test="${param.maxData == fn:length(poExpediteViewBeanColl)}">
 <fmt:message key="label.maxdata">
  <fmt:param value="${param.maxData}"/>
 </fmt:message>
</c:if>
            
</div>

<script type="text/javascript">
<c:choose>
<c:when test="${empty tcmISErrors and empty tcmISError}">
 <c:choose>
  <c:when test="${param.maxData == fn:length(poExpediteViewBeanColl)}">
    showErrorMessage = true;
    parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
  </c:when>
  <c:otherwise>
    showErrorMessage = false;
  </c:otherwise>
 </c:choose>
</c:when>
<c:otherwise>
  parent.messagesData.errors = "<fmt:message key="label.errors"/>";
  showErrorMessage = true;
  </c:otherwise>
</c:choose>
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="poLineExpediteDateBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${poExpediteViewBeanColl != null}">
<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>

<c:set var="opsEntityIdVal" value=''/>
<c:if test="${!empty poExpediteViewBeanColl}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="poLineExpediteDateBean" items="${poExpediteViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtOrderDate" value="${status.current.orderDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtPromisedDate" value="${status.current.promisedDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtRevisedPromisedDate" value="${status.current.revisedPromisedDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtLastRevised" value="${status.current.lastRevised}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtVendorShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
<c:set var="orderDatSortable" value="${status.current.orderDate.time}"/>
<c:set var="needDateSortable" value="${status.current.needDate.time}"/>
<c:set var="promisedDateSortable" value="${status.current.promisedDate.time}"/>
<c:set var="revisedPromisedDateSortable" value="${status.current.revisedPromisedDate.time}"/>
<c:set var="lastRevisedSortable" value="${status.current.lastRevised.time}"/>
<c:set var="expediteAgeSortable" value="${status.current.expediteAge}"/>
<c:set var="vendorShipDateSortable" value="${status.current.vendorShipDate.time}"/>

<fmt:formatNumber var="fmtUnitPrice" value="${status.current.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}" processMultiLines="true" />
<tcmis:jsReplace var="expediteComments" value="${status.current.expediteComments}" processMultiLines="true" />  
<tcmis:jsReplace var="shipToAddress" value="${status.current.shipToAddress}" processMultiLines="true" />
<tcmis:jsReplace var="supplierName" value="${status.current.supplierName}" processMultiLines="true" />
<tcmis:jsReplace var="buyerName" value="${status.current.buyerName}" processMultiLines="false" />
<tcmis:jsReplace var="lastRevisedBy" value="${status.current.lastRevisedBy}" processMultiLines="false" />
<tcmis:jsReplace var="partNumber" value="${status.current.partNumber}" processMultiLines="false" />

<c:set var="readonly" value='N'/>
<c:set var="creditHold" value='false'/>
<c:if test="${status.current.creditHold eq 'Y'}">
	<c:set var="creditHold" value='true'/>
</c:if>
<%--tcmis:opsEntityPermission indicator="true" userGroupId="updateExpediteNotes" opsEntityId="${status.current.opsEntityId}">
 <c:set var="readonly" value='Y'/>
 <c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission--%>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="readonly" value='Y'/>
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:set var="opsEntityIdVal" value='${status.current.opsEntityId}'/>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${readonly}','${status.current.radianPo}-${status.current.poLine}',
  '${supplierName}','${partNumber}',
  '${status.current.itemId}',  '${itemDesc}','${buyerName}',
  '${status.current.supplyPath}','${fmtOrderDate}',
  '${fmtNeedDate}','${status.current.quantityOpen}', 
  '${status.current.mrAlloc}','${status.current.qtyReceived}',   
  '${status.current.currencyId} ${fmtUnitPrice}',
  '${status.current.inventoryGroup}', '${fmtVendorShipDate}','${fmtPromisedDate}',
  '',
  '${fmtRevisedPromisedDate}','${expediteComments}',${creditHold},
  '${fmtLastRevised}','${lastRevisedBy}','${status.current.expediteAge} <fmt:message key="label.days"/>' ,
   <c:choose>
    <c:when test="${status.current.daysLate > 0}">
  '${status.current.daysLate} <fmt:message key="label.days"/>' ,
    </c:when>
    <c:otherwise>
     '' ,
    </c:otherwise>
   </c:choose>
  '${status.current.companyId}','${status.current.customerPo}', 
  '${shipToAddress}', '${status.current.carrier}',
  '${status.current.hubName}', 
  '${orderDatSortable}','${needDateSortable}','${promisedDateSortable}',
  '${lastRevisedSortable}', '${status.current.radianPo}','${status.current.poLine}',
   '${expediteAgeSortable}','${revisedPromisedDateSortable}','${status.current.daysLate}','${vendorShipDateSortable}',
  '${status.current.supplierDateAccepted.time}',
  '<fmt:formatDate value="${status.current.supplierDateAccepted}" pattern="${dateFormatPattern}"/>'
   ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->  
</script>

        <!-- If the collection is empty say no data found -->
        <c:if test="${empty poExpediteViewBeanColl}">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                <tr>
                    <td width="100%">
                        <fmt:message key="main.nodatafound"/>
                    </td>
                </tr>
            </table>
        </c:if>
        <!-- Search results end -->
    </c:if>

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="action" id="action" value="" type="hidden">     
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <c:forEach var="hub" items="${paramValues.hubIdArray}">
			<input name="hubIdArray" id="hubIdArray" value="${hub}" type="hidden"/>
	    </c:forEach>
        <input name="inventoryGroup" id="dodaacType" type="hidden" value="${param.inventoryGroup}">
        <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
        <input name="buyerId" id="buyerId" type="hidden" value="${param.buyerId}">
        <input name="creditHold" id="creditHold" type="hidden" value="${param.creditHold}">
        <input name="supplier" id="supplier" type="hidden" value="${param.supplier}">
        <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
        <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
        <input name="shipFromDate" id="shipFromDate" type="hidden" value="${param.shipFromDate}">
        <input name="shipToDate" id="shipToDate" type="hidden" value="${param.shipToDate}">
        <input name="orderFromDate" id="orderFromDate" type="hidden" value="${param.orderFromDate}">
        <input name="orderToDate" id="orderToDate" type="hidden" value="${param.orderToDate}">
        <input name="revisedPromisedFromDate" id="revisedPromisedFromDate" type="hidden" value="${param.revisedPromisedFromDate}">
        <input name="revisedPromisedToDate" id="revisedPromisedToDate" type="hidden" value="${param.revisedPromisedToDate}">
        <input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">
        <input name="expediteAge" id="expediteAge" type="hidden" value="${param.expediteAge}">
         <input name="opsEntity" id="opsEntity" type="hidden" value="${opsEntityIdVal}">
         <input name="maxData" id="maxData" type="hidden" value="${param.maxData}"/>
        <!-- Popup Calendar input options -->
		<input type="hidden" name="blockBefore_revisedPromisedDate" id="blockBefore_revisedPromisedDateDisplay" value=""/>
		<input type="hidden" name="blockAfter_revisedPromisedDate" id="blockAfter_revisedPromisedDate" value=""/>
		<input type="hidden" name="blockBeforeExclude_revisedPromisedDate" id="blockBeforeExclude_revisedPromisedDate" value=""/>
		<input type="hidden" name="blockAfterExclude_revisedPromisedDate" id="blockAfterExclude_revisedPromisedDate" value=""/>
		<input type="hidden" name="inDefinite_revisedPromisedDate" id="inDefinite_revisedPromisedDate" value=""/>
		
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>