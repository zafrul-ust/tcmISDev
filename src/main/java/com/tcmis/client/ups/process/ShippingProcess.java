package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Dec 10, 2007
 * Time: 2:44:39 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Encoder;

import com.tcmis.client.ups.beans.BoxShipmentSetupBean;
import com.tcmis.client.ups.beans.PackageResultsBean;
import com.tcmis.client.ups.beans.ShipmentAcceptResponseBean;
import com.tcmis.client.ups.beans.ShipmentConfirmResponseBean;
import com.tcmis.client.ups.beans.UpsShippingInfoBean;
import com.tcmis.client.ups.factory.BoxShipmentSetupBeanFactory;
import com.tcmis.client.ups.factory.UpsShippingInfoBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;
import com.tcmis.supplier.shipping.factory.MslBoxViewBeanFactory;

/**
 * ***************************************************************************
 * Process for processing ups shipments.
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class ShippingProcess
extends BaseProcess{
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;
	private DbManager dbManager = null;
	private String[] boxIdArray;
	private String fileFormat = "EPL";

	public ShippingProcess(String client) throws Exception{
		super(client);
		library = new ResourceLibrary("ups");
		dbManager = new DbManager(client);
	}


	public void getUpsLabel(LabelInputBean inputcBean) throws
	BaseException, Exception{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		inputcBean.setPaperSize("UPS");
		//inputcBean.setPrinterPath(inputBean.getPrinterName());
		inputcBean.setSkipKitLabels("");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,inputcBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputcBean.setPrinterName(printerPath);

		String confirmResponse = sendShipmentConfirmRequest(new BigDecimal(inputcBean.getPackingGroupId()));
		File confirmResponseFile = FileHandler.saveTempFile(confirmResponse, "confirm_response", ".xml", library.getString("backupfile.dir"));
		ShipmentConfirmResponseParser shipmentConfirmResponseParser = new ShipmentConfirmResponseParser(getClient());
		ShipmentConfirmResponseBean shipmentConfirmResponseBean = new ShipmentConfirmResponseBean();
		shipmentConfirmResponseBean = shipmentConfirmResponseParser.parse(confirmResponseFile);
		if(shipmentConfirmResponseBean.getShipmentDigest() == null) {
			throw new BaseException("Error getting label:" + shipmentConfirmResponseBean.getResponseStatusDescription() + " - " + shipmentConfirmResponseBean.getErrorDescription());
		}

		if(log.isDebugEnabled()) {
			log.debug("sending Ship Accept: packingGroupId "+inputcBean.getPackingGroupId()+"");
		}

		String acceptResponse = sendShipmentAcceptRequest(shipmentConfirmResponseBean);

		if(log.isDebugEnabled()) {
			//log.debug("Ship Accept Resoponse: packingGroupId "+inputcBean.getPackingGroupId()+"\n" + acceptResponse);
		}

		File acceptResponseFile = FileHandler.saveTempFile(acceptResponse, "accept_response", ".xml", library.getString("backupfile.dir"));
		ShipmentAcceptResponseParser shipmentAcceptResponseParser = new ShipmentAcceptResponseParser(getClient());
		ShipmentAcceptResponseBean shipmentAcceptResponseBean = shipmentAcceptResponseParser.parse(acceptResponseFile);

		int i = 0;
		for(PackageResultsBean packageResultsBean : shipmentAcceptResponseBean.getPackageResultsBeanCollection()) {
			File image = File.createTempFile("label","." + fileFormat,new File(library.getString("backupfile.dir")));
			FileOutputStream out = new FileOutputStream(image, true);
			out.write(new sun.misc.BASE64Decoder().decodeBuffer(packageResultsBean.getGraphicImage()));
			out.close();
			//PrintHandler.print("UPS Thermal 2844", image.getAbsolutePath());
			packageResultsBean.setLabelFilePath(image.getAbsolutePath());
			packageResultsBean.setBoxId(boxIdArray[i]);
			packageResultsBean.setFile(image);
			String s = this.saveShippingInfo(shipmentAcceptResponseBean, packageResultsBean);
			//print label
			if("OK".equalsIgnoreCase(s)) {
				try {
					PrintHandler.print(printerPath, image.getAbsolutePath());
				}
				catch(Exception e) { //in case someone turned off the printer....
					log.fatal("Error printing UPS label:" + e.getMessage(),e);
				}
			}
			i++;
		}

	}

	public void voidShipment(String shipmentId, Collection<String> trackingNumberCollection) throws
	BaseException, Exception{
		if(log.isDebugEnabled()) {
			log.debug("Canceling UPS tracking number:" + getVoidShipmentRequest(shipmentId, null));
		}

		FileHandler.saveTempFile(getVoidShipmentRequest(shipmentId, null), "void_request", ".xml", library.getString("backupfile.dir"));
		//String response = NetHandler.getHttpPostResponse(library.getString("url.void"), library.getString("username"), library.getString("password"), getVoidShipmentRequest(shipmentId, null));
		String response = this.getHttpPostResponse(library.getString("url.void"), library.getString("username"), library.getString("password"), getVoidShipmentRequest(shipmentId, null));

		FileHandler.saveTempFile(response, "void_response", ".xml", library.getString("backupfile.dir"));
	}

	private String saveShippingInfo(ShipmentAcceptResponseBean shipmentAcceptResponseBean, PackageResultsBean packageResultsBean) throws
	BaseException, Exception{
		String result = "";
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Collection<String> resultCollection = null;
		Collection inArgs = new Vector(7);
		inArgs.add(packageResultsBean.getBoxId());
		inArgs.add("");//pallet id
		inArgs.add(shipmentAcceptResponseBean.getShipmentIdentificationNumber());
		inArgs.add("UPS");
		inArgs.add("");//delivery ticket
		inArgs.add("");//date delivered
		inArgs.add("");//date shipped
		if(log.isDebugEnabled()) {
			//log.debug("inargs:" + inArgs);
		}
		Collection inArgs2 = new Vector(7);
		inArgs2.add(shipmentAcceptResponseBean.getTransportationCharges());
		inArgs2.add(shipmentAcceptResponseBean.getServiceOptionsCharges());
		inArgs2.add(shipmentAcceptResponseBean.getTotalCharges());
		inArgs2.add(shipmentAcceptResponseBean.getBillingWeight());
		inArgs2.add(packageResultsBean.getLabelFilePath());
		inArgs2.add(packageResultsBean.getServiceOptionsCharges());
		inArgs2.add(packageResultsBean.getTrackingNumber());
		if(log.isDebugEnabled()) {
			//log.debug("inargs2:" + inArgs2);
		}
		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		resultCollection = procFactory.doProcedure("P_BOX_UPDATE", inArgs, outArgs, inArgs2);
		for(String s:resultCollection) {
			result = s;
			if(!"OK".equalsIgnoreCase(s)) {
				log.error("Error calling P_BOX_UPDATE:"+inArgs+" "+inArgs2);
				log.error("Procedure message:" + s);
				//System.out.println("result:" + s);
			}
		}
		return result;

		/*
    BoxShipmentSetupBeanFactory factory = new BoxShipmentSetupBeanFactory(dbManager);
    BoxShipmentSetupBean boxShipmentSetupBean = new BoxShipmentSetupBean();
    boxShipmentSetupBean.setCarrierName("UPS");
    boxShipmentSetupBean.setCarrierShipTrackingNum(shipmentAcceptResponseBean.getShipmentIdentificationNumber());
    boxShipmentSetupBean.setCarrierServiceCharges(shipmentAcceptResponseBean.getServiceOptionsCharges());
    boxShipmentSetupBean.setCarrierTransportCharges(shipmentAcceptResponseBean.getTransportationCharges());
    boxShipmentSetupBean.setCarrierTotalCharges(shipmentAcceptResponseBean.getTotalCharges());
    boxShipmentSetupBean.setTrackingNumber(packageResultsBean.getTrackingNumber());
    boxShipmentSetupBean.setCarrierBoxServiceCharge(packageResultsBean.getServiceOptionsCharges());
    boxShipmentSetupBean.setCarrierLabelFilename(packageResultsBean.getLabelFilePath());
    boxShipmentSetupBean.setBoxId(packageResultsBean.getBoxId());
    boxShipmentSetupBean.setCarrierBillingWeight(shipmentAcceptResponseBean.getBillingWeight());
    //boxShipmentSetupBean.setCarrierLabel(packageResultsBean.getFile());
    factory.insert(boxShipmentSetupBean);
		 */
	}

	private String sendShipmentConfirmRequest(BigDecimal packingGroupId) throws
	BaseException, Exception{

		String response = null;
		UpsShippingInfoBean upsShippingInfoBean = null;
		UpsShippingInfoBeanFactory factory = new UpsShippingInfoBeanFactory(dbManager);
		Collection<UpsShippingInfoBean> c = factory.select(new SearchCriteria("packingGroupId", SearchCriterion.EQUALS, packingGroupId.toString()), null);

		if(c == null || c.size() == 0) {
			throw new NoDataException("No shipments for packingGroupId=" + packingGroupId);
		}
		int i = 0;
		boxIdArray = new String[c.size()];
		for(UpsShippingInfoBean tempBean : c) {
			boxIdArray[i] = tempBean.getBoxId().toString();
			i++;
		}
		Vector v = new Vector(c);

		upsShippingInfoBean = (UpsShippingInfoBean)((UpsShippingInfoBean)v.elementAt(0)).clone();

		upsShippingInfoBean.setUpsShippingInfoBeanCollection(c);


		FileHandler.saveTempFile(getShipmentConfirmRequest(upsShippingInfoBean), "confirm_request", ".xml", library.getString("backupfile.dir"));
		if(log.isDebugEnabled()) {
			//log.debug("sending ship confirm: packingGroupId "+packingGroupId+"\n" + getShipmentConfirmRequest(upsShippingInfoBean));
		}
		//response = NetHandler.getHttpPostResponse(library.getString("url.shipconfirm"), library.getString("username"), library.getString("password"), getShipmentConfirmRequest(upsShippingInfoBean));
		response = this.getHttpPostResponse(library.getString("url.shipconfirm"), library.getString("username"), library.getString("password"), getShipmentConfirmRequest(upsShippingInfoBean));

		if(log.isDebugEnabled()) {
			//log.debug("response:" + response);
			//log.debug("response ship confirm: packingGroupId "+packingGroupId+"");
		}
		return response;
	}

	private String getHttpPostResponse(String url,
			String userName,
			String password,
			String postString) throws IOException,
			MalformedURLException

			{

		String authenticationString = userName + ":" + password;
		BASE64Encoder be = new BASE64Encoder();
		String encodedAuthenticationString = be.encode(authenticationString.getBytes());
		java.net.URL target = new URL(url);

		HttpURLConnection uc = (HttpURLConnection) target.openConnection(Proxy.NO_PROXY);
		if (userName != null && password != null) {
			uc.setRequestProperty("Authorization", "Basic " + encodedAuthenticationString);
		}
		uc.setReadTimeout(0);
		uc.setFollowRedirects(true);
		uc.setInstanceFollowRedirects(true);
		uc.setAllowUserInteraction(true);
		uc.setDoOutput(true);
		uc.setDoInput(true);
		uc.setUseCaches(false);
		uc.setRequestMethod("POST");
		uc.setRequestProperty("Content-Type", "text/xml");
		// serialize and write the data
		OutputStream os = uc.getOutputStream();
		PrintWriter writer = new PrintWriter(os);
		writer.write(postString);
		writer.close();

		//    BufferedReader in = new BufferedReader(new InputStreamReader(target.openStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
		}
		in.close();
		writer.close();
		uc.disconnect();
		return sb.toString();
			}

	private String sendShipmentAcceptRequest(ShipmentConfirmResponseBean bean) throws
	BaseException, Exception{

		String response = null;
		FileHandler.saveTempFile(getShipmentAcceptRequest(bean), "accept_request", ".xml", library.getString("backupfile.dir"));
		if(log.isDebugEnabled()) {
			//log.debug("sending:" + getShipmentAcceptRequest(bean));
		}
		//response = NetHandler.getHttpPostResponse(library.getString("url.shipaccept"), library.getString("username"), library.getString("password"), getShipmentAcceptRequest(bean));
		response = this.getHttpPostResponse(library.getString("url.shipaccept"), library.getString("username"), library.getString("password"), getShipmentAcceptRequest(bean));

		if(log.isDebugEnabled()) {
			//log.debug("response:" + response);
		}
		return response;
	}

	private String getAccessRequest() throws
	BaseException{
		return "<?xml version=\"1.0\" ?> <AccessRequest xml:lang='en-US'> <AccessLicenseNumber> " + library.getString("accesslicensenumber") + "</AccessLicenseNumber> <UserId> " + library.getString("username") + " </UserId> <Password> " + library.getString("password") + " </Password> </AccessRequest>";
	}

	private String getShipmentConfirmRequest(UpsShippingInfoBean bean) throws
	BaseException{
		StringBuilder stringBuilder = new StringBuilder(this.getAccessRequest());
		stringBuilder.append("<?xml version=\"1.0\" ?> <ShipmentConfirmRequest> <Request>");
		stringBuilder.append("<TransactionReference> <CustomerContext> <InternalKey></InternalKey> </CustomerContext> </TransactionReference>");
		stringBuilder.append("<RequestAction>ShipConfirm</RequestAction><RequestOption>nonvalidate</RequestOption>");
		stringBuilder.append("</Request> ");
		stringBuilder.append("<Shipment> ");
		stringBuilder.append("<Shipper>  ");
		stringBuilder.append("<Name>" + StringHandler.xmlEncode(bean.getShipFromCompany()) + "</Name><PhoneNumber>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipFromPhone())) + "</PhoneNumber>");
		stringBuilder.append("<ShipperNumber>" + StringHandler.xmlEncode(bean.getUpsAccountNumber()) + "</ShipperNumber>");
		stringBuilder.append("<Address> <AddressLine1>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipFromAdd1())) + "</AddressLine1>");
		stringBuilder.append("<AddressLine2>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipFromAdd2())) + "</AddressLine2>");
		stringBuilder.append("<AddressLine3>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipFromAdd3())) + "</AddressLine3>");
		stringBuilder.append("<City>" + StringHandler.xmlEncode(bean.getShipFromCity()) + "</City> <StateProvinceCode>" + StringHandler.xmlEncode(bean.getShipFromState()) + "</StateProvinceCode> <CountryCode>" + StringHandler.xmlEncode(bean.getShipFromCountry()) + "</CountryCode> <PostalCode>" + StringHandler.xmlEncode(bean.getShipFromZip()) + "</PostalCode> </Address> </Shipper>");
		stringBuilder.append("<ShipTo>");
		stringBuilder.append("<CompanyName>" + StringHandler.xmlEncode(bean.getShipToCompany()) + "</CompanyName>");
		stringBuilder.append("<PhoneNumber>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipToPhone())) + "</PhoneNumber>");
		stringBuilder.append("<Address>");
		stringBuilder.append("<AddressLine1>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipToAdd1())) + "</AddressLine1>");
		stringBuilder.append("<AddressLine2>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipToAdd2())) + "</AddressLine2>");
		stringBuilder.append("<AddressLine3>" + StringHandler.xmlEncode(StringHandler.emptyIfNull(bean.getShipToAdd3())) + "</AddressLine3>");
		stringBuilder.append("<City>" + StringHandler.xmlEncode(bean.getShipToCity()) + "</City> <StateProvinceCode>" + StringHandler.xmlEncode(bean.getShipToState()) + "</StateProvinceCode> <CountryCode>" + StringHandler.xmlEncode(bean.getShipToCountry()) + "</CountryCode> <PostalCode>" + StringHandler.xmlEncode(bean.getShipToZip()) + "</PostalCode></Address>");
		stringBuilder.append("</ShipTo >");
		stringBuilder.append("<PaymentInformation>");
		stringBuilder.append("<Prepaid>>");
		stringBuilder.append("<BillShipper>");
		stringBuilder.append("<AccountNumber>");
		stringBuilder.append(StringHandler.xmlEncode(bean.getUpsAccountNumber()));
		stringBuilder.append("</AccountNumber>");
		stringBuilder.append("</BillShipper>");
		stringBuilder.append("</Prepaid>>");
		stringBuilder.append("</PaymentInformation>");

		stringBuilder.append("<Service>");
		stringBuilder.append("<Code>" + StringHandler.xmlEncode(bean.getServiceCode()) + "</Code>");
		stringBuilder.append("</Service>");
		for(UpsShippingInfoBean upsShippingInfoBean : bean.getUpsShippingInfoBeanCollection()) {
			stringBuilder.append("<Package>");
			stringBuilder.append("<PackagingType> <Code>" + StringHandler.xmlEncode(upsShippingInfoBean.getPackageCode()) + "</Code> </PackagingType>");
			//stringBuilder.append("<Dimensions> <UnitOfMeasurement> <Code>IN</Code> </UnitOfMeasurement> <Length>22</Length> <Width>20</Width> <Height>18</Height> </Dimensions> ");
			stringBuilder.append(" <PackageWeight> <Weight>" + upsShippingInfoBean.getWeight() + "</Weight> </PackageWeight>");
			stringBuilder.append("<ReferenceNumber><Code>ID</Code> <Value>" + StringHandler.xmlEncode(upsShippingInfoBean.getOrderNo()) + "</Value> </ReferenceNumber>");
			//stringBuilder.append("<PackageServiceOptions> <InsuredValue> <CurrencyCode>USD</CurrencyCode> <MonetaryValue>1000.00</MonetaryValue> </InsuredValue>  </PackageServiceOptions>");
			stringBuilder.append("</Package>");
		}
		stringBuilder.append("</Shipment>");
		stringBuilder.append("<LabelSpecification> <LabelStockSize><Height>4</Height><Width>6</Width></LabelStockSize><LabelPrintMethod> <Code>" + fileFormat + "</Code> </LabelPrintMethod> <HTTPUserAgent>Mozilla/4.5</HTTPUserAgent> <LabelImageFormat> <Code>" + fileFormat + "</Code> </LabelImageFormat> </LabelSpecification> </ShipmentConfirmRequest>");

		return stringBuilder.toString();
	}

	private String getShipmentAcceptRequest(ShipmentConfirmResponseBean bean) throws
	BaseException{
		StringBuilder stringBuilder = new StringBuilder(this.getAccessRequest());
		stringBuilder.append("<?xml version=\"1.0\" ?> <ShipmentAcceptRequest> <Request>  <RequestAction>ShipAccept</RequestAction> </Request> <ShipmentDigest>"+bean.getShipmentDigest()+"</ShipmentDigest> </ShipmentAcceptRequest>");
		return stringBuilder.toString();
	}

	private String getVoidShipmentRequest(String shipmentId, Collection<String> trackingNumberCollection) throws
	BaseException{
		StringBuilder stringBuilder = new StringBuilder(this.getAccessRequest());
		stringBuilder.append("<?xml version=\"1.0\" ?> <VoidShipmentRequest> <Request>  <RequestAction>Void</RequestAction> </Request>");
		stringBuilder.append("<ExpandedVoidShipment>");
		stringBuilder.append("<ShipmentIdentificationNumber>" + shipmentId + "</ShipmentIdentificationNumber>");
		if(trackingNumberCollection != null) {
			for(String trackingNumber : trackingNumberCollection) {
				stringBuilder.append("TrackingNumber>" + trackingNumber + "</TrackingNumber>");
			}
		}

		stringBuilder.append("</ExpandedVoidShipment>");
		stringBuilder.append("</VoidShipmentRequest>");
		return stringBuilder.toString();
	}

	public void reprint(PrintLabelInputBean inputBean) throws
	BaseException, Exception{
		MslBoxViewBeanFactory mslBoxViewBeanFactory = new MslBoxViewBeanFactory(dbManager);
		Collection<MslBoxViewBean> coll = mslBoxViewBeanFactory.select(new SearchCriteria("packingGroupId", SearchCriterion.EQUALS, inputBean.getPackingGroupId().toString()), null);
		BoxShipmentSetupBeanFactory factory = new BoxShipmentSetupBeanFactory(dbManager);
		for(MslBoxViewBean mslBoxViewBean : coll) {
			Collection<BoxShipmentSetupBean> c = factory.select(new SearchCriteria("boxId", SearchCriterion.EQUALS, mslBoxViewBean.getBoxId()), null);
			for(BoxShipmentSetupBean bean : c) {
				PrintHandler.print(inputBean.getPrinterName(), bean.getCarrierLabelFilename());
			}
		}
	}
}
