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
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
<fmt:message key="label.manageexpertreviewentities"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
pleaseSelect:"<fmt:message key="error.norowselected"/>",
shipinfo: "<fmt:message key="label.shipinfo"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
genericError:"<fmt:message key="generic.error"/>",
pomrreviewError:"<fmt:message key="error.pomrreview"/>"
};

var showErrors = false;

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var columnConfig = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.select"/>',
  type:"hchstatus",
  align:"center",
  width:4,
  onChange: setModified,
  submit:true
},
{
  columnId:"opsEntityId"
},
{ columnId:"operatingEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width: 20,
  tooltip:"Y"
},
{ 
  columnId:"createdBy"
},
{ columnId:"createdByName",
  columnName:'<fmt:message key="label.createdby"/>',
  tooltip:"Y"
},
{ columnId:"createdDate",
  columnName:'<fmt:message key="label.datecreated"/>',
  tooltip:"Y",
  width: 10
},
{
  columnId:"poReview",
  columnName:'<fmt:message key="label.poreview"/>',
  type:"hchstatus",
  align:"center",
  width:4,
  onChange: setModified,
  submit:true
},
{
  columnId:"mrReview",
  columnName:'<fmt:message key="label.mrreview"/>',
  type:"hchstatus",
  align:"center",
  width:4,
  onChange: setModified,
  submit:true
},
{
	columnId:"modified",
	type:"hchstatus",
	submit:true
}
];

var gridConfig = {
		divName:'expertReviewEntityBean',
		beanData:'jsonMainData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: false,
		submitDefault:true
};

function resultOnLoad() {
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("expertReviewEntityBean").style["display"] = "";
		initGridWithGlobal(gridConfig); 
	}
	else {
		document.getElementById("expertReviewEntityBean").style["display"] = "none";
	}
	
	//setResultFrameSize();
	if (showUpdateLinks) {
		$("mainUpdateLinks").style.display = "";
	}
	
	if (showErrors) {
		$("errorMessagesArea").style.display = "";
	}
}

function setModified(rowId, colId) {
	var checkbox = $("modified"+rowId);
	if (checkbox != null) {
		checkbox.checked = true;
	}
}

function validate() {
	var valid = true;
	
	var totallines = $v("totalLines");
	for ( var i = 1; i <= totallines; i++) {
		try {
			var select = $("ok"+i);
			var poReview = $("poReview"+i);
			var mrReview = $("mrReview"+i);
			if (select.checked) {
				valid = poReview.checked || mrReview.checked;
				if ( ! valid) {
					alert(messagesData.pomrreviewError);
					break;
				}
			}
		}
		catch(ex){
			valid = false;
			alert(messagesData.genericError);
		}
	}
	return valid;
}

function updateLists() {
	if (validate()) {
		beanGrid.parentFormOnSubmit();
		$('uAction').value = "update";
		document.genericForm.submit();
	}
}
// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames();">

<tcmis:form action="/expertreviewentitylist.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"><html:errors/>${tcmisError}</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmisError }">
     showErrors = false;
    </c:when>
    <c:otherwise>
     showErrors = true;
    </c:otherwise>
   </c:choose>   
    //-->
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<table>
<tr>
<td class="optionTitleBoldRight"><fmt:message key="label.operatingentity"/>:</td>
<td><input id="expertReviewId" name="expertReviewId" type="text" class="inputBox" readonly value="<tcmis:jsReplace value="${param.expertReviewId}" processMultiLines="false" />"/></td>
</tr>
<tr>
<td class="optionTitleBoldRight"><fmt:message key="label.description"/>:</td>
<td><tcmis:jsReplace value="${description}" processMultiLines="false" /></td>
</tr>
</table>
<div class="backGroundContent">

<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv"> 
<!-- Search results start -->
<div class="roundcont filterContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateLink"><a href="#" onclick="updateLists();"><fmt:message key="label.update"/></a></span>
      </div>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
		<div id="expertReviewEntityBean" style="width:100%;height:300px;" style="display: none;"></div>
		<!-- If the collection is empty say no data found -->

<c:if test="${empty expertReviewEntityColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
	</div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>  
</div><!-- Result Frame Ends -->


<c:if test="${expertReviewEntityColl != null}">
<c:set var="editable" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="ExpertReviewAdmin" opsEntityId="">
<c:set var="editable" value='Y'/>
</tcmis:opsEntityPermission>
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty expertReviewEntityColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="expertReviewEntity" items="${expertReviewEntityColl}" varStatus="status">
<c:if test="${dataCount >= 1}">,</c:if>
      
<fmt:formatDate var="fmtDateCreated" value="${status.current.dateCreated}" pattern="${dateTimeFormatPattern}"/>

/*The row ID needs to start with 1 per their design.*/
{ id:${dataCount+1},
 data:['${editable}',
 	${not empty fmtDateCreated},
 	'<tcmis:jsReplace value="${status.current.opsEntityId}" processMultiLines="false"/>',
 	'<tcmis:jsReplace value="${status.current.operatingEntityName}" processMultiLines="false"/>',
 	'${status.current.createdBy}',
 	'<tcmis:jsReplace value="${status.current.createdByName}" processMultiLines="false"/>',
 	'${fmtDateCreated}',
 	${status.current.poReview},
 	${status.current.mrReview},
 	${false}
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

 <!-- Search results end --> 
</c:if>



    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
        <input name="uAction" id="uAction" value="" type="hidden"/>
        <input name="minHeight" id="minHeight" type="hidden" value="100"/>
        <input name="expertReviewer" id="expertReviewer" type="hidden" value="${readonly}"/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${editable == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</c:if>

</tcmis:form>
</body>
</html:html>
