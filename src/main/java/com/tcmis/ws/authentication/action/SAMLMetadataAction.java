package com.tcmis.ws.authentication.action;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import org.opensaml.common.SAMLRuntimeException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml2.metadata.EmailAddress;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.GivenName;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.LocalizedString;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.OrganizationURL;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SurName;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.w3c.dom.Document;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.authentication.saml.OpenSAMLUtils;
import com.tcmis.ws.authentication.saml.SPCredentials;

public class SAMLMetadataAction extends TcmISBaseAction {
	private static ResourceLibrary	tcmisLibrary	= new ResourceLibrary("tcmISWebResource");
	private static String			serverURL		= tcmisLibrary.getString("hosturl");

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
		if (!module.startsWith("/")) {
			module = "/" + module;
		}
		String companyId = getPathCompany(request);
		LoginProcess process = new LoginProcess(getDbUser());
		CompanyBean company = process.getCompanyByID(companyId);

		EntityDescriptor spEntityDescriptor = OpenSAMLUtils.buildSAMLObject(EntityDescriptor.class);
		spEntityDescriptor.setEntityID(serverURL);
		SPSSODescriptor ssoDescriptor = OpenSAMLUtils.buildSAMLObject(SPSSODescriptor.class);
		ssoDescriptor.setWantAssertionsSigned(true);
		ssoDescriptor.setAuthnRequestsSigned(false);
		spEntityDescriptor.getRoleDescriptors().add(ssoDescriptor);

		KeyDescriptor encKeyDescriptor = OpenSAMLUtils.buildSAMLObject(KeyDescriptor.class);

		encKeyDescriptor.setUse(UsageType.ENCRYPTION); // Set usage

		// Generating key info. The element will contain the public key. The key
		// is used to by the IDP to encrypt data
		try {
			encKeyDescriptor.setKeyInfo(getServerKeyInfo());
		}
		catch (SecurityException e) {
			log.error(e.getMessage(), e);
		}

		ssoDescriptor.getKeyDescriptors().add(encKeyDescriptor);

		KeyDescriptor signKeyDescriptor = OpenSAMLUtils.buildSAMLObject(KeyDescriptor.class);

		signKeyDescriptor.setUse(UsageType.SIGNING); // Set usage

		// Generating key info. The element will contain the public key. The key
		// is used to by the IDP to verify signatures
		try {
			signKeyDescriptor.setKeyInfo(getServerKeyInfo());
		}
		catch (SecurityException e) {
			log.error(e.getMessage(), e);
		}

		ssoDescriptor.getKeyDescriptors().add(signKeyDescriptor);

		// Request transient pseudonym
		NameIDFormat nameIDFormat = OpenSAMLUtils.buildSAMLObject(NameIDFormat.class);
		nameIDFormat.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
		ssoDescriptor.getNameIDFormats().add(nameIDFormat);

		// Setting address for our AssertionConsumerService
		AssertionConsumerService postConsumerService = OpenSAMLUtils.buildSAMLObject(AssertionConsumerService.class);
		postConsumerService.setIndex(1);
		postConsumerService.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		postConsumerService.setLocation(serverURL + "tcmIS" + module + "/SAMLPostConsumer.do");
		ssoDescriptor.getAssertionConsumerServices().add(postConsumerService);

		AssertionConsumerService postSSOConsumerService = OpenSAMLUtils.buildSAMLObject(AssertionConsumerService.class);
		postSSOConsumerService.setIndex(2);
		postSSOConsumerService.setBinding(SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI);
		postSSOConsumerService.setLocation(serverURL + "tcmIS" + module + "/SAMLPostConsumer.do");
		ssoDescriptor.getAssertionConsumerServices().add(postSSOConsumerService);

		// AssertionConsumerService artifactConsumerService =
		// OpenSAMLUtils.buildSAMLObject(AssertionConsumerService.class);
		// artifactConsumerService.setIndex(3);
		// artifactConsumerService.setBinding(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);
		// artifactConsumerService.setLocation(serverURL + "tcmIS/" + module +
		// "/SAMLArtifactConsumer.do");
		// ssoDescriptor.getAssertionConsumerServices().add(artifactConsumerService);

		ssoDescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);

		Organization org = OpenSAMLUtils.buildSAMLObject(Organization.class);
		ssoDescriptor.setOrganization(org);

		OrganizationName orgName = OpenSAMLUtils.buildSAMLObject(OrganizationName.class);
		orgName.setName(new LocalizedString("Wesco Aircraft - tcmIS", "en"));
		org.getOrganizationNames().add(orgName);

		OrganizationDisplayName orgDisplayName = OpenSAMLUtils.buildSAMLObject(OrganizationDisplayName.class);
		orgDisplayName.setName(new LocalizedString("tcmIS @ Wesco Aircraft", "en"));
		org.getDisplayNames().add(orgDisplayName);

		OrganizationURL orgURL = OpenSAMLUtils.buildSAMLObject(OrganizationURL.class);
		orgURL.setURL(new LocalizedString(serverURL, "en"));
		org.getURLs().add(orgURL);

		ContactPerson contact = OpenSAMLUtils.buildSAMLObject(ContactPerson.class);
		contact.setType(ContactPersonTypeEnumeration.TECHNICAL);
		ssoDescriptor.getContactPersons().add(contact);

		GivenName name = OpenSAMLUtils.buildSAMLObject(GivenName.class);
		name.setName("Kelly");
		contact.setGivenName(name);
		SurName lastName = OpenSAMLUtils.buildSAMLObject(SurName.class);
		lastName.setName("Hatcher");
		contact.setSurName(lastName);
		EmailAddress email = OpenSAMLUtils.buildSAMLObject(EmailAddress.class);
		email.setAddress("kelly.hatcher@wescoair.com");
		contact.getEmailAddresses().add(email);

		StringWriter stringWriter = new StringWriter();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.newDocument();
			Marshaller out = Configuration.getMarshallerFactory().getMarshaller(spEntityDescriptor);
			out.marshall(spEntityDescriptor, document);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StreamResult streamResult = new StreamResult(stringWriter);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, streamResult);
			stringWriter.close();
		}
		catch (Exception e) {
			log.error("Unable to marshall XML for SAML metadata", e);
		}

		response.setContentType("application/saml+xml");
		response.getWriter().append(stringWriter.toString());

		return noForward;
	}

	protected KeyInfo getServerKeyInfo() {
		Credential serverCredential = SPCredentials.getCredential();
		if (serverCredential == null) {
			throw new RuntimeException("SSO Key not found");
		}
		else if (serverCredential.getPrivateKey() == null) {
			throw new RuntimeException("SSO Key doesn't have a private key");
		}
		return generateKeyInfoForCredential(serverCredential);
	}

	protected KeyInfo generateKeyInfoForCredential(Credential credential) {
		try {
			X509KeyInfoGeneratorFactory keyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();
			keyInfoGeneratorFactory.setEmitEntityCertificate(true);
			KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();
			return keyInfoGenerator.generate(credential);
		}
		catch (org.opensaml.xml.security.SecurityException e) {
			log.error("Can't obtain key from the keystore or generate key info for credential: " + credential, e);
			throw new SAMLRuntimeException("Can't obtain key from keystore or generate key info", e);
		}
	}
}
