function validateForm() {
//var intValues = {'quantity','total','month','week','day'};
	$( 'quantity' ).value = $('quantity').value.trim();
	if( ! isPositiveInteger($( 'quantity' ).value,false) ) {
			alert( messagesData.errorInput );
			$( 'quantity' ).focus();
			return false;
	}
	$( 'total' ).value = $('total').value.trim();
	if( ! isPositiveInteger($( 'total' ).value,false) ) {
			alert( messagesData.errorInput );
			$( 'total' ).focus();
			return false;
	}
	if( $('frequency_month').checked ) {
		$('week').value = '';
		$('day').value = '';
		$( 'month' ).value = $('month').value.trim();
		if( ! isPositiveInteger($( 'month' ).value,false) ) {
			alert( messagesData.errorInput );
			$( 'month' ).focus();
			return false;
		}
	}
	else if( $('frequency_week').checked ) {
		$('month').value = '';
		$('day').value = '';
		$( 'week' ).value = $('week').value.trim();
		if( ! isPositiveInteger($( 'week' ).value,false) ) {
			alert( messagesData.errorInput );
			$( 'week' ).focus();
			return false;
		}
	}
	else {
		$('month').value = '';
		$('week').value = '';
		$( 'day' ).value = $('day').value.trim();
		if( ! isPositiveInteger($( 'day' ).value,false) ) {
			alert( messagesData.errorInput );
			$( 'day' ).focus();
			return false;
		}
	}
	if(	$( 'startingDate' ).value == '' ) {
		alert( messagesData.errorRequired.replace(/[{]0[}]/, messagesData.dateFieldName ) );
		return false;
	}
	return true;
}

function submitSearchForm()
{
 	var flag = validateForm();
  	if(flag) 
  	{
     var orderedQty =  document.getElementById("orderedQty");
     var scheduledQty = document.getElementById("total");
     if (orderedQty.value.trim() != scheduledQty.value.trim()) {
       if (confirm(messagesData.orderedqtychange)) {
          $('action').value = 'freqUpdate';
          return true;
        }
        else
        {
          return false;
        }
     }
     else
     {
      $('action').value = 'freqUpdate';
      return true;
     }
     }
     else
  	 {
    	return false;
  	 }
}

function sendData(action)
{
 if (action == 'freqUpdate')
 {     
   window.opener.parent.refreshData($('totalQty').value);
   window.close();
 }else {
	$('total').value = window.opener.parent.frames["searchFrame"].document.getElementById("orderedQty").innerHTML.trim();
	$('orderedQty').value = window.opener.parent.frames["searchFrame"].document.getElementById("orderedQty").innerHTML.trim();
 }
}

function selectRadioOption(radioOption)
{
    $(''+radioOption+'').checked = true;
}

function showCalendar(index)
{
  var cal4 = new CalendarPopup();
  cal4.setMonthNames('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
  cal4.setDayHeaders('S','M','T','W','T','F','S');
  cal4.setWeekStartDay(0);
  var now = new Date();
  var currentDate = (now.getMonth()+1)+"/"+now.getDate()+"/"+now.getFullYear();
  //disable all dates in the past including today
  cal4.addDisabledDates(null,currentDate);
  //disable all dates in the future
  for (i = 0; i < blackOutDate.length;i++) {
    cal4.addDisabledDates(blackOutDate[i]);
  }
  var inputObj = document.getElementById("startingDate");
  if (inputObj == null || inputObj.value.length == 0 ) {
    cal4.select(inputObj,'linkstartingDate','MMM dd, yyyy');
  }else {
    cal4.select(inputObj,'linkstartingDate','MMM dd, yyyy',inputObj.value);
  }
  return false;
}
