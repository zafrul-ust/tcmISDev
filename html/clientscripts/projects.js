var submitcount=0;
var shipupdcount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function transferMultipleItems(objFrom,objTo) {
     if(objFrom.length <= 0)
          return;

     for(i=0;i<objFrom.length;i++) {
          if(objFrom.options[i].selected){
               addItem(objTo,objFrom.options[i].value,objFrom.options[i].text);
               removeItem(objFrom,i);
               i--;
          }
     }
}

function addItem(obj,value,text) {
     index = obj.length;
     obj.options[index]=new Option(text,value);
     //obj.options[index].selected = true;
}

function selectAllOptionItem(obj) {
     if(obj.length <= 0)
          return;
     var len = obj.length;
     for(i=0;i<len;i++)
     {
      obj.options[i].selected = true;
     }
}

function removeItem(obj) {
     if(obj.length <= 0)
          return;
     var index = obj.selectedIndex;
     if(index != -1)
          obj.options[index]=null;
}

function calprojectedsavings()
{
var projected_saving_year_1  =  document.getElementById("projected_saving_year_1").value;
var projected_saving_year_2  =  document.getElementById("projected_saving_year_2").value;
var projected_saving_year_3  =  document.getElementById("projected_saving_year_3").value;
var projected_saving_year_4  =  document.getElementById("projected_saving_year_4").value;
var projected_saving_year_5  =  document.getElementById("projected_saving_year_5").value;

if (projected_saving_year_1.trim().length > 0) {projected_saving_year_1 = projected_saving_year_1*1} else {projected_saving_year_1 = 0;}
if (projected_saving_year_2.trim().length > 0) {projected_saving_year_2 = projected_saving_year_2*1} else {projected_saving_year_2 = 0;}
if (projected_saving_year_3.trim().length > 0) {projected_saving_year_3 = projected_saving_year_3*1} else {projected_saving_year_3 = 0;}
if (projected_saving_year_4.trim().length > 0) {projected_saving_year_4 = projected_saving_year_4*1} else {projected_saving_year_4 = 0;}
if (projected_saving_year_5.trim().length > 0) {projected_saving_year_5 = projected_saving_year_5*1} else {projected_saving_year_5 = 0;}

var projsavingstotal  =  document.getElementById("projsavingstotal");
projsavingstotal.innerHTML = projected_saving_year_1 + projected_saving_year_2 + projected_saving_year_3 + projected_saving_year_4 + projected_saving_year_5
}

function calactualsavings()
{
var actual_saving_year_1  =  document.getElementById("actual_saving_year_1").value;
var actual_saving_year_2  =  document.getElementById("actual_saving_year_2").value;
var actual_saving_year_3  =  document.getElementById("actual_saving_year_3").value;
var actual_saving_year_4  =  document.getElementById("actual_saving_year_4").value;
var actual_saving_year_5  =  document.getElementById("actual_saving_year_5").value;

if (actual_saving_year_1.trim().length > 0) {actual_saving_year_1 = actual_saving_year_1*1} else {actual_saving_year_1 = 0;}
if (actual_saving_year_2.trim().length > 0) {actual_saving_year_2 = actual_saving_year_2*1} else {actual_saving_year_2 = 0;}
if (actual_saving_year_3.trim().length > 0) {actual_saving_year_3 = actual_saving_year_3*1} else {actual_saving_year_3 = 0;}
if (actual_saving_year_4.trim().length > 0) {actual_saving_year_4 = actual_saving_year_4*1} else {actual_saving_year_4 = 0;}
if (actual_saving_year_5.trim().length > 0) {actual_saving_year_5 = actual_saving_year_5*1} else {actual_saving_year_5 = 0;}

var totalactualsavings  =  document.getElementById("totalactualsavings");
totalactualsavings.innerHTML = actual_saving_year_1 + actual_saving_year_2 + actual_saving_year_3 + actual_saving_year_4 + actual_saving_year_5
}

function getpersonnelID()
{
        loc = "/tcmIS/dana/searchpersonnel?Action=findlike";
        openWinGeneric(loc,"search_personnel","400","250","yes");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=no,status=no,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function search(entered)
{
    window.document.projectmanager.Action.value = "Search";

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function newtask(entered)
{
    window.document.projectmanager.Action.value = "newtask";

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function entertask(entered)
{
    window.document.projectmanager.Action.value = "entertask";
	 selectAllOptionItem(document.getElementById("keywords"));

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function updatetask(entered)
{
    window.document.projectmanager.Action.value = "updatetask";
	 selectAllOptionItem(document.getElementById("keywords"));

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function printpdf(entered)
{
    window.document.projectmanager.Action.value = "printpdf";

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function backsearch(entered)
{
    window.document.projectmanager.Action.value = "BackSearch";

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function doPrintprojectlist(entered)
{
    loc = "/tcmIS/dana/projects?Action=prinprojectlist";
    openWinGeneric(loc,"showprintasklist","600","500","yes")
}

function printtask(entered)
{
    var printaskid  =  document.getElementById("printaskid");
	 loc = "/tcmIS/dana/projects?Action=printtask";
	 openWinGeneric(loc,"showEmailnotes","600","500","yes")
}

function createxlsrpt(entered)
{
    var printaskid  =  document.getElementById("printaskid");
         loc = "/tcmIS/dana/projects?Action=createxlsrpt";
         openWinGeneric(loc,"showcreatexlsrpt","600","500","yes")
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function cancel()
{
    window.close();
}