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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/receivingreport.js"></script>

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
<fmt:message key="label.receivingreport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="hubInventoryGroupColl" value='${personnelBean.hubInventoryGroupOvBeanCollection}'/>
<bean:size id="hubSize" name="hubInventoryGroupColl"/>
var altHubId = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
			<c:when test="${status.index == 0}">
	        "<c:out value="${status.current.branchPlant}"/>"
			</c:when>
			<c:otherwise>
	       	,"<c:out value="${status.current.branchPlant}"/>"
			</c:otherwise>
		</c:choose>
</c:forEach>
);

var altHubName = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
			<c:when test="${status.index == 0}">
	        "<c:out value="${status.current.hubName}"/>"
			</c:when>
			<c:otherwise>
	       	,"<c:out value="${status.current.hubName}"/>"
			</c:otherwise>
		</c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
  <c:if test="${hubSize > 1}">
    altInventoryGroup["ALL"] = new Array("<fmt:message key="label.all"/>");
	 altInventoryGroupName["ALL"] = new Array("<fmt:message key="label.all"/>");
  </c:if>
<c:forEach var="hubInventoryGroupFacOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

	<bean:size id="inventoryGroupSize" name="inventoryGroupCollection"/>
  altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
		<c:choose>
			<c:when test="${status1.index == 0 && inventoryGroupSize > 1}">
				"ALL","<c:out value="${status1.current.inventoryGroup}"/>"
			</c:when>
			<c:otherwise>
				<c:choose>
		      	<c:when test="${status1.index == 0}">
		         	"<c:out value="${status1.current.inventoryGroup}"/>"
					</c:when>
					<c:otherwise>
						,"<c:out value="${status1.current.inventoryGroup}"/>"
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
		<c:choose>
			<c:when test="${status1.index == 0 && inventoryGroupSize > 1}">
				"<fmt:message key="label.all"/>","<c:out value="${status1.current.inventoryGroup}"/>"
			</c:when>
			<c:otherwise>
				<c:choose>
		      	<c:when test="${status1.index ==0}">
		         	"<c:out value="${status1.current.inventoryGroup}"/>"
					</c:when>
					<c:otherwise>
						,"<c:out value="${status1.current.inventoryGroup}"/>"
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
  </c:forEach>
  );
</c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
missingData:"<fmt:message key="label.missingsearchordate"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="mySearchOnload();">

<tcmis:form action="/receivingreportresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="650" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	 <%-- row 1 --%>
    <tr>
			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.hub"/>:&nbsp;
			</td>
			<td width="40%" class="optionTitleBoldLeft">
         	<select name="hub" id="hub" class="selectBox" onchange="hubChanged();">

          	</select>
			</td>

		   <td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.dorbegin"/>:&nbsp;
		   </td>
			<td width="40%" class="optionTitleBoldLeft">
		 		<input class="inputBox" name="beginDate" id="beginDate" type="text" value="" size="6" readonly="true" onClick="return getCalendar(document.getElementById('beginDate'),null,null,null,document.getElementById('endDate'));">
				<a href="javascript: void(0);" ID="linkbeginDate" class="datePopUpLink" onClick="return getCalendar(document.getElementById('beginDate'),null,null,null,document.getElementById('endDate'));"></a>
		  </td>



	 </tr>

	  <%-- row 2 --%>
	  <tr>
			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.inventory"/>:&nbsp;
			</td>
			<td width="40%" class="optionTitleBoldLeft">
				<select name="inventoryGroup" id="inventoryGroup" class="selectBox">

				 </select>
			</td>

			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.dorend"/>:&nbsp;
		   </td>
			<td width="40%" class="optionTitleBoldLeft">
		 		<input class="inputBox" name="endDate" id="endDate" type="text" value="" size="6" readonly="true" onClick="return getCalendar(document.getElementById('endDate'),null,null,document.getElementById('beginDate'));">
				<a href="javascript: void(0);" ID="linkendDate" class="datePopUpLink" onClick="return getCalendar(document.getElementById('endDate'),null,null,document.getElementById('beginDate'));"></a>
		  </td>

	  </tr>

	 <%-- row 3 --%>
	 <tr>
			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.searchby"/>:&nbsp;
			</td>
			 <td width="50%" class="optionTitleBoldLeft">
 			 	<html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
   				<html:option value="radian_po" key="label.haaspo"/>
          		<html:option value="customer_po_no" key="label.customerpo"/>
					<html:option value="release_no" key="label.releaseno"/>
					<html:option value="cat_part_no" key="label.partNno"/>
          		<html:option value="item_id" key="label.item"/>
		   	</html:select>

 				<html:select property="searchType" styleId="searchType" styleClass="selectBox">
   				<html:option value="LIKE" key="label.contains"/>
   				<html:option value="IS" key="label.is"/>
					<html:option value="STARTSWITH" key="label.startswith"/>
					<html:option value="ENDSWITH" key="label.endswith"/>
				</html:select>

        		<input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="10">
			 </td>

       <td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.sort"/>:&nbsp;
			</td>
			 <td width="40%" class="optionTitleBoldLeft">
 			 	<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
   				<html:option value="radian_po,po_line,inventory_group,customer_po_no,release_no,cat_part_no,item_id,date_of_receipt" key="label.haaspo"/>
          		<html:option value="customer_po_no,release_no,radian_po,po_line,inventory_group,cat_part_no,item_id,date_of_receipt" key="label.customerpo"/>
					<html:option value="release_no,customer_po_no,radian_po,po_line,inventory_group,cat_part_no,item_id,date_of_receipt" key="label.releaseno"/>
					<html:option value="cat_part_no,radian_po,po_line,inventory_group,customer_po_no,release_no,item_id,date_of_receipt" key="label.partNno"/>
          		<html:option value="item_id,radian_po,po_line,inventory_group,customer_po_no,release_no,cat_part_no,date_of_receipt" key="label.item"/>
		   	</html:select>
			 </td>

	 </tr>

	<%-- row 4 --%>
	<tr>
		<td width="15%" class="optionTitleBoldRight">
				&nbsp;
			</td>
			 <td width="50%" colspan="2" class="optionTitleBoldRight">
				 <fmt:message key="label.unitofmeasure"/>:&nbsp;

			 </td>

			 <td width="50%" class="optionTitleBoldLeft">
 			 	<html:select property="unitOfMessure" styleId="unitOfMessure" styleClass="selectBox">
   				<html:option value="uos" key="label.uos"/>
          		<html:option value="itemuom" key="label.itemuom"/>
		   	</html:select>
			 </td>

	 </tr>

	 <%-- buttons --%>
    <tr>
      <td width="10%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
      </td>
    </tr>

    </table>
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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="hubName" id="hubName" type="hidden"value="">
<input name="inventoryGroupName" id="inventoryGroupName" type="hidden"value="">
<input name="facilityName" id="facilityName" type="hidden"value="">
<input name="searchWhatDesc" id="searchWhatDesc" type="hidden"value="">
<input name="searchTypeDesc" id="searchTypeDesc" type="hidden"value="">
<input name="sortByDesc" id="sortByDesc" type="hidden"value="">
<input name="companyId" id="companyId" type="hidden"value="">
<input name="unitOfMessureDesc" id="unitOfMessureDesc" type="hidden"value="">	
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>