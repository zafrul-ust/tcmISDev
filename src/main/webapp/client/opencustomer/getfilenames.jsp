<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
if('${type}' == 'msds')
  parent.getMSDS('${fileNames}');
else if('${type}' == 'certs')
  parent.getCerts('${fileNames}');
else if('${type}' == 'receipts')
  parent.getReceipts('${receiptId}','${documentId}');
else if('${requestSent}' == 'Y')
  parent.alert('<fmt:message key="label.requestsent"/>');
// -->
</script>
</head>
<body>
<input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', personnelBean.companyId)}'/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
</body>
</html>