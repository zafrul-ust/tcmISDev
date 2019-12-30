<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />

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
<script type="text/javascript" src="/js/client/het/mixturemanagementmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="mixtureManagement"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    or:"<fmt:message key="label.or"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};


// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','mixtureManagement');" onresize="resizeFrames();">

<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/mixturemanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
			<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.facility"/>:
			</td>
			<td width="40%" class="optionTitleBoldLeft">
				<c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
				<select name="facilityId" id="facilityId" class="selectBox">
				
				  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
				    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
				        <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
				          <c:if test="${empty selectedFacilityId}" >
				            <c:set var="selectedFacilityId" value='${currentFacilityId}' />
				          </c:if>
				          <c:if test="${currentFacilityId == selectedFacilityId}" >
				            <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
				          </c:if>
				
				        <c:choose>
				          <c:when test="${currentFacilityId == selectedFacilityId}">
				            <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
				          </c:when>
				          <c:otherwise>
				            <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
				          </c:otherwise>
				        </c:choose>
				    </c:if>
				  </c:forEach>
				</select>
				</td>
				<td width="10%" id="simpleSearchTd1" class="optionTitleBoldRight" nowrap>
		            <fmt:message key="label.search"/>:
		        </td>
		        <td width="50%"class="optionTitleBoldLeft" nowrap>
		        	<select name="searchField" class="selectBox" id="searchField" >
              <%--	   <option value="catPartNo"> <fmt:message key="label.partno"/>  </option>   --%>      
		                   <option value="materialDesc"> <fmt:message key="label.materialdescription"/>  </option>
		                   <option value="msdsNo"><fmt:message key="label.msds"/> </option>
		                   <option value="mixtureName"><fmt:message key="label.mixturename"/> </option>
		            </select>
		        	<select name="searchMode" class="selectBox" id="searchMode" >
                    	   	   <option value="is"> <fmt:message key="label.is"/>  </option>
		                   <option value="like"> <fmt:message key="label.contains"/>  </option>
		                   <option value="startsWith"><fmt:message key="label.startswith"/> </option>
		            </select>
		            <input name="searchArgument" id="searchArgument" type="text" class="inputBox" onkeypress="onKeySearch(event,submitSearchForm);" size="40" value="">&nbsp;
		        </td>		
			</tr>
			<tr>
     			<td colspan="4" width="100%" class="optionTitleBoldLeft">
          			<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
          			<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
				</td>
    		</tr>
    </table>
    
   <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
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
    <input name="uAction" id="uAction" type="text" value="">
    <input name="companyId" id="companyId" type="hidden" value="${companyId}">
	<input name="defaultFacilityId" id="defaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
	<input name="searchHeight" id="searchHeight" type="hidden" value="150">
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
<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>


<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
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
     <div id="mainUpdateLinks" style="display: "> <%-- mainUpdateLinks Begins --%>
     	<span id="updateResultLink" style="display: none">
     		<a href="#" onclick="resultFrame.searchMSDS('newMixture');"><fmt:message key="label.newmixture" /></a>
     		<span id="hasDataLink" style="display: none">
     		| <a href="#" onclick="resultFrame.updateParts();"><fmt:message key="label.update" /></a>
<%--	 		| <a href="#" onclick="resultFrame.deletePart();"><fmt:message key="label.delete" /></a>  --%>
			</span>
		</span>
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