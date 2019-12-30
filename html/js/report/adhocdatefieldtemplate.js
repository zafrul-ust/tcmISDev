function validateDate() {
	var numberOfDays = $v("numOfDays");
	//var dayOfYear = $v("selDayOfYear");
	
	if(document.forms[0]["numberOfDays"].checked && numberOfDays != '' && (!isFloat(numberOfDays,false) || numberOfDays < 1 || numberOfDays > 366 )) {
		alert(messagesListDateTemplate.pleaseentervalidnumofdays);
		return false;
	}
    /*
    if(document.forms[0]["dayOfYear"].checked && dayOfYear != '' && (!isFloat(dayOfYear,false) || dayOfYear < 1 || dayOfYear > 366 )) {
		alert(messagesListDateTemplate.pleaseentervaliddayofyear);
		return false;
	}
	*/
	return true;
}

function handleClick(radioName) {	
	if(document.forms[0]["specificDates"].checked) {
		document.getElementById("beginDateJsp").disabled=false;
		document.getElementById("endDateJsp").disabled=false;	
		document.getElementById("numOfDays").disabled=true;
		document.getElementById("selDayOfWeek" ).disabled=true;
		document.getElementById("selDayOfMonth" ).disabled=true;
		//document.getElementById("selDayOfYear" ).disabled=true;
		//document.getElementById("dayOfYearJsp").disabled=true;
	} else if (document.forms[0]["numberOfDays"].checked ){
		document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;	
		document.getElementById("numOfDays").disabled=false;
		document.getElementById("selDayOfWeek" ).disabled=true;
		document.getElementById("selDayOfMonth" ).disabled=true;
		//document.getElementById("selDayOfYear" ).disabled=true;
		//document.getElementById("dayOfYearJsp").disabled=true;
	} else if(document.forms[0]["dayOfWeek"].checked) {
		document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;	
		document.getElementById("numOfDays").disabled=true;
		document.getElementById("selDayOfWeek" ).disabled=false;
		document.getElementById("selDayOfMonth" ).disabled=true;
		//document.getElementById("selDayOfYear" ).disabled=true;
		//document.getElementById("dayOfYearJsp").disabled=true;
	} else if(document.forms[0]["dayOfMonth"].checked) {
		document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;	
		document.getElementById("numOfDays").disabled=true;
		document.getElementById("selDayOfWeek" ).disabled=true;
		document.getElementById("selDayOfMonth" ).disabled=false;
		//document.getElementById("selDayOfYear" ).disabled=true;
		//document.getElementById("dayOfYearJsp").disabled=true;
	}
    /*
    else if(document.forms[0]["dayOfYear"].checked) {
		document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;	
		document.getElementById("numOfDays").disabled=true;
		document.getElementById("selDayOfWeek" ).disabled=true;
		document.getElementById("selDayOfMonth" ).disabled=true;
		document.getElementById("selDayOfYear" ).disabled=false;
		document.getElementById("dayOfYearJsp").disabled=false;
	} */
}

function validateDatePopulated() {
	if(document.forms[0]["specificDates"].checked) {		
		if ($v("beginDateJsp").length == 0) {
			alert(messagesListDateTemplate.begindaterequired);
      		return false;
	    }
	    if ($v("endDateJsp").length == 0) {
	    	alert(messagesListDateTemplate.enddaterequired);
	    	return false;
	    }
	} else if (document.forms[0]["numberOfDays"].checked ){		
		if ($v("numOfDays").length == 0) {
			alert(messagesListDateTemplate.numberofdaysrequired);
			return false;
	    }
	} else if(document.forms[0]["dayOfWeek"].checked) {			
		if ($v("selDayOfWeek").length == 0) {
			alert(messagesListDateTemplate.dayofweekrequired);
			return false;
	    }
	} else if(document.forms[0]["dayOfMonth"].checked) {		
		if ($v("selDayOfMonth").length == 0) {
			alert(messagesListDateTemplate.dayofmonthrequired);
			return false;
	    }
	}
    /*
    else if(document.forms[0]["dayOfYear"].checked) {
		if ($v("selDayOfYear").length == 0) {
			alert(messagesListDateTemplate.dayofyearrequired);
			return false;
	    }		
	} */
	return true;
}