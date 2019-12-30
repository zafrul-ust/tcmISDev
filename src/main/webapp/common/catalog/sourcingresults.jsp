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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/pagename.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.sourcing"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<!--Uncomment for production-->
<%--<tcmis:form action="/pagenameresults.do" onsubmit="return submitFrameOnlyOnce();">--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="pageNameUserGroup" facilityId="${param.testingField}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<!--Uncomment for production-->
<%--<c:if test="${pageNameViewBeanCollection != null}" >--%>
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <!--Uncomment for production-->
 <%--<c:if test="${!empty pageNameViewBeanCollection}" >--%>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <!--Uncomment for production-->
    <%--<c:forEach var="pageNameViewBean" items="${pageNameViewBeanCollection}" varStatus="status">--%>

    <!--Uncomment for production-->
    <%--<c:if test="${status.index % 10 == 0}">--%>
    <!-- Need to print the header every 10 rows-->
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.requestor"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.requestor}"/>
	</td>
</tr>
<tr class="">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.phone"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.phone}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.facility"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.facility}"/>
	</td>
</tr>
<tr class="">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.workarea"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.workarea}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.haas"/> <fmt:message key="label.requestid"/>: 
	</td>>
	<td>
		<c:out value="${sourcingBean.requestId}"/>
	</td>
</tr>
<tr class="">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.partdesc"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.partDesc}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.process"/> <fmt:message key="label.desc"/>: 
	</td>>
	<td>
		<c:out value="${sourcingBean.processDesc}"/>
	</td>
</tr>

<tr>
	<th class="optionTitleBoldCenter" colspan="2">
		<fmt:message key="label.component"/> <fmt:message key="label.information"/>
	</th>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.component"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.component}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.material"/> <fmt:message key="label.description"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.materialDesc}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.grade"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.grade}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.tradename"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.tradeName}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.manufacturer"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.manufacturer}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.mfg.part.num"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.mfgPartNum}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.haas"/> <fmt:message key="label.msdsnumber"/>:
	</td>
	<td>
		<a href="/tcmIS/fec/ViewMsds?act=msds&showspec=N&id=<c:out value="${sourcingBean.msdsNumber}"/>&cl=fec" target="msds">
			<c:out value="${sourcingBean.msdsNumber}"/>
		</a>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.packaging"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.packaging}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.shelflibe"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.shellLife}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.storagetemp"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.storageTemp}"/>
	</td>
</tr>
<tr>
	<th class="optionTitleBoldCenter" colspan="2">
		<fmt:message key="label.requestor"/> <fmt:message key="label.suggestSupplier"/>
	</th>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.suppliername"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.supplier}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.contactname"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.contact}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.phone"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.contactPhone}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.fax"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.contactFax}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.email"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.contactEmail}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.supplier"/> <fmt:message key="label.partNno"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.supplierPartNum}"/>
	</td>
</tr>
<tr>
	<th class="optionTitleBoldCenter" colspan="2">
		<fmt:message key="label.item"/> <fmt:message key="label.summary"/>
	</th>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.itemid"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.itemId}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.itemdescription"/>: 
	</td>
	<td>
		<c:out value="${sourcingBean.itemDesc}"/>
	</td>
</tr>
<tr>
	<td width="20%" class="optionTitleBoldLeft">
		<fmt:message key="label.item"/> <fmt:message key="label.packaging"/>:
	</td>
	<td>
		<c:out value="${sourcingBean.itemPacking}"/>
	</td>
</tr>
<tr class="alt">
	<td width="20%" colspan="2" class="optionTitleBoldLeft">
		<b><fmt:message key="label.baselinePrice"/>: </b>
		<input type="text" class="inputBox" name="baselinePrice" id="baselinePrice" value="<c:out value="${sourcingBean.baselinePrice}"/>" size="15"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><fmt:message key="label.catalogSellingPrice"/>:</b>
		<input type="text" class="inputBox" name="sellingPrice" id="sellingPrice" value="<c:out value="${sourcingBean.sellingPrice}"/>" size="15"/>
	</td>
</tr>
</table>

<table width="100%" class="tableResults" id="resultsPageTable2" border="0" cellpadding="0" cellspacing="0">

<input type="hidden" name="companyID" value="fec">

<input type="hidden" name="requestID" value="61811">
<tr class="alt">
	<th colspan="4" class="optionTitleBoldCenter">
		<fmt:message key="label.haas"/> <fmt:message key="label.supplier"/> <fmt:message key="label.information"/>
	</th>
</tr>
<tr class="alt">
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.supplier"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<c:out value="${supplierBean.supplierName}"/>
		<input type="hidden" name="supplierName" id="supplierName" value="<c:out value="${supplierBean.supplierName}"/>"/>
		<input type="hidden" name="supplierID" id="supplierID" value="<c:out value="${supplierBean.supplierId}"/>"/>
		&nbsp;&nbsp;&nbsp;
		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onClick= "searchSupplierWin()" name="searchSupplier">
			<img src="/images/search_small.gif" alt="<fmt:message key="label.search"/>" title="<fmt:message key="label.search"/>">
		</button>
	</td>
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.contactname"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<c:out value="${supplierBean.contactName}"/>
		<input type="hidden" name="contactName" id="contactName" value="<c:out value="${supplierBean.contactName}"/>"/>
		<input type="hidden" name="contactID" id="contactID" value="<c:out value="${supplierBean.contactId}"/>"/>
		&nbsp;&nbsp;&nbsp;
		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onClick= "contactNameWin()" name="searchContact">
			<img src="/images/search_small.gif" alt="<fmt:message key="label.search"/> <fmt:message key="contact"/>" title="<fmt:message key="label.search"/> <fmt:message key="contact"/>">
		</button>
	</td>
</tr>
<tr class="">
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.carrier"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<select class="selectBox" name="carrierID" id="carrierID">
			<option value="None"><fmt:message key="label.selectOne"/></option>
			<option value="VT"><fmt:message key="label.vendorTruck"/></option>
			<option value="PPA"><fmt:message key="label.prePayAndAdd"/></option>	
			<option value="CM"><fmt:message key="label.customerManaged"/></option>
		</select>
	</td>
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.freightOnBoard"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<c:out value="${supplierBean.freightOnBoard}"/>
		<input type="hidden" name="freightOnBoard" id="freightOnBoard" value="<c:out value="${supplierBean.freightOnBoard}"/>"/>
		<input type="hidden" name="freightOnBoardId" id="freightOnBoardId" value="<c:out value="${supplierBean.freightOnBoardId}"/>"/>
	</td>
</tr>
<tr class="alt">
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.consignment"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<select class="selectBox" name="consignment" id="consignment">
			<option value="None"><fmt:message key="label.selectOne"/></option>
			<option value="No"><fmt:message key="label.no"/></option>
			<option value="Yes"><fmt:message key="label.yes"/></option>	
		</select>	
	</td>
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.supplier"/> <fmt:message key="label.partNno"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" class="inputBox" name="supplierPartNumber" name="supplierPartNumber" value="<c:out value="${supplierBean.supplierPartNumber}"/>" size="30">
	</td>
</tr>
<tr class="">
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.itemMuliples"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" class="inputBox" name="multipleOf" id="multipleOf" value="<c:out value="${supplierBean.multipleOf}"/>" size="30"/>
	</td>
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.leadtimeindays"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" class="inputBox" name="leadTime" id="leadTime" value="<c:out value="${supplierBean.leadTime}"/>" size="30"/>
	</td>
</tr>
<tr class="alt">
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.currency"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<select class="selectBox" name="currency" id="currency">
			<option value="None"><fmt:message key="label.selectOne"/></option>
			<!-- database query values -->
		</select>	
	</td>
	<td width="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.sourceType"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<select class="selectBox" name="contractType" id="contractType">
			<option value="None"><fmt:message key="label.selectOne"/></option>
			<!-- database query values -->
		</select>	
	</td>
</tr>
<tr> <!-- just a black line -->
	<td colspan="4">
		<hr style="color: 'black'; height: '3'; text-align: 'center'; width: '80%'"/>
	</td>
</tr>
<tr class="alt">
	<td width="35%" colspan="4" class="optionTitleBoldCenter">
		<fmt:message key="label.pricingGoodUntil"/>: 
		<c:out value="${supplierBean.goodUntil}"/>
		<input type="hidden" name="goodUntil" id="goodUntil" value="<c:out value="${supplierBean.goodUntil}"/>"/>
		<a href="javascript: void(0);" id="goodUntil" onClick="return getCalendar(document.catAddDetail.goodUntil);" class="datePopUpLink">
			&diams;
		</a>
		<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.clear"/>" onClick="clearDate(document.catAddDetail.goodUntil)" name="clearSD"/>
	</td>
</tr>
<tr class="alt">
	<td width="35%" class="optionTitleBoldRight">
		<fmt:message key="label.purchasingUnitPrice"/>:
	</td>
	<td width="15%" class="optionTitleLeft">
		<input type="text" name="purchasing" id="purchasing" value="<c:out value="${supplierBean.purchasing}"/>" size="25"/>
	</td>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.upToQuantity"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" name="uptoQuantity" id="uptoQuantity" value="<c:out value="${supplierBean.uptoQuantity}"/>" size="25"/>
	</td>
</tr>
<tr class="alt">
	<td width="35%" class="optionTitleBoldRight">
		<fmt:message key="label.purchasingUnitPrice"/>:
	</td>
	<td width="15%" class="optionTitleLeft">
		<input type="text" name="purchasing2" id="purchasing2" value="<c:out value="${supplierBean.purchasing2}"/>" size="25"/>
	</td>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.upToQuantity"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" name="uptoQuantity2" id="uptoQuantity2" value="<c:out value="${supplierBean.uptoQuantity2}"/>" size="25"/>
	</td>
</tr>
<tr class="alt">
	<td width="35%" class="optionTitleBoldRight">
		<fmt:message key="label.purchasingUnitPrice"/>:
	</td>
	<td width="15%" class="optionTitleLeft">
		<input type="text" name="purchasing3" id="purchasing3" value="<c:out value="${supplierBean.purchasing3}"/>" size="25"/>
	</td>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.upToQuantity"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" name="uptoQuantity3" id="uptoQuantity3" value="<c:out value="${supplierBean.uptoQuantity3}"/>" size="25"/>
	</td>
</tr>
<tr class="alt">
	<td width="35%" class="optionTitleBoldRight">
		<fmt:message key="label.purchasingUnitPrice"/>:
	</td>
	<td width="15%" class="optionTitleLeft">
		<input type="text" name="purchasing4" id="purchasing4" value="<c:out value="${supplierBean.purchasing4}"/>" size="25"/>
	</td>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.upToQuantity"/>:
	</td>
	<td width="35%" class="optionTitleLeft">
		<input type="text" name="uptoQuantity4" id="uptoQuantity4" value="<c:out value="${supplierBean.uptoQuantity4}"/>" size="25"/>
	</td>
</tr>
<tr>
<td width="15%" colspan="4">
	<input type="hidden" name="requestId" id="requestId" value="<c:out value="${sourcingBean.requestId}"/>"/>
	<input type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.approve"/>" name="udpate"/>
</td>
</tr>
</table>


    <!--Uncomment for production-->
    <%--</c:if>--%>

    <!--Uncomment for production-->
    <%--
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>
    --%>

   <c:set var="dataCount" value='${dataCount+1}'/>

   <!--Uncomment for production-->
   <%--</c:forEach>--%>
   </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>
   <!-- If the collection is empty say no data found -->

   <!--Uncomment for production-->
   <%--<c:if test="${empty pageNameViewBeanCollection}" >--%>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>

<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>