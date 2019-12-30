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
		<%--
		<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
		<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
		<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
		--%> 
		
<%-- Add any other javascript you need for the page here --%>
<script src="/js/common/cabinet/clientcabinetbin.js" language="JavaScript"></script>

<title><fmt:message key="label.addpart" /></title>

<script language="JavaScript" type="text/javascript">
<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
	stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",
	leadTimeDaysInteger:"<fmt:message key="error.leadtimedays.integer"/>",
	reorderPointRequired:"<fmt:message key="error.reorderpoint.required"/>",
	stockingLevelRequired:"<fmt:message key="error.stockinglevel.required"/>",
	leadTimeDaysRequired:"<fmt:message key="error.leadtimedays.required"/>",
	binNameRequired:"<fmt:message key="error.binname.required"/>",
	facPartNoRequired:"<fmt:message key="error.partnumber.required"/>",
	pleasewait:"<fmt:message key="label.pleasewait"/>",
	all:"<fmt:message key="label.all"/>",
	errors:"<fmt:message key="label.errors"/>",
	reorderQuantityRequired:"<fmt:message key="error.reorderquantity.required"/>",
	reorderQuantityInteger:"<fmt:message key="error.reorderquantity.integer"/>",
	kanbanReorderQuantityInteger:"<fmt:message key="error.kanbanreorderquantity.integer"/>",	
	reorderPointLessThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>",
	waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
	waitFor:"<fmt:message key="label.partnumber"/>",
	selecta:"<fmt:message key="errors.selecta"/>",
	tieriistoragetemperature:"<fmt:message key="label.tieriistoragetemperature"/>"
	};

<c:set var="tmpCount" value='0'/>
var altCompanyId = new Array(
<c:forEach var="catalogFacilityBean" items="${catalogFacilityBeanCollection}" varStatus="status">
	<c:if test="${catalogFacilityBean.display == 'Y'}">
	   <c:if test="${tmpCount > 0}">,</c:if>
		'<tcmis:jsReplace value="${catalogFacilityBean.companyId}"/>'
	   <c:set var="tmpCount" value='${tmpCount+1}'/>
	</c:if>
</c:forEach>
);

<c:set var="tmpCount" value='0'/>
var altCatalogCompanyId = new Array(
<c:forEach var="catalogFacilityBean" items="${catalogFacilityBeanCollection}" varStatus="status">
	<c:if test="${catalogFacilityBean.display == 'Y'}">
	   <c:if test="${tmpCount > 0}">,</c:if>
		'<tcmis:jsReplace value="${catalogFacilityBean.catalogCompanyId}"/>'
	   <c:set var="tmpCount" value='${tmpCount+1}'/>
	</c:if>
</c:forEach>
);

	// -->
	</script>
</head>
	
	<body bgcolor="#ffffff" onload="myOnLoad('${param.uAction}');" onunload="closeThisWindow();closeAllchildren();">
		<tcmis:form action="/clientcabinetbin.do" onsubmit="return submitOnlyOnce();">
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
							<td class="optionTitleBoldRight"><fmt:message key="label.facility" />:</td>
							<td>
								<c:out value="${param.facilityName}" />
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.workarea" />:</td>
							<td>
								<c:out value="${param.applicationDesc}" />
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.catalog" />:</td>
							<td>
		               	<select name="catalogId" id="catalogId" class="selectBox">
									<c:forEach var="catalogFacilityBean" items="${catalogFacilityBeanCollection}" varStatus="status">
										<c:if test="${catalogFacilityBean.display == 'Y'}">
											<option value="<c:out value="${catalogFacilityBean.catalogId}"/>">
												<c:out value="${catalogFacilityBean.catalogDesc}"/>
											</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.partnumber" />:</td>
							<td>
								<input name="facPartNo" id="facPartNo" type="text" class="inputBox" value="<c:out value="${param.facPartNo}"/>" readonly="true">
					 			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedFacPartNo" id="selectedFacPartNo" value="..." align="right" onClick="lookupPartNumber();"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.reorderpoint" />:</td>
							<c:set var="reorderPoint" value="${param.reorderPoint}" />
							<c:if test="${reorderPoint == null}">
								<c:set var="reorderPoint" value="0" />
							</c:if>
							<td><input name="reorderPoint" id="reorderPoint" type="text" class="inputBox" value="<c:out value="${reorderPoint}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.stockinglevel" />:</td>
							<c:set var="stockingLevel" value="${param.stockingLevel}" />
							<c:if test="${stockingLevel == null}">
								<c:set var="stockingLevel" value="0" />
							</c:if>
							<td><input name="stockingLevel" id="stockingLevel" type="text" class="inputBox" value="<c:out value="${stockingLevel}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.reorderquantity" />:</td>
							<c:set var="reorderQuantity" value="${param.reorderQuantity}" />
							<c:if test="${reorderQuantity == null}">
								<c:set var="reorderQuantity" value="" />
							</c:if>
							<td><input name="reorderQuantity" id="reorderQuantity" type="text" class="inputBox" value="<c:out value="${reorderQuantity}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.kanbanreorderqty" />:</td>
							<c:set var="kanbanReorderQuantity" value="${param.kanbanReorderQuantity}" />
							<c:if test="${kanbanReorderQuantity == null}">
								<c:set var="kanbanReorderQuantity" value="" />
							</c:if>
							<td><input name="kanbanReorderQuantity" id="kanbanReorderQuantity" type="text" class="inputBox" value="<c:out value="${kanbanReorderQuantity}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.leadtimeindays" />:</td>
							<c:set var="leadTimeDays" value="${param.leadTimeDays}" />
							<c:if test="${leadTimeDays == null}">
								<c:set var="leadTimeDays" value="0" />
							</c:if>
							<td><input name="leadTimeDays" id="leadTimeDays" type="text" class="inputBox" value="<c:out value="${leadTimeDays}"/>" maxlength="5"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.counttype" />:</td>
							<td>
								<html:select property="countType" styleClass="selectBox" styleId="countType">									
                            <html:options collection="countTypeDropDownList" property="value" labelProperty="label"/>
                        </html:select>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.tieriistoragetemperature" />:</td>
							<td>
                        		  <select name="tierIIStorageTemperature" id="tierIIStorageTemperature" class="selectBox">
                        		  	<option value=""><fmt:message key='label.pleaseselect'/></option>
									<c:forEach var="bean" items="${vvTierIITemperatureCodeBeanColl}" varStatus="tierIIStorageTemperatureStatus">
											<option value="<c:out value="${bean.tierIITemperatureCode}"/>">
												<c:out value="${bean.tierIITemperature}"/>
											</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight">&nbsp;</td>
							<td class="optionTitleBoldRight">&nbsp;</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><input name="addButton" id="addButton" type="button" class="inputBtns" value="<fmt:message key="label.add"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onclick="submitAdd();">
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
		<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
		</div>
		
		<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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
				<input name="uAction" id="uAction" type="hidden">
				<input type="hidden" name="companyId" id="companyId" value="${param.companyId}">
				<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}">
				<input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}">
				<input type="hidden" name="application" id="application" value="${param.application}">
				<input type="hidden" name="cabinetId" id="cabinetId" value="${param.applicationId}">
				<input type="hidden" name="applicationId" id="applicationId" value="${param.applicationId}">
				<input type="hidden" name="applicationDesc" id="applicationDesc" value="${param.applicationDesc}">
				<input type="hidden" name="binId" id="binId" value="${param.binId}">
				<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="">
				<input type="hidden" name="partGroupNo" id="partGroupNo" value="">
				<input type="hidden" name="errorMsg" id="errorMsg" value="${tcmISError}">
				<input name="sourcePage" id="sourcePage" type="hidden" value="clientCabinetBin">
			</div>
			<!-- Hidden elements end -->
			</div>
			<!-- close of contentArea -->
				
		</tcmis:form>

	</body>
</html:html>
