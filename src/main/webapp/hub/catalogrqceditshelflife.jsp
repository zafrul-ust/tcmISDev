<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>
<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>  

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- For Calendar support for column type hcal-->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/catalogrqceditshelflife.js"></script>  


<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<script src="/js/jquery/jquery-1.6.4.js"></script>


<title><fmt:message key="label.editshelflife" /></title>

<script language="JavaScript" type="text/javascript">


var messagesData={
		positive:"<fmt:message key="label.positivenumber"/>",
		shelflifedays:"<fmt:message key="label.shelflife(days)"/>",
		allrequired:"<fmt:message key="errors.allrequired"/>"
}
</script>
</head>
<body <c:if test="${updateComplete}">onload="updateAndClose();"</c:if>>
<tcmis:form action="/catalogrqceditshelflife.do" onsubmit="return submitFrameOnlyOnce();">
<c:set var="catRQCPermission" value='Yes'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${param.hub}">
 	<c:set var="catRQCPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>



<!-- Search Option Begins -->
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
     <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space. 
      --%>
      <div id="mainUpdateLinks" <c:if test="${catRQCPermission ne 'Yes'}">style="display: none"</c:if>> <%-- mainUpdateLinks Begins --%>
        <span id="updateLink"><a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a></span> | 
        <span id="cancelLink"><a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a></span>
     </div> <%-- mainUpdateLinks Ends --%>
     </div>
     <div class="dataContent"> 
	<c:set var="tab" value="${1}"/>
		<c:if test="${param.catPartNo}">
     	<span style="font-weight:bold"><fmt:message key="label.partnumber"/>: </span><span><c:out value="${param.catPartNo}"/></span>
     	</c:if>
     	<tcmis:permission indicator="true" userGroupId="UpdateAllDistShelfLife">
     		<c:if test="${distributionIG}">
		     	<label for="applyToAllDist" class="optionTitleBoldLeft">
		     	<input type="checkbox" id="applyToAllDist" name="applyToAllDist" onclick="updateAllDistSelected();"></input>
		     	<fmt:message key="label.updatealldistig"/>
		     	</label>
	     	</c:if>
	    </tcmis:permission>
     	<br/>
     	<br/>
     	
		<c:if test="${not inseparable}">
		<div id="itemTabs" class="haasTabs" style="min-width:400px;background-color:transparent;overflow-x:auto;white-space:nowrap">
			<ul>
				<c:forEach var="comp" items="${components}" varStatus="status">
					<c:set var="tab" value="${status.count}"/>
					<li>
						<a id="itemLink${tab-1}" class="<c:choose><c:when test="${tab == 1}">selectedTab</c:when><c:otherwise>tabLeftSide</c:otherwise></c:choose>" onclick="toggleItem('${tab-1}');" href="#">
							<span class="<c:choose><c:when test="${tab == 1}">selectedSpanTab</c:when><c:otherwise>tabRightSide</c:otherwise></c:choose>" id="itemLinkSpan${tab-1}">
								<img class="iconImage" src="/images/spacer14.gif">
								<fmt:message key="label.component"/> ${tab}
							</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	    </c:if>
	    <c:forEach var="part" items="${components}" varStatus="status">
	    <div id="itemDiv${status.index}" <c:if test="${status.index gt 0}">style="display:none"</c:if>>
	    <table style="width:96%;border:1px solid gray">
	    	<c:if test="${not empty status.current.shelfLifeDays}">
	    	<tr>
	    	<td style="width:30%;text-align:right">
	     	<span class="optionTitleBoldRight"><fmt:message key="label.currentshelflife"/>: </span>
	     	</td>
	     	<td style="width:70%">
	     	<span style="color:gray">
	     		<c:if test="${status.current.shelfLifeDays eq -1}">
	     			<fmt:message key="label.indefinite"/>
	     		</c:if>
	     		<c:if test="${status.current.shelfLifeDays gt -1}">
	     			<c:out value="${status.current.shelfLifeDays}"/>
	     		</c:if>
	     		<c:out value="${status.current.shelfLifeBasisDesc}"/>
	     		<c:if test="${not empty status.current.storageTemp }">
	     			<c:out value=" @ ${status.current.storageTemp}"/>
	     		</c:if>
	     	</span>
	     	</td>
	     	</tr>
	     	</c:if>
	     	<tr>
	     	<td style="width:30%;text-align:right">
	     	<label for="part[${status.index}].shelfLifeDays" class="optionTitleBoldRight"><fmt:message key="label.shelflife(days)"/>: </label>
	     	</td>
	     	<td style="width:70%">
	     	<input type="text" id="part[${status.index}].shelfLifeDays" name="part[${status.index}].shelfLifeDays" value="${status.current.shelfLifeDays}" />
	     	</td>
	     	</tr>
	     	<tr>
	     	<td style="width:30%;text-align:right">
	     	<label for="part[${status.index}].shelfLifeBasis" class="optionTitleBoldRight"><fmt:message key="label.basis"/>: </label>
	     	</td>
	     	<td style="width:70%">
	     	<select id="part[${status.index}].shelfLifeBasis" name="part[${status.index}].shelfLifeBasis">
	     		<c:forEach var="basis" items="${vvShelfLifeBasis}" varStatus="basisStatus">
	     		<option value="${basisStatus.current.shelfLifeBasis}" ${basisStatus.current.shelfLifeBasis==status.current.shelfLifeBasis?'selected':''}>${basisStatus.current.shelfLifeBasisDesc}</option>
	     		</c:forEach>
	     	</select>
	     	</td>
	     	</tr>
	     	<tr>
	     	<td style="width:30%;text-align:right">
	     	<label for="part[${status.index}].storageTemp" class="optionTitleBoldRight"><fmt:message key="label.storagetemp"/>: </label>
	     	</td>
	     	<td style="width:70%">
	     	<select id="part[${status.index}].storageTemp" name="part[${status.index}].storageTemp">
	     	<c:choose>
	     	<c:when test="${empty vvStorageTemp}">
	     		<c:forEach var="temp" items="${vvCatalogStorageTemp}" varStatus="stStatus">
	     		<tcmis:jsReplace var="stgTemp" value='${stStatus.current.customerTemperatureId}'/>
				<option value="${stgTemp}" ${stgTemp eq status.current.customerTemperatureId?'selected':''}>${stStatus.current.displayTemp}</option>	     		
	     		</c:forEach>
	     	</c:when>
	     	<c:otherwise>
	     		<c:forEach var="temp" items="${vvStorageTemp}" varStatus="stStatus">
	     		<tcmis:jsReplace var="stgTemp" value='${stStatus.current.temperature}'/>
				<option value="${stgTemp}" ${stgTemp==status.current.storageTemp?'selected':''}>${stgTemp}</option>	     		
	     		</c:forEach>
	     	</c:otherwise>
	     	</c:choose>
	     	</select>
	     	</td>
	     	</tr>
	     	<tr>
	     	<td style="width:30%;text-align:right">
	     	<label for="part[${status.index}].source" class="optionTitleBoldRight"><fmt:message key="label.source"/>: </label>
	     	</td>
	     	<td style="width:70%">
	     	<select id="part[${status.index}].source" name="part[${status.index}].source">
	     		<c:forEach var="source" items="${vvSource}" varStatus="srcStatus">
	     		<tcmis:jsReplace var="srcVal" value='${srcStatus.current}'/>
				<option value="${srcVal}" ${srcVal==status.current.source?'selected':''}>${srcVal}</option>	     		
	     		</c:forEach>
	     	</select>
	     	</td>
	     	</tr>
	     	<c:if test="${labelColorRequired}">
	     	<tr>
	     	<td style="width:30%;text-align:right">
	     	<label for="part[${status.index}].labelColor" class="optionTitleBoldRight"><fmt:message key="label.labelcolor"/>: </label>
	     	</td>
	     	<td style="width:70%">
	     	<select id="part[${status.index}].labelColor" name="part[${status.index}].labelColor">
	     		<c:forEach var="labelColor" items="${vvLabelColor}" varStatus="lcStatus">
	     		<tcmis:jsReplace var="lcVal" value='${lcStatus.current}'/>
				<option value="${lcVal}" ${lcVal==status.current.labelColor?'selected':''}>${lcVal}</option>	     		
	     		</c:forEach>
	     	</select>
	     	<!-- <input type="text" id="part[${status.index}].labelColor" name="part[${status.index}].labelColor" value="${status.current.labelColor}"/>-->
	     	</td>
	     	</tr>
	     	</c:if>
	     	<tr>
	     	<td style="width:100%" colspan="2">
	     	<label for="part[${status.index}].comments" class="optionTitleBoldLeft"><fmt:message key="label.comments"/>: </label>
	     	</td>
	     	</tr>
	     	<tr>
	     	<td style="width:100%" colspan="2">
	     	<textarea id="part[${status.index}].comments" name="part[${status.index}].comments" rows="4" style="width:98%"></textarea>
	     	</td>
	     	</tr>
	     </table>
	     </div>
     	</c:forEach>
     </div>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
<!-- Search Option Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="uAction" id="uAction" type="hidden" value=""/>
	<input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>
	<input name="hub" id="hub" type="hidden" value="${param.hub}"/>
	<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
	<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
	<input name="catalogId" id="catalogId" type="hidden" value="${param.catalogId}"/>
	<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${param.catalogCompanyId}"/>
	<input name="caller" id="caller" type="hidden" value="${param.caller}"/>
	<input name="totalParts" id="totalParts" type="hidden" value="${tab}"/>
</div>
<!-- Hidden elements end -->

</tcmis:form>
</body>
</html:html>