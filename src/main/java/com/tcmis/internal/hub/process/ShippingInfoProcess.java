package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

import com.tcmis.common.admin.beans.VvHazardClassBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.sql.builder.QueryBuilder;
import com.tcmis.common.util.sql.builder.QueryBuilderFactory;
import com.tcmis.common.util.sql.builder.QueryTable;
import com.tcmis.internal.hub.beans.DotPartMaterialViewBean;
import com.tcmis.internal.hub.beans.ShippingInfoStorageVvBean;

/******************************************************************************
 * Process for ShippingInfo
 * @version 1.0
 *****************************************************************************/
public class ShippingInfoProcess
    extends GenericProcess {

  public ShippingInfoProcess(String client) {
	    super(client);
  }
  
  public ShippingInfoProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getDotPartMaterialViewColl(DotPartMaterialViewBean inputBean) throws BaseException {
	  DbManager dbManager = new DbManager(getClient(), getLocale());
	  GenericSqlFactory factory = new GenericSqlFactory(dbManager,new DotPartMaterialViewBean());
	  SearchCriteria searchCriteria = new SearchCriteria();
     try {

        if (inputBean.getItemId() != null) {
           searchCriteria.addCriterion("itemId",SearchCriterion.EQUALS,inputBean.getItemId().toString());
        }

        SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);		
		sort.addCriterion("partId,materialId");	

        return factory.select(searchCriteria, sort, "dot_part_material_view");

     } finally {
        dbManager = null;
        factory = null;
     }

  }
  
  public Collection getHazardClassColl() throws BaseException {
	  DbManager dbManager = new DbManager(getClient(), getLocale());
	  GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvHazardClassBean());
	  SearchCriteria searchCriteria = new SearchCriteria();
     try {

        SortCriteria sort = new SortCriteria();
        sort.addCriterion("hazardClass");	

        return factory.select(searchCriteria, sort, "vv_hazard_class");

     } finally {
        dbManager = null;
        factory = null;
     }

  }
 
  public Collection updateItem(BigDecimal personnelId, Collection<DotPartMaterialViewBean> updateBeanCollection) throws BaseException {
	  String errorMsg = "";
	  Vector errorMessages = new Vector();

	  Connection conn = factory.getDbManager().getConnection();
	  try {
		  for(DotPartMaterialViewBean dotPartMaterialViewBean : updateBeanCollection) {
			  Collection inArgs =
						buildProcedureInput(
								dotPartMaterialViewBean.getItemId(),dotPartMaterialViewBean.getPartId(),
								"Y".equals(dotPartMaterialViewBean.getOrmD())?"Y":"N",
								"Y".equals(dotPartMaterialViewBean.getPkgGtRq())?"Y":"N",
								"Y".equals(dotPartMaterialViewBean.getBulkPkgMarinePollutant())?"Y":"N",
								dotPartMaterialViewBean.getReportableQuantityLb(),dotPartMaterialViewBean.getErg(),
								dotPartMaterialViewBean.getEccn(),
								"Y".equals(dotPartMaterialViewBean.getMarinePollutant())?"Y":"N",
								"Y".equals(dotPartMaterialViewBean.getDryIce())?"Y":"N",
								personnelId, new Date(), 
								dotPartMaterialViewBean.getHazmatId(),dotPartMaterialViewBean.getProperShippingName(),
								dotPartMaterialViewBean.getShippingInfoRemarks(),dotPartMaterialViewBean.getSubsidiaryHazardClass(),
								dotPartMaterialViewBean.getHazmatTechnicalName(),dotPartMaterialViewBean.getIataDgId(),
								dotPartMaterialViewBean.getIataProperShippingName(),dotPartMaterialViewBean.getIataShippingRemark(),
								dotPartMaterialViewBean.getIataSubrisk(),dotPartMaterialViewBean.getIataTechnicalName(),
								dotPartMaterialViewBean.getIataMixtureSolution(),dotPartMaterialViewBean.getAdrId(),
								dotPartMaterialViewBean.getAdrProperShippingName(),dotPartMaterialViewBean.getAdrShippingRemark(),
								dotPartMaterialViewBean.getAdrTechnicalName(),dotPartMaterialViewBean.getAdrSubrisk(),
								dotPartMaterialViewBean.getImdgId(),dotPartMaterialViewBean.getImdgProperShippingName(),
								dotPartMaterialViewBean.getImdgShippingRemark(),dotPartMaterialViewBean.getImdgSubsidiaryRisk(),
								dotPartMaterialViewBean.getImdgTechnicalName()
						);
					Vector outArgs = new Vector();
					outArgs.add( new Integer(java.sql.Types.VARCHAR) );
					Vector result = (Vector) factory.doProcedure(conn, "P_UPDATE_PART_SHIP", inArgs, outArgs);
					
					if(result.size()>0 && result.get(0) != null)
					{
						errorMessages.add(result.get(0).toString());
					}
					saveStorageData(dotPartMaterialViewBean, conn);
		   }
	  } catch(SQLException e) {
		  throw new BaseException(e);
	  } finally {
		  factory.getDbManager().returnConnection(conn);
	  }

	   return errorMessages;
  }
  
  private void saveStorageData(DotPartMaterialViewBean input, Connection conn) throws SQLException, BaseException {
	  QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
	  		.update(new QueryTable("global.material"))
	  		.setString("wms_acid_base", input.getWmsAcidBase())
	  		.setString("wms_corrosive", input.getWmsCorrosive())
	  		.setString("wms_aerosol", input.getWmsAerosol())
	  		.setString("wms_flammable", input.getWmsFlammable())
	  		.setString("wms_toxic", input.getWmsToxic())
	  		.setString("wms_oxidizer", input.getWmsOxidizer())
	  		.setString("wms_water_reactive", input.getWmsWaterReactive())
	  		.setString("wms_reactive", input.getWmsReactive())
	  		.setString("wms_organic_peroxide", input.getWmsOrganicPeroxide())
	  		.setString("wms_pyrophoric", input.getWmsPyrophoric())
	  		.setString("wms_water_miscible", input.getWmsWaterMiscible())
	  		.whereNumberEquals("material_id", input.getMaterialId());
	  
	  factory.deleteInsertUpdate(query.toString(), conn);
	  
	  query = QueryBuilderFactory.generateBuilder(conn)
			  .update(new QueryTable("global.part"))
			  .setString("wms_container", input.getWmsContainer())
			  .setString("wms_cont_pressure_relieving", input.getWmsContPressureRelieving())
			  .whereNumberEquals("item_id", input.getItemId())
			  .whereNumberEquals("part_id", input.getPartId());
	  
	  factory.deleteInsertUpdate(query.toString(), conn);
  }
  
  public Collection<String> listAcidBaseChoices() throws BaseException {
	  String query = "select wms_acid_base from vv_wms_acid_base order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsAcidBase()).collect(Collectors.toList());
  }
  
  public Collection<String> listCorrosiveChoices() throws BaseException {
	  String query = "select wms_corrosive from vv_wms_corrosive order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsCorrosive()).collect(Collectors.toList());
  }
  
  public Collection<String> listAerosolChoices() throws BaseException {
	  String query = "select wms_aerosol from vv_wms_aerosol order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsAerosol()).collect(Collectors.toList());
  }
  
  public Collection<String> listFlammableChoices() throws BaseException {
	  String query = "select wms_flammable from vv_wms_flammable order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsFlammable()).collect(Collectors.toList());
  }
  
  public Collection<String> listToxicChoices() throws BaseException {
	  String query = "select wms_toxic from vv_wms_toxic order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsToxic()).collect(Collectors.toList());
  }
  
  public Collection<String> listOxidizerChoices() throws BaseException {
	  String query = "select wms_oxidizer from vv_wms_oxidizer order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsOxidizer()).collect(Collectors.toList());
  }
  
  public Collection<String> listReactiveChoices() throws BaseException {
	  String query = "select wms_reactive from vv_wms_reactive order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsReactive()).collect(Collectors.toList());
  }
  
  public Collection<String> listWaterReactiveChoices() throws BaseException {
	  String query = "select wms_water_reactive from vv_wms_water_reactive order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsWaterReactive()).collect(Collectors.toList());
  }
  
  public Collection<String> listOrganicPeroxideChoices() throws BaseException {
	  String query = "select wms_organic_peroxide from vv_wms_organic_peroxide order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsOrganicPeroxide()).collect(Collectors.toList());
  }
  
  public Collection<String> listContainerChoices() throws BaseException {
	  String query = "select wms_container from vv_wms_container order by priority desc";
	  
	  Collection<ShippingInfoStorageVvBean> coll = factory.setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
	  return coll.stream().map(bean -> bean.getWmsContainer()).collect(Collectors.toList());
  }
}
