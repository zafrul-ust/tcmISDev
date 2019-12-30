var resizeGridWithWindow = true;
var children = new Array(); 
windowCloseOnEsc = true;
addNewRowOK = true;

var noSpecifictionChecked = false;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("catalogItemSpecListBean").style["display"] = "";
		document.getElementById("updateResultLink").style["display"] = "";
		initializeGrid();  
	}  
	else
	{
		document.getElementById("catalogItemSpecListBean").style["display"] = "none";   
	}
	
	if(typeof(mygrid ) == 'undefined' ) {
     	initializeGrid(); 
    }  
    
    try {  
		if($v("specListConcat").length > 0) {  
		    var specListConcat = $v("specListConcat");
		    var specListArray = specListConcat.split("|");
		    
		    var specNameConcat = $v("specNameConcat");
		    var specNameArray = specNameConcat.split("|");
		    
		    var specVersionConcat = $v("specVersionConcat");
		    var specVersionArray = specVersionConcat.split("|");
		    
		    var specAmendmentConcat = $v("specAmendmentConcat");
		    var specAmendmentArray = specAmendmentConcat.split("|");
		    
		    var specDetailConcat = $v("specDetailConcat");
		    var specDetailArray = specDetailConcat.split("|");
		    
		    var specLibraryDescConcat = $v("specLibraryDescConcat");
		    var specLibraryDescArray = specLibraryDescConcat.split("|");
		    
		    var specLibraryConcat = $v("specLibraryConcat");
		    var specLibraryArray = specLibraryConcat.split("|");
		    
		    var specCocConcat = $v("specCocConcat");
		    var specCocArray = specCocConcat.split("|");
		    
		    var specCoaConcat = $v("specCoaConcat");
		    var specCoaArray = specCoaConcat.split("|");
//alert("specCocConcat:"+specCocConcat+"      specCoaConcat:"+specCoaConcat);		
		    for(var i=0;i<specListArray.length;i++)
			{
				var exist = "N";
				for(var j=1;j<=mygrid.getRowsNum();j++)
				{
					if(specListArray[i] == 'No Specification' && cellValue(j,"specId") == specListArray[i]) {
						$("ok"+j).checked = true;
						updateHchStatusA("ok"+j);
						callEnableDisable(j);
						exist = "Y";
						break;
					}  
					
					if ((cellValue(j,"specId") == specListArray[i] && cellValue(j,"detail") == specDetailArray[i])) // && cellValue(j,"specLibrary") == specLibraryArray[i]))
					{
						mygrid.cells(j,mygrid.getColIndexById("ok")).setValue(true);
						$("ok"+j).checked = true; 
				   		hchstatusA["ok"+j] = true;
				   			
						if(specCocArray[i] == "Y") {
							mygrid.cells(j,mygrid.getColIndexById("coc")).setValue(true);
			   				$("coc"+j).checked = true; 
				   			hchstatusA["coc"+j] = true;
			   			}
			   			
			   			if(specCoaArray[i] == "Y") {
			   				mygrid.cells(j,mygrid.getColIndexById("coa")).setValue(true);
			   				$("coa"+j).checked = true; 
				   			hchstatusA["coa"+j] = true;
			   			}
						exist = "Y";
						break;
					}
				} 
				if (exist == "N")
				{
					addNewRow(specListArray[i], specNameArray[i], specDetailArray[i], "", "", "", "", "", "", "", "", "", "", "", "", "", "", specLibraryDescArray[i], specLibraryArray[i], specCocArray[i], specCoaArray[i], "Y", specVersionArray[i], specAmendmentArray[i]);
				}  
			}   
		}
    }catch(ex){}
	
	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();
// alert("noSpecifictionChecked"+noSpecifictionChecked);	
	if(noSpecifictionChecked) {
		for(var k=1;k<=mygrid.getRowsNum();k++)
			callEnableDisable(k);
	}
	
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";
}

function initializeGrid(){
	mygrid = new dhtmlXGridObject('catalogItemSpecListBean');

	initGridWithConfig(mygrid,config,-1,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		mygrid.parse(jsonMainData,"json");
	}
}


function callEnableDisable(rowId) {//alert(rowId);alert(cellValue(rowId,"specName")+"   "+cellValue(rowId,"ok"));
	if(cellValue(rowId,"specId") == 'No Specification' && cellValue(rowId,"ok") == "true") {
		$('addSpecSpan').style.display="none";
		for(var i=1;i<=mygrid.getRowsNum();i++)
		{
			if(rowId != i) {
				$("ok"+i).checked = false;
				$("ok"+i).disabled = true;
				updateHchStatusA("ok"+i);
			} 
			$("coc"+i).checked = false;
			$("coa"+i).checked = false;
			$("coc"+i).disabled = true;
			$("coa"+i).disabled = true;
			updateHchStatusA("coc"+i);
			updateHchStatusA("coa"+i);
		} 
	} else if(cellValue(rowId,"specId") == 'No Specification' && cellValue(rowId,"ok") == "false") {
		$('addSpecSpan').style.display="";
		for(var j=1;j<=mygrid.getRowsNum();j++)
		{
			if(rowId != j) {
				$("ok"+j).disabled = false;
				updateHchStatusA("ok"+j);
			}
			$("coc"+j).disabled = false;
			$("coa"+j).disabled = false;
			updateHchStatusA("coc"+j);
			updateHchStatusA("coa"+j);
		} 	
	
	}
}


function getSpecList() {
  	var selectedCounter = 0;
  	
  	for(var i=1;i<=mygrid.getRowsNum();i++)
	{
		var specId = cellValue(i,"specId");
		var coc = cellValue(i,"currentCocRequirement");
		var coa = cellValue(i,"currentCoaRequirement");
		if( specId == "No Specification" && cellValue(i,"ok") == "true")
			selectedCounter++;
        else if (cellValue(i,"ok") == "true" && (coc == "true" || coa == "true")) 
			selectedCounter++;
	}

	if(selectedCounter>0)
	{	
		try {
			
			showPleaseWait(); // Show "please wait" while updating
			
			$('uAction').value = 'selectSpecs';

			if (mygrid != null) {
				// This function prepares the grid dat for submitting top the server
				mygrid.parentFormOnSubmit();
			}

			document.genericForm.submit(); // Submit the form
		}
		catch (ex) {}
	}
	else
	{
		alert(messagesData.norow);
	}
}