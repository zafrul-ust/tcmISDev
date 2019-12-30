<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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

    <!--Use this tag to get the correct CSS class
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
    <tcmis:fontSizeCss/>
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


    <!-- Add any other javascript you need for the page here -->
    <script type="text/javascript" src="/js/hub/dodaacupdatesearch.js"></script>

    <title>
        <fmt:message key="dodaacupdate.title"/>
    </title>

    <script language="JavaScript" type="text/javascript">
        <!--
        //add all the javascript messages here, this for internationalization of client side javascript messages
        var messagesData = new Array();
        messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            dodaacorsearchtextrequired:"<fmt:message key="label.dodaacorsearchtextrequired"/>"};
        // -->
    </script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/dodaacupdateresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0">
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
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.dodaactype"/>:</td>

                                 <td>

                            <select name="dodaacType"
                                    class="selectBox"
                                    id="dodaacType">
                                    <option value=""><fmt:message key="label.all"/></option>
                                <c:forEach var="dodaacTypeBean" items="${vvDodaacTypeCollection}"
                                           varStatus="locationTypes">
                                    <option value="<c:out value="${locationTypes.current.dodaacType}" /> "
                                    <c:if test="${locationTypes.current.dodaacType == viewBean.current.dodaacType}" >
                                             selected
                                     </c:if>>
                                     <c:out value="${locationTypes.current.dodaacTypeDesc}"  />

                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                            
                                <td class="optionTitleBoldRight"><fmt:message key="label.dodaac"/>:</td>
                                <td><input name="dodaac" id="dodaac" type="text" size="6" class="inputBox"></td>
                            </tr>
                            <tr>
                                <td class="optionTitle">&nbsp;</td>
                                <td class="optionTitle">&nbsp;</td>
                                <td class="optionTitleBoldRight"><fmt:message key="label.searchtext"/>:</td>
                                <td><input name="searchText" id="searchText" type="text" size="15" class="inputBox">
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldLeft" colspan="3">
                                    <input name="submitSearch" id="submitSearch" type="submit"
                                           value="<fmt:message key="label.search"/>" class="inputBtns"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="return submitSearchListForm();">

                                    <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit"
                                           class="inputBtns" value="<fmt:message key="label.createexcelfile"/>"
                                           onmouseover="this.className='inputBtns inputBtnsOver'"
                                           onmouseout="this.className='inputBtns'"
                                           onclick="generateExcel(); return false;">
                                    <tcmis:facilityPermission indicator="true" userGroupId="DodaacAddressUpdate">
                                     <input name="submitNew" id="submitNew" type="submit" value="<fmt:message key="label.newaddress"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitNewForm('nododaac'); return false;">
                                    </tcmis:facilityPermission>
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
        <div id="errorMessagesArea" class="errorMessages">
            <html:errors/>
        </div>
    </div>
</c:if>
<!-- Error Messages Ends -->

</div>
<!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">

</div>
<!-- Hidden elements end -->

</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>