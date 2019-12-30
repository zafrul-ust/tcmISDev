var calendarWindow = null;
var calendarColors = new Array();
calendarColors['bgColor'] = '#FFFFFF';
calendarColors['borderColor'] = '#333366';
calendarColors['headerBgColor'] = '#8b8b8b';
calendarColors['headerColor'] = '#FFFFFF';
calendarColors['dateBgColor'] = '#f8f8f8';
calendarColors['dateColor'] = '#000000';
calendarColors['dateHoverBgColor'] = '#dddddd';
calendarColors['dateHoverColor'] = '#8493A8';
//var calendarFormat = 'y-d-m';

// make external vars so other window can get it.
// SM...S
var disabledWeekDays = new Array( false,false,false,false,false,false,false,false);

function typeOf(value) {
    var s = typeof value;
    if (s === 'object') {
        if (value) {
            if (value instanceof Array) {
                s = 'Array';
            }
        } else {
            s = 'null';
        }
    }
    return s;
}

var disabledDatesExpression = "";
var allowIndefinite  = null;
// getCalendar( dataField, // this data field's value will be changed after user selected a date, i.e. pass getElementById(...)
//				block_before_include, // block the dates before and including the input date i.e. passing 01/15/2007 will not allow them to select dates 01/15/2007,01/14/2007, .....
//				block_after_include,  // block the dates after and including the input date i.e. passing 01/15/2007 will not allow them to select dates 01/15/2007,01/16/2007, .....
//				block_before_exclude, // block the dates before and excluding the input date i.e. passing 01/15/2007 will not allow them to select dates 01/14/2007,01/13/2007, .....
//				block_after_exclude,  // block the dates after and excluding the input date i.e. passing 01/15/2007 will not allow them to select dates 01/16/2007,01/17/2007, .....
//				allowIndefinite)      // pass 'y' or 'Y' to allow 'Indefinite' as a valid input. i.e. The indefinite button will show up.
// The default behavior is not blocking any dates and not show the indefinite button.
// if a parameter is not used, pass null, or not not pass it if its after the last needed parameter.
// e.g. getCalendar( getElementById('changeThisDate') ,'01/15/2007') will block the dates of '01/15/2007','01/14/2007'.... and not anything else
//                                 and the indefinit button will not show up.
//      while getCalendar( getElementById('changeThisDate') ,null,null,null,null,'y') will not block any dates and the indefinite button will show.
// define __block_dates array to block out dates. in 'yyyymmdd' format.
//   e.g. define
//   var __block_dates = new Array(
// '20090616',
// '20090617'
// );
// will block June 16, 17 or year 2009
// define __block_weekdays array to block out weekdays, 0 means sunday, 1 means monday....6 means saturday.
// e.g.block sunday and  monday.
// var __block_weekdays = new Array(
// 0,
// 1
// );
   
if (typeof(calendarIndefinite) == 'undefined') {
    var calendarIndefinite  = 'Indefinite';
	var calendarStartMonday = false;
	var calendarFormat = 'm/d/y';
}

var block_after_exclude;
var block_after;
var block_before_exclude;
var block_before;

function getCalendar()
{

	if( typeof(__block_weekdays) != 'undefined' && typeOf(__block_weekdays) == 'Array' ) {
		for(ii=0;ii<__block_weekdays.length;ii++) {
			if( __block_weekdays[ii] ) 
				disabledWeekDays[ __block_weekdays[ii] ] = true;
			else
				disabledWeekDays[ 0 ] = disabledWeekDays[ 7 ] = true;
		}
	}

block_after_exclude = null;
block_after = null;
block_before_exclude = null;
block_before = null;

var dataFieldName = null;
if (arguments.length > 0 ) 
	dataFieldName = arguments[0];
/*Setting the current value in a data field to blank string when if has Indefinite in it.
* This is to avoid a error in calendar.html.*/  
//if (dataFieldName.value == calendarIndefinite)
//  dataFieldName.value = "";

if (arguments.length > 1 ) {
	block_before = arguments[1];
	if( typeof(block_before) != 'string' ) {
	if ( block_before != null && block_before.value != "" )
		 block_before = block_before.value;
	else block_before = null;
	}
}

if (arguments.length > 2 ) {
	block_after = arguments[2];
	if( typeof(block_after) != 'string' ) {
	if ( block_after != null && block_after.value != "" && block_after.value != calendarIndefinite )
		 block_after = block_after.value;
	else block_after = null;
	}
}

if (arguments.length > 3 ) {
	block_before_exclude = arguments[3];
	if( typeof(block_before_exclude) != 'string' ) {
	if ( block_before_exclude != null && block_before_exclude.value != "" )
		 block_before_exclude = block_before_exclude.value;
	else block_before_exclude = null;
	}
}

if (arguments.length > 4 ) {
	block_after_exclude = arguments[4];
	if( typeof(block_after_exclude) != 'string' ) {
	if ( block_after_exclude != null && block_after_exclude.value != ""  && block_after_exclude.value != calendarIndefinite )
		 block_after_exclude = block_after_exclude.value;
	else block_after_exclude = null;
	}
}

var indef = null;
allowIndefinite = null;
if (arguments.length > 5 ) {
	indef = arguments[5];
	if ( indef != null && ( indef == "y" || indef == "Y" ) )
		allowIndefinite = "Y";
}

    var cal_width = 210;
    var cal_height = 255;

    // IE needs less space to make this thing
    if ((document.all) && (navigator.userAgent.indexOf("Konqueror") == -1)) {
        cal_width = 210;
    }

	//var formField = document.getElementById("dataFieldName");
   calendarTarget = dataFieldName;
   calendarTargetDisplay = document.getElementById(dataFieldName.id+"Display");

   try
   {
	var calendarWindow;
	calendarWindow = new PopupWindow();
//	calendarWindow = new CalendarPopup();
//  initializing external available vars. 
	disabledDatesExpression = "";
//	disabledWeekDays = new Object();

	calendarWindow.setSize(210,250);
	calendarWindow.setUrl("/calender/calendar.html");
   	calendarWindow.offsetX = -1;
	calendarWindow.offsetY = 25;
	calendarWindow.autoHide();
//  add disabled dates here
//	calendarWindow.addDisabledDates(block_after,block_before);		
//	calendarWindow.addDisabledDatesExclude(block_after_exclude,block_before_exclude);		
	
	if( calendarTargetDisplay != null ) 
			calendarWindow.showPopup(calendarTargetDisplay.id+"");
	else if( document.getElementById("link"+dataFieldName.id ) != null ) 
		   	calendarWindow.showPopup("link"+dataFieldName.id+"");
	else 
			calendarWindow.showPopup(""+dataFieldName.id+"");

   }
   catch (ex)
   {
   }

 /*calendarTarget = formField;
 calendarWindow = window.open('/calender/calendar.html', 'dateSelectorPopup','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=0,dependent=no,width='+cal_width+',height='+cal_height);*/

   return false;
}


function killCalendar()
{
    if (calendarWindow && !calendarWindow.closed) {
        calendarWindow.close();
    }
}