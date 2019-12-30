<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<!-- Add any other stylesheets you need for the page here -->

<link rel="stylesheet" type="text/css" href="/yui/build/fonts/fonts-min.css" />
<link type="text/css" rel="stylesheet" href="/yui/build/treeview/assets/skins/sam/treeview.css">
<script src = "/yui/build/yahoo/yahoo-min.js" ></script>
<script src = "/yui/build/event/event-min.js" ></script>
<script src = "/yui/build/treeview/treeview-min.js" ></script>


<title>
<fmt:message key="label.approvalrulesresult"/>
</title>

<c:if test="${beanCollection != null}" >
 <script type="text/javascript">
 <!--
 
    var previousNode = null;
    var selectedNode = null;
    function demoOnLoad() {
	    var tree = new YAHOO.widget.TreeView("TreeRoot");
	    var rootNode = tree.getRoot();
        var curAppNode = null;
        var curAppDetailNode = null;
        var curMaterialNode = null;
        var curChemNode = null;

        <c:set var="currentLine" value=''/>
        <c:set var="currentApprovalRole" value=''/>
	    <c:forEach var="approvalBean" items="${beanCollection}" varStatus="status">
            <c:if test="${currentLine != approvalBean.lineItem}">
                <c:set var="currentItem" value=''/>
                <c:if test="${!empty approvalBean.itemId}">
                    <c:set var="currentItem">(<fmt:message key="label.item"/> : ${approvalBean.itemId})</c:set>
                </c:if>
                curLineItemNode = new YAHOO.widget.TextNode("<fmt:message key="label.line"/> : ${approvalBean.lineItem} ${currentItem}", rootNode, true );
            </c:if>

            <c:if test="${currentApprovalRole != approvalBean.approvalRole}">
                <c:set var="approvalRoleStatus" value=''/>
                <c:if test="${param.showPassAndFailReviewRules == 'Y'}">
                    <c:if test="${approvalBean.reviewRequired == 'Y'}">
                        <c:set var="approvalRoleStatus">(<fmt:message key="label.requiresreview"/>)</c:set>
                    </c:if>
                    <c:if test="${approvalBean.reviewRequired == 'N'}">
                        <c:set var="approvalRoleStatus">(<fmt:message key="label.autoapproved"/>)</c:set>
                    </c:if>
                    <c:if test="${approvalBean.reviewRequired == 'R'}">
                        <c:set var="approvalRoleStatus">(<fmt:message key="label.autorejected"/>)</c:set>
                    </c:if>
                </c:if>
                curAppNode = new YAHOO.widget.TextNode("${approvalBean.approvalRole} ${approvalRoleStatus}", curLineItemNode, false );
	        </c:if>

             <%-- messages to approver from test functions --%>
            <c:if test="${!empty approvalBean.messageTextColl}">
                <c:forEach var="approvalReviewMessageViewBean" items="${approvalBean.messageTextColl}" varStatus="status3">
                    curAppDetailNode = new YAHOO.widget.TextNode('<tcmis:jsReplace value="${approvalReviewMessageViewBean.messageText}" processMultiLines="true"/>', curAppNode, false );
                 </c:forEach>
            </c:if>

            <%-- output statement from test functions --%>
            <c:if test="${!empty approvalBean.outputStatement && approvalBean.outputStatement != 'Fail'}">
                curAppDetailNode = new YAHOO.widget.TextNode('<tcmis:jsReplace value="${approvalBean.outputStatement}" processMultiLines="true"/>', curAppNode, false );
            </c:if>

            <c:if test="${approvalBean.fail == 'Y'}">
                <c:set var="currentMaterial" value=''/>
                <c:forEach var="CaiLineObjBean" items="${approvalBean.listCas}" varStatus="status2">
                    <c:if test="${currentMaterial != CaiLineObjBean.materialId}">
                        curMaterialNode = new YAHOO.widget.TextNode('<tcmis:jsReplace value="${CaiLineObjBean.materialDesc}" processMultiLines="true"/>', curAppDetailNode, false );
                    </c:if>
                    curChemNode = new YAHOO.widget.TextNode('<tcmis:jsReplace value="${CaiLineObjBean.casNumber}"/> : <tcmis:jsReplace value="${CaiLineObjBean.chemicalName}" processMultiLines="true"/> : ${CaiLineObjBean.percentage}%&nbsp;', curMaterialNode, false );
                    <c:set var="currentMaterial" value="${CaiLineObjBean.materialId}"/>
                </c:forEach>
            </c:if>

            <c:set var="currentApprovalRole" value="${approvalBean.approvalRole}"/>
            <c:set var="currentLine" value="${approvalBean.lineItem}"/>
	    </c:forEach>

        tree.draw();
	
	    if(selectedNode != null) {
	    	document.getElementById(selectedNode).style["border"] = "solid 2px gray";
	    	previousNode = selectedNode;
	    }
	
	    tree.subscribe("labelClick", function(node) {
		    currentIndex = node.data;
		    currentXId = node.xID;
		    var id = node.labelElId;
		    document.getElementById(id).style["border"]  = "solid 2px gray";
		    if( previousNode != null )
			    document.getElementById(previousNode).style["border"] = "";
		    if( previousNode == id )
			    previousNode = null;
		    else
			    previousNode = id;
	    });

    }   //end of method

    function resizeTree() {
	    var height;
	    if (self.innerWidth) {
		    height = self.innerHeight;
	    } else if (document.documentElement && document.documentElement.clientWidth) {
		    height = document.documentElement.clientHeight;
	    } else if (document.body) {
		    height = document.body.clientHeight;
	    }
	    
	    document.getElementById("TreeRoot").style.height =  (height - 60)+ "px";
    }

// -->
</script>
</c:if>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="demoOnLoad();" onresize="resizeTree();">

<div class="interface" id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div class="backGroundContent">

<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanCollection}" >
    <table width="100%" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0" height="90%" bgcolor="white">
		<tr>
    		<td valign="top">
				<div id="resultDiv" style="width: 100%; height: 100%; overflow: auto">
					<fmt:message key="label.approvalrulesresult"/> (<fmt:message key="label.request"/>: ${param.requestId}<c:if test="${!empty archivedDate}"> <fmt:message key="label.asof"/> <fmt:formatDate value="${archivedDate}" pattern="${dateFormatPattern}"/></c:if>)
					<div id="TreeRoot" class="treeClass" style="height: 440px; overflow: auto"></div>
				</div>
			</td>
    	</tr>
   </table>
</c:if>

<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>


<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

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

showUpdateLinks = true;
//-->
</script>

</body>
</html:html>