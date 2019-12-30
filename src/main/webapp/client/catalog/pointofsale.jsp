<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script> 
<script type="text/javascript" src="/js/client/catalog/pointofsale.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>	
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
${param.facilityName}: <fmt:message key="label.mrnumber"/> ${param.prNumber}
</title>

</head>

<body bgcolor="#ffffff"  onload="myOnLoad('${param.action}');" onresize="resizeFrames();">  

<tcmis:form action="/pointofsale.do" onsubmit="return submitFrameOnlyOnce();">
<c:set var="dataCount" value='${0}'/>
<script language="JavaScript" type="text/javascript">
<!--

var currencyColl = new Array();
<c:forEach var="tmpBean" items="${financeApproverListBean.currencyColl}" varStatus="status">
	currencyColl[${status.index}]={
		currencyId:'${tmpBean.currencyId}',
		currencyDescription:'${tmpBean.currencyDescription}',
	   exchangeRate:'${tmpBean.exchangeRate}'
	};
</c:forEach>

with(milonic=new menuname("empty")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("");
}
drawMenus();

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"lineItem",
  columnName:'<fmt:message key="label.lineitem"/>',
  width:4,
  submit:true
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnumber"/>',
  width:6,
  submit:true
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.partdescription"/>',
  width:10,
  tooltip:"Y",
  submit:true
},
{ columnId:"catalogPrice",
  columnName:'<fmt:message key="label.price"/>',
  width:5,
  sorting:'int',
  submit:true
},
{ columnId:"mrQuantity",
  columnName:'<fmt:message key="label.totalordered"/>',
  width:6,
  sorting:'int',
  submit:true
},
{ columnId:"sumOfQuantityPicked",
  columnName:'<fmt:message key="label.totalpicked"/>',
  width:5,
  sorting:'int',
  submit:true
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  width:10,
  tooltip:"Y",
  submit:true
},
{ columnId:"item",
  columnName:'<fmt:message key="label.item"/>',
  width:8,
  submit:false
},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>',
  width:6,
  submit:true
},
{ columnId:"bin",
  columnName:'<fmt:message key="label.bin"/>',
  submit:true
},
{ columnId:"expireDate",
  columnName:'<fmt:message key="label.expirationdate"/>',
  submit:false
},
{ columnId:"quantityOnHand",
  columnName:'<fmt:message key="label.currentinventory"/>',
  width:6,
  sorting:'int',
  submit:true
},
{ columnId:"quantityAllocated",
  columnName:'<fmt:message key="pickingqc.qtypicked"/>',
  type:"hed",
  width:5,
  sorting:'int',
  submit:true
},
{ columnId:"clickBtn",
  columnName:'<fmt:message key="label.close"/>',
  submit:false
},
{ columnId:"itemId",
  submit:true
},
{ columnId:"inventoryGroup",
  submit:true
}
,
{ columnId:"hub",
  submit:true
}
];

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
warning:"<fmt:message key="label.warning"/>",
forLine:"<fmt:message key="label.forline"/>",
notPicked:"<fmt:message key="label.notpicked"/>",
youPicked:"<fmt:message key="label.youpicked"/>",
moreThanQtyOrdered:"<fmt:message key="label.morethanqtyordered"/>",
mrExceededFinancialLimit:"<fmt:message key="label.mrexceededfinanciallimit"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
 </script>

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText"></textarea>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
				<input name="continueMessageB"  id="continueMessageB"  type="button" value="<fmt:message key="label.continue"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="continueMessageWin();"/>
				<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			</td>
		</tr>
	</table>
</div>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
    	<div id="mainUpdateLinks" style="display: none"></div>
		 <span id="updateResultLink" style="display: none">
			<a href="#" onclick="submitSearchForm(); return false;"><fmt:message key="button.submit"/></a>
			&nbsp;|&nbsp;<a href="#" onclick="closeWindow(); return false;"><fmt:message key="label.cancel"/></a>
	  	 </span>
		 &nbsp;
		 <span id="headerDataSpan" style="display: none">
			 <table width="100%" height="100%">
				<tr>
					<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.facility"/>:</td>
					<td  width="40%">${param.facilityName}</td>
					<c:if test="${not empty financeApproverListBean.currencyId}">
						<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.orderinglimit"/>:</td>
						<td  width="40%">${financeApproverListBean.costLimit} ${financeApproverListBean.currencyId}</td>
					</c:if>
				 </tr>
				<tr>
					<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.customer"/>:</td>
					<td  width="40%">${userName}</td>
					<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.totalprice"/>:</td>
					<td  width="40%">
						<span id="totalPriceSpan">
						</span>
					</td>
				</tr>
			</table>
		</span>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
<div id="pointOfSaleInventoryViewBean" style="width:200;height:400px;" style="display: none;"></div>

<c:if test="${pointOfSaleInventoryColl != null}" >
<script type="text/javascript">
<!--
<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<c:set var="dataCount" value='${0}'/>

<bean:size id="listSize" name="pointOfSaleInventoryColl"/>

var map = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
<c:set var="preLine" value=""/>
<c:set var="preReceipt" value=""/>
<c:set var="lineCount" value="1"/>
<c:forEach var="tmpBean" items="${pointOfSaleInventoryColl}" varStatus="status">
	<c:set var="curLine">${status.current.lineItem}</c:set>
	<c:set var="curReceipt" value="receipt${status.current.receiptId}"/>

	<c:if test="${!(curLine eq preLine)}">
		<c:set var="lineCount" value="${lineCount+1}"/>
		<c:set var="preItem" value=""/>
		lineMap[${status.index}] = ${rowCountLine[curLine]} ;
		map = new Array();
	</c:if>
	<c:if test="${ !( curItem eq preItem)}">
		<c:set var="componentSize" value='${rowCountReceipt[curLine][curReceipt]}' />
		lineMap2[${status.index}] = ${componentSize} ;
	</c:if>
	lineMap3[${status.index}] = ${lineCount}%2 ;
	map[map.length] =  ${status.index} ;

	<c:set var="preLine" value="${status.current.lineItem}"/>
	<c:set var="preReceipt" value="receipt${status.current.receiptId}"/>
</c:forEach>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="pointOfSaleInventory" items="${pointOfSaleInventoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>
<tcmis:jsReplace var="partDescription" value="${status.current.partDescription}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${status.current.packaging}"  processMultiLines="false"/>
<c:set var="currentPermission" value='Y'/>
<c:choose>
   <c:when test="${status.current.quantityOnHand == null || status.current.quantityOnHand == '0'}">
    	<c:set var="bin">
	 		<fmt:message key="label.nostock"/>
	   </c:set>
	  <c:set var="currentPermission" value='N'/>
	</c:when>
   <c:otherwise>
    	<c:set var="bin" value="${status.current.bin}"/>
   </c:otherwise>
</c:choose>

<c:if test="${empty tcmISError && param.action == 'submit'}">
	<c:set var="currentPermission" value='N'/>
</c:if>

<c:set var="currentReceiptId" value="${status.current.receiptId}"/>
<c:if test="${status.current.lineItem == status.current.receiptId}">
	<c:set var="currentReceiptId" value=""/>	  
</c:if>

<c:choose>
	  <c:when test="${status.current.itemType == 'MD' && param.action != 'submit'}">
	  	<c:set var="tapableItem">
   		<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="${status.current.itemId} (${status.current.itemType})" OnClick="tapItem('${status.current.itemId}','${status.current.inventoryGroup}')"/>
		</c:set>
      <tcmis:jsReplace value="${tapableItem}" var="tapableItem"/> 																																																																									  
		<c:set var="closeBin"></c:set>
		<c:if test="${!empty currentReceiptId}">
			<c:set var="closeBin">
				<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.close"/>" OnClick="closeBin('${status.current.receiptId}')"/>
			</c:set>
         <tcmis:jsReplace value="${closeBin}" var="closeBin"/>
		</c:if>
	  </c:when>
	  <c:otherwise>
	  	<c:choose>
			<c:when test="${status.current.itemType == 'MP' && param.action != 'submit'}">
				<c:set var="tapableItem">
   				<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="${status.current.itemId} (${status.current.itemType})" OnClick="tapItem(${status.current.itemId},${status.current.inventoryGroup})"/>
				</c:set>
				<tcmis:jsReplace value="${tapableItem}" var="tapableItem"/>																																																																											 
			</c:when>
			<c:otherwise>
				<c:set var="tapableItem">${status.current.itemId} (${status.current.itemType})</c:set>
			</c:otherwise>
		</c:choose>
		<c:set var="closeBin"></c:set>
	  </c:otherwise>
</c:choose>

	<c:set var="finalPriceDisplay" value="" />
   <c:if test="${!empty status.current.catalogPrice}">
		<fmt:formatNumber var="finalPriceDisplay" maxFractionDigits="4" minFractionDigits="4">${status.current.catalogPrice}</fmt:formatNumber>
	 	<c:set var="finalPriceDisplay" value="${finalPriceDisplay}" />
	</c:if>

{ id:${status.index +1},
 data:['${currentPermission}','${status.current.lineItem}','${status.current.catPartNo}','${partDescription}',
 '${finalPriceDisplay}','${status.current.mrQuantity}','${status.current.sumOfQuantityPicked}',
 '${packaging}','${tapableItem}','${currentReceiptId}','${bin}','${fmtExpireDate}',
 '${status.current.quantityOnHand}','${status.current.quantityAllocated}','${closeBin}',
 '${status.current.itemId}','${status.current.inventoryGroup}','${status.current.hub}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty pointOfSaleInventoryColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

</div>

    <%-- result count and time --%>
    <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<%--Store the search parameters.--%>
<input name="action" id="action" value="${param.action}" type="hidden"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}">
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
<input name="facilityName" id="facilityName" type="hidden" value="${param.facilityName}">
<input name="receiptId" id="receiptId" type="hidden" value="">
<input name="bin" id="bin" type="hidden" value="">	
<input name="itemId" id="itemId" type="hidden" value="">
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="">
<input name="currencyId" id="currencyId" type="hidden" value="${financeApproverListBean.currencyId}">
<input name="orderingLimit" id="orderingLimit" type="hidden" value="${financeApproverListBean.costLimit}">
<input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>