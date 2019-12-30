<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<script type="text/javascript" src="/js/common/report/clientconsignedinventoryreport.js"></script>

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
    <fmt:message key="consignedinventoryreport.title"/>
</title>


<tcmis:inventoryGroupPermission indicator="true" userGroupId="releaseOrder">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission> 

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
cancelmrreason:"<fmt:message key="label.cancelmrreason"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
open:"<fmt:message key="label.open"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="ShowQtyInCustomerUom" value="${tcmis:isFeatureReleased(personnelBean,'ShowQtyInCustomerUom', param.facilityId)}"/>
var config = [
{ columnId:"inventoryGroupName",
  columnName:'<fmt:message key="label.inventorygroup"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"clientPartNos",
  columnName:'<fmt:message key="label.part"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  tooltip:"Y",
  width:5
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  width:20
},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>',
  sorting:'int',
  align:"right",
  width:6
},
<c:if test="${tcmis:isFeatureReleased(personnelBean,'DisplayChargeNoOwnerSeqment', 'ALL')}">
{ columnId:"traceId",
  columnName:'<fmt:message key="label.traceid"/>',
  align:"right",
  width:6
},
</c:if>
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  sorting:'int',
  align:"right",
  width:5
},
{ columnId:"uosQuantity",
  <c:if test="${ShowQtyInCustomerUom == 'true'}">
    columnName:'<fmt:message key="label.qtyinuom"/>',
  </c:if>
  sorting:'int',
  align:"right",
  width:5
},
{ columnId:"unitOfSale",
  <c:if test="${ShowQtyInCustomerUom == 'true'}">
    columnName:'<fmt:message key="label.uom"/>',
  </c:if>
  sorting:'int',
  align:"right",
  width:5
},
{ columnId:"unitPriceDisplay",
  columnName:'<fmt:message key="label.unitprice"/>',
  hiddenSortingColumn:'unitPrice',
  sorting:'int'
},
{ columnId:"unitPrice",
  sorting:'int'
},
{ columnId:"extPriceDisplay",
  columnName:'<fmt:message key="label.extprice"/>',
  hiddenSortingColumn:'extPrice',
  sorting:'int'
},
{ columnId:"extPrice",
  sorting:'int'
},
{ columnId:"mfgLot",
  columnName:'<fmt:message key="label.lot"/>',
  tooltip:"Y",
  width:10
},
{
  columnId:"expireDate",
  columnName:'<fmt:message key="label.expiredate"/>',
  hiddenSortingColumn:'hiddenExpireDateTime',
  sorting:'int'
},
{ 
  columnId:"hiddenExpireDateTime",
  sorting:'int'
},
{ columnId:"lotStatus",
  columnName:'<fmt:message key="label.lotstatus"/>',
  width:10
},
{ columnId:"accountNumber",
  columnName:'<fmt:message key="label.chargeno1"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"accountNumber2",
  columnName:'<fmt:message key="label.chargeno2"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"accountNumber3",
  columnName:'<fmt:message key="label.chargeno3"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"accountNumber4",
  columnName:'<fmt:message key="label.chargeno4"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"ownerSegmentId",
  columnName:'<fmt:message key="label.owner"/>',
  width:10
}
];


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/clientconsignedinventoryreportresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="logisticsViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${logisticsBeanCollection != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty logisticsBeanCollection}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="mrReleaseViewBean" items="${logisticsBeanCollection}" varStatus="status">
<c:set var="showUpdateLink" value='N'/>

<c:if test="${status.index > 0}">,</c:if>

<c:set var="unitPriceDisplay" value=''/>
<c:set var="extPriceDisplay" value=''/>
<fmt:formatNumber var="unitPrice" value="${status.current.establishedUnitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="extPrice" value="${status.current.extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<c:if test="${!empty status.current.establishedUnitPrice}"><c:set var="unitPriceDisplay">${status.current.currencyId} ${unitPrice}</c:set></c:if>
<c:if test="${!empty status.current.extPrice}"><c:set var="extPriceDisplay">${status.current.currencyId} ${extPrice}</c:set></c:if>

<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
    <c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

/* Get time for hidden date column. This is for sorting purpose. */
<c:set var="expireDateTime" value="${status.current.expireDate.time}"/>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
/* Set the color to the rows according to their releaseStatus*/
	 data:[
	 '<tcmis:jsReplace value="${status.current.inventoryGroupName}"/>',	  	 
	 '<tcmis:jsReplace value="${status.current.catPartNo}" processMultiLines="true" />',
	 '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />',
	 '${status.current.receiptId}',
	 <c:if test="${tcmis:isFeatureReleased(personnelBean,'DisplayChargeNoOwnerSeqment', 'ALL')}">
	 '${status.current.customerReceiptId}',
	 </c:if>
	 '${status.current.quantity}',
     '${status.current.uosQuantity}',
     '<tcmis:jsReplace value="${status.current.unitOfSale}"/>',	        
     '${unitPriceDisplay}',
	 '${status.current.establishedUnitPrice}',
	 '${extPriceDisplay}',
	 '${status.current.extPrice}',
	 '${status.current.mfgLot}',
	 '${fmtExpireDate}','${expireDateTime}',
	 '${status.current.lotStatus}',
	 '<tcmis:jsReplace value="${status.current.accountNumber}" />',
	 '<tcmis:jsReplace value="${status.current.accountNumber2}" />',
	 '<tcmis:jsReplace value="${status.current.accountNumber3}" />',
	 '<tcmis:jsReplace value="${status.current.accountNumber4}" />',
	 '${status.current.ownerSegmentId}'
	  ]}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty logisticsBeanCollection}">
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
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>

<!-- Hidden elements for re-search-after-update purpose -->	
	<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
	<input name="expiresWithin" id="expiresWithin" type="hidden" value="${param.expiresWithin}"/>
	<input name="expiresAfter" id="expiresAfter" type="hidden" value="${param.expiresAfter}"/>
	<input name="showCustomerReturn" id="showCustomerReturn" type="hidden" value="${param.showCustomerReturn}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>