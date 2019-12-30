<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here
<script type="text/javascript" src="/js/client/catalog/itemcatalog.js"></script>
-->
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
selectedRowMsds:"<fmt:message key="label.selectedmsds"/>",
selectedRowMaterial:"<fmt:message key="label.selectedmaterial"/>"
};

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var map = null;
var map2 = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();
var selectedRowId = null;
if('Y' == '${showFacilityUseCode}')
	var rowSpanCols = [0,1,2,3];
else
	var rowSpanCols = [0,1,2];

with ( milonic=new menuname("viewMsdsMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewcomponentmsds"/> ;url=javascript:viewMsds();" );
 aI( "text=<fmt:message key="label.showstoragelocations"/> ;url=javascript:showStorageLocationMenu();" );   
}

with ( milonic=new menuname("viewKitMsdsMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewkitmsds"/> ;url=javascript:viewKitMsds();" );
}

with ( milonic=new menuname("viewEmptyMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=" );
}

drawMenus();

var windowCloseOnEsc = true;

var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

function viewMsds() {
	try {
		if(newMsdsViewer)
			opener.children[opener.children.length] = openWinGeneric('viewmsds.do?act=msds'+
                                                  '&materialId='+ cellValue(selectedRowId,"materialId") +
                                                  '&spec=' + // do we need to know spec id?
                                                  '&facility=' +opener.$v("facilityId") +
                                                  '&catpartno='
                                                  ,"ViewMSDS","900","600",'yes' );
        else
        	opener.children[opener.children.length] = openWinGeneric('ViewMsds?act=msds'+
                                                  '&id='+ cellValue(selectedRowId,"materialId") +
                                                  '&spec=' + // do we need to know spec id?
                                                  '&facility=' +opener.$v("facilityId") +
                                                  '&catpartno='
                                                  ,"ViewMSDS","900","600",'yes' );
    } catch(ex) {
		openWinGeneric('viewmsds.do?act=msds'+'&materialId='+ cellValue(selectedRowId,"materialId") +
                        '&showspec=N' +
                        '&spec=' + // do we need to know spec id?
                        '&cl='+cellValue(mygrid.getSelectedRowId(),"companyId")+
                        '&facility='+opener.$v("facilityId")
                        ,"ViewMSDS","900","600",'yes' );
	}
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+cellValue(mygrid.getSelectedRowId(),"companyId")+
                    "&pr_custMsdsDb="+cellValue(mygrid.getSelectedRowId(),"customerMsdsDb")+
					"&pr_custMsdsNo="+cellValue(mygrid.getSelectedRowId(),"customerMixtureNumber")+
					"&rpt_ReportBeanId=MSDSKitReportDefinition";
	try {
		opener.children[opener.children.length] = openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	} catch(ex) {
		openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	}
}

function showStorageLocationMenu() {
	var url = 'storagelocations.do?materialId='+cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent(opener.$v('facilityId')) +
			'&facilityName=' + encodeURIComponent(opener.$("facilityId").options[opener.$("facilityId").selectedIndex].text) +
			'&application=' +
			'&msdsNo=' + cellValue(selectedRowId,"customerMsdsNumber") +
			'&tradeName=' + encodeURIComponent(cellValue(selectedRowId,"tradeName")) +
			'&mfgDesc=' + encodeURIComponent(cellValue(selectedRowId,"mfgDesc")) +
			'&desc=' + encodeURIComponent(cellValue(selectedRowId,"materialDesc")) ;

	openWinGeneric(url,"storageLocations","500","500",'yes' );
}

function newMaterial() {
	showTransitWin();
	var url = 'catalogaddmsdsrequest.do?uAction=newMaterial&calledFrom=searchMsds&requestId='+opener.$v("requestId");
	opener.children[opener.children.length] = openWinGeneric(url,"catalogAddEditNewMsds",800,350,'yes' );
}


function addNewMaterialToList() {
    opener.opener.addNewMaterialToList();
    opener.window.close();
}

function closeTransitWin() {
	opener.closeTransitWin();
}

var selectedMsdsNo = "";
function selectRow()
{
    // to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
   }

    selectedRowId = rowId0;

    selectedMsdsNo = cellValue(rowId0,"customerMixtureNumber");
    if (selectedMsdsNo == null || selectedMsdsNo.length == 0) {
        selectedMsdsNo = cellValue(rowId0,"customerMsdsNumber");
    }
	
    if( !rightClick ) return;

    if (cellValue(selectedRowId,"msdsOnLine") == 'Y') {
        if (colId0 < mygrid.getColIndexById("customerMsdsNumber")) {
            if (cellValue(selectedRowId,"customerMixtureNumber").length > 0) {
                toggleContextMenu("viewKitMsdsMenu");
            }else {
                toggleContextMenu("viewEmptyMenu");
            }
        }else {
            toggleContextMenu("viewMsdsMenu");
        }
    }else {
        toggleContextMenu("viewEmptyMenu");
    }
}

var config = [
{ columnId:"customerMsdsDbName",
    columnName:'<fmt:message key="label.msdsdatabase"/>',
    align:"left"
},
{
    columnId:"customerMixtureNumber",
	columnName:'<fmt:message key="label.kitmsds"/>'
},
{
    columnId:"mixtureDesc",
	columnName:'<fmt:message key="label.kitdesc"/>',
    width:15,
    tooltip:"Y"
},
{
    columnId:"approvalCode"
    <c:if test="${'Y' == showFacilityUseCode}">
    ,
	columnName:'<fmt:message key="label.kitapprovalcodes"/>'
	</c:if>
},
{
    columnId:"customerMsdsNumber",
	 columnName:'<fmt:message key="label.msds"/>'
},
{
    columnId:"materialDesc",
	columnName:'<fmt:message key="label.materialdesc"/>',
	width:30,
    tooltip:"Y"
},
{ columnId:"mixRatio",
	  columnName:'<fmt:message key="label.mixratio"/>',
	  align:"right",
	  width:9
},
{
    columnId:"materialId",
    columnName:'<fmt:message key="label.materialid"/>'
},
{
    columnId:"mfgDesc",
	columnName:'<fmt:message key="label.manufacturer"/>',
    width:15,
    tooltip:"Y"
},
{
    columnId:"tradeName",
	columnName:'<fmt:message key="label.tradename"/>',
	width:30,
    tooltip:"Y"
},
{
    columnId:"msdsOnLine"
},
{
    columnId:"customerMsdsDb"
},
{
    columnId:"companyId"
}
];

var gridConfig = {
		divName:'itemCatalogScreenSearchBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false,
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
        selectChild:1
	};

   function resultOnLoad() {
   	    $('endSearchTime').value = new Date().getTime();
   	    displayNoSearchSecDuration();
   	   	setResultSize();
   }
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid(gridConfig);setTimeout('resultOnLoad()',100);"  onresize="setTimeout('resizeFrames();',55)">
<tcmis:form action="/advancedmsdsviewerresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>
<!-- Transit Page Begins -->
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

<div class="interface" id="resultsPage">
<div class="backGroundContent"> <!-- start of backGroundContent -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="resultGridDiv" style="display: none;">

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
        <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
   			<a href="javascript:opener.showLegend()"><fmt:message key="label.showlegend"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">

<div id="itemCatalogScreenSearchBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${!empty itemCatalogBeanCollection}" >
<script language="JavaScript" type="text/javascript">
<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>

<c:if test="${itemCatalogBeanCollection != null}" >
 <c:if test="${!empty itemCatalogBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	  <c:if test="${status.index != 0 }">,</c:if>
	<c:set var="curPar" value="${status.current.customerMixtureNumber}"/>
	<c:if test="${!(curPar eq prePar)}">
		<c:set var="parCount" value="${parCount+1}"/>
	</c:if>

    <c:set var="tmpMixRatioUnit" value="${p.mixRatioSizeUnit}"/>
    <c:if test="${p.mixRatioSizeUnit == '%(v/v)'}">
        <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByVolume"/></c:set>
    </c:if>
     <c:if test="${p.mixRatioSizeUnit == '%(w/w)'}">
        <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByWeight"/></c:set>
    </c:if>

    { id:${status.index +1},
        data:[
                '${p.customerMsdsDb}',
                '${p.customerMixtureNumber}',
                '<tcmis:jsReplace value="${p.mixtureDesc}" processMultiLines="true"/>',
                '${p.kitApprovalCode}',
                '${p.customerMsdsNumber}',
                '<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>',
                '<fmt:formatNumber value="${p.mixRatioAmount}" pattern="${totalcurrencyformat}"/> ${tmpMixRatioUnit}',
                '${p.materialId}',
			    '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${p.tradeName}" processMultiLines="true"/>',
                '${p.msdsOnLine}',
                '${p.customerMsdsDb}',
                '${p.companyId}'
        ]}
		<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
		<c:if test="${!(numberOfKit eq -1)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>
		<c:set var="prePar" value="${status.current.customerMixtureNumber}"/>

     </c:forEach>
  ]};

  </c:if>
 </c:if>

// -->
</script>
 </c:if> 

<%-- determining rowspan --%>
<c:set var="itemCount" value='0'/>
<c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	   lineMap[${status.index}] = ${numberOfKit} ;
		<c:if test="${!(numberOfKit eq -1)}">
	      <c:set var="itemCount" value='${itemCount+1}'/>
			map = new Array();
		</c:if>
	   lineMap3[${status.index}] = ${itemCount} % 2;
	   map[map.length] =  ${status.index} ; lineIdMap[${status.index}] = map;
	// -->
	</script>
</c:forEach>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%--
<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >
--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
<%--</tcmis:facilityPermission>--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
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
   <c:if test="${empty itemCatalogBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
   </c:if>
     <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>
</div>
</div>

<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
  </div>
</div>  
  </td>
  
</tr>
</table>



<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="showFacilityUseCode" id="showFacilityUseCode" value="${showFacilityUseCode}" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${param.startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="">
 <input name="minHeight" id="minHeight" type="hidden" value="100">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>