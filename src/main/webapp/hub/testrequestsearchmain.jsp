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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="expires" content="-1" />
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
	<%@ include file="/common/locale.jsp" %>
	<%@ include file="/common/opshubig.jsp" %>
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preferred font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<%-- Add any other style sheets you need for the page here --%>
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> 
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
		
	<!-- Add any other javaScript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>	
	<script type="text/javascript" src="/js/common/opshubig.js"></script>
	<script type="text/javascript" src="/js/hub/testrequest.js"></script>
	
		<!-- Add any other javascript you need for the page here --> 
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	
	<c:set var="module">
	 <tcmis:module/>
    </c:set>
	
	<title>
		<fmt:message key="title.testrequestsearch"/>	
	</title>

</head>

<script language="JavaScript" type="text/javascript">
<!--

<c:forEach var="hubvar" items="${UserFacilityCollection}" varStatus="status">

<bean:size id="facilitySize" name="UserFacilityCollection"/>

var altFacilityId = new Array( ''
<c:forEach var="hubvar" items="${UserFacilityCollection}" varStatus="status">
	<c:if test="${status.current.active == 'y' || status.current.active == 'Y'}">
    	,'<tcmis:jsReplace value="${status.current.facilityId}"/>'
    </c:if>
</c:forEach>
);

var altFacilityName = new Array('<fmt:message key="label.myfacilities"/>'
	<c:forEach var="hubvar" items="${UserFacilityCollection}" varStatus="status">
		<c:if test="${status.current.active == 'y' || status.current.active == 'Y'}">
	    	,'<tcmis:jsReplace value="${status.current.facilityName}"/>'
	    </c:if>
	</c:forEach>
	);
</c:forEach>

-->
</script>



 <c:choose>
   <c:when test="${module == 'haas'}">
    <body bgcolor="#ffffff" onload="loadLayoutWin('','marsRequestSearch');searchTestRequestsOnLoad();setOps();setFacility();" onresize="resizeFrames();">
   </c:when>
   <c:otherwise>
    <body bgcolor="#ffffff" onload="loadLayoutWin('','marsRequestSearch');searchTestRequestsOnLoad();setFacility();<c:if test="${param.receiptId != null}">submitSearchForm();</c:if>" onresize="resizeFrames();">
   </c:otherwise>
  </c:choose>

	<div class="interface" id="mainPage" style="">
		
		<!-- Search Div Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/testrequestsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
 									<div class="roundright">
   										<div class="roundtop">
     											<div class="roundtopright">
     												<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
     											</div>
   										</div>
										<div class="roundContent">
											<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
											<c:if test="${module == 'haas'}">
												<tr>
													<td nowrap class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>&nbsp;:</td>
													<td class="optionTitleBoldLeft">
													   <select name="opsEntityId" id="opsEntityId" class="selectBox"/>
													</td>
											    </tr>
												<tr>			
													<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.hub" />:</td>
													<td width="50%" class="optionTitleBoldLeft">
														<select name="hub" id="hub" class="selectBox"></select>
													</td>
												</tr>
												<tr>
												 	<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup" />:</td>
													<td width="50%" class="optionTitleBoldLeft" >
														<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
													</td>  
												</tr>
											</c:if>
											<c:if test="${module != 'haas'}">											  
										        <tr>
											        <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
											        <td  class="optionTitleLeft">
											          <!-- Use this for dropdowns you build with collections from the database -->
											          <input type="hidden" name="oldfacilityId" id="oldfacilityId" value=""/>		
											          <select name="facilityId" id="facilityId" class="selectBox" onChange="facilityChanged()"></select>
											       </td>
											    </tr>											    
											   </c:if>
												<tr>
											  		<td  class="optionTitleBoldRight"><fmt:message key="label.search"/>&nbsp;:</td>
											        <td nowrap class="optionTitleBoldLeft">
											            <select name="searchField"  class="selectBox" id="searchField">                                
											                <option value="testRequestId"><fmt:message key="title.testrequest"/></option>
											                <option value="itemId"><fmt:message key="label.itemid"/></option>
											                <option value="catPartNo"> <fmt:message key="label.customerpart"/></option>
											                <option value="lotStatus"><fmt:message key="label.lotstatus"/></option>
											                <c:if test="${param.receiptId == null}">
											                <option value="receiptId"><fmt:message key="label.receiptid"/></option>
											                </c:if> 
											                <c:if test="${param.receiptId != null}">
											                <option value="receiptId" selected><fmt:message key="label.receiptid"/></option>
											                </c:if>  
											            </select>
											            &nbsp;
											             <select name="searchMode" class="selectBox" id="searchMode" >
										                   <option value="is"> <fmt:message key="label.is"/>  </option>
										                   <option value="like"> <fmt:message key="label.contains"/>  </option>
										                   <option value="startsWith"><fmt:message key="label.startswith"/> </option>
										                   <option value="endsWith"><fmt:message key="label.endswith" /></option>
											            </select>
											             &nbsp;
											            <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15" value="${param.receiptId}"/>											       
											        </td>
											        <!-- <td class="optionTitleBoldLeft">
											            <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
											    	</td> -->
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.requeststatus"/>&nbsp;:</td>
													<td class="optionTitleBoldLeft">
														<select class="selectBox"  name="requestStatus" id="requestStatus" size="1" width="105" style="width: 105px">
														   <option value=""><fmt:message key="label.all"/></option>															
                                                            <c:if test="${module == 'haas'}">                                                       		    
                                                                <option value="New"><fmt:message key="label.new"/></option>
                                                            </c:if>                                                                                                                  
                                                            <option value="To Lab"><fmt:message key="label.tolab"/></option>
                                                            <option value="In Test"><fmt:message key="label.intest"/></option>                                                            
															<option value="QC"><fmt:message key="label.qc"/></option>
															<option value="Complete"><fmt:message key="label.complete"/></option>
														</select>							
													</td>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.over"/>&nbsp;
														<input name="labAge" id="labAge" type="text" class="inputBox" size="4"/>&nbsp;
														<fmt:message key="label.daysinlab"/>
													</td>
												</tr>					
												<tr>
													<td colspan="4" class="optionTitleBoldLeft">
														<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key='label.search'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return submitSearchForm()"/>
														<input name="buttonCreateExcel" id="buttonCreateExcel" type="button" value="<fmt:message key='label.createexcelfile'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return generateExcel()"/>
													</td>
												</tr>
											</table>
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
											
					<!-- Search Option Ends -->
					
			<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
					<div class="spacerY">&nbsp;
						<div id="searchErrorMessagesArea" class="errorMessages">
							<html:errors />
						</div>
					</div>
				</c:if>			
					<!-- Hidden element start -->
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="uAction" id="uAction" value=""/>
						<input type="hidden" name="facility" id="facility" value=""/>						
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="searchHeight" id="searchHeight" type="hidden" value="150">
						<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
						<input name="totalLines" id="totalLines" type="hidden" value="0"/>
					</div>
					<!-- Hidden elements end -->
			
				</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search Div Ends -->
		
		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
		
			<%-- Transit Page Starts --%>
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
						  				<div class="boxhead">
											<div id="mainUpdateLinks">
											</div>
                                        </div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"> </iframe>
										</div>
					  					
										<%-- result count and time --%>
										<div id="footer" class="messageBar">
										</div>
									</div>
									<div class="roundbottom">
									 	<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>  
		</div><!-- Result Frame Ends -->
		
	</div> <!-- close of interface -->
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">		
	</div>
	
	
<script language="JavaScript" type="text/javascript">
<!--	
	
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		errors:"<fmt:message key="label.errors"/>",  
		validvalues:"<fmt:message key="label.validvalues"/>",
		pleasewait:"<fmt:message key="label.pleasewait"/>",	
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		mustBeANumber:"<fmt:message key="label.mustbeanumber"/>",
		daysInLabMustBeInteger:"<fmt:message key="error.daysInLabMustBeInteger"/>",
		integerErr:"<fmt:message key="errors.integer"/>",
		testRequest:"<fmt:message key="title.testrequest"/>",
		itemId:"<fmt:message key="label.itemid"/>",
		receiptId:"<fmt:message key="label.receiptid"/>"
	};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;  

// -->
</script>
</body>
</html:html>