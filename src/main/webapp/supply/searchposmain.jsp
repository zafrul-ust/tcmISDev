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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/searchposmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="searchpurchaseorders.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
   //add all the javascript messages here, this for internationalization of client side javascript messages
   var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
                   and:"<fmt:message key="label.and"/>",
                   all:"<fmt:message key="label.all"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
           validValues:"<fmt:message key="transactions.validvalues"/>",
           inputText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
           selecttocontinue:"<fmt:message key="label.selecttocontinue"/>",
                itemId:"<fmt:message key="label.itemid"/>"};

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

   var altinvid = new Array();
   var altinvName = new Array();
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
     <c:set var="currentHub" value='${status.current.branchPlant}'/>
     <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

   altinvid["<c:out value="${currentHub}"/>"] = new Array(""
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
      </c:forEach>
   );

   altinvName["<c:out value="${currentHub}"/>"] = new Array(messagesData.all
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
      </c:forEach>
   );
   </c:forEach>

   var searchMyArr = new Array(
			{value:'contains', text: '<fmt:message key="label.contains"/>'}
			,{value:'is', text: '<fmt:message key="label.is"/>'}
			,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
			,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
		);

   <c:set var="callFromPopup" value="false"/>
   <c:if test="${param.searchFromPopup == 'true'}">
    <c:set var="callFromPopup" value="true"/>
   </c:if> 
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','searchPos')" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
<%-- <iframe id="searchFrame" name="searchFrame" width="100%" height="150" frameborder="0" marginwidth="0" style="" src="transactionssearch.do"></iframe>--%>
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/searchposresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    
    <%-- Row 1 --%>
    <tr>
      <td width="10%" class="optionTitleBoldRight" rowspan="3"><fmt:message key="label.hub"/>:</td>
      <td width="10%" rowspan="3">
         <c:set var="selectedHub" value='${param.branchPlant}'/>
         <c:set var="selectedHubName" value=''/>
         <c:set var="inventoryGroupDefinitions" value='${null}'/>
         <select name="branchPlant" onchange="hubChanged()" class="selectBox" id="branchPlant" multiple size="4">
            <option value=""><fmt:message key="label.all"/></option>
           <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:if test="${currentHub == selectedHub}" >
              <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
                <c:set var="selectedHubName" value="${status.current.hubName}"/>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <td width="70%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.search"/>:&nbsp;
        <select id="searchField" name="searchField"  class="selectBox" onchange="changeSearchTypeOptions(this.value,this.id)" >
         <option value="itemId"><fmt:message key="label.itemid"/></option>
         <option value="itemDesc"><fmt:message key="label.itemdesc"/></option>
         <%--<option value="manufacturer"><fmt:message key="label.manufacturer"/></option>
         <option value="mfgPartNo"><fmt:message key="label.mfgpartno"/></option>--%>
         <option value="customerPo"><fmt:message key="label.customerpo"/></option>
         <option value="partNumber"><fmt:message key="label.partnumber"/></option>
         <option value="supplierName"><fmt:message key="label.suppliername"/></option>                        
        </select>
        <select name="searchMode" id="searchMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
          </select>
        <input class="inputBox" type="text" name="searchString" id="searchString" value='<c:out value="${param.searchString1}"/>' size="25">
      </td>
    </tr>
    
    <%-- Row 2 --%>
    <tr>
      <td width="5%" class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      
      	<fmt:message key="label.search"/>:&nbsp;
        <select id="searchField2" name="searchField2"  class="selectBox" onchange="changeSearchTypeOptions(this.value,this.id)" >
         <option value="itemId"><fmt:message key="label.itemid"/></option>
         <option value="itemDesc"><fmt:message key="label.itemdesc"/></option>
         <%--<option value="manufacturer"><fmt:message key="label.manufacturer"/></option>
         <option value="mfgPartNo"><fmt:message key="label.mfgpartno"/></option>--%>
         <option value="customerPo"><fmt:message key="label.customerpo"/></option>
         <option value="partNumber"><fmt:message key="label.partnumber"/></option>
         <option value="supplierName"><fmt:message key="label.suppliername"/></option>                        
        </select>
        <select name="searchMode2" id="searchMode2" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
          </select>
        <input class="inputBox" type="text" name="searchString2" id="searchString2" value='' size="25">

      </td>
    </tr>
    
    <%-- Row 3 --%>
    <tr>
      <td  width="10%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      
      	<fmt:message key="label.buyer"/>:&nbsp;
        <select class="inputBox" name="buyer" id="buyer" size="1">
          <option value=""><fmt:message key="label.anybuyer"/></option>
         <c:choose>
           <c:when test="${param.buyer == null}">
             <c:set var="currentBuyer" value="${personnelBean.personnelId}"/>
           </c:when>
           <c:otherwise>
              <c:set var="currentBuyer" value="${param.buyer}"/>
           </c:otherwise>
         </c:choose>
         <c:forEach items="${buyerColl}" var="buyerBean">
          <c:set var="stautsClass" value=''/>
          <c:if test="${buyerBean.status != 'A'}">
            <c:set var="stautsClass" value="class=lightgray"/>
          </c:if>
          <c:choose>
            <c:when test="${currentBuyer == buyerBean.personnelId}">
             <option ${stautsClass} value='<c:out value="${buyerBean.personnelId}"/>' selected><c:out value="${buyerBean.lastName}"/></option>
            </c:when>
            <c:otherwise>
             <option ${stautsClass} value='<c:out value="${buyerBean.personnelId}"/>'><c:out value="${buyerBean.lastName}"/></option>
            </c:otherwise>
           </c:choose>
          </c:forEach>
        </select>
      
       </td>                    
    </tr>
    
    <%-- Row 4 --%>
    <tr>
       <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <c:set var="selectedIg" value='${param.inventoryGroup}'/>
       <c:set var="invenGroupCount" value='${0}'/>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" size="1">
        <option value=""><fmt:message key="label.all"/></option>
        <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
          <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
          <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
          <c:if test="${empty selectedIg}" >
              <c:set var="selectedIg" value=""/>
          </c:if>
          <c:choose>
            <c:when test="${currentIg == selectedIg}">
              <option value='<c:out value="${currentIg}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:when>
            <c:otherwise>
              <option value='<c:out value="${currentIg}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
       </select>

      <td width="70%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	<fmt:message key="label.originalpromiseddate"/>:
        <fmt:message key="label.from"/>
        <input class="inputBox pointer" readonly type="text" name="promisedFromDate" id="promisedFromDate" value="" 
        	onClick="return getCalendar(document.searchPosForm.promisedFromDate,null,document.searchPosForm.promisedToDate);" maxlength="10" size="10"/>
        <fmt:message key="label.to"/>&nbsp;

      	<input class="inputBox pointer" readonly type="text" name="promisedToDate" id="promisedToDate" value="" 
      		onClick="return getCalendar(document.searchPosForm.promisedToDate,document.searchPosForm.promisedFromDate);" maxlength="10" size="10"/>
                                                                                                                       
      </td>
    </tr>
    
    <%-- Row 5 --%>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.supplier"/>:</td>
      <td width="10%">
 		  <input name="supplierName" id="supplierName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly/>                                                           
          <input name="supplierId" id="supplierId" type="hidden" value=""/>                                    
          <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button">
          <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                    name="None" value=""  OnClick="clearSupplier()"><fmt:message key="label.clear"/> </button>
      </td>
      <td width="70%" class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.dateconfirmed"/>:                          
        <fmt:message key="label.from"/>
        <input class="inputBox pointer" readonly type="text" name="confirmedFromDate" id="confirmedFromDate" value="" 
           onClick="return getCalendar(document.searchPosForm.confirmedFromDate,null,document.searchPosForm.confirmedToDate);" maxlength="10" size="10"/>
        <fmt:message key="label.to"/>&nbsp;
        <input class="inputBox pointer" readonly type="text" name="confirmedToDate" id="confirmedToDate" value="" 
        	onClick="return getCalendar(document.searchPosForm.confirmedToDate,document.searchPosForm.confirmedFromDate);" maxlength="10" size="10"/>  
      </td>
    </tr> 
    
    <%-- Row 6 --%>
    <tr>
    	<td width="3%" class="optionTitleBoldRight" nowrap><fmt:message key="label.supplypath"/>:</td>
        <td width="10%" class="optionTitleBoldLeft" colspan="3">
            <select name="supplyPath"  class="selectBox" id="supplyPath" >
               <option value=""> <fmt:message key="label.all"/>  </option>
               <option value="Dbuy"> <fmt:message key="label.dbuy"/>  </option>
               <option value="Purchaser"> <fmt:message key="label.purchasing"/>  </option>
               <option value="Xbuy"> <fmt:message key="label.xbuy"/>  </option>
               <option value="Wbuy"> <fmt:message key="label.wbuy"/>  </option>
                <option value="Ibuy"> <fmt:message key="label.ibuy"/>  </option>
             </select>
      	</td>
   </tr>   
    
    <%-- Row 6 --%>
    <tr>
      <td width="100%" class="optionTitleBoldLeft" colspan="4">
        <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" 
            onclick="return submitSearchForm();" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'">
            <c:if test="${callFromPopup == 'false'}">    
		<input type="button" name="createXSL" id="createXSL" value="<fmt:message key="label.createExcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createExcel()"/>
		</c:if>
      </td>
   
    </tr>
    </table>

   <!-- End search options -->
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
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="213">
<input type="hidden" name="searchFromPopup" id="searchFromPopup" value="${param.searchFromPopup}"/>
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
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
        <%--<a href="#" onclick="call('printGrid'); return false;"><fmt:message key="label.print"/></a>--%>
      <%-- <span id="showlegendLink">
        <a href="javascript:showreceivingpagelegend()"><fmt:message key="label.showlegend"/></a>
      </span>
      <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
	      <div id="updateResultLink" style="display: none">        
	         <a href="#" onclick="returnSelected();"><fmt:message key="label.returnselected"/></a>        
	      </div>
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


</div><!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>