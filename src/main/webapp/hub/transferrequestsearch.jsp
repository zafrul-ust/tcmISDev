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

<%--This is used to define the date format that will be used on the page.--%>
<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
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
<script type="text/javascript" src="/js/hub/transferrequestdual.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.branchPlant}"
   </c:when>
   <c:otherwise>
    "${status.current.branchPlant}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroupId = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupBeanCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroupId["${currentHub}"] = new Array(""
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"${status1.current.inventoryGroup}"
   </c:when>
   <c:otherwise>
        ,"${status1.current.inventoryGroup}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altInventoryGroupName["${currentHub}"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="InventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.inventoryGroupName}"/>'
   </c:when>
   <c:otherwise>
        ,'<tcmis:jsReplace value="${status1.current.inventoryGroupName}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

var toAltHubId = new Array(
<c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.branchPlant}"
   </c:when>
   <c:otherwise>
    "${status.current.branchPlant}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var toAltInventoryGroupId = new Array();
var toAltInventoryGroupName = new Array();
<c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupBeanCollection" value='${status.current.inventoryGroupCollection}'/>

  toAltInventoryGroupId["${currentHub}"] = new Array(
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"${status1.current.inventoryGroup}"
   </c:when>
   <c:otherwise>
        "${status1.current.inventoryGroup}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  toAltInventoryGroupName["${currentHub}"] = new Array(
  <c:forEach var="InventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.inventoryGroupName}"/>'
   </c:when>
   <c:otherwise>
        '<tcmis:jsReplace value="${status1.current.inventoryGroupName}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>
// -->
</script>

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
<fmt:message key="transferrequest.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", transferQuantityInteger:"<fmt:message key="error.quantity.integer"/>",
noTransfer:"<fmt:message key="error.transfer.none"/>", itemIdInteger:"<fmt:message key="error.item.integer"/>",
dateFormat:"<fmt:message key="error.date.invalid"/>",
all:"<fmt:message key="label.all"/>"};

function setOpsEntityName() {
$('opsEntityName').value = $('opsEntityId').options[$('opsEntityId').selectedIndex].text;	
}

defaults.ops.nodefault=true;
defaults.hub.nodefault=true;
defaults.ops.callback = setOpsEntityName;

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();setOps();">

<tcmis:form action="/transferrequestresults.do" onsubmit="return submitFrameOnlyOnce();setOps();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
      <td/>
      <td/>
</tr>
      <tr>
        <td width="15%" class="optionTitleBoldRight"><fmt:message key="label.from"/> <fmt:message key="label.hub"/>:</td>
        <td width="10%">
          <!-- Use this for dropdowns you build with collections from the database -->
<c:set var="selectedHub" value='${param.branchPlant}'/>
          <select name="branchPlant" id="hub" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
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
                <option value="${currentHub}" selected>${status.current.hubName}</option>
              </c:when>
              <c:otherwise>
                <option value="${currentHub}">${status.current.hubName}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
        <td width="15%" class="optionTitleBoldRight" nowrap="nowrap">
          <!-- Use this for dropdowns that are hardcoded -->
          <fmt:message key="label.search"/>
          <html:select property="itemOrDescription" styleClass="selectBox" styleId="itemOrDescription">
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="description" key="label.description"/>
        </html:select>
       </td>
        <td class="optionTitleBoldLeft" nowrap>
          <!-- Use this for dropdowns that are hardcoded -->
          <html:select property="criterion" styleClass="selectBox" styleId="criterion">
          <html:option value="is" key="label.is"/>
          <html:option value="contain" key="label.contain"/>
          <html:option value="startsWith" key="label.startswith"/>
          <html:option value="endsWith" key="label.endswith"/>
        </html:select>
<input name="criteria" id="criteria" type="text" class="inputBox" value="${param.criteria}"/>
       </td>
      </tr>
      <tr>
        <td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.from"/> <fmt:message key="label.inventorygroup"/>:</td>
        <td width="10%">
          <!-- Use this for dropdowns you build with collections from the database -->
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value="${currentInventoryGroup}" selected>${status.current.inventoryGroupName}</option>
                </c:when>
                <c:otherwise>
                  <option value="${currentInventoryGroup}">${status.current.inventoryGroupName}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
       </td>

        <td width="15%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td >
          <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
            <html:option value="itemId" key="label.itemid"/>
            <html:option value="itemDesc" key="label.description"/>
          </html:select>
        </td>
      </tr>

      <tr>
      <td colspan="4"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();"></td>
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
<input name="opsEntityName" id="opsEntityName" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>