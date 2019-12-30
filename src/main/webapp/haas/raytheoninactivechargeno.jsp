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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"/>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here 
<script type="text/javascript" src="/js/distribution/speclist.js"></script>
-->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>    

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


<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title><fmt:message key="raytheonInactiveChargeNo"/></title>
  
<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
scratchpad:"<fmt:message key="label.scratchPad"/>",
norow:"<fmt:message key="error.norowselected"/>",
errors:"<fmt:message key="label.errors"/>"};

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	initializeGrid(); 
	displayNoSearchSecDuration();
	$('dataBean').style.display="";
	setResultSize();
}

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
} 

function selectRightclick(rowId,cellInd) {
	selectRow(rowId,cellInd);
	toggleContextMenu('rightClickMenu');
} 

function initializeGrid(){
	mygrid = new dhtmlXGridObject('dataBean');

	initGridWithConfig(mygrid,config,-1,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		mygrid.parse(jsonMainData,"json");
	}
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRightclick);
}

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"c1",
  columnName:'<fmt:message key="label.inactivedate"/>'
},
{ columnId:"c2",
  columnName:'<fmt:message key="label.chargeid"/>'
},
{ columnId:"c3",
  columnName:'<fmt:message key="label.tradingpartner"/>'
},
	{ columnId:"c4",
	  columnName:'<fmt:message key="label.chargenumber"/>'
	},
	{ columnId:"c5",
	  columnName:'<fmt:message key="label.ponumber"/>'
	},
	{ columnId:"c6",
 	  columnName:'<fmt:message key="label.poline"/>'
	},
{ columnId:"c7",
  columnName:'<fmt:message key="label.radianpo"/>'
},
{ columnId:"c8",
	  columnName:'<fmt:message key="label.mrnumber"/>'
},
{ columnId:"c81",
	  columnName:'<fmt:message key="label.lineitem"/>'
},
{ columnId:"c82",
	  columnName:'<fmt:message key="label.status"/>'
},
{ columnId:"c9",
	  columnName:'<fmt:message key="label.facilityid"/>'
},
{ columnId:"c10",
	  columnName:'<fmt:message key="label.deliverytype"/>'
},
{ columnId:"c11",
	  columnName:'<fmt:message key="label.releasedate"/>'
}
];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
//    aI("text=<fmt:message key="label.modifyspecdetail"/>;url=javascript:getDetail();");
//    aI("text=<fmt:message key="label.modifyspec"/>;url=javascript:getSpec();");
}

drawMenus();
//closeAllchildren();
// -->
</script>
</head>
<body bgcolor="#ffffff"  onload="resultOnLoad();" onresize="resizeFrames()" onunload="try{opener.closeTransitWin();}catch(ex){}">

<tcmis:form action="/raytheoninactivechargeno.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>      
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
       <span id="updateResultLink">&nbsp;
			<a href="#" onclick="createXSL();"><fmt:message key="label.createexcelfile"/></a> 
       </span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="dataBean" style="height:400px;" style="display: none;"></div>

<c:if test="${dataColl != null}">
<script type="text/javascript">
<!--

<c:set var="dataCount" value='${0}'/>
var jsonMainData = new Array();
var jsonMainData = { 
rows:[    
<c:forEach items="${dataColl}">
	<c:if test="${dataCount > 0}">,</c:if>
	<fmt:formatDate var="inactiveDate" value="${dataColl[dataCount][0]}" pattern="${dateFormatPattern}"/>	
	<fmt:formatDate var="releaseDate" value="${dataColl[dataCount][12]}" pattern="${dateFormatPattern}"/>	
 	{ 
 	id:${dataCount +1},
 	data:[ 
 	'Y',
'${inactiveDate}',
'${dataColl[dataCount][1]}',
'${dataColl[dataCount][2]}',
'${dataColl[dataCount][3]}',
'${dataColl[dataCount][4]}',
'${dataColl[dataCount][5]}',
'${dataColl[dataCount][6]}',
'${dataColl[dataCount][7]}',
'${dataColl[dataCount][8]}',
'${dataColl[dataCount][9]}',
'${dataColl[dataCount][10]}',
'${dataColl[dataCount][11]}',
'${releaseDate}'
 	<%--
		'Y'
		<c:forEach var="obj" items="${dataColl[dataCount]}">
		,'${obj}'
		</c:forEach>
		--%>
 	]}
 	<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
//-->
</script>
</c:if>

<!-- If the collection is empty say no data found -->

<c:if test="${empty dataColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 


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
<input name="uAction" id="uAction" value="createExcel" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        function createXSL(){
      	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Raytheor_Charge_Number_Excel','650','600','yes');
      	    document.genericForm.target='_Raytheor_Charge_Number_Excel';
      	    window.setTimeout("document.genericForm.submit();",50);
      	}  
        //-->
    </script>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>