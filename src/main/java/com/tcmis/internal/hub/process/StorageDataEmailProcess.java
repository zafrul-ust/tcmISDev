package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.catalog.factory.IItemStorageDataMapper;

public class StorageDataEmailProcess {

	private static final String SHIPPING_INFO_URL = "tcmIS/hub/shippinginfo.do?uAction=search&itemId=";
	
	private final Log log;
	private IItemStorageDataMapper dataMapper;
	private final ResourceLibrary resources;
	private final ResourceBundle commonResources;
	
	public StorageDataEmailProcess(IItemStorageDataMapper dataMapper, Locale locale) {
		Objects.requireNonNull(dataMapper);
		log = LogFactory.getLog(this.getClass());
		resources = new ResourceLibrary("tcmISWebResource");
		commonResources = ResourceBundle.getBundle("com.tcmis.common.resources.CommonResources", locale);
		this.dataMapper = dataMapper;
	}
	
	private IItemStorageDataMapper getDataMapper() {
		return dataMapper;
	}
	
	public String process() throws BaseException {
		try {
			Collection<BigDecimal> incompleteItems = getDataMapper().findItemsWithIncompleteStorageData();
			if ( ! incompleteItems.isEmpty()) {
				sendEmail(generateMsg(incompleteItems));
			}
		} catch(Exception e) {
			log.error(e);
		}
		
		return commonResources.getString("label.success");
	}
	
	private void sendEmail(String msg) {
		try {
			Collection<PersonnelBean> recipientList = getDataMapper().listEmailRecipients();
			String[] recipients = recipientList.stream().map(PersonnelBean::getEmail).collect(Collectors.toList()).toArray(new String[recipientList.size()]);
			String[] cc = new String[0];
			String replyTo = "deverror@tcmis.com";
			String subject = commonResources.getString("label.storagedatasubject");
			MailProcess.sendEmail(recipients, cc, replyTo, subject, msg);
		} catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send notification email failed)");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to intended recipients to notify them of incomplete storage data on Confirmed PO Items");
		}
	}
	
	private String generateMsg(Collection<BigDecimal> incompleteItems) {
		return commonResources.getString("label.storagedatabodyheader") + "\n\n"
				+ "\t" + incompleteItems.stream()
						.map(itemId -> generateUrl(itemId))
						.collect(Collectors.joining("\n\t")) + "\n\n"
				+ commonResources.getString("label.storagedatabodyfooter");
	}
	
	private String generateUrl(BigDecimal itemId) {
		return resources.getString("hosturl") + SHIPPING_INFO_URL + itemId;
	}
}
