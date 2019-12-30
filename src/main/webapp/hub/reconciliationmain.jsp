<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<script type="text/javascript" src="/js/hub/reconciliation.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/hub/showinventoryreportlegend.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<title>
<fmt:message key="label.reconciliation"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",choosehubbeforestartnewcount:"<fmt:message key="label.choosehubbeforestartnewcount"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",mustselectcountdate:"<fmt:message key="label.mustselectcountdate"/>",    
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
startnewcount:"<fmt:message key="label.startnewcount"/>",
closecount:"<fmt:message key="label.closecount"/>",
searchInput:"<fmt:message key="error.searchInput.integer"/>",
missingoperatingentity:"<fmt:message key="label.missingoperatingentity"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;

var facilityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="InvGrpBean" items="${personnelBean.permissionBean.userGroupMemberIgBeanCollection}" varStatus="status">
  <c:if test="${(status.current.userGroupId  == 'approveReconciliation') or (status.current.userGroupId  == 'inventoryReconciliation') }">
    facilityPermissionArray["<c:out value="${counter}"/>"] = "<c:out value="${status.current.facilityId}" escapeXml="false"/>";
    <c:set var="counter" value='${counter + 1}'/>    
  </c:if>
</c:forEach>


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

var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contains"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
	);

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setOpsValues();attachCountDateUpdate();hubValChanged();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="label.reconciliation"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">


<tcmis:form action="/reconciliationresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">



<c:set var="hasPerm" value='N'/> 

<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReconciliation">
<c:set var="hasPerm" value='Y'/> 
</tcmis:inventoryGroupPermission>


<tcmis:inventoryGroupPermission indicator="true" userGroupId="approveReconciliation">
<c:set var="hasPerm" value='Y'/> 
</tcmis:inventoryGroupPermission>

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
    <table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
        <td width="10%" class="optionTitleBoldRight" nowrap>
          <fmt:message key="label.countdate"/>:&nbsp;
        </td>
       
        <td class="optionTitleBoldLeft" nowrap>
          <input name="countDate" id="countDate" type="hidden" />
          <input name="inputDate" id="inputDate" type="hidden" />
          <select class="selectBox" name="countDateString"  id="countDateString" value="${param.countDateString}" style="width:150px" onchange="countDropDownChanged();"></select>
          <c:if test="${hasPerm == 'Y'}">
          <input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.startnewcount"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 onclick= "return startNewCount()"/>
          <input name="closeCountBtn" id="closeCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.closecount"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 onclick= "return closeCount();"/>
           </c:if>
           		 
        </td>
        
        <td width="10%" style="display: none;">
         <select class="selectBox" name="countTypeDropDown"  id="countTypeDropDown" value=""></select> 
         <select class="selectBox" name="countStartedByDropDown"  id="countStartedByDropDown" value=""></select> 
        </td>
       
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.counttype"/>:</td>
      <td width="10%" class="optionTitleLeftBold" nowrap>				
  		  <input  type="text"  name="countType"  id="countType" value='' size="25" class="invGreyInputText" readonly/>
  		  <b><fmt:message key="label.countstartedby"/>: </b><input  type="text"  name="countStartedByName"  id="countStartedByName" value='' size="20" class="invGreyInputText" readonly/>          
      </td>
      <td width="10%" class="optionTitleBoldRight">&nbsp;</td>
    </tr>
    <tr>
      <td width="10%" rowspan="2" class="optionTitleBoldRight" nowrap><fmt:message key="label.invgrp"/>:&nbsp;</td>
      <td width="10%" rowspan="2">
       <select name="inventoryGroup" id="inventoryGroup" multiple="multiple" size="5" class="selectBox">
       </select>
      </td>
      <td width="10%" class="optionTitleBoldRight" rowspan="1" nowrap><fmt:message key="label.lotstatus"/>:</td>
      <td width="10%" class="optionTitleBoldLeft" rowspan="1">

	    
	   <select class="selectBox" name="lotStatus" id="lotStatus" size="4" multiple>
 	 	<option value=""><fmt:message key="label.all"/></option>
	   <c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
	   	<c:set var="jspLabel" value=""/>
	    <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
	     	<c:choose>
				<c:when test="${selectedLotStatus == bean.lotStatus}">
					<option value="<c:out value="${status.current.lotStatus}"/>" selected><fmt:message key="${jspLabel}"/></option>
				</c:when>
				<c:otherwise>
					<option value="${bean.lotStatus}"><fmt:message key="${status.current.jspLabel}"/></option>
				</c:otherwise>
			</c:choose>
	  </c:forEach>
	 </select>
	   <input name="radiobox" id="radiobutton_0" type="radio" class="radioBtns" value="Inclusive" checked><fmt:message key="label.include"/>
	  	<input name="radiobox" id="radiobutton_1" type="radio" class="radioBtns" value="Exclusive"><fmt:message key="label.exclude"/>
	   </td>
          
    </tr> 
    <tr>
    <td width="10%" class="optionTitleBoldRight" nowrap>
      	<fmt:message key="label.room"/>:
      </td>
      <td width="10%" class="optionTitleBoldLeft">
      	<select name="room"  class="selectBox" id="room" >
        	<option value=""><fmt:message key="label.all"/></option>
        </select>
      </td>   
	    <td width="10%" class="optionTitleBoldRight">&nbsp;</td> 
	  </tr>
     <tr>
      <td width="10%" class="optionTitleBoldLeft" colspan="2" nowrap>
      	<fmt:message key="label.showproblemreceiptsonly"/>&nbsp;
      	<input type="checkbox" class="optionBox" name="checkbox" id="checkbox" onChange=""/> 
      </td>
      <td width="10%" class="optionTitleBoldRight" nowrap>
      	<fmt:message key="label.bin"/>:
      </td>
      <td width="10%" class="optionTitleBoldLeft" >
		 <fmt:message key="label.from"/>
		 <input class="inputBox" type="text" name="binFrom" id="binFrom" value=""  maxlength="25" size="15"/>
		 <fmt:message key="label.to"/>&nbsp;
		 <input class="inputBox" type="text" name="binTo" id="binTo" value="" maxlength="25" size="15"/>
      </td> 
          
    </tr> 
    <tr>
      <td width="10%" class="optionTitleBoldLeft" colspan="2" nowrap>
      	<fmt:message key="label.skipprintingreconhand"/>&nbsp;
      	<input type="checkbox" class="optionBox" name="printOnHandCheckbox" id="printOnHandCheckbox" onChange=""/> 
      </td> 
       <td width="10%" class="optionTitleBoldRight" nowrap>
      	<fmt:message key="label.search"/>:
      </td>
       <td class="optionTitleBoldLeft" nowrap>
                <select name="searchField"  class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value)" >
                    <option value="itemId"> <fmt:message key="label.itemid"/>  </option>
                    <option value="receiptId"><fmt:message key="label.receiptid"/> </option>
                    <option value="itemDesc"><fmt:message key="label.itemdesc"/> </option> 
                    <option value="bin"><fmt:message key="label.bin"/> </option>
                    <option value="catPartNo"><fmt:message key="label.partnumber"/> </option>          
                </select>
                &nbsp;
                <select name="searchMode" class="selectBox" id="searchMode" >
                        <option value="is"> <fmt:message key="label.is"/>  </option>
                        <option value="startsWith"><fmt:message key="label.startswith"/> </option>  
                </select>
             &nbsp;
                <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
            </td> 
             <td width="10%" class="optionTitleBoldRight">&nbsp;</td>    
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldLeft" colspan="2" nowrap>
      	<label><fmt:message key="label.skipalreadycyclecounted"/>&nbsp;
      	<input type="checkbox" class="optionBox" name="skipCountedCheckbox" id="skipCountedCheckbox"/>
      	</label> 
      </td>
      <td width="10%" class="optionTitleBoldLeft" colspan="2" nowrap>
      	<label><fmt:message key="label.includepartnumbers"/>&nbsp;
      	<input type="checkbox" class="optionBox" name="includePartNumbers" id="includePartNumbers" checked/>
      	</label> 
      </td>
    </tr>
    <tr>    
     <td colspan="5" width="100%" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()"/>
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
          <input name="createPdfBtn" type="button" class="inputBtns" value="<fmt:message key="label.createpdffile"/>" id="createPdfBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "createPdf();"/>
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


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<%--
<input name="hub" id="hub" type="hidden" value="${param.hub}" />
<input name="countDate" id="countDate" type="hidden" value="${param.countDate}" />
--%>
<input name="countId" id="countId" type="hidden" value="${param.countId}" />
<input name="hubName" id="hubName" type="hidden" value="${param.hubName}" />
<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}" />
<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}" />
<input name="displayCountId" id="displayCountId" type="hidden" value="" />

<input name="searchHeight" id="searchHeight" type="hidden" value="228"> 

</div>
<!-- Hidden elements end -->

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

<!--  Transit Modal Win -->
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
		
		 <span id="countTxt"> <fmt:message key="label.countdate"/>:<span id="spanid1"></span>&nbsp;&nbsp;
  	    <fmt:message key="label.counttype"/>:<span id="spanid2">&nbsp;</span>
  	     <span id="spanid3"><br/><fmt:message key="label.approvedby"/>:&nbsp;</span><span id="spanid4">&nbsp;</span>
  	    </span>
		<span id="updateResultLink" style="display:none">
  	     <span id="updateLink1"> <br/><a href="#" onclick="call('updateData') "><fmt:message key="label.update"/></a></span>  	      
  	     <span id="addReceiptLink" style="display:none">|  <a href="#" onclick="call('addReceipts') "><fmt:message key="label.addreceipts"/></a></span>
         <span id="approveLink" style="display:none">|  <a href="#" onclick="call('approveData') "><fmt:message key="label.approve"/></a></span>
  	    </span>  	      	  
  	   
      <%-- 
            <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
     </div> <%-- mainUpdateLinks Ends --%>
     <span id="addReceiptLinkOnly" style="display:none">  <a href="#" onclick="call('addReceipts') "><fmt:message key="label.addreceipts"/></a></span>  
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

</body>
</html:html>