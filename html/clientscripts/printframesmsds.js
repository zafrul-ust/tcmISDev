var da = (document.all) ? 1 : 0;
var pr = (window.print) ? 1 : 0;
var mac = (navigator.userAgent.indexOf("Mac") != -1);
var winact="msds"
var clientname =""
var specrevdate = ""
var specid = "";

img1on=new Image();
img1on.src="/images/buttons/comp/2comp.gif";
img1off=new Image();
img1off.src="/images/buttons/comp/1comp.gif";

img2on=new Image();
img2on.src="/images/buttons/prop/2prop.gif";
img2off=new Image();
img2off.src="/images/buttons/prop/1prop.gif";

img3on=new Image();
img3on.src="/images/buttons/lists/2lists.gif";
img3off=new Image();
img3off.src="/images/buttons/lists/1lists.gif";

img5on=new Image();
img5on.src="/images/buttons/2msds.gif";
img5off=new Image();
img5off.src="/images/buttons/1msds.gif";

imghelpon=new Image();
imghelpon.src="/images/help_down.gif";
imghelpoff=new Image();
imghelpoff.src="/images/help_up.gif";

isIE = false;
if (navigator.mimeTypes && navigator.mimeTypes.length)
{

}
else
{
 isIE = true;
}

function insertprintLink(url)
{

if (isIE)
{
	var target1 = document.getElementById("printlinkie");
	target1.style.display = "none";

	document.write("<BR><INPUT TYPE=\"BUTTON\" ID=\"printLink\" ONCLICK=\"return printPage(parent.data, this)\" value=\"Print MSDS\">\n");
}
else
{
	document.write("<BR><A HREF=\"#\" onclick=\"javascript:window.open('"+printiconurl+"')\" STYLE=\"color:#0000ff;cursor:hand;\"><U>Open MSDS</U></A>\n");
}

}

function printPage(frame, arg) {
  if (frame == window) {
    printThis();
  } else {
    link = arg; // a global variable
    printFrame(frame);
  }
  return false;
}

function printThis() {
  if (pr) { // NS4, IE5
    window.print();
  } else if (da && !mac) { // IE4 (Windows)
    vbPrintPage();
  } else { // other browsers
    alert("Sorry, your browser doesn't support this feature.");
  }
}

function printFrame(frame) {
  if (pr && da) { // IE5
    frame.focus();
    window.print();
    link.focus();
  } else if (pr) { // NS4
    frame.print();
  } else if (da && !mac) { // IE4 (Windows)
    frame.focus();
    setTimeout("vbPrintPage(); link.focus();", 100);
  } else { // other browsers
    alert("Sorry, your browser doesn't support this feature.");
  }
}

if (da && !pr && !mac) with (document) {
  writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
  writeln('<' + 'SCRIPT LANGUAGE="VBScript">');
  writeln('Sub window_onunload');
  writeln('  On Error Resume Next');
  writeln('  Set WB = nothing');
  writeln('End Sub');
  writeln('Sub vbPrintPage');
  writeln('  OLECMDID_PRINT = 6');
  writeln('  OLECMDEXECOPT_DONTPROMPTUSER = 2');
  writeln('  OLECMDEXECOPT_PROMPTUSER = 1');
  writeln('  On Error Resume Next');
  writeln('  WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER');
  writeln('End Sub');
  writeln('<' + '/SCRIPT>');
}



function plugdetect( plugName )
{
browserNN= ( navigator.appName == "Netscape" );
browserIE= ( navigator.appName == "Microsoft Internet Explorer" );
if ( browserNN )
{
  if ( navigator.plugins[plugName] )
  {
    return true;
  }
  else
  {
    return false;
  }
}
else if ( browserIE )
{
  return true;
}
}
function detect()
{
if ( plugdetect( "Adobe Acrobat" ) == true )
{
  return "Y";
}
else
{
  return "N";
}
}

function DropDownMenuMSDS( entered )
{
   with( entered )
   {
   ref=options[selectedIndex].value;
   splitcharacter=ref.lastIndexOf( "*" );
if ( splitcharacter != -1 )
{
  loc=ref.substring( 0,splitcharacter );
  target=ref.substring( splitcharacter + 1,1000 ).toLowerCase();
}
else
{
  loc=ref;
  target="_self";
}

loc=loc + "&plug=" + detect();
loc=loc + "&act=" + winact;
if ( target == "false" )
{
  return;
}
if ( target == "notav" )
{
  alert( "This Material Safety Data Sheet is not available. \nPlease limit your selection to those marked with a *" );
  return;
}
if ( target == "_self" )
{
  document.location=loc;
}
else
{
  if ( target == "_top" )
  {
    top.location=loc;
  }
  else
  {
    if ( target == "_blank" )
    {
      window.open( loc );
    }
    else
    {
      if ( target == "_parent" )
      {
        parent.location=loc;
      }
      else
      {
        parent.frames.location=loc;
      }

      parent.frames.title.location="/tcmIS/"+clientname.toLowerCase()+"/ViewMsds?SESSION=0" + loc.substring( 58,1000 );
      //parent.frames.title.location="/tcmIS/servlet/radian.web.servlets."+clientname.toLowerCase()+"."+clientname+"msdsSideView?SESSION=0" + loc.substring( 58,1000 );
    }
  }
}
}
}
   function DropDownMenuSpec( entered )
   {
   winact="spec";
   with( entered )
   {
   ref=options[selectedIndex].value;
   splitcharacter=ref.lastIndexOf( "*" );
if ( splitcharacter != -1 )
{
  loc=ref.substring( 0,splitcharacter );
  target=ref.substring( splitcharacter + 1,1000 ).toLowerCase();
}
else
{
  loc=ref;
  target="_self";
}

loc=loc + "&plug=" + detect();
if ( target == "false" )
{
  return;
}
if ( target == "notav" )
{
  alert( "This Material Safety Data Sheet is not available. \nPlease limit your selection to those marked with a *" );
  return;
}
if ( target == "_self" )
{
  document.location=loc;
}
else
{
  if ( target == "_top" )
  {
    top.location=loc;
  }
  else
  {
    if ( target == "_blank" )
    {
      window.open( loc );
    }
    else
    {
      if ( target == "_parent" )
      {
        parent.location=loc;
      }
      else
      {
        parent.frames.location=loc;
      }

      parent.frames.title.location="/tcmIS/"+clientname.toLowerCase()+"/ViewMsds?SESSION=0" + loc.substring( 58,1000 );
      //parent.frames.title.location="/tcmIS/servlet/radian.web.servlets."+clientname.toLowerCase()+"."+clientname+"msdsSideView?SESSION=0" + loc.substring( 58,1000 );
    }
  }
}
}
}

function openwin( str )
{
   MSDS_Help=window.open( str,"MSDS_Help",
                          "toolbar=no,location=no,directories=no,status=yes" +
                          ",menubar=no,scrollbars=yes,resizable=yes," +
                          "top=100,left=2,width=800,height=600" );
}

function openwin2( str )
{
MSDS_Problem=window.open( str,"MSDS_Problem",
                             "toolbar=no,location=no,directories=no,status=no" +
                             ",menubar=no,scrollbars=no,resizable=no," +
                             ",width=200,height=150" );
}

function openwin23( str )
{
   parent.frames.data.location=str;
}

function doTitle( imgName )
{

if ( winact == "spec" )
{
  parent.frames.title.location="/tcmIS/"+clientname.toLowerCase()+"/ViewMsds?SESSION=0&date="+specrevdate+"&id="+specid;
  //parent.frames.title.location="/tcmIS/servlet/radian.web.servlets."+clientname.toLowerCase()+"."+clientname+"msdsSideView?SESSION=0&date="+specrevdate+"&id="+specid;
}
if ( imgName == "img1" )
{
  imgOff( 'img2'); imgOff( 'img5'); imgOff( 'img3');
  document.MSDSForm.composition.disabled=false;
  document.MSDSForm.lists.disabled=true;
  document.MSDSForm.msdsactive.disabled=true;
  document.MSDSForm.properties.disabled=true;
  winact="comp";
}
if ( imgName == "img2" )
{
  document.MSDSForm.properties.disabled=false;
  document.MSDSForm.composition.disabled=true;
  document.MSDSForm.lists.disabled=true;
  document.MSDSForm.msdsactive.disabled=true;
  imgOff( 'img1'); imgOff( 'img5'); imgOff( 'img3');
  winact="prop";
}
if ( imgName == "img5" )
{
  document.MSDSForm.msdsactive.disabled=false;
  document.MSDSForm.properties.disabled=true;
  document.MSDSForm.composition.disabled=true;
  document.MSDSForm.lists.disabled=true;
  imgOff( 'img2'); imgOff( 'img1'); imgOff( 'img3');
  winact="msds";
}
if ( imgName == "img3" )
{
  document.MSDSForm.msdsactive.disabled=true;
  document.MSDSForm.properties.disabled=true;
  document.MSDSForm.composition.disabled=true;
  document.MSDSForm.lists.disabled=false;
  imgOff( 'img2'); imgOff( 'img1'); imgOff( 'img5');
  winact="lists";
}
imgOn( imgName );

}

function imgOn( imgName )
{
if ( document.images )
{
  document[imgName].src=eval( imgName + "on.src" );
}
}

function imgOff( imgName )
{
if ( document.images )
{
  document[imgName].src=eval( imgName + "off.src" );
}
}