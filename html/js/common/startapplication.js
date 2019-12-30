function openWinGeneric(destination, WinName, WinWidth, WinHeight, Resizable) {
	var windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=no,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight + ",resizable=" + Resizable;
	return window.open(destination, WinName, windowprops);
}

function startTcmisApplication(companyName) {
	/*
	 * Need to remove space and other special characters in company name to
	 * avoid javascript error
	 */
	companyName = companyName.replace(/ /g, "");
	companyName = companyName.replace(/&/gi, "");
	companyName = companyName.replace(/#/gi, "");
	companyName = companyName.replace(/\./gi, "");
	companyName = companyName.replace(/-/gi, "");
	var newWindowName = '_' + companyName + 'TcmisApplication';
	var newWindow = openWinGeneric('/tcmIS/common/loadingpleasewait.jsp', newWindowName, '1024', '720', 'yes');
	document.genericForm.target = newWindowName;
	setTimeout("document.genericForm.submit();", 50);
	newWindow.focus();
}
