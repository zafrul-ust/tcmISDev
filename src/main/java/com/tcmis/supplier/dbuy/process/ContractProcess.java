package com.tcmis.supplier.dbuy.process;

import com.tcmis.common.creator.CodeCreator;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
// import com.tcmis.common.framework.
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.factory.InventoryGroupDefinitionBeanFactory;
import com.tcmis.internal.supply.factory.CarrierInfoBeanFactory;
import com.tcmis.supplier.dbuy.beans.DbuyContractBean;
import com.tcmis.supplier.dbuy.beans.DbuyContractInputBean;
import com.tcmis.supplier.dbuy.beans.DbuyContractPriceBean;
import com.tcmis.supplier.dbuy.factory.DbuyContractBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuyContractPriceBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuyInventoryGroupBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuySpecDataDisplayViewBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuyStatusViewBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuySupplierBeanFactory;
import org.apache.commons.logging.*;

import java.math.BigDecimal; 
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process for dbuy contract items
 * @version 1.0
 *****************************************************************************/

public class ContractProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ContractProcess(String client) {
    super(client);
  }

  public Collection getSuppliers() throws BaseException {
     Collection supplierBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuySupplierBeanFactory supplierFactory = new DbuySupplierBeanFactory(dbManager);
     try {
        supplierBeans = supplierFactory.select();
     } catch (BaseException be2) {
        log.error("Base Exception in getSuppliers: " + be2);
     } finally {
        dbManager = null;
        supplierFactory = null;
     }     
     return supplierBeans;
  }
  
  public Collection getInventoryGroups() throws BaseException {
     Collection inventoryGroupBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuyInventoryGroupBeanFactory dbuyIGFactory = new DbuyInventoryGroupBeanFactory(dbManager);
     try {
        inventoryGroupBeans = dbuyIGFactory.select();
     } catch (BaseException be2) {
        log.error("Base Exception in getInventoryGroups: " + be2);
     } finally {
        dbManager = null;
        dbuyIGFactory = null;
     }          
     return inventoryGroupBeans; 
  }
  
 public Collection getDbuyItems(String itemId, String supplierId, String inventoryGroup, String status) throws BaseException {
     Collection itemBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractBeanFactory dbuyViewFactory = new DbuyContractBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("supplyPath",SearchCriterion.EQUALS,"Dbuy");
        searchCriteria.addValueToCriterion("supplyPath","Wbuy");
        searchCriteria.addValueToCriterion("supplyPath","Purchaser");
        
        if (itemId != null && itemId.length()>0) {
           searchCriteria.addCriterion("itemId",SearchCriterion.EQUALS,itemId);
        }
        if (supplierId != null && supplierId.length()>0) {
           searchCriteria.addCriterion("supplier",SearchCriterion.EQUALS,supplierId);
        }
        if (inventoryGroup != null && inventoryGroup.length()>0) {
           searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,inventoryGroup);
        }                   
        if (status != null && status.length()>0) {
           searchCriteria.addCriterion("status",SearchCriterion.EQUALS,"DBUY");
        }
        
        itemBeans = dbuyViewFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getDbuyItems: " + be2);
     } finally {
        dbManager = null;
        dbuyViewFactory = null;
     }          
     return itemBeans; 
  }  
 
 /*
  * Search for all the specs for the given item id (for dbuy)
  */
 public Collection getDbuySpecs(String itemId, String supplierId, String inventoryGroup) throws BaseException {
     Collection itemSpecs = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuySpecDataDisplayViewBeanFactory dbuySpecViewFactory = new DbuySpecDataDisplayViewBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        
        if (itemId != null && itemId.length()>0) {
           searchCriteria.addCriterion("itemId",SearchCriterion.EQUALS,itemId);
        }
        if (supplierId != null && supplierId.length()>0) {
           searchCriteria.addCriterion("supplier",SearchCriterion.EQUALS,supplierId);
        }
        if (inventoryGroup != null && inventoryGroup.length()>0) {
           searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,inventoryGroup);
        }                   
        
        itemSpecs = dbuySpecViewFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getDbuySpecs: " + be2);
     } finally {
        dbManager = null;
        dbuySpecViewFactory = null;
     }          
     return itemSpecs; 
  }   
 
  /*
   * Search for all the item prices for the specified contract id
   */
  public Collection getItemPrices(String contractId) throws BaseException {
     Collection itemPrices = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractPriceBeanFactory dbuyContractPriceFactory = new DbuyContractPriceBeanFactory(dbManager);
     try {
        Date now = new Date();
        SearchCriteria searchCriteria = new SearchCriteria();
        
        if (contractId != null && contractId.length()>0) {
           searchCriteria.addCriterion("dbuyContractId",SearchCriterion.EQUALS,contractId);
           searchCriteria.addCriterion("endDate",SearchCriterion.GREATER_THAN,DateHandler.formatOracleDate(now));
        }
        
        itemPrices = dbuyContractPriceFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getItemPrices: " + be2);
     } finally {
        dbManager = null;
        dbuyContractPriceFactory = null;
     }          
     return itemPrices; 
  }   
 
  public Collection getAllInventoryGroups() throws BaseException {
     Collection inventoryGroupBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     InventoryGroupDefinitionBeanFactory igFactory = new InventoryGroupDefinitionBeanFactory(dbManager);
     try {
        inventoryGroupBeans = igFactory.select(null);
     } catch (BaseException be2) {
        log.error("Base Exception in getAllInventoryGroups: " + be2);
     } finally {
        dbManager = null;
        igFactory = null;
     }          
     return inventoryGroupBeans; 
  }
  
  public Collection getCarriers(String invGrp) throws BaseException {
     Collection carrierBeans = null;
     // CarrierInfoBeanFactory
     DbManager dbManager = new DbManager(this.getClient());
     CarrierInfoBeanFactory carrierFactory = new CarrierInfoBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,invGrp);
        carrierBeans = carrierFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getCarriers: " + be2);
     } finally {
        dbManager = null;
        carrierFactory = null;
     }          
     return carrierBeans;
  }
  
  /*
   * Search for all the item prices for the specified contract id
   */
  public void updateItemPrices(String contractId, Double price) throws BaseException {
     DbManager dbManager = new DbManager(this.getClient());
     GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
     Vector params = new Vector(6);
     params.clear();
     params.add(contractId);
     params.add(price);
     procFactory.doProcedure("pkg_dbuy.update_contract_price_for_dbuy",params);

  }
  
  /*
   * Search for all the item prices for the specified contract id
   */
  public void updateItemPrices(String contractId, String price,String endDate, String uptoQty, String currencyId, String comments) throws BaseException {
     DbuyContractPriceBean priceBean = new DbuyContractPriceBean();
     
     BigDecimal contrId = new BigDecimal(contractId);
     BigDecimal newPrice = new BigDecimal(price);
     BigDecimal upto = new BigDecimal(uptoQty);
     Date end = null;
     try {            
        end = DateHandler.getDateFromString("MM/dd/yyyy",endDate);        
     } catch (ParseException pe) {
        log.error("parse exception on date in UpdateItemPrices: " + endDate + " | " + pe.getMessage());
        throw new BaseException(pe);
     }
     
     priceBean.setDbuyContractId(contrId);
     priceBean.setEndDate(end);
     priceBean.setUptoQuantity(upto);
     priceBean.setUnitPrice(newPrice);
     priceBean.setCurrencyId(currencyId);
     priceBean.setComments(comments);
     
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractPriceBeanFactory factory = new DbuyContractPriceBeanFactory(dbManager);     
     
     factory.updatePrice(priceBean);
     
     factory = null;
     dbManager = null;
  }
  
  
  public void updateContractItem(String itemId, String invGrp, String shiptoCoId, String shiptoLocId, String priority, String supplier, String carrier,
                                 String buyer, String sourcer, String terms, String fob, String supplierPart, String supplierContact, String shelfLife,
                                 String critCarrier, String clientPart, String clientPO, String roundTo, String multiple, String consign, String leadTime,
                                 String shipOrigin, String transitTime, String comments, String priceType, String supplyPath, String status, String prevPriority) throws BaseException {
                                    
  
     DbuyContractBean contractItem = new DbuyContractBean();
     
     BigDecimal item = new BigDecimal(itemId);     
     BigDecimal prior = new BigDecimal(priority); 
     BigDecimal buy = new BigDecimal(buyer);
     BigDecimal source = new BigDecimal(sourcer);
     BigDecimal contact = new BigDecimal(supplierContact);
     BigDecimal shelf = new BigDecimal(shelfLife);
     BigDecimal mult = new BigDecimal(multiple);
     BigDecimal lead = new BigDecimal(leadTime);
     BigDecimal transit = new BigDecimal(transitTime);
     BigDecimal prevPrior = new BigDecimal(prevPriority);
     
     contractItem.setItemId(item);
     contractItem.setInventoryGroup(invGrp);
     contractItem.setShipToCompanyId(shiptoCoId);
     contractItem.setShipToLocationId(shiptoLocId);
     contractItem.setPriority(prior);
     contractItem.setSupplier(supplier);
     contractItem.setCarrier(carrier);     
     contractItem.setBuyer(buy);
     contractItem.setSourcer(source);
     contractItem.setPaymentTerms(terms);
     contractItem.setFreightOnBoard(fob);
     contractItem.setSupplierPartNo(supplierPart);    
     contractItem.setSupplierContactId(contact);
     contractItem.setRemainingShelfLifePercent(shelf);
     contractItem.setCriticalOrderCarrier(critCarrier);
     contractItem.setRefClientPartNo(clientPart);
     contractItem.setRefClientPoNo(clientPO);
     contractItem.setRoundToMultiple(roundTo);
     contractItem.setMultipleOf(mult);
     contractItem.setConsignment(consign);
     contractItem.setLeadTimeDays(lead);
     contractItem.setDefaultShipmentOriginState(shipOrigin);
     contractItem.setTransitTimeInDays(transit);
     contractItem.setContractReferenceComments(comments);
     contractItem.setPricingType(priceType);
     contractItem.setStatus(status);
     contractItem.setSupplyPath(supplyPath);
     
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractBeanFactory dbuyViewFactory = new DbuyContractBeanFactory(dbManager);
     
     dbuyViewFactory.update(contractItem, prevPrior);     
     
     dbuyViewFactory = null;
     dbManager = null;     
  }
 
  public void addContractItem(String itemId, String invGrp, String shiptoCoId, String shiptoLocId, String priority, String supplier, String carrier,
                                 String buyer, String sourcer, String terms, String fob, String supplierPart, String supplierContact, String shelfLife,
                                 String critCarrier, String clientPart, String clientPO, String roundTo, String multiple, String consign, String leadTime,
                                 String shipOrigin, String transitTime, String comments, String priceType, String supplyPath, String contractId,
                                 String status, String prevPriority) throws BaseException {
                                    
     // check for fields that cant be null
     if (itemId==null || invGrp==null || shiptoCoId==null || shiptoLocId==null ||
         priority==null || contractId==null || status==null || leadTime==null ||
         supplyPath==null) {
            throw new BaseException("Cannot have an empty/null value for Item ID, Inventory Group, ShipTo Company, ShipTo Location, Priority, Lead Time or Supply Path");
     }                                         
     if (itemId.trim().length()==0 || invGrp.trim().length()==0 || shiptoCoId.trim().length()==0 || shiptoLocId.trim().length()==0 ||
         priority.trim().length()==0 || contractId.trim().length()==0 || status.trim().length()==0 || leadTime.trim().length()==0 ||
         supplyPath.trim().length()==0) {
            throw new BaseException("Cannot have an empty value for Item ID, Inventory Group, ShipTo Company, ShipTo Location, Priority, Lead Time or Supply Path");
     }                                         
                                    
     DbuyContractBean contractItem = new DbuyContractBean();
     
     BigDecimal item = new BigDecimal(itemId);     
     BigDecimal prior = new BigDecimal(priority); 
     BigDecimal buy = new BigDecimal(buyer);
     BigDecimal source = new BigDecimal(sourcer);
     BigDecimal contact = new BigDecimal(supplierContact);
     BigDecimal shelf = new BigDecimal(shelfLife);
     BigDecimal mult = new BigDecimal(multiple);
     BigDecimal lead = new BigDecimal(leadTime);
     BigDecimal transit = new BigDecimal(transitTime);
     BigDecimal prevPrior = new BigDecimal(prevPriority);
     BigDecimal contractid = new BigDecimal(contractId);
     
     contractItem.setItemId(item);
     contractItem.setInventoryGroup(invGrp);
     contractItem.setShipToCompanyId(shiptoCoId);
     contractItem.setShipToLocationId(shiptoLocId);
     contractItem.setPriority(prior);
     contractItem.setSupplier(supplier);
     contractItem.setCarrier(carrier);     
     contractItem.setBuyer(buy);
     contractItem.setSourcer(source);
     contractItem.setPaymentTerms(terms);
     contractItem.setFreightOnBoard(fob);
     contractItem.setSupplierPartNo(supplierPart);    
     contractItem.setSupplierContactId(contact);
     contractItem.setRemainingShelfLifePercent(shelf);
     contractItem.setCriticalOrderCarrier(critCarrier);
     contractItem.setRefClientPartNo(clientPart);
     contractItem.setRefClientPoNo(clientPO);
     contractItem.setRoundToMultiple(roundTo);
     contractItem.setMultipleOf(mult);
     contractItem.setConsignment(consign);
     contractItem.setLeadTimeDays(lead);
     contractItem.setDefaultShipmentOriginState(shipOrigin);
     contractItem.setTransitTimeInDays(transit);
     contractItem.setContractReferenceComments(comments);
     contractItem.setPricingType(priceType);
     contractItem.setSupplyPath(supplyPath);
     contractItem.setStatus(status);
     contractItem.setDbuyContractId(contractid);
     
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractBeanFactory dbuyViewFactory = new DbuyContractBeanFactory(dbManager);
     
     dbuyViewFactory.insert(contractItem);     
     
     dbuyViewFactory = null;
     dbManager = null;     
  }
  
public String addContractItem(DbuyContractInputBean inputBean) throws BaseException {
    String error=null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
      Vector params = new Vector(33);
      BigDecimal nullBigD = null;
      Timestamp nullTime = null;
      String nullString = "";
      if (inputBean.getItemId()!=null) {
         params.add(inputBean.getItemId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getInventoryGroup()!=null) {
         params.add(inputBean.getInventoryGroup());
      } else {
         params.add(nullString);
      }
      if (inputBean.getShipToCompanyId()!=null) {
         params.add(inputBean.getShipToCompanyId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getShipToLocationId()!=null) {
         params.add(inputBean.getShipToLocationId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplier()!=null) {
         params.add(inputBean.getSupplier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCarrier()!=null) {
         params.add(inputBean.getCarrier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getBuyer()!=null) {
         params.add(inputBean.getBuyer());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSourcer()!=null) {
         params.add(inputBean.getSourcer());
      } else {
         params.add(nullString);
      }
      if (inputBean.getPaymentTerms()!=null) {
         params.add(inputBean.getPaymentTerms());
      } else {
         params.add(nullString);
      }
      if (inputBean.getContractType()!=null) {
         params.add(inputBean.getContractType());
      } else {
         params.add(nullString);
      }
      if (inputBean.getFreightOnBoard()!=null) {
         params.add(inputBean.getFreightOnBoard());
      } else {
         params.add(nullString);
      }
      if (inputBean.getMultipleOf()!=null) {
         params.add(inputBean.getMultipleOf());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplierPartNo()!=null) {
         params.add(inputBean.getSupplierPartNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplierContactId()!=null) {
         params.add(inputBean.getSupplierContactId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRemainingShelfLifePercent()!=null) {
         params.add(inputBean.getRemainingShelfLifePercent());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCriticalOrderCarrier()!=null) {
         params.add(inputBean.getCriticalOrderCarrier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRefClientPartNo()!=null) {
         params.add(inputBean.getRefClientPartNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRefClientPoNo()!=null) {
         params.add(inputBean.getRefClientPoNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRoundToMultiple()!=null) {
         params.add(inputBean.getRoundToMultiple());
      } else {
         params.add(nullString);
      }
      if (inputBean.getConsignment()!=null) {
         params.add(inputBean.getConsignment());
      } else {
         params.add(nullString);
      }
      if (inputBean.getLeadTimeDays()!=null) {
         params.add(inputBean.getLeadTimeDays());
      } else {
         params.add(nullString);
      }
      if (inputBean.getDefaultShipmentOriginState()!=null) {
         params.add(inputBean.getDefaultShipmentOriginState());
      } else {
         params.add(nullString);
      }
      if (inputBean.getTransitTimeInDays()!=null) {
         params.add(inputBean.getTransitTimeInDays());
      } else {
         params.add(nullString);
      }
      if (inputBean.getEndDate() != null) {
         params.add(new Timestamp(inputBean.getEndDate().getTime()));
      } else {
         params.add(nullString);
      }
      if (inputBean.getUptoQuantity()!=null) {
         params.add(inputBean.getUptoQuantity());
      } else {
         params.add(nullString);
      }
      if (inputBean.getItemUnitPrice()!=null) {
         params.add(inputBean.getItemUnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId1()!=null) {
         params.add(inputBean.getAddChargeItemId1());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId1UnitPrice()!=null) {
         params.add(inputBean.getAddChargeItemId1UnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId2()!=null) {
         params.add(inputBean.getAddChargeItemId2());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId2UnitPrice()!=null) {
         params.add(inputBean.getAddChargeItemId2UnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getInsertSource()!=null) {
         params.add(inputBean.getInsertSource());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCurrencyId()!=null) {
         params.add(inputBean.getCurrencyId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplyPath()!=null) {
         params.add(inputBean.getSupplyPath());
      } else {
         params.add(nullString);
      }
      Vector outParams = new Vector();
      outParams.add(new Integer(java.sql.Types.VARCHAR));
      
      Collection results = procFactory.doProcedure("pkg_contract_setup.P_ALL_CONTRACT_SETUP",params,outParams);
      log.debug("ContractProcess::addContractItem called pkg_contract_setup.P_ALL_CONTRACT_SETUP");
      Iterator iter = results.iterator();
      if (iter.hasNext()) error = (String) iter.next();
    } catch (BaseException be) {
      log.error("Base Exception calling ContractProcess::addContractItem() " + be);
      log.trace(be);
    }
    return error;
}

 public Collection getContractItem(String contractId, String catPartNo) throws BaseException {
     Collection itemBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuyContractBeanFactory dbuyViewFactory = new DbuyContractBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        
        if (contractId != null && contractId.length()>0) {
           searchCriteria.addCriterion("dbuyContractId",SearchCriterion.EQUALS,contractId);
        }
        
        itemBeans = dbuyViewFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getContractItem: " + be2);
     } finally {
        dbManager = null;
        dbuyViewFactory = null;
     }          
     return itemBeans; 
  }  
 
 public Collection getDbuyStatusBeans() throws BaseException {
     Collection statusBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     DbuyStatusViewBeanFactory dbuyViewFactory = new DbuyStatusViewBeanFactory(dbManager);
     try {        
        statusBeans = dbuyViewFactory.select(null);
     } catch (BaseException be2) {
        log.error("Base Exception in getDbuyStatusBeans: " + be2);
     } finally {
        dbManager = null;
        dbuyViewFactory = null;
     }          
     return statusBeans;     
 }

   /*
    * Sort the Collection based on the column specified by sortColumn
    */
   public Collection sortItems(Collection orders, String sortColumn) throws BaseException {
      BeanSorter beanSorter = new BeanSorter(new DbuyContractBean());
      String sortFieldGetter = getFieldGetterName(sortColumn);
      return beanSorter.sort(orders,sortFieldGetter);
   }
 
  /*
   * create the name of the getter for the given database field name
   */
  private String getFieldGetterName(String col) {
     String fieldName = CodeCreator.getFieldHandlerName(col);
     String getterName = "get" + fieldName.replaceFirst(fieldName.substring(0,1), fieldName.substring(0,1).toUpperCase());
     return getterName;
  }
  
public String testAddItem(DbuyContractInputBean inputBean) throws BaseException {
    String error=null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
      Vector params = new Vector(1);
      BigDecimal nullBigD = null;
      Timestamp nullTime = null;
      String nullString = "";
      if (inputBean.getItemId()!=null) {
         params.add(inputBean.getItemId());
      } else {
         params.add(nullString);
      }
      /*
      if (inputBean.getInventoryGroup()!=null) {
         params.add(inputBean.getInventoryGroup());
      } else {
         params.add(nullString);
      }
      if (inputBean.getShipToCompanyId()!=null) {
         params.add(inputBean.getShipToCompanyId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getShipToLocationId()!=null) {
         params.add(inputBean.getShipToLocationId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplier()!=null) {
         params.add(inputBean.getSupplier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCarrier()!=null) {
         params.add(inputBean.getCarrier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getBuyer()!=null) {
         params.add(inputBean.getBuyer());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSourcer()!=null) {
         params.add(inputBean.getSourcer());
      } else {
         params.add(nullString);
      }
      if (inputBean.getPaymentTerms()!=null) {
         params.add(inputBean.getPaymentTerms());
      } else {
         params.add(nullString);
      }
      if (inputBean.getContractType()!=null) {
         params.add(inputBean.getContractType());
      } else {
         params.add(nullString);
      }
      if (inputBean.getFreightOnBoard()!=null) {
         params.add(inputBean.getFreightOnBoard());
      } else {
         params.add(nullString);
      }
      if (inputBean.getMultipleOf()!=null) {
         params.add(inputBean.getMultipleOf());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplierPartNo()!=null) {
         params.add(inputBean.getSupplierPartNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplierContactId()!=null) {
         params.add(inputBean.getSupplierContactId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRemainingShelfLifePercent()!=null) {
         params.add(inputBean.getRemainingShelfLifePercent());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCriticalOrderCarrier()!=null) {
         params.add(inputBean.getCriticalOrderCarrier());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRefClientPartNo()!=null) {
         params.add(inputBean.getRefClientPartNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRefClientPoNo()!=null) {
         params.add(inputBean.getRefClientPoNo());
      } else {
         params.add(nullString);
      }
      if (inputBean.getRoundToMultiple()!=null) {
         params.add(inputBean.getRoundToMultiple());
      } else {
         params.add(nullString);
      }
      if (inputBean.getConsignment()!=null) {
         params.add(inputBean.getConsignment());
      } else {
         params.add(nullString);
      }
      if (inputBean.getLeadTimeDays()!=null) {
         params.add(inputBean.getLeadTimeDays());
      } else {
         params.add(nullString);
      }
      if (inputBean.getDefaultShipmentOriginState()!=null) {
         params.add(inputBean.getDefaultShipmentOriginState());
      } else {
         params.add(nullString);
      }
      if (inputBean.getTransitTimeInDays()!=null) {
         params.add(inputBean.getTransitTimeInDays());
      } else {
         params.add(nullString);
      }
      if (inputBean.getEndDate() != null) {
         params.add(new Timestamp(inputBean.getEndDate().getTime()));
      } else {
         params.add(nullString);
      }
      if (inputBean.getUptoQuantity()!=null) {
         params.add(inputBean.getUptoQuantity());
      } else {
         params.add(nullString);
      }
      if (inputBean.getItemUnitPrice()!=null) {
         params.add(inputBean.getItemUnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId1()!=null) {
         params.add(inputBean.getAddChargeItemId1());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId1UnitPrice()!=null) {
         params.add(inputBean.getAddChargeItemId1UnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId2()!=null) {
         params.add(inputBean.getAddChargeItemId2());
      } else {
         params.add(nullString);
      }
      if (inputBean.getAddChargeItemId2UnitPrice()!=null) {
         params.add(inputBean.getAddChargeItemId2UnitPrice());
      } else {
         params.add(nullString);
      }
      if (inputBean.getInsertSource()!=null) {
         params.add(inputBean.getInsertSource());
      } else {
         params.add(nullString);
      }
      if (inputBean.getCurrencyId()!=null) {
         params.add(inputBean.getCurrencyId());
      } else {
         params.add(nullString);
      }
      if (inputBean.getSupplyPath()!=null) {
         params.add(inputBean.getSupplyPath());
      } else {
         params.add(nullString);
      }
      */
      Vector outParams = new Vector();
      outParams.add(new Integer(java.sql.Types.VARCHAR));
      
      procFactory.doProcedure("P_TEST_CONTRACT_SETUP",params,outParams);
      log.debug("ContractProcess::addContractItem called P_TEST_CONTRACT_SETUP");
      Iterator iter = outParams.iterator();
      if (iter.hasNext()) error = (String) iter.next();
    } catch (BaseException be) {
      log.error("Base Exception calling ContractProcess::testAddItem() " + be);
      log.trace(be);
    }
    return error;
}  
   
}
