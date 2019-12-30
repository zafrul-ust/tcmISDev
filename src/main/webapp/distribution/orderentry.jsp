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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/orderentry.js"></script>
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

<title>
<fmt:message key="orderentry.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("openscratchpad")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.open"/>;url=javascript:openScratchPadsOnRightClick();");
      
}

drawMenus();

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
lookup:"<fmt:message key="label.lookup"/>",  
scratchpadlimitexceeded:"<fmt:message key="label.scratchpadlimitexceeded"/>",
orderentrylookup:"<fmt:message key="orderentrylookup.title"/>",
errors:"<fmt:message key="label.errors"/>"};

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:"hch",
  sorting:"haasHch",
  align:"center",
  submit:true
},
{ columnId:"prNumber",
  columnName:'<fmt:message key="label.order"/>',
  width:7,
  submit:true
},
{ columnId:"orderType",
  columnName:'<fmt:message key="label.order.type"/>',
  width:7
  
},
{ columnId:"personnelName",
  columnName:'<fmt:message key="label.enteredby"/>'
},
{ columnId:"requestDate",
  columnName:'<fmt:message key="label.date.created"/>'
},
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customername"/>',
  width:22,
  tooltip:"Y"
},
{ columnId:"carrierAccountNuumber",
  columnName:'<fmt:message key="label.customerid"/>',
  sorting:"int",
  width:6
},
{ columnId:"shipToAddressLine1",
  columnName:'<fmt:message key="label.shipto"/>',
  width:18,
  tooltip:"Y"
},
{ columnId:"opsEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:10
},
{ columnId:"creditStatus",
  columnName:'<fmt:message key="label.creditstatus"/>'
},
{ columnId:"requestorName",
  columnName:'<fmt:message key="label.requestor"/>'
},
{ columnId:"requestorPhone",
  columnName:'<fmt:message key="label.phone"/>'
},
{ columnId:"requestorEmail",
  columnName:'<fmt:message key="label.email"/>',
  width:11
},
{ columnId:"requestorFax",
  columnName:'<fmt:message key="label.fax"/>'
}
];
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();"  onunload="closeAllchildren();" onresize="resizeFrames()">

<tcmis:form action="/orderentry.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
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
       <span id="updateResultLink"><a href="#" onclick="getScratchPadId();"><fmt:message key="label.new"/></a></span>&nbsp;   
       <c:choose>
		<c:when test="${empty salesQuoteViewBeanCollection}">	         
		     |&nbsp;<span id="updateResultLink"><a href="#" onclick="openLookup();"><fmt:message key="label.lookup"/></a></span>&nbsp;
		     |&nbsp;<span id="updateResultLink"><a href="#" onclick="refreshWin();"><fmt:message key="label.refresh"/></a></span>  
	  </c:when>
	  <c:otherwise>		
       |&nbsp;<span id="updateResultLink"><a href="#" onclick="openScratchPads();"><fmt:message key="label.open"/></a></span>&nbsp;     
       |&nbsp;<span id="updateResultLink"><a href="#" onclick="deleteRecords();"><fmt:message key="label.delete"/></a></span>&nbsp; 
        |&nbsp;<span id="updateResultLink"><a href="#" onclick="openLookup();"><fmt:message key="label.lookup"/></a></span>&nbsp;
        |&nbsp;<span id="updateResultLink"><a href="#" onclick="refreshWin();"><fmt:message key="label.refresh"/></a></span>    
	 </c:otherwise>
	</c:choose>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="salesQuoteViewBean" style="height:400px;" style="display: none;"></div>

<c:if test="${salesQuoteViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty salesQuoteViewBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = { 
rows:[
      
<c:forEach var="salesQuoteViewBean" items="${salesQuoteViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:set var="readonly" value='Y'/> 
<c:set var="showUpdateLink" value='Y'/> 

<tcmis:jsReplace var="shipToAddressLine1" value="${status.current.shipToAddressLine1}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine2" value="${status.current.shipToAddressLine2}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine3" value="${status.current.shipToAddressLine3}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine4" value="${status.current.shipToAddressLine4}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine5" value="${status.current.shipToAddressLine5}"  processMultiLines="true"/>

<fmt:formatDate var="fmtRequestDate" value="${status.current.requestDate}" pattern="${dateFormatPattern}"/>
<tcmis:jsReplace var="customerName" value="${status.current.customerName}"  processMultiLines="true"/>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[ '${readonly}',  
 '','${status.current.prNumber}','${status.current.orderType}','<tcmis:jsReplace value="${status.current.submittedByName}"/>','${fmtRequestDate}',
 '${customerName}','${status.current.customerId}',
 '${shipToAddressLine1} ${shipToAddressLine2} ${shipToAddressLine3} ${shipToAddressLine4}  ${shipToAddressLine5}',
 '<tcmis:jsReplace value="${status.current.opsEntityName}"/>',
 '${status.current.creditStatus}','<tcmis:jsReplace value="${status.current.requestorName}"/>',
 '${status.current.requestorPhone}','<tcmis:jsReplace value="${status.current.requestorEmail}"/>',
 '${status.current.requestorFax}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty salesQuoteViewBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="label.noscratchpadfound"/>
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
<input name="action" id="action" value="" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="status" id="status" type="hidden" value="${param.status}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>