<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/inputaddress.js"></script>

<script type="text/javascript">
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
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
	<c:set var="curState" value="${status1.current.stateAbbrev}"/>
  	<c:if test="${ curState == 'None' }">
  		<c:set var="curState" value=""/>
  	</c:if>
   <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${curState}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${curState}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["${currentCountry}"] = new Array(
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
  );
</c:forEach>


/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.onetimeaddress"/>-${param.tabName}
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
countryRequired:"<fmt:message key="label.country"/>",
addressRequired:"<fmt:message key="label.address"/>",
cityRequired:"<fmt:message key="label.city"/>",
stateRequired:"<fmt:message key="label.state"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
all:"<fmt:message key="label.all"/>",
shipto:"<fmt:message key="label.shipto"/> ",
mustbeanumber:"<fmt:message key="label.mustbeanumberinthisfield"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
addressline1:"<fmt:message key="label.addressline1"/>",
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>"
};

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="myOnload();document.genericForm.addressLine1.focus();" onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/inputaddress.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
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

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
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
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
	<c:choose>
    	<c:when test="${param.country != null}" >
          <c:set var="selectedCountry" value='${param.country}'/>
    	</c:when>
        <c:otherwise>
                <c:set var="selectedCountry" value='USA'/>
                <tcmis:isCNServer>
					<c:set var="selectedCountry" value='CHN'/>
				</tcmis:isCNServer> 
        </c:otherwise>
     </c:choose>

<fieldset>
<legend><fmt:message key="label.address"/></legend>

<table>
<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.fulladdress"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
	</td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="addressLine1" id="addressLine1" value="<tcmis:inputTextReplace value="${param.addressLine1}" />" size="30" maxlength="100"/>
	</td>
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.country"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
	</td>
    <td width="15%" class="optionTitle">
    	<select name="countryAbbrev" id="countryAbbrev" class="selectBox" onchange="countryChanged();">
          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="selectedCountry" value='${status.current.countryAbbrev}'/>
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentCountry == selectedCountry}">
                <option value="<c:out value="${currentCountry}"/>" selected><c:out value="${status.current.country}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentCountry}"/>"><c:out value="${status.current.country}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </select>
  	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="addressLine2" id="addressLine2" value="<tcmis:inputTextReplace value="${param.addressLine2}" />" size="30" maxlength="50"/>
	</td>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.state"/>/<fmt:message key="label.region"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
	</td>
	<td width="15%" class="optionTitle">
          <select name="stateAbbrev" id="stateAbbrev" class="selectBox">
          </select>
	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="addressLine3" id="addressLine3" value="<tcmis:inputTextReplace value="${param.addressLine3}" />" size="30" maxlength="50"/>
	</td>
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.city"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
	</td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="city" id="city" value="<tcmis:inputTextReplace value="${param.city}" />" size="20" maxlength="30"/>
	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="addressLine4" id="addressLine4" value="<tcmis:inputTextReplace value="${param.addressLine4}" />" size="30" maxlength="50"/>
	</td>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.postalcode"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
	</td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="zip" id="zip" value="<tcmis:inputTextReplace value="${param.zip}" />" size="10" maxlength="30"/>
		<span id="gone" style="display:none">
		<span id="zipPlusSpan" style="">
			-<input type="text" class="inputBox" name="zipPlus" id="zipPlus" value="" size="5" maxlength="4"/>
		</span>
		</span>
	</td>
</tr>
<tr>
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="addressLine5" id="addressLine5" value="<tcmis:inputTextReplace value="${param.addressLine5}" />" size="30" maxlength="50"/>
	</td>
	<td/>
	<td/>
</tr>
<tr>
	<td colspan="4" width="100%" align="center">
          <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick="returnAddress();">
          <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick="window.close();"/>
    </td>
</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>  

</fieldset>



<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}">
<input type="hidden" name="ShipToOrBillTo" id="ShipToOrBillTo" value="${param.ShipToOrBillTo}">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>