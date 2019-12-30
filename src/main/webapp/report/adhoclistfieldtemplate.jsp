<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<script language="JavaScript" type="text/javascript">
<!--
    addMouseEvent();
    
    //add all the javascript messages here, this for internationalization of client side javascript messages
    var messagesListDataTemplate = new Array();
    messagesListDataTemplate = {
        validvalues:"<fmt:message key="label.validvalues"/>",
        pleaseselect:"<fmt:message key="errors.selecta"/>",        
        list:"<fmt:message key="label.list"/>",
        casnumber:"<fmt:message key="label.casnumber"/>",
        constraintsoperators:"<fmt:message key="label.constraintsoperators"/>",
        decimalgreaterthanzero:"<fmt:message key="label.decimalgreaterthanzero"/>",
        decimalbetweenzeroand100:"<fmt:message key="label.decimalbetweenzeroand100"/>",
        triggersthreshold:"<fmt:message key="label.triggersthreshold"/>",
        triggerscompositionthreshold:"<fmt:message key="label.triggerscompositionthreshold"/>",
        operatorsmissing:"<fmt:message key="label.operatorsmissing"/>",
        valuesmissing:"<fmt:message key="label.valuesmissing"/>",
        select:"<fmt:message key="label.pleaseselect"/>",
        selectcasconstraints:"<fmt:message key="label.selectcasconstraints"/>",
        chemicallistlimit:"<fmt:message key="label.chemicallistlimit"/>",
        selectlistconstraints:"<fmt:message key="label.selectlistconstraints"/>"
        };

    var storSelectedRowId;
    var listSelectedRowId;
    var casSelectedRowId;
    var pageId = '${pageId}';

    Array.prototype.push = function() {
        var n = this.length >>> 0;
        for (var i = 0; i < arguments.length; i++) {
        this[n] = arguments[i];
        n = n + 1 >>> 0;
        }
        this.length = n;
        return n;
    };

    with(milonic=new menuname("rightClickListRemove")){
        top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delList();");
    }

    with(milonic=new menuname("rightClickCasRemove")){
        top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delCas();");
    }
    
    <%--todo I don't think this is use so I am blocking it out for now.
    with(milonic=new menuname("rightClickStorRemove")){
        top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delStor();");
    }--%>

    <%-- Initialize the RCMenus --%>
    drawMenus();




var listGridConfig = {
        divName:'listGridDiv', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'listJsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'listBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'listConfig',         // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,           // this page has rowSpan > 1 or not.
        submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:true, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick: onListRightClick,
        onRowSelect: listRoSel//,
        //onAfterHaasRenderRow: populateListFormatArray     
        };

var listConfig = [
{
       columnId:"permission"
},
{
        columnId:"listName",
        columnName:'<fmt:message key="label.listname"/>',
        tooltip:true,
        width:30
},
{
        columnId:"listFormatText",
        columnName:'<fmt:message key="label.listformat"/>',
        width:25        
},
{
        columnId:"listConstraint",
        columnName:'<fmt:message key="label.constraint"/>',
        type:"hcoro",
        width:25,
        onChange:listConstraintChange,
        size:15
},
{
        columnId:"listOperator",
        columnName:'<fmt:message key="label.operator"/>',
        type:"hcoro",
        width:12,
        onChange:listOptChange,
        size:15
},
{
        columnId:"listValue",
        columnName:'<fmt:message key="label.value"/>',
        width:12,
        type:'hed',
        size:7
},
{
        columnId:"listId"    
},
{
        columnId:"listFormat"
},
{
        columnId:"chemicalFieldX"
},
{
        columnId:"hasThreshold"
},
{
	   columnId:"hasAmountThreshold"
}
];

//This is the array for type:'hcoro'.
//var listFormat = new Array({value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'});
/*
<c:set var="uCount" value="0"/>
<c:set var="igCount" value="0"/>
var listFormatArray = [                       
    <c:forEach var="list" items="${listColl}" varStatus="status">
    <c:if test="${ uCount !=0 }">,</c:if> [
        <c:forEach var="formatDataColl" items="${list.listFormatDataColl}" varStatus="status1">
                <c:if test="${ igCount == 0 }"> 
                {value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'}
                <c:set var="igCount" value="${igCount+1}"/>
              </c:if>                           
               <c:if test="${ igCount !=0 }">,</c:if>
                   {value:'${formatDataColl.id}', text:'<tcmis:jsReplace value="${formatDataColl.displayValue}"/>'}
               <c:set var="igCount" value="${igCount+1}"/>
        </c:forEach>
     ] 
    <c:set var="igCount" value="0"/>
    <c:set var="uCount" value="${uCount+1}"/>
    </c:forEach>
];
if (listFormatArray != null){
    listFormat = listFormatArray[0];    
}
*/

var listConstraint = new Array();
<c:set var="dataCount" value='${0}'/>
var listJsonMainData = {
        rows:[
        <c:forEach var="bean" items="${listColl}" varStatus="listStatus">       
        <c:if test="${listStatus.index > 0}">,</c:if>
        {id:${listStatus.count},
         data:[
          'Y',
          '<tcmis:jsReplace value="${bean.listName}" processMultiLines="true"/>',         
          '<input id="listFormatText${listStatus.count}" name="listFormatText${listStatus.count}" value="${bean.listFormatText}" class="inputBox" readonly="true" type="text" size="40"/> <input name="searchForListFormat" type="button" class="listButton" onmouseover="this.className=\'listButton listButtonOvr\'" onMouseout="this.className=\'listButton\'" name="None" value="" onclick="lookupListFormat(${listStatus.count});">',
          '${bean.constraint}',
          '${bean.operator}',
          '${bean.value}',
          '${bean.listId}',
          '${bean.listFormat}',
          '${bean.chemicalFieldListId}',
          '${bean.hasThreshold}',
          '${bean.hasAmountThreshold}'
          ]}
         <c:set var="dataCount" value='${dataCount+1}'/>
         </c:forEach>
        ]};

<%-- start of mfg lot collection--%>
<c:forEach var="bean" items="${listColl}" varStatus="listStatus">    
       listConstraint['${listStatus.count}']=[
         {value:'None', text: '<fmt:message key="label.none"/>'},
         {value:'OnList', text: '<fmt:message key="label.onlist"/>'},
         <c:if test="${pageId == 'adHocInv' || pageId == 'adHocUsageRpt'}">
         {value:'NotOnList', text: '<fmt:message key="label.notonlist"/>'},
         </c:if>                 
         {value:'percent_lower', text: '<fmt:message key="label.minchemicalpercent"/>'},                                           
         {value:'percent', text: '<fmt:message key="label.avgchemicalpercent"/>'},
         {value:'percent_upper', text: '<fmt:message key="label.maxchemicalpercent"/>'}
         <c:if test="${bean.hasThreshold == 'Y'}">     
         ,{value:'TriggersThreshold', text: '<fmt:message key="label.triggersthreshold"/>'}
         </c:if> 
         <c:if test="${bean.hasAmountThreshold == 'Y'}">     
         ,{value:'TriggersCompositionThreshold', text: '<fmt:message key="label.triggerscompositionthreshold"/>'}
         </c:if>                                           
       ];    
</c:forEach>
<%-- end of mfg lot collection --%>


var casGridConfig = {
        divName:'casGridDiv', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'casJsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'casBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'casConfig',      // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:'false',             // this page has rowSpan > 1 or not.
        submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick: onCasRightClick,
        onRowSelect: casRoSel
        };

var casConfig = [
{
      columnId:"permission"
},
{
      columnId:"casNum",
      columnName:'<fmt:message key="label.cas"/>',
      width:15
},

{
      columnId:"chemicalName",
      columnName:'<fmt:message key="label.chemicalname"/>',
      width:45
},
{
      columnId:"casConstraint",
      columnName:'<fmt:message key="label.constraint"/>',
      type:"hcoro",
      width:15,
      size:7
},
{
      columnId:"casOperator",
      columnName:'<fmt:message key="label.operator"/>',
      type:"hcoro",
      onChange:casOptChange,
      width:15,
      size:7
},
{
      columnId:"casValue",
      columnName:'<fmt:message key="label.value"/>',
      type:'hed',
      size:21,
      width:14
}
];

//This is the array for type:'hcoro'.
var tempListConstraint = [{value:'None', text: '<fmt:message key="label.none"/>'},
                      {value:'OnList', text: '<fmt:message key="label.onlist"/>'},    
                      <c:if test="${pageId == 'adHocInv' || pageId == 'adHocUsageRpt'}">
                      {value:'NotOnList', text: '<fmt:message key="label.notonlist"/>'},
                      </c:if>                  
                      {value:'percent_lower', text: '<fmt:message key="label.minchemicalpercent"/>'},
                      //{value:'chemical_lower_wt', text: '<fmt:message key="label.minchemicalwt"/>'},
                      {value:'percent', text: '<fmt:message key="label.avgchemicalpercent"/>'},
                      //{value:'chemical_wt', text: '<fmt:message key="label.avgchemicalwt"/>'},
                      {value:'percent_upper', text: '<fmt:message key="label.maxchemicalpercent"/>'}
                      //{value:'chemical_upper_wt', text: '<fmt:message key="label.maxchemicalwt"/>'}
                      //<c:if test="${ThresholdForFirstRow == 'Y'}">
                        ,{value:'TriggersThreshold', text: '<fmt:message key="label.triggersthreshold"/>'}
                      //</c:if>
                        ,{value:'TriggersCompositionThreshold', text: '<fmt:message key="label.triggerscompositionthreshold"/>'}
                      ];

//This is the array for type:'hcoro'.
var casConstraint = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                  {value:'percent_lower', text: '<fmt:message key="label.minimum"/>'},
                  {value:'percent', text: '<fmt:message key="label.average"/>'},
                  {value:'percent_upper', text: '<fmt:message key="label.maximum"/>'}];

//This is the array for type:'hcoro'.
var casOperator = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                  {value:'=', text: '='},
                  {value:'>', text: '>'},
                  {value:'>=', text: '>='},
                  {value:'<=', text: '<='},
                  {value:'<', text: '<'},
                  {value:'is null', text: 'null'}
                  ];

//This is the array for type:'hcoro'.
var listOperator = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                  {value:'=', text: '='},
                  {value:'>', text: '>'},
                  {value:'>=', text: '>='},
                  {value:'<=', text: '<='},
                  {value:'<', text: '<'}
                  <c:if test="${pageId != 'adHocMatMx'}">
                  , {value:'notlisted', text: '<fmt:message key="label.notlisted"/>'}
                  </c:if>
                  //,{value:'trace', text: '<fmt:message key="label.trace"/>'}
                  ];

<c:set var="dataCount" value='${0}'/>
var casJsonMainData = new Array();
var casJsonMainData = {
rows:[
<c:forEach var="bean" items="${casColl}" varStatus="casStat">
<c:if test="${casStat.index > 0}">,</c:if>
{id:${casStat.count},
 data:[
  'Y',
  '${bean.casNum}',
  '<tcmis:jsReplace value="${bean.chemicalName}" processMultiLines="true"/>',
  '${bean.constraint}',
  '${bean.operator}',
  '${bean.value}'
  ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

var gridDefault = 'cas';
<c:if test="${gridType == 'list'}">
gridDefault = 'list';
</c:if>


function showListOrCasNos(id)
{
    if(id == 'list') {
        $('listEditBtn').disabled = false;
        $('addCasBtn').disabled = true;
        $('listGridDiv').style["display"]="";
        $('casGridDiv').style["display"]="none";
        gridDefault = 'list';

    }else {
        $('listEditBtn').disabled = true;
        $('addCasBtn').disabled = false;
        $('listGridDiv').style["display"]="none";
        $('casGridDiv').style["display"]="";
        gridDefault = 'cas';
    }

}

function loadTemplate()
{
    <c:choose>
        <c:when test="${pageId == 'msdsAnalysis'}">
            myPopupOnLoadWithGrid(listGridConfig);          
            myPopupOnLoadWithGrid(casGridConfig);
        </c:when>
        <c:otherwise>
            popupOnLoadWithGrid(listGridConfig);            
            popupOnLoadWithGrid(casGridConfig);
        </c:otherwise>
    </c:choose>

    if (gridDefault == 'list')
    {
        showListOrCasNos('list');
        onListLoadShowVal();
    }
    else
    {
        showListOrCasNos('cas');
        onCasLoadShowVal();
    }
}

// -->
</script>

        <table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2"> 
            <table>
             <tr>
               <td class="optionTitleBoldLeft" nowrap>
                <%-- List --%>

                <input name="listOrCasNos" id="list" type="radio" class="radioBtns" value="list" onclick="showListOrCasNos(this.id);" <c:if test="${gridType == 'list'}">checked</c:if>>&nbsp;
                <fmt:message key="label.list"/>&nbsp;
                <button id="listEditBtn" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                    name="None" value=""  OnClick="editChemList();return false;"><fmt:message key="label.editlist"/> </button>&nbsp;&nbsp; &nbsp;
                <%-- This is the fileter for list --%>
                </td>
                <td class="optionTitleBoldLeft" nowrap>
                    <%-- CAS NUMBER --%>
                    <input name="listOrCasNos" id="casNos" type="radio" class="radioBtns" value="casNos" onclick="showListOrCasNos(this.id);" <c:if test="${gridType == 'cas'}">checked</c:if>>&nbsp;
                    <fmt:message key="label.casnumber"/>&nbsp;
                    <button id="addCasBtn" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                        name="None" value=""  OnClick="addCas();return false;"><fmt:message key="label.editlist"/> </button>&nbsp;&nbsp;&nbsp;
                </td>
                <td class="optionTitleBoldLeft" nowrap>                    
                    <img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="moveListOrCasNosFields(true);"/>
                    <img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="moveListOrCasNosFields(false);"/>
                </td>
                </tr>
             </table>
           </td>
        </tr>
        </table>
        <table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td>
        <div class="dataContent">
            <div id="listGridDiv"  style="width:100%;height:130px;"></div> 
            <div id="casGridDiv"  style="width:100%;height:130px;"></div>

           <%-- result count and time --%>
               <div id="footer" style="display:none"/>
        </div> 
          </td>
        </tr>
        </table>

<input type="hidden" name="gridType" id="gridType" value=""/>
<input type="hidden" name="gridSubmit" id="gridSubmit" value=""/>
<input type="hidden" name=gridDesc id="gridDesc" value=""/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="listFormat" id="listFormat" value=""/>
<input type="hidden" name="chemicalFieldListId" id="chemicalFieldListId" value=""/>