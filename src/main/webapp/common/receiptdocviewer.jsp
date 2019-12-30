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
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:fontSizeCss />
	<%-- Add any other stylesheets you need for the page here --%>

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>

	<title>
		<fmt:message key="label.itar"/>
	</title>
	
	<script language="JavaScript" type="text/javascript">
	<!--
        windowCloseOnEsc = true;
        //add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			maxUpload:"<fmt:message key="label.maxupload"/>",
			verifyRemove:"<fmt:message key="label.verifyremovefromqueue"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
			};
	
		function closeThisWindow() {
		 	
		}
	
		function initializeThisWindow() {

		}
		
	// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="initializeThisWindow();" onunload="closeThisWindow();">
	<tcmis:form action="/receiptdocviewer.do" onsubmit="return submitOnlyOnce();">
		<div class="interface" id="mainPage">
			<div class="contentArea">
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
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                                            <tr>
                                                <td>
                                                    <center><fmt:message key="label.itarcontrolledspec"/></center>
                                                </td>
                                            </tr>
											<tr>
												<td>
													<center><input name="viewDoc" id="viewDoc" type="submit" value="<fmt:message key="label.viewdocument"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"></center>
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

				<div class="spacerY">&nbsp;</div>

				<!-- Error Messages Begins -->
				<div id="errorMessagesArea" class="errorMessages">
					<html:errors/>
					<c:if test="${result == 'OK'}">
						<fmt:message key="label.successfulupload"/>
					</c:if>
				</div>

				 <div id="hiddenElements" style="display: none;">
                     <input type="hidden" name="uAction" id="uAction" value='${param.uAction}'/>
                     <input type="hidden" name="companyId" id="companyId" value='${param.companyId}'/>
                     <input type="hidden" name="receiptId" id="receiptId" value='${param.receiptId}'/>
                     <input type="hidden" name="viewItarConfirmed" id="viewItarConfirmed" value='Y'/>
                     <input type="hidden" name="documentId" id="documentId" value='${param.documentId}'/>
                 </div>
			</div>

			 <div class="messageBar">&nbsp;</div>
		</div>
	</tcmis:form>
</body>
</html:html>