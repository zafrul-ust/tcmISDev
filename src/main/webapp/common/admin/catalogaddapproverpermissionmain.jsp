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
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
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

<title>
<fmt:message key="label.catalogaddapproverpermission"/>	
</title>

<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",	
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
var children = new Array(); 
windowCloseOnEsc = true;

<c:set var="workareaFacilityColl" value='${personnelBean.myWorkareaFacilityOvBeanCollection}'/>
<bean:size id="tmpCompanySize" name="workareaFacilityColl"/>
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && tmpCompanySize > 1}">
     "My Companies","<c:out value="${status.current.companyId}" escapeXml="false"/>"
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
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && tmpCompanySize > 1}">
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
  <c:if test="${tmpCompanySize > 1}">
    altFacilityId["My Companies"] = new Array("My Facilities");
    altFacilityName["My Companies"] = new Array("<fmt:message key="label.myfacilities"/>");
  </c:if>

  <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>

    altFacilityId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "My Facilities","<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
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
            "<fmt:message key="label.myfacilities"/>","<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>

var myCompanyDefaultFacilityIdColl = new Array();
<c:forEach var="tmpBean" items="${personnelBean.myCompanyDefaultFacilityIdCollection}" varStatus="status">
	myCompanyDefaultFacilityIdColl[${status.index}]={
		companyId:'${tmpBean.companyId}',
		facilityId:'${tmpBean.facilityId}'
	};
</c:forEach>

<c:set var="mySearchHeight" value="108"/>
<c:if test="${tmpCompanySize > 1}">
	<c:set var="mySearchHeight" value="130"/>
</c:if>

function validateForm() {
	return true;
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function closeAllchildren() 
{
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

function closeThisWindow() {
	try {
		closeAllchildren();
	}catch(e) {
	}
}

function myOnLoad() {
	//call this so it can load facility drop down
	companyChanged();
}

var selectedFacilityIdIndex = 0;
function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var facilityIdO = document.getElementById("facilityId");
  var selectedCompany = companyIdO.value;

  for (var i = facilityIdO.length; i >= 0;i--) {
    facilityIdO[i] = null;
  }

  selectedFacilityIdIndex = 0;
  showFacility(selectedCompany);
  facilityIdO.selectedIndex = selectedFacilityIdIndex;
}

function showFacility(selectedCompany) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedCompany];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedCompany];

  for (var i=0; i < facilityIdArray.length; i++) {
	 setOption(i,facilityNameArray[i].replace('&nbsp;&nbsp;&nbsp;&nbsp;','    '),facilityIdArray[i], "facilityId");
	 //check to see if facility_id is user default facility
	 for (var j = 0; j < myCompanyDefaultFacilityIdColl.length; j++) {
		if (myCompanyDefaultFacilityIdColl[j].companyId == selectedCompany) {
			tmpFacilityId = facilityIdArray[i];
			facIdArray = tmpFacilityId.split('.');
			if (facIdArray.length == 2) {
				if (myCompanyDefaultFacilityIdColl[j].facilityId == facIdArray[1]) {
					selectedFacilityIdIndex = i;
					break;
				}
			}
		}
	 } //end of checking default facility
  }
}

</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','');myOnLoad();" onresize="resizeFrames()" onunload="closeThisWindow();">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/catalogaddapproverpermissionresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
		<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<c:choose>
				<c:when test="${tmpCompanySize > 1}">
					<tr>
						<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
						<td width="80%">
							<c:set var="selectedCompanyId" value=''/>
							<select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">
							<c:choose>
							  <c:when test="${tmpCompanySize == 0}">
								 <option value="My Companies"><fmt:message key="label.mycompanies"/></option>
							  </c:when>
							  <c:otherwise>
								 <c:if test="${tmpCompanySize > 1}">
									<option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
									<c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
								 </c:if>

								 <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
									<option value="<c:out value="${status.current.companyId}"/>"><c:out value="${status.current.companyName}" escapeXml="false"/></option>
								 </c:forEach>
							  </c:otherwise>
							</c:choose>
							</select>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
						<input name="companyId" id="companyId" type="hidden" value="${status.current.companyId}" />	
					</c:forEach>
				</c:otherwise>
			</c:choose>

			<tr>
      		<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
				<td width="80%">
					<select name="facilityId" id="facilityId" class="selectBox">
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%" class="optionTitleBoldRight">
					&nbsp;
				</td>
				<td width="80%" class="optionTitleLeft">
					<input type="checkbox" name="showInactive" id="showInactive" value="Y" class="radioBtns"><fmt:message key="label.showinactive"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				  <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm()">
				  <input name="createexcel"  id="createexcel"  type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
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

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="search"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="${mySearchHeight}">
<input name="companySize" id="companySize" type="hidden" value="${tmpCompanySize}">	
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
		<span id="updateResultLink" style="display: none">
			<a href="#" onclick="call('submitUpdate'); return false;"><fmt:message key="label.update"/></a>
      </span>&nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>