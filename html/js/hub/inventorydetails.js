var beangrid;

var resizeGridWithWindow = true;

var windowCloseOnEsc = true;

function resultOnLoad()
{
/*try{
	
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
  
 }
 }
 catch(ex)
 {}*/

 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("logisticsViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("logisticsViewBean").style["display"] = "none";   
 }

 displayNoSearchSecDuration();  
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}



function initializeGrid(){
	 beangrid = new dhtmlXGridObject('logisticsViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	}