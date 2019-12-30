
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

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
<script src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/client/het/hetcontainerentryresults.js"></script>
<title>
    <fmt:message key="containerentry"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
returnWG:"<fmt:message key="label.return"/>",
containerIdFormat:"<fmt:message key="errors.containeridformat"/>",
uniqueContainerId:"<fmt:message key="errors.unique"/>",
containerId:"<fmt:message key="label.containerid"/>",
containerValid:"<fmt:message key="errors.nosuchcontainerforitem"/>",
quantityVal:"<fmt:message key="label.xxpositiveinteger"/>",
quantity:"<fmt:message key="label.quantity"/>",
waitFor:"<fmt:message key="printnonhaaspurchased"/>",
nom:"<fmt:message key="label.numberofcontainers"/>",
enterValues:"<fmt:message key="label.pleaseadd"/>",
quantityValRow:"<fmt:message key="label.positivenumber"/>",
selectForEach:"<fmt:message key="errors.selectforeach"/>",
kit:"<fmt:message key="label.kit"/>",
numberOfContainers:"<fmt:message key="label.numberofcontainers"/>",
containertype:"<fmt:message key="label.containertype"/>",
notValidValues:"<fmt:message key="label.validvalue"/>",
alreadyExists:"<fmt:message key="errors.alreadyexists"/>",
partnumber:"<fmt:message key="label.partnumber"/>",
permitRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.permit"/></fmt:param></fmt:message>",
substrateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.substrate"/></fmt:param></fmt:message>",
methodRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.applicationmethod"/></fmt:param></fmt:message>"
};

var numberOfContainers = '<fmt:message key="label.numberofcontainers"/>';
var dupRow = '<fmt:message key="label.duprow"/>';
var itemId = '<fmt:message key="label.item"/>';
var customerMsdsNo = '<fmt:message key="label.customermsdsno"/>';
var container = '<fmt:message key="label.container"/>';
var containerId = '<fmt:message key="label.containerid"/>';
var overrideUsageLogging = ${overrideUsageLogging};

<c:set var="containerIdVar" value=''/>
<c:set var="varCol" value=''/>
var updateSuccess = '${updateSuccess}';

<c:if test="${param.nonHaasPurchased != 'Y'}" >

</c:if>

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,3];	
var config = [
{columnId:"permission"},
{ columnId:'', columnName:'', align:'center', width:7 },
{ columnId:"catPartNo", columnName:'<fmt:message key="label.partnumber"/>', align:'center', width:7 <c:if test="${param.nonHaasPurchased == 'Y'}">, type:'hed'</c:if>},
{ columnId:'', columnName:'', align:'center', width:7 },
{ columnId:'containerId', columnName:'', attachHeader:'', align:'center', width:7, size:10, maxlength:17, type:'', onChange:'' },
{ columnId:"amountRemaining", attachHeader:'<fmt:message key="label.quantity"/>', columnName:'', type:'', size:'', align:'', width:''<c:if test="${param.nonHaasPurchased == 'Y'}">, onChange: determineOTR</c:if>},
{ columnId:"unitOfMeasure", attachHeader:'<fmt:message key="label.unitofmeasure"/>', columnName:'', type:'', size:'', align:'', width:''<c:if test="${param.nonHaasPurchased == 'Y'}">, onChange: determineOTR</c:if> },
<c:if test="${param.nonHaasPurchased == 'Y'}">
	{columnId:"expirationDate", type:'hcal', attachHeader:'<fmt:message key="label.expirationdate"/>', columnName:'#cspan', align:'center'},
</c:if>
{ columnId:"componentMsdsNo", columnName:'<fmt:message key="label.msds"/>', align:'center', width:7 },
{ columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', tooltip:"Y", width:30 },
<c:if test="${param.nonHaasPurchased != 'Y'}">
	{ columnId:"containerSize", columnName:'<fmt:message key="label.containersize"/>', tooltip:"Y", width:30 },
	{ columnId:"containerType"},
</c:if>
<c:if test="${param.nonHaasPurchased == 'Y'}">
	{ columnId:"containerSize"},
	{ columnId:"containerType", columnName:'<fmt:message key="label.containertype"/>', type:"hcoro", align:"center", width:10},
</c:if>
{ columnId:"kitStart"},
{ columnId:"materialId"},
{ columnId:"applicationId"},
{ columnId:"partId"},
{ columnId:"validContainerId"},
{ columnId:"catalogId"},
{ columnId:"catalogCompanyId"},
{ columnId:"customerMsdsDb"},
{ columnId:"hetUsageRecording"},
{ columnId:"solventPermission"},
{ columnId:"solvent", columnName:'<fmt:message key="label.solvent"/>', type:'hchstatus', align:'center',width:4},
{ columnId:"diluentPermission"},
{ columnId:"diluent", columnName:'<fmt:message key="label.diluent"/>', type:'hchstatus', align:'center',width:4},
{ columnId:"defaultApplicationMethodCodPermission"},
{ columnId:"defaultApplicationMethodCod", columnName:'<fmt:message key="label.applicationmethod"/>', type:"hcoro", align:"center", width:10},
{ columnId:"defaultPartTypePermission"},
{ columnId:"defaultPartType", columnName:'<fmt:message key="label.parttype"/>', type:"hcoro", align:"center", width:10, onChange: toggleSubstrate},
{ columnId:"defaultPermitIdPermission"},
{ columnId:"defaultPermitId", columnName:'<fmt:message key="label.permit"/>', type:"hcoro", align:"center", width:10},
{ columnId:"defaultSubstrateCodePermission"},
{ columnId:"defaultSubstrateCode", columnName:'<fmt:message key="label.substrate"/>', type:"hcoro", align:"center", width:10},
{ columnId:"itemId"},
{ columnId:"buildingId"}
];

var defaultPartType = [
	{value:'F', text: '<fmt:message key="label.aerospace"/>'},
	{value:'R', text: '<fmt:message key="label.aircraftrework"/>'},
	{value:'N', text: '<fmt:message key="label.nonaerospace"/>'}
];
var defaultPermitId = [
			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
			<c:forEach var="permit" items="${input.permits}" varStatus="status">
				{value:'${permit.permitId}', text: '<tcmis:jsReplace value="${permit.permitName}" />'},
			</c:forEach>
			{value:'NONE', text: '<fmt:message key="label.none"/>'}
		];
			
var defaultApplicationMethodCod = [
 			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
			<c:forEach var="method" items="${input.applicationMethods}" varStatus="status">
				{value:'${method.methodCode}', text: '<tcmis:jsReplace value="${method.method}" />'},
			</c:forEach>
			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
		];
	
var applicationMethodForSolvent = [
   			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
  			<c:forEach var="method" items="${input.applicationMethods}" varStatus="status">
  				<c:if test="${method.forSolvent}">
  					{value:'${method.methodCode}', text: '${method.methodCode} - <tcmis:jsReplace value="${method.method}"/>'},
  				</c:if>
  			</c:forEach>
  			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
  		];

var defaultSubstrateCode = [
			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
 			<c:forEach var="substrate" items="${input.substrates}" varStatus="status">
 				{value:'${substrate.substrateCode}', text: '<tcmis:jsReplace value="${substrate.substrate}" />'},
 			</c:forEach>
			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
 		];

var unitOfMeasure = new Array(
	<c:forEach var="uom" items="${unitsOfMeasure}" varStatus="status">
		 {value:'${uom.sizeUnit}',text:'<tcmis:jsReplace value="${uom.sizeUnit}" />'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>
);

var containerType = new Array(
	{value:'', text: '<fmt:message key="label.pleaseselect"/>'},
	<c:forEach var="cType" items="${containerType}" varStatus="status">
		 {value:'${cType.unidocsStorageContainer}',text:'<tcmis:jsReplace value="${cType.description}" />'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>
);


var gridConfig = {
	       divName:'hetContainerEntryViewBean', // the div id to contain the grid. ALSO the var name for the submitted data
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'mygrid', // the grid's name, for later reference/usage
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
	       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	       variableHeight:false,
	       onAfterHaasRenderRow: initToggleSubstrate,
	       onRowSelect:selectRow
	       
};


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="myLoad();resultOnLoadWithGrid(gridConfig);nhpUpdate();">
<tcmis:form action="/hetcontainerentryresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="hetContainerEntryViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="showUpdateLink" value='N'/>
<c:if test="${hetContainerEntryColl != null}">
<script type="text/javascript"><!--


var plusButtonRows = new Array();
<c:set var="dataCount" value='${0}'/>
<c:set var="kitStart" value='${0}'/>
<c:set var="curMsds" value=''/>
<c:set var="curItem" value=''/>
<c:if test="${!empty hetContainerEntryColl}" >
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="ordered" items="${hetContainerEntryColl}" varStatus="orderedStatus">
<c:forEach var="inner" items="${ordered}" varStatus="innerStatus">
<c:set var="showUpdateLink" value='Y'/>

<c:set var="sdPermission" value='N'/>
<%-- <c:if test="${param.nonHaasPurchased == 'Y' }"><c:set var="sdPermission" value='Y'/></c:if>--%>

<c:set var="usageLoggingReqPermission" value='N'/>
<c:if test="${param.nonHaasPurchased != 'Y' && !inner.usageLoggingRequired}"><c:set var="usageLoggingReqPermission" value='Y'/></c:if>


<tcmis:jsReplace var="matDes" value="${inner.materialDesc}" processMultiLines="true" /> 

<c:choose>
<c:when test="${param.nonHaasPurchased == 'Y'}">
	<c:set var="contId" value='LMCO-####'/>
</c:when>
<c:otherwise>
	<c:set var="contId" value='${inner.containerId}'/>
</c:otherwise>
</c:choose>

<c:if test="${dataCount > 0}">,</c:if>

/* set page permission here */
/*<c:set var="readonly" value='N'/>
<c:set var="showUpdateLink" value='Y'/>
/*The row ID needs to start with 1 per their design.*/
{ id:${dataCount + 1},
 data:[
       'Y',   
       <c:choose>
       		<c:when test="${param.nonHaasPurchased == 'Y'}">
       			'',
       			'',
       		</c:when>
       		<c:otherwise>
       			'<input type="button" name="dupRow${dataCount}" id="dupRow${dataCount}" onclick="rowDupClicked()" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'"  value="+">',
       			'${inner.catPartNo}',
       		</c:otherwise>
       </c:choose>    	
       <c:choose>
	       	<c:when test="${param.nonHaasPurchased == 'Y'}">
	   			'${inner.custMsdsNo}', 
	     	</c:when>
	     	<c:otherwise>
	     		'${inner.itemId}',
	     	</c:otherwise>
	   </c:choose>   
    	 '${contId}',
    	 '',
    	 '',
    	 <c:if test="${param.nonHaasPurchased == 'Y'}">
    		'',
		 </c:if>
    	 '${inner.componentMsdsNo}',
    	'${matDes}',
    	'${inner.containerSize}',
    	'${inner.containerType}',
       <c:choose>
	   		<c:when test="${param.nonHaasPurchased == 'Y'}">
		        <c:choose>
		        	<c:when test="${curMsds == inner.custMsdsNo}">
		      			'${kitStart}',
		     		 </c:when>
		        	<c:otherwise>
			        	<c:set var="curMsds" value='${inner.custMsdsNo}'/>
			        	<c:set var="kitStart" value='${kitStart + 1}'/>
			        	'${kitStart}',
		      		</c:otherwise>
		       </c:choose>
		 	</c:when>
			<c:otherwise>
		        <c:choose>
	        	<c:when test="${curItem == inner.itemId}">
	      			'${kitStart}',
	     		 </c:when>
	        	<c:otherwise>
		        	<c:set var="curItem" value='${inner.itemId}'/>
		        	<c:set var="kitStart" value='${kitStart + 1}'/>
		        	'${kitStart}',
	      		</c:otherwise>
	       		</c:choose>
	       	</c:otherwise>
     	</c:choose>
       '${inner.materialId}',
       '${param.workArea}',
       <c:choose> 
       		<c:when test="${param.nonHaasPurchased == 'Y'}">
       			'1',
       		</c:when>
       		<c:otherwise>
       			'${inner.partId}',
       		</c:otherwise>
       </c:choose>
       false,
      '${inner.catalogId}',
      '${inner.catalogCompanyId}',
      '${inner.customerMsdsDb}',
      '${inner.hetUsageRecording}',
      '${sdPermission}',
      ${inner.solvent},
      '${sdPermission}',
      ${inner.diluent},
      '${usageLoggingReqPermission}',
      '<c:if test="${usageLoggingReqPermission == 'Y'}">${inner.defaultApplicationMethodCod}</c:if>',
      '${usageLoggingReqPermission}',
  	  '<c:if test="${usageLoggingReqPermission == 'Y'}">${inner.defaultPartType}</c:if>',
      '${usageLoggingReqPermission}',
  	  '<c:if test="${usageLoggingReqPermission == 'Y'}">${inner.defaultPermitId}</c:if>',
      '${usageLoggingReqPermission}',
  	  '<c:if test="${!inner.usageLoggingRequired}">${inner.defaultSubstrateCode}</c:if>',
      '<c:if test="${param.nonHaasPurchased != 'Y'}">${inner.partId}</c:if>',
  	  '${inner.buildingId}',
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
</c:forEach>
]};
var kitStart = '${kitStart}';
<c:set var="colorIndex" value="-1"/>
<%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
<c:set var="lineCount" value='0' />
<c:forEach var="ordered" items="${hetContainerEntryColl}" varStatus="orderedSpan">
	<c:forEach var="inner" items="${ordered}" varStatus="innerSpan">
	   <c:choose>
	  		<c:when test="${param.nonHaasPurchased == 'Y'}">
				<c:choose>
					<c:when test="${innerSpan.first}">
						rowSpanMap[${lineCount}]= ${fn:length(ordered)};
						<c:set var="prevComp" value="${inner.custMsdsNo}"/>
						<c:set var="prevLineIndex" value="${lineCount}"/>
					</c:when>
					<c:otherwise>	
						<c:choose>
							<c:when test="${prevComp == inner.custMsdsNo}">
								rowSpanMap[${lineCount}]= 0;			
							</c:when>
							<c:otherwise>
								rowSpanMap[${lineCount}]= ${fn:length(ordered)};
								<c:set var="prevComp" value="${inner.custMsdsNo}"/>
								<c:set var="prevLineIndex" value="${lineCount}"/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			  </c:when>
			 <c:otherwise>
				<c:choose>
					<c:when test="${innerSpan.first}">
						rowSpanMap[${lineCount}]= ${fn:length(ordered)};
						<c:set var="prevComp" value="${inner.itemId}"/>
						<c:set var="prevLineIndex" value="${lineCount}"/>
					</c:when>
					<c:otherwise>	
						<c:choose>
							<c:when test="${prevComp == inner.itemId}">
								rowSpanMap[${lineCount}]= 0;			
							</c:when>
							<c:otherwise>
								rowSpanMap[${lineCount}]= ${fn:length(ordered)};
								<c:set var="prevComp" value="${inner.itemId}"/>
								<c:set var="prevLineIndex" value="${lineCount}"/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
		     </c:otherwise>
	     </c:choose>
		rowSpanClassMap[${lineCount}]= ${rowSpanCount % 2}; 
		<c:set var="lineCount" value='${lineCount+1}'/>
		</c:forEach>
	<c:set var="rowSpanCount" value='${rowSpanCount +1}' />
	</c:forEach>
	
</c:if>


//   
--></script>

<!-- Search results end -->
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty hetContainerEntryColl}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${updateSuccess == 'Y'}">
	<script type="text/javascript">
	var jsonSendData = new Array();
	var jsonSendData = {
	rows:[<c:forEach var="inserted" items="${insertColl}" varStatus="insertedStatus">
		{ id:${insertedStatus.count},
		 data:[
		       'Y',
		       '<tcmis:jsReplace value="${inserted.containerId}" />',
		       '<tcmis:jsReplace value="${inserted.catPartNo}" />',
		       '<tcmis:jsReplace value="${inserted.tradeName}" />',
		       '<tcmis:jsReplace value="${inserted.materialDesc}" processMultiLines="true" />',
		       '${inserted.custMsdsNo}',
		       '<tcmis:jsReplace value="${inserted.manufacturer}" />',
		       '<fmt:formatDate value="${inserted.expirationDate}" pattern="${dateFormatPattern}"/>',
		       '${inserted.hetUsageRecording}',
		       '<c:choose><c:when test="${!inserted.usageLoggingRequired}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>'
		  ]}<c:if test="${!insertedStatus.last}">,</c:if>
	</c:forEach>]};
	 </script>
</c:if>


<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>


<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
	<input type="hidden" name="nonHaasPurchased" id="nonHaasPurchased" value="${param.nonHaasPurchased}"/>
	<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
	<input type="hidden" name="workAreaGroup" id="workAreaGroup" value="${param.workAreaGroup}"/>
	<input type="hidden" name="workArea" id="workArea" value="${param.workArea}"/>
	<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
	<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
	<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
	<!-- Popup Calendar input options for expirationDate -->
	<input type="hidden" name="blockBefore_expirationDate" id="blockBefore_expirationDate"  value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
	<input type="hidden" name="blockAfter_expirationDate" id="blockAfter_expirationDate" value=""/>
	<input type="hidden" name="blockBeforeExclude_expirationDate" id="blockBeforeExclude_expirationDate" value=""/>
	<input type="hidden" name="blockAfterExclude_expirationDate" id="blockAfterExclude_expirationDate" value=""/>
	<input type="hidden" name="inDefinite_expirationDate" id="inDefinite_expirationDate" value=""/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>