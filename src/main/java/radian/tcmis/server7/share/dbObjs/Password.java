// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Password.java

package radian.tcmis.server7.share.dbObjs;

import Acme.Crypto.EncryptedOutputStream;
import Acme.Crypto.Rc4Cipher;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.tcmis.common.util.EncryptHandler;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

// Referenced classes of package com.tcmis.server7.share.dbObjs:
//            TcmISDBModel

public class Password {

	public Password(TcmISDBModel db, String logon, String presented) throws Exception {
		presentedPassword = null;
		logonID = null;
		pID = null;
		filePassword = null;
		expDate = null;
		status = "";
		this.db = db;
		logonID = logon;
		loadInfo();
		presentedPassword = encrypt(presented);
	}

	public Password(TcmISDBModel db, String logon, String presented, String aribaLogon) throws Exception {
		presentedPassword = null;
		logonID = null;
		pID = null;
		filePassword = null;
		expDate = null;
		status = "";
		this.db = db;
		logonID = logon;
		if (aribaLogon.length() > 1) loadInfoForRadian(aribaLogon);
		else
			loadInfo();
		if ("Seagate".equalsIgnoreCase(db.getClient())) presentedPassword = presented;
		else
			presentedPassword = encrypt(presented);
	}

	public boolean setInitialPassword(String newWord) throws Exception {
		if (resetPassword(newWord)) {
			removeFromStatus(RESET);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isAuthenticated() throws Exception {
		return passwordsMatch() && !isExpired() && !isLocked() && !isReset() && !isDeveloper();
	}

	public boolean isAuthenticatedWeb() throws Exception {
		return passwordsMatch();
	}

	public boolean isAuthenticatedWeb(String userGroup) throws Exception {
		// first check to see if password match if match then check to see if
		// user belong to user group
		if (passwordsMatch()) {
			try {
				return (HelpObjs.countQuery(db, "select count(*) from user_group_member where user_group_id = '" + userGroup + "' and personnel_id = " + this.getUserId()) > 0);
			}
			catch (Exception e) {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public String getUserId() throws Exception {
		return pID;
	}

	public void setUserId(String userId) {
		pID = userId;
	}

	public String checkForValidUser() throws Exception {
		if (isExpired()) return "1";
		if (isReset()) return "2";
		if (isLocked()) return "3";
		else
			return "0";
	}

	public String getAuthStatus() throws Exception {
		if (isAuthenticated()) return "0";
		if (isExpired()) return "1";
		if (isReset()) return "2";
		if (isLocked()) return "3";
		if (passwordsMatch() && isDeveloper()) return "99";
		else
			return "4";
	}

	public boolean changePassword(String newWord) throws Exception {
		filePassword = "";
		new_encryption_used = "Y";
		String mud = encrypt(newWord);
		filePassword = new String(mud);
		presentedPassword = new String(mud);
		// setNotReset();
		setExpirationDate();
		updateTable();
		// loadInfo();
		return true;
	}

	public boolean setExpirationDate() {
		return setExpirationDate(60);
	}

	public boolean setExpirationDate(int i) {
		GregorianCalendar newDate = new GregorianCalendar();
		newDate.add(6, i);
		return setExpirationDate(newDate);
	}

	public boolean setExpirationDate(GregorianCalendar g) {
		expDate = g;
		return true;
	}

	public static boolean logonExists(TcmISDBModel db, String logon) throws Exception {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String query = "select logon_id from personnel where lower(logon_id) = lower('" + HelpObjs.singleQuoteToDouble(logon) + "')";
		boolean exists = false;
		try {
			dbrs = db.doQuery(query);
			rs = dbrs.getResultSet();
			if (rs.next()) exists = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			HelpObjs.monitor(1, "Error object(Password): ".concat(String.valueOf(String.valueOf(e.getMessage()))), null);
			exists = false;
		}
		finally {
			dbrs.close();
		}
		return exists;
	}

	public boolean resetPassword(String newPW) throws Exception {
		//filePassword = new String(newPW);
		//presentedPassword = new String(newPW);
		changePassword(newPW);
		setReset();
		updateTable();
		loadInfo();
		return true;
	}

	public boolean isExpired() throws Exception {
		return false;
	}

	public String getStatus() {
		return status;
	}

	public boolean isLocked() {
		return status.indexOf(LOCKED) >= 0;
	}

	public boolean isReset() {
		return status.indexOf(RESET) >= 0;
	}

	public boolean isNonExpiring() {
		return status.indexOf(NOEXPIRE) >= 0;
	}

	public boolean isDeveloper() {
		boolean result = false;
		try {
			result = HelpObjs.countQuery(db,
					"select count(*) from user_group_member where user_group_id = 'AlternateLogon' and personnel_id = ".concat(String.valueOf(String.valueOf(pID)))) > 0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setNonExpiring() {
		if (isNonExpiring()) {
			return;
		}
		else {
			addToStatus(NOEXPIRE);
			return;
		}
	}

	public void setLocked() {
		if (isLocked()) {
			return;
		}
		else {
			addToStatus(LOCKED);
			return;
		}
	}

	public void setReset() {
		if (isReset()) {
			return;
		}
		else {
			addToStatus(RESET);
			return;
		}
	}

	private void setNotReset() {
		removeFromStatus(RESET);
	}

	private void setNotLocked() {
		removeFromStatus(LOCKED);
	}

	private void setExpires() {
		removeFromStatus(NOEXPIRE);
	}

	private boolean passwordsMatch() {
		if (presentedPassword.equals("")) return false;
		else
			return presentedPassword.equals(filePassword);
	}

	private void loadInfo() throws Exception {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String query = "select personnel_id, password, status, to_char(password_expire_date,'yyyymmdd'), new_encryption_used from personnel where lower(logon_id) = lower('"
				+ HelpObjs.singleQuoteToDouble(logonID) + "')";
		try {
			dbrs = db.doQuery(query);
			for (rs = dbrs.getResultSet(); rs.next(); decodeExpDate(rs.getString(4))) {
				pID = rs.getString("personnel_id");
				filePassword = rs.getString("password");
				status = rs.getString("status");
				new_encryption_used = rs.getString("new_encryption_used");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			HelpObjs.monitor(1, "Error object(" + getClass().getName() + "): " + e.getMessage(), null);
		}
		finally {
			dbrs.close();
		}
		if (filePassword == null) filePassword = new String("");
		if (status == null) status = new String("");
		if (expDate == null) expDate = new GregorianCalendar(1980, 1, 1);
	}

	private void loadInfoForRadian(String CompanyId) throws Exception {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String query = "select personnel_id, password, status, to_char(password_expire_date,'yyyymmdd') , new_encryption_used from personnel where lower(logon_id) = lower('"
				+ HelpObjs.singleQuoteToDouble(logonID) + "')  and company_id = '" + CompanyId + "'";
		try {
			dbrs = db.doQuery(query);
			for (rs = dbrs.getResultSet(); rs.next(); decodeExpDate(rs.getString(4))) {
				pID = rs.getString("personnel_id");
				filePassword = rs.getString("password");
				status = rs.getString("status");
				new_encryption_used = rs.getString("new_encryption_used");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			HelpObjs.monitor(1, "Error object(" + getClass().getName() + "): " + e.getMessage(), null);
		}
		finally {
			dbrs.close();
		}
		if (filePassword == null) filePassword = new String("");
		if (status == null) status = new String("");
		if (expDate == null) expDate = new GregorianCalendar(1980, 1, 1);
	}

	private void decodeExpDate(String in) {
		try {
			int year = Integer.parseInt(in.substring(0, 4));
			int month = Integer.parseInt(in.substring(4, 6)) - 1;
			int day = Integer.parseInt(in.substring(6));
			expDate = new GregorianCalendar(year, month, day);
		}
		catch (Exception e) {
			expDate = new GregorianCalendar(1980, 1, 1);
		}
	}

	private void addToStatus(String s) {
		if (status == null) status = new String(s);
		else
			status = status.concat(s);
		updateTable();
	}

	private void removeFromStatus(String s) {
		char c[] = status.toCharArray();
		String out = new String("");
		for (int i = 0; i < Array.getLength(c); i++)
			if (!s.equals(String.valueOf(c[i]))) out = out.concat(String.valueOf(c[i]));

		status = new String(out);
		updateTable();
	}

	private void updateTable() {
		StringBuffer sb = new StringBuffer();
		String year = String.valueOf(expDate.get(1));
		String month = String.valueOf(expDate.get(2) + 1);
		String day = String.valueOf(expDate.get(5));
		if (month.length() == 1) month = new String("0".concat(String.valueOf(String.valueOf(month))));
		if (day.length() == 1) day = new String("0".concat(String.valueOf(String.valueOf(day))));
		String myDate = String.valueOf(year) + month + day;
		try {
			Hashtable inData = new Hashtable(3);
			inData.put("USER_ID", new Integer(pID));
			inData.put("PASSWORD", filePassword);
			Personnel personnel = new Personnel(db);
			personnel.updateUserPersonInfo(inData, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			HelpObjs.monitor(1, "Error object(" + getClass().getName() + "): " + e.getMessage(), null);
		}
	}

	public String encrypt(String clear) {
		String mud = null;
		try {
			if ("Y".equals(new_encryption_used)) {
				byte[] salt = extractSalt(filePassword);
				mud = EncryptHandler.pbkdf2Encrypt(clear, salt);
			}
			else {
				Rc4Cipher rc = new Rc4Cipher("h0wminir08dsmuchstamAInwALkdown");
				PipedInputStream pis = new PipedInputStream();
				PipedOutputStream pos = new PipedOutputStream(pis);
				EncryptedOutputStream eos = new EncryptedOutputStream(rc, pos);
				byte c[] = clear.getBytes();
				eos.write(c);
				eos.flush();
				eos.close();
				int in = 0;
				StringBuffer sb = new StringBuffer();
				do {
					in = pis.read();
					if (in == -1) break;
					sb.append(String.valueOf(in));
				} while (true);
				mud = sb.toString();
				pis.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			HelpObjs.monitor(1, "Error object(Password): ".concat(String.valueOf(String.valueOf(e.getMessage()))), null);
		}
		return mud;
	}

	public byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	public String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}

	public byte[] extractSalt(String hashString) throws Exception {
		byte[] ret = null;
		try {
			if (hashString != null && hashString.length() > 8) {
				byte[] salt = new byte[8];
				byte[] hash = fromHex(hashString);
				for (int i = 0; i < salt.length; i++)
					salt[i] = hash[i];

				ret = salt;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}

	public byte[] generateSalt() throws Exception {
		byte[] ret = null;
		try {
			// Generate a random salt
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[8];
			random.nextBytes(salt);
			ret = salt;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}

	public String pbkdf2Encrypt(String clear, byte[] salt) throws Exception {
		String mud = null;
		try {
			char[] c = clear.toCharArray();

			if (salt == null) salt = generateSalt();

			// Compute password hash
			PBEKeySpec spec = new PBEKeySpec(c, salt, 1000, 192);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();

			// Create array which will hold hash and salt bytes.
			byte[] hashWithSalt = new byte[hash.length + salt.length];
			for (int i = 0; i < salt.length; i++)
				hashWithSalt[i] = salt[i];

			for (int i = 0; i < hash.length; i++)
				hashWithSalt[salt.length + i] = hash[i];

			// Convert result into a base64 string.
			mud = toHex(hashWithSalt);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mud;
	}

	public static boolean checkPassword(TcmISDBModel myDb, String logonId, String pWord) throws Exception {
		Password pw = new Password(myDb, logonId, pWord);
		return pw.isAuthenticated();
	}

	public static boolean checkPasswordWeb(TcmISDBModel myDb, String logonId, String pWord) throws Exception {
		Password pw = new Password(myDb, logonId, pWord);
		return pw.isAuthenticatedWeb();
	}

	protected TcmISDBModel			db;
	protected String				presentedPassword;
	protected String				logonID;
	protected String				pID;
	protected String				filePassword;
	protected GregorianCalendar		expDate;
	protected String				status;
	protected String				new_encryption_used;
	protected static final String	LOCKED		= new String("L");
	protected static final String	RESET		= new String("R");
	protected static final String	NOEXPIRE	= new String("E");
	protected static final String	NEWPASSWORD	= new String("N");
	protected static final int		DURATION	= 60;
	/*
	 * static { DURATION = 60; }
	 */
}
