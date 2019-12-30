package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;

public abstract class WasteSearchScreen {
  public static final int SEARCH_PROFILE_ID_COL = 0;
  public static final int SEARCH_FACILITY_COL = 1;
  public static final int SEARCH_VENDOR_PROFILE_COL = 2;
  public static final int SEARCH_VENDOR_COL = 3;
  public static final int SEARCH_PROFILE_DESC_COL = 4;
  public static final int SEARCH_WASTE_MGMT_OPTION_COL = 5;
  public static final int SEARCH_WASTE_MGMT_LOC_COL = 6;
  public static final int SEARCH_SIZE_UNIT_PKG_COL = 7;
  public static final int SEARCH_CATEGORY_COL = 8;
  public static final int SEARCH_TYPE_COL = 9;
  public static final int SEARCH_LPP_COL = 10;

  public static final String[] colHeads = {"Profile Id","Facility","Vendor Profile Id","Vendor","Profile Desc.","Waste Mgmt Option","Mgmt Option Location","Unit of Measure","Type","LPP"};
  public static final int[] colWidths = {50,50,50,50,50,50,50,50,50,50,50};
  public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING};

}