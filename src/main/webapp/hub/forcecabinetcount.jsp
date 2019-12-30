<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">

<HEAD>
<META http-equiv="content-type" content="text/html; charset=UTF-8">
<META http-equiv="expires" content="-1">
<LINK rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>

<%@ include file="/common/locale.jsp"%>
<!--Use this tag to get the correct CSS class.
This looks at what the user's prefered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<SCRIPT src="/js/common/formchek.js" language="JavaScript" type="text/javascript"></SCRIPT>
<SCRIPT src="/js/common/commonutil.js" language="JavaScript" type="text/javascript"></SCRIPT>
<!-- This handles which key press events are disabled on this page -->
<SCRIPT src="/js/common/disableKeys.js" language="JavaScript" type="text/javascript"></SCRIPT>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<SCRIPT type="text/javascript" src="/js/menu/milonic_src.js"></SCRIPT>
<SCRIPT type="text/javascript" src="/js/menu/mmenudom.js"></SCRIPT>
<SCRIPT type="text/javascript" src="/js/menu/mainmenudata.js"></SCRIPT>
<SCRIPT type="text/javascript" src="/js/menu/contextmenu.js"></SCRIPT>

<!-- Add any other javascript you need for the page here -->

<SCRIPT language="JavaScript" type="text/javascript">
	
	//add all the javascript messages here, this for internationalization of client
	//side javascript messages
	var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
			required:"<fmt:message key="label.fieldrequired"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
			};
	

	function validateForm() {
		var userId = document.getElementById("userId");
		var bins = document.getElementById("bins");
		var missing = "";
		var message = "";
		
		if (userId == null || userId.value.trim().length < 1) {
			missing += "<fmt:message key="label.personnelid" />";
		}
		else if (!isPositiveInteger(userId.value)) {
			message += "<fmt:message key="label.personnelid" />" + " <fmt:message key="label.errorinteger"/>\n"; 

		}
		if (bins == null || bins.value.trim().length < 1) {
			if (missing.length > 0) {
				missing += ", ";
			}
			missing += "<fmt:message key="label.scannedbins" />";
		}
		if (missing.length > 0) {
			message += messagesData.required + " " + missing;
		}
		
		if (message.length > 0) {
			alert(message);
			return false;
		}
		return true;
	}
</SCRIPT>

	<TITLE><fmt:message key="label.forcecabinetcount" /></TITLE>
</HEAD>
<BODY>
<DIV id="transitPage" class="optionTitleBoldCenter" style="display: none;">
	<BR>
	<BR>
	<BR>
	<fmt:message key="label.pleasewait" /> <BR>
	<BR>
	<BR>
	<IMG src="/images/rel_interstitial_loading.gif" align="middle" alt="">
</DIV>

<DIV class="interface" id="mainPage">

<DIV class="contentArea">
	<tcmis:form action="/forcecabinetcount.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

		<!-- Search Option Begins -->
		<TABLE id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
			<TR>
				<TD>
					<DIV class="roundcont filterContainer">
						<DIV class="roundright">
							<DIV class="roundtop">
								<DIV class="roundtopright"><IMG src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></DIV>
							</DIV>
							<DIV class="roundContent">
	
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					
									<TR>
										<TD width="10%" class="optionTitleBoldRight">
											<fmt:message key="label.personnelid" /> : 
										</TD>
										<TD width="30%" class="optionTitleBoldLeft">
											<INPUT name="userId" id="userId" type="text" class="inputBox" value="" size="10" maxlength="10">
										</TD>
									</TR>
					
									<TR>
										<TD width="10%" class="optionTitleBoldRight">
											<fmt:message key="label.scannedbins" /> : 
										</TD>
										<TD width="30%" class="optionTitleBoldLeft">
											<INPUT name="bins" id="bins" type="text" class="inputBox" value="" size="50">
										</TD>
									</TR>

                                    <TR>
										<TD width="10%" class="optionTitleBoldRight">
											<fmt:message key="label.uploadsequence" /> : 
										</TD>
										<TD width="30%" class="optionTitleBoldLeft">
											<INPUT name="upLoadSeq" id="upLoadSeq" type="text" class="inputBox" value="" size="8">
										</TD>
									</TR>

                                    <TR>
										<TD colspan="2" class="optionTitleBoldLeft">
											<INPUT type="submit" name="uAction" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value='<fmt:message key="label.forcecabinetcount"/>'
											onclick="return validateForm();"
											/> 
										</TD>
									</TR>
					
								</TABLE>
							</DIV>
						</DIV>
						<DIV class="roundbottom">
							<DIV class="roundbottomright"><IMG src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></DIV>
						</DIV>
					</DIV>
				</TD>
			</TR>
		</TABLE>
		<!-- Search results end -->

	</tcmis:form>
</DIV>
<!-- close of contentArea --> <!-- Footer message start -->
<DIV class="messageBar">&nbsp;</DIV>
<!-- Footer message end --></DIV>
<!-- close of interface -->
</BODY>
</html:html>
