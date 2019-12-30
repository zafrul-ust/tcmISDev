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

<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<%--
 <script type="text/javascript" src="/js/hub/minmaxlevel.js"></script>
--%>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<title>
<fmt:message key="minmaxlevel.title"/>
</title>

<%--TODO Add option to search for requests waiting for my approval.--%>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
criteriaRequired:"<fmt:message key="error.itempartnumber.required"/>",
itemIdInteger:"<fmt:message key="error.item.integer"/>",
reorderQuantityInteger:"<fmt:message key="error.reorderquantity.integer"/>",
reorderQuantityNotZero:"<fmt:message key="error.reorderquantity.notzero"/>",
reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",
reorderPointVersusLevel:"<fmt:message key="error.reorderpointlevel"/>",
lookAheadDaysInteger:"<fmt:message key="error.lookaheaddays.integer"/>",
all:"<fmt:message key="label.all"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>"
};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
/*
var altHubId = new Array(
		<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"${status.current.branchPlant}"
		   </c:when>
		   <c:otherwise>
		    "${status.current.branchPlant}"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
		);

		var altInventoryGroupId = new Array();
		var altInventoryGroupName = new Array();
		<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="inventoryGroupBeanCollection" value='${status.current.inventoryGroupCollection}'/>

		  altInventoryGroupId["${currentHub}"] = new Array("",
		  <c:forEach var="inventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
		 <c:choose>
		   <c:when test="${status1.index > 0}">
		        ,"${status1.current.inventoryGroup}"
		   </c:when>
		   <c:otherwise>
		        "${status1.current.inventoryGroup}"
		   </c:otherwise>
		  </c:choose>
		  </c:forEach>
		  );

		  altInventoryGroupName["${currentHub}"] = new Array("<fmt:message key="label.all"/>",
		  <c:forEach var="InventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
		 <c:choose>
		   <c:when test="${status1.index > 0}">
		        ,"${status1.current.inventoryGroupName}"
		   </c:when>
		   <c:otherwise>
		        "${status1.current.inventoryGroupName}"
		   </c:otherwise>
		  </c:choose>
		  </c:forEach>
		  );
		</c:forEach>

function hubChanged() {
	  var hub0 = document.getElementById("branchPlant");
	  var ig0 = document.getElementById("inventoryGroup");
	  var selectedHub = hub0.value;

	  for (var i = ig0.length; i > 0; i--) {
	    ig0[i] = null;
	  }
	  showIgOptions(selectedHub);
	  ig0.selectedIndex = 0;
	}

	function showIgOptions(selectedHub) {
	  var igArray = new Array();
	  igArray = altInventoryGroupId[selectedHub];

	  var igNameArray = new Array();
	  igNameArray = altInventoryGroupName[selectedHub];

	  if(igArray.length == 0) {
	    setOption(0,messagesData.all,"", "inventoryGroup")
	  }

	  for (var i=0; i < igArray.length; i++) {
	    setOption(i,igNameArray[i],igArray[i], "inventoryGroup")
	  }
	}
*/
	function validateForm() {
	  if(document.genericForm.criteria == null || document.genericForm.criteria.value.trim().length == 0) {
	    alert(messagesData.criteriaRequired);
	    return false;
	  }

	  if(document.genericForm.criterion.value == 'itemId') {
	    if(!isInteger(document.genericForm.criteria.value.trim(), true)) {
	      alert(messagesData.itemIdInteger);
	      return false;
	    }
	  }
//	  parent.showPleaseWait();
	  return true;
	}

window.onresize = resizeFrames;

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/

  var isValidSearchForm = validateForm();

  if(isValidSearchForm && submitSearchOnlyOnce() ) {
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
   $("uAction").value = 'search';
   	document.genericForm.target='resultFrame';
   	//alert(submitCount);
   	showPleaseWait();
    document.genericForm.target='resultFrame';
    document.genericForm.submit(); 
  }
  return false;
}

function generateExcel() {
    $('uAction').value = 'createExcel';
    if(validateForm()) 
    {
     openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_MinMaxLevelGenerateExcel','650','600','yes');
     document.genericForm.target='_MinMaxLevelGenerateExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
    }
}

function createTemplateData() {
    $('uAction').value = 'createTemplateData';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_MinMaxLevelGenerateExcel','650','600','yes');
    document.genericForm.target='_MinMaxLevelGenerateTemplateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}


function uploadList() {
    openWinGeneric("uploadminmaxlevel.do","uploadList","450","170","yes","80","80");
    showPleaseWait();
}

uploadMinMaxLevelPermission = true;
var opsEntityPermArr = new Array();

<c:forEach var="opsEntityPermission" items="${authorizedUsersForUsergroup}" varStatus="opsEntityStatus">
     opsEntityPermArr[<c:out value="${opsEntityStatus.index}"/>] = '<tcmis:jsReplace value="${opsEntityPermission.opsEntityId}"/>';
</c:forEach>

function checkOpsEntityPermission(selectedOpsEntity) {
    var found = false;
    for (var i = 0; i < opsEntityPermArr.length; i++ ) {
        if(opsEntityPermArr[i] == selectedOpsEntity) {
            found = true; 
        }
    }
    if(found) {
      try {
          document.getElementById("showHideBtn").style.display="";
      } catch(ex) {}    
    }
    else {
      try {
          document.getElementById("showHideBtn").style.display="none";
      } catch(ex) {}
    }
  }


// -->
</script>
</head>
<!--TODO Add Ops entity to page-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','minMaxLevels');if( '${param.uAction}' == 'showlevels' ) submitSearchForm();setOps();if('${param.opsEntityId}' != ''){$('opsEntityId').value='${param.opsEntityId}';opsChanged();$('hub').value='${param.hub}';hubChanged();$('inventoryGroup').value='${param.inventoryGroup}';}">

<c:set var="showUpload" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="uploadPartLevel">
    <c:set var="showUpload" value="Y"/>   
</tcmis:inventoryGroupPermission>


<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/minmaxlevelresults.do" onsubmit="return false;" target="resultFrame">
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
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td nowrap  width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      	<td width="50%" class="optionTitleBoldLeft">
         	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
      	</td>
<%-- 
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="20%">
          <!-- Use this for dropdowns you build with collections from the database -->
          <c:set var="selectedHub" value='${param.branchPlant}'/>
          <select name="branchPlant" id="branchPlant" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="${currentHub}" selected>${status.current.hubName}</option>
              </c:when>
              <c:otherwise>
                <option value="${currentHub}">${status.current.hubName}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>  --%>
      	<td width="30%" colspan="2" class="optionTitleBoldLeft" nowrap="nowrap">
          <!-- Use this for dropdowns that are hardcoded -->
          <fmt:message key="label.search" />:
          <html:select property="criterion" styleClass="selectBox" styleId="criterion">
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="partNumber" key="label.partnumber"/>
        </html:select>
 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
				<html:option value="is" ><fmt:message key="label.is"/></html:option>
<%--				
				<html:option value="contains"><fmt:message key="label.contains"/></html:option>
          		<html:option value="startsWith" key="label.startswith"/>
          		<html:option value="endsWith" key="label.endswith"/>
 --%>          		
 			</html:select>
			<input name="criteria" id="criteria" type="text" class="inputBox" value="${param.criteria}" onkeypress="return onKeyPress(event)"/>
		</td>
      </tr>
	  <tr>
	    <td nowrap width="20%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      	<td  width="50%" class="optionTitleBoldLeft">
         	<select name="hub" id="hub" class="selectBox">
         	</select>
      	</td>
<%--
        <td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="20%">
          <!-- Use this for dropdowns you build with collections from the database -->
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value="${currentInventoryGroup}" selected>${status.current.inventoryGroupName}</option>
                </c:when>
                <c:otherwise>
                  <option value="${currentInventoryGroup}">${status.current.inventoryGroupName}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
       </td>
 --%> 
</tr>
      <tr>
      	<td nowrap  width="20%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      	<td width="50%" class="optionTitleBoldLeft">
       		<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       		</select>
      	</td>	
      </tr>
      <tr>
      <td colspan="6">
      <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm()"/>
      <input name="createexcel" id="createexcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcel"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="generateExcel()"/>      
      <span id="showHideBtn" name="showHideBtn">
        <input title="<fmt:message key="label.createminmaxtemplatetitle"/>" name="createDataB" id="createDataB" type="button" class="inputBtns" value="<fmt:message key="label.createminmaxtemplate"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="createTemplateData()"/>
        <input name="uploadB" id="uploadB" type="button" class="inputBtns" value="<fmt:message key="label.uploadminmaxdata"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="uploadList(); return false;"/>
      </span>
      
      
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
 <input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
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
<span id="showlegendLink">
<%--         <a href="javascript:simpleCallToServerWithGrid('inventoryreportresults.do?uAction=loaddata')"><fmt:message key="label.lotstatuslegend"/></a>
--%>
      </span>
              <a href="#" onclick="resultFrame.submitUpdate();"><fmt:message key="label.update"/></a>
      
&nbsp;
      <%-- 
            <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
      
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

</body>
</html:html>

