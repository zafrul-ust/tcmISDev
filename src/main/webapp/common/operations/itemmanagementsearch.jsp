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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/common/operations/itemmanagementsearch.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var staticHubName = new Array();
<c:forEach var="hubBean" items="${itemManagementIgOvColl}" varStatus="status">
  staticHubName["<c:out value="${status.current.hub}"/>"] = "<c:out value="${status.current.hubName}"/>";
</c:forEach>

var staticHubId = new Array(
<c:forEach var="hubBean" items="${itemManagementIgOvColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.hub}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.hub}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var staticPlantId = new Array();
<c:forEach var="hubBean" items="${itemManagementIgOvColl}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="plantBldgIgBeanCollection" value='${status.current.plantBldgIg}'/>

  staticPlantId["<c:out value="${currentHub}"/>"] = new
  Array(
  		<c:forEach var="plantBldgIgBean" items="${plantBldgIgBeanCollection}" varStatus="plantStatus">
 			<c:choose>
   				<c:when test="${plantStatus.index > 0}">
        			,"<c:out value="${plantStatus.current.plantId}" escapeXml="false"/>"
   				</c:when>
   				<c:otherwise>
        			"<c:out value="${plantStatus.current.plantId}" escapeXml="false"/>"
  				</c:otherwise>
  			</c:choose>
  		</c:forEach>
  		);
</c:forEach>

var staticBldgId = new Array();

<c:forEach var="hubBean" items="${itemManagementIgOvColl}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="plantBldgIgObjBeanCollection" value='${status.current.plantBldgIg}'/>
  <c:forEach var="plantBean" items="${plantBldgIgObjBeanCollection}" varStatus="plantStatus">
  	<c:set var="currentPlant" value='${plantStatus.current.plantId}'/>
    <c:set var="bldgIgObjBeanCollection" value='${plantStatus.current.bldgIg}'/>
    staticBldgId["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentPlant}" escapeXml="false"/>"] = new
    Array(
      		<c:forEach var="bldgIgObjBean" items="${bldgIgObjBeanCollection}" varStatus="bldgStatus">
        	<c:choose>
   				<c:when test="${bldgStatus.index > 0}">
        			,"<c:out value="${bldgStatus.current.bldgId}" escapeXml="false"/>"
   				</c:when>
   				<c:otherwise>
        			"<c:out value="${bldgStatus.current.bldgId}" escapeXml="false"/>"
  				</c:otherwise>
  			</c:choose>
      		</c:forEach>
      	  );
  </c:forEach>
</c:forEach>

var staticInventoryGroup = new Array();
var staticInventoryGroupName = new Array();
<c:forEach var="hubBean" items="${itemManagementIgOvColl}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="plantBldgIgObjBeanCollection" value='${status.current.plantBldgIg}'/>
  <c:forEach var="plantBean" items="${plantBldgIgObjBeanCollection}" varStatus="plantStatus">
  	<c:set var="currentPlant" value='${plantStatus.current.plantId}'/>
    <c:set var="bldgIgObjBeanCollection" value='${plantStatus.current.bldgIg}'/>
  	<c:forEach var="bldgBean" items="${bldgIgObjBeanCollection}" varStatus="bldgStatus">
    	<c:set var="currentBldg" value='${bldgStatus.current.bldgId}'/>
        <c:set var="initInvGrpBeanCollection" value='${bldgStatus.current.inventoryGroupColl}'/>

      	staticInventoryGroup["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentPlant}" escapeXml="false"/>-<c:out value="${currentBldg}" escapeXml="false"/>"] = new
       	Array(
      		<c:forEach var="inventoryGroupBean" items="${initInvGrpBeanCollection}" varStatus="invGrpStatus">
        	<c:choose>
   				<c:when test="${invGrpStatus.index > 0}">
        			,"<c:out value="${invGrpStatus.current.inventoryGroup}" escapeXml="false"/>"
   				</c:when>
   				<c:otherwise>
        			"<c:out value="${invGrpStatus.current.inventoryGroup}" escapeXml="false"/>"
  				</c:otherwise>
  			</c:choose>
      		</c:forEach>
      		);

      	staticInventoryGroupName["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentPlant}" escapeXml="false"/>-<c:out value="${currentBldg}" escapeXml="false"/>"] = new
       	Array(
      		<c:forEach var="inventoryGroupBean" items="${initInvGrpBeanCollection}" varStatus="invGrpStatus">
        		<c:choose>
   				<c:when test="${invGrpStatus.index > 0}">
        			,"<c:out value="${invGrpStatus.current.inventoryGroupName}" escapeXml="false"/>"
   				</c:when>
   				<c:otherwise>
        			"<c:out value="${invGrpStatus.current.inventoryGroupName}" escapeXml="false"/>"
  				</c:otherwise>
  			</c:choose>
      		</c:forEach>
      		);

    </c:forEach>
  </c:forEach>
</c:forEach>

// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.item"/> <fmt:message key="label.management"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",itemInteger:"<fmt:message key="error.item.integer"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",none:"<fmt:message key="label.none"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/itemmanagementresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea -->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" >
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
  <tr>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.hub"/>:
	</td>
	<td width="85%" colspan="3" class="optionTitleBoldLeft">
         <select name="hub" id="hub" class="selectBox" onchange="hubChanged()">
          <c:forEach var="itemManagementIgOvBean" items="${itemManagementIgOvColl}" varStatus="status">
            <c:set var="currentHub" value='${status.current.hub}'/>
            <c:choose>
            <c:when test="${empty hub}" >
      			<c:set var="hub" value='${status.current.hub}'/>
      			<c:set var="initPlantBldgIgObjCollection" value='${status.current.plantBldgIg}'/>
		    </c:when>
    			<c:when test="${currentHub == hub}" >
      			<c:set var="initPlantBldgIgObjCollection" value='${status.current.plantBldgIg}'/>
    		</c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentHub == hub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
	</td>
</tr>
<tr>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.plant"/>:
	</td>
	<td width="85%" colspan="3" class="optionTitleBoldLeft">
		<select class="selectBox" name="plantId" id="plantId" onchange="plantChanged()">
		<c:forEach var="plantBldgIgObjBean" items="${initPlantBldgIgObjCollection}" varStatus="status">
  			<c:set var="currentPlant" value='${status.current.plantId}'/>
  			<c:choose>
    		<c:when test="${empty plantId}" >
      			<c:set var="initBldgBeanCollection" value='${status.current.bldgIg}'/>
    		</c:when>
    			<c:when test="${currentPlant == plantId}" >
      			<c:set var="initBldgBeanCollection" value='${status.current.bldgIg}'/>
    		</c:when>
  			</c:choose>

            <c:choose>
              <c:when test="${currentPlant == plantId}">
                <option value="<c:out value="${currentPlant}"/>" selected><c:out value="${status.current.plantId}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentPlant}"/>"><c:out value="${status.current.plantId}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.building"/>:
	</td>
	<td  width="85%" colspan="3" class="optionTitleBoldLeft">
		<select class="selectBox" name="bldgId" id="bldgId" onchange="bldgChanged()">

		   <c:forEach var="bldgIgObjBean" items="${initBldgBeanCollection}" varStatus="status">
  			<c:set var="currentBldg" value='${status.current.bldgId}'/>
  			<c:choose>
    		<c:when test="${empty bldgId}" >
      			<c:set var="initInvGrpBeanCollection" value='${status.current.inventoryGroupColl}'/>
    		</c:when>
    		<c:when test="${currentPlant == bldgId}" >
      			<c:set var="initInvGrpBeanCollection" value='${status.current.inventoryGroupColl}'/>
    		</c:when>
  			</c:choose>

            <c:choose>
              <c:when test="${currentBldg == bldgId}">
                <option value="<c:out value="${currentBldg}"/>" selected><c:out value="${status.current.facilityId}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentBldg}"/>"><c:out value="${status.current.facilityId}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.inventorygroup"/>:
	</td>
	<td width="55%" class="optionTitleBoldLeft" colspan="2" >
		<select class="selectBox" name="inventoryGroup" id="inventoryGroup" > <%-- OnChange="getbuildingaddress()"> --%>

		  <c:forEach var="inventoryGroupObjBean" items="${initInvGrpBeanCollection}" varStatus="status">
  			<c:set var="currentInvGrp" value='${status.current.inventoryGroup}'/>

            <c:choose>
              <c:when test="${currentInvGrp == inventoryGroup}">
                <option value="<c:out value="${currentInvGrp}"/>" selected><c:out value="${status.current.inventoryGroupName}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentInvGrp}"/>"><c:out value="${status.current.inventoryGroupName}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
			</c:forEach>
		</select>
	</td>
	<td width="30%" class="optionTitleBoldLeft">
		<%--<div id="showActiveOnlyDiv">
     <input type="checkbox" name="showActiveOnly" id="showActiveOnly" value="Yes" checked="checked"/>
		 &nbsp;&nbsp;
		 <fmt:message key="label.showactiveonly"/>
    </div>--%>
	</td>
</tr>
<tr>
 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:
 		</td>
		<td width="55%" class="optionTitleLeft" nowrap >
 			<html:select property="searchField" styleId="searchField" styleClass="selectBox">
				<html:option  value="itemId" ><fmt:message key="label.itemid"/> </html:option>
				<html:option  value="catPartNo"><fmt:message key="label.partno"/></html:option>
 			</html:select>

 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
				<html:option  value="is" ><fmt:message key="label.is"/></html:option>
				<html:option  value="contains"><fmt:message key="label.contains"/></html:option>
				<html:option  value="startsWith" ><fmt:message key="label.startswith"/></html:option>
				<html:option  value="endsWith"><fmt:message key="label.endswith"/></html:option>
 			</html:select>

   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="15" value="" onkeypress="return onKeyPress()">
			<%-- <button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="idLike" id="idLike" value="..."> --%>
 		</td>
		<td width="30%" class="optionTitleBoldRight" nowrap>
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="showMinMaxOnly" id="showMinMaxOnly" value="Yes" />
				
				<fmt:message key="label.showminmaxonly"/>
		</td>
</tr>
<tr>
	<td width="15%" class="optionTitleRight">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
          		 onclick="return submitSearchForm();">
	<td width="85%" colspan="3" class="optionTitleLeft">
         <input name="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
          		 onclick="generateExcel(); return false;">
	</td>
</tr>
    </table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="userAction" id="userAction" type="hidden">
	<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>