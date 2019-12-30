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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>

<script src="/js/report/opentemplate.js" language="JavaScript"></script>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="opentemplate.title"/>
</title>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();">

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselectatemplate:"<fmt:message key="label.pleaseselectatemplate"/>"};

<bean:size id="templateSize" name="templateCollection"/>
var altTemplateDesc = new Array(
   <c:forEach var="template" items="${templateCollection}" varStatus="status">
		<c:choose>
			<c:when test="${status.index == 0 && templateSize > 1}">
				'','<tcmis:jsReplace value="${status.current.templateDescription}"/>'
			</c:when>
		 <c:otherwise>
			<c:choose>
			  <c:when test="${status.index == 0}">
				 '<tcmis:jsReplace value="${status.current.templateDescription}"/>'
			  </c:when>
			  <c:otherwise>
				 ,'<tcmis:jsReplace value="${status.current.templateDescription}"/>'
			  </c:otherwise>
			</c:choose>
		 </c:otherwise>
		</c:choose>
	</c:forEach>
);

// -->
</script>

<tcmis:form action="/opentemplate.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">



<div class="contentArea">



<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
       <div class="boxhead"> <%-- boxhead Begins --%>
         <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
              Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.--%>
           <div id="mainUpdateLinks" style="font-size: 12px"> 
                <span id='selectedFieldsSpan'></span>
                <a href="javascript:auditOpen()"><fmt:message key="label.open"/></a> |
                <a href="javascript:cancelAndClose()"><fmt:message key="label.cancel"/></a>  
           </div> <%-- mainUpdateLinks Ends --%>
       </div> <%-- boxhead Ends --%>
    
	<c:if test="${!empty templateCollection}" >
	 <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">		
		<tr><td class="optionTitleBoldCenter"><fmt:message key="opentemplate.title"/></td></tr>
		<tr>
			<td class="optionTitleBoldCenter"><fmt:message key="label.template"/>:&nbsp;
			  <select class="selectBox" name="templateId" id="templateId" onchange="templateChanged()" >
					<c:set var="templateColl" value='${templateCollection}'/>
					<bean:size id="templateSize" name="templateColl"/>
					<c:if test="${templateSize > 1}">
						<option value=""><fmt:message key="label.selectOne"/></option>
					</c:if>
					<c:forEach var="templateBean" items="${templateCollection}" varStatus="status1">
						<c:set var="globalLabelLetter"><fmt:message key="${status1.current.globalizationLabelLetter}"/></c:set>						
						<c:set var="templateOwnerVer" value=''/>
						<c:if test="${status1.current.owner == 'PERSONNEL_ID' }">
						  <c:set var="templateOwnerVer" value="${status1.current.createdByName}"/>
						</c:if>
						<c:if test="${status1.current.owner == 'COMPANY_ID' }">
                          <c:set var="templateOwnerVer" value="${status1.current.companyName}"/>
                        </c:if>
                        <c:if test="${status1.current.owner == 'USER_GROUP_ID' }">
                          <tcmis:jsReplace var="userGroupDesc" value="${status1.current.userGroupDesc}" processMultiLines="true" />
                          <c:set var="templateOwnerVer" value="${userGroupDesc}"/>
                        </c:if>
                        <c:set var="templateFinalName" value="${globalLabelLetter}${status1.current.templateId}-${status1.current.templateName} (${templateOwnerVer})"/>
                        <c:if test="${empty templateOwnerVer}">
                            <c:set var="templateFinalName" value="${globalLabelLetter}${status1.current.templateId}-${status1.current.templateName}"/>
                        </c:if>                         
                        <option value="<c:out value="${status1.current.templateId}"/>"> <c:out value="${templateFinalName}" escapeXml="false"/></option>						
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldLeft">
				<table>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<fmt:message key="label.description"/>:&nbsp;
						</td>
						<td>
							<textarea cols="75" rows="4" class="inputBox" name="templateDescription" id="templateDescription" readonly="true"></textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	 </table>
	</c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty templateCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="searchMaskTable">
	  <input name="noDataFound" id="noDataFound" type="hidden" value="Yes">
	  <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
 <input name="reportType" id="reportType" type="hidden" value="${param.reportType}">
 <input name="action" id="action" type="hidden" value="${action}">	
 <input name="reportTemplateId" id="reportTemplateId" type="hidden" value="">
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
