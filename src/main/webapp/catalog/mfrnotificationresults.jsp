<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<c:choose>
<c:when test="${category.manufacturerData}">
<%@ include file="/catalog/mfraffectednotification.jsp" %>
</c:when>
<c:when test="${category.materialData}">
<%@ include file="/catalog/materialaffectednotification.jsp" %>
</c:when>
<c:when test="${category.itemData}">
<%@ include file="/catalog/itemaffectednotification.jsp" %>
</c:when>
</c:choose>
