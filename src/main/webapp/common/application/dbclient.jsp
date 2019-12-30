<c:set var="module">
 <tcmis:module/>
</c:set>
<%--Don't need the client hidden variable as I started to use dbuser.properties to login to the correct schema.
The only reason to use this to tell which companyId in a schema this user is logging into.
If the databse schema the user is loggin into has multiple companies and you want to restrict
which companies this page can access we define it in this file. Currenlty we do this
for TCM_OPS login to avoid storing user permission data for all clients.
If we store permissions data for all clients that will increase the amount
of data stored in the session many folds. -Nawaz 07-08-08
--%>
<c:choose>
<c:when test='${module == "haas"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "catalog"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "hub"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "invoice"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "invoice2"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "supply"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "saic"}' >
 <html:hidden property="companyId" styleId="companyId" value="SAIC"/>
</c:when>
<c:when test='${module == "accountspayable"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "distribution"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>
<c:when test='${module == "demo"}' >
 <html:hidden property="companyId" styleId="companyId" value="Radian"/>
</c:when>

</c:choose>
