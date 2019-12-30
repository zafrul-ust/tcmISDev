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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/customertaxexemptioncertificate.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>    

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


<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.taxexemptioncertificate"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--

var shipToCountryAbbrev = new Array(
		<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
		 <c:if test="${status.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status.current.country}"/>',value:'${status.current.countryAbbrev}'}
		</c:forEach>
		);

var shipToStateAbbrev = new Array();

var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,'${status.current.countryAbbrev}'
   </c:when>
   <c:otherwise>
    '${status.current.countryAbbrev}'
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["${currentCountry}"] = new Array(
		  <c:if test="${status.current.countryAbbrev ne 'USA' and status.current.countryAbbrev ne 'CAN'}">
		  	"All"
		  </c:if>
		  <c:if test="${!(status.current.countryAbbrev ne 'USA' and status.current.countryAbbrev ne 'CAN')}">
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  		  </c:if>
  );

  altStateName["${currentCountry}"] = new Array(
		  <c:if test="${status.current.countryAbbrev ne 'USA' and status.current.countryAbbrev ne 'CAN'}">
		  	"All"
		  </c:if>
		  <c:if test="${!(status.current.countryAbbrev ne 'USA' and status.current.countryAbbrev ne 'CAN')}">
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  			</c:if>
  );
</c:forEach>


// -->
</script>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
all:"<fmt:message key="label.all"/>",
taxExemptionCertificate:"<fmt:message key="label.taxexemptioncertificate"/>",
expirationDate:"<fmt:message key="label.expiredate"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var config = [
{
 columnId:"permission"
},
{ columnId:"countryAbbrev",
  columnName:'<fmt:message key="label.country"/>',
  width:12,
  onChange:taxCountryChanged,
  type:"hcoro"
},
{ columnId:"stateAbbrev",
  columnName:'<fmt:message key="label.state"/>',
  width:13,
  type:"hcoro"
},
{ columnId:"taxExemptionCertificate",
  columnName:'<fmt:message key="label.taxexemptioncertificate"/>',
  width:15,  
  size:20,
  type:"hed"
},
{ columnId:"expirationDateStr",
  columnName:'<fmt:message key="label.expiredate"/>',
  hiddenSortingColumn:"hexpirationDate",
  type:"hcal",
  sorting:"int"
},
{ columnId:"customerId",
  submit:true
},
{ columnId:"hexpirationDate",
  sorting:"int"
}
];


// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" onresize="resizeFrames()">

<tcmis:form action="/customertaxexemptioncertificate.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<c:set var="adminPermissions" value="false"/>

<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailAdmin">   
   <c:set var="adminPermissions" value="true"/>   
   <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</tcmis:opsEntityPermission>

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
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
      <div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
      <c:if test="${(adminPermissions== true)}">
       <span id="updateData"><a href="#" onclick="update();"><fmt:message key="label.process"/></a></span>&nbsp;
       <span id="addLink">|&nbsp;<a href="#" onclick="addRecord();"><fmt:message key="label.add"/></a></span>
       <span id="removeLink" style="display: none">|&nbsp;<a href="#" onclick="removeRecord();"><fmt:message key="label.remove"/></a></span>      
      </c:if>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="billToCustTaxExempViewBean" style="height:400px;" style="display: none;"></div>

<c:if test="${billToCustTaxExempViewBeanColl != null}">
<script type="text/javascript">
<!--

<c:set var="permissions" value="Y"/>


//var validTaxCountryAbbrev = new Array('USA','CAN','IRL','DEU');
var stateAbbrev = new Array();
//var countryAbbrev = getValidCountryAbbrev();
var countryAbbrev = new Array(
         <c:forEach var="vvCountryBean" items="${vvValidTaxCountryColl}" varStatus="status2">
		 <c:if test="${status2.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status2.current.country}"/>',value:'${status2.current.countryAbbrev}'}
		</c:forEach>
		);

<c:forEach var="bean" items="${billToCustTaxExempViewBeanColl}" varStatus="status">
	stateAbbrev[${status.index +1}] = buildStateAbbrev('${bean.countryAbbrev}');
</c:forEach>

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty billToCustTaxExempViewBeanColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="billToCustTaxExempViewBeanColl" items="${billToCustTaxExempViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtExpirationDate" value="${status.current.expirationDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtExpirationDateYear" value="${status.current.expirationDate}" pattern="yyyy"/>
<c:if test="${fmtExpirationDateYear == '3000'}">
	<c:set var="fmtExpirationDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

<c:set var="expirationDateSortable" value="${status.current.expirationDate.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${permissions}','${status.current.countryAbbrev}',
       '${status.current.stateAbbrev}','${status.current.taxExemptionCertificate}',
       '${fmtExpirationDate}','${status.current.customerId}','${expirationDateSortable}'
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

 <!-- Search results end -->
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
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="action" id="action" value="" type="hidden"/>
<input name="customerId" id="customerId" value="${customerId}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input name="inDefinite_expirationDateStr" id="inDefinite_expirationDateStr" value="Y" type="hidden"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>