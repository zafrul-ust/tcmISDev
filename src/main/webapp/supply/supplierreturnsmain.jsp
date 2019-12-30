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
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/supply/supplierreturns.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.supplierreturns"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",searchInputInteger:"<fmt:message key="error.searchInput.integer"/>",
itemid:"<fmt:message key="label.itemid"/>", 
radianpo:"<fmt:message key="label.radianpo"/>",  
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>"};

defaults.ops.nodefault=true;
defaults.hub.nodefault=true;
defaults.inv.nodefault=true;


function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','rmaProcess');setOps();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/supplierreturnsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="400" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
      <td width="70%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.search"/>:&nbsp;
        <select id="searchField" name="searchField"  class="selectBox">
          <option value="itemId"><fmt:message key="label.itemid"/></option>
          <option value="radianPo" selected="selected"><fmt:message key="label.haaspo"/></option>
        </select>
        <select name="searchMode" id="searchMode" class="selectBox">
            <option value="is" selected="selected"><fmt:message key="label.is"/></option>
            <option value="contains"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
        </select>
        <input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='' size="25" >     
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
      <td width="10%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	
      </td>     
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
      <td width="10%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      
	  </td>     
    </tr>
   
    <tr>
     <td colspan="4" width="100%" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
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
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="133">
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
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
      <div id="mainUpdateLinks" style="display:"> <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
      <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="supplierReturns">
         | <a href="#" onclick="call('process');return false;"><fmt:message key="label.process"/></a>
        </tcmis:permission>
      </span>
          &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.customerownreceipt"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>