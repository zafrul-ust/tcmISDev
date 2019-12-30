<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="helpdeskticket.title"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
emailRequired:"<fmt:message key="label.email"/>",
locationRequired:"<fmt:message key="label.locationsite"/>",
phoneRequired:"<fmt:message key="label.phone"/>",
descOfIssueRequired:"<fmt:message key="label.descofissuereq"/>"
};


function sendEmail() {
	if (!validateForm())
		return false;
	
	var update = document.getElementById("uAction");
	update.value = 'sendemail';
	//submitFrameOnlyOnce();
	document.genericForm.submit();
}

function validateEmail(email) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(email);
}

function validateForm() {
	var errorMessage = messagesData.validvalues;
	var error =false;
	var phone = document.getElementById("phone").value;
	if (phone == "" || phone.trim().length < 10 ) {
		error = true;
		errorMessage += "\n" + messagesData.phoneRequired;
    }
    
	var email = document.getElementById("email").value;
	if (email == "" || !validateEmail(email)) {
		error = true;
        errorMessage += "\n"  + messagesData.emailRequired;       
    }    
    
	var location = document.getElementById("location").value;
	if (location == "" ) {
		error = true;
        errorMessage += "\n" + messagesData.locationRequired;       
    }
    
	var descofissue = document.getElementById("descOfIssue").value;
	if (descofissue == "" ) {
		error = true;
        errorMessage += "\n" + messagesData.descOfIssueRequired;       
    }	

	if (error) {
		alert(errorMessage);
		return false; 
	}	
	return true;
}



// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="document.genericForm.phone.focus();" onresize="resizeFrames()">

<tcmis:form action="/helpdeskticket.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${sent == 'Y'}">
   sent = true;
   </c:when>
   <c:otherwise>
   sent = false;
   </c:otherwise>
</c:choose>
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<c:set var="phone" value='${sessionScope.personnelBean.phone}'/>
<c:set var="email" value='${sessionScope.personnelBean.email}'/>

<div id="helpdeskticketsent" class="errorMessages" style="display: none; padding-left:400px;overflow: auto;">
    <b><font color="red"><fmt:message key="label.helpdeskticketsent"/></font></b>
</div>
<script type="text/javascript">
<!--

if ( sent ) {
    document.getElementById("helpdeskticketsent").style["display"] = "";
} else { 
    document.getElementById("helpdeskticketsent").style["display"] = "none";
}
//-->
</script>
<fieldset>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr class="alt">
<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.name"/>:&nbsp;</td>
<td width="75%"><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></td>
</tr>
<tr class="alt">
<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.phonenumber"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="75%"><input type="text" class="inputBox" name="phone" id="phone" value="<c:out value="${phone}" />" maxlength="30" size="15"/> </td>
</tr>
<tr class="alt">
<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.email"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="75%"><input type="text" class="inputBox" name="email" id="email" value="<c:out value="${email}" />" maxlength="30" size="50"/></td>
</tr>
<tr class="alt">
<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.locationsite"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="75%"><input type="text" class="inputBox" name="location" id="location" value="" maxlength="80" size="50"/></td>
</tr>
<tr class="alt">
<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.descofissue"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
<td width="75%"><textarea id="descOfIssue" name ="descOfIssue" cols="60" rows="10"></textarea></td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td width="75%">
        <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.submitticket"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return sendEmail();">        
    </td>
</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>


<!--  result page section start -->
<div></div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="personnelId" id="personnelId" type="hidden" value="${sessionScope.personnelBean.personnelId}"/>
<input name="name" id="name" type="hidden" value="${firstname} ${lastname}"/>
<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html>