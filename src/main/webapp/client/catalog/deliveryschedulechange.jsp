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
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/deliveryschedulechange.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="deliveryschedule.label.deliveryschedulereview"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", showlegend:"<fmt:message key="label.showlegend"/>",
deliveryScheduleView:"<fmt:message key="deliveryschedule.label.title"/>"};

var gridConfig = {
	divName:'deliveryScheduleChangeBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
    noSmartRender:true, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
    onAfterHaasRenderRow:setRowStatusColors
};

/*Declare any right click menus if any.*/
with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
 	aI("text=<fmt:message key="label.open"/> <fmt:message key="label.mr"/>;url=javascript:openMr();");
}
drawMenus();

/*This array defines the grid and properties about the columns in the grid.
* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
* */
var config = [
  //permissions column will be used to decide if a column in a row is editable or not,
  //except if a specific permisison is defined for that column.
  {
  	columnId:"permission"
  },
  {columnId:"status",
   columnName:'<fmt:message key="label.status"/>',
   tooltip:"Y",
   width:8,
   align:"center"
  },
{ columnId:"dateToDeliver",
  columnName:'<fmt:message key="label.date"/>',
  tooltip:"Y",
  width:10,
  align:"center"
},
{ columnId:"originalQty",
  columnName:'<fmt:message key="deliveryschedule.label.originalqty"/>',
  tooltip:"Y",
  align:"center",
  width: 4
},
{ columnId:"revisedQty",
  columnName:'<fmt:message key="deliveryschedule.label.revisedqty"/>',
  tooltip:"Y",
  align:"center",
  width: 4
},
{ columnId:"updatedBy", 
  columnName:'<fmt:message key="label.updatedby"/>',
  width:12
},
{ columnId:"updatedDate",
  columnName:'<fmt:message key="label.updateddate"/>',
  width:12
},
{ columnId:"approvedBuyer", 
  columnName:'<fmt:message key="label.approvingbuyerdate"/>', 
  width:15
},
{
  columnId:"approvedCSR", 
  columnName:'<fmt:message key="label.approvingcsrdate"/>', 
  width:15
}
];

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 220);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
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

<body bgcolor="#ffffff" onload="loadLayoutWin('','deliveryschedulechange');resultOnLoadWithGrid(gridConfig);" onresize="resizeFrames();">
<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
	<tcmis:form action="/deliveryschedulechange.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="searchFrameDiv">
			<div class="contentArea">
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="roundcont filterContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
				</div>
				<div class="roundContent">
					<div class="boxhead">
						<div id="gridUpdateLinks">
						</div>
					</div>
					
					<table width="100%" id="detailsDataTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.mrline" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.prNumber}-${headerBean.lineItem}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.part" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.facPartNo}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.requestor" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.fullName}</td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.facilityName}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.item" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.itemId}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.email" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft"><a HREF="mailto:${headerBean.email}">${headerBean.email}</a></td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.workarea" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.applicationDisplay}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.packaging" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.packaging}</td>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.phone" />:&nbsp;</td>
							<td width="15%" class="optionTitleLeft">${headerBean.phone}</td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.itemdescription" />:&nbsp;</td>
							<td width="15%" colspan=5 class="optionTitleLeft">${headerBean.itemDesc}</td>
						</tr>
						<tr>
							<td colspan=6 width="15%" class="optionTitleLeft">${reviewerMsg}</td>
						</tr>
					</table>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
		</div>
		</td>
		</tr>
		</table>
	</div>
</div>
		
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>

		<div id="resultGridDiv">
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
		<div class="roundcont contentContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
				</div>
				<div class="roundContent">
					<div class="boxhead">
						<a href="#" onclick="showLegend();">Legend</a>
							| <a href="#" onclick="showFinalSchedule();">Show Resulting Schedule</a>
							<c:if test="${deliveryBeanCollection != null and !empty deliveryBeanCollection}" >
							    | <a href="#" onclick="submitApprove();">Approve</a>
							</c:if>
					</div>

<div class="interface" id="resultFrame" style="">
	<div class="backgroundContent">
<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="deliveryScheduleChangeBean" style="width:100%;height:390px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${deliveryBeanCollection != null}" >
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty deliveryBeanCollection}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="deliveryBean" items="${deliveryBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:set var="deliveryStatus" value="" />
<c:if test="${status.current.originalQty != status.current.revisedQty}">
		<c:choose>
		<c:when test="${status.current.originalQty == 0}">
			<c:set var="deliveryStatus">
				<fmt:message key="label.added"/>
			</c:set>
		</c:when>
		<c:when test="${status.current.revisedQty == 0}">
			<c:set var="deliveryStatus">
				<fmt:message key="label.deleted"/>
			</c:set>
		</c:when>
		<c:otherwise>
			<c:set var="deliveryStatus">
				<fmt:message key="label.changed"/>
			</c:set>
		</c:otherwise>
	</c:choose>
</c:if>

/* fmt:formatDate, to formate your date to locale pattern */
/* pattern="${dateFormatPattern}" or pattern="${dateTimeFormatPattern}" */
<fmt:formatDate var="deliverDate" value="${status.current.dateToDeliver}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="updateDate" value="${status.current.updatedDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="expediteApproveDate" value="${status.current.expediteApprovalDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="csrApproveDate" value="${status.current.csrApprovalDate}" pattern="${dateTimeFormatPattern}"/>

<c:set var="appDateBuyer" value="" />
<c:set var="appDateCsr" value="" />
<c:if test="${not empty expediteApproveDate}">
	<c:set var="appDateBuyer" value="(${expediteApproveDate})" />
</c:if>
<c:if test="${not empty csrApproveDate}">
	<c:set var="appDateCsr" value="(${csrApproveDate})" />
</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
/* Set the color of the rows if different from standard colors.*/
	 data:[
     // Set PERMISSIONS here
     'N',
     '${deliveryStatus}',
     '${deliverDate}',
	 '${status.current.originalQty}',
	 '${status.current.revisedQty}',
	 '<tcmis:jsReplace value="${status.current.updatedByName}"/>',
	 '${updateDate}',
	 '<tcmis:jsReplace value="${status.current.expediteApprovalName}"/> ${appDateBuyer}',
	 '<tcmis:jsReplace value="${status.current.csrApprovalName}"/> ${appDateCsr}'
	 ]}

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
//-->
</script>
       
<!-- If the collection is empty say no data found -->
<c:if test="${empty deliveryBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

</c:if>
	</div>
</div>

				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
		</div>
		</td>
		</tr>
		</table>
		</div>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" />
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" id="totalQty" name="totalQty" value="${headerBean.quantity}"/>
<input type="hidden" id="prNumber" name="prNumber" value="${headerBean.prNumber}"/>
<input type="hidden" id="lineItem" name="lineItem" value="${headerBean.lineItem}"/>
<input type="hidden" id="callback" name="callback" value="${callback}"/>
<input type="hidden" id="isBuyer" name="isBuyer" value="${buyer}"/>
<input type="hidden" id="isCsr" name="isCsr" value="${csr}"/>

<%-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates
Do not use <tcmis:saveRequestParameter/> on pages with updates
The purpose of this tag was to save the search criteria on the result page.
When there is a form in the result section and gets submitted to the server there is no difference between
the search parameters and the result parameters. This causes duplicates of the result section parameters.
This can potentially cause lot of mix-ups.
 --%>
<%--<tcmis:saveRequestParameter/>--%>

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="0">
 </div>
<!-- Hidden elements end -->

</div> 

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</div><!-- close of interface -->

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" style="background-color:lightGreen;">&nbsp;</td><td class="legendText"><fmt:message key="label.added"/></td></tr>
    <tr><td width="100px" style="background-color:lightYellow">&nbsp;</td><td class="legendText"><fmt:message key="label.changed"/></td></tr>
    <tr><td width="100px" style="background-color:lightGray">&nbsp;</td><td class="legendText"><fmt:message key="label.deleted"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>