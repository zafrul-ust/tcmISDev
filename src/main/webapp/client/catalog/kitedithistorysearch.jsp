<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.kitrevisionhistory"/>: ${param.customerMixtureNumber}
</title>

<script language="JavaScript" type="text/javascript">

var maxData = ${param.maxData};
function myOnLoad()
{

	 if( !gridConfig ) gridConfig = _gridConfig;
	 initGridWithGlobal(gridConfig);
	 displayNoSearchSecDurationLocal();
	 if (typeof( extraReduction ) != 'undefined')
	 	setResultSize(extraReduction);
	 else
	 	setResultSize();
}

function displayNoSearchSecDurationLocal() {
	   var totalLines = document.getElementById("totalLines");
	   if (totalLines.value != null) {
	     if (totalLines.value*1 > 0) {
	       var startSearchTime = document.getElementById("startSearchTime");
	       var endSearchTime = document.getElementById("endSearchTime");
	       var minutes = 0;
	       var seconds = 0;
	       //the duration is in milliseconds
	       var searchDuration = (endSearchTime.value*1)-(startSearchTime.value*1);
	       if (searchDuration > (1000*60)) {   //calculating minutes
	         minutes = Math.round((searchDuration / (1000*60)));
	         var remainder = searchDuration % (1000*60);
	         if (remainder > 0) {   //calculating seconds
	           seconds = Math.round(remainder / 1000);
	         }
	       }else {  //calculating seconds
	         seconds = Math.round(searchDuration / 1000);
	       }
	       var footer = document.getElementById("footer");
	       if (minutes != 0) {
	         footer.innerHTML = messagesData.recordFound+": "+(totalLines.value-1)+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
	       }else {
	         footer.innerHTML = messagesData.recordFound+": "+(totalLines.value-1)+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
	       }
	       
	       footer.innerHTML = footer.innerHTML + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*" + messagesData.maxrecordsdisplayed.replace('{0}', maxData);
	     }else {
	       var footer = document.getElementById("footer");
	       footer.innerHTML ="";
	     }
	   }else {
	     var footer = document.getElementById("footer");
	     footer.innerHTML ="&nbsp;";
	   }
	}

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
maxrecordsdisplayed:"<fmt:message key="label.maxrecordsdisplayed"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
positivenumber:"<fmt:message key="label.positivenumber"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


var gridConfig = {
		divName:'kitEditHistViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var config = [
{
  columnId:"permission"
},
{
	 columnId:"updateByName", 
	 columnName:'<fmt:message key="label.updatedby"/>', 
	 align:'center', 
	 width:8
},
{
	 columnId:"updatedOn", 
	 columnName:'<fmt:message key="label.updatedon"/>', 
	 align:'center', 
	 width:7
},
{
	 columnId:"mixtureDesc", 
	 columnName:'<fmt:message key="report.label.kitDescription"/>', 
	 align:'center', 
	 width:7
},
{
	 columnId:"mixtureMfgId", 
	 columnName:'<fmt:message key="label.kitmanufacturer"/>', 
	 align:'center', 
	 width:7
},
{
	 columnId:"mixtureProductCode", 
	 columnName:'<fmt:message key="report.label.productCode"/>', 
	 align:'center', 
	 width:6
},
{ columnId:"voc", 
	columnName:'<fmt:message key="label.voc"/>', 
    sorting:'int',
    width:4
    
},
{ columnId:"vocLwes", 
  columnName:'<fmt:message key="label.voclwes"/>', 
  align:'left',
  sorting:'int',
  width:4
},
{ columnId:"specificGravity", 
	columnName:'<fmt:message key="label.specificgravity"/>', 
    align:'left',
    sorting:'int',
    width:4
},
{ columnId:"density", 
	columnName:'<fmt:message key="label.density"/>', 
    align:'left',
    sorting:'int',
    width:4
},
{ columnId:"physicalState", 
	columnName:'<fmt:message key="label.physicalstate"/>', 
	align:'left', 
	width:7
}
];

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();" onresize="resizeFrames()">
<tcmis:form action="/kitrevisionhistorysearch.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="readonly" value='N'/>
<tcmis:permission indicator="true"  userGroupId="editCustomerList">
 <script type="text/javascript">
 <!--
showUpdateLinks = false;
var hasPermission;
 <c:choose>
	<c:when test="${param.permission == 'Y'}">
        <c:set var="readonly" value="Y"/>
        hasPermission = true;
    </c:when>
	<c:otherwise>
        <c:set var="readonly" value="N"/>
        hasPermission = false;
    </c:otherwise>
</c:choose>
 //-->
 </script>
</tcmis:permission>
   
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
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
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
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">

<div id="kitEditHistViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${searchResults != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty searchResults}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${searchResults}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{id:${status.index +1},
 data:[
  'N',
	'${bean.updatedByName}',
	'<fmt:formatDate value="${bean.lastUpdated}" pattern="${dateTimeFormatPattern}"/>',
	'${bean.mixtureDesc}',
	'${bean.mfgShortName}',
	'${bean.mixtureProductCode}',
	'${bean.vocDisplay}',
	'${bean.vocLwesDisplay}',
	'${bean.specificGravityDisplay}',
    '${bean.densityDisplay}',
	'${bean.physicalState} ${bean.physicalStateSource}'
  ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>

 //-->
</script>
</c:if>
        
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>

</div>
</div>

<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    
  </td>
</tr>
</table>
<!-- results end -->

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount + 1}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input type="hidden" name="maxData" id="maxData" value="${param.maxData}"/>

</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</tcmis:form>
</body>
</html:html>