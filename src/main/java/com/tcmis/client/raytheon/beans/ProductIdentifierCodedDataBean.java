package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ProductIdentifierCodedDataBean <br>
 * @version: 1.0, Jun 29, 2005 <br>
 *****************************************************************************/

public class ProductIdentifierCodedDataBean
    extends BaseDataBean {

  private java.lang.String productIdentifier;
  private java.lang.String productIdentifierQualifierCoded;
  private java.lang.String productIdentifierQualifierCodedOther;

  //constructor
  public ProductIdentifierCodedDataBean() {
  }

  //setters
  public void setProductIdentifier(java.lang.String productIdentifier) {
    this.productIdentifier = productIdentifier;
  }

  public void setProductIdentifierQualifierCoded(java.lang.String
                                                 productIdentifierQualifierCoded) {
    this.productIdentifierQualifierCoded = productIdentifierQualifierCoded;
  }

  public void setProductIdentifierQualifierCodedOther(java.lang.String
      productIdentifierQualifierCodedOther) {
    this.productIdentifierQualifierCodedOther = productIdentifierQualifierCodedOther;
  }

  //getters
  public java.lang.String getProductIdentifier() {
    return productIdentifier;
  }

  public java.lang.String getProductIdentifierQualifierCoded() {
    return productIdentifierQualifierCoded;
  }

  public java.lang.String getProductIdentifierQualifierCodedOther() {
    return productIdentifierQualifierCodedOther;
  }
}