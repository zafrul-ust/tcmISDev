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
<script src="/js/hub/reprintpicklist.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
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
<fmt:message key="label.picklistno"/>:<c:out value="${param.picklistId}"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
          daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
     pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
           itemMrInteger:"<fmt:message key="error.itemmr.integer"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>"};

var windowCloseOnEsc = true;
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/picklistreprintresults.do" onsubmit="return submitFrameOnlyOnce();">

<%--**TODO** check for permissions--%>
<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${! empty picklistPrintColl}" >
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

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

<c:if test="${picklistPrintColl != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="consolidationColorClass" value='green'/>
<c:set var="consolidationCount" value='${0}'/>
<c:set var="lastConsolidationNo" value=''/>
<c:set var="distribution" value='N'/>
<c:set var="headerRowSpan" value='1'/>
<c:if test="${fromCarrierPicking =='Y'}">
	<c:set var="headerRowSpan" value='2'/>
</c:if>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty picklistPrintColl}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
<c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pickBean" items="${picklistPrintColl}" varStatus="status">
    <c:if test="${status.current.distribution == 'Y'}"><c:set var="distribution" value='Y'/></c:if>
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="2%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.ok"/><input type="checkbox" name='okTitle<c:out value="${status.index}"/>' id='okTitle<c:out value="${status.index}"/>' onclick="checkall(this,'genericForm','ok','okTitle');"></th>
    <th width="3%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.facility"/></th>
   <c:choose>
   <c:when test="${fromPickingPicklist =='Y'}">
   </c:when>
   <c:otherwise>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.carrier"/>/<fmt:message key="label.trailer"/>/<fmt:message key="label.stop"/><br>(<fmt:message key="label.pickuptime"/>)</th>
    <th width="3%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.mode"/></th>
    <th width="3%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.consolidationno"/></th>
    <th width="3%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.packinggroupid"/></th>
    <th width="2%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.splittcn"/></th>
    <th width="2%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.transportationpriority"/></th>
    <th width="2%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.hazardous"/></th>
     <th width="2%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.oconus"/></th>
   </c:otherwise>
   </c:choose>
   <th width="12%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.shipto"/></th>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.bin"/></th>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.mrline"/></th>
    <th width="4%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.quantity"/></th>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.needed"/></th>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.partnumber"/></th>
    <th width="4%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.type"/></th>
    <th width="15%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.partdescription"/></th>
    <th width="5%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.packaging"/></th>
    <th width="3%" rowspan='<c:out value="${headerRowSpan}"/>'><fmt:message key="label.mr"/> <fmt:message key="label.notes"/></th>
    <c:if test="${fromCarrierPicking =='Y'}">
    	<th width="25%" colspan="2"><fmt:message key="label.boxlabel"/></th>
    </c:if>
    </tr>
    <c:if test="${fromCarrierPicking =='Y'}">
	    <tr>
	    	<th><fmt:message key="label.externalid"/></th>
	    	<th><fmt:message key="label.serialnumber"/></th>
	    </tr>
	</c:if>
    </c:if>

    <c:choose>
      <c:when test='${status.current.critical == "Y"}'>
       <c:set var="colorClass" value='red'/>
      </c:when>
      <c:when test='${status.current.critical == "S"}'>
       <c:set var="colorClass" value='pink'/>
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

   <tr class='<c:out value="${colorClass}"/>' align="center">
          <td width="2%">
       <input name='picklistBean[<c:out value="${status.index}"/>].ok' id='ok<c:out value="${status.index}"/>' value='<c:out value="${status.index}"/>' type="checkbox">
     </td>
 <td width="3%" class="${consolidationColorClass}"><c:out value="${status.current.facilityId}"/>&nbsp;</td>
 <c:choose>
   <c:when test="${fromPickingPicklist =='Y'}">
   </c:when>
   <c:otherwise>
  	<td width="5%" class="${consolidationColorClass}">
    <c:if test="${!empty status.current.carrierName}">
     ${status.current.carrierName}/${status.current.trailerNumber}/${status.current.stopNumber}
     <br>
       <fmt:formatDate var="fmtPickupTime" value="${status.current.pickupTime}" pattern="${dateFormatPattern}"/>
        (<c:out value="${fmtPickupTime}"/>)
    </c:if>
     </td>
     <td width="3%" class="${consolidationColorClass}"><c:out value="${status.current.transportationMode}"/>&nbsp;</td>
     <td width="3%" class="${consolidationColorClass}"><c:out value="${status.current.consolidationNumber}"/>&nbsp;
       <c:if test="${status.current.mrCount > 1}">
         <br>
        (<fmt:message key="label.multiorders"/> - ${status.current.mrCount})
       </c:if>
     </td>
     <td width="3%" class="${consolidationColorClass}"><c:out value="${status.current.packingGroupId}"/>&nbsp;</td>
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
   </c:otherwise>
</c:choose>
    <td width="5%">${status.current.shipToCity}, ${status.current.shipToStateAbbrev} ${status.current.shipToZip}</td>
     <td width="3%"><c:out value="${status.current.bin}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.pickQty}"/>&nbsp;</td>
     <td width="5%">
        <c:out value="${status.current.needDatePrefix}"/>
        <fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtNeedDate}"/>&nbsp;
     </td>
     <td width="5%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.deliveryType}"/>&nbsp;</td>
     <td width="15%" class="alignLeft"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
     <td width="5%" class="alignLeft"><c:out value="${status.current.packaging}"/>&nbsp;</td>
     <c:set var="notes" value='${status.current.mrNotes}'/>
    <c:choose>
      <c:when test="${empty notes || notes == ' '}" >
        <td width="5%" id="lineNotesTd${status.index}">&nbsp;</td>
      </c:when>
      <c:otherwise>
        <textarea name="mrNotes${status.index}" id="mrNotes${status.index}" style="display:none">
				<c:out value="${status.current.mrNotes}" escapeXml="true"/>
		  </textarea>
		  <td width="5%" id="lineNotesTd${status.index}" style="cursor:pointer;" onclick="showDeliveryComments2('${status.index}')">
        +
      </c:otherwise>
    </c:choose>

     <c:if test="${fromCarrierPicking =='Y'}">
	     <c:choose>
	     	<c:when test="${status.current.serialNumberTracked && status.current.boxLabels != null}">
	     		<td colspan="2">
		     		<table class="tableResults" id='<c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>-<c:out value="${status.current.bin}"/>boxLabels' border="0" cellpadding="0" cellspacing="0">
						<c:forEach var="boxLabelBean" items="${status.current.boxLabels}" varStatus="boxLabel">
							<tr class='<c:out value="${colorClass}"/>'>
								<td>
									<input type="hidden" name='<c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>-<c:out value="${status.current.bin}"/>boxLabelBean[<c:out value="${boxLabel.index}"/>].boxLabelId' id='boxLabelId<c:out value="${status.index}"/>_<c:out value="${boxLabel.index}"/>' value='<c:out value="${boxLabel.current.boxLabelId}"/>'>
		     						${boxLabel.current.boxLabelId}
		     					</td>
		     					<td>
		     						<input size="12" name='<c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>-<c:out value="${status.current.bin}"/>boxLabelBean[<c:out value="${boxLabel.index}"/>].serialNumber' id='serialNumber<c:out value="${status.index}"/>_<c:out value="${boxLabel.index}"/>' value='<c:out value="${boxLabel.current.serialNumber}"/>'>
		     					</td>
		     				</tr>    		
						</c:forEach>     		
					</table>
				</td>
	     	</c:when>
	     	<c:otherwise>
	     		<td width="18%" colspan="2" class="alignLeft"><fmt:message key="label.na"/></td>
	     	</c:otherwise>
	     </c:choose>
	 </c:if>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].prNumber' id='prNumber<c:out value="${status.index}"/>' value='<c:out value="${status.current.prNumber}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].lineItem' id='lineItem<c:out value="${status.index}"/>' value='<c:out value="${status.current.lineItem}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].bin' id='bin<c:out value="${status.index}"/>' value='<c:out value="${status.current.bin}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].trackSerialNumber' id='trackSerialNumber<c:out value="${status.index}"/>' value='<c:out value="${status.current.trackSerialNumber}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].facilityId' id='facilityId<c:out value="${status.index}"/>' value='<c:out value="${status.current.facilityId}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].packingGroupId' id='packingGroupId<c:out value="${status.index}"/>' value='<c:out value="${status.current.packingGroupId}"/>'>
    </tr>
       <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistPrintColl}" >

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
<input name="action" id="action" type="hidden" value="">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>
<input name="picklistId" id="picklistId" type="hidden" value="${param.picklistId}">
<input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">
<input type="hidden" name="fromPickingPicklist" id="fromPickingPicklist" value="${fromPickingPicklist}"/>
<input type="hidden" name="fromCarrierPicking" id="fromCarrierPicking" value="${fromCarrierPicking}"/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>	
<input type="hidden" name="distribution" id="distribution" value="${distribution}"/>	        
<input type="hidden" name="personnelId" id="personnelId" value='${sessionScope.personnelBean.personnelId}'/>
<input type="hidden" name="personnelLocale" id="personnelLocale" value='${sessionScope.personnelBean.locale}'/>	
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="labelType" id="labelType" value="UNITEXT"/>
<input name="printCarrierPickingLabels" id="printCarrierPickingLabels" type="hidden" value="Y"/>  
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>