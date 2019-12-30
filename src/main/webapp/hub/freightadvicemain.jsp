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
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/freightadvicemain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
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
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
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


// -->
</script>

<title>
<fmt:message key="freightadvice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
inputSearchText:"<fmt:message key="label.inputSearchText"/>",
mrNumber:"<fmt:message key="error.mrnumber.integer"/>",
packingInt:"<fmt:message key="label.packingInt"/>",
errors:"<fmt:message key="label.errors"/>"};

var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contain"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
	);

// -->
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"    onload="loadLayoutWin('','freightAdvice');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/freightadviceresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt=""
                                                        width="15" height="10" class="corner_filter"
                                                        style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                        <!-- Insert all the search option within this div -->
                        <table width="700" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="30%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="70%">
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <option value="<c:out value="${status.current.branchPlant}"/>"><c:out value="${status.current.hubName}"/></option>
          </c:forEach>
          </select>
       </td>
      </tr>
      <tr>
        <td width="30%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="70%">
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option> 
<%--
          <c:forEach var="inventoryGroupBean" items="${personnelBean.hubInventoryGroupOvBeanCollection[0].inventoryGroupCollection}" varStatus="status">
            <option value="<c:out value="${status.current.inventoryGroup}"/>"><c:out value="${status.current.inventoryGroup}"/></option>
          </c:forEach> 
--%>
          </select>
       </td>
	    <td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:
		</td>
		<td width="10%" class="optionTitleLeft">
			<select name="searchField" id="searchField" class="selectBox" onchange="changeSearchTypeOptions(this.value);">
						<option value="packingGroupId" selected="selected"><fmt:message key="label.packinggroupid" /></option>
						<option value="carrierName"><fmt:message key="label.carrier" /></option>
						<option value="carrierTrackingNumber"><fmt:message key="label.trackingnumber" /></option>
						<option value="consolidationNumber"><fmt:message key="label.consolidationno" /></option>
            <option value="milstripCode"><fmt:message key="label.milstrip" /></option>
            <option  value="prNumber"><fmt:message key="label.mr"/></option>
      </select>
		</td>
		<td width="10%" class="optionTitleLeft">
 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
				<html:option value="is" ><fmt:message key="label.is"/></html:option>
				<html:option value="startsWith" key="label.startswith"/>
 			</html:select>
 		</td>
 		<td width="3%" class="optionTitleBoldRight">
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20" onkeypress="return onKeyPress()">
 		</td>
		</tr>
      <tr>
        <td width="30%" class="optionTitleBoldRight"><fmt:message key="label.projectedshipdate"/>:</td>
        <td width="70%">
          <input name="shipDate" id="shipDate" readonly type="text" onClick="return getCalendar(document.genericForm.shipDate);" class="inputBox pointer" size="10">
 <%--     <a href="javascript: void(0);" class="datePopUpLink" ID="linkshipDate" onClick="return getCalendar(document.genericForm.shipDate);">&diams;</a></td>
      --%>    
      </tr>
       <tr>
        <td width="30%" class="optionTitleBoldRight"><fmt:message key="label.shipstatus"/>:</td>
        <td width="70%">
          <select name="shipStatus" id="shipStatus" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="vvBean" items="${vvFreightAdviceStatusBeanCollection}" varStatus="status">
            <c:set var="selected" value=''/>
<%--            <c:if test='${status.current.status == "New"}'>
              <c:set var="selected" value='selected'/>
            </c:if>--%>
            <option value="<c:out value="${status.current.status}"/>" ${selected}><c:out value="${status.current.statusDescription}"/></option>
          </c:forEach>
          </select>
       </td>
      </tr>
      <tr>
      <td width="30%" class="optionTitleBoldRight">
<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
</td>
      <td width="70%">
<input name="createExcel" id="createExcel" type="button" value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
</td>
      </tr>
    </table>
                        <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt=""
                                                           width="15" height="15" class="corner"
                                                           style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input type="hidden" name="personnelLoggedIn" id="personnelLoggedIn" value="${sessionScope.personnelBean. personnelId}"/>
<input name="searchHeight" id="searchHeight" type="hidden" value="163">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none"><a href="#" onclick="call('updateExpedite');"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>