package com.tcmis.client.fedex.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 29, 2008
 * Time: 2:55:07 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.fedex.beans.ProcessShipmentReplyBean;
import com.tcmis.client.fedex.beans.PackageDetailBean;

public class ProcessShipmentReplyParser extends BaseProcess {
	public ProcessShipmentReplyParser(String client) {
		super(client);
	}

	public ProcessShipmentReplyBean parse(File file, int packageCount, int packageSequence) throws BaseException {
		Class[] bigDecimalClass = {BigDecimal.class};
		Class[] dateClass = {Date.class};
		ProcessShipmentReplyBean processShipmentReplyBean = null;
		try {
			Digester digester = new Digester();
			// Process previous version v3: XML tags
			digester.addObjectCreate("v3:ProcessShipmentReply", ProcessShipmentReplyBean.class);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:HighestSeverity", "setNotification", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:Notifications/v3:Severity", "setSeverity", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:Notifications/v3:Message", "setMessage", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CarrierCode", "setCarrierCode", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:ServiceTypeDescription", "setServiceDescription", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:RoutingDetail/v3:TransitTime", "setTransitTime", 0);
			digester.addObjectCreate("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails", PackageDetailBean.class);
			digester.addSetNext("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails", "addPackageDetailBean");
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:TrackingId/v3:TrackingNumber", "setTrackingNumber", 0);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:BillingWeight/v3:Value", "setBillingWeight", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:BaseCharge/v3:Amount", "setBaseCharge", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:TotalFreightDiscounts/v3:Amount","setTotalFreightDiscounts", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:NetFreight/v3:Amount","setNetFreight", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:TotalSurcharges/v3:Amount","setTotalSurcharges", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:PackageRating/v3:PackageRateDetails/v3:NetCharge/v3:Amount","setNetCharge", 0, bigDecimalClass);
			digester.addCallMethod("v3:ProcessShipmentReply/v3:CompletedShipmentDetail/v3:CompletedPackageDetails/v3:Label/v3:Parts/v3:Image", "setLabelImage", 0);
			// Add ability to parse XML WITHOUT the v3: in the tags
			digester.addObjectCreate("ProcessShipmentReply", ProcessShipmentReplyBean.class);
			digester.addCallMethod("ProcessShipmentReply/HighestSeverity", "setNotification", 0);
			digester.addCallMethod("ProcessShipmentReply/Notifications/Severity", "setSeverity", 0);
			digester.addCallMethod("ProcessShipmentReply/Notifications/Message", "setMessage", 0);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CarrierCode", "setCarrierCode", 0);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/ServiceTypeDescription", "setServiceDescription", 0);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/RoutingDetail/TransitTime", "setTransitTime", 0);
			digester.addObjectCreate("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails", PackageDetailBean.class);
			digester.addSetNext("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails", "addPackageDetailBean");
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/TrackingId/TrackingNumber", "setTrackingNumber", 0);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/BillingWeight/Value", "setBillingWeight", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/BaseCharge/Amount", "setBaseCharge", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/TotalFreightDiscounts/Amount","setTotalFreightDiscounts", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/NetFreight/Amount","setNetFreight", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/TotalSurcharges/Amount","setTotalSurcharges", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/PackageRating/PackageRateDetails/NetCharge/Amount","setNetCharge", 0, bigDecimalClass);
			digester.addCallMethod("ProcessShipmentReply/CompletedShipmentDetail/CompletedPackageDetails/Label/Parts/Image", "setLabelImage", 0);

			digester.parse(file);
			processShipmentReplyBean = (ProcessShipmentReplyBean) digester.getRoot();
		}
		catch (Exception e) {
			log.error("Error parsing XML response from FEDEX:" + e.getMessage(), e);
			BaseException be = new BaseException(e);
			be.setMessageKey("");
			be.setRootCause(e);
			throw be;
		}
		return processShipmentReplyBean;
	}

}
