package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 *
 * 11-01-02
 * Made changes so that msds_chemical_name has to be something to be able to enter into the chemical composition table it is not null in database
 * 11-04-02
 * Checking the length of the comments and remarks field before updating
 * 11-11-02
 * moved msds_chemical_name above to make sure it is not null to insert into the chemical table
 * 11-15-02
 * adding PERSONAL_PROTECTION to hmis data
 *
 * 01-03-03
 * Adding a text area to enter ALT_DATA_SOURCE information
 *
 * 01-07-03
 * if (msds_chemical_name.length() > 160){msds_chemical_name = msds_chemical_name.substring(0,159);}
 *
 * 01-27-03
 * the Cas Number can be null
 * 07-14-03 - Removing the DOT and Solids info from the page
 * 08-18-03 - Giving a message when a cas_number is not found and an error occures
 * 09-09-03 - Converting from Ascii to normal text when updating Chemical ID and Chemila Name for Composition data
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 * 11-10-04 - Added new VOC fields
 */

import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class editMsds {

    private TcmISDBModel db;
    private Vector vMsdsNoList = null;

    public editMsds() {
        vMsdsNoList = new Vector();
    }

    public editMsds(TcmISDBModel db ){
        this.db = db;
        vMsdsNoList = new Vector();
    }

    public void setDB(TcmISDBModel db){
        this.db = db;
    }

    public void setMsdsNoList(){
        this.vMsdsNoList = vMsdsNoList;
    }
    public Vector getMsdsNoList( ){
        return this.vMsdsNoList;
    }

    public Hashtable getMSDSData( )
    {
      Hashtable ResultData=new Hashtable();
      Vector result=new Vector();
      Hashtable MaterialData=new Hashtable();
      Hashtable MSDSData=new Hashtable();
      Hashtable CompositionData=new Hashtable();
      Vector compresult=new Vector();

      int totalrecords=0;
      int comprecords=0;

      try
      {
        String materialid="";
        totalrecords+=1;
        ResultData=new Hashtable();
        MaterialData=new Hashtable();
        MSDSData=new Hashtable();
        compresult=new Vector();
        comprecords=0;

        //PART_ID
        ResultData.put( "PART_ID","" );
        //UPDATE_FLAG
        ResultData.put( "UPDATE_FLAG",new Boolean( true ) );
        ResultData.put( "MATERIAL_ID",materialid );

        //Material Data
        //MATERIAL_DESC
        MaterialData.put( "MATERIAL_DESC","" );
        //MANUFACTURER
        MaterialData.put( "MANUFACTURER","" );
        //MATERIAL_ID
        MaterialData.put( "MATERIAL_ID",materialid );
        //MFG_ID
        MaterialData.put( "MFG_ID","" );
        //MFG_TRADE_NAME
        MaterialData.put( "MFG_TRADE_NAME","" );
        /*//LANDDOT
        MaterialData.put( "LANDDOT","" );
        //AIRDOT
        MaterialData.put( "AIRDOT","" );
        //WATERDOT
        MaterialData.put( "WATERDOT","" );*/
        //MATERIAL_ID
        MaterialData.put( "MATERIAL_ID","" );
        //ERG
        //MaterialData.put( "ERG","" );
        //MFG_URL
        MaterialData.put( "MFG_URL","" );
        //CONTACT
        MaterialData.put( "CONTACT","" );
        //PHONE
        MaterialData.put( "PHONE","" );
		//EMAIL
		 MaterialData.put( "EMAIL","");
		 //NOTES
		 MaterialData.put( "NOTES","");
        //MATERIAL_DATA
        ResultData.put( "MATERIAL_DATA",MaterialData );
        //MSDS DATA
        //REVISION_DATE
        MSDSData.put( "REVISION_DATE","" );
        //CONTENT
        MSDSData.put( "CONTENT","" );
        //SPECIFIC_GRAVITY
        MSDSData.put( "SPECIFIC_GRAVITY","" );
        //HEALTH
        MSDSData.put( "HEALTH","" );
        //FLAMMABILITY
        MSDSData.put( "FLAMMABILITY","" );
        //REACTIVITY
        MSDSData.put( "REACTIVITY","" );
        //HEALTH
        MSDSData.put( "HMIS_HEALTH","" );
        //FLAMMABILITY
        MSDSData.put( "HMIS_FLAMMABILITY","" );
        //REACTIVITY
        MSDSData.put( "HMIS_REACTIVITY","" );
        //SPECIFIC_HAZARD
        MSDSData.put( "SPECIFIC_HAZARD","" );
        //PERSONAL_PROTECTION
        MSDSData.put( "PERSONAL_PROTECTION","" );
        //FLASH_POINT
        MSDSData.put( "FLASH_POINT","" );
        //DENSITY
        MSDSData.put( "DENSITY","" );
        //DENSITY_UNIT
        MSDSData.put( "DENSITY_UNIT","" );
        //PHYSICAL_STATE
        MSDSData.put( "PHYSICAL_STATE","" );
        //VOC_UNIT
        MSDSData.put( "VOC_UNIT","" );
        //VOC
        MSDSData.put( "VOC","" );
        //VOC_LOWER
        MSDSData.put( "VOC_LOWER","" );
        //VOC_UPPER
        MSDSData.put( "VOC_UPPER","" );
        //REMARK
        MSDSData.put( "REMARK","" );
        //FLASH_POINT_UNIT
        MSDSData.put( "FLASH_POINT_UNIT","" );
        //SOLIDS
        MSDSData.put( "SOLIDS","" );
        //SOLIDS_LOWER
        MSDSData.put( "SOLIDS_LOWER","" );
        //SOLIDS_UPPER
        MSDSData.put( "SOLIDS_UPPER","" );
        //SOLIDS_UNIT
        MSDSData.put( "SOLIDS_UNIT","" );
		MSDSData.put( "SOLIDS_SOURCE","" );
        /*
        //SOLIDS_PERCENT
        MSDSData.put( "SOLIDS_PERCENT","" );
        //SOLIDS_LOWER_PERCENT
        MSDSData.put( "SOLIDS_LOWER_PERCENT","" );
        //SOLIDS_UPPER_PERCENT
        MSDSData.put( "SOLIDS_UPPER_PERCENT","" );
        */
        //VOC_LOWER_PERCENT
        MSDSData.put( "VOC_LOWER_PERCENT","" );
        //VOC_UPPER_PERCENT
        MSDSData.put( "VOC_UPPER_PERCENT","" );
        //VOC_PERCENT
        MSDSData.put( "VOC_PERCENT","" );
        //ALT_DATA_SOURCE
        MSDSData.put( "ALT_DATA_SOURCE","" );
		//VAPOR_PRESSURE_DETECT
	    MSDSData.put( "VAPOR_PRESSURE_DETECT","" );
	    //VAPOR_PRESSURE
	    MSDSData.put( "VAPOR_PRESSURE","" );
	    //VAPOR_PRESSURE_UNIT
	    MSDSData.put( "VAPOR_PRESSURE_UNIT","" );
	    //ALT_DATA_SOURCE
	    MSDSData.put( "VAPOR_PRESSURE_TEMP","" );
	    //VAPOR_PRESURE_TEMP_UNIT
	    MSDSData.put( "VAPOR_PRESURE_TEMP_UNIT","" );

		MSDSData.put("VOC_SOURCE","");
		MSDSData.put( "VOC_LESS_H2O_EXEMPT","" );
		MSDSData.put( "VOC_LESS_H2O_EXEMPT_UNIT","" );
		MSDSData.put( "VOC_LESS_H2O_EXEMPT_LOWER","" );
		MSDSData.put( "VOC_LESS_H2O_EXEMPT_UPPER","" );
		MSDSData.put( "VOC_LESS_H2O_EXEMPT_SOURCE","" );

		MSDSData.put( "VOC_LB_PER_SOLID_LB","" );
		MSDSData.put( "VOC_LB_PER_SOLID_LB_SOURCE","" );
		MSDSData.put( "VOC_LB_PER_SOLID_LB_LOWER","" );
		MSDSData.put("VOC_LB_PER_SOLID_LB_UPPER","" );

		MSDSData.put("VAPOR_PRESSURE_SOURCE","" );
		MSDSData.put("VAPOR_PRESSURE_LOWER","" );
		MSDSData.put("VAPOR_PRESSURE_UPPER","" );

        //MSDS_DATA
        ResultData.put( "MSDS_DATA",MSDSData );
        for ( int rem=0; rem < 50; rem++ )
        {
          CompositionData=new Hashtable();

          //PERCENT
          CompositionData.put( "PERCENT","" );
          //PERCENT_LOWER
          CompositionData.put( "PERCENT_LOWER","" );
          //PERCENT_UPPER
          CompositionData.put( "PERCENT_UPPER","" );
          //HAZARDOUS
          CompositionData.put( "HAZARDOUS","" );
          //CAS_NUMBER
          CompositionData.put( "CAS_NUMBER","" );
          //TRADE_SECRET
          CompositionData.put( "TRADE_SECRET","" );
          //REMARK
          CompositionData.put( "REMARK","" );
          //MSDS_CHEMICAL_NAME
          CompositionData.put( "MSDS_CHEMICAL_NAME","" );

          CompositionData.put( "LINE_STATUS","" );

          compresult.addElement( CompositionData );
        }

        ResultData.put( "COMPOSITION_DATA",compresult );
        result.addElement( ResultData );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
      }

      Hashtable recsum=new Hashtable();
      recsum.put( "TOTAL_NUMBER",new Integer( totalrecords ) );
      //ITEM_ID
      recsum.put( "ITEM_ID","" );
      //PART_DESC
      recsum.put( "PART_DESC","" );
      recsum.put( "DATA",result );
      recsum.put( "REQUEST_ID","" );

      return recsum;
    }

    public Hashtable updateMsdsData( Hashtable DataH,String loginID )
    {
      boolean msdsresult=true;
      Hashtable materialData=null;
      Hashtable msdsData=null;
      Hashtable compositionData=null;
      Vector ofCompositionData = null;
      String msdserrmsg = "";

      materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );
      msdsData= ( Hashtable ) DataH.get( "MSDS_DATA" );
      ofCompositionData= ( Vector ) DataH.get( "COMPOSITION_DATA" );
      String material_desc=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MATERIAL_DESC" ).toString() );
      //String manufacturer=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MANUFACTURER" ).toString() );
      String material_id=materialData.get( "MATERIAL_ID" ).toString();
      String mfg_id=materialData.get( "MFG_ID" ).toString();
      String mfg_trade_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "MFG_TRADE_NAME" ).toString() );
      /*String landdot=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "LANDDOT" ).toString() );
      String airdot=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "AIRDOT" ).toString() );
      String waterdot=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( materialData.get( "WATERDOT" ).toString() );
      String erg=materialData.get( "ERG" ).toString();*/
      String revision_date=msdsData.get( "REVISION_DATE" ).toString();
      String content=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "CONTENT" ).toString() );
      String specific_gravity=msdsData.get( "SPECIFIC_GRAVITY" ).toString();
      String health=msdsData.get( "HEALTH" ).toString();
      String flammability=msdsData.get( "FLAMMABILITY" ).toString();
      String reactivity=msdsData.get( "REACTIVITY" ).toString();
      String hmishealth=msdsData.get( "HMIS_HEALTH" ).toString();
      String hmisflammability=msdsData.get( "HMIS_FLAMMABILITY" ).toString();
      String hmisreactivity=msdsData.get( "HMIS_REACTIVITY" ).toString();
      String personnelprotection=msdsData.get( "PERSONAL_PROTECTION" ).toString();
      String specific_hazard=msdsData.get( "SPECIFIC_HAZARD" ).toString();
      String flash_point=msdsData.get( "FLASH_POINT" ).toString();
      if (flash_point.length() > 12){flash_point = flash_point.substring(0,11);}

      String density=msdsData.get( "DENSITY" ).toString();
      String density_unit=msdsData.get( "DENSITY_UNIT" ).toString();
      String physical_state=msdsData.get( "PHYSICAL_STATE" ).toString();
      String voc_unit=msdsData.get( "VOC_UNIT" ).toString();
      String voc=msdsData.get( "VOC" ).toString();
      String voc_lower=msdsData.get( "VOC_LOWER" ).toString();
      String voc_upper=msdsData.get( "VOC_UPPER" ).toString();
      String msdsremark=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "REMARK" ).toString() );
      if ( msdsremark.length() > 60 )
      {
        msdsremark=msdsremark.substring( 0,59 );
      }

      String altdataSoucre=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( msdsData.get( "ALT_DATA_SOURCE" ).toString() );
      if ( altdataSoucre.length() > 100 )
      {
        altdataSoucre=altdataSoucre.substring( 0,99 );
      }

      String flash_point_unit=msdsData.get( "FLASH_POINT_UNIT" ).toString();
      String solids=msdsData.get( "SOLIDS" ).toString();
      String solids_lower=msdsData.get( "SOLIDS_LOWER" ).toString();
      String solids_upper=msdsData.get( "SOLIDS_UPPER" ).toString();
      String solids_unit=msdsData.get( "SOLIDS_UNIT" ).toString();
	  String solids_source = msdsData.get("SOLIDS_SOURCE").toString();
	  String vappressdet=msdsData.get( "VAPOR_PRESSURE_DETECT" ).toString();
	  String vappress=msdsData.get( "VAPOR_PRESSURE" ).toString();
	  String vappressunit=msdsData.get( "VAPOR_PRESSURE_UNIT" ).toString();
	  String vappresstemp=msdsData.get( "VAPOR_PRESSURE_TEMP" ).toString();
	  String vappresstempunt=msdsData.get( "VAPOR_PRESURE_TEMP_UNIT" ).toString();

	  String voc_source = msdsData.get("VOC_SOURCE").toString();
	  String voc_less_h2o_exempt = msdsData.get("VOC_LESS_H2O_EXEMPT").toString();
	  String voc_less_h2o_exempt_unit = msdsData.get("VOC_LESS_H2O_EXEMPT_UNIT").toString();
	  String voc_less_h2o_exempt_lower = msdsData.get("VOC_LESS_H2O_EXEMPT_LOWER").toString();
	  String voc_less_h2o_exempt_upper = msdsData.get("VOC_LESS_H2O_EXEMPT_UPPER").toString();
	  String voc_less_h2o_exempt_source = msdsData.get("VOC_LESS_H2O_EXEMPT_SOURCE").toString();


	   String voc_lb_per_solid_lb = msdsData.get("VOC_LB_PER_SOLID_LB").toString();
	  String voc_lb_per_solid_lb_lower = msdsData.get("VOC_LB_PER_SOLID_LB_LOWER").toString();
	  String voc_lb_per_solid_lb_upper = msdsData.get("VOC_LB_PER_SOLID_LB_UPPER").toString();
	  String voc_lb_per_solid_lb_source = msdsData.get("VOC_LB_PER_SOLID_LB_SOURCE").toString();
		String vapor_pressure_source = msdsData.get("VAPOR_PRESSURE_SOURCE").toString();
		String vapor_pressure_lower = msdsData.get("VAPOR_PRESSURE_LOWER").toString();
		String vapor_pressure_upper = msdsData.get("VAPOR_PRESSURE_UPPER").toString();

	  /*String voc_less_exempt_lower = msdsData.get("VOC_LESS_EXEMPT_LOWER").toString();
	  String voc_less_exempt_upper = msdsData.get("VOC_LESS_EXEMPT_UPPER").toString();
	  String solids_source = msdsData.get("SOLIDS_SOURCE").toString();
	  String voc_lb_per_solid_lb_low_calc = msdsData.get("VOC_LB_PER_SOLID_LB_LOW_CALC").toString();
	  String voc_lb_per_solid_lb_calc = msdsData.get("VOC_LB_PER_SOLID_LB_CALC").toString();
	  String voc_lb_per_solid_lb_up_calc = msdsData.get("VOC_LB_PER_SOLID_LB_UP_CALC").toString();
	  String voc_less_h2o_exempt_lower_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_LOWER_CALC").toString();
	  String voc_less_h2o_exempt_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_CALC").toString();
	  String voc_less_h2o_exempt_upper_calc = msdsData.get("VOC_LESS_H2O_EXEMPT_UPPER_CALC").toString();*/

      boolean updateorinsert=false;

      try
      {
        String materialquery="";
        String msdsquery="";

        if ( material_id.length() > 0 )
        {
          materialquery="update global.material set \n";
          materialquery+="MATERIAL_DESC='" + material_desc + "', \n"; //MANUFACTURER='"+manufacturer+"',
          if ( mfg_id.length() > 0 )
          {
            materialquery+="MFG_ID=" + mfg_id + ",";
          }
          materialquery+=" TRADE_NAME='" + mfg_trade_name + "'";
          /*materialquery += " TRADE_NAME='"+mfg_trade_name+"',GROUND_DOT='"+landdot+"',";
          if (erg.length() > 0) {materialquery += "ERG="+erg+",";}
          materialquery += "AIR_DOT='"+airdot+"',WATER_DOT='"+waterdot+"' \n";*/
          materialquery+=" where MATERIAL_ID = " + material_id + " ";
        }

        String onLine = "Y";

        if ( revision_date.length() == 10 )
        {
          int test_number=DbHelpers.countQuery( db,"select count(*) from global.msds where MATERIAL_ID = " + material_id + " and REVISION_DATE=to_date('" + revision_date + "','MM/DD/YYYY')" );
          int foreignMsds = DbHelpers.countQuery(db,"select count(*) from global.msds_locale where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY')");
          if ( foreignMsds > 0 )
          {
            onLine = "N";
          }
            
          if ( test_number > 0 )
          {
            updateorinsert=true;
          }

		  if ( updateorinsert )
		  {
			msdsquery  = "update global.msds set \n";
			msdsquery += "VAPOR_PRESSURE_LOWER='"+vapor_pressure_lower+"',VAPOR_PRESSURE_UPPER='"+vapor_pressure_upper+"',VAPOR_PRESSURE_SOURCE='"+vapor_pressure_source+"', \n";
			msdsquery += "ALT_DATA_SOURCE='"+altdataSoucre+"',PERSONAL_PROTECTION='"+personnelprotection+"',REMARK='"+msdsremark+"',CONTENT='"+content+"',ON_LINE='"+onLine+"',SPECIFIC_GRAVITY="+(specific_gravity.length()==0?"null":specific_gravity)+",HEALTH='"+health+"',FLAMMABILITY='"+flammability+"', \n";
			msdsquery += "REACTIVITY='"+reactivity+"',SPECIFIC_HAZARD='"+specific_hazard+"',FLASH_POINT='"+flash_point+"',FLASH_POINT_UNIT='"+flash_point_unit+"',DENSITY="+(density.length()==0?"null":density)+", \n";
			msdsquery += "HMIS_HEALTH='"+hmishealth+"',HMIS_FLAMMABILITY='"+hmisflammability+"',HMIS_REACTIVITY='"+hmisreactivity+"', \n";
			msdsquery += "DENSITY_UNIT="+("None".equalsIgnoreCase(density_unit)?"null":"'"+density_unit+"'")+",PHYSICAL_STATE="+("None".equalsIgnoreCase(physical_state)?"null":"'"+physical_state+"'")+",VOC="+(voc.length()==0?"null":voc)+",VOC_LOWER="+(voc_lower.length()==0?"null":voc_lower)+",VOC_UPPER="+(voc_upper.length()==0?"null":voc_upper)+", \n";
			msdsquery += "VOC_UNIT="+("None".equalsIgnoreCase(voc_unit)?"null":"'"+voc_unit+"'")+", SOLIDS="+(solids.length()==0?"null":solids)+",SOLIDS_LOWER="+(solids_lower.length()==0?"null":solids_lower)+",SOLIDS_UPPER="+(solids_upper.length()==0?"null":solids_upper)+",SOLIDS_UNIT="+("None".equalsIgnoreCase(solids_unit)?"null":"'"+solids_unit+"'")+",SOLIDS_SOURCE="+("None".equalsIgnoreCase(solids_source)?"null":"'"+solids_source+"'")+", \n";
			msdsquery += "VAPOR_PRESSURE_DETECT="+(vappressdet.length()==0?"null":"'"+vappressdet+"'")+",VAPOR_PRESSURE="+(vappress.length()==0?"null":vappress)+",VAPOR_PRESSURE_UNIT="+(vappressunit.length()==0?"null":"'"+vappressunit+"'")+",VAPOR_PRESSURE_TEMP="+(vappresstemp.length()==0?"null":vappresstemp)+",VAPOR_PRESSURE_TEMP_UNIT="+(vappresstempunt.length()==0?"null":"'"+vappresstempunt+"'")+", \n";
			msdsquery += "VOC_SOURCE='"+voc_source+"',VOC_LESS_H2O_EXEMPT='"+voc_less_h2o_exempt+"',VOC_LESS_H2O_EXEMPT_UNIT='"+voc_less_h2o_exempt_unit+"',VOC_LESS_H2O_EXEMPT_LOWER='"+voc_less_h2o_exempt_lower+"',VOC_LESS_H2O_EXEMPT_UPPER='"+voc_less_h2o_exempt_upper+"',VOC_LESS_H2O_EXEMPT_SOURCE='"+voc_less_h2o_exempt_source+"', \n";
			msdsquery += "VOC_LB_PER_SOLID_LB_UPPER='"+voc_lb_per_solid_lb_upper+"',VOC_LB_PER_SOLID_LB='"+voc_lb_per_solid_lb+"',VOC_LB_PER_SOLID_LB_LOWER='"+voc_lb_per_solid_lb_lower+"',VOC_LB_PER_SOLID_LB_SOURCE='"+voc_lb_per_solid_lb_source+"', \n";
			msdsquery += " REVIEWED_BY="+loginID+",REVIEW_DATE=sysdate where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY') \n"; //'"+revision_date+"'";
		  }
		  else
		  {
			msdsquery  = "insert into global.msds (VAPOR_PRESSURE_LOWER,VAPOR_PRESSURE_UPPER,VAPOR_PRESSURE_SOURCE,ALT_DATA_SOURCE,PERSONAL_PROTECTION,REMARK,MATERIAL_ID,REVISION_DATE,CONTENT,SPECIFIC_GRAVITY,HEALTH,FLAMMABILITY,REACTIVITY,HMIS_HEALTH,HMIS_FLAMMABILITY,HMIS_REACTIVITY,SPECIFIC_HAZARD,FLASH_POINT,DENSITY,ON_LINE,DENSITY_UNIT, \n";
			msdsquery += "PHYSICAL_STATE,VOC_UNIT,VOC,VOC_LOWER,VOC_UPPER,FLASH_POINT_UNIT,VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT,REVIEWED_BY,REVIEW_DATE,\n";
			msdsquery += "VOC_SOURCE,VOC_LESS_H2O_EXEMPT,VOC_LESS_H2O_EXEMPT_UNIT,VOC_LESS_H2O_EXEMPT_LOWER,VOC_LESS_H2O_EXEMPT_UPPER,VOC_LESS_H2O_EXEMPT_SOURCE,VOC_LB_PER_SOLID_LB_UPPER,VOC_LB_PER_SOLID_LB,VOC_LB_PER_SOLID_LB_LOWER,VOC_LB_PER_SOLID_LB_SOURCE,SOLIDS, SOLIDS_LOWER, SOLIDS_UPPER,SOLIDS_UNIT,SOLIDS_SOURCE) values ";
			msdsquery += "('"+vapor_pressure_lower+"','"+vapor_pressure_upper+"','"+vapor_pressure_source+"','"+altdataSoucre+"','"+personnelprotection+"','"+msdsremark+"',"+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),'"+content+"',"+(specific_gravity.length()==0?"null":specific_gravity)+",'"+health+"','"+flammability+"','"+reactivity+"','"+hmishealth+"','"+hmisflammability+"','"+hmisreactivity+"','"+specific_hazard+"','"+flash_point+"', \n";
			msdsquery += " "+(density.length()==0?"null":density)+",'"+onLine+"',"+("None".equalsIgnoreCase(density_unit)?"null":"'"+density_unit+"'")+","+("None".equalsIgnoreCase(physical_state)?"null":"'"+physical_state+"'")+","+("None".equalsIgnoreCase(voc_unit)?"null":"'"+voc_unit+"'")+","+(voc.length()==0?"null":voc)+","+(voc_lower.length()==0?"null":voc_lower)+","+(voc_upper.length()==0?"null":voc_upper)+",'"+flash_point_unit+"', \n";
			msdsquery += " "+(vappressdet.length()==0?"null":"'"+vappressdet+"'")+","+(vappress.length()==0?"null":vappress)+","+(vappressunit.length()==0?"null":"'"+vappressunit+"'")+","+(vappresstemp.length()==0?"null":vappresstemp)+","+(vappresstempunt.length()==0?"null":"'"+vappresstempunt+"'")+", \n";
			msdsquery += " "+loginID+",sysdate, ";
			msdsquery += "'"+voc_source+"','"+voc_less_h2o_exempt+"','"+voc_less_h2o_exempt_unit+"','"+voc_less_h2o_exempt_lower+"','"+voc_less_h2o_exempt_upper+"','"+voc_less_h2o_exempt_source+"', \n";
			msdsquery += "'"+voc_lb_per_solid_lb_upper+"','"+voc_lb_per_solid_lb+"','"+voc_lb_per_solid_lb_lower+"','"+voc_lb_per_solid_lb_source+"', \n";
			msdsquery += ""+(solids.length()==0?"null":solids)+","+(solids_lower.length()==0?"null":solids_lower)+","+(solids_upper.length()==0?"null":solids_upper)+","+("None".equalsIgnoreCase(solids_unit)?"null":"'"+solids_unit+"'")+","+("None".equalsIgnoreCase(solids_source)?"null":"'"+solids_source+"'")+") \n";
		  }
        }

        if ( material_id.length() > 0 )
        {
          db.doUpdate( materialquery );
        }

        if ( revision_date.length() == 10 )
        {
          db.doUpdate( msdsquery );
        }

        /*boolean updatecomposition=false;
                 for ( int rem=0; rem < 25; rem++ )
                 {
          compositionData=new Hashtable();
          compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );
          //String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
          String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );
          //01-27-03
          if ( msds_chemical_name.trim().length() > 0 )
          {
            updatecomposition=true;
          }
                 }
              if ( (material_id.length() > 0) && (revision_date.length() == 10) && (updatecomposition) )
              {
            db.doUpdate("delete from global.composition where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY') ");
          //System.out.println("Comp Sizein Update "+ofCompositionData.size());
          for ( int rem=0; rem < 25; rem++ )
          {
            compositionData=new Hashtable();
            compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );
            String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
            String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );
            if ( msds_chemical_name.length() > 160 )
            {
              msds_chemical_name=msds_chemical_name.substring( 0,159 );
            }
                 //01-27-03
           if (msds_chemical_name.trim().length() > 0)
           {
           String percent = compositionData.get("PERCENT").toString();
           String percent_lower = compositionData.get("PERCENT_LOWER").toString();
           String percent_upper = compositionData.get("PERCENT_UPPER").toString();
           String hazardous = compositionData.get("HAZARDOUS").toString();
           String trade_secret = compositionData.get("TRADE_SECRET").toString();
           String remark = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(compositionData.get("REMARK").toString());
                if (remark.length() > 32){remark = remark.substring(0,31);}
           String updatecomp = "";
           updatecomp = "insert into global.composition (MATERIAL_ID,REVISION_DATE,PERCENT,PERCENT_LOWER,PERCENT_UPPER,HAZARDOUS,CAS_NUMBER,TRADE_SECRET,REMARK,MSDS_CHEMICAL_NAME,CHEMICAL_ID) values \n";
           updatecomp += "("+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),"+(percent.length()==0?"null":percent)+","+(percent_lower.length()==0?"null":percent_lower)+","+(percent_upper.length()==0?"null":percent_upper)+",'"+hazardous+"',"+(cas_number.length()==0?"null":"'"+cas_number+"'")+",'"+trade_secret+"','"+remark+"','"+msds_chemical_name+"','"+msds_chemical_name+"') \n";
                 //System.out.println(updatecomp);
                 db.doUpdate( updatecomp );
               }
               else
               {
                 continue;
               }
             }
           }*/
        msdsresult=true;
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        msdserrmsg+=e.getMessage();
        msdsresult=false;
      }

      Hashtable finalresu = new Hashtable();
      finalresu.put( "MSDS_UPDATE_FLAG",new Boolean( msdsresult ) );
      finalresu.put( "MSDS_ERROR_MSG",msdserrmsg );
      return finalresu;
}

public Hashtable updatecompositionData( Vector ofCompositionData,String material_id,String revision_date )
{
Hashtable compositionData=null;
Vector new_commpdata=new Vector();
boolean updatecomposition=false;
boolean finalresult=true;
boolean lineresult=true;
String errormsg = "";

for ( int rem=0; rem < 50; rem++ )
{
  compositionData=new Hashtable();
  compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

  String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );

  if ( msds_chemical_name.trim().length() > 0 )
  {
    updatecomposition=true;
  }
}

if ( ( material_id.length() > 0 ) && ( revision_date.length() == 10 ) && ( updatecomposition ) )
{
  try
  {
    db.doUpdate( "delete from global.composition where MATERIAL_ID = " + material_id + " and REVISION_DATE=to_date('" + revision_date + "','MM/DD/YYYY') " );
  }
  catch ( Exception ex )
  {
    //ex.printStackTrace();
    //finalresult=false;
  }

  for ( int rem=0; rem < 50; rem++ )
  {
    compositionData=new Hashtable();
    lineresult=true;
    compositionData= ( Hashtable ) ofCompositionData.elementAt( rem );

    String cas_number=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "CAS_NUMBER" ).toString() );
    String msds_chemical_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "MSDS_CHEMICAL_NAME" ).toString() );

    if ( msds_chemical_name.trim().length() > 0 )
    {
	  if ( msds_chemical_name.length() > 160 )
	  {
	  msds_chemical_name=msds_chemical_name.substring( 0,159 );
	  }

	  msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#34;","\"" );
	  msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#40;","(" );
	  msds_chemical_name=HelpObjs.findReplace( msds_chemical_name,"&#41;",")" );

      String percent=compositionData.get( "PERCENT" ).toString();
      String percent_lower=compositionData.get( "PERCENT_LOWER" ).toString();
      String percent_upper=compositionData.get( "PERCENT_UPPER" ).toString();
      /*String hazardous=compositionData.get( "HAZARDOUS" ).toString();
      String trade_secret=compositionData.get( "TRADE_SECRET" ).toString();*/
      String remark=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( compositionData.get( "REMARK" ).toString() );
      if ( remark.length() > 32 )
      {
        remark=remark.substring( 0,31 );
      }

      String updatecomp="";
      updatecomp="insert into global.composition (MATERIAL_ID,REVISION_DATE,PERCENT,PERCENT_LOWER,PERCENT_UPPER,CAS_NUMBER,REMARK,MSDS_CHEMICAL_NAME,CHEMICAL_ID) values \n";
      updatecomp+="(" + material_id + ",to_date('" + revision_date + "','MM/DD/YYYY')," + ( percent.length() == 0 ? "null" : percent ) + "," +
         ( percent_lower.length() == 0 ? "null" : percent_lower ) + "," + ( percent_upper.length() == 0 ? "null" : percent_upper ) + ","
          + ( cas_number.length() == 0 ? "null" : "'" + cas_number + "'" ) + ",'" + remark + "','" + msds_chemical_name +
         "','" + msds_chemical_name + "') \n";

      try
      {
        db.doUpdate( updatecomp );
      }
      catch ( Exception ex1 )
      {
        finalresult=false;
        lineresult=false;
        //ex1.printStackTrace();
        errormsg+=ex1.getMessage() + "<BR>";
      }

      if ( !lineresult )
      {
        compositionData.remove( "LINE_STATUS" );
        compositionData.put( "LINE_STATUS","N" );
      }
      else
      {
        compositionData.remove( "LINE_STATUS" );
        compositionData.put( "LINE_STATUS","" );
      }

      new_commpdata.addElement( compositionData );
    }
    else
    {
      continue;
    }
  }
}
Hashtable finalres = new Hashtable();
finalres.put("COMP_DATA_VECTOR",new_commpdata);
finalres.put("COMP_UPDATE_FLAG",new Boolean( finalresult ) );
finalres.put("COMP_ERROR_MSG",errormsg);

return finalres;
}
}