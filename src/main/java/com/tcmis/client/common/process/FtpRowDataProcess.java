package com.tcmis.client.common.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.RowDataBean;
import com.tcmis.client.common.factory.RowDataBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.ftp.FtpClient;
import com.tcmis.common.ftp.FtpsClient;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;


/******************************************************************************
 * Process for FtpRowDataProcess
 * @version 1.0
 *****************************************************************************/
public class FtpRowDataProcess extends BaseProcess {

  Log log = LogFactory.getLog(this.getClass());
  ResourceLibrary resourceLibrary = new ResourceLibrary("tcmISWebResource");
  
  public FtpRowDataProcess(String client) {
    super(client);
  }

	public boolean createRowDataFile(String dataKey) {
		boolean result = true;
		String tempPath = "";
		String fileName = "";
		String remoteFileName = ""; 
		try {
			DbManager dbManager = new DbManager(this.getClient());
			if (dataKey == null) {
				result = false;
				//send error email
				MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","FtpRowDataProcess.createRowDtaFile called without key","See log file,");
			} else {
				String errorMsg = "";
				//first get data from procedure
				RowDataBean configData = new RowDataBean();
				RowDataBeanFactory factory = new RowDataBeanFactory(dbManager);
				Collection rowData = factory.select(dataKey,configData);
				if (!rowData.isEmpty()) {
					
					if(!StringHandler.isBlankString(configData.getFtpServer())) {  
						tempPath = resourceLibrary.getString("saveltempreportpath"); 
					} else {
						tempPath = configData.getPath();  
					}
					fileName = configData.getFileName();
					//delete file from current directory
					File f = new File(tempPath+fileName);
					f.delete();
					File ftmp = new File(tempPath+fileName+".tmp"); 				
					ftmp.delete();//new
					//create file from data
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempPath+fileName+".tmp"));
					Iterator iterator = rowData.iterator();						
					while(iterator.hasNext()) {						
						RowDataBean bean = (RowDataBean)iterator.next();						
						bufferedWriter.write(bean.getRowData());  
						//if (this.getClient().equalsIgnoreCase("FNC") || this.getClient().equalsIgnoreCase("USGOV") || this.getClient().equalsIgnoreCase("BOEING")) {
						if (!StringHandler.isBlankString(configData.getDosFormat()) && configData.getDosFormat().trim().equalsIgnoreCase("Y")) {
							bufferedWriter.write("\r\n");							
						} else {
							bufferedWriter.newLine();							
						}	
					} 
					bufferedWriter.close();					
					boolean fileRename = ftmp.renameTo(f); //new
					if (fileRename) {	
						// Comment out encryption
						//check for the encryption required flag and if it is Y then use the public key to encrypt the file
						if(isEncryptionRequired(configData)) {
							if (encryptFile(tempPath, configData, fileName)) {
								//delete the .txt file after encryption process is completed
								f = new File(tempPath+fileName);
								f.delete();
								//add the .gpg extention to the filename
								remoteFileName = fileName;
								fileName = fileName + ".gpg";						
							} else {
								errorMsg += "Error occurred while encrypting the file. ";
							}
						}
						
						boolean ftpCompletionResult = false;
						if (errorMsg.length() <= 0  ) {
							if(!StringHandler.isBlankString(configData.getFtpServer())) {
								String ftpType = configData.getFtpType();
								//log.debug("ftpType = " + ftpType);
								if (!StringHandler.isBlankString(ftpType) && 
										ftpType.trim().equalsIgnoreCase("FTP")) {
									//ftp file to it final destination
									ftpCompletionResult = ftpFile(tempPath, configData, fileName);
								} else if (!StringHandler.isBlankString(ftpType) && 
										ftpType.trim().equalsIgnoreCase("FTPS")) {
									//ftps file to it final destination
									log.debug("calling ftps ");
									ftpCompletionResult = ftpsFile(tempPath, configData, fileName, remoteFileName);
								}
								if (ftpCompletionResult) {
									//delete file from current directory
									f = new File(tempPath+fileName);
									f.delete();		
								} else {
									log.debug("Error in ftp or ftps");
									errorMsg += "Error occurred while ftp/ftps file.";
								}							
							} else {							
								//Change the group to the user name and give read/write permissions on that file for that group
								//this is required because tomcat runs as root in production							
								Runtime.getRuntime().exec("chgrp " + configData.getFtpUserName() + " " + tempPath+fileName);							
								Runtime.getRuntime().exec("chmod g+rw " + tempPath+fileName);							
							}
						}
					} else {
						errorMsg += "File rename failed -> " + ftmp.getAbsolutePath();
					}
				} else {
					errorMsg += "No data found from query. ";
				}
				//call complete procedure
				markDataForCompletion(dbManager,dataKey,errorMsg);
				if (errorMsg.length() > 0) {
					result = false;
				}
			}
		}catch (Exception e) {
			result = false;			
			log.error(e);						
			//send error email
			MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Error occurred while trying to create file "+dataKey,"See log file,");
		}
		return result;
	}
	
	/**
	 * Read the input and output error stream of the Runtime command executed. It tells us if the command was successfully executed
	 * ot if there were any issues in the execution. For e.g: if encryption failed then why it failed can be found by reading the error stream.
	 * @param in
	 * @param err
	 * @throws IOException
	 */
	private void forwardStreamtoStd(InputStream in, InputStream err) 
	throws IOException {
	    int c = -1;
	    BufferedReader inReader = new BufferedReader(
	        new InputStreamReader(in, "US-ASCII"));
	    BufferedReader errReader = new BufferedReader(
	        new InputStreamReader(err, "US-ASCII"));
	    
	    boolean inFinished = false, errFinished = false;
	    String stmp = "";
	    try {
	        if (!inFinished) {
	            while ((c = inReader.read()) != -1) {
	            		stmp += ((char) c);	                    
	            }
	            log.debug("input stream = " + stmp );
	            inFinished = true;
	        }
	        stmp = "";
	        if (!errFinished) {
	            while ((c = errReader.read()) != -1) {
	            	stmp += ((char) c);                    
	            }
	            log.error("Error = " + stmp );
	            errFinished = true;
	        }	        		
	    } 
	    catch (IOException e) {
	        throw e;
	    } 
	    finally {
	        errReader.close();
	        inReader.close();
	    }
	}
	
	
	private void markDataForCompletion(DbManager dbManager,String dataKey, String msg) {
		try {
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection cin = new Vector(3);
			cin.add(dataKey);
			if (msg.length() > 0) {
				cin.add("ERROR");
			}else {
				cin.add("SENT");
			}
			cin.add(msg);
			procFactory.doProcedure("pkg_ftp_outbound.p_ftp_outbound_sent", cin);
		} catch (Exception e) {
			log.error(e);			
			//send error email
			MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Error occurred while try to mark data for completion","See log file,");
		}
	}

	private boolean ftpFile(String localPath, RowDataBean configData, String fileName) {
		//log.debug("ftpFile - localPath = " + localPath + " fileName = " + fileName);
		boolean result = true;		
		try {			
			FtpClient ftpClient = new FtpClient(configData.getFtpServer(),configData.getFtpUserName(),configData.getFtpPassword());
			//change diretory
			if (configData.getPath() != null) {
				ftpClient.cd(configData.getPath());
			}			
			FileInputStream in = new FileInputStream(localPath+fileName);
			//ftpClient.put(configData.getFileName(),in,false);
			ftpClient.put(fileName,in,false);
			in.close();
			ftpClient.disconnect();			 
			//log.debug("done ftpFile. result = " + result);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			log.error(e);			
			//send error email
			MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Error occurred while ftp file "+localPath+fileName,"See log file,");
		}
		return result;
	}

	/**
	 * Encrypt the contents of the file using GPG encryption. 
	 * @param localPath
	 * @param configData
	 * @param fileName
	 * @return boolean result
	 */
	private boolean encryptFile(String localPath, RowDataBean configData, String fileName) {
		//log.debug("encryptFile - localPath = " + localPath + " fileName = " + fileName);

		boolean result = true;
		String strErr = "";
		try {
			if (StringHandler.isBlankString(configData.getEncryptionPublicKey())) {
				result = false;
				strErr = " with empty public key - " + configData.getEncryptionPublicKey();
				//log.debug("done. empty key. result = " + result);
				throw new Exception();
			}
			String keyringboeing = resourceLibrary.getString("keyringboeing");
			String keyringhomedir = resourceLibrary.getString("keyringhomedir");
			//Process process = new ProcessBuilder("gpg", "--always-trust", "--recipient", configData.getEncryptionPublicKey(),"--encrypt", localPath+fileName).start();
			Process process = new ProcessBuilder("gpg", "--homedir", keyringhomedir, 
														"--no-default-keyring", 
														"--keyring", keyringboeing, 
														"--recipient", configData.getEncryptionPublicKey(),
														"--batch",
														"--out", localPath+fileName+".gpg",
														"--encrypt", localPath+fileName
												).start();
			log.debug("gpg --homedir " + keyringhomedir 
						+ " --no-default-keyring "
						+ " --keyring " + keyringboeing 
						+ " --recipient " + configData.getEncryptionPublicKey() 
						+ " --batch " 
						+ " --out " + localPath+fileName+".gpg" 
						+ " --encrypt " + localPath+fileName);
			
			//Wait to get exit value
			int exitStatus  = process.waitFor();
			forwardStreamtoStd(process.getInputStream(),process.getErrorStream());			
			if (exitStatus != 0 ) {
				log.debug("inside exitstatus != 0");
				//forwardStreamtoStd(process.getInputStream(),process.getErrorStream());
				result = false;
			}		
			log.debug("encryption result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			log.error(e);
			//send error email
			MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Error occurred while encrypting the file - " + localPath+fileName + strErr ,"See log file,");
		}		
		return result;
	}
	
	/**
	 * Check the flag in the database which determines if we have to ftps the client server.
	 * @param configData
	 * @return boolean flag
	 */
	private boolean isEncryptionRequired(RowDataBean configData) {
		boolean required = false;
		if (!StringHandler.isBlankString(configData.getEncryptionRequired()) 
				&& configData.getEncryptionRequired().equalsIgnoreCase("Y")) {
			required = true;
		}
		log.debug("isEncryptionRequired - required = " + required );
		return required; 
	}
	
	/**
	 * ftps the file to the client server.
	 * @param localPath
	 * @param configData
	 * @param fileName
	 * @return boolean result
	 */	
	private boolean ftpsFile(String localPath, RowDataBean configData, String fileName, String remoteFileName) {
		log.debug("ftpsFile - localPath = " + localPath + " fileName = " + fileName + ", remotefilename = " + remoteFileName);
		boolean result = true;		
		try {
			FtpsClient ftpsClient = new FtpsClient(configData.getFtpServer(),configData.getFtpUserName(),configData.getFtpPassword());
			log.debug("initialize ftpsClient done");
			//change diretory			
			if (configData.getPath() != null) {
				ftpsClient.cd(configData.getPath());
			}
			FileInputStream in = new FileInputStream(localPath+fileName);
			//ftpClient.put(configData.getFileName(),in,false);
			log.debug("ftpsclient put");
			result = ftpsClient.put(remoteFileName,in,false);
			in.close();			
			ftpsClient.disconnect();	
			log.debug("logout & disconnect ftpsFile. result = " + result);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			log.error(e);			
			//send error email
			MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Error occurred while ftps file "+localPath+fileName,"See log file,");
		}
		return result;
	}
	
} //end of class