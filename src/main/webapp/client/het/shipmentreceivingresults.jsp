
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
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
 hlink- this is for any links you want tp provide in the grid, the URL/function to call
 can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%> 
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/client/het/shipmentreceivingresults.js"></script>
<title>
 <fmt:message key="shipmentReceiving"/>
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
pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
suredeletetherecord:"<fmt:message key="label.suredeletetherecord"/>",
containerid:"<fmt:message key="label.containerid"/>",
inputcontaineridforsamekit:"<fmt:message key="label.inputcontaineridforsamekit"/>",
duplicatecontainerid:"<fmt:message key="errors.duplicatecontainerid"/>",
defaultvalusforall:"<fmt:message key="error.defaultvalusforall"/>",
invalidContainer:"<fmt:message key="errors.alreadyexists"/>",
updateSuccess:"<fmt:message key="msg.savesuccess"/>",
returnWG:"<fmt:message key="label.return"/>"
};

// Allow different permissions for different columns
var multiplePermissions = true;

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
	permissionColumns = {
        "solvent" : true,
        "diluent" : true,
        "defaultPartType" : true,
        "defaultPermitId" : true,
        "defaultApplicationMethodCod" : true,
        "defaultSubstrateCode" : true
	};


var defaultPartType = new Array();
var defaultPermitId = new Array();
var defaultApplicationMethodCod = new Array();
var defaultSubstrateCode = new Array();
	
var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,3,4,5,11,12,13,14,15,16,17,18,19,20];	

var config = [
{ columnId:"permission" },
 { columnId:"shipmentId", columnName:'<fmt:message key="label.shipmentid"/>', width:6 },
 { columnId:"applicationDesc", columnName:'<fmt:message key="label.workarea"/>', tooltip:"Y", width:12 },
 { columnId:"applicationId"},
 { columnId:"facpartNo", columnName:'<fmt:message key="label.partnumber"/>', width:8 },
 { columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:5 },
 { columnId:"receiptId", columnName:'<fmt:message key="label.containerid"/>', attachHeader:'<fmt:message key="label.prefix"/>', align:'right', width:6 },
 { columnId:"containerId", columnName:'#cspan', attachHeader:'<fmt:message key="label.suffix"/>', type:'hed', sorting:'int', onChange:'validateLine', align:'left', width:5, size:3, maxlength:25},
 { columnId:"customerMsdsNo", columnName:'<fmt:message key="label.msds"/>', width:8},
 { columnId:"materialDesc",columnName:'<fmt:message key="label.materialdesc"/>',tooltip:"Y",width:30},
 { columnId:"containerSize",columnName:'<fmt:message key="label.containersize"/>', tooltip:"Y", width:16},
{ columnId:"defaultPartTypePermission"},
{ columnId:"defaultPermitIdPermission"},
{ columnId:"defaultApplicationMethodCodPermission"},
{ columnId:"defaultSubstrateCodePermission"},
{ columnId:"defaultPartType", columnName:'<fmt:message key="label.parttype"/>', type:"hcoro", align:"center", width:10, onChange:toggleSubstrate},
{ columnId:"defaultPermitId", columnName:'<fmt:message key="label.permit"/>', type:"hcoro", align:"center", width:12},
{ columnId:"defaultApplicationMethodCod", columnName:'<fmt:message key="label.applicationmethod"/>', type:"hcoro", align:"center", width:10},
{ columnId:"defaultSubstrateCode", columnName:'<fmt:message key="label.substrate"/>', type:"hcoro", align:"center", width:10},
{ columnId:"hetUsageRecording"},
{ columnId:"reportingEntityId"},
{ columnId:"customerMsdsOrMixtureNo"},
{ columnId:"customerMsdsDb"},
{ columnId:"materialId"},
{ columnId:"partId"},
{ columnId:"mfgLot"},
{ columnId:"expireDate"},
{ columnId:"catalogCompanyId"},
{ columnId:"catalogId"},
{ columnId:"kitCount"},
{ columnId:"usageLoggingReq", type:'hchstatus'},
{ columnId:"containerType"},
{ columnId:"solventPermission"},
{ columnId:"solvent", columnName:'<fmt:message key="label.solvent"/>', type:'hchstatus', align:'center', width:4},
{ columnId:"diluentPermission"},
{ columnId:"diluent", columnName:'<fmt:message key="label.diluent"/>', type:'hchstatus', align:'center', width:4},
{ columnId:"buildingId"},
{ columnId:"buildingName"}
];

var gridConfig = {
	       divName:'fxIncomingShipDetailDataBean', // the div id to contain the grid. ALSO the var name for the submitted data
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'mygrid', // the grid's name, for later reference/usage
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
	       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	       onAfterHaasRenderRow: initRow,
		   variableHeight:false
};

var updateSuccess = ${updateSuccess};


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);resultOnLoad()">
<tcmis:form action="/shipmentreceivingresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<!-- If the collection is empty say no data found -->
<c:if test="${empty fxIncomingShipDetailDataColl}">
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
  <tr>
   <td width="100%">
    <fmt:message key="main.nodatafound"/>
   </td>
  </tr>
 </table>
</c:if>

<div id="fxIncomingShipDetailDataBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="rowCount" value='${0}'/>
<c:if test="${!empty fxIncomingShipDetailDataColl}" >
	<c:forEach var="item" items="${fxIncomingShipDetailDataColl}" varStatus="itemStatus">
		<c:forEach var="kitId" items="${item}" varStatus="kitIdStatus">
			<c:choose>
				<c:when test="${!kitId.usageLoggingReq}">
					defaultPartType[${rowCount+1}] = [
							{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
							{value:'F', text: '<fmt:message key="label.aerospace"/>'},
							{value:'R', text: '<fmt:message key="label.aircraftrework"/>'},
							{value:'N', text: '<fmt:message key="label.nonaerospace"/>'}
						];
					defaultPermitId[${rowCount+1}] = [
							{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
							<c:forEach var="permit" items="${kitId.permits}" varStatus="status1">
							{value:'<tcmis:jsReplace value="${permit.permitId}"/>', text: '<tcmis:jsReplace value="${permit.permitName} - ${permit.description}"/>'},
							</c:forEach>
							{value:'NONE', text: '<fmt:message key="label.none"/>'}
						]; 
						
					defaultApplicationMethodCod[${rowCount+1}] = [
				 			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
							<c:forEach var="method" items="${kitId.applicationMethods}" varStatus="status2">
								<c:choose>
									<c:when test="${kitId.solvent && method.forSolvent}">
										{value:'<tcmis:jsReplace value="${method.methodCode}" />', text: '<tcmis:jsReplace value="${method.method}" />'},
									</c:when>
									<c:when test="${!kitId.solvent}">
										{value:'<tcmis:jsReplace value="${method.methodCode}" />', text: '<tcmis:jsReplace value="${method.method}" />'},
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</c:forEach>
							{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
						];
						
					defaultSubstrateCode[${rowCount+1}] = [
							{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
				 			<c:forEach var="substrate" items="${kitId.substrates}" varStatus="status3">
				 				{value:'${substrate.substrateCode}', text: '<tcmis:jsReplace value="${substrate.substrate}" />'},
				 			</c:forEach>
							{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
				 		];
				</c:when>
				<c:otherwise>
					defaultPartType[${rowCount+1}] = [
   						{value:'', text: ''}
   					];
   					defaultPermitId[${rowCount+1}] = [
   						{value:'', text: ''}
   					]; 
   					defaultApplicationMethodCod[${rowCount+1}] = [
   						{value:'', text: ''}
   					];
   					defaultSubstrateCode[${rowCount+1}] = [
   						{value:'', text: ''}
   			 		];
			 	</c:otherwise>
			 </c:choose>
		 	<c:set var="rowCount" value='${rowCount+1}'/>
		</c:forEach>
	</c:forEach>

<c:set var="dataCount" value='${0}'/>
<c:set var="showUpdateLink" value='Y'/>
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
	<c:forEach var="item" items="${fxIncomingShipDetailDataColl}" varStatus="itemStatus">
		<c:forEach var="kitId" items="${item}" varStatus="kitIdStatus">
			<c:if test="${dataCount > 0}">,</c:if>			
			{ id:${dataCount + 1},
			 data:['Y',
			  '${kitId.shipmentId}',
			  '${kitId.applicationDesc}',
			  '${kitId.applicationId}',
			  '${kitId.facpartNo}',
			  '<tcmis:jsReplace value="${kitId.itemId}"/>', 
			  '${kitId.receiptId}' ,
			  '', 
			  '<tcmis:jsReplace value="${kitId.customerMsdsNo}" processMultiLines="true"/>',
			  '<tcmis:jsReplace value="${kitId.materialDesc}" processMultiLines="true"/>',
			  '${kitId.containerSize}',
			  <c:choose>
				<c:when test="${!kitId.usageLoggingReq}">
					'Y','Y','Y','Y',
					<c:choose><c:when test="${!empty kitId.defaultPartType}">'${kitId.defaultPartType}'</c:when><c:otherwise>'SELECT'</c:otherwise></c:choose>,
					<c:choose><c:when test="${!empty kitId.defaultPermitId}">'${kitId.defaultPermitId}'</c:when><c:otherwise>'SELECT'</c:otherwise></c:choose>,
					<c:choose><c:when test="${!empty kitId.defaultApplicationMethodCod}">'${kitId.defaultApplicationMethodCod}'</c:when><c:otherwise>'SELECT'</c:otherwise></c:choose>,
					<c:choose><c:when test="${!empty kitId.defaultSubstrateCode}">'${kitId.defaultSubstrateCode}'</c:when><c:otherwise>'SELECT'</c:otherwise></c:choose>,
				</c:when>
				<c:otherwise>
					'N','N','N','N',
					'',
					'',
					'',
					'',
				</c:otherwise>
			</c:choose>			
			  '${kitId.hetUsageRecording}',
			  '${kitId.reportingEntityId}',
			  '${kitId.customerMsdsOrMixtureNo}',
			  '${kitId.customerMsdsDb}',
			  '${kitId.materialId}',
			  '${kitId.partId}',
			  '${kitId.mfgLot}',
			  '<fmt:formatDate value="${kitId.expireDate}" pattern="${dateFormatPattern}"/>',
			  '${kitId.catalogCompanyId}',
			  '${kitId.catalogId}',
			  '${kitId.kitCount}',
			  ${kitId.usageLoggingReq},
			  '${kitId.containerType}',
			  'N',
			  ${kitId.solvent},
			  'N',
			  ${kitId.diluent},
			  '${kitId.buildingId}',
			  '<tcmis:jsReplace value="${kitId.buildingName}"/>',
			 ]} 
			 <c:set var="dataCount" value='${dataCount+1}'/> 
		 </c:forEach>
	 </c:forEach>
	]};
	
	
	<%-- determining rowspan --%>
	
	<c:set var="lineCount" value='0' />
	<c:set var="rowSpanCount" value='0' />
	  <c:forEach var="item" items="${fxIncomingShipDetailDataColl}" varStatus="itemSpan">
	   <c:forEach var="kitId" items="${item}" varStatus="kitIdSpan">
	     <c:choose>
	      <c:when test="${kitIdSpan.first}">
	        rowSpanMap[${lineCount}]= ${fn:length(item)};
	        <c:set var="prevkitId" value="${kitId.rowCount}"/>
	        <c:set var="prevLineIndex" value="${lineCount}"/>   
	      </c:when>
	      <c:otherwise>  
	        <c:choose>
	         <c:when test="${prevkitId == kitId.rowCount}">
	           rowSpanMap[${lineCount}]= 0;
	         </c:when>
	         <c:otherwise>
	           rowSpanMap[${lineCount}]= ${fn:length(item)};
	           <c:set var="prevLineIndex" value="${lineCount}"/>
	         </c:otherwise>
	        </c:choose>
	      </c:otherwise>
	     </c:choose>
	     rowSpanClassMap[${lineCount}] = ${rowSpanCount % 2};
	     <c:set var="lineCount" value='${lineCount+1}'/>
	   </c:forEach>
	   <c:set var="rowSpanCount" value='${rowSpanCount +1}' />
	  </c:forEach>
</c:if>
//--> 
</script>

<!-- Search results end -->


<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;"> 
	<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
	<input type="hidden" name="workAreaGroup" id="workAreaGroup" value="${param.workAreaGroup}"/> 		
	<input type="hidden" name="workArea" id="workArea" value="${param.workArea}"/> 		
	<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/> 		
	<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/> 		
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
		
<c:if test="${showUpdateLink == 'Y'}">
 <script type="text/javascript">
  <!--
  showUpdateLinks = true;
  //-->
 </script>
</c:if>				

						
</tcmis:form>
</body>
</html:html>