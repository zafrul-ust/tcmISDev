var submitcount=0;

function submitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
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
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function generateReportAudit() {

}

function showReportResult()
{
 excelfileurl = "/tcmIS/common/viewfile.jsp";

 openWinGenericViewFile(excelfileurl,"show_report_file","610","600","yes");
}

function openWinGenericViewFile(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  if (topCoor == null || topCoor.trim().length == 0)
  {
    topCoor = "200";
  }

  if (leftCoor == null || leftCoor.trim().length == 0)
  {
    leftCoor = "200";
  }

  if (scrollbars == null || scrollbars.trim().length == 0)
  {
    scrollbars = "yes";
  }

  windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top="+topCoor+",left="+leftCoor+",width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
  preview = window.open(destination, WinName,windowprops);
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}
