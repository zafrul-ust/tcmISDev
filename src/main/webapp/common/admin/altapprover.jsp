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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<title>
<fmt:message key="label.alternateapprover"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var windowCloseOnEsc = true;

function changePersonnel() {
  var loc = "searchpersonnelmain.do?companyId="+encodeURIComponent(document.getElementById('companyId').value) +
         						"&facilityId="+encodeURIComponent(document.getElementById('facilityId').value);
  openWinGeneric(loc,"changepersonnel","650","450","yes","50","50","20","20","no");
}

function personnelChanged(pid) {
	document.getElementById("transitPage").style["display"] = ""
 	document.getElementById('searchMaskTable').style["display"]="none";
	document.getElementById("alternateApprover").value = pid;
	document.getElementById("editAction").value = "newAlt";
	document.genericForm.submit();
}

function deleteAlt() {
	document.getElementById("transitPage").style["display"] = ""
 	document.getElementById('searchMaskTable').style["display"]="none";
	document.genericForm.submit();
}

// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="financialapproverresults.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight">
</td>
      </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 ${tcmISError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<c:if test="${FinanceAlternateApproverBeanCollection != null}" >
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
    <div class="boxhead"><table><tr>
    								<td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:
    								<td class="optionTitleLeft">${param.facilityName}</td>
    							</tr>
    							<tr>
    								<td class="optionTitleBoldRight"><fmt:message key="label.approver"/>:
    								<td class="optionTitleLeft">${param.userName}</td>
    						    </tr>
    					 </table>
		<input name="bAdd"    id="bAdd"    type="button" value="<fmt:message key="label.newaltapprover"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="changePersonnel()"/>
		<input name="bDelete" id="bDelete" type="button" value="<fmt:message key="label.delete"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="deleteAlt()"/>
		<input name="bCancel" id="bCancel" type="button" value="<fmt:message key="label.close"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="window.close()"/>
	</div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pageNameViewBean" items="${FinanceAlternateApproverBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.lastname"/></th>
    <th width="5%"><fmt:message key="label.firstname"/></th>
    <th width="5%"><fmt:message key="label.phone"/></th>
    <th width="5%"><fmt:message key="label.email"/></th>
    <th width="5%"><fmt:message key="label.delete"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%"><c:out value="${status.current.lastName}"/></td>
     <td width="5%"><c:out value="${status.current.firstName}"/></td>
     <td width="5%"><c:out value="${status.current.phone}"/></td>
     <td width="5%"><c:out value="${status.current.email}"/></td>
     <td width="5%">
     	<input type="checkbox" class="radioBtn" name="FinanceAlternateApprover[${dataCount}].remove" id="ok_${dataCount}" value="Y"/>
		<input type="hidden" name="FinanceAlternateApprover[${dataCount}].alternateApprover" id="alternateApprover_${dataCount}" value="${status.current.alternateApprover}"/>				
	 </td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty FinanceAlternateApproverBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="approver" id="approver" value="${param.approver}"/>
 <input type="hidden" name="alternateApprover" id="alternateApprover" value=""/>
 <input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
 <input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
 <input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}"/>
 <input type="hidden" name="userName" id="userName" value="${param.userName}"/>
 <input type="hidden" name="editAction" id="editAction" value="delete"/>
 <input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
 <input type="hidden" name="userId" id="userId" value="${param.userId}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>