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
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
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
<script src="/js/hub/cabinetbinlabel.js" language="JavaScript"></script>

<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="cabinetbinlabel.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/label.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>

<div class="contentArea" id="mainPage">
<!-- Search Option Begins -->

<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${cabinetBinLabelViewBeanCollection != null}" >
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<!-- Search results start -->
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
<input name="size" id="size" type="radio" class="radioBtns" value="small" checked>
<fmt:message key="cabinetlabel.label.smallradio"/>
<input name="size" id="size" type="radio" class="radioBtns" value="large">
<fmt:message key="cabinetlabel.label.largeradio"/>
|&nbsp;<a href="javascript:generateBinLabels()"><fmt:message key="cabinetbinlabel.label.generatebinlabel"/></a>
    </div>
    <div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
<bean:size collection="${cabinetBinLabelViewBeanCollection}" id="resultSize"/>
    <c:forEach var="resultBean" items="${cabinetBinLabelViewBeanCollection}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index == 0}">
        <tr>
          <th width="5%"><fmt:message key="label.print"/>
<br><input name="allCheck" id="allCheck" type="checkbox" class="" value="all" onclick="checkAll(<c:out value="${resultSize}"/>);">
          </th>
          <th width="10%"><fmt:message key="label.cabinetid"/></th>
          <th width="10%"><fmt:message key="label.binid"/></th>
          <th width="20%"><fmt:message key="label.binname"/></th>
          <th width="10%"><fmt:message key="label.itemid"/></th>
          <th width="15%"><fmt:message key="label.partno"/></th>
          <th width="20%"><fmt:message key="label.partdesc"/></th>
        </tr>
</c:if>


<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="rowClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="rowClass" value='class=alt'/>
  </c:otherwise>
</c:choose>
        <tr <c:out value="${rowClass}"/>>
<td width="5%"><input name="myCheckboxArray<c:out value="${status.index}"/>" id="checkbox<c:out value="${status.index}"/>" type="checkbox" class="" value="<c:out value="${status.index}"/>"></td>
          <td width="10%"><tcmis:padding value="${status.current.cabinetId}" length="8" paddingCharacter="0" paddingSide="left"/>&nbsp;</td>
          <td width="10%"><input type="hidden" name="binIdArray" value="<c:out value="${status.current.binId}"/>"><tcmis:padding value="${status.current.binId}" length="8" paddingCharacter="0" paddingSide="left"/>&nbsp;</td>
          <td width="20%"><c:out value="${status.current.binName}"/>&nbsp;</td>
          <td width="10%"><c:out value="${status.current.itemId}"/>&nbsp;</td>
          <td width="15%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
          <td width="20%"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
        </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty cabinetBinLabelViewBeanCollection}" >
    <table width="100%">
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
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

</tcmis:form>
</body>
</html:html>
