package radian.tcmis.server7.share.factory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseBeanFactory;
import radian.tcmis.common.util.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.both1.beans.PartCategoryCriteriaBean;

/******************************************************************************
 * CLASSNAME: PartCategoryCriteriaBeanFactory <br>
 * @version: 1.0, Feb 25, 2004 <br>
 *****************************************************************************/

public class PartCategoryCriteriaBeanFactory extends BaseBeanFactory {

  //column names
  public String ATTRIBUTE_CATEGORY = "CATEGORY";
  public String ATTRIBUTE_PARENT = "PARENT";
  public String ATTRIBUTE_REFERENCE = "REFERENCE";
  public String ATTRIBUTE_DISPLAY = "DISPLAY";
  public String ATTRIBUTE_CRITERIA = "CRITERIA";
  public String ATTRIBUTE_CRITERIA_LIMIT = "CRITERIA_LIMIT";
  public String ATTRIBUTE_DISPLAY_ORDER = "DISPLAY_ORDER";
  public String ATTRIBUTE_CATEGORY_SYSTEM = "CATEGORY_SYSTEM";
  //sequence and table names
  public String SEQUENCE = "CATEGORY";
  public String TABLE = "PART_CATEGORY_CRITERIA";

  //constructor
  public PartCategoryCriteriaBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("category")) {
      return ATTRIBUTE_CATEGORY;
    }
    if (attributeName.equals("parent")) {
      return ATTRIBUTE_PARENT;
    }
    if (attributeName.equals("reference")) {
      return ATTRIBUTE_REFERENCE;
    }
    if (attributeName.equals("display")) {
      return ATTRIBUTE_DISPLAY;
    }
    if (attributeName.equals("criteria")) {
      return ATTRIBUTE_CRITERIA;
    }
    if (attributeName.equals("criteriaLimit")) {
      return ATTRIBUTE_CRITERIA_LIMIT;
    }
    if (attributeName.equals("displayOrder")) {
      return ATTRIBUTE_DISPLAY_ORDER;
    }
    if (attributeName.equals("categorySystem")) {
      return ATTRIBUTE_CATEGORY_SYSTEM;

    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PartCategoryCriteriaBean.class);
  }

  //select
  public Collection select(SearchCriteria criteria, TcmISDBModel dbModel) throws BaseException, Exception {

    Collection partCategoryCriteriaBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + " order by display_order";
    DataSet dataSet = dbModel.select(query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PartCategoryCriteriaBean partCategoryCriteriaBean = new PartCategoryCriteriaBean();
      load(dataSetRow, partCategoryCriteriaBean);
      partCategoryCriteriaBeanColl.add(partCategoryCriteriaBean);
    }

    return partCategoryCriteriaBeanColl;
  } //end of method

  //select
  public Collection selectByCategorySystem(SearchCriteria criteria, TcmISDBModel dbModel) throws BaseException, Exception {

    Collection partCategoryCriteriaBeanColl = new Vector();
    Collection categorySystemName = new Vector();
    Collection categorySystemCollTex = new Vector();
    Collection categorySystemCollSCAQMD = new Vector();
    int categorySystemCount = 0;

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + " order by display_order";
    DataSet dataSet = dbModel.select(query);
//System.out.println("----- query: "+query);
    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PartCategoryCriteriaBean partCategoryCriteriaBean = new PartCategoryCriteriaBean();
      load(dataSetRow, partCategoryCriteriaBean);

      if (partCategoryCriteriaBean.getParent() == null) {
        categorySystemName.add(partCategoryCriteriaBean.getCategorySystem());
        categorySystemCount++;
      }

      if (categorySystemCount == 1) {
        categorySystemCollTex.add(partCategoryCriteriaBean);
      }else if (categorySystemCount == 2) {
        categorySystemCollSCAQMD.add(partCategoryCriteriaBean);
      }
    }
    partCategoryCriteriaBeanColl.add(categorySystemCollTex);
    partCategoryCriteriaBeanColl.add(categorySystemCollSCAQMD);
    //add the number of category systems name to the end of collection
    partCategoryCriteriaBeanColl.add(categorySystemName);
    return partCategoryCriteriaBeanColl;
  }


}