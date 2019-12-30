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
<%@ include file="/common/opshubig.jsp" %>
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/binlabelsmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


    
<title>
<fmt:message key="binlabels.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",    
all:"<fmt:message key="label.all"/>",
selectaroom:"<fmt:message key="label.selectaroom"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
//showSearchHeight = true; 


// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.hub}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.hub}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altRoomId = new Array();
var altRoomName = new Array();
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="roomCollection" value='${status.current.roomVar}'/>
  <c:set var="tempRoom" value=''/>
  altRoomId["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status1">
    ,"${status1.current.room}"

    <c:set var="tempRoom" value='${status1.current.room}'/>
  </c:forEach>
  );

  altRoomName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status2">
    ,"${status2.current.room} - ${status2.current.roomDescription}"
  </c:forEach>
  );
</c:forEach>
// -->
function roomMgtPopup() {
	if( validateSearchForm() ) {
		//uAction=update&
	var loc = "manageroom.do?hub="+ $v("hub")+ "&room="+encodeURIComponent( $v("room") );
		openWinGeneric(loc, "500", "550", "yes", "80", "80");
	};
	}
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="setDropDowns();setDefaults();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/binlabelsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

 <c:set var="selectedHubName" value='${param.sourceHubName}'/>

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
    <table width="900" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:
        </td>
         <td width="6%" class="optionTitleBoldLeft">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
         <input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
      </td>
         		   <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
            <td colspan="3" class="optionTitleBoldLeft">
                <select name="searchField"  class="selectBox" id="searchField" >
                <option value="bin"> <fmt:message key="label.bin"/> </option>                                                                                                                                                                                 
                </select>
                &nbsp;
                <select name="searchMode" class="selectBox" id="searchMode" >                
                <option value="is"> <fmt:message key="label.is"/>  </option>
                <option value="like" selected> <fmt:message key="label.contains"/>  </option>
                <option value="startsWith"><fmt:message key="label.startswith"/> </option>
                <option value="endsWith"><fmt:message key="label.endswith" /></option>
                        
                </select>
             &nbsp;
                <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15" onkeypress="return onKeyPress()"/>
            </td>   
      </tr>
      <tr>
      <td width="2%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:
        </td>
        <td width="15%" class="optionTitleLeft">
          <c:set var="selectedHub" value='${param.branchPlant}'/>
          <select name="hub" id="hub" class="selectBox">
          <c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.hub}'/>
            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.hub}'/>
                <c:set var="roomCollection" value='${status.current.roomVar}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="roomCollection" value='${status.current.roomVar}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
          <input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
        </td>
      </tr>
      <tr>
        <td width="2%" class="optionTitleBoldRight">
          <fmt:message key="label.room"/>:
        </td>
         <td class="optionTitleLeft">
          <c:set var="selectedRoom" value='${param.room}'/>
          <select class="selectBox"  name="room" id="room" size="1">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status">
              <c:set var="currentRoom" value='${status.current.room}'/>
              <c:choose>
                <c:when test="${empty selectedRoom}" >
                  <c:set var="selectedRoom" value=""/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentRoom == selectedRoom}">
                  <option value="<c:out value="${currentRoom}"/>" selected><c:out value="${status.current.roomDescription}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentRoom}"/>" selected><c:out value="${status.current.roomDescription}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="2%" class="optionTitleBoldRight">
          <fmt:message key="label.bintype"/>
        </td>     
        <td class="optionTitleLeft">
          <select class="selectBox"  name="binType" id="binType" size="1">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="vvBinType" items="${vvBinTypes}" varStatus="status">
              <option value="<c:out value="${vvBinType}"/>"><c:out value="${status.current}"/></option>
            </c:forEach>
          </select>
        </td>
        </tr>  
        <tr>
         <td colspan="3">
         <input type="checkbox" name="showActiveOnly" id="showActiveOnly" value="Yes" />&nbsp;&nbsp;<fmt:message key="label.showactiveonly"/>
         </td>
      </tr> 
      <tr>
        <td><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
        			onclick="return submitSearchForm(); "/></td>
        <td><input name="manageRoom" id="manageRoom" type="button" class="inputBtns" value="<fmt:message key="label.manageRoom"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
        			onclick="roomMgtPopup()"/></td>
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
<input name="action" id="action" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="133"/>   
<input type="hidden" name="sourceHubName"  id="sourceHubName" value="${sourceHubName}"/>
<input type="hidden" name="branchPlant"  id="branchPlant" value=""/>
<input type="hidden" name="pageid"  id="pageid" value="${param.pageid }"/>
<input name="maxData" id="maxData" type="hidden" value="2000"/>
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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>  
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>   
       <%-- 
       <span id="printStuff"><a href="#" onclick="call('printSelection');"><fmt:message key="label.print"/></a></span>|
        --%>
       <span id="updateRecord" style="display: none">&nbsp;&nbsp;<a href="#" onclick="call('updateBinLabels');"><fmt:message key="label.update"/></a></span>
   	   <span id="addNewBinLink" style="display: none">&nbsp;|&nbsp;<a href="#" onclick="call('createnewbin');"><fmt:message key="label.createnewbin"/></a></span>
   	   <span id="generateBinLabelsLink" style="display: none">&nbsp;|&nbsp;<a href="#" onclick="call('generateLabels');"><fmt:message key="labels.generatelabels"/></a></span>
        
      </div> <%-- mainUpdateLinks Ends --%>

	</div> <%-- boxhead Ends --%>
    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="150" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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