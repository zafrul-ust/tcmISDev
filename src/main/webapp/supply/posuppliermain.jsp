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
<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
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
<script type="text/javascript" src="/js/supply/posuppliermain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.purchaseordersupplier"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var returnSelectedValue=null;
var returnSelectedId=null;
var valueElementId="${valueElementId}";
var displayElementId="${displayElementId}";
var statusFlag="${statusFlag}";

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
newsupplierrequest:"<fmt:message key="newsupplierrequest.title"/>",    
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="loadLayoutWin('','supplierSearch');myMainOnload();document.genericForm.searchArgument.focus();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/posupplierresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <!-- Search Option Table Start -->
<table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="15%" class="optionTitleBoldRight">
			<fmt:message key="label.searchtext"/>:
		</td>
		<td width="15%" class="optionTitleLeft">
			<input type="text" name="searchArgument" id="searchArgument" value="" size="30" class="inputBox"/>
		</td>
		<td class="optionTitleBoldLeft" nowrap>
			<input type="checkbox" name="activeOnly" id="activeOnly" checked value="activeOnly" class="radioBtns"><fmt:message key="label.activeOnly"/>    
		</td>
    </tr>
    
    <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.country"/>:</td>
        <td >
         <select name="countryAbbrev" id="countryAbbrev" class="selectBox">
          <option value="All" selected><fmt:message key="label.all"/></option>
             <c:set var="selectedCountry" value='USA'/>
             <tcmis:isCNServer>
				<c:set var="selectedCountry" value='CHN'/>
			 </tcmis:isCNServer> 
          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>

            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="selectedCountry" value='${status.current.countryAbbrev}'/>                
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentCountry == selectedCountry}">
                <option value="<c:out value="${currentCountry}"/>" selected><c:out value="${status.current.country}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentCountry}"/>"><c:out value="${status.current.country}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
       <td/>
      </tr>
	<tr>
		<td colspan="3" class="optionTitleBoldLeft">
		 <input name="submitSearch" id="submitSearch" type="submit" 
		 							class="inputBtns" 
		 							onmouseover="this.className='inputBtns inputBtnsOver'" 
		 							onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" 
				   					onclick="return submitSearchForm();"/>
				   
		 <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit"
                                    class="inputBtns" value="<fmt:message key="label.createexcelfile"/>"
                                    onmouseover="this.className='inputBtns inputBtnsOver'"
                                    onmouseout="this.className='inputBtns'"
                                    onclick="generateExcel(); return false;">
<c:set var="newPermission" value="false"/>
<tcmis:facilityPermission indicator="true" userGroupId="NewSuppApprover">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<c:set var="newPermission" value="false"/>
<tcmis:facilityPermission indicator="true" userGroupId="BuyOrder">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="Sourcing">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="newSupplier">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<c:if test="${newPermission == 'true' && empty param.displayElementId}">
<input name="submitNew" id="submitNew" type="button" value="<fmt:message key="label.newsupplier"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
 onclick="subnew()">
</c:if>
		
		</td>		
	</tr>
</table>
    <!-- Search Option Table end -->
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
<div id="searchErrorMessagesArea" class="errorMessages">
            <html:errors/>
        </div>
    </div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="test" id="test" value="" type="text" />
<input type="hidden" name="supplierRequestType" id="supplierRequestType"/>
<input type="hidden" name="displayElementId" id="displayElementId" value="${param.displayElementId}"/>
<input type="hidden" name="valueElementId" id="valueElementId" value="${param.valueElementId}"/>
<input type="hidden" name="statusFlag" id="statusFlag" value="${param.statusFlag}"/>
<input type="hidden" name="popUp" id="popUp" value="${param.popUp}"/>
<input type="hidden" name="fromSupplierPriceList" id="fromSupplierPriceList" value="${param.fromSupplierPriceList}"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input name="searchHeight" id="searchHeight" type="hidden" value="108">
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
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
    <div id="mainUpdateLinks"  > <%-- mainUpdateLinks Begins --%>
      <c:choose>
    <c:when test="${ empty displayElementId  &&  empty valueElementId}">  
     <span id="updateResultLink" style="display: none"><a href="#" onclick="call('updatePoSupplier');"><fmt:message key="label.update"/></a></span>   
    </c:when>
    <c:otherwise>
     <span id="selectedRow">&nbsp;</span>             
    </c:otherwise>
   </c:choose>       
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
</div>

</body>
</html:html>