<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
		<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
		<tcmis:fontSizeCss /> 
		<%-- Add any other stylesheets you need for the page here --%>
		
		<%--
		<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
		<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
		--%> 
		<!-- This handels the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<script src="/js/common/disableKeys.js" language="JavaScript"></script> 
		<script src="/js/common/formchek.js" language="JavaScript"></script> 
		<script src="/js/common/commonutil.js" language="JavaScript"></script> 
		
		<%-- For Calendar support --%>
		<%--
		<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
		<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
		<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
		--%> 
		
		<%-- Add any other javascript you need for the page here --%> 
		<script src="/js/hub/cabinetbin.js" language="JavaScript"></script> 
		<script language="JavaScript" type="text/javascript">
		<!--

		var altCompanyId = new Array(
		<c:forEach var="bean" items="${catalogFacilityBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"<c:out value="${status.current.companyId}"/>"
		   </c:when>
		   <c:otherwise>
		    "<c:out value="${status.current.companyId}"/>"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
		);
		var altCatalogCompanyId = new Array(
		<c:forEach var="bean" items="${catalogFacilityBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"<c:out value="${status.current.catalogCompanyId}"/>"
		   </c:when>
		   <c:otherwise>
		    "<c:out value="${status.current.catalogCompanyId}"/>"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
		);
		<%--
		var altHubId = new Array(
		<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"<c:out value="${status.current.branchPlant}"/>"
		   </c:when>
		   <c:otherwise>
		    "<c:out value="${status.current.branchPlant}"/>"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
		);
		
		var altFacilityId = new Array();
		var altFacilityName = new Array();
		<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
		
		  altFacilityId["<c:out value="${currentHub}"/>"] = new Array(
		  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
		 <c:choose>
		   <c:when test="${status1.index > 0}">
		        ,"<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
		   </c:when>
		   <c:otherwise>
		        "<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
		   </c:otherwise>
		  </c:choose>
		  </c:forEach>
		  );
		
		  altFacilityName["<c:out value="${currentHub}"/>"] = new Array(
		  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
		 <c:choose>
		   <c:when test="${status1.index > 0}">
		        ,"<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
		   </c:when>
		   <c:otherwise>
		        "<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
		   </c:otherwise>
		  </c:choose>
		  </c:forEach>
		  );
		</c:forEach>
		
		var altApplicationId = new Array();
		var altApplicationDesc = new Array();
		<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
		  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
		    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
		    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
		
		      altApplicationId["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
		      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
		        <c:choose>
		          <c:when test="${status2.index > 0}">
		            ,"<c:out value="${status2.current.application}" escapeXml="false"/>"
		          </c:when>
		          <c:otherwise>
		            "<c:out value="${status2.current.application}" escapeXml="false"/>"
		          </c:otherwise>
		        </c:choose>
		      </c:forEach>
		      );
		
		      altApplicationDesc["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
		      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
		        <c:choose>
		          <c:when test="${status2.index > 0}">
		            ,"<c:out value="${status2.current.applicationDesc}" escapeXml="false"/>"
		          </c:when>
		          <c:otherwise>
		            "<c:out value="${status2.current.applicationDesc}" escapeXml="false"/>"
		          </c:otherwise>
		        </c:choose>
		      </c:forEach>
		      );
		
		  </c:forEach>
		</c:forEach>
		
		
		var altCabinetId = new Array();
		var altCabinetName = new Array();
		<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
		  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
		    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
		    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
		    <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
		      <c:set var="currentApplication" value='${status2.current.application}'/>
		      <c:set var="cabinetBeanCollection" value='${status2.current.cabinetBeanCollection}'/>
		
		
		      altCabinetId["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-<c:out value="${currentApplication}" escapeXml="false"/>"] = new Array(
		      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
		        <c:choose>
		          <c:when test="${status3.index > 0}">
		            ,"<c:out value="${status3.current.cabinetId}" escapeXml="false"/>"
		          </c:when>
		          <c:otherwise>
		            "<c:out value="${status3.current.cabinetId}" escapeXml="false"/>"
		          </c:otherwise>
		        </c:choose>
		      </c:forEach>
		      );
		
		      altCabinetName["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-<c:out value="${currentApplication}" escapeXml="false"/>"] = new Array(
		      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
		        <c:choose>
		          <c:when test="${status3.index > 0}">
		            ,"<c:out value="${status3.current.cabinetName}" escapeXml="false"/>"
		          </c:when>
		          <c:otherwise>
		            "<c:out value="${status3.current.cabinetName}" escapeXml="false"/>"
		          </c:otherwise>
		        </c:choose>
		      </c:forEach>
		      );
		    </c:forEach>
		  </c:forEach>
		</c:forEach>
		--%>

		// -->
		</script> 
		
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
		
		<title><fmt:message key="cabinetbin.title" /></title>
		
		<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
		stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",leadTimeDaysInteger:"<fmt:message key="error.leadtimedays.integer"/>",
		reorderPointRequired:"<fmt:message key="error.reorderpoint.required"/>",
		stockingLevelRequired:"<fmt:message key="error.stockinglevel.required"/>",leadTimeDaysRequired:"<fmt:message key="error.leadtimedays.required"/>",
		binNameRequired:"<fmt:message key="error.binname.required"/>", facPartNoRequired:"<fmt:message key="error.partnumber.required"/>",
		reorderPointGreaterThanZero:"<fmt:message key="label.reorderpointgreaterthanzero"/>",	
		reorderPointLessThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>"};
		// -->
		</script>
	</head>
	
	<body bgcolor="#ffffff" onload="myOnLoad();" onunload="try{opener.closeTransitWin();}catch(ex){}">
	
		<tcmis:form action="/cabinetbin.do" onsubmit="return submitOnlyOnce();">
			
			<div id="transitPage" class="optionTitleBoldCenter" style="display: none;"><br/><br/><br/><fmt:message key="label.pleasewait" />
			</div>
			<div class="contentArea" id="mainPage">

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
					   <input type="hidden" name="companyId" id="companyId" value="<c:out value="${param.companyId}"/>">
						<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="<c:out value="${param.catalogCompanyId}"/>">
						<input type="hidden" name="catalogDesc" id="catalogDesc" value="<c:out value="${param.catalogDesc}"/>">
						<input type="hidden" name="branchPlant" value="<c:out value="${param.branchPlant}"/>">
						<input type="hidden" name="facilityId" value="<c:out value="${param.facilityId}" escapeXml="false"/>">
						<input type="hidden" name="facilityName" id="facilityName" value="<c:out value="${param.facilityName}"/>">
						<input type="hidden" name="application" id="application" value="<c:out value="${param.application}"/>">
						<input type="hidden" name="applicationId" id="applicationId" value="<c:out value="${param.applicationId}"/>">
						<input type="hidden" name="cabinetId" id="cabinetId" value="<c:out value="${param.cabinetId}"/>">
						<input type="hidden" name="cabinetName" id="cabinetName" value="<c:out value="${param.cabinetName}"/>">

						<input type="hidden" name="partGroupNo" id="partGroupNo" value="1">
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.facility" />:</td>
							<td>
								<c:out value="${param.facilityName}" />
							</td>
						</tr>
						<%--
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.workarea" />:</td>
							<td>
								<c:out value="${param.application}" />
								<c:set var="selectedHub" value='${param.branchPlant}' />
								<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
									<c:set var="currentHub" value='${status.current.branchPlant}' />
									<c:if test="${currentHub == selectedHub}">
										<c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}' />
									</c:if>
								</c:forEach> 
								<c:set var="selectedFacility" value='${param.facilityId}' /> 
								<c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
									<c:set var="currentFacility" value='${status.current.facilityId}' />
									<c:if test="${currentFacility == selectedFacility}">
										<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}' />
									</c:if>
								</c:forEach> 
								<select name="application" id="application" class="selectBox" onchange="applicationChanged()">
									<c:set var="selectedApplication" value='${param.application}' />
									<c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status">
										<c:if test="${empty selectedApplication}">
											<c:set var="selectedApplication" value='${status.current.application}' />
											<c:set var="cabinetBeanCollection" value='${status.current.cabinetBeanCollection}' />
										</c:if>
			
										<c:set var="currentApplication" value='${status.current.application}' />
										<c:choose>
											<c:when test="${currentApplication == selectedApplication}">
												<c:set var="cabinetBeanCollection" value='${status.current.cabinetBeanCollection}' />
												<option value="<c:out value="${currentApplication}"/>" selected>
													<c:out value="${status.current.applicationDesc}" escapeXml="false" />
												</option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${currentApplication}"/>">
													<c:out value="${status.current.applicationDesc}" escapeXml="false" />
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>

							</td>
						</tr>
						--%>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.cabinet" />:</td>
							<td>
								<c:out value="${param.cabinetName}" />
								<%--
								<select name="cabinetId" id="cabinetId" class="selectBox">
									<c:set var="selectedCabinet" value='${param.cabinetId}' />
									<c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status">
										<c:set var="currentCabinet" value='${status.current.cabinetId}' />
										<c:choose>
											<c:when test="${currentCabinet == selectedCabinet}">
												<option value="<c:out value="${currentCabinet}"/>" selected>
													<c:out value="${status.current.cabinetName}" escapeXml="false" />
												</option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${currentCabinet}"/>">
													<c:out value="${status.current.cabinetName}" escapeXml="false" />
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								--%>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.catalog" />:</td>
							<td>
		                 <%-- <c:out value="${param.catalogDesc}" /> --%>
							<html:select property="catalogId" styleClass="selectBox" styleId="catalogId">
								<html:options collection="catalogFacilityBeanCollection" property="catalogId" />
							</html:select> 
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.binname" />:</td>
							<td><input name="binName" id="binName" type="text" class="inputBox" value="<c:out value="${param.binName}"/>" maxlength="30"></td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.partnumber" />:</td>
							<td><input name="facPartNo" id="facPartNo" type="text" class="inputBox" value="<c:out value="${param.facPartNo}"/>"></td>
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
							<td class="optionTitleBoldRight">&nbsp;</td>
						</tr>
						<tr>
							<td><input name="submitAdd" id="submitAdd" type="submit" class="inputBtns" value="<fmt:message key="label.add"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onclick="return validateForm();">
							</td>
							<td><input name="submitCancel" id="submitCancel" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>"
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
		
			<div class="spacerY">&nbsp;</div>
		
			<!-- Error Messages Begins -->
			<div id="errorMessagesArea" class="errorMessages">
				<c:choose>
					<c:when test="${!empty tcmisError}">
						${tcmisError}
					</c:when>
					<c:otherwise>
						<c:if test="${param.binName != null && param.facPartNo != null && requestScope['org.apache.struts.action.ERROR'] == null}">
               		Part <c:out value="${param.facPartNo}"/> has been added for bin <c:out value="${param.binName}"/>
            		</c:if>
					</c:otherwise>
				</c:choose>

			</div>
			<!-- Error Messages Ends --> <!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
			</div>
			<!-- Hidden elements end -->
			</div>
			<!-- close of contentArea -->
		
			<!-- Footer message start -->
			<div class="messageBar">&nbsp;</div>
			<!-- Footer message end -->
			
		</tcmis:form>
	</body>
</html:html>
