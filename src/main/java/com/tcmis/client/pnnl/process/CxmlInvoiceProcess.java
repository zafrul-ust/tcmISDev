/*
 * CxmlInvoiceProcess.java
 *
 * Created on June 26, 2009, 4:31 PM
 */

package com.tcmis.client.pnnl.process;

import java.io.StringWriter;
import com.tcmis.common.framework.BaseProcess;
/**
 *
 * @author  mnajera
 */
public class CxmlInvoiceProcess extends BaseProcess {
   
   /** Creates a new instance of CxmlInvoiceProcess */
   public CxmlInvoiceProcess(String client) {
      super(client);
   }

   public String getCxmlDebitInvoice() {
      StringWriter sw = new StringWriter();
      sw.write("<?xml version=\"1.0\">");
      sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.009/InvoiceDetail.dtd\">");
      sw.write("<cXML payloadID=\"200602061629149210.791@eaidev1\" timestamp=\"2006-02-06T16:29:15-08:00\">");  // payloadId  / timeStamp
      sw.write("<Header>");
      sw.write("<From>");
      sw.write("<Credential domain=\"NetworkId\">");  // fromDomain
      sw.write("<Identity>xxxxx</Identity>");         // fromIdentity
      sw.write("</Credential>");
      sw.write("</From>");
      sw.write("<To>");
      sw.write("<Credential domain=\"NetworkId\">");  // toDomain
      sw.write("<Identity>xxxxx</Identity>");         // toIdentity
      sw.write("</Credential>");
      sw.write("</To>");
      sw.write("<Sender>");
      sw.write("<Credential domain=\"NetworkId\">");  // senderDomain
      sw.write("<Identity>xxxxxxx</Identity>");       // senderIdnetity
      sw.write("<SharedSecret>xxxxxx</SharedSecret>");  // shared Secret
      sw.write("<UserAgent>Ariba Network v1.1</UserAgent>");   // user agent
      sw.write("</Sender>");
      sw.write("</Header>");
      sw.write("<Request deploymentMode=\"production\">");
      sw.write("<InvoiceDetailRequest>");
      sw.write("<InvoiceDetailRequestHeader invoiceID=\"9999999\" purpose=\"creditMemo\" operation=\"new\" InvoiceDate=\"2006-01-31T16:29:15-05:00\">");  // invoice ID / invoice Date
      sw.write("<InvoiceDetailHeaderIndicator/>");
      sw.write("<InvoiceDetailLineIndicator/>");
      sw.write("<InvoicePartner>");
      sw.write("<Contact role=\"soldTo\" addressID=\"99999999\">");   // ?? address id ?
      sw.write("<Name xml:lang=\"en\">vendor</Name>");                // vendor name
      sw.write("<PostalAddress>");  
      sw.write("<Street>PO Box 9999</Street>");                       // vendor street addr
      sw.write("<City>West Chester</City>");                         // vendor city
      sw.write("<State>PA</State>");
      sw.write("<PostalCode>19380</PostalCode>");
      sw.write("<Country isoCountryCode=\"US\">United States</Country>");
      sw.write("</PostalAddress>");
      sw.write("</Contact>");
      sw.write("</InvoicePartner>");
      sw.write("<InvoicePartner>");
      sw.write("<Contact role=\"billTo\"");   
      sw.write("<Name xml:lang=\"en\">BATTELLE NORTHWEST</Name>");               
      sw.write("<PostalAddress>");       
      sw.write("<Street>ATTN:A/P</Street>");                      
      sw.write("<Street>BATTELLE FOR THE US DOE</Street>");                      
      sw.write("<City>RICHLAND</City>");                         // vendor city
      sw.write("<State>WA</State>");
      sw.write("<PostalCode>19380</PostalCode>");
      sw.write("<Country isoCountryCode=\"US\"/>");
      sw.write("</PostalAddress>");
      sw.write("</Contact>");
      sw.write("</InvoicePartner>");
      sw.write("</InvoiceDetailRequestHeader>");
      sw.write("<InvoiceDetailOrder>");
      sw.write("<InvoiceDetailOrderInfo>");
      sw.write("<OrderReference orderID=\"B2B01.0000099999\"> orderDate=\"20060427\"");   // order ID / order date
      sw.write("<DocumentReference payloadID=\"xxxxxxxx\"/>");             // payload Id 
      sw.write("</OrderReference>");
      sw.write("</InvoiceDetailOrderInfo>");
      
      // Lines
      sw.write("<InvoiceDetailItem invoiceLineNumber=\"1\" quantity=\"4\">");   // invoice Line / qty
      sw.write("<UnitOfMeasure>EA</UnitOfMeasure>");                            // uom
      sw.write("<UnitPrice>");
      sw.write("<Money currency=\"USD\">18.66</Money>");                       // currency / unit price
      sw.write("</UnitPrice>");
      sw.write("<InvoiceDetailItemReference lineNumber=\"001\">");             // cust_po_line
      sw.write("<ItemID>");
      sw.write("<SupplierPartID>ABCD</SupplierPartID>");                       // supplier part no
      sw.write("</ItemID>");
      sw.write("<Description xml:lang=\"en\">PART DESCRIPTION</Description>"); // part desc
      sw.write("</InvoiceDetailItemReference>");
      sw.write("</InvoiceDetailItem>");
      
      
      sw.write("</InvoiceDetailOrder>");
      sw.write("<InvoiceDetailSummary>");
      sw.write("<SubtotalAmount>");            
      sw.write("<Money currency=\"USD\">-74.64</Money>");                  // currency / order_sub_total
      sw.write("</SubtotalAmount>");
      sw.write("<Tax>");
      sw.write("<Money currency=\"USD\">0</Money>");                       // currency / tax_total
      sw.write("<Description xml:lang=\"en\">Total Tax</Description>");    
      sw.write("</Tax>");
      sw.write("<NetAmount>");
      sw.write("<Money currency=\"USD\">-74.64</Money>");                 // order total
      sw.write("</NetAmount>");
      sw.write("</InvoiceDetailSummary>");
      sw.write("</InvoiceDetailRequest>");
      sw.write("</Request>");
      sw.write("</cXML>");
      

      return sw.toString();
   }
   
   /*
    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE cXML SYSTEM "http://xml.cXML.org/schemas/cXML/1.2.009/InvoiceDetail.dtd">
<cXML payloadID="xxxxxx" timestamp="2006-05-01T09:10:25" version="1.2.009">
   <Header>
      <From>
         <Credential domain="DUNS">
            <Identity>xxxxxx</Identity>
         </Credential>
      </From>
      <To>
         <Credential domain="NetworkId">
            <Identity>xxxxxx</Identity>
         </Credential>
      </To>
      <Sender>
         <Credential domain="DUNS">
            <Identity>xxxxxx</Identity>
         </Credential>
         <UserAgent/>
      </Sender>
   </Header>
   <Request deploymentMode="production">
      <InvoiceDetailRequest>
         <InvoiceDetailRequestHeader invoiceID="xxxxxx" purpose="standard" operation="new" invoiceDate="20060428">
            <InvoiceDetailHeaderIndicator/>
            <InvoiceDetailLineIndicator/>
            <InvoicePartner>
               <Contact role="soldTo">
                  <Name xml:lang="en">BATTELLE NORTHWEST</Name>
                  <PostalAddress>
                     <Street>ATTN:CENTRAL RECEIVING</Street>
                     <Street>BATTELLE FOR THE US DOE</Street>
                     <City>RICHLAND</City>
                     <State>WA</State>
                     <Country isoCountryCode="US"/>
                  </PostalAddress>
               </Contact>
            </InvoicePartner>
            <InvoicePartner>
               <Contact role="billTo">
                  <Name xml:lang="en">BATTELLE NORTHWEST</Name>
                  <PostalAddress>
                     <Street>ATTN:A/P</Street>
                     <Street>BATTELLE FOR THE US DOE</Street>
                     <City>RICHLAND</City>
                     <State>WA</State>
                     <Country isoCountryCode="US"/>
                  </PostalAddress>
               </Contact>
            </InvoicePartner>
         </InvoiceDetailRequestHeader>
         <InvoiceDetailOrder>
            <InvoiceDetailOrderInfo>
               <OrderReference orderID="B2B01.00000XXXXX" orderDate="20060427">
                  <DocumentReference payloadID="xxxxxx"/>
               </OrderReference>
            </InvoiceDetailOrderInfo>
            <InvoiceDetailItem invoiceLineNumber="1" quantity="3.0">
               <UnitOfMeasure>EA</UnitOfMeasure>
               <UnitPrice>
                  <Money currency="USD">7.1</Money>
               </UnitPrice>
               <InvoiceDetailItemReference lineNumber="1">
                  <ItemID>
                     <SupplierPartID>xxxxxx</SupplierPartID>
                  </ItemID>
                  <Description xml:lang="en">HEX PLUG</Description>
               </InvoiceDetailItemReference>
            </InvoiceDetailItem>
            <InvoiceDetailItem invoiceLineNumber="2" quantity="1.0">
               <UnitOfMeasure>EA</UnitOfMeasure>
               <UnitPrice>
                  <Money currency="USD">27</Money>
               </UnitPrice>
               <InvoiceDetailItemReference lineNumber="2">
                  <ItemID>
                     <SupplierPartID>xxxxxx</SupplierPartID>
                  </ItemID>
                  <Description xml:lang="en">TOGGLE VALVE</Description>
               </InvoiceDetailItemReference>
            </InvoiceDetailItem>
         </InvoiceDetailOrder>
         <InvoiceDetailSummary>
            <SubtotalAmount>
               <Money currency="USD">48.3</Money>
            </SubtotalAmount>
            <Tax>
               <Money currency="USD">0.0</Money>
               <Description xml:lang="en">Total Tax</Description>
            </Tax>
            <NetAmount>
               <Money currency="USD">48.3</Money>
            </NetAmount>
         </InvoiceDetailSummary>
      </InvoiceDetailRequest>
   </Request>
</cXML>

    */
   
   public String getCxmlCreditInvoice() {
      StringWriter sw = new StringWriter();
      sw.write("<?xml version=\"1.0\">");
      sw.write("<!DOCTYPE cXML SYSTEM \"http://xml.cxml.org/schemas/cXML/1.2.009/InvoiceDetail.dtd\">");
      sw.write("<cXML payloadID=\"200602061629149210.791@eaidev1\" timestamp=\"2006-02-06T16:29:15-08:00\">");  // payloadId  / timeStamp
      sw.write("<Header>");
      sw.write("<From>");
      sw.write("<Credential domain=\"NetworkId\">");  // fromDomain
      sw.write("<Identity>xxxxx</Identity>");         // fromIdentity
      sw.write("</Credential>");
      sw.write("</From>");
      sw.write("<To>");
      sw.write("<Credential domain=\"NetworkId\">");  // toDomain
      sw.write("<Identity>xxxxx</Identity>");         // toIdentity
      sw.write("</Credential>");
      sw.write("</To>");
      sw.write("<Sender>");
      sw.write("<Credential domain=\"NetworkId\">");  // senderDomain
      sw.write("<Identity>xxxxxxx</Identity>");       // senderIdnetity
      sw.write("<SharedSecret>xxxxxx</SharedSecret>");  // shared Secret
      sw.write("<UserAgent>Ariba Network v1.1</UserAgent>");   // user agent
      sw.write("</Sender>");
      sw.write("</Header>");
      sw.write("<Request deploymentMode=\"production\">");
      sw.write("<InvoiceDetailRequest>");
      sw.write("<InvoiceDetailRequestHeader invoiceID=\"9999999\" purpose=\"creditMemo\" operation=\"new\" InvoiceDate=\"2006-01-31T16:29:15-05:00\">");  // invoice ID / invoice Date
      sw.write("<InvoiceDetailHeaderIndicator/>");
      sw.write("<InvoiceDetailLineIndicator/>");
      sw.write("<InvoicePartner>");
      sw.write("<Contact role=\"soldTo\" addressID=\"99999999\">");   // ?? address id ?
      sw.write("<Name xml:lang=\"en\">vendor</Name>");                // vendor name
      sw.write("<PostalAddress>");  
      sw.write("<Street>PO Box 9999</Street>");                       // vendor street addr
      sw.write("<City>West Chester</City>");                         // vendor city
      sw.write("<State>PA</State>");
      sw.write("<PostalCode>19380</PostalCode>");
      sw.write("<Country isoCountryCode=\"US\">United States</Country>");
      sw.write("</PostalAddress>");
      sw.write("</Contact>");
      sw.write("</InvoicePartner>");
      sw.write("</InvoiceDetailRequestHeader>");
      sw.write("<InvoiceDetailOrder>");
      sw.write("<InvoiceDetailOrderInfo>");
      sw.write("<OrderReference orderID=\"B2B01.0000099999\">");   // order ID
      sw.write("<DocumentReference payloadID=\"\"/>");             // payload Id NOT sent (on credits)
      sw.write("</OrderReference>");
      sw.write("</InvoiceDetailOrderInfo>");
      
      // Lines
      sw.write("<InvoiceDetailItem invoiceLineNumber=\"1\" quantity=\"4\">");   // invoice Line / qty
      sw.write("<UnitOfMeasure>EA</UnitOfMeasure>");                            // uom
      sw.write("<UnitPrice>");
      sw.write("<Money currency=\"USD\">-18.66</Money>");                       // currency / unit price
      sw.write("</UnitPrice>");
      sw.write("<InvoiceDetailItemReference lineNumber=\"001\">");             // cust_po_line
      sw.write("<ItemID>");
      sw.write("<SupplierPartID>ABCD</SupplierPartID>");                       // supplier part no
      sw.write("</ItemID>");
      sw.write("<Description xml:lang=\"en\">PART DESCRIPTION</Description>"); // part desc
      sw.write("</InvoiceDetailItemReference>");
      sw.write("</InvoiceDetailItem>");
      
      
      sw.write("</InvoiceDetailOrder>");
      sw.write("<InvoiceDetailSummary>");
      sw.write("<SubtotalAmount>");            
      sw.write("<Money currency=\"USD\">-74.64</Money>");                  // currency / order_sub_total  (make sure value is negative)
      sw.write("</SubtotalAmount>");
      sw.write("<Tax>");
      sw.write("<Money currency=\"USD\">0</Money>");                       // currency / tax_total  (will this apply or always be zero?)
      sw.write("<Description xml:lang=\"en\">Total Tax</Description>");    
      sw.write("</Tax>");
      sw.write("<SpecialHandlingAmount>");
      sw.write("<Money currency=\"USD\">0</Money>");                       // special_handling ?? (will this apply or always be zero?)
      sw.write("</SpecialHandlingAmount>");
      sw.write("<NetAmount>");
      sw.write("<Money currency=\"USD\">-74.64</Money>");                 // order total    (make sure value is negative)
      sw.write("</NetAmount>");
      sw.write("</InvoiceDetailSummary>");
      sw.write("</InvoiceDetailRequest>");
      sw.write("</Request>");
      sw.write("</cXML>");

/*
<cXML payloadID="200602061629149210.791@eaidev1" timestamp="2006-02-06T16:29:15-08:00">
<Header>
<From>
	<Credential domain="NetworkId">
		<Identity>xxxxx</Identity>
	</Credential>
</From>
<To>
	<Credential domain="NetworkId">
		<Identity>xxxxx</Identity>
	</Credential>
</To>
<Sender>
	<Credential domain="NetworkId">
		<Identity>xxxxxxx</Identity>
		<SharedSecret>xxxxxx</SharedSecret>
	</Credential>
	<UserAgent>Ariba Network v1.1</UserAgent>
</Sender>
</Header>
<Request deploymentMode="production">
	<InvoiceDetailRequest>
		<InvoiceDetailRequestHeader invoiceID="9999999" purpose="creditMemo" operation="new" InvoiceDate="2006-01-31T16:29:15-05:00">
			<InvoiceDetailHeaderIndicator/>
			<InvoiceDetailLineIndicator/>
			<InvoicePartner>
				<Contact role="soldTo" addressID="99999999">
					<Name xml:lang="en">vendor</Name>
 	    			        <PostalAddress>
						<Street>PO Box 9999</Street>
						<City>West Chester</City>
						<State>PA</State>
						<PostalCode>19380</PostalCode>
						<Country isoCountryCode="US">United States</Country>
					</PostalAddress>
				</Contact>
			</InvoicePartner>
		</InvoiceDetailRequestHeader>
		<InvoiceDetailOrder>
			<InvoiceDetailOrderInfo>
				<OrderReference orderID="B2B01.0000099999">
					<DocumentReference payloadID=""/>
				</OrderReference>
			</InvoiceDetailOrderInfo>
			<InvoiceDetailItem invoiceLineNumber="1" quantity="4">
				<UnitOfMeasure>EA</UnitOfMeasure>
				<UnitPrice>
					<Money currency="USD">-18.66</Money>
				</UnitPrice>
				<InvoiceDetailItemReference lineNumber="001">
					<ItemID>
						<SupplierPartID>ABCD</SupplierPartID>
					</ItemID>
					<Description xml:lang="en">PART DESCRIPTION</Description>
				</InvoiceDetailItemReference>
			</InvoiceDetailItem>
		</InvoiceDetailOrder>
		<InvoiceDetailSummary>
			<SubtotalAmount>
				<Money currency="USD">-74.64</Money>
			</SubtotalAmount>
			<Tax>
				<Money currency="USD">0</Money>
				<Description xml:lang="en">Total Tax</Description>
			</Tax>
			<SpecialHandlingAmount>
				<Money currency="USD">0</Money>
			</SpecialHandlingAmount>
			<NetAmount>
				<Money currency="USD">-74.64</Money>
			</NetAmount>
		</InvoiceDetailSummary>
	</InvoiceDetailRequest>
</Request>
</cXML>
 
 */      
      return sw.toString();
   }
   
}
