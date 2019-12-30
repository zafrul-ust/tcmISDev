<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<html:html lang="true">

<head>

  <title>Haas TCM Purchase Orders</title>

 <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>

</head>



<body>



<table width="100%">

  <tr>

     <%-- Banner --%>

     <td width="90%"><img src="/images/tcmtitlegif.gif"></td>

     <td class="pagetitle" align="right">PO&nbsp;Summary</td>

  </tr>

  <tr>

     <td class="heading"><b>Web DBuy</b></td>

     <td class="headingr2" align="right" width="5%"><a class="tbar" href="logout.do">Log&nbsp;Off</a></td>

  </tr>         

</table>



<logic:present name="PersonnelBean">

    <table width='100%'>

    <tr>

      <td>

         <bean:write name="PersonnelBean" property="firstName"/>&nbsp;<bean:write name="PersonnelBean" property="lastName"/>

      </td>

    </tr>   

    </table>

</logic:present>



<br>



<form name="DisplayPOForm"  action="showpo.do">

<table>

<tr>

  <td>

    <table>

      <tr>

        <td valign="top" ><input type="radio" name="showpos" value="bystatus" 

          <logic:equal name="ShowPO" value="bystatus">

            checked

          </logic:equal>

            ><b>Status:</b></td>

        <td>&nbsp;</td>

        <td>

           <table>

            <tr>

              <td><input type="checkbox" name="stat_new" value="NEW" <logic:present name="StatNew">checked</logic:present> >New</input></td>

              <td><input type="checkbox" name="stat_prob" value="PROBLEM" <logic:present name="StatProb">checked</logic:present> >Problem</input></td>

              <td>&nbsp;&nbsp;&nbsp;&nbsp;within&nbsp;<input type="text" name="numdays" size="4" value='<logic:present name="NumDays"><bean:write name="NumDays"/></logic:present>'>&nbsp;days</td>          

            </tr>

            <tr>

              <td><input type="checkbox" name="stat_ack" value="ACKNOWLEDGED" <logic:present name="StatAck">checked</logic:present> >Acknowledged</input></td>

              <td><input type="checkbox" name="stat_res" value="RESOLVED" <logic:present name="StatRes">checked</logic:present> >Resolved</input></td>

              <td></td>

            </tr>

            <tr>

              <td><input type="checkbox" name="stat_conf" value="CONFIRMED" <logic:present name="StatConf">checked</logic:present> >Confirmed</input></td>

              <td><input type="checkbox" name="stat_rej" value="REJECTED" <logic:present name="StatRej">checked</logic:present> >Rejected</input></td>

              <td></td>

            </tr>

           </table>

        </td>

      </tr>

      <tr>

        <td colspan="3">&nbsp;</td>

      </tr>

      <tr>

        <td valign="top"><input type="radio" name="showpos" value="bypo"

          <logic:equal name="ShowPO" value="bypo">

            checked

          </logic:equal>        

            ><b>PO:</b></td>

        <td>&nbsp;</td>

        <td><input type="text" name="ponum" value='<logic:present name="POSearch"><bean:write name="POSearch"/></logic:present>' size="10"></td>    

      </tr>

    </table>

  </td>

  <td>&nbsp;</td>

  <td>

     <input type='submit' name='Search' value='Search' class='SUBMIT' onmouseover='className="SUBMITHOVER"' onmouseout='className="SUBMIT"'>

  </td>

</tr>

</table>

</form>



<br>



<logic:present name="OrderBeans">

<form name="formSortData" action="sortview.do">

  <input type="hidden" name="sortfield" value="">

  <input type="hidden" name="path" value="Order">

</form>



<script>

function sortView(field) {

  document.formSortData.sortfield.value = field;

  document.formSortData.submit();

}

</script>

    

  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">

  <c:set var="rowCount" value='${0}'/>

  <c:forEach items="${OrderBeans}" var="line">

    <c:set var="rowCount" value='${rowCount+1}'/>

    <c:choose>

      <c:when test="${rowCount % 2 == 0}" >

        <c:set var="colorClass" value='blue'/>

      </c:when>

      <c:otherwise>

        <c:set var="colorClass" value='white'/>

      </c:otherwise>

    </c:choose>

    <c:if test="${rowCount % 10 == 1}">

      <TR align="center">

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('radian_po')">PO Num.</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('home_company_name')">Company</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('ship_to_location_id')">Ship To</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('date_created')">Date Created</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('critical')">Critical</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('dbuy_status')">Status</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('days_since_last_status')">Current Status</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('date_acknowledgement')">First Viewed</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('date_confirmed')">Confirmed</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('vendor_ship_date')">Promised Ship Date</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('promised_date')">Est. Dock Date</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('comments')">Comments</td>

      </TR>    

    </c:if>



      <TR>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <a href='purchaseorder.do?po=<c:out value="${line.radianPo}"/>'><c:out value="${line.radianPo}"/></a>

        </TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.homeCompanyName}"/></TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.shipToLocationId}"/></TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <fmt:formatDate var="fmtCreateDate" value="${line.dateCreated}" pattern="MM/dd/yyyy"/>

          <c:out value="${fmtCreateDate}"/>          

        </TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.critical}"/></TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.dbuyStatus}"/></TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.daysSinceLastStatus}"/></TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <fmt:formatDate var="fmtAckDate" value="${line.dateAcknowledgement}" pattern="MM/dd/yyyy"/>

          <c:out value="${fmtAckDate}"/>          

        </TD>

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <fmt:formatDate var="fmtConfDate" value="${line.dateConfirmed}" pattern="MM/dd/yyyy"/>

          <c:out value="${fmtConfDate}"/>          

        </TD>        

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <fmt:formatDate var="fmtShipDate" value="${line.vendorShipDate}" pattern="MM/dd/yyyy"/>

          <c:out value="${fmtShipDate}"/>          

        </TD>        

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">

          <fmt:formatDate var="fmtDockDate" value="${line.promisedDate}" pattern="MM/dd/yyyy"/>

          <c:out value="${fmtDockDate}"/>          

        </TD>        

        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${line.comments}"/></TD>

      </TR>

  </c:forEach>   

  </TABLE>

    

</logic:present>          



<logic:present name="ProblemOrReject">

<br>

<script>

  function showDetails() {

     if (document.detailFrm.problem.checked==false &&

        document.detailFrm.reject.checked==false) {

        alert("Please check at least one of the selections to see details.");

     } else {

        document.detailFrm.submit();

     }

  }

</script>

<br>

<form name="detailFrm" action="probdetail.do">

<table>

 <tr>

   <td>

     <input type="button" name="viewDetail" value="Show Details" onClick="showDetails();" class='SUBMIT' onmouseover='className="SUBMITHOVER"' onmouseout='className="SUBMIT"'> 

   </td>

   <td>

     for

   </td>

   <td>

     <input type="checkbox" name="problem" value="Problem" checked>Problems</input>

   </td>

   <td>

     <input type="checkbox" name="reject" value="Reject" checked>Rejections</input>

   </td>

 </tr>

</form>

</logic:present>



</body>



</html:html>

