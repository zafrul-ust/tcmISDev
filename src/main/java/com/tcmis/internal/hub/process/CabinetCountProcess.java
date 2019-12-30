package com.tcmis.internal.hub.process;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.CabinetInventoryCountStageBeanFactory;
import com.tcmis.internal.hub.factory.CabinetMrCreateViewBeanFactory;
import com.tcmis.internal.hub.factory.PickViewBeanFactory;

/******************************************************************************
 * Process for Receipt Document
 * 
 * @version 1.0
 *****************************************************************************/

public class CabinetCountProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CabinetCountProcess(String client, Locale locale) {
		super(client, locale);
	}

	/* Need this for some old code that is dependent */
	public CabinetCountProcess(String client) {
		super(client);
	}

	public String doUpload(InventoryCountScanInputBean bean, BigDecimal personnelId) throws BaseException {
		ResourceLibrary messages = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		Vector<CabinetInventoryCountStageBean> countByReceiptBeans = new Vector<CabinetInventoryCountStageBean>();
		Collection<CabinetItemInventoryCountBean> countByItemBeans = new Vector<CabinetItemInventoryCountBean>();
		Vector binIdsScanned = new Vector();
		String mrsCreated = "";
        String companyId = "";
        String errorMessage = "";

        if (bean.getTheFile() != null && bean.getTheFile().length() > 0) {
            DbManager dbManager = new DbManager(getClient());
            Connection connection = null;
            try {
                connection = dbManager.getConnection();
                BigDecimal nextupLoadSeq = getNextUpLoadSeq(connection);

                // copy file to server
                File f = bean.getTheFile();
                String fileType = f.getName().substring(f.getName().lastIndexOf("."));
                bean.setFileName(fileType);
                ResourceLibrary resource = new ResourceLibrary("scannerupload");

                File dir = new File(resource.getString("upload.serverpath"));
                File file = File.createTempFile("inventorycount-", "" + fileType + "", dir);

                FileHandler.copy(bean.getTheFile(), file);
                bean.setFileName(file.getName());

                // reading the file and storing the data in a
                // bean
                BufferedReader in = new BufferedReader(new FileReader(file));
                String ln = new String();
                StringTokenizer st = null;
                while ((ln = in.readLine()) != null) {
                    st = new StringTokenizer(ln, ",");

                    if (st.countTokens() > 1) {

                        int loopcount = 0;
                        CabinetInventoryCountStageBean scannedBean = new CabinetInventoryCountStageBean();
                        while (st.hasMoreTokens()) {
                            loopcount++;
                            String tok = st.nextToken();
                            String tokenValue = "";

                            tokenValue = tok == null ? "" : tok.trim();
                            if (loopcount == 1) {
                                BigDecimal binId = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(tokenValue);
                                scannedBean.setBinId(binId);
                                if (!binIdsScanned.contains(tokenValue)) {
                                    binIdsScanned.add(tokenValue);
                                }
                            }
                            else if (loopcount == 2) {
                                BigDecimal receiptId = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(tokenValue);
                                scannedBean.setReceiptId(receiptId);
                            }
                            else if (loopcount == 3) {
                                BigDecimal quantity = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(tokenValue);
                                scannedBean.setCountQuantity(quantity);
                            }
                            else if (loopcount == 4) {
                                scannedBean.setCountDatetime(com.tcmis.common.util.DateHandler.getDateFromString("yyyy/MM/dd HH:mm", tokenValue));
                            }
                            else if (loopcount == 5) {
                                BigDecimal scanPersonnelId = com.tcmis.common.util.NumberHandler.zeroBigDecimalIfNull(tokenValue);
                                scannedBean.setPersonnelId(scanPersonnelId);
                            }
                            else if (loopcount == 6) {
                                companyId = tokenValue;
                                scannedBean.setCompanyId(companyId);
                            }
                            else if (loopcount == 7) {
                                scannedBean.setCountType(tokenValue);
                            }
                        }
                        //updating upload sequence for each beans
                        scannedBean.setUploadSequence(nextupLoadSeq);
                        //process count by receipt
                        if (scannedBean.isCountByReceipt()) {
                            countByReceiptBeans.add(scannedBean);
                        }else if (scannedBean.isCountByItem()){
                            //process count by item
                            CabinetItemInventoryCountBean countByItemBean = new CabinetItemInventoryCountBean();
                            BeanHandler.copyAttributes(scannedBean, countByItemBean);
                            countByItemBean.setItemId(scannedBean.getReceiptId());
                            countByItemBean.setCountQuantity(scannedBean.getCountQuantity());
                            countByItemBean.setCountDatetime(scannedBean.getCountDatetime());
                            countByItemBeans.add(countByItemBean);
                        }
                    }
                }
                in.close();

                //processing data
                // Insert count by item counts
                if (!countByItemBeans.isEmpty()) {
                    try {
                        com.tcmis.ws.scanner.process.ManualCabinetCountProcess countByItemProcess = new com.tcmis.ws.scanner.process.ManualCabinetCountProcess(getClient());
                        countByItemProcess.setConnection(connection);
                        mrsCreated = countByItemProcess.doCabinetItemInventoryCountInserts(countByItemBeans, personnelId,nextupLoadSeq);
                    } catch (Exception byItemEx) {
                        errorMessage += byItemEx.getMessage();
                    }
                }
                //processing count by receipt
                if (!countByReceiptBeans.isEmpty()) {
                    com.tcmis.ws.scanner.process.CabinetCountProcess cProcess = new com.tcmis.ws.scanner.process.CabinetCountProcess(getClient());
                    cProcess.setConnection(connection);
                    String[] receiptCountResult = cProcess.processReceiptCount(binIdsScanned,countByReceiptBeans,personnelId,nextupLoadSeq);
                    mrsCreated += receiptCountResult[0];
                    errorMessage += receiptCountResult[1];
                }

                Vector inArgs = new Vector(1);
                inArgs.add(nextupLoadSeq);
                Vector outArgs = new Vector(1);
                outArgs.add(new Integer(java.sql.Types.VARCHAR));
                GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new CabinetItemInventoryCountBean());
                Vector error = (Vector) genericSqlFactory.doProcedure(connection,"pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs);
                if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                    MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","ManualCabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:"+nextupLoadSeq,(String)error.get(0));
                }
                error = (Vector) genericSqlFactory.doProcedure(connection,"pkg_cabinet_count.p_process_cabinet_rcpt_inv_ct", inArgs, outArgs);
                if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                    MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","ManualCabinetCountProcess Error encountered while calling pkg_cabinet_count.p_process_cabinet_rcpt_inv_ct:"+nextupLoadSeq,(String)error.get(0));
                }

                String wwwHome = resource.getString("upload.hosturl");
                String resultUrl = wwwHome + resource.getString("upload.cabinetcountresult");
                String message = messages.getString("letter.cabinetscannerupload") + resultUrl + "UserAction=Search&mrslist=" + mrsCreated + "\n\n\n" +  messages.getString("label.uploadsequence") + " : " + nextupLoadSeq + "\n\n\n" + messages.getString("label.personnelid") + " : " + personnelId;
                BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
                bulkMailProcess.sendBulkEmail(personnelId, messages.getString("letter.cabinetscanneruploadtitle"), message, true);
                bulkMailProcess.sendBulkEmail(new BigDecimal("86405"), "Cabinet Scanner Upload Results", message + "\n\nScanned Bin Ids" + binIdsScanned.toString(), true);
                if (log.isDebugEnabled()){
                    log.debug(message);
                }
            }catch (Exception e) {
                BaseException be = new BaseException("Can't put document file on server:" + e.getMessage());
                be.setRootCause(e);
                e.printStackTrace();
                throw be;
            }finally {
                dbManager.returnConnection(connection);
            }
            return errorMessage;
		}
		else {
			return messages.getString("label.nofile");
		}
	} //end of method

    private BigDecimal getNextUpLoadSeq(Connection connection) throws BaseException {
        BigDecimal b = null;
        try {
            b = new BigDecimal("" +new SqlManager().getOracleSequence(connection, "upload_sequence"));
        }catch(Exception e) {
            log.error(e);
        }
        return b;
   } //end of method

   //the following method is only by the SWING GUI code 
   public HashMap createMaterialRequest(Collection binIdsScanned, String companyId, BigDecimal personnelId) throws Exception {
		boolean createMrSuccess = true;
		String errorMessage = "";
		DbManager dbManager = new DbManager(getClient());

		SearchCriteria criteria = new SearchCriteria();

		String binIdsScannedList = "";
		int count = 0;
		Iterator binIdsScannedIterator = binIdsScanned.iterator();
		while (binIdsScannedIterator.hasNext()) {
			String binId = (String) binIdsScannedIterator.next();
			if (count > 0) {
				binIdsScannedList += ",";
			}

			binIdsScannedList += "'" + binId + "'";
			count++;
		}

		/* creating the MRs */
		CabinetMrCreateViewBeanFactory cabinetMrCreateViewBeanFactory = new CabinetMrCreateViewBeanFactory(dbManager);
		Collection resultColl = null;
		criteria = new SearchCriteria();
        criteria.addCriterion("binId", SearchCriterion.IN, binIdsScannedList, "");
        criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId, "");

        Collection cabinetMrCreateViewBeanCollection = cabinetMrCreateViewBeanFactory.select(criteria);
		String createdMrList = "";
		count = 0;

		Iterator cabinetMrCreateViewIterator = cabinetMrCreateViewBeanCollection.iterator();
		while (cabinetMrCreateViewIterator.hasNext()) {
			CabinetMrCreateViewBean cabinetMrCreateViewBean = (CabinetMrCreateViewBean) cabinetMrCreateViewIterator.next();
			try {
				resultColl = cabinetMrCreateViewBeanFactory.createMaterialRequest(cabinetMrCreateViewBean, personnelId,"");
			}
			catch (Exception ex4) {
				createMrSuccess = false;
				ex4.printStackTrace();
				errorMessage += ex4.getMessage();
			}

			if (resultColl != null) {
				Iterator resultIterator = resultColl.iterator();
				int resultCount = 0;
				BigDecimal prNumber = null;
				String lineItem = "";
				String errorCode = "";
				while (resultIterator.hasNext()) {
					if (resultCount == 0) {
						prNumber = (BigDecimal) resultIterator.next();
						;
					}
					else if (resultCount == 1) {
						lineItem = (String) resultIterator.next();
						;
					}
					else if (resultCount == 2) {
						errorCode = (String) resultIterator.next();
						;
						if (errorCode == null) {
							errorCode = "";
						}
					}
					resultCount++;
				}

				if (errorCode.length() > 0) {
					log.info("errorCode :" + errorCode + "");
					errorMessage += errorCode;
					createMrSuccess = false;
				}
				else {
					if (count > 0) {
						createdMrList += ",";
					}

					createdMrList += "" + prNumber + "";
					count++;
					lineItemAllocate(cabinetMrCreateViewBean.getCompanyId(), prNumber, lineItem);
				}
				log.info("MR is created " + prNumber + "-" + lineItem + "");
			}
		}

		HashMap result = new HashMap();
		result.put("CREATEDMRLIST", createdMrList);
		result.put("ERROR", errorMessage);
		result.put("SUCESS", new Boolean(createMrSuccess));
		return result;
	}

	private void lineItemAllocate(String companyId, BigDecimal prNumber, String lineItem) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PickViewBeanFactory factory = new PickViewBeanFactory(dbManager);
		Vector args = new Vector(3);
		args.add(companyId);
		args.add(prNumber);
		args.add(lineItem);
		try {
			factory.lineItemAllocate(args);
			if (log.isDebugEnabled()) {
				log.debug("called p_line_item_allocate for (companyId,pr,line): " + companyId + "," + lineItem + "," + lineItem);
			}
		}
		catch (BaseException be2) {
			log.error("BaseException calling p_line_item_allocate(" + companyId + "," + lineItem + "," + lineItem + "): " + be2);
		}
	} //end of method

} //end of class
