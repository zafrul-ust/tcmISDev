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
	<script type="text/javascript" src="/js/common/cabinet/clientcabinetcountresults.js"></script>
		
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
            countDate:"<fmt:message key="label.countdate"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            validQtyOnHandForPartNo:"<fmt:message key="error.validqtyonhandforpartno"/>",
            validCountDateForPartNo:"<fmt:message key="error.validcountdateforpartno"/>",
            countDateLater:"<fmt:message key="error.countdatelaterthanlastcountdate"/>"
		};

        var map = null;
        lineMap = new Array();
        lineMap2 = new Array();
        lineMap3 = new Array();

        var config = [
		    {columnId:"permission"},
		    {columnId:"countQuantityPermission"},
		    {columnId:"kanbanBinScanQtyPermission"},
			{columnId:"useApplication", columnName:'<fmt:message key="label.workarea"/>', width:15},
			{columnId:"cabinetName"},
			{columnId:"bin"},
			{columnId:"catalog", columnName:'<fmt:message key="label.catalog"/>', width:10, align:"center"},
			{columnId:"partNo", columnName:'<fmt:message key="label.partno"/>', width:10, align:"center"},
            {columnId:"countQuantity",
                <c:if test="${!containDisbursementData}">
                    columnName:'<fmt:message key="label.qtyonhand"/>',
                </c:if>
                <c:if test="${containDisbursementData}">
                    columnName:'<fmt:message key="label.disbursedqty"/>',
                </c:if>
                type:'hed', width:5 },
            {columnId:"disbursedUnitOfMeasure",
                <c:if test="${containDisbursementData}">
                    columnName:'<fmt:message key="label.disbursedunit"/>',
                </c:if>
                type:'hcoro', width:6 },
            {columnId:"countDatetimePermission"},
            {columnId:"countDatetime",<c:if test="${containAutomaticRefillData}">columnName:'<fmt:message key="label.countdate"/>',</c:if> type:'hcal', width:7, permission:true },
            {columnId:"priorCountDatetime",<c:if test="${containAutomaticRefillData}">columnName:'<fmt:message key="label.lastcountdate"/>',</c:if> width:7 },
            {columnId:"kanbanBinScanQty", columnName:'<fmt:message key="label.kanbanbinscan"/>', type:'hcoro', width:5 },
			{columnId:"receiptId"},
			{columnId:"receiptQty"},
			{columnId:"receiptQtyDisplay", columnName:'<fmt:message key="label.receiptqty"/>', width:5},
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
            {columnId:"startupCountNeeded"}
        ];

        var disbursedUnitOfMeasure = new Array();
		var disbursedUnitOfMeasureMd = new Array(
            {value:'fluid oz', text: '<fmt:message key="label.fluidoz"/>'},
            {value:'gal', text: '<fmt:message key="label.gal"/>'},
            {value:'pint', text: '<fmt:message key="label.pint"/>'},
            {value:'quart', text: '<fmt:message key="label.quart"/>'}
        );
        var disbursedUnitOfMeasureMa = new Array(
            {value:'each', text: '<fmt:message key="label.each"/>'}
        );

		var kanbanBinScanQty = new  Array(
			{text:'',value:''},
	        {text:'0',value:'0'},
	        {text:'1',value:'1'},
	        {text:'2',value:'2'});
		
		<%-- Define the right click menus --%>
		with(milonic=new menuname("receiptCountMenu")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.receiptcount"/>;url=javascript:openReceiptCount();");
		}

        with(milonic=new menuname("levelCountMenu")){
            top="offset=2"
            style = contextStyle;
            margin=3
            aI("text=<fmt:message key="label.levelcount"/>;url=javascript:updateLevelCount();");
        }
        
		<%-- Initialize the RCMenus --%>
		drawMenus();
		
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultBodyOnload()">

	<tcmis:form action="/clientcabinetcountresults.do" onsubmit="return submitFrameOnlyOnce();">

		<%-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		     The default value of showUpdateLinks is false.--%>
		<c:set var="userHasPermission" value='N'/>
		<tcmis:applicationPermission indicator="true" userGroupId="Scan" companyId="${param.companyId}" facilityId="${param.facilityId}">
			<c:set var="userHasPermission" value='Y'/>
			<script type="text/javascript">
				<!--
				showUpdateLinks = true;
 				//-->
			</script>
		</tcmis:applicationPermission>

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
							<c:if test="${userHasPermission == 'Y'}">
							   <c:set var="currentKey" value="${bean.companyId}${bean.facilityId}${bean.application}"/>
							 	<c:if test="${lastKey != currentKey}">
							      <c:set var="tmpPermission" value='N'/>
									<tcmis:applicationPermission indicator="true" userGroupId="Scan" facilityId="${bean.facilityId}" companyId="${bean.companyId}" application="${bean.application}">
										<c:set var="tmpPermission" value='Y'/>
									</tcmis:applicationPermission>
								</c:if>
							   <c:set var="lastKey" value="${currentKey}"/>
							</c:if>
							
							<c:set var="countQuantityPermission" value="N"/>
							<c:if test="${tmpPermission == 'Y' && (!bean.kanBanCount && !bean.countByReceipt && !bean.countByLevel && !bean.advancedReceipt)}">
								<c:set var="countQuantityPermission" value="Y"/>  
							</c:if>

							<c:set var="kanbanBinPermission" value="N"/>
							<c:if test="${tmpPermission == 'Y' && bean.kanBanCount}">
								<c:set var="kanbanBinPermission" value="Y"/>  
							</c:if>

                            <c:set var="countDatetimePermission" value="N"/>
							<c:if test="${tmpPermission == 'Y' && bean.automaticRefill}">
								<c:set var="countDatetimePermission" value="Y"/>
							</c:if>
                            <fmt:formatDate var="fmtPriorCountDatetime" value="${bean.priorCountDatetime}" pattern="${dateFormatPattern}"/>
                            <c:if test="${'AutomaticRefill' == bean.countType && 'Y' == bean.startupCountNeeded}">
								<c:set var="fmtPriorCountDatetime" value=""/>
							</c:if>
                            <fmt:formatDate var="fmtCabinetStartDate" value="${bean.cabinetStartDate}" pattern="${dateFormatPattern}"/>

                            {id:${status.index +1},
							 data:['${tmpPermission}',
							 	'${countQuantityPermission}',
							 	'${kanbanBinPermission}',
								'${bean.applicationDesc}',
								'${bean.cabinetName}',
							 	'<tcmis:jsReplace value="${bean.binName}" processMultiLines="true"/>',
								'<tcmis:jsReplace value="${bean.catalogDesc}" />',
								'<tcmis:jsReplace value="${bean.catPartNo}" />',
                                <c:choose>
                                    <c:when test="${bean.updatedStatus}">'&nbsp;',</c:when>
                                    <c:otherwise>'${bean.quantity}'</c:otherwise>
                                 </c:choose>,
                                '',
                                '${countDatetimePermission}',
                                '',
                                '${fmtPriorCountDatetime}', 
                                '',
                                '',
								'',
                                '',
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
                                '${bean.startupCountNeeded}'     
                              ]}<c:if test="${!status.last}">,</c:if>
						  </c:forEach>]};

                         <%-- start of disbursed unit of measure collection--%>
                         <c:if test="${containDisbursementData}">
                             <c:forEach var="bean" items="${cabinetsCollection}" varStatus="status">
                                <c:choose>
                                    <c:when test="${bean.itemType == 'MD'}" >
                                         disbursedUnitOfMeasure[${status.count}] = disbursedUnitOfMeasureMd;
                                    </c:when>
                                    <c:otherwise>
                                        disbursedUnitOfMeasure[${status.count}] = disbursedUnitOfMeasureMa;
                                    </c:otherwise>
                                </c:choose>
                             </c:forEach>
                         </c:if>
                         <c:if test="${!containDisbursementData}">
                            disbursedUnitOfMeasure = disbursedUnitOfMeasureMa;
                         </c:if>
                         <%-- end of disbursed unit of measure collection --%>

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

<%-- rowspan --%>
<c:set var="preFirstLevel" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="tmpBean" items="${cabinetsCollection}" varStatus="status">
	<c:set var="curFirstLevel">${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}</c:set>
    <c:set var="curSecondLevel">secondLevel${status.current.cabinetId}${status.current.binId}${status.current.catPartNo}${status.current.itemId}</c:set>

    <script language="JavaScript" type="text/javascript">
    <!--
        <c:if test="${!(curFirstLevel eq preFirstLevel)}">
            <c:set var="parCount" value="${parCount+1}"/>
            <c:set var="preSecondLevel" value=""/>
            lineMap[${status.index}] = ${rowCountFirstLevel[curFirstLevel]} ;
            map = new Array();
        </c:if>
        <c:if test="${ !( curSecondLevel eq preSecondLevel)}">
            <c:set var="componentSize" value='${rowCountSecondLevel[curFirstLevel][curSecondLevel]}' />
            lineMap2[${status.index}] = ${componentSize} ;
        </c:if>
        lineMap3[${status.index}] = ${parCount}%2 ;
        map[map.length] =  ${status.index} ;
    // -->
    </script>

    <c:set var="preFirstLevel" value="${curFirstLevel}"/>
    <c:set var="preSecondLevel" value="${curSecondLevel}"/>

</c:forEach>


</body>
</html:html>