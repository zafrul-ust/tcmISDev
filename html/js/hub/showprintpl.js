windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();
var beangrid;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("shipmentBean").style["display"] = "";
		
		initializeGrid();  
	}  
	else
	{
		document.getElementById("shipmentBean").style["display"] = "none";   
	}
	
		/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();
		/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	
	document.getElementById("mainUpdateLinks").style["display"] = "";
		
    try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('shipmentBean');

	initGridWithConfig(beangrid,config,-1,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}
	
}

function countChecked() {
	var totallines = $("totalLines").value;
	var totalChecked = 0;
	for ( var p = 1; p <= totallines; p++ ) {
		if (haasGrid.haasRowIsRendered(p)) {
			var checkbox = document.getElementById("selected" + p);
			if (checkbox.checked == true) {
				totalChecked++;
			}
		}
	}
	return totalChecked;
}



function checkAll(checkboxname)
{
  var checkall = $("chkAllSelected");
  var rowsNum = haasGrid.getRowsNum();

  rowsNum = rowsNum*1;

  renderAllRows()
  
  if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
  }
  else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
  }
  return true;  
}

function consolidate() {
	   var checked = countChecked();
	   var totallines = $("totalLines").value;
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
		   var tmp = "";
 			for ( var p = 1; p <= totallines; p++ ) {
 					var checkbox = document.getElementById("selected" + p);
        			if (checkbox.checked == true) {
         				var shipmentId = cellValue(p,'shipmentId');
					if(tmp == "") {
						tmp = "?shipmentIds=" + "" + shipmentId;
					}
					else {
          					tmp = tmp + "," + shipmentId;
					}
				}
 			}
  			
 			var loc = "/tcmIS/hub/printpackinglist.do" + tmp;
 			openWinGeneric(loc,"printpackinglist","800","500","yes","80","80")
		}
    
}

function consolidateBol() {
   	var totallines = $("totalLines").value;
   		var checked = countChecked();
		if (checked==0) {
			alert(messagesData.pleasemakeselection);
		}
   		else {
      			var tmp = "";
      			for ( var p = 1; p <= totallines; p++ ) {
      					var checkbox = document.getElementById("selected" + p);
	         			if (checkbox.checked == true) {
	         				var shipmentId = cellValue(p,'shipmentId');
						if(tmp == "") {
							tmp = "?shipmentIds=" + "" + shipmentId;
						}
						else {
	           					tmp = tmp + "," + shipmentId;
						}
					}
      				
			}
     			var loc = "/tcmIS/hub/printconsolidatedbol.do" + tmp;
     			openWinGeneric(loc,"printconsolidatebol","800","500","yes","80","80")
   		}
	
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

function cancel()
{
    window.close();
}




