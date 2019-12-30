<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/reconciliation.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.reconciliation"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="countapprovealert">
 <fmt:message key="label.countapprovealert"/>
</c:set>
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
actualQuantity:"<fmt:message key="label.actualonhand"/>",
countapprovealert:"<tcmis:jsReplace value="${countapprovealert}" processMultiLines="true"/>",
addedreceiptsuccessfully:"<fmt:message key="label.addedreceiptsuccessfully"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
searchInput:"<fmt:message key="error.searchInput.integer"/>",
pickNewBin:"<fmt:message key="hubbin.add.title"/>"
};

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.changebin"/>;url=javascript:showVvHubBins();");

}

drawMenus();

var countApprovalStatus = null;
var showApproveUpdateLinks = false;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad(); setLinks();" onunload="closeAllchildren();">

<tcmis:form action="/reconciliationresults.do" onsubmit="return submitFrameOnlyOnce();">

<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReconciliation">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="approveReconciliation" facilityId="${param.hubName}">
<script type="text/javascript">
    <!--
   	 showApproveUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
   <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>   
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError }">
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
<c:set var="dataCount" value='${0}'/>
<c:set var="countStat" value=''/>
<c:set var="approvedBy" value=''/>

<div id="ReconciliationBean" style="width:100%;height:400px;"></div>
<c:if test="${!empty beanCollection}" >
<!-- Search results start -->
<script type="text/javascript">
<!--
parent.$("addReceiptLinkOnly").style.display="none";

/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">

<c:set var="hasPerm" value='N'/>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReconciliation" facilityId="${param.hubName}" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/> 
</tcmis:inventoryGroupPermission>


<tcmis:inventoryGroupPermission indicator="true" userGroupId="approveReconciliation" facilityId="${param.hubName}" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/> 
</tcmis:inventoryGroupPermission>

<fmt:formatDate var="fmtExpireDate" value="${bean.expireDate}" pattern="${dateFormatPattern}"/>

<c:if test="${fmtExpireDate == '01-Jan-3000'}">
	<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>


<fmt:formatNumber var="unitPriceFinal" value="${bean.unitCost}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<fmt:formatNumber var="expectedQuantity" value="${bean.expectedQuantity}"  maxFractionDigits="4"></fmt:formatNumber>

<c:set var="approvedBy" value='${status.current.approvedByName}'/>

<c:if test="${status.current.approvedBy !=null}">
  <c:set var="hasPerm" value='N'/>
</c:if>

<c:set var="countStat" value='${status.current.countStatus}'/>
<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:[
  '${hasPerm}',
  '<tcmis:jsReplace value="${bean.inventoryGroupName}" processMultiLines="false"/>',
  '${bean.itemId}',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/> ',
  '<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true"/>', 
  <c:choose> 
  <c:when test="${bean.unitCost==null}">
  '',
  </c:when>
  <c:otherwise>	
    '${unitPriceFinal} ${bean.currencyId}',
   </c:otherwise>
   </c:choose>
   '<tcmis:jsReplace value="${bean.mfgLot}"/> ',   
  '<tcmis:jsReplace value="${bean.room}" processMultiLines="false"/>',
  '<tcmis:jsReplace value="${bean.bin}" processMultiLines="false"/>',
  '${bean.lotStatus}',
  '${fmtExpireDate}',
  '${bean.expireDate}',
  '${bean.receiptId}',
  '${bean.customerReceiptId}',
  <c:choose>
	  <c:when test="${bean.catPartNo != null}">
	  	  '<tcmis:jsReplace value="${bean.catPartNo}" processMultiLines="false"/>',
	  </c:when>
	  <c:when test="${bean.catPartNos != null}">
		  '<tcmis:jsReplace value="${bean.catPartNos}" processMultiLines="false"/>',
	  </c:when>
	  <c:otherwise>
			'',
	  </c:otherwise>
  </c:choose> 
  '${expectedQuantity}',
  '${bean.actualQuantity}',
  '${unitPriceFinal}',
  '${bean.inventoryGroup}',
  '${bean.actualQuantity}',
  '${bean.nonIntegerReceiving}' 
  
  ]
}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

var showMenu = "N";

<c:if test="${hasPerm == 'Y'}">
showMenu = "Y";
</c:if>


var cStatCol ="hed";
<c:if test="${countStat == 'Approved'}">
	cStatCol="ro"
</c:if>  

countApprovalStatus = '${countStat}'
	
<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>	
var config = [
  {
  	columnId:"permission",
    columnName:''
  },
  {
  	columnId:"inventoryGroupName",
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>'
  },
  {
  	columnId:"itemDesc",
  	columnName:'<fmt:message key="label.description"/>',
  	width:20
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.packaging"/>',
  	width:20
  }, 
  {
  	columnId:"unitCost",
  	columnName:'<fmt:message key="label.unitcost"/>',
  	sorting:'int',
  	hiddenSortingColumn:"hUnitCost"
  	
  },
  {
  	columnId:"mfgLot",
  	columnName:'<fmt:message key="label.mfglot"/>',
  	width:10
  },
  {
  	columnId:"room",
  	columnName:'<fmt:message key="label.room"/>',
  	width:8
  },
  {
  	columnId:"bin",
  	columnName:'<fmt:message key="label.bin"/>',
  	width:8
  },
  {
  	columnId:"lotStatus",
  	columnName:'<fmt:message key="label.lotstatus"/>'
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
  {
  	columnId:"receiptId",
  	columnName:'<fmt:message key="label.receiptid"/>',
  	width:6,
  	submit:true,
  	sorting:'int'
  },
   {
  	columnId:"customerReceiptId",
  	columnName:'<fmt:message key="label.legacyreceiptid"/>',
  	width:7,
  	sorting:'int'
  },
  {
  	columnId:"partNumber",
  	columnName:'<fmt:message key="label.partnumber"/>',
  	tooltip:"Y",
  	width:12
  },
  {
  	columnId:"expectedQuantity",
  	columnName:'<fmt:message key="label.recordedonhand"/>',
  	sorting:'int',
	align:'right',
	submit:true,
	width:8
  },
  {
  	columnId:"actualQuantity",
  	columnName:'<fmt:message key="label.actualonhand"/>',  	
  	type:cStatCol, 
  	onChange:'function',
  	sorting:'int', 
  	submit:true, 	
  	width:8
  },
  { columnId:"hUnitCost",
	 sorting:"int"
  },
  { columnId:"inventoryGroup"   
  },
  { columnId:"oldActualQuantity",
	submit:true  
  },
  { columnId:"nonIntegerReceiving"   
  }
     
  ];

//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty beanCollection}" >
<script type="text/javascript">
<!--
	parent.$("addReceiptLinkOnly").style.display="";
//-->
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/> 
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="approvedBy" id="approvedBy" type="hidden" value="${approvedBy}" />
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="hub" id="hub" type="hidden" value="${param.hub}" />
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}" />
<input name="countDate" id="countDate" type="hidden" value="${param.countDate}" />
<input name="countDateString" id="countDateString" type="hidden" value="${param.countDateString}" />
<input name="checkbox" id="checkbox" type="hidden" value="${param.checkbox}" />
<input name="printOnHandCheckbox" id="printOnHandCheckbox" type="hidden" value="${param.printOnHandCheckbox}" />
<input name="countId" id="countId" type="hidden" value="${param.countId}" />
 <c:forEach var="invGrp" items="${paramValues.inventoryGroup}">
			<input name="inventoryGroup" id="inventoryGroup" value="${invGrp}" type="hidden"/>
 </c:forEach>
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}" />
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}" />
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}" />
<input name="room" id="room" type="hidden" value="${param.room}" />
<input name="binFrom" id="binFrom" type="hidden" value="${param.binFrom}" />
<input name="binTo" id="binTo" type="hidden" value="${param.binTo}" />
<input name="hubName" id="hubName" type="hidden" value="${param.hubName}" />
<c:forEach var="lotStat" items="${paramValues.lotStatus}">
			<input name="lotStatus" id="lotStatus" value="${lotStat}" type="hidden"/>
 </c:forEach>
 <input name="radiobox" id="radiobox" type="hidden" value="${param.radiobox}" />
</div>
</div> <!-- close of backGroundContent -->
</tcmis:form>
</body>
</html:html>