package com.tcmis.internal.invoice.action;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import com.tcmis.common.util.NetHandler;
import com.tcmis.internal.invoice.process.SlacInvoiceProcess;

public class SlacInvoiceAction {

	public static String readFileAsString(String filePath)
	throws java.io.IOException{
		StringBuffer fileData = new StringBuffer(1024);
		BufferedReader reader = new BufferedReader(
				new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead=reader.read(buf)) != -1){
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

// run from local.
	public static void main(String[] args){

		try {

			SlacInvoiceProcess process = new SlacInvoiceProcess("TCM_OPS");
			//		    select * from INVOICE_FORMAT_VIEW_SLAC where invoice_period = '481'
			String invoice_period = "563";
//			String invoiceN = "1302096";
			String invoices[] = {"1560676","1563160"};
			for(String invoiceN: invoices) {
			Map<String, String> postString = process.getInvoiceXmlWInvoice(invoiceN);//getInvoiceXml(invoice_period);
			// testing url 
//			String url = "https://psoft-fuatib01.slac.stanford.edu/PSIGW/HttpListeningConnector?From=SL_HAAS_INVOICE&To=PSFT_FUAT&Operation=SL_HAAS_INVOICE.VERSION_1";
		    String url = "https://psoft-fsysib.slac.stanford.edu/PSIGW/HttpListeningConnector?From=SL_HAAS_INVOICE&To=PSFT_FSYS&Operation=SL_HAAS_INVOICE.VERSION_1"; // change this url.
		    String res = null;
			for(String s:postString.keySet()) {
						res = postString.get(s);//System.out.println(postString);
					    res = NetHandler.getHttpPostResponse(url,
					    	      null,//String userName,
					    	      null,//String password,
					    	      res );
					    break;
			}
			System.out.println(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
=====================
	<?xml version="1.0"?>
<HAASTCM timestamp="2009-12-12T23:59:59" xml:lang="en-US">
  <Invoice>
      <InvoiceSummary invoiceDate="2009-12-12T23:59:59" invoiceID="1234567">
            <Invoicedetail>
		      <Invoiceline>1</Invoiceline>
		      <WorkArea>XL0100</WorkArea>
		      <ChargeType>d</ChargeType>
		      <CommodityType>Gas</CommodityType>
		      <InvoiceType>MV</InvoiceType>
		      <Account>53105</Account>
		      <ChargeNo>9474332</ChargeNo>
		      <Requestor>Todd Slater</Requestor>
		      <Approver>Natalie Cramer</Approver>
		      <MRNumber>996401</MRNumber>
		      <MRLine>1</MRLine>
		      <Descr>100 Liter Liquid Nitrogen</Descr>
		      <Packaging>Cylinder</Packaging>
		      <ReceiptID>2953816</ReceiptID>
		      <DeliveryDate>01/01/2009</DeliveryDate>
		      <Quantity>1</Quantity>
		      <CatalogPrice currency="USD">10.20</CatalogPrice>
		      <InvoicePrice currency="USD">10.20</InvoicePrice>
		      <AdditionalCharges>5</AdditionalCharges>
		      <NetAmount>15.20</NetAmount>
		      <PriceFlag>N</PriceFlag>
		      <ContractPO>0000054076</ContractPO>
		      <PriceDifference>1.14</PriceDifference>
            </Invoicedetail>
            <Invoicedetail>
	    </Invoicedetail>
	    <Invoicedetail>
	    </Invoicedetail>
	    <Invoicedetail>
	    </Invoicedetail>
	 </InvoiceSummary>
	 <InvoiceSummary invoiceDate="2009-12-12T23:59:59" invoiceID="1234568">
	    <Invoicedetail>
	    	      <Invoiceline>2</Invoiceline>
	    	      <WorkArea>XL0100</WorkArea>
	    	      <ChargeType>d</ChargeType>
	    	      <CommodityType>Chemical</CommodityType>
	    	      <InvoiceType>MA</InvoiceType>
	    	      <Account>53106</Account>
	    	      <ChargeNo>9474334</ChargeNo>
	    	      <Requestor>Todd Slater</Requestor>
	    	      <Approver>Natalie Cramer</Approver>
	    	      <MRNumber>996402</MRNumber>
	    	      <MRLine>1</MRLine>
	    	      <Descr>100 Liter Liquid Nitrogen</Descr>
	    	      <Packaging>Cylinder</Packaging>
	    	      <ReceiptID>2953817</ReceiptID>
	    	      <DeliveryDate>01/01/2009</DeliveryDate>
	    	      <Quantity>1</Quantity>
	    	      <CatalogPrice currency="USD">15.20</CatalogPrice>
	    	      <InvoicePrice currency="USD">15.20</InvoicePrice>
	    	      <AdditionalCharges>5</AdditionalCharges>
	    	      <NetAmount>20.20</NetAmount>
	    	      <PriceFlag>N</PriceFlag>
	    	      <ContractPO>0000054076</ContractPO>
	    	      <PriceDifference>5.14</PriceDifference>
            </Invoicedetail>
            <Invoicedetail>
	    </Invoicedetail>
      </InvoiceSummary>
  </Invoice>
</HAASTCM>

 */