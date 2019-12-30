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
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/poshiptoresults.js"></script>

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
<fmt:message key="label.purchaseordershipto"/>
</title>

<script language="JavaScript" type="text/javascript">
<!-- 
//permission stuff here -->
showUpdateLinks = false;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
selectedRowMsg:"<fmt:message key="label.selectedshiptoid"/>"};

var config = [
  { columnId:"locationId",
    columnName:"<fmt:message key="label.shipto"/> <fmt:message key="label.id"/>",
    width:15
  },
  { columnId:"locationDesc",
    columnName:"<fmt:message key="label.shipto"/> <fmt:message key="label.description"/>",
    width:22	  
  },
  { columnId:"facilityId",
    columnName:"<fmt:message key="label.facilityid"/>",
    width:10
  },
  { columnId:"companyId",
    columnName:"<fmt:message key="label.company"/> <fmt:message key="label.id"/>",
    width:10
  },
  { columnId:"addressLine",
    columnName:"<fmt:message key="label.address"/>",
    width:35
  },
  { columnId:"addressLine1"
  },
  { columnId:"addressLine2"
  },
  { columnId:"addressLine3"
  },
  { columnId:"city"
  },
  { columnId:"stateAbbrev"
  },
  { columnId:"zip"
  },
  { columnId:"countryAbbrev"
  }
  ];


//-->
</script>
</head>

<body bgcolor="#ffffff" onload='myResultOnLoad();'>
<tcmis:form action="/poshiptoresults.do" onsubmit="return submitFrameOnlyOnce();">

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
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
</script>

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<div id="shipToLocationViewBean" style="width:100%;height:400px;display: none;"></div>
<!-- Search results start -->
<c:if test="${!empty shipToLocationViewBeanCollection}" >
<script type="text/javascript">
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
jsonMainData = {
rows:[
 <c:forEach var="bean" items="${shipToLocationViewBeanCollection}" varStatus="status"> 
   <tcmis:jsReplace var="locdesc" value='${bean.locationDesc}' processMultiLines="true"/>
   <tcmis:jsReplace var="addr1" value='${bean.addressLine1}' processMultiLines="true"/>
   <tcmis:jsReplace var="addr2" value='${bean.addressLine2}' processMultiLines="true"/>
   <tcmis:jsReplace var="addr3" value='${bean.addressLine3}' processMultiLines="true"/>
 
 <c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:[
  '${bean.locationId}',
  '${locdesc}',
  '${bean.facilityId}',
<c:if test="${status.current.companyId == 'Radian' }">
  '<fmt:message key="label.haastcm"/>',
</c:if>
<c:if test="${status.current.companyId != 'Radian'}">
  '${status.current.companyId}',
</c:if>
  '${addr1}${addr2}${addr3}',
  '${addr1}','${addr2}','${addr3}',
  '<tcmis:jsReplace value="${status.current.city}"/>','${status.current.stateAbbrev}','${status.current.zip}','${status.current.countryAbbrev}'
   ]} 
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</script>
</c:if>

<c:if test="${empty shipToLocationViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden" />
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>
