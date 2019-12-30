package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.BarCodeHelpObj;
import radian.web.barcode.ACJBarCodeGenerator;
import radian.web.barcode.BarCodePage;
import radian.web.barcode.ZPLBarCodeGenerator;
import radian.web.servlets.internal.InternalServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 03-31-03 - Can print seagate labels from the transactions page
 * 06-11-03 Showing the padded 8 digit receipt Id on the receipt labels
 * 01-15-03 Showing Quality Control acceptance stamp and updating the pickable field when the label is printed.Changed the wording from Acceptance Stamp to Accepted By
 * 01-21-04 Fixing Seagate Label print error
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 01-30-04 Fixed the Javascript error on this page
 * 06-05-04 - Moving to use ZPL to print labels
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 07-30-04 Changing the templates to be based on inventory group instead of hub
 * 08-11-04 Allowing editing the 6th and 7th partnumber
 * 09-13-04 Printing Main Receipt Label and the kit labels consecutively
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels. Also giving the option to pick a prnter if there are multiple printers at a hub
 */


public class reprintLabelswnoInventory
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private PrintWriter out = null;
	private static org.apache.log4j.Logger barclog = org.apache.log4j.Logger.getLogger(reprintLabelswnoInventory.class);
	private boolean intcmIsApplication = false;
	private static Pattern barCode2dReplace = Pattern.compile("\\||,",Pattern.MULTILINE);
	public reprintLabelswnoInventory(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}
	public void doResult(HttpServletRequest request, HttpServletResponse response,HttpSession session)
	throws ServletException,  IOException
	{
		out=response.getWriter();
		response.setContentType( "text/html" );
		//StringBuffer Msgn=new StringBuffer();
		String personnelId= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
			intcmIsApplication = true;
		}

		String User_Action=null;
		User_Action=request.getParameter( "UserAction" );
		if ( User_Action == null )
			User_Action="New";

		String PaperSize=request.getParameter( "paperSize" );
		if ( PaperSize == null )
			PaperSize="31";

		String receiptId=null;
		receiptId=request.getParameter( "receiptId" );
		if ( receiptId == null )
			receiptId="";

		String HubNoforlabel=request.getParameter( "HubNoforlabel" );
		if ( HubNoforlabel == null )
			HubNoforlabel="";
		HubNoforlabel.trim();

		String qty_picked=request.getParameter( "qty_picked" );
		if ( qty_picked == null )
			qty_picked="";

		boolean noeditvalues= false;
		if ( "2106".equalsIgnoreCase( HubNoforlabel ) )
		{
			noeditvalues = true;
		}

		String printKitLabels = request.getParameter("printKitLabels");
		if (printKitLabels == null)
			printKitLabels = "";

		String printerPath=request.getParameter( "printerPath" );
		if ( printerPath == null )
			printerPath="";

		String printerRes=request.getParameter( "printerRes" );
		if ( printerRes == null )
			printerRes="";

		String total = BothHelpObjs.makeBlankFromNull(request.getParameter("Total"));

		if ( User_Action.equalsIgnoreCase( "generatelabels" )  || User_Action.equalsIgnoreCase("pickedPrinterForLabels") )
		{
			Vector finaldata=new Vector( 2 );
			Vector synch_data=new Vector( 2 );
			Hashtable finalcompdata =new Hashtable( 2 );
			Vector dumpcompdata = new Vector();
			finalcompdata = (Hashtable)session.getAttribute("PRINTCOMPDATA");
			ACJBarCodeGenerator acobj=new ACJBarCodeGenerator();
			Hashtable finalLabelcompdata = new Hashtable();
			Random rand=new Random();
			int tmpReq=rand.nextInt();
			Integer tmpReqNum=new Integer( tmpReq );

			if ( !noeditvalues )
			{
				finaldata= ( Vector ) session.getAttribute( "PRINTBARCODEDATA" );
				finalLabelcompdata = finalcompdata;
			}

			if ( !User_Action.equalsIgnoreCase( "pickedPrinterForLabels" ) )
			{
				if ( !noeditvalues )
				{
					synch_data=SynchprintData( request,finaldata );
					session.setAttribute("PRINTBARCODEDATA",synch_data);
					finaldata = synch_data;
					finalLabelcompdata = copyFreeTextData(total,finalcompdata,synch_data);

                /*TODO Store the data that will be printed on the label in the following tables.
                     Container_label
                     container_label_cat_part_no
                     container_label_component

                     For each receipt that is being printed (where label_qty > 0) in finaldata
                     1. Get a new container_label_id. query: select CONTAINER_LABEL_ID_SEQ.NEXTVAL from dual
                     2. Insert into  Container_label (= container_label_master_view) get the data from  finaldata
                     3. insert into container_label_cat_part_no  ( = container_label_detail_view)  for each not null value of DETAIL_9, DETAIL_10, DETAIL_11, DETAIL_12,DETAIL_13, DETAIL_17,DETAIL_18
                        For  DETAIL_9 also insert values for the columns SPEC_ID, LABEL_SPEC, KIT_MSDS_NUMBER, MIN_RT_OUT_TIME, MIN_STORAGE_TEMP_DISPLAY, TIME_TEMP_SENSITIVE, CAT_PART_MIN_RT_OUT_TIME, SPEC_DISPLAY, CUSTOMER_PART_REVISION
				     4. If DETAIL_16 is = 'N' (managekitsassinlge = 'N'. insert into container_label_component ( = container_label_component_view)  values from finalcompdata
                */
				String[] receiptIds = receiptId.split(",");
				for(int m = 0;m < receiptIds.length; m++)
				{
					DBResultSet dbrs = null;
					ResultSet rs = null;
					String containerLabelId = "";
						try
						{
							
							dbrs=db.doQuery("select CONTAINER_LABEL_ID_SEQ.NEXTVAL from dual");
							rs=dbrs.getResultSet();
	
							while ( rs.next() )
							{
								containerLabelId=rs.getString( "NEXTVAL" ) == null ? "" : rs.getString( "NEXTVAL" );
							}
						}
						catch ( Exception e )
						{
							e.printStackTrace();
						}
						
						StringBuilder insertContainerLabel = new StringBuilder("insert into container_label (CONTAINER_LABEL_ID, RECEIPT_ID, EXPIRE_DATE, MFG_LOT, STORAGE_TEMP, RECERT_NUMBER, DATE_OF_RECEIPT, DATE_OF_MANUFACTURE, QUALITY_TRACKING_NUMBER, LABEL_COLOR, SPEC_DETAIL, SPEC_LIST, LAST_ID_PRINTED, SUPPLIER_CAGE_CODE, QUANTITY_PRINTED,PRINT_USER,CERTIFIED_BY,CERTIFICATION_DATE) values(");
						Hashtable containerLabelDataHashes = (Hashtable)finaldata.firstElement();
						Hashtable containerLabelData = (Hashtable)containerLabelDataHashes.get("DATA");
						String quantityFromHash = (String)containerLabelDataHashes.get("QUANTITY_RECEIVED");
						insertContainerLabel.append(containerLabelId + ",");
						insertContainerLabel.append(receiptIds[m] + ",");
						String expireDate = (StringHandler.isBlankString((String)containerLabelData.get("EXPIRE_DATE"))?"":containerLabelData.get("EXPIRE_DATE").toString());
						insertContainerLabel.append((expireDate.equalsIgnoreCase("INDEFINITE")?"to_date('01/01/3000','/mm/dd/yyyy')":"'"+expireDate+"'") + ",");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("MFG_LOT"))?"":containerLabelData.get("MFG_LOT")) + "',");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("DETAIL_8"))?"":containerLabelData.get("DETAIL_8")) + "',");
						insertContainerLabel.append((StringHandler.isBlankString((String)containerLabelData.get("DETAIL_5"))?"null":"'" + containerLabelData.get("DETAIL_5") + "'") + ",");
						insertContainerLabel.append((StringHandler.isBlankString((String)containerLabelData.get("DETAIL_4"))?"null":"to_date('"+containerLabelData.get("DETAIL_4")+"','/mm/dd/yyyy')") + ",");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("DATE_OF_MANUFACTURE"))?"":containerLabelData.get("DATE_OF_MANUFACTURE")) + "',");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("QUALITY_TRACKING_NUMBER"))?"":containerLabelData.get("QUALITY_TRACKING_NUMBER")) + "',");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("LABEL_COLOR"))?"":containerLabelData.get("LABEL_COLOR")) + "',");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("SPEC_DETAIL"))?"":containerLabelData.get("SPEC_DETAIL")) + "',");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("SPEC_LIST"))?"":containerLabelData.get("SPEC_LIST")) + "',");
						insertContainerLabel.append((StringHandler.isBlankString((String)containerLabelData.get("LAST_ID_PRINTED"))?"null":containerLabelData.get("LAST_ID_PRINTED")) + ",");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("SUPPLIER_CAGE_CODE"))?"":containerLabelData.get("SUPPLIER_CAGE_CODE")) + "',");
						insertContainerLabel.append((StringHandler.isBlankString(quantityFromHash)?"null":quantityFromHash) + ",");
						insertContainerLabel.append(personnelId + ",");
						insertContainerLabel.append((StringHandler.isBlankString((String)containerLabelData.get("CERTIFIED_BY"))?"null":containerLabelData.get("CERTIFIED_BY")) + ",");
						insertContainerLabel.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("CERTIFICATION_DATE"))?"":containerLabelData.get("CERTIFICATION_DATE")) + "')");
					
						try
						{
							db.doUpdate(insertContainerLabel.toString());
						}
						catch ( Exception ex )
						{
							ex.printStackTrace();
						}
				
						for(int n = 9; n < 19;n++)
						{
							if(n == 15)
								continue;
							String currentDetail = (String)containerLabelData.get("DETAIL_" + Integer.toString(n));
							if(!StringHandler.isBlankString(currentDetail))
							{
								String detailQuery = null;
								if(n == 9)
								{
									StringBuilder containerLabelCatpartNo = new StringBuilder("insert into container_label_cat_part_no (CAT_PART_NO,CONTAINER_LABEL_ID,SPEC_ID, LABEL_SPEC, KIT_MSDS_NUMBER, MIN_RT_OUT_TIME, MIN_STORAGE_TEMP_DISPLAY, TIME_TEMP_SENSITIVE, CAT_PART_MIN_RT_OUT_TIME, SPEC_DISPLAY, CUSTOMER_PART_REVISION) values (");
									containerLabelCatpartNo.append("'" + currentDetail + "',");
									containerLabelCatpartNo.append(containerLabelId + ",");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("SPEC_ID"))?"":containerLabelData.get("SPEC_ID")) + "',");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("LABEL_SPEC"))?"":containerLabelData.get("LABEL_SPEC")) + "',");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("KIT_MSDS_NUMBER"))?"":containerLabelData.get("KIT_MSDS_NUMBER")) + "',");
									containerLabelCatpartNo.append((StringHandler.isBlankString((String)containerLabelData.get("MIN_RT_OUT_TIME"))?"null":containerLabelData.get("MIN_RT_OUT_TIME")) + ",");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("MIN_STORAGE_TEMP_DISPLAY"))?"":containerLabelData.get("MIN_STORAGE_TEMP_DISPLAY")) + "',");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("TIME_TEMP_SENSITIVE"))?"":containerLabelData.get("TIME_TEMP_SENSITIVE")) + "',");
									containerLabelCatpartNo.append((StringHandler.isBlankString((String)containerLabelData.get("CAT_PART_MIN_RT_OUT_TIME"))?"null":containerLabelData.get("CAT_PART_MIN_RT_OUT_TIME")) + ",");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("SPEC_DISPLAY"))?"":containerLabelData.get("SPEC_DISPLAY")) + "',");
									containerLabelCatpartNo.append("'" + (StringHandler.isBlankString((String)containerLabelData.get("CUSTOMER_PART_REVISION"))?"":containerLabelData.get("CUSTOMER_PART_REVISION")) + "')");
									detailQuery = containerLabelCatpartNo.toString();
								}
								else if(n == 16)
								{
									if(currentDetail.equalsIgnoreCase("N"))
									{
										Vector components = (Vector)finalcompdata.get(receiptIds[m]);								
										for(int p = 0; p < components.size(); p++)
										{
											Hashtable containerLabelComponentHash = (Hashtable)components.get(p);
											StringBuilder containerLabelComponent = new StringBuilder("insert into container_label_component (CONTAINER_LABEL_ID, RECEIPT_ID, PART_ID, MFG_LOT, EXPIRE_DATE, MFG_LABEL_EXPIRE_DATE) values (");
											containerLabelComponent.append(containerLabelId + ",");
											containerLabelComponent.append("'" + (StringHandler.isBlankString((String)containerLabelComponentHash.get("RECEIPT_ID"))?"":containerLabelComponentHash.get("RECEIPT_ID")) + "',");
											containerLabelComponent.append("'" + (StringHandler.isBlankString((String)containerLabelComponentHash.get("PART_ID"))?"":containerLabelComponentHash.get("PART_ID")) + "',");
											containerLabelComponent.append("'" + (StringHandler.isBlankString((String)containerLabelComponentHash.get("DETAIL_2"))?"":containerLabelComponentHash.get("DETAIL_2")) + "',");							
											String expireDateComp = (StringHandler.isBlankString((String)containerLabelComponentHash.get("DETAIL_5"))?"":containerLabelComponentHash.get("DETAIL_5").toString());
											containerLabelComponent.append((expireDateComp.equalsIgnoreCase("INDEFINITE")?"to_date('01/01/3000','/mm/dd/yyyy')":"'"+expireDateComp+"'") + ",'')");		

											try
											{
												db.doUpdate(containerLabelComponent.toString());
											}
											catch ( Exception ex )
											{
												ex.printStackTrace();
											}
										}
									}
									//Just go for this case when DETAIL_16 whether component or not
									continue;
								}			
								else
								{
									detailQuery = "insert into container_label_cat_part_no (CAT_PART_NO,CONTAINER_LABEL_ID) values ('"+currentDetail+"',"+containerLabelId+")";
								}
								
								if(detailQuery != null)
								{
									try
									{
										db.doUpdate(detailQuery);
									}
									catch ( Exception ex )
									{
										ex.printStackTrace();
									}
								}
							}
						}
				    }
              }   

                String printupquery="update receipt set PICKABLE='Y',LAST_LABEL_PRINT_DATE = SYSDATE where receipt_id in (" + receiptId + ") ";
				try
				{
					db.doUpdate( printupquery );
				}
				catch ( Exception ex )
				{
				}
			}

			String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
			String url= wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".pdf";
			String jnlpurl = wwwHome + "labels/Barcode"+tmpReqNum.toString()+".jnlp";

			try
			{
				if ( noeditvalues )
				{
					Vector sequencenumbers=new Vector( 2 );
					Integer intreceipt_id=new Integer( receiptId );
					sequencenumbers.addElement( intreceipt_id );

					Vector QuantityNos=new Vector( 2 );
					if ( qty_picked.length() > 0 )
					{
						QuantityNos.addElement( qty_picked );
					}
					else
					{
						QuantityNos.addElement( "1" );
					}
					BarCodePage obj1=new BarCodePage( new InternalServerResourceBundle(),db,QuantityNos );
					obj1.sethubNum(HubNoforlabel);
					url=obj1.generateContainerLabel(sequencenumbers,personnelId,!"Yes".equalsIgnoreCase(printKitLabels));
					sequencenumbers = null;
				}
				else
				{
					DBResultSet dbrs = null;
					ResultSet rs = null;
					int printerCount = 0;
					String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '" + PaperSize + "'";
					//String printerPath = "";
					//String printerRes = "";

					if ( printerPath.length() == 0 )
					{
						try
						{
							dbrs=db.doQuery( iszplprinter );
							rs=dbrs.getResultSet();

							while ( rs.next() )
							{
								printerPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
								printerRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );
								printerCount++;
							}
						}
						catch ( Exception e )
						{
							e.printStackTrace();
						}
						finally
						{
							if ( dbrs != null ){dbrs.close();}
						}

						if ( printerCount > 1 )
						{
							printPrinterChoice( out,printKitLabels,printerPath,printerRes,PaperSize,personnelId,HubNoforlabel );
							return;
						}
					}

					Vector invgrpdata = new Vector();
					invgrpdata = (Vector)session.getAttribute("PRINTINVGRPDATA");

					if (printerPath.trim().length() > 0)
						//if ( !"811".equalsIgnoreCase( PaperSize ) && ("2118".equalsIgnoreCase(HubNoforlabel) || "35".equalsIgnoreCase(PaperSize) ) )
					{
						ZPLBarCodeGenerator zplobj=new ZPLBarCodeGenerator( db );
						zplobj.buildReceiptZpl( finaldata, tmpReqNum.toString(),PaperSize,HubNoforlabel,finalLabelcompdata,personnelId,printerPath,printerRes,invgrpdata,!"Yes".equalsIgnoreCase(printKitLabels) );
						url=jnlpurl;
					}
					else
					{
						if (finaldata.size() >0)
							acobj.buildTest( finaldata,tmpReqNum.toString(),PaperSize,HubNoforlabel,dumpcompdata );
					}
				}
			}
			catch ( Exception ex11 )
			{
				url = "";
			}

			//System.out.println(URL11);
			finaldata=null;
			synch_data=null;
			finalcompdata =null;
			dumpcompdata = null;

			out.println(radian.web.HTMLHelpObj.labelredirection(url));

		}
		else
		{
			out.println( "<HTML>\n" );
			out.println( "<HEAD>\n" );
			out.println( "<TITLE>Re-Print Labels</TITLE>\n" );
			out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
			out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
			out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
			out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
			out.println( "<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
			out.println( "<SCRIPT SRC=\"/clientscripts/inventransaction.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
			out.println( "</HEAD>\n" );
			out.println( "<BODY>\n" );
			out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
			out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
			out.println( "</DIV>\n" );
			out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
			out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Re-Print Labels</B>\n" ) );
			out.println( "<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generatelabels\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + HubNoforlabel + "\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"receiptId\" VALUE=\"" + receiptId + "\">\n" );
			out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"300\" CLASS=\"moveup\">\n" );
			out.println( "<TR VALIGN=\"MIDDLE\">\n" );
			out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
			out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" value=\"31\" checked>\n" );
			out.println( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/1''&nbsp;</TD>\n" );
			out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
			out.println( "<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"paperSize\" value=\"811\">\n" );
			out.println( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">8.5''/11''</TD>\n\n" );
			out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option5\" TYPE=\"radio\" NAME=\"paperSize\" value=\"35\">\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option6\">3''/5''</TD>\n\n");
			out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );

			out.println( "<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
			out.println( "</TD>\n" );
			out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
			out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n" );
			out.println("</TD></TR>\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");
			out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
			out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
			out.println( "</TD>\n" );
			out.println( "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
			out.println( "</TD>\n" );
			out.println("</TR></TABLE>\n");

			if ( noeditvalues )
			{
				out.println( "<BR><BR><BR><td width=\"5%\" height=\"38\">Receipt Id: " + receiptId +  "     <input type=\"text\" CLASS=\"DETAILS\" name=\"qty_picked\"  value=\"1\" maxlength=\"10\" size=\"5\"></td>\n" );
				out.println( "</form>\n" );
				out.println( "</body></html>\n" );
			}
			else
			{
				Vector retrn_data=new Vector();
				buildDetails( receiptId,HubNoforlabel,session);
			}
		}
		out.close();
		//out.println( Msgn );
	}

	private void printPrinterChoice( PrintWriter out,String printKitLabels,String printerPath,String printerRes,String paperSize,String personnelId,
			String hubNoforlabel)
	{
		out.println("<HTML>\n");
		out.println("<HEAD>\n");
		out.println("<TITLE>Re-Print Labels</TITLE>\n");
		out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
		out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		out.println("<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		out.println("</HEAD>\n");
		out.println("<BODY>\n");

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Re-Print Labels</B>\n"));

		out.println("<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"pickedPrinterForLabels\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printerRes\" VALUE=\""+printerRes+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printKitLabels\" VALUE=\""+printKitLabels+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\""+paperSize+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\""+hubNoforlabel+"\">\n");

		out.println("<BR><B>Please Pick a Printer:</B>");
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String tmpPrinterPath = "";
		String tmpPrinterRes = "";

		try
		{
			String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +
			personnelId + " and x.LABEL_STOCK = '" + paperSize + "' ORDER BY x.PRINTER_PATH";
			dbrs=db.doQuery( iszplprinter );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				tmpPrinterPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
				tmpPrinterRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );

				out.println( "<TR VALIGN=\"MIDDLE\">\n" );
				out.println( "<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onChange=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" " + ( tmpPrinterPath.equalsIgnoreCase( printerPath ) ? "checked" : "" ) + ">\n" );
				out.println( "</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n" );
				/*out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"printerRes\" value=\""+printerRes+"\">\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">"+printerRes+"</TD>\n\n");*/
				out.println( "</TR>\n" );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( dbrs != null ){dbrs.close();}
		}

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Generate Labels Button
		out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
		out.println("</TD></TR>\n");

		/*out.println("<TR VALIGN=\"MIDDLE\">\n");
	   out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	   out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	   out.println( "</TD>\n" );
	   out.println( "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	   out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
	   out.println( "</TD>\n" );*/
		out.println("</TABLE>\n");
	}

	private Vector SynchprintData( HttpServletRequest request, Vector in_data)
	{
		Vector new_data = new Vector();
		String total = BothHelpObjs.makeBlankFromNull(request.getParameter("Total"));

		try
		{
			for ( int i = 1 ; i < (Integer.parseInt(total)+1); i++)
			{
				Hashtable hD = new Hashtable();
				Hashtable hDdata = new Hashtable();

				hD = ( Hashtable)( in_data.elementAt(i-1));
				hDdata = ( Hashtable)( hD.get("DATA"));

				String qtypicked1 = "";
				qtypicked1 = BothHelpObjs.makeBlankFromNull(request.getParameter("qty_picked"+i));

				if (!"0".equalsIgnoreCase(qtypicked1))
				{
					hD.remove("QUANTITY_RECEIVED");
					hD.put("QUANTITY_RECEIVED", qtypicked1 );

					String mrline = BothHelpObjs.makeBlankFromNull(request.getParameter("mrline"+i));
					String itemid = BothHelpObjs.makeBlankFromNull(request.getParameter("itemid"+i));

					//String expdate = BothHelpObjs.makeBlankFromNull(request.getParameter("expdate"+i));
					String recerts = BothHelpObjs.makeBlankFromNull(request.getParameter("recert"+i));
					String qcDoc=BothHelpObjs.makeBlankFromNull( request.getParameter( "qcDoc" + i ) );
					String temp = BothHelpObjs.makeBlankFromNull(request.getParameter("temp"+mrline+itemid));
					String part1 = BothHelpObjs.makeBlankFromNull(request.getParameter("part1"+mrline+itemid));
					String part2 = BothHelpObjs.makeBlankFromNull(request.getParameter("part2"+mrline+itemid));
					String part3 = BothHelpObjs.makeBlankFromNull(request.getParameter("part3"+mrline+itemid));
					String part4 = BothHelpObjs.makeBlankFromNull(request.getParameter("part4"+mrline+itemid));
					String part5 = BothHelpObjs.makeBlankFromNull(request.getParameter("part5"+mrline+itemid));
					String part6 = BothHelpObjs.makeBlankFromNull(request.getParameter("part6"+mrline+itemid));
					String part7 = BothHelpObjs.makeBlankFromNull(request.getParameter("part7"+mrline+itemid));
					//String color = BothHelpObjs.makeBlankFromNull(request.getParameter("color"+mrline+itemid));
					String freeText = BothHelpObjs.makeBlankFromNull(request.getParameter("freeText"+mrline+itemid));
                    String rePrintContainerId = BothHelpObjs.makeBlankFromNull(request.getParameter("rePrintContainerId"+mrline+itemid));

					hDdata.remove("DETAIL_8");
					hDdata.put("DETAIL_8",temp);
					hDdata.remove("DETAIL_5");
					hDdata.put("DETAIL_5",recerts);
					hDdata.remove( "DETAIL_14" );
					hDdata.put( "DETAIL_14",qcDoc );
					/*hDdata.remove("DETAIL_7");
            hDdata.put("DETAIL_7",expdate);*/

					hDdata.remove("DETAIL_9");
					hDdata.remove("DETAIL_10");
					hDdata.remove("DETAIL_11");
					hDdata.remove("DETAIL_12");
					hDdata.remove("DETAIL_13");

					hDdata.put("DETAIL_9",part1);
					hDdata.put("DETAIL_10",part2);
					hDdata.put("DETAIL_11",part3);
					hDdata.put("DETAIL_12",part4);
					hDdata.put("DETAIL_13",part5);
					hDdata.put("DETAIL_17",part6);
					hDdata.put("DETAIL_18",part7);

					DBResultSet dbrs = null;
					ResultSet rs = null;
					String inventoryGroup = hDdata.get("INVENTORY_GROUP").toString();
					String itemId = hDdata.get("ITEM_ID").toString();
					String Query_Statement="select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
					"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,SPEC_FIELDNAME," +
					"UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,CAT_PART_MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE ,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + itemId +
					" and INVENTORY_GROUP = '" + inventoryGroup + "' and CAT_PART_NO = '"+part1+"' group by CUSTOMER_PART_REVISION,LABEL_SPEC," +
					"CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,CAT_PART_MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY order by 4 desc,5 desc, 12 desc) a where rownum < 8";
					try
					{
						dbrs=db.doQuery( Query_Statement );
						rs=dbrs.getResultSet();

						while ( rs.next() )
						{
							hDdata.remove( "KIT_MSDS_NUMBER" );
							hDdata.put( "KIT_MSDS_NUMBER",rs.getString( "KIT_MSDS_NUMBER" ) == null ? " " : rs.getString( "KIT_MSDS_NUMBER" ) );
							hDdata.remove( "MIN_RT_OUT_TIME" );
							hDdata.put( "MIN_RT_OUT_TIME",rs.getString( "CAT_PART_MIN_RT_OUT_TIME" ) == null ? " " : rs.getString( "CAT_PART_MIN_RT_OUT_TIME" ) );
							hDdata.remove( "MIN_STORAGE_TEMP_DISPLAY" );
							hDdata.put( "MIN_STORAGE_TEMP_DISPLAY",rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) == null ? " " : rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) );
							hDdata.remove( "PART_SHORT_NAME" );
							hDdata.put( "PART_SHORT_NAME",rs.getString( "PART_SHORT_NAME" ) == null ? " " : rs.getString( "PART_SHORT_NAME" ) );
							hDdata.remove( "SPEC_ID" );
							hDdata.put( "SPEC_ID",rs.getString( "SPEC_ID" ) == null ? " " : rs.getString( "SPEC_ID" ) );
							hDdata.remove( "SPEC_FIELDNAME" );
							hDdata.put( "SPEC_FIELDNAME",rs.getString( "SPEC_FIELDNAME" ) == null ? " " : rs.getString( "SPEC_FIELDNAME" ) );
							hDdata.remove( "PACKAGING" );
							hDdata.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
							hDdata.remove( "TIME_TEMP_SENSITIVE" );
							hDdata.put( "TIME_TEMP_SENSITIVE",rs.getString( "TIME_TEMP_SENSITIVE" ) == null ? " " : rs.getString( "TIME_TEMP_SENSITIVE" ) );
							hDdata.remove( "PART_DESCRIPTION" );
							String partDescription = rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" );
							if (partDescription.length() > 30)
							{
								partDescription = partDescription.substring(0,30);
							}
							hDdata.put( "PART_DESCRIPTION",partDescription); 
							hDdata.remove( "SPEC_DISPLAY" );
							String specDisplay = rs.getString( "SPEC_DISPLAY" ) == null ? " " : rs.getString( "SPEC_DISPLAY" );
							if (specDisplay.length() > 76)
								specDisplay = specDisplay.substring(0,75);
							hDdata.put( "SPEC_DISPLAY",specDisplay);
						}
					}
					catch ( Exception e )
					{
						e.printStackTrace();
					}
					finally
					{
						if ( dbrs != null ){dbrs.close();}
					}

					String partList = "";
					int partCount = 0;
					if (part1.trim().length() > 0){	partList += part1;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part2.trim().length() > 0){	partList += part2;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part3.trim().length() > 0){	partList += part3;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part4.trim().length() > 0){	partList += part4;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part5.trim().length() > 0){	partList += part5;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part6.trim().length() > 0){	partList += part6;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}

					if (part7.trim().length() > 0){	partList += part7;
					if (partCount > 0) { partList += ",";}
					partCount++;
					}


					hDdata.remove("PART_LIST");
					hDdata.put("PART_LIST",partList);

					/*hDdata.remove("LABEL_COLOR");
			hDdata.put("LABEL_COLOR",color);*/

					hDdata.remove("FREE_TEXT");
					hDdata.put("FREE_TEXT",freeText);

                    hDdata.remove("RE_PRINT_CONTAINER_ID");
                    hDdata.put("RE_PRINT_CONTAINER_ID",rePrintContainerId);                 
                                                
                    hD.remove("DATA");
					hD.put("DATA", hDdata );

					new_data.addElement(hD);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Re-Print Labels",intcmIsApplication));
		}

		return new_data;
	}

	private Hashtable copyFreeTextData(String total, Hashtable componentData, Vector receiptData) {
		Hashtable finalLacompdata = new Hashtable();
		if (componentData != null) {
			{
				try {
					for (int i = 1; i < (Integer.parseInt(total) + 1); i++) {
						Hashtable hD = new Hashtable();
						Hashtable hDdata = new Hashtable();

						hD = (Hashtable) (receiptData.elementAt(i - 1));
						hDdata = (Hashtable) (hD.get("DATA"));

						String freeText = (hDdata.get("FREE_TEXT") == null ? " " : hDdata.get("FREE_TEXT").toString());
						String keyreceiptid = (hDdata.get("DETAIL_2") == null ? " " : hDdata.get("DETAIL_2").toString());
						String qcDoc = (hDdata.get("DETAIL_14") == null ? " " : hDdata.get("DETAIL_14").toString());
						String part1 = (hDdata.get("DETAIL_9") == null ? " " : hDdata.get("DETAIL_9").toString());
						String part2 = (hDdata.get("DETAIL_10") == null ? " " : hDdata.get("DETAIL_10").toString());
						String part3 = (hDdata.get("DETAIL_11") == null ? " " : hDdata.get("DETAIL_11").toString());
						String part4 = (hDdata.get("DETAIL_12") == null ? " " : hDdata.get("DETAIL_12").toString());
						String part5 = (hDdata.get("DETAIL_13") == null ? " " : hDdata.get("DETAIL_13").toString());
						String part6 = (hDdata.get("DETAIL_17") == null ? " " : hDdata.get("DETAIL_17").toString());
						String part7 = (hDdata.get("DETAIL_18") == null ? " " : hDdata.get("DETAIL_18").toString());

						Vector compData = new Vector();
						Vector newCompData = new Vector();

						compData = (Vector) componentData.get(keyreceiptid);
						if (compData != null) {
							for (int componenetId = 0; componenetId < compData.size(); componenetId++) {
								Hashtable comph = (Hashtable) compData.elementAt(componenetId);
								comph.remove("FREE_TEXT");
								comph.put("FREE_TEXT", freeText);

								comph.remove("QC_DOC");
								comph.put("QC_DOC", qcDoc);

								comph.remove("DETAIL_1");
								comph.put("DETAIL_1", qcDoc);

								comph.remove("PART_1");
								comph.put("PART_1", part1);

								comph.remove("PART_2");
								comph.put("PART_2", part2);

								comph.remove("PART_3");
								comph.put("PART_3", part3);

								comph.remove("PART_4");
								comph.put("PART_4", part4);

								comph.remove("PART_5");
								comph.put("PART_5", part5);

								comph.remove("PART_6");
								comph.put("PART_6", part6);

								comph.remove("PART_7");
								comph.put("PART_7", part7);

								newCompData.addElement(comph);
							}
						}
						finalLacompdata.put(keyreceiptid, newCompData);

					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			return finalLacompdata;
		} else {
			return componentData;
		}
	}

	private void buildDetails(String receiptId,String hubNoforlabel,HttpSession sessiondetails)
	{
		//StringBuffer Msg = new StringBuffer();
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String checkednon = "";
		String Color ="CLASS=\"Inwhite"  ;
		String temp = "";
		String part1 = "";
		String part2 = "";
		String part3 = "";
		String part4 = "";
		String part5 = "";
		String part6 = "";
		String part7 = "";
		String color = "";
		String freeText = "";
		String qcDoc = "";
        String lastIdPrinted = "";
		Hashtable datab = new Hashtable();
		Hashtable dataV = new Hashtable();
		Vector dataV1 = new Vector();
		Vector comptdataV1=new Vector();
		Hashtable compdata=new Hashtable();
		Hashtable finalcompdata=new Hashtable();
		Vector invengrpvec = new Vector();

		try
		{
			out.println("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
			int lineadded = 0;
			int lastItemNum = 1;
			int lastdate = 1;
			BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();

			out.println("<tr align=\"center\">\n");
			out.println("<TH width=\"5%\"  height=\"38\">Item</TH>\n");
			out.println("<TH width=\"5%\"  height=\"38\">Temp</TH>\n");
			if (!"2117".equalsIgnoreCase(hubNoforlabel))
			{
				out.println("<TH width=\"5%\"  height=\"38\">Part 1</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 2</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 3</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 4</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 5</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 6</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Part 7</TH>\n");
			}
			else
			{
				//out.println("<TH width=\"5%\"  height=\"38\">Color</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">Test</TH>\n");
			}
			out.println("<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n");
			out.println("<TH width=\"5%\"  height=\"38\">Exp Date</TH>\n");
			if (!"2117".equalsIgnoreCase(hubNoforlabel))
			{
				out.println("<TH width=\"5%\"  height=\"38\">Recert</TH>\n");
			}
			if ("2118".equalsIgnoreCase(hubNoforlabel))
			{
				out.println("<TH width=\"5%\"  height=\"38\">Cert</TH>\n");
			}
			out.println("<TH width=\"5%\"  height=\"38\">Re-Print Container ID</TH>\n");
            out.println("<TH width=\"5%\"  height=\"38\">No of Labels</TH>\n");
            out.println("</tr>\n");

			// get main information
			String prnumber = "";
			String itemid= "";
			String receiptid= receiptId;
			String qtypicked= "";
			String expireDate = "";

			String Line_Check  =  "";
			if (Line_Check.equalsIgnoreCase("yes")){checkednon = "checked";}else{checkednon = "";}

			int qty = 0;
			try {qty = Integer.parseInt(qtypicked);} catch(Exception eee){qty=0;}

			String chkbox_value = "no";
			{
				chkbox_value = "yes";
				lineadded++;

				out.println("<input type=\"hidden\" name=\"mrline"+lineadded+"\" value=\""+prnumber+"\">\n");
				out.println("<input type=\"hidden\" name=\"itemid"+lineadded+"\" value=\""+itemid+"\">\n");

				String BarcodeRId = "";
				String Item_Id = "";
				String recert = "";
				String Hub_Value = "";
				String inventorygrp = "";
				String certnuminvngrp = "";
				String qualitycntitem = "";
				String singleitemkit = "";
				String certified= "";

				String Query_Statement = "Select COMPONENT_MSDS_LIST,DIST_CUSTOMER_PART_LIST,RECERT_NUMBER,TOTAL_RECERTS_ALLOWED, LAST_ID_PRINTED, SPEC_LIST, COMPANY_MSDS_LIST,CATALOG_ITEM_DESCRIPTION,CUSTOMER_RECEIPT_ID,RECEIVER_DATE_OF_RECEIPT,SHELF_LIFE_STORAGE_TEMP,to_char(EXPIRE_DATE_ORIG,'dd-MON-yyyy') BOEING_EXPIRE_DATE,to_char(EXPIRE_DATE_ORIG,'mmddyyyy') ITALY_EXPIRE_DATE,DATE_OF_MANUFACTURE,MAX_USE_IN_MONTHS,EXPIRE_DATE_LOCALE,DATE_OF_MANUFACTURE_LOCALE,CERTIFIED,PICKABLE,TOTAL_COMPONENTS,INSEPARABLE_KIT,NUMBER_OF_KITS,MANAGE_KITS_AS_SINGLE_UNIT,QC_DOC,CERTIFICATION_NUMBER,QUALITY_CONTROL_ITEM,INVENTORY_GROUP,HUB,QUANTITY_RECEIVED,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,ITEM_ID,RADIAN_PO,MFG_LOT,to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DOR,";
				Query_Statement += "RECERTS,BIN,EXPIRE_DATE,LABEL_STORAGE_TEMP,LABEL_COLOR, SHELF_LIFE_BASIS, SHELF_LIFE_BASIS_DATE, SPEC_DETAIL,QUALITY_TRACKING_NUMBER, RECERTS_TO_RECERTS_ALLOWED,OWNER_SEGMENT_ID,ACCOUNT_NUMBER,ACCOUNT_NUMBER2,ACCOUNT_NUMBER3,ACCOUNT_NUMBER4,TRACE_ID,MATERIAL_ID,SUPPLIER_CAGE_CODE,DATE_OF_REPACKAGING, to_char(EXPIRE_DATE_ORIG,'MM/dd/yyyy') BARCODE_EXPIRE_DATE, to_char(EXPIRE_DATE_ORIG, 'MM/YY') EXPIRE_DATE_MONTH_YEAR, PO_NUMBER  from container_label_master_view where RECEIPT_ID = '"+receiptid+"'";

				dbrs = db.doQuery(Query_Statement);
				rs=dbrs.getResultSet();
				try
				{
					while (rs.next())
					{
						BarcodeRId = com.tcmis.common.util.BarCodeHandler.Code128c(rs.getString("RECEIPT_ID1"));
						datab.put("DETAIL_0",BarcodeRId);
						Item_Id = rs.getString("ITEM_ID")==null?" ":rs.getString("ITEM_ID");
						inventorygrp = rs.getString("INVENTORY_GROUP")==null?" ":rs.getString("INVENTORY_GROUP");
						certnuminvngrp = rs.getString("CERTIFICATION_NUMBER")==null?" ":rs.getString("CERTIFICATION_NUMBER");
						qualitycntitem = rs.getString("QUALITY_CONTROL_ITEM")==null?" ":rs.getString("QUALITY_CONTROL_ITEM");
						singleitemkit = rs.getString("MANAGE_KITS_AS_SINGLE_UNIT")==null?" ":rs.getString("MANAGE_KITS_AS_SINGLE_UNIT");
						certified=rs.getString( "CERTIFIED" ) == null ? " " : rs.getString( "CERTIFIED" );
						String receiptId1 = rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" );
						String mfgLot = rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" );
						String italyExpireDate = rs.getString( "ITALY_EXPIRE_DATE" ) == null ? " " : rs.getString( "ITALY_EXPIRE_DATE" );
						String labelStorageTemp = rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" );
						String maxUseInMonths = rs.getString( "MAX_USE_IN_MONTHS" ) == null ? " " : rs.getString( "MAX_USE_IN_MONTHS" );
						String expireDateLocale = rs.getString( "EXPIRE_DATE_LOCALE" ) == null ? " " : rs.getString( "EXPIRE_DATE_LOCALE" );
						if (("-1").equals(maxUseInMonths)) {
							maxUseInMonths = expireDateLocale;
						}
						String expireDateMonthYear = rs.getString( "EXPIRE_DATE_MONTH_YEAR" ) == null ? "" : rs.getString( "EXPIRE_DATE_MONTH_YEAR" );
						if(expireDateMonthYear.equals("01/00"))
							expireDateMonthYear = "Ind.";

						datab.put("DETAIL_1",Item_Id);
						datab.put("DETAIL_2",rs.getString("RECEIPT_ID1")==null?" ":rs.getString("RECEIPT_ID1"));
						datab.put("DETAIL_3",rs.getString("MFG_LOT")==null?" ":rs.getString("MFG_LOT"));
						datab.put("DETAIL_4",rs.getString("DOR")==null?" ":rs.getString("DOR"));
						recert = rs.getString("RECERTS")==null?"":rs.getString("RECERTS");
						datab.put("DETAIL_5",recert);
						datab.put("DETAIL_6",rs.getString("BIN")==null?" ":rs.getString("BIN"));
						expireDate = rs.getString("EXPIRE_DATE")==null?" ":rs.getString("EXPIRE_DATE");
						datab.put("DETAIL_7",expireDate);
						temp = rs.getString("LABEL_STORAGE_TEMP")==null?" ":rs.getString("LABEL_STORAGE_TEMP");
						datab.put("DETAIL_8",temp);
						datab.put("DETAIL_16",singleitemkit);
						datab.put("INVENTORY_GROUP",inventorygrp);
						datab.put("NUMBER_OF_KITS",rs.getString("NUMBER_OF_KITS")==null?" ":rs.getString("NUMBER_OF_KITS"));
						datab.put("INSEPARABLE_KIT",rs.getString("INSEPARABLE_KIT")==null?" ":rs.getString("INSEPARABLE_KIT"));
						datab.put( "TOTAL_COMPONENTS",rs.getString( "TOTAL_COMPONENTS" ) == null ? "" : rs.getString( "TOTAL_COMPONENTS" ) );
						color = rs.getString( "LABEL_COLOR" ) == null ? "" : rs.getString( "LABEL_COLOR" );
						datab.put( "LABEL_COLOR",color);
						datab.put( "SHELF_LIFE_BASIS",rs.getString( "SHELF_LIFE_BASIS" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS" ) );
						datab.put( "SHELF_LIFE_BASIS_DATE",rs.getString( "SHELF_LIFE_BASIS_DATE" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS_DATE" ) );
						datab.put( "SPEC_DETAIL",rs.getString( "SPEC_DETAIL" ) == null ? "" : rs.getString( "SPEC_DETAIL" ) );
						datab.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
						datab.put( "FREE_TEXT","" );
                        datab.put( "RE_PRINT_CONTAINER_ID","" );
                        datab.put( "MAX_USE_IN_MONTHS",maxUseInMonths);
						datab.put( "EXPIRE_DATE_LOCALE",expireDateLocale);
						datab.put( "DATE_OF_MANUFACTURE_LOCALE",rs.getString( "DATE_OF_MANUFACTURE_LOCALE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE_LOCALE" ) );
						datab.put( "DATE_OF_MANUFACTURE",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
						String italyBarcode = Item_Id+","+receiptId+","+mfgLot+","+italyExpireDate;
						
						String iai2DBarcode = Item_Id+"|"+(mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot)+"|"+italyExpireDate;
						datab.put( "IAI_2D_BARCODE",iai2DBarcode);

						datab.put( "DATE_OF_MANUFACTURE",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
						datab.put( "ITALY_BARCODE",italyBarcode);
						datab.put( "RECEIPT_ID",receiptId);
						datab.put( "MFG_LOT",mfgLot );
						datab.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
						datab.put( "USE_WITH","" );
						datab.put( "EXPIRE_DATE",expireDate );
						datab.put( "ITEM_COMPONENT","");
						datab.put( "RECEIVER_DATE_OF_RECEIPT",rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) );
						datab.put( "SHELF_LIFE_STORAGE_TEMP",rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) == null ? "" : rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) );
						datab.put( "CATALOG_ITEM_DESCRIPTION",rs.getString( "CATALOG_ITEM_DESCRIPTION" ) == null ? "" : rs.getString( "CATALOG_ITEM_DESCRIPTION" ) );
						datab.put( "CUSTOMER_RECEIPT_ID",rs.getString( "CUSTOMER_RECEIPT_ID" ) == null ? "" : rs.getString( "CUSTOMER_RECEIPT_ID" ) );
						datab.put( "TOTAL_RECERTS_ALLOWED",rs.getString( "TOTAL_RECERTS_ALLOWED" ) == null ? "" : rs.getString( "TOTAL_RECERTS_ALLOWED" ) );
						lastIdPrinted =rs.getString( "LAST_ID_PRINTED" ) == null ? "" : rs.getString( "LAST_ID_PRINTED" );
                        datab.put( "LAST_ID_PRINTED", lastIdPrinted );
						datab.put( "SPEC_LIST",rs.getString( "SPEC_LIST" ) == null ? "" : rs.getString( "SPEC_LIST" ) );
						datab.put( "COMPANY_MSDS_LIST",rs.getString( "COMPANY_MSDS_LIST" ) == null ? "" : rs.getString( "COMPANY_MSDS_LIST" ) );
						datab.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
						datab.put( "DIST_CUSTOMER_PART_LIST",rs.getString( "DIST_CUSTOMER_PART_LIST" ) == null ? "" : rs.getString( "DIST_CUSTOMER_PART_LIST" ) );
						datab.put( "QUALITY_TRACKING_NUMBER",rs.getString( "QUALITY_TRACKING_NUMBER" ) == null ? "" : rs.getString( "QUALITY_TRACKING_NUMBER" ) );
						datab.put( "RECERTS_TO_RECERTS_ALLOWED",rs.getString( "RECERTS_TO_RECERTS_ALLOWED" ) == null ? "" : rs.getString( "RECERTS_TO_RECERTS_ALLOWED" ) );
						datab.put( "OWNER_SEGMENT_ID",rs.getString( "OWNER_SEGMENT_ID" ) == null ? "" : rs.getString( "OWNER_SEGMENT_ID" ) );
						datab.put( "ACCOUNT_NUMBER",rs.getString( "ACCOUNT_NUMBER" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER" ) );
						datab.put( "ACCOUNT_NUMBER2",rs.getString( "ACCOUNT_NUMBER2" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER2" ) );
						datab.put( "ACCOUNT_NUMBER3",rs.getString( "ACCOUNT_NUMBER3" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER3" ) );
						datab.put( "ACCOUNT_NUMBER4",rs.getString( "ACCOUNT_NUMBER4" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER4" ) );
						datab.put( "TRACE_ID",rs.getString( "TRACE_ID" ) == null ? "" : rs.getString( "TRACE_ID" ));
						datab.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ));
                        datab.put( "COMPONENT_MSDS_LIST",rs.getString( "COMPONENT_MSDS_LIST" ) == null ? "" : rs.getString( "COMPONENT_MSDS_LIST" ));
                	    datab.put( "SUPPLIER_CAGE_CODE",rs.getString( "SUPPLIER_CAGE_CODE" ) == null ? "" : rs.getString( "SUPPLIER_CAGE_CODE" ));
                	    datab.put( "DATE_OF_REPACKAGING",rs.getString( "DATE_OF_REPACKAGING" ) == null ? "" : rs.getString( "DATE_OF_REPACKAGING" ));
                	    datab.put( "BARCODE_EXPIRE_DATE",rs.getString( "BARCODE_EXPIRE_DATE" ) == null ? "" : rs.getString( "BARCODE_EXPIRE_DATE" ));
                	    datab.put( "EXPIRE_DATE_MONTH_YEAR",expireDateMonthYear);
                	    datab.put( "EXPIRE_DATE_MMDDYYYY", italyExpireDate);
                	    datab.put( "PO_NUMBER",rs.getString( "PO_NUMBER" ) == null ? "" : rs.getString( "PO_NUMBER" ));
                	    
                        if (labelStorageTemp !=null && labelStorageTemp.trim().length() >0)
						{
							labelStorageTemp = labelStorageTemp.substring(2,labelStorageTemp.length());
						}
						datab.put( "LABEL_STORAGE_TEMP",labelStorageTemp);

						String dpmnumber = rs.getString("QC_DOC")==null?" ":rs.getString("QC_DOC");
						if ( dpmnumber.trim().length() == 0 )
						{
							datab.put( "DETAIL_14","Not For Production" );
							qcDoc = "Not For Production";
						}
						else
						{
							datab.put( "DETAIL_14","" + dpmnumber + "" );
							qcDoc = dpmnumber;
						}
						datab.put("DETAIL_15",rs.getString("LABEL_STORAGE_TEMP")==null?" ":rs.getString("LABEL_STORAGE_TEMP"));

						Hub_Value = rs.getString("HUB")==null?"":rs.getString("HUB");
						if ( !invengrpvec.contains( inventorygrp ) )
						{
							invengrpvec.add( inventorygrp );
						}

						//Rcpt ID + | + Part # + | + Lot # + | + Exp Date (DD-MMM-YY) + | + Pad with zeros to total 81 chars
						String boeing2dBarcode="";
						boeing2dBarcode += com.tcmis.common.util.StringHandler.padCharacter(receiptId,new Integer("8"),"0","left");
						if (dpmnumber.trim().length() == 0)
						{
							boeing2dBarcode += "|N/A";
						}
						else
						{
							boeing2dBarcode += "|" + (dpmnumber.indexOf("|") != -1 || dpmnumber.indexOf(",") != -1 ? barCode2dReplace.matcher(dpmnumber).replaceAll(" "):dpmnumber);
						}
						boeing2dBarcode += "|" + (mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot);
						String boeingExpireDate = rs.getString( "BOEING_EXPIRE_DATE" ) == null ? " " : rs.getString( "BOEING_EXPIRE_DATE" );
						datab.put( "BOEING_EXPIRE_DATE",rs.getString( "BOEING_EXPIRE_DATE" ) == null ? " " : rs.getString( "BOEING_EXPIRE_DATE" ) );

						if (expireDate.equalsIgnoreCase("N/A"))
						{
							boeing2dBarcode += "|N/A|";
						}
						else
						{
							boeing2dBarcode += "|" +boeingExpireDate + "|";
						}
						boeing2dBarcode = com.tcmis.common.util.StringHandler.padCharacter(boeing2dBarcode,new Integer("81"),"0","");
						//System.out.println("boeing2dBarcode:  " +boeing2dBarcode);
						datab.put( "BOEING_2D_BARCODE",boeing2dBarcode);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return;
				}
				finally
				{
					dbrs.close();
				}

				if (lastItemNum ==1)
				{
					if ( inventorygrp.length() > 0 )
					{
						Query_Statement="select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
						"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,SPEC_FIELDNAME," +
						"UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + Item_Id +
						" and HUB = " + Hub_Value +	" and INVENTORY_GROUP = '" + inventorygrp + "' group by CUSTOMER_PART_REVISION,LABEL_SPEC," +
						"CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY order by 4 desc,5 desc, 12 desc) a where rownum < 8";
					}
					else
					{
						Query_Statement="select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
						"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id," +
						"SPEC_FIELDNAME,UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + Item_Id +
						" and HUB = " + Hub_Value + " group by CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY " +
						"order by 4 desc,5 desc, 12 desc) a where rownum < 8";
					}

					int numberRec = 0;
					dbrs = db.doQuery(Query_Statement);
					rs=dbrs.getResultSet();

					String partList = "";
					int partCount = 0;
					datab.put( "PART_SHORT_NAME","" );
					datab.put( "LABEL_SPEC","" );
					datab.put( "SPEC_ID","" );
					datab.put( "SPEC_FIELDNAME","" );
					datab.put( "UNIT_OF_SALE","" );
					datab.put( "PACKAGING","" );
					datab.put( "KIT_MSDS_NUMBER","" );
					datab.put( "MIN_RT_OUT_TIME","" );
					datab.put( "MIN_STORAGE_TEMP_DISPLAY","" );
					datab.put( "TIME_TEMP_SENSITIVE","" );
					datab.put( "PART_DESCRIPTION","" );
					datab.put( "SPEC_DISPLAY","" );
                    datab.put( "CUSTOMER_PART_REVISION","" );
                    
                    try
					{
						while (rs.next())
						{
							String currentPartNumber = rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" );
							if (currentPartNumber.trim().length() > 0)
							{
								partList += currentPartNumber;
								if (partCount > 0) {
									partList += ",";
								}

								partCount++;
							}

							if (numberRec == 0)
							{
								part1 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
								datab.remove( "PART_SHORT_NAME" );
								datab.put( "PART_SHORT_NAME",rs.getString( "PART_SHORT_NAME" ) == null ? " " : rs.getString( "PART_SHORT_NAME" ) );
								datab.remove( "LABEL_SPEC" );
								datab.put( "LABEL_SPEC",rs.getString( "LABEL_SPEC" ) == null ? " " : rs.getString( "LABEL_SPEC" ) );
								datab.remove( "SPEC_ID" );
								datab.put( "SPEC_ID",rs.getString( "SPEC_ID" ) == null ? " " : rs.getString( "SPEC_ID" ) );
								datab.remove( "SPEC_FIELDNAME" );
								datab.put( "SPEC_FIELDNAME",rs.getString( "SPEC_FIELDNAME" ) == null ? " " : rs.getString( "SPEC_FIELDNAME" ) );
								datab.remove( "UNIT_OF_SALE" );
								datab.put( "UNIT_OF_SALE",rs.getString( "UNIT_OF_SALE" ) == null ? " " : rs.getString( "UNIT_OF_SALE" ) );
								datab.remove( "PACKAGING" );
								datab.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
								datab.remove( "KIT_MSDS_NUMBER" );
								datab.put( "KIT_MSDS_NUMBER",rs.getString( "KIT_MSDS_NUMBER" ) == null ? " " : rs.getString( "KIT_MSDS_NUMBER" ) );
								datab.remove( "MIN_RT_OUT_TIME" );
								datab.put( "MIN_RT_OUT_TIME",rs.getString( "MIN_RT_OUT_TIME" ) == null ? " " : rs.getString( "MIN_RT_OUT_TIME" ) );
								datab.remove( "MIN_STORAGE_TEMP_DISPLAY" );
								datab.put( "MIN_STORAGE_TEMP_DISPLAY",rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) == null ? " " : rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) );
								datab.remove( "TIME_TEMP_SENSITIVE" );
								datab.put( "TIME_TEMP_SENSITIVE",rs.getString( "TIME_TEMP_SENSITIVE" ) == null ? " " : rs.getString( "TIME_TEMP_SENSITIVE" ) );
								datab.remove( "PART_DESCRIPTION" );
								String partDescription = rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" );
								if (partDescription.length() > 30)
								{
									partDescription = partDescription.substring(0,30);
								}
								datab.put( "PART_DESCRIPTION",partDescription);
								datab.remove( "SPEC_DISPLAY" );
								String specDisplay = rs.getString( "SPEC_DISPLAY" ) == null ? " " : rs.getString( "SPEC_DISPLAY" );
								if (specDisplay.length() > 76)
									specDisplay = specDisplay.substring(0,75);
								datab.put( "SPEC_DISPLAY",specDisplay);
						

								String iai2DBarcode=datab.get( "IAI_2D_BARCODE" ) == null ? "" : datab.get( "IAI_2D_BARCODE" ).toString();
								iai2DBarcode +=  "|" +currentPartNumber;
								datab.remove( "IAI_2D_BARCODE" );
								datab.put( "IAI_2D_BARCODE",iai2DBarcode);

                                datab.remove( "CUSTOMER_PART_REVISION" );
                                datab.put( "CUSTOMER_PART_REVISION",rs.getString( "CUSTOMER_PART_REVISION" ) == null ? " " : rs.getString( "CUSTOMER_PART_REVISION" ) );
                            }
							else if (numberRec == 1)
							{
								part2 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
								datab.remove( "LABEL_SPEC2" );
								datab.put( "LABEL_SPEC2",rs.getString( "LABEL_SPEC" ) == null ? " " : rs.getString( "LABEL_SPEC" ) );
							}
							else if (numberRec == 2)
							{
								part3 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
							}
							else if (numberRec == 3)
							{
								part4 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
							}
							else if (numberRec == 4)
							{
								part5 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
							}
							else if (numberRec == 5)
							{
								part6 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
							}
							else if (numberRec == 6)
							{
								part7 = rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO");
							}

							numberRec +=1;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						return;
					}
					finally
					{
						dbrs.close();
					}
					datab.put( "PART_LIST",partList);

				}


				datab.put("DETAIL_9",part1);
				datab.put("DETAIL_10",part2);
				datab.put("DETAIL_11",part3);
				datab.put("DETAIL_12",part4);
				datab.put("DETAIL_13",part5);
				datab.put("DETAIL_17",part6);
				datab.put("DETAIL_18",part7);

				if ("Y".equalsIgnoreCase(qualitycntitem) && certnuminvngrp.trim().length() > 0)
				{
					datab.remove("DETAIL_13");
					datab.put( "DETAIL_13","Accepted By: "+certnuminvngrp+"");
					part5 = "Accepted By: "+certnuminvngrp+"";
				}

				if ( "Y".equalsIgnoreCase( qualitycntitem ) && "Y".equalsIgnoreCase( certified ) && certnuminvngrp.trim().length() == 0 )
				{
					barclog.info("No Certification Number for Receipt ID "+receiptid+"");
					out.println("Can not print labels. No Certification for Receipt ID "+receiptid+"");
					return;
				}

				Query_Statement="Select distinct PART_ID,HMIS_HEALTH, HMIS_FLAMMABILITY, HMIS_REACTIVITY, SPECIFIC_HAZARD, HEALTH, FLAMMABILITY, REACTIVITY, PERSONAL_PROTECTION, MFG_DESC, TRADE_NAME,RECERT_NUMBER,LABEL_STORAGE_TEMP, lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID, USE_WITH,ITEM_COMPONENT_QUANTITY,ITEM_ID,ITEM_COMPONENT,MFG_LOT, BIN, EXPIRE_DATE,QC_DOC,LABEL_COLOR, SHELF_LIFE_BASIS, SHELF_LIFE_BASIS_DATE, SPEC_DETAIL,DATE_OF_REPACKAGING, COMPONENT_MSDS  from CONTAINER_LABEL_COMPONENT_VIEW where RECEIPT_ID = " + receiptid + "";
				dbrs=db.doQuery( Query_Statement );
				rs=dbrs.getResultSet();
				comptdataV1=new Vector();
				String keyreceiptid="";
				StringBuilder componentMfgLots = new StringBuilder();	
				int recCntr = 0;
				try {
					while ( rs.next() )
					{
						if (recCntr == 0) {
							recCntr = 1;
							datab.put("HEALTH", rs.getString("HEALTH") == null ? " " : rs.getString("HEALTH") );
							datab.put("FLAMMABILITY", rs.getString("FLAMMABILITY") == null ? " " : rs.getString("FLAMMABILITY"));
							datab.put("REACTIVITY", rs.getString("REACTIVITY") == null ? " " : rs.getString("REACTIVITY"));
							datab.put("PERSONAL_PROTECTION", rs.getString("PERSONAL_PROTECTION") == null ? " " : rs.getString("PERSONAL_PROTECTION") );
							datab.put("MFG_DESC", rs.getString("MFG_DESC") == null ? " " : rs.getString("MFG_DESC") );
							datab.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? " " :  rs.getString("TRADE_NAME"));
							datab.put("HMIS_HEALTH", rs.getString("HMIS_HEALTH") == null ? " " : rs.getString("HMIS_HEALTH"));
							datab.put("HMIS_FLAMMABILITY", rs.getString("HMIS_FLAMMABILITY") == null ? " " : rs.getString("HMIS_FLAMMABILITY"));
                            datab.put("HMIS_REACTIVITY", rs.getString("HMIS_REACTIVITY") == null ? " " : rs.getString("HMIS_REACTIVITY"));
                            datab.put("SPECIFIC_HAZARD", rs.getString("SPECIFIC_HAZARD") == null ? " " : rs.getString("SPECIFIC_HAZARD"));
                            
                            componentMfgLots.append(rs.getString( "MFG_LOT" ));
						}
						if ( "N".equalsIgnoreCase( singleitemkit ) )
						{
							compdata=new Hashtable();
							compdata.put( "DETAIL_0",rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" ) );
							compdata.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" ) );
							String dpmnumber=rs.getString( "QC_DOC" ) == null ? " " : rs.getString( "QC_DOC" );
							if ( dpmnumber.trim().length() == 0 )
							{
								compdata.put( "DETAIL_1","Not For Production" );
								compdata.put( "QC_DOC","Not For Production" );
							}
							else
							{
								compdata.put( "DETAIL_1","" + dpmnumber + "" );
								compdata.put( "QC_DOC","" + dpmnumber + "" );
							}

							keyreceiptid=rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" );

							compdata.put( "DETAIL_2",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
							compdata.put( "DETAIL_3",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
							compdata.put( "DETAIL_4",rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" ) );
							compdata.put( "DETAIL_5",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
							compdata.put( "DETAIL_6",rs.getString( "ITEM_COMPONENT" ) == null ? " " : rs.getString( "ITEM_COMPONENT" ) );
							String usewith="Use ";
							usewith+=rs.getString( "ITEM_COMPONENT_QUANTITY" ) == null ? " " : rs.getString( "ITEM_COMPONENT_QUANTITY" );
							usewith+=" With: ";
							usewith+=rs.getString( "USE_WITH" ) == null ? " " : rs.getString( "USE_WITH" );
							compdata.put( "DETAIL_7",usewith );
							compdata.put( "DETAIL_8",rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" ) );
							compdata.put( "ITEM_COMPONENT_QUANTITY",rs.getString( "ITEM_COMPONENT_QUANTITY" ) == null ? " " : rs.getString( "ITEM_COMPONENT_QUANTITY" ) );
							compdata.put( "LABEL_COLOR",rs.getString( "LABEL_COLOR" ) == null ? "" : rs.getString( "LABEL_COLOR" ) );
							compdata.put( "SHELF_LIFE_BASIS",rs.getString( "SHELF_LIFE_BASIS" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS" ) );
							compdata.put( "SHELF_LIFE_BASIS_DATE",rs.getString( "SHELF_LIFE_BASIS_DATE" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS_DATE" ) );
							compdata.put( "SPEC_DETAIL",rs.getString( "SPEC_DETAIL" ) == null ? "" : rs.getString( "SPEC_DETAIL" ) );
							compdata.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
							compdata.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
							compdata.put( "FREE_TEXT","" );
							compdata.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
							compdata.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
							compdata.put( "USE_WITH",usewith );
							compdata.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
							compdata.put( "ITEM_COMPONENT",rs.getString( "ITEM_COMPONENT" ) == null ? " " : rs.getString( "ITEM_COMPONENT" ) );
							compdata.put( "HMIS_HEALTH",rs.getString( "HMIS_HEALTH" ) == null ? " " : rs.getString( "HMIS_HEALTH" ) );
							compdata.put( "HMIS_FLAMMABILITY",rs.getString( "HMIS_FLAMMABILITY" ) == null ? " " : rs.getString( "HMIS_FLAMMABILITY" ) );
							compdata.put( "HMIS_REACTIVITY",rs.getString( "HMIS_REACTIVITY" ) == null ? " " : rs.getString( "HMIS_REACTIVITY" ) );
							compdata.put( "SPECIFIC_HAZARD",rs.getString( "SPECIFIC_HAZARD" ) == null ? " " : rs.getString( "SPECIFIC_HAZARD" ) );
							compdata.put( "HEALTH",rs.getString( "HEALTH" ) == null ? " " : rs.getString( "HEALTH" ) );
							compdata.put( "FLAMMABILITY",rs.getString( "FLAMMABILITY" ) == null ? " " : rs.getString( "FLAMMABILITY" ) );
							compdata.put( "REACTIVITY",rs.getString( "REACTIVITY" ) == null ? " " : rs.getString( "REACTIVITY" ) );
							compdata.put( "PERSONAL_PROTECTION",rs.getString( "PERSONAL_PROTECTION" ) == null ? " " : rs.getString( "PERSONAL_PROTECTION" ) );
							compdata.put( "MFG_DESC",rs.getString( "MFG_DESC" ) == null ? " " : rs.getString( "MFG_DESC" ) );
							compdata.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? " " : rs.getString( "TRADE_NAME" ) );
					        compdata.put( "PART_ID",rs.getString( "PART_ID" ) == null ? " " : rs.getString( "PART_ID" ) );
					        compdata.put( "DATE_OF_REPACKAGING",rs.getString( "DATE_OF_REPACKAGING" ) == null ? " " : rs.getString( "DATE_OF_REPACKAGING" ) );
					        compdata.put( "COMPONENT_MSDS",rs.getString( "COMPONENT_MSDS" ) == null ? " " : rs.getString( "COMPONENT_MSDS" ) );

							compdata.put( "PART_1",""+datab.get("DETAIL_9").toString()+"" );
							compdata.put( "PART_2",""+datab.get("DETAIL_10").toString()+"" );
							compdata.put( "PART_3",""+datab.get("DETAIL_11").toString()+"" );
							compdata.put( "PART_4",""+datab.get("DETAIL_12").toString()+"" );
							compdata.put( "PART_5",""+datab.get("DETAIL_17").toString()+"" );
							compdata.put( "PART_6",""+datab.get("DETAIL_18").toString()+"" );
							compdata.put( "PART_7",""+datab.get("DETAIL_13").toString()+"" );
							compdata.put( "CATALOG_ITEM_DESCRIPTION",""+datab.get("CATALOG_ITEM_DESCRIPTION").toString()+"" );
							compdata.put( "COMPANY_MSDS_LIST",""+datab.get("COMPANY_MSDS_LIST").toString()+"" );
							compdata.put( "CUSTOMER_RECEIPT_ID",""+datab.get("CUSTOMER_RECEIPT_ID").toString()+"" );
							compdata.put( "KIT_MSDS_NUMBER",""+datab.get("KIT_MSDS_NUMBER").toString()+"" );
							compdata.put( "MIN_RT_OUT_TIME",""+datab.get("MIN_RT_OUT_TIME").toString()+"" );
							compdata.put( "MIN_STORAGE_TEMP_DISPLAY",""+datab.get("MIN_STORAGE_TEMP_DISPLAY").toString()+"" );
							compdata.put( "PART_DESCRIPTION",""+datab.get("PART_DESCRIPTION").toString()+"" );
							compdata.put( "TIME_TEMP_SENSITIVE",""+datab.get("TIME_TEMP_SENSITIVE").toString()+"" );
							compdata.put( "EXPIRE_DATE_LOCALE",""+datab.get("EXPIRE_DATE_LOCALE").toString()+"" );
                    

                            if ( "Y".equalsIgnoreCase( qualitycntitem ) && certnuminvngrp.trim().length() > 0 )
							{
								compdata.put( "DETAIL_9","Accepted By: " + certnuminvngrp + "" );
							}
							else
							{
								compdata.put( "DETAIL_9","" );
							}

							compdata.put( "DETAIL_10","" );
							compdata.put( "DETAIL_11","" );
							compdata.put( "DETAIL_12","" );
							compdata.put( "DETAIL_13","" );

							comptdataV1.addElement( compdata );
							
							if(!StringHandler.isBlankString(rs.getString( "MFG_LOT" )) && recCntr != 0){
								if(componentMfgLots.length() > 0)
									componentMfgLots.append(";");
								componentMfgLots.append(rs.getString( "MFG_LOT" ));
							}
						}
					}
					
					datab.put("COMPONENT_MFG_LOTS", componentMfgLots);
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
				finally
				{
					if ( dbrs != null )
					{
						dbrs.close();
					}
				}
				if(!comptdataV1.isEmpty()) {
					finalcompdata.put( keyreceiptid,comptdataV1 );
				}
                
                // Create the label field content lmgp2dFN2
				// This barcode is defined here rather being assigned to a specific label format and label field id in ZPLBarCodeGenerator so that it can be reused by multiple label formats
				StringBuilder lmgp2dFN2 = new StringBuilder("   LCM$");
				String field = (!StringHandler.isBlankString(part1) && (part1.indexOf("|") != -1 ||part1.indexOf(",") != -1) ? barCode2dReplace.matcher(part1).replaceAll(" "):part1);
				if (field != null && field.length() > 30) {
					lmgp2dFN2.append("\t").append(field.substring(0, 30));
				}
				else {
					lmgp2dFN2.append("\t").append(field);
				}

				lmgp2dFN2.append("\t").append(receiptId);
				
				field = (String) datab.get("EXPIRE_DATE_LOCALE");
				if(field.equals("NONE REQUIRED") || field.equals("INDEFINITE"))
					lmgp2dFN2.append("\t").append("01013000");
				else
					lmgp2dFN2.append("\t").append(datab.get("EXPIRE_DATE_MMDDYYYY"));

				lmgp2dFN2.append("\t").append(datab.get("COMPONENT_MFG_LOTS"));
				lmgp2dFN2.append("\t").append("HGI8");
				
				datab.remove("lmgp2dFN2");
	            datab.put("lmgp2dFN2", lmgp2dFN2.toString());

				out.println("</TR>\n");

				out.println("<td height=\"25\" "+Color+"\" width=\"5%\" ALIGN=\"CENTER\" ROWSPAN=\""+lastdate+"\" >"+Item_Id+"</td>\n");
				out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"temp"+prnumber+itemid+"\"  value=\""+temp+"\" maxlength=\"40\" size=\"10\"></td>\n");
				if (!"2117".equalsIgnoreCase(Hub_Value))
				{
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part1"+prnumber+itemid+"\"  value=\""+part1+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part2"+prnumber+itemid+"\"  value=\""+part2+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part3"+prnumber+itemid+"\"  value=\""+part3+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part4"+prnumber+itemid+"\"  value=\""+part4+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part5"+prnumber+itemid+"\"  value=\""+part5+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part6"+prnumber+itemid+"\"  value=\""+part6+"\" maxlength=\"40\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"part7"+prnumber+itemid+"\"  value=\""+part7+"\" maxlength=\"40\" size=\"10\"></td>\n");
				}
                
                else
				{
					//out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"color"+prnumber+itemid+"\"  value=\""+color+"\" maxlength=\"30\" size=\"10\"></td>\n");
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\" ROWSPAN=\""+lastdate+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"freeText"+prnumber+itemid+"\"  value=\""+freeText+"\" maxlength=\"30\" size=\"10\"></td>\n");
				}

				out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+receiptid+"</td>\n");
				if ( "01/01/3000".equalsIgnoreCase(expireDate) )
				{
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\">INDEFINITE</td>\n");
				}
				else
				{
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+expireDate+"</td>\n");
				}
				if (!"2117".equalsIgnoreCase(Hub_Value))
				{
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"recert"+lineadded+"\"  value=\""+recert.trim()+"\" maxlength=\"10\" size=\"5\"></td>\n");
				}
				if ("2118".equalsIgnoreCase(hubNoforlabel))
				{
					out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qcDoc"+lineadded+"\"  value=\""+qcDoc.trim()+"\" size=\"15\"></td>\n");
				}
                 if (lastIdPrinted.trim().length() > 0)
                {
                    out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"checkContainerIdValue('"+lineadded+"','"+prnumber+itemid+"')\" name=\"rePrintContainerId"+prnumber+itemid+"\"  value=\"\" maxlength=\"10\" size=\"3\"></td>\n");
                }
                else
                {
                    out.println("<td "+Color+"\" width=\"5%\" height=\"38\"></td>\n");
                }
                out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"ChecktransQtyValue('"+lineadded+"')\" name=\"qty_picked"+lineadded+"\"  value=\""+qtypicked+"\" maxlength=\"10\" size=\"3\"></td>\n");

                out.println("</TR>\n");

				dataV.put("QUANTITY_RECEIVED",qtypicked);
				dataV.put("DATA",datab);
				dataV1.addElement(dataV);
			}

			out.println("</table>\n");
			//
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<tr>");
			out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
			out.println("</TD></tr>");
			out.println("</table>\n");

			out.println("<input type=\"hidden\" name=\"Total\" value=\""+lineadded+"\">\n");

			sessiondetails.setAttribute("PRINTBARCODEDATA", dataV1 );
			sessiondetails.setAttribute("PRINTCOMPDATA", finalcompdata );
			sessiondetails.setAttribute("PRINTINVGRPDATA", invengrpvec );

			datab = null;
			dataV = null;
			dataV1 = null;

			out.println("</form>\n");
			out.println("</body></html>\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Re-Print Labels",intcmIsApplication));
		}

		datab=null;
		dataV=null;
		dataV1=null;
		comptdataV1=null;
		compdata=null;
		finalcompdata=null;
		invengrpvec=null;

		return;
	}
}