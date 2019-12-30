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
<script type="text/javascript" src="/js/distribution/newcustomercontact.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
function setJGroup() {
 currJ = $v('contactType');
 var values = new Array("purchasing","accounts","receiving","quality","management","other");
 // we can use $v('contactType').options all values are gauranteed.
 for(i=0;i< values.length; i++) {
 	if( currJ == values[i] ) {
 		$(values[i]).disabled = "disabled";
 		$('span_'+values[i]).style["color"] = "gray";
 	}
 	else {
 		$(values[i]).disabled = "";
 		$('span_'+values[i]).style["color"] = "black";
 	}
 }
}

function setJob() {
	setSelect('contactType','<tcmis:jsReplace value="${customerBean.contactType}" />');
	setJGroup();
}

// -->
</script>

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
<fmt:message key="newcustomercontact.title"/>
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
var fromQuickCustomerView = false;

<c:choose>
<c:when test="${fromCustomerDetail eq 'Yes'}">
fromCustomerDetail = true;
</c:when>
<c:when test="${param.fromQuickCustomerView eq 'Yes'}">
fromQuickCustomerView = true;
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

<body bgcolor="#ffffff"  onload="setJob();myOnload();document.genericForm.firstName.focus();" onresize="resizeFrames()" onunload="try{opener.closeTransitWin();}catch(ex){}">

<tcmis:form action="/newcustomercontact.do" onsubmit="return submitFrameOnlyOnce();">

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
<legend>&nbsp;<fmt:message key="label.customer"/> <fmt:message key="label.contact"/>&nbsp;</legend>
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
	<input type="text" class="inputBox" name="firstName" id="firstName" value="<tcmis:inputTextReplace value="${customerContact.firstName}" />" maxlength="30" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.phone"/>:<span style='font-size:12.0pt;color:red'>*</span>
</td>
<td width="15%">
	<input type="text" class="inputBox" name="phone" id="phone" value="<tcmis:inputTextReplace value="${customerContact.phone}" />" maxlength="30" size="15"/>
</td>
</tr>

<tr class="alt">
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.lastname"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="15%" >
	<input type="text" class="inputBox" name="lastName" id="lastName" value="<tcmis:inputTextReplace value="${customerContact.lastName}" />" maxlength="30" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.mobile"/></td>
<td width="15%" class="optionTitleBoldLeft">
		<input type="text" class="inputBox" name="mobile" id="mobile" value="<tcmis:inputTextReplace value="${customerContact.altPhone}" />" maxlength="30" size="15"/>
	</td>

</tr>

<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.nickname"/>:</td>

<td width="15%" >
	<input type="text" class="inputBox" name="nickname" id="nickname" value="<tcmis:inputTextReplace value="${customerContact.nickname}" />" maxlength="50" size="20"/>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.email"/>:</td>
<td width="15%" >
	<input type="text" class="inputBox" name="email" id="email" value="<tcmis:inputTextReplace value="${customerContact.email}" />" maxlength="80" size="20"/>
</td>
</tr>

<tr>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.jobfunction"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="15%">

    <select name="contactType" id="contactType" class="selectBox" onChange="setJGroup()">
    	<c:forEach var="cbean" items="${vvJobFunctionCollection}" varStatus="status">
	 	   	<option value="${cbean.contactType}" <c:if test="${customerContact.contactType eq cbean.contactType}">selected</c:if>>${cbean.contactTypeDesc}</option>
    	</c:forEach>
    </select>
</td>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.fax"/>:</td>
<td width="15%">
	<input type="text" class="inputBox" name="fax" id="fax" value="<tcmis:inputTextReplace value="${customerContact.fax}" />" maxlength="30" size="15"/>
</td>
</tr>
<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.otherjobfunctions"/></td>
	<td colspan="2">&nbsp;</td>
	<td width="8%" class="optionTitleBoldLeft"><input name="defaultContact" id="defaultContact" type="checkbox" class="radioBtns" value="true"/><fmt:message key="label.defaultcontact"/></td>
</tr>
<tr>
<td colspan="4">
<span id="span_purchasing"><input name="purchasing" id="purchasing" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.purchasing"/> </span>
<span id="span_accounts"><input name="accountsPayable" id="accounts" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.accountspayable"/> </span>
<span id="span_receiving"><input name="receiving" id="receiving" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.receiving"/> </span>
<span id="span_quality"><input name="qualityAssurance" id="quality" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.qualityassurance"/> </span>
<span id="span_management"><input name="management" id="management" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.management"/> </span>
<span id="span_other"><input name="other" id="other" type="checkbox" class="radioBtns" value="Y"/><fmt:message key="label.other"/> </span>
</td>
</tr>

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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="billToCompanyId" id="billToCompanyId" type="hidden" value="${param.billToCompanyId}"/>
<input name="modifyRecord" id="modifyRecord" type="hidden" value="${param.modifyRecord}"/>
<input name="fromCustomerDetail" id="fromCustomerDetail" type="hidden" value="${param.fromCustomerDetail}"/>
<input name="fromContactLookup" id="fromContactLookup" type="hidden" value="${param.fromContactLookup}"/>
<input name="fromQuickCustomerView" id="fromQuickCustomerView" type="hidden" value="${param.fromQuickCustomerView}"/>
<input name="personnelId" id="personnelId" type="hidden" value="${personnelId}"/>
<input name="status" id="status" type="hidden" value="${customerContact.status}"/>

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