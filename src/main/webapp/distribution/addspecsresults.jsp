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
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
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
<script type="text/javascript" src="/js/distribution/addspecsresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
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
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="addspecs.title"/>&nbsp;|&nbsp;<fmt:message key="label.new"/>
</title>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:set var="checkBoxPermission" value='N'/>
<c:set var="showCheckBox" value='N'/>
<c:if test="${showUpdateLink == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    
    //-->
</script>
</c:if>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
returnspec:"<fmt:message key="label.return"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


with(milonic=new menuname("openscratchpad")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.open"/>;url=javascript:openScratchPadsOnRightClick();");
      
}

drawMenus();


var config = [
{ columnId:"permission",
 columnName:''
},
{ columnId:"specLibraryDesc",
  columnName:'<fmt:message key="label.library"/>',
  width:14
},
{ columnId:"specName",
  columnName:'<fmt:message key="label.spec"/>',
  width:14
},
{ columnId:"specVersion",
  columnName:'<fmt:message key="label.revision"/>'
},
{ columnId:"specAmendment",
  columnName:'<fmt:message key="label.amendment"/>'
},
{ columnId:"detail"
},
{ columnId:"coc",
  columnName:''
},
{
 columnId:"coa",	
 columnName:''	
},
{
 columnId:"specLibrary",	
 columnName:''	
},
{ 
 columnId:"specId"
}
];


var fromReceiptSpec = false;
<c:choose>
<c:when test="${fromReceiptSpec eq 'Yes'}">
fromReceiptSpec = true;
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/addspecsresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="specsBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${specsBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty specsBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
<c:set var="readonly" value='Y'/>
		
rows:[
<c:forEach var="specsBeanCollection" items="${specsBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="specName" value="${status.current.specName}" processMultiLines="true" />
<tcmis:jsReplace var="specLibraryDesc" value="${status.current.specLibraryDesc}" processMultiLines="true" />
<tcmis:jsReplace var="specLibrary" value="${status.current.specLibrary}" processMultiLines="true" />

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${readonly}','${specLibraryDesc}','${specName}','${status.current.specVersion}','${status.current.specAmendment}', 
       '${status.current.detail}',
 	   '${status.current.coc}','${status.current.coa}','${specLibrary}','${status.current.specId}'
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty specsBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 
</c:if>

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input name="creditHoldCount" id="creditHoldCount" value="<c:out value="${creditHoldCount}"/>" type="hidden"/>
	<input name="uAction" id="uAction" value="" type="hidden"/>   
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	
	<input name="fromReceiptSpec" id="fromReceiptSpec" type="hidden" value="${param.fromReceiptSpec}"/>	
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>
</body>
</html:html>
