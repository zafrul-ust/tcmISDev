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
	
	<!-- This handles all the resizing of the page and frames -->
		<%--
		<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
		<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
		--%> 
		<!-- This handels the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<script type="text/javascript" src="/js/common/disableKeys.js" language="JavaScript"></script>
		<script type="text/javascript" src="/js/common/formchek.js" language="JavaScript"></script>
		<script type="text/javascript" src="/js/common/commonutil.js" language="JavaScript"></script>
			<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

		
		<%-- For Calendar support --%>
		
		<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
		<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
		<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
		
		
<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/updateedierror.js" language="JavaScript"></script>

<title><fmt:message key="label.editupdateedierror" /></title>

<script language="JavaScript" type="text/javascript">
<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	pleasewait:"<fmt:message key="label.pleasewait"/>",
	waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
	all:"<fmt:message key="label.all"/>",
	errors:"<fmt:message key="label.errors"/>"
    };


// -->
</script>
</head>
	
	<body bgcolor="#ffffff" onload="myOnLoad('${param.userAction}');" onunload="closeThisWindow();closeAllchildren();">
		<tcmis:form action="/ediordertrackingresults.do" onsubmit="return submitOnlyOnce();">
			<div id="transitPage" class="optionTitleBoldCenter" style="display: none;"><br/><br/><br/><fmt:message key="label.pleasewait" />
			</div>
			<div class="contentArea" id="mainPage">

			<script type="text/javascript">
				<!--
				/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
				<c:choose>
					<c:when test="${empty tcmISError}">
					 showErrorMessage = false;
					</c:when>
					<c:otherwise>
					 showErrorMessage = true;
					</c:otherwise>
				</c:choose>
			//-->
			</script>
			<!-- Error Messages Ends -->
			
			<!-- Search Option Begins -->
			<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					<div class="roundcont filterContainer">
					<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<!-- Insert all the search option within this div -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.company" />:</td>
                            <td>
                                <c:out value="${param.companyName}" />
                            </td>
                        </tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.facility" />:</td>
							<td>
								<c:out value="${param.facilityName}" />
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.ordernumber" />:</td>
							<td>
								<c:out value="${param.customerPoNo}" />
					 		</td>
						</tr>
						<tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.orderline" />:</td>
                            <td>
                                <c:out value="${param.customerPoLineNo}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.description" />:</td>
                            <td>
                                <c:out value="${param.partShortDesc}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.statusdetail" />:</td>
                            <td>
                                <c:out value="${param.statusDetail}" />
                            </td>
                        </tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.partnumber" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
								<input name="catPartNo" id="catPartNo" type="text" class="inputBox" value="<c:out value="${param.catPartNo}"/>" readonly="true">
					 			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCatPartNo" id="selectedCatPartNo" value="..." align="right" onClick="lookupPartNumber();"/>
                                <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearPartNumber()" />
                            </td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.quantity" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td><input name="quantity" id="quantity" type="text" class="inputBox" value="<c:out value="${param.quantity}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.uom" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td><input name="orderPartUom" id="orderPartUom" type="text" class="inputBox" value="<c:out value="${param.orderPartUom}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.shiptocity" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
                        		  <select name="shiptoCity" id="shiptoCity" class="selectBox">
                        		  	<option value=""><fmt:message key='label.pleaseselect'/></option>
                                    <c:set var="selectedShiptoCity" value='${param.shiptoCity}'/>
									<c:forEach var="bean" items="${shiptoCityBeanColl}" varStatus="shiptoCityStatus">
									    <c:set var="tmpSelect" value=''/>
									    <c:if test="${selectedShiptoCity == bean.facilityId}">
                                            <c:set var="tmpSelect" value='selected'/>
                                        </c:if>
                                        <option ${tmpSelect} value="<c:out value="${bean.facilityId}"/>"><c:out value="${bean.facilityName}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>

                        <tr>
							<td class="optionTitleBoldRight">&nbsp;</td>
							<td class="optionTitleBoldRight">&nbsp;</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><input name="updateButton" id="updateButton" type="button" class="inputBtns" value="<fmt:message key="label.update"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onclick="submitUpdate();">
							</td>
							<td class="optionTitleBoldLeft"><input name="submitCancel" id="submitCancel" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'" onclick="closeWindow();">
							</td>
						</tr>
					</table>
					<!-- End search options -->
					</div>
					<div class="roundbottom">
						<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
					</div>
					</div>
					</div>
					</td>
				</tr>
			</table>
			<!-- Search Option Ends -->
		<div id="transitDialogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
		</div>
		
		<div id="transitDialogWinBody" class="errorMessages" style="display: none;">
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
		
			<div class="spacerY">&nbsp;</div>

			<div id="hiddenElements" style="display: none;">
			    <input name="userAction" id="userAction" type="hidden">
				<input type="hidden" name="companyId" id="companyId" value="${param.companyId}">
				<input type="hidden" name="companyName" id="companyName" value="${param.companyName}">
				<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}">
				<input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}">
				<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}">
				<input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}">
				<input type="hidden" name="partGroupNo" id="partGroupNo" value="">
				<input type="hidden" name="customerPoNo" id="customerPoNo" value="${param.customerPoNo}">
				<input type="hidden" name="customerPoLineNo" id="customerPoLineNo" value="${param.customerPoLineNo}">
				<input type="hidden" name="partShortDesc" id="partShortDesc" value="${param.partShortDesc}">
				<input type="hidden" name="statusDetail" id="statusDetail" value="${param.statusDetail}">
				<input type="hidden" name="errorMsg" id="errorMsg" value="${tcmISError}">
            </div>
			<!-- Hidden elements end -->
			</div>
			<!-- close of contentArea -->
				
		</tcmis:form>

	</body>
</html:html>
