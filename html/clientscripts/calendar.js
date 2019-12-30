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
var calendarStartMonday = false;
var disabledDatesExpression = "";
var disabledWeekDays = new Object();
var allowIndefinite  = null;

function getCalendar(formField)
{
    var cal_width = 210;
    var cal_height = 255;

    // IE needs less space to make this thing
    if ((document.all) && (navigator.userAgent.indexOf("Konqueror") == -1)) {
        cal_width = 210;
    }

    calendarTarget = formField;
    calendarWindow = window.open('/calender/calendar.html', 'dateSelectorPopup','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=0,dependent=no,width='+cal_width+',height='+cal_height);

    return false;
}

function killCalendar()
{
    if (calendarWindow && !calendarWindow.closed) {
        calendarWindow.close();
    }
}