<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/newposuppliercontact.js"></script>


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<fmt:message key="label.newsuppliercontact"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
lastnameRequired:"<fmt:message key="label.lastname"/>",
firstnameRequired:"<fmt:message key="label.firstname"/>",
phoneRequired:"<fmt:message key="label.phone"/>",
all:"<fmt:message key="label.all"/>"
};



var closeContactWin = false;
var fromCustomerDetail = false;
var fromContactLookup = false;

<c:choose>
<c:when test="${fromCustomerDetail eq 'Yes'}">
fromCustomerDetail = true;
</c:when>
<c:otherwise>
<c:choose>
<c:when test="${fromContactLookup eq 'Yes'}">
fromContactLookup = true;
</c:when>
</c:choose>
</c:otherwise>
</c:choose>
// -->
</script>
</head>
<%--TODO supply path assigning to a contact--%>
<body bgcolor="#ffffff"  onload="myOnload();" onunload="try{opener.parent.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/newposuppliercontact.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${modifyOpener == 'Y'}">
    modifyOpener = true;
   </c:when>
   <c:otherwise>
    modifyOpener = false;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${addToOpener == 'Y'}">
    addToOpener = true;
   </c:when>
   <c:otherwise>
    addToOpener = false;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
    <c:set var="selectedShipToCountry" value='USA'/>
	<tcmis:isCNServer>
		<c:set var="selectedShipToCountry" value='CHN'/>
	</tcmis:isCNServer> 
<fieldset>
<legend>&nbsp;<fmt:message key="label.supplier"/> <fmt:message key="label.contact"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr class="alt">
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.firstname"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="15%" >
	<input type="hidden" name="customerId" id="customerId" value="${param.customerId}">
	
	<c:choose>
   <c:when test="${ !empty param.contactPersonnelId }">
    <c:set var="contactPersonnelId" value='${ param.contactPersonnelId}'/>
   </c:when>
   <c:otherwise>
    <c:choose>
   <c:when test="${ !empty customerBean.contactPersonnelId }">
    <c:set var="contactPersonnelId" value='${ customerBean.contactPersonnelId}'/>
   </c:when>
    <c:otherwise>
    <c:set var="contactPersonnelId" value='${personnelId}'/>
     </c:otherwise>
   </c:choose>
   </c:otherwise>
</c:choose>
	
	
	
	<input type="hidden" name="contactPersonnelId" id="contactPersonnelId" value="${contactPersonnelId}"/>
	<input type="text" class="inputBox" name="firstName" id="firstName" value="${supplierContactBean.firstName}" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.phone"/>:<span style='font-size:12.0pt;color:red'>*</span>
</td>
<td width="15%">
	<input type="text" class="inputBox" name="phone" id="phone" value="${supplierContactBean.phone}" size="15"/>
</td>
</tr>

<tr class="alt">
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.lastname"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="15%" >
	<input type="text" class="inputBox" name="lastName" id="lastName" value="${supplierContactBean.lastName}" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.phone"/>&nbsp;-&nbsp;<fmt:message key="label.ext"/></td>
<td width="15%" class="optionTitleBoldLeft">
		<input type="text" class="inputBox" name="phoneExtension" id="phoneExtension" value="${supplierContactBean.phoneExtension}" size="8" maxlength="30"/>
	</td>

</tr>

<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.nickname"/>:</td>

<td width="15%" >
	<input type="text" class="inputBox" name="nickname" id="nickname" value="${supplierContactBean.nickname}" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.email"/>:</td>
<td width="15%" >
	<input type="text" class="inputBox" name="email" id="email" value="${supplierContactBean.email}" size="20"/>
</td>
</tr>

<tr>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contacttype"/></td>
<td width="15%">
<c:if test="${supplierContactBean.contactType != 'Main'}">
    <select name="contactType" id="contactType" class="selectBox" >
    	<c:forEach var="cbean" items="${vvSupplierContactTypeCollection}" varStatus="status">
	 	   	<option value="${cbean.contactType}" <c:if test="${supplierContactBean.contactType eq cbean.contactType}">selected</c:if>>${cbean.description}</option>
    	</c:forEach>
    </select>
</c:if>
<c:if test="${supplierContactBean.contactType == 'Main'}">
    <input type="hidden" name="contactType" id="contactType" value="${supplierContactBean.contactType}" />
    ${supplierContactBean.contactType}
</c:if>
</td>


<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.fax"/>:</td>
<td width="15%">
	<input type="text" class="inputBox" name="fax" id="fax" value="${supplierContactBean.fax}" size="15"/>
</td>
</tr>

<%--<tr>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.supplypath"/></td>
<td width="15%">

    <select name="supplyPath" id="supplyPath" class="selectBox" >
     <option value="purchaser"> <fmt:message key="label.purchaser"/>  </option>
     <option value="wBuy"> <fmt:message key="label.wbuy"/>  </option>
     <option value="edi"><fmt:message key="label.edi"/> </option>     		 	      	
    </select>
</td>
</tr>--%>
<tr>
	<td width="15%" colspan="4">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return newContact();">
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>


<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="actionType" id="actionType" type="hidden" value="${param.actionType}"/>
<input name="fromQuickSupplierView" id="fromQuickSupplierView" type="hidden" value="${param.fromQuickSupplierView}"/>
<input name="action" id="action" type="hidden" value=""/>
<input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/>
<input name="contactId" id="contactId" type="hidden" value="${param.contactId}"/>
<input name="newContactId" id="newContactId" type="hidden" value="${newContactId}"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>
