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

<!--Use this tag to get the correct CSS class
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
<script type="text/javascript" src="/js/hub/inventoryreceive.js"></script>

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

<script language="JavaScript" type="text/javascript">
<!--

var defaultValues = new Array(
		 "<fmt:message key="label.all"/>"
		,"<fmt:message key="label.all"/>"
		,"<fmt:message key="label.all"/>"
);
var emptyValues = new Array(
		 "<fmt:message key="label.all"/>"
		,"<fmt:message key="label.all"/>"
		,"<fmt:message key="label.all"/>"
);

althub = new Array(""
<c:forEach var="invgrp" items="${invgrpColl}" varStatus="status0">
	 , "${status0.current.branchPlant}"
</c:forEach>);
althubName = new Array("<fmt:message key="label.all"/>"
<c:forEach var="invgrp" items="${invgrpColl}" varStatus="status0">
	 , "${status0.current.hubName}"
</c:forEach>);


altinvGrp_ = new Array();
altinvGrpName_ = new Array();
<c:forEach var="invgrp" items="${invgrpColl}" varStatus="status0">
altinvGrp_["${status0.current.branchPlant}"] = new Array(""
	<c:forEach var="inventoryGroupBean" items="${status0.current.inventoryGroupCollection}" varStatus="status1">
		 , "${status1.current.inventoryGroup}"
	</c:forEach>);
altinvGrpName_["${status0.current.branchPlant}"] = new Array("<fmt:message key="label.all"/>"
	<c:forEach var="inventoryGroupBean" items="${status0.current.inventoryGroupCollection}" varStatus="status1">
		 , "${status1.current.inventoryGroupName}"
	</c:forEach>);
</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseinputsearchtext:"<fmt:message key="label.inputSearchText"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();setTopDropDown();">

<tcmis:form action="/inventoryreceiveresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

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
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td class="alignLeft" colspan="5">
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
          </select>
        </td>
  <td width="10%" class="optionTitleBoldLeft">
     <html:select property="searchField" styleId="searchField" styleClass="selectBox">
<!-- 
       <html:option value="radianPo" key="label.po"/>
 -->
        <html:option value="customerPo" key="label.customerpo"/>
 <!-- 
       <html:option value="itemId" key="label.itemid"/>
       <html:option value="itemDescription" key="label.itemdesc"/>
       <html:option value="lastSupplier" key="label.supplier"/>
  -->
     </html:select>
     </td><td>
     <html:select property="searchType" styleId="searchType" styleClass="selectBox">
        <html:option value="is"/>
<!--          <html:option value="contains"/> -->
      </html:select>
  </td>
  <td width="10%" class="optionTitleBoldLeft">
   	<html:text property="searchArgument" styleId="searchArgument" size="20" styleClass="inputBox" onkeypress="onKeyPress()"/>
  </td>
      </tr>
      <tr>
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td class="alignLeft" colspan="5">
          <input type="hidden" name="oldinventoryGroup" id="oldinventoryGroup" value="">
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
          </select>
          </td>
      </tr>
		<tr>
			<td width="5%" class="optionTitleBoldRight">
				<input 	name="submitSearch" id="submitSearch" type="button"
						value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"
				/>
			</td>
			<td>
			<input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createExcel()"/>
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
<input name="uAction" id="uAction" value="search" type="hidden"/>
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>    
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>