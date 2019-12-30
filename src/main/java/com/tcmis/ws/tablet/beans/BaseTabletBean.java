package com.tcmis.ws.tablet.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

@SuppressWarnings("serial")
public class BaseTabletBean extends BaseDataBean {

	public BaseTabletBean() {
	}

	public BaseTabletBean(ActionForm inputForm) {
		this(inputForm, null);
	}

	public BaseTabletBean(ActionForm inputForm, Locale locale) {
		this(inputForm, locale, "tabletdateformat");
	}

	public BaseTabletBean(ActionForm inputForm, Locale locale, String dateFormat) {
		if (inputForm != null) {
			try {
				if (locale != null) {
					if (dateFormat != null) {
						BeanHandler.copyAttributes(inputForm, this, dateFormat, locale);
					}
					else {
						BeanHandler.copyAttributes(inputForm, this, locale);
					}
				}
				else {
					BeanHandler.copyAttributes(inputForm, this);
				}
			}
			catch (BeanCopyException e) {
				e.printStackTrace();
			}
		}
	}

}
