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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon"
			href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

		<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:fontSizeCss />
		<!-- CSS for YUI -->
		<link rel="stylesheet" type="text/css"
			href="/yui/build/container/assets/container.css" />
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
		<%@ include file="/common/rightclickmenudata.jsp"%>

		<!-- For Calendar support -->
		<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


		<!-- Add any other javascript you need for the page here -->
		<script type="text/javascript" src="/js/common/admin/userprofile.js"></script>

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

		<title><fmt:message key="label.userProfile" /></title> <script
			language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onLoad="myResultOnLoad()">

	<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;">
		<html:errors />
		${tcmISError}
	</div>

	<script type="text/javascript">/*YAHOO.namespace("example.aqua");
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
</script>
	<!-- Error Messages Ends -->

	<div class="interface" id="resultsPage">
		<!-- start of interface -->
		<div class="backGroundContent">
			<!-- start of backGroundContent -->

			<c:set var="colorClass" value='' />
			<c:set var="dataCount" value='${0}' />

			<c:set var="module">
				<tcmis:module />
			</c:set>

			<c:set var="disableButton" value='' />
			<c:set var="showThisFrame" value='true' />
			<c:if test="${createNewUserCount == 0 && userId != personnelId }">
				<c:set var="disableButton" value='style="display: none;"' />
				<c:set var="showThisFrame" value='true' />
			</c:if>

            <!-- hide this section if logged as catalog vendor -->
			<c:if test="${module == 'cv'}">
                <c:set var="showThisFrame" value='false' />
            </c:if>

			<c:set var="dataCount" value='${0}' />
			<c:set var="colorClass" value='' />

			<table width="100%" class="tableResults" id="resultsPageTable"
				border="0" cellpadding="0" cellspacing="0">

				<c:if test='${module != "supplier" && !personnelBean.standalone}'>
					<c:set var="detailCount" value='${detailCount+1}' />
					<tr class="${colorClass}">
						<td width="5%" class="alignRight"><input type="button"
							name="companiesPage" id="companiesPage"
							value="<fmt:message key="label.companies"/>" class="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onClick="openCompaniesPages()" ${disableButton} /></td>
						<td width="85%" class="alignLeft"><fmt:message
								key="label.companiesMessage" /></td>
					</tr>
				</c:if>

				<c:choose>
					<c:when test="${detailCount % 2 == 0}">
						<c:set var="colorClass" value='' />
					</c:when>
					<c:otherwise>
						<c:set var="colorClass" value='alt' />
					</c:otherwise>
				</c:choose>
				<tr class="${colorClass}">
					<td width="5%" class="alignRight"><input type="button"
						name="pagesPage" id="pagesPage"
						value="<fmt:message key="label.pages"/>" class="inputBtns"
						onmouseover="this.className='inputBtns inputBtnsOver'"
						onmouseout="this.className='inputBtns'" onClick="openPagesPages()"
						${disableButton} /></td>
					<td width="85%" class="alignLeft"><fmt:message
							key="label.pagesMessage" /></td>
				</tr>
				<c:set var="detailCount" value='${detailCount+1}' />

				<c:set var="operationsPermisisons" value="false" />
				<tcmis:opsEntityPermission indicator="true"
					userGroupId="operationsSupport">
					<c:set var="operationsPermisisons" value="true" />
				</tcmis:opsEntityPermission>

				<tcmis:opsEntityPermission indicator="true" userGroupId="developers">
					<c:set var="operationsPermisisons" value="true" />
				</tcmis:opsEntityPermission>

				<c:if test='${module == "haas" && operationsPermisisons}'>
					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>

					<tr class="${colorClass}">
						<td width="5%" class="alignRight"><input type="button"
							name="entityPage" id="entityPage"
							value="<fmt:message key="label.entity"/>" class="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onClick="openEntityPages()" ${disableButton} /></td>
						<td width="85%" class="alignLeft"><fmt:message
								key="label.pagesMessage" /></td>
					</tr>
					<c:set var="detailCount" value='${detailCount+1}' />

					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>
					<tr class="${colorClass}">
						<td width="5%" class="alignRight"><input type="button"
							name="operationsPage" id="operationsPage"
							value="<fmt:message key="label.operations"/>" class="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onClick="openOperationsPages()" ${disableButton} /></td>
						<td width="85%" class="alignLeft"><fmt:message
								key="label.pagesMessage" /></td>
					</tr>
					<c:set var="detailCount" value='${detailCount+1}' />
				</c:if>

				<c:choose>
					<c:when test="${detailCount % 2 == 0}">
						<c:set var="colorClass" value='' />
					</c:when>
					<c:otherwise>
						<c:set var="colorClass" value='alt' />
					</c:otherwise>
				</c:choose>

				<c:if test='${module != "supplier"}'>
						<tr class="${colorClass}">
							<td width="5%" class="alignRight"><input type="button"
								name="facilitiesPage" id="facilitiesPage"
								value="<fmt:message key="label.facilities"/>" class="inputBtns"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onClick="openFacilitiesPages()" ${disableButton} /></td>
							<td width="85%" class="alignLeft"><fmt:message
									key="label.facilitiesMessage" /></td>
						</tr>
					<c:set var="detailCount" value='${detailCount+1}' />
					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>

					<tcmis:facilityPermission indicator="true" userGroupId="Administrator" companyId="${personnelBean.companyId}">
						<tr class="${colorClass}">
							<td width="5%" class="alignRight"><input type="button"
								name="facilityLocalePage" id="facilityLocalePage"
								value="<fmt:message key="label.facilitylocale"/>" class="inputBtns"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onClick="openFacilityLocalePages()" ${disableButton} /></td>
							<td width="85%" class="alignLeft"><fmt:message
									key="label.facilityLocaleMessage" /></td>
						</tr>
					</tcmis:facilityPermission>


					<c:set var="detailCount" value='${detailCount+1}' />
					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>

					<c:if test='${module != "usgov"}'>
						<tr class="${colorClass}">
							<td width="5%" class="alignRight"><input type="button"
								name="workareasPage" id="workareasPage"
								value="<fmt:message key="label.workareas"/>" class="inputBtns"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onClick="openWorkareasPages()" ${disableButton} /></td>
							<td width="85%" class="alignLeft"><fmt:message
									key="label.workareaMessage" /></td>
						</tr>

						<c:if test="${!personnelBean.standalone}">
						<%--
							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>

							
			   <tr class="${colorClass}">
			      <td width="5%" class="alignRight">
					  <input type="button" name="cabinetsPage" id="cabinetsPage" value="<fmt:message key="label.cabinets"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="openCabinetsPages()" ${disableButton}/>
				  </td>
				  <td width="85%" class="alignLeft">
					<fmt:message key="label.cabinetMessage"/>
				  </td>
			   </tr>
			   --%>

							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>

							<tr class="${colorClass}">
								<td width="5%" class="alignRight"><input type="button"
									name="docksPage" id="docksPage"
									value="<fmt:message key="label.docks"/>" class="inputBtns"
									onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'"
									onClick="openDocksPages()" ${disableButton} /></td>
								<td width="85%" class="alignLeft"><fmt:message
										key="label.docksMessage" /></td>
							</tr>

							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>

							<tr class="${colorClass}">
								<td width="5%" class="alignRight"><input type="button"
									name="financialApproverPage" id="financialApproverPage"
									value="<fmt:message key="label.financialApproverTitle"/>"
									class="inputBtns"
									onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'"
									onClick="openFinancialApprover()" ${disableButton} /></td>
								<td width="85%" class="alignLeft"><fmt:message
										key="label.financialApproverMessage" /></td>
							</tr>
							
							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>

							<tr class="${colorClass}">
								<td width="5%" class="alignRight"><input type="button"
									name="poApproverPage" id="poApproverPage"
									value="<fmt:message key="label.poApproverTitle"/>"
									class="inputBtns"
									onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'"
									onClick="openPoApprover()" ${disableButton} /></td>
								<td width="85%" class="alignLeft"><fmt:message
										key="label.poApproverMessage" /></td>
							</tr>

							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>
							<tr class="${colorClass}">
								<td width="5%" class="alignRight"><input type="button"
									name="userGroupPermission" id="userGroupPermission"
									value="<fmt:message key="label.orderusergroup"/>"
									class="inputBtns"
									onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'"
									onClick="openUserGroupPermission()" ${disableButton} /></td>
								<td width="85%" class="alignLeft"><fmt:message
										key="label.usergroupperms" /></td>
							</tr>
							<tcmis:facilityPermission indicator="true" userGroupId="ListApprovalAdmin" companyId="${personnelBean.companyId}">
								<c:set var="detailCount" value='${detailCount+1}' />
								<c:choose>
									<c:when test="${detailCount % 2 == 0}">
										<c:set var="colorClass" value='' />
									</c:when>
									<c:otherwise>
										<c:set var="colorClass" value='alt' />
									</c:otherwise>
								</c:choose>
								<tr class="${colorClass}">
									<td width="5%" class="alignRight"><input type="button"
										name="facilityListApproval" id="facilityListApproval"
										value="<fmt:message key="label.listapprovalperms"/>"
										class="inputBtns"
										onmouseover="this.className='inputBtns inputBtnsOver'"
										onmouseout="this.className='inputBtns'"
										onClick="openFacilityListApproval()" ${disableButton} /></td>
									<td width="85%" class="alignLeft"><fmt:message
											key="label.listapprovalmessage" /></td>
								</tr>
							</tcmis:facilityPermission>

							<c:set var="detailCount" value='${detailCount+1}' />
							<c:choose>
								<c:when test="${detailCount % 2 == 0}">
									<c:set var="colorClass" value='' />
								</c:when>
								<c:otherwise>
									<c:set var="colorClass" value='alt' />
								</c:otherwise>
							</c:choose>

							<tr class="${colorClass}">
								<td width="5%" class="alignRight"><input type="button"
									name="userGroupReportPublishPermission"
									id="userGroupReportPublishPermission"
									value="<fmt:message key="label.reportusergroup"/>"
									class="inputBtns"
									onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'"
									onClick="openUserGroupReportPublishPermission()"
									${disableButton} /></td>
								<td width="85%" class="alignLeft"><fmt:message
										key="label.publishreportusergroupdesc" /></td>
							</tr>
						</c:if>
					</c:if>
				</c:if>

				<c:if test='${module == "supplier"}'>
					<tr class="${colorClass}">
						<td width="5%" class="alignRight"><input type="button"
							name="suppliersPage" id="suppliersPage"
							value="<fmt:message key="label.suppliers"/>" class="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onClick="openSupplierPages()" ${disableButton} /></td>
						<td width="85%" class="alignLeft"><fmt:message
								key="label.suppliersmessage" /></td>
					</tr>

					<c:set var="detailCount" value='${detailCount+1}' />
					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>

					<tr class="${colorClass}">
						<td width="5%" class="alignRight"><input type="button"
							name="supplierLocationsPage" id="supplierLocationsPage"
							value="<fmt:message key="label.supplierlocations"/>"
							class="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onClick="openSupplierLocationPages()" ${disableButton} /></td>
						<td width="85%" class="alignLeft"><fmt:message
								key="label.supplierlocationsmessage" /></td>
					</tr>

					<c:set var="detailCount" value='${detailCount+1}' />
					<c:choose>
						<c:when test="${detailCount % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>
				</c:if>

			</table>

			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<input name="totalLines" id="totalLines" value="${dataCount}"
					type="hidden"> <input name="showResultFrame"
					id="showResultFrame" value="${showThisFrame}" type="hidden">

						<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
			</div>
			<!-- Hidden elements end -->

		</div>
		<!-- close of backGroundContent -->
	</div>
	<!-- close of interface -->

</body>
</html:html>
