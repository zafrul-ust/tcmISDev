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
<tcmis:fontSizeCss />
<%@ include file="/common/opshubig.jsp" %>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- Add any other stylesheets you need for the page here -->


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/searchiframeresize.js" language="JavaScript"></script>

<script src="/js/supply/buyorders.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
defaults.hub.callback = igchanged;

var altFacilityId = new Array();
var altFacilityName = new Array();
<%--  <c:if test="${hubSize > 1}">--%>
    altFacilityId["ALL"] = new Array("ALL");
	altFacilityName["ALL"] = new Array("<fmt:message key="label.all"/>");
  <%--</c:if>--%>
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

		<bean:size id="facilitySize" name="facilityBeanCollection"/>
		altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
			<c:choose>
				<c:when test="${status2.index == 0}">
					"ALL","<c:out value="${status2.current.facilityId}"/>"
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${status2.index == 0}">
							"<c:out value="${status2.current.facilityId}"/>"
						</c:when>
						<c:otherwise>
							,"<c:out value="${status2.current.facilityId}"/>"
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array(
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        <c:choose>
				<c:when test="${status2.index == 0}">
					"<fmt:message key="label.all"/>","<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${status2.index ==0}">
							"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
						</c:when>
						<c:otherwise>
							,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

// -->
</script>
<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>


<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
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
<fmt:message key="buyorders.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
mr:"<fmt:message key="label.mr"/>",
po:"<fmt:message key="label.po"/>",
pr:"<fmt:message key="label.pr"/>",
itemIdInteger:"<fmt:message key="error.item.integer"/>",
mrNumberInteger:"<fmt:message key="error.mrnumber.integer"/>",
prNumberInteger:"<fmt:message key="error.prnumber.integer"/>",
missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
radianPoInteger:"<fmt:message key="error.po.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="searchOnload();setOps();igchanged();">

<tcmis:form action="/buyordersresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

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
        <td width="100" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
        <td width="200" class="optionTitleBoldLeft">
          <select name="opsEntityId" id="opsEntityId" class="selectBox">
	      </select>
        </td>
        <td width="60" class="optionTitleBoldRight" rowspan="2"><fmt:message key="label.company"/>:</td>
        <td width="340" rowspan="2">         
        <html:select property="companyIdArray" styleClass="selectBox" styleId="companyIdArray" size="4" multiple="true">
          <html:option value="ALL" key="label.all" />
          <html:options collection="companyBeanCollection" property="companyId" labelProperty="companyName"/>
        </html:select>
       </td>
        <td width="100" class="optionTitleBoldRight" rowspan="2"><fmt:message key="label.status"/>:</td>
        <td width="200" rowspan="2">         
        <html:select property="statusArray" styleClass="selectBox" styleId="statusArray" multiple="true" size="4">
          <html:options collection="vvBuyOrderStatusBeanCollection" property="status"/>
        </html:select>
       </td>
<%--
        <td width="209" class="optionTitle" rowspan="3">
          <input name="boForUnconfirmedPo" id="boForUnconfirmedPo" type="checkbox" class="radioBtns" value="YES" onclick="boForUnconfirmedPoChanged()">
          <fmt:message key="buyorders.label.onlyunconfirmed"/>
<br>
          <input name="boWithNoPo" id="boWithNoPo" type="checkbox" class="radioBtns" value="YES" onclick="boWithNoPoChanged()">
          <fmt:message key="buyorders.label.notassigned"/>
<br>
          <input name="boForConfirmedPo" id="boForConfirmedPo" type="checkbox" class="radioBtns" value="YES" onclick="boForConfirmedPoChanged()">
          <fmt:message key="buyorders.label.onlyconfirmed"/>
<br>
          <input name="confirmedButNoAssociation" id="confirmedButNoAssociation" type="checkbox" class="radioBtns" value="YES" onclick="confirmedButNoAssociationChanged()">
          <fmt:message key="buyorders.label.associationnotconfirmed"/>
        </td>
--%>
      </tr>
      <tr>
        <td width="100" class="optionTitleBoldRight" nowrap><fmt:message key="label.hub"/>:</td>
        <td width="200" class="optionTitleBoldLeft">
         	<select name="branchPlant" id="hub"  class="selectBox">
	        </select>
        </td>
      </tr>

      <tr>
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:</td>
        <td>
          <select name="inventoryGroup" id="inventoryGroup"  onchange="igchanged()" class="selectBox">
	      </select>
        </td>
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.buyer"/>:</td>
        <td>         
        <html:select property="buyerId" styleClass="selectBox" styleId="buyerId" value="${personnelBean.personnelId}">
          <html:option value="0" key="label.allbuyers"/>
          <html:option value="" key="label.nobuyerassigned"/>
          <html:options collection="buyerBeanCollection" property="personnelId" labelProperty="lastName"/>
        </html:select>
       </td>
<%--
        <td class="optionTitleBoldRight"><fmt:message key="label.category"/>:</td>
        <td>         
        <html:select property="category" styleClass="selectBox" styleId="category">
          <html:options collection="vvCategoryBeanCollection" property="categoryId" labelProperty="categoryDesc"/>
        </html:select>
       </td>
--%>
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.supplypath"/>:</td>
        <td>         
        <html:select property="supplyPath" styleClass="selectBox" styleId="supplyPath">
          <html:option value="ALL" key="label.all"/>
          <html:option value="Dbuy" key="label.dbuy"/>
          <html:option value="Purchaser" key="label.purchasing"/>
			 <html:option value="Xbuy" key="label.xbuy"/> 
			 <html:option value="Wbuy" key="label.wbuy"/>
             <html:option value="Ibuy" key="label.ibuy"/>
        </html:select>
       </td>

      </tr>

      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td>
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">

          </select>
        </td>
        <td class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
        <td nowrap>         
        <html:select property="searchWhat" styleClass="selectBox" styleId="searchWhat">
          <html:option value="RAYTHEON_PO" key="label.customerpo"/>
          <html:option value="ITEM_ID" key="label.itemid"/>
          <html:option value="LAST_SUPPLIER" key="label.lastsupplier"/>
          <html:option value="MFG_ID" key="label.manufacturer"/>
          <html:option value="MR_NUMBER" key="label.mr"/>
          <html:option value="PART_ID" key="label.partnumber"/>
          <html:option value="RADIAN_PO" key="label.po"/>
          <html:option value="PR_NUMBER" key="label.pr"/>
        </html:select>
        <html:select property="searchType" styleClass="selectBox" styleId="searchType">
          <html:option value="IS" key="label.is"/>
          <html:option value="CONTAINS" key="label.contain"/>
          <html:option value="START_WITH" key="label.startswith"/>
          <html:option value="END_WITH" key="label.endswith"/>
        </html:select>
<input name="searchText" id="searchText" type="text" class="inputBox">
       </td>
        <td class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td>         
        <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
          <html:options collection="vvBuypageSortBeanCollection" property="sortId" labelProperty="sortDesc"/>
        </html:select>
       </td>

      </tr>
<%--
      <tr>
        <td class="optionTitle">
&nbsp;
        </td>
        <td class="optionTitle">
          <input name="checkbox" id="checkbox" type="checkbox" class="radioBtns" value="checkbox">
          <fmt:message key="buyorders.label.onlyunconfirmed"/>
        </td>
        <td class="optionTitle">
          <input name="checkbox" id="checkbox" type="checkbox" class="radioBtns" value="checkbox">
          <fmt:message key="buyorders.label.notassigned"/>
        </td>
        <td class="optionTitle">
          <input name="checkbox" id="checkbox" type="checkbox" class="radioBtns" value="checkbox">
          <fmt:message key="buyorders.label.onlyconfirmed"/>
        </td>
        <td class="optionTitle">
          <input name="checkbox" id="checkbox" type="checkbox" class="radioBtns" value="checkbox">
          <fmt:message key="buyorders.label.associationnotconfirmed"/>
        </td>
        <td class="optionTitle">
&nbsp;
        </td>
      </tr>
--%>
<tr>
<td class="optionTitleBoldLeft" colspan="6">
          <input name="boForUnconfirmedPo" id="boForUnconfirmedPo" type="checkbox" class="radioBtns" value="YES" onclick="boForUnconfirmedPoChanged()">
          <fmt:message key="buyorders.label.onlyunconfirmed"/>
&nbsp;&nbsp;&nbsp;
          <input name="boWithNoPo" id="boWithNoPo" type="checkbox" class="radioBtns" value="YES" onclick="boWithNoPoChanged()">
          <fmt:message key="buyorders.label.notassigned"/>
&nbsp;&nbsp;&nbsp;
          <input name="boForConfirmedPo" id="boForConfirmedPo" type="checkbox" class="radioBtns" value="YES" onclick="boForConfirmedPoChanged()">
          <fmt:message key="buyorders.label.onlyconfirmed"/>
&nbsp;&nbsp;&nbsp;
          <input name="confirmedButNoAssociation" id="confirmedButNoAssociation" type="checkbox" class="radioBtns" value="YES" onclick="confirmedButNoAssociationChanged()">
          <fmt:message key="buyorders.label.associationnotconfirmed"/>
&nbsp;&nbsp;&nbsp;
          <input name="excludeMxItems" id="excludeMxItems" type="checkbox" class="radioBtns" checked><%-- default to checked --%>
          <fmt:message key="buyorders.label.hidemxitems"/>
</td>
</tr>
      <tr>
      <td colspan="6">
		<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
		<input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
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
<!-- Build this section only if there is an error message to display -->
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
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input type="hidden" name="inventoryGroupName" id="inventoryGroupName" value=""/>
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="companyName" id="companyName" value=""/>
<input type="hidden" name="buyerName" id="buyerName" value=""/>
<input type="hidden" name="searchWhatDesc" id="searchWhatDesc" value=""/>
<input type="hidden" name="searchTypeDesc" id="searchTypeDesc" value=""/>
<input type="hidden" name="supplyPathDesc" id="supplyPathDesc" value=""/>
<input type="hidden" name="statusDesc" id="statusDesc" value=""/>
<input type="hidden" name="sortByDesc" id="sortByDesc" value=""/>
<input name="maxData" id="maxData" type="hidden" value="2000"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

