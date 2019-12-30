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

<!--Use this tag to get the correct CSS class.
This looks at what the user's prefered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/editcomment.js"></script>
<title>
<fmt:message key="label.comment"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function callback() {
	if( '${callback}' == 'true' ) {
		eval( 'try{opener.${param.callback}(document.getElementById("comments").value,document.getElementById("callbackParam").value);}catch(ex){};window.close();'); 
	}
}
// -->
</script>


</head>
<body bgcolor="#ffffff" onload="mySearchOnLoad(); callback();">

<!--Uncomment for production-->

<html:form action="/editcomment.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <tr>    
      <td width="15%" class="optionTitleBoldRight"><fmt:message key="label.comment"/>:</td>      
      <td>      
       <textarea cols="60" rows="6" class="inputBox" name="comments" id="comments"><c:out value="${param.comments}"/>${comments}</textarea>
      </td>
    </tr>  
	<tr>
		<td width="15%" class="optionTitleBoldRight">
			<input name="addcomment" id="addcomment" type="submit" value="<fmt:message key="label.ok(done)"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="addComment();"/>
		</td>
		<td width="85%">
			<input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="window.close()"/>
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value="addcomment"/>	
<input name="catPartNo" id="catPartNo" type="hidden" value="${param.catPartNo}"/>	
<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${param.catalogCompanyId}"/>	
<input name="partGroupNo" id="partGroupNo" type="hidden" value="${param.partGroupNo}"/>	
<input name="catalogId" id="catalogId" type="hidden" value="${param.catalogId}"/>	
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>	
<input name="callback" id="callback" type="hidden" value="${param.callback}"/>	
<input name="callbackParam" id="callbackParam" type="hidden" value="${param.callbackParam}"/>	
<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
</html:form>
</body>
</html:html>