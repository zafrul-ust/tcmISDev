package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.client.catalog.beans.*;
import com.tcmis.common.admin.beans.VvFlowDownBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;


public class QualitySummaryProcess extends BaseProcess {
	
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	Log log = LogFactory.getLog(this.getClass());
	DbManager dbManager;
	Connection connection = null;
	GenericSqlFactory genericSqlFactory;

	  public QualitySummaryProcess(String client) {
	    super(client);
	    library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	  }
	  
	  public QualitySummaryProcess(String client,String locale) {
		    super(client,locale);
		    library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	  }
	  
	  
	  public QualitySummaryViewBean getSearchData(QualitySummaryInputBean bean, BigDecimal personnelId) throws BaseException, Exception {
		  QualitySummaryViewBean qualitySummaryViewBean = new QualitySummaryViewBean();
			try {
				dbManager = new DbManager(getClient(),this.getLocale());
				connection = dbManager.getConnection();
				genericSqlFactory = new GenericSqlFactory(dbManager);
				qualitySummaryViewBean.setFacilityId(bean.getCatalogId());
				qualitySummaryViewBean.setFacPartNo(bean.getCatPartNo());
				qualitySummaryViewBean.setFacItemColl(getFacItem(bean));
				qualitySummaryViewBean.setQualityIdLabel(getQualityIdLabel(bean));
				qualitySummaryViewBean.setSpecificationsColl(getFacSpecDetail(bean));
				qualitySummaryViewBean.setPurchasingNotesColl(getPurchasingNotes(bean));
				qualitySummaryViewBean.setReceivingNotesColl(getReceivingNotes(bean));
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				dbManager.returnConnection(connection);
				dbManager = null;
				connection = null;
				genericSqlFactory = null;
			}
			return qualitySummaryViewBean;
		}


          private String getQualityIdLabel(QualitySummaryInputBean bean) throws BaseException {
		    StringBuilder result = new StringBuilder("");
		    try {
			   genericSqlFactory.setBeanObject(new CatalogFacilityBean());
			   StringBuilder query = new StringBuilder("select quality_id_label from catalog where catalog_id = '").append(bean.getCatalogId());
			   query.append("' and catalog_company_id = '").append(bean.getCatalogCompanyId());
			   query.append("'");
			   Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
				Vector tmpV = new Vector();
				while(iter.hasNext()) {
					CatalogFacilityBean tmpBean = (CatalogFacilityBean)iter.next();
					if (!StringHandler.isBlankString(tmpBean.getQualityIdLabel()) &&
						 !tmpV.contains(tmpBean.getQualityIdLabel())) {
						if (result.length() == 0) {
							result.append(tmpBean.getQualityIdLabel());
						}else {
							result.append("/").append(tmpBean.getQualityIdLabel());
						}
						tmpV.addElement(tmpBean.getQualityIdLabel());
					}
				}
			 }catch (Exception e) {
				e.printStackTrace();
			}
			return result.toString();
		}

	  private Collection getFacItem(QualitySummaryInputBean bean) throws BaseException {
		    Collection result = new Vector(0);
		    try {
			   genericSqlFactory.setBeanObject(new FacItemBean());
			   StringBuilder query = new StringBuilder("select customer_part_revision, part_description,recert_instructions,incoming_testing,quality_id");
               query.append(",pkg_catalog_planned_add.fx_approval_codes(catalog_company_id, catalog_id, cat_part_no,").append(bean.getPartGroupNo()).append(",'").append(bean.getFacilityId()).append("') approval_code");
               query.append(", het_usage_recording from company_fac_item where catalog_id = '").append(bean.getCatalogId());
			   query.append("' and cat_part_no = '").append(bean.getCatPartNo());
			   query.append("' and catalog_company_id = '").append(bean.getCatalogCompanyId());
			   query.append("'");
			   result = genericSqlFactory.selectQuery(query.toString(),connection);
			   Iterator iter = result.iterator();
			   while (iter.hasNext()) {
				   FacItemBean fib = (FacItemBean) iter.next();
				   getMaterialCategoryData(bean, fib);
			   }
		    }catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	  
	  public Collection getQualifiedProducts(QualitySummaryInputBean bean) throws BaseException, Exception {
		    DbManager dbManager = new DbManager(getClient(),this.getLocale());
		    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new QualityProductsBean());
			 SearchCriteria criteria = new SearchCriteria();
			 if(bean.getCatPartNo()!= null)
			 {
			 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,bean.getCatPartNo());
			 }
			 if(bean.getCatalogCompanyId()!= null)
			 {
			 criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS,bean.getCatalogCompanyId());
			 }
			 if(bean.getCatalogId()!= null)
			 {
			 criteria.addCriterion("catalogId", SearchCriterion.EQUALS,bean.getCatalogId());
			 }
			 SortCriteria sortCriteria = new SortCriteria();
			 sortCriteria.addCriterion("itemId");
			 return  factory.select(criteria, sortCriteria,"cat_part_component_detail");
		}
	  
	  public Collection createRelationalObject(Collection qualityProductColl) {
		  Collection finalqualityProductColl = new Vector();
			String nextKitMsdsNumber = "";
			String nextItemId = "";
			String shelfLifeBasis = null;
			int sameItemCount = 0;
			Vector collectionVector = new Vector(qualityProductColl);
			Vector catPartV1 = new Vector();
			for (int loop = 0; loop < collectionVector.size(); loop++) {

				QualityProductsBean currentQualityProductsBean = (QualityProductsBean) collectionVector.elementAt(loop);
				String currentItemId = "" + currentQualityProductsBean.getItemId() + "";
				String currentKitMsdsNumber = "" + currentQualityProductsBean.getKitMsdsNumber() + "";
							
				
				if (!((loop + 1) == collectionVector.size())) {
					QualityProductsBean nextQualityProductsBean = (QualityProductsBean) collectionVector.elementAt(loop + 1);

					nextKitMsdsNumber = nextQualityProductsBean.getKitMsdsNumber();
					nextItemId = "" + nextQualityProductsBean.getItemId() + "";
					
				} else {
					nextKitMsdsNumber = "";
					nextItemId = "";
					
				}

				boolean sameKitMsdsNumber = false;
				boolean sameItemId = false;

				if (currentItemId.equalsIgnoreCase(nextItemId)) {
					sameItemId = true;
					sameItemCount++;
					if (nextItemId.equalsIgnoreCase(currentItemId)) {
						sameItemId = true;
					}
				}

				CatPartComponentBean catPartComponentBean = new CatPartComponentBean();
				catPartComponentBean.setMaterialDesc(currentQualityProductsBean.getMaterialDesc());
				catPartComponentBean.setPackaging(currentQualityProductsBean.getPackaging());
				catPartComponentBean.setMfgId(currentQualityProductsBean.getMfgId());
				catPartComponentBean.setMfgDesc(currentQualityProductsBean.getMfgDesc());
				catPartComponentBean.setMfgPartNo(currentQualityProductsBean.getMfgPartNo());
				catPartComponentBean.setCompMsds(currentQualityProductsBean.getCompMsds());
				catPartComponentBean.setShelfLifeDays(currentQualityProductsBean.getShelfLifeDays());
				if(currentQualityProductsBean.getShelfLifeBasis() != null)
				{
					if(currentQualityProductsBean.getShelfLifeBasis().equalsIgnoreCase("-1"))
					{
						shelfLifeBasis=library.getString("label.indefinite");
					}
					else {
						shelfLifeBasis=library.getString(currentQualityProductsBean.getJspLabel());
					}
				}
				catPartComponentBean.setShelfLifeBasis(shelfLifeBasis);
				catPartComponentBean.setDisplayTemp(currentQualityProductsBean.getDisplayTemp());
				catPartComponentBean.setMaxRecertNumber(currentQualityProductsBean.getMaxRecertNumber());
				catPartComponentBean.setRoomTempOutTime(currentQualityProductsBean.getRoomTempOutTime());
				
				catPartV1.add(catPartComponentBean);

				if (sameItemId) {

				}  else {
					currentQualityProductsBean.setCatPartColl((Vector) catPartV1.clone());
					catPartV1 = new Vector();
					currentQualityProductsBean.setRowSpan(new BigDecimal(sameItemCount + 1));

					finalqualityProductColl.add(currentQualityProductsBean);
					sameItemCount = 0;

				}
			}

			//log.info("Final collectionSize here " + finalPkgInventoryDetailWebPrInventoryBeanCollection.size() + "");
			return finalqualityProductColl;

		}
	  
	  private Collection getFacSpecDetail(QualitySummaryInputBean bean) throws BaseException, Exception {
			genericSqlFactory.setBeanObject(new FacSpecViewBean());
			StringBuilder query = new StringBuilder("select * from FAC_SPEC_DETAIL_VIEW where catalog_id = '").append(bean.getCatalogId());
			query.append("' and cat_part_no = '").append(bean.getCatPartNo());
			query.append("' and catalog_company_id = '").append(bean.getCatalogCompanyId());
			query.append("' and (COC != 'N' or COA != 'N')");
			Collection result = genericSqlFactory.selectQuery(query.toString(),connection);
			return result;
		}
	  
	  private Collection getPurchasingNotes(QualitySummaryInputBean bean) throws BaseException, Exception {
		   genericSqlFactory.setBeanObject(new VvFlowDownBean());
			SearchCriteria criteria = new SearchCriteria();
			if(bean.getCatalogId()!= null)
			 {
			 criteria.addCriterion("catalogId", SearchCriterion.EQUALS,bean.getCatalogId());
			 }
			if(bean.getCatPartNo()!= null)
			 {
			 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,bean.getCatPartNo());
			 }
			if(bean.getCatalogCompanyId()!= null)
			 {
			 criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS,bean.getCatalogCompanyId());
			 }
			
			criteria.addCriterion("flowDownType", SearchCriterion.IN,"'Quality','Packaging'" );
			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("flowDown");
			return genericSqlFactory.select(criteria, sortCriteria,connection,"fac_item_flow_down_view");
		}
	  
	  private Collection getReceivingNotes(QualitySummaryInputBean bean) throws BaseException, Exception {
		   genericSqlFactory.setBeanObject(new VvFlowDownBean());
			SearchCriteria criteria = new SearchCriteria();
			if(bean.getCatalogId()!= null)
			 {
			 criteria.addCriterion("catalogId", SearchCriterion.EQUALS,bean.getCatalogId());
			 }
			if(bean.getCatPartNo()!= null)
			 {
			 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,bean.getCatPartNo());
			 }
			if(bean.getCatalogCompanyId()!= null)
			 {
			 criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS,bean.getCatalogCompanyId());
			 }
			criteria.addCriterion("flowDownType", SearchCriterion.IN,"'Receiving'" );
			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("flowDown");
			return genericSqlFactory.select(criteria, sortCriteria,connection,"fac_item_flow_down_view");
		}
	  
	  private void getMaterialCategoryData(QualitySummaryInputBean inputBean, FacItemBean outputBean) {
		  try {
	          if (!StringHandler.isBlankString(inputBean.getCatPartNo())) {
	              StringBuilder query = new StringBuilder("select msc.material_subcategory_name,mc.material_category_name");
	              query.append(" from fac_part_use_code fpuc, vv_material_subcategory msc, vv_material_category mc");
	              query.append(" where fpuc.catalog_company_id = '").append(inputBean.getCatalogCompanyId());
	              query.append("' and fpuc.catalog_id = '").append(inputBean.getCatalogId()).append("' and fpuc.cat_part_no = '").append(inputBean.getCatPartNo());
	              query.append("' and fpuc.part_group_no = ").append(inputBean.getPartGroupNo());
	              query.append(" and fpuc.company_id = msc.company_id and fpuc.material_subcategory_id = msc.material_subcategory_id");
	              query.append(" and msc.material_category_id = mc.material_category_id");
	              genericSqlFactory.setBeanObject(new MaterialCategorySubcatViewBean());
	              Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
	              while(iter.hasNext()) {
	                  MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean)iter.next();
	                  if (bean.getMaterialCategoryName() != null)
	                      outputBean.setMaterialCategoryName(bean.getMaterialCategoryName().toString());
	                  else
	                     outputBean.setMaterialCategoryName("");
	                  if (bean.getMaterialSubcategoryName() != null)
	                      outputBean.setMaterialSubcategoryName(bean.getMaterialSubcategoryName().toString());
	                  else
	                      outputBean.setMaterialSubcategoryName("");
	                  break;
	              } //end of loop
	          }
		  }
		  catch (Exception e) {
	          e.printStackTrace();
	      }
	  }
}
