package radian.web.erw.pdfReports;

import java.util.Hashtable;
import java.util.Vector;

public class catalogSearchData
{
  private String COMMENTS="";
  private String CATALOG_ID="";
  private String CAT_PART_NO="";
  private String PART_GROUP_NO="";
  private String INVENTORY_GROUP="";
  private String PART_GROUP="";
  private String PART_ITEM_GROUP="";
  private String STORAGE_TEMP="";
  private String SHELF_LIFE="";
  private String SHELF_LIFE_BASIS="";
  private String ITEM_ID="";
  private String BUNDLE="";
  private String CATALOG_PRICE="";
  private String UNIT_PRICE="";
  private String MIN_BUY="";
  private String PART_DESCRIPTION="";
  private String QUANTITY_PER_BUNDLE="";
  private String PART_ID="";
  private String COMPONENTS_PER_ITEM="";
  private String SIZE_VARIES="";
  private String MATERIAL_DESC="";
  private String MFG_DESC="";
  private String MATERIAL_ID="";
  private String PACKAGING="";
  private String GRADE="";
  private String DIMENSION="";
  private String MFG_PART_NO="";
  private String MSDS_ON_LINE="";
  private String APPROVAL_STATUS="";
  private String LIMIT_QUANTITY_PERIOD1="";
  private String DAYS_PERIOD1="";
  private String LIMIT_QUANTITY_PERIOD2="";
  private String DAYS_PERIOD2="";
  private String SOURCE_HUB="";
  private String STOCKING_METHOD="";
  private String SPECS="";

  public catalogSearchData( Hashtable h,int size)
  {
	COMMENTS="";
	CATALOG_ID="";
	CAT_PART_NO="";
	PART_GROUP_NO="";
	INVENTORY_GROUP="";
	PART_GROUP="";
	PART_ITEM_GROUP="";
	STORAGE_TEMP="";
	SHELF_LIFE="";
	SHELF_LIFE_BASIS="";
	ITEM_ID="";
	BUNDLE="";
	CATALOG_PRICE="";
	UNIT_PRICE="";
	MIN_BUY="";
	PART_DESCRIPTION="";
	QUANTITY_PER_BUNDLE="";
	PART_ID="";
	COMPONENTS_PER_ITEM="";
	SIZE_VARIES="";
	MATERIAL_DESC="";
	MFG_DESC="";
	MATERIAL_ID="";
	PACKAGING="";
	GRADE="";
	DIMENSION="";
	MFG_PART_NO="";
	MSDS_ON_LINE="";
	APPROVAL_STATUS="";
	LIMIT_QUANTITY_PERIOD1="";
	DAYS_PERIOD1="";
	LIMIT_QUANTITY_PERIOD2="";
	DAYS_PERIOD2="";
	SOURCE_HUB="";
	STOCKING_METHOD="";
	SPECS="";

	for ( int i=0; i < size; i++ )
	  switch ( i )
	  {
		case 0:
this.COMMENTS = h.get("COMMENTS").toString();
break;
case 1:
this.CATALOG_ID = h.get("CATALOG_ID").toString();
break;
case 2:
this.CAT_PART_NO = h.get("CAT_PART_NO").toString();
break;
case 3:
this.PART_GROUP_NO = h.get("PART_GROUP_NO").toString();
break;
case 4:
this.INVENTORY_GROUP = h.get("INVENTORY_GROUP").toString();
break;
case 5:
this.PART_GROUP = h.get("PART_GROUP").toString();
break;
case 6:
this.PART_ITEM_GROUP = h.get("PART_ITEM_GROUP").toString();
break;
case 7:
this.SPECS = h.get("SPECS").toString();
break;
case 8:
this.SHELF_LIFE = h.get("SHELF_LIFE").toString();
break;
case 9:
this.STOCKING_METHOD = h.get("STOCKING_METHOD").toString();
break;
case 10:
this.ITEM_ID = h.get("ITEM_ID").toString();
break;
case 11:
this.BUNDLE = h.get("BUNDLE").toString();
break;
case 12:
this.CATALOG_PRICE = h.get("CATALOG_PRICE").toString();
break;
case 13:
this.UNIT_PRICE = h.get("UNIT_PRICE").toString();
break;
case 14:
this.MIN_BUY = h.get("MIN_BUY").toString();
break;
case 15:
this.PART_DESCRIPTION = h.get("PART_DESCRIPTION").toString();
break;
case 16:
this.QUANTITY_PER_BUNDLE = h.get("QUANTITY_PER_BUNDLE").toString();
break;
case 17:
this.PART_ID = h.get("PART_ID").toString();
break;
case 18:
this.COMPONENTS_PER_ITEM = h.get("COMPONENTS_PER_ITEM").toString();
break;
case 19:
this.SIZE_VARIES = h.get("SIZE_VARIES").toString();
break;
case 20:
this.MATERIAL_DESC = h.get("MATERIAL_DESC").toString();
break;
case 21:
this.MFG_DESC = h.get("MFG_DESC").toString();
break;
case 22:
this.MATERIAL_ID = h.get("MATERIAL_ID").toString();
break;
case 23:
this.PACKAGING = h.get("PACKAGING").toString();
break;
case 24:
this.GRADE = h.get("GRADE").toString();
break;
case 25:
this.DIMENSION = h.get("DIMENSION").toString();
break;
case 26:
this.MFG_PART_NO = h.get("MFG_PART_NO").toString();
break;
case 27:
this.MSDS_ON_LINE = h.get("MSDS_ON_LINE").toString();
break;
case 28:
this.APPROVAL_STATUS = h.get("APPROVAL_STATUS").toString();
break;
case 29:
this.LIMIT_QUANTITY_PERIOD1 = h.get("LIMIT_QUANTITY_PERIOD1").toString();
break;
case 30:
this.SOURCE_HUB = h.get("SOURCE_HUB").toString();
break;
case 31:
this.LIMIT_QUANTITY_PERIOD2 = h.get("LIMIT_QUANTITY_PERIOD2").toString();
break;
case 32:
//this.DAYS_PERIOD2 = h.get("DAYS_PERIOD2").toString();
break;
case 33:
//this.DAYS_PERIOD1 = h.get("DAYS_PERIOD1").toString();
break;
case 34:
//this.SHELF_LIFE_BASIS = h.get("SHELF_LIFE_BASIS").toString();
break;
case 35:
//this.STORAGE_TEMP = h.get("STORAGE_TEMP").toString();
break;
	  }
  }

public String getcommentsDesc(){return COMMENTS;}
public String getcatalog_idDesc(){return CATALOG_ID;}
public String getcat_part_noDesc(){return CAT_PART_NO;}
public String getpart_group_noDesc(){return PART_GROUP_NO;}
public String getinventory_groupDesc(){return INVENTORY_GROUP;}
public String getpart_groupDesc(){return PART_GROUP;}
public String getpart_item_groupDesc(){return PART_ITEM_GROUP;}
//public String getstorage_tempDesc(){return STORAGE_TEMP;}
public String getshelf_lifeDesc(){return SHELF_LIFE;}
//public String getshelf_life_basisDesc(){return SHELF_LIFE_BASIS;}
public String getitem_idDesc(){return ITEM_ID;}
public String getbundleDesc(){return BUNDLE;}
public String getcatalog_priceDesc(){return CATALOG_PRICE;}
public String getunit_priceDesc(){return UNIT_PRICE;}
public String getmin_buyDesc(){return MIN_BUY;}
public String getpart_descriptionDesc(){return PART_DESCRIPTION;}
public String getquantity_per_bundleDesc(){return QUANTITY_PER_BUNDLE;}
public String getpart_idDesc(){return PART_ID;}
public String getcomponents_per_itemDesc(){return COMPONENTS_PER_ITEM;}
public String getsize_variesDesc(){return SIZE_VARIES;}
public String getmaterial_descDesc(){return MATERIAL_DESC;}
public String getmfg_descDesc(){return MFG_DESC;}
public String getmaterial_idDesc(){return MATERIAL_ID;}
public String getpackagingDesc(){return PACKAGING;}
public String getgradeDesc(){return GRADE;}
public String getdimensionDesc(){return DIMENSION;}
public String getmfg_part_noDesc(){return MFG_PART_NO;}
public String getmsds_on_lineDesc(){return MSDS_ON_LINE;}
public String getapproval_statusDesc(){return APPROVAL_STATUS;}
public String getlimit_quantity_period1Desc(){return LIMIT_QUANTITY_PERIOD1;}
//public String getdays_period1Desc(){return DAYS_PERIOD1;}
public String getlimit_quantity_period2Desc(){return LIMIT_QUANTITY_PERIOD2;}
//public String getdays_period2Desc(){return DAYS_PERIOD2;}
public String getsource_hubDesc(){return SOURCE_HUB;}
public String getstocking_methodDesc(){return STOCKING_METHOD;}
public String getspecsDesc(){return SPECS;}


  public static Vector getFieldVector()
  {
	Vector v=new Vector();
	v.addElement( "COMMENTS = getcommentsDesc" );
	v.addElement( "CATALOG_ID = getcatalog_idDesc" );
	v.addElement( "CAT_PART_NO = getcat_part_noDesc" );
	v.addElement( "PART_GROUP_NO = getpart_group_noDesc" );
	v.addElement( "INVENTORY_GROUP = getinventory_groupDesc" );
	v.addElement( "PART_GROUP = getpart_groupDesc" );
	v.addElement( "PART_ITEM_GROUP = getpart_item_groupDesc" );
//	v.addElement( "STORAGE_TEMP = getstorage_tempDesc" );
	v.addElement( "SHELF_LIFE = getshelf_lifeDesc" );
//	v.addElement( "SHELF_LIFE_BASIS = getshelf_life_basisDesc" );
	v.addElement( "ITEM_ID = getitem_idDesc" );
	v.addElement( "BUNDLE = getbundleDesc" );
	v.addElement( "CATALOG_PRICE = getcatalog_priceDesc" );
	v.addElement( "UNIT_PRICE = getunit_priceDesc" );
	v.addElement( "MIN_BUY = getmin_buyDesc" );
	v.addElement( "PART_DESCRIPTION = getpart_descriptionDesc" );
	v.addElement( "QUANTITY_PER_BUNDLE = getquantity_per_bundleDesc" );
	v.addElement( "PART_ID = getpart_idDesc" );
	v.addElement( "COMPONENTS_PER_ITEM = getcomponents_per_itemDesc" );
	v.addElement( "SIZE_VARIES = getsize_variesDesc" );
	v.addElement( "MATERIAL_DESC = getmaterial_descDesc" );
	v.addElement( "MFG_DESC = getmfg_descDesc" );
	v.addElement( "MATERIAL_ID = getmaterial_idDesc" );
	v.addElement( "PACKAGING = getpackagingDesc" );
	v.addElement( "GRADE = getgradeDesc" );
	v.addElement( "DIMENSION = getdimensionDesc" );
	v.addElement( "MFG_PART_NO = getmfg_part_noDesc" );
	v.addElement( "MSDS_ON_LINE = getmsds_on_lineDesc" );
	v.addElement( "APPROVAL_STATUS = getapproval_statusDesc" );
	v.addElement( "LIMIT_QUANTITY_PERIOD1 = getlimit_quantity_period1Desc" );
//	v.addElement( "DAYS_PERIOD1 = getdays_period1Desc" );
	v.addElement( "LIMIT_QUANTITY_PERIOD2 = getlimit_quantity_period2Desc" );
//	v.addElement( "DAYS_PERIOD2 = getdays_period2Desc" );
	v.addElement( "SOURCE_HUB = getsource_hubDesc" );
	v.addElement( "STOCKING_METHOD = getstocking_methodDesc" );
	v.addElement("SPECS = getspecsDesc");
	v.trimToSize();

	return v;
  }

  public static Vector getVector( Vector in )
  {
	Vector out=new Vector();
	try
	{
	  for ( int i=0; i < in.size(); i++ )
	  {
		//System.out.print(i);
		Hashtable hdd= ( Hashtable ) in.elementAt( i );
		out.addElement( new catalogSearchData( hdd,hdd.size() ) );
	  }
	}
	catch ( Exception e11 )
	{
	  e11.printStackTrace();
	}
	return out;
  }

}