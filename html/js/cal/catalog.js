var selectedCommentRow=null;

function catchcommentevent(rowid)
{
    var selectedRow = document.getElementById("commentrowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 if (selectedCommentRow != null && rowid != selectedCommentRow)
	 {
		var previouslySelectedRow = document.getElementById("commentrowId"+selectedCommentRow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedCommentRow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
	 }
	 selectedCommentRow =rowid;
}

function addCommentsLine()
{
        // getting reference to my Line Table
        mytable = document.getElementById("line_table");

        var allTRs = mytable.getElementsByTagName("tr");
        var str = "# of table Rows: " + allTRs.length + "\n";
        var rownum = (allTRs.length)*1;
        //alert(str);

        var Color ="white";
        if (rownum%2==0)
        {
            Color ="blue";
        }
        else
        {
            Color ="white";
        }

		  // get the reference for the body
		  //var mybody=document.getElementsByTagName("body").item(0);

		  // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");
            mycurrent_row.id = "commentrowId"+rownum;
            mycurrent_row.className = Color;
				mycurrent_row.attachEvent("onmouseup", function () {catchcommentevent(''+rownum+'');});

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "comment"+rownum;
                mycurrent_cell.setAttribute("width",'25%');
                mycurrent_cell.innerHTML = " ";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "comment"+rownum;
                mycurrent_cell.setAttribute("width",'2%');
                mycurrent_cell.innerHTML = "<FONT SIZE=\"4\"><A HREF=\"#\" onClick=\"editcommentevent('"+rownum+"');return false;\" NAME=\"anchor"+rownum+"\" ID=\"anchor"+rownum+"\" style=\"text-decoration:none\">&diams;</A></FONT>";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "enteredBy"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                //mycurrent_cell.setAttribute("align",'left');
					 mycurrent_cell.innerHTML = ""+ document.getElementById("loginname").value+"";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "date"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                mycurrent_cell.innerHTML = "Today";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "delete"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                var invisibleElements ="<INPUT TYPE=\"checkbox\" NAME=\"deleteCheckBox\" ID=\"deleteCheckBox"+rownum+"\" value=\"new\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"colorClass"+rownum+"\" VALUE=\""+Color+"\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"comments\" ID=\"comments"+rownum+"\" value=\"\" >";
	             invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"updateStatus\"  ID=\"updateStatus"+rownum+"\" VALUE=\"Insert\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"commentId\"  VALUE=\"New\">";

					 mycurrent_cell.innerHTML = invisibleElements;

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

				 // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);
		  //mybody.appendChild(mytable);
}

function editcommentevent(rowid)
{
	var editcommentspopup = new PopupWindow("editcommentsdiv");
	editcommentspopup.setSize(400,430);
	editcommentspopup.autoHide();
   editcommentspopup.offsetX = -400;
	editcommentspopup.offsetY = 25;
	var commentElements ="<B>Edit Comments:</B><BR><FORM><TEXTAREA name=\"txtcomments\" rows=\"7\" cols=\"100\">"+document.getElementById("comments"+rowid+"").value+"</TEXTAREA>";
	commentElements +="<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\">";
	commentElements +="<TR VALIGN=\"MIDDLE\">";
	commentElements +="<TD CLASS=\"announce\" HEIGHT=\"35\" WIDTH=\"10%\" ALIGN=\"LEFT\">";
	commentElements +="<input type=\"button\" name=\"buttonTransfer\" value=\"Ok\" onclick=\"sendComments("+rowid+")\" onmouseover=\"className='SUBMITHOVER'\" onmouseout=\"className='SUBMIT'\" class=\"SUBMIT\">";
	commentElements +="</TD></TR></TABLE></FORM>";
	editcommentspopup.populate(commentElements);
   editcommentspopup.showPopup("anchor"+rowid+"");

}

function sendComments(rowid)
{
var comment = document.getElementById("comment"+rowid+"");
comment.innerHTML = document.getElementById("txtcomments").value;

var comments = document.getElementById("comments"+rowid+"");
comments.value = document.getElementById("txtcomments").value;

var updateStatus = document.getElementById("updateStatus"+rowid+"");
updateStatus.value = "changed";

try
{
  var  editcommentsdiv = document.getElementById("editcommentsdiv");
  editcommentsdiv.style.visibility = "hidden";
}
catch (ex)
{
}
}

function checkall(checkboxname)
{
var checkall = document.getElementById("chkAll");
var totallines = document.getElementById("totallines").value;
totallines = totallines*1;

var result ;
var valueq;
if (checkall.checked)
{
  result = true;
  //valueq = "yes";
}
else
{
  result = false;
  //valueq = "no";
}

 for ( var p = 0 ; p < totallines ; p ++)
 {
	try
	{
	var linecheck = document.getElementById(""+checkboxname+""+(p*1)+"");
	linecheck.checked =result;
	//linecheck.value = valueq;
	}
	catch (ex1)
	{

	}
 }
}

function doprintpopup(printurl)
{
 openWinGeneric(printurl,"show_print_file","800","500","yes");
}
