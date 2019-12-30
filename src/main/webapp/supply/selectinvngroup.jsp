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
<script type="text/javascript" src="/js/supply/selectinvngroup.js"></script> 

<script type="text/javascript">
<!--
windowCloseOnEsc = true;


/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

//-->
</script>
   
<title>
<fmt:message key="label.inventorygrouplookup"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>"
};

// -->
</script>
</head>

<body bgcolor="#ffffff"  onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/selectinvngroup.do">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

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

<fieldset>
<legend class="optionTitleBoldRight"> <fmt:message key="label.selectinventorygroup"/> : </legend>

<table>
	<%-- Row 1 --%>	
	<tr>
		<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup" />:</td>
		<td width="50%" class="optionTitleBoldLeft" >			
			<select id="inventoryGroup" class="selectBox" size=1 name="inventoryGroup">
                <option value="None"><fmt:message key="label.pleaseselect"/></option>
                <c:forEach var="invGrpName" items="${inventoryGroupCollection}" varStatus="status">
                    <option value="${invGrpName.inventoryGroup}"><c:out value="${invGrpName.inventoryGroupName}"/></option>
                </c:forEach>
             </select>
                   
			<input name="opsEntityId" id="opsEntityId" type="hidden" value="${opsEntityId}"/>
			<input name="currencyId" id="currencyId" type="hidden" value="${currencyId}"/>
		</td>
	</tr>	
	<%-- Row 2 --%>
	<tr>
		<td colspan="2" width="100%" align="center">
	          <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
	          		 onclick="returnInventoryGroup();">
	          <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
	          		 onclick="window.opener.closeTransitWin();window.close();"/>
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
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>