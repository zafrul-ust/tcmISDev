// Util.js
//
// supporting JS variables and functions for Ionic Enterprise Geo Beans Demo


// ******************
// expand(section_id)
// ******************
function expand(section)
{
  if (document.getElementById) 
  { // NS5 - IE5
    current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
    document.getElementById(section).style.display = current;
  }
  else if (document.all) 
  { //IE4
    current = (document.all[section].style.display == 'block') ? 'none' : 'block';
    document.all[section].style.display = current;
  }
}


// **************************************************************************
// validDate(date)
// 
// verifies a date of the form 6/3/04 to 03/06/2004, or 6-3-04 to 03-06-2004
// **************************************************************************
function validDate(dateVal)
{
  if (dateVal=="") return false;

  var spos = dateVal.indexOf("/");
  var dpos = dateVal.indexOf("-");

  if (spos < 0 && dpos < 0) return false;

  var dateArray = new Array();

  if (spos > 0) {
     dateArray = dateVal.split("/");
  } else {
     dateArray = dateVal.split("-");
  }

  try {
     return isDate(myParseInt(dateArray[1]), myParseInt(dateArray[0]), myParseInt(dateArray[2]));
  } catch (e) {
     return false;
  }
}

function myParseInt(source) {
  var x = source;
  // strip leading spaces and zeros (0)
  while (x.substring(0,1) == ' ' ||
         x.substring(0,1) == '0' ) {
      x = x.substring(1);
  }

  return parseInt(x);
}

function y2k(number) { 
    return (number < 1000) ? number + 2000 : number; 
}

function isDate (day,month,year) {
    var today = new Date();
    year = ((!year) ? y2k(today.getYear()) : y2k(year));
    month = ((!month) ? today.getMonth() : month-1);
    if (!day) return false

    var test = new Date(year,month,day);

    var sampleMonth = test.getMonth();
    var sampleYear = test.getYear();
    var sampleDay = test.geDay();

    if ( (y2k(test.getYear()) == year) &&
         (month == test.getMonth()) &&
         (day == test.getDate()) )
        return true;
    else
        return false
}
