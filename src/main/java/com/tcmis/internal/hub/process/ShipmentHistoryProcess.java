package com.tcmis.internal.hub.process;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CarrierInfoBean;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryInputBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryViewBean;
import com.tcmis.internal.hub.beans.VvIncotermBean;
import com.tcmis.internal.hub.beans.VvMotIncotermBean;
import com.tcmis.internal.hub.factory.CarrierInfoBeanFactory;
import com.tcmis.internal.hub.factory.HubIgDockOvBeanFactory;
import com.tcmis.internal.hub.factory.ShipmentBeanFactory;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class ShipmentHistoryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ShipmentHistoryProcess(String client) {
		super(client);
	}

	public ShipmentHistoryProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getDropDownData(int personnelId) throws BaseException {
		//log.debug("Calling getDropDownData" + getClient());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId",
				SearchCriterion.EQUALS,
				"" + personnelId);

		DbManager dbManager = new DbManager(getClient(),getLocale());
		HubIgDockOvBeanFactory factory = new HubIgDockOvBeanFactory(dbManager);
		return factory.selectObject(criteria);
	}


	public Object[] getSearchResult(PersonnelBean personnelBean, ShipmentHistoryInputBean bean,
			Collection dropDownData) throws BaseException {
		/*	SearchCriteria criteria = new SearchCriteria();
	SortCriteria scriteria = new SortCriteria();
	scriteria.addCriterion("dateDelivered");
	scriteria.addCriterion("shipmentId");
    if(bean.getHub() != null && bean.getHub().length() > 0) {
      criteria.addCriterion("branchPlant",
                            SearchCriterion.EQUALS,
                            bean.getHub());
    }
    if(bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0) {
      criteria.addCriterion("inventoryGroup",
                            SearchCriterion.EQUALS,
                            bean.getInventoryGroup());
    }

    if(bean.getCompanyId() != null && bean.getCompanyId().length() > 0) {
      criteria.addCriterion("companyId",
                            SearchCriterion.EQUALS,
                            bean.getCompanyId());
    }
    if(bean.getFacilityId() != null && bean.getFacilityId().length() > 0) {
      criteria.addCriterion("facilityId",
                            SearchCriterion.EQUALS,
                            bean.getFacilityId());
    }
    if(bean.getShipmentId() != null && bean.getShipmentId().length() > 0) {
      if("shipmentId".equalsIgnoreCase(bean.getShipmentOrTracking())) {
        criteria.addCriterion("shipmentId",
                              SearchCriterion.EQUALS,
                              bean.getShipmentId().toString());
      }
      else if("trackingNumber".equalsIgnoreCase(bean.getShipmentOrTracking())) {
        criteria.addCriterion("trackingNumber",
                              SearchCriterion.EQUALS,
                              bean.getShipmentId().toString());

      }
    }
    if(bean.getDock() != null && bean.getDock().length() > 0) {
      criteria.addCriterion("shipToLocationId",
                            SearchCriterion.EQUALS,
                            bean.getDock());
    }
    if(bean.getToDate() != null) {
      criteria.addCriterion("dateDelivered",
                            SearchCriterion.TO_DATE,
                            bean.getToDate());
    }
    if(bean.getFromDate() != null) {
      criteria.addCriterion("dateDelivered",
                            SearchCriterion.FROM_DATE,
                            bean.getFromDate());
    }
    DbManager dbManager = new DbManager(getClient(),getLocale());
    ShipmentHistoryViewBeanFactory factory = new ShipmentHistoryViewBeanFactory(dbManager);

    Collection<ShipmentHistoryViewBean> c = factory.select(criteria,scriteria);
		 */
		DbManager  dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);

		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		Date toDate = bean.getToDate();
		if(bean.getToDate() != null)
			toDate.setTime(toDate.getTime() +  86399000); // Bump time to 23:59:59 rather than 00:00:00 by adding the appropriate amount of milliseconds

		String query = "select * from table (pkg_shipment_history.fx_shipment_history('" +
		StringHandler.emptyIfNull(bean.getHub()) + "','" +
		StringHandler.emptyIfNull(bean.getShipmentId()) + "','" +
		StringHandler.emptyIfNull(bean.getOpsEntityId()) + "','" +
		StringHandler.emptyIfNull(bean.getInventoryGroup()) + "','" +
		StringHandler.emptyIfNull(bean.getDock()) + "','" +
		StringHandler.emptyIfNull(bean.getPrNumber()) + "'," +
		(null!=bean.getFromDate()?"TO_DATE('" + dateFormatter.format( bean.getFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +"," +
		(null!=bean.getToDate()?"TO_DATE('" + dateFormatter.format( toDate) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +"," +
		StringHandler.emptyIfNull(personnelBean.getPersonnelId()) + ",'" +
		StringHandler.emptyIfNull(bean.getCustomerId()) + "','" + 
		StringHandler.emptyIfNull(bean.getShipmentOrTracking()) + "'))";
		genericSqlFactory.setBeanObject(new ShipmentHistoryViewBean());
		Collection<ShipmentHistoryViewBean> c = genericSqlFactory.selectQuery(query);

		HashMap map = new HashMap();
		Collection<ShipmentHistoryViewBean> ccc = null;
		boolean showProForma = false;
		
		if( c.size() > 0 ) {
			CarrierInfoBeanFactory cfac = new CarrierInfoBeanFactory(dbManager);

			for(ShipmentHistoryViewBean sbean:c) {
				String ig = sbean.getInventoryGroup();
				Collection<CarrierInfoBean> cc = (Collection<CarrierInfoBean>) map.get(ig);
				if( cc == null) {
					SearchCriteria ccriteria = new SearchCriteria();
					ccriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, ig);
					cc = cfac.select(ccriteria,null);
					map.put(ig, cc);
				}
				sbean.setCarrierInfoBeanCollection(cc);
				
				if(sbean.isProformaRequired())
					showProForma = true;
			}

			SearchCriteria ccriteria = new SearchCriteria();
			ccriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, "ALL");
			ccc = cfac.select(ccriteria,null);
		}
		Object objs[] = {c,map,ccc, showProForma ? "Y":"N"};
		return objs;
	}

	public ExcelHandler  getExcelReport(PersonnelBean personnelBean, ShipmentHistoryInputBean bean, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Object[] result = getSearchResult(personnelBean,bean,null);
		Collection<ShipmentHistoryViewBean> data = (Collection) result[0];
		ExcelHandler pw = new ExcelHandler(library);		
		Collection<VvMotIncotermBean> modeOfTransportCol = getIncotermForModeOfTransport();
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.shipmentid","label.datedelivered","label.inventorygroup","label.shipto","label.customer", "label.companyid", "label.shipto",
				"label.carrier","label.accountowner","label.accountnumber","label.trackingnumber","label.modeoftransport","label.incoterms"
		};
		/*This array defines the type of the excel column.
  0 means default depending on the data type. */
		int[] types = {
				0,pw.TYPE_CALENDAR,0,0,0,0,0,
				0,0,0,0,0,0
		};
		/*This array defines the default width of the column when the Excel file opens.
  0 means the width will be default depending on the data type.*/
		int[] widths = {
				10,0,20,20,20,12,10,
				20,17,17,22,15,40};
		/*This array defines the horizontal alignment of the data in a cell.
  0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,0,0,
				0,0,0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (ShipmentHistoryViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getShipmentId());
			pw.addCell(member.getDateDelivered());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(StringHandler.emptyIfNull(member.getAddressLine1())+" "+StringHandler.emptyIfNull(member.getAddressLine2())+" "+StringHandler.emptyIfNull(member.getAddressLine3())+" "+StringHandler.emptyIfNull(member.getAddressLine4())+" "+StringHandler.emptyIfNull(member.getAddressLine5()));
			pw.addCell(member.getCustomerName());
			pw.addCell(member.getCompanyId());
			pw.addCell(member.getShipToLocationId());
			pw.addCell(member.getCarrierCode());
			pw.addCell(member.getCarrierOwner());
			pw.addCell(member.getAccount());
			pw.addCell(member.getTrackingNumber());
			pw.addCell(member.getModeOfTransport());
			String incotermDesc = "";
			if (member.getModeOfTransport() != null && !member.getModeOfTransport().equalsIgnoreCase("")) {
				incotermDesc = getIncotermDescription(member.getModeOfTransport(), member.getIncoterm(), modeOfTransportCol); 
			}
			pw.addCell(incotermDesc);
		}
		return pw;
	}

	public String getIncotermDescription (String modeOfTransport, String incoterm, Collection<VvMotIncotermBean> modeOfTransportCol ) {
		for (VvMotIncotermBean bean : modeOfTransportCol) {
			if (modeOfTransport != null && bean.getModeOfTransport().equalsIgnoreCase(modeOfTransport)) {
				Collection<VvIncotermBean> incotermList = bean.getVvIncotermList();			
				for (VvIncotermBean incBean : incotermList) {
					if (incoterm != null && incoterm.equalsIgnoreCase(incBean.getIncoterm())) {
						return incBean.getIncotermShortDesc();
					}
				}
			}
		}
		return "";
	}

	/*
  public Collection getSearchResult(ShipmentHistoryInputBean bean,
                                    Collection dropDownData) throws BaseException {
    SearchCriteria criteria = new SearchCriteria();
    if(bean.getSourceHub() != null && bean.getSourceHub().length() > 0) {
      criteria.addCriterion("branchPlant",
                            SearchCriterion.EQUALS,
                            bean.getSourceHub());
    }
    if(bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0) {
      criteria.addCriterion("inventoryGroup",
                            SearchCriterion.EQUALS,
                            bean.getInventoryGroup());
    }
    if(bean.getShipmentId() != null && bean.getShipmentId().intValue() != 0) {
      criteria.addCriterion("shipmentId",
                            SearchCriterion.EQUALS,
                            bean.getShipmentId().toString());
    }
    if(bean.getShipTo() != null && bean.getShipTo().length() > 0) {
      //if shipTo location is specified, add companyId to search criteria
      Vector v = this.getCompanyId(bean, dropDownData);
      for(int i=0; i<v.size(); i++) {
        if(i == 0) {
          criteria.addCriterion("companyId",
                                SearchCriterion.EQUALS,
                                v.elementAt(i).toString());
        }
        else {
          criteria.addValueToCriterion("companyId", v.elementAt(i).toString());
        }
      }
      criteria.addCriterion("shipToLocationId",
                            SearchCriterion.EQUALS,
                            bean.getShipTo());
    }
    if(bean.getCompanyId() != null && bean.getCompanyId().length() > 0) {
      criteria.addCriterion("companyId",
                            SearchCriterion.EQUALS,
                            bean.getCompanyId());
    }
    if(bean.getToDate() != null) {
      criteria.addCriterion("dateDelivered",
                            SearchCriterion.LESS_THAN_OR_EQUAL_TO,
                            DateHandler.formatOracleDate(bean.getToDate()));
    }
    if(bean.getFromDate() != null) {
      criteria.addCriterion("dateDelivered",
                            SearchCriterion.GREATER_THAN_OR_EQUAL_TO,
                            DateHandler.formatOracleDate(bean.getFromDate()));
    }

    DbManager dbManager = new DbManager(getClient());
    ShipmentHistoryViewBeanFactory factory = new ShipmentHistoryViewBeanFactory(dbManager);
    return factory.select(criteria);
  }


  private Vector getCompanyId(ShipmentHistoryInputBean inputBean,
                              Collection dropDownData) throws BaseException {
    boolean found = false;
    //most of the times there will only be one company id
    Vector resultVector = new Vector(1);
    Iterator iterator = dropDownData.iterator();
    while(iterator.hasNext() && !found) {
      HubIgDockOvBean bean = (HubIgDockOvBean)iterator.next();
      if(bean.getBranchPlant().equalsIgnoreCase(inputBean.getSourceHub())) {
        Collection c2 = bean.getInventoryGroupDocks();
        Iterator iterator2 = c2.iterator();
        while(iterator2.hasNext() && !found) {
          InventoryGroupDockObjBean bean2 = (InventoryGroupDockObjBean)iterator2.next();
          if(bean2.getInventoryGroup().equalsIgnoreCase(inputBean.getInventoryGroup())) {
            Collection c3 = bean2.getDocks();
            Iterator iterator3 = c3.iterator();
            while(iterator3.hasNext()) {
              FacilityDockObjBean bean3 = (FacilityDockObjBean)iterator3.next();
              if(bean3.getDockLocationId().equalsIgnoreCase(inputBean.getDock())) {
                resultVector.add(bean3.getCompanyId());
                found = true;
              }
            }
          }
        }
      }
    }
    return resultVector;
  }
	 */
	public void updateShipping(Collection beanCollection) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ShipmentBeanFactory factory = new ShipmentBeanFactory(dbManager);
		ShipmentBean bean = null;
		Iterator iterator = beanCollection.iterator();
		Connection connection = dbManager.getConnection();
		try {
			connection.setAutoCommit(false);
			while (iterator.hasNext()) {
				bean = (ShipmentBean)iterator.next();
				if( bean.getOk() != null )
					factory.updateFromHistory(bean, connection);
			}
			connection.commit();
		}
		catch(Exception e) {
			//in case of an error we'll rollback
			try {
				connection.rollback();
			}
			catch(Exception ex) {
				//ignore
			}
			BaseException be = new BaseException("Error updating shipment history" + e.getMessage());
			be.setRootCause(e);
			throw be;
		}
		finally {
			try {
				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
			}
			catch(Exception e) {
				//ignore
			}
		}
	}

	public Collection getModeOfTransportData() throws BaseException  {
		StringBuffer query = new StringBuffer("select * from VV_MODE_OF_TRANSPORT");	    
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvMotIncotermBean());
		return factory.selectQuery(query.toString());		
	}

	@SuppressWarnings("unchecked")
	public Collection getIncotermForModeOfTransport() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
        Connection conn = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		Collection<VvMotIncotermBean> modeOfTransportCol = getModeOfTransportData();
		
		for (VvMotIncotermBean modeOfTransportbean: modeOfTransportCol)
		{	
			factory.setBeanObject(new VvIncotermBean());
			modeOfTransportbean.setVvIncotermList(factory.selectQuery("select mot.mode_of_transport, inc.incoterm, inc.incoterm_short_desc from vv_incoterm inc, vv_incoterm_mode_of_transport mot where inc.incoterm = mot.incoterm "					
                    + " and mot.mode_of_transport = '"+ modeOfTransportbean.getModeOfTransport() + "'",conn));
		}
		
		return modeOfTransportCol;
	}
}