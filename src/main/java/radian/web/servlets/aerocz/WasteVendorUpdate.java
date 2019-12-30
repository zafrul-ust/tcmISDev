package radian.web.servlets.aerocz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WasteOrder;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WasteVendorUpdate
{
		private ServerResourceBundle bundle=null;
		private TcmISDBModel db = null;
		//Initialize global variables
		private Vector data = new Vector();
		private Vector data1 = null;
		private Vector data2 = null;
		private Vector datam = new Vector();
		private PrintWriter out = null;
		private String order_no = "";
		private String actual_pickup_date = "";
		private String disabeled = "";
		private String borc = "";
		private String Logon_Id = "";
		private String userId = "";
		private String Shp_id = "";
		private String session = null;
		private String order_text = "";
		private String order_Drop = "";
		private String order_Form = "";
		private String msg = "";
		private String vendor_shp_id = "";
		private String plan_shp_dt = "";
		private String actual_shp_dt = "";
		private String org_submit_dt = "";
		private String resubmit_dt = "";
		private String cancel_dt = "";
		private String company_name = "";
		private String passed = null;
		private String Pswd = "N";
		private String UpdateShip_Id = "";
		private String Vendor_Id = null;
		private String Status = "";
		private String open_order = "";
		private String old_order = "";
		private String type = "";
		private String session1 = "";
		private String store_loc = "";
		private String fullname = "";
		private String phone = "";
		private String email = "";
		private int recs = 0;
		private String vendorfacid = "";
		private String preferedservdate = "";
		private Hashtable result = new Hashtable();
		private Hashtable resultm = new Hashtable();
		private String vendor_order_no = "";
		private String planned_pickup_date = "";
		private String preffered_pickup_date = "";
		private Vector data3 = null;
		private boolean HasVendor = true;
		private boolean CheckPass = false;
		private boolean gettingPass = false;
		private boolean gettingHTML = false;
		private boolean checkpswd = false;
		private boolean gettingShpDetail = false;
		private boolean gettingUpdate = false;
		private boolean UpdateDone = true;

		public WasteVendorUpdate(ServerResourceBundle b, TcmISDBModel d)
		{
				bundle = b;
				db = d;
		}
		//Process the HTTP Get request
		public void doGet(HttpServletRequest request,
											HttpServletResponse response) throws ServletException,
				IOException
		{
				doPost(request,response);
		}

		//Process the HTTP Post request
		public void doPost(HttpServletRequest request,
											 HttpServletResponse response) throws ServletException,
				IOException
		{

				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				HttpSession Session = request.getSession();
				Hashtable tmp1 = new Hashtable();
				//Logon_Id = "nawaz"; //for local testing
				Logon_Id = BothHelpObjs.makeBlankFromNull((String)Session.getValue("loginId"));
				Status = BothHelpObjs.makeBlankFromNull((String)Session.getValue("SESSION"));

				if ((Status.equalsIgnoreCase("0")))
				{

						tmp1 = (Hashtable)Session.getValue("ALL_I_NEED");
						order_no = tmp1.get("ORDER_NO").toString();
						Shp_id = tmp1.get("SHP_ID").toString().trim();
						borc =  tmp1.get("BORC").toString().trim();;
						try
						{
								WasteOrder wo = new WasteOrder(db);
								data = wo.getAcessParam(Logon_Id);
						}
						catch (Exception e)
						{
								System.out.println("*** Error on open DB on Getting Open Orders ***");
						}

						if (data.size() > 0)
						{
								buildShpDetail(out);
						}
						else
						{
								msg +="<HTML><HEAD><TITLE>Waste Orders - Login</TITLE></HEAD>\n";
								msg +="<BODY>\n";
								msg +="<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n"+
										"&nbsp;&nbsp;&nbsp;"+
										"<BR><P></P>";
								msg += "<FONT FACE=\"Arial\" SIZE=3><b>Error You don't have access to this module.<br>\n";
								msg +="<p>Please contact your supervisor</P></B></FONT>\n";
								msg +="</BODY>\n";
								out.println(msg);
								return;
						}
				}

				// Session = 2 MEANS THE REQUEST IS COMING FROM THE SEARCH BUTTOM ON THE PAGE.
				if ((Status.equalsIgnoreCase("2")))
				{
						tmp1 = (Hashtable)Session.getValue("ALL_I_NEED");
						//System.out.println("the servlet "+tmp1);
						open_order = tmp1.get("OPEN_ORDER").toString().trim();
						old_order = tmp1.get("OLD_ORDER").toString().trim();
						order_text = tmp1.get("OLD_ORDER_DAYS").toString().trim();
						Vendor_Id  = tmp1.get("VENDOR_ID").toString().trim();

						if (!open_order.equalsIgnoreCase("openorders") && !old_order.equalsIgnoreCase("oldorders"))
						{
						open_order = "openorders";
						}
						buildWasteDetail(out);
				}
				// Session = 4 means the request is for Updateing the records.
				if (Status.equalsIgnoreCase("4"))
				{
						tmp1 = (Hashtable)Session.getValue("ALL_I_NEED");
						vendor_shp_id = tmp1.get("VENDOR_SHP_ID").toString().trim();
						plan_shp_dt = tmp1.get("PLAN_SHP_DT").toString().trim();
						borc =  tmp1.get("BORC").toString().trim();;
						Shp_id = tmp1.get("SHP_ID").toString().trim();
						order_no = tmp1.get("ORDER_NO").toString();
						//System.out.println(" This  is the update page : "+vendor_shp_id+"  "+plan_shp_dt+"  "+Shp_id);
						buildUpdate(out);
				}

				out.close();
				//System.out.println("*** The Order Number ***:"+order_no+"");
				//System.out.println("*** Session is  ***:" +Status+"");
		}

		public void buildWasteDetail(PrintWriter out)
		{
				String header = "";
				String store_loc = "";
				String fullname = "";
				String phone = "";
				String email = "";
				String vendorfacid = "";
				String preferedservdate = "";
				String checkedol = "";
				String checkedop = "";
				String oldnodaysi = "30";

				if (open_order.equalsIgnoreCase("openorders"))
				{
						checkedop = "checked";
				}
				if (old_order.equalsIgnoreCase("oldorders"))
				{
						checkedol = "checked";
						oldnodaysi = order_text;
				}

				try
				{
						WasteOrder wo = new WasteOrder(db);
						HasVendor = wo.IsinVendorLogin(Logon_Id,Vendor_Id);
						data1 = wo.getAcessParam(Logon_Id);
				}
				catch (Exception e)
				{
						e.printStackTrace();
						System.out.println("*** Error on open DB on Getting Open Orders *** 226");
				}

				header +="<HTML><HEAd><TITLE>Waste Orders</TITLE>              \n"+
						"<SCRIPT LANGUAGE=\"JavaScript\">\n"+
						 "<!-- \n"+
						"function setField1(form)\n"+
						"{\n"+
						"  form.numberofdays.checked = true;\n"+
						"}\n"+
						"//-->     \n"+
						"</SCRIPT></HEAD><BODY>  \n";
				header +="<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n"+
										"&nbsp;&nbsp;&nbsp;\n"+
										"<FONT FACE=\"Arial\" SIZE=5 COLOR=\"#000080\"><B>\n"+
										"Waste Orders\n"+
										"</B></FONT>\n";
				String selected = "selected";

				String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");

			 header += "<TABLE BORDER=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\">\n"+
								 "<TR>\n"+
								 "<form method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=2\">\n"+
								 "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n"+
								 "<FONT SIZE=\"3\" FACE=\"Arial\"><B>Waste Vendor:  </B></FONT></TD>\n"+
								 "<TD WIDTH=\"35%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n"+
								 "<FONT SIZE=\"3\" FACE=\"Arial\"><SELECT name=\"vendor_id\" SIZE=\"1\">\n"+
								 "  <OPTION value=\"None\">Please Select The Vendor</OPTION>\n";

									Hashtable hD3 = null;
									for (int i=0;i<data1.size(); i++)
									{
											hD3 = (Hashtable) data1.elementAt(i);
											String Vnedor_Name = (hD3.get("VENDOR_NAME")==null?"":hD3.get("VENDOR_NAME").toString());
											String Vnedor_Id1 = (hD3.get("VENDOR_ID")==null?"":hD3.get("VENDOR_ID").toString());

											//System.out.println("*** The vendor Id's are "+Vendor_Id+" and from select "+Vnedor_Id1+" ***");

											if (!(Vnedor_Id1.equalsIgnoreCase(Vendor_Id)))
											{
													selected = "";
											}
											header +="  <OPTION "+selected+" Value=\""+Vnedor_Id1+"\">"+Vnedor_Name+"</OPTION>\n";
											selected = "selected";
									}

				header +="</SELECT>\n"+
								 "</FONT></TD>\n"+
								 "<TD WIDTH=\"22%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n"+
								 "<INPUT type=\"checkbox\" name=\"openorders\" value=\"openorders\" "+checkedop+">\n"+
								 "&nbsp;&nbsp;<FONT SIZE=\"3\" FACE=\"Arial\">Open Orders only</FONT>\n"+
								 "<BR><BR>\n"+
								 "<INPUT type=\"checkbox\" name=\"numberofdays\" value=\"oldorders\" "+checkedol+">\n"+
								 "&nbsp;&nbsp;<FONT SIZE=\"3\" FACE=\"Arial\">Closed Within &nbsp;&nbsp;<INPUT type=\"text\" name=\"nodaysold\" value=\""+(checkedol==null?"30":oldnodaysi)+"\" onClick=\"setField1(this.form)\" SIZE=\"2\">\n"+
								 "</FONT></TD>\n"+
								 "<TD WIDTH=\"28%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" HEIGHT=110>\n"+
								 "<INPUT type=\"submit\" value=\"Search\" name=\"SearchButton\">\n"+
								 "<INPUT type=\"hidden\" name=\"order_Form\" value=\"1\"> \n"+
								 "<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n"+
								 "</TD>\n"+
								 "</form>\n"+
								 "</TR>\n"+
								 "</TABLE>\n"+

			/*  "<TABLE BORDER=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" >\n"+
						"<TR>\n"+
						"<TD VALIGN=\"MIDDLE\" ROWSPAN=\"3\" WIDTH=\"20%\">\n"+
						"<div align=right><b>Waste Vendor:  </B></div>\n"+
						" </td>\n"+

						" <TD  VALIGN=\"MIDDLE\" ROWSPAN=\"3\" WIDTH=\"28%\">\n"+
						"  <SELECT name=\"vendor_id\" SIZE=\"1\">\n"+
						"  <OPTION value=\"None\">Please Select The Vendor</OPTION>\n";

				Hashtable hD3 = null;
				for (int i=0;i<data1.size(); i++)
				{
						hD3 = (Hashtable) data1.elementAt(i);
						String Vnedor_Name = (hD3.get("VENDOR_NAME")==null?"":hD3.get("VENDOR_NAME").toString());
						String Vnedor_Id1 = (hD3.get("VENDOR_ID")==null?"":hD3.get("VENDOR_ID").toString());

						System.out.println("*** The vendor Id's are "+Vendor_Id+" and from select "+Vnedor_Id1+" ***");

						if (!(Vnedor_Id1.equalsIgnoreCase(Vendor_Id)))
						{
								selected = "";
						}
						header +="  <OPTION "+selected+" Value=\""+Vnedor_Id1+"\">"+Vnedor_Name+"</OPTION>\n";
						selected = "selected";
				}

				header +="</SELECT>\n"+
						"</td>\n"+
						"<TD  VALIGN=\"MIDDLE\" WIDTH=\"5%\" >\n"+
						"<div align=right><B><INPUT type=\"checkbox\" name=\"openorders\" value=\"openorders\" "+checkedop+"></B></div></td>\n"+
						"<TD  VALIGN=\"MIDDLE\" WIDTH=\"20%\">\n"+
						"<INPUT type=\"hidden\" name=\"order_Form\" value=\"1\"> \n"+
						"<INPUT type=\"hidden\" name=\"Session\" value=\"2\">"+
						"<div align=left>&nbsp;&nbsp;Open Orders only</div>\n";
				header +="</td>\n"+
						"<TD  VALIGN=\"MIDDLE\" ROWSPAN=\"4\" WIDTH=\"5%\" >\n"+
						"<INPUT type=\"submit\" value=\"Search\" name=\"SearchButton\">\n"+
						"</td>\n"+
						"</TR>\n"+
						"<TR>\n"+
						"<TD VALIGN=\"MIDDLE\" WIDTH=\"20%\" COLSPAN=\"3\"><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"+
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</B></td>\n"+
						"</TR>\n"+
						// Closed within N number of days
						"<TR>\n"+
						"<TD VALIGN=\"MIDDLE\" WIDTH=\"5%\">\n"+
						"<div align=right><B><INPUT type=\"checkbox\" name=\"numberofdays\" value=\"oldorders\" "+checkedol+"></B></div></td>\n"+
						"<TD VALIGN=\"MIDDLE\" WIDTH=\"20%\">\n"+
						"&nbsp;&nbsp;Closed Within &nbsp;&nbsp;<INPUT type=\"text\" name=\"nodaysold\" value=\""+(checkedol==null?"30":oldnodaysi)+"\" onClick=\"setField1(this.form)\" SIZE=\"2\">\n"+
						"&nbsp; Days</td>\n"+
						"</TR>\n"+
						"</TABLE></form>*/
						"<HR width=\"100%\" align=\"left\">\n";

				String details = buildDetail(Vendor_Id,open_order,
																		 old_order,order_text);
				header += details;
				out.println(header);
		}

	protected void printHtmlNoData(PrintWriter out, String vendor_id)
		{
String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");

				msg += "<HTML><HEAD><TITLE>Waste Order - No Records</TITLE><HEAD>";
				msg +="<BODY>\n";
				msg +="<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n"+
										"&nbsp;&nbsp;&nbsp;\n"+
										"<FONT FACE=\"Arial\" SIZE=4 COLOR=\"#000080\"><B>\n"+
										"Waste Order Detail for Order : "+order_no+"-"+Shp_id+" \n"+
										"</B></FONT>\n"+
										"<P> </P>\n";
				msg += "<FONT SIZE=\"3\" FACE=\"Arial\"><B> No records found. </B></FONT>";
				msg += "<FONT SIZE=\"3\" FACE=\"Arial\"><B> You can go to the All Orders Page by Clicking Here</B></FONT>";
				msg += "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
				msg += "<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n";
				msg += "<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n";
				msg += "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+vendor_id+"\">\n";
				msg += "<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n";
				msg += "<HR width=\"95%\" align=\"left\">\n";
				msg += "</BODY></HTML>";
				out.println(msg);
				//out.close();
		}

		public void  buildShpDetail (PrintWriter out)
		{
				data = null;
				boolean Lab_Pack = false;
				boolean Waste_material = false;
				String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");

				if(!order_no.equalsIgnoreCase("0") && !Shp_id.equalsIgnoreCase("0"))
				{
						try
						{
								WasteOrder wo = new WasteOrder(db);
								Vendor_Id = wo.getVendorId(order_no);
								HasVendor = wo.IsinVendorLogin(Logon_Id, Vendor_Id);
								result = wo.getshpidDetail(order_no, Shp_id);
								data2 = wo.getOrderHeader(order_no);
								data3 = wo.getwasteorderDetail(order_no, Shp_id);


								String  where = "Select * from Waste_order_item where order_no = "+order_no+" and shipment_id = "+Shp_id+"";
								int count = DbHelpers.countQuery(db,where);

										if (count > 0)
										{
										 Waste_material = true;
										}
							 String  where1 = "Select lab_pack_drum_estimate, lab_pack_preferred_service_dat from Waste_shipment where order_no = "+order_no+"";
							 int count1 = DbHelpers.countQuery(db,where1);
										if (count1 >0)
										{
										 Lab_Pack = true;
										}

								String NumRecs = result.get("Number").toString();
								//System.out.println("**Shipment Id :-) *****"+type+"    "+count+"    "+count1+"     "+NumRecs);
								if ( (NumRecs.equalsIgnoreCase("0")) && ((!(Waste_material)) && (!(Lab_Pack))) )
								{
										printHtmlNoData(out,Vendor_Id);
										return;
								}
						}
						catch(Exception e)
						{
								e.printStackTrace();
								System.out.println("*** Error on open DB for SearchWaste ***");
								return;
						}
						if ((HasVendor))
						{
								Hashtable hD = null;
								Hashtable hD4 = null;
								Hashtable hD5 = null;
								if (UpdateDone)
								{
										msg +="<HTML><HEAD><TITLE>Waste Order Detail</TITLE>\n"+
												"<SCRIPT LANGUAGE=\"JavaScript\">\n"+
												"<!-- \n"+
												"function DoLoad()\n"+
												"{ \n"+
												"alert(\"Your update request was successful. \");\n"+
												"return;\n"+
												"} \n"+
												"function changed(form) {\n"+
												"document.UpdateForm.UpdateButton.disabled=false;\n"+
												"} \n"+
												"//-->     \n"+
												"</SCRIPT></HEAD>  \n";
										if (Status.equalsIgnoreCase("4"))
										{
												msg +="<BODY onLoad=\"javascript:DoLoad()\">\n";
										}
										else
										{
												msg +="<BODY>\n";
										}
								}
								else
								{
										msg +="<HTML><HEAD><TITLE>Waste Orders</TITLE>\n"+
												"<SCRIPT LANGUAGE=\"JavaScript\">\n"+
												"<!-- \n"+
												"function DoLoad()\n"+
												"{ \n"+
												"alert(\"Error please check your input and try again.\\nCould not update value. \");\n"+
												"return;\n"+
												"} \n"+
												"function changed(form) {\n"+
												"document.UpdateForm.UpdateButton.disabled=false;\n"+
												"} \n"+
												"//-->     \n"+
												"</SCRIPT></HEAD>  \n"+
												"<BODY onLoad=\"javascript:DoLoad()\">\n";
								}
								msg +="<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n"+
										"&nbsp;&nbsp;&nbsp;\n"+
										"<FONT FACE=\"Arial\" SIZE=5 COLOR=\"#000080\"><B>\n"+
										"Waste Order Detail for Order : "+order_no+"-"+Shp_id+" \n"+
										"</B></FONT>\n"+
										"<P> </P>\n";
								//System.out.println("*** Error 431***");
								try
								{
								hD4 = (Hashtable) data2.elementAt(0);
								store_loc = hD4.get("STORAGE_LOCATION")==null?"":hD4.get("STORAGE_LOCATION").toString();
								fullname = hD4.get("FULL_NAME")==null?"":hD4.get("FULL_NAME").toString();
								phone = hD4.get("PHONE")==null?"":hD4.get("PHONE").toString();
								email = hD4.get("EMAIL")==null?"":hD4.get("EMAIL").toString();
								vendorfacid = hD4.get("VENDOR_FACILITY_ID")==null?"":hD4.get("VENDOR_FACILITY_ID").toString();
								org_submit_dt = (hD4.get("ORIGINAL_SUBMIT_DATE")==null?"&nbsp;":hD4.get("ORIGINAL_SUBMIT_DATE").toString());
								resubmit_dt = (hD4.get("RESUBMIT_DATE")==null?"&nbsp;":hD4.get("RESUBMIT_DATE").toString());

								hD5 = (Hashtable) data3.elementAt(0);
								vendor_order_no = (hD5.get("VENDOR_SHIPMENT_ID")==null?"":hD5.get("VENDOR_SHIPMENT_ID").toString());
								planned_pickup_date = (hD5.get("PLANNED_SHIP_DATE")==null?"":hD5.get("PLANNED_SHIP_DATE").toString());
								actual_pickup_date = (hD5.get("ACTUAL_SHIP_DATE")==null?"&nbsp;":hD5.get("ACTUAL_SHIP_DATE").toString());
								preffered_pickup_date = (hD5.get("PREFERRED_SERVICE_DATE")==null?"&nbsp;":hD5.get("PREFERRED_SERVICE_DATE").toString());
								disabeled = "disabled ";
								}
								catch (Exception e)
								{
								e.printStackTrace();
								}

								//System.out.println("*** Error 455***");


								msg +="<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n"+
										"<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n"+
										"<TR>\n"+
										"<TD VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" WIDTH=\"40%\" HEIGHT=\"24\"><FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>General Information : </B>\n"+
										"</FONT></TD>\n"+
										"<TD VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" WIDTH=\"30%\" HEIGHT=\"24\">\n"+
										"<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n"+
										"<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n"+
										"<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n"+
										"<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"></TD> </FORM>\n"+

										"<TD VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" WIDTH=\"30%\" HEIGHT=\"24\">\n"+
										"<FORM method=\"POST\" name=\"UpdateForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=4\">\n"+
										"<INPUT type=\"submit\" value=\"Update\" name=\"UpdateButton\" "+disabeled+"> \n"+
										"<INPUT type=\"hidden\" name=\"order_no\" value=\""+order_no+"\">\n"+
										"<INPUT type=\"hidden\" name=\"Session\" value=\"4\">\n"+
										"<INPUT type=\"hidden\" name=\"Shp_id\" value=\""+Shp_id+"\">\n"+
										"<INPUT type=\"hidden\" name=\"borc\" value=\""+borc+"\">\n"+
										"</TD></TR></TABLE>\n"+

										"<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Facility :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendorfacid+"</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Vendor Order No :</B></FONT></div>\n"+
										"</td>\n";
								if (actual_pickup_date.length() == 10)
								{
										msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendor_order_no+"</FONT></td>\n";
								}
								else
								{
										msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\"><INPUT type=\"text\" name=\"vendor_shp_id\" value=\""+(vendor_order_no==null?null:vendor_order_no)+"\" SIZE=\"10\" onfocus=\"changed(this.form)\"     ></FONT></td>\n";
								}
								msg +="</TR>\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Storage Area :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+store_loc+"</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Original Submittal Date :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+org_submit_dt+"</FONT></td>\n"+
										"</TR>\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requestor :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+fullname+"</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Latest Re-submittal Date :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+resubmit_dt+"</FONT></td>\n"+
										"</TR>\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+phone+"</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Planned Pickup Date (mm/dd/yyyy) :</B></FONT></div>\n"+
										"</td>\n";
								if (actual_pickup_date.length() == 10)
								{
										msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+planned_pickup_date+"</FONT></td>\n";
								}
								else
								{
										msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\"><INPUT type=\"text\" name=\"plan_shp_dt\" value=\""+(planned_pickup_date==null?null:planned_pickup_date)+"\" SIZE=\"10\" onfocus=\"changed(this.form)\"     ></FONT></td>\n";
								}
								msg +="</TR>\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Email :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+email+"</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Preferred Pickup Date :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+preffered_pickup_date+"</FONT></td>\n"+
										"</TR>\n"+
										"<TR>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>&nbsp;</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">&nbsp;</FONT></td>\n"+
										"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
										"<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Actual Pickup Date :</B></FONT></div>\n"+
										"</td>\n"+
										"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+actual_pickup_date+"</FONT></td>\n"+
										"</TR>\n"+
										"</TABLE>\n"+
										"</FORM><HR width=\"95%\" align=\"left\"><BR>\n";

								try
								{
										data = (Vector) result.get("Pickup");
								}
								catch (Exception e)
								{
										e.printStackTrace();
										System.out.println("*** Error 181 or 2***");
										return;
								}
								if (data.size() >0 )
								{
										if ( borc.length() == 0)
										{
												WasteOrder wo = new WasteOrder(db);
												try
												{
														//System.out.println("*** Bulk or Container ***");
														borc = wo.Bulk_or_Container(order_no,Shp_id);
												}
												catch (Exception e)
												{
														e.printStackTrace();
												}
										}
										try
										{
												if (borc.equalsIgnoreCase("C"))
												{
														msg +="<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Container Waste Pickup Detail</B></FONT><BR>\n";
												}
												else if (borc.equalsIgnoreCase("B"))
												{
														msg +="<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Bulk Waste Pickup Detail</B></FONT><BR>\n";
												}
												else
												{
														msg +="<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Waste Pickup Detail</B></FONT><BR>\n";
												}
										}
										catch (Exception e)
										{
												e.printStackTrace();
												msg +="<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Waste Pickup Detail</B></FONT><BR>\n";
										}
										msg +="<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\">\n";

										for (int i=0;i<data.size(); i++)
										{
												if (i%10==0)
												{
														msg +=""+
																"<tr align=\"CENTER\" >                                                                           \n"+
																"  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>VENDOR PROFILE</strong></FONT></td>                \n"+
																"  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong> DESCRIPTION </strong></FONT></td>                    \n"+
																"  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>PACKAGING </strong></FONT></td>                \n"+
																"  <TD bgcolor=\"#B0BFD0\" width=\"15%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>QUANTITY</strong></FONT></td>        \n"+
																"  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>STATE WASTE CODE</strong></FONT></td>        \n"+
																"</TR>";
												}
												hD = (Hashtable) data.elementAt(i);
												// get main information
												String vendor_profile_id = (hD.get("VENDOR_PROFILE_ID")==null?"&nbsp;":hD.get("VENDOR_PROFILE_ID").toString());
												String description = (hD.get("DESCRIPTION")==null?"&nbsp;":hD.get("DESCRIPTION").toString());
												String packaging = (hD.get("PACKAGING")==null?"&nbsp;":hD.get("PACKAGING").toString());
												String quantity = (hD.get("QUANTITY")==null?"&nbsp;":hD.get("QUANTITY").toString());
												String statecode = (hD.get("STATE_WASTE_CODE")==null?"&nbsp;":hD.get("STATE_WASTE_CODE").toString());

												msg +="<tr align=\"CENTER\">\n";
												msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendor_profile_id+"</FONT></td>\n";
												msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+description+"</FONT></td>\n";
												msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+packaging+"</FONT></td>\n";
												msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"15%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+quantity+"</FONT></td>\n";
												msg +="<TD VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+statecode+"</FONT></td>\n";
												msg +="</TR>\n";
										}

										msg +="</TABLE><BR>";
								}

								//String  where = "Select * from Waste_order_item where order_no = "+order_no+" and shipment_id = "+Shp_id+"";
								try
								{
										//if ((DbHelpers.countQuery(db,where) > 0))
										if (Waste_material)
										{
												WasteOrder wo = new WasteOrder(db);
												resultm = wo.materialdetails(order_no,Shp_id);
												//System.out.println("**** Done Getting Material Detail for Shipment Id 235:-) ***************");

												datam =  (Vector) resultm.get("Material");
												if (datam.size() >0 )
												{
														msg +="<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Material Dropoff Detail</B></FONT><BR>\n";

														msg +="<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\">\n";

														for (int i=0;i<datam.size(); i++)
														{
																if (i%10==0)
																{
																		msg +=""+
																				"<tr align=\"CENTER\" >                                                                           \n"+
																				"  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>VENDOR PROFILE</strong></FONT></td>                \n"+
																				"  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong> DESCRIPTION </strong></FONT></td>                    \n"+
																				"  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>PACKAGING </strong></FONT></td>                \n"+
																				"  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>QUANTITY</strong></FONT></td>        \n"+
																				"</TR>";
																}
																hD = (Hashtable) datam.elementAt(i);
																// get main information
																String vendor_profile_id = (hD.get("VENDOR_PROFILE_ID")==null?"&nbsp;":hD.get("VENDOR_PROFILE_ID").toString());
																String description = (hD.get("DESCRIPTION")==null?"&nbsp;":hD.get("DESCRIPTION").toString());
																String packaging = (hD.get("PACKAGING")==null?"&nbsp;":hD.get("PACKAGING").toString());
																String quantity = (hD.get("QUANTITY")==null?"&nbsp;":hD.get("QUANTITY").toString());

																msg +="<tr align=\"CENTER\">\n";
																msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendor_profile_id+"</FONT></td>\n";
																msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+description+"</FONT></td>\n";
																msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+packaging+"</FONT></td>\n";
																msg +="<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+quantity+"</FONT></td>\n";
																msg +="</TR>\n";
														}
												}
												msg +="</TABLE><BR>";
										}
								}
								catch (Exception e)
								{
										System.out.println("*******************103");
										e.printStackTrace();
								}

								//String  where1 = "Select lab_pack_drum_estimate, lab_pack_preferred_service_dat from Waste_shipment where order_no = "+order_no+"";
								try
								{
										//if ((DbHelpers.countQuery(db,where1) >0))
										if (Lab_Pack)
										{
												WasteOrder wo = new WasteOrder(db);
												Vector labdate = new Vector();
												String msg2 ="";
												try
												{
														labdate = wo.getlacpackdate(order_no);
												}
												catch (Exception e)
												{
														e.printStackTrace();
												}
												msg +="<BR><FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Labpack Service Request</B></FONT><BR><BR>\n";
												msg +="<FONT FACE=\"Arial\" SIZE=\"2\"><B>Original Submittal Date : </B>"+org_submit_dt+"<BR><BR>";
												msg +="<B>Labpack Preferred Service Date : </B>"+labdate.elementAt(0)+"<BR><BR>";
												msg +="<B>Labpack Estimated Drums : </B>"+labdate.elementAt(1)+"<BR> </FONT><BR>";
										}
								}
								catch (Exception e)
								{
										System.out.println("*******************674");
										e.printStackTrace();
								}
								finally
								{
										//db.close();
								}
								if (Status.equalsIgnoreCase("4"))
								{
										if (UpdateDone)
										{
												msg += "<BR><FONT FACE=\"Arial\" SIZE=3>The record with Order # <B><FONT COLOR=\"#FF0000\">"+order_no+"-"+UpdateShip_Id+"</FONT></B> has been updated and a email was sent to the requestor </FONT><BR> \n";
										}
										else
										{
												msg += "<BR><FONT FACE=\"Arial\" SIZE=2><B>Error please check your input and try again<BR>\n";
												msg += "Make sure there are no blank spaces in the Planned Shipment Date and the Vendor Order No is an integer.</B></FONT><BR>\n";
										}
								}

								msg +="<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
								msg += "<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n";
								msg += "<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n";
								msg += "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n";
								msg += "<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n";
								msg +="<HR width=\"95%\" align=\"left\">";

								if (Vendor_Id.equalsIgnoreCase("MAD980523203"))
								{
								 msg +="<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Basic Ordering Agreement for Services\", between<BR>Radian  \n"+
										"International and Supplier with the Effective Date of January 24, 2000, are incorporated into the<BR>Order, by this reference, to the same effect  \n"+
										"as if such terms and conditions were printed in their<BR> entirety on the face of this Order.</FONT>";
								}
								else if (Vendor_Id.equalsIgnoreCase("ARD069748192"))
								{
								 msg +="<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Total Chemical Management Hazardous Waste Transportation and Disposal Services Contract\" \n"+
								 "between Radian International and Supplier with the Effective Date of March 29, 2000, are incorporated into the Order, by this reference, to the same effect as if such terms and  \n"+
								 "conditions were printed in their entirety on the face of this Order.</FONT>";
								}

								msg +="</BODY></HTML>";
								out.println(msg);
						}
						else
						{
								msg = "<HTML>\n";
								msg += "<HEAd>\n" +
										"<TITLE>Waste Update - No Access</TITLE>\n"+
										"</HEAd>\n"+
										"<BODY>\n";
								msg += "<CENTER><FONT FACE=\"Arial\" SIZE=3><P><B>You Don't have access to this record.</B></P></FONT></CENTER>\n"+
										"</BODY>\n"+
										"</HTML>\n";
								out.println(msg);
						}
				}
				 else
				{
						msg += "<CENTER><FONT FACE=\"Arial\" SIZE=3><P><B>No Records Found.<BR><BR>\n Please Check Your URL and Try Again, This Page Has Changed Recently<BR><BR>\n</B></P></FONT></CENTER>\n";
						msg += "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
						msg += "<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n";
						msg += "<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n";
						msg += "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n";
						msg += "<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n";
						msg += "<HR width=\"95%\" align=\"left\">\n";
						msg += "</FONT></BODY>\n";
						msg += "</HTML>\n";
						out.println(msg);
				}
				//db.close();
				//out.close();
		}
		public void buildUpdate(PrintWriter out)
		{
				String headeru = "";
				WasteOrder wo = new WasteOrder(db);
				Date CurrentDate = new Date();
				try
				{
						Vendor_Id = wo.getVendorId(order_no);
						HasVendor = wo.IsinVendorLogin(Logon_Id,Vendor_Id);
				}
				catch (Exception e)
				{
						e.printStackTrace();
				}
				if (HasVendor)
				{
						try
						{
								/*System.out.println("686");
								int testint = Integer.parseInt(vendor_shp_id);
								System.out.println("The integer value of vendor_id id "+testint);*/
						}
						catch (Exception e)
						{
								e.printStackTrace();
								UpdateDone = false;
								buildShpDetail(out);
								return;
						}

						if (vendor_shp_id.length() > 0 )
						{
								if (plan_shp_dt.length() == 10)
								{
										//For Java 1.18 on condor The below code in Java 1.2 Does not work
										Date d = new Date();
										Calendar cal = Calendar.getInstance();
										String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));

										//System.out.println("\n The Date today is "+ cdate +"");
										try
										{
												//System.out.println("\nThe Dates are  - Today"+ d.parse(cdate)+"     Planned Shipment"+d.parse(plan_shp_dt)+ "");
										}
										catch (Exception e)
										{
												e.printStackTrace();
												UpdateDone = false;
												buildShpDetail(out);
												return;
										}
										long test = (d.parse(cdate))-(d.parse(plan_shp_dt));
										try
										{
												if ( d.parse(cdate) < d.parse(plan_shp_dt))
												{
														wo.insVendorshpId(db,
																							vendor_shp_id,
																							plan_shp_dt,order_no,Shp_id);
														//System.out.println("**** Done Doing Update :-) ***************");
														UpdateDone = true;
												}
												else
												{
														//System.out.println("*** Error The Date is before Today or Wrong ***");
														UpdateDone = false;
														buildShpDetail(out);
														return;
												}
										}
										catch (Exception e)
										{
												e.printStackTrace();
												UpdateDone = false;
												buildShpDetail(out);
												return;
										}
								}
								else
								{
										if (plan_shp_dt.length() == 0)
										{
												try
												{
														String plan_shp_dt1 = "null";
														wo.insVendorshpId(db,
																							vendor_shp_id,
																							plan_shp_dt1,order_no,Shp_id);
														UpdateDone = true;
														//System.out.println("**** Done Doing Update :-) for planned shipment id ***************");
												}
												catch (Exception e)
												{
														e.printStackTrace();
														UpdateDone = false;
														buildShpDetail(out);
														return;
												}
										}
										else
										{
												UpdateDone = false;
												buildShpDetail(out);
												return;
										}

								}
								// Email
								{
										DBResultSet dbrs = null;
										try
										{
												String logonId = null;
												String Personnel_id = null;
												ResultSet rs = null;
												String query = "select REQUESTER_ID from waste_order where order_no ="+order_no+"";

												dbrs = db.doQuery(query);
												rs=dbrs.getResultSet();

												while (rs.next())
												{
														Personnel_id = (BothHelpObjs.makeBlankFromNull(rs.getString("REQUESTER_ID")));
												}

												query = "select logon_id from personnel where personnel_id ="+Personnel_id+"";

												dbrs = db.doQuery(query);
												rs=dbrs.getResultSet();
												while (rs.next())
												{
														logonId = (BothHelpObjs.makeBlankFromNull(rs.getString("logon_id")));
												}

												String esub = "Your Waste Vendor has updated the planned shipment date.";
												String emsg = "The waste vendor has updated the planned shipment date of your waste pickup request.\n";
												emsg +="The Planned pick up date is now "+plan_shp_dt+" for the shipment Id "+Shp_id+" of the waste order "+order_no+"";

												Integer user = new Integer(Personnel_id);
												HelpObjs.sendMail(db,
																					esub,emsg,user.intValue(),false);

										}
										catch (Exception e)
										{
												e.printStackTrace();
										}
										finally
										{
										dbrs.close();
										}
								}
						}
						else
						{
								UpdateDone = false;
								buildShpDetail(out);
								return;
						}
						UpdateShip_Id = Shp_id;
						buildShpDetail(out);
				}
				else
				{
						UpdateDone = false;
						headeru = "<HTML>\n"+
						 "<HEAd>\n" +
							"<TITLE>Waste Update - No Access</TITLE>\n"+
							"</HEAd>\n"+
							"<BODY>\n";
						headeru += "<FONT FACE=\"Arial\" SIZE=3><B><P><B>Error!! You don't have acess to update this order</B></P>";
						headeru +="</FONT></Body>\n";
						headeru +="</HTML>\n";
						out.println(headeru);
				}
		}
		public String  buildDetail (String Vendor_Id, String open_order,
																String old_order,
																String order_text)
		{
				data = null;
				Vector data_borc = new Vector();
				Vector resultHv = new Vector();
				Hashtable data_dates = new Hashtable();
				Hashtable resultH = new Hashtable();
				Hashtable temp11 = new Hashtable();
				Hashtable temp12 = new Hashtable();
				String checked = "";
				String headerw = "";
				String msgout = "";
				String compName = "";

				if (Vendor_Id.length() <= 1)
				{
						//System.out.println("*******************        437");
						msg += "<center><font face=Arial size=3><p><B>Please select and hit search.</B></P></font></center>\n";
						msgout = headerw+msg;
						return msgout ;
				}
				else
				{
						try
						{
								WasteOrder wo = new WasteOrder(db);
								{
										resultHv = wo.getorderDetail(Vendor_Id,open_order,
																								 old_order,order_text);
								}
								//System.out.println("**** Done Getting Order Detail for Shipment Id :-) ***************");
								recs = resultHv.size();
								if ((recs == 0) || (!(open_order.equalsIgnoreCase("openorders")) && !(old_order.equalsIgnoreCase("oldorders"))))
								{
										msg = "<font face=Arial size=3><BR><p><b><center> No records found for this Search  </center></b></P></font>\n";
										msgout = headerw+msg;
										return msgout;
								}
						}
						catch (Exception e)
						{
								e.printStackTrace();
								System.out.println("*** Error on open DB for  Order Detail***");
								msg = "<font face=Arial size=3><BR><P><b><center> Error Please Check Your Input and Try Again</center></b></P></font>\n";
								msgout = headerw+msg;
								return msgout;
						}

						try
						{
								{
										Hashtable hD = null;
										Hashtable hD2 = null;
										String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");

										String url = new String(""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?");
										String qtmp = new String("");
										msg +="<table BORDER=\"0\" CELLPADDING=\"1\" WIDTH=\"100%\">\n";
										for (int K=0;K<resultHv.size(); K++)
										{
												resultH = (Hashtable) resultHv.elementAt(K);
												temp11 = (Hashtable) resultH.get("SHIP_DETAILS");
												data = (Vector)temp11.get("SHIP_DETAILS_"+K+"");
												if (K%10==0)
												{
														msg +="<font face=Arial size=2>\n"+
																"<tr align=\"center\">                                                                           \n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>ORDER #</font></strong></td>\n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>CONTAINER(C) <B>/</B> BULK(B)</font></strong></td>\n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>VENDOR ORDER NO</font></strong></td>\n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>PREFERRED PICKUP DATE</font></strong></td>\n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>PLANNED PICKUP DATE\n"+
																"  <font face=Arial size=1>(MM/DD/YYYY)</font></font></strong></td>\n"+

																"  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>ACTUAL PICKUP DATE</font></strong></td>\n"+
																"  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>CANCELLED</font></strong></td>\n"+
																"</TR>\n";
												}
												for (int i=0;i<data.size(); i++)
												{
														hD = (Hashtable) data.elementAt(i);
														String Shp_id = (hD.get("SHIPMENT_ID")==null?"&nbsp;":hD.get("SHIPMENT_ID").toString());
														String order_no = (hD.get("ORDER_NO")==null?"&nbsp;":hD.get("ORDER_NO").toString());
														String Shp_idd = order_no+"-"+Shp_id;
														String vendor_shp_id = (hD.get("VENDOR_SHIPMENT_ID")==null?"":hD.get("VENDOR_SHIPMENT_ID").toString());
														String plan_shp_dt = (hD.get("PLANNED_SHIP_DATE")==null?"":hD.get("PLANNED_SHIP_DATE").toString());
														String actual_shp_dt = (hD.get("ACTUAL_SHIP_DATE")==null?"&nbsp;":hD.get("ACTUAL_SHIP_DATE").toString());
														String cancel_dt = (hD.get("CANCEL_DATE")==null?"&nbsp;":hD.get("CANCEL_DATE").toString());
														String BulkorCont = (hD.get("BULK_CONT")==null?"&nbsp;":hD.get("BULK_CONT").toString());
														String preffered_pickup_date = (hD.get("PREFERRED_SERVICE_DATE")==null?"&nbsp;":hD.get("PREFERRED_SERVICE_DATE").toString());
														String Color = "";
														if (i%2==0)
														{
														Color = "bgcolor=\"#dddddd\"";
														}
														qtmp = "order_no="+order_no+"&Shp_id="+Shp_id+"&borc="+BulkorCont+"";
														url += qtmp;
														msg +="<tr align=\"center\">\n";
														msg +="<td "+Color+" width=\"7%\"><a href=\""+url+"\">"+Shp_idd+"</a></td>\n";
														msg +="<td "+Color+" width=\"4%\">"+BulkorCont+"</td>\n";
														msg +="<td "+Color+" width=\"7%\">"+(vendor_shp_id.length() <= 0 ?"&nbsp;":vendor_shp_id)+"</td>\n";
														msg +="<td "+Color+" width=\"10%\">"+preffered_pickup_date+"</td>\n";
														msg +="<td "+Color+" width=\"7%\">"+(plan_shp_dt.length() <= 0 ?"&nbsp;":plan_shp_dt)+"</td>\n";
														msg +="<td "+Color+" width=\"10%\">"+actual_shp_dt+"</td>\n";
														msg +="<td "+Color+" width=\"10%\">"+cancel_dt+"</td>\n";
														msg +="</TR>\n";
														url = ""+WWWHomeDirectory+"/servlet/radian.web.servlets.aerocz.AeroczWasteVendorUpdate?";
														checked = "";
														Shp_id = "";
												}
										}
										msg +="</font></table><BR>\n";
										msg +="<HR>";
										if (Vendor_Id.equalsIgnoreCase("MAD980523203"))
										{
										 msg +="<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"HAAS TCM Basic Ordering Agreement for Services\", between<BR>HAAS  \n"+
												"TCM and Supplier with the Effective Date of January 24, 2000, are incorporated into the<BR>Order, by this reference, to the same effect  \n"+
												"as if such terms and conditions were printed in their<BR> entirety on the face of this Order.</FONT>";
										}
										else if (Vendor_Id.equalsIgnoreCase("ARD069748192"))
										{
										 msg +="<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"HAAS TCM Hazardous Waste Transportation and Disposal Services Contract\" \n"+
										 "between HAAS TCM and Supplier with the Effective Date of March 29, 2000, are incorporated into the Order, by this reference, to the same effect as if such terms and  \n"+
										 "conditions were printed in their entirety on the face of this Order.</FONT>";
										}
										msg +="</Body>\n";
										msg +="</html>\n";

								}

						}
						catch (Exception e)
						{
								e.printStackTrace();
						}

						msgout = headerw+msg;
						return msgout;
				}
		}
}