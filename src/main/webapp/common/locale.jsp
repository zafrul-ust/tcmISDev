<%-- 
	 To enable locale for a page, include this file.
     This file is used to handle the locale stuff in TCMIS client side, including javascript and jsp

	 Always include this file in your 'head' section, and before any other 'javascript'.
     right before tcmis:fontSizeCss tag would be a good place. 

     The pageLocale variable will 'always' be set after including this file.
     The algorithm is:
     1. If ${tcmisLocale} exists ( means a user is logined), use it.
     2. If it doesn't, use request.getLocale and check is its supported.
     3. If not in supported list, use "en_US".
     
     The global javascript and jsp variables that are set after this are
     Javascript:
		tcmISLocale: would be '' if user are not yet login. session locale if logined.
		pageLocale:  determined by above algorithm, always have a valid value.
		calendarStartMonday: determined by pagelocale
		calendarFormat:
		calendarIndefinite: determined by pagelocale
		
	Jsp:
		${pageLocale}: always have a valid value. determine by above algorithm.
		${dateFormatPattern}: To be used in date related tag, always have a valid value.
     	${dateFormatLabel}: To be used as a label, always have a valid value.
--%>

<script language="JavaScript" type="text/javascript">
<!--
//This for internationalization of client side javascript values.
	<c:set var="browserLocale" value="<%=request.getLocale()%>"/>
	<c:if test="${!empty tcmISLocale}">
		<c:set var="pageLocale" value="${tcmISLocale}"/>
	</c:if>
	<c:if test="${empty tcmISLocale}">
	<c:set var="pageLocale" value="en_US"/>
   	<c:forEach var="localeBean" items="${vvLocaleBeanCollection}"  varStatus="status">
         <c:if test="${status.current.localeCode == browserLocale }">
			<c:set var="pageLocale" value="${browserLocale}"/>     
	     </c:if>
    </c:forEach>
    </c:if>

<fmt:setLocale value="${pageLocale}" scope="page"/>
<c:set var="dateFormatPattern"><fmt:message key="java.dateformat"/></c:set>
<c:set var="dateTimeFormatPattern"><fmt:message key="java.datetimeformat"/></c:set>  
<c:set var="dateShortFormatPattern"><fmt:message key="java.dateShortformat"/></c:set> 
<c:set var="unitpricecurrencyformat"><fmt:message key="jsp.unitpricecurrencyformat"/></c:set>
<c:set var="totalcurrencyformat"><fmt:message key="jsp.totalcurrencyformat"/></c:set>  
<c:set var="oneDigitformat"><fmt:message key="jsp.oneDigitformat"/></c:set>

var tcmISLocale  = "${tcmISLocale}";
var pageLocale   = "${pageLocale}";
var calendarStartMonday = <fmt:message key="javascript.calendarStartMonday"/> ;
var calendarFormat = '<fmt:message key="javascript.calendarFormat"/>';
var calendarIndefinite = '<fmt:message key="label.indefinite"/>';
var displayFormat="new";

// -->
</script>
