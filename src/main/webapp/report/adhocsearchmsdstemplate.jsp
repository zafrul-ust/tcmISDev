<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<script language="JavaScript" type="text/javascript">
<!--
	addMouseEvent();
	
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesDataTemplate = new Array();
messagesDataTemplate = {
		validvalues:"<fmt:message key="label.validvalues"/>",
		pleaseselect:"<fmt:message key="errors.selecta"/>",
		facility:"<fmt:message key="label.facility"/>",
		ph:"<fmt:message key="label.ph"/>",
	    flashpoint:"<fmt:message key="label.flashpoint"/>",
	    vaporpressure:"<fmt:message key="label.vaporpressure"/>",
	    vaporpressureunit:"<fmt:message key="label.vaporpressureunit"/>",
	    vocLwesPercent:"<fmt:message key="label.voclwes"/>",
	    voc:"<fmt:message key="label.voc"/>",
	    solids:"<fmt:message key="label.solids"/>",
	    nfpa:"<fmt:message key="label.nfpa"/>",
	    hmis:"<fmt:message key="label.hmis"/>",
	    health:"<fmt:message key="label.health"/>",
	    flammability:"<fmt:message key="label.flammability"/>",
	    reactivity:"<fmt:message key="label.reactivity"/>",
	    list:"<fmt:message key="label.list"/>",
	    manufacturer:"<fmt:message key="label.manufacturer"/>",
	    casnumber:"<fmt:message key="label.casnumber"/>",
	    constraintsoperators:"<fmt:message key="label.constraintsoperators"/>",
	    decimalgreaterthanzero:"<fmt:message key="label.decimalgreaterthanzero"/>"
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

	with(milonic=new menuname("rightClickStorRemove")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delStor();");
	}

	<%-- Initialize the RCMenus --%>
	drawMenus();

<c:set var="dataCount" value='${0}'/>


var listGridConfig = {
		divName:'listGridDiv', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'listJsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'listBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'listConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
	    onRightClick: onListRightClick,
		onRowSelect: listRoSel
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
	  columnId:"listConstraint",
	  columnName:'<fmt:message key="label.constraint"/>',
	  type:"hcoro",
	  width:10,
	  size:15
},
{
	  columnId:"listOperator",
	  columnName:'<fmt:message key="label.operator"/>',
	  type:"hcoro",
	  width:15,
	  onChange:listOptChange,
	  size:15
},
{
	  columnId:"listValue",
	  columnName:'<fmt:message key="label.value"/>',
	  width:10,
	  width:7,
	  type:'hed',
	  size:7
},
{
	  columnId:"listId"
}
];

<c:set var="dataCount" value='${0}'/>
var listJsonMainData = {
		rows:[
		<c:forEach var="bean" items="${listColl}" varStatus="listStatus">
		<c:if test="${listStatus.index > 0}">,</c:if>
		{id:${listStatus.index},
		 data:[
		  'Y',
		  '${bean.listName}',
		  '${bean.constraint}',
		  '${bean.operator}',
		  '${bean.value}',
		  '${bean.listId}'
		  ]}
		 <c:set var="dataCount" value='${dataCount+1}'/>
		 </c:forEach>
		]};

var casGridConfig = {
		divName:'casGridDiv', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'casJsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'casBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'casConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
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
	  width:5
},

{
	  columnId:"chemicalName",
	  columnName:'<fmt:message key="label.chemicalname"/>',
	  width:5
},
{
	  columnId:"casConstraint",
	  columnName:'<fmt:message key="label.constraint"/>',
	  type:"hcoro",
	  width:7,
	  size:7
},
{
	  columnId:"casOperator",
	  columnName:'<fmt:message key="label.operator"/>',
	  type:"hcoro",
	  onChange:casOptChange,
	  width:7,
	  size:7
},
{
	  columnId:"casValue",
	  columnName:'<fmt:message key="label.value"/>',
	  type:'hed',
	  size:21,
	  width:5
}
];

//This is the array for type:'hcoro'.
var listConstraint = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                  {value:'percent_lower', text: '<fmt:message key="label.minimum"/>'},
                  {value:'percent', text: '<fmt:message key="label.average"/>'},
                  {value:'percent_upper', text: '<fmt:message key="label.maximum"/>'}];

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
                  {value:'<', text: '<'},
                  {value:'is null', text: 'null'},
                  {value:'> threshold', text: '> <fmt:message key="label.threshold"/>'},
                  {value:'>= threshold', text: '>= <fmt:message key="label.threshold"/>'}
                  ];

<c:set var="dataCount" value='${0}'/>
var casJsonMainData = new Array();
var casJsonMainData = {
rows:[
<c:forEach var="bean" items="${casColl}" varStatus="casStat">
<c:if test="${casStat.index > 0}">,</c:if>
{id:${casStat.index},
 data:[
  'Y',
  '${bean.casNum}',
  '${bean.chemicalName}',
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
	if(id == 'list')
	{
		<c:if test="${pageId != 'msdsAnalysis'}">
		document.getElementById("repLisHead").style['display']="";
		document.getElementById("repLisBox").style['display']="";
		document.getElementById("repLisBtn").style['display']="";
		</c:if>
		$('listEditBtn').disabled = false;
		$('addCasBtn').disabled = true;
		$('listGridDiv').style["display"]="";
		$('casGridDiv').style["display"]="none";
		gridDefault = 'list';

	}
	else
	{
		<c:if test="${pageId != 'msdsAnalysis'}">
		document.getElementById("repLisHead").style['display']="none";
		document.getElementById("repLisBox").style['display']="none";
		document.getElementById("repLisBtn").style['display']="none";
		</c:if>
		$('listEditBtn').disabled = true;
		$('addCasBtn').disabled = false;
		$('listGridDiv').style["display"]="none";
		$('casGridDiv').style["display"]="";
		gridDefault = 'cas';
	}

}

function loadTemplate()
{

	document.body.onkeydown =
		function (e) {
		  var keyCode;
		  if(window.event)
		  {
		    keyCode = window.event.keyCode;     //IE
		  }else
		  {
		    try
		    {
		      keyCode = e.which;     //firefox
		    }
		    catch (ex){
		      //return false;
		    }
		  }

		  if (keyCode==13) {
			  if (pageId == 'msdsAnalysis')
			  {
				  var ret = submitSearchForm();
				  return ret;
			  }
			else
		    	return false;
		  }
	}

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

	var blankCheck = '${templateBean.phCompare}';
	if(blankCheck != '')
		$("phCompare").value = blankCheck;

	blankCheck = '${templateBean.flashPointCompare}';
	if(blankCheck != '')
		$("flashPointCompare").value = blankCheck;

	blankCheck = '${templateBean.vaporPressureCompare}';
	if(blankCheck != '')
		$("vaporPressureCompare").value = blankCheck;

	blankCheck = '${templateBean.vocPercentCompare}';
	if(blankCheck != '')
		$("vocPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.vocLwesPercentCompare}';
	if(blankCheck != '')
		$("vocLwesPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.solidsPercentCompare}';
	if(blankCheck != '')
		$("solidsPercentCompare").value = blankCheck;

	blankCheck = '${templateBean.healthCompare}';
	if(blankCheck != '')
		$("healthCompare").value = blankCheck;

	blankCheck = '${templateBean.flammabilityCompare}';
	if(blankCheck != '')
		$("flammabilityCompare").value = blankCheck;

	blankCheck = '${templateBean.reactivityCompare}';
	if(blankCheck != '')
		$("reactivityCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisHealthCompare}';
	if(blankCheck != '')
		$("hmisHealthCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisFlammabilityCompare}';
	if(blankCheck != '')
		$("hmisFlammabilityCompare").value = blankCheck;

	blankCheck = '${templateBean.hmisReactivityCompare}';
	if(blankCheck != '')
		$("hmisReactivityCompare").value = blankCheck;

	blankCheck = '${templateBean.physicalState}';
	if(blankCheck != '')
		$("physicalState").value = blankCheck;

	blankCheck = '${templateBean.temperatureUnit}';
	if(blankCheck != '')
		$("temperatureUnit").value = blankCheck;

	blankCheck = '${templateBean.vaporPressureUnit}';
	if(blankCheck != '')
		$("vaporPressureUnit").value = blankCheck;

	blankCheck = '${templateBean.specificHazard}';
	if(blankCheck != '')
		$("specificHazard").value = blankCheck;

	blankCheck = '${templateBean.personalProtection}';
	if(blankCheck != '')
		$("personalProtection").value = blankCheck;

	blankCheck = '${templateBean.searchField}';
	if(blankCheck != '')
		$("searchField").value = blankCheck;

	blankCheck = '${templateBean.matchType}';
	if(blankCheck != '')
		$("matchType").value = blankCheck;

}

j$().ready(function() {

	j$("#manufacturer").autocomplete("manufacturersearchmain.do?loginNeeded=N&uAction=autoCompleteSearch",{
			width: 528,
			max: 60,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" : "+value.split(":")[1].substring(0,240);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});

	j$('#manufacturer').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateManufacturerInput();
	}));

	j$("#manufacturer").result(mfgLog).next().click(function() {
		j$(this).prev().search();
	});

	function mfgLog(event, data, formatted) {
		$("manufacturer").className = "inputBox";
		$('mfgId').value = formatted.split(":")[0];
		$('manufacturer').title = formatted.split(":")[1];
	}

});

function invalidateManufacturerInput()
{
 if ($v("manufacturer").length == 0) {
   $("manufacturer").className = "inputBox";
 }else {
   $("manufacturer").className = "inputBox red";
//   $("manufacturer").className = "inputBox red";
 }
 //set to empty
 $('mfgId').value = "";
}
// -->
</script>

<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td width ="1%" align="right">
                <table class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
                		<tr><td  class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td></tr>
						<tr>
							<TD  class="optionTitleBoldRight">
								<fmt:message key="label.ph"/>:
							</TD>
						</tr>
						<tr>
							<TD  class="optionTitleBoldRight">
								<fmt:message key="label.flashpoint"/>:
							</TD>
						</tr>
						<tr>
							<TD  class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.vaporpressure"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.voc"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.voclwes"/>:
							</TD>
						</tr>
						<tr>
							<TD class="optionTitleBoldRight">
								%<fmt:message key="label.solids"/>:
							</TD>
						</tr>
					</table>
            </td>
   <td class="optionTitleBoldLeft" nowrap>
       <table width="1%" border="0" >
   			<tr>
   			   	<td class="optionTitleBoldLeft" colspan="3" nowrap>
	        	<select name="searchField" id="searchField" class="selectBox" onchange="showOrHideMsdsNoSpan();">
                	 <c:if test="${pageId != 'adHocInv'}"><option value="item_id"><fmt:message key="label.itemid"/></option></c:if>
                	 <option value="material_id"><fmt:message key="label.materialid"/></option>
                	 <option value="material_desc" selected><fmt:message key="label.materialdesc"/></option>
                	 <option value="customer_msds_number"><fmt:message key="label.msds"/></option>
                	 <c:if test="${pageId != 'adHocInv'}"><option value="cat_part_no"><fmt:message key="label.partnumber"/></option></c:if>
                	 <option value="SPEC_ID"><fmt:message key="label.specification"/></option>
                	 <option value="Synonym"><fmt:message key="label.synonym"/></option>
                	 <option value="trade_name"><fmt:message key="label.tradename"/></option>
                </select>
		        <select name="matchType" id="matchType" class="selectBox" onchange="openMsdsNoTextArea(this.value);">
		               <option value="="> <fmt:message key="label.is"/>  </option>
		               <option value="like" selected> <fmt:message key="label.contains"/>  </option>
		               <option value="starts with"><fmt:message key="label.startswith"/> </option>
		               <option value="ends with"><fmt:message key="label.endswith" /></option>
		               <option value="in csv list"><fmt:message key="label.incsvlist" /></option>
		               <option value="create list"><fmt:message key="label.createlist" /></option>
		        </select>
	            <input name="searchText" id="searchText" type="text" class="inputBox" size="20" value="${templateBean.searchText}">&nbsp;
	            <span id="msdsNoSpan" style="display:none;font-size:160%;vertical-align:bottom;color:blue;"  onclick="showTooltip('hiddenDesc', 'searchText', -50, 23);">
					*
	   			</span>
           		<fmt:message key="label.manufacturer"/>:&nbsp;
            	<input class="inputBox" type="text" name="manufacturer" id="manufacturer" value="${templateBean.mfgDesc}" size="20" maxlength="240" />
 		 		<input name="mfgId" id="mfgId" type="hidden" value="${templateBean.mfgId}">

               	<fmt:message key="label.physicalstate"/>:&nbsp;
	        	<select name="physicalState" id="physicalState" class="selectBox">
                	 <option value=""><fmt:message key="label.all"/></option>
                     <c:forEach var="physicalStateBean" items="${physicalStateColl}" varStatus="status">
                            <option value="${physicalStateBean.physicalState}"><c:out value="${physicalStateBean.physicalStateDesc}"/></option>
                     </c:forEach>
                </select>
				</td>
		</tr>
       <tr><td>
                <table width="33%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">

								<select name="phCompare" id="phCompare" class="selectBox">
									<option value=">">&gt;</option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="ph" id="ph" size="5" maxlength="10" value="${templateBean.ph}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
								<select name="flashPointCompare" id="flashPointCompare" class="selectBox" >
									<option value="&lt;"><</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
								</select>
								<input class="inputBox" type="text" name="flashPoint" id="flashPoint" size="5" maxlength="10" value="${templateBean.flashPoint}">
								<select name="temperatureUnit" id="temperatureUnit" class="selectBox">
									<option value="C">C</option>
									<option value="F" selected>F</option>
								</select>
							</TD>
						</tr>
						<tr>
								<TD width="40%" class="optionTitleBoldLeft" nowrap>
								<select name="vaporPressureCompare" id="vaporPressureCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="vaporPressure" id="vaporPressure" size="5" maxlength="10" value="${templateBean.vaporPressure}">
								<select name="vaporPressureUnit" id="vaporPressureUnit" class="selectBox">
									<option value=""><fmt:message key="label.pleaseselect"/></option>
                     				<c:forEach var="vaporPressureUnitBean" items="${vaporPressureUnitColl}" varStatus="status">
										<option value="${vaporPressureUnitBean.vaporPressureUnit}">${vaporPressureUnitBean.vaporPressureUnit}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
							<select name="vocPercentCompare" id="vocPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
							<input class="inputBox" type="text" name="vocPercent" id="vocPercent" size="5" maxlength="10" value="${templateBean.vocPercent}">
							<select name="vocSearchSelect" id="vocSearchSelect" class="selectBox" style="width:103px">
                     				<c:forEach var="vvVocUnitBean" items="${vocUnitColl}" varStatus="status">
                                        <c:if test="${!empty vvVocUnitBean.vocUnit}">
                                        	<c:choose>
                                        		<c:when test="${vvVocUnitBean.vocUnit == 'g/L'}">
                                        			 <option selected="selected" value="${vvVocUnitBean.vocUnit}">${vvVocUnitBean.vocUnit}</option>
                                        		</c:when>
                                        		<c:otherwise>
                                        			 <option value="${vvVocUnitBean.vocUnit}">${vvVocUnitBean.vocUnit}</option>
                                        		</c:otherwise>
                                        	</c:choose>
                                        </c:if>
                                    </c:forEach>
							</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft" nowrap>
							<select name="vocLwesPercentCompare" id="vocLwesPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
							<input class="inputBox" type="text" name="vocLwesPercent" id="vocLwesPercent" size="5" maxlength="10" value="${templateBean.vocLwesPercent}">
							<select name="vocLwesSearchSelect" id="vocLwesSearchSelect" class="selectBox" style="width:103px">
                                <option selected="selected" value="g/L">g/L</option>
                                <option  value="lb/gal">lb/gal</option>
							</select>

							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="solidsPercentCompare" id="solidsPercentCompare" class="selectBox">
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="solidsPercent" id="solidsPercent" size="5" maxlength="10" value="${templateBean.solidsPercent}">
							</TD>
						</tr>
					</table>
		 </td>
		 <td>
				<fieldset>
					<legend class="optionTitleBold"><fmt:message key="label.nfpa"/></legend>
					<table width="33%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="healthCompare" id="healthCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="health" id="health" size="5" maxlength="10" value="${templateBean.health}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.flammability"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="flammabilityCompare" id="flammabilityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="flammability" id="flammability" size="5" maxlength="10" value="${templateBean.flammability}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="reactivityCompare" id="reactivityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="reactivity" id="reactivity" size="5" maxlength="10" value="${templateBean.reactivity}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.hazard"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="specificHazard" id="specificHazard" class="selectBox">
									<option value=""><fmt:message key="label.all"/></option>
                     				<c:forEach var="specificHazardBean" items="${specificHazardColl}" varStatus="status">
										<option value="${specificHazardBean.specificHazard}">${specificHazardBean.specificHazardDescription}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</fieldset>
		</td>
		<td>
				<fieldset>
					<legend class="optionTitleBold"><fmt:message key="label.hmis"/></legend>
					<table width="33%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisHealthCompare" id="hmisHealthCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisHealth" id="hmisHealth" size="5" maxlength="10" value="${templateBean.hmisHealth}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.flammability"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisFlammabilityCompare" id="hmisFlammabilityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisFlammability" id="hmisFlammability" size="5" maxlength="10" value="${templateBean.hmisFlammability}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" nowrap>
								<select name="hmisReactivityCompare" id="hmisReactivityCompare" class="selectBox">
									<option value="=">=</option>
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="&lt;"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisReactivity" id="hmisReactivity" size="5" maxlength="10" value="${templateBean.hmisReactivity}">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.personalProtection"/>:
							</TD>
							<TD width="60%" class="optionTitleLeft" colspan="3" >
								<select name="personalProtection" id="personalProtection" class="selectBox">
					                <option value=""><fmt:message key="label.all"/></option>
					            	 <c:forEach var="ppBean" items="${ppColl}" varStatus="status">
					                      <option value="${ppBean.personalProtectCode}"><c:out value="${ppBean.personalProtectCode}: ${ppBean.personalProtectDesc}"/></option>
					                </c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</fieldset>
			</td></tr></table>
    </td>
		</tr>
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
	                 	name="None" value=""  OnClick="addCas();return false;"><fmt:message key="label.editlist"/> </button>&nbsp;&nbsp; &nbsp;
		        </td>

		</tr>
		</table>
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td>
		<div class="dataContent">
			<div id="listGridDiv"  style="width:100%;height:150px;"></div>
			<div id="casGridDiv"  style="width:100%;height:150px;"></div>

		   <%-- result count and time --%>
			   <div id="footer" style="display:none"/>
		</div> 
		  </td>
		</tr>
		</table>

<input type="hidden" name="gridType" id="gridType" value=""/>
<input type="hidden" name="gridSubmit" id="gridSubmit" value=""/>
<input type="hidden" name=gridDesc id="gridDesc" value=""/>
<input type="hidden" name="storageAreaId" id="storageAreaId" value=""/>
<input type="hidden" name="storageAreaDesc" id="storageAreaDesc" value=""/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value=""/>
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount + 1}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="searchFieldDesc" id="searchFieldDesc" value=""/>
<input type="hidden" name="matchTypeDesc" id="matchTypeDesc" value=""/>
<input type="hidden" name="specificHazardDesc" id="specificHazardDesc" value=""/>
<input type="hidden" name="personalProtectionDesc" id="personalProtectionDesc" value=""/>
 <input name="mfgDesc" id="mfgDesc" type="hidden" value="">

