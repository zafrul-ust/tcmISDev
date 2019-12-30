package com.tcmis.client.aerojet.process;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.cxml.beans.CxmlDocumentDataBean;
import com.tcmis.client.cxml.beans.PunchOutSetupRequestBean;
import com.tcmis.client.cxml.beans.ShoppingCartViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.StringHandler;

public class IProcuShoppingCartParser extends GenericProcess{

  public IProcuShoppingCartParser(String client) {
    super(client);
  }

  public String getCxml(CxmlDocumentDataBean cxmlDocumentDataBean, 
                        PunchOutSetupRequestBean punchoutSetupRequestBean,
                        Collection shoppingCartViewBeanCollection) throws BaseException {
    String response = null;
    BigDecimal totalAmount = new BigDecimal("0");
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    StringWriter sw = new StringWriter();
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.003/cXML.dtd\">");
    sw.write("<cXML payloadID=\"" + cxmlDocumentDataBean.getResponsePayloadId() + "\" timestamp=\"" + cxmlDocumentDataBean.getTimestamp() + "\">");
    sw.write("<Header>");
//the incoming "from" is the outgoing "to" and vice versa.
    sw.write("<From>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getToDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</From>");
    sw.write("<To>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getFromDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getFromIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("</To>");
    sw.write("<Sender>");
    sw.write("<Credential domain=\"" + cxmlDocumentDataBean.getToDomain() + "\">");
    sw.write("<Identity>" + cxmlDocumentDataBean.getToIdentity() + "</Identity>");
    sw.write("</Credential>");
    sw.write("<UserAgent>tcmis</UserAgent>");
    sw.write("</Sender>");
    sw.write("</Header>");
    //in Chucks code this could be set to test but I don't see the need...
    sw.write("<Message deploymentMode=\"production\">");
    sw.write("<PunchOutOrderMessage>");
    sw.write("<BuyerCookie>" + punchoutSetupRequestBean.getBuyerCookie() + "</BuyerCookie>");

    //to be able to put the total amount I'll have to jump to after the loop

//for each item
    StringWriter itemSw = new StringWriter();
    Iterator iterator = shoppingCartViewBeanCollection.iterator();
    while(iterator.hasNext()) {
      ShoppingCartViewBean shoppingCartViewBean = (ShoppingCartViewBean)iterator.next();
      totalAmount = totalAmount.add((shoppingCartViewBean.getQuantity()).multiply(shoppingCartViewBean.getQuotedPrice()));
      itemSw.write("<ItemIn quantity=\"" + shoppingCartViewBean.getQuantity() + "\">");
      itemSw.write("<ItemID>");
      itemSw.write("<SupplierPartID>" + shoppingCartViewBean.getItemId() + "</SupplierPartID>");
      itemSw.write("<SupplierPartAuxiliaryID>" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + "</SupplierPartAuxiliaryID>");
      itemSw.write("</ItemID>");
      itemSw.write("<ItemDetail>");
      itemSw.write("<UnitPrice>");
      itemSw.write("<Money currency=\"USD\">" + shoppingCartViewBean.getQuotedPrice() + "</Money>");
      itemSw.write("</UnitPrice>");

      itemSw.write("<Description xml:lang=\"en-US\">" + shoppingCartViewBean.getFacPartNo() + " - " + 
                   StringHandler.xmlEncode(shoppingCartViewBean.getMaterialDesc()) + " [" + StringHandler.xmlEncode(shoppingCartViewBean.getPackaging()) + 
                   " (MR" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + 
                   ")]</Description>");
      itemSw.write("<UnitOfMeasure>EA</UnitOfMeasure>");
      itemSw.write("<Classification domain=\"UNSPSC\">" + shoppingCartViewBean.getAccountSysDesc() + "</Classification>");
      itemSw.write("</ItemDetail>");
      itemSw.write("</ItemIn>");
    }
//end for each item
//now I have the total amount and can write the rest of the cxml
    //this is hardcode in Chucks code
    sw.write("<PunchOutOrderMessageHeader quoteStatus=\"\" operationAllowed=\"create\">");
    sw.write("<Total>");
    //currency is hardcoded in Chucks code
    sw.write("<Money currency=\"USD\">" + decimalFormat.format(totalAmount) + "</Money>");
    sw.write("</Total>");
    sw.write("</PunchOutOrderMessageHeader>");
    sw.write(itemSw.toString());
    sw.write("</PunchOutOrderMessage>");
    sw.write("</Message>");
    sw.write("</cXML>");
    response = sw.toString();
    return response;
  }
// Larry Note: This one can be resued for any one that allow quoted price empty  
  public String getAerojetXml(CxmlDocumentDataBean cxmlDocumentDataBean, 
		  PunchOutSetupRequestBean punchoutSetupRequestBean,
		  Collection shoppingCartViewBeanCollection) throws BaseException {
	  String response = null;
	  BigDecimal totalAmount = new BigDecimal("0");

//	  <?xml version = '1.0' encoding = 'UTF-8'?>
//	  <response>
//	  <header version="1.0">
//	  <return returnCode="S"/>
//	  </header>
//	  <body>
//	  <OrderLinesDataElements>
//	  <catalogTradingPartner>
//	  <![CDATA[OracleExchange]]>
//	  </catalogTradingPartner>
//	  <orderLine>
//	  <contract>
//	  <buyerContract>
//	  <contractNumber>
//	  <![CDATA[2345]]>
//	  </contractNumber>
//	  </buyerContract>
//	  </contract>
//	  <item lineType="GOODS" quantity="3.0">
//	  <itemNumber>
//	  <supplierItemNumber>
//	  <itemID>
//	  <![CDATA[P456]]>
//	  </itemID>
//	  </supplierItemNumber>
//	  </itemNumber>
//	  <itemDescription>
//	  <![CDATA[Red Pencil]]>
//	  </itemDescription>
//	  <unitOfMeasure>
//	  <supplierUnitOfMeasure>
//	  <supplierUOMType>
//	  <![CDATA[DZ]]>
//	  </supplierUOMType>
//	  <supplierUOMQuantity>
//	  <![CDATA[]]>
//	  </supplierUOMQuantity>
//	  </supplierUnitOfMeasure>
//	  </unitOfMeasure>
//	  </item>
//	  <category>
//	  <categoryCode categoryCodeIdentifier="SUPPLIER">
//	  <![CDATA[BC Personal Digital Assistants (PDA's) or Pen-Based
//	  Computers]]>
//	  </categoryCode>
//	  </category>
//	  <price>
//	  <currency>
//	  <![CDATA[USD]]>
//	  </currency>
//	  <unitPrice>
//	  <![CDATA[9.99]]>
//	  </unitPrice>
//	  </price>
//	  <supplier>
//	  <supplierTradingPartnerCode>
//	  <![CDATA[105676]]>
//	  </supplierTradingPartnerCode>
//	  <supplierName>
//	  <![CDATA[Supplier Corp.]]>
//	  </supplierName>
//	  </supplier>
//	  </orderLine>
//	  </OrderLinesDataElements>
//	  </body>
//	  </response>
	  
	  DecimalFormat decimalFormat = new DecimalFormat("0.00");
	  StringWriter sw = new StringWriter();
	  // I will list Unknown/Hard Code data here.
	  // catalogTradingPartner = HaasTCM
	  // contractNumber	= ''
	  // supplierTradingPartnerCode = HaasTCM
	  sw.write("<?xml version = '1.0' encoding = 'UTF-8'?><response><header version='1.0'><return returnCode='S'/></header>");
	  sw.write("<body><OrderLinesDataElements><catalogTradingPartner><![CDATA[HaasTCM]]></catalogTradingPartner>");
			  
	  //to be able to put the total amount I'll have to jump to after the loop

	  //for each item
	  StringWriter itemSw = new StringWriter();
	  Iterator iterator = shoppingCartViewBeanCollection.iterator();
	  while(iterator.hasNext()) {
		  ShoppingCartViewBean shoppingCartViewBean = (ShoppingCartViewBean)iterator.next();
		  //Larry Note: just for testing.      
		  if( GenericProcess.isBlank(shoppingCartViewBean.getQuotedPrice()))
			  totalAmount = null;
		  if( totalAmount != null )
			  totalAmount = totalAmount.add((shoppingCartViewBean.getQuantity()).multiply(shoppingCartViewBean.getQuotedPrice()));
// 1st line contract.		  
		  itemSw.write("<orderLine><contract><buyerContract><contractNumber><![CDATA[]]></contractNumber></buyerContract></contract>");
// 2nd line item.
		  itemSw.write("<item lineType='GOODS' quantity='" + shoppingCartViewBean.getQuantity() +"'>"); 
//		  itemSw.write("<itemNumber><supplierItemNumber><itemID><![CDATA[" + shoppingCartViewBean.getItemId() + "]]></itemID></supplierItemNumber><buyerItemNumber><itemID><![CDATA[" + shoppingCartViewBean.getFacPartNo() + "]]></itemID></buyerItemNumber></itemNumber>");
		  itemSw.write("<itemNumber><supplierItemNumber><itemID><![CDATA[" + ""                               + "]]></itemID></supplierItemNumber><buyerItemNumber><itemID><![CDATA[" + shoppingCartViewBean.getFacPartNo() + "]]></itemID></buyerItemNumber></itemNumber>");
		  itemSw.write("<itemDescription><![CDATA[" + shoppingCartViewBean.getFacPartNo() + " - " + 
				  StringHandler.xmlEncode(shoppingCartViewBean.getMaterialDesc()) + " [" + StringHandler.xmlEncode(shoppingCartViewBean.getPackaging()) + 
				  " (MR" + shoppingCartViewBean.getPrNumber() + "-" + shoppingCartViewBean.getLineItem() + 
				  ")]]]></itemDescription>"); 
		  String uom = getUom(shoppingCartViewBean.getFacPartNo());//"EACH";//shoppingCartViewBean.getUnitOfSale() +
		  itemSw.write("<unitOfMeasure><supplierUnitOfMeasure><supplierUOMType><![CDATA["+ uom +"]]></supplierUOMType><supplierUOMQuantity><![CDATA[]]></supplierUOMQuantity></supplierUnitOfMeasure></unitOfMeasure>");
		  itemSw.write("</item>");
		  itemSw.write("<category><categoryCode categoryCodeIdentifier='SUPPLIER'><![CDATA[AEROJET]]></categoryCode></category>");
		  itemSw.write("<price><currency><![CDATA[USD]]></currency><unitPrice><![CDATA[" + StringHandler.emptyIfNull(shoppingCartViewBean.getQuotedPrice()) + "]]></unitPrice></price>");
		  itemSw.write("<supplier><supplierTradingPartnerCode><![CDATA[HaasTCM]]></supplierTradingPartnerCode><supplierName><![CDATA[Haas Group International.]]></supplierName></supplier>");
		  itemSw.write("</orderLine>");
	  }
	  sw.write(itemSw.toString());
	  sw.write("</OrderLinesDataElements></body></response>");
	  response = sw.toString();
	  return response;
  }
  
  //select quality_id from fac_item where facility_id = 'xxxxx' and fac_part_no = '123123'
  String getCategoryCode(String facilityId, String catalogCompanyId, String catalogId, String catPartNo, BigDecimal partGroupNo) {	  
  //shoppingCartViewBean.getFacilityId(),shoppingCartViewBean.getCatPartNo());//"EACH";//shoppingCartViewBean.getUnitOfSale() +
	  String categoryCode = "";
	  StringBuilder query = new StringBuilder();
	  try {
		  //select cat_part_no,unit_of_sale from catalog_part_inventory where company_id='AEROJET' and cat_part_no like '3%48' 
		  //categoryCode = factory.selectSingleValue("select quality_id from fac_item where facility_id = "+this.getSqlString(facilityId)+" and fac_part_no = "+this.getSqlString(catPartNo));
		  //categoryCode = factory.selectSingleValue("select quality_id from fac_item where fac_part_no = "+this.getSqlString(catPartNo));
		  //use material_subcategory_id from fac_part_use_code table as the category id
		  query.append(" SELECT MATERIAL_SUBCATEGORY_NAME ");
		  query.append(" FROM FAC_PART_USE_CODE A, VV_MATERIAL_SUBCATEGORY B ");
		  query.append(" WHERE A.COMPANY_ID = 'AEROJET' ");
		  query.append(" AND A.FACILITY_ID = " + this.getSqlString(facilityId));
		  query.append(" AND A.CATALOG_COMPANY_ID = " + this.getSqlString(catalogCompanyId));
		  query.append(" AND A.CATALOG_ID = " + this.getSqlString(catalogId));
		  query.append(" AND A.CAT_PART_NO = " + this.getSqlString(catPartNo));
		  query.append(" AND A.PART_GROUP_NO = " + this.getSqlString(partGroupNo));
		  query.append(" AND A.COMPANY_ID = B.COMPANY_ID ");
		  query.append(" AND A.CATALOG_COMPANY_ID = B.CATALOG_COMPANY_ID ");
		  query.append(" AND A.CATALOG_ID = B.CATALOG_ID ");
		  query.append(" AND A.MATERIAL_SUBCATEGORY_ID = B.MATERIAL_SUBCATEGORY_ID ");
		     	 	
		  categoryCode = factory.selectSingleValue(query.toString());
			   
	  }catch(Exception ex){};
	  return categoryCode;
  }
  
  String getUom(String partno){
	  String uom = "";
	  try {
		  //select cat_part_no,unit_of_sale from catalog_part_inventory where company_id='AEROJET' and cat_part_no like '3%48' 
		  uom = factory.selectSingleValue("select unit_of_sale from catalog_part_inventory where company_id='AEROJET' and cat_part_no = "+this.getSqlString(partno));
	  }catch(Exception ex){};
	  return uom;
  }
  
  String getBuyerItemRevision(String catalogCompanyId, String catalogId, String catPartNo){
	  String buyerItemRevision = "";
	  try {		   
		  buyerItemRevision = factory.selectSingleValue("SELECT CUSTOMER_PART_REVISION FROM FAC_ITEM WHERE " +
		  		" COMPANY_ID = " + this.getSqlString(catalogCompanyId) +  
		  		" AND FACILITY_ID = " + this.getSqlString(catalogId) + 
		  		" AND FAC_PART_NO = " + this.getSqlString(catPartNo));
	  } catch(Exception ex){};
	  return buyerItemRevision;
  }
  
  String getUnitOfSaleQuantityPerEach(String catalogCompanyId, String catalogId, String catPartNo){
	  StringBuilder qtyPerEachQuery = new StringBuilder();
	  String qtyPerEach = "";
	  try {		   
		  qtyPerEachQuery.append(" SELECT UNIT_OF_SALE_QUANTITY_PER_EACH FROM CATALOG_PART_UNIT_OF_SALE WHERE ");
		  qtyPerEachQuery.append(" COMPANY_ID = ").append(this.getSqlString(this.client));
		  qtyPerEachQuery.append(" AND CATALOG_COMPANY_ID = ").append(this.getSqlString(catalogCompanyId));
		  qtyPerEachQuery.append(" AND CATALOG_ID = ").append(this.getSqlString(catalogId));
		  qtyPerEachQuery.append(" AND CAT_PART_NO = ").append(this.getSqlString(catPartNo)); 
		  qtyPerEach = factory.selectSingleValue(qtyPerEachQuery.toString());
						  		  
	  } catch(Exception ex){};
	  return qtyPerEach;
  }
  
  
//Larry Note: This one can be resued for any one that allow quoted price empty  
 public String getAerojetXmlWithGrid(CxmlDocumentDataBean cxmlDocumentDataBean, 
		  PunchOutSetupRequestBean punchoutSetupRequestBean,
		  Collection<ShoppingCartBean> beans) throws BaseException {
//Collection shoppingCartViewBeanCollection	 
	  String response = null;
	  BigDecimal totalAmount = new BigDecimal("0");	  
	  DecimalFormat decimalFormat = new DecimalFormat("0.00");
	  StringWriter sw = new StringWriter();
	  // I will list Unknown/Hard Code data here.
	  // catalogTradingPartner = HaasTCM
	  // contractNumber	= ''
	  // supplierTradingPartnerCode = HaasTCM
	  sw.write("<?xml version = '1.0' encoding = 'UTF-8'?><response><header version='1.0'><return returnCode='S'/></header>");
	  sw.write("<body><OrderLinesDataElements><catalogTradingPartner><![CDATA[HaasTCM]]></catalogTradingPartner>");
			  
	  //to be able to put the total amount I'll have to jump to after the loop

	  //for each item
	  StringWriter itemSw = new StringWriter();
//	  Iterator iterator = shoppingCartViewBeanCollection.iterator();
//	  while(iterator.hasNext()) {
//		  ShoppingCartViewBean shoppingCartViewBean = (ShoppingCartViewBean)iterator.next();
//		  //Larry Note: just for testing.
	  for(ShoppingCartBean shoppingCartViewBean:beans) {	
		  BigDecimal totalQty = new BigDecimal("0");
		  BigDecimal unitOfSaleConvertedPrice = new BigDecimal("0");
		  BigDecimal tempUnitOfSaleQtyPerEach  = new BigDecimal("0");
		  //.getQuotedPrice()
		  
		  String strUnitOfSaleQuantityPerEach = getUnitOfSaleQuantityPerEach(shoppingCartViewBean.getCatalogCompanyId(), shoppingCartViewBean.getCatalogId(),shoppingCartViewBean.getCatPartNo());
		  if (!StringHandler.isBlankString(strUnitOfSaleQuantityPerEach)) {
			  tempUnitOfSaleQtyPerEach = new BigDecimal(strUnitOfSaleQuantityPerEach);  
			  if( GenericProcess.isBlank(shoppingCartViewBean.getCatalogPrice()))
				  unitOfSaleConvertedPrice = null;
			  else 
				  unitOfSaleConvertedPrice = shoppingCartViewBean.getCatalogPrice().divide(tempUnitOfSaleQtyPerEach,4,RoundingMode.HALF_UP);
		  }
		  
		  if( GenericProcess.isBlank(strUnitOfSaleQuantityPerEach))
			  totalQty = null;
		  if( totalQty != null ) {
			  totalQty = (shoppingCartViewBean.getQuantity().multiply(tempUnitOfSaleQtyPerEach));
		  }
		  
		  if( GenericProcess.isBlank(shoppingCartViewBean.getCatalogPrice()))
			  totalAmount = null;
		  if( totalAmount != null )
			  totalAmount = totalAmount.add((shoppingCartViewBean.getQuantity()).multiply(shoppingCartViewBean.getCatalogPrice()));
		  
//1st line contract.		  
		  itemSw.write("<orderLine><contract><buyerContract><contractNumber><![CDATA[]]></contractNumber></buyerContract></contract>");
//2nd line item.
		  //itemSw.write("<item lineType='GOODS' quantity='" + StringHandler.emptyIfNull(shoppingCartViewBean.getQuantity()) +"'>"); 
		  itemSw.write("<item lineType='GOODS' quantity='" + StringHandler.emptyIfNull(totalQty) +"'>");
//		  itemSw.write("<itemNumber><supplierItemNumber><itemID><![CDATA[" + shoppingCartViewBean.getItemId() + "]]></itemID></supplierItemNumber><buyerItemNumber><itemID><![CDATA[" + shoppingCartViewBean.getFacPartNo() + "]]></itemID></buyerItemNumber></itemNumber>");
		  
		  itemSw.write("<itemNumber>");
		  itemSw.write("<supplierItemNumber><itemID><![CDATA[" + ""                               + "]]></itemID></supplierItemNumber>");
		  String buyerItemRevision = getBuyerItemRevision(shoppingCartViewBean.getCatalogCompanyId(), shoppingCartViewBean.getCatalogId(),shoppingCartViewBean.getCatPartNo());
		  itemSw.write("<buyerItemNumber>");
		  itemSw.write("<itemID><![CDATA[" + StringHandler.emptyIfNull(shoppingCartViewBean.getCatPartNo()) + "]]></itemID>" );
		  itemSw.write("<buyerItemRevision><![CDATA[" + StringHandler.emptyIfNull(buyerItemRevision) + "]]></buyerItemRevision>");
		  itemSw.write("</buyerItemNumber>");
		  itemSw.write("</itemNumber>");

		  itemSw.write("<itemDescription><![CDATA[" + StringHandler.emptyIfNull(shoppingCartViewBean.getCatPartNo()) + " - " + 
				  StringHandler.xmlEncode(StringHandler.emptyIfNull(shoppingCartViewBean.getPartDescription())) + " [" + StringHandler.xmlEncode(StringHandler.emptyIfNull(shoppingCartViewBean.getExamplePackaging())) + 
				  ")]]]></itemDescription>");
		  //select quality_id from fac_item where facility_id = 'xxxxx' and fac_part_no = '123123'


		  String uom = getUom(shoppingCartViewBean.getCatPartNo());//"EACH";//shoppingCartViewBean.getUnitOfSale() +
		  // old code was shoppingCartViewBean.getFacilityId() instead of shoppingCartViewBean.getCatalogId
		  String categoryCode = getCategoryCode(shoppingCartViewBean.getFacilityId(),shoppingCartViewBean.getCatalogCompanyId(), 
				  shoppingCartViewBean.getCatalogId(),shoppingCartViewBean.getCatPartNo(), shoppingCartViewBean.getPartGroupNo());//"EACH";//shoppingCartViewBean.getUnitOfSale() +		  
		  itemSw.write("<unitOfMeasure><supplierUnitOfMeasure><supplierUOMType><![CDATA["+ StringHandler.emptyIfNull(uom) +"]]></supplierUOMType><supplierUOMQuantity><![CDATA[]]></supplierUOMQuantity></supplierUnitOfMeasure></unitOfMeasure>");
		  itemSw.write("</item>");
		  itemSw.write("<category><categoryCode categoryCodeIdentifier='SUPPLIER'><![CDATA["+StringHandler.xmlEncode(StringHandler.emptyIfNull(categoryCode))+"]]></categoryCode></category>");
		  //itemSw.write("<price><currency><![CDATA[USD]]></currency><unitPrice><![CDATA[" + StringHandler.emptyIfNull(shoppingCartViewBean.getCatalogPrice()) + "]]></unitPrice></price>");
		  itemSw.write("<price><currency><![CDATA[USD]]></currency><unitPrice><![CDATA[" + StringHandler.emptyIfNull(unitOfSaleConvertedPrice) + "]]></unitPrice></price>");
		  itemSw.write("<supplier><supplierTradingPartnerCode><![CDATA[HaasTCM]]></supplierTradingPartnerCode><supplierName><![CDATA[Haas Group International.]]></supplierName></supplier>");
		  itemSw.write("<additionalAttributes><attribute15><![CDATA[" + StringHandler.emptyIfNull(shoppingCartViewBean.getApplication()) + "]]></attribute15></additionalAttributes>");
		  itemSw.write("</orderLine>");   
	  }
	  sw.write(itemSw.toString());
	  sw.write("</OrderLinesDataElements></body></response>");
	  response = sw.toString();
	  return response;
 }  

}