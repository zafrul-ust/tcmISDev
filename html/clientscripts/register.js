var varjavawsInstalled = 0;
ns4 = (document.layers)? true:false;
ie4 = (document.all)? true:false;
ns6 = (!(ie4) && document.getElementById)? true : false;

var debugwin;

function dump(obj) {
	if( !debugwin ) {
		debugwin = window.open("","debugwin","width=200,height=150,scrollbars,resizable,left=0,top=0,screenX=0,screenY=0");
	}
	if( typeof(obj) == "string" || typeof(obj) == "number" ) {
		debugwin.document.write("<pre>"+obj+"</pre>");
	} else {
		debugwin.document.write("<pre>Properties for Object:\n");
		for( prop in obj ) {
			debugwin.document.write("\t"+prop+" = "+obj[prop]+"\n");
		}
		debugwin.document.write("</pre>");
	}
}

function debugClear() {
	debugwin.document.close();
	debugwin.document.write("");
	debugwin.document.close();
}

function debugClose() {
	debugwin.close();
}

function setStatus(text) {
	window.status = text;
}

function logout()
{
    window.document.LoginForm.logoutAction.value = "logoutclicked";
    return true;
}

javawsInstalled = 1;
isIE = "false";
if (navigator.mimeTypes && navigator.mimeTypes.length) {
   x = navigator.mimeTypes['application/x-java-jnlp-file'];
   if (x) javawsInstalled = 1;
 } else {
   isIE = "true";
}

function opennewwin()
{
MSDS_Help=window.open( "/images/newrelease/index.html","new_release",
              "toolbar=yes,location=no,directories=no,status=yes" +
              ",menubar=no,scrollbars=yes,resizable=yes," +
              "width=750,height=520" );
}

function insertLink( name, clientname )
{
testurl="/download/installtcmis.exe";
offlninstl="/download/offline/installtcmis14.exe";
imagerl="/images/downloadtcm.gif";
imager2="/images/tcminstallsave.gif";
imager3="/images/installlogo.gif";
imager4="/images/installstep1.gif";
imager5="/images/installstep2.gif";
imager6="/images/installstep3.gif";
style="color:#0000ff";
if ( name == "test" )
{
document.write( "<FONT FACE=Arial SIZE=4><B><CENTER>Please Install Java Web Start Plugin Before You Login<BR>&nbsp;&nbsp;&nbsp;&nbsp;- use the following procedure<BR><BR></CENTER></B></FONT>" );
}
else
{
document.write( "<FONT FACE=Arial SIZE=5><B><CENTER>Java Web Start Plugin not installed - use the following procedure<BR><BR></CENTER></B></FONT>" );
}

if (clientname == "DOE" || clientname == "CAL" || name == "tcmisfromweb.jnlp")
{
document.write( "<FONT FACE=Arial SIZE=2>1.  Download the <I>tcm</I>IS installer ..<A href=" + offlninstl + "><IMG src=" + imagerl + " BORDER=1></A>" );
}
else
{
document.write( "<FONT FACE=Arial SIZE=2>1.  Download the <I>tcm</I>IS installer ..<A href=" + testurl + "><IMG src=" + imagerl + " BORDER=1></A>" );
}

document.write( "<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;Save file to your Desktop..." );
document.write( "<BR><BR><CENTER><IMG src=" + imager2 + "  WIDTH=427 BORDER=1></CENTER><BR><BR>" );
document.write( "2.  Go to your desktop and double click on the <I>tcm</I>IS installation icon... <IMG src=" + imager3 +
       " BORDER=1 WIDTH=55 HEIGHT=55>" );
document.write( "<BR><BR>3.  Click the button which says Step 1." );
document.write( "<BR><BR><CENTER><IMG src=" + imager4 + "  WIDTH=427 BORDER=1></CENTER><BR><BR>" );
document.write( "&nbsp;&nbsp;&nbsp;&nbsp;Accept Sun's license and accept the default answers during installation..." );
document.write( "<BR><BR>4.  Click the button which says Step 2." );
document.write( "<BR><BR><CENTER><IMG src=" + imager5 + "  WIDTH=427 BORDER=1></CENTER><BR><BR>" );
document.write( "&nbsp;&nbsp;&nbsp;&nbsp;Accept Sun's license and accept the default answers during installation...<BR>Depending on your system you might be asked to restart your computer." );
document.write( "<BR><BR>5.  This completes your installation." );
document.write( "<BR><BR><CENTER><IMG src=" + imager6 + "  WIDTH=427 BORDER=1></CENTER><BR><BR>" );
document.write( "&nbsp;&nbsp;&nbsp;&nbsp;Click Exit." );
document.write( "<BR><BR>6. Click <A href=" + testurl3 + " STYLE=" + style + ">here</A> to start <I>tcm</I>IS.</FONT><BR><BR>" );
}

function insert14Link( name, clientname )
{
testurl="/download/installtcmis14.exe";
offlninstl="/download/offline/installtcmis14.exe";
imagerl="/images/downloadtcm.gif";
imager2="/images/downjre14.jpg";
imager3="/images/tcm14installsave.gif";
imager4="/images/installstep1.gif";
imager5="/images/installstep2.gif";
imager6="/images/installstep3.gif";
style="color:#0000ff";
if ( name == "test" )
{
document.write( "<FONT FACE=Arial SIZE=4><B><CENTER>Please Install Java Web Start Plugin Before You Login<BR>&nbsp;&nbsp;&nbsp;&nbsp;- use the following procedure<BR><BR></CENTER></B></FONT>" );
}
else
{
document.write( "<FONT FACE=Arial SIZE=5><B><CENTER>Java Web Start Plugin not installed - use the following procedure<BR><BR></CENTER></B></FONT>" );
}

if (clientname == "CAL" || "DOE")
{
document.write( "<FONT FACE=Arial SIZE=2>1.  Download the <I>tcm</I>IS installer ..<A href=" + offlninstl + "><IMG src=" + imagerl + " BORDER=1></A>" );
}
else
{
document.write( "<FONT FACE=Arial SIZE=2>1.  Download the <I>tcm</I>IS installer ..<A href=" + testurl + "><IMG src=" + imagerl + " BORDER=1></A>" );
}

document.write( "<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;Save file to your Desktop..." );
document.write( "<BR><BR><CENTER><IMG src=" + imager2 + " BORDER=1 WIDTH=550 HEIGHT=350></CENTER><BR><BR>" );
document.write( "2.  Go to your desktop and double click on the <I>tcm</I>IS installation icon... <IMG src=" + imager3 + " BORDER=1>" );
document.write( "<BR><BR>3.  Accept Sun's license and accept the default answers during installation..." );
document.write( "<BR><BR>4.  This completes your installation. Restart your computer and login again." );
}

function launchLink( versioncount, cltname )
{
     imager5="/images/tcmisstarting.gif";
     Scriptclose="JavaScript:parent.close();"
     style="color:#0000ff";
     if ( javawsInstalled )
     {
     document.write( "<BR><BR><CENTER><IMG SRC=" + imager5 + ">" );
     document.write( "<BR><BR><BR><BR><BR><BR>" );
     }
     else
     {
       if (versioncount*1 > 0)
       {
       insert14Link( 'Start tcmIS', cltname );
       }
       else
       {
       insertLink( 'Start tcmIS', cltname );
       }
     }
}

function launchLink2( versioncount, cltname )
{
     imagerlogo="/images/tcmintro.gif";
     Scriptclose="JavaScript:parent.close();"
     style="color:#0000ff";
     if ( javawsInstalled )
     {
     document.write( "<BR><BR><CENTER><IMG SRC=" + imagerlogo + ">" );
     document.write( "<BR><BR><BR><BR><BR><BR>" );
     }
     else
     {
       if (versioncount*1 > 0)
       {
       insert14Link( 'test' , cltname );
       }
       else
       {
       insertLink( 'test', cltname );
       }
     }
}

function getWebStart()
{
   if ( javawsInstalled )
   {
   window.document.LoginForm.WebStart.value="Yes";
   return true;
   }
   else
   {
   window.document.LoginForm.WebStart.value="Notre";
   return true;
   }
}

function getWebStartlogin()
{
     if ( javawsInstalled )
     {
     window.document.LoginingForm.WebStart.value="Yes";
     return true;
     }
     else
     {
     window.document.LoginingForm.WebStart.value="Notre";

     return false;
     }
}