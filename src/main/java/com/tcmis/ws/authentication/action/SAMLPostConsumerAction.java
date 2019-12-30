package com.tcmis.ws.authentication.action;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.util.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.common.admin.action.LoginAction;
import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.authentication.saml.OpenSAMLUtils;
import com.tcmis.ws.authentication.saml.SPCredentials;

public class SAMLPostConsumerAction extends TcmISBaseAction {
	public static Log		logger	= LogFactory.getLog(SAMLPostConsumerAction.class);

	private static String	serverURL;
	static {
		serverURL = new ResourceLibrary("tcmISWebResource").getString("hosturl");
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		ActionForward next = null;

		try {
			next = mapping.findForward("ssoError");
			String SAMLresponse = request.getParameter("SAMLResponse");
			String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
			String companyId = getPathCompany(request);
			LoginProcess process = new LoginProcess(getDbUser());
			CompanyBean company = process.getCompanyByID(companyId);
			if (StringUtils.isNotBlank(SAMLresponse)) {
				if (company.isModuleAccessSSO() || company.isModuleAccessSAML()) {
					ByteArrayInputStream is = new ByteArrayInputStream(Base64.decode(SAMLresponse));

					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
					documentBuilderFactory.setNamespaceAware(true);
					DocumentBuilder docBuilder;

					docBuilder = documentBuilderFactory.newDocumentBuilder();

					Document document = docBuilder.parse(is);
					Element element = document.getDocumentElement();

					UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
					Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
					XMLObject responseXmlObj = unmarshaller.unmarshall(element);

					Response response = (Response) responseXmlObj;

					Assertion assertion = null;
					List<Assertion> assertions = response.getAssertions();
					List<EncryptedAssertion> encAssertions = response.getEncryptedAssertions();
					if (encAssertions != null && !encAssertions.isEmpty()) {
						assertion = decryptAssertion(encAssertions.get(0));
					}
					else if (!assertions.isEmpty()) {
						assertion = assertions.get(0);
					}

					if (assertion != null) {
						if (isAssertionSignatureVerifiable(assertion, company)) {
							String logonId = getAssertionAttributeValue(assertion, "uid");
							if (StringUtils.isBlank(logonId)) {
								logonId = getAssertionAttributeValue(assertion, "employeeId");
							}
							if (StringUtils.isBlank(logonId)) {
								logonId = getAssertionAttributeValue(assertion, "logonId");
							}
							if (StringUtils.isBlank(logonId)) {
								logonId = getAssertionAttributeValue(assertion, "loginId");
							}

							if (StringUtils.isBlank(logonId)) {
								try {
									logonId = assertion.getSubject().getNameID().getValue();
								}
								catch (Exception e) {
									log.debug("Error fetching NameId Value", e);
								}
							}

							if (StringUtils.isBlank(logonId)) {
								request.setAttribute("SAML ERROR", "No uid/employeeId/logonId/loginId in Assertion during SSO!");
								log.error("No uid/employeeId/logonId/loginId in Assertion during SSO!");
								logAssertionAttributes(assertion);
							}
							else {
								Boolean doLogin = (Boolean) request.getSession().getAttribute("loginRequested");
								if (doLogin != null && doLogin) {
									try {
										login(request, resp, process, company, logonId);
										request.setAttribute("requestedURLWithParameters", serverURL + "tcmIS" + module + "/application.do?redirectToHome=Y");
										next = mapping.findForward("redirectToUrl");
									}
									catch (Exception e) {
										next = mapping.findForward("accessDenied");
									}
								}
								else {
									setCookie(request.getSession(true), request, resp);
									request.setAttribute("requestedURLWithParameters", serverURL + "tcmIS" + module + "/home.do");
									next = mapping.findForward("redirectToUrl");									
								}
							}
						}
						else {
							request.setAttribute("SAML Response", OpenSAMLUtils.SAMLObjectToString(response));
							request.setAttribute("SAML ERROR", "Signature unverifiable during SSO!");
							log.error("Signature unverifiable during SSO!");
							log.debug("Assertion:\n" + OpenSAMLUtils.SAMLObjectToString(assertion));
						}
					}
					else {
						request.setAttribute("SAML Response", OpenSAMLUtils.SAMLObjectToString(response));
						request.setAttribute("SAML ERROR", "No Assertions in SAML Response");
						log.error("No Assertions in SAML Response");
					}
				}
				else {
					request.setAttribute("SAML ERROR", "Company not configured for SSO");
					log.error("Company not configured for SSO");
				}
			}
			else {
				request.setAttribute("SAML ERROR", "No SAMLResponse in Request");
				log.error("No SAMLResponse passed");
			}
		}
		catch (Exception e) {
			request.setAttribute("SAML ERROR", "Error consuming response in  SAMLPostConsumer");
			request.setAttribute("SAML ERROR 2", e.getMessage());
			request.setAttribute("SAML ERROR 3", ExceptionUtils.getFullStackTrace(e));
			log.error("Error consuming response in  SAMLPostConsumer", e);
		}
		log.error("Forwarding to " + next.getPath());
		return next;
	}
	
	private void setCookie(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix().replace("/", "");
		Cookie tcmIScookie = new Cookie("tcmIS_Session", module + "_" + session.getId());
		tcmIScookie.setMaxAge(12 * 60 * 60); // 12 hours.
		tcmIScookie.setPath("/");
		tcmIScookie.setSecure(true);
		response.addCookie(tcmIScookie);
		session.setAttribute("SAMLAccessAuthorized", Boolean.TRUE);
	}

	private void login(HttpServletRequest request, HttpServletResponse response, LoginProcess process, CompanyBean company, String logonId) throws Exception {
		PersonnelBean user = new PersonnelBean();
		user.setLogonId(logonId);
		user.setClient(getDbUser());
		user.setCompanyId(company.getCompanyId());

		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession(true);
		session.setAttribute("standalone", request.getSession().getServletContext().getInitParameter("standalone"));

		setCookie(session, request, response);
		
		String standalone = request.getSession().getServletContext().getInitParameter("standalone");
		session.setAttribute("standalone", standalone);
		user.setStandalone("true".equalsIgnoreCase(standalone));
		try {
			user = process.loginWeb(user, false);
			user.setDbUser(getDbUser());
			session.setAttribute("personnelBean", user);
			session.setAttribute("schema", process.getSchema());

			user.setLocale(LoginAction.setLocale(request, session, "en_US"));
			if (!user.isStandalone()) {
				VvDataProcess vvDataProcess = new VvDataProcess(getDbUser(request), user.getLocale());
				session.setAttribute("vvCurrencyCollection", vvDataProcess.getVvCurrency());
				// use session data as little as possible, we should try to
				// group them together.
				session.setAttribute("vvpriceListCollection", process.getPriceListDropDown(user));
				session.setAttribute("hubInventoryGroupOvBeanCollection", user.getHubInventoryGroupOvBeanCollection());

				// keep track of who, when and for which client
				process.storeUserLoginData(user, request.getRequestURL().toString());
			}

			session.setAttribute("timeout", company.getAppTimeout());
			session.setAttribute("timeoutWait", company.getAppTimeoutWait());

			Cookie personnelCookie = new Cookie("pid", user.getPersonnelId() + "");
			personnelCookie.setMaxAge(12 * 60 * 60); // 12 hours.
			personnelCookie.setPath("/");
			response.addCookie(personnelCookie);

			Cookie companyCookie = new Cookie("cid", user.getCompanyId());
			personnelCookie.setMaxAge(12 * 60 * 60); // 12 hours.
			personnelCookie.setPath("/");
			response.addCookie(companyCookie);

			logger.debug("Succesfully logged in!");
		}
		catch (Exception ex) {
			log.error("Error logging in from SAMLPostConsumer as " + logonId, ex);
			request.setAttribute("requestedURLWithParameters", "home.do");
			request.setAttribute("home", user.getHome());
			throw ex;
		}
	}

	private Assertion decryptAssertion(EncryptedAssertion encryptedAssertion) {
		StaticKeyInfoCredentialResolver keyInfoCredentialResolver = new StaticKeyInfoCredentialResolver(SPCredentials.getCredential());

		Decrypter decrypter = new Decrypter(null, keyInfoCredentialResolver, new InlineEncryptedKeyResolver());
		decrypter.setRootInNewDocument(true);

		try {
			return decrypter.decrypt(encryptedAssertion);
		}
		catch (DecryptionException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isAssertionSignatureVerifiable(Assertion assertion, CompanyBean company) {
		if (!assertion.isSigned()) {
			request.setAttribute("SAML SIGNATURE ERROR", "The SAML Assertion was not signed");
			log.debug("The SAML Assertion was not signed");
			return false;
		}

		try {
			// SAMLSignatureProfileValidator profileValidator = new
			// SAMLSignatureProfileValidator();
			// profileValidator.validate(assertion.getSignature());
			// Signature sig = assertion.getSignature();
			// if (sig.getSigningCredential() != null ) {
			// SignatureValidator sigValidator = new
			// SignatureValidator(sig.getSigningCredential());
			// sigValidator.validate(assertion.getSignature());
			// }
			// else if (sig.getKeyInfo() != null) {
			// String x509CertString =
			// sig.getKeyInfo().getX509Datas().get(0).getX509Certificates().get(0).getValue();
			// SignatureValidator sigValidator = new
			// SignatureValidator(SPCredentials.getCredentialFromString(x509CertString));
			// sigValidator.validate(assertion.getSignature());
			// }
			// else {
			SignatureValidator sigValidator = new SignatureValidator(SPCredentials.getCredentialFromString(company.getSamlCertificate()));
			sigValidator.validate(assertion.getSignature());
			// }

			logger.debug("SAML Assertion signature verified");
		}
		catch (Exception e) {
			request.setAttribute("SAML SIGNATURE ERROR", "The SAML Assertion signature was not valid");
			log.debug("The SAML Assertion signature was not valid", e);
			return false;
		}

		return true;

	}

	private void logAuthenticationMethod(Assertion assertion) {
		logger.info("Authentication method: " + assertion.getAuthnStatements().get(0).getAuthnContext().getAuthnContextClassRef().getAuthnContextClassRef());
	}

	private void logAuthenticationInstant(Assertion assertion) {
		logger.info("Authentication instant: " + assertion.getAuthnStatements().get(0).getAuthnInstant());
	}

	private void logAssertionAttributes(Assertion assertion) {
		for (Attribute attribute : assertion.getAttributeStatements().get(0).getAttributes()) {
			for (XMLObject attributeValue : attribute.getAttributeValues()) {
				if (attributeValue instanceof XSString) {
					logger.info("Attribute name: " + attribute.getFriendlyName() + "(" + attribute.getName() + ") - " + ((XSString) attributeValue).getValue());
				}
			}
		}
	}

	private String getAssertionAttributeValue(Assertion assertion, String name) {
		if (!assertion.getAttributeStatements().isEmpty()) {
			for (AttributeStatement statement : assertion.getAttributeStatements()) {
				for (Attribute attribute : statement.getAttributes()) {
					if (name.equals(attribute.getName()) || name.equals(attribute.getFriendlyName())) {
						for (XMLObject value : attribute.getAttributeValues()) {
							if (value instanceof XSString) {
								return ((XSString) value).getValue();
							}
							else {
								return value.getDOM().getTextContent();
							}
						}
					}

				}
			}
		}
		log.debug("Assertion has no attribute in statement for " + name);
		return "";
	}

}
