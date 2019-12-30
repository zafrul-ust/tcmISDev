var hcalPageLocale = 'en_US';

if (typeof(window.pageLocale) != 'undefined') {
	hcalPageLocale = window.pageLocale;
}
var hcalMonth = new Array();
hcalMonth["en_US"] = {"Jan":0,"Feb":1,"Mar":2,"Apr":3,"May":4,"Jun":5,"Jul":6,"Aug":7,"Sep":8,"Oct":9,"Nov":10,"Dec":11};
hcalMonth["en_GB"] = {"Jan":0,"Feb":1,"Mar":2,"Apr":3,"May":4,"Jun":5,"Jul":6,"Aug":7,"Sep":8,"Oct":9,"Nov":10,"Dec":11};
hcalMonth["de_DE"] = {"Jan":0,"Feb":1,"Mrz":2,"Apr":3,"Mai":4,"Jun":5,"Jul":6,"Aug":7,"Sep":8,"Okt":9,"Nov":10,"Dez":11};
hcalMonth["pl_PL"] = {"Sty":0,"Lut":1,"Mar":2,"Kwi":3,"Maj":4,"Cze":5,"Lip":6,"Sie":7,"Wrz":8,"Paź":9,"Lis":10,"Gru":11};
function onselectHcal(hidRowId, hidColIndex, selectedVal) {
	if ( _onChangeFunctionArray[hidColIndex] ) {
		_onChangeFunctionArray[hidColIndex](hidRowId,hidColIndex);
	} else if (selectedVal == '') {
		haasGrid.cells(hidRowId,hidColIndex).setValue('');
	}  else {
		var dateFields = selectedVal.split("-");
		var dateVal = new Date();
		dateVal.setFullYear(dateFields[2],hcalMonth[hcalPageLocale][dateFields[1]],dateFields[0]);
		haasGrid.cells(hidRowId,hidColIndex).setValue(dateVal.getTime());
	}
}
function eXcell_hcal(cell){                                    
 if (cell){                                                     
     this.cell = cell;
     this.grid = this.cell.parentNode.grid;
 };
 this.setValue=function(val){
	var columnId = this.grid.getColumnId(this.cell._cellIndex);
	var pColIndex = null;
	if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
		if (typeof permissionColumns[columnId] != 'undefined') {
			pColIndex = this.grid.getColIndexById(columnId + 'Permission');
		} else {
			pColIndex = this.grid.getColIndexById('permission');
		}
	} else {		
		pColIndex = this.grid.getColIndexById('permission');
	}
	var permission;
	if (pColIndex == undefined) {
		permission = 'N';
	} else {
		permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
	}
	if (permission == 'Y') {
	 	var textId="" + columnId+ this.cell.parentNode.idd;
	 	var size;
	 	if (!fontSizePref) {
	 		size = 10;
	 	} else {
		 	switch (fontSizePref)
		 	{
		 	case "Smallest":
		 	  size=8;
		 	  break;
		 	case "Small":
		 	  size=10;
		 	  break;
		 	case "Medium":
		 	  size=9;
		 	  break;
		 	case "Large":
			  size=9;
			  break;
		 	case "Largest":
			  size=8;
			  break;		 	  
		 	default:
		 	  size=10;
		 	};
	 	};
	 	var hcalOnchange = "";
	 	var hidColIndex = this.grid.getColIndexById("hidden" + columnId.substring(0,1).toUpperCase() + columnId.substring(1));
	 	if (hidColIndex != undefined) {
	 		hcalOnchange = "onchange=\"onselectHcal(" + this.cell.parentNode.idd + ",'" + hidColIndex + "',this.value);\" ";
	 	} else if( _onChangeFunctionArray[columnId] )
	 		hcalOnchange = "onchange=\"onselectHcal(" + this.cell.parentNode.idd + ",'" + columnId + "',this.value);\" ";
	    this.setCValue("<input class='inputBox pointer' readonly='true' type='text' " + 
		         "id='" + textId +  "' value='" + val + "' size='" + size + "' " +
		         hcalOnchange + 
		         "onclick=\"return getCalendar(document.getElementById('" +  textId + "')," +
		         "document.getElementById('blockBefore_" + columnId + "'),document.getElementById('blockAfter_" + columnId + "')," +
		         "document.getElementById('blockBeforeExclude_" + columnId + "'),document.getElementById('blockAfterExclude_" + columnId + "')," +
		         "document.getElementById('inDefinite_" + columnId + "').value);\"/>",val);
	} else {
//		if (val.length == 0) {
//			this.setCValue("<label>&nbsp;</label>",val);
//		} else {
			this.setCValue("<label>" + val + "</label>",val);
//		}
	}
 };
 this.getValue=function(){
	var pColIndex = null;
	if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		if (typeof permissionColumns[columnId] != 'undefined') {
			pColIndex = this.grid.getColIndexById(columnId + 'Permission');
		} else {
			pColIndex = this.grid.getColIndexById('permission');
		}
	} else {		
		pColIndex = this.grid.getColIndexById('permission');
	}
	var permission;
	if (pColIndex == undefined) {
		permission = 'N';
	} else {
		permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
	}
	if (permission == 'Y') {	 
		return this.cell.firstChild.value; // get value
	} else {
		return this.cell.firstChild.innerHTML; // get value
	}
 };
 this.edit=function(){};
 this.detach=function(){
	 return false;
 };
}
eXcell_hcal.prototype = new eXcell;    // nest all other methods from base class
function eXcell_hlink(cell){
    if (cell){                                
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}                                //read-only cell doesn't have edit method
    this.isDisabled = function(){ return true; }      // the cell is read-only, that's why it is always in the disabled state
    this.setValue=function(val){
//    	if (val.length == 0)
//    		this.setCValue("<label>&nbsp;</label>",val);
//    	else
    		this.setCValue("<label class='pointer' style='color:blue;text-decoration:underline'>" + val + "</label>",val);
     };
    this.getValue=function(){
    	return this.cell.firstChild.innerHTML; // get value
     };
}
eXcell_hlink.prototype = new eXcell;    // nest all other methods from base class

function _hedOnChange(rowId,columnId) {
	_onChangeFunctionArray[columnId](rowId,columnId);
}

var _onChangeFunctionArray = new Array();

function eXcell_hed(cell){                                    
	 if (cell){                                                     
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			} 
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		var hedOnChange ="";
		try 
		{
		  if( _onChangeFunctionArray[columnId] )
		  	hedOnChange = ' onchange=_hedOnChange';
		  else 
		  	hedOnChange =  eval(''+columnId+'_hedFunction');			
		  hedOnChange +="("+this.cell.parentNode.idd+",'"+columnId+"')";
		}
		catch(exp)
		{
		 hedOnChange ="";
		}	
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		if (permission == 'Y') {	
		    var textId="" + columnId+ this.cell.parentNode.idd;
		    var size = 0;
		    try {
			    var inputSizeArray = eval("inputSize");
		    	if (inputSizeArray[columnId]) {
		    		size = inputSizeArray[columnId];    
		    	} else throw "Err1";
		    } catch(err) {
		    	if (err == "Err1")
		    		alert("Column with id " + columnId + " has type as hed but no input size is defined in inputSize array.");
		    	else 
		    		alert("Column with id " + columnId + " has type as hed but no inputSize array is defined. ");
		    	return;
		    }	    
		    var maxlength = 0;
		    try {
			    var maxInputArray = eval("maxInputLength");
		    	if (maxInputArray[columnId]) {
		    		maxlength = maxInputArray[columnId];    
		    	}
		    } catch(err) {}
		    
		    if (typeof val == 'string') { // HTMLize single quotes
			    val = val.replace(/\'/g,"&#39;");
		    }
		    
		    if (maxlength == 0) {
		    	this.setCValue("<input class='inputBox' type='text' id='" + textId +  "' value='" + val + "'"+hedOnChange+" size='" + size + "'/>",val);
		    } else {
		    	this.setCValue("<input class='inputBox' type='text' id='" + textId +  "' value='" + val + "'"+hedOnChange+" size='" + (size * (fontSizeFactor / 6) )+ "' maxlength='" + maxlength + "'/>",val);
		    }
		} else {
//			if (!val || val.length == 0) {
//				this.setCValue("<label>&nbsp;</label>",val);
//			} else {
				this.setCValue("<label>" + val + "</label>",val);
//			}
		}
	 };
	 this.getValue=function(){
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			var columnId = this.grid.getColumnId(this.cell._cellIndex);
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		
		try {
			var permission;
			if (pColIndex == undefined) {
				permission = 'N';
			} else {
				permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
			}
			if (permission == 'Y') {
				return this.cell.firstChild.value.replace(/\&\#39\;/g,"'"); // get value
			} else {
				return this.cell.firstChild.innerHTML.replace(/\&\#39\;/g,"'"); // get value
			}
		}
		catch (error) {
			return "";
		}
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hed.prototype = new eXcell;    // nest all other methods from base class
var hcoroA = {};
function onselectHcoro(columnId,rowId, selectedOptVal) {
	hcoroA[""+columnId+rowId] = selectedOptVal;
	try {
		_onChangeFunctionArray[columnId](rowId,columnId);
	}catch(ex){}
}
	
function eXcell_hcoro(cell){                                    
	 if (cell){                                                     
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		var selectId="" + columnId+ this.cell.parentNode.idd;
		if (permission == 'Y') {
			hcoroA[selectId] = val;
			var selectValue = "<select class='selectBox' id='" + selectId + "' onchange=\"onselectHcoro('" +  columnId + "','" +  this.cell.parentNode.idd + "',this.options[this.selectedIndex].value);\">";	
			try {
				optArray = eval(columnId);
				if( typeof( optArray[0] ) == 'undefined' ){//&& optArray[this.cell.parentNode.idd] instanceof Array ) {
					optArray = optArray[this.cell.parentNode.idd];
				}
				for (i=0; i < optArray.length;i++) {
					var disabled = optArray[i]["disabled"]?"disabled":"";
					if (val == optArray[i]["value"])
						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "' selected='selected' "+disabled+">" + optArray[i]["text"] + "</option>";
					else
						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "' "+disabled+">" + optArray[i]["text"] + "</option>";
				}
				selectValue = selectValue + "</select>"
				this.setCValue(selectValue);
			} catch(err) {
				alert("Column with id " + columnId + " has type as hcoro but no global array named " + columnId +" is defined for the select options.");
				return;
			}
		} else {
			var optText = "";	
			try {
				optArray = eval(columnId);
				if( typeof( optArray[0] ) == 'undefined' )
					optArray = optArray[this.cell.parentNode.idd];
				for (i=0; i < optArray.length;i++) {
					if (val == optArray[i]["value"]) {
						optText =  optArray[i]["text"];
					}
				}
			} catch(err) {};
			if (optText.length == 0) {
// larry overwrite: showing id instead of nothing.				
//				this.setCValue("<label>&nbsp;</label>",optText);
				this.setCValue("<input type='hidden' id='" + selectId + "' value='" + val +"'/>"+"<label>" + val + "</label>",val);
			} else {
//				this.setCValue("<label>" + optText + "</label>",optText);
				this.setCValue("<input type='hidden' id='" + selectId + "' value='" + val +"'/>"+"<label>" + optText + "</label>",val);
			}
		}
	 };
	 this.getValue=function(){
		/*var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}*/
		var retvalue = this.cell.firstChild.value;
		//if (permission == 'Y') {
			//var selectId="" + columnId+ this.cell.parentNode.idd;
			//retvalue = this.cell.firstChild.value;
		//} else {
			//retvalue = this.cell.firstChild.value; // get value
		//}
		return !retvalue ? '' : retvalue;
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hcoro.prototype = new eXcell;    // nest all other methods from base class
var hchA={};

var _onCheckFunctionArray = new Array();

function updateHchA(chkboxId,rowId,columnId) {
	hchA[chkboxId] = !hchA[chkboxId];
	if( _onCheckFunctionArray[columnId] ) {
		if( $(chkboxId).checked && !_onCheckFunctionArray[columnId](rowId,columnId,chkboxId) ) {
			hchA[chkboxId] = !hchA[chkboxId];
			$(chkboxId).checked = false;
		}
	}
}

function eXcell_hch(cell){                                    
	 if (cell){                                                     
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		
		if (permission == 'Y') {	
		    var chkboxId="" + columnId+ this.cell.parentNode.idd;
		    hchA[chkboxId] = val;
		    var chec = "";
		    if (val == true)
		    	chec = "checked ";
	    	this.setCValue("<input type='checkbox' id='" + chkboxId +  "' value='" + val + "' onclick=\"updateHchA('" +  chkboxId + "',"+this.cell.parentNode.idd+",'"+columnId+"');\"/>",val);
		} else {
			//this.setCValue("<label>&nbsp;</label>",'');
			this.setCValue("<label></label>",'');
		}
	 };
	 this.getValue=function(){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		if (permission == 'Y') {		 
		     var chkboxId="" + columnId+ this.cell.parentNode.idd;
		     if (hchA[chkboxId]) {
		    	 return true;
		     } else {
		    	 return '';
		     }
		} else {
			return this.cell.firstChild.innerHTML; // get value
		}
	 };
	 this.setCheck=function(value){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		if (permission == 'Y') {		 
		     var chkboxId="" + columnId+ this.cell.parentNode.idd;
		     $(chkboxId).checked = value;
		     hchA[chkboxId] = value;
		}
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hch.prototype = new eXcell;    // nest all other methods from base class
var hchstatusA={};
function updateHchStatusA(chkboxId,rowId,columnId) {
	hchstatusA[chkboxId] = !hchstatusA[chkboxId];
    if( _onChangeFunctionArray[columnId] )
        _onChangeFunctionArray[columnId](rowId,columnId);
}
function eXcell_hchstatus(cell){                                    
	 if (cell){                                                     
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {		
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
	
	    var chkboxId="" + columnId+ this.cell.parentNode.idd;
	    if (val == true) {
	    	hchstatusA[chkboxId]=true;
			if (permission == 'Y') {
				this.setCValue("<input type='checkbox' id='" + chkboxId +  "' value='" + val + "' checked onclick=\"updateHchStatusA('" +  chkboxId + "',"+this.cell.parentNode.idd+",'"+columnId+"');\"/>");
			} else {
				this.setCValue("<input type='checkbox' id='" + chkboxId +  "' value='" + val + "' checked disabled />");
			}
	    } else {
	    	hchstatusA[chkboxId]=false;
	    	if (permission == 'Y') {
	    		this.setCValue("<input type='checkbox' id='" + chkboxId +  "' value='" + val + "' onclick=\"updateHchStatusA('" +  chkboxId + "',"+this.cell.parentNode.idd+",'"+columnId+"');\"/>");
			} else {
				this.setCValue("<input type='checkbox' id='" + chkboxId +  "' value='" + val + "' disabled/>");
			}	   
			} 	
	 };
	 
	 this.getValue=function(){ 
		//var columnId = this.grid.getColumnId(this.cell._cellIndex);
		//var chkboxId="" + columnId+ this.cell.parentNode.idd;
		var chkbox = this.cell.firstChild;
//		if( $( chkboxId ).tagName == 'LABEL' ) return ':' + $( chkboxId ).innerHTML;
	    //if( $( chkboxId ) != null && $( chkboxId ).disabled ) {
		if (chkbox != null) {
			if (chkbox.disabled) {
				if (chkbox.checked) {
					return 'disabled|true';
				} else {
					return 'disabled|false';
				}
	//			if (hchstatusA[chkboxId] == null ) return 'disabled';
			}
		//if ( $( chkboxId ) != null && $( chkboxId ).checked ) {
		    if (chkbox.checked) {
			    return 'true';
			} else {
				return 'false';
			}
		}
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hchstatus.prototype = new eXcell;    // nest all other methods from base class
