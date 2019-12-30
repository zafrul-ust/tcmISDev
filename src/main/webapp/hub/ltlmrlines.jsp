<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>

<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/js/hub/ltlmrlines.js"></script>

<title>MR Lines for Consolidation # <c:out value="${param.consolidationNumber}"/></title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	pleaseselect:"<fmt:message key="error.norowselected"/>"
};

ltlmrlines.columnConfig = [ 
	{columnId:"permission"},  
    {columnId:"originalSupplier", columnName:"<fmt:message key="label.mrnumber"/>"},
    {columnId:"problemChanged", columnName:"<fmt:message key="label.lineitem"/>"}
];
       
ltlmrlines.gridConfig = {
	divName:'MrLineBean', // the div id to contain the grid. ALSO the var name for the submitted data
	beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beangrid', // the grid's name, for later reference/usage
	config:ltlmrlines.columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan: false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	submitDefault: false, // the fields in grid are defaulted to be submitted or not.
	noSmartRender: true
};

//-->
</script>
</head>
<body onresize="resizeFrames()">
<div class="interface" id="mainPage">

<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<div id="resultGridDiv">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
	<div class="roundcont contentContainer">
		<div class="roundright">
			<div class="roundtop">
				<div class="roundtopright">
					<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
				</div>
			</div>
			<div class="roundContent">
				<div class="boxhead">
					<div id="mainUpdateLinks">
						<a href="#" onclick="window.close()"><fmt:message key="label.close"/></a>
					</div>
				</div>
				<div class="dataContent"> 
					<div id="MrLineBean" style="width: 100%; height: 375px;" style="display: none;"></div>
					<c:choose>
					<c:when test="${empty mrlineCollection}">
						<%-- If the collection is empty say no data found --%> 
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%"><fmt:message key="main.nodatafound" /></td>
							</tr>
						</table>
					</c:when> 
					<c:otherwise>
					<script>
					<!--
					<c:set var="lineCount" value="${0}"/>
					var jsonMainData = {
							rows:[
							<c:forEach var="mrline" items="${mrlineCollection}" varStatus="status">
									<c:set var="lineCount" value='${lineCount+1}'/>

                                    <c:if test="${lineCount > 1}">,</c:if>
									{id:${lineCount},
									 data:['N',
									  '${mrline.prNumber}',
									  '${mrline.lineItem}'
                                      ]}
							</c:forEach>
							]};					
					//-->
					</script>
					</c:otherwise>
					</c:choose>
				</div>
				<div id="footer" class="messageBar"></div>
			</div>
			<div class="roundbottom">
				<div class="roundbottomright">
					<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
				</div>
			</div>
		</div>
	</div>
</td>
</tr>
</table>
</div>
</div>

<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="totalLines" id="totalLines" value="${lineCount}"/>
</div>
</div>
</body>

</html:html>
