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
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
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
<%@ include file="/common/opshubig.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/inventoryreceive.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

    
<title>
<fmt:message key="label.customerinventorytoreceive"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",

errors:"<fmt:message key="label.errors"/>",     
searchInput:"<fmt:message key="error.searchInput.integer"/>"};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
//defaults.inv.nodefault = true;

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('<fmt:message key="label.customerinventorytoreceive"/>','custInvReceiving');setDropDowns();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/inventoryreceiveresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
                        <table width="90%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                            <tr>
                                <td nowrap width="8%"  class="optionTitleBoldRight"><fmt:message key="label.operatingentity"/>:</td>
                                <td width="10%" class="optionTitleBoldLeft">
							         <select name="opsEntityId" id="opsEntityId" class="selectBox">
							         </select>							         
							      </td>	
							      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.search"/>:</td>
							      <td class="optionTitleBoldLeft" nowrap>
								     <html:select property="searchField" styleId="searchField" styleClass="selectBox">
								       <html:option value="itemId" key="label.itemid"/>
								       <html:option value="itemDesc" key="label.itemdesc"/>
								       <html:option value="customerPoNo" key="label.customerpo"/>
								       <html:option value="catPartNo" key="label.part"/>
								       </html:select>
								       <html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
								        <html:option value="is"/>
								        <html:option value="contains"/> 
								      </html:select>
								    	<html:text property="searchArgument" styleId="searchArgument" size="20" styleClass="inputBox" onkeypress="onKeyPress()"/>
								  </td>
			            
                            </tr>    
                            <tr>  <td nowrap width="6%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
							      <td  width="5%" class="optionTitleBoldLeft">
							         <select name="hub" id="hub" class="selectBox" onchange=""></select>
							         <input name="sourceHubName" id="sourceHubName" value="" type="hidden"/>
							       </td> 
							       <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.expecteddeliverydate"/>:</td>
							       <td nowrap class="optionTitleBoldLeft">
										 <fmt:message key="label.from"/>
										 <input class="inputBox pointer" readonly type="text" name="expDeliveryFromDate" id="expDeliveryFromDate" value="" onClick="return getCalendar(document.genericForm.expDeliveryFromDate,null,document.genericForm.expDeliveryToDate);"
										        maxlength="10" size="9"/>
										 <fmt:message key="label.to"/>&nbsp;
										 <input class="inputBox pointer" readonly type="text" name="expDeliveryToDate" id="expDeliveryToDate" value="" onClick="return getCalendar(document.genericForm.expDeliveryToDate,document.genericForm.expDeliveryFromDate);"
										        maxlength="10" size="9"/>
									</td>
							       										      	  
							</tr>     
                           <tr>						      
							       <td nowrap width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
							      <td width="5%" class="optionTitleBoldLeft">
							       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
							       </select>
							       </td>
							       <td width="20%" class="optionTitleBoldRight">
									<input type="checkbox" name="showAvailable" value="Y" id="showAvailable" checked />
								    <span class="optionTitleBoldLeft"><fmt:message key="label.showavailable"/></span>&nbsp;
						           </td>
							       								 					      				       	                                                                              
                            </tr> 
                                                     
                           <tr>
							<td width="5%" class="optionTitleBoldRight">
								<input 	name="submitSearch" id="submitSearch" type="button"
										value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
										onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/>
							</td>
							<td>
							<input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createExcel()"/>
							</td>
		                   </tr>
                        </table>
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
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="searchHeight" id="searchHeight" type="hidden" value="133">
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
        <span id="showUpdateLink" style="display: none">
        <a href="#" onclick="call('update');return false;"><fmt:message key="label.update"/></a></span>
      <span id="addCitrSpan" style="display: none"><a href="javascript:addCitr()">|&nbsp<fmt:message key="label.addcitr"/></a></span>
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