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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<tcmis:fontSizeCss />

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/allocationanalysis.js" language="JavaScript"></script>
<script src="/js/hub/picklistpicking.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">

var altHubId = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityId}"/>"
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>
</script>

<title>
<fmt:message key="picklistpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
         mrNumberInteger:"<fmt:message key="error.mrnumber.integer"/>",
       expireDaysInteger:"<fmt:message key="error.expiredays.integer"/>",
         picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
            pleaseSelect:"<fmt:message key="picklistpicking.pleaseselect"/>",
                   total:"<fmt:message key="label.total"/>"};

<c:if test="${picklistColl != null}" >
   <bean:size id="picklistSize" name="picklistColl"/>
   var TOTAL_ROWS = <c:out value="${picklistSize}"/>;
</c:if>
<c:if test="${empty picklistColl}">
   var TOTAL_ROWS=0;
</c:if>
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/picklistpicking.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="picklistpicking.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="20%">
         <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
        <td width="20%">
          <input name="prNumber" id="prNumber" type="text" class="inputBox" value='<c:out value="${param.prNumber}"/>' size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="20%">
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="inventoryGroupChanged();">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupFacilityObjBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                  <c:set var="facilityBeanCollection" value=''/>
                </c:when>
                <c:when test="${selectedInventoryGroup == currentInventoryGroup}" >
                  <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value='<c:out value="${currentInventoryGroup}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentInventoryGroup}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="20%">
          <c:set var="selectedSortBy" value="${param.sortBy}"/>
          <c:if test="${selectedSortBy == null || selectedSortBy == ''}">
            <c:set var="selectedSortBy" value='FACILITY_ID,APPLICATION'/>
          </c:if>
          <html:select property="sortBy" styleId="sortBy" styleClass="selectBox" value="${selectedSortBy}">
            <html:option value="facilityId,application" key="picklistpicking.facilityidworkarea"/>
            <html:option value="needDate" key="label.needdate"/>
            <html:option value="prNumber" key="label.mr"/>
            <html:option value="catPartNo" key="label.partnumber"/>
          </html:select>
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="20%">
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentFacilityId == selectedFacilityId}">
                  <option value='<c:out value="${currentFacilityId}"/>' selected><c:out value="${status.current.facilityId}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentFacilityId}"/>'><c:out value="${status.current.facilityId}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.expiresafter"/>:</td>
        <td width="20%">
          <c:set var="enteredDays" value='${param.expireDays}'/>
          <c:if test='${param.expireDays == null}'>
              <c:set var="enteredDays" value='30'/>
          </c:if>
          <input name="expireDays" id="expireDays" type="text" class="inputBox" value='<c:out value="${enteredDays}"/>' size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
           <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.search"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validatePickingForm();">
        </td>
        <td width="20%" class="optionTitleBoldRight">
          <input name="regeneratePicklist" id="regeneratePicklist" type="submit" class="inputBtns" value='<fmt:message key="picklistpicking.regeneratepicklist"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return genPicklist(messagesData.picklistConfirm);">
        </td>
        <td width="10%">
          &nbsp;
        </td>
        <td width="20%">
          &nbsp;
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${picklistColl != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <a href="#" onclick="showpickingpagelegend(); return false;"><fmt:message key="label.showlegend"/></a> 
   <c:if test="${! empty picklistColl}" >
      | <a href="#" onclick="genPicklist(messagesData.picklistConfirm);"><fmt:message key="picklistpicking.generatepicklist"/></a>
   </c:if>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pickBean" items="${picklistColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="2%"><fmt:message key="label.ok"/><br><input type="checkbox" name='okTitle<c:out value="${status.index}"/>' onclick="checkall(this,'genericForm','ok','okTitle');"></th>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="5%"><fmt:message key="label.workarea"/></th>
    <th width="5%"><fmt:message key="label.deliverypoint"/></th>
    <th width="5%"><fmt:message key="label.shipto"/></th>
    <th width="5%"><fmt:message key="label.requestor"/></th>
    <th width="5%"><fmt:message key="label.mrline"/></th>
    <th width="6%"><fmt:message key="label.releasedate"/></th>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="4%"><fmt:message key="label.type"/></th>
    <th width="15%"><fmt:message key="label.partdescription"/></th>
    <th width="5%"><fmt:message key="label.packaging"/></th>
    <th width="4%"><fmt:message key="label.quantity"/></th>
    <th width="5%"><fmt:message key="label.needed"/></th>
    <th width="3%"><fmt:message key="label.mr"/> <fmt:message key="label.notes"/></th>
    </tr>
    </c:if>

    <c:choose>
      <c:when test='${status.current.critical == "Y"}'>
       <c:set var="colorClass" value='red'/>
      </c:when>
      <c:when test='${status.current.critical == "S"}'>
       <c:set var="colorClass" value='pink'/>
      </c:when>
      <c:when test='${status.current.hazmatIdMissing == "MISSING"}'>
       <c:set var="colorClass" value='orange'/>
      </c:when>
      <c:when test="${status.index % 2 == 0}">
       <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
       <c:set var="colorClass" value="alt"/>
      </c:otherwise>     
    </c:choose>       

   <tr class='<c:out value="${colorClass}"/>' align="center">
     <td width="2%">
       <input name='picklistBean[<c:out value="${status.index}"/>].ok' id='ok<c:out value="${status.index}"/>' value='<c:out value="${status.index}"/>' type="checkbox" class="radioBtns" >
     </td>
     <td width="5%"><c:out value="${status.current.facilityId}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.application}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.deliveryPoint}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.shipToLocationId}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.requestor}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>&nbsp;</td>
     <td width="6%">        
        <fmt:formatDate var="fmtReleaseDate" value="${status.current.releaseDate}" pattern="MM/dd/yyyy hh:mm"/>
        <c:out value="${fmtReleaseDate}"/>&nbsp;        
     </td>
     <td width="5%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.deliveryType}"/>&nbsp;</td>
     <td width="15%"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.packaging}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.pickQty}"/>&nbsp;</td>
     <td width="5%">
        <c:out value="${status.current.needDatePrefix}"/>         
        <fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="MM/dd/yyyy"/>
        <c:out value="${fmtNeedDate}"/>&nbsp;        
     </td>
     <td width="3%">        
        <c:if test='${! empty status.current.mrNotes}'>
          <span id='spanMrNote<c:out value="${status.index}"/>' onclick='showNotes("MrNote<c:out value="${status.index}"/>");'>
          <p id='pgphMrNote<c:out value="${status.index}"/>'><i>+</i></p>
          <div id='divMrNote<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
            <c:out value="${status.current.mrNotes}"/>
          </div>
          </span>
        </c:if>
        &nbsp;          
     </td>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].prNumber' id='prNumber<c:out value="${status.index}"/>' value='<c:out value="${status.current.prNumber}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].lineItem' id='lineItem<c:out value="${status.index}"/>' value='<c:out value="${status.current.lineItem}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].companyId' id='companyId<c:out value="${status.index}"/>' value='<c:out value="${status.current.companyId}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].hub' id='hub<c:out value="${status.index}"/>' value='<c:out value="${status.current.hub}"/>'>
     <input type="hidden" name='picklistBean[<c:out value="${status.index}"/>].needDate' id='needDate<c:out value="${status.index}"/>' value='<c:out value="${fmtNeedDate}"/>'>
   </tr>
   </c:forEach>
   </c:forEach>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistColl}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input name="action" id="action" type="hidden" value="createExcel">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>
