
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
	<script type="text/javascript" src="/js/common/admin/databaseobjectsresults.js"></script>
	
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<title>
	    <fmt:message key="projectdocument.label.customerDisplay"/>
	</title>
		
	<script language="JavaScript" type="text/javascript">
	<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {
		alert:"<fmt:message key="label.alert"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		action:"<fmt:message key="label.action"/>",
		databaseObject:"<fmt:message key="label.databaseobject"/>",
        maximum2000:"<fmt:message key="label.maximum2000"/>",
        notes:"<fmt:message key="label.notes"/>"
		};
	
	var pageId = new Array();
    <c:forEach var="allPageNames" items="${pageCollection}" varStatus="status2">
        <tcmis:jsReplace var="pageName" value="${allPageNames.pageName}"/>
        pageId[${status2.index}]  = {text:'<fmt:message key="${allPageNames.pageId}"/>', value:'${allPageNames.pageId}'};
    </c:forEach>
	<%--
	with(milonic=new menuname("rightClickMenu")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.open"/> <fmt:message key="label.mr"/>;url=javascript:openMr();");
	}

	drawMenus();
    --%>
    <c:set var="databaseObjectName"><fmt:message key="label.databaseobject"/></c:set>
	  <c:set var="pageIdVar" value ='pageId'></c:set>
	var config = [
	    {
	    	columnId:"permission"
	    },
		{
			columnId:"pageIdPermission"
		}, 
		{
			columnId:"actionPermission"
		}, 
		{
		  columnId:"dataBaseObjectPermission"
		}, 
		{
		  columnId:"notesPermission"
		},
		{
			columnId:"isAddLine"
		},
		{
			columnId:"pageObjectId"
		},
		{ columnId:"pageId",
		  columnName:'<fmt:message key="label.page"/>',
		  type:'hcoro', 
		  tooltip:"Y",
		  width:10
		},
		{
		  columnId:"okDoUpdate",
		  columnName:'<fmt:message key="label.ok"/>',
		  type:'hchstatus',  // checkbox:The value is string "true" if checked
		   align:'center',
		   onChange:callValidateLinefunction, // onCheck only works for hch, but we don't use hch for Haas
		   width:3
		},
		{ columnId:"action",
		  columnName:'<fmt:message key="label.action"/>',
		  type:'hed', 
		  tooltip:"Y",
		  width:20,
		  size:30
		},
		{ columnId:"databaseobject",
		  columnName:'<tcmis:jsReplace value="${databaseObjectName}"/>',
		  type:'hed', 
		  width:20, // the width of this cell
		  size:45,  // the size of the input field
		  tooltip:"Y"
		},
		{ columnId:"notes",
		  columnName:'<fmt:message key="label.notes"/>',
		  type:"txt",
		  tooltip:"Y",
		  width:30
		}
	];
	
	//-->
	</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
	<tcmis:form action="/databaseobjectsresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display:none;">
			<html:errors/>
			${tcmISError}
			<c:forEach items="${tcmISErrors}" varStatus="status">
				${status.current}<br/>
			</c:forEach>        
		</div>

		<script type="text/javascript">
			<!--
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
        <c:set var="dataCount" value='${0}'/>
        <c:set var="readonly" value='N'/>
        <c:set var="showUpdateLink" value='N'/>
        <tcmis:opsEntityPermission indicator="true" userGroupId="developers">
        <c:set var="readonly" value='Y'/>
        <c:set var="showUpdateLink" value='Y'/>
        </tcmis:opsEntityPermission>
        <tcmis:opsEntityPermission indicator="true" userGroupId="operationsSupport">
        <c:set var="readonly" value='Y'/>
        <c:set var="showUpdateLink" value='Y'/>
        </tcmis:opsEntityPermission>
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="pageDatabaseObjectViewBean" style="width:100%;height:400px;" style="display: none;"></div>
				<c:set var="colorClass" value=''/>
				<c:if test="${databaseObjectsViewBeanCollection != null}">
					<script type="text/javascript">
						<!--
						<c:if test="${!empty databaseObjectsViewBeanCollection}" >
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="viewBean" items="${databaseObjectsViewBeanCollection}" varStatus="status">
									<c:if test="${status.index > 0}">,</c:if>
									<%--
									/*The row ID needs to start with 1 per their design.*/
									/* Use tcmis:jsReplace, to escape special characters */ 
									--%>
									{ id:${status.index +1},
									  data:['${readonly}',
										'N',
										'${readonly}',
										'${readonly}',
										'${readonly}',
										false,
										'${viewBean.pageObjectId}',
									  	'${viewBean.pageId}',
									  	false,
									  	'<tcmis:jsReplace value="${viewBean.pageAction}" processMultiLines="true" />',
									  	'<tcmis:jsReplace value="${viewBean.databaseObjectName}" processMultiLines="true" />',
                                        '<tcmis:jsReplace value="${viewBean.notes}" processMultiLines="true" />'    
										]
									}
									<c:set var="dataCount" value='${dataCount+1}'/> 
								 </c:forEach>
								]};
							</c:if>
						//-->   
					</script>

					<!-- If the collection is empty say no data found -->
<%--				<c:if test="${empty databaseObjectsViewBeanCollection}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:if>   --%>	
					<!-- Search results end -->
				</c:if>


				<!-- Hidden element start --> 
				<div id="hiddenElements" style="display: none;">    			
					<input name="totalLines" id="totalLines" value="<c:out value="${dataCount+1}"/>" type="hidden"/>
					<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
				
					<!-- Hidden elements for re-search purpose -->	
					<input name="searchField" id="searchField" type="hidden" value="${param.searchField}"/>
					<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"/>
					<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"/>
                    <input name="pageField" id="pageField" type="hidden" value="${param.pageField}"/>

					<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
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