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


		<!-- Add any other javascript you need for the page here -->
		<script type="text/javascript" src="/js/catalog/catalogspec.js"></script>


		<title><fmt:message key="catalogspec.title" /></title> <script
			language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
nospecification:"<fmt:message key="label.nospecification"/>",stdmfgcert:"<fmt:message key="label.stdmfgcert"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

	<tcmis:form action="/catalogspecresult.do"
		onsubmit="return submitFrameOnlyOnce();">

		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
		<c:set var="specUpdatePermission" value='' />
		<tcmis:catalogPermission indicator="true" userGroupId="CatalogSpec"
			catalogId="${param.catalogId}">
			<script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
			<c:set var="specUpdatePermission" value='Yes' />
		</tcmis:catalogPermission>

		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display: none;">
			<html:errors />
		</div>

		<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("catalogSpec")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="catalogspec.label.viewparthistory"/>;url=javascript:viewPartHistory();");
 aI("text=<fmt:message key="catalogspec.label.viewspechistory"/>;url=javascript:viewSpecHistory();");
    <c:choose>
     <c:when test="${specUpdatePermission == 'Yes'}" >
       aI("text=<fmt:message key="label.addrow"/>;url=javascript:addRow();");
     </c:when>
     <c:otherwise>
       aI("type=header;text=<fmt:message key="label.addrow"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
     </c:otherwise>
    </c:choose>
}

with(milonic=new menuname("catalogSpecNew")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="catalogspec.label.viewparthistory"/>;url=javascript:viewPartHistory();");
 aI("type=header;text=<fmt:message key="catalogspec.label.viewspechistory"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
 aI("type=header;text=<fmt:message key="label.addrow"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
}

drawMenus();
// -->
</script>

		<script type="text/javascript">/*YAHOO.namespace("example.aqua");
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
</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">

				<c:if test="${catalogSpecViewBeanCollection != null}">
					<!-- Search results start -->

					<c:set var="colorClass" value='' />
					<c:set var="dataCount" value='${0}' />

					<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
					<c:if test="${!empty catalogSpecViewBeanCollection}">

						<table width="100%" class="tableResults" id="resultsPageTable"
							border="0" cellpadding="0" cellspacing="0">
							<c:forEach var="row"
								items="${catalogSpecViewBeanCollection}" varStatus="status">

								<c:if test="${status.index % 10 == 0}">
									<!-- Need to print the header every 10 rows-->
									<tr>
										<th width="15%"><fmt:message key="label.catalog" /></th>
										<th width="15%"><fmt:message key="label.partnumber" /></th>
										<th width="15%"><fmt:message key="label.specification" /></th>
										<th width="5%"><fmt:message key="catalogspec.label.coc" /></th>
										<th width="5%"><fmt:message key="catalogspec.label.coa" /></th>
										<th width="5%"><fmt:message key="label.itar" /></th>
										<th width="5%"><fmt:message key="label.delete" /></th>
									</tr>
								</c:if>

								<c:choose>
									<c:when test="${status.index % 2 == 0}">
										<c:set var="colorClass" value='' />
									</c:when>
									<c:otherwise>
										<c:set var="colorClass" value='alt' />
									</c:otherwise>
								</c:choose>

								<tr class="<c:out value="${colorClass}"/>"
									id="rowId<c:out value="${status.index}"/>"
									onmouseup="selectRow('<c:out value="${status.index}"/>')">

									<input name="colorClass<c:out value="${status.index}"/>"
										id="colorClass<c:out value="${status.index}"/>"
										value="<c:out value="${colorClass}"/>" type="hidden">
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].newRow"
										id="newRow<c:out value="${status.index}"/>" type="hidden"
										value="false"> 
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].catalogId"
										id="catalogId<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.catalogId}"/>">
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].catPartNo"
										id="catPartNo<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.catPartNo}"/>">
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].specId"
										id="specId<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.specId}"/>">
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].oldCoc"
										id="oldCoc<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.coc}"/>"> 
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].oldCoa"
										id="oldCoa<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.coa}"/>"> 
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].oldItar"
										id="oldItar<c:out value="${status.index}"/>" type="hidden"
										value="<c:out value="${row.itar}"/>"> 
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].onLine"
										id="onLine<c:out value="${status.index}"/>"
										type="hidden"
										value="<c:out value="${row.onLine}"/>">
									<input
										name="catalogSpecInputBean[<c:out value="${status.index}"/>].content"
										id="content<c:out value="${status.index}"/>"
										type="hidden"
										value="<c:out value="${row.content}"/>">

									<td width="15%"><c:out
											value="${row.catalogId}" /></td>
									<td width="15%"><c:out
											value="${row.catPartNo}" /></td>
									<td width="15%"><c:choose>
											<c:when test="${row.onLine == 'Y'}">
												<a
													href="javascript:viewSpec('${status.index}')"><c:out
														value="${row.specification}" /></a>
											</c:when>
											<c:otherwise>
												<c:out value="${row.specification}" />
											</c:otherwise>
										</c:choose></td>
									<td width="5%" id="rowId<c:out value="${status.index}"/>">
										<c:choose>
											<c:when test="${row.specId != 'No Specification' && specUpdatePermission == 'Yes'}">													
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coc"
													id="coc<c:out value="${status.index}"/>"
													type="checkbox" value="Y" <c:if test="${row.coc == 'Y'}">checked</c:if> />
											</c:when>
											<c:when test="${row.specId != 'No Specification' && specUpdatePermission != 'Yes'}">
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coc"
													id="coc<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.coc}"/>"> 
												<c:choose>
													<c:when test="${row.coc == 'Y'}">
														<img src=/images/item_chk1.gif border=0 alt=Active align="center">
													</c:when>
													<c:otherwise>
														<img src=/images/item_chk0.gif border=0 alt=Active align="center">
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coc"
													id="coc<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.coc}"/>"> 
												&nbsp;
											</c:otherwise>
										</c:choose>
									</td>

									<td width="5%" id="rowId<c:out value="${status.index}"/>"><c:choose>
											<c:when test="${row.specId != 'No Specification' && specUpdatePermission == 'Yes'}">
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coa"
													id="coa<c:out value="${status.index}"/>"
													type="checkbox" value="Y" <c:if test="${row.coa == 'Y'}">checked</c:if> />
											</c:when>
											<c:when test="${row.specId != 'No Specification' && specUpdatePermission != 'Yes'}">
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coa"
													id="coa<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.coa}"/>"> 
											
												<c:choose>
													<c:when test="${row.coa == 'Y'}">
														<img src=/images/item_chk1.gif border=0
															alt=Active align="center">
													</c:when>
													<c:otherwise>
														<img src=/images/item_chk0.gif border=0
															alt=Active align="center">
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].coa"
													id="coa<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.coa}"/>"> 
												&nbsp;
											</c:otherwise>
										</c:choose></td>

									<td width="5%" id="rowId<c:out value="${status.index}"/>">
										<c:choose>
											<c:when	test="${row.specId != 'No Specification' && specUpdatePermission == 'Yes'}">
													<input
														name="catalogSpecInputBean[<c:out value="${status.index}"/>].itar"
														id="itar<c:out value="${status.index}"/>"
														type="checkbox" value="true" <c:if test="${row.itar}">checked</c:if>
											</c:when>
											<c:when test="${row.specId != 'No Specification' && specUpdatePermission != 'Yes'}">
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].itar"
													id="itar<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.itar}"/>"> 
												<c:choose>
													<c:when test="${row.itar}">
														<img src=/images/item_chk1.gif border=0
															alt="Non-Itar" align="center">
													</c:when>
													<c:otherwise>
														<img src=/images/item_chk0.gif border=0
															alt="ITAR" align="center">
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].itar"
													id="itar<c:out value="${status.index}"/>" type="hidden"
													value="<c:out value="${row.itar}"/>"> 
												&nbsp;
											</c:otherwise>
										</c:choose>
									</td>

									<td width="5%"
									id="rowId<c:out value="${status.index}"/>"><c:choose>
											<c:when test="${specUpdatePermission == 'Yes'}">
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].delete"
													id="delete<c:out value="${status.index}"/>"
													type="checkbox" value="Y"
													onclick="deleteSpec(<c:out value="${status.index}"/>)">
											</c:when>
											<c:otherwise>
												<input
													name="catalogSpecInputBean[<c:out value="${status.index}"/>].delete"
													id="delete<c:out value="${status.index}"/>"
													type="checkbox" value="Y" style="display: none">
											</c:otherwise>
										</c:choose> &nbsp;</td>
								</tr>

								<c:set var="dataCount" value='${dataCount+1}' />

							</c:forEach>
						</table>
					</c:if>
					<!-- If the collection is empty say no data found -->
					<c:if test="${empty catalogSpecViewBeanCollection}">

						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%"><fmt:message key="main.nodatafound" /></td>
							</tr>
						</table>
					</c:if>

					<!-- Search results end -->
				</c:if>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
					<input name="action" id="action" value="" type="hidden">
					<input name="companyId" id="companyId" value="${personnelBean.companyId}" type="hidden">
					<input name="historyCatalogId" id="historyCatalogId" type="hidden">
					<input name="historySpecId" id="historySpecId" type="hidden">
					<input name="historyCatPartNo" id="historyCatPartNo" type="hidden">
					<input name="minHeight" id="minHeight" type="hidden" value="150">
                    <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','',personnelBean.companyId)}'/
                    <%-- Store all search criteria in hidden elements, need this to requery the database after updates --%>
					<tcmis:saveRequestParameter />
				</div>
				<!-- Hidden elements end -->

			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->

	</tcmis:form>

</body>
</html:html>