package radian.tcmis.server7.share.dbObjs;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 *
 * 12-30-02 adding facility fo that BAE will be able to see a PDF MSDS if there is no txt file is available
 * 07-15-03 Adding the Client MSDS number
 * 09-01-03 - taking out /tcmIS in the code
 * 10-06-03 - When building the side dropdown box for the first time when there is no rev date setting the rev date
 * to the latest date so that the print button show up on the MSDs screen.
 * 11-17-03 - Changed the function used to get the msds coustomer number
 */
public class SideView {
  private ServerResourceBundle sdbundle = null;
  private String heal = "";
  private String flam = "";
  private String react = "";
  private String haz = "";
  private String date = "";
  private String radios = "";
  private String radios2 = "";
  private String radios3 = "";
  private String content = "";
  private String act = "";
  private String material_desc = "";
  private String flash = "";
  private String boiling = "";
  private String freezing = "";
  private String spec_grav = "";
  private String spec_id = "";
  private String phy_state = "";
  private String density = "";
  private String trade_name = "";
  private String composition = "";
  private String material_name = "";
  private String manufacturer = "";
  private String part_no = "";
  private String fac_part_no = "";
  private String erg = "";
  private String container = "";
  private String gsn = "";
  private String pack_type = "";
  private String ground_ship = "";
  private String air_ship = "";
  private String hazard_class = "";
  private String un_num = "";
  private String na_num = "";
  private String pack_group = "";
  private String revdatedmd = "";
  private String himshealth = "";
  private String hmisflamma = "";
  private String hmisreacti = "";
  private String hmisppe = "";
  private String clmsdsno = "";

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
  private int count = 0;
  private String currentRow = "";
  private String locale = "";
  private String localeCode = "";
  private String browserLocaleCode = "";

  private String compatibility="";
  private String densityUnit="";
  private String eyes="";
  private String flashPointUnit="";
  private String ingestion="";
  private String inhalation="";
  private String injection="";
  private String oshaHazard="";
  private String personalProtection="";
  private String ph="";
  private String ppe="";
  private String routeOfEntry="";
  private String sara311312Acute="";
  private String sara311312Chronic="";
  private String sara311312Fire="";
  private String sara311312Pressure="";
  private String sara311312Reactivity="";
  private String signalWord="";
  private String skin="";
  private String solids="";
  private String solidsLower="";
  private String solidsUpper="";
  private String solidsUnit="";
  private String targetOrgan="";
  private String tsca12B="";
  private String tscaList="";
  private String vaporPressure="";
  private String vaporPressureDetect="";
  private String vaporPressureLower="";
  private String vaporPressureSource="";
  private String vaporPressureTemp="";
  private String vaporPressureTempUnit="";
  private String vaporPressureUnit="";
  private String vaporPressureUpper="";
  private String voc="";
  private String vocLower="";
  private String vocUpper="";
  private String vocUnit="";
  private String vocLessH2OExempt="";
  private String vocLessH2OExemptLower="";
  private String vocLessH2OExemptUpper="";
  private String vocLessH2OExemptUnit="";
  private String vocPercent="";
  private String chronic="";
  private String lfcCode="";
  private String polymerize="";
  private String incompatible="";
  private String corrosive="";
  private String healthEffects="";
  private String stable="";
  private String vaporDensity="";
  private String evaporationRate="";
  private String fireConditionsToAvoid="";
  private String alloy="";
  private String revDateTime="";

  //Get methods
  //Nawaz 04-20-01
  public String getclmsdsno() {
    return clmsdsno;
  }

  public String getrevdated() {
    return revdatedmd;
  }

  public String getRadios() {
    return radios;
  }

  public String getRadios2() {
    return radios2;
  }

  public String getRadios3() {
    return radios3;
  }

  public String getFlam() {
    return flam;
  }

  public String getHeal() {
    return heal;
  }

  public String getReact() {
    return react;
  }

  public String getHaz() {
    return haz;
  }

  public String getDate() {
    return date;
  }

  public String getDate2() {
    return date;
  }

  public String getLatestDate() {
    return date;
  }

  public String getAct() {
    return act;
  }

  public String getContent() {
    return content;
  }

  public String getMaterial() {
    return material_desc;
  }

  public String getComposition() {
    return composition;
  }

  public String getFlash() {
    return flash;
  }

  public String getBoiling() {
    return boiling;
  }

  public String getFreezing() {
    return freezing;
  }

  public String getSpecGrav() {
    return spec_grav;
  }

  public String getSpecId() {
    return spec_id;
  }

  public String getPhysicalState() {
    return phy_state;
  }

  public String getDensity() {
    return density;
  }

  public String getTradeName() {
    return trade_name;
  }

  public String getMaterialName() {
    return material_name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getPartNo() {
    return part_no;
  }

  public String getFacPartNo() {
    return fac_part_no;
  }

  public String getErg() {
    return erg;
  }

  public String getContainer() {
    return container;
  }

  public String getGsn() {
    return gsn;
  }

  public String getPackType() {
    return pack_type;
  }

  public String getGroundShippingName() {
    return ground_ship;
  }

  public String getAirShippingName() {
    return air_ship;
  }

  public String getHazardClass() {
    return hazard_class;
  }

  public String getUnNumber() {
    return un_num;
  }

  public String getNaNumber() {
    return na_num;
  }

  public String getPackingGroup() {
    return pack_group;
  }

  public String gethimshealth() {
    return himshealth;
  }

  public String gethmisflamma() {
    return hmisflamma;
  }

  public String gethmisreacti() {
    return hmisreacti;
  }

  public String gethmisppe() {
    return hmisppe;
  }

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

  public String getLocale() {
    return this.locale;
  }

  public String getLocaleCode() {
    return this.localeCode;
  }

  public String getBrowserLocaleCode() {
    return this.browserLocaleCode;
  }

	public String getCompatibility() {
		return compatibility;
	}

	public String getDensityUnit() {
		return densityUnit;
	}

	public String getEyes() {
		return eyes;
	}

	public String getFlashPointUnit() {
		return flashPointUnit;
	}

	public String getIngestion() {
		return ingestion;
	}

	public String getInhalation() {
		return inhalation;
	}

	public String getInjection() {
		return injection;
	}

	public String getOshaHazard() {
		return oshaHazard;
	}

	public String getPersonalProtection() {
		return personalProtection;
	}

	public String getPh() {
		return ph;
	}

	public String getPpe() {
		return ppe;
	}

	public String getRouteOfEntry() {
		return routeOfEntry;
	}

	public String getSara311312Acute() {
		return sara311312Acute;
	}

	public String getSara311312Chronic() {
		return sara311312Chronic;
	}

	public String getSara311312Fire() {
		return sara311312Fire;
	}

	public String getSara311312Pressure() {
		return sara311312Pressure;
	}

	public String getSara311312Reactivity() {
		return sara311312Reactivity;
	}

	public String getSignalWord() {
		return signalWord;
	}

	public String getSkin() {
		return skin;
	}

	public String getSolids() {
		return solids;
	}

	public String getSolidsLower() {
		return solidsLower;
	}

	public String getSolidsUpper() {
		return solidsUpper;
	}

	public String getSolidsUnit() {
		return solidsUnit;
	}

	public String getTargetOrgan() {
		return targetOrgan;
	}

	public String getTsca12B() {
		return tsca12B;
	}

	public String getTscaList() {
		return tscaList;
	}

	public String getVaporPressure() {
		return vaporPressure;
	}

	public String getVaporPressureDetect() {
		return vaporPressureDetect;
	}

	public String getVaporPressureLower() {
		return vaporPressureLower;
	}

	public String getVaporPressureSource() {
		return vaporPressureSource;
	}

	public String getVaporPressureTemp() {
		return vaporPressureTemp;
	}

	public String getVaporPressureTempUnit() {
		return vaporPressureTempUnit;
	}

	public String getVaporPressureUnit() {
		return vaporPressureUnit;
	}

	public String getVaporPressureUpper() {
		return vaporPressureUpper;
	}

	public String getVoc() {
		return voc;
	}

	public String getVocLower() {
		return vocLower;
	}

	public String getVocUpper() {
		return vocUpper;
	}

	public String getVocUnit() {
		return vocUnit;
	}

	public String getVocLessH2OExempt() {
		return vocLessH2OExempt;
	}

	public String getVocLessH2OExemptLower() {
		return vocLessH2OExemptLower;
	}

	public String getVocLessH2OExemptUpper() {
		return vocLessH2OExemptUpper;
	}

	public String getVocLessH2OExemptUnit() {
		return vocLessH2OExemptUnit;
	}

	public String getVocPercent() {
		return vocPercent;
	}
	
	public String getRevDateTime() {
		return revDateTime;
	}
	
	//set methods
	
  public void setRevDateTime(String revDateTime) {
    this.revDateTime = revDateTime;
  }

	
  public void setLocale(String locale) {
    this.locale = locale;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  public void setBrowserLocaleCode(String browserLocaleCode) {
    this.browserLocaleCode = browserLocaleCode;
  }

  public void setclmsdsno(String x) {
    if (x == null) {
      x = " ";
    }
    clmsdsno = x;
  }

  public void sethimshealth(String x) {
    if (x == null) {
      x = "";
    }
    himshealth = x;
  }

  public void sethmisflamma(String x) {
    if (x == null) {
      x = "";
    }
    hmisflamma = x;
  }

  public void sethmisreacti(String x) {
    if (x == null) {
      x = "";
    }
    hmisreacti = x;
  }

  public void sethmisppe(String x) {
    if (x == null) {
      x = "";
    }
    hmisppe = x;
  }

  private String doDateFormat(String s) {
    if (BothHelpObjs.isBlankString(s)) {
      return "";
    }
    return BothHelpObjs.formatDate("toCharfromDB", s);
  }

  //Set methods
  public void setrevdated(String x) {
    revdatedmd = x;
  }

  public void setGroundShippingName(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    ground_ship = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setAirShippingName(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    air_ship = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setHazardClass(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    hazard_class = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setUnNumber(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    un_num = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setNaNumber(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    na_num = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setPackingGroup(String x) {
    if (x == null) {
      x = "&nbsp;";
    }
    pack_group = "<FONT FACE=\"Arial\" SIZE=\"2\">" + x + "</FONT>";
  }

  public void setMaterialName(String x) {
    if (x == null) {
      x = " ";
    }
    material_name = x;
  }

  public void setManufacturer(String x) {
    if (x == null) {
      x = " ";
    }
    manufacturer = x;
  }

  public void setPartNo(String x) {
    if (x == null) {
      x = " ";
    }
    part_no = x;
  }

  public void setFacPartNo(String x) {
    if (x == null) {
      x = " ";
    }
    fac_part_no = x;
  }

  public void setErg(String x) {
    if (x == null) {
      x = " ";
    }
    erg = x;
  }

  public void setContainer(String x) {
    if (x == null) {
      x = " ";
    }
    container = x;
  }

  public void setGsn(String x) {
    if (x == null) {
      x = " ";
    }
    gsn = x;
  }

  public void setSpecId(String x) {
    if (x == null) {
      x = " ";
    }
    spec_id = x;
  }

  public void setPackType(String x) {
    if (x == null) {
      x = " ";
    }
    pack_type = x;
  }

  public void setRadios(String x) {
    if (x == null) {
      x = " ";
    }
    radios = x;
  }

  public void setRadios2(String x) {
    if (x == null) {
      x = " ";
    }
    radios2 = x;
  }

  public void setRadios3(String x) {
    if (x == null) {
      x = " ";
    }
    radios3 = x;
  }

  public void setFlam(String x) {
    if (x != null) {
      flam = x;
    } else {
      flam = "&nbsp;";
    }
  }

  public void setHeal(String x) {
    if (x != null) {
      heal = x;
    } else {
      heal = "&nbsp;";
    }
  }

  public void setReact(String x) {
    if (x != null) {
      react = x;
    } else {
      react = "&nbsp;";
    }
  }

  public void setHaz(String x) {
    if (x != null) {
      haz = x;
    } else {
      haz = "&nbsp;";
    }
  }

  public void setDate(String x) {
    date = doDateFormat(x);
  }

  public void setDate2(String x) {
    date = x;
  }

  public void setLatestDate(String x) {
    if (x == null) {
      x = " ";
    }
    date = x;
  }

  public void setAct(String x) {
    if (x == null) {
      x = " ";
    }
    act = x;
  }

  public void setContent(String x) {
	String wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();
	  
	if (x == null) {
    	content = " ";
    }
    else if(wwwhomeurl.contains("cn"))	{
    	content = x.replace("www", "cn");
    }
    else {
    	content = x;
    }
    
  }

  public void setMaterial(String x) {
    if (x == null) {
      x = " ";
    }
    material_desc = x;
  }

	public void setComposition(String x, String y, String z, String p, String q, String r,
                             String s, String t, int count) {
    String bgcolor = "";
    count++;
    if (count % 2 == 0) {
      bgcolor = "E6E8FA";
    } else {
      bgcolor = "ffffff";
    }
    if (x != null || y != null || z != null || p != null || q != null || r != null) {
      if (x == null) {
        x = "&nbsp;";
      }
      if (y == null) {
        y = "&nbsp;";
      }
      if (z == null) {
        z = "&nbsp;";
      }
      if (p == null) {
        p = "&nbsp;";
      }
      if (q == null) {
        q = "&nbsp;";
      }
      if (s == null) {
        s = "&nbsp;";
      }
      if (t == null) {
        t = "&nbsp;";
      }
      composition += "\n<tr>\n";
		 /*moved VOC into properties
		if ( (count == 1) && (r != null)) {
        composition += "   <td ALIGN=\"CENTER\" WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\"  SIZE=\"2\">&nbsp;" + "</FONT></td>\n<td ALIGN=\"LEFT\" WIDTH=\"55%\" BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">VOC" +
            "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
            s + "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
            t + "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
            r + "</FONT></td>\n</tr>";
      }  */
      composition += "   <td ALIGN=\"CENTER\" WIDTH=\"15%\" BGCOLOR=\"#" + bgcolor +
          "\"><FONT FACE=\"Arial\"  SIZE=\"2\">" + x +
          "</FONT></td>\n<td ALIGN=\"LEFT\" WIDTH=\"55%\" BGCOLOR=\"#" + bgcolor +
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">" + y +
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#" + bgcolor +
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">" + p +
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#" + bgcolor +
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">" + q +
          "</FONT></td>\n<td ALIGN=\"CENTER\" WIDTH=\"10%\"BGCOLOR=\"#" + bgcolor +
          "\"><FONT FACE=\"Arial\" SIZE=\"2\">" + z + "</FONT></td>\n</tr>";
    }
  }

  public void setFlash(String x) {
    flash = "" + x + "</FONT></td>\n</tr>";
  }

  public void setBoiling(String x) {
    boiling = "" + x + "</FONT></td>\n</tr>";
  }

  public void setFreezing(String x) {
    freezing = "" + x + "</FONT></td>\n</tr>";
  }

  public void setSpecGrav(String x) {
    spec_grav = "" + x + "</FONT></td>\n</tr>";
  }

  public void setPhysicalState(String x) {
    phy_state = "" + x + "</FONT></td>\n</tr>";
  }

  public void setDensity(String x) {
    density = "" + x + "</FONT></td>\n</tr>";
  }

	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}

	public void setDensityUnit(String densityUnit) {
		this.densityUnit = densityUnit;
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public void setIngestion(String ingestion) {
		this.ingestion = ingestion;
	}

	public void setInhalation(String inhalation) {
		this.inhalation = inhalation;
	}

	public void setInjection(String injection) {
		this.injection = injection;
	}

	public void setOshaHazard(String oshaHazard) {
		this.oshaHazard = oshaHazard;
	}

	public void setPersonalProtection(String personalProtection) {
		this.personalProtection = personalProtection;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public void setPpe(String ppe) {
		this.ppe = ppe;
	}

	public void setRouteOfEntry(String routeOfEntry) {
		this.routeOfEntry = routeOfEntry;
	}

	public void setSara311312Acute(String sara311312Acute) {
		this.sara311312Acute = sara311312Acute;
	}

	public void setSara311312Chronic(String sara311312Chronic) {
		this.sara311312Chronic = sara311312Chronic;
	}

	public void setSara311312Fire(String sara311312Fire) {
		this.sara311312Fire = sara311312Fire;
	}

	public void setSara311312Pressure(String sara311312Pressure) {
		this.sara311312Pressure = sara311312Pressure;
	}

	public void setSara311312Reactivity(String sara311312Reactivity) {
		this.sara311312Reactivity = sara311312Reactivity;
	}

	public void setSignalWord(String signalWord) {
		this.signalWord = signalWord;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public void setSolids(String solids) {
		this.solids = solids;
	}

	public void setTargetOrgan(String targetOrgan) {
		this.targetOrgan = targetOrgan;
	}

	public void setTsca12B(String tsca12B) {
		this.tsca12B = tsca12B;
	}

	public void setTscaList(String tscaList) {
		this.tscaList = tscaList;
	}

	public void setVaporPressure(String vaporPressure) {
		this.vaporPressure = vaporPressure;
	}

	public void setVaporPressureDetect(String vaporPressureDetect) {
		this.vaporPressureDetect = vaporPressureDetect;
	}

	public void setVoc(String voc) {
		this.voc = voc;
	}

	public void setVocLessH2OExempt(String vocLessH2OExempt) {
		this.vocLessH2OExempt = vocLessH2OExempt;
	}

	public void setVocPercent(String vocPercent) {
		this.vocPercent = vocPercent;
	}

  public void setTradeName(String x) {
    trade_name = x;
  }

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

  public void setSubListName(String listName, String lookupListId, String onList,
                             String percent) {
    Vector v = new Vector(4);
    v.add(0, listName);
    v.add(1, lookupListId);
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

    public void setChronic(String chronic) {
        this.chronic = chronic;
    }

    public void setLfcCode(String lfcCode) {
        this.lfcCode = lfcCode;
    }

    public void setPolymerize(String polymerize) {
        this.polymerize = polymerize;
    }

    public void setIncompatible(String incompatible) {
        this.incompatible = incompatible;
    }

    public void setCorrosive(String corrosive) {
        this.corrosive = corrosive;
    }

    public void setHealthEffects(String healthEffects) {
        this.healthEffects = healthEffects;
    }

    public void setStable(String stable) {
        this.stable = stable;
    }

    public void setVaporDensity(String vaporDensity) {
        this.vaporDensity = vaporDensity;
    }

    public void setEvaporationRate(String evaporationRate) {
        this.evaporationRate = evaporationRate;
    }

    public void setFireConditionsToAvoid(String fireConditionsToAvoid) {
        this.fireConditionsToAvoid = fireConditionsToAvoid;
    }

    public void setAlloy(String alloy) {
        this.alloy = alloy;
    }

    public String getChronic() {
    return chronic;
    }

 public String getLfcCode() {
    return lfcCode;
 }
 public String getPolymerize() {
    return polymerize;
 }
 public String getIncompatible() {
    return incompatible;
 }
 public String getCorrosive() {
    return corrosive;
 }
 public String getHealthEffects() {
    return healthEffects;
 }
 public String getStable() {
    return stable;
 }
 public String getVaporDensity() {
    return vaporDensity;
 }
 public String getEvaporationRate() {
    return evaporationRate;
 }
 public String getFireConditionsToAvoid() {
    return fireConditionsToAvoid;
 }
 public String getAlloy() {
    return alloy;
 }
    
  //Constructor
  public SideView(TcmISDBModel db) {

  }

  public SideView(ServerResourceBundle b) {
    this.sdbundle = b;
  }

  public SideView() {

  }

  public void setDbModel(TcmISDBModel db) {

  }

  // Make a month
  public static String doDate(String d2) {
    if (d2.equalsIgnoreCase("01")) {
      d2 = "JAN";
    } else if (d2.equalsIgnoreCase("02")) {
      d2 = "FEB";
    } else if (d2.equalsIgnoreCase("03")) {
      d2 = "MAR";
    } else if (d2.equalsIgnoreCase("04")) {
      d2 = "APR";
    } else if (d2.equalsIgnoreCase("05")) {
      d2 = "MAY";
    } else if (d2.equalsIgnoreCase("06")) {
      d2 = "JUN";
    } else if (d2.equalsIgnoreCase("07")) {
      d2 = "JUL";
    } else if (d2.equalsIgnoreCase("08")) {
      d2 = "AUG";
    } else if (d2.equalsIgnoreCase("09")) {
      d2 = "SEP";
    } else if (d2.equalsIgnoreCase("10")) {
      d2 = "OCT";
    } else if (d2.equalsIgnoreCase("11")) {
      d2 = "NOV";
    } else if (d2.equalsIgnoreCase("12")) {
      d2 = "DEC";
    }
    return d2;
  }

  public Vector getSideViewVector(TcmISDBModel db, String rev_date, String id,
                                  String facility, HttpServletRequest requestside) throws Exception {
    Vector v = new Vector();
    String query = "";
    String content1 = "";
    String tempdate12 = "";

    if (! (rev_date == null || rev_date.equalsIgnoreCase("null"))) {
      tempdate12 = rev_date;
    }

    if(isModuleHaas(requestside)) { 
		query = "select fx_company_catalog_msds_id (" + id +
          ",'All') MSDS_NO,m.* from msds_locale_view m where material_id='" + id +
		          "' and ON_LINE = 'Y' ";
	} else if (facility.trim().length() > 0) {
       query = "select fx_company_catalog_msds_id (" + id + ",'" + facility +
          "') MSDS_NO,m.* from msds_locale_view m where material_id='" + id +
          "' and ON_LINE = 'Y' and locale_code in (" +
          " select locale_code from facility_locale where facility_id = '" + facility + "'" +
			 " union select a.alternate_msds_locale from vv_locale a, facility_locale fl where a.locale_code = fl.locale_code and fl.facility_id = '" + facility + "')";
    } else {
       query = "select fx_company_catalog_msds_id (" + id +
          ",'All') MSDS_NO,m.* from msds_locale_view m where material_id='" + id +
          "' and ON_LINE = 'Y' and locale_code in (" +
          " select locale_code from facility_locale"+
			 " union select a.alternate_msds_locale from vv_locale a, facility_locale fl where a.locale_code = fl.locale_code";
		if (browserLocaleCode.length() > 0) {
			query += " union select '"+browserLocaleCode+"' from dual"+
				      " union select alternate_msds_locale from vv_locale where locale_code = '"+browserLocaleCode+"')";
		}
    }
    query += " order by revision_date desc,locale_code";
    
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int Ki = 0;
    int count = 0;
    //Hashtable result=new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        SideView sv = new SideView(db);
        content1 = rs.getString("content");
        //if ( ! ( content1.endsWith( ".pdf" ) && clientt.equalsIgnoreCase( "BAE" ) ) || showpdffiles )
        {
          sv.setContent(rs.getString("content"));

          if (rs.getString("locale_display") != null) {
            sv.setLocale("(" + rs.getString("locale_display") + ")");
          } else {
            sv.setLocale("");
          }

          if (rs.getString("locale_code") != null) {
            sv.setLocaleCode(rs.getString("locale_code"));
          } else {
            sv.setLocaleCode("");
          }

          if (rs.getString("MSDS_NO") != null) {
            sv.setclmsdsno(rs.getString("MSDS_NO"));
          } else {
            sv.setclmsdsno("");
          }

          if (rs.getString("boiling_point") != null) {
            sv.setBoiling(rs.getString("boiling_point"));
          } else {
            sv.setBoiling("none");
          }
          if (rs.getString("flash_point") != null) {
            sv.setFlash(rs.getString("flash_point"));
          } else {
            sv.setFlash("none");
          }

          if (rs.getString("density") != null) {
            sv.setDensity(rs.getString("density"));
          } else {
            sv.setDensity("none");
          }
          if (rs.getString("freezing_point") != null) {
            sv.setFreezing(rs.getString("freezing_point"));
          } else {
            sv.setFreezing("none");
          }
          if (rs.getString("specific_gravity") != null) {
            sv.setSpecGrav(rs.getString("specific_gravity"));
          } else {
            sv.setSpecGrav("none");
          }
          if (rs.getString("physical_state") != null) {
            sv.setPhysicalState(rs.getString("physical_state"));
          } else {
            sv.setPhysicalState("none");
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
          sv.setRevDateTime(rs.getString("revision_date"));
          String d1 = rs.getString("revision_date").substring(0, 4);
          String d2 = rs.getString("revision_date").substring(5, 7);
          String d3 = rs.getString("revision_date").substring(8, 10);
          d2 = doDate(d2);
          String tempdate = d3 + "/" + d2 + "/" + d1;
          sv.setLatestDate(tempdate);
          if ( (count == 0) && (rev_date == null || rev_date.equalsIgnoreCase("null"))) {
            tempdate12 = tempdate;
          }

          sv.setrevdated(tempdate12);
          String test = BothHelpObjs.makeBlankFromNull(rs.getString("on_line"));
          String selected = "";
          if ( (tempdate12.equalsIgnoreCase(tempdate)) || (Ki == 0)) {
            selected = "selected";
          }
          if (test.equalsIgnoreCase("N")) {
            sv.setRadios("notav");
          } else {
            sv.setRadios("" + tempdate + "");
          }
          Ki = Ki + 1;
          count += 1;
          v.addElement(sv);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    if (count == 0) {
      Vector v1 = new Vector();
      String testmsg = "No Records";
      v1.add(testmsg);
      return v1;
    }
    return v;
  }

  //Query the database and return values
  public Vector getSpecVector(TcmISDBModel db, String id, String catpartno,
                              String facility) throws Exception {
    Vector v = new Vector();
    String client = db.getClient().toString().trim();
    String query = "";
    int count = 0;

    if (catpartno.length() > 0 && facility.length() > 0) {
      try {
        query = " select s.spec_id, c.catalog_id from catalog_part_item_group l, fac_spec s, catalog_facility c ";
        query += " where l.catalog_id=c.catalog_id and l.catalog_id=s.facility_id and ";
        query += " l.cat_part_no=s.fac_part_no and l.status in ('A','D') and ";
        query += " l.CAT_PART_NO='" + catpartno + "' and c.facility_id='" + facility + "' ";
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      DBResultSet dbrs = null;
      ResultSet rs = null;

      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          SideView sv = new SideView(db);
          String qurl = "";
          if (facility == null || facility.length() == 0) {
            qurl = qurl + "&facility=";
          } else {
            qurl = qurl + "&facility=" + BothHelpObjs.addSpaceForUrl(facility);
          }
          if (catpartno == null || catpartno.length() == 0) {
            qurl = qurl + "&catpartno=";
          } else {
            qurl = qurl + "&catpartno=" + catpartno;
          }

          String msdsservlet = sdbundle.getString("VIEW_MSDS");
          sv.setRadios2("<option value=\"" + msdsservlet + "SESSION=4&act=spec&id=" + id +
                        "&spec=" + rs.getString("spec_id") + "" + qurl + "*data\">" +
                        rs.getString("spec_id") + "</option>\n");
          count += 1;
          v.addElement(sv);
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally {
        dbrs.close();
      }
    }

    if (count == 0) {
      Vector v1 = new Vector();
      String testmsg = "No Records";
      v1.add(testmsg);
      return v1;
    }

    return v;
  }
  
  //adding this method since I don't want to change the method signature
  //of the original method
  public Vector getContentVector(TcmISDBModel db, String rev_date, String id, String act,
                                 String spec, String catpartno, String facility, String listId, String lookupListId,
                                 String currentRow) throws Exception {
	 return  getContentVector(db, rev_date, id, act,
              spec, catpartno, facility, listId, lookupListId,
              currentRow, null);
	  
  }

  //adding this method since I don't want to change the method signature
  //of the original method
  public Vector getContentVector(TcmISDBModel db, String rev_date, String id, String act,
                                 String spec, String catpartno, String facility, String listId, String lookupListId,
                                 String currentRow, String revDateTime) throws Exception {
    this.listId = listId;
    this.lookupListId = lookupListId;
    this.currentRow = currentRow;

    if (act.equalsIgnoreCase("msds")) {
      return getMSDSInfo(db, rev_date, id, facility);
    } else if (act.equalsIgnoreCase("comp")) {
      return getCompositionInfo(db, rev_date, id, facility, revDateTime);
    } else if (act.equalsIgnoreCase("title")) {
      return getTitleInfo(db, rev_date, id, facility);
    } else if (act.equalsIgnoreCase("prop")) {
      return getPropertyInfo(db, rev_date, id, facility, revDateTime);
    } else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("subList")) {
      return getListInfo(db, rev_date, id, facility, act, revDateTime);
    } else {
      return getContentVector(db, rev_date, id, act, spec, catpartno, facility);
    }
  }

  //if no revision date then return data for the lastest msds
  //otherwise, return data for given revision date
  public Vector getTitleInfo(TcmISDBModel db, String rev_date, String id, String facility) throws Exception {
    Vector v = new Vector(1);
    String query = "select nvl(content,' ') content,nvl(trade_name,' ') trade_name,nvl(revision_date_display,' ') revision_date_display,nvl(mfg_desc,' ') mfg_desc" +
          " from msds_locale_view m where material_id='" + id + "' and ON_LINE = 'Y'";
    //locale 0 - English
    if (facility.trim().length() > 0) {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale where facility_id = '" + facility + "')";
      }
    } else {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale)";
      }
    }

    //revision date
    if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(REVISION_DATE)='" + rev_date + "'";
    }
    //add order by
    query += " order by revision_date desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        SideView sv = new SideView(db);
        sv.setContent(rs.getString("content"));
        sv.setMaterial(rs.getString("trade_name"));
        sv.setManufacturer(rs.getString("mfg_desc"));
        sv.setDate2(rs.getString("revision_date_display"));
        v.addElement(sv);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  public String getAlternateMsdsLocale(TcmISDBModel db, String tmpLocaleCode) {
		String result = "";
	  	DBResultSet dbrs = null;
    	ResultSet rs = null;
    	try {
			String query = "select a.alternate_msds_locale from vv_locale a where a.locale_code = '"+tmpLocaleCode+"'";
			dbrs = db.doQuery(query);
      	rs = dbrs.getResultSet();
      	while (rs.next()) {
				result = BothHelpObjs.makeBlankFromNull(rs.getString("alternate_msds_locale"));
			}
		}catch (Exception e) {
			e.printStackTrace();
    	} finally {
      	dbrs.close();
    	}
	  return result;
  }

  public String getFacilityDefaultLocale(TcmISDBModel db,String facility) {
		String result = "";
	  	DBResultSet dbrs = null;
    	ResultSet rs = null;
    	try {
			String query = "select locale_code from facility_locale f where facility_id = '" + facility + "' order by display_order, locale_code";
			dbrs = db.doQuery(query);
      	rs = dbrs.getResultSet();
      	while (rs.next()) {
				result = BothHelpObjs.makeBlankFromNull(rs.getString("locale_code"));
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
    	} finally {
      	dbrs.close();
    	}
	  return result;
  }

  //get the lastest revision date
  public String[] getLastestRevisionDate(TcmISDBModel db, String id, String facility, HttpServletRequest request) {
    String[] result = new String[3];
    String query = "";
	 String facilityDefaultLocale = "";
	 String facilityAlternateMsdsLocale = "";
	 String browserAlternateMsdsLocale = "";
	// System.out.print(request.getRequestURI());
	if(isModuleHaas(request)) { 
		query = "select to_char(revision_date,'dd/Mon/yyyy') revision_date,revision_date revision_date_no_format,locale_code" +
		          "	from msds_locale_view m where material_id='" + id +
		          "' and ON_LINE = 'Y' order by revision_date_no_format desc, locale_code desc";
	} else if (facility.trim().length() > 0) {
		facilityDefaultLocale = getFacilityDefaultLocale(db,facility);
		query = "select to_char(revision_date,'dd/Mon/yyyy') revision_date,revision_date revision_date_no_format,locale_code" +
          "	from msds_locale_view m where material_id='" + id +
          "' and ON_LINE = 'Y' and locale_code in (select 'en_US' from dual";
      if (facilityDefaultLocale.length() > 0) {
			query += " union select '"+facilityDefaultLocale+"' from dual";
			facilityAlternateMsdsLocale = getAlternateMsdsLocale(db,facilityDefaultLocale);
			if (facilityAlternateMsdsLocale.length() > 0 && !facilityAlternateMsdsLocale.equalsIgnoreCase(facilityDefaultLocale)) {
				query += " union select '"+facilityAlternateMsdsLocale+"' from dual";
			}
		}
		query += ") order by revision_date_no_format desc, locale_code desc";
	 } else {
		query = "select to_char(revision_date,'dd/Mon/yyyy') revision_date,revision_date revision_date_no_format,locale_code from msds_locale_view m where material_id='" + id +
          "' and ON_LINE = 'Y' and locale_code in (" +
          " select locale_code from facility_locale"+
		    " union select a.alternate_msds_locale from vv_locale a, facility_locale fl where a.locale_code = fl.locale_code";
		if (browserLocaleCode.length() > 0) {
			query += " union select '"+browserLocaleCode+"' from dual";
			browserAlternateMsdsLocale = getAlternateMsdsLocale(db,browserLocaleCode);
			if (browserAlternateMsdsLocale.length() > 0 && !browserAlternateMsdsLocale.equalsIgnoreCase(browserLocaleCode)) {
				query += " union select '"+browserAlternateMsdsLocale+"' from dual";
			}
		}

		query += ") order by revision_date_no_format desc,locale_code";
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      boolean firstTime = true;
		boolean foundAlternateData = false;
		while (rs.next()) {
		  String tmpLocaleId = rs.getString("locale_code");
        //if facility is selected then look for the lastest revision_date for the facility default_locale_code
        if (facility.trim().length() > 0) {
			 if (tmpLocaleId.equalsIgnoreCase(facilityAlternateMsdsLocale) && !foundAlternateData) {
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
				//got the lastest alternate data
				foundAlternateData = true;
			 }
			 if (tmpLocaleId.equalsIgnoreCase(facilityDefaultLocale)) {
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
            //got the lastest data
            break;
          }
			 if (firstTime && !foundAlternateData) {
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
            firstTime = false;
          }
		  } else {
          //else get the lastest browser locale data
			 if (tmpLocaleId.equalsIgnoreCase(browserAlternateMsdsLocale) && !foundAlternateData) {
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
				//got the lastest alternate data
				foundAlternateData = true;
			 }
			 if (tmpLocaleId.equalsIgnoreCase(browserLocaleCode)) {
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
            //got the lastest data
            break;
          }
			 if (firstTime && !foundAlternateData) {
            //default to the lastest revision date
            result[0] = rs.getString("revision_date");
            result[1] = tmpLocaleId;
            result[2] = rs.getString("revision_date_no_format");
            firstTime = false;
          }
		  }
      }
	 } catch (Exception e) {
		e.printStackTrace();
		result[0] = "";
      result[1] = "";
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  
private boolean isModuleHaas(HttpServletRequest request) {
	return request.getRequestURI().contains("/all/ViewMsds");
}

  //if no revision date then return data for the lastest msds
  //otherwise, return data for given revision date
  public Vector getMSDSInfo(TcmISDBModel db, String rev_date, String id, String facility) throws Exception {
    Vector v = new Vector(1);
    String query = "select nvl(content,' ') content,nvl(on_line,' ') on_line" +
                   " from msds_locale_view m where material_id='" + id + "' and ON_LINE = 'Y'";
    //locale 0 - English
   if (facility.trim().length() > 0) {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale where facility_id = '" + facility + "')";
      }
    } else {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale)";
      }
    }

    //revision date
    if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(REVISION_DATE)='" + rev_date + "'";
    }
    //add order by
    query += " order by revision_date desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    String wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();
    String hold = wwwhomeurl + "gen/msds_not_found.html";
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        SideView sv = new SideView(db);
        if (BothHelpObjs.isBlankString(rs.getString("content"))) {
          sv.setContent(hold);
        } else {
          sv.setContent(rs.getString("content"));
        }
        v.addElement(sv);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method
  
  public Vector getCompositionInfo(TcmISDBModel db, String rev_date, String id, String facility) throws Exception {
	  return getCompositionInfo(db,rev_date, id,facility, null);
  }

  public Vector getCompositionInfo(TcmISDBModel db, String rev_date, String id, String facility, String revDateTime) throws Exception {
    Vector v = new Vector(1);
    String client = db.getClient().toString().trim();
    String query = "select mcv.cas_number,mcv.CHEMICAL_ID,mcv.percent,mcv.PERCENT_LOWER,mcv.PERCENT_UPPER," +
        " round(mcv.VOC_PERCENT, 1) VOC_PERCENT,round(mcv.VOC_LOWER_PERCENT,1) VOC_LOWER_PERCENT ," +
        " round(mcv.VOC_UPPER_PERCENT,1) VOC_UPPER_PERCENT";

    if ("Tcm_Ops".equalsIgnoreCase(client)) {
      query += " from global.material_composition_view mcv";
    } else {
      query += " from material_composition_view mcv";
    }
    query += " where material_id = '"+id+"'";
    //revision date
    if(revDateTime != null) {
      query += " and mcv.REVISION_DATE = TO_TIMESTAMP('"+revDateTime+"', 'YYYY-MM-DD HH24:MI:SS.FF9')";
    }
    else if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(mcv.REVISION_DATE)='" + rev_date + "'";
    }
    query += " order by percent desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int count = 0;
      while (rs.next()) {
        SideView sv = new SideView(db);
        sv.setComposition(rs.getString("cas_number"), rs.getString("CHEMICAL_ID"),
                          rs.getString("percent"), rs.getString("PERCENT_LOWER"),
                          rs.getString("PERCENT_UPPER"), rs.getString("VOC_PERCENT"),
                          rs.getString("VOC_LOWER_PERCENT"), rs.getString("VOC_UPPER_PERCENT"), count);

        v.addElement(sv);
        count++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  public Vector getPropertyInfo(TcmISDBModel db, String rev_date, String id, String facility, String revDateTime) throws Exception {
     Vector v = new Vector();

	 String query = "select * from msds_locale_view where material_id = "+id+" and locale_code = '"+this.localeCode+"'";
	 if(null != revDateTime) {
	      query += " and revision_date = TO_TIMESTAMP('"+revDateTime+"', 'YYYY-MM-DD HH24:MI:SS.FF9')";
	 } else if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(revision_date) = '" + rev_date + "'";
     }

	 DBResultSet dbrs = null;
     ResultSet rs = null;

	 String densityunit = "";
     String flashpoint = "";
	 String tmpSolids = "";
	 String tmpVaporPressure = "";
	 String tmpVoc = "";
	 String tmpVocPercent = "";
	 String tmpVocLess = "";
	 try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
          SideView sv = new SideView(db);
          sv.setBoiling(rs.getString("boiling_point"));
		  sv.setCompatibility(rs.getString("compatibility"));
		  densityunit = rs.getString("DENSITY");
		  sv.setDensity(densityunit);
		  sv.setEyes(rs.getString("eyes"));
		  flashpoint = rs.getString("flash_point");
          sv.setFlash(flashpoint);
		  sv.setFreezing(rs.getString("freezing_point"));
		  sv.setIngestion(rs.getString("ingestion"));
		  sv.setInhalation(rs.getString("inhalation"));
		  sv.setInjection(rs.getString("injection"));
		  sv.setPhysicalState(rs.getString("physical_state"));
		  sv.setPh(rs.getString("ph"));	
		  sv.setPpe(rs.getString("ppe"));
		  sv.setRouteOfEntry(rs.getString("route_of_entry"));
		  sv.setSignalWord(rs.getString("signal_word"));
		  sv.setSkin(rs.getString("skin"));
		  tmpSolids = BothHelpObjs.makeBlankFromNull(rs.getString("solids"));
		  sv.setSolids(tmpSolids);
		  sv.setSpecGrav(rs.getString("specific_gravity"));
		  tmpVaporPressure = BothHelpObjs.makeBlankFromNull(rs.getString("vapor_pressure"));
		  sv.setVaporPressure(tmpVaporPressure);
          tmpVoc = BothHelpObjs.makeBlankFromNull(rs.getString("voc"));
          sv.setVoc(tmpVoc);
          tmpVocPercent = BothHelpObjs.makeBlankFromNull(rs.getString("voc_percent"));
          if (!BothHelpObjs.isBlankString(tmpVocPercent)) {
		    tmpVocPercent = ((new BigDecimal(tmpVocPercent)).setScale(2,BigDecimal.ROUND_HALF_UP)).toString();
          }
          sv.setVocPercent(tmpVocPercent);
		  tmpVocLess = BothHelpObjs.makeBlankFromNull(rs.getString("voc_less_h2o_exempt"));
		  sv.setVocLessH2OExempt(tmpVocLess);

          sv.setChronic(rs.getString("chronic"));
          sv.setLfcCode(rs.getString("lfc_code"));
          sv.setPolymerize(rs.getString("polymerize"));
          sv.setIncompatible(rs.getString("incompatible"));
          sv.setCorrosive(rs.getString("corrosive"));
          sv.setHealthEffects(rs.getString("health_effects"));
          sv.setStable(rs.getString("stable"));
          sv.setVaporDensity(rs.getString("vapor_density"));
          sv.setEvaporationRate(rs.getString("evaporation_rate"));
          sv.setFireConditionsToAvoid(rs.getString("fire_conditions_to_avoid"));
          sv.setAlloy(rs.getString("alloy"));
          v.addElement(sv);
      }
    } catch (Exception e) {
		 e.printStackTrace();

    } finally {
      dbrs.close();
    }
	 return v;
  } //end of method

  public String[] getListHeaderInfo(TcmISDBModel db, String rev_date, String id, String facility) throws Exception {
    String[] headerInfo = new String[3];
    headerInfo[0] = "";
    headerInfo[1] = "";
    headerInfo[2] = "";
    String query = "select trade_name,mfg_desc,revision_date_display" +
                   " from msds_locale_view m where material_id='" + id +"' and ON_LINE = 'Y'";

    //locale 0 - English
    if (facility.trim().length() > 0) {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale where facility_id = '" + facility + "')";
      }
    } else {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and m.locale_code = '" + localeCode+"'";
      } else {
        query += " and m.locale_code in (" +
            " select locale_code from facility_locale)";
      }
    }

    //revision date
    if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(REVISION_DATE)='" + rev_date + "'";
    }
    //add order by
    query += " order by revision_date desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        headerInfo[0] = rs.getString("MFG_DESC");
        headerInfo[1] = rs.getString("TRADE_NAME");
        headerInfo[2] = rs.getString("revision_date_display");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return headerInfo;
  } //end of method

  public Vector getListInfo(TcmISDBModel db, String rev_date, String id, String facility, String listType, String revDateTime) throws Exception {
    Vector v = new Vector(1);
    String query = "select mvlv.list_id,mvlv.percent,mvlv.display_level,mvlv.list_name," +
        "mvlv.on_list,mvlv.sublist,mvlv.lookup_list_id from msds_viewer_list_view mvlv where mvlv.material_id=" + id + "";
    String queryForSubList = "";
    //revision date
    if(revDateTime != null) {
        query += " and mvlv.REVISION_DATE = TO_TIMESTAMP('"+revDateTime+"', 'YYYY-MM-DD HH24:MI:SS.FF9')";
    } else if (!rev_date.equalsIgnoreCase("null")) {
      query += " and trunc(mvlv.REVISION_DATE)='" + rev_date + "'";
    }
    //locale 0 - English
/*    if (facility.trim().length() > 0) {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and mvlv.locale_code = " + localeCode;
      } else {
        query += " and mvlv.locale_code in (select 'en_US' from dual union" +
            " select default_locale_code from facility where facility_id = '" + facility + "')";
      }
    } else {
      if (!BothHelpObjs.isBlankString(localeCode)) {
        query += " and mvlv.locale_code = " + localeCode;
      } else {
        query += " and mvlv.locale_code in (select 'en_US' from dual union" +
            " select default_locale_code from facility)";
      }
    }*/

    queryForSubList = query;
    if ("lists".equalsIgnoreCase(listType)) {
      query += " and mvlv.display_level = 0";
    } else {
      query += " and mvlv.display_level = 0";
      queryForSubList += " and mvlv.display_level = 1 and mvlv.list_id='" + listId + "'";
    }
    //add order by
    query += " order by mvlv.list_name";
    queryForSubList += " order by mvlv.list_name";

    /*first get header info
    String[] headerInfo = getListHeaderInfo(db, rev_date, id, facility);
    String tmpTradeName = headerInfo[0];
    String tmpMfgDesc = headerInfo[1];
    String tmpRevisionDateDisplay = headerInfo[2];
    */

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        SideView sv = new SideView(db);
        /*set header info
        sv.setTradeName(tmpTradeName);
        sv.setManufacturer(tmpMfgDesc);
        sv.setDate2(tmpRevisionDateDisplay);
        */
        //get the rest of data
        sv.setListId(rs.getString("LIST_ID"));
        sv.setPercent(rs.getString("PERCENT"));
        sv.setDisplayLevel(rs.getInt("DISPLAY_LEVEL"));
        sv.setListName(rs.getString("LIST_NAME"));
        sv.setOnList(rs.getString("ON_LIST"));
        sv.setSubList(rs.getString("SUBLIST"));
        if (listType.equalsIgnoreCase("subList") && listId.equalsIgnoreCase(rs.getString("LIST_ID")) &&
            sv.getDisplayLevel() == 0) {
          DBResultSet dbrs2 = null;
          ResultSet rs2 = null;
          dbrs2 = db.doQuery(queryForSubList);
          rs2 = dbrs2.getResultSet();
          while (rs2.next()) {
            sv.setSubListName(rs2.getString("LIST_NAME"), rs2.getString("LOOKUP_LIST_ID"), rs2.getString("ON_LIST"), rs2.getString("PERCENT"));
          }
        }

        v.addElement(sv);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  public Vector getContentVector(TcmISDBModel db, String rev_date, String id, String act,
                                 String spec, String catpartno, String facility) throws Exception {

    Vector v = new Vector();
    String query = "";
    String query2 = "";
    String query3 = "";
    String query4 = "";
    String densityunit = "";
    String flashpoint = "";
    String clientt = db.getClient().toString().trim();

    try {
      //need trade name and manufacturer
      if (id == null) {
        id = "0";
      }

      if (!act.equalsIgnoreCase("title")) {
        query4 = "select m.trade_name, g.mfg_desc " + "from manufacturer g, material m " +
            "where m.mfg_id = g.mfg_id " + "and m.material_id = '" + id + "'";
      }

      if (act.equalsIgnoreCase("spec")) {
        query = "select * from spec where SPEC_ID='" + spec + "'";
      } else if (act.equalsIgnoreCase("compound")) {
        //need to figure out what rownumbers to get
        int min = 1;
        int max = 200;
        if (currentRow == null || currentRow.equals("0")) {
          //get the first 200 rows
          min = 1;
          max = 200;
        } else {
          min += new Integer(currentRow).intValue();
          max += new Integer(currentRow).intValue();
        }
        query = "select row_num, cas_number, rpt_chemical, " +
            "(select count(*) from report_list_snap where list_id = '" + lookupListId +
            "') count " + "from (select rownum row_num, cas_number, rpt_chemical from report_list_snap where list_id = '" +
            lookupListId + "' order by cas_number) where row_num between " + min + " and " + max;
      } else if (act.equalsIgnoreCase("compoundpercent")) {
        //need to figure out what rownumbers to get
        int min = 1;
        int max = 200;
        if (currentRow == null || currentRow.equals("0")) {
          //get the first 200 rows
          min = 1;
          max = 200;
        } else {
          min += new Integer(currentRow).intValue();
          max += new Integer(currentRow).intValue();
        }
        query = "select * from msds_viewer_list_comp_view " + "where material_id = " + id +
            " " + "and trunc(revision_date) = '" + rev_date + "' " + "and list_id = '" + listId + "'";
      } else if (act.equalsIgnoreCase("land")) {
        query = "select * from land_shipping_view where ITEM_ID=" + catpartno +
            " and FACILITY_ID='" + facility + "'";
      } else if (act.equalsIgnoreCase("air")) {
        query = "select * from air_shipping_view where ITEM_ID=" + catpartno +
            " and FACILITY_ID='" + facility + "'";
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    DBResultSet dbrs = null;
    DBResultSet dbrs2 = null;
    DBResultSet dbrs3 = null;
    DBResultSet dbrs4 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    //Hashtable result=new Hashtable();
    int count = 0;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (query2 != "") {
        dbrs2 = db.doQuery(query2);
        rs2 = dbrs2.getResultSet();
      }
      if (query3 != "") {
        dbrs3 = db.doQuery(query3);
        rs3 = dbrs3.getResultSet();
      }
      if (query4 != "") {
        dbrs4 = db.doQuery(query4);
        rs4 = dbrs4.getResultSet();
      }

      String wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();
      String hold = wwwhomeurl + "gen/msds_not_found.html";

      //SideView sv=new SideView(db);
      SideView sv = null;
      while (rs.next()) {
        sv = new SideView(db);
        if (act.equalsIgnoreCase("spec")) {
          String msdsservlet = sdbundle.getString("VIEW_MSDS");
          sv.setRadios3("" + msdsservlet + "SESSION=2&act=spec&spec=" + rs.getString("spec_id") +
                        "*data");
          sv.setContent(rs.getString("CONTENT"));
        } else if (act.equalsIgnoreCase("compound")) {
          sv.setCasNumber(rs.getString("CAS_NUMBER"));
          sv.setRptChemical(rs.getString("RPT_CHEMICAL"));
          sv.setCount(rs.getInt("COUNT"));
        } else if (act.equalsIgnoreCase("compoundpercent")) {
          sv.setCasNumber(rs.getString("CAS_NUMBER"));
          sv.setRptChemical(rs.getString("RPT_CHEMICAL"));
          sv.setPercent(rs.getString("PERCENT"));
        } else if (act.equalsIgnoreCase("land")) {
          sv.setMaterialName(rs.getString("material_desc"));
          sv.setManufacturer(rs.getString("mfg_desc"));
          sv.setPackType(rs.getString("package_desc"));
          sv.setPartNo(rs.getString("mfg_part_no"));
          sv.setFacPartNo(rs.getString("fac_part_no"));
          sv.setErg(rs.getString("emergency_response_guide_no"));
          sv.setContainer(rs.getString("container"));
          sv.setGsn(rs.getString("ground_shipping_name"));
        } else if (act.equalsIgnoreCase("air")) {
          sv.setMaterialName(rs.getString("material_desc"));
          sv.setManufacturer(rs.getString("mfg_desc"));
          sv.setPartNo(rs.getString("mfg_part_no"));
          sv.setFacPartNo(rs.getString("fac_part_no"));
          sv.setErg(rs.getString("emergency_response_guide_no"));
          sv.setContainer(rs.getString("container"));
          sv.setGsn(rs.getString("air_shipping_name"));
        }
        count += 1;
        v.addElement(sv);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (dbrs2 != null) {
        try {
          dbrs2.close();
          dbrs3.close();
          dbrs4.close();
        } catch (Exception sqle) {
          //ignore
        }
      }
      dbrs.close();
    }
    if (count == 0) {
      Vector v1 = new Vector();
      String testmsg = "No Records";
      v1.add(testmsg);
      return v1;
    }
    return v;
  }
}
/*
 public Vector getContentVector(TcmISDBModel db, String rev_date, String id, String act,
 String spec, String catpartno, String facility) throws Exception {
 Vector v = new Vector();
 String query = "";
 String query2 = "";
 String query3 = "";
 String query4 = "";
 String densityunit = "";
 String flashpoint = "";
 String clientt = db.getClient().toString().trim();
//boolean showpdffiles=false;
 try {
  //need trade name and manufacturer
  if (id == null) {
  id = "0";
  }
  if (!act.equalsIgnoreCase("title")) {
  query4 = "select m.trade_name, g.mfg_desc " + "from manufacturer g, material m " +
   "where m.mfg_id = g.mfg_id " + "and m.material_id = '" + id + "'";
  }
  if (act.equalsIgnoreCase("msds")) {
  if (!rev_date.equalsIgnoreCase("null")) {
   query = "select content,on_line from msds where MATERIAL_ID=" + id +
   " and REVISION_DATE='" + rev_date + "' and ON_LINE = 'Y'";
  }
  else {
   query = "select content,on_line from msds where material_id='" + id +
   "' and ON_LINE = 'Y' order by revision_date asc";
  }
  //=================================================//
  }
  else if (act.equalsIgnoreCase("spec")) {
  query = "select * from spec where SPEC_ID='" + spec + "'";
  //=================================================//
  }
  else if (act.equalsIgnoreCase("comp")) {
  if (!rev_date.equalsIgnoreCase("null")) {
   query = "select TRADE_NAME,cas_number,CHEMICAL_ID,percent,PERCENT_LOWER,PERCENT_UPPER, round(VOC_PERCENT, 1) VOC_PERCENT,round(VOC_LOWER_PERCENT,1) VOC_LOWER_PERCENT ,round(VOC_UPPER_PERCENT,1)";
   if ("Tcm_Ops".equalsIgnoreCase(clientt)) {
   query += " VOC_UPPER_PERCENT from global.material_composition_view ";
   }
   else {
   query += " VOC_UPPER_PERCENT from material_composition_view ";
   }
   query += " where MATERIAL_ID=" + id + " and REVISION_DATE='" + rev_date +
   "' order by percent desc";
  }
  }
  else if (act.equalsIgnoreCase("title")) {
  query2 = "select TRADE_NAME  from material where material_id='" + id + "'";
  if (!rev_date.equalsIgnoreCase("null")) {
   query = "select * from msds where material_id=" + id + " and REVISION_DATE='" +
   rev_date + "' and ON_LINE = 'Y' order by revision_date asc";
   query3 = "select content,on_line from msds where MATERIAL_ID=" + id +
   " and REVISION_DATE='" + rev_date + "' and ON_LINE = 'Y'";
  }
  else {
   query = "select * from msds where material_id=" + id +
   "  and ON_LINE = 'Y' order by revision_date asc";
   query3 = "select content,on_line from msds where material_id='" + id +
   "'  and ON_LINE = 'Y' order by revision_date asc";
  }
  }
  else if (act.equalsIgnoreCase("prop")) {
  if (!rev_date.equalsIgnoreCase("null")) {
   query = "select freezing_point,specific_gravity,physical_state,boiling_point,flash_point,flash_point_unit,density,density_unit,";
   query += "trade_name,ground_shipping_name,air_shipping_name,hazard_class,un_number,na_number,packing_group ";
   if ("Tcm_Ops".equalsIgnoreCase(clientt)) {
   query += "from global.material_property_view where ";
   }
   else {
   query += "from material_property_view where ";
   }
   query += " material_id='" + id + "' and REVISION_DATE='" + rev_date + "'";
  }
  }
  else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("subList")) {
  query = "select * from msds_viewer_list_view " + "where display_level = 0 " +
   "and material_id=" + id + " " + "and REVISION_DATE='" + rev_date +
   "' ORDER BY LIST_NAME";
  if (act.equalsIgnoreCase("subList")) {
   query2 = "select * from msds_viewer_list_view " + "where display_level = 1 " +
   "and list_id='" + listId + "' " + "and material_id='" + id + "' " +
   "and REVISION_DATE='" + rev_date + "' ORDER BY LIST_NAME";
  }
  }
  else if (act.equalsIgnoreCase("compound")) {
  //need to figure out what rownumbers to get
  int min = 1;
  int max = 200;
  if (currentRow == null || currentRow.equals("0")) {
   //get the first 200 rows
   min = 1;
   max = 200;
  }
  else {
   min += new Integer(currentRow).intValue();
   max += new Integer(currentRow).intValue();
  }
  query = "select row_num, cas_number, rpt_chemical, " +
   "(select count(*) from report_list_snap where list_id = '" + lookupListId +
   "') count " + "from (select rownum row_num, cas_number, rpt_chemical from report_list_snap where list_id = '" +
   lookupListId + "' order by cas_number) where row_num between " + min + " and " + max;
  }
  else if (act.equalsIgnoreCase("compoundpercent")) {
  //need to figure out what rownumbers to get
  int min = 1;
  int max = 200;
  if (currentRow == null || currentRow.equals("0")) {
   //get the first 200 rows
   min = 1;
   max = 200;
  }
  else {
   min += new Integer(currentRow).intValue();
   max += new Integer(currentRow).intValue();
  }
  query = "select * from msds_viewer_list_comp_view " + "where material_id = " + id +
   " " + "and revision_date = '" + rev_date + "' " + "and list_id = '" + listId + "'";
  }
  else if (act.equalsIgnoreCase("land")) {
  query = "select * from land_shipping_view where ITEM_ID=" + catpartno +
   " and FACILITY_ID='" + facility + "'";
  }
  else if (act.equalsIgnoreCase("air")) {
  query = "select * from air_shipping_view where ITEM_ID=" + catpartno +
   " and FACILITY_ID='" + facility + "'";
  }
 }
 catch (Exception ex) {
  ex.printStackTrace();
 }
 DBResultSet dbrs = null;
 DBResultSet dbrs2 = null;
 DBResultSet dbrs3 = null;
 DBResultSet dbrs4 = null;
 ResultSet rs = null;
 ResultSet rs2 = null;
 ResultSet rs3 = null;
 ResultSet rs4 = null;
//Hashtable result=new Hashtable();
 int count = 0;
 try {
  dbrs = db.doQuery(query);
  rs = dbrs.getResultSet();
  if (query2 != "") {
  dbrs2 = db.doQuery(query2);
  rs2 = dbrs2.getResultSet();
  }
  if (query3 != "") {
  dbrs3 = db.doQuery(query3);
  rs3 = dbrs3.getResultSet();
  }
  if (query4 != "") {
  dbrs4 = db.doQuery(query4);
  rs4 = dbrs4.getResultSet();
  }
  String wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();
  String hold = wwwhomeurl + "gen/msds_not_found.html";
  //SideView sv=new SideView(db);
  SideView sv = null;
  while (rs.next()) {
  sv = new SideView(db);
  if (act.equalsIgnoreCase("msds")) {
   String test = BothHelpObjs.makeBlankFromNull(rs.getString("on_line"));
   if (test.equalsIgnoreCase("N")) {
   sv.setContent(hold);
   }
   else {
    sv.setContent(rs.getString("content"));
   }
  }
  else if (act.equalsIgnoreCase("spec")) {
   String msdsservlet = sdbundle.getString("VIEW_MSDS");
   sv.setRadios3("" + msdsservlet + "SESSION=2&act=spec&spec=" + rs.getString("spec_id") +
   "*data");
   sv.setContent(rs.getString("CONTENT"));
  }
  else if (act.equalsIgnoreCase("title")) {
   while (rs2.next()) {
   sv.setMaterial(rs2.getString("TRADE_NAME"));
   }
   String d1 = rs.getString("revision_date").substring(0, 4);
   String d2 = rs.getString("revision_date").substring(5, 7);
   String d3 = rs.getString("revision_date").substring(8, 10);
   d2 = doDate(d2);
   String tempdate = d3 + "/" + d2 + "/" + d1;
   sv.setDate2(tempdate);
   while (rs3.next()) {
   sv.setContent(rs3.getString("content"));
   }
  }
  else if (act.equalsIgnoreCase("comp")) {
   while (rs4.next()) {
   sv.setManufacturer(rs4.getString("MFG_DESC"));
   sv.setTradeName(rs4.getString("TRADE_NAME"));
   }
   sv.setMaterial(rs.getString("TRADE_NAME"));
   sv.setComposition(rs.getString("cas_number"), rs.getString("CHEMICAL_ID"),
   rs.getString("percent"), rs.getString("PERCENT_LOWER"),
   rs.getString("PERCENT_UPPER"), rs.getString("VOC_PERCENT"),
   rs.getString("VOC_LOWER_PERCENT"), rs.getString("VOC_UPPER_PERCENT"), count);
  }
  else if (act.equalsIgnoreCase("prop")) {
   while (rs4.next()) {
   sv.setManufacturer(rs4.getString("MFG_DESC"));
   sv.setTradeName(rs4.getString("TRADE_NAME"));
   }
   sv.setTradeName(rs.getString("trade_name"));
   sv.setBoiling(rs.getString("boiling_point"));
   sv.setFreezing(rs.getString("freezing_point"));
   sv.setSpecGrav(rs.getString("specific_gravity"));
   sv.setPhysicalState(rs.getString("physical_state"));
   densityunit = rs.getString("DENSITY") + " " + (rs.getString("DENSITY_UNIT") == null ?
   " " : rs.getString("DENSITY_UNIT"));
   flashpoint = rs.getString("flash_point") + " " + (rs.getString("flash_point_unit") == null ?
   " " : rs.getString("flash_point_unit"));
   sv.setFlash(flashpoint);
   sv.setDensity(densityunit);
   sv.setGroundShippingName(rs.getString("ground_shipping_name"));
   sv.setAirShippingName(rs.getString("air_shipping_name"));
   sv.setHazardClass(rs.getString("hazard_class"));
   sv.setUnNumber(rs.getString("un_number"));
   sv.setNaNumber(rs.getString("na_number"));
   sv.setPackingGroup(rs.getString("packing_group"));
  }
  else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("subList")) {
   while (rs4.next()) {
   sv.setManufacturer(rs4.getString("MFG_DESC"));
   sv.setTradeName(rs4.getString("TRADE_NAME"));
   }
   sv.setListId(rs.getString("LIST_ID"));
   sv.setPercent(rs.getString("PERCENT"));
   sv.setDisplayLevel(rs.getInt("DISPLAY_LEVEL"));
   sv.setListName(rs.getString("LIST_NAME"));
   sv.setOnList(rs.getString("ON_LIST"));
   sv.setSubList(rs.getString("SUBLIST"));
   if (act.equalsIgnoreCase("subList") &&
   listId.equalsIgnoreCase(rs.getString("LIST_ID"))) {
   while (rs2.next()) {
    sv.setSubListName(rs2.getString("LIST_NAME"), rs2.getString("LOOKUP_LIST_ID"),
    rs2.getString("ON_LIST"), rs2.getString("PERCENT"));
   }
   }
  }
  else if (act.equalsIgnoreCase("compound")) {
   sv.setCasNumber(rs.getString("CAS_NUMBER"));
   sv.setRptChemical(rs.getString("RPT_CHEMICAL"));
   sv.setCount(rs.getInt("COUNT"));
  }
  else if (act.equalsIgnoreCase("compoundpercent")) {
   sv.setCasNumber(rs.getString("CAS_NUMBER"));
   sv.setRptChemical(rs.getString("RPT_CHEMICAL"));
   sv.setPercent(rs.getString("PERCENT"));
  }
  else if (act.equalsIgnoreCase("land")) {
   sv.setMaterialName(rs.getString("material_desc"));
   sv.setManufacturer(rs.getString("mfg_desc"));
   sv.setPackType(rs.getString("package_desc"));
   sv.setPartNo(rs.getString("mfg_part_no"));
   sv.setFacPartNo(rs.getString("fac_part_no"));
   sv.setErg(rs.getString("emergency_response_guide_no"));
   sv.setContainer(rs.getString("container"));
   sv.setGsn(rs.getString("ground_shipping_name"));
  }
  else if (act.equalsIgnoreCase("air")) {
   sv.setMaterialName(rs.getString("material_desc"));
   sv.setManufacturer(rs.getString("mfg_desc"));
   sv.setPartNo(rs.getString("mfg_part_no"));
   sv.setFacPartNo(rs.getString("fac_part_no"));
   sv.setErg(rs.getString("emergency_response_guide_no"));
   sv.setContainer(rs.getString("container"));
   sv.setGsn(rs.getString("air_shipping_name"));
  }
  count += 1;
  v.addElement(sv);
  }
 }
 catch (Exception e) {
  e.printStackTrace();
  throw e;
 }
 finally {
  if (dbrs2 != null) {
  try {
   dbrs2.close();
   dbrs3.close();
   dbrs4.close();
  }
  catch (Exception sqle) {
   //ignore
  }
  }
  dbrs.close();
 }
 if (count == 0) {
  Vector v1 = new Vector();
  String testmsg = "No Records";
  v1.add(testmsg);
  return v1;
 }
 return v;
 }
 */
