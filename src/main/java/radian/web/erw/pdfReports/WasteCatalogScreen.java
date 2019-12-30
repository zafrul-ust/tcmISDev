package radian.web.erw.pdfReports;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Vector;

public class WasteCatalogScreen
{
    public static final int SEARCH_STATE_WASTE_CODE_COL = 0;
    public static final int SEARCH_DESC_COL = 1;
    public static final int SEARCH_PACKAGING_COL = 2;
    public static final int SEARCH_VENDOR_PROFILE_ID_COL = 3;
    public static final int SEARCH_VENDOR_ID_COL = 4;
    public static final int SEARCH_WASTE_CATEGORY_COL = 5;
    public static final int SEARCH_WASTE_TYPE_COL = 6;
    public static final int SEARCH_WASTE_MGMT_OPT_COL = 7;
    public static final int SEARCH_ITEM_ID_COL = 8;
    public static final int SEARCH_FACILITY_COL = 9;
    public static final int SEARCH_LPP_COL = 10;

    String stateWasteCode = "";
    String wasteItemID = "";
    String vendorProfileID = "";
    String facilityID = "";
    String vendorID = "";
    String wasteMgmtOpt = "";
    String wasteCategory = "";
    String wasteType = "";
    String packaging = "";
    String wasteDesc = "";
    String lpp = "";

    public WasteCatalogScreen(Object[] oa)
    {
      stateWasteCode = oa[SEARCH_STATE_WASTE_CODE_COL].toString();
      wasteItemID = oa[SEARCH_ITEM_ID_COL].toString();
      vendorProfileID = oa[SEARCH_VENDOR_PROFILE_ID_COL].toString();
      facilityID = oa[SEARCH_FACILITY_COL].toString();
      vendorID = oa[SEARCH_VENDOR_ID_COL].toString();
      wasteMgmtOpt = oa[SEARCH_WASTE_MGMT_OPT_COL].toString();
      wasteCategory = oa[SEARCH_WASTE_CATEGORY_COL].toString();
      wasteType = oa[SEARCH_WASTE_TYPE_COL].toString();
      packaging = oa[SEARCH_PACKAGING_COL].toString();
      wasteDesc = oa[SEARCH_DESC_COL].toString();
      lpp = oa[SEARCH_LPP_COL].toString();
    }

    public String getStateWasteCode()
    {
        return stateWasteCode;
    }

    public String getWasteItemID()
    {
        return wasteItemID;
    }

    public String getVendorProfileID()
    {
        return vendorProfileID;
    }

    public String getFacilityID()
    {
        return facilityID;
    }

    public String getVendorID()
    {
        return vendorID;
    }

    public String getWasteMgmtOpt()
    {
        return wasteMgmtOpt;
    }

    public String getWasteCategory()
    {
        return wasteCategory;
    }

    public String getWasteType()
    {
        return wasteType;
    }


    public String getPackaging()
    {
        return packaging;
    }

    public String getWasteDesc()
    {
        return wasteDesc;
    }

    public String getLPP()
    {
        return lpp;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("STATE_WASTE_CODES = getStateWasteCode");
        v.addElement("WASTE_ITEM_ID = getWasteItemID");
        v.addElement("VENDOR_PROFILE_ID = getVendorProfileID");
        v.addElement("FACILITY_ID = getFacilityID");
        v.addElement("VENDOR_ID = getVendorID");
        v.addElement("WASTE_MGMT_OPT = getWasteMgmtOpt");
        v.addElement("WASTE_CATEGORY = getWasteCategory");
        v.addElement("WASTE_TYPE = getWasteType");
        v.addElement("PACKAGING = getPackaging");
        v.addElement("WASTE_DESC = getWasteDesc");
        v.addElement("LPP = getLPP");
        return v;
    }

    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Object[] oa = (Object[])in.elementAt(i);
            out.addElement(new WasteCatalogScreen(oa));
        }
        return out;
    }
}
