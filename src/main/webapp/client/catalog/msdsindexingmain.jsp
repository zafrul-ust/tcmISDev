<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="expires" content="-1"/>
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

		<%@ include file="/common/locale.jsp" %>

		<!--Use this tag to get the correct CSS class. -->

		<tcmis:gridFontSizeCss overflow="auto"/>
			<%--<tcmis:fontSizeCss />--%>
		<link rel="stylesheet" type="text/css" href="/css/tabs.css" />
		<link rel="stylesheet" type="text/css" href="/css/msdsindexing.css" />

		<script type="text/javascript" src="/js/common/formchek.js"></script>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
		<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
		<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />
		<!-- This handles all the resizing of the page and frames -->
		<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
		<!-- This handels which key press events are disabeled on this page -->
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>

			<%-- This handles the menu style and what happens to the right click on the whole page --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

		<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

		<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
		<script type="text/javascript" src="/js/catalog/msdsindexing.js"></script>

		<!-- Include this so I can submit grid thru form -->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<title><fmt:message key="msdsMaintenance"/></title>


		<script language="JavaScript" type="text/javascript">
			<!--
			var dhxWins = null;
			var transitWin = null;
			var children = new Array();
			var showErrorMessage = false;
			var messagesData = new Array();
			messagesData={
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				clear:"<fmt:message key="label.clear"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
				saving:"<fmt:message key="label.saving"/>",
				approving:"<fmt:message key="label.approving"/>",
				rejecting:"<fmt:message key="label.rejecting"/>",
				waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
				newRevision:"<fmt:message key="label.new"/>",
				author:"<fmt:message key="label.author"/>",
				notRequired:"<fmt:message key="label.na"/>",
				mfgRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.manufacturerid"/></fmt:param></fmt:message>",
				materialRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.materialid"/></fmt:param></fmt:message>",
				msdsRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.msds"/></fmt:param></fmt:message>",
				duplicate:"<fmt:message key="error.duplicatevalue"/>",
				loading:"<fmt:message key="label.loadingpleasewait"/>",
				cas:"<fmt:message key="label.casnumbersearch"/>",
				material:"<fmt:message key="label.materialsearch"/>",
				mfg:"<fmt:message key="label.manufacturersearch"/>",
				positiveError:"<fmt:message key="label.positivenumber"/>",
				floatError:"<fmt:message key="errors.float"/>",
				rangeError:"<fmt:message key="errors.range"/>",
				integerError:"<fmt:message key="errors.integer"/>",
				percent:"<fmt:message key="label.percent"/>",
				percentUpper:"<fmt:message key="label.upperpercent"/>",
				percentLower:"<fmt:message key="label.lowerpercent"/>",
				invalidcasnumber:"<fmt:message key="label.invalidcasnumber"/>",
				component:"<fmt:message key="label.component"/>",
				composition:"<fmt:message key="label.composition"/>",
				nfpaHealth:"<fmt:message key="label.nfpahealth"/>",
				nfpaFlammability:"<fmt:message key="label.nfpaflammability"/>",
				nfpaReactivity:"<fmt:message key="label.nfpareactivity"/>",
				hmisHealth:"<fmt:message key="label.hmishealth"/>",
				hmisFlammability:"<fmt:message key="label.hmisflammability"/>",
				hmisReactivity:"<fmt:message key="label.hmisreactivity"/>",
				specificGravityBasis:"<fmt:message key="label.specificgravitybasis"/>",
				specificGravity:"<fmt:message key="label.specificgravity"/>",
				density:"<fmt:message key="label.density"/>",
				flashPoint:"<fmt:message key="label.flashpoint"/>",
				voc:"<fmt:message key="label.voc"/>",
				vocLower:"<fmt:message key="label.voclower"/>",
				vocUpper:"<fmt:message key="label.vocupper"/>",
				vocLessH2oExempt:"<fmt:message key="label.voclesswaterandexempt"/>",
				vocLessH2oExemptLower:"<fmt:message key="label.voclesswaterandexemptlower"/>",
				vocLessH2oExemptUpper:"<fmt:message key="label.voclesswaterandexemptupper"/>",
				solids:"<fmt:message key="label.solids"/>",
				solidsLower:"<fmt:message key="label.solidslower"/>",
				solidsUpper:"<fmt:message key="label.solidsupper"/>",
				vaporPressure:"<fmt:message key="label.vaporpressure"/>",
				vaporPressureLower:"<fmt:message key="label.vaporpressurelower"/>",
				vaporPressureUpper:"<fmt:message key="label.vaporpressureupper"/>",
				vaporPressureTemp:"<fmt:message key="label.vaporpressuretemp"/>",
				vaporDensity:"<fmt:message key="label.vapordensity"/>",
				ph:"<fmt:message key="label.ph"/>",
				boilingPoint:"<fmt:message key="label.boilingpoint"/>",
				errorRevDate:"<fmt:message key="error.msdsnorevisiondate"/>",
				itemInteger:"<fmt:message key="error.item.integer"/>",
				phRange:"<fmt:message key="errors.range"/>",
				range:"<fmt:message key="label.wrongrange"/>",
				compare:"<fmt:message key="label.compare"/>",
				nounit:"<fmt:message key="label.selectunit"/>",
				pleaseselect:"<fmt:message key="errors.selecta"/>",
				pleaseselectfor:"<fmt:message key="errors.selectafor"/>",
				physicalState:"<fmt:message key="label.physicalstate"/>",
				detect:"<fmt:message key="label.detect"/>",
				comprow:"<fmt:message key="errors.comprow"/>",
				changes:"<fmt:message key="label.anychangeslost"/>",
				revDateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.revisiondate"/></fmt:param></fmt:message>",
				viewFile:"<fmt:message key="label.viewfile"/>",
				localizedTradeNameRequired:"<fmt:message key="errors.required"><fmt:param>Foreign Language Trade Name</fmt:param></fmt:message>",
				tradeNameRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.tradename"/></fmt:param></fmt:message>",
				localizedMaterialDescRequired:"<fmt:message key="errors.required"><fmt:param>Foreign Language Material Desc</fmt:param></fmt:message>",
				mfrDescRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.manufacturerdescription"/></fmt:param></fmt:message>",
				materialDescRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.materialdesc"/></fmt:param></fmt:message>",
				newrevdate:"<fmt:message key="label.newrevdate"/>",
				maxinputlength:"<fmt:message key="label.maxinputlength"/>",
				detail:"<fmt:message key="label.detail"/>",
				remark:"<fmt:message key="label.comments"/>",
				altDatasource:"Alt. Data Source",
				customerOverride:"<fmt:message key="label.customeroverridemsdsqc"/>",
				uploadmsdsfile:"<fmt:message key="error.uploadmsdsfile"/>",
				evaporationRate:"<fmt:message key="label.evaporationrate"/>",
				hazardStatement:"<fmt:message key="label.hazardstatement"/>",
				precautionaryStatement:"<fmt:message key="label.precautionarystatement"/>",
				signalWord:"<fmt:message key="label.signalWord"/>",
				stateAbbrev:"<fmt:message key="label.state"/>",
				selectOne:"<fmt:message key="label.pleaseselect"/>",
				company:"<fmt:message key="label.companyname"/>",
				addressLine1:"<fmt:message key="label.address"/>"+" "+"<fmt:message key="label.line1"/>",
				city:"<fmt:message key="label.city"/>",
				zip:"<fmt:message key="label.zip"/>",
				emergencyNumber:"<fmt:message key="label.emergencynumber"/>",
				asMixed:"<fmt:message key="label.vocasmixed"/>",
				required:"<fmt:message key="errors.required"/>",
				maxcomestimatepicalert:"<fmt:message key="label.maxcomestimatepicalert"/>",
				maxcomclearpicalert:"<fmt:message key="label.maxcomclearpicalert"/>",
				maxcomestimatestmtalert:"<fmt:message key="label.maxcomestimatestmtalert"/>",
				maxcomclearstmtalert:"<fmt:message key="label.maxcomclearstmtalert"/>",
				pleaseselectpictogram:"<fmt:message key="error.pleaseselectpictogram"/>",
				atleastonerequired:"<fmt:message key="label.atleastonerequired"/>",
				pleaseentervaluefor:"<fmt:message key="label.pleaseentervalue"/>"+" "+" <fmt:message key="label.fornext"/>",
				wanttocontinue:"<fmt:message key="label.wanttocontinue"/>",
				idOnlyEqualN:"<fmt:message key="label.idonlyequaln"/>",
				invalidIdOnly:"<fmt:message key="label.invalididonly"/>",
				openalltabs:"Unable to validate tab {0}. Please open all tabs, then try to Approve again.",
				rejectioncomments:"Rejection Comments",
				msdsauthor:"<fmt:message key="label.msdsauthorid"/>",
				languagemismatch:"The language of the document for this revision does not match the requested language. Please upload a new document."
			};

			-->
		</script>
		<c:set var="module"><tcmis:module/></c:set>
		<c:set var="masterData" value="${not empty chemRequest}"/>
		<c:set var="vendorTask" value="${not empty catalogQueueRow}"/>
		<c:set var="maintenance" value="${not vendorTask && not masterData}"/>
		<c:set var="isWescoModule" value="${module eq 'haas' or module eq 'catalog'}"/>
		<c:set var="catalogAddMSDSPermission" value="N"/>
		<tcmis:catalogPermission indicator="true" userGroupId="catalogAddMSDS">
			<c:set var="catalogAddMSDSPermission" value="Y"/>
		</tcmis:catalogPermission>
	</head>
	<!-- The 2 if-statements cover all cases, so put them into a choose statement -->
	<c:choose>
		<c:when test="${msdsApproved || queueAdvanced}">
			<body bgcolor="#ffffff" onload="msdsIndex.loadParent();msdsIndex.resizeMainPage();" >
			<input name="qId" id="qId" type="hidden" value="<tcmis:jsReplace value="${param.qId}"/>"/>
			</body>
		</c:when>
		<c:otherwise>
			<body onload="msdsIndex.loadit();" onresize="resizeFrames(0);msdsIndex.resizeMainPage();">
			<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"><html:errors/>${tcmisError}</div>
			<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
				<table width="100%" border="0" cellpadding="2" cellspacing="1">
					<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td align="center" id="transitLabel"></td></tr>
					<tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"/></td></tr>
				</table>
			</div>
			<script type="text/javascript">
				msdsIndex.showErrorMessage = <c:out value="${empty tcmisError?'false':'true'}"/>;
			</script>
			<tcmis:form action="/msdsindexingmain.do" onsubmit="return submitSearchOnlyOnce();">
				<c:if test="${not empty tabs && masterData && not vendorTask}">
					<script type="text/javascript">msdsIndex.totalComponents = ${tabs};</script>
					<%@ include file="/client/catalog/msdsindexingrequestdata.jsp" %>

					<%@ include file="/client/catalog/msdsindexingkit.jsp" %>
					<p style="clear:both"></p>
					<div id="itemTabs" class="haasTabs" style="overflow-x:auto;white-space:nowrap">
						<ul>
							<c:forEach var="tab" begin="1" end="${tabs}">
								<li>
									<a id="itemLink${tab-1}" class="<c:choose><c:when test="${tab == 1}">selectedTab</c:when><c:otherwise>tabLeftSide</c:otherwise></c:choose>" style="display:none" onclick="msdsIndex.toggleItem('${tab-1}');" href="#">
										<span class="<c:choose><c:when test="${tab == 1}">selectedSpanTab</c:when><c:otherwise>tabRightSide</c:otherwise></c:choose>" id="itemLinkSpan${tab-1}">
											<img class="iconImage" src="/images/spacer14.gif"/>
											<fmt:message key="label.component"/> ${tab}
										</span>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<%@ include file="/client/catalog/msdsindexingcomponent.jsp" %>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="uAction" id="uAction" type="hidden" value='<tcmis:jsReplace value="${param.uAction}"/>' />
					<!-- Remove if-statement since it doesn't make sense. If vendorTask is true, user passes over qId and not requestId and lineItem. -->
					<input name="requestId" id="requestId" type="hidden" value='<tcmis:jsReplace value="${param.requestId}"/>' />
					<input name="lineItem" id="lineItem" type="hidden" value='<tcmis:jsReplace value="${param.lineItem}"/>' />
					<input name="approvalRole" id="approvalRole" type="hidden" value='<tcmis:jsReplace value="${param.approvalRole}"/>' />
					<input name="beforeOrAfterSdsQc" id="beforeOrAfterSdsQc" type="hidden" value="${beforeOrAfterSdsQc}"/>
					<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
					<input name="maxData" id="maxData" type="hidden" value="4000"/>
					<input type="hidden" name="allowMixture" id="allowMixture" value="${allowMixture}"/>
					<input type="hidden" name="approve" id="approve" />
					<input type="hidden" name="submitForQc" id="submitForQc" />
					<input name="hasOutOfScopeFeature" id="hasOutOfScopeFeature" type="hidden" value="${hasOutOfScopeFeature}"/>
					<input type="hidden" name="requestStatus" id="requestStatus" value="${empty chemRequest?'':chemRequest.requestStatus}"/>
					<input type="hidden" name="catalogQueueRowStatus" id="catalogQueueRowStatus" value="${empty catalogQueueRow?'':catalogQueueRow.status}"/>
					<input type="hidden" name="catalogQueueRowTask" id="catalogQueueRowTask" value="${empty catalogQueueRow?'':catalogQueueRow.task}"/>
					<input type="hidden" name="catalogAddRequestId" id="catalogAddRequestId" value="${empty catalogQueueRow?'':catalogQueueRow.catalogAddRequestId}"/>
					<input name="catalogAddItemId" id="catalogAddItemId" type="hidden" value='<tcmis:jsReplace value="${param.catalogAddItemId}"/>' />
					<!-- remove chemRequestRequestId var since it is duplicate. If chemRequest is not empty, user has passed requestId as a parameter -->
					<input name="qId" id="qId" type="hidden" value="<tcmis:jsReplace value="${param.qId}"/>"/>
					<input name="qAssignee" id="qAssignee" type="hidden" value="${empty catalogQueueRow?'':catalogQueueRow.assignedTo}"/>
					<input name="currentUser" id="currentUser" type="hidden" value="${personnelBean.personnelId}"/>
					<input name="maintenance" id="maintenance" type="hidden" value="${maintenance?'Y':'N'}"/>
					<input name="masterData" id="masterData" type="hidden" value="${masterData?'Y':'N'}"/>
					<input name="vendorTask" id="vendorTask" type="hidden" value="${vendorTask?'Y':'N'}"/>
					<input type="hidden" name="todaysDate" id="todaysDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="qLocaleCode" id="qLocaleCode" value="${empty catalogQueueRow?'':catalogQueueRow.localeCode}"/>
					<input type="hidden" name="supplierIsWesco" id="supplierIsWesco" value="${not empty catalogQueueRow&&catalogQueueRow.supplierWesco?'Y':'N'}"/>
					<input type="hidden" name="catalogAddRequestLineItem" id="catalogAddRequestLineItem" value="${empty catalogQueueRow?'':catalogQueueRow.lineItem}"/>
					<input type="hidden" name="isWescoModule" id="isWescoModule" value="${isWescoModule?'Y':'N'}"/>
					<input type="hidden" name="expressApproval" id="expressApproval" value=""/>
					<input type="hidden" name="hasAltSupplier" id="hasAltSupplier" value="${not empty catalogQueueRow && not empty catalogQueueRow.altSupplier && catalogQueueRow.altSupplier ne catalogQueueRow.supplier?'Y':'N'}"/>
					<input type="hidden" name="catalogAddMSDSPermission" id="catalogAddMSDSPermission" value="${catalogAddMSDSPermission}"/>
					<input type="hidden" name="companyId" id="companyId" value="${empty catalogQueueRow?'':catalogQueueRow.companyId}"/>
				</div>
				<!-- Hidden elements end -->
			</tcmis:form>
			<!-- close of mainpage -->
			</body>
		</c:otherwise>
	</c:choose>
</html:html>