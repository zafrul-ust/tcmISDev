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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<title>
<fmt:message key="label.reason"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--

//windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    reason:"<fmt:message key="label.reason"/>",
    nodatafound:"<fmt:message key="main.nodatafound"/>"
};

function changeReason(){
    if ($('reason').value == '') {
        alert(messagesData.validvalues+" "+messagesData.reason);
    }else {
	    window.opener.loadChangeSupplierReasonCallback('${param.selectedRowId}',$('reason').value);
	    window.close();
	}
}
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="" onunload="" onresize="resizeFrames()">

<form  onsubmit="">

<div class="interface" id="mainPage" style="">

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

<div class="optionTitleBoldCenter" nowrap>
	<fmt:message key="label.reason" />:&nbsp;
	        <select name="reason" class="selectBox" id="reason">
	         <option value=""><fmt:message key="label.selectOne"/></option>
			 <c:forEach var="reasonBean" items="${reasonColl}" varStatus="status1">
					  <option value="<c:out value="${status1.current.reasonId}"/>"><c:out value="${status1.current.reasonDescription}"/></option>
			 </c:forEach>
        </select>
	<input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.ok"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
		onclick="changeReason()">
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

<form>

</body>
</html>