<div id="RESULTSPAGE" style="">
  <div id="gridbox" width="100%" height="400px" style="background-color:white;overflow:hidden"></div>
<BR>
<div id="invisibleElements" style="display: none;"></div>
<%--
<BR><BR>
<TD WIDTH="15%" CLASS="announce">
<input type="button" name="submitSearch" value="show html" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="showHtml()" class="SUBMIT">
</TD>
<TD WIDTH="15%" CLASS="announce">
<input type="button" name="showInvisibleHtmleddd" value="show Invisible html" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="showInvisibleHtml()" class="SUBMIT">
</TD>
<BR><BR>
<TEXTAREA name="buyocomments" id="buyocomments">buyordercomments</TEXTAREA>
--%>
</div>

<script type="text/javascript">
<!--
var jsMainData = new Array();
<c:set var="dataCount" value='${0}'/>
<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">
jsMainData[<c:out value="${dataCount}"/>] = {catalogId:"<c:out value="${status.current.catalogId}"/>",
catPartNo:"<c:out value="${status.current.catPartNo}"/>",partDescription:"<c:out value="${status.current.partDescription}"/>",
stockingMethod:"<c:out value="${status.current.stockingMethod}"/>",maxCatalogPrice:"<c:out value="${status.current.maxCatalogPrice}"/>",
currencyId:"<c:out value="${status.current.currencyId}"/>",storageTemp:"<c:out value="${status.current.storageTemp}"/>",
shelfLife:"<c:out value="${status.current.shelfLife}"/>",rowSpan:"<c:out value="${status.current.rowSpan}"/>",
<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
itemcollection:[
<c:forEach var="prCatalogScreenSearchItemBean" items="${itemCollection}" varStatus="status1">
  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
   <c:if test="${status1.index > 0 && listSize > 1 }">
   ,
   </c:if>
  {componentSize:"<c:out value="${componentSize}"/>",bundle:"<c:out value="${status1.current.bundle}"/>",itemId:"<c:out value="${status1.current.itemId}"/>",materialcollection:[
  <c:forEach var="prCatalogScreenSearchComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     ,
    </c:if>
    {materialDesc:"<c:out value="${status2.current.materialDesc}"/>",packaging:"<c:out value="${status2.current.packaging}"/>",grade:"<c:out value="${status2.current.grade}"/>",mfgDesc:"<c:out value="${status2.current.mfgDesc}"/>",mfgPartNo:"<c:out value="${status2.current.mfgPartNo}"/>"}
  </c:forEach>
  ]}
 </c:forEach>
]};
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
totalRows = <c:out value="${dataCount}"/>;
//-->
</script>

<script>
  <c:if test="${prCatalogScreenSearchBeanCollection != null}" >
  mygrid = new dhtmlXGridObject('gridbox');
  mygrid.setImagePath("/test/dhtmlgrid1/imgs/");
  mygrid.setHeader("Catalog, Part Number,Part Description,Stocking Method,Price,Currency,Storage Temp,Shelf Life,Bundle,Item,Material Description,Packaging,Manufacturer,Mfg Part Number,notes");
  mygrid.setInitWidthsP("5,5,15,5,5,5,5,5,5,5,5,10,10,10,5");
  //mygrid.setInitWidths("45,85,150,70,30,55,50,50,50,50,120,150,150,150");
  mygrid.setColAlign("left,left,left,right,center,right,left,right,left,left,left,left,left,left,left");
  mygrid.setColVAlign("top,top,top,middle,middle,middle,top,middle,top,top,top,top,top,top,top");
  mygrid.setColTypes("mro,mro,mro,mro,mro,mro,ed,mro,mro,mro,mro,edmro,mro,mro,mro");
  mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
  //mygrid.enableTooltips("true,true,true,true,true,true,true,true,true,true,true,true,true,true,true");
  //mygrid.setColHidden("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
  mygrid.enableBuffering(20);
  //mygrid.enableAutoHeigth(false,400);
  mygrid.enableKeyboardSupport(false);
  //mygrid.setOnEnterPressedHandler(returnFalse);
  //mygrid.enableMultiline(true);
  //mygrid.enableLightMouseNavigation(true);
  //mygrid.enableCellIds(true);
  //mygrid.enableEditEvents(true,false,false);
  //mygrid.setOnColumnSort(sortSelectedColumn);
  mygrid.setOnRowSelectHandler(rowSelected);
  mygrid.setOnRowDblClickedHandler(rowDblClicked);
  //mygrid.setOnRowSelectHandler(onRowSelect);
  //mygrid.setOnRightClickHandler(rightMouseClicked);
  //mygrid.setNoHeader(false);
  //mygrid.setMultiselect(true);
  mygrid.init(false);

  buildRows(0,20);
  </c:if>

  myshoppingcartgrid = new dhtmlXGridObject('gridbox2');
  myshoppingcartgrid.setImagePath("/test/dhtmlgrid1/imgs/");
  myshoppingcartgrid.setHeader("Part Number,Part Description,Stocking Method,Price,Currency,Quantity,Total");
  myshoppingcartgrid.setInitWidthsP("15,35,10,10,10,10,10");
  myshoppingcartgrid.setColAlign("left,left,left,right,left,right,right");
  myshoppingcartgrid.setColTypes("ro,mro,ro,ro,ro,ed,ro");
  myshoppingcartgrid.setColSorting("na,na,na,na,na,na,na");
  //myshoppingcartgrid.enableTooltips("false,false,false,false,false,false,false");
  myshoppingcartgrid.enableKeyboardSupport(false);
  //myshoppingcartgrid.setOnRowDblClickedHandler(rowDblClicked);
  //myshoppingcartgrid.enableAutoHeigth(false,"150");
  //myshoppingcartgrid.enableEditEvents(true,true,false);
  myshoppingcartgrid.setOnRowSelectHandler(shoppingCartRowSelected);
  myshoppingcartgrid.init(false);
  //myshoppingcartgrid.enableMultiline(true);
  myshoppingcartgrid.addRow(0,"test,%`Fine/\&#039;Gross# leak :&#34;pressurization&#34; medium. To bomb&lt;BR&gt;semi-conductor deviced,test,test,test,test,test",-1);

//%`,Fine/\&#039;Gross# leak :&amp;#34;pressurization&amp;#34; medium. To bomb&lt;BR&gt;semi-conductor deviced
//%`,Fine/&#039;Gross# leak :&amp;#34;pressurization&amp;#34; medium. To bomb&lt;BR&gt;semi-conductor deviced
</script>

<%--
<form action="">
   <div id="divA">
     <input type="text" name="textA" id="textA">
     <input type="button" value="Add input"
      onclick="addInput(this.form.textA)"><br>
   </div>
   <div>
   <input type="submit">
   </div>
</form>


<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<tr align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.partnumber"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="catalog.label.partdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.price"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.shelflife"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.unitOfSaleQuantityPerEach"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.unitOfSale"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.status"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.numperpart"/></TH>--%>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.item"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="inventory.label.componentdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.grade"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="inventory.label.manufacturer"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="inventory.label.mfgpartno"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catalogId}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></TD>
<TD width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><NOBR style="FONT-SIZE: 11px; OVERFLOW: hidden; WIDTH: 200px; FONT-FAMILY: Sans-Serif, Tahoma; TEXT-OVERFLOW: ellipsis"><c:out value="${status.current.partDescription}"/></NOBR></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></TD>

<c:set var="finalPrice" value='${0}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>
<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}'/>
<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}'/>
<c:choose>
 <c:when test="${facilityOrAllCatalog == 'All Catalog'}" >
  <c:choose>
   <c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
     <c:set var="finalPrice" value='${maxCatalogPrice}'/>
   </c:when>
   <c:otherwise>
     <c:if test="${!empty minCatalogPrice}">
        <c:set var="finalPrice" value='${minCatalogPrice}'/>
     </c:if>
     <c:if test="${!empty maxCatalogPrice}">
        <c:set var="finalPrice" value='${maxCatalogPrice}'/>
     </c:if>
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
  <c:set var="finalPrice" value='${minCatalogPrice}'/>
 </c:otherwise>
</c:choose>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty finalPrice}">
     <tcmis:emptyIfZero value="${finalPrice}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${finalPrice}"/></fmt:formatNumber></tcmis:emptyIfZero>
     <c:out value="${status.current.currencyId}"/>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:set var="storageTemp" value='${status.current.storageTemp}'/>
<c:set var="shelfLife" value='${status.current.shelfLife}'/>

<c:choose>
 <c:when test="${empty storageTemp || storageTemp == ' '}" >
 &nbsp;
 </c:when>
 <c:otherwise>
   <c:choose>
    <c:when test="${shelfLife != 'Indefinite'}" >
       <c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}'/>
       <c:if test="${!empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> <c:out value="${shelfLifeBasis}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
      <c:if test="${empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
       </c:if>
    </c:when>
    <c:otherwise>
     <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
    </c:otherwise>
   </c:choose>
 </c:otherwise>
</c:choose>
</TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
   <c:when test="${!empty status.current.unitOfSaleQuantityPerEach}">
     <tcmis:emptyIfZero value="${status.current.unitOfSaleQuantityPerEach}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${status.current.unitOfSaleQuantityPerEach}"/></fmt:formatNumber></tcmis:emptyIfZero>
   </c:when>
   <c:otherwise>
   &nbsp;
   </c:otherwise>
</c:choose>
</TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.unitOfSale}"/></TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
 <c:when test="${empty status.current.approvalStatus}" >
  &nbsp;
 </c:when>
 <c:otherwise>
  <c:out value="${status.current.approvalStatus}"/>
 </c:otherwise>
</c:choose>
</TD>

<c:forEach var="prCatalogScreenSearchItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
   <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
   <!--
    var childRowId  =  document.getElementById("childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
    // -->
    </SCRIPT>
  </c:if>

  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
  <INPUT TYPE="hidden" NAME="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >

  <%--<TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.bundle}"/></TD>--%>
 <%-- <TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/></TD>

  <c:forEach var="prCatalogScreenSearchComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var childRowId  =  document.getElementById("grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
    </c:if>

    <TD width="15%"><c:out value="${status2.current.materialDesc}"/></TD>
    <TD width="5%"><c:out value="${status2.current.packaging}"/></TD>
    <TD width="5%"><c:out value="${status2.current.grade}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgDesc}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgPartNo}"/></TD>

    <c:if test="${status2.index > 0 || componentSize ==1 }">
    </TR>
    </c:if>
  </c:forEach>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </TR>
  </c:if>
 </c:forEach>

</c:forEach>
</TABLE>
<%--
<?xml version="1.0" encoding="UTF-8"?>
<rows>
<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">
<%--<row id="<c:out value="${status.index}"/>">
  <cell><c:out value="${status.current.catalogId}"/></cell>
  <cell><c:out value="${status.current.catPartNo}"/></cell>
  <cell><c:out value="${status.current.partDescription}"/></cell>
  <cell><c:out value="${status.current.packaging}"/></cell>
  <cell><c:out value="${status.current.stockingMethod}"/></cell>
  <cell><c:out value="${status.current.maxCatalogPrice}"/></cell>
  <cell><c:out value="${status.current.currencyId}"/></cell>
  <cell><c:out value="${status.current.storageTemp}"/></cell>
  <cell><c:out value="${status.current.shelfLife}"/></cell>
  <cell><c:out value="${status.current.mfgPartNo}"/></cell>
  <cell><c:out value="${status.current.mfgDesc}"/></cell>
  <cell><c:out value="${status.current.materialDesc}"/></cell>
</row>--%>
<%--["<c:out value="${status.current.catalogId}"/>","<c:out value="${status.current.catPartNo}"/>","<c:out value="${status.current.partDescription}"/>","<c:out value="${status.current.packaging}"/>","<c:out value="${status.current.stockingMethod}"/>"],--%>
<%--var jsData<c:out value="${status.index}"/> = new Array();
jsData<c:out value="${status.index}"/>[0] = ("<c:out value="${status.current.catalogId}"/>");
jsData<c:out value="${status.index}"/>[1] = ("<c:out value="${status.current.catPartNo}"/>");
jsData<c:out value="${status.index}"/>[2] = ("<c:out value="${status.current.partDescription}"/>");
jsData<c:out value="${status.index}"/>[3] = ("<c:out value="${status.current.packaging}"/>");
jsData<c:out value="${status.index}"/>[4] = ("<c:out value="${status.current.stockingMethod}"/>");
jsData<c:out value="${status.index}"/>[5] = ("<c:out value="${status.current.maxCatalogPrice}"/>");
jsData<c:out value="${status.index}"/>[6] = ("<c:out value="${status.current.currencyId}"/>");
jsData<c:out value="${status.index}"/>[7] = ("<c:out value="${status.current.storageTemp}"/>");
jsData<c:out value="${status.index}"/>[8] = ("<c:out value="${status.current.shelfLife}"/>");
jsData<c:out value="${status.index}"/>[9] = ("<c:out value="${status.current.mfgPartNo}"/>");
jsData<c:out value="${status.index}"/>[10] = ("<c:out value="${status.current.mfgDesc}"/>");
jsData<c:out value="${status.index}"/>[11] = ("<c:out value="${status.current.materialDesc}"/>");
jsData<c:out value="${status.index}"/>["rowspan"] = ("1");
<%--
mygrid.addRow("<c:out value="${status.index}"/>",jsData<c:out value="${status.index}"/>,-1);
--%>
<%--jsData[<c:out value="${status.index}"/>] = {catalogId:"<c:out value="${status.current.catalogId}"/>",catPartNo:"<c:out value="${status.current.catPartNo}"/>",partDescription:"<c:out value="${status.current.partDescription}"/>",stockingMethod:"<c:out value="${status.current.stockingMethod}"/>",
maxCatalogPrice:"<c:out value="${status.current.maxCatalogPrice}"/>",currencyId:"<c:out value="${status.current.currencyId}"/>",storageTemp:"<c:out value="${status.current.storageTemp}"/>",shelfLife:"<c:out value="${status.current.shelfLife}"/>",
packaging:"<c:out value="${status.current.packaging}"/>",mfgPartNo:"<c:out value="${status.current.mfgPartNo}"/>",mfgDesc:"<c:out value="${status.current.mfgDesc}"/>",materialDesc:"<c:out value="${status.current.materialDesc}"/>"};
--%>
<%--</c:forEach>
</rows>
--%>
<%--<c:if test="${empty prCatalogScreenSearchBeanCollection}" >
<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>
--%>