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
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- Popup window support -->
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->

<!-- Add any other javascript you need for the page here -->

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

<title>
 <fmt:message key="label.revisiondate"/>
</title>

<c:set var="includeDeleted" value='${includeDeleted}'/>
<c:set var="personnelId" value='${personnelId}'/>

<c:set var="updatePermission" value='N'/>
<c:if test="${personnelId != '-1'}" >
<tcmis:facilityPermission indicator="true" userGroupId="msdsDocuments" facilityId="${param.facilityId}">
<c:set var="updatePermission" value='Y'/>
</tcmis:facilityPermission>
</c:if>
<%-- if called from haas module than allow users to update--%>
<c:set var="module"><tcmis:module /></c:set>
<c:if test="${module == 'catalog'}" >
    <c:set var="updatePermission" value='Y'/>
</c:if>

<script language="JavaScript" type="text/javascript">
<!--
var windowCloseOnEsc = true;
var dhxWins = null;
var children = new Array();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
selectedRowMsg:"<fmt:message key="label.selectedmaterial"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
material:"<fmt:message key="label.materialsearch"/>",
waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
msdsnorevisiondate:"<fmt:message key="error.msdsnorevisiondate"/>"};
// -->

function getRevisionDates(matlId) {
	j$.ajax({
		url:"contactlog.do",
		cache:false,
		data:{uAction: "revisionDates", materialId: matlId},
		success: function(data) {
			if (data != null && data != "") {
				var jsonObj = j$.parseJSON(data);
				var selectBox = document.getElementById("revisionDate");
				var leng = selectBox.length;
				for (var i = 0; i < leng;i++) {
					selectBox.remove(0);
				}
				for (date in jsonObj.dates) {
					var option = document.createElement("option");
					option.text = jsonObj.dates[date];
					option.value = jsonObj.dates[date];
					selectBox.add(option);
				}
				if (selectBox.length == 1) {
					returnSelected();
				}
			}
		}
	});
}

function materialChanged(materialId, materialDesc, tradeName, mfgId, productCode, customerOnly) {
	document.getElementById("materialId").value = materialId;
	document.getElementById("returnResult").innerHTML = messagesData.selectedRowMsg + ' (' + materialId + ')';
	getRevisionDates(materialId);
	document.getElementById("materialDesc").innerHTML = materialDesc;
	document.getElementById("tradeName").innerHTML = tradeName;
	document.getElementById("mfgId").innerHTML = mfgId;
	document.getElementById("productCode").innerHTML = productCode;
	document.getElementById("customerOnly").innerHTML = customerOnly?"Yes":"No";
}

function login() {
	$("uAction").value = "login";
	document.genericForm.submit();
}

function lookupMaterial() {
	var loc = "materialsearchmain.do?userAction=init";
	showWait(formatMessage(messagesData.waitingFor, messagesData.material));
	children[children.length] = openWinGeneric(loc,"materialsearch","700","500","yes","50","50","20","20","no");
}

function returnSelected() {
	var materialId = document.getElementById("materialId").value;
	var revisionDate = document.getElementById("revisionDate").value;
	if (materialId != null && materialId != "" &&
			revisionDate != null && revisionDate != "") {
		parent.opener.addMsds(materialId, revisionDate);
		parent.close();
	}
	else {
		alert(messagesData.msdsnorevisiondate);
	}
}

function myOnLoad() {
	$("mainUpdateLinks").style.display = "";
	lookupMaterial();
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitPage");
	transitDailogWin.style.display="";

	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}

	if (!dhxWins.window("transitPage")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitPage",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitPage");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(150, 25);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitPage").show();
	}
}

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitPage")) {
			dhxWins.window("transitPage").setModal(false);
			dhxWins.window("transitPage").hide();
		}
	}
	return true;
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array(); 
}

function myUnload() {
	parent.opener.stopShowingWait();
	closeAllchildren();
}

</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();" onresize="resizeFrames()" onunload="myUnload();">
<tcmis:form action="/showmsdsdocuments.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><span id="transitLabel"></span>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv">
<!-- results start -->
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
    <div class="boxhead"> 
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <c:if test="${updatePermission == 'Y'}" >
        <a href="#" onclick="lookupMaterial();"><fmt:message key="label.materialsearch"/></a>&nbsp;|
        <a id="returnResult" href="#" onclick="returnSelected();"></a>
      </c:if>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="dataContent">
     <table width="600px" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      	<tr>
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.materialid"/>:</td>
        	<td width="20%" ><input name="materialId" readonly id="materialId" type="text" class="inputBox" onclick="lookupMaterial();" value=""/></td>
      	
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.revisiondate"/>:</td>
        	<td width="20%" >
        	  <select name="revisionDate" id="revisionDate">
        	  </select>
        	</td>
        </tr>
        <tr>
        	<td class="optionTitleBoldRight" width="20%" ><fmt:message key="label.materialdesc"/>:</td>
        	<td width="30%" ><span id="materialDesc"></span></td>
        </tr>
        <tr>
        	<td class="optionTitleBoldRight" width="20%" ><fmt:message key="label.tradename"/>:</td>
        	<td width="30%" ><span id="tradeName"></span></td>
      	</tr>
      	<tr>
      		<td class="optionTitleBoldRight" width="20%" ><fmt:message key="label.mfgid"/>:</td>
        	<td width="30%" ><span id="mfgId"></span></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="20%" ><fmt:message key="report.label.productCode"/>:</td>
        	<td width="30%" ><span id="productCode"></span></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="20%" ><fmt:message key="label.customeronlymsds"/>:</td>
        	<td width="30%" ><span id="customerOnly"></span></td>
      	</tr>
     </table>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<%--Store the search parameters.--%>
<input name="uAction" id="uAction" value="" type="hidden"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>
