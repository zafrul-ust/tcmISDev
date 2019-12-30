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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/poexpeditingmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

    
<title>
<fmt:message key="poexpediting.title"/>
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
allbuyers:"<fmt:message key="label.allbuyers"/>",
age:"<fmt:message key="error.age.integer"/>",
errors:"<fmt:message key="label.errors"/>",     
searchInput:"<fmt:message key="error.searchInput.integer"/>"};

var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:set var="currentHub" value='${status.current.branchPlant}'/>
 <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

 altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array({id:"All",name:messagesData.all}
         <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
        ,{id:"<c:out value='${status1.current.inventoryGroup}'/>",name:"<c:out value='${status1.current.inventoryGroupName}'/>"}
          </c:forEach>
       );
</c:forEach>

var allBuyers = new Array();
<c:forEach var="buyerBean" items="${buyersCollection}" varStatus="statusBuyer">
  allBuyers["${statusBuyer.index}"] = {lastName:"${statusBuyer.current.lastName}",personnelId:"${statusBuyer.current.personnelId}",status:"${statusBuyer.current.status}"};
</c:forEach>

var searchMyArr = new Array(
		{value:'like', text: '<fmt:message key="label.contains"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
	);

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','openPos');showHub();showBuyers();" onresize="resizeFrames()" onunload="closeAllchildren()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/poexpeditingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
                        <table width="750" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                            <tr>
                                <td rowspan="3" width="2%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
                                <td rowspan="3" width="10%" class="optionTitleBoldLeft">
                                 <c:set var="selectedHub" value='${param.branchPlant}'/>
                                 <c:set var="selectedHubName" value=''/>
                                 <c:set var="inventoryGroupDefinitions" value='${null}'/>
                                 <select name="hubIdArray" id="hubIdArray" class="selectBox" onchange="hubChanged() " multiple size="5" >
                                   <option value="All" selected><fmt:message key="label.all"/></option>
                                   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
                                    <c:set var="currentHub" value='${status.current.branchPlant}'/>
                                    <c:if test="${selectedHub == null}">
                                        <c:set var="selectedHub" value="${currentHub}"/>
                                    </c:if>
                                    <c:if test="${currentHub == selectedHub}" >
                                      <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
                                    </c:if>
                                    <c:choose>    
                                      <c:when test="${currentHub == selectedHub}">
                                        <option value='<c:out value="${currentHub}"/>' ><c:out value="${status.current.hubName}"/></option>
                                        <c:set var="selectedHubName" value="${status.current.hubName}"/>
                                      </c:when>
                                      <c:otherwise>
                                        <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
                                      </c:otherwise>
                                    </c:choose>
                                  </c:forEach>
                                 </select>                                   
                                </td>
                                <td width="5%" class= "optionTitleBoldRight"> <fmt:message key="label.inventorygroup"/>:</td>
                                <td width="15%" colspan="3" class="optionTitleBoldLeft">
                                    <select name="inventoryGroup" id="inventoryGroup" class="selectBox" >
                                   <%--  <option value="All"><fmt:message key="label.all"/></option>--%>
                                    </select>
                                    <input name="inventoryGroupName" id="inventoryGroupName" type="hidden"> 
                                </td>                                                                                  
                            </tr>
                            <tr>
                               <td width="5%" class= "optionTitleBoldRight"> <fmt:message key="label.buyer"/>:</td>
                               <td width="15%" colspan="3" class= "optionTitleBoldLeft">
                                    <select name="buyerId" id="buyerId" class="selectBox" >
                                      <%--  <option value="All"><fmt:message key="label.all"/></option>--%>
                                    </select>
                                    <%--<html:select property="buyerId" styleClass="selectBox" styleId="buyerId" value="${personnelBean.personnelId}">
                                    <html:option value="" key="label.allbuyers"/>
                                    <html:options collection="buyersCollection" styleClass="yellow" property="personnelId" labelProperty="lastName"/>
                                    </html:select>--%>
                                   
                                </td>                                
                            </tr>  
                            <tr>
                                <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
                                <td width="5%" class="optionTitleBoldLeft">
                                    <select name="searchField"  class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value)">
                                        <option value="carrier"> <fmt:message key="label.carrier"/>  </option>
                                        <option value="radianPo" selected> <fmt:message key="label.haaspo"/>  </option>
                                        <option value="itemId"> <fmt:message key="label.itemid"/>  </option>
                                        <option value="itemDesc"><fmt:message key="label.itemdesc"/> </option>
                                        <option value="shipToAddress"> <fmt:message key="label.shipto"/>  </option>
                                        <option value="companyId"> <fmt:message key="label.company"/>  </option> 
                                        <option value="supplierName"><fmt:message key="label.suppliername"/> </option>                                                                                                                                                                  
                                    </select>
                                </td> 
                                <td width="5%" class="optionTitleBoldLeft">
                                    <select name="searchMode" class="selectBox" id="searchMode" >
                                           <option value="is"> <fmt:message key="label.is"/>  </option>
                                           <option value="like"> <fmt:message key="label.contains"/>  </option>
                                           <option value="startsWith"><fmt:message key="label.startswith"/> </option>
                                           <option value="endsWith"><fmt:message key="label.endswith" /></option>
                                    </select>
                                </td>                                      
                                <td width="5%" class="optionTitleBoldLeft">
                                    <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
                                </td>                                
                            </tr>                            
                            <tr>
                              <td width="2%" class= "optionTitleBoldRight"> <fmt:message key="label.supplier"/>:</td>
                              <td width="2%" class= "optionTitleBoldLeft" nowrap>                             
                                <input name="supplierName" id="supplierName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly/>                                                           
                                 <input name="supplier" id="supplier" type="hidden" value=""/>                                    
                                  <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button">
                                  <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearSupplier()"><fmt:message key="label.clear"/> </button>
                              </td>                                
                              <td  width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.promised.ship.date"/>:</td>
                              <td  width="15%" colspan="3" class="optionTitleBoldLeft">
                                <%--<sub> (${dateFormatLabel})</sub> <br/>--%>
                                 <%--Date search fields.getDateTag is used to set default values for the search field --%>
                               <fmt:message key="label.from"/>
                               <input class="inputBox pointer" readonly type="text" name="shipFromDate" id="shipFromDate" value="" onClick="return getCalendar(document.poExpeditingForm.shipFromDate,null,document.poExpeditingForm.shipToDate);"
                                                                    maxlength="10" size="8"/>
                              <fmt:message key="label.to"/>&nbsp;

                               <input class="inputBox pointer" readonly type="text" name="shipToDate" id="shipToDate" value="" onClick="return getCalendar(document.poExpeditingForm.shipToDate,document.poExpeditingForm.shipFromDate);"
                                                                    maxlength="10" size="8"/>
                              </td>                                
                            </tr>                         
                            <tr>                                                                            
                            <%--   supply path start    --%>                                                                    	                        
                             <td width="3%" class="optionTitleBoldRight" nowrap><fmt:message key="label.supplypath"/>:</td>
                             <td width="10%" class="optionTitleBoldLeft">
                                <select name="supplyPath"  class="selectBox" id="supplyPath" >
                                    <option value="ALL"> <fmt:message key="label.all"/>  </option>
                                    <option value="Dbuy"> <fmt:message key="label.dbuy"/>  </option>
                                    <option value="Purchaser"> <fmt:message key="label.purchasing"/>  </option>
                                    <option value="Xbuy"> <fmt:message key="label.xbuy"/>  </option>
                                    <option value="Wbuy"> <fmt:message key="label.wbuy"/>  </option>
                                    <option value="Ibuy"> <fmt:message key="label.ibuy"/>  </option>
                                </select>
                            </td>   
                            <td  width="2%"class="optionTitleBoldRight"><fmt:message key="label.dateconfirmed"/>:</td>
                             <td width="15%" colspan="3" class="optionTitleBoldLeft">                              
                               <fmt:message key="label.from"/>
                               <input class="inputBox pointer" readonly type="text" name="orderFromDate" id="orderFromDate" value="" onClick="return getCalendar(document.poExpeditingForm.orderFromDate,null,document.poExpeditingForm.orderToDate);"
                                                                    maxlength="10" size="8"/>
                              <fmt:message key="label.to"/>&nbsp;

                               <input class="inputBox pointer" readonly type="text" name="orderToDate" id="orderToDate" value="" onClick="return getCalendar(document.poExpeditingForm.orderToDate,document.poExpeditingForm.orderFromDate);"
                                                                    maxlength="10" size="8"/>                                                        
                                                            
                               </td>                                 
                            </tr>                    
                            <tr>                           
                                 <td  class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td> 
                                 <td    class="optionTitleBoldLeft">
                                    <select name="sortBy"  class="selectBox" id="sortBy" >
                                        <option value="vendorShipDate"> <fmt:message key="label.promised.ship.date"/>  </option>
                                        <option value="radianPo"> <fmt:message key="label.haaspo"/>  </option>
                                        <option value="supplierName"> <fmt:message key="label.supplier"/>  </option>
                                        <option value="itemId"> <fmt:message key="label.item"/>  </option>
                                        <option value="buyerId"> <fmt:message key="label.buyer"/>  </option>
                                    </select>
                                </td>         
                               <%--  Age field  --%>                  
                               <td nowrap class= "optionTitleBoldRight"  > 
                               <fmt:message key="label.expediteage"/>&nbsp;>
                               </td>
                               <td width="2%" colspan="3" class= "optionTitleBoldLeft">
                                <input name="expediteAge" id="expediteAge" type="text" size="3" maxlength="3" class="inputBox">&nbsp;
                                 <fmt:message key="label.days"/>                                
                                 &nbsp;&nbsp;&nbsp;&nbsp;                                
                        <input name="creditHold" id="creditHold" type="checkbox" class="radioBtns" value="Y" />
				        <fmt:message key="label.creditholdonly"/>
                                </td>                                                                                                                                                                                                                                              
                            </tr> 
                            <tr>
                            <td></td>
                            <td></td>
                             <td  width="2%"class="optionTitleBoldRight"><fmt:message key="label.revprojecteddelivery"/>:</td>
                             <td width="15%" colspan="3" class="optionTitleBoldLeft">                              
                               <fmt:message key="label.from"/>
                               <input class="inputBox pointer" readonly type="text" name="revisedPromisedFromDate" id="revisedPromisedFromDate" value="" onClick="return getCalendar(document.poExpeditingForm.revisedPromisedFromDate,null,document.poExpeditingForm.revisedPromisedToDate);"
                                                                    maxlength="10" size="8"/>
                              <fmt:message key="label.to"/>&nbsp;

                               <input class="inputBox pointer" readonly type="text" name="revisedPromisedToDate" id="revisedPromisedToDate" value="" onClick="return getCalendar(document.poExpeditingForm.revisedPromisedToDate,document.poExpeditingForm.revisedPromisedFromDate);"
                                                                    maxlength="10" size="8"/>                                                        
                                                            
                               </td>   
                            </tr>                          
                            <tr>
                                <td class="optionTitleBoldLeft" colspan="6">
                                    <input name="submitSearch" id="submitSearch" type="submit"
                                           value="<fmt:message key="label.search"/>" class="inputBtns"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="return submitSearchForm();">
                                    <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit"
                                           class="inputBtns" value="<fmt:message key="label.createexcelfile"/>"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="generateExcel(); return false;">
                                    <input name="buttonShowExampleExcel" id="buttonShowExampleExcel" type="submit"
                                           class="inputBtns" value="<fmt:message key="label.examplefile"/>"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="openExampleTemplate();return false;">
                                    <input name="buttonUploadExcel" id="buttonUploadExcel" type="submit"
                                           class="inputBtns" value="<fmt:message key="label.uploadfile"/>"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="uploadList(); return false;">
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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input type="hidden" name="personnelLoggedIn" id="personnelLoggedIn" value="${sessionScope.personnelBean. personnelId}"/>
<input name="searchHeight" id="searchHeight" type="hidden" value="214">
<input name="maxData" id="maxData" type="hidden" value="1000"/>    
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