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
// content of your Javascript goes here
var jsonobj = new Array();
<c:forEach var="detailBean" items="${detailColl}" varStatus="status">
	jsonobj[${status.index}]={
		   specDetailType:   		'<tcmis:jsReplace value='${detailBean.specDetailType}'/>',	
		   specDetailClass:   		'<tcmis:jsReplace value='${detailBean.specDetailClass}'/>',	
		   specDetailForm:   		'<tcmis:jsReplace value='${detailBean.specDetailForm}'/>',	
		   specDetailGroup:   		'<tcmis:jsReplace value='${detailBean.specDetailGroup}'/>',			
		   specDetailGrade:   		'<tcmis:jsReplace value='${detailBean.specDetailGrade}'/>',	
		   specDetailStyle:   		'<tcmis:jsReplace value='${detailBean.specDetailStyle}'/>',	
		   specDetailFinish:   		'<tcmis:jsReplace value='${detailBean.specDetailFinish}'/>',	
		   specDetailSize:   		'<tcmis:jsReplace value='${detailBean.specDetailSize}'/>',	
		   specDetailColor:   		'<tcmis:jsReplace value='${detailBean.specDetailColor}'/>',	
		   specDetailOther:   		'<tcmis:jsReplace value='${detailBean.specDetailOther}'/>'	   		 		
	};
</c:forEach>
  parent.passDefaultValues(jsonobj[0]);
// -->
</script>
</head>
<body></body>
</html>