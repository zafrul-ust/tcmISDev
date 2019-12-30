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
<script type="text/javascript" src="/js/common/admin/listapprovalpermissionsearch.js"></script>

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
<fmt:message key="label.listapprovalperms"/> (${fullName})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var facilityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="hubBean" items="${personnelBean.permissionBean.userGroupMemberBeanCollection}" varStatus="status">
  <c:if test="${status.current.userGroupId == 'ListApprovalAdmin'}">
    facilityPermissionArray["<c:out value="${counter}"/>"] = "<c:out value="${status.current.facilityId}" escapeXml="false"/>";
    <c:set var="counter" value='${counter + 1}'/>
  </c:if>
</c:forEach>

<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
<bean:size id="companySize" name="companyFacilityColl"/>
var altCompanyId = new Array(
<c:forEach var="myCompanyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<c:out value="${status.current.companyId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myCompanyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<c:out value="${status.current.companyName}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
  <c:if test="${companySize > 1}">
    altFacilityId["<fmt:message key="label.mycompanies"/>"] = new Array("<fmt:message key="label.selectOne"/>");
    altFacilityName["<fmt:message key="label.mycompanies"/>"] = new Array("<fmt:message key="label.selectOne"/>");
  </c:if>

  <c:forEach var="myCompanyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>

    altFacilityId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "<fmt:message key="label.selectOne"/>","<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );

    altFacilityName["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "<fmt:message key="label.selectOne"/>","<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>




var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectFacility:"<fmt:message key="msg.selectFacility"/>",
selectOne:"<fmt:message key="label.selectOne"/>"};
// -->
</script>

<%-- set height dynamically --%>
<script language="JavaScript" type="text/javascript">
<!--
	var myHeight = 110;    
	<c:if test="${companySize > 1}">
    	myHeight = 137;
	</c:if>
// -->
</script>
<%-- set height dynamically END --%>

</head>

<body bgcolor="#ffffff" onload="parent.document.getElementById('searchFrame').height=myHeight;myOnload();">

<tcmis:form action="/listapprovalpermissionresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<%-- determine whether to display company dropdown --%>
			<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
			<bean:size id="companySize" name="companyFacilityColl"/>
			<input type="hidden" name="companySize" id="companySize" value="${companySize}"/>	
			<c:choose>
				<c:when test="${companySize == 1}">
					 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
						 <input type="hidden" name="companyId" id="companyId" value="${status.current.companyId}"/>
						 <c:set var="facilityCollection" value='${status.current.facilityList}'/>
					 </c:forEach>
				</c:when>
				<c:otherwise>
					 <tr>
							<td width="13%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
							<td width="33%" class="optionTitleLeft">
								<c:set var="selectedCompanyId" value='${param.companyId}'/>
								<select name="companyId" id="companyId" class="selectBox" onChange="CompanyChanged()">
								 <c:choose>
									 <c:when test="${companySize == 0}">
										 <option value="<fmt:message key="label.mycompanies"/>"><fmt:message key="label.mycompanies"/></option>
									 </c:when>
									 <c:otherwise>
										 <c:if test="${companySize > 1}">
											 <option value="<fmt:message key="label.mycompanies"/>" selected><fmt:message key="label.mycompanies"/></option>
											 <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
										 </c:if>

										 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
											 <c:set var="currentCompanyId" value='${status.current.companyId}'/>
												 <c:if test="${empty selectedCompanyId}" >
													 <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
													 <c:set var="facilityCollection" value='${status.current.facilityList}'/>
												 </c:if>
												 <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}" escapeXml="false"/></option>
										 </c:forEach>
									 </c:otherwise>
								 </c:choose>
								</select>
							</td>
						 	<td width="0%">
								&nbsp;
							</td>
						</tr>
				</c:otherwise>
			</c:choose>

      <tr>
        <td width="13%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="33%" class="optionTitleLeft">
					<c:choose>
						 <c:when test="${facilityCollection == null}">
							 <c:set var="facilitySize" value="0"/>
						 </c:when>
						 <c:otherwise>
							 <bean:size id="facilitySize" name="facilityCollection"/>
						 </c:otherwise>
					 </c:choose>
					 <c:set var="selectedFacilityId" value='${param.facilityId}'/>
					 <select name="facilityId" id="facilityId" class="selectBox" onChange="facilityChanged()">
					 <c:choose>
						 <c:when test="${facilitySize == 0}">
							 <option value="<fmt:message key="label.selectOne"/>"><fmt:message key="label.selectOne"/></option>
						 </c:when>
						 <c:otherwise>
							 <c:if test="${facilitySize > 1}">
								 <option value="<fmt:message key="label.selectOne"/>" selected><fmt:message key="label.selectOne"/></option>
								 <c:set var="selectedFacilityId" value="selectOne"/>  <%-- set it to something so I can test it selectedFacilityId is empty below --%>
							 </c:if>
							 <c:forEach var="facilityOvBean" items="${facilityCollection}" varStatus="status">
								 <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
									 <c:if test="${empty selectedFacilityId}" >
										<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
									 </c:if>
									 <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
							 </c:forEach>
						 </c:otherwise>
					 </c:choose>
			</td>
			<td width="5%" class="optionTitleRight">
				<input style="display: none" type="button" class="smallBtns" name="createNewGroup" id="createNewGroup" value="<fmt:message key="label.newlistapproval"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="editFacilityListApproval()"/>
		 	</td>
      </tr>

		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
				<input name="viewType" id="viewTypeMy" type="radio" class="radioBtns" value="MY" checked>
        		<fmt:message key="label.userapproval"/>
        		<input name="viewType" id="viewTypeAll" type="radio" class="radioBtns" value="ALL">
        		<fmt:message key="label.alluserapproval"/>
			</td>
		</tr>

		<tr>
      	<td colspan="3">
				  <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchData();">
				  <input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
				  <input type="button" name="cancel" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="parent.window.close()"/>
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
<input type="hidden" name="action" id="action" value="search"/>
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="companyName" id="companyName" value=""/>	
<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}" />
<input type="hidden" name="displayView" id="displayView" value=""/>
<input type="hidden" name="resultView" id="resultView" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>