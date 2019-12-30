package radian.tcmis.server7.share.dbObjs;
import java.sql.ResultSet;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 03-12-04 Made Changes to accomodate Sikorsky MSDSs. These are MSDS with no material ID
 */

public class noMatSideView{
  private ServerResourceBundle sdbundle = null;
  private String heal="";
  private String flam="";
  private String react="";
  private String haz="";
  private String date="";
  private String radios="";
  private String radios2="";
  private String radios3="";
  private String content="";
  private String act="";
  private String material_desc="";
  private String flash="";
  private String boiling="";
  private String freezing="";
  private String spec_grav="";
  private String spec_id="";
  private String phy_state="";
  private String density="";
  private String trade_name="";
  private String composition="";
  private String material_name="";
  private String manufacturer="";
  private String part_no="";
  private String fac_part_no="";
  private String erg="";
  private String container="";
  private String gsn="";
  private String pack_type="";
  private String ground_ship="";
  private String air_ship="";
  private String hazard_class="";
  private String un_num="";
  private String na_num="";
  private String pack_group="";
  private String revdatedmd="";
  private String himshealth="";
  private String hmisflamma="";
  private String hmisreacti="";
  private String hmisppe="";
  private String clmsdsno="";

  //adding list
  private int displayLevel = 0;
  private String listName = "";
  private String onList = "";
  private String percent = "";
  private String listId = "";
  private String lookupListId = "";
  private Vector subListVector = new Vector();
  private String casNumber = "";
  private String rptChemical = "";
  private String subList;
  private int count=0;
  private String currentRow = "";

  //Get methods
  //Nawaz 04-20-01
  public String getclmsdsno(){
    return clmsdsno;}
  public String getrevdated(){
    return revdatedmd;}
  public String getRadios(){
    return radios;}
  public String getRadios2(){
    return radios2;}
  public String getRadios3(){
    return radios3;}
  public String getFlam(){
    return flam;}
  public String getHeal(){
    return heal;}
  public String getReact(){
    return react;}
  public String getHaz(){
    return haz;}
  public String getDate(){
    return date;}
  public String getDate2(){
    return date;}
  public String getLatestDate(){
    return date;}
  public String getAct(){
    return act;}
  public String getContent(){
    return content;}
  public String getMaterial(){
    return material_desc;}
  public String getComposition(){
    return composition;}
  public String getFlash(){
    return flash;}
  public String getBoiling(){
    return boiling;}
  public String getFreezing(){
    return freezing;}
  public String getSpecGrav(){
    return spec_grav;}
  public String getSpecId(){
    return spec_id;}
  public String getPhysicalState(){
    return phy_state;}
  public String getDensity(){
    return density;}
  public String getTradeName(){
    return trade_name;}
  public String getMaterialName(){
    return material_name;}
  public String getManufacturer(){
    return manufacturer;}
  public String getPartNo(){
    return part_no;}
  public String getFacPartNo(){
    return fac_part_no;}
  public String getErg(){
    return erg;}
  public String getContainer(){
    return container;}
  public String getGsn(){
    return gsn;}
  public String getPackType(){
    return pack_type;}
  public String getGroundShippingName(){
    return ground_ship;}
  public String getAirShippingName(){
    return air_ship;}
  public String getHazardClass(){
    return hazard_class;}
  public String getUnNumber(){
    return un_num;}
  public String getNaNumber(){
    return na_num;}
  public String getPackingGroup(){
    return pack_group;}
  public String gethimshealth(){
    return himshealth;}
  public String gethmisflamma(){
    return hmisflamma;}
  public String gethmisreacti(){
    return hmisreacti;}
  public String gethmisppe(){
    return hmisppe;}

  //list get methods
  public int getDisplayLevel() {
    return this.displayLevel;
  }

  public String getListName() {
    return this.listName;
  }

  public Vector getSubListVector() {
    return this.subListVector;
  }


  public String getOnList() {
    return this.onList;
  }

  public String getPercent() {
    return this.percent;
  }

  public String getListId() {
    return this.listId;
  }

  public String getLookupListId() {
    return this.lookupListId;
  }

  public String getCasNumber() {
    return this.casNumber;
  }

  public String getRptChemical() {
    return this.rptChemical;
  }

  public String getSubList() {
    return this.subList;
  }

  public int getCount() {
    return this.count;
  }



  //set methods

  public void setclmsdsno(String x){
    if(x==null){
      x=" ";}
    clmsdsno=x;}
  public void sethimshealth(String x){
    if(x==null){
      x="";}
    himshealth=x;}
  public void sethmisflamma(String x){
    if(x==null){
      x="";}
    hmisflamma=x;}
  public void sethmisreacti(String x){
    if(x==null){
      x="";}
    hmisreacti=x;}
  public void sethmisppe(String x){
    if(x==null){
      x="";}
    hmisppe=x;}
  private String doDateFormat(String s){
    if(BothHelpObjs.isBlankString(s)){
      return "";
    }
    return BothHelpObjs.formatDate("toCharfromDB",s);
  }
  //Set methods
  public void setrevdated(String x){
    revdatedmd=x;}
  public void setGroundShippingName(String x){
    if(x==null){
      x="&nbsp;";}
    ground_ship="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
  public void setAirShippingName(String x){
    if(x==null){
      x="&nbsp;";}
    air_ship="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
  public void setHazardClass(String x){
    if(x==null){
      x="&nbsp;";}
    hazard_class="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
  public void setUnNumber(String x){
    if(x==null){
      x="&nbsp;";}
    un_num="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
  public void setNaNumber(String x){
    if(x==null){
      x="&nbsp;";}
    na_num="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
  public void setPackingGroup(String x){
    if(x==null){
      x="&nbsp;";}
    pack_group="<FONT FACE=\"Arial\" SIZE=\"2\">"+x+"</FONT>";}
    public void setMaterialName(String x){
    if(x==null){
      x=" ";}
    material_name=x;}
  public void setManufacturer(String x){
    if(x==null){
      x=" ";}
    manufacturer=x;}
  public void setPartNo(String x){
    if(x==null){
      x=" ";}
    part_no=x;}
  public void setFacPartNo(String x){
    if(x==null){
      x=" ";}
    fac_part_no=x;}
  public void setErg(String x){
    if(x==null){
      x=" ";}
    erg=x;}
  public void setContainer(String x){
    if(x==null){
      x=" ";}
    container=x;}
  public void setGsn(String x){
    if(x==null){
      x=" ";}
    gsn=x;}
  public void setSpecId(String x){
    if(x==null){
      x=" ";}
    spec_id=x;}
  public void setPackType(String x){
    if(x==null){
      x=" ";}
    pack_type=x;}
  public void setRadios(String x){
    if(x==null){
      x=" ";}
    radios=x;}
  public void setRadios2(String x){
    if(x==null){
      x=" ";}
    radios2=x;}
  public void setRadios3(String x){
    if(x==null){
      x=" ";}
    radios3=x;}
  public void setFlam(String x){
    if(x!=null)
      flam=x;
    else
      flam="&nbsp;";
  }
  public void setHeal(String x){
    if(x!=null)
      heal=x;
    else
      heal="&nbsp;";
  }
  public void setReact(String x){
    if(x!=null)
      react=x;
    else
      react="&nbsp;";
  }
  public void setHaz(String x){
    if(x!=null)
      haz=x;
    else
      haz="&nbsp;";
  }
  public void setDate(String x){
    date=doDateFormat(x);}
  public void setDate2(String x){
    date=x;}
  public void setLatestDate(String x){
    if(x==null){
      x=" ";}
    date=x;}
  public void setAct(String x){
    if(x==null){
      x=" ";}
    act=x;}
  public void setContent(String x){
    if(x==null){
      x=" ";}
    content=x;}
  public void setMaterial(String x){
    if(x==null){
      x=" ";}
    material_desc=x;}
  public void setComposition(String x,String y,String z,String p,String q,
      String r,String s,String t,int count)
  {
    String bgcolor="";
    count++;if(count%2==0){
      bgcolor="E6E8FA";} else{
      bgcolor="ffffff";}
    if(x!=null||y!=null||z!=null||p!=null||q!=null||r!=null){
      if(x==null){
        x="&nbsp;";}
      if(y==null){
        y="&nbsp;";}
      if(z==null){
        z="&nbsp;";}
      if(p==null){
        p="&nbsp;";}
      if(q==null){
        q="&nbsp;";}
      if(s==null){
        s="&nbsp;";}
      if(t==null){
        t="&nbsp;";}
      composition+="\n<tr>\n";
      if((count==1)&&(r!=null)){
        composition+="   <td ALIGN=\"CENTER\" WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\"  SIZE=\"2\">&nbsp;"+"</FONT></td>\n<td ALIGN=\"LEFT\" WIDTH=\"55%\" BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">VOC"+"</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
            s+"</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
            t+"</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
            r+"</FONT></td>\n</tr>";
      }
      composition+="   <td ALIGN=\"CENTER\" WIDTH=\"15%\" BGCOLOR=\"#"+bgcolor+
          "\"><FONT FACE=\"Arial\"  SIZE=\"2\">"+x+
          "</FONT></td>\n<td ALIGN=\"LEFT\" WIDTH=\"55%\" BGCOLOR=\"#"+bgcolor+
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">"+y+
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#"+bgcolor+
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">"+p+
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#"+bgcolor+
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">"+q+
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#"+bgcolor+
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">"+z+"</FONT></td>\n</tr>";
    }
  }
  public void setFlash(String x){
    flash=""+x+"</FONT></td>\n</tr>";}
  public void setBoiling(String x){
    boiling=""+x+"</FONT></td>\n</tr>";}
  public void setFreezing(String x){
    freezing=""+x+"</FONT></td>\n</tr>";}
  public void setSpecGrav(String x){
    spec_grav=""+x+"</FONT></td>\n</tr>";}
  public void setPhysicalState(String x){
    phy_state=""+x+"</FONT></td>\n</tr>";}
  public void setDensity(String x){
    density=""+x+"</FONT></td>\n</tr>";}
  public void setTradeName(String x){
    trade_name=x;}

  //list set methods

  public void setDisplayLevel(int displayLevel) {
    this.displayLevel = displayLevel;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public void setOnList(String onList) {
    this.onList = onList;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }

  public void setListId(String listId) {
    this.listId = listId;
  }

  public void setSubListName(String listName,
                             String lookupListId,
                             String onList,
                             String percent) {
    Vector v = new Vector(4);
    v.add(0,listName);
    v.add(1,lookupListId);
    v.add(2, onList);
    v.add(3, percent);
    this.subListVector.add(v);
  }

  public void setLookupListId(String lookupListId) {
    this.lookupListId = lookupListId;
  }

  public void setCasNumber(String casNumber) {
    this.casNumber = casNumber;
  }


  public void setRptChemical(String rptChemical) {
    this.rptChemical = rptChemical;
  }

  public void setSubList(String subList) {
    this.subList = subList;
  }

  public void setCount(int count) {
    this.count = count;
  }



  //Constructor
  public noMatSideView(TcmISDBModel db)
  {

  }

  public noMatSideView( ServerResourceBundle b )
  {
	this.sdbundle=b;
  }

  public noMatSideView()
  {

  }

  public void setDbModel(TcmISDBModel db)
  {

  }
  // Make a month
  public static String doDate(String d2){
    if(d2.equalsIgnoreCase("01")){
      d2="JAN";} else if(d2.equalsIgnoreCase("02")){
      d2="FEB";} else if(d2.equalsIgnoreCase("03")){
      d2="MAR";} else if(d2.equalsIgnoreCase("04")){
      d2="APR";} else if(d2.equalsIgnoreCase("05")){
      d2="MAY";} else if(d2.equalsIgnoreCase("06")){
      d2="JUN";} else if(d2.equalsIgnoreCase("07")){
      d2="JUL";} else if(d2.equalsIgnoreCase("08")){
      d2="AUG";} else if(d2.equalsIgnoreCase("09")){
      d2="SEP";} else if(d2.equalsIgnoreCase("10")){
      d2="OCT";} else if(d2.equalsIgnoreCase("11")){
      d2="NOV";} else if(d2.equalsIgnoreCase("12")){
      d2="DEC";}
    return d2;
  }

  public Vector getSideViewVector(TcmISDBModel db,String rev_date,String id,String facility) throws Exception
  {
    Vector v=new Vector();
    //String clientt=db.getClient().toString().trim();
    String query="";
    String content1="";
    String tempdate12="";

    if(!(rev_date==null||rev_date.equalsIgnoreCase("null"))){
      tempdate12=rev_date;
    }

    if (facility.trim().length() > 0)
	{
	  query="select fx_company_catalog_msds_id (" + id + ",'" + facility + "') MSDS_NO,on_line,fac_msds_no MSDS_NO,Content, boiling_point, flash_point, density, ";
	  query+="freezing_point, specific_gravity, physical_state, flammability, health, reactivity, specific_hazard, HMIS_HEALTH, HMIS_FLAMMABILITY, HMIS_REACTIVITY, ";
	  query+="PERSONAL_PROTECTION, revision_date from noncatalog_msds_data m where fac_msds_no='" +	id + "' and facility_id='"+facility+"' and ON_LINE = 'Y' order by revision_date desc";
	}

    DBResultSet dbrs=null;
    ResultSet rs=null;
    int Ki=0;
    int count=0;
    //Hashtable result=new Hashtable();
    try{
      dbrs=db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        noMatSideView sv=new noMatSideView(db);
        content1=rs.getString("content");
        //if ( ! ( content1.endsWith( ".pdf" ) && clientt.equalsIgnoreCase( "BAE" ) ) || showpdffiles )
        {
          sv.setContent( rs.getString( "content" ) );

          if ( rs.getString( "MSDS_NO" ) != null )
          {
            sv.setclmsdsno( rs.getString( "MSDS_NO" ) );
          }
          else
          {
            sv.setclmsdsno( "" );
          }

          if ( rs.getString( "boiling_point" ) != null )
          {
            sv.setBoiling( rs.getString( "boiling_point" ) );
          }
          else
          {
            sv.setBoiling( "none" );
          }
          if ( rs.getString( "flash_point" ) != null )
          {
            sv.setFlash( rs.getString( "flash_point" ) );
          }
          else
          {
            sv.setFlash("none");
          }

		  if ( rs.getString( "density" ) != null )
		  {
			sv.setDensity( rs.getString( "density" ) );
		  }
		  else
		  {
			sv.setDensity( "none" );
		  }
		  if ( rs.getString( "freezing_point" ) != null )
		  {
			sv.setFreezing( rs.getString( "freezing_point" ) );
		  }
		  else
		  {
			sv.setFreezing( "none" );
		  }
		  if ( rs.getString( "specific_gravity" ) != null )
		  {
			sv.setSpecGrav( rs.getString( "specific_gravity" ) );
		  }
		  else
		  {
			sv.setSpecGrav( "none" );
		  }
		  if ( rs.getString( "physical_state" ) != null )
		  {
			sv.setPhysicalState( rs.getString( "physical_state" ) );
		  }
		  else
		  {
			sv.setPhysicalState( "none" );
		  }

		  sv.setFlam(rs.getString("flammability"));
          sv.setHeal(rs.getString("health"));
          sv.setReact(rs.getString("reactivity"));
          sv.setHaz(rs.getString("specific_hazard"));
          sv.sethimshealth(rs.getString("HMIS_HEALTH"));
          sv.sethmisflamma(rs.getString("HMIS_FLAMMABILITY"));
          sv.sethmisreacti(rs.getString("HMIS_REACTIVITY"));
          sv.sethmisppe(rs.getString("PERSONAL_PROTECTION"));
          sv.setDate(rs.getString("revision_date"));
          String d1=rs.getString("revision_date").substring(0,4);
          String d2=rs.getString("revision_date").substring(5,7);
          String d3=rs.getString("revision_date").substring(8,10);
          d2=doDate(d2);
          String tempdate=d3+"/"+d2+"/"+d1;
          sv.setLatestDate(tempdate);
		  if ( (count == 0) && (rev_date==null||rev_date.equalsIgnoreCase("null")) ){tempdate12 = tempdate;}

          sv.setrevdated(tempdate12);
          String test=BothHelpObjs.makeBlankFromNull(rs.getString("on_line"));
          String selected="";
          if((tempdate12.equalsIgnoreCase(tempdate))||(Ki==0)){
            selected="selected";
          }
          if(test.equalsIgnoreCase("N")){
            sv.setRadios("notav");
          } else{
            sv.setRadios(""+tempdate+"");
          }
          Ki=Ki+1;
          count+=1;
          v.addElement(sv);
        }
      }
    } catch(Exception e){
      e.printStackTrace();
      throw e;
    } finally{
      dbrs.close();
    }

    if(count==0){
      Vector v1=new Vector();
      String testmsg="No Records";
      v1.add(testmsg);
      return v1;
    }
    return v;
  }
  //Query the database and return values
  public Vector getSpecVector(TcmISDBModel db,String id,String catpartno,String facility) throws Exception
  {
    Vector v=new Vector();
    String client=db.getClient().toString().trim();
    String query="";
    int count=0;

    if ( catpartno.length() > 0 && facility.length() > 0 )
    {
      try
      {
        query=" select s.spec_id, c.catalog_id from catalog_part_item_group l, fac_spec s, catalog_facility c ";
        query+=" where l.catalog_id=c.catalog_id and l.catalog_id=s.facility_id and ";
        query+=" l.cat_part_no=s.fac_part_no and l.status in ('A','D') and ";
        query+=" l.CAT_PART_NO='" + catpartno + "' and c.facility_id='" + facility + "' ";
      }
      catch ( Exception ex )
      {
        ex.printStackTrace();
      }

      DBResultSet dbrs=null;
      ResultSet rs=null;

      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          noMatSideView sv=new noMatSideView( db );
          String qurl="";
          if ( facility == null || facility.length() == 0 )
          {
            qurl=qurl + "&facility=";
          }
          else
          {
            qurl=qurl + "&facility=" + BothHelpObjs.addSpaceForUrl( facility );
          }
          if ( catpartno == null || catpartno.length() == 0 )
          {
            qurl=qurl + "&catpartno=";
          }
          else
          {
            qurl=qurl + "&catpartno=" + catpartno;
          }

		  String msdsservlet = sdbundle.getString("VIEW_MSDS");
          sv.setRadios2( "<option value=\""+msdsservlet+"SESSION=4&act=spec&id=" + id + "&spec=" + rs.getString( "spec_id" ) + "" + qurl + "*data\">" + rs.getString( "spec_id" ) + "</option>\n" );
          count+=1;
          v.addElement( sv );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
        dbrs.close();
      }
    }

    if ( count == 0 )
    {
      Vector v1=new Vector();
      String testmsg="No Records";
      v1.add( testmsg );
      return v1;
    }

    return v;
  }

  //adding this method since I don't want to change the method signature
  //of the original method
  public Vector getContentVector(TcmISDBModel db,
                                 String rev_date,
                                 String id,
                                 String act,
                                 String spec,
                                 String catpartno,
                                 String facility,
                                 String listId,
                                 String lookupListId,
                                 String currentRow)
      throws Exception {
    this.listId = listId;
    this.lookupListId = lookupListId;
    this.currentRow = currentRow;
    return getContentVector(db, rev_date, id, act, spec, catpartno, facility);
  }


  public Vector getContentVector( TcmISDBModel db,String rev_date,String id,String act,String spec,String catpartno,String facility ) throws Exception
  {
	Vector v=new Vector();
	String query="";
	String query2="";
	String query3="";
	String query4="";
	String densityunit="";
	String flashpoint="";
	String clientt=db.getClient().toString().trim();
	//boolean showpdffiles=false;

	try
	{
	  if ( id == null )
	  {
		id="0";
	  }

	  if ( !act.equalsIgnoreCase( "title" ) )
	  {
		query4="select m.TRADE_NAME, m.mfg_desc from noncatalog_material_data m where m.fac_msds_no = '" + id + "' and facility_id='"+facility+"'";
	  }

	  if ( act.equalsIgnoreCase( "msds" ) )
	  {
		if ( !rev_date.equalsIgnoreCase( "null" ) )
		{
		  query="select content,on_line from noncatalog_msds_data where fac_msds_no='" + id +"' and REVISION_DATE='" + rev_date + "' and ON_LINE = 'Y'";
		}
		else
		{
		  query="select content,on_line from noncatalog_msds_data where fac_msds_no='" + id +"' and ON_LINE = 'Y' order by revision_date asc";
		}
	  }
	  else if ( act.equalsIgnoreCase( "comp" ) )
	  {
		if ( !rev_date.equalsIgnoreCase( "null" ) )
		{
		  query="select TRADE_NAME,cas_number,CHEMICAL_ID,percent,PERCENT_LOWER,PERCENT_UPPER, round(VOC_PERCENT, 1) VOC_PERCENT,round(VOC_LOWER_PERCENT,1) VOC_LOWER_PERCENT ,round(VOC_UPPER_PERCENT,1)";
		  query+=" VOC_UPPER_PERCENT from noncatalog_composition_data x,noncatalog_msds_data y ,noncatalog_material_data z ";
		  query+=" where x.fac_msds_no='"+id+"'  and x.facility_id='"+facility+"' and x.REVISION_DATE= to_date('"+rev_date+"','dd/Mon/yyyy') and ";
          query+=" x.fac_msds_no=y.fac_msds_no and  x.FACILITY_ID=y.facility_id and  x.fac_msds_no=y.fac_msds_no and x.fac_msds_no=y.fac_msds_no and ";
          query+=" x.REVISION_DATE=y.REVISION_DATE and  x.FACILITY_ID=z.facility_id and  x.fac_msds_no=z.fac_msds_no  order by percent desc ";
		}
	  }
	  else if ( act.equalsIgnoreCase( "title" ) )
	  {
		query2="select m.TRADE_NAME from noncatalog_material_data m where m.fac_msds_no = '" + id + "' and facility_id='"+facility+"'";
		if ( !rev_date.equalsIgnoreCase( "null" ) )
		{
		  query="select content,on_line,revision_date from noncatalog_msds_data where fac_msds_no='" + id +"' and REVISION_DATE='" + rev_date +"' and ON_LINE = 'Y' order by revision_date asc";
		  query3="select content,on_line from noncatalog_msds_data where fac_msds_no='" + id + "' and REVISION_DATE='" + rev_date + "' and ON_LINE = 'Y'";
		}
		else
		{
		  query="select content,on_line,revision_date from noncatalog_msds_data where fac_msds_no='" + id +"'  and ON_LINE = 'Y' order by revision_date asc";
		  query3="select content,on_line,revision_date from noncatalog_msds_data where fac_msds_no='" + id + "'  and ON_LINE = 'Y' order by revision_date asc";
		}
	  }
	  else if ( act.equalsIgnoreCase( "prop" ) )
	  {
		if ( !rev_date.equalsIgnoreCase( "null" ) )
		{
		  query="select freezing_point,specific_gravity,physical_state,boiling_point,flash_point,flash_point_unit,density,density_unit,";
		  query+="trade_name,ground_shipping_name,air_shipping_name,hazard_class,un_number,na_number,packing_group ";
		  query+="from noncatalog_msds_data y ,noncatalog_material_data z ";
		  query+="where y.fac_msds_no='"+id+"'  and y.facility_id='"+facility+"' and y.REVISION_DATE= to_date('"+rev_date+"','dd/Mon/yyyy') and ";
		  query+="y.FACILITY_ID=z.facility_id and ";
		  query+="y.fac_msds_no=z.fac_msds_no ";
		}
	  }
	  else if ( act.equalsIgnoreCase( "lists" ) || act.equalsIgnoreCase( "subList" ) )
	  {
		query="select * from msds_viewer_list_view where display_level = 0 and material_id=" + id + " and REVISION_DATE='" + rev_date + "' ORDER BY LIST_NAME";
		if ( act.equalsIgnoreCase( "subList" ) )
		{
		  query2="select * from msds_viewer_list_view where display_level = 1 and list_id='" + listId + "' and material_id='" + id + "' and REVISION_DATE='" + rev_date + "' ORDER BY LIST_NAME";
		}
	  }
	  else if ( act.equalsIgnoreCase( "compound" ) )
	  {
		int min=1;
		int max=200;
		if ( currentRow == null || currentRow.equals( "0" ) )
		{
		  min=1;
		  max=200;
		}
		else
		{
		  min+=new Integer( currentRow ).intValue();
		  max+=new Integer( currentRow ).intValue();
		}
		query="select row_num, cas_number, rpt_chemical, (select count(*) from report_list_snap where list_id = '" + lookupListId + "') count from (select rownum row_num, cas_number, rpt_chemical from report_list_snap where list_id = '" +lookupListId + "' order by cas_number) where row_num between " + min + " and " + max;
	  }
	  else if ( act.equalsIgnoreCase( "compoundpercent" ) )
	  {
		int min=1;
		int max=200;
		if ( currentRow == null || currentRow.equals( "0" ) )
		{
		  min=1;
		  max=200;
		}
		else
		{
		  min+=new Integer( currentRow ).intValue();
		  max+=new Integer( currentRow ).intValue();
		}
		query="select * from msds_viewer_list_comp_view where material_id = " + id + " and revision_date = '" + rev_date + "' and list_id = '" + listId + "'";
	  }
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	}

	DBResultSet dbrs=null;
	DBResultSet dbrs2=null;
	DBResultSet dbrs3=null;
	DBResultSet dbrs4=null;
	ResultSet rs=null;
	ResultSet rs2=null;
	ResultSet rs3=null;
	ResultSet rs4=null;

	int count=0;
	try
	{
	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();
	  if ( query2 != "" )
	  {
		dbrs2=db.doQuery( query2 );
		rs2=dbrs2.getResultSet();
	  }
	  if ( query3 != "" )
	  {
		dbrs3=db.doQuery( query3 );
		rs3=dbrs3.getResultSet();
	  }
	  if ( query4 != "" )
	  {
		dbrs4=db.doQuery( query4 );
		rs4=dbrs4.getResultSet();
	  }

	  String wwwhomeurl=radian.web.tcmisResourceLoader.gethosturl();
	  String hold=wwwhomeurl + "gen/msds_not_found.html";

	  noMatSideView sv=null;
	  while ( rs.next() )
	  {
		sv=new noMatSideView( db );
		if ( act.equalsIgnoreCase( "msds" ) )
		{
		  String test=BothHelpObjs.makeBlankFromNull( rs.getString( "on_line" ) );
		  if ( test.equalsIgnoreCase( "N" ) )
		  {
			sv.setContent( hold );
		  }
		  else
		  {
			sv.setContent( rs.getString( "content" ) );
		  }
		}
		else if ( act.equalsIgnoreCase( "title" ) )
		{
		  while ( rs2.next() )
		  {
			sv.setMaterial( rs2.getString( "TRADE_NAME" ) );
		  }
		  {
			String d1=rs.getString( "revision_date" ).substring( 0,4 );
			String d2=rs.getString( "revision_date" ).substring( 5,7 );
			String d3=rs.getString( "revision_date" ).substring( 8,10 );
			d2=doDate( d2 );
			String tempdate=d3 + "/" + d2 + "/" + d1;
			sv.setDate2( tempdate );
		  }
		  while ( rs3.next() )
		  {
			sv.setContent( rs3.getString( "content" ) );
		  }
		}
		else if ( act.equalsIgnoreCase( "comp" ) )
		{
		  while ( rs4.next() )
		  {
			sv.setManufacturer( rs4.getString( "MFG_DESC" ) );
			sv.setTradeName( rs4.getString( "TRADE_NAME" ) );
		  }
		  sv.setMaterial( rs.getString( "TRADE_NAME" ) );
		  sv.setComposition( rs.getString( "cas_number" ),
							 rs.getString( "CHEMICAL_ID" ),rs.getString( "percent" ),
							 rs.getString( "PERCENT_LOWER" ),rs.getString( "PERCENT_UPPER" ),
							 rs.getString( "VOC_PERCENT" ),rs.getString( "VOC_LOWER_PERCENT" ),
							 rs.getString( "VOC_UPPER_PERCENT" ),count );
		}
		else if ( act.equalsIgnoreCase( "prop" ) )
		{
		  while ( rs4.next() )
		  {
			sv.setManufacturer( rs4.getString( "MFG_DESC" ) );
			sv.setTradeName( rs4.getString( "TRADE_NAME" ) );
		  }
		  sv.setTradeName( rs.getString( "trade_name" ) );
		  sv.setBoiling( rs.getString( "boiling_point" ) );
		  sv.setFreezing( rs.getString( "freezing_point" ) );
		  sv.setSpecGrav( rs.getString( "specific_gravity" ) );
		  sv.setPhysicalState( rs.getString( "physical_state" ) );
		  densityunit=rs.getString( "DENSITY" ) + " " + ( rs.getString( "DENSITY_UNIT" ) == null ?  " " : rs.getString( "DENSITY_UNIT" ) );
		  flashpoint=rs.getString( "flash_point" ) + " " + ( rs.getString( "flash_point_unit" ) == null ? " " : rs.getString( "flash_point_unit" ) );
		  sv.setFlash( flashpoint );
		  sv.setDensity( densityunit );
		  sv.setGroundShippingName( rs.getString( "ground_shipping_name" ) );
		  sv.setAirShippingName( rs.getString( "air_shipping_name" ) );
		  sv.setHazardClass( rs.getString( "hazard_class" ) );
		  sv.setUnNumber( rs.getString( "un_number" ) );
		  sv.setNaNumber( rs.getString( "na_number" ) );
		  sv.setPackingGroup( rs.getString( "packing_group" ) );
		}
		else if ( act.equalsIgnoreCase( "lists" ) || act.equalsIgnoreCase( "subList" ) )
		{
		  while ( rs4.next() )
		  {
			sv.setManufacturer( rs4.getString( "MFG_DESC" ) );
			sv.setTradeName( rs4.getString( "TRADE_NAME" ) );
		  }
		  sv.setListId( rs.getString( "LIST_ID" ) );
		  sv.setPercent( rs.getString( "PERCENT" ) );
		  sv.setDisplayLevel( rs.getInt( "DISPLAY_LEVEL" ) );
		  sv.setListName( rs.getString( "LIST_NAME" ) );
		  sv.setOnList( rs.getString( "ON_LIST" ) );
		  sv.setSubList( rs.getString( "SUBLIST" ) );
		  if ( act.equalsIgnoreCase( "subList" ) && listId.equalsIgnoreCase( rs.getString( "LIST_ID" ) ) )
		  {
			while ( rs2.next() )
			{
			  sv.setSubListName( rs2.getString( "LIST_NAME" ),
								 rs2.getString( "LOOKUP_LIST_ID" ),
								 rs2.getString( "ON_LIST" ),
								 rs2.getString( "PERCENT" ) );
			}
		  }
		}
		else if ( act.equalsIgnoreCase( "compound" ) )
		{
		  sv.setCasNumber( rs.getString( "CAS_NUMBER" ) );
		  sv.setRptChemical( rs.getString( "RPT_CHEMICAL" ) );
		  sv.setCount( rs.getInt( "COUNT" ) );
		}
		else if ( act.equalsIgnoreCase( "compoundpercent" ) )
		{
		  sv.setCasNumber( rs.getString( "CAS_NUMBER" ) );
		  sv.setRptChemical( rs.getString( "RPT_CHEMICAL" ) );
		  sv.setPercent( rs.getString( "PERCENT" ) );
		}
		count+=1;
		v.addElement( sv );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  throw e;
	}
	finally
	{
	  if ( dbrs2 != null )
	  {
		try
		{
		  dbrs2.close();
		  dbrs3.close();
		  dbrs4.close();
		}
		catch ( Exception sqle )
		{
		  //ignore
		}
	  }
	  dbrs.close();
	}
	if ( count == 0 )
	{
	  Vector v1=new Vector();
	  String testmsg="No Records";
	  v1.add( testmsg );
	  return v1;
	}
	return v;
  }
}
