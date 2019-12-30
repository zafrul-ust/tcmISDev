<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<title>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var rowid = '${param.rowid}';
var note = '<tcmis:jsReplace value="${param.rcptQualityHoldNote}" processMultiLines="true" />';

function invpg() {
	this.inv='${inv}';
	this.invName='<tcmis:jsReplace value="${invName}"/>';
	this.pg='${pg}';
	this.pgName='<tcmis:jsReplace value="${pgName}"/>';
}

function loadpupup() {
	eval('parent.${param.callback}( new invpg )');
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpupup()">
</body>
</html>