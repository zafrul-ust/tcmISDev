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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss/>
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/supplier/usgovlabeling.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.usgovlabeling"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var altSupplierId = new Array(
	""
<c:forEach var="SupplierLocPicklistOvBean" items="${supplierLocPicklistOvBeanCollection}" varStatus="status">
    ,"${status.current.supplier}"
</c:forEach>
);

var altSupplierName = new Array(
	"<fmt:message key="label.all"/>"
<c:forEach var="SupplierLocPicklistOvBean" items="${supplierLocPicklistOvBeanCollection}" varStatus="status">
    ,"${status.current.supplierName}"
</c:forEach>
);

var altSupplierLocationId = new Array();
var altSupplierLocationName = new Array();
<c:forEach var="SupplierLocPicklistOvBean" items="${supplierLocPicklistOvBeanCollection}" varStatus="status">

altSupplierLocationId["${status.current.supplier}"] = new Array(
	""
 <c:forEach var="LocationPicklistObjBean" items="${status.current.supplierLocPicklists}" varStatus="status1">
    ,"${status1.current.locationId}"
 </c:forEach>
  );

altSupplierLocationName["${status.current.supplier}"] = new Array(
	"<fmt:message key="label.all"/>"
  <c:forEach var="LocationPicklistObjBean" items="${status.current.supplierLocPicklists}" varStatus="status1">
    ,"${status1.current.locationShortName}"
  </c:forEach>
  );
</c:forEach>

var altPackingId = new Array();
var altPackingName = new Array();

altPackingId[""] = new Array();
altPackingId[""][""] = new Array("");
altPackingName[""] = new Array();
altPackingName[""][""] = new Array("<fmt:message key="label.all"/>");

<c:forEach var="SupplierLocPicklistOvBean" items="${supplierLocPicklistOvBeanCollection}" varStatus="status">
altPackingId["${status.current.supplier}"] = new Array();
altPackingId["${status.current.supplier}"][""] = new Array("");

<c:forEach var="locationPicklistObjBean" items="${status.current.supplierLocPicklists}" varStatus="status1">
 	altPackingId["${status.current.supplier}"]["${status1.current.locationId}"]
 	 = new Array(
 	 	""
		<c:forEach var="picklistObjBean" items="${status1.current.picklists}" varStatus="status2">
    			,"${status2.current.picklistId}"
		</c:forEach>
 	 );
</c:forEach>

altPackingName["${status.current.supplier}"] = new Array();
altPackingName["${status.current.supplier}"][""] = new Array("<fmt:message key="label.all"/>");

 <c:forEach var="locationPicklistObjBean" items="${status.current.supplierLocPicklists}" varStatus="status1">
 	altPackingName["${status.current.supplier}"]["${status1.current.locationId}"]
 	 = new Array(
		"<fmt:message key="label.all"/>"
		<c:forEach var="picklistObjBean" items="${status1.current.picklists}" varStatus="status2">
		   	   	<fmt:formatDate var="picklistPrintDate" value="${status2.current.picklistPrintDate}" pattern="EEE MMM dd yyyy HH:mm a zzz" />
    			,"${picklistPrintDate}"
		</c:forEach>
 	 );
 </c:forEach>
</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
required:"<fmt:message key="label.supplierandlocationrequired"/>",errorInteger:"<fmt:message key="label.positiveinteger"/>",
none:"<fmt:message key="label.none"/>",noopenpackingids:"<fmt:message key="label.noopenpackingids"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="searchOnLoad()">

<tcmis:form action="/usgovlabelingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.supplier"/>:</td>
        <td>
  		<input type="hidden" name="oldsupplier" id="oldsupplier" value="${param.supplier}"/>
      <select name="supplier" id="supplier" class="selectBox" OnChange="supplierChanged()">
       <c:set var="selectedSupplier" value='${param.supplier}'/>
       <c:forEach var="SupplierLocPicklistOvBean" items="${supplierLocPicklistOvBeanCollection}" varStatus="status">
        <c:set var="currentSupplier" value='${status.current.supplier}'/>
          <c:choose>
            <c:when test="${empty selectedSupplier}" >
              <c:set var="selectedSupplier" value='${currentSupplier}'/>
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocPicklists}'/>
            </c:when>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocPicklists}'/>
            </c:when>
          </c:choose>

           <c:choose>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <option value="${currentSupplier}" selected><c:out value="${status.current.supplierName}" escapeXml="false"/></option>
            </c:when>
            <c:otherwise>
              <option value="${currentSupplier}" ><c:out value="${status.current.supplierName}" escapeXml="false"/></option>
            </c:otherwise>
          </c:choose>
         </c:forEach>
        </select>        
       </td>
        <td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:
 		</td>
       <td width="10%" class="optionTitleLeft">
 			<select name="searchField" id="searchField" class="selectBox">
        <option  value="supplierSalesOrderNo" selected="selected"><fmt:message key="label.deliveryticket"/></option>
        <option  value="radianPo"><fmt:message key="label.haaspo"/></option>
				<option  value="itemDesc"><fmt:message key="label.itemdesc"/></option>
				<option  value="itemId"><fmt:message key="label.itemid"/></option>
				<option  value="prNumber"><fmt:message key="label.mr"/></option>
				<option  value="shipToCityCommaState"><fmt:message key="label.shittocitycommastate"/></option>
				<option  value="shipToZipcode"><fmt:message key="label.shiptozip"/></option>

       </select>
 		</td>
		<td width="10%" class="optionTitleLeft">
 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
				<html:option value="is" ><fmt:message key="label.is"/></html:option>
				<html:option value="contains"><fmt:message key="label.contains"/></html:option>
				<html:option value="startsWith" key="label.startswith"/>
        <html:option value="endsWith" key="label.endswith"/>
 			</html:select>
 		</td>
 		<td width="3%" class="optionTitleBoldRight">
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20" onkeypress="return onKeyPress()">
 		</td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.supplierlocation"/>:</td>
        <td colspan="5">
  		<input type="hidden" name="oldshipFromLocationId" id="oldshipFromLocationId" value="${param.shipFromLocationId}"/>
 		  <%--<select class="selectBox" name="shipFromLocationId" id="shipFromLocationId" onChange="supplierLocationChanged()">--%>
       <c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
       <select name="suppLocationIdArray" id="suppLocationIdArray" class="selectBox" multiple size="4">
            <c:set var="selectedAll" value=''/>
            <c:if test="${empty suppLocationIdArray}" >
             <c:set var="selectedAll" value='selected'/>
            </c:if>
            <option value="" ${selectedAll}><fmt:message key="label.all"/></option>
            <c:forEach var="locationPicklistObjBean" items="${initSuppLocBeanCollection}" varStatus="status">
              <c:set var="currentLocation" value='${status.current.locationId}'/>
              <c:choose>
                <c:when test="${empty suppLocationIdArray}" >
                  <option value="<c:out value="${currentLocation}"/>"><c:out value="${status.current.locationShortName}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <c:set var="selectedFlag" value=''/>
                  <c:forEach items="${suppLocationIdArray}" varStatus="status1">
                    <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
                    <c:if test="${currentLocation == selectedLocation}">
                      <c:set var="selectedFlag" value='selected'/>
                    </c:if>
                  </c:forEach>
                  <option value="<c:out value="${currentLocation}"/>" <c:out value="${selectedFlag}"/>><c:out value="${status.current.locationShortName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
       </td>
      </tr>
<%--      <tr>
        <td class="optionTitleBoldRight" width="25%"><fmt:message key="label.packingid"/>:</td>
        <td>
  		<input type="hidden" name="oldpicklistId" id="oldpicklistId" value="${param.picklistId}"/>
 		<select class="selectBox" name="picklistId" id="picklistId" class="selectBox">
        </select>
       </td>
      </tr>--%>
      <tr>
      <td colSpan="6" class="optionTitleLeft">
<input name="searchb" id="searchb" type="button" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="search()"/>
<%--
	<input name="CreateExcelFile" id="CreateExcelFile" type="submit" value="<fmt:message key="label.createexcelfile"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
--%>
</td>
      </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="_action" id="_action" value="${_action}"/>
<c:set var="submitSearch"><tcmis:jsReplace value="${param.submitSearch}"/></c:set>
<input type="hidden" name="shpSelpicklistId" id="shpSelpicklistId" value="<tcmis:jsReplace value="${param.picklistId}"/>"/> 
<input type="hidden" name="oldpicklistId" id="oldpicklistId" value="<tcmis:jsReplace value="${param.picklistId}"/> "/>
<input type="hidden" name="picklistId" id="picklistId" value="<tcmis:jsReplace value="${param.picklistId}"/> "/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>