var SortBy=" ";
var submitcount=0;
var companynames=new Array( "ALL","REST" );
var altName=new Array();
altName["ALL"]=new Array( "Material","Part Number","Trade Name","Manufacturer","Catalog" );
altName["REST"]=new Array( "Material","Part Number","Trade Name","Manufacturer","Facility" );
var OptValue=new Array();
OptValue["All"]=new Array( "MATERIAL_ID","FAC_ITEM_ID","TRADE_NAME","MFG_DESC","CATALOG_ID" );
OptValue["REST"]=new Array( "MATERIAL_ID","FAC_ITEM_ID","TRADE_NAME","MFG_DESC","FACILITY_ID" );

function reshow( object )
{
   artist=object.options[object.selectedIndex].text;
	if ( artist == "All Catalogs" )
	{
	  var indexofxompany=0;
	}
	else
	{
	  var indexofxompany=1;
	}
	var testbin;
   eval( testbin="window.document.form1.sortby" );
   var cur=null;
   eval( cur= ( eval( testbin.toString() ) ).selectedIndex );
	for ( var i=document.form1.sortby.length; i > 0; i-- )
	{
	  document.form1.sortby.options[i]=null;
	}
	showlinks( indexofxompany );
   window.document.form1.sortby.selectedIndex=cur;
}

function showlinks( selectedIndex )
{
   var companytoshow=companynames[selectedIndex];
   var nickNameValue=new Array();
   var OptionValue=new Array();
   nickNameValue=altName[companytoshow];
   OptionValue=OptValue[companytoshow];

	for ( var i=0; i < nickNameValue.length; i++ )
	{
	  opt( i,nickNameValue[i] )

	}
}

function opt( href,text )
{
    var optionName=new Option( text,href,false,false );
    document.form1.sortby.options[href]=optionName;
}

function RefreshSortBy( entered )
{
   with( entered )
   {
   SortBy=document.form1.sortby.options[selectedIndex].VALUE;
   }
}

function goBack()
{
	if ( submitcount == 0 )
	{
	  submitcount++;
	  window.document.backForm.Second.value="Yes";
	  document.backForm.submit();
	}
}

function goNext()
{
	if ( submitcount == 0 )
	{
	  submitcount++;
	  window.document.nextForm.Second.value="Yes";
	  document.nextForm.submit();
	}
}

function resetApplicationTimer()
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    //alert("Here MSDS Reset tImer");
    }
}