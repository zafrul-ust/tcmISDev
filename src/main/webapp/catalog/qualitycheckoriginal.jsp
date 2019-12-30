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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="expires" content="-1"/>
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

		<!--Use this tag to get the correct CSS class.
        This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss />
		<tcmis:fontSizeCss />
		<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>
		<link rel="stylesheet" type="text/css" href="/css/msdsindexing.css"/>

		<script src="/js/common/formchek.js" type="text/javascript"></script>
		<script src="/js/common/commonutil.js" type="text/javascript"></script>
		<!-- This handles all the resizing of the page and frames -->
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
		<!-- This handels which key press events are disabeled on this page -->
		<script src="/js/common/disableKeys.js" type="text/javascript"></script>

		<%@ include file="/common/locale.jsp" %>

			<%--
        <!-- This handels the menu style and what happens to the right click on the whole page -->
        <script type="text/javascript" src="/js/menu/milonic_src.js"></script>
        <script type="text/javascript" src="/js/menu/mmenudom.js"></script>
        <script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
        <script type="text/javascript" src="/js/menu/contextmenu.js"></script>
        <%@ include file="/common/rightclickmenudata.jsp" %>

        <script src="/js/ordertracking.js" type="text/javascript"></script>

        <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
        <script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
        <script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

        <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
        --%>

		<script lang="JavaScript">
			<!--

			var windowCloseOnEsc = true;
			var selectedItemTab = 1;

			function toggleItem(itemNum) {
				if (itemNum != selectedItemTab) {
					hideItem(selectedItemTab);
					showItem(itemNum);
					selectedItemTab = itemNum;
				}
			}

			function hideItem(itemNum) {
				var itemLink =  $("itemLink" + itemNum);
				var itemLinkSpan =  $("itemLinkSpan" + itemNum);
				var itemDiv =  $("itemDiv" + itemNum);

				itemLink.className = "tabLeftSide";
				itemLinkSpan.className = "tabRightSide";
				itemDiv.style["display"] = "none";

			}

			function showItem(itemNum) {
				var itemLink =  $("itemLink" + itemNum);
				var itemLinkSpan =  $("itemLinkSpan" + itemNum);
				var itemDiv =  $("itemDiv" + itemNum);

				itemLink.className = "selectedTab";
				itemLinkSpan.className = "selectedSpanTab";
				itemDiv.style["display"] = "block";
			}

			var children = new Array();
			function addComponent(requestId) {
				var loc = "catalogaddmsdsrequest.do?uAction=editNewMaterial&requestId="+requestId+"&lineItem=1&partId=1&calledFrom=viewRequestPopup";
				children[children.length] = openWinGeneric(loc,"showNewMaterial","680","280","yes","80","80","no");
			}

			function closeAllchildren()
			{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
				try {
					for(var n=0;n<children.length;n++) {
						try {
							children[n].closeAllchildren();
						} catch(ex){}
						children[n].close();
					}
				} catch(ex){}
				children = new Array();
			}

			function addNewMaterialToList() {
				document.genericForm.submit();
			}

			//-->
		</script>

		<title>
			<fmt:message key="existingcatalogitems.label.title"/>&nbsp;<fmt:message key="label.catalog"/>:&nbsp;<c:out value='${param.catalogId}'/>
			&nbsp;<fmt:message key="label.and"/>&nbsp;<fmt:message key="label.partnumber"/>:&nbsp;<c:out value='${param.catPartNo}'/>
		</title>

		<style>
			.field {
				display:inline-block;
				*display:inline;
				zoom:1;
				padding-bottom:5px;
				margin-bottom:5px;
				border-bottom:1px dotted gray
			}
		</style>
	</HEAD>

	<body BGCOLOR="#FFFFFF" TEXT="#000000" onunload="closeAllchildren();">
	<tcmis:form action="/msdsindexingmain.do" onsubmit="return submitFrameOnlyOnce();">

		<!-- Transit Page Begins -->
		<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
			<br><br><br><fmt:message key="label.pleasewait"/>
			<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
		</div>
		<!-- Transit Page Ends -->


		<div class="interface" id="mainPage" style="">
			<!-- open contentArea -->
			<div class="contentArea">
				<c:if test="${not empty requestHeader}">
					<div class="roundcont contentContainer">
						<div class="roundright">
							<div class="roundtop">
								<div class="roundtopright">
									<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
							<div class="roundContent">
								<c:if test="${param.workItemTask == 'SDS Sourcing' || workItemTask == 'SDS Sourcing'}">
									<div class="boxhead"> <%-- boxhead Begins --%>
										<a href="javascript:addComponent(${requestHeader.requestId})"><fmt:message key="label.addcomponent"/></a>
										<br/>&nbsp;
									</div>
								</c:if>
								<div class="roundContent">
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.requestid"/>:  </label><b>${requestHeader.requestId}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.company"/>:  </label><b>${requestHeader.companyName}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.catalog"/>:  </label><b>${requestHeader.catalogName}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.requestor"/>:  </label><b>${requestHeader.requestorFullName}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.phone"/>:  </label><b>${requestHeader.requestorPhone}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.email"/>:  </label><b>${requestHeader.requestorEmail}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.userpartnumber"/>:  </label><b>${requestHeader.catPartNo}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.evaluation"/>:  </label><b>${requestHeader.engineeringEvaluation}</b>
									</div>
									<div style="width:90%;" class="field">
										<label class="optionTitle"><fmt:message key="label.partdescription"/>:  </label><b>${requestHeader.partDescription}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.supplier"/>:  </label><b>${requestHeader.suggestedVendor}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.contact"/>:  </label><b>${requestHeader.vendorContactName}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.email"/>:  </label><b>${requestHeader.vendorContactEmail}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.phone"/>:  </label><b>${requestHeader.vendorContactPhone}</b>
									</div>
									<div style="width:30%;" class="field">
										<label class="optionTitle"><fmt:message key="label.fax"/>:  </label><b>${requestHeader.vendorContactFax}</b>
									</div>
									<div style="width:90%">
										<label for="comments" class="optionTitle"><fmt:message key="label.additionalcomments"/></label>
										<textarea id="comments" name="comments" class="optionTitleBold msdsReadOnlyField" readonly><c:out value="${requestHeader.messageToApprovers}"/></textarea>
									</div>
								</div>
							</div>
							<div class="roundbottom">
								<div class="roundbottomright">
									<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${empty requestHeader}">
					<c:if test="${param.workItemTask == 'SDS Sourcing' || workItemTask == 'SDS Sourcing'}">
						<div class="boxhead"> <%-- boxhead Begins --%>
							<a href="javascript:addComponent(${param.requestId})"><fmt:message key="label.addcomponent"/></a>
							<br/>&nbsp;
						</div>
					</c:if>
				</c:if>
				<div id="itemTabs" class="haasTabs">
					<ul>
						<c:forEach var="item" items="${resultsCollection}" varStatus="status">
							<li>
								<a id="itemLink${status.count}" class="<c:choose><c:when test="${status.first}">selectedTab</c:when><c:otherwise>tabLeftSide</c:otherwise></c:choose>" href="javascript: toggleItem('${status.count}');">
						<span class="<c:choose><c:when test="${status.first}">selectedSpanTab</c:when><c:otherwise>tabRightSide</c:otherwise></c:choose>" id="itemLinkSpan${status.count}">
							<img class="iconImage" src="/images/spacer14.gif">
							<fmt:message key="label.component"/> ${status.count}
						</span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<c:forEach var="item" items="${resultsCollection}" varStatus="status">
					<div id="itemDiv${status.count}" <c:if test="${!status.first}">style="display:none"</c:if>>
						<div class="roundcont contentContainer">
							<div class="roundright">
								<div class="roundtop">
									<div class="roundtopright">
										<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
									</div>
								</div>
								<div class="roundContent">
									<div class="boxhead"> <%-- boxhead Begins --%>
									</div>

									<div style="width:90%;" class="field">
										<label for="comp[${status.count}].manufacturer" class="optionTitle"><fmt:message key="label.manufacturer"/>:</label><br/>
										<input type="text" id="comp[${status.count}].manufacturer" name="comp[${status.count}].manufacturer" class="optionTitleBold msdsReadOnlyField" readonly value="<c:out value="${item.manufacturer}"/>"></input>
									</div>
									<div style="width:90%;" class="field">
										<label for="comp[${status.count}].materialDesc" class="optionTitle"><fmt:message key="label.materialdesc"/>:</label><br/>
										<input type="text" id="comp[${status.count}].materialDesc" name="comp[${status.count}].materialDesc" class="optionTitleBold msdsReadOnlyField" readonly value="<c:out value="${item.materialDesc}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].grade" class="optionTitle"><fmt:message key="label.grade"/>:</label><br/>
										<input type="text" id="comp[${status.count}].grade" name="comp[${status.count}].grade" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="<c:out value="${item.grade}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].sampleSize" class="optionTitle"><fmt:message key="label.samplesize"/>:</label><br/>
										<input type="text" id="comp[${status.count}].sampleSize" name="comp[${status.count}].sampleSize" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="${item.sampleOnly}"></input>
									</div>
									<div style="width:30%;" class="field">
										<label for="comp[${status.count}].tradeName" class="optionTitle"><fmt:message key="label.tradename"/>:</label><br/>
										<input type="text" id="comp[${status.count}].tradeName" name="comp[${status.count}].tradeName" class="optionTitleBold msdsReadOnlyField" readonly value="<c:out value="${item.mfgTradeName}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].nrComp" class="optionTitle"><fmt:message key="label.nrcomp"/>:</label><br/>
										<input type="text" id="comp[${status.count}].nrComp" name="comp[${status.count}].nrComp" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="${item.componentsPerItem}"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].partSize" class="optionTitle"><fmt:message key="label.partsize"/>:</label><br/>
										<input type="text" id="comp[${status.count}].partSize" name="comp[${status.count}].partSize" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="${item.partSize}"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].sizeUnit" class="optionTitle"><fmt:message key="label.sizeunit"/>:</label><br/>
										<input type="text" id="comp[${status.count}].sizeUnit" name="comp[${status.count}].sizeUnit" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="${item.sizeUnit}"></input>
									</div>
									<div style="width:30%;" class="field">
										<label for="comp[${status.count}].pkgStyle" class="optionTitle"><fmt:message key="label.packagingstyle"/>:</label><br/>
										<input type="text" id="comp[${status.count}].pkgStyle" name="comp[${status.count}].pkgStyle" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="<c:out value="${item.pkgStyle}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].dimension" class="optionTitle"><fmt:message key="label.dimension"/>:</label><br/>
										<input type="text" id="comp[${status.count}].dimension" name="comp[${status.count}].dimension" class="optionTitleBold msdsReadOnlyField" readonly value="<c:out value="${item.dimension}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].netwt" class="optionTitle"><fmt:message key="label.netwt"/>:</label><br/>
										<input type="text" id="comp[${status.count}].netwt" name="comp[${status.count}].netwt" class="optionTitleBold msdsReadOnlyField" size="4" readonly value="${item.netwt}"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].netwtUnit" class="optionTitle"><fmt:message key="label.netwtunit"/>:</label><br/>
										<input type="text" id="comp[${status.count}].netwtUnit" name="comp[${status.count}].netwtUnit" class="optionTitleBold msdsReadOnlyField" readonly value="${item.netwtUnit}"></input>
									</div>
									<div style="width:30%;" class="field">
										<label for="comp[${status.count}].mfgPartNo" class="optionTitle"><fmt:message key="label.mfgpartnr"/>:</label><br/>
										<input type="text" id="comp[${status.count}].mfgPartNo" name="comp[${status.count}].mfgPartNo" class="optionTitleBold msdsReadOnlyField" readonly value="<c:out value="${item.mfgCatalogId}"/>"></input>
									</div>
									<div style="width:20%;" class="field">
										<label for="comp[${status.count}].labelColor" class="optionTitle"><fmt:message key="label.labelcolor"/>:</label><br/>
										<input type="text" id="comp[${status.count}].labelColor" name="comp[${status.count}].labelColor" class="optionTitleBold msdsReadOnlyField" readonly value="${item.labelColor}"></input>
									</div>
								</div>
								<div class="roundbottom">
									<div class="roundbottomright">
										<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
			<!-- close of contentArea -->
		</div>



		<!-- Hidden element start -->
		<div id="hiddenElements" style="display: none;">
			<input name="uAction" id="uAction" value='<tcmis:jsReplace value="${param.uAction}"/>' type="hidden"/>
			<input name="qId" id="qId" value='<tcmis:jsReplace value="${param.qId}"/>' type="hidden"/>
			<input name="workItemStatus" id="workItemStatus" value='<tcmis:jsReplace value="${param.workItemStatus}"/>' type="hidden"/>
			<input name="workItemTask" id="workItemTask" value='<tcmis:jsReplace value="${param.workItemTask}"/>' type="hidden"/>
			<input name="requestId" id="requestId" value='<tcmis:jsReplace value="${param.requestId}"/>' type="hidden"/>
			<input name="lineItem" id="lineItem" value='<tcmis:jsReplace value="${param.lineItem}"/>' type="hidden"/>
			<input name="companyId" id="companyId" value='<tcmis:jsReplace value="${param.companyId}"/>' type="hidden"/>
		</div>
	</tcmis:form>
	</body>
</html:html>
