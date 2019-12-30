function search( name,entered )
{
   mainFormvalue1=eval( "document.MaterialLike.Action" );
   mainFormvalue1.value=name.toString();
   alert( window.document.MaterialLike.Action.value );
   return true;
}

function Createnew( name,entered )
{
   window.document.MaterialLike.Action.value="New";
   return true;
}

function actionValue( name,entered )
{
   var finalMsgt; finalMsgt="Please enter valid values for: \n\n"; var result;
   var allClear=0; curval=window.document.MaterialLike.material_id.value;
   if ( curval.length < 1 )
   {
    finalMsgt=finalMsgt + " Material Id " + "\n";
    allClear=1;
   }
  curval2=window.document.MaterialLike.material_desc.value;
  if ( curval2.length < 1 )
  {
  finalMsgt=finalMsgt + " Material Desc " + "\n";
  allClear=1;
  }
  curval3=window.document.MaterialLike.tradename.value;
  if ( curval3.length < 1 )
  {
  finalMsgt=finalMsgt + " Trade Name " + "\n";
  allClear=1;
  }
  curval4=window.document.MaterialLike.MfgID.value;
  if ( curval4.length < 1 )
  {
  finalMsgt=finalMsgt + " Manufacturer Id " + "\n";
  allClear=1;
  }

if ( allClear < 1 )
{
  result=true;
}
else
{
  alert( finalMsgt );
  result=false;
}
return result;
}

function cancel()
{
   window.close();
}
function addmaterialID( matidvalue,matdesc,tradename,landdot,airdot,waterdot )
{
   document.MaterialLike.material.value=matidvalue;
   document.MaterialLike.mat_description.value=matdesc;
   document.MaterialLike.tradename.value=tradename;
   /*document.MaterialLike.landdot.value=landdot;
   document.MaterialLike.airdot.value=airdot;
   document.MaterialLike.waterdot.value=waterdot;*/
}

function openWinGeneric( destination,WinName,WinWidth,WinHeight,Resizable )
{
   windowprops="toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight + ",resizable=" + Resizable;
   preview=window.open( destination,WinName,windowprops );
}

function ShowMSDS( MaterialID )
{
  var loc;
  company=window.document.MaterialLike.Company.value;

  if (company == "SEAGATE")
  {
      loc = "/tcmIS/seagate/ViewMsds?cl=Seagate&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "RAYTHEON")
  {
      loc = "/tcmIS/ray/ViewMsds?cl=Ray&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "SWA")
  {
      loc = "/tcmIS/swa/ViewMsds?cl=SWA&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "DRS")
  {
      loc = "/tcmIS/drs/ViewMsds?cl=DRS&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "LOCKHEED")
  {
      loc = "/tcmIS/lmco/ViewMsds?cl=Lockheed&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "BAE")
  {
      loc = "/tcmIS/bae/ViewMsds?cl=BAE&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "UTC")
  {
      loc = "/tcmIS/utc/ViewMsds?cl=UTC&showspec=N&act=msds&id="
      loc = loc + MaterialID;
  }
  else if (company == "SD")
  {
     loc = "/tcmIS/sd/ViewMsds?cl=SD&showspec=N&act=msds&id="
     loc = loc + MaterialID;
  }
  else
  {
	 loc = "/tcmIS/ray/ViewMsds?cl=Ray&showspec=N&act=msds&id="
	 loc = loc + MaterialID;
  }

openWinGeneric( loc,"CATMSDS","800","650","yes" );
}