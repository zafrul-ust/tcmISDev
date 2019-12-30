<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<body>

<form action='<c:out value="${HookUrlBean}"/>' method="post" NAME="aform">
<c:set var="linecount" scope="page" value="1"/>
<c:forEach items="${ShoppingCartBean}" var="item">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-DESCRIPTION[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemDescription}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-QUANTITY[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemQuantity}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-UNIT[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemUnit}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-PRICE[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemPrice}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-CURRENCY[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemCurrency}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-EXT_SCHEMA_TYPE[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemExtSchemaType}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-EXT_CATEGORY_ID[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemExtCategoryId}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-CUST_FIELD2[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemCustField2}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-CUST_FIELD3[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemCustField3}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-CUST_FIELD4[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemCustField4}'/>">
   <INPUT TYPE="HIDDEN" NAME="NEW_ITEM-CUST_FIELD5[<c:out value='${linecount}'/>]" VALUE = "<c:out value='${item.newItemCustField5}'/>">
   <c:set var="linecount" scope="page" value="${linecount + 1}"/>
</c:forEach>
</form>
<script language="JavaScript">
   document.aform.submit();
</script>

</body>
</html>

