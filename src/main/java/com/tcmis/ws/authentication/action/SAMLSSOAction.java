package com.tcmis.ws.authentication.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.authentication.saml.OpenSAMLUtils;
import com.tcmis.ws.authentication.saml.SPCredentials;

public class SAMLSSOAction extends TcmISBaseAction {
	public static Log				log				= LogFactory.getLog(SAMLSSOAction.class);

	private static ResourceLibrary	tcmisLibrary	= new ResourceLibrary("tcmISWebResource");
	private static String			serverURL		= tcmisLibrary.getString("hosturl");

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
		String companyId = getPathCompany(request);
		LoginProcess process = new LoginProcess(getDbUser());
		CompanyBean company = process.getCompanyByID(companyId);

		AuthnRequest authnRequest = OpenSAMLUtils.buildSAMLObject(AuthnRequest.class);
		authnRequest.setDestination(company.getSamlUrl());
		authnRequest.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		authnRequest.setAssertionConsumerServiceURL(serverURL + "tcmIS" + module + "/SAMLPostConsumer.do");
		authnRequest.setID(OpenSAMLUtils.generateSecureRandomId());
		authnRequest.setIssuer(buildIssuer());
		authnRequest.setNameIDPolicy(buildNameIdPolicy());
		//authnRequest.setRequestedAuthnContext(buildRequestedAuthnContext());
		authnRequest.setIssueInstant(new DateTime());

		HttpServletResponseAdapter responseAdapter = new HttpServletResponseAdapter(response, true);
		BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> context = new BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject>();

		SingleSignOnService endpoint = OpenSAMLUtils.buildSAMLObject(SingleSignOnService.class);
		endpoint.setBinding(SAMLConstants.SAML2_REDIRECT_BINDING_URI);
		endpoint.setLocation(company.getSamlUrl());
		context.setPeerEntityEndpoint(endpoint);

		context.setOutboundSAMLMessage(authnRequest);
		context.setOutboundMessageTransport(responseAdapter);
		//context.setOutboundSAMLMessageSigningCredential(SPCredentials.getCredential());

		HTTPRedirectDeflateEncoder encoder = new HTTPRedirectDeflateEncoder();
		try {
			encoder.encode(context);
		}
		catch (MessageEncodingException e) {
			throw new RuntimeException(e);
		}
		if (log.isInfoEnabled()) {
			log.info("AuthnRequest: ");
			OpenSAMLUtils.logSAMLObject(authnRequest);
			log.info("Redirecting to IDP -> " + company.getSamlUrl());
		}
		return noForward;
	}

	private Issuer buildIssuer() {
		Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
		issuer.setValue(serverURL);

		return issuer;
	}

	private NameIDPolicy buildNameIdPolicy() {
		NameIDPolicy nameIDPolicy = OpenSAMLUtils.buildSAMLObject(NameIDPolicy.class);
		nameIDPolicy.setAllowCreate(true);

		nameIDPolicy.setFormat(NameIDType.TRANSIENT);

		return nameIDPolicy;
	}

	private RequestedAuthnContext buildRequestedAuthnContext() {
		RequestedAuthnContext requestedAuthnContext = OpenSAMLUtils.buildSAMLObject(RequestedAuthnContext.class);
		requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.MINIMUM);

		AuthnContextClassRef passwordAuthnContextClassRef = OpenSAMLUtils.buildSAMLObject(AuthnContextClassRef.class);
		passwordAuthnContextClassRef.setAuthnContextClassRef(AuthnContext.PASSWORD_AUTHN_CTX);

		requestedAuthnContext.getAuthnContextClassRefs().add(passwordAuthnContextClassRef);

		return requestedAuthnContext;

	}
}
