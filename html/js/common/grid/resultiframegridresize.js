var showErrorMessage = false;
var showUpdateLinks = false;

function setResultFrameSize() {
	parent.submitCount = 0;
	parent.resizeFramecount = 1;

	try {
		parent.resizeGridWithWindow = resizeGridWithWindow;
	}
	catch (exError1) {
		// create resizeGridWithWindow global variable if it doesn't exist.
		// I want aall programmers to be aware of what (resizeGridWithWindow) this does.
		// window["resizeGridWithWindow"] = true;
		// parent.resizeGridWithWindow = resizeGridWithWindow;
		alert("resizeGridWithWindow variable not defined.");
	}

	try {
		parent.document.getElementById("transitPage").style["display"] = "none";
		parent.showsearchresults = true;
		var resultGridDiv = parent.document.getElementById("resultGridDiv");
		resultGridDiv.style["display"] = "";

		totalLines = document.getElementById("totalLines").value;
		if (totalLines == 0) /* Dont show any links if no data found */
		{
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
		}
		else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		}

		setTimeout("parent.resizeFrame()", 15);
		setTimeout('parent.resetResizeFramecount()', 10);
	}
	catch (exError) {
	}

	if (showErrorMessage) {
		setTimeout('showErrorMessages()', 50); /* Showing error messages if any */
	}
}

function standardResultOnLoad() {
	setResultFrameSize();
	try { // don't show error please...
		if (!showUpdateLinks) /* Dont show any update links if the user does not have permissions */
		{
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	}
	catch (ex) {
	}
	try { // don't show error please...
		displaySearchDuration();
	}
	catch (ex) {
	}
}

// Larry Note: will be overwritten if it also exist in result js files, so its safe.
function showErrorMessages() {
	parent.showErrorMessages();
}

/* This is to re-size column widths based on window size. */
function reSizeGridCoLWidths() {
	if (haasGrid != null) {
		reSizeCoLumnWidths(haasGrid);
	}
}

/*
 * This is called from the main page to set the correct sizes.
 */
function setGridSize() {
	if (haasGrid != null)
		haasGrid.setSizes();
}

/*
 * This is called from the main page to set the grid height.
 */
function setGridHeight(resultFrameHeight) {
	try {
		if (haasGrid != null) {
			var griDiv = document.getElementById(haasGrid.entBox.id);
			var newHeight = resultFrameHeight - 3 + "px";
			if (griDiv.style.height != newHeight) {
				griDiv.style.height = newHeight;
			}
		}
	}
	catch (ex) {
		// alert("THis means the grid was not initialized");
	}
}

/* This is called to set the grid width. */
function setGridWidth() {
	try {
		setWindowSizes();
		// if (_isIE) {
		// myWidth = myWidth - 5;
		// }
		// else {
		// myWidth = myWidth - 5;
		// }
		var griDiv = document.getElementById(haasGrid.entBox.id);
		var newWidth = myWidth + "px";
		if (griDiv.style.width != newWidth) {
			griDiv.style.width = newWidth;
		}
		lastWindowWidth = myWidth;
	}
	catch (ex) {
		// alert("THis means the grid was not initialized");
	}
}
// cab be used to initialized grid for result frame onload.
function resultOnLoadWithGrid(gridConfig) {
	try {
		if (!showUpdateLinks) {/* Dont show any update links if the user does not have permissions */
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch (ex) {}
	
	try {
		totalLines = document.getElementById("totalLines").value;
		if (totalLines == 0) {/* hide result div if there's no data */
			document.getElementById(gridConfig.divName).style["display"] = "none";
		} else {
			document.getElementById(gridConfig.divName).style["display"] = "";
		}
	} catch (ex) {}

	initGridWithGlobal(gridConfig);

	/* This dislpays our standard footer message */
	displayGridSearchDuration();

	/* It is important to call this after all the divs have been turned on or off. */
	setResultFrameSize();

}
