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
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- For Calendar support for column type hcal-->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<script type="text/javascript" src="/js/client/opencustomer/mrallocationreport.js"></script>
	
	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
	<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
	
	<%--Uncomment the below if your grid has rowspans >--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

	<%--This file has our custom sorting and other utility methos for the grid.--%>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

    
<title>
<fmt:message key="label.orderstatus"/>
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
displaycerts:"<fmt:message key="label.displaycerts"/>",
displaymsds:"<fmt:message key="label.displaymsds"/>",
requestmsds:"<fmt:message key="label.requestmsds"/>",
displaypackinglist:"<fmt:message key="label.displaypackinglist"/>",
displayinvoice:"<fmt:message key="label.displayinvoice"/>",
csr:"<fmt:message key="label.csr"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",  
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

var gridConfig = {
		       divName:'orderTrackDetailBean', // the div id to contain the grid. ALSO the var name for the submitted data
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'beanGrid', // the grid's name, for later reference/usage
		       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       submitDefault: false, // the fields in grid are defaulted to be submitted or not.
		       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		       onRightClick:selectRightclick, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		       selectChild: 1 // Double select single row
}; 

// RowSpan variables:
// rowSpanMap contains an entry for each row with its associated rowspan 1, 2, ... or 0 for child row 
// rowSpanClassMap contains the color (odd, even) for the row
// rowSpanCols contains the indexes of the columns that span
var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [1,2,3,4,5,6,7];	
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [8,9];	

var config = [
{ columnId:"permission"
},
{
  columnId:"lineItem",
  columnName:'<fmt:message key="mrallocationreport.label.mrline"/>',
  width:3
},
{ columnId:"customerPartNo",
  columnName:'<fmt:message key="label.customerpartnumber"/>',
  tooltip:true,
  width:7
},
{ columnId:"poNumber",
  columnName:'<fmt:message key="label.customerpo"/>',
  tooltip:true,
  width:7
},
{ columnId:"orderDate",
  columnName:'<fmt:message key="label.orderdate"/>',
  width:8
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.catalogitem"/>',
  width:7
},
{ columnId:"itemType",
  columnName:'<fmt:message key="label.type"/>',
  width:4
},
{ columnId:"deliveryType",
  columnName:'<fmt:message key="label.deliverytype"/>',
  width:6
},
{ columnId:"requiredDatetime",
  columnName:'<fmt:message key="label.needed"/>',
  width:7
},
{ columnId:"orderQuantity",
  columnName:'<fmt:message key="label.orderedqty"/>',
  align:"right",
  width:5
},
{ columnId:"allocatedQuantity",
  columnName:'<fmt:message key="label.allocatedqty"/>',
  align:"right",
  width:5
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  tooltip:true,
  width:8
},
{ columnId:"refDisplay",
  columnName:'<fmt:message key="mrallocationreport.label.ref"/>',
  tooltip:true,
  width:20
},
{ columnId:"allocationReferenceDate",
  columnName:'<fmt:message key="label.projecteddeliverydate"/>'
},
{ columnId:"mfgLot",
  columnName:'<fmt:message key="mrallocationreport.label.lot"/>'
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  tooltip:true,
  width:10
},
{ columnId:"receiptDocument"
},
{ columnId:"ref"
},
{ columnId:"refType"
},
{ columnId:"refNumber"
},
{ columnId:"shipmentId"
},
{ columnId:"itemId"
},
{ columnId:"showDisplayCerts"
},
{ columnId:"showDisplayMSDS"
},
{ columnId:"showDisplayInvoice"
}
];

with(milonic=new menuname("rightClickMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 //aI("text=<fmt:message key="label.print"/>;url=javascript:window.print();");	
 aI("text=test;");
}

drawMenus();

function showCSR()
{
  var showCSRArea = document.getElementById("showCSRArea");
  showCSRArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.csr)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 350, 75);
  legendWin.setText(messagesData.csr);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showCSRArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}


// -->
    </script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad(gridConfig);" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/mrallocationreport.do" onsubmit="return submitFrameOnlyOnce();">

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
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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
        <fmt:message key="label.csr"/>: <a href="#" onclick="showCSR();">${param.csrName}</a> 
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="orderTrackDetailBean" style="height:400px;display: none;" ></div>
<c:if test="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection != null}" >

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="detailBean" items="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" varStatus="status">
   
        <c:if test="${!status.first}">,</c:if>
        <fmt:formatDate var="fmtOrderDate" value="${detailBean.orderDate}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="fmtRequiredDatetime" value="${detailBean.requiredDatetime}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="fmtAllocationReferenceDate" value="${detailBean.allocationReferenceDate}" pattern="${dateFormatPattern}"/>
        
		<c:set var="refDisplay"><tcmis:jsReplace value="${detailBean.ref}" processMultiLines="true"/></c:set>
		<c:if test="${!empty detailBean.carrierName}">
			<c:set var="refDisplay">${refDisplay}<br><fmt:message key="label.carrier"/>=<c:out value="${status2.current.carrierName}"/></c:set>
		</c:if>
		<c:if test="${!empty detailBean.trackingNumber}">
			<c:set var="refDisplay">${refDisplay}<br><fmt:message key="label.trackingN"/>=<c:out value="${status2.current.trackingNumber}"/></c:set>
		</c:if>
		
			<c:choose>
				<c:when test="${!empty detailBean.receiptDocumentColl}">
					<c:set var="showDisplayCerts" value='Y'/>
				</c:when>
				<c:otherwise>
					<c:set var="showDisplayCerts" value='N'/>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${!empty detailBean.msdsColl}">
					<c:set var="showDisplayMSDS" value='Y'/>
				</c:when>
				<c:otherwise>
					<c:set var="showDisplayMSDS" value='N'/>
				</c:otherwise>
			</c:choose>
			
			<c:set var="showDisplayInvoice" value='N'/>
			<c:if test="${!empty detailBean.invoiceColl}">
				<tcmis:facilityPermission indicator="true" userGroupId="CustomerInvoice" facilityId="${param.facilityId}">
					<c:set var="showDisplayInvoice" value='Y'/>
				</tcmis:facilityPermission>			
			</c:if>
			
        
        { id:${status.count},
         data:['Y',
         '${detailBean.lineItem}','${detailBean.customerPartNo}','${detailBean.poNumber}','${fmtOrderDate}','${detailBean.facPartNo}','${detailBean.itemType}',
  		 '${detailBean.deliveryType}','${fmtRequiredDatetime}','${detailBean.orderQuantity}','${detailBean.allocatedQuantity}',
  		 '${detailBean.status}','${refDisplay}','${fmtAllocationReferenceDate}','${detailBean.mfgLot}',
  		 '<tcmis:jsReplace value="${detailBean.notes}" processMultiLines="true"/>',
  		 '${detailBean.receiptDocument}','${refDisplay}','${detailBean.refType}','${detailBean.refNumber}','${detailBean.shipmentId}',
  		 '${detailBean.itemId}','${showDisplayCerts}','${showDisplayMSDS}','${showDisplayInvoice}',
  		 ]}

        <c:set var="dataCount" value='${dataCount+1}'/>
         </c:forEach>
        ]};
        
        
        <c:forEach var="row" items="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" varStatus="status">
							<c:set var="currentKey" value='${row.facPartNo}${row.lineItem}' />
							<c:set var="currentKeyLvl2" value='${row.requiredDatetime}' />
							<c:choose>
								<c:when test="${status.first}">
									<c:set var="rowSpanStart" value='0' />
									<c:set var="rowSpanLvl2Start" value='0' />
									<c:set var="rowSpanCount" value='1' />
									rowSpanMap[0] = 1;
									rowSpanLvl2Map[0] = 1;
									rowSpanClassMap[0] = 1 % 2;
								</c:when>
								<c:when test="${currentKey == previousKey}">
									rowSpanMap[${rowSpanStart}]++;
									rowSpanMap[${status.index}] = 0;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
									<c:choose>
										<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
											rowSpanLvl2Map[${rowSpanLvl2Start}]++;
											rowSpanLvl2Map[${status.index}] = 0;
										</c:when>
										<c:otherwise>
											<c:set var="rowSpanLvl2Start" value='${status.index}' />
											rowSpanLvl2Map[${status.index}] = 1;
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:set var="rowSpanStart" value='${status.index}' />
									<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
									<c:set var="rowSpanLvl2Start" value='${status.index}' />
									rowSpanMap[${status.index}] = 1;
									rowSpanLvl2Map[${status.index}] = 1;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:otherwise>
							</c:choose>
							<c:set var="previousKey" value='${currentKey}' />
							<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
						</c:forEach>
    // -->
    </script>
</c:if>


<!-- If the collection is empty say no data found -->
<c:if test="${empty pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" >
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
 <div id="footerHolder" class="messageBar"><span id="footer" style="float: left"></span><span style="float: right"><fmt:message key="label.rightclickformoreoptions"/></span></div>
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
<input name="mrNumber" id="mrNumber" value="${param.mrNumber}" type="hidden"/>
<input name="lineItem" id="lineItem" value="${param.lineItem}" type="hidden"/>
<input name="selectedRefType" id="selectedRefType" value="" type="hidden"/>
<input name="selectedDocumentId" id="selectedDocumentId" value="" type="hidden"/>
<input name="selectedRefNumber" id="selectedRefNumber" value="" type="hidden"/>
<input name="selectedLineItem" id="selectedLineItem" value="" type="hidden"/>
<input name="selectedShipmentId" id="selectedShipmentId" value="" type="hidden"/>
<input name="csrEmail" id="csrEmail" value="${param.csrEmail}" type="hidden"/>
<input name="documentId" id="documentId" value="${documentId}" type="hidden"/>
<input name="receiptId" id="receiptId" value="${receiptId}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">    
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', personnelBean.companyId)}'/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
<%-- show CSR Begins --%>
<div id="showCSRArea" style="display: none;overflow: auto;">
  <table width=100%  border="0" cellpadding="0" cellspacing="0">
   	<tr><td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.csr"/>&nbsp;:&nbsp;</td><td class="optionTitleLeft" width="90%" >${param.csrName}</td></tr>
   	<tr><td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.phone"/>&nbsp;:&nbsp;</td><td class="optionTitleLeft" width="90%" >${param.csrPhone}</td></tr>
   	<tr><td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.email"/>&nbsp;:&nbsp;</td><td class="optionTitleLeft" width="90%" ><a href='mailto:${param.csrEmail}'>${param.csrEmail}</a></td></tr>
  </table>
</div>
<%-- show CSR Ends --%>
</body>
</html:html>