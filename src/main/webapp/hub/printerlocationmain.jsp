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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

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
<script type="text/javascript" src="/js/hub/printerlocationmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

    
<title>
<fmt:message key="label.labelPrinter"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
all:"<fmt:message key="label.all"/>",
errors:"<fmt:message key="label.errors"/>",      
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
searchInput:"<fmt:message key="error.searchInput.integer"/>"};

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
If you dont want to use the layout set javascript variable useLayout=false;
It is important to send the pageId if this page is going to open in a tab in the application.
You can also call any functions you need to do your search initialization for drop downs.
-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','printerLocation');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search div Begins -->
<div id="searchFrameDiv">
<!-- start of contentArea -->
<div class="contentArea">
<tcmis:form action="/printerlocationresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<table id="searchMaskTable" width="250" border="0" cellpadding="0" cellspacing="0">
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
       <td width="10%">
         <!-- Use this for dropdowns you build with collections from the database -->
         <select name="hub" id="hub" class="selectBox">
           <c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status">
             <option value="${status.current.branchPlant}">${status.current.hubName}</option>
          </c:forEach>
         </select>
       </td>
      </tr>
 

      <tr>
      <%--The colspan will depend on the page--%>
      <td colspan="2">
        <input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.search"/>" 
            class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm();">
         <input name="New" id="New" type="button"  value="<fmt:message key="label.addnewpl"/>" onclick="addNewPrinterLoc();"
            class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
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
<input name="searchHeight" id="searchHeight" type="hidden" value="83">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search div Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr> 
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	  <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
	  <span id="updateResultLink" style="display: none">
	    <tcmis:facilityPermission indicator="true" userGroupId="updatePrinterPath">
          | <a href="#" onclick="call('submitUpdate');return false;"><fmt:message key="label.update"/></a>
          
        </tcmis:facilityPermission>
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
</td>
</tr>
</table>
<!-- results end -->
</div>  
</div>
<!-- Result Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="80px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.offline"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>