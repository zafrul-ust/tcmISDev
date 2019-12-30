package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Connection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.UsageMatlSubcategoryViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for AirReviewProcess
 * @version 1.0
 *****************************************************************************/
public class AirReviewProcess extends BaseProcess {
    
    Log log = LogFactory.getLog(this.getClass());
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;
    private EngEvalProcess engEvalProcess;
    private Collection usageMaterialSubcategoryColl = new Vector(0);

    public AirReviewProcess(String client) {
	    super(client);
    }

    public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

    public void setEngEvalProcess(EngEvalProcess process) {
        engEvalProcess = process;
    }

    public void getPlannedUseDetailForRequest(BigDecimal requestId) throws Exception {
        try {
            //todo need to get this view to CUSTOMER
            StringBuilder query = new StringBuilder("select * from catalog_add_planned_dtl_view where request_id = ").append(requestId);
            genericSqlFactory.setBeanObject(new UsageMatlSubcategoryViewBean());
            usageMaterialSubcategoryColl = genericSqlFactory.selectQuery(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // If the material description is not marked for transmission to VOCET,
    // the air review doesn't apply.
    boolean sendToVocet () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Y".equals(bean.getToVocet())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    boolean typeOfUsage (Vector typeOfUseTest) {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            for (int i = 0; i < typeOfUseTest.size(); i++) {
                if (typeOfUseTest.elementAt(i).equals(bean.getTypeOfUse())) {
                    testOk = true;
                    break;
                }
            }
        }
        return testOk;
    }

    boolean forNonProductionUsage () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Y".equals(bean.getShowForNonProd())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    boolean isSpray () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Spray".equals(bean.getUseName())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    boolean forProductionUsage () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Y".equals(bean.getShowForProd())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    // If the material description is "Stripper Material", it requires review.
    boolean stripperMaterial () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Stripper Materials".equals(bean.getMaterialSubcategoryName())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    // If the material is for facilities contractor use for non-production work,
    // check if the material is paint, wood coating or metal coating, then check
    // if the material has a VOC value. If the material has a VOC value, then
    // check if it is being sprayed.
    void facilitiesContractorUsage (BigDecimal requestId, String companyId) {
        try {
            //if it's for Facilities contractor
            Vector typeOfUseTest = new Vector(1);
            typeOfUseTest.add("Facilities Contractor Use Only");
            if (typeOfUsage(typeOfUseTest)) {
                // and it a non-production material
                if (forNonProductionUsage()) {
                    //if material on FA16 list
                    StringBuilder query = new StringBuilder("select PKG_LIST_APPROVAL.fx_matl_in_chem_list_in_list(").append(requestId).append(",'").append(companyId).append("','FA16;','Y') from dual");
                    String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
                    if ("Y".equalsIgnoreCase(tmpVal)) {
                        //todo has VOC
                        if (true) {
                            if (isSpray()) {
                                 //engEvalProcess.additionalNotificationMsg.append("Material has VOC values and is sprayed.\n");
                            }
                        }else {
                            //engEvalProcess.additionalNotificationMsg.append("Material does not have indexed VOC values.\n");
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean flexPermitRequired () {
        boolean testOk = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if ("Y".equals(bean.getFlexPermitRequired())) {
                testOk = true;
                break;
            }
        }
        return testOk;
    }

    // If the material is a composite, check for a VOC value. If there's
    // a VOC value, and the value is greater than zero, check the location. If the
    // location requires Flex Permit Review, check for ingredients on the FW FA20 Composite
    // list. Any ingredients on the list must be less than 1.5% of the material content.
    // Otherwise, manual review is required.
    boolean isComposites (StringBuilder stopMsg, StringBuilder continueMsg, boolean continueProcessing) {
        boolean result = false;
        Iterator iter = usageMaterialSubcategoryColl.iterator();
        while(iter.hasNext()) {
            UsageMatlSubcategoryViewBean bean = (UsageMatlSubcategoryViewBean)iter.next();
            if (bean.getMaterialSubcategoryName() != null) {
                if (bean.getMaterialSubcategoryName().startsWith("Composite")) {
                    //todo has VOC
                    if (true) {
                        //todo VOC greater than or equal to zero
                        if (true) {
                            //location requires flex permit
                            if (flexPermitRequired()) {
                                //todo If ingredients are on the list and have percentages greater than 1.5; the flex permit review should be performed.
                                //"FW FA20 Composite" list
                                if (true) {
                                    stopMsg.append("Composite material need manual flex permit review.\n");
                                    result = true;
                                    continueProcessing = true;
                                    break;
                                }else {
                                    continueMsg.append("Composite material.\n");
                                }
                            }else {
                                continueMsg.append("Composite material is used at a location that does not require flex permit review.\n");
                            }
                        }else {
                            continueMsg.append("Composite material has zero VOC.\n");
                        }
                    }else {
                       stopMsg.append("Composite material does not have indexed VOC values.\n");
                       break;
                    }
                }
            }
        }
        return result;
    }

    // The flex permit review is performed in two instances,
    // 1. When the material is a composite that has ingredients on list FA20 over 1.5%, OR
    // 2. When the material falls through all other evaluation conditions.
    //todo
    void flexPermitReview() {
        
    }

    void doMoreAirReviewTest(BigDecimal requestId) {
        try {
            StringBuilder stopMsg = new StringBuilder("");
            StringBuilder continueMsg = new StringBuilder("");
            if (forProductionUsage()) {
                if (stripperMaterial()) {
                    //engEvalProcess.additionalNotificationMsg.append("HMMP Mandatory Review.\n");
                }

                /*
                //material is composite
                if (engEvalProcess.additionalNotificationMsg.length() == 0) {
                    boolean continueProcessing = false;
                    if (isComposites(stopMsg,continueMsg,continueProcessing)) {
                        if (continueProcessing) {
                            //flex premit Review
                            //todo
                            //
                            flexPermitReview();
                        }else {
                            if (stopMsg.length() != 0) {
                                engEvalProcess.additionalNotificationMsg.append(stopMsg.toString());
                            }
                        }
                    }
                }


                //material is solvents
                if (engEvalProcess.additionalNotificationMsg.length() == 0) {
                    //todo
                }

                //material is Aerosol
                if (engEvalProcess.additionalNotificationMsg.length() == 0) {
                    //todo
                }
                */

            }else if (forNonProductionUsage()) {
                //todo
                //check bldg 420 and booth NNB
                if (true) {
                    //return
                }

                //material is solvents

                //material is composite

                //test for refrigerants

                //test for thermosetting

                //test for fuels

                //test for paint/coating

            }

            // If the material makes it through the above without being eliminated,
            // and is being used in a location that requires Flex Permit review,
            // it requires Flex Permit review.

            //location requires Flex permit
            //todo
            if (true)
            {
                flexPermitReview();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

} //end of class