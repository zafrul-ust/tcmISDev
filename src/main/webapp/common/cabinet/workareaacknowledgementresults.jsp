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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%@ include file="/common/locale.jsp" %>
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<!-- Add any other stylesheets you need for the page here -->
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

    <!-- For Calendar support -->
    <script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
    <script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
    <script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

    <!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/cabinet/workareaacknowledgementresults.js"></script>
		
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title><fmt:message key="label.cabinetcount"/></title>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			updateSuccess:"<fmt:message key="msg.updatesuccess"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			quantity:"<fmt:message key="label.qtyonhand"/>",
			pleaseSelect:"<fmt:message key="label.pleaseselectarowforupdate"/>"
			
		};

        var config = [
		    {columnId:"permission"},    
			{columnId:"useApplication", columnName:'<fmt:message key="label.workarea"/>', width:13},
			{columnId:"cabinetName"},
			{columnId:"bin"},
			{columnId:"catalog", columnName:'<fmt:message key="label.catalog"/>', width:8, align:"center"},
			{columnId:"partNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:"center"},
			{columnId:"okDoUpdate", columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoUpdate\');" name="chkAllOkDoUpdate" id="chkAllOkDoUpdate">', type:'hchstatus', align:'center', width:3 },   
			{columnId:"disbursedQty", columnName:'<fmt:message key="label.disbursedqty"/>', width:5},			
			{columnId:"disbursedDate",columnName:'<fmt:message key="label.disburseddate"/>',type:'hcal', hiddenSortingColumn:'hiddenDisbursedDateTime', sorting:'int', permission: true},
			{columnId:"hiddenDisbursedDateTime", sorting:'int' },
			{columnId:"disbursedBy", columnName:'<fmt:message key="label.disbursedby"/>', width:8},
			{columnId:"acknowledgedDate",columnName:'<fmt:message key="dbuystatus.dateacknowledged"/>',type:'hcal', hiddenSortingColumn:'hiddenAcknowledgedDateTime', sorting:'int', permission: true},
			{columnId:"hiddenAcknowledgedDateTime", sorting:'int' },
			{columnId:"acknowledgedBy", columnName:'<fmt:message key="label.acknowledgedby"/>', width:8},	
            {columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center"},
            {columnId:"packaging", columnName:'<fmt:message key="label.packaging"/>', tooltip:true, width:20},
			{columnId:"description" , columnName:'<fmt:message key="label.description"/>', tooltip:true , width:32 },
            {columnId:"binName" , columnName:'<fmt:message key="label.binname"/>', tooltip:true , width:10 },
            {columnId:"companyId" },
			{columnId:"binId" }, 
			{columnId:"cabinetId" },  
			{columnId:"status" } , 
			{columnId:"countType" } , 
			{columnId:"cabinetRowspan" },
            {columnId:"cabinetStartDate"},
            {columnId:"receiptDamagedQty"},
            {columnId:"receiptExpiredQty"},
            {columnId:"startupCountNeeded"},
            {columnId:"cabinetCountId"}
        ];
			
		<%-- Initialize the RCMenus --%>
		drawMenus();
		
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultBodyOnload()">

	<tcmis:form action="/workareaacknowledgementresults.do" onsubmit="return submitFrameOnlyOnce();">

		<%-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		     The default value of showUpdateLinks is false.--%>

			<script type="text/javascript">
				<!--
				showUpdateLinks = true;
 				//-->
			</script>

		<%-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.
		Similar divs would have to be built to show any other messages in a pop-up.--%>
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmISError}<br/>
			<c:forEach items="${tcmISErrors}" varStatus="status">
				${status.current}<br/>
			</c:forEach>
			
			 <c:if test="${param.maxData == fn:length(cabinetsCollection)}">
			     <fmt:message key="label.maxdata">
			       <fmt:param value="${param.maxData}"/>
			     </fmt:message>
			     &nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
		    </c:if>
		</div>

		<script type="text/javascript">
			<!--
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose>
			    <c:when test="${empty tcmISErrors and empty tcmISError}">
			    	<c:choose>
			     		<c:when test="${param.maxData == fn:length(cabinetsCollection)}">
			        		showErrorMessage = true;
			        		parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
			      		</c:when>
			      		<c:otherwise>
			        		showErrorMessage = false;
			      		</c:otherwise>
			     	</c:choose>
			    </c:when>
			    <c:otherwise>
			    	parent.messagesData.errors = "<fmt:message key="label.errors"/>";
			      	showErrorMessage = true;
			    </c:otherwise>
		    </c:choose>
			<c:choose>
				<c:when test="${updateSuccess == 'Y'}">
					updateSuccess = true;
				</c:when>
				<c:otherwise>
					updateSuccess = false;
				</c:otherwise>
			</c:choose>

			//-->
		</script>

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
			
				<div id="cabinetBinItemBean" style="width:100%;height:500px;" style="display: none;"></div>
				
				<c:if test="${!empty cabinetsCollection}">
					<script type="text/javascript">
						<!--
						<c:set var="tmpPermission" value='N'/>
						<c:set var="lastKey" value=''/>
						var jsonMainData = new Array();
						var jsonMainData = {
						rows:[<c:forEach var="bean" items="${cabinetsCollection}" varStatus="status">

						    <c:set var="tmpPermission" value='N'/>

							<c:if test="${bean.acknowledgedBy == null || bean.acknowledgedBy ==''}">
								<tcmis:applicationPermission indicator="true" userGroupId="DisbursementAcknowledgment" facilityId="${bean.facilityId}" companyId="${bean.companyId}" application="${bean.cabinetId}">
									<c:set var="tmpPermission" value='Y'/>
								</tcmis:applicationPermission>
							 </c:if>
						      							
                            {id:${status.index +1},
							 data:['${tmpPermission}',
								'${bean.applicationDesc}',
								'${bean.cabinetName}',
							 	'<tcmis:jsReplace value="${bean.binName}" processMultiLines="true"/>',
								'<tcmis:jsReplace value="${bean.catalogDesc}" />',
								'<tcmis:jsReplace value="${bean.catPartNo}" />',	
								'',
                               	'${bean.countQuantity}',
                               '<fmt:formatDate value="${bean.dateProcessed}" pattern="${dateTimeFormatPattern}"/>',
                                '${bean.dateProcessed.time}',
                                '${bean.disbursedBy}',
                                '<fmt:formatDate value="${bean.acknowledgedDate}" pattern="${dateTimeFormatPattern}"/>',
                                '${bean.acknowledgedDate.time}',
                                '${bean.acknowledgedBy}',
                                '${bean.itemId}',     
                                '<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true"/>',
								'<tcmis:jsReplace value="${bean.description}" processMultiLines="true"/>',
                                '<tcmis:jsReplace value="${bean.binName}" processMultiLines="true"/>',     
                                '${bean.companyId}',
								'${bean.binId}',
								'${bean.cabinetId}',
								'${bean.updatedStatus}',
								'${bean.countType}',
								'${bean.cabinetRowspan}',
                                '${fmtCabinetStartDate}',
                                '',
                                '',
                                '${bean.startupCountNeeded}',
                                '${bean.cabinetCountId}'
                              ]}<c:if test="${!status.last}">,</c:if>
						  </c:forEach>]};

				    //-->
					</script>
				</c:if>
				
				<%-- If the collection is empty say no data found --%>
				<c:if test="${empty cabinetsCollection}" >			
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound"/>
							</td>
						</tr>
					</table>
				</c:if>
	
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="input"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
                    <!-- Popup Calendar input options -->
                    <input type="hidden" name="blockBefore_countDatetime" id="blockBefore_countDatetime" value=""/>
                    <input type="hidden" name="blockAfter_countDatetime" id="blockAfter_countDatetime" value="<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>"/>
                    <input type="hidden" name="blockBeforeExclude_countDatetime" id="blockBeforeExclude_countDatetime" value=""/>
                    <input type="hidden" name="blockAfterExclude_countDatetime" id="blockAfterExclude_countDatetime" value=""/>
                    <input type="hidden" name="inDefinite_countDatetime" id="inDefinite_countDatetime" value=""/>
                    <input type="hidden" name="blockBeforeExcludeDate" id="blockBeforeExcludeDate" value=""/>                      
                        

                 </div>
			</div> <!-- close of backGroundContent -->
		</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>