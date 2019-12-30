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
	mygrid.attachEvent("onRowSelect",selectRow);
	mygrid.attachEvent("onRightClick",selectRightclick);
}

var selectedRowId = null;

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
} 

function selectRightclick(rowId,cellInd) {
	selectRow(rowId,cellInd);
	toggleContextMenu('rightClickMenu');
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
  	var specListDisplay = "";
  	var specListString = "";
  	var specNameString = "";
  	var detailString = "";
  	var specVersionString = "";
  	var specAmendmentString = "";
  	var libraryDescString = "";
  	var libraryString = "";
  	var cocString = "";
  	var coaString = "";
  	var pipeLine = "";
  	var commaSep = "";
  	var semiSep = "";
  	var cc = 0;
//  	try {
//  		window.opener.tmpArray = new Array();
//  	}catch(ex){}
  	
  	for(var i=1;i<=mygrid.getRowsNum();i++)
	{
		var specId = cellValue(i,"specId");
		var specName = cellValue(i,"specName");
		var specVersion = cellValue(i,"specVersion");
		var specAmendment = cellValue(i,"specAmendment");
		var detail = cellValue(i,"detail");
		var specLibraryDesc = cellValue(i,"specLibraryDesc");
		var specLibrary = cellValue(i,"specLibrary");
		
		var coc = cellValue(i,"coc");
		var coa = cellValue(i,"coa");
		var cocFmt = 'N';
		var coaFmt = 'N';	
		
		if( specId == "No Specification" && cellValue(i,"ok") == "true") {
			selectedCounter++;
			specListDisplay = specId;
			specListString = specId;
			specNameString = specName;
			specVersionString = specVersion;
			specAmendmentString = specAmendment;
			detailString = detail;
			libraryDescString = specLibraryDesc;
			libraryString = specLibrary;
			break;
        } 
        else if (cellValue(i,"ok") == "true" && (coc == "true" || coa == "true")) {		
			selectedCounter++;

			if(detail.trim().length == 0)
				specListDisplay += commaSep+specId;
			else
				specListDisplay += commaSep+specId+":"+detail;

			if ( coc == "true" )
				cocFmt = 'Y';
			if ( coa == "true" )
				coaFmt = 'Y';

            if ( coc == "true" )
				specListDisplay += ":COC";
				
			if ( coa == "true" )
				specListDisplay += ":COA";
				
/*			if(detail.trim().length == 0)
				specListDisplay += commaSep+specId+", , "+cocFmt+", "+coaFmt;
			else
				specListDisplay += commaSep+specId+", "+detail+", "+cocFmt+", "+coaFmt; */
				
			specListString += pipeLine+specId;
			specNameString += pipeLine+specName;
			specVersionString += pipeLine+specVersion;
			specAmendmentString += pipeLine+specAmendment;
			detailString += pipeLine+detail;
			libraryDescString += pipeLine+specLibraryDesc;
			libraryString += pipeLine+specLibrary;
			cocString += pipeLine+cocFmt;
			coaString += pipeLine+coaFmt;
			semiSep = ";";
			commaSep = ", ";
			pipeLine = "|";
//			try {
//				window.opener.tmpArray[cc] = {specId:cellValue(i,"specId"),detail:cellValue(i,"detail"),specLibraryDesc:cellValue(i,"specLibraryDesc"),coc:cellValue(i,"coc"),coa:cellValue(i,"coa")}		
//			}catch(ex){}
			cc++;
		}
	}

	if(selectedCounter>0)
	{	
		try {
			window.opener.getList(specListDisplay, specListString, specVersionString, specAmendmentString, detailString, libraryString, cocString, coaString, libraryDescString, specNameString);
		}
		catch (ex) {}
		window.close();
	}
	else
	{
		alert(messagesData.norow);
	}
}

function addNewSpec() {
  loc = "addspecsmain.do?itemId="+$v("itemId");
  var winId= 'addNewSpec'+$v("itemId"); 
  children[children.length] = openWinGeneric(loc,winId,"700","500","yes","50","50","20","20","no");
}

function getDetail() {
  loc = "modifyspecdetail.do?uAction=search&specId="+cellValue(selectedRowId,"specId")+"&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"))+"&specAmendment="+cellValue(selectedRowId,"specAmendment")+"&specVersion="+cellValue(selectedRowId,"specVersion");
  var winId= 'modifyspecdetail'; 
  children[children.length] = openWinGeneric(loc,winId,"500","350","yes","50","50","20","20","no"); 
}

function getSpec() {
  if(cellValue(selectedRowId,"detail").length > 0)
    callToServer("newspecs.do?uAction=getSpecDetails&specId="+encodeURIComponent(cellValue(selectedRowId,"specId"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specDetail="+encodeURIComponent(cellValue(selectedRowId,"detail")));
  else
    passDefaultValues();  
}

function passDefaultValues(d) {
  if (d == null || typeof(d) == 'undefined')
  	loc = "newspecs.do?uAction=newspecs&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"));
  else
    loc = "newspecs.do?uAction=newspecs&specName="+encodeURIComponent(cellValue(selectedRowId,"specName"))+"&specLibrary="+encodeURIComponent(cellValue(selectedRowId,"specLibrary"))+"&specLibraryDesc="+encodeURIComponent(cellValue(selectedRowId,"specLibraryDesc"))+
    		"&specDetailType="+encodeURIComponent(d.specDetailType)+"&specDetailClass="+encodeURIComponent(d.specDetailClass)+
    		"&specDetailForm="+encodeURIComponent(d.specDetailForm)+"&specDetailGroup="+encodeURIComponent(d.specDetailGroup)+
    		"&specDetailGrade="+encodeURIComponent(d.specDetailGrade)+"&specDetailStyle="+encodeURIComponent(d.specDetailStyle)+
    		"&specDetailFinish="+encodeURIComponent(d.specDetailFinish)+"&specDetailSize="+encodeURIComponent(d.specDetailSize)+
    		"&specDetailColor="+encodeURIComponent(d.specDetailColor)+"&specDetailOther="+encodeURIComponent(d.specDetailOther);
  
  var winId= 'modifyspec'; 
  children[children.length] = openWinGeneric(loc,winId,"525","300","yes","50","50","20","20","no"); 
}

function addNewRow(specId, specName, detail, specDetailType, specDetailClass,
		specDetailForm, specDetailGroup, specDetailGrade, specDetailStyle, specDetailFinish,
		specDetailSize, specDetailColor, specDetailMethod, specDetailCondition,
		specDetailDash, specDetailNote, specDetailOther,
		specLibraryDesc, specLibrary, coc, coa, preCheck, specVersion, specAmendment, addDetail)
{
   $("catalogItemSpecListBean").style["display"] = "";

   if(mygrid == null) {
     initializeGrid(); 
   }  
  
   var rowid = mygrid.getRowsNum()*1+1;
    
   if(addDetail == "Y")
   		var ind = mygrid.getRowIndex(selectedRowId)*1 + 1;  
   else
   		var ind = mygrid.getRowsNum();
   		
   var thisrow = mygrid.addRow(rowid,"",ind);
 
   var cc =0; 
 
   mygrid.cells(rowid,cc++).setValue("Y");
   
   if (preCheck == "Y") {
   	 mygrid.cells(rowid,cc++).setValue(true);
   	 $("ok"+rowid).checked = true; 
	 hchstatusA["ok"+rowid] = true;
   }
   else
   	 mygrid.cells(rowid,cc++).setValue(false);

   mygrid.cells(rowid,cc++).setValue(specLibraryDesc);
   mygrid.cells(rowid,cc++).setValue(specName);
   mygrid.cells(rowid,cc++).setValue(specVersion);
   mygrid.cells(rowid,cc++).setValue(specAmendment);
   var detail = detail;
   if(!specDetailType == ""){detail = detail + "Ty "+specDetailType};
   if(!specDetailClass == ""){detail = detail + " Cl "+specDetailClass};
   if(!specDetailForm == ""){detail = detail + " Frm "+specDetailForm};
   if(!specDetailGroup == ""){detail = detail + " Grp "+specDetailGroup};
   if(!specDetailGrade == ""){detail = detail + " Gr "+specDetailGrade};
   if(!specDetailStyle == ""){detail = detail + " Sty "+specDetailStyle};
   if(!specDetailFinish == ""){detail = detail + " Fin "+specDetailFinish};
   if(!specDetailSize == ""){detail = detail + " Sz "+specDetailSize};
   if(!specDetailColor == ""){detail = detail + " Clr "+specDetailColor};
   if(!specDetailMethod == ""){detail = detail + " Md "+specDetailMethod};
   if(!specDetailCondition == ""){detail = detail + " Cd "+specDetailCondition};
   //if(!specDetailDash == ""){detail = detail + " Dash "+specDetailDash};
   if(!specDetailNote == ""){detail = detail + " Note "+specDetailNote};
   if(!specDetailOther == ""){detail = detail + " Other "+specDetailOther};
   mygrid.cells(rowid,cc++).setValue(detail);
   mygrid.cells(rowid,cc++).setValue(coc); 
   mygrid.cells(rowid,cc++).setValue(coa); 
   
   if(coc == "Y") {
	   	$("coc"+rowid).checked = true; 
		hchstatusA["coc"+rowid] = true;
   }
   if(coa == "Y") {
	   	$("coa"+rowid).checked = true; 
		hchstatusA["coa"+rowid] = true;
   }
   
   mygrid.cells(rowid,cc++).setValue($v("itemId")); 
   mygrid.cells(rowid,cc++).setValue(specLibrary);
   mygrid.cells(rowid,cc++).setValue(specId);
   
   mygrid.selectRow(mygrid.getRowIndex(rowid),null,false,false);
    
   try {
   		$("resultsPageTable").style.display="none";
   } catch(ex){}
   
/* 
   $("uAction").value  = "save"; 

   showPageWait();
   if (mygrid != null)
    	mygrid.parentFormOnSubmit();
    	
   var rowsNum = mygrid.getRowsNum();
   $("totalRows").value = rowsNum;
   
   document.genericForm.submit();
*/ 
}

function closeAllchildren() 
{ 
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != "search") {

			for(var n=0;n<children.length;n++) {
				try {
					children[n].closeAllchildren(); 
				} catch(ex){}
				children[n].close();
			}
		children = new Array();
} 
