package com.tcmis.client.raytheon.process;

import java.io.StringWriter;
import java.util.*;
import java.util.Iterator;

import com.tcmis.client.raytheon.beans.XcblOrderBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.client.raytheon.util.XcblHandler;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process to translate xcbl order documents into bean objects.
 * @version 1.0
 *****************************************************************************/
public class XcblOrderResponseParser
    extends BaseProcess {

  public XcblOrderResponseParser(String client) {
    super(client);
  }

  public String getOrderResponse(XcblOrderBean xcblOrderDataBean) throws BaseException {
    String orderResponse = null;
    StringWriter sw = new StringWriter();
    sw.write(
        "<?soxtype urn:x-commerceone:document:com:commerceone:XCBL35:XCBL35.sox$1.0?>");
    sw.write(
        "<?import urn:x-commerceone:document:com:commerceone:XCBL35:XCBL35.sox$1.0?>");
    sw.write("<OrderResponse>");
    sw.write("<OrderResponseHeader>");
    sw.write("<OrderResponseNumber>");
    sw.write("<BuyerOrderResponseNumber>");
    sw.write("</BuyerOrderResponseNumber>");
    sw.write("</OrderResponseNumber>");
    sw.write("<OrderResponseIssueDate>");
    sw.write(XcblHandler.getXcblDateString(new Date()));
    sw.write("</OrderResponseIssueDate>");
    sw.write("<OrderResponseDocTypeCoded>");
    sw.write("OrderResponse");
    sw.write("</OrderResponseDocTypeCoded>");
    sw.write("<OrderReference>");
    sw.write("<Reference>");
    sw.write("<RefNum>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getBuyerOrderNumber()));
    sw.write("</RefNum>");
    sw.write("<RefDate>");
    sw.write(XcblHandler.getXcblDateString(xcblOrderDataBean.getOrderIssueDate()));
    sw.write("</RefDate>");
    sw.write("</Reference>");
    sw.write("</OrderReference>");
/*
    //if change order
    if (XcblProcess.OTHER.equalsIgnoreCase(xcblOrderDataBean.getResponseOrderStatus())) {
      sw.write("<ChangeOrderReference>");
      sw.write("<Reference>");
      sw.write("<RefNum>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getBuyerOrderNumber()));
      sw.write("</RefNum>");
      sw.write("<RefDate>");
      sw.write(XcblHandler.getXcblDateString(xcblOrderDataBean.getChangeOrderDate()));
      sw.write("</RefDate>");
      sw.write("</Reference>");
      sw.write("</ChangeOrderReference>");
    }
*/
    sw.write("<SellerParty>");
    sw.write("<Party>");
    sw.write("<PartyID>");
    sw.write("<Identifier>");
    sw.write("<Agency>");
    sw.write("<AgencyCoded>");
    sw.write("Other");
    sw.write("</AgencyCoded>");
    sw.write("<AgencyCodedOther>");
    sw.write("N/A");
    sw.write("</AgencyCodedOther>");
    sw.write("</Agency>");
    sw.write("<Ident>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getSellerIdent()));
    sw.write("</Ident>");
    sw.write("</Identifier>");
    sw.write("</PartyID>");
    sw.write("<NameAddress>");
    sw.write("<Name1>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getSellerName1()));
    sw.write("</Name1>");
    sw.write("</NameAddress>");
    sw.write("<OtherContacts>");
    sw.write("<ListOfContact>");
    sw.write("<Contact>");
    sw.write("<ContactName>");
    sw.write("Haas TCM");
    sw.write("</ContactName>");
    sw.write("<ContactFunction>");
    sw.write("<ContactFunctionCoded>");
    sw.write("Respondant");
    sw.write("</ContactFunctionCoded>");
    sw.write("</ContactFunction>");
    sw.write("</Contact>");
    sw.write("</ListOfContact>");
    sw.write("</OtherContacts>");
    sw.write("</Party>");
    sw.write("</SellerParty>");
    sw.write("<BuyerParty>");
    sw.write("<Party>");
    sw.write("<PartyID>");
    sw.write("<Identifier>");
    sw.write("<Agency>");
    sw.write("<AgencyCoded>");
    sw.write("Other");
    sw.write("</AgencyCoded>");
    sw.write("<AgencyCodedOther>");
    sw.write("N/A");
    sw.write("</AgencyCodedOther>");
    sw.write("</Agency>");
    sw.write("<Ident>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyIdent()));
    sw.write("</Ident>");
    sw.write("</Identifier>");
    sw.write("</PartyID>");
    sw.write("<NameAddress>");
    sw.write("<Name1>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyName1()));
    sw.write("</Name1>");
    sw.write("</NameAddress>");
    sw.write("<OrderContact>");
    sw.write("<Contact>");
    sw.write("<ContactName>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyContactName()));
    sw.write("</ContactName>");
    sw.write("<ListOfContactNumber>");
    sw.write("<ContactNumber>");
    sw.write("<ContactNumberValue>");
    sw.write("</ContactNumberValue>");
    sw.write("<ContactNumberTypeCoded>");
    sw.write("EmailAddress");
    sw.write("</ContactNumberTypeCoded>");
    sw.write("</ContactNumber>");
    sw.write("</ListOfContactNumber>");
    sw.write("</Contact>");
    sw.write("</OrderContact>");
    sw.write("</Party>");
    sw.write("</BuyerParty>");
    sw.write("<Purpose>");
    sw.write("<PurposeCoded>");
    sw.write("Other");
    sw.write("</PurposeCoded>");
    sw.write("<PurposeCodedOther>");
    sw.write("N/A");
    sw.write("</PurposeCodedOther>");
    sw.write("</Purpose>");
    sw.write("<ResponseType>");
    sw.write("<ResponseTypeCoded>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getResponseType()));
    sw.write("</ResponseTypeCoded>");
    sw.write("</ResponseType>");
    sw.write("<OrderStatus>");
    sw.write("<Status>");
    sw.write("<StatusEvent>");
    sw.write("<StatusEventCoded>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getStatusEvent()));
    sw.write("</StatusEventCoded>");
    sw.write("<StatusEventCodedOther>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getStatusEventOther()));
    sw.write("</StatusEventCodedOther>");
    sw.write("</StatusEvent>");
    sw.write("<StatusNote>");
    sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getResponseOrderStatusNotes()));
    sw.write("</StatusNote>");
    sw.write("</Status>");
    sw.write("</OrderStatus>");
/*
    //if change order
    if (XcblProcess.OTHER.equalsIgnoreCase(xcblOrderDataBean.getResponseOrderStatus())) {
      sw.write("<ChangeOrderHeader>");
      sw.write("<ChangeOrderNumber>");
      sw.write("<BuyerChangeOrderNumber>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getBuyerOrderNumber()));
      sw.write("</BuyerChangeOrderNumber>");
      sw.write("</ChangeOrderNumber>");
      sw.write("<ChangeOrderSequence>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getChangeOrderSequence()));
      sw.write("</ChangeOrderSequence>");
      sw.write("<ChangeOrderIssueDate>");
      sw.write(XcblHandler.getXcblDateString(xcblOrderDataBean.getChangeOrderDate()));
      sw.write("</ChangeOrderIssueDate>");
      sw.write("<OrderReference>");
      sw.write("<Reference>");
      sw.write("<RefNum>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getBuyerOrderNumber()));
      sw.write("</RefNum>");
      sw.write("<RefDate>");
      sw.write(XcblHandler.getXcblDateString(xcblOrderDataBean.getOrderIssueDate()));
      sw.write("</RefDate>");
      sw.write("</Reference>");
      sw.write("</OrderReference>");
      sw.write("<SellerParty>");
      sw.write("<Party>");
      sw.write("<PartyID>");
      sw.write("<Identifier>");
      sw.write("<Agency>");
      sw.write("<AgencyCoded>");
      sw.write("Other");
      sw.write("</AgencyCoded>");
      sw.write("<AgencyCodedOther>");
      sw.write("N/A");
      sw.write("</AgencyCodedOther>");
      sw.write("</Agency>");
      sw.write("<Ident>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getSellerIdent()));
      sw.write("</Ident>");
      sw.write("</Identifier>");
      sw.write("</PartyID>");
      sw.write("<NameAddress>");
      sw.write("<Name1>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getSellerName1()));
      sw.write("</Name1>");
      sw.write("</NameAddress>");
      sw.write("<OtherContacts>");
      sw.write("<ListOfContact>");
      sw.write("<Contact>");
      sw.write("<ContactName>");
      sw.write("Haas TCM");
      sw.write("</ContactName>");
      sw.write("<ContactFunction>");
      sw.write("<ContactFunctionCoded>");
      sw.write("Respondant");
      sw.write("</ContactFunctionCoded>");
      sw.write("</ContactFunction>");
      sw.write("</Contact>");
      sw.write("</ListOfContact>");
      sw.write("</OtherContacts>");
      sw.write("</Party>");
      sw.write("</SellerParty>");
      sw.write("<BuyerParty>");
      sw.write("<Party>");
      sw.write("<PartyID>");
      sw.write("<Identifier>");
      sw.write("<Agency>");
      sw.write("<AgencyCoded>");
      sw.write("Other");
      sw.write("</AgencyCoded>");
      sw.write("<AgencyCodedOther>");
      sw.write("N/A");
      sw.write("</AgencyCodedOther>");
      sw.write("</Agency>");
      sw.write("<Ident>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyIdent()));
      sw.write("</Ident>");
      sw.write("</Identifier>");
      sw.write("</PartyID>");
      sw.write("<NameAddress>");
      sw.write("<Name1>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyName1()));
      sw.write("</Name1>");
      sw.write("</NameAddress>");
      sw.write("<OrderContact>");
      sw.write("<Contact>");
      sw.write("<ContactName>");
      sw.write(StringHandler.emptyIfNull(xcblOrderDataBean.getOrderPartyContactName()));
      sw.write("</ContactName>");
      sw.write("<ListOfContactNumber>");
      sw.write("<ContactNumber>");
      sw.write("<ContactNumberValue>");
      sw.write("</ContactNumberValue>");
      sw.write("<ContactNumberTypeCoded>");
      sw.write("EmailAddress");
      sw.write("</ContactNumberTypeCoded>");
      sw.write("</ContactNumber>");
      sw.write("</ListOfContactNumber>");
      sw.write("</Contact>");
      sw.write("</OrderContact>");
      sw.write("</Party>");
      sw.write("</BuyerParty>");
      sw.write("<Purpose>");
      sw.write("<PurposeCoded>");
      sw.write("Other");
      sw.write("</PurposeCoded>");
      sw.write("<PurposeCodedOther>");
      sw.write("N/A");
      sw.write("</PurposeCodedOther>");
      sw.write("</Purpose>");
      sw.write("<ChangeType>");
      sw.write("<ChangeTypeCoded>Other</ChangeTypeCoded>");
      sw.write("<ChangeTypeCodedOther>N/A</ChangeTypeCodedOther>");
      sw.write("</ChangeType>");
      sw.write("</ChangeOrderHeader>");
    } //end change order
*/
    sw.write("</OrderResponseHeader>");
    //we don't need to put the detail on a "PROCESSING" response
    if(!XcblProcess.PROCESSING.equalsIgnoreCase(xcblOrderDataBean.getStatusEvent())) {
      sw.write("<OrderResponseDetail>");
      sw.write("<ListOfOrderResponseItemDetail>");
//loop thru item detail
      Iterator iterator = xcblOrderDataBean.getXcblOrderDetailBeanColl().iterator();
      while (iterator.hasNext()) {
        XcblOrderDetailBean detailBean = (XcblOrderDetailBean) iterator.next();
        sw.write("<OrderResponseItemDetail>");
        sw.write("<ItemDetailResponseCoded>");
        sw.write(StringHandler.emptyIfNull(detailBean.getResponseOrderStatus()));
        sw.write("</ItemDetailResponseCoded>");
        sw.write("<ItemStatusEvent>");
        sw.write("<Status>");
        sw.write("<StatusEvent>");
        sw.write("<StatusEventCoded>");
        sw.write(StringHandler.emptyIfNull(detailBean.getStatusEvent()));
        sw.write("</StatusEventCoded>");
        sw.write("</StatusEvent>");
        sw.write("<StatusNote>");
        sw.write(StringHandler.xmlEncode(StringHandler.emptyIfNull(detailBean.getResponseOrderStatusNotes())));
        sw.write("</StatusNote>");
        sw.write("</Status>");
        sw.write("</ItemStatusEvent>");
//original detail first
        sw.write("<OriginalItemDetail>");
        sw.write("<ItemDetail>");
        sw.write("<BaseItemDetail>");
        sw.write("<LineItemNum>");
        sw.write("<BuyerLineItemNum>");
        sw.write(StringHandler.emptyIfNull(detailBean.getBuyerLineItemNum()));
        sw.write("</BuyerLineItemNum>");
        sw.write("</LineItemNum>");
        sw.write("<ItemIdentifiers>");
        sw.write("<PartNumbers>");
        sw.write("<SellerPartNumber>");
        sw.write("<PartNum>");
        sw.write("<PartID>");
        sw.write(StringHandler.emptyIfNull(detailBean.getSellerPartId()));
        sw.write("</PartID>");
        sw.write("</PartNum>");
        sw.write("</SellerPartNumber>");
        //sw.write("<BuyerPartNumber>");
        //sw.write("<PartNum>");
        //sw.write("<PartID>");
        //sw.write(StringHandler.emptyIfNull(detailBean.getBuyerPartId()));
        //sw.write("</PartID>");
        //sw.write("</PartNum>");
        //sw.write("</BuyerPartNumber>");
        sw.write("</PartNumbers>");
        sw.write("<CommodityCode>");
        sw.write("<Identifier>");
        sw.write("<Agency>");
        sw.write("<AgencyCoded>");
        sw.write("Other");
        sw.write("</AgencyCoded>");
        sw.write("<AgencyCodedOther>");
        sw.write("N/A");
        sw.write("</AgencyCodedOther>");
        sw.write("</Agency>");
        sw.write("<Ident>");
        sw.write("</Ident>");
        sw.write("</Identifier>");
        sw.write("</CommodityCode>");
        sw.write("</ItemIdentifiers>");
        sw.write("<TotalQuantity>");
        sw.write("<Quantity>");
        sw.write("<QuantityValue>");
        sw.write(NumberHandler.emptyIfNull(detailBean.getQuantity()));
        sw.write("</QuantityValue>");
        sw.write("<UnitOfMeasurement>");
        sw.write("<UOMCoded>");
        sw.write(StringHandler.emptyIfNull(detailBean.getUom()));
        sw.write("</UOMCoded>");
        sw.write("</UnitOfMeasurement>");
        sw.write("</Quantity>");
        sw.write("</TotalQuantity>");
        sw.write("</BaseItemDetail>");
        sw.write("<PricingDetail>");
        sw.write("<ListOfPrice>");
        sw.write("<Price>");
        sw.write("<UnitPrice>");
        sw.write("<UnitPriceValue>");
        sw.write(StringHandler.emptyIfNull(detailBean.getUnitPrice()));
        sw.write("</UnitPriceValue>");
        sw.write("</UnitPrice>");
        sw.write("</Price>");
        sw.write("</ListOfPrice>");
        sw.write("</PricingDetail>");
        sw.write("<DeliveryDetail>");
//loop thru schedules
        sw.write("<ListOfScheduleLine>");
        Iterator iterator2 = null;
        if (detailBean.getXcblOrderDetailOriginalScheduleBeanColl() == null ||
            detailBean.getXcblOrderDetailOriginalScheduleBeanColl().size() < 1) {
          iterator2 = detailBean.getXcblOrderDetailScheduleBeanColl().iterator();
        }
        else {
          iterator2 = detailBean.getXcblOrderDetailOriginalScheduleBeanColl().iterator();
        }
        while (iterator2.hasNext()) {
          XcblOrderDetailScheduleBean scheduleBean = (XcblOrderDetailScheduleBean)
              iterator2.next();
          sw.write("<ScheduleLine>");
          sw.write("<Quantity>");
          sw.write("<QuantityValue>");
          sw.write(StringHandler.emptyIfNull(scheduleBean.getQuantity()));
          sw.write("</QuantityValue>");
          sw.write("<UnitOfMeasurement>");
          sw.write("<UOMCoded>");
          sw.write(StringHandler.emptyIfNull(scheduleBean.getUom()));
          sw.write("</UOMCoded>");
          sw.write("</UnitOfMeasurement>");
          sw.write("</Quantity>");
          sw.write("<RequestedDeliveryDate>");
          sw.write(XcblHandler.getXcblDateString(scheduleBean.getRequestedDeliveryDate()));
          sw.write("</RequestedDeliveryDate>");
          sw.write("</ScheduleLine>");
        }
        sw.write("</ListOfScheduleLine>");
        sw.write("</DeliveryDetail>");
        sw.write("</ItemDetail>");
        sw.write("</OriginalItemDetail>");
//now the changes
//we'll do the changes section even if there are no changes
        //if (XcblProcess.OTHER.equalsIgnoreCase(xcblOrderDataBean.getResponseOrderStatus())) {
        sw.write("<ItemDetailChanges>");
        sw.write("<ItemDetail>");
        sw.write("<BaseItemDetail>");
        sw.write("<LineItemNum>");
        sw.write("<BuyerLineItemNum>");
        sw.write(NumberHandler.emptyIfNull(detailBean.getBuyerLineItemNum()));
        sw.write("</BuyerLineItemNum>");
        sw.write("</LineItemNum>");
        sw.write("<ItemIdentifiers>");
        sw.write("<CommodityCode>");
        sw.write("<Identifier>");
        sw.write("<Agency>");
        sw.write("<AgencyCoded>");
        sw.write("Other");
        sw.write("</AgencyCoded>");
        sw.write("<AgencyCodedOther>");
        sw.write("N/A");
        sw.write("</AgencyCodedOther>");
        sw.write("</Agency>");
        sw.write("<Ident>");
        sw.write(NumberHandler.emptyIfNull(detailBean.getBuyerLineItemNum()));
        sw.write("</Ident>");
        sw.write("</Identifier>");
        sw.write("</CommodityCode>");
        sw.write("</ItemIdentifiers>");
        sw.write("<TotalQuantity>");
        sw.write("<Quantity>");
        sw.write("<QuantityValue>");
        sw.write(NumberHandler.emptyIfNull(detailBean.getResponseQuantity()));
        sw.write("</QuantityValue>");
        sw.write("<UnitOfMeasurement>");
        sw.write("<UOMCoded>");
        sw.write(StringHandler.emptyIfNull(detailBean.getUom()));
        sw.write("</UOMCoded>");
        sw.write("</UnitOfMeasurement>");
        sw.write("</Quantity>");
        sw.write("</TotalQuantity>");
        sw.write("</BaseItemDetail>");
        sw.write("<PricingDetail>");
        sw.write("<ListOfPrice>");
        sw.write("<Price>");
        sw.write("<UnitPrice>");
        sw.write("<UnitPriceValue>");
        sw.write(NumberHandler.emptyIfNull(detailBean.getResponseUnitPrice()));
        sw.write("</UnitPriceValue>");
        sw.write("</UnitPrice>");
        sw.write("</Price>");
        sw.write("</ListOfPrice>");
        sw.write("</PricingDetail>");
        sw.write("<DeliveryDetail>");
        sw.write("<ListOfScheduleLine>");
        Iterator iterator3 = detailBean.getXcblOrderDetailScheduleBeanColl().iterator();
        //need the original schedule
        Vector originalScheduleVector = new Vector(detailBean.
            getXcblOrderDetailOriginalScheduleBeanColl());
        int newScheduleCount = 0;
        while (iterator3.hasNext()) {
          XcblOrderDetailScheduleBean scheduleBean = (XcblOrderDetailScheduleBean)
              iterator3.next();
          sw.write("<ScheduleLine>");
          sw.write("<Quantity>");
          sw.write("<QuantityValue>");
          sw.write(NumberHandler.emptyIfNull(scheduleBean.getResponseQuantity()));
          sw.write("</QuantityValue>");
          sw.write("<UnitOfMeasurement>");
          sw.write("<UOMCoded>");
          sw.write(StringHandler.emptyIfNull(scheduleBean.getUom()));
          sw.write("</UOMCoded>");
          sw.write("</UnitOfMeasurement>");
          sw.write("</Quantity>");
          //only include RequestedDeliveryDate if the line was originally on the PO,
          //hopefully doing it by number of lines will work
          if (newScheduleCount < originalScheduleVector.size()) {
            XcblOrderDetailScheduleBean originalScheduleBean = (
                XcblOrderDetailScheduleBean) originalScheduleVector.get(newScheduleCount);
            sw.write("<RequestedDeliveryDate>");
            sw.write(XcblHandler.getXcblDateString(originalScheduleBean.
                getRequestedDeliveryDate()));
            sw.write("</RequestedDeliveryDate>");
          }
          sw.write("<ListOfOtherDeliveryDate>");
          sw.write("<ListOfDateCoded>");
          sw.write("<DateCoded>");
          sw.write("<Date>");
          sw.write(XcblHandler.getXcblDateString(scheduleBean.getResponseDeliveryDate()));
          sw.write("</Date>");
          sw.write("<DateQualifier>");
          sw.write("<DateQualifierCoded>");
          sw.write("PromisedForDelivery");
          sw.write("</DateQualifierCoded>");
          sw.write("</DateQualifier>");
          sw.write("</DateCoded>");
          sw.write("</ListOfDateCoded>");
          sw.write("</ListOfOtherDeliveryDate>");
          sw.write("<ScheduleLineNote>");
          sw.write(StringHandler.xmlEncode(StringHandler.emptyIfNull(scheduleBean.getResponseNotes())));
          sw.write("</ScheduleLineNote>");
          sw.write("</ScheduleLine>");
          newScheduleCount++;
        }
        sw.write("</ListOfScheduleLine>");
        sw.write("</DeliveryDetail>");
        sw.write("</ItemDetail>");
        sw.write("</ItemDetailChanges>");
        //}
        sw.write("</OrderResponseItemDetail>");
      }
      sw.write("</ListOfOrderResponseItemDetail>");
      sw.write("</OrderResponseDetail>");
    }
    sw.write("</OrderResponse>");
    orderResponse = sw.toString();
    return orderResponse;
  }
}