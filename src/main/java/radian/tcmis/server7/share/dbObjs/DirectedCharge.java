package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class DirectedCharge  {

   protected TcmISDBModel db;
   protected String facility_id;
   protected String account_sys_id;
   protected String application;
   protected String charge_type;
   protected String charge_id;
   protected String charge_number_1;
   protected String charge_number_2;
   protected String pct;
   protected String banned;
   protected String cat_part_no;
   protected String catalog_id;
	protected String catalog_company_id;
	protected String company_id;

	public DirectedCharge()  throws java.rmi.RemoteException {

   }

   public DirectedCharge(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setFacilityId(String s) {
     this.facility_id = s;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setAccountSysId(String s) {
     this.account_sys_id= s;
   }

   public String getAccountSysId() {
     return account_sys_id;
   }

   public void setApplication(String s) {
     this.application= s;
   }

   public String getApplication() {
     return application;
   }

   public void setChargeType(String s) {
     this.charge_type = s;
   }

   public String getChargeType() {
     return charge_type;
   }

   public void setChargeId(String s) {
     this.charge_id = s;
   }

   public String getChargeId() {
     return charge_id;
   }

   public void setChargeNumber1(String s) {
     this.charge_number_1 = s;
   }

   public String getChargeNumber1() {
     return charge_number_1;
   }

   public void setChargeNumber2(String s) {
     this.charge_number_2 = s;
   }

   public String getChargeNumber2() {
     return charge_number_2;
   }

   public void setPct(String s) {
     this.pct = s;
   }

   public String getPct() {
     return pct;
   }

   public void setBanned(String s) {
     this.banned = s;
   }

   public String getBanned() {
     return banned;
   }

   public void setCatPartNo(String s) {
     this.cat_part_no = s;
   }

   public String getCatPartNo() {
     return cat_part_no;
   }

   public void setCatalogID(String s) {
     this.catalog_id = s;
   }

   public String getCatalogID() {
     return catalog_id;
   }

	public void setCatalogCompanyId (String s) {
		this.catalog_company_id = s;
	}

	public String getCatalogCompanyId() {
		return catalog_company_id;
	}

	public void setCompanyId(String s) {
		this.company_id = s;
	}

	public String getCompanyId() {
		return company_id;
	}


	public void load()  throws Exception {
     String query = "select facility_id,account_sys_id,application,charge_type,charge_number_1,charge_number_2,banned,percent";
     query += " from directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     query += " and application = '"+getApplication()+"'";
     //query += " and charge_id = '"+getChargeId()+"'";
     query += " and charge_type = '"+getChargeType()+"'";

     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         this.setChargeNumber1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
         this.setPct(rs.getString("percentage"));
         this.setChargeNumber2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
         this.setBanned(BothHelpObjs.makeBlankFromNull(rs.getString("banned")));
       }
     } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     } finally{
        dbrs.close();
     }
     return;
   }

   public boolean isDirected()  throws Exception {
     String query = "select count(*) from directed_charge where facility_id = '"+getFacilityId()+"'"+
                    " and account_sys_id = '"+getAccountSysId()+"'"+
                    " and application = '"+getApplication()+"'"+
                    " and charge_type = '"+getChargeType()+"'";
                    //" and (charge_number_1 is not null or charge_number_2 is not null)";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int n = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         n = (int)rs.getInt(1);
       }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{
        dbrs.close();
      }
     if (n >= 1) {
       return true;
     } else {
       return false;
     }
   }

   public boolean isBanned()  throws Exception {
     String query = "select count(*)";
     query += " from directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     query += " and application = '"+getApplication()+"'";
     //query += " and charge_id = '"+getChargeId()+"'";
     query += " and charge_type = '"+getChargeType()+"'";
     query += " and upper(banned) = upper('Y')";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int n = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         n = (int)rs.getInt(1);
       }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{
        dbrs.close();
      }
     if (n == 1) {
       return true;
     } else {
       return false;
     }
   }

	//THIS IS FOR ENG EVAL
	//need to replace below with NEW table function
	//because directed_charge = for eng eval and regular MRs: part -> work area
	// and for cabinet MRs: part -> work area (cabinet) -> (use work area)
	public Vector retrieveAll()  throws Exception {
     Vector V = new Vector();
     String query = "select charge_number_1,charge_number_2,percent"+
                    " from table (pkg_directed_charge_util.fx_get_directed_charges('"+getCompanyId()+"','"+getFacilityId()+"','"+
						  getApplication()+"','','"+getAccountSysId()+"','"+getChargeType()+"','Material','','','',''))";
	  query += " where charge_number_1 is not null or charge_number_2 is not null";

	  DBResultSet dbrs = null;
     ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         DirectedCharge dc = new DirectedCharge(db);
         dc.setChargeNumber1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
         dc.setChargeNumber2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
         dc.setPct(BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
         V.addElement(dc);
       }
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       dbrs.close();
     }
     return V;
   }

   String[] getNumberOfChargeDisplay() throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String[] numberOfChargeDisplay = new String[2];
     try {
       String query = "select charge_number_1_required,charge_number_2_required from account_required_view " +
                      "where account_sys_id = '" + getAccountSysId() + "' " +
                      "and charge_type = '" + getChargeType() + "' ";
       dbrs = db.doQuery(query);
       rs = dbrs.getResultSet();
       while (rs.next()) {
         numberOfChargeDisplay[0] = rs.getString("charge_number_1_required");
         numberOfChargeDisplay[1] = rs.getString("charge_number_2_required");
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
     return numberOfChargeDisplay;
   } //end of methof

	public Vector getDirectedChargeNumber()  throws Exception {
		Vector result = new Vector();
		DBResultSet dbrs = null;
		ResultSet rs = null;
		try {
			//first find out how many charge number to display for account_sys
			String[] numberOfChargeDisplay = getNumberOfChargeDisplay();
			String displayChargeNumber1 = numberOfChargeDisplay[0];
			String displayChargeNumber2 = numberOfChargeDisplay[1];
			if ("y".equalsIgnoreCase(displayChargeNumber1) || "y".equalsIgnoreCase(displayChargeNumber2)) {
				//need to replace below with NEW table function
				//because directed_charge = for eng eval and regular MRs: part -> work area
				// and for cabinet MRs: part -> work area (cabinet) -> (use work area)
				String query = "select charge_number_1,charge_number_2,percent"+
                    			" from table (pkg_directed_charge_util.fx_get_directed_charges('"+getCompanyId()+"','"+getFacilityId()+"','"+
						  			getApplication()+"','','"+getAccountSysId()+"','"+getChargeType()+"','Material','"+getCatalogCompanyId()+"','"+getCatalogID()+"','"+getCatPartNo()+"',1))";
				if ("y".equalsIgnoreCase(displayChargeNumber1) && "y".equalsIgnoreCase(displayChargeNumber2)) {
					query += " where charge_number_1 is not null and charge_number_2 is not null";
				}else if ("y".equalsIgnoreCase(displayChargeNumber1)) {
					query += " where charge_number_1 is not null";
				}else {
					query += " where charge_number_2 is not null";
				}
				dbrs = db.doQuery(query);
				rs=dbrs.getResultSet();
				while(rs.next()) {
					 Hashtable h = new Hashtable(3);
					 h.put("ACCT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
					 h.put("ACCT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
					 h.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
					 result.addElement(h);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = new Vector(0);
		}finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		return result;
	}  //end of method
} //end of class

/*
   String[] getChargeNumberSource(String displayChargeNumber1, String displayChargeNumber2) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String[] chargeNumberSource = new String[2];
     try {
       String query = "";
       if ("y".equalsIgnoreCase(displayChargeNumber1) && "y".equalsIgnoreCase(displayChargeNumber2)) {
         query = "select b.source_value source_1, c.source_value source_2 from directed_charge a, vv_directed_charge_source b, vv_directed_charge_source c " +
                 "where facility_id = '" + getFacilityId() + "' " +
                 "and account_sys_id = '" + getAccountSysId() + "' " +
                 "and application = '" + getApplication() + "' " +
                 "and charge_type = '" + getChargeType() + "' " +
                 "and b.source_id = a.charge_number_1_source " +
                 "and c.source_id = a.charge_number_2_source and rownum = 1 "; //rownum < 2 coz I am expecting one row from query
       }else if ("y".equalsIgnoreCase(displayChargeNumber1)) {
         query = "select b.source_value source_1, 'NA' source_2 from directed_charge a, vv_directed_charge_source b " +
                 "where a.facility_id = '" + getFacilityId() + "' " +
                 "and a.account_sys_id = '" + getAccountSysId() + "' " +
                 "and a.application = '" + getApplication() + "' " +
                 "and a.charge_type = '" + getChargeType() + "' " +
                 "and b.source_id = a.charge_number_1_source " +
                 "and rownum = 1 "; //rownum < 2 coz I am expecting one row from query
       }else if ("y".equalsIgnoreCase(displayChargeNumber2)) {
         query = "select 'NA' source_1, b.source_value source_2 from directed_charge a, vv_directed_charge_source b " +
                 "where a.facility_id = '" + getFacilityId() + "' " +
                 "and a.account_sys_id = '" + getAccountSysId() + "' " +
                 "and a.application = '" + getApplication() + "' " +
                 "and a.charge_type = '" + getChargeType() + "' " +
                 "and b.source_id = a.charge_number_2_source " +
                 "and rownum = 1 "; //rownum < 2 coz I am expecting one row from query
       }
       dbrs = db.doQuery(query);
       rs = dbrs.getResultSet();
       while (rs.next()) {
         chargeNumberSource[0] = BothHelpObjs.makeBlankFromNull(rs.getString("source_1"));
         chargeNumberSource[1] = BothHelpObjs.makeBlankFromNull(rs.getString("source_2"));
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
     return chargeNumberSource;
   } //end of methof

	public Vector getDirectedChargeNumber()  throws Exception {
     Vector result = new Vector();
     String query = "";
     try {
         //first find out how many charge number to display for account_sys
         String[] numberOfChargeDisplay = getNumberOfChargeDisplay();
         String displayChargeNumber1 = numberOfChargeDisplay[0];
         String displayChargeNumber2 = numberOfChargeDisplay[1];
         //next find out where to get the charge_number from
         String chargeNumber1Source = "NA";
         String chargeNumber2Source = "NA";
         if ("y".equalsIgnoreCase(displayChargeNumber1) || "y".equalsIgnoreCase(displayChargeNumber2)) {
           String[] chargeNumberSource = getChargeNumberSource(displayChargeNumber1, displayChargeNumber2);
           chargeNumber1Source = chargeNumberSource[0];
           chargeNumber2Source = chargeNumberSource[1];
         }
         //set variables
         Vector chargeNumber1V = new Vector(10);
         Vector chargeNumber2V = new Vector(10);
         Vector percentV = new Vector(10);
         if ("y".equalsIgnoreCase(displayChargeNumber1) && "y".equalsIgnoreCase(displayChargeNumber2)) {
           if ("NA".equalsIgnoreCase(chargeNumber1Source) || "NA".equalsIgnoreCase(chargeNumber2Source)) {
             //return empty vector
             return (new Vector(1));
           }
           if (chargeNumber1Source.equalsIgnoreCase("directed_charge") && chargeNumber2Source.equalsIgnoreCase("directed_charge")) {
             getBothChargeNumberFromDirectedCharge(result);
             //return empty vector
             if (result.size() < 1) {
               return (new Vector(1));
             }
           }else {
             //get charge number 1 separately
             getDirectedChargeNumber1(chargeNumber1V,percentV,chargeNumber1Source);
             //don't treate it as directed charge if no charge number was found
             if (chargeNumber1V.size() < 1) {
               //try to get it from directed_charge
               if (!"directed_charge".equalsIgnoreCase(chargeNumber1Source)) {
                 getDirectedChargeNumber1(chargeNumber1V,percentV,"directed_charge");
                 chargeNumber1Source = "directed_charge";
               }
               //if it still nothing then return empty vector
               if (chargeNumber1V.size() < 1) {
                 //return empty vector
                 return (new Vector(1));
               }
             }
             //then get charge number 2 separately
             getDirectedChargeNumber2(chargeNumber2V,percentV,chargeNumber2Source);
             //don't treate it as directed charge if no charge number was found
             if (chargeNumber2V.size() < 1) {
               //try to get it from directed_charge
               if (!"directed_charge".equalsIgnoreCase(chargeNumber2Source)) {
                 getDirectedChargeNumber2(chargeNumber2V,percentV,"directed_charge");
                 chargeNumber2Source = "directed_charge";
               }
               //if it still nothing then return empty vector
               if (chargeNumber2V.size() < 1) {
                 //return empty vector
                 return (new Vector(1));
               }
             }
             //after all of that if percent Vector is empty then get percentage from directed charge
             if (percentV.size() < 1) {
               getDirectedPercentage(percentV);
               //even after this if percent is empty, return empty vector
               if (percentV.size() < 1) {
                 return (new Vector(1));
               }
             }
             //NOW put it all together
             if (chargeNumber1Source.equalsIgnoreCase("directed_charge") || chargeNumber2Source.equalsIgnoreCase("directed_charge")) {
               if (chargeNumber1Source.equalsIgnoreCase(chargeNumber2Source)) {
                 //this mean that charge_number_1 and 2 directed charge, becuase it is not in catalog_part_directed_charge
                 for (int j = 0; j < chargeNumber1V.size(); j++) {
                   Hashtable h = new Hashtable(3);
                   h.put("ACCT_NUM_1",(String)chargeNumber1V.elementAt(j));
                   h.put("ACCT_NUM_2",(String)chargeNumber2V.elementAt(j));
                   h.put("PERCENTAGE",(String)percentV.elementAt(j));
                   result.addElement(h);
                 }
               }else if (chargeNumber1Source.equalsIgnoreCase("directed_charge")) {
                 //this mean that charge_number_2 is by cat_part_no, therefore charge_number_2 is repeat for all charge_number_1
                 for (int j = 0; j < chargeNumber1V.size(); j++) {
                   Hashtable h = new Hashtable(3);
                   h.put("ACCT_NUM_1",(String)chargeNumber1V.elementAt(j));
                   h.put("ACCT_NUM_2",(String)chargeNumber2V.firstElement());     //chargeNumber2 vector should only has one charge number
                   h.put("PERCENTAGE",(String)percentV.elementAt(j));
                   result.addElement(h);
                 }
               }else {
                 //this mean that charge_number_1 is by cat_part_no, therefore charge_number_1 is repeat for all charge_number_2
                 for (int j = 0; j < chargeNumber2V.size(); j++) {
                   Hashtable h = new Hashtable(3);
                   h.put("ACCT_NUM_1",(String)chargeNumber1V.firstElement());     //chargeNumber2 vector should only has one charge number
                   h.put("ACCT_NUM_2",(String)chargeNumber2V.elementAt(j));
                   h.put("PERCENTAGE",(String)percentV.elementAt(j));
                   result.addElement(h);
                 }
               }
             }else {
               //this mean that charge_number_1 and charge_number_2 are both by cat_part_no,
               //one to one ratio between charge_number_1 and charge_number_2
               for (int j = 0; j < chargeNumber1V.size(); j++) {
                 Hashtable h = new Hashtable(3);
                 h.put("ACCT_NUM_1",(String)chargeNumber1V.firstElement());
                 h.put("ACCT_NUM_2",(String)chargeNumber2V.elementAt(j));
                 h.put("PERCENTAGE",(String)percentV.elementAt(j));
                 result.addElement(h);
               }
             } //end of else either charge_number_1 or charge_number_2 come from directed_charge
           } //end of else both charge numbers from different tables
         }else if ("y".equalsIgnoreCase(displayChargeNumber1)) {
           if ("NA".equalsIgnoreCase(chargeNumber1Source)) {
             return (new Vector(1));
           }
           //get charge number 1 separately
           getDirectedChargeNumber1(chargeNumber1V,percentV,chargeNumber1Source);
           //don't treate it as directed charge if no charge number was found
           if (chargeNumber1V.size() < 1) {
             //try to get it from directed_charge
             if (!"directed_charge".equalsIgnoreCase(chargeNumber1Source)) {
               getDirectedChargeNumber1(chargeNumber1V,percentV,"directed_charge");
             }
             //if it still nothing then return empty vector
             if (chargeNumber1V.size() < 1) {
               //return empty vector
               return (new Vector(1));
             }
           }
           //after all of that if percent Vector is empty then get percentage from directed charge
           if (percentV.size() < 1) {
             getDirectedPercentage(percentV);
             //even after this if percent is empty, return empty vector
             if (percentV.size() < 1) {
               return (new Vector(1));
             }
           }
           //NOW put it all together
           for (int j = 0; j < chargeNumber1V.size(); j++) {
             Hashtable h = new Hashtable(3);
             h.put("ACCT_NUM_1",(String)chargeNumber1V.elementAt(j));
             h.put("ACCT_NUM_2","");                                          //chargeNumber2 PLACE HOLDER
             h.put("PERCENTAGE",(String)percentV.elementAt(j));
             result.addElement(h);
           }
         }else if ("y".equalsIgnoreCase(displayChargeNumber2)) {
           if ("NA".equalsIgnoreCase(chargeNumber2Source)) {
             return (new Vector(1));
           }
           //then get charge number 2 separately
           getDirectedChargeNumber2(chargeNumber2V,percentV,chargeNumber2Source);
           //don't treate it as directed charge if no charge number was found
           if (chargeNumber2V.size() < 1) {
             //try to get it from directed_charge
             if (!"directed_charge".equalsIgnoreCase(chargeNumber2Source)) {
               getDirectedChargeNumber2(chargeNumber2V,percentV,"directed_charge");
             }
             //if it still nothing then return empty vector
             if (chargeNumber2V.size() < 1) {
               //return empty vector
               return (new Vector(1));
             }
           }
           //after all of that if percent Vector is empty then get percentage from directed charge
           if (percentV.size() < 1) {
             getDirectedPercentage(percentV);
             //even after this if percent is empty, return empty vector
             if (percentV.size() < 1) {
               return (new Vector(1));
             }
           }
           //NOW put it all together
           for (int j = 0; j < chargeNumber2V.size(); j++) {
             Hashtable h = new Hashtable(3);
             h.put("ACCT_NUM_1","");                                          //chargeNumber1 PLACE HOLDER
             h.put("ACCT_NUM_2",(String)chargeNumber2V.elementAt(j));
             h.put("PERCENTAGE",(String)percentV.elementAt(j));
             result.addElement(h);
           }
         } //end of displayChargeNumber2
     } catch (Exception e) {
       e.printStackTrace();
       result = new Vector();
     }
     return result;
   }  //end of method

	void getDirectedChargeNumber1(Vector chargeNumber1V, Vector percentV, String chargeNumber1Source) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select ";
       String from = "from ";
       String where = "where ";
       if (!BothHelpObjs.isBlankString(chargeNumber1Source)) {
         if (chargeNumber1Source.equalsIgnoreCase("directed_charge")) {
           query += "a.charge_number_1,percent ";
           from += "directed_charge a ";
           where += "a.facility_id = '"+getFacilityId()+"'"+
                  " and a.account_sys_id = '"+getAccountSysId()+"'"+
                  " and a.application = '"+getApplication()+"'"+
                  " and a.charge_type = '"+getChargeType()+"' ";
           query = query+from+where;
         }else {
           query = "select fx_cat_part_fac_app_charge('"+getCatalogID()+"','"+getCatPartNo()+"','"+getFacilityId()+"','"+getApplication()+"','"+getCatalogCompanyId()+"') charge_number_1 from dual";
         }
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while(rs.next()) {
           if (!BothHelpObjs.isBlankString(rs.getString("charge_number_1"))) {
             chargeNumber1V.addElement(rs.getString("charge_number_1"));
           }
           if (chargeNumber1Source.equalsIgnoreCase("directed_charge")) {
             percentV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
           }
         }
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
   } //end of method

   void getDirectedChargeNumber2(Vector chargeNumber2V, Vector percentV, String chargeNumber2Source) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select ";
       String from = "from ";
       String where = "where ";
       if (chargeNumber2Source.equalsIgnoreCase("directed_charge")) {
         query += "a.charge_number_2,percent ";
         from += "directed_charge a ";
         where += "a.facility_id = '"+getFacilityId()+"'"+
                  " and a.account_sys_id = '"+getAccountSysId()+"'"+
                  " and a.application = '"+getApplication()+"'"+
                  " and a.charge_type = '"+getChargeType()+"' ";
         query = query+from+where;
       }else {
         query = "select fx_cat_part_fac_app_charge2('"+getCatalogID()+"','"+getCatPartNo()+"','"+getFacilityId()+"','"+getApplication()+"','"+getCatalogCompanyId()+"') charge_number_2 from dual";
       }
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
          if (!BothHelpObjs.isBlankString(rs.getString("charge_number_2"))) {
            chargeNumber2V.addElement(rs.getString("charge_number_2"));
          }
          if (chargeNumber2Source.equalsIgnoreCase("directed_charge")) {
            percentV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
          }
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
   } //end of method

	void getBothChargeNumberFromCatalogPartDirectedCharge(Vector result) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select fx_cat_part_fac_app_charge('"+getCatalogID()+"','"+getCatPartNo()+"','"+getFacilityId()+"','"+getApplication()+"','"+getCatalogCompanyId()+"') charge_number_1,"+
                      "fx_cat_part_fac_app_charge2('"+getCatalogID()+"','"+getCatPartNo()+"','"+getFacilityId()+"','"+getApplication()+"','"+getCatalogCompanyId()+"') charge_number_2,"+
                      "100 percent from dual";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
          Hashtable h = new Hashtable(3);
          String num1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1"));
          String num2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2"));
          String perc = BothHelpObjs.makeBlankFromNull(rs.getString("percent"));
          if (BothHelpObjs.isBlankString(num1) || BothHelpObjs.isBlankString(num2) || BothHelpObjs.isBlankString(perc)) {
            continue;
          }
          h.put("ACCT_NUM_1",num1);
          h.put("ACCT_NUM_2",num2);
          h.put("PERCENTAGE",perc);
          result.addElement(h);
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
   } //end of method

   void getBothChargeNumberFromDirectedCharge(Vector result) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select charge_number_1,charge_number_2,percent"+
               " from directed_charge where facility_id = '"+getFacilityId()+"'"+
               " and account_sys_id = '"+getAccountSysId()+"'"+
               " and application = '"+getApplication()+"'"+
               " and charge_type = '"+getChargeType()+"'"+
               " order by charge_number_1,charge_number_2";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
          Hashtable h = new Hashtable(3);
          String num1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1"));
          String num2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2"));
          String perc = BothHelpObjs.makeBlankFromNull(rs.getString("percent"));
          if (BothHelpObjs.isBlankString(num1) || BothHelpObjs.isBlankString(num2) || BothHelpObjs.isBlankString(perc)) {
            continue;
          }
          h.put("ACCT_NUM_1",num1);
          h.put("ACCT_NUM_2",num2);
          h.put("PERCENTAGE",perc);
          result.addElement(h);
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
   } //end of method

   void getDirectedPercentage(Vector percentV) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select percent from directed_charge where facility_id = '"+getFacilityId()+"'"+
                      " and account_sys_id = '"+getAccountSysId()+"'"+
                      " and application = '"+getApplication()+"'"+
                      " and charge_type = '"+getChargeType()+"' ";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         percentV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
   } //end of method
 */
