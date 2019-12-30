package radian.web.servlets.share;

import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpSession;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class projectData
{
  private String CUSTOMER_CONTACT="";
  private String KEYWORDS="";
  private String PROJECT_MANAGER="";
  private String PROJECT_ID="";
  private String PROJECT_NAME="";
  private String PROJECT_DESC="";
  private String PROJECT_CATEGORY="";
  private String PROJECT_MANAGER_ID="";
  private String FACILITY_ID="";
  private String APPROVED_DATE="";
  private String START_DATE="";
  private String PROJECTED_FINISTED_DATE="";
  private String POJECTED_COST="";
  private String ACTUAL_COST="";
  private String PROJECTED_SAVING_YEAR_1="";
  private String PROJECTED_SAVING_YEAR_2="";
  private String PROJECTED_SAVING_YEAR_3="";
  private String PROJECTED_SAVING_YEAR_4="";
  private String PROJECTED_SAVING_YEAR_5="";
  private String ACTUAL_SAVING_YEAR_1="";
  private String ACTUAL_SAVING_YEAR_2="";
  private String ACTUAL_SAVING_YEAR_3="";
  private String ACTUAL_SAVING_YEAR_4="";
  private String ACTUAL_SAVING_YEAR_5="";
  private String COMMENTS="";
  private String BEST_PRACTICE="";
  private String PROJECT_STATUS="";
  private String ACTUAL_FINISH_DATE="";
  private String PRIORITY="";
  private String CUSTOMER_CONTACT_MANAGER="";
  private String CUSTOMER_CONTACT_EMAIL="";
  private String CUSTOMER_CONTACT_PHONE="";
  private String PROJECT_MANAGER_PHONE="";
  private String PROJECT_MANAGER_EMAIL="";
  private String PROJECTED_SAVING_YEAR_TOTAL="";
  private String ACTUAL_SAVING_YEAR_TOTAL="";

    public projectData(Hashtable h,int size,HttpSession tasksession)
    {
	  CUSTOMER_CONTACT="";
	  KEYWORDS="";
	  PROJECT_MANAGER="";
	  PROJECT_ID="";
	  PROJECT_NAME="";
	  PROJECT_DESC="";
	  PROJECT_CATEGORY="";
	  PROJECT_MANAGER_ID="";
	  FACILITY_ID="";
	  APPROVED_DATE="";
	  START_DATE="";
	  PROJECTED_FINISTED_DATE="";
	  POJECTED_COST="";
	  ACTUAL_COST="";
	  /*PROJECTED_SAVING_YEAR_1="";
	  PROJECTED_SAVING_YEAR_2="";
	  PROJECTED_SAVING_YEAR_3="";
	  PROJECTED_SAVING_YEAR_4="";
	  PROJECTED_SAVING_YEAR_5="";
	  ACTUAL_SAVING_YEAR_1="";
	  ACTUAL_SAVING_YEAR_2="";
	  ACTUAL_SAVING_YEAR_3="";
	  ACTUAL_SAVING_YEAR_4="";
	  ACTUAL_SAVING_YEAR_5="";*/
	  COMMENTS="";
	  BEST_PRACTICE="";
	  PROJECT_STATUS="";
	  ACTUAL_FINISH_DATE="";
	  PRIORITY="";
	  CUSTOMER_CONTACT_MANAGER="";
	  CUSTOMER_CONTACT_EMAIL="";
	  CUSTOMER_CONTACT_PHONE="";
	  PROJECT_MANAGER_PHONE="";
	  PROJECT_MANAGER_EMAIL="";
	  PROJECTED_SAVING_YEAR_TOTAL="";
	  ACTUAL_SAVING_YEAR_TOTAL="";

	  Hashtable vvprority= ( Hashtable ) tasksession.getAttribute( "PROJECT_PRIORITY" );
	  Hashtable vvstatus= ( Hashtable ) tasksession.getAttribute( "PROJECT_STATUS" );
	  Hashtable vvtype= ( Hashtable ) tasksession.getAttribute( "PROJECT_CATEGORY" );
	  //Hashtable vvowner= ( Hashtable ) tasksession.getAttribute( "PROJECT_OWNERS" );
	  //Hashtable depdata= ( Hashtable ) tasksession.getAttribute( "PROJECT_FACILITY" );
	  Hashtable projkeywords= ( Hashtable ) tasksession.getAttribute( "PROJECT_KEYWORDS" );

	  for ( int i=0; i < size; i++ )
	  {
		switch ( i )
		{
		  case 0:
			this.CUSTOMER_CONTACT=h.get( "CUSTOMER_CONTACT_ID" ).toString();
			break;
		  case 1:
		  {
			Vector seleckeywords = new Vector();
			seleckeywords = (Vector) h.get( "KEYWORDS" );
			if (seleckeywords == null) {seleckeywords = new Vector();}

			String keywords= "";
			for ( int id=0; id < seleckeywords.size(); id++ )
			{
			  if ( id > 0 )
			  {
				keywords+=",";
			  }
			  String keykey = (String) seleckeywords.get(id);
			  keywords+= projkeywords.get( keykey ).toString();
			}

			/*String tmpkeywords = h.get( "KEYWORDS" ).toString();
			String result6 = "";
			StringTokenizer st=new StringTokenizer( tmpkeywords,"," );

			if ( st.countTokens() > 1 )
			{
			  while ( st.hasMoreTokens() )
			  {
				String tok=st.nextToken();
				result6=result6 + projkeywords.get( tok ).toString() + ",";
			  }
			  result6=result6.substring( 0,result6.length() - 1 );
			}
			else
			{
			  result6= projkeywords.get( tmpkeywords ).toString();
		    }*/
			this.KEYWORDS=keywords;
			break;
		  }
		  case 2:
			this.PROJECT_MANAGER=h.get( "PROJECT_MANAGER" ).toString();
			break;
		  case 3:
			this.PROJECT_ID=h.get( "PROJECT_ID" ).toString();
			break;
		  case 4:
			this.PROJECT_NAME=h.get( "PROJECT_NAME" ).toString();
			break;
		  case 5:
			this.PROJECT_DESC=h.get( "PROJECT_DESC" ).toString();
			break;
		  case 6:
		  {
			String tmptype = h.get( "PRIORITY" ).toString();
			this.PROJECT_CATEGORY=vvtype.get( tmptype ).toString();
			break;
		  }
		  case 7:
			this.PROJECT_MANAGER_ID=h.get( "PROJECT_MANAGER_ID" ).toString();
			break;
		  case 8:
			this.FACILITY_ID=h.get( "FACILITY_ID" ).toString();
			break;
		  case 9:
			this.APPROVED_DATE=h.get( "APPROVED_DATE" ).toString();
			break;
		  case 10:
			this.START_DATE=h.get( "START_DATE" ).toString();
			break;
		  case 11:
			this.PROJECTED_FINISTED_DATE=h.get( "PROJECTED_FINISTED_DATE" ).toString();
			break;
		  case 12:
			this.POJECTED_COST=h.get( "POJECTED_COST" ).toString();
			break;
		  case 13:
			this.ACTUAL_COST=h.get( "ACTUAL_COST" ).toString();
			break;
		  case 14:
			//this.PROJECTED_SAVING_YEAR_1=h.get( "PROJECTED_SAVING_YEAR_1" ).toString();
			break;
		  case 15:
			//this.PROJECTED_SAVING_YEAR_2=h.get( "PROJECTED_SAVING_YEAR_2" ).toString();
			break;
		  case 16:
			//this.PROJECTED_SAVING_YEAR_3=h.get( "PROJECTED_SAVING_YEAR_3" ).toString();
			break;
		  case 17:
			//this.PROJECTED_SAVING_YEAR_4=h.get( "PROJECTED_SAVING_YEAR_4" ).toString();
			break;
		  case 18:
			//this.PROJECTED_SAVING_YEAR_5=h.get( "PROJECTED_SAVING_YEAR_5" ).toString();
			break;
		  case 19:
			//this.ACTUAL_SAVING_YEAR_1=h.get( "ACTUAL_SAVING_YEAR_1" ).toString();
			break;
		  case 20:
			//this.ACTUAL_SAVING_YEAR_2=h.get( "ACTUAL_SAVING_YEAR_2" ).toString();
			break;
		  case 21:
			//this.ACTUAL_SAVING_YEAR_3=h.get( "ACTUAL_SAVING_YEAR_3" ).toString();
			break;
		  case 22:
			//this.ACTUAL_SAVING_YEAR_4=h.get( "ACTUAL_SAVING_YEAR_4" ).toString();
			break;
		  case 23:
			//this.ACTUAL_SAVING_YEAR_5=h.get( "ACTUAL_SAVING_YEAR_5" ).toString();
			break;
		  case 24:
			this.COMMENTS=h.get( "COMMENTS" ).toString();
			break;
		  case 25:
			this.BEST_PRACTICE=h.get( "BEST_PRACTICE" ).toString();
			break;
		  case 26:
		  {
			String tmpstatus = h.get( "PROJECT_STATUS" ).toString();
			this.PROJECT_STATUS=vvstatus.get( tmpstatus ).toString();
			break;
		  }
		  case 27:
			this.ACTUAL_FINISH_DATE=h.get( "ACTUAL_FINISH_DATE" ).toString();
			break;
		  case 28:
		  {
			String tmppriority = h.get( "PRIORITY" ).toString();
			this.PRIORITY=vvprority.get( tmppriority ).toString();
			break;
		  }
		  case 29:
			this.CUSTOMER_CONTACT_MANAGER=h.get( "CUSTOMER_CONTACT_MANAGER" ).toString();
			break;
		  case 30:
			this.CUSTOMER_CONTACT_EMAIL=h.get( "CUSTOMER_CONTACT_EMAIL" ).toString();
			break;
		  case 31:
			this.CUSTOMER_CONTACT_PHONE=h.get( "CUSTOMER_CONTACT_PHONE" ).toString();
			break;
		  case 32:
			this.PROJECT_MANAGER_PHONE=h.get( "PROJECT_MANAGER_PHONE" ).toString();
			break;
		  case 33:
			this.PROJECT_MANAGER_EMAIL=h.get( "PROJECT_MANAGER_EMAIL" ).toString();
			break;
		}
	  }

	  int totalprojecsavings = 0;
	  int actualsavings = 0;
/*
	if (PROJECTED_SAVING_YEAR_1.length() > 0 ) {totalprojecsavings += Integer.parseInt(PROJECTED_SAVING_YEAR_1);}
	if (PROJECTED_SAVING_YEAR_2.length() > 0 ) {totalprojecsavings += Integer.parseInt(PROJECTED_SAVING_YEAR_2);}
	if (PROJECTED_SAVING_YEAR_3.length() > 0 ) {totalprojecsavings += Integer.parseInt(PROJECTED_SAVING_YEAR_3);}
	if (PROJECTED_SAVING_YEAR_4.length() > 0 ) {totalprojecsavings += Integer.parseInt(PROJECTED_SAVING_YEAR_4);}
	if (PROJECTED_SAVING_YEAR_5.length() > 0 ) {totalprojecsavings += Integer.parseInt(PROJECTED_SAVING_YEAR_5);}

	if (ACTUAL_SAVING_YEAR_1.length() > 0 ) {actualsavings += Integer.parseInt(ACTUAL_SAVING_YEAR_1);}
	if (ACTUAL_SAVING_YEAR_2.length() > 0 ) {actualsavings += Integer.parseInt(ACTUAL_SAVING_YEAR_2);}
	if (ACTUAL_SAVING_YEAR_3.length() > 0 ) {actualsavings += Integer.parseInt(ACTUAL_SAVING_YEAR_3);}
	if (ACTUAL_SAVING_YEAR_4.length() > 0 ) {actualsavings += Integer.parseInt(ACTUAL_SAVING_YEAR_4);}
	if (ACTUAL_SAVING_YEAR_5.length() > 0 ) {actualsavings += Integer.parseInt(ACTUAL_SAVING_YEAR_5);}*/

	PROJECTED_SAVING_YEAR_TOTAL=""+totalprojecsavings+"";
	ACTUAL_SAVING_YEAR_TOTAL=""+actualsavings+"";

	  vvprority=null;
	  vvstatus=null;
	  vvtype=null;
	  //depdata=null;
	  projkeywords=null;
    }

  public String getCUSTOMER_CONTACTDesc(){return CUSTOMER_CONTACT;}
  public String getKEYWORDSDesc(){return KEYWORDS;}
  public String getPROJECT_MANAGERDesc(){return PROJECT_MANAGER;}
  public String getPROJECT_IDDesc(){return PROJECT_ID;}
  public String getPROJECT_NAMEDesc(){return PROJECT_NAME;}
  public String getPROJECT_DESCDesc(){return PROJECT_DESC;}
  public String getPROJECT_CATEGORYDesc(){return PROJECT_CATEGORY;}
  public String getPROJECT_MANAGER_IDDesc(){return PROJECT_MANAGER_ID;}
  public String getFACILITY_IDDesc(){return FACILITY_ID;}
  public String getAPPROVED_DATEDesc(){return APPROVED_DATE;}
  public String getSTART_DATEDesc(){return START_DATE;}
  public String getPROJECTED_FINISTED_DATEDesc(){return PROJECTED_FINISTED_DATE;}
  public String getPOJECTED_COSTDesc(){return POJECTED_COST;}
  public String getACTUAL_COSTDesc(){return ACTUAL_COST;}
  public String getPROJECTED_SAVING_YEAR_1Desc(){return PROJECTED_SAVING_YEAR_1;}
  public String getPROJECTED_SAVING_YEAR_2Desc(){return PROJECTED_SAVING_YEAR_2;}
  public String getPROJECTED_SAVING_YEAR_3Desc(){return PROJECTED_SAVING_YEAR_3;}
  public String getPROJECTED_SAVING_YEAR_4Desc(){return PROJECTED_SAVING_YEAR_4;}
  public String getPROJECTED_SAVING_YEAR_5Desc(){return PROJECTED_SAVING_YEAR_5;}
  public String getACTUAL_SAVING_YEAR_1Desc(){return ACTUAL_SAVING_YEAR_1;}
  public String getACTUAL_SAVING_YEAR_2Desc(){return ACTUAL_SAVING_YEAR_2;}
  public String getACTUAL_SAVING_YEAR_3Desc(){return ACTUAL_SAVING_YEAR_3;}
  public String getACTUAL_SAVING_YEAR_4Desc(){return ACTUAL_SAVING_YEAR_4;}
  public String getACTUAL_SAVING_YEAR_5Desc(){return ACTUAL_SAVING_YEAR_5;}
  public String getCOMMENTSDesc(){return COMMENTS;}
  public String getBEST_PRACTICEDesc(){return BEST_PRACTICE;}
  public String getPROJECT_STATUSDesc(){return PROJECT_STATUS;}
  public String getACTUAL_FINISH_DATEDesc(){return ACTUAL_FINISH_DATE;}
  public String getPRIORITYDesc(){return PRIORITY;}
  public String getCUSTOMER_CONTACT_MANAGERDesc(){return CUSTOMER_CONTACT_MANAGER;}
  public String getCUSTOMER_CONTACT_EMAILDesc(){return CUSTOMER_CONTACT_EMAIL;}
  public String getCUSTOMER_CONTACT_PHONEDesc(){return CUSTOMER_CONTACT_PHONE;}
  public String getPROJECT_MANAGER_PHONEDesc(){return PROJECT_MANAGER_PHONE;}
  public String getPROJECT_MANAGER_EMAILDesc(){return PROJECT_MANAGER_EMAIL;}
  public String getPROJECTED_SAVING_YEAR_TOTALDesc(){return PROJECTED_SAVING_YEAR_TOTAL;}
  public String getACTUAL_SAVING_YEAR_TOTALDesc(){return ACTUAL_SAVING_YEAR_TOTAL;}

  public static Vector getFieldVector()
  {
	Vector v=new Vector();
	v.addElement( "CUSTOMER_CONTACT = getCUSTOMER_CONTACTDesc" );
	v.addElement( "KEYWORDS = getKEYWORDSDesc" );
	v.addElement( "PROJECT_MANAGER = getPROJECT_MANAGERDesc" );
	v.addElement( "PROJECT_ID = getPROJECT_IDDesc" );
	v.addElement( "PROJECT_NAME = getPROJECT_NAMEDesc" );
	v.addElement( "PROJECT_DESC = getPROJECT_DESCDesc" );
	v.addElement( "PROJECT_CATEGORY = getPROJECT_CATEGORYDesc" );
	v.addElement( "PROJECT_MANAGER_ID = getPROJECT_MANAGER_IDDesc" );
	v.addElement( "FACILITY_ID = getFACILITY_IDDesc" );
	v.addElement( "APPROVED_DATE = getAPPROVED_DATEDesc" );
	v.addElement( "START_DATE = getSTART_DATEDesc" );
	v.addElement( "PROJECTED_FINISTED_DATE = getPROJECTED_FINISTED_DATEDesc" );
	v.addElement( "POJECTED_COST = getPOJECTED_COSTDesc" );
	v.addElement( "ACTUAL_COST = getACTUAL_COSTDesc" );
	v.addElement( "PROJECTED_SAVING_YEAR_1 = getPROJECTED_SAVING_YEAR_1Desc" );
	v.addElement( "PROJECTED_SAVING_YEAR_2 = getPROJECTED_SAVING_YEAR_2Desc" );
	v.addElement( "PROJECTED_SAVING_YEAR_3 = getPROJECTED_SAVING_YEAR_3Desc" );
	v.addElement( "PROJECTED_SAVING_YEAR_4 = getPROJECTED_SAVING_YEAR_4Desc" );
	v.addElement( "PROJECTED_SAVING_YEAR_5 = getPROJECTED_SAVING_YEAR_5Desc" );
	v.addElement( "ACTUAL_SAVING_YEAR_1 = getACTUAL_SAVING_YEAR_1Desc" );
	v.addElement( "ACTUAL_SAVING_YEAR_2 = getACTUAL_SAVING_YEAR_2Desc" );
	v.addElement( "ACTUAL_SAVING_YEAR_3 = getACTUAL_SAVING_YEAR_3Desc" );
	v.addElement( "ACTUAL_SAVING_YEAR_4 = getACTUAL_SAVING_YEAR_4Desc" );
	v.addElement( "ACTUAL_SAVING_YEAR_5 = getACTUAL_SAVING_YEAR_5Desc" );
	v.addElement( "COMMENTS = getCOMMENTSDesc" );
	v.addElement( "BEST_PRACTICE = getBEST_PRACTICEDesc" );
	v.addElement( "PROJECT_STATUS = getPROJECT_STATUSDesc" );
	v.addElement( "ACTUAL_FINISH_DATE = getACTUAL_FINISH_DATEDesc" );
	v.addElement( "PRIORITY = getPRIORITYDesc" );
	v.addElement( "CUSTOMER_CONTACT_MANAGER = getCUSTOMER_CONTACT_MANAGERDesc" );
	v.addElement( "CUSTOMER_CONTACT_EMAIL = getCUSTOMER_CONTACT_EMAILDesc" );
	v.addElement( "CUSTOMER_CONTACT_PHONE = getCUSTOMER_CONTACT_PHONEDesc" );
	v.addElement( "PROJECT_MANAGER_PHONE = getPROJECT_MANAGER_PHONEDesc" );
	v.addElement( "PROJECT_MANAGER_EMAIL = getPROJECT_MANAGER_EMAILDesc" );
	v.addElement( "PROJECTED_SAVING_YEAR_TOTAL = getPROJECTED_SAVING_YEAR_TOTALDesc" );
	v.addElement( "ACTUAL_SAVING_YEAR_TOTAL = getACTUAL_SAVING_YEAR_TOTALDesc" );
    return v;
  }

  public static Vector getVector(Vector in,HttpSession tasksession)
  {
      Vector out = new Vector();
	  Hashtable summary = new Hashtable();
	  summary = (Hashtable)in.elementAt(0);
	  int total = ((Integer)summary.get("TOTAL_NUMBER")).intValue();
	  int i = 0;

      try
      {
		for ( int count=0; count < total; count++ )
        {
            i++;
			Hashtable h = (Hashtable)in.elementAt(i);
			String Line_Check= h.get( "USERCHK" ) == null ? "" : h.get( "USERCHK" ).toString();
			if ("Y".equalsIgnoreCase(Line_Check))
			{
			  out.addElement( new projectData( h,h.size(),tasksession ) );
			}
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }

      return out;
  }
}
