package com.tcmis.internal.fileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.tcmis.common.admin.process.MailProcess;


public class UploadRequest {
	
	private String docPath;
	
	public class FileDesc{
		public String fileName;
		public File file;
		
		@Override
		public String toString() {
			return "FileDesc [fileName=" + fileName + ", file=" + file + "]";
		}				
	}
	
	private String fileType;	
	private List<FileDesc> streams = new ArrayList<FileDesc>();
			
	
	public UploadRequest(String dPath) {
						
		this.docPath = dPath;
	}

	public void processFormField(FileItemStream fItem) throws IOException {
				
		String fieldName = fItem.getFieldName().trim();		
		if (fieldName.equals("barCodeType")){
			InputStream stream = fItem.openStream();
			fileType = Streams.asString(stream);
		}
	}

	public String getFileType() {
		return fileType;
	}
		
	public FileDesc getStream(int paramInt) {
		
		if (streams.size() > 0)
			return streams.get(paramInt);
		
		return null;
	}

	public void addStream(FileItemStream stream) throws IOException,Exception {
						
		//File f = File.createTempFile("SDC", ".tmp");
		File f = new File( new File( docPath ),stream.getName());
		
		OutputStream  output = new FileOutputStream(f);
		long fileSize = IOUtils.copyLarge(stream.openStream(), output);
		
		output.flush();
		output.close();
		
		if(!f.exists() || fileSize == 0)
		{
			StringBuilder fName = new StringBuilder(docPath).append(stream.getName());
			MailProcess.sendEmail("deverror@haastcm.com", "", "donotreplay@haastcm.com", "Scanned Document Process Error", "Error writing incoming doc WS1: " + fName);
			throw new Exception("Error writing incoming scanned document: " + fName);
		}
		
		FileDesc node = new FileDesc();
		node.file = f;
		node.fileName = stream.getName();
				
		this.streams.add(node);	
		//f.deleteOnExit();
	}
}
