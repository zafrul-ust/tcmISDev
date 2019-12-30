<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/dhtmlxCombo/codebase/dhtmlxcombo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_combo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/subrisk.js"></script>
    
<title>
	<fmt:message key="title.subsrisk">
		<fmt:param>
			${param.itemId}
		</fmt:param>
		<fmt:param>
			${param.type}
		</fmt:param>
	</fmt:message>
</title>

<script language="JavaScript" type="text/javascript">
// add all the javascript messages here, this for internationalization of client
// side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		errors:"<fmt:message key="label.errors"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		pleaseselect:"<fmt:message key="label.pleaseselect"/>",  
		alreadyselected:"<fmt:message key="label.alreadyselected"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		norowselected:'<fmt:message key="error.norowselected"/>',
		pleaseselectorenter:'<fmt:message key="label.pleaseselectorenter"/>'
};

</script>

</head>

<body bgcolor="#ffffff" onload="myOnload();" onresize="resizeFrames()" onunload="try{opener.closeTransitWin();}catch(ex){}">

<tcmis:form action="/subrisk.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
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
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>  
      <div id="mainUpdateLinks">     
<c:set var="pagePermission" value="N"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="shippingInfo">   
   <c:set var="pagePermission" value="Y"/>
</tcmis:opsEntityPermission>

<c:if test="${ pagePermission eq 'Y' }"> 
<a href="#" onclick="addNewLine()"><fmt:message key="label.addline"/></a>	
<span id="removeLineSpan" style="display:none">
| <a href="#" onclick="removeLine();"><fmt:message key="label.removeLine"/></a>
</span> 
| <a href="#" onclick="returnSelected();"><fmt:message key="label.returnselected"/></a>
<br/>
</c:if>

     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<div id="hazardClassBean" style="height:400px;display: ;" ></div>
<script type="text/javascript">

<c:set var="extra" value="0"/>

var hazardClass = new Array(
	{text:messagesData.pleaseselect,value:''}
	<c:forEach var="hazardClassBean" items="${hazardClassColl}" varStatus="status">
    	,{text:'${status.current.hazardClass} (<tcmis:jsReplace value="${status.current.hazardClassDesc}" processMultiLines="true"/>)',value:'${status.current.hazardClass}'}
	</c:forEach>
); 

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
var jsonData = {
    rows:[
    ]};


var config = [
  {
  	columnId:"permission"
  },
  {
	 columnId:"hazardClass",
	 columnName:'<fmt:message key="label.hazardclass"/>',
	 type:"hcoro",
	 width:66
  }
];


</script>

<!-- Search results end -->
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
<input name="totalLines" id="totalLines" value="1" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="riskString" id="riskString" value="${param.riskString}" type="hidden"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${pagePermission == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>
