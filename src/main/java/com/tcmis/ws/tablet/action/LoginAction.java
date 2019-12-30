package com.tcmis.ws.tablet.action;

import java.io.OutputStreamWriter;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class LoginAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String responseStatus = RESPONSE_INVALID_LOGIN;
		String message = "error.login.invalid";
		PersonnelBean loginBean = new PersonnelBean();
		PersonnelBean user = new PersonnelBean();
		Locale usrSelectedLocale = new Locale((String) request.getParameter("locale"));
		BeanHandler.copyAttributes(form, loginBean);
		JSONArray localizedPages = null;
		JSONArray menuItems = null;
		JSONArray hubs = null;
		String defaultHub = "";
		String userName = "";

		HttpSession session = request.getSession(true);
		if (!StringHandler.isBlankString(loginBean.getLogonId())) {
			try {
				String client = getDbUser(request);
				LoginProcess loginProcess = new LoginProcess(client);
				user.setClient(client);
				loginBean.setClient(client);
				loginBean.setCompanyId("Radian");
				user = loginProcess.loginTablet(loginBean);
				if (user != null) {
					if (loginProcess.isPasswordExpired(user)) {
						responseStatus = RESPONSE_PASSWORD_EXPIRED;
						message = "label.expiredpassordmsg";
					}
					else {
						userName = (StringHandler.isBlankString(user.getFirstName()) ? "" : user.getFirstName()) + " " + (StringHandler.isBlankString(user.getLastName()) ? "" : user.getLastName());
						user.setDbUser(getDbUser(request));
						user.setLocale(setLocale(request, session, request.getParameter("locale")));

						session.setAttribute("personnelBean", user);
						responseStatus = RESPONSE_OK;
						message = "";

						SimpleTabletProcess tabletProcess = new SimpleTabletProcess(client);
						menuItems = tabletProcess.getTabletMenu(user);

						if (menuItems != null) {
							ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", user.getLocale());
							for (int i = 0; i < menuItems.length(); i++) {
								JSONObject pagemenu = menuItems.getJSONObject(i);
								pagemenu.put("pageNameFromLocale", library.getString(pagemenu.getString("pageId")));
							}
						}

						hubs = new JSONArray();
						@SuppressWarnings("unchecked")
						Collection<HubInventoryGroupOvBean> hubCollection = user.getHubInventoryGroupOvBeanCollection();
						for (HubInventoryGroupOvBean hub : hubCollection) {
							JSONObject hubJSON = new JSONObject();
							hubJSON.put("hubId", hub.getBranchPlant());
							hubJSON.put("hubName", hub.getHubName());
							hubs.put(hubJSON);
						}
						defaultHub = tabletProcess.getUserDefaultHub(user);
					}
				}
			}
			catch (Exception ex) {
				log.error("Error logging in: ", ex);
				responseStatus = RESPONSE_ERROR;
				message = ex.toString();
				message = "error.login.invalid";
			}
		}

		JSONObject results = new JSONObject();
		results.put("Status", responseStatus);
		results.put("Message", new ResourceLibrary("com.tcmis.common.resources.CommonResources", usrSelectedLocale).getString(message));
		results.put("LogonId", loginBean.getLogonId());
		results.put("userName", userName);

		if (menuItems != null) {
			results.put("Pages", menuItems);
		}

		if (hubs != null) {
			results.put("defaultHub", defaultHub);
			results.put("hubs", hubs);
		}

		// Write out the data
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

	// Kavitha: Added for locale implementation
	// Larry Note: take struct locale.
	public static Locale setLocale(HttpServletRequest request, HttpSession session, String lang) {
		Locale locale = (Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
		if (locale == null) locale = request.getLocale(); // take from brower.

		boolean found = false;
		Vector<VvLocaleBean> localeBeans = (Vector<VvLocaleBean>) session.getServletContext().getAttribute("vvLocaleBeanCollection");
		session.setAttribute("langSetting", lang); // for our own use...

		if (lang != null) {
			if (lang.length() == 5) {
				String[] langArr = lang.split("_");
				if (langArr.length == 2) {
					locale = new Locale(langArr[0].toLowerCase(), langArr[1].toUpperCase());
					String localeS = locale.toString();
					for (VvLocaleBean bean : localeBeans) {
						if (bean.getLocaleCode().equals(localeS)) found = true;
					}
				}
			}
			else if (lang.length() == 2) {
				locale = new Locale(lang.toLowerCase());
				String localeS = locale.toString();
				for (VvLocaleBean bean : localeBeans) {
					if (bean.getLocaleCode().substring(0, 2).equals(localeS)) {
						locale = new Locale(bean.getLocaleCode().substring(0, 2), bean.getLocaleCode().substring(3, 5));
						found = true;
					}
				}
			}
		}

		if (!found) locale = defaultLocale;
		session.setAttribute("tcmISLocale", locale);
		/* This is for struts to know which locale to use */
		session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, locale);
		/* This is for the <fmt> tag. */
		Config.set(session, Config.FMT_LOCALE, locale);

		session.setAttribute("tcmISDecimalSeparator", new DecimalFormatSymbols(locale).getDecimalSeparator());

		return locale;
	}

}
