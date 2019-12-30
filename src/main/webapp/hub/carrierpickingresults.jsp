<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script src="/js/hub/carrierpicking.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="carrierpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
         mrNumberInteger:"<fmt:message key="error.mrnumber.integer"/>",
       expireDaysInteger:"<fmt:message key="error.expiredays.integer"/>",
         picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
            pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>"};
// -->
</script>
</head>


<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/carrierpickingresults.do" onsubmit="return submitFrameOnlyOnce();">

<%--**TODO** check for permissions--%>
<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="pickingPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Picking" >
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
</tcmis:inventoryGroupPermission>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

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
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${picklistColl != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="consolidationColorClass" value='green'/>
<c:set var="consolidationCount" value='${0}'/>
<c:set var="lastConsolidationNo" value=''/>
<c:set var="showChkAllBox" value='N'/>
 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty picklistColl}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pickBean" items="${picklistColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="2%"><fmt:message key="label.ok"/><br/><input type="checkbox" name='okTitle<c:out value="${status.index}"/>' onclick="checkall(this,'genericForm','ok','okTitle');"></th>
    <th width="3%"><fmt:message key="label.facility"/></th>
    <th width="5%"><fmt:message key="label.carrier"/>/<fmt:message key="label.trailer"/>/<fmt:message key="label.stop"/><br>(<fmt:message key="label.pickuptime"/>)</th>
    <th width="3%"><fmt:message key="label.mode"/></th>
    <th width="3%"><fmt:message key="label.consolidationno"/></th>      
    <th width="12%"><fmt:message key="label.shipto"/></th>
    <th width="2%"><fmt:message key="label.splittcn"/></th>
<%--     <th width="5%"><fmt:message key="label.ultimatedodaac"/></th>
    <th width="6%"><fmt:message key="label.facilityid"/></th>  --%>
    <th width="2%"><fmt:message key="label.transportationpriority"/></th>
    <th width="2%"><fmt:message key="label.hazardous"/></th>
    <th width="2%"><fmt:message key="label.oconus"/></th>
    <th width="10%"><fmt:message key="label.bin"/></th>
    <th width="5%"><fmt:message key="label.mrline"/></th>
    <th width="4%"><fmt:message key="label.quantity"/></th>
    <th width="5%"><fmt:message key="label.needed"/></th>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="5%"><fmt:message key="label.itemid"/></th>
    <th width="4%"><fmt:message key="label.type"/></th>
    <th width="15%"><fmt:message key="label.partdescription"/></th>
    <th width="10%"><fmt:message key="label.packaging"/></th>
    <th width="6%"><fmt:message key="label.releasedate"/></th>
    <th width="6%"><fmt:message key="label.dot"/></th>
    <th width="3%"><fmt:message key="label.mr"/> <fmt:message key="label.notes"/></th>
    </tr>
    </c:if>

<c:set var="readonly" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Picking" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="readonly" value='Y'/>
</tcmis:inventoryGroupPermission>

    <c:choose>
      <c:when test='${status.current.critical == "Y"}'>
       <c:set var="colorClass" value='red'/>
      </c:when>
      <c:when test='${empty status.current.unitGrossWeightLb }'>
       <c:set var="colorClass" value='error'/>
      </c:when>
      <c:when test='${status.current.critical == "S"}'>
       <c:set var="colorClass" value='pink'/>
      </c:when>
      <c:when test='${status.current.critical == "L"}'>
       <c:set var="colorClass" value='pink'/>
      </c:when>
      <c:when test='${status.current.hazmatIdMissing == "MISSING" || status.current.dot == "Not Defined"}'>
       <c:set var="colorClass" value='orange'/>
      </c:when>
      
      <c:when test="${status.index % 2 == 0}">
       <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
       <c:set var="colorClass" value="alt"/>
      </c:otherwise>     
    </c:choose>       

   <c:if test="${status.index == 0}">
    <c:set var="lastConsolidationNo" value='${status.current.consolidationNumber}'/>
	 </c:if>

   <c:choose>
    <c:when test="${status.current.consolidationNumber == lastConsolidationNo}" >
    </c:when>
    <c:otherwise>
     <c:set var="lastConsolidationNo" value='${status.current.consolidationNumber}'/>
     <c:set var="consolidationCount" value='${consolidationCount+1}'/>
       <c:choose>
        <c:when test="${consolidationCount % 2 == 0}" >
         <c:set var="consolidationColorClass" value='green'/>
        </c:when>
        <c:otherwise>
         <c:set var="consolidationColorClass" value='lightgray'/>
        </c:otherwise>
       </c:choose>
    </c:otherwise>
   </c:choose>

   <tr class='<c:out value="${colorClass}"/>'>
     <td width="2%">
       <c:if test="${readonly == 'Y' && !empty status.current.unitGrossWeightLb && !empty status.current.consolidationNumber && status.current.hazmatIdMissing != 'MISSING' && status.current.dot != 'Not Defined' && !empty status.current.carrierCode}">
       <input name='picklistBean[<c:out value="${status.index}"/>].ok' id='ok<c:out value="${status.index}"/>' value='true' type="checkbox">
       <c:set var="showChkAllBox" value='Y'/>
       </c:if>
       <c:if test="${status.current.hazmatIdMissing == 'MISSING'}">
        <fmt:message key="label.missing"/> <fmt:message key="label.hazard"/>
       </c:if>
       <c:if test="${status.current.dot == 'Not Defined'}">
        <fmt:message key="label.missing"/> <fmt:message key="label.dot"/>
       </c:if>
       <c:if test="${empty status.current.carrierCode}">
        <fmt:message key="label.missing"/> <fmt:message key="label.carrier"/>
       </c:if>
       <c:if test="${empty status.current.unitGrossWeightLb}">
        <fmt:message key="label.missing"/> <fmt:message key="label.grossweightlbs"/>
       </c:if>
     </td>
     <td width="5%">${status.current.facilityName}</td>
     <td width="5%" class="${consolidationColorClass}">
     <c:if test="${!empty status.current.carrierName}">
     ${status.current.carrierName}/${status.current.trailerNumber}/${status.current.stopNumber}
     <br>
       <fmt:formatDate var="fmtPickupTime" value="${status.current.pickupTime}" pattern="${dateFormatPattern}"/>
        (<c:out value="${fmtPickupTime}"/>)
     </c:if>
     </td>
     <td width="3%" class="${consolidationColorClass}"><c:out value="${status.current.transportationMode}"/>&nbsp;</td>
     <td width="3%" class="${consolidationColorClass}">${status.current.consolidationNumber}
       <c:if test="${status.current.mrCount > 1}">
         <br>
        (<fmt:message key="label.multiorders"/> - ${status.current.mrCount})
       </c:if>
     
     </td>
     <td width="5%">${status.current.shipToLocationDesc}<br/>${status.current.shipToCity}, ${status.current.shipToStateAbbrev} ${status.current.shipToZip}</td>     
<%--      <td width="5%"><c:out value="${status.current.shipToDodaac}"/>&nbsp;
      <c:if test="${!empty status.current.oconusFlag && status.current.oconusFlag=='Y'}" >
       <b>(OCONUS)</b>
      </c:if>
     </td> --%>
     <td width="2%" align="center">
       <c:choose>
         <c:when test="${status.current.splitTcn == 0}">
           N
         </c:when>
         <c:when test="${status.current.splitTcn == 1}">
           Y
         </c:when>
       </c:choose>
     </td>  
     <td width="2%" align="center"><c:out value="${status.current.transportationPriority}"/>&nbsp;</td>
     <td width="2%" align="center"><c:out value="${status.current.hazardous}"/>&nbsp;</td>
     <td width="2%" align="center"><c:out value="${status.current.oconusFlag}"/>&nbsp;</td>
     <td width="10%" nowrap><c:out value="${status.current.bin}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>&nbsp;</td>
     <td width="4%" align="right"><c:out value="${status.current.pickQty}"/>&nbsp;</td>
     <td width="5%">
        <c:out value="${status.current.needDatePrefix}"/>
        <fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtNeedDate}"/>&nbsp;
     </td>

     <td width="5%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.itemId}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.deliveryType}"/>&nbsp;</td>
     <td width="15%"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
     <td width="10%"><c:out value="${status.current.packaging}"/>&nbsp;</td>
     <td width="6%">
        <fmt:formatDate var="fmtReleaseDate" value="${status.current.releaseDate}" pattern="EEE MMM dd yyyy HH:mm a zzz"/>
        <c:out value="${fmtReleaseDate}"/>&nbsp;
     </td>
     <td width="10%"><c:out value="${status.current.dot}"/>&nbsp;</td>
    <c:set var="notes" value='${status.current.mrNotes}'/>
    <c:choose>
      <c:when test="${empty notes || notes == ' '}" >
        <td width="3%" id="lineNotesTd${status.index}">&nbsp;</td>
      </c:when>
      <c:otherwise>
        <textarea name="mrNotes${status.index}" id="mrNotes${status.index}" style="display:none">
				<c:out value="${status.current.mrNotes}" escapeXml="true"/>
		  </textarea>
        <td width="3%" id="lineNotesTd${status.index}" style="cursor:pointer;" onclick="showDeliveryComments2('${status.index}')">
        +
      </c:otherwise>
    </c:choose>  
<%--      <td width="3%">        
        <c:if test='${! empty status.current.mrNotes}'>
          <span id='spanMrNote<c:out value="${status.index}"/>' onclick='showNotes("MrNote<c:out value="${status.index}"/>");'>
          <p id='pgphMrNote<c:out value="${status.index}"/>'><i>+</i></p>
          <div id='divMrNote<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
            <c:out value="${status.current.mrNotes}"/>
          </div>
          </span>
        </c:if>
        &nbsp;          
     </td>  --%>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].prNumber' id='prNumber<c:out value="${status.index}"/>' value='<c:out value="${status.current.prNumber}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].lineItem' id='lineItem<c:out value="${status.index}"/>' value='<c:out value="${status.current.lineItem}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].companyId' id='companyId<c:out value="${status.index}"/>' value='<c:out value="${status.current.companyId}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].hub' id='hub<c:out value="${status.index}"/>' value='<c:out value="${status.current.hub}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].needDate' id='needDate<c:out value="${status.index}"/>' value='<c:out value="${fmtNeedDate}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].packingGroupId' id='packingGroupId<c:out value="${status.index}"/>' value='<c:out value="${status.current.packingGroupId}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].inventoryGroup' id='inventoryGroup<c:out value="${status.index}"/>' value='<c:out value="${status.current.inventoryGroup}"/>'>
   </tr>
       <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistColl}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td> 
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="createExcel">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="carrierCode" id="carrierCode" type="hidden" value="${param.carrierCode}">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
<input name="transportationMode" id="transportationMode" type="hidden" value="${param.transportationMode}">
<input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
<input name="hub" id="hub" type="hidden" value="${param.hub}">
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">
<input name="picklistId" id="picklistId" type="hidden" value="${picklistId}">
<input name="packingGroupId" id="packingGroupId" type="hidden" value="0">
<input name="fromPickingPicklist" id="fromPickingPicklist" type="hidden" value="N"/>
<%--  <input name="picklistId" id="picklistId" type="hidden" value="191494">
   --%>
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<c:if test="${showChkAllBox == 'Y'}">
<script type="text/javascript">
    <!--
   	 showCheckAllBox = true;
    //-->
</script>
</c:if>

</tcmis:form>
</body>
</html:html>