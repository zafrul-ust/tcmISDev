package com.tcmis.client.catalog.process;

import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.client.catalog.beans.ManageCatAddSDSInputBean;

import java.util.Collection;
import java.util.Vector;

public class ManageCatAddSDSProcess  extends GenericProcess {
	public ManageCatAddSDSProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Vector<FileUploadBean> getSearchResult(ManageCatAddSDSInputBean inputBean, String requestURL) throws Exception {
		Vector<FileUploadBean> resultCollection = new Vector<FileUploadBean>();
		
		if (inputBean.getRequestId() != null) {
			String resultStr = new EngEvalProcess(getClient(), getLocale(), requestURL.substring(0, requestURL.lastIndexOf("/"))).getUserUploadedMsdsForRequest(inputBean.getRequestId());
			
			if (!StringHandler.isBlankString(resultStr)) {
				String[] resultArr = resultStr.split("\n");
				for (String path : resultArr) {
					FileUploadBean newBean = new FileUploadBean();
					newBean.setFilename(path);
					newBean.setLocation(path);
					resultCollection.addElement(newBean);
				}
			}
		}
		log.info("Finished retrieving search result");
		
		return resultCollection;
	}
	
	public void deleteUserUploadedMsds(Collection<FileUploadBean> coll, String requestURL) throws Exception {
		String uploadFilePath = new ResourceLibrary("uploadFile").getString("file.path");
		String URL = requestURL.substring(0, requestURL.indexOf("tcmIS"));
		for (FileUploadBean bean : coll) {
			String relativeFilePath = bean.getLocation().substring(URL.length());
			FileHandler.delete(uploadFilePath + relativeFilePath);
			log.info("File deleted from system:" + uploadFilePath + relativeFilePath);
		}
	}
}