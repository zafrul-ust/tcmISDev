var currentRow;
var months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

function submitUpdate(closeAfter) {
	var valid = validateSearchForm();
	if (valid) {
		if (closeAfter) {
			document.getElementById('uAction').value="submitClose";
		}
		else {
			document.getElementById('uAction').value="submit";
		}
	   	showTransitWin();
	   	if(haasGrid) {
	    	haasGrid.parentFormOnSubmit(); //prepare grid for data sending
	    }
		document.genericForm.submit();
	}
	return valid;
}

function validateSearchForm() {
	var valid = true;
	var numRows = haasGrid.getRowsNum();
	for (var rowId = 1; rowId <= numRows; rowId++) {
		var currentEnd = cellValue(rowId,"endDate");
		var originalEnd = cellValue(rowId,"originalEndDate");
		
		if (originalEnd != null && originalEnd.length > 0 &&
				currentEnd.length == 0) {
			alert(messagesData.requirestartenddates);
			valid = false;
			break;
		}
	}
	return valid;
}

function submitCancel() {
	window.close();
}

function initGrid(gridConfig)
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		initGridWithGlobal(gridConfig);
	}
	else
	{
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}

	$("mainUpdateLinks").style.display = "";
}

function rowSelect(rowId,cellIdx) {
	currentRow = rowId;
	var columnId = beanGrid.getColumnId(cellIdx);
	if (columnId == "endDate") {
		var today = new Date();
		var startDate = cellValue(rowId,"hiddenStartDateSort");
		var endDate = cellValue(rowId,"hiddenOriginalEndDateSort");
	
		var d = new Date();
		if (startDate != null && startDate != "") {
			if (startDate < today.getTime()) {
				startDate = today.getTime();
			}
			d.setTime(startDate);
			d.setDate(d.getDate()-1);
			$("blockBefore_"+columnId).value = padNumber(d.getDate(),2)+"-"+months[d.getMonth()]+"-"+d.getFullYear();
		}
		if (endDate != null && endDate != "") {
			d.setTime(endDate);
			$("blockAfterExclude_"+columnId).value = padNumber(d.getDate(),2)+"-"+months[d.getMonth()]+"-"+d.getFullYear();
		}
		
		return getCalendar(document.getElementById(columnId+rowId),
				document.getElementById('blockBefore_'+columnId),
				document.getElementById('blockAfter_'+columnId),
				document.getElementById('blockBeforeExclude_'+columnId),
				document.getElementById('blockAfterExclude_'+columnId),
			document.getElementById('inDefinite_'+columnId).value);
	}
}

function showCalendar() {
	alert("Showing calendar");
}

function finishRenderingRow(rowId) {
	if ($("startDate"+rowId)) {
		$("startDate"+rowId).readOnly = true;
	}
	
	if ($("endDate"+rowId)) {
		$("endDate"+rowId).readOnly = true;
	}
}

function padNumber(number, padding) {
	var exp = Math.pow(10,padding);
	if (number > exp) {
		return number;
	}
	else {
		number = number + exp;
		return number.toString().substring(1);
	}
}

function arrayIndexOf(array, obj) {
	for (var i = 0; i < array.length; i++) {
		if (array[i] == obj)
			return i;
	}
	return -1;
}

function changeDateSort() {
	var startDateText = cellValue(currentRow,"startDate").split("-");
	var endDateText = cellValue(currentRow,"endDate").split("-");
	
	var startDate = new Date();
	startDate.setYear(startDateText[2]);
	try {
		startDate.setMonth(arrayIndexOf(months,startDateText[1]),startDateText[0]);
	}catch(e){}
	
	var endDate = new Date();
	endDate.setYear(endDateText[2]);
	try {
		endDate.setMonth(arrayIndexOf(months,endDateText[1]),endDateText[0]);
	}catch(e){}
	
	cell(currentRow,"hiddenStartDateSort").cell.innerHTML = startDate.getTime();
	cell(currentRow,"hiddenEndDateSort").cell.innerHTML = endDate.getTime();
}

var dhxWins = null;
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin()
{
	var waitingMsg = messagesData.pleasewait;
	$("transitLabel").innerHTML = waitingMsg;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";
	
	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}