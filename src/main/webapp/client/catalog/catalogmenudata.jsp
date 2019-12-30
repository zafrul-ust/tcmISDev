<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
 var jsonobj =
 {
 	reorderPoint:   "${stockingReorder.reorderPoint}",
	partInventory:	"${partInvColl.partInventory}",
	stockingLevel:	"${stockingReorder.stockingLevel}",
    showDirectedCharge: "${showDirectedCharge}",
    Img:	"${ImgLit.Img}",
	Lit:	"${ImgLit.Lit}",
	kitMsdsNumber: "${kitMsdsNumber}", 
	baselineReset: 	"Bas==",
	baselineExpiration: "BasExp==",
	editApprovalCode: ${editApprovalCode}
	<c:if test="${ !empty specColl }">
	, 
	spec: 
	[
		<c:forEach var="spec" items="${specColl}" varStatus="status">
			<c:if test="${ status.index  != 0}">
			,
			</c:if>
			{ 
			  specId: "${spec.specId}",
              content: "${spec.content}",  
              onLine: "${spec.onLine}",
              originalSpecId: "${spec.originalSpecId}"
			}
		</c:forEach>
	]
	</c:if>
	<c:if test="${ !empty partInvColl.itemId }">
	,
	itemInventory: 
	[
		<c:forEach items="${partInvColl.itemId}" varStatus="status">
		<c:if test="${ status.index  != 0}">
		,
		</c:if>
			{ 
				item:	"${status.current}",
				qty:	"${partInvColl.itemQty[status.index]}"
			}
		</c:forEach>
	]
	</c:if>
	<c:if test="${ !empty requestIdColl }">
	,
	requestId: 
	[
		<c:forEach items="${requestIdColl}" varStatus="status">
		<c:if test="${ status.index  != 0}">
		,
		</c:if>
			{ 
				requestId:	"${status.current.requestId}",
				applicationUseGroupName:	"${status.current.applicationUseGroupName}",
				startDate: '<fmt:formatDate value="${status.current.startDate}" pattern="MM/dd/yyyy"/>',
				endDate: '<fmt:formatDate value="${status.current.endDate}" pattern="MM/dd/yyyy"/>'
			}
		</c:forEach>
	]
	</c:if>
 };
 
 eval('parent.${param.callback}(jsonobj)');
 
// -->
</script>
</head>
<body></body>
<%--
/*
    JSONObject obj=new JSONObject();

JSONObject obj2=new JSONObject();
obj2.put("phone","123456");
obj2.put("zip","7890");
obj.put("contact",obj2);

JSONArray array=new JSONArray();
array.put("hello");
array.put(new Integer(123));
array.put(new Boolean(false));
array.put(null);
array.put(new Double(123.45));
array.put(obj2);//see above

    obj.put("name","foo");
    obj.put("num",new Integer(100));
    obj.put("balance",new Double(1000.21));
    obj.put("is_vip",new Boolean(true));
    obj.put("nickname",null);
    obj.put("dataarr",array);
    out.print(obj);
    out.flush();
   */
--%>
