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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/cabinet/clientcabinetinventorymain.js"></script>
<script type="text/javascript" src="/js/common/cabinet/workareasearchtemplate.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

    
<title>
<fmt:message key="label.cabinetinventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
pleasewait:"<fmt:message key="label.pleasewait"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>",
errors:"<fmt:message key="label.errors"/>",  
expireInDaysInteger:"<fmt:message key="label.expireindaysinteger"/><fmt:message key="label.mustbeaninteger"/>",
pleaseDoNotSelectAllCabinets:"<fmt:message key="label.pleasedonotselectallcabinets"/>",
pleaseDoNotSelectAllCabinets:"<fmt:message key="label.pleasedonotselectallcabinets"/>",
pleaseSelectACabinet:"<fmt:message key="label.pleaseselectacabinet"/>",
expireInFromDaysInteger:"<fmt:message key="label.expireinfromdays"/> <fmt:message key="label.mustbeaninteger"/>",
expireInToDaysInteger:"<fmt:message key="label.expireintodays"/> <fmt:message key="label.mustbeaninteger"/>"};
// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
		
// -->
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','newClientCabinetInventory');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">  

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/newclientcabinetinventoryresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt=""
                                                        width="15" height="10" class="corner_filter"
                                                        style="display: none"/></div>
                    </div>
                    <div class="roundContent">
  <!-- Insert all the search option within this div -->
    <table width="900" cellpadding="0" cellspacing="0" class="tableSearch">
    	<tr>
    		<td>
	    		<table>
	    			<%-- Row 1 --%>
		 			<%@ include file="/common/cabinet/workareasearchtemplate.jsp" %>
	    		</table>
    		</td>
    		<td>
    			<table>
    				<tr>
				        <td width="10%" nowrap class="optionTitleBoldRight">
						  	 <fmt:message key="label.expiresin" />
						  </td>
						  <td width="20%" nowrap class="optionTitleBoldLeft" >
									<input type="text" class="inputBox" name="expireInFrom" id="expireInFrom" value="" size="2" maxLength="4"/>
									<fmt:message key="label.to"/>
									<input type="text" class="inputBox" name="expireInTo" id="expireInTo" value="" size="2" maxLength="4"/> 
									<fmt:message key="label.days" />
						  </td>
				      </tr>
				      <tr>		  
				          <td  class="optionTitleBoldRight">
				        			<input type="checkBox" class="radioBtns" name="positiveQ" id="positiveQ" value="Yes"/>
				        </td>  
				        <td  nowrap class="optionTitleBoldLeft">
									<fmt:message key="label.showonlypositiveq"/>
						</td>    						
				      </tr>		
				      <tr>
				      	<td>
				      		&nbsp;
				      	</td>
				      </tr>     
    			</table>
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2">
	    		<table>
	    			<tr>
				         <td width="3%" class="optionTitleBold">
							<fmt:message key="label.sortby"/>:
				        </td>
			           <td width="30%" class="optionTitleBoldLeft" colspan=3>
						<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
			  				<html:option value="CAT_PART_NO" key="label.partnumber"/>
			  				<html:option value="ITEM_ID" key="label.itemid"/>
						</html:select>
			        </td>
			      </tr>
			      <tr>
			       <td width="3%" class="optionTitleBold">
			          <fmt:message key="label.search"/>:
			        </td>
			      	<td width="22%" class="optionTitleBoldLeft" nowrap>
						<select name="searchField" id="searchField" class="selectBox">
						<option value="CAT_PART_NO"> <fmt:message key="label.partnumber"/></option>
			            <option value="ITEM_DESC"><fmt:message key="label.itemdesc"/>  </option>      
			            <option value="ITEM_ID" selected="selected"> <fmt:message key="label.itemid" /></option>
						</select>
						&nbsp;
						<html:select property="matchingMode" styleId="matchingMode" styleClass="selectBox">
						<html:option value="contains" key="label.contain"/>
						<html:option value="is" key="label.is"/>
						</html:select>
						&nbsp;
							<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="<c:out value="${param.searchArgument}"/>" size="15">
					</td> 
					<td width="10%" class="optionTitleBoldRight" nowrap>
			          <fmt:message key="label.tieriistoragecontainer"/>:&nbsp;
			        </td>      
			        <td width="10%" class="optionTitleLeft" nowrap>
			          <select name="tierIiStorage" id="tierIiStorage" class="selectBox">
			            <option value=""><fmt:message key="label.pleaseselect"/></option>
			          	<c:forEach var="container" items="${storageContainerColl}" varStatus="status">
			          		<option value="${container.unidocsStorageContainer}"> <tcmis:jsReplace value="${container.description}"/></option>
			          	</c:forEach>
					  </select>
			        </td>  
			       
			      </tr> 
			      <tr>
			        <td  class="optionTitleBoldLeft" colspan="4">
			          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
			          <input name="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
			        
			        </td>

			        
			      </tr>
	    		</table>
    		</td>
    	</tr>    
    </table>
   <!-- End search options -->
                        <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt=""
                                                           width="15" height="15" class="corner"
                                                           style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
<!-- Search Option Ends -->

<div id="transitDialogWin" class="errorMessages" style="display: none;overflow: auto;">
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
					
<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="facilityName" id="facilityName" value="<c:out value="${selectedFacilityName}"/>" type="hidden">
<input name="companyId" id="companyId" value="${personnelBean.companyId}" type="hidden">
<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>	
<input name="searchHeight" id="searchHeight" type="hidden" value="139">

</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
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
      <span id="updateResultLink" style="display: none"><a href="#" onclick="call('updateExpedite');"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>


</body>
</html:html>
