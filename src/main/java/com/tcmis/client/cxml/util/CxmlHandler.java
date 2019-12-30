package com.tcmis.client.cxml.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CxmlHandler {

	private final static Random random = new Random(System.currentTimeMillis());

	public CxmlHandler() {
	}

	/*
	 * Returns a unique payloadid
	 */
	public static synchronized String getPayloadId() {
		return "" + System.currentTimeMillis() + random.nextLong() + "@www.tcmis.com";
	}

	public static synchronized String getTimeStamp(Date date) throws BaseException{

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);

		StringBuilder sb = new StringBuilder(DateHandler.formatDate(date,
				"yyyy-MM-dd'T'HH:mm:ss"));
		try {
			int rawzone = gc.get(Calendar.ZONE_OFFSET);
			if (rawzone < 0) {
				sb.append('-');
				rawzone *= -1;
			}
			else {
				sb.append('+');
			}
			int hours = rawzone / 3600000;
			int minutes = (rawzone / 60000) % 60;
			if (hours < 10) {
				sb.append('0');
			}
			sb.append("" + hours);
			sb.append(":");
			if (minutes < 10) {
				sb.append('0');
			}
			sb.append("" + minutes);
		}
		catch(Exception e) {
			System.out.println("CxmlHandler - Error getting timestamp from date:" + date);
			throw new BaseException(e);
		}
		return sb.toString();
	}

}