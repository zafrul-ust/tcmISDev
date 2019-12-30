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
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/pagename.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.dotinfo"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<!--Uncomment for production-->
<%--<tcmis:form action="/pagename.do" onsubmit="return submitFrameOnlyOnce();">--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="pageNameUserGroup" facilityId="${param.testingField}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<!--Uncomment for production-->
<%--<c:if test="${pageNameViewBeanCollection != null}" >--%>
<!-- Search results start -->
<input type="hidden" name="Action" value="Details">
<input type="hidden" name="totalComps" value="0">

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <!--Uncomment for production-->
 <%--<c:if test="${!empty pageNameViewBeanCollection}" >--%>

<table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.materialid"/>:
 		</td>
	 	<td width="15%" class="optionTitleLeft">
			<input type="hidden" name="materialId" id="materialId" value="<c:out value="${dotinfoBean.materialId}"/>">
			<c:out value="${dotinfoBean.materialId}"/>
			<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="msdsbutton" onClick="ShowMSDS('10')" value="<fmt:message key="label.msds"/>" OnClick="ShowMSDS('10')" type="button">
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.material"/> <fmt:message key="label.description"/>:
		</td>
		<td width="25%" class="optionTitleLeft">
			<!-- query value -->
			<c:out value="${dotinfoBean.materialDesc}"/>
		</td>
	</tr>
	<tr class="">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.packaging"/>:
		</td>
		<td width="15%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.packaging}"/>
		</td>	
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.netWeightlb"/>:
		</td>
		<td width="25%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.weight}"/>
			<input type="hidden" name="weight" id="weight" value="<c:out value="${dotinfoBean.weight}"/>"/>
		</td>
	</tr>
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.lastUpdatedBy"/>:
		</td>
		<td width="15%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.lastUpdatedBy}"/>
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.laastUpdatedDate"/>:
		</td>
		<td width="25%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.lastUpdatedDate}"/>
		</td>
	</tr>
</table>
<br/><br/>
<table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.properShippingName"/>:
		</td>
		<td colspan="3" width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.groundShipping}"/>
			<input type="hidden" name="groundShipping" id="groundShipping" value="<c:out value="${dotinfoBean.groundShipping}"/>"/>
			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="properShpnameLike" value="<fmt:message key="label.lookUp"/>"/>
		</td>
	</tr>
	<tr class="">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.technicalName"/>:
		</td>
		<td colspan="3" width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.technical}"/>
			<input type="hidden" name="technical" id="technical" value="<c:out value="${dotinfoBean.technical}"/>"/>
			<input type="hidden" name="hazmatId" id="hazmatId" value="<c:out value="${dotinfoBean.hazmatId}"/>">
			<input type="hidden" name="symbol" id="symbol" value="<c:out value="${dotinfoBean.symbol}"/>">
		</td>
	</tr>
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.hazardclass"/>:
		</td>
		<td width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.hazardClass}"/>
			<input type="hidden" name="hazardClass" id="hazardClass" value="<c:out value="${dotinfoBean.hazardClass}"/>"/>
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.packing"/> <fmt:message key="label.group"/>
		</td>
		<td width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.packingGroup}"/>
			<input type="hidden" name="packingGroup" id="packingGroup" value="<c:out value="${dotinfoBean.packingGroup}"/>"/>
		</td>
	</tr>
	<tr>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.subsidaryRisk"/>:
		</td>
		<td width="10%" class="optionTitleLeft">
			<select name="subsidiaryHazardClass" id="subsidiaryHazardClass">
				<option value="">
					<fmt:message key="label.pleaseselect"/>
				</option>
				<!-- query value -->
			</select>
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.unNaNo"/>: 
		</option>
		<td width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.unNumber}"/>
			<input type="hidden" name="unNumber" id="unNumber" value="<c:out value="${dotinfoBean.unNumber}"/>"/>
		</td>
	</tr>
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.scheduleB"/>:
		</td>
		<td colspan="3" width="10%" class="optionTitleLeft">
			<c:out value="${dotinfoBean.scheduleB}"/>
			<input type="hidden" name="scheduleB" id="scheduleB" value="<c:out value="${dotinfoBean.scheduleB}"/>"/>
		</td>
	</tr>
	<tr class="">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.rqWeightlb"/>:
		</td>
		<td width="10%">
			<input type="text" maxLength="40" name="rqWeightlb" id="rqWeightlb" value="<c:out value="${dotinfoBean.rqWeightlb}"/>" size="5">
		</td>
		<td width="8%" class="optionTitleBoldRight">
			<input type="checkbox" value="<c:out value="${dotinfoBean.pkgGtRq}"/>" name="pkgGtRq" id="pkgGtRq"/>
		</td>
		<td width="20%" class="optionTitleLeft">
			<fmt:message key="label.componentNetWeightRQ"/>
		</td>
	</tr>
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.erg"/>:
		</td>
		<td width="10%" class="optionTitleLeft">
			<input type="text" maxLength="30" name="erg" id="erg" value="<c:out value="${dotinfoBean.erg}"/>" size="15"/>
		</td>
		<td width="2%" class="optionTitleBoldRight">
			<input type="checkbox" value="<c:out value="${dotinfoBean.ormD}"/>" name="ormD" id="ormD"/>
		</td>
		<td width="10%" class="optionTitleLeft">
			<fmt:message key="label.ormD"/>
		</td>
	</tr>
	<tr class="">
		<td width="10%" class="optionTitleBoldRight">
			<input type="checkbox" value="<c:out value="${dotinfoBean.marinePollutant}"/>" name="marinePollutant" id="marinePollutant">
		</td>
		<td width="15%" class="optionTitleLeft">
			<fmt:message key="label.marinePollutant"/>
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<input type="checkbox" value="<c:out value="${dotinfoBean.bulkPkgMarinePollutant}"/>" name="bulkPkgMarinePollutant" id="bulkPkgMarinePollutant"/>
		</td>
		<td width="15%" class="optionTitleLeft">
			<fmt:message key="label.bulkPackaging"/>
		</td>
	</tr>
	<tr class="alt">
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.shipping"/> <fmt:message key="label.remarks"/>:
		</td>
		<td width="15%" class="optionTitleLeft" colspan="3">
			<textarea name="remarks" id="remarks" rows="3" cols="80">
				<c:out value="${dotinfoBean.remarks}"/>
			</textarea>
		</td>
	</tr>
<!-- no orgininal source down here.... -->  
  <!--Uncomment for production-->
    <%--<c:forEach var="pageNameViewBean" items="${pageNameViewBeanCollection}" varStatus="status">--%>

    <!--Uncomment for production-->
    <%--<c:if test="${status.index % 10 == 0}">--%>
    <!-- Need to print the header every 10 rows-->

    <%--Place the table header here --%>
    <%--<tr>
    <th width="5%"><fmt:message key="label.example1"/></th>
    </tr>--%>

    <!--Uncomment for production-->
    <%--</c:if>--%>

    <!--Uncomment for production-->
    <%--
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>
    --%>

    <%--<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%"><c:out value="${status.current.exampleColumn1}"/></td>
     <td width="5%"><c:out value="${status.current.exampleColumn2}"/></td>
   </tr>--%>

   <c:set var="dataCount" value='${dataCount+1}'/>

   <!--Uncomment for production-->
   <%--</c:forEach>--%>
   </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>
   <!-- If the collection is empty say no data found -->

   <!--Uncomment for production-->
   <%--<c:if test="${empty pageNameViewBeanCollection}" >--%>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>

<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>