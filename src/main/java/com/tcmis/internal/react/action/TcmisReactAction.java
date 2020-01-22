package com.tcmis.internal.react.action;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.JsonRequestResponseAction;
import com.tcmis.internal.react.beans.TokenReactBean;

public abstract class TcmisReactAction extends JsonRequestResponseAction {

	private static HashMap<String, Date> tokenList = new HashMap<>();
	private static PersonnelBean personnelBean;
	private static final String ALGKEY = "Ww@reurdGsjee9o0oiBgBsddhaaqG3#dg%$sgbretcc$$4erHWsv?dk";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.request = request;
		if (log.isDebugEnabled()) {
			log.debug(getRequestedURLWithParameters(request));
		}
		personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		executeAction(mapping, form, request, response);
		return mapping.findForward("empty");
	}

	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}

	public PersonnelBean getSessionPersonnelBean(HttpServletRequest request) {
		return (PersonnelBean) this.getSessionObject(request, "personnelBean");
	}

	public String createToken(int idUser, String module, String company, String schema, int timeout) {

		try {
			Date expirationDate = Date.from(ZonedDateTime.now().plusSeconds(timeout).toInstant());
			Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
			Algorithm algorithm = Algorithm.HMAC256(ALGKEY);
			String token = "Bearer " + JWT.create().withIssuedAt(issuedAt).withExpiresAt(expirationDate)
					.withClaim("userId", idUser).withClaim("module", module).withClaim("companyId", company)
					.withClaim("schema", schema).withClaim("timeout", timeout).withIssuer("WESCO").sign(algorithm);
			tokenList.put(token, expirationDate);
			return token;
		} catch (JWTCreationException e) {
			throw (e);
		}

	}

	public void deleteToken(String token) {
		tokenList.remove(token);
	}

	public TokenReactBean tokenVerify(String token) {
		TokenReactBean tokenResponse = new TokenReactBean();
		try {
			String realToken = token.replaceFirst("Bearer ", "");
			Algorithm algorithm = Algorithm.HMAC256(ALGKEY);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("WESCO").build(); // Reusable verifier instance

			if (verifier.verify(realToken).getType() != null) {
				if (tokenList.containsKey(token)) {
					DecodedJWT jwt = verifier.verify(realToken);
					tokenResponse.setUserId(jwt.getClaim("userId").asInt());
					tokenResponse.setModule(jwt.getClaim("module").asString());
					tokenResponse.setCompanyId(jwt.getClaim("companyId").asString());
					tokenResponse.setSchema(jwt.getClaim("schema").asString());
					tokenResponse.setTimeout(jwt.getClaim("timeout").asInt());

					Date expiresAt = jwt.getExpiresAt();// exp
					Date notBefore = jwt.getNotBefore();// nbf
					Date issuedAt = jwt.getIssuedAt(); // iat

					Date currentDate = Date.from(ZonedDateTime.now().toInstant());
					if (currentDate.after(expiresAt)) {
						deleteToken(token);
						tokenResponse.setValid(false);
						tokenResponse.setMessage("Token has expired");
					} else {
						tokenResponse.setValid(true);
					}

				} else {
					tokenResponse.setValid(false);
					tokenResponse.setMessage("Invalid Token");
				}

			} else {
				deleteToken(token);
			}

		} catch (JWTVerificationException exception) {
			deleteToken(token);
			// Invalid signature/claims
			tokenResponse.setValid(false);
			tokenResponse.setMessage(exception.getMessage());
		}
		return tokenResponse;
	}

}