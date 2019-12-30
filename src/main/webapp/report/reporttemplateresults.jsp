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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/report/reporttemplateresults.js"></script>

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
    <fmt:message key="label.reporttemplate"/>
</title>

<script type="text/javascript">
    <!--
   	 showUpdateLinks = false;
    
    //-->
</script>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
selectedUser:"<fmt:message key="label.selecteduser"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
usage:"<fmt:message key="label.usage"/>",
materialMatrix:"<fmt:message key="label.materialmatrix"/>",
waste:"<fmt:message key="label.waste"/>",
cost:"<fmt:message key="label.cost"/>",
openReport:"<fmt:message key="label.openreport"/>",
deleteTemplate:"<fmt:message key="label.deletereport"/>",
inactivateTemplate:"<fmt:message key="label.inactivetemplate"/>",
activateTemplate:"<fmt:message key="label.activatetemplate"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"permission",
  columnName:'',
  width:0
},
{ columnId:"templateNameVersion",
  columnName:'<fmt:message key="label.report"/><br>(<fmt:message key="label.version"/>)',
  sorting:"haasStr",
  align:'center',
  width:15
},
{ columnId:"description",
  columnName:'<fmt:message key="label.description"/>',
  sorting:"haasStr",
  width:60
},
{ columnId:"reportTypeDisplay",
  columnName:'<fmt:message key="label.reporttype"/>',
  sorting:"haasStr",
  width:8
},
{ columnId:"createdBy",
  columnName:'<fmt:message key="label.createdby"/><br><fmt:message key="label.date"/>',
  sorting:"haasStr",
  width:8
},
{ columnId:"lastUpdated",
  columnName:'<fmt:message key="label.lastupdated"/><br><fmt:message key="label.date"/>',
  width:8
},
{ columnId:"templateId",
  columnName:''
},
{ columnId:"urlPageArg",
  columnName:''
},
{ columnId:"allowEdit",
  columnName:''
},
{ columnId:"owner",
  columnName:''
},
{ columnId:"status",
  columnName:''
},
{ columnId:"templateName",
  columnName:''
},
{ columnId:"reportType",
  columnName:''
},
{ columnId:"globalizationLabelLetter",
  columnName:''
},
{ columnId:"pageId",
  columnName:''
}

];

with(milonic=new menuname("rightClickedMenu")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.openreport"/>;url=javascript:openReportTemplate();");
}

drawMenus();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/reporttemplateresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="reportTemplateDiv" style="width:100%;height:400px;" style="display: none;"></div>
<c:if test="${reportTemplateColl != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty reportTemplateColl}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
	<c:forEach var="templateBean" items="${reportTemplateColl}" varStatus="status">
	   <tcmis:jsReplace var="tmpName" value="${templateBean.templateName}" processMultiLines="false" />
	   <tcmis:jsReplace var="tmpDesc" value="${templateBean.templateDescription}" processMultiLines="false" />
       <tcmis:jsReplace var="userGroupDesc" value="${templateBean.userGroupDesc}" processMultiLines="false" /> 
       <fmt:formatDate var="fmtLastModified" value="${status.current.lastModifiedOn}" pattern="${dateFormatPattern}"/>
	   <fmt:formatDate var="fmtDateCreated" value="${status.current.dateCreated}" pattern="${dateFormatPattern}"/>
       <c:set var="tmpVersion" value=''/>
	   <c:if test="${templateBean.owner == 'PERSONNEL_ID'}">
	 		<c:set var="tmpVersion" value='${templateBean.createdByName}'/>
		</c:if>
	   <c:if test="${templateBean.owner == 'USER_GROUP_ID'}">
	 		<c:set var="tmpVersion" value='${userGroupDesc}'/>
		</c:if>
	   <c:if test="${templateBean.owner == 'COMPANY_ID'}">
	 		<c:set var="tmpVersion" value='${templateBean.companyName}'/>
		</c:if>
       <c:set var="nameNVersion"><fmt:message key="${templateBean.globalizationLabelLetter}"/>${templateBean.templateId}-${tmpName}<br>(${tmpVersion})</c:set>
       <c:if test="${empty tmpVersion}">
	 		<c:set var="tmpVersion"><fmt:message key="label.standardmenupage"/></c:set>
            <%--this handle internationalization for reqular page --%>
            <c:set var="tmpName"><fmt:message key="${templateBean.pageId}"/></c:set>
            <c:set var="nameNVersion">${tmpName}<br>(${tmpVersion})</c:set>
        </c:if>

		<c:if test="${status.index > 0}">,</c:if>
		{ id:${status.index +1},
	 		data:['Y',
	  			   '${nameNVersion}',
				   '${tmpDesc}',
				   '<fmt:message key="${templateBean.globalizationLabel}"/>',
				   '${templateBean.createdByName}<br>${fmtDateCreated}',
				   '${templateBean.lastModifiedByName}<br>${fmtLastModified}',
				   '${templateBean.templateId}',
				   '${templateBean.urlPageArg}',
				   '${templateBean.allowEdit}',
				   '${templateBean.owner}',
				   '${templateBean.status}',
				   '${tmpName}',
				   '${templateBean.reportType}',
				   '<fmt:message key="${templateBean.globalizationLabelLetter}"/>',
                   '${templateBean.pageId}'
             ]}
	 	<c:set var="dataCount" value='${dataCount+1}'/>
	 </c:forEach>
]};
</c:if>
//-->   
</script>
<c:if test="${showLegend == 'true'}">
<script type="text/javascript">

<!--

parent.document.getElementById("showlegendLink").style["display"] = "";

//-->   
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty reportTemplateColl}">
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
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
	<input name="action" id="action" value="" type="hidden">
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>