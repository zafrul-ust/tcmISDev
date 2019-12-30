
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
	<tcmis:gridFontSizeCss />
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<script src="/js/common/disableKeys.js"></script>

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
	<script type="text/javascript" src="/js/client/het/hetcontainerinventoryresults.js"></script>
<title>
    <fmt:message key="containerInventory"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
	suredeletetherecord:"<fmt:message key="label.suredeletetherecord"/>",
	returnWG:"<fmt:message key="label.return"/>",
	indefinite:"<fmt:message key="label.indefinite"/>",
	reasonRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.reason"/></fmt:param></fmt:message>",
	amountRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.amount"/></fmt:param></fmt:message>",
	amountNumber:"<fmt:message key="label.positivenumber"><fmt:param><fmt:message key="label.amount"/></fmt:param></fmt:message>"
};

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,3,4,5];	

var config = [
	{columnId:"permission"},
	{columnId:"okDoUpdate", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', align:'center',width:3, submit: true },
	{ columnId:"applicationId", columnName:'<fmt:message key="label.workarea"/>',type:'hcoro',width:20, submit: true },
	{ columnId:"cartName", columnName:'<fmt:message key="label.cart"/>', width:10, submit: true},
	{ columnId:"catPartNo", columnName:'<fmt:message key="label.partnumber"/>', width:10, align: "center", tooltip: true},
	{ columnId:"itemId",columnName:'<fmt:message key="label.item"/>', width:5, align: "center"},
	{ columnId:"containerId",columnName:'<fmt:message key="label.containerid"/>', width:8, align: "center", submit: true },
	{ columnId:"amountRemainingPermission"},
	{ columnId:"unitOfMeasurePermission"},
	{ columnId:"amountRemaining", columnName:'<fmt:message key="label.quantity"/>', width:5, align: "right", type: 'hed', permission: true, submit: true},
	{ columnId:"unitOfMeasure", columnName:'<fmt:message key="label.unit"/>', width:8, align: "left", type: 'hcoro', permission: true, submit: true},
	{ columnId:"expirationDate",columnName:'<fmt:message key="label.expirationdate"/>', width:8, align: "center"},
	{ columnId:"deletionCode", columnName:'<fmt:message key="label.reason"/>',type:'hcoro',width:21, submit: true},
	{ columnId:"msdsNumber", columnName:'<fmt:message key="label.msds"/>', width:8, align: "center"},
	{ columnId:"materialDescription", columnName:'<fmt:message key="label.materialdesc"/>', tooltip: true,	width:18},
	{ columnId:"packaging",	columnName:'<fmt:message key="label.containersize"/>', tooltip: true, width:18},
	{ columnId:"solvent", align:'center', columnName:'<fmt:message key="label.solvent"/>',  width:4},
	{ columnId:"diluent", align:'center', columnName:'<fmt:message key="label.diluent"/>',  width:4},
	{ columnId:"containerType", submit: true},
	{ columnId:"kitId", submit: true},
	{ columnId:"materialId", submit: true},
	{ columnId:"companyId", submit: true},
	{ columnId:"facilityId", submit: true},
	{ columnId:"reportingEntityId", submit: true},
	{ columnId:"applicationId", submit: true},
	{ columnId:"buildingId", submit: true},
	{ columnId:"deletionDesc", submit: true}
];

var gridConfig = {
    divName:'hetContainerInventoryViewBean', // the div id to contain the grid. ALSO the var name for the submitted data
    beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
    beanGrid:'mygrid', // the grid's name, for later reference/usage
    config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
    rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
    submitDefault: false, // the fields in grid are defaulted to be submitted or not.
    noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
    onRowSelect:selectRow,
	variableHeight:false
};

<c:set var="workAreaCount" value="0"/>
var applicationId =
new Array(
<c:forEach var="workArea" items="${workAreaColl}" varStatus="status">
	 	 <c:if test="${ workAreaCount !=0 }">,</c:if>
		 {
			value:'${workArea.applicationId}',
			text:'<tcmis:jsReplace value="${workArea.applicationDesc}"/>'
		 }
		 <c:set var="workAreaCount" value="${workAreaCount+1}"/>
</c:forEach>
<c:if test="${workAreaCount == 0}">
{
	value:'',
	text:'BLDG HAS NO WORK AREAS CONFIGURED'
 }
</c:if>
);

var unitOfMeasure = new Array(
	<c:forEach var="uom" items="${unitsOfMeasure}" varStatus="status">
		 {value:'${uom.sizeUnit}',text:'${uom.sizeUnit}'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>
);

var deletionCode = new Array(
	{value:'',text:''}<c:if test="${!empty deletionCodes}">,</c:if>
	<c:forEach var="code" items="${deletionCodes}" varStatus="status">
		 {value:'${code.deletionCode}',text:'${code.deletionDesc}'}<c:if test="${!status.last}">,</c:if>
	</c:forEach>
);

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);resultOnLoad()">
<tcmis:form action="/hetcontainerinventoryresults.do" onsubmit="return submitFrameOnlyOnce();">
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
<c:if test="${empty hetContainerInventoryColl}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>

<div id="hetContainerInventoryViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty hetContainerInventoryColl}" >
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="item" items="${hetContainerInventoryColl}" varStatus="itemStatus">
	<c:forEach var="kit" items="${item}" varStatus="kitStatus">
	<c:set var="solvent" value='Y'/> 
	<c:set var="diluent" value='Y'/> 
	<c:if test="${kit.solvent == false}" ><c:set var="solvent" value='N'/> </c:if>
	<c:if test="${kit.diluent == false}" ><c:set var="diluent" value='N'/> </c:if>
		{ id:${dataCount+1},
		 data:['<c:choose><c:when test="${kit.cartCheckedOut}">N</c:when><c:otherwise>Y</c:otherwise></c:choose>',
		       false,
		       '${kit.applicationId}',
		       '<tcmis:jsReplace value="${kit.cartName}" processMultiLines="true"/>',
		       '<tcmis:jsReplace value="${kit.catPartNo}" processMultiLines="true"/>',
		       '${kit.itemId}',
		       '${kit.containerId}' ,
		       <c:choose>
		       		<c:when test="${kit.cartCheckedOut || kit.usageLogged}">'N','N',</c:when>
		       		<c:otherwise>'Y','Y',</c:otherwise>
		       </c:choose>  
		       '${kit.amountRemaining}',
		       '${kit.unitOfMeasure}',
				<fmt:formatDate var="expireYear" value="${kit.expirationDate}" pattern="yyyy"/>
				<c:choose>
					<c:when test="${expireYear == '3000'}">'<fmt:message key="label.indefinite"/>'</c:when>
					<c:otherwise>'<fmt:formatDate value="${kit.expirationDate}" pattern="${dateFormatPattern}"/>'</c:otherwise>
				</c:choose>,
		       '',
		       '${kit.msdsNumber}',  
		       '<tcmis:jsReplace value="${kit.materialDesc}" processMultiLines="true"/>',
		       '<tcmis:jsReplace value="${kit.packaging}" processMultiLines="true"/>',
		       '${solvent}',  
		       '${diluent}',   
			  '${kit.containerType}',
		      '${kit.kitId}',
		      '${kit.materialId}',
		      '${kit.companyId}',
		      '${kit.facilityId}',
		      '${kit.reportingEntityId}',
		      '${kit.applicationId}',
		      ''
		 ]} <c:if test="${!(itemStatus.last && kitStatus.last)}">,</c:if>
		<c:set var="dataCount" value='${dataCount+1}'/> 
	</c:forEach>
</c:forEach>
]};
<c:set var="showUpdateLink" value='Y'/>


<%-- determining rowspan --%>

<c:set var="lineCount" value='0' />
<c:set var="rowSpanCount" value='0' />
      <c:forEach var="item" items="${hetContainerInventoryColl}" varStatus="itemSpan">
            <c:forEach var="kitId" items="${item}" varStatus="kitIdSpan">
                  <c:choose>
                        <c:when test="${kitIdSpan.first}">
                              rowSpanMap[${lineCount}]= ${fn:length(item)};
                              <c:set var="prevkitId" value="${kitId.kitId}"/>
                              <c:set var="prevLineIndex" value="${lineCount}"/>           
                        </c:when>
                        <c:otherwise>     
                              <c:choose>
                                    <c:when test="${prevkitId == kitId.kitId}">
                                          rowSpanMap[${lineCount}]= 0;
                                    </c:when>
                                    <c:otherwise>
                                          rowSpanMap[${lineCount}]= ${fn:length(item)};
                                          <c:set var="prevLineItem" value="${kitId.kitId}"/>
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
	<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>   		
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