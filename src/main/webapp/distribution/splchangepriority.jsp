<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/splchangepriority.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.supplierpricelistchangepriority"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectedaddress:"<fmt:message key="label.selectedaddress"/>",
errors:"<fmt:message key="label.errors"/>",  
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
allprioritiesunique:"<fmt:message key="label.allprioritiesunique"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
 var gridConfig = {
		divName:'SupperPriceListBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		noSmartRender:true,
		submitDefault:true
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="leadtimeindays"><fmt:message key="label.leadtimeindays"/></c:set>
var config = [
	{columnId:"permission"},
	{columnId:"itemId", columnName:'<fmt:message key="label.catalogitem"/>', sorting:"haasStr", tooltip:true, width:6},
	{columnId:"itemDesc", columnName:'<fmt:message key="label.description"/>', sorting:"haasStr", tooltip:true, width:15},
	{columnId:"shipToLocationId",columnName:'<fmt:message key="label.shipto"/>'},
	{columnId:"inventoryGroupName", columnName:'<tcmis:jsReplace value="${inventorygroup}"/>', tooltip:true, width:15},
	{columnId:"priority", columnName:'<fmt:message key="label.priority"/>', type:'hcoro', width:4},
	{columnId:"supplierName", columnName:'<fmt:message key="label.supplier"/>', sorting:"haasStr", tooltip:true, width:15},
	{columnId:"dbuyContractId"}
];
    
var priority = new Array(
	{text:'1',value:'1'},
	{text:'2',value:'2'},
	{text:'3',value:'3'},
	{text:'4',value:'4'},
	{text:'5',value:'5'},
	{text:'6',value:'6'},
	{text:'7',value:'7'},
	{text:'8',value:'8'},
	{text:'9',value:'9'},
	{text:'10',value:'10'},
	{text:'11',value:'11'}
);

function closeIfDone() {
	if('${done}' == 'Y') {
		opener.$("uAction").value = 'search';
		opener.document.genericForm.submit();
  		setTimeout('window.close()',1000);
  	}	
}

// -->
    </script>
</head>

<body bgcolor="#ffffff"  onload="closeIfDone();resultOnLoadWithGrid(gridConfig);" onunload="opener.closeTransitWin();" onresize="resizeFrames()">

<tcmis:form action="/splchangepriority.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!--  update permissions here, if it is read-only page you don't need this -->
<%--<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >--%><%--
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
</tcmis:facilityPermission>--%>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
 </c:forEach>    
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>


<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks"> 
         <a href="#" onclick="javascript:submitUpdate()"><fmt:message key="label.update"/></a>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="SupperPriceListBean" style="height:400px;display: none;" ></div>
<c:if test="${beanCollection != null}" >

<tcmis:opsEntityPermission indicator="true" userGroupId="supplierPriceList" opsEntityId="${param.opsEntityId}">
	<c:set var="permission" value="Y"/>
</tcmis:opsEntityPermission>

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanCollection}" >
<script type="text/javascript">
<!--
var jsonMainData = new Array();
var jsonMainData = {
rows:[
  <c:forEach var="bean" items="${beanCollection}" varStatus="status">
   
    <c:if test="${status.index > 0}">,</c:if>
        
    { id:${status.index +1},
      data:[
       '${permission}',
	   '${bean.itemId}', 
	   '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
	   '${bean.shipToLocationId}',
	   '<tcmis:jsReplace value="${bean.inventoryGroupName}" />',
	   '${bean.priority}',
	   '<tcmis:jsReplace value="${bean.supplierName}"/>',
	   '${bean.dbuyContractId}'
	   ]}

   	<c:set var="dataCount" value='${dataCount+1}'/>
  </c:forEach>
]};
// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</c:if>
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<input name="itemId" id="itemId" value="${param.itemId}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
<input name="shipToCompanyId" id="shipToCompanyId" value="${param.shipToCompanyId}" type="hidden"/> 
<input name="shipToLocationId" id="shipToLocationId" value="${param.shipToLocationId}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">    
<input name="minHeight" id="minHeight" type="hidden" value="100">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>