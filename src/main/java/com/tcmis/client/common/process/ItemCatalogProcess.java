package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.ItemCatalogViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for ItemCatalogProcess
 * @version 1.0
 *****************************************************************************/
public class ItemCatalogProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public ItemCatalogProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection searchGlobalItemCatalog(CatalogInputBean bean) {
        StringBuilder query;

        //this is for new consigned item and new supplier item notes
        if("Y".equalsIgnoreCase(bean.getDistribution())) {
            query = new StringBuilder("select a.item_id,a.material_id,a.material_desc,a.packaging,a.grade,a.mfg_desc,");
            query.append("a.country_name,a.mfg_part_no,a.shelf_life,a.msds_on_line,a.sample_only,'n' eng_eval");
            query.append(",( select count(*) from catalog_item where item_id = a.item_id) global_item_count ");
            query.append(" from global.internal_item_catalog_view a");
            StringBuilder where = new StringBuilder("");
            String searchText = bean.getSearchText();
            if (!StringHandler.isBlankString(searchText)) {
                try {
                    where.append(" where " ).append( doItemCatSearchLogic(StringHandler.replace(searchText,"'","''")));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            query.append(where.toString()).append(" order by item_id");
        //this is for catalog add thru catalog page and thru scratch pad
        }else {
            /*
            query = new StringBuilder("select a.item_id,a.material_id,a.material_desc,a.packaging,a.grade,a.mfg_desc,");
            query.append("a.country_name,a.mfg_part_no,a.shelf_life,a.msds_on_line,a.sample_only");
            query.append(" from item_catalog_view a");
            StringBuilder where = new StringBuilder("");
            String searchText = bean.getSearchText();
            if (!StringHandler.isBlankString(searchText)) {
                try {
                    where.append(" where " ).append( doItemCatSearchLogic(StringHandler.replace(searchText,"'","''")));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            query.append(where.toString()).append(" order by item_id");
            */
            // since this table function is slow I am just going to use above view until we can speed it up
            query = new StringBuilder("select * from table (pkg_catalog_add.FX_CATALOG_ADD_ITEM_SEARCH(");
            query.append(SqlHandler.delimitString(bean.getSearchText()));
            if (!StringHandler.isBlankString(bean.getCompanyId())) {
                query.append(",'").append(bean.getCompanyId()).append("'");
            }else {
                query.append(",''");
            }
            if (!StringHandler.isBlankString(bean.getFacilityId())) {
                query.append(",").append(SqlHandler.delimitString(bean.getFacilityId()));
            }else {
                query.append(",''");
            }
            query.append(")) order by item_id");

        }

		Collection c = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemCatalogViewBean());
			c =  factory.selectQuery(query.toString());
			if (c != null) {
				if (c.size() > 0) {
					calculatingNumberOfKitsPerItem(c);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			c = new Vector(0);
		}
		return c;
	}
	
	public Collection searchItemOnlyCatalog(CatalogInputBean bean) {
		StringBuilder query = new StringBuilder("select * from table (pkg_catalog_add.FX_CAT_ADD_ITEM_ONLY_SEARCH(");
		    query.append(SqlHandler.delimitString(bean.getSearchText()));
		    if (!StringHandler.isBlankString(bean.getCompanyId())) {
		        query.append(",'").append(bean.getCompanyId()).append("'");
		    }else {
		        query.append(",''");
		    }
		    if (!StringHandler.isBlankString(bean.getFacilityId())) {
		        query.append(",").append(SqlHandler.delimitString(bean.getFacilityId()));
		    }else {
		        query.append(",''");
		    }
		    query.append(")) order by item_id");

		Collection c = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemCatalogViewBean());
			c =  factory.selectQuery(query.toString());
			if (c != null) {
				if (c.size() > 0) {
					calculatingNumberOfKitsPerItem(c);
				}
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			c = new Vector(0);
		}
		return c;
	}
	
	public Collection searchItemCatalog(CatalogInputBean bean, BigDecimal personnelId) {
		StringBuilder query = new StringBuilder("select a.item_id,a.material_id,a.material_desc,a.packaging,a.grade,a.mfg_desc,").append(
		"a.country_name,a.mfg_part_no,a.shelf_life,a.msds_on_line,a.sample_only");
		StringBuilder where = new StringBuilder("");
		String searchText = bean.getSearchText();
		if (!StringHandler.isBlankString(bean.getPreviousEvalOrder())) {
			query.append(",'y' eng_eval from item_catalog_view a");
			where.append(" where a.item_id in (select cai.item_id from catalog_add_request_new b, catalog_add_item cai").append(
			" where b.company_id = cai.company_id and b.request_id = cai.request_id and cai.part_id = 1 and lower(b.engineering_evaluation) = 'y' and b.request_status = 12").append(
			" and b.eng_eval_facility_id = '").append(bean.getFacilityId()).append("'");
			if (!StringHandler.isBlankString(bean.getEvalRequestor())) {
				where.append(" and b.requestor = ").append(personnelId);
			}
			if (!StringHandler.isBlankString(bean.getEvalWorkArea())) {
				String tmp = bean.getApplicationId();
				if (!StringHandler.isBlankString(tmp) && !tmp.equalsIgnoreCase("My Work Areas")) {
					where.append(" and b.eng_eval_work_area = '").append(tmp).append("'");
				}
			}
			where.append(")");
		}else {
			query.append(",nvl((select 'y' from catalog_add_request_new x, catalog_add_item cai where x.company_id = cai.company_id");
			query.append(" and x.request_id = cai.request_id and cai.item_id = a.item_id and");
			query.append(" x.request_status = 12 and rownum = 1 and lower(x.engineering_evaluation)='y'),'n') eng_eval");
			query.append(" from item_catalog_view a");
		}

		if (!StringHandler.isBlankString(searchText)) {
			try {
				if (where.length() == 0) {                    //where is an empty string if user doesn't limit search to show eng eval item
					where.append(" where " ).append( doItemCatSearchLogic(StringHandler.replace(searchText,"'","''")));
				}else {
					where.append(" and " ).append( doItemCatSearchLogic(StringHandler.replace(searchText,"'","''")));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		Collection c = null;
		try {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemCatalogViewBean());
			c =  factory.selectQuery(query.append(where).append(" order by item_id").toString());
			if (c != null) {
				if (c.size() > 0) {
					calculatingNumberOfKitsPerItem(c);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			c = new Vector(0);
		}
		return c;
	}

	private void calculatingNumberOfKitsPerItem(Collection dataColl) {
		try {
			if (dataColl.size() > 1) {
				Object[] arrayObj = dataColl.toArray();
				int count = 1;
				int firstMatchedItemIndex = 0;
				//go ahead and get the first row and declare it as our current
				ItemCatalogViewBean firstBean = (ItemCatalogViewBean)arrayObj[0];
				BigDecimal currentItem = firstBean.getItemId();
				for (int i = 1; i < arrayObj.length; i++) {
					ItemCatalogViewBean bean = (ItemCatalogViewBean)arrayObj[i];
                    if (currentItem != null) {
                        if (currentItem.equals(bean.getItemId())) {
                            count++;
                            //setting number of kits per item to -1 because it's kit
                            bean.setNumberOfKitsPerItem(new BigDecimal(-1));
                        }else {
                            ItemCatalogViewBean matchBean = (ItemCatalogViewBean)arrayObj[firstMatchedItemIndex];
                            matchBean.setNumberOfKitsPerItem(new BigDecimal(count));
                            //reset count
                            count = 1;
                            //reset first matched
                            firstMatchedItemIndex = i;
                        }
                    }else {
                        ItemCatalogViewBean matchBean = (ItemCatalogViewBean)arrayObj[firstMatchedItemIndex];
                        matchBean.setNumberOfKitsPerItem(new BigDecimal(count));
                        //reset count
                        count = 1;
                        //reset first matched
                        firstMatchedItemIndex = i;
                    }
                    currentItem = bean.getItemId();
				}
				//taking care of the last record
				ItemCatalogViewBean lastBean = (ItemCatalogViewBean)arrayObj[arrayObj.length-1];
				//this the last record is not a kit
				if (lastBean.getNumberOfKitsPerItem() == null) {
					lastBean.setNumberOfKitsPerItem(new BigDecimal(1));
				}else {
					//otherwise it's a kit
					ItemCatalogViewBean matchBean = (ItemCatalogViewBean)arrayObj[firstMatchedItemIndex];
					matchBean.setNumberOfKitsPerItem(new BigDecimal(count));
				}
			}else {
				//since only one record then no need to merge cell
				Object[] arrayObj = dataColl.toArray();
				ItemCatalogViewBean firstBean = (ItemCatalogViewBean)arrayObj[0];
				firstBean.setNumberOfKitsPerItem(new BigDecimal(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String doItemCatSearchLogic(String search) {
        Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " (a.item_id like '%"+search+"%'"+
                     " or lower(a.item_desc) like lower('%" + search + "%'))";
                     //" or lower(a.mfg_desc) like lower('%" + search + "%')"+
                     //" or lower(a.mfg_part_no) like lower('%" + search + "%')) ";
            return result;
		}

		//contains operation in search text
		result += " ( (a.item_id like '%" + likes.elementAt(0).toString().trim() + "%'"+
                  " or lower(a.item_desc) like lower('%" + likes.elementAt(0).toString().trim() + "%'))";
                  //" or lower(a.mfg_desc) like lower('%" + likes.elementAt(0).toString().trim() + "%')"+
                  //" or lower(a.mfg_part_no) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
        boolean butNot = false;
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
				butNot = true;
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			if (butNot) {
				result += " " + op + " (a.item_id "+lk+" '%"+searchS+"%'"+
                          " and lower(a.item_desc) " + lk + " lower('%" + searchS + "%'))";
                          //" and lower(a.mfg_desc) " + lk + " lower('%" + searchS + "%')"+
                          //" and lower(a.mfg_part_no) " + lk + " lower('%" + searchS + "%')) ";
            }else {
				result += " " + op + " (a.item_id "+lk+" '%"+searchS+"%'"+
                          " or lower(a.item_desc) " + lk + " lower('%" + searchS + "%'))";
                          //" or lower(a.mfg_desc) " + lk + " lower('%" + searchS + "%')"+
                          //" or lower(a.mfg_part_no) " + lk + " lower('%" + searchS + "%')) ";
            }
		}
		result += " ) ";

		return result;
	}

	public String doMaterialCatSearchLogic(String search) {
        Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " (m.material_id like '%"+search+"%'"+
                     " or lower(m.material_desc) like lower('%" + search + "%'))";
            return result;
		}

		//contains operation in search text
		result += " ( (m.material_id like '%" + likes.elementAt(0).toString().trim() + "%'"+
                  " or lower(m.material_desc) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
        boolean butNot = false;
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
				butNot = true;
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			if (butNot) {
				result += " " + op + " (m.material_id "+lk+" '%"+searchS+"%'"+
                          " and lower(m.material_desc) " + lk + " lower('%" + searchS + "%')) ";
            }else {
				result += " " + op + " (m.material_id "+lk+" '%"+searchS+"%'"+
                          " or lower(m.material_desc) " + lk + " lower('%" + searchS + "%')) ";
            }
		}
		result += " ) ";

		return result;
	}

    public ExcelHandler  getItemExcelReport(CatalogInputBean inputbean) throws
	NoDataException, BaseException, Exception {

		//Vector<PrCatalogScreenSearchBean> data = (Vector<PrCatalogScreenSearchBean>) getSearchResult(inputbean, personnelId, false);
		ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getClient(),this.getLocale());
		Vector<ItemCatalogViewBean> data = (Vector)this.searchGlobalItemCatalog(inputbean);


		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	 write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.item",
				"inventory.label.componentdescription",
				"inventory.label.packaging",
				"label.grade",
				"label.manufacturer",
				"label.country",
				"label.mfgpartno",
		"catalog.label.shelflife"};
		/*This array defines the type of the excel column.
0 means default depending on the data type. */
		int[] types = {
				0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0,pw.TYPE_PARAGRAPH,0,0,pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
0 means the width will be default depending on the data type.*/
		int[] widths = {0,0,0,10,0,10,10,0};
		/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(ItemCatalogViewBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getMaterialDesc());
			pw.addCell(bean.getPackaging());
			pw.addCell(bean.getGrade());
			pw.addCell(bean.getMfgDesc());
			pw.addCell(bean.getCountryName());
			pw.addCell(bean.getMfgPartNo());
			pw.addCell(bean.getShelfLife());
		}
		return pw;
	}


} //end of class


/*
//test
			query = "select tcm_ops.fx_sales_catalog_search('366', 'contains') from dual";
			Object[] myData = factory.selectCursorIntoObjectArray(query);
			Collection reportData = (Collection)myData[GenericSqlFactory.DATA_INDEX];
			Iterator iter = reportData.iterator();
			while(iter.hasNext()) {
				Object[] rowData = (Object[])iter.next();
				for (int i = 0; i < rowData.length; i++) {
					log.debug(StringHandler.emptyIfNull(rowData[i]));
				}
			}
 */