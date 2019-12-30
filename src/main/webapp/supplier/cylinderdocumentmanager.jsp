<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/cylinderdocumentmanager.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
 <fmt:message key="label.managedocumentsimages"/>
</title>

<c:set var="includeDeleted" value='${param.includeDeleted}'/>
<c:set var="updatePermission" value='Y'/>

<script language="JavaScript" type="text/javascript">
<!--
    //add all the javascript messages here, this for internationalization of client side javascript messages
    var messagesData = new Array();
    messagesData={alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    norowselected:"<fmt:message key="error.norowselected"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


	
	with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.viewdocument"/>;url=javascript:viewDocument();");
    }

    drawMenus();

    var config = [
    {
      columnId:"permission"
    },
    { columnId:"ok",
      columnName:'<fmt:message key="label.delete"/>',
      width:4,
      type:'hch',
      align:'center'
    },
    { columnId:"documentTypeName",
      columnName:'<fmt:message key="label.type"/>',
      width:10,
      tooltip:"Y"
    },
    { columnId:"documentName",
      columnName:'<fmt:message key="label.name"/>',
      tooltip:"Y",
      width:15
    },
    { columnId:"enteredByName",
      columnName:'<fmt:message key="label.enteredby"/>',
      tooltip:"Y",
      width:10
    },
    { columnId:"dateInserted",
      columnName:'<fmt:message key="label.entereddate"/>',
      width:8,
      hiddenSortingColumn:'hiddenDocumentDateTime',
      sorting:'int'
    },
    {
      columnId:"hiddenDocumentDateTime",
      sorting:'int'
    },

    { columnId:"lastUpdatedByName",
      <c:if test="${!empty param.includeDeleted}" >
        columnName:'<fmt:message key="label.deletedby"/>',
      </c:if>
      tooltip:"Y",
      width:12
    },
    { columnId:"lastUpdatedDate",
      <c:if test="${!empty param.includeDeleted}" >
        columnName:'<fmt:message key="label.deleteddate"/>',
      </c:if>
      width:8,
      hiddenSortingColumn:'hiddenDeletedDateTime',
      sorting:'int'
    },
    {
      columnId:"hiddenDeletedDateTime",
      sorting:'int'
    },
    {
      columnId:"cylinderDocumentId"
    },
    {
      columnId:"documentUrl"
    },
    {
      columnId:"cylinderTrackingId"
    },
    {
      columnId:"supplier"
    }
    ]

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames()" onunload="closeAllchildren();">
<tcmis:form action="/cylinderdocumentmanager.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- results start -->
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink"><fmt:message key="label.documentsfor"/>: <c:out value="${param.serialNumber} - ${param.manufacturerName}"/></span>
      <br /><br />
      <c:if test="${!empty cylinderDocumentBeanCollection}" >
          <c:if test="${updatePermission == 'Y'}" >
            <a href="#" onclick="update();"><fmt:message key="label.update"/></a>
          </c:if>
      </c:if>
      <c:if test="${updatePermission == 'Y'}" >
        | <a href="#" onclick="addNewDocument();"><fmt:message key="label.addnewdocumentimage"/></a>&nbsp;|
      </c:if>&nbsp;
      <input type="checkbox" name="includeDeleted" value="Yes" id="includeDeleted" onclick="includeDeletedDocuments();" <c:if test="${!empty param.includeDeleted}" > checked </c:if> />
      <span class="optionTitleBoldLeft"><fmt:message key="label.includedeleteddoc"/></span>&nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="cylinderDocumentBean" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:set var="colorClass" value=''/>
<c:if test="${cylinderDocumentBeanCollection != null}" >
<script type="text/javascript">

    <c:if test="${!empty cylinderDocumentBeanCollection}" >
        /*Storing the data to be displayed in a JSON object array.*/
        var jsonMainData = new Array();
            var jsonMainData = {
                rows:[
                <c:forEach var="cylinderDocumentBean" items="${cylinderDocumentBeanCollection}" varStatus="status">
                    <c:if test="${!status.first}">,</c:if>
                    <c:set var="showUpdateLink" value='Y'/>
                    <c:set var="rowUpdatePermission" value='Y'/>
                    <c:if test="${!empty cylinderDocumentBean.lastUpdatedByName}" >
                      <c:set var="rowUpdatePermission" value='N'/>
                    </c:if>

                    /*The row ID needs to start with 1 per their design.*/
                    { id:${status.index +1},
                         <c:if test="${!empty cylinderDocumentBean.lastUpdatedByName}">'class': 'grid_lightgray',</c:if>
                         data:[
                                '${rowUpdatePermission}',
                                '',
                                '${cylinderDocumentBean.documentType}',
                                '<tcmis:jsReplace value="${cylinderDocumentBean.documentName}" />',
                                '<tcmis:jsReplace value="${cylinderDocumentBean.insertedByName}" />',
                                '<fmt:formatDate value="${cylinderDocumentBean.dateInserted}" pattern="${dateFormatPattern}"/>',
                                '${cylinderDocumentBean.dateInserted.time}',
                                '<tcmis:jsReplace value="${cylinderDocumentBean.lastUpdatedByName}" />',
                                '<fmt:formatDate value="${cylinderDocumentBean.lastUpdatedDate}" pattern="${dateFormatPattern}"/>',
                                '${cylinderDocumentBean.lastUpdatedDate.time}',
                                '${status.current.cylinderDocumentId}',
                                '${status.current.documentUrl}',
                                '${status.current.cylinderTrackingId}',
                                '${status.current.supplier}'
                          ]}
                </c:forEach>
            ]};
    </c:if>
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty cylinderDocumentBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>
<!-- Search results end -->
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>
    <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
  </div>
 </div>
</td>
</tr>
</table>

<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input name="totalLines" id="totalLines" value="${fn:length(cylinderDocumentBeanCollection)}" type="hidden">
    <%--Store the search parameters.--%>
    <input type="hidden" name="serialNumber" id="serialNumber" value='<tcmis:jsReplace value="${param.serialNumber}"/>'>
    <input type="hidden" name="cylinderTrackingId" id="cylinderTrackingId" value='<tcmis:jsReplace value="${param.cylinderTrackingId}"/>'>
    <input type="hidden" name="manufacturerName" id="manufacturerName" value='<tcmis:jsReplace value="${param.manufacturerName}"/>'>
    <input type="hidden" name="supplier" id="supplier" value='<tcmis:jsReplace value="${param.supplier}"/>'>
    <input type="hidden" name="userAction" id="userAction" value='<tcmis:jsReplace value="${param.userAction}"/>'>
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
    <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
    <input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>

