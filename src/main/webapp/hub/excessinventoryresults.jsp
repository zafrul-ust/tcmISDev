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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/excessinventoryresults.js"></script>

<!-- These are for the Grid-->

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
<fmt:message key="label.excessinventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
     searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
        itemInteger:"<fmt:message key="error.item.integer"/>"};


<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="inpurchasing"><fmt:message key="label.inpurchasing"/></c:set> 
var config = [
{ columnId:"opsEntityId",
  columnName:'<fmt:message key="label.operatingentity"/>',
  type:"ro",
  width:12
},
{ columnId:"hubName",
  columnName:'<fmt:message key="label.hub"/>',
  type:"ro",
  width:10
},
{ columnId:"hub"
},
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  type:"ro",
  width:12
},
{ columnId:"itemId",
  columnName:'<tcmis:jsReplace value="${itemid}"/>',
  type:"ro",
  width:5
},
{ columnId:"partNo",
  columnName:'<fmt:message key="label.partnum"/>',
  type:"ro",
  width:15,
  tooltip:"Y"
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.description"/>',
  type:"ro",
  width:20,
  tooltip:"Y"
},

{ columnId:"onHand",
  columnName:'<fmt:message key="label.onhand"/>',
  type:"ro",
  align:"right",
  width:5
},
{ columnId:"onOrder",
  columnName:'<fmt:message key="label.onorder"/>',
  type:"ro",
  align:"right",
  width:5
},
{ columnId:"inPurchasing",
  columnName:'<tcmis:jsReplace value="${inpurchasing}"/>',
  type:"ro",
  align:"right",
  width:6
},
{ columnId:"currencyId",
  columnName:'<fmt:message key="label.currencyid"/>',
  type:"ro",
  width:6
},
{ columnId:"averageCost",
  columnName:'<fmt:message key="label.averagecost"/>',
  type:"ro",
  align:"right",
  width:6
},
{ columnId:"expireDate",
  columnName:'<fmt:message key="label.expiredate"/>',
  type:"ro",
  width:8
}
];

with(milonic=new menuname("showInventoryDetail")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.showinventorydetail"/>;url=javascript:viewInventoryDetail();");
	}

	drawMenus();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(_gridConfig);">

<tcmis:form action="/excessinventorymain.do" onsubmit="return submitFrameOnlyOnce();">

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
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">

<div class="backGroundContent">    
<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="beanData" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${!empty excessInventoryViewBeanCollection}" >
  <script type="text/javascript">
  /*This is to keep track of whether to show any update links.
    If the user has any update permisions for any row then we show update links.*/
  <c:set var="showUpdateLink" value='N'/>
  <c:set var="dataCount" value='${0}'/>
  /*Storing the data to be displayed in a JSON object array.*/
  var jsonData = new Array();
  var jsonData = {
  rows:[
  <c:forEach var="excessInventoryViewBean" items="${excessInventoryViewBeanCollection}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    <fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
    <fmt:formatDate var="fmtDelivDate" value="${status.current.deliveryDate}" pattern="${dateFormatPattern}"/>
    <c:set var="expireDateSortable" value="${status.current.expireDate.time}"/>
    <c:set var="delivDateSortable" value="${status.current.deliveryDate.time}"/>
    <fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
    <c:if test="${expireYear == '3000'}">
	<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
    </c:if>

    <tcmis:jsReplace var="partShortName" value="${status.current.partShortName}" processMultiLines="true" />
    <tcmis:jsReplace var="specs" value="${status.current.specs}" processMultiLines="true" />
                    
  <c:choose>
   <c:when test="${status.current.averageCost eq null}">
     <c:set var="currencyId" value='' />
   </c:when>
   <c:otherwise>
     <c:set var="currencyId" value='${status.current.currencyId}' />
   </c:otherwise>
  </c:choose>
  <fmt:formatNumber var="averageCost" value="${status.current.averageCost}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
  
   /*The row ID needs to start with 1 per their design.*/
   {      id:${status.index +1},
        data:['${status.current.operatingEntityName}',
              '<tcmis:jsReplace value="${status.current.hubName}" />','${status.current.hub}',
              '<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
              '${status.current.itemId}',
              '<tcmis:jsReplace value="${status.current.partNo}" />',
              '${partShortName}',
              '${status.current.onHand}',
              '${status.current.onOrder}',
              '${status.current.inPurchasing}',
              '${currencyId}',
              '${averageCost}',
              '${fmtExpireDate}']}
    <c:set var="dataCount" value='${dataCount+1}'/>
  </c:forEach>
  ]};
  </script>
</c:if>       
<!-- If the collection is empty say no data found -->
<c:if test="${empty excessInventoryViewBeanCollection}" >
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
   <tr>
    <td width="100%">
      <fmt:message key="main.nodatafound"/>
    </td>
   </tr>
  </table>
</c:if>

<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
  <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
  <tcmis:saveRequestParameter/>
  <%--Need to store search input options here. This is used to re-do the original search upon updates etc.--%>    
  <input name="action" id="action" value="" type="hidden">
  <input name="inventoryGroup" id="dodaacType" type="hidden" value="${param.inventoryGroup} ">
  <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
  <input name="buyerId" id="buyerId" type="hidden" value="${param.buyerId}">
  <!--This sets the start time for time elapesed.-->
  <input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
  <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
  <input name="minHeight" id="minHeight" type="hidden" value="100">
  
  <!-- Popup Calendar input options for hcal column Type in the grid-->
  <!--
  <input type="hidden" name="blockBefore_columnId" id="blockBefore_columnId" value=""/>
  <input type="hidden" name="blockAfter_columnId" id="blockAfter_columnId" value=""/>
  <input type="hidden" name="blockBeforeExclude_columnId" id="blockBeforeExclude_columnId" value=""/>
  <input type="hidden" name="blockAfterExclude_columnId" id="blockAfterExclude_columnId" value=""/>
  <input type="hidden" name="inDefinite_columnId" id="inDefinite_columnId" value=""/>
  -->

  <%--This is the minimum height of the result section you want to display--%>
  <input name="minHeight" id="minHeight" type="hidden" value="0">
</div>
<!-- Hidden elements end -->

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

</div> <!-- backGroundContent -->

</div> <!-- close of interface -->

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
</body>
</html:html>
