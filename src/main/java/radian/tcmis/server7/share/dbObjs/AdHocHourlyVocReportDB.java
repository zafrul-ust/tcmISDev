package radian.tcmis.server7.share.dbObjs;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * 11-20-02
 * Changed Query if the Facility Choosen is Dallas Love
 * 06-12-03 - Changed the SWA query and added new methods for new reports. and code cleanup
 */
public class AdHocHourlyVocReportDB
{
	private TcmISDBModel db1;
	private String endYear;
	private String endMonth;
	private String facilityID;

	public AdHocHourlyVocReportDB(TcmISDBModel db)
	{
		this.db1 = db;
	}
	public AdHocHourlyVocReportDB()
	{
	}
	public void setDb(TcmISDBModel db)
	{
		this.db1 = db;
	}

	public Hashtable getUsageTemplateInfo( Hashtable inData )  throws Exception
	{
	  Hashtable result=new Hashtable();
	  result.put( "FACILITY_WORK_AREA",getFacilityWorkArea( ( Integer ) inData.get( "USER_ID" ) ) );
	  return result;
        }

		public Hashtable getFacilityWorkArea(Integer userId) throws Exception {
		  Hashtable result = new Hashtable();
		  Vector facilityV = new Vector();
		  String query = "select a.facility_id,b.application,b.application_desc app_desc from user_group_member a, fac_loc_app b where ";
		  query += "a.facility_id = b.facility_id and a.user_group_id = 'CreateReport' and a.personnel_id = "+userId;
		  query += " order by facility_id,application_desc";
		  DBResultSet dbrs = null;
		  ResultSet rs = null;
		  String lastFacId = "";
		  try {
			dbrs = db1.doQuery(query);
			rs=dbrs.getResultSet();
			while (rs.next()) {
			  String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
			  if (!fac.equals(lastFacId)) {
				facilityV.addElement(fac);
				lastFacId = fac;
			  }
			  String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
			  String desc = BothHelpObjs.makeBlankFromNull(rs.getString("app_desc"));
			  if (result.containsKey(fac)) {
				Hashtable h = (Hashtable)result.get(fac);
				Vector appId = (Vector)h.get("APPLICATION");
				Vector appDesc = (Vector)h.get("APPLICATION_DESC");
				if (!appId.contains(app)) {
				  appId.addElement(app);
				  appDesc.addElement(desc);
				}
				h.put("APPLICATION",appId);
				h.put("APPLICATION_DESC",appDesc);
				result.put(fac,h);
			  }else {
				Hashtable h = new Hashtable();
				Vector appId = new Vector();
				Vector appDesc = new Vector();
				if (!db1.getClient().equalsIgnoreCase("SWA")) {
				  if (!fac.equalsIgnoreCase("All Facilities")) {
					appId.addElement("All Work Areas");
					appDesc.addElement("All Work Areas");
				  }
				}
				appId.addElement(app);
				appDesc.addElement(desc);
				h.put("APPLICATION",appId);
				h.put("APPLICATION_DESC",appDesc);
				result.put(fac,h);
			  }
			}
		  } catch (Exception e) {
			e.printStackTrace(); throw e;
		  } finally {
			dbrs.close();
		  }

		  //add 'All Facilities' if user is allows to create report for two or more facilities
		  if (facilityV.size() > 1) {
			Hashtable h = new Hashtable(2);
			Vector appId = new Vector(1);
			Vector appDesc = new Vector(1);
			appId.addElement("All Work Areas");
			appDesc.addElement("All Work Areas");
			result.put("All Facilities",h);
			facilityV.insertElementAt("All Facilities",0);
		  }

		  result.put("FACILITY",facilityV);
		  //System.out.println("------------ my result: "+result);
		  return result;
		}

		public Vector getFiveHourReportData( TcmISDBModel db,Hashtable inData )
		   throws Exception
		{
		  Vector resultData=new Vector( 2 );
		  Vector maxFourHourV=new Vector( 20 );
		  DBResultSet dbrs=null;
		  ResultSet rs=null;
		  Hashtable Result=new Hashtable();
		  String reportType= ( String ) inData.get( "REPORT_TYPE" );

		  endYear=inData.get( "END_YEARN" ).toString();
		  endMonth=inData.get( "END_MONTH" ).toString();
		  facilityID= ( String ) inData.get( "FACILITY_ID" ).toString();

		  //String where = " FACILITY_ID = '"+facilityID.trim()+"'";
		  Integer endD=new Integer( 209012 );
		  try
		  {
			Integer em=new Integer( endMonth );
			em=new Integer( em.intValue() + 1 );
			Integer ey=new Integer( endYear );
			/*if(em.intValue() >=12)
			   {
			 em = new Integer(1);
			 ey = new Integer(ey.intValue() + 1);
			   }*/
			String esm=new String( em.toString() );
			if ( esm.length() < 2 )
			  esm="0" + esm;
			endD=new Integer( ey.toString() + esm );
		  }
		  catch ( Exception e )
		  {
			e.printStackTrace();
		  }

		  String QueryString="";
		  if ( "FIVE_HOUR_6A".equalsIgnoreCase( reportType ) )
		  {
			QueryString="select report_category source,to_char(daily_hour,'MM/DD/YYYY') usage_dateday,to_char(daily_hour,'HH24') ||':00' usage_datehur,fx_sig_fig(voc_5_hr_lb_per_hr, 3) voc_per_hr_5hr_avg from hourly_voc_view where report_type = 'Five-Hour Average 433.6A'";
			if ( !"All Facilities".equalsIgnoreCase( facilityID ) )
			{
			  QueryString+=" and facility_id = '" + facilityID + "'";
			}
			QueryString+=" and daily_hour >= to_date('" + endD + "01','YYYYMMDD') and daily_hour < last_day(to_date('" + endD + "01','YYYYMMDD'))+1 order by report_category,daily_hour";
		  }
		  else
		  {
			QueryString="select report_category source,to_char(daily_hour,'MM/DD/YYYY') usage_dateday,to_char(daily_hour,'HH24') ||':00' usage_datehur,fx_sig_fig(voc_5_hr_lb_per_hr, 3) voc_per_hr_5hr_avg from hourly_voc_view where report_type = 'Five-Hour Average 433.7A'";
			if ( !"All Facilities".equalsIgnoreCase( facilityID ) )
			{
			  QueryString+=" and facility_id = '" + facilityID + "'";
			}
			QueryString+=" and daily_hour >= to_date('" + endD + "01','YYYYMMDD') and daily_hour < last_day(to_date('" + endD + "01','YYYYMMDD'))+1 order by report_category,daily_hour";
		  }

		  int numRecs=0;
		  Hashtable Result2=new Hashtable();
		  Vector ResultV=new Vector();
		  try
		  {
			dbrs=db.doQuery( QueryString );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
			  Result2=new Hashtable();
			  numRecs+=1;
			  Result2.put( "SOURCE", ( rs.getString( "source" ) ) );
			  Result2.put( "DATE_TIME", ( rs.getString( "usage_dateday" ) ) );
			  Result2.put( "DATE_HOUR", ( rs.getString( "usage_datehur" ) ) );
			  Result2.put( "VOC_WT_SHIPPED", ( rs.getString( "voc_per_hr_5hr_avg" ) ) );
			  ResultV.addElement( Result2 );
			}

			//getting maximum five hour average
			if ( "FIVE_HOUR_6A".equalsIgnoreCase( reportType ) )
			{
			  QueryString="select report_category source,fx_sig_fig(max(nvl(voc_5_hr_lb_per_hr,0)), 3) voc_per_hr_5hr_avg from hourly_voc_view where report_type = 'Five-Hour Average 433.6A'";
			  if ( !"All Facilities".equalsIgnoreCase( facilityID ) )
			  {
				QueryString+=" and facility_id = '" + facilityID + "'";
			  }
			  QueryString+=" and daily_hour >= to_date('" + endD + "01','YYYYMMDD') and daily_hour < last_day(to_date('" + endD + "01','YYYYMMDD'))+1 group by report_category";
			}
			else
			{
			  QueryString="select report_category source,fx_sig_fig(max(nvl(voc_5_hr_lb_per_hr,0)), 3) voc_per_hr_5hr_avg from hourly_voc_view where report_type = 'Five-Hour Average 433.7A'";
			  if ( !"All Facilities".equalsIgnoreCase( facilityID ) )
			  {
				QueryString+=" and facility_id = '" + facilityID + "'";
			  }
			  QueryString+=" and daily_hour >= to_date('" + endD + "01','YYYYMMDD') and daily_hour < last_day(to_date('" + endD + "01','YYYYMMDD'))+1 group by report_category";
			}

			dbrs=db.doQuery( QueryString );
			rs=dbrs.getResultSet();
			while ( rs.next() )
			{
			  Hashtable maxFourHourH=new Hashtable( 3 );
			  maxFourHourH.put( "DETAIL_0", ( rs.getString( "source" ) ) );
			  maxFourHourH.put( "DETAIL_1", ( rs.getString( "voc_per_hr_5hr_avg" ) ) );
			  maxFourHourH.put( "DETAIL_2","" );
			  maxFourHourV.addElement( maxFourHourH );
			}
		  }
		  catch ( Exception e )
		  {
			Result.put( "STRING_BUFFER",ResultV );
			Result.put( "RECORDS","Error" );
			resultData.addElement( maxFourHourV );
			resultData.addElement( Result );
			e.printStackTrace();
			return resultData;
		  }
		  finally
		  {
			if (dbrs!= null) {dbrs.close();}
		  }

		  Result.put( "STRING_BUFFER",ResultV );
		  Result.put( "RECORDS","" + numRecs + "" );
		  //System.out.println( "The number of Records AdHocHourlyVocReport getFiveHourReportData is " + numRecs );

		  resultData.addElement( maxFourHourV );
		  resultData.addElement( Result );
		  return resultData;
		}

	public Vector getFourHourReportData(TcmISDBModel db,Hashtable inData) throws Exception {
		Vector resultData = new Vector(2);
		Vector maxFourHourV = new Vector(20);
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable Result = new Hashtable();

		endYear = inData.get("END_YEARN").toString();
		endMonth = inData.get("END_MONTH").toString();
		facilityID = (String)inData.get("FACILITY_ID").toString();

		//String where = " FACILITY_ID = '"+facilityID.trim()+"'";
		Integer endD = new Integer(209012);
		try
		{
			Integer em = new Integer(endMonth);
			em = new Integer(em.intValue()+1);
			Integer ey = new Integer(endYear);
			/*if(em.intValue() >=12)
			{
				em = new Integer(1);
				ey = new Integer(ey.intValue() + 1);
			}*/
			String esm = new String(em.toString());
			if(esm.length() < 2) esm = "0"+esm;
			endD = new Integer(ey.toString() + esm);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		String QueryString = "select source,fx_sig_fig(voc_per_hr_4hr_avg,3) voc_per_hr_4hr_avg,to_char(usage_datetime ,'MM/DD/YYYY') usage_dateday,to_char(usage_datetime ,'HH:MI AM') usage_datehour from hourly_voc_view where "+
							 "source <> 'Facility' and usage_date between "+       //to_char(usage_datetime, 'MM/DD/YYYY')
							 "to_date('"+endD+"01','YYYYMMDD') and  last_day(to_date('"+endD+"01','YYYYMMDD'))+1";
		if ("All Facilities".equalsIgnoreCase(facilityID)) {
		  QueryString += " and facility_id = '"+facilityID+"'";
		}
		QueryString += " order by report_category,daily_hour";

		int numRecs = 0 ;
		Hashtable Result2 = new Hashtable();
		Vector ResultV = new Vector();
		try {
			dbrs = db.doQuery(QueryString);
			rs=dbrs.getResultSet();

			while(rs.next()) {
				Result2 = new Hashtable();
				numRecs += 1;
				Result2.put("SOURCE",(rs.getString("source")));
				Result2.put("DATE_TIME",(rs.getString("usage_dateday")));
				Result2.put("VOC_WT_SHIPPED",(rs.getString("voc_per_hr_4hr_avg")));
				ResultV.addElement(Result2);
			}
			//getting maximum four hour average
		   QueryString = "select report_category source,fx_sig_fig(max(voc_lb_per_hr), 3) voc_per_hr_4hr_avg"+
						 " from voc_hourly_view where report_type = 'Hourly' and daily_hour >= to_date('"+endD+"01','dd-mon-yyyy')"+
						 " and daily_hour < last_day(to_date('"+endD+"01','dd-mon-yyyy'))+1";
			if ("All Facilities".equalsIgnoreCase(facilityID)) {
			  QueryString += " and facility_id = '"+facilityID+"'";
			}
			QueryString += " group by report_category";
			dbrs = db.doQuery(QueryString);
			rs=dbrs.getResultSet();
			while(rs.next()) {
				Hashtable maxFourHourH = new Hashtable(2);
				maxFourHourH.put("DETAIL_0",(rs.getString("source")));
				maxFourHourH.put("DETAIL_1",(rs.getString("voc_per_hr_4hr_avg")));
				maxFourHourV.addElement(maxFourHourH);
			}
		}catch(Exception e) {
			Result.put("STRING_BUFFER",ResultV);
			Result.put("RECORDS","Error");
			resultData.addElement(maxFourHourV);
			resultData.addElement(Result);
			e.printStackTrace();
			return resultData;
		}
		finally
		{
		  if (dbrs!= null) {dbrs.close();}
		}

		Result.put("STRING_BUFFER",ResultV);
		Result.put("RECORDS",""+numRecs+"");
		//System.out.println("The number of Records AdHocHourlyVocReport is getFourHourReportData "+numRecs);

		resultData.addElement(maxFourHourV);
		resultData.addElement(Result);
		return resultData;
	}


        //this method will insert data into template table with template(report_hash_seq)
        public String createReportTemplate(String userID, Hashtable inData) throws Exception {
          String sessionID = userID+"xyz";
          try {
            Integer x = new Integer(HelpObjs.getNextValFromSeq(db1,"report_hash_seq"));
            Hashtable innerH = (Hashtable) inData.get("SELECTED_DATA");
            innerH.put("USER_ID",userID);
            for(Enumeration enuma = innerH.keys(); enuma.hasMoreElements();){
              String key = enuma.nextElement().toString();
              String value = "";
              if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
                value = HelpObjs.getStringValuesFromVector((Vector)innerH.get(key));
              }else if ("REPORT_FIELDS1".equalsIgnoreCase(key)) {
                value = HelpObjs.getStringValuesFromVector((Vector)innerH.get(key));
              }else {
                value = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(innerH.get(key).toString());
              }
              db1.doQuery("insert into report_hash_table(report_id,field_name,field_value) values("+x.toString()+",'"+key+"','"+value+"')");
            }
            sessionID = x.toString();
          }catch (Exception e) {
            e.printStackTrace();
            throw e;
          }
          return sessionID;
        } //end of method

        public Hashtable loadReportTemplate(String sessionID) throws Exception {
            String query = "select field_name, field_value from report_hash_table where report_id = "+sessionID;
            Hashtable result = new Hashtable();
            DBResultSet dbrs = null;
            ResultSet rs = null;
            try {
                dbrs = db1.doQuery(query);
                rs=dbrs.getResultSet();
                while (rs.next()) {
                    String key = rs.getString("field_name");
                    String value = BothHelpObjs.makeBlankFromNull(rs.getString("field_value"));
                    if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
                      Vector v = HelpObjs.getVectorFromString(value);
                      result.put(key,v);
                    }else if ("REPORT_FIELDS1".equalsIgnoreCase(key)) {
                      Vector v = HelpObjs.getVectorFromString(value);
                      result.put(key,v);
                    }else {
                      result.put(key,value);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace(); throw e;
            }finally {
                dbrs.close();
            }
            return result;
        } //end of method

	 public Hashtable getReportData(TcmISDBModel db,Hashtable inData) throws Exception
	 {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable Result = new Hashtable();

		endYear = inData.get("END_YEARN").toString();
		endMonth = inData.get("END_MONTH").toString();
		facilityID = (String)inData.get("FACILITY_ID").toString();

		String where = " FACILITY_ID = '"+facilityID.trim()+"'";
		Integer endD = new Integer(209012);
		try
		{
			Integer em = new Integer(endMonth);
			em = new Integer(em.intValue()+1);
			Integer ey = new Integer(endYear);
			/*if(em.intValue() >=12)
			{
				em = new Integer(1);
				ey = new Integer(ey.intValue() + 1);
			}*/
			String esm = new String(em.toString());
			if(esm.length() < 2) esm = "0"+esm;
			endD = new Integer(ey.toString() + esm);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String QueryString = "select report_category source,to_char(daily_hour ,'MM/DD/YYYY') DATE_SHIPPED,to_char(daily_hour ,'HH:MI AM') HOUR_SHIPPED,"+
							"fx_sig_fig(nvl(voc_lb_per_hr,0), 3) VOC_WT_SHIPPED"+
							" from hourly_voc_view where report_type = 'Hourly' and daily_hour >= to_date('"+endD+"01','YYYYMMDD')"+
							" and daily_hour < last_day(to_date('"+endD+"01','YYYYMMDD'))+1";
		if (!"All Facilities".equalsIgnoreCase(facilityID)) {
		  QueryString += " and facility_id = '"+facilityID+"'";
		}
		QueryString += " order by report_category,daily_hour";

		int numRecs = 0 ;
		Hashtable Result2 = new Hashtable();
		Vector ResultV = new Vector();
		//System.out.print("\n\n ++++++ The ad hoc usage query: "+QueryString+"\n");
		try
		{
			dbrs = db.doQuery(QueryString);
			rs=dbrs.getResultSet();
			while(rs.next())
			{
				Result2 = new Hashtable();
				numRecs += 1;
				Result2.put("SOURCE",(rs.getString("SOURCE")));
				Result2.put("DATE_TIME",(rs.getString("DATE_SHIPPED")));
				Result2.put("HOUR_TIME",(rs.getString("HOUR_SHIPPED")));
				Result2.put("VOC_WT_SHIPPED",(rs.getString("VOC_WT_SHIPPED")));
				ResultV.addElement(Result2);
			}
		}
		catch(Exception e)
		{
			Result.put("STRING_BUFFER",ResultV);
			Result.put("RECORDS","Error");
			e.printStackTrace();
			return Result;
		}
		finally
		{
		  if (dbrs!= null) {dbrs.close();}
		}

		Result.put("STRING_BUFFER",ResultV);
		Result.put("RECORDS",""+numRecs+"");
		//System.out.println("The number of Records AdHocHourlyVocReport Hourly Report is "+numRecs);
		return Result;
	}
}
