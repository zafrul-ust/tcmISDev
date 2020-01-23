package com.tcmis.internal.react.actions.authentication;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.react.actions.TcmisReactAction;
import com.tcmis.internal.react.beans.LoginReactBean;

public class LoginAction extends TcmisReactAction {
    private static boolean needpass = false;
    static {
	try {

	    ResourceLibrary tcmisLibrary = new ResourceLibrary("tcmISWebResource");
	    String serverURL = tcmisLibrary.getString("hosturl");
	    ResourceLibrary localconfig = new ResourceLibrary("localconfig");
	    String noRequired = localconfig.getString("NoPassRequired");
	    String noPass = "NoPassRequired";
	    if (serverURL.indexOf("www") == -1 && noPass.equals(noRequired)) {
		needpass = false;
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
	    String jsonString) throws BaseException {
	boolean ok = false;
	String requestMethod = request.getMethod();
	JSONObject responseBody = new JSONObject();
	int responseCode = HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED;
	String responseMsg = "";
	if (requestMethod.equalsIgnoreCase("POST")) {
	    try {
		JSONObject requestBody = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString)
			: new JSONObject();
		LoginReactBean inputBean = (LoginReactBean) BeanHandler.getJsonBeans(requestBody, new LoginReactBean());
		if (inputBean.getLogonId().isEmpty()) {
		    responseMsg = "LOGON ID IS MANDATORY";
		    if (this.log.isDebugEnabled()) {
			this.log.debug("PersonnelBean is null!");
		    }
		} else {
		    HttpSession session = request.getSession(true);
		    PersonnelBean personnelBean = new PersonnelBean();
		    personnelBean.setLogonId(inputBean.getLogonId());
		    personnelBean.setPassword(inputBean.getPassword());
		    session.setAttribute("passwd", personnelBean.getPassword());
		    personnelBean.setClearTextPassword(personnelBean.getPassword());
		    LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
		    personnelBean = loginProcess.loginWeb(personnelBean, needpass);
		    if (loginProcess.isPasswordExpired(personnelBean)) {
			if (this.log.isDebugEnabled()) {
			    this.log.debug("Password Expired!");
			}
			responseMsg = "PASSWORD IS EXPIRED";
		    } else {
			if (personnelBean != null) {
			    if (this.log.isInfoEnabled()) {
				this.log.info("bean:" + personnelBean.getLogonId() + " - "
					+ personnelBean.getPersonnelId() + " - " + this.getDbUser(request));
			    }
			    personnelBean.setDbUser(this.getDbUser());
			    personnelBean.setLocale(setLocale(request, session, inputBean.getLangSetting()));
			    if (!personnelBean.isStandalone()) {
				loginProcess.storeUserLoginData(personnelBean, request.getRequestURL().toString());
				responseBody.put("fullName", personnelBean.getFullName());
				responseBody.put("name", personnelBean.getFirstName());
				responseBody.put("surname", personnelBean.getLastName());
				responseBody.put("phone", personnelBean.getPhone());
				responseBody.put("fax", personnelBean.getFax());
				responseBody.put("email", personnelBean.getEmail());
				responseBody.put("companyName", personnelBean.getCompanyName());
			    }
			    CompanyBean company = loginProcess.getCompany(personnelBean);
			    int timeout = company.getAppTimeout();
			    if (timeout == 0) {
				timeout = 7200;
			    } else {
				timeout /= 1000;
				timeout += 300;
			    }
			    String token = createToken(personnelBean.getPersonnelId(), "", personnelBean.getCompanyId(),
				    loginProcess.getSchema(), timeout);
			    responseBody.put("token", token);
			    session.setAttribute("personnelBean", personnelBean);
			    responseMsg = "Success";
			    ok = true;

			} else {
			    if (this.log.isDebugEnabled()) {
				this.log.debug("PersonnelBean is null!");
			    }
			    responseMsg = "PersonnelBean is null";
			}
		    }
		    responseBody.put("ok", ok);
		    responseBody.put("message", responseMsg);
		    responseCode = HttpServletResponse.SC_OK;
		}
	    }

	    catch (JSONException e) {
		responseCode = HttpServletResponse.SC_BAD_REQUEST;
		responseMsg = "Request body is not valid JSON";
	    } catch (JWTCreationException e) {
		responseCode = HttpServletResponse.SC_FORBIDDEN;
	    } catch (Exception e) {
		responseCode = HttpServletResponse.SC_BAD_REQUEST;
	    }

	} else {
	    responseCode = HttpServletResponse.SC_BAD_REQUEST;
	    responseMsg = "Bad Request";
	}

	try {
	    if (responseCode == HttpServletResponse.SC_OK) {
		PrintWriter out = response.getWriter();
		out.write(responseBody.toString(2));
		out.close();
	    } else {
		response.sendError(responseCode, responseMsg);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return noForward;
    }

    public static Locale setLocale(HttpServletRequest request, HttpSession session, String lang) {
	Locale locale = (Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	if (locale == null) {
	    locale = request.getLocale();
	}
	boolean found = false;
	Vector<VvLocaleBean> localeBeans = (Vector) session.getServletContext().getAttribute("vvLocaleBeanCollection");
	session.setAttribute("langSetting", lang);

	if (lang != null) {
	    if (lang.length() == 5) {
		String[] langArr = lang.split("_");
		if (langArr.length == 2) {
		    locale = new Locale(langArr[0].toLowerCase(), langArr[1].toUpperCase());
		    String localeS = locale.toString();
		    Iterator<VvLocaleBean> var8 = localeBeans.iterator();
		    while (var8.hasNext()) {
			VvLocaleBean bean = var8.next();
			if (bean.getLocaleCode().equals(localeS)) {
			    found = true;
			}
		    }
		}
	    } else if (lang.length() == 2) {
		locale = new Locale(lang.toLowerCase());
		String localeS = locale.toString();
		Iterator<VvLocaleBean> var11 = localeBeans.iterator();
		while (var11.hasNext()) {
		    VvLocaleBean bean = var11.next();
		    if (bean.getLocaleCode().substring(0, 2).equals(localeS)) {
			locale = new Locale(bean.getLocaleCode().substring(0, 2), bean.getLocaleCode().substring(3, 5));
			found = true;
		    }
		}
	    }
	}

	if (!found) {
	    locale = defaultLocale;
	}
	return locale;
    }

}