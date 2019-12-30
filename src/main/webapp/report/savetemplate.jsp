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

<script src="/js/report/savetemplate.js" language="JavaScript"></script>

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

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
<title>
<fmt:message key="savetemplate.title"/>
</title>

</head>

<body bgcolor="#ffffff" onload="onMyLoad();">
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseentertemplatename:"<fmt:message key="label.pleaseentertemplatename"/>",
pleaseenterresultdetails:"<fmt:message key="label.pleaseenterresultdetails"/>",
templateexistwarning:"<fmt:message key="label.templateexistwarning"/>"};

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

var altTemplateName = new Array(
   <c:forEach var="template" items="${templateCollection}" varStatus="status">
		<c:choose>
			<c:when test="${status.index == 0 && templateSize > 1}">
				'','<tcmis:jsReplace value="${status.current.templateName}"/>'
			</c:when>
		 <c:otherwise>
			<c:choose>
			  <c:when test="${status.index == 0}">
				 '<tcmis:jsReplace value="${status.current.templateName}"/>'
			  </c:when>
			  <c:otherwise>
				 ,'<tcmis:jsReplace value="${status.current.templateName}"/>'
			  </c:otherwise>
			</c:choose>
		 </c:otherwise>
		</c:choose>
	</c:forEach>
);



// -->
</script>

<tcmis:form action="/savetemplate.do" onsubmit="return SubmitOnlyOnce();">

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
                <a href="javascript:auditSave();"><fmt:message key="label.save"/></a> |
                <a href="javascript:closeWindow();"><fmt:message key="label.cancel"/></a>
           </div> <%-- mainUpdateLinks Ends --%>
       </div> <%-- boxhead Ends --%>
    
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<html:hidden property="reportName" value="${param.reportName}"/>
		<tr><td class="optionTitleBoldCenter"><fmt:message key="savetemplate.title"/></td></tr>

		<c:choose>
			<c:when test="${empty templateCollection}">
				 <tr>
					<td class="optionTitleBoldLeft">
					  <fmt:message key="label.name"/>:&nbsp;
					  <input class="inputBox" id="newTemplateName" type="text" name="newTemplateName" value="<c:out value=""/>" size="25">
					</td>
				 </tr>
				 <tr>
					<td class="optionTitleBoldLeft">
						<table>
							<tr>
								<td><fmt:message key="label.description"/>:&nbsp;</td>
								<td><textarea cols="75" rows="4" class="inputBox" name="newTemplateDescription" id="newTemplateDescription"></textarea></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="optionTitleBoldLeft">
						<input name="status" id="updateExisting" type="radio" class="radioBtns" onclick="statusChanged()" value="UPDATE">
        				<fmt:message key="label.updateexistingtemplate"/> :&nbsp;
						<select class="selectBox" name="templateId" id="templateId" onchange="templateChanged()" >
							<c:set var="templateColl" value='${templateCollection}'/>
							<bean:size id="templateSize" name="templateColl"/>
							<c:if test="${templateSize > 1}">
								<option value=""><fmt:message key="label.selectOne"/></option>
							</c:if>
							<c:forEach var="templateColBean" items="${templateCollection}" varStatus="status1">
								<c:set var="globalLabelLetter"><fmt:message key="${status1.current.globalizationLabelLetter}"/></c:set>
								<option value="<c:out value="${status1.current.templateId}"/>"><c:out value="${globalLabelLetter}${status1.current.templateId}-${status1.current.templateName}" escapeXml="false"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:message key="label.name"/>:&nbsp;
						<input class="inputBox" id="existingTemplateName" type="text" name="existingTemplateName" value="<c:out value=""/>" size="25"/>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldLeft">
						<table>
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<fmt:message key="label.description"/>:&nbsp;
								</td>
								<td><textarea cols="75" rows="4" class="inputBox" name="existingTemplateDescription" id="existingTemplateDescription"></textarea></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldLeft">
						<input name="status" id="createNew" type="radio" class="radioBtns" onclick="statusChanged()" value="NEW" checked/>
        				<fmt:message key="label.createnewtemplate"/>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <fmt:message key="label.name"/>:&nbsp;
					  <input class="inputBox" id="newExistingTemplateName" type="text" name="newExistingTemplateName" value="<c:out value=""/>" size="25"/>
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
									<textarea cols="75" rows="4" class="inputBox" name="newExistingTemplateDescription" id="newExistingTemplateDescription"></textarea>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		<c:if test="${param.gateKeeping == true}">
		     <tr>    
               <td class="optionTitleBoldLeft" colspan="2">
                    <input type="checkbox" name="includeOpenOrders" id="includeOpenOrders" class="radioBtns" <c:if test="${templateBean.includeOpenOrders == 'Y'}">checked</c:if> value="Y" />
                    <fmt:message key="label.includeopenorders"/>
               </td>
            </tr>
            <tr>    
               <td class="optionTitleBoldLeft"><fmt:message key="label.gatekeepingpositiveresult"/></td>
            </tr>
            <tr>
               <td class="optionTitleBoldLeft">
                   <table>
                      <%--
                      <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.facility"/>:&nbsp;
                           </td>
                           <td><c:out value="${param.facilityId}"/></td>
                       </tr>
                       --%> 
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.usergroup"/>:*&nbsp;
                           </td>
                           <td>
                                <select class="selectBox" name="emailUserGroupId" id="emailUserGroupId" >
		                            <c:set var="userGroupCollection" value='${userGroupCollection}'/>
		                            <bean:size id="userGroupSize" name="userGroupCollection"/>
		                            <c:if test="${userGroupSize > 1}">
		                                <option value=""><fmt:message key="label.selectOne"/></option>
		                            </c:if>
		                            <c:forEach var="userGroupBean" items="${userGroupCollection}" varStatus="statusUg">		                                
		                                <option value="<c:out value="${statusUg.current.userGroupId}"/>" <c:if test="${statusUg.current.userGroupId == templateBean.emailUserGroupId}">selected</c:if> ><c:out value="${statusUg.current.userGroupDesc}" escapeXml="false"/></option>
		                            </c:forEach>
		                        </select>
		                   </td>
                       </tr>
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.subject"/>:*&nbsp;
                           </td>
                           <td><input class="inputBox" type="text" name="emailSubject" id="emailSubject" size="73" value="${templateBean.emailSubject}"/></td>
                       </tr>
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.message"/>:*&nbsp;
                           </td>
                           <td><textarea cols="75" rows="4" class="inputBox" name="emailMessage" id="emailMessage">${templateBean.emailMessage}</textarea></td>
                       </tr>
                   </table>
               </td>
           </tr>
           <tr>    
               <td class="optionTitleBoldLeft"><fmt:message key="label.gatekeepingnegativeresult"/></td>
           </tr>
           <tr>
               <td class="optionTitleBoldLeft">
                   <table>
                      <%-- 
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.facility"/>:&nbsp;
                           </td>
                           <td><c:out value="${param.facilityId}"/></td>
                       </tr>
                       --%>
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.usergroup"/>:&nbsp;
                           </td>
                           <td>
                               <select class="selectBox" name="emailUserGroupIdNeg" id="emailUserGroupIdNeg">
                                    <c:set var="userGroupCollection1" value='${userGroupCollection}'/>
                                    <bean:size id="userGroupSize1" name="userGroupCollection1"/>
                                    <option value=""><fmt:message key="label.selectOne"/></option>
                                    <c:forEach var="userGroupBean1" items="${userGroupCollection}" varStatus="statusUg1">
                                        <option value="<c:out value="${statusUg1.current.userGroupId}"/>" <c:if test="${statusUg1.current.userGroupId == templateBean.emailUserGroupIdNeg}">selected</c:if> ><c:out value="${statusUg1.current.userGroupDesc}" escapeXml="false"/></option>
                                    </c:forEach>
                               </select>
                           </td>
                       </tr>
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.subject"/>:&nbsp;
                           </td>
                           <td><input class="inputBox" type="text" name="emailSubjectNeg" id="emailSubjectNeg" size="73" value="${templateBean.emailSubjectNeg}"/></td>
                       </tr>
                       <tr>
                           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <fmt:message key="label.message"/>:&nbsp;
                           </td>
                           <td><textarea cols="75" rows="4" class="inputBox" name="emailMessageNeg" id="emailMessageNeg">${templateBean.emailMessageNeg}</textarea></td>
                       </tr>
                   </table>
               </td>
           </tr>
        </c:if>
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input name="reportType" id="reportType" type="hidden" value="${param.reportType}"/>
 <input name="gateKeeping" id="gateKeeping" type="hidden" value="${param.gateKeeping}"/>
 <input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}"/>
<!-- Hidden elements end -->
</div>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
