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
<tcmis:loggedIn indicator="true" forwardPage="/internalreportajax/index.jsp"/>
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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<SCRIPT language=JavaScript>
<!--

  var req;
  var destination;
  var queryExistFlag;

  function enableSaveButton() {
//    if(document.getElementById("saveQuery").disabled) {
//      document.getElementById("saveQuery").disabled = !(document.getElementById("saveQuery").disabled)
//document.getElementById("tableNameDiv").style.visibility="hidden";
//    }
  }

  function disableSaveButton() {
//    document.getElementById("saveQuery").disabled='true';
//document.getElementById("tableNameDiv").style.visibility="visible";
  }

  function retrieveURL(url, componentName) {
    destination = componentName;
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
        req.open("GET", url, true);
      } catch (e) {
        alert(e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        req.open("GET", url, true);
        req.send();
      }
    }
  }

  function processStateChange() {
    if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        updateValue(req.responseText, destination);
        //updateValue(req.responseXML, destination);
        //document.getElementById("invoicePeriod").value = req.responseText;
      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }

function viewChanged() {
  document.getElementById("tableNameDiv").innerHTML = "wait...";
  document.getElementById("tableCommentDiv").innerHTML = "";
  //document.getElementById("tableDetailDiv").innerHTML = "";
  var tableName = document.getElementById("tableName");
  var selectedTableName = tableName.value;
  retrieveURL("ajax.do?tableName=" + escape(selectedTableName), "viewDetailDiv");
}

function queryChanged() {
  disableSaveButton();
  var queryName = document.getElementById("queryName");
  var selectedQueryName = queryName.value;
  retrieveURL("ajax.do?queryName=" + escape(selectedQueryName), "query");
}

function updateValue(xmlString, destination) {
  var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async="false";
  xmlDoc.loadXML(xmlString);

  if(destination == "query") {
    document.getElementById(destination).value = xmlDoc.getElementsByTagName(destination)[0].firstChild.nodeValue;
    copyQuery();
  }
  else if(destination == "save") {
    var test;
    test = xmlDoc.getElementsByTagName("query")[0].firstChild;
    if(test == null) {
      queryExistFlag = false;
    }
    else {
      queryExistFlag = true;
    }
    submitSaveQuery(queryExistFlag);
  }
  else {
    var data = xmlDoc.getElementsByTagName('data');
    var UserObjectBean = xmlDoc.getElementsByTagName('UserObjectBean');
    var tableName = xmlDoc.getElementsByTagName('tableName')(0).firstChild.nodeValue;
    var tableComment = "";
    if(xmlDoc.getElementsByTagName('comments')(0).firstChild != null) {
      tableComment = xmlDoc.getElementsByTagName('comments')(0).firstChild.nodeValue;
    }
    else {
      tableComment = "&nbsp;";
    }

    //var userColCommentsBeanCollection = xmlDoc.getElementsByTagName('userColCommentsBeanCollection');
    //var detailList = xmlDoc.getElementsByTagName('UserColCommentsBean');
    var tableDetail="<table width=\"500\">";
    var dataArray = xmlToJavascript(xmlDoc,"userColCommentsBeanCollection");
    for(var i=0; i<dataArray.length; i++) {
      tableDetail = tableDetail + "<tr><td width=\"200\">" + dataArray[i].columnName + "</td>";
      tableDetail = tableDetail + "<td width=\"100\">" + dataArray[i].dataType + "</td>";
      tableDetail = tableDetail + "<td width=\"200\">" + dataArray[i].comments + "</td></tr>";
    }
    tableDetail = tableDetail + "</table>";

    document.getElementById("tableNameDiv").innerHTML = tableName;
    document.getElementById("tableCommentDiv").innerHTML = tableComment;
    document.getElementById("tableDetailDiv").innerHTML = tableDetail;
  }
}

function saveQuery(form) {
  if(document.saveQueryForm.query.value.length < 10) {
    alert("Query is required");
  }
  else if(form.queryName.value.length < 1) {
    alert("Query name is required");
  }
  else {
    retrieveURL("ajax.do?queryName=" + escape(form.queryName.value), "save");
  }
//  alert(queryExistFlag);
//  if(queryExistFlag) {
//    alert("query exists");
//  }
  return false;
}

function submitSaveQuery(queryExistFlag) {
  if(queryExistFlag) {
    if(confirm("A query with that name already exists. Do you want to overwrite it?")) {
      document.saveQueryForm.submit();
//alert("saving");
    }
  }
  else {
    //alert("saving");
    document.saveQueryForm.submit();
  }


}

function deleteQuery(form) {
document.deleteQueryForm.queryName.value = document.excelForm.queryName.value;
//alert(form.queryName.value);
if(form.queryName.value.length<1) {
  alert("Query name is required");
   return false;
}
return true;
}

function copyQuery() {
document.saveQueryForm.query.value = document.excelForm.query.value;
//alert(document.saveQueryForm.query.value);
//document.saveQueryForm.query = document.excelForm.query;
}

function xmlToJavascript(xmlDoc, containerTag) {
    var output = new Array( );
    var rawData = xmlDoc.getElementsByTagName(containerTag)[0];
    var i, j, oneRecord, oneObject;
    for (i = 0; i < rawData.childNodes.length; i++) {
        if (rawData.childNodes[i].nodeType == 1) {
            oneRecord = rawData.childNodes[i];
            oneObject = output[output.length] = new Object( );
            for (j = 0; j < oneRecord.childNodes.length; j++) {
                if (oneRecord.childNodes[j].nodeType == 1) {
                    if(oneRecord.childNodes[j].firstChild != null) {
                      oneObject[oneRecord.childNodes[j].tagName] = oneRecord.childNodes[j].firstChild.nodeValue;
                    }
                    else {
                      oneObject[oneRecord.childNodes[j].tagName] = "";
                    }
                }
            }
        }
    }
    return output;
}
-->
</SCRIPT>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
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
<fmt:message key="excel.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">


 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="excel.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

  <logic:notPresent name="userObjectBeanCollection">
  <tr>
    <td colspan="2" >
        <bean:message key="excel.message.notpresent"/>

    </td>
  </tr>
  </logic:notPresent>
  <logic:present name="userObjectBeanCollection">
  <c:if test="${empty userObjectBeanCollection}">
  <tr>
    <td colspan="2" >
        <bean:message key="excel.message.notpresent"/>
    </td>
  </tr>
  </c:if>
  <c:if test="${!empty userObjectBeanCollection}">
  <tr>
    <td colspan="2">
        <html:link forward="showgenericreporthelp" target="new">
          <bean:message key="label.help"/>
        </html:link>
    </td>
  </tr>
  <html:form action="/runreportrelay.do" target="_blank">
  <tr>
    <td width="50%" class="optionTitleBoldLeft">
        <bean:message key="excel.label.query"/>:
    </td>
    <td width="50%" class="optionTitleBoldLeft">
        <fmt:message key="viewquery.header"/>
    </td>
  </tr>
  <tr>
    <td width="40%">
        <html:textarea property="query" styleId="query" styleClass="inputBox" cols="60" rows="10" onchange="copyQuery();"/>
    </td>
    <td width="60%">
      <html:select property="queryName" styleId="queryName" styleClass="selectBox" size="10" onchange="queryChanged();">
          <html:options collection="usersSavedQueriesBeanCollection"
                        property="queryName"
                        labelProperty="queryName"/>
        </html:select>
    </td>
  </tr>
  <tr>
   <td valign="top">
        <html:submit property="submit" styleId="submit" styleClass="inputBtns">
          <bean:message key="excel.label.submit"/>
        </html:submit>
</html:form>
</td>    
<td valign="top">
<html:form action="/deletequery.do" onsubmit="return deleteQuery(this);">
<html:hidden property="queryName" value=""/>
        <html:submit property="saveQuery" styleId="saveQuery" styleClass="inputBtns">
          Delete saved query
        </html:submit>
</html:form>
</td>

</tr>

<div id="saveQueryDiv">
<html:form action="/ajaxsavequery.do" onsubmit="return saveQuery(this);">
        <tr>
          <td colspan="2" class="optionTitleBoldLeft">

              <html:submit styleClass="inputBtns">
                <fmt:message key="savequery.header"/>
              </html:submit>
<html:hidden property="query" value=""/>
&nbsp;&nbsp;<fmt:message key="viewquery.label.queryname"/>: <html:text property="queryName" styleId="queryName" styleClass="inputBox"/>

          </td>

        </tr>
</html:form>
</div>
  <tr><td colspan="2"><hr width="100%" size="1" NOSHADE></td></tr>
  <html:form action="/showgenericreportinput.do">
        <tr>
          <td colspan="2" class="optionTitleBoldLeft">
&nbsp;<fmt:message key="viewdetail.label.tablename"/>
          </td>
        </tr>
  <tr>
    <td valign="top" width="30%">
        <html:select property="tableName" styleId="tableName" styleClass="selectBox" size="15" onchange="viewChanged();">
          <html:options collection="userObjectBeanCollection"
                        property="tableName"
                        labelProperty="tableName"/>
        </html:select>
    </td>
    <td width="50%" align="left">
    <logic:present name="userObjectBeanDetailCollection">
            <table border="0" width="100%">
              <tr>
                <td width="40%" class="optionTitleBoldLeft">
                    <bean:message key="viewdetail.label.tablename"/>
                </td>
                <td width="60%" class="optionTitleBoldLeft" colspan="2">
                    <bean:message key="label.comment"/>
                </td>
              </tr>
              <logic:iterate id="UserObjectBean"
                             name="userObjectBeanDetailCollection"
                             indexId="colCount">
              <tr>
                <td width="40%">
<div id="tableNameDiv">
<c:out value="${UserObjectBean.tableName}"/>
                   
</div>
                </td>
                <td width="60%" colspan="2">
<div id="tableCommentDiv">
<c:out value="${UserObjectBean.comments}"/>
                   
</div>
                </td>
              </tr>
              <tr>
                <td width="100%" colspan="3">
                  <hr width="100%" size="1" NOSHADE>
                </td>
              </tr>
              <tr>
                <td width="40%">
                    <bean:message key="viewdetail.label.columnname"/>
                </td>
                <td width="20%">
&nbsp;
                </td>
                <td width="40%">
&nbsp;
                </td>
              </tr>
<tr>
<td width="100%" colspan="3">
<div id="tableDetailDiv">
<table width="100%">
             <logic:iterate id="UserColCommentsBean"
                             name="UserObjectBean"
                             property="userColCommentsBeanCollection">
              <tr>
                <td width="40%">
<c:out value="${UserColCommentsBean.columnName}"/>
                   
                </td>
                <td width="20%">
&nbsp;
                </td>
                <td width="40%">
&nbsp;
                </td>
              </tr>
            </logic:iterate>
</table>
</div>
</td>
</tr>
            </logic:iterate>
            </table>
          </logic:present>
          </td>
        </tr>

    </html:form>
  </c:if>
  </logic:present>
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<logic:present name="errorBean" scope="request">
    <bean:message key="excel.label.oracleerror"/>: 
<c:out value="${errorBean.cause}"/>

</logic:present>
</div>
<!-- Error Messages Ends -->



  </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->


</body>
<%--
<html:javascript formName="excelForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>







