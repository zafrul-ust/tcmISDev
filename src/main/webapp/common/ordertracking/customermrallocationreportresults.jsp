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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%@ include file="/common/locale.jsp" %>
    
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>	
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script src="/js/common/ordertracking/customermrallocation.js" language="JavaScript"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
<fmt:message key="mrallocationreport.label.title"/>
</title>

	
<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("showAll")){
 top="offset=2"
 style = contextStyle;
 margin=3
 //aI("text=<fmt:message key="label.print"/>;url=javascript:window.print();");	
 aI("text=test;");
}

with(milonic=new menuname("showReceiptDocument")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

drawMenus();

<%-- initialize for later use --%>
var documentId = new Array();
var documentName = new Array();

// -->
</script>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
displaycerts:"<fmt:message key="label.displaycerts"/>",
displaymsds:"<fmt:message key="label.displaymsds"/>",
displaypackinglist:"<fmt:message key="label.displaypackinglist"/>",
displayinvoice:"<fmt:message key="label.displayinvoice"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()" onmouseup="toggleContextMenuToNormal()">

<tcmis:form action="/mrallocationreportresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = false;
 //-->
 </script>

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

<c:if test="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>
<c:set var="recordCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="rowCount" value='${0}'/>
    <c:set var="dataCount" value='${0}'/>
    <bean:size collection="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" id="resultSize"/>
    <c:forEach var="pkgOrderTrackWebPrOrderTrackDetailBean" items="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" varStatus="status">
      <c:set var="dataCount" value='${dataCount+1}'/>
      <c:if test="${status.index % 10 == 0}">
        <tr>
          <th width="2%"><fmt:message key="mrallocationreport.label.mrline"/></th>
          <th width="5%"><fmt:message key="label.customerpo"/></th>
          <th width="5%"><fmt:message key="mrallocationreport.label.useapprover"/></th>
          <th width="5%"><fmt:message key="mrallocationreport.label.releasedate"/></th>
          <th width="5%"><fmt:message key="label.workarea"/></th>
          <th width="5%"><fmt:message key="label.partnumber"/></th>
          <th width="5%"><fmt:message key="label.type"/></th>
           <th width="7%"><fmt:message key="label.partdesc"/></th>    
          <th width="7%"><fmt:message key="label.packaging"/></th>               
          <th width="5%"><fmt:message key="label.deliverytype"/></th>
          <th width="5%"><fmt:message key="label.needed"/></th>
          <th width="5%"><fmt:message key="label.orderedqty"/></th>
          <th width="5%"><fmt:message key="label.allocatedqty"/></th>
          <th width="5%"><fmt:message key="label.status"/></th>
          <th width="5%"><fmt:message key="mrallocationreport.label.ref"/></th>
          <th width="5%"><fmt:message key="label.projecteddeliverydate"/></th>
          <th width="5%"><fmt:message key="mrallocationreport.label.lot"/></th>
          <th width="5%"><fmt:message key="label.expdate"/></th>
          <th width="7%"><fmt:message key="label.notes"/></th>
        </tr>
      </c:if>

      <c:set var="needDateCollection"  value='${status.current.needDateCollection}'/>
      <bean:size id="listSize" name="needDateCollection"/>
      <c:set var="mainRowSpan" value='${status.current.rowSpan}' />

      <c:choose>
        <c:when test="${dataCount % 2 == 0}" >
          <c:set var="colorClass" value=''/>
        </c:when>
        <c:otherwise>
         <c:set var="colorClass" value='alt'/>
        </c:otherwise>
      </c:choose>

      <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" >
        <input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
        <input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
        <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.lineItem}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.poNumber}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.useApproverName}"/></td>
        <fmt:formatDate var="formattedReleaseDate" value="${status.current.releaseDate}" pattern="${dateTimeFormatPattern}"/>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${formattedReleaseDate}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.applicationDesc}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.facPartNo}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.itemType}"/></td>
        <td width="7%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.partDescription}"/></td>
        <td width="7%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.packaging}"/></td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" onmouseup="catchdetailsevent('<c:out value="${recordCount}"/>','<c:out value="${status.index}"/>')"><c:out value="${status.current.deliveryType}"/></td>

        <%-- START of MIDDLE FOR-EACH --%>
        <c:forEach var="pkgOrderTrackWebNeededBean" items="${needDateCollection}" varStatus="status1">
          <c:if test="${status1.index > 0 && listSize > 1 }">
            <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>" >
          </c:if>

          <c:set var="allocQtyCollection"  value='${status1.current.allocationCollection}'/>
          <bean:size id="allocationSize" name="allocQtyCollection"/>
          <input type="hidden" name="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   	  id="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   	  value="<c:out value="${allocationSize}"/>" >
		<fmt:formatDate var="fmtRequiredDatetime" value="${status1.current.requiredDatetime}" pattern="${dateFormatPattern}"/>
          <td width="5%" rowspan="<c:out value="${allocationSize}"/>" onmouseup="catchdetailsevent('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${fmtRequiredDatetime}"/></td>
          <td width="5%" rowspan="<c:out value="${allocationSize}"/>" onmouseup="catchdetailsevent('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${status1.current.orderQuantity}"/></td>

          <%-- START of INNERMOST FOR-EACH --%>
          <c:forEach var="PkgOrderTrackWebAllocationBean" items="${allocQtyCollection}" varStatus="status2">
            <c:if test="${status2.index > 0 && allocationSize > 1 }">
              <tr class="<c:out value="${colorClass}"/>" id="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>" >
            </c:if>

            <td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${status2.current.allocatedQuantity}"/></td>
            <td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${status2.current.status}"/></td>
            <td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${status2.current.ref}"/>

				<c:if test="${!empty status2.current.carrierName}">
					<br><fmt:message key="label.carrier"/>=<c:out value="${status2.current.carrierName}"/>
				</c:if>
				<c:if test="${!empty status2.current.trackingNumber}">
					<br><fmt:message key="label.trackingN"/>=<c:out value="${status2.current.trackingNumber}"/>
				</c:if>

				<c:choose>
					<c:when test="${!empty status2.current.receiptDocumentColl}">
						<input type="hidden" id="showDisplayCerts<c:out value="${recordCount}"/>" value="Y">
					</c:when>
					<c:otherwise>
						<input type="hidden" id="showDisplayCerts<c:out value="${recordCount}"/>" value="N">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${!empty status2.current.msdsColl}">
						<input type="hidden" id="showDisplayMSDS<c:out value="${recordCount}"/>" value="Y">
					</c:when>
					<c:otherwise>
						<input type="hidden" id="showDisplayMSDS<c:out value="${recordCount}"/>" value="N">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${!empty status2.current.invoiceColl}">
						<input type="hidden" id="showDisplayInvoice<c:out value="${recordCount}"/>" value="Y">
					</c:when>
					<c:otherwise>
						<input type="hidden" id="showDisplayInvoice<c:out value="${recordCount}"/>" value="N">
					</c:otherwise>
				</c:choose>
			
<%-- 					<c:if test="${!empty status2.current.receiptDocument}">
						<br><img src="/images/buttons/document.gif" onmouseover=style.cursor="hand" onclick="showDocumentImageMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"/>
						<script language="JavaScript" type="text/javascript">
						<!--
							documentId[<c:out value="${recordCount}"/>] = new Array(
							 <c:forEach var="receiptDocumentBean" items="${status2.current.receiptDocumentColl}" varStatus="status3">
								<c:choose>
								  <c:when test="${status3.index == 0}">
									 "<c:out value="${status3.current.documentUrl}"/>"
								  </c:when>
								  <c:otherwise>
									 ,"<c:out value="${status3.current.documentUrl}"/>"
								  </c:otherwise>
								</c:choose>
							 </c:forEach>
							 );

						  documentName[<c:out value="${recordCount}"/>] = new Array(
							 <c:forEach var="receiptDocumentBean" items="${status2.current.receiptDocumentColl}" varStatus="status3">
								<fmt:formatDate var="formattedDocumentDate" value="${status3.current.documentDate}" pattern="${dateFormatPattern}"/>
								<c:choose>
								  <c:when test="${status3.index == 0}">
									 "<c:out value="${status3.current.documentTypeDesc} ${status3.current.documentName} ${formattedDocumentDate}" escapeXml="false"/>"
								  </c:when>
								  <c:otherwise>
									 ,"<c:out value="${status3.current.documentTypeDesc} ${status3.current.documentName} ${formattedDocumentDate}" escapeXml="false"/>"
								  </c:otherwise>
								</c:choose>
							 </c:forEach>
							 );
						// -->
						</script>
					</c:if>  --%>
				</td>
				<input type="hidden" name="receiptDocument<c:out value="${status2.index}"/>" id="receiptDocument<c:out value="${recordCount}"/>" value="<c:out value="${status2.current.receiptDocument}"/>">
				<input type="hidden" name="ref<c:out value="${status2.index}"/>" id="ref<c:out value="${recordCount}"/>" value="<c:out value="${status2.current.ref}"/>">
				<input type="hidden" name="refType<c:out value="${status2.index}"/>" id="refType<c:out value="${recordCount}"/>" value="<c:out value="${status2.current.refType}"/>">
				<input type="hidden" name="refNumber<c:out value="${status2.index}"/>" id="refNumber<c:out value="${recordCount}"/>" value="<c:out value="${status2.current.refNumber}"/>">
                <input type="hidden" name="lineItem<c:out value="${status2.index}"/>" id="lineItem<c:out value="${recordCount}"/>" value="<c:out value="${status.current.lineItem}"/>">
                <input type="hidden" id="shipmentId<c:out value="${recordCount}"/>" value="<c:out value="${status2.current.shipmentId}"/>">
				<fmt:formatDate var="fmtAllocationReferenceDate" value="${status2.current.allocationReferenceDate}" pattern="${dateFormatPattern}"/>
				<td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${fmtAllocationReferenceDate}"/></td>
            <td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${status2.current.mfgLot}"/></td>
            <fmt:formatDate var="formattedexpireDate" value="${status2.current.expireDate}" pattern="${dateFormatPattern}"/>
				<c:if test="${formattedexpireDate == '01-Jan-3000'}">
					<c:set var="formattedexpireDate"><fmt:message key="label.indefinite"/>
					</c:set>
				</c:if>
				<td width="5%" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')"><c:out value="${formattedexpireDate}"/></td>

            <c:set var="notes" value='${status2.current.notes}'/>
            <c:choose>
                <c:when test="${empty notes || notes == ' '}" >
                  <td width="5%" id="lineNotesTd<c:out value="${status.index}"/>" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')">
                </c:when>
              <c:otherwise>
					  <tcmis:jsReplace var="notes" value="${status2.current.notes}" processMultiLines="true" />
					  <td width="5%" id="lineNotesTd<c:out value="${status.index}"/>" onmouseover=style.cursor="hand" onclick="parent.showMrLineNotes('<c:out value="${notes}"/>')"
						  onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>','<c:out value="${recordCount}"/>')">
					  +
              </c:otherwise>
            </c:choose>
            </td>


            <c:if test="${status2.index > 0 || allocationSize ==1 }">
            </tr>
            </c:if>
				<c:set var="recordCount" value='${recordCount+1}'/>
			 </c:forEach>	<%-- END of INNERMOST FOR-EACH --%>

          <c:if test="${status1.index > 0 || listSize ==1 }">
          </tr>
          </c:if>
        </c:forEach>	<%-- END of MIDDLE FOR-EACH --%>

    <!-- end of hidden columns -->
      <c:set var="rowCount" value='${rowCount+1}'/>
    </c:forEach>
    </table>
</c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" >

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
<input name="totalLines" id="totalLines" value="<c:out value="${rowCount}"/>" type="hidden">
<input name="selectedDocumentId" id="selectedDocumentId" value="" type="hidden">
<input name="selectedRefType" id="selectedRefType" value="" type="hidden">
<input name="selectedRefNumber" id="selectedRefNumber" value="" type="hidden">
<input name="selectedLineItem" id="selectedLineItem" value="" type="hidden">
<input name="selectedShipmentId" id="selectedShipmentId" value="" type="hidden">
<input name="prNumber" id="prNumber" value="${param.prNumber}" type="hidden">
<input name="fromCustomerOrdertracking" id="fromCustomerOrdertracking" type="hidden" value="<c:out value="${param.fromCustomerOrdertracking}"/>">

<input name="minHeight" id="minHeight" type="hidden" value="300">	
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>