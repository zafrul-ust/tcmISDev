package com.tcmis.client.common.process;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Vector;

import com.tcmis.client.common.beans.MsdsLocaleViewBean;

import com.tcmis.client.common.beans.RevisionComparisionBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;


public class RevisionComparisionProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

    public RevisionComparisionProcess(String client) {
        super(client);
    }

    public RevisionComparisionProcess(String client, String locale) {
        super(client, locale);
    }


    public Collection getVersions(BigDecimal materialId) throws
            BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new RevisionComparisionBean());

        StringBuilder query = new StringBuilder("select revision_date from msds where material_id = ").append(materialId.toString()).append(" and on_line = 'Y' order by revision_date desc");

        return factory.selectQuery(query.toString());
    }

    public Vector getProperties() throws BaseException {

        Vector prodColl = new Vector();

        /*prodColl.add(library.getString("label.alloy"));*/
        prodColl.add(library.getString("label.boilingpoint"));
        prodColl.add(library.getString("label.carcinogen"));
        prodColl.add(library.getString("label.chronic"));
        prodColl.add(library.getString("label.compatibility"));
        prodColl.add(library.getString("label.corrosive"));
        prodColl.add(library.getString("label.density"));
        prodColl.add(library.getString("label.detonable"));
        prodColl.add(library.getString("label.evaporationrate"));
        prodColl.add(library.getString("label.federalhazardclass"));
        prodColl.add(library.getString("label.fireconditionstoavoid"));
        prodColl.add(library.getString("label.flashpoint"));
        prodColl.add(library.getString("label.freezingpoint"));
        prodColl.add(library.getString("label.healtheffects"));
        prodColl.add(library.getString("label.hmishealth"));
        prodColl.add(library.getString("label.hmisflammability"));
        prodColl.add(library.getString("label.hmisreactivity"));
        prodColl.add(library.getString("label.hmispersonalrotection"));
        /*prodColl.add(library.getString("label.hydrocarbon"));*/
        prodColl.add(library.getString("label.incompatible"));
        /*prodColl.add(library.getString("label.ingestion"));*/
        prodColl.add(library.getString("label.injection"));
        prodColl.add(library.getString("label.mixture"));
        prodColl.add(library.getString("label.miscible"));
        /*prodColl.add(library.getString("label.nanomaterial"));*/
        prodColl.add(library.getString("label.nfpahealth"));
        prodColl.add(library.getString("label.nfpaflammability"));
        prodColl.add(library.getString("label.nfpareactivity"));
        prodColl.add(library.getString("label.nfpaspecial"));
        prodColl.add(library.getString("label.nfpafromcustomer"));
        /*prodColl.add(library.getString("label.oshahazard"));*/
        prodColl.add(library.getString("label.oxidizer"));
        prodColl.add(library.getString("label.ph"));
        prodColl.add(library.getString("label.photoreactive"));
        prodColl.add(library.getString("label.physicalstate"));
        prodColl.add(library.getString("label.polymerize"));
        prodColl.add(library.getString("report.label.productCode"));
        prodColl.add(library.getString("label.pyrophoric"));
        prodColl.add(library.getString("label.radioactive"));
        prodColl.add(library.getString("label.radioactivecuries"));
        prodColl.add(library.getString("label.reactivity"));
        prodColl.add(library.getString("label.sara311312acute"));
        prodColl.add(library.getString("label.sara311312chronic"));
        prodColl.add(library.getString("label.sara311312fire"));
        prodColl.add(library.getString("label.sara311312pressure"));
        prodColl.add(library.getString("label.sara311312reactivity"));
        prodColl.add(library.getString("label.signalWord"));
        prodColl.add(library.getString("label.solids"));
        prodColl.add(library.getString("label.specificgravity"));
        prodColl.add(library.getString("label.specifichazard"));
        prodColl.add(library.getString("label.spontaneouslycombustible"));
        prodColl.add(library.getString("label.stable"));
        /*prodColl.add(library.getString("label.targetorgan"));*/
        prodColl.add(library.getString("label.tsca12b"));
        prodColl.add(library.getString("label.tscalist"));
        prodColl.add(library.getString("label.vapordensity"));
        prodColl.add(library.getString("label.vaporpressure"));
        prodColl.add(library.getString("label.voccompvaporpressuremmhg"));
        prodColl.add(library.getString("label.voclbpersolidlb"));
        prodColl.add(library.getString("label.voclbpersolidlblow"));
        prodColl.add(library.getString("label.voclbpersolidlbup"));
        prodColl.add(library.getString("label.voclesswaterandexempt"));
        prodColl.add(library.getString("label.voc"));
        prodColl.add(library.getString("label.vocpercent"));
        prodColl.add(library.getString("label.voclower(%)"));
        prodColl.add(library.getString("label.vocupper(%)"));
        prodColl.add(library.getString("label.waterreactive"));
        prodColl.add(library.getString("label.dataentrycomplete"));
        prodColl.add(library.getString("label.dateentered"));

        return prodColl;
    } //end of method

    public Collection getPropertyVersions(BigDecimal materialId) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new MsdsLocaleViewBean());

        StringBuilder query = new StringBuilder("select revision_date,locale_display,alloy,boiling_point,carcinogen,chronic,company_id,compatibility,content,corrosive,density,evaporation_rate,eyes,fire_conditions_to_avoid,flammability,flash_point,freezing_point,health,health_effects,hmis_flammability,hmis_from_customer,hmis_health,hmis_reactivity,incompatible,ingestion,injection,inhalation,");
        query.append("mixture,nfpa_from_customer,osha_hazard,oxidizer,personal_protection,ph,physical_state,polymerize,ppe,product_code,reactivity,route_Of_Entry,sara_311_312_acute,sara_311_312_chronic,sara_311_312_fire,sara_311_312_pressure,sara_311_312_reactivity,signal_word,skin,solids,specific_gravity,specific_hazard,stable,target_organ,tsca_12b,tsca_list,");
        query.append("vapor_density,vapor_pressure,voc_comp_vapor_pressure_mmhg,round(voc_lb_per_solid_lb_calc, 4) voc_lb_per_solid_lb,round(voc_lb_per_solid_lb_low_calc, 4) voc_lb_per_solid_lb_low,round(voc_lb_per_solid_lb_up_calc, 4) voc_lb_per_solid_lb_up,voc_less_h2o_exempt,voc,voc_unit,round(voc_percent, 4) voc_percent,round(voc_lower_percent,4) voc_lower_percent,round(voc_upper_percent,4) voc_upper_percent,water_reactive,");
        query.append("federal_hazard_class,nanomaterial,radioactive,radioactivity_curies,miscible,pyrophoric,detonable,photoreactive,spontaneously_combustible,data_entry_complete,date_entered");

        query.append(" from msds_locale_view where material_id = ").append(materialId.toString()).append(" and on_line = 'Y' order by revision_date desc");
        return factory.selectQuery(query.toString());
    } //end of method


    @SuppressWarnings("unchecked")
    public HashMap getSearchDataRowSpan(Vector revisionDateColl, BigDecimal materialId) throws Exception {
        HashMap m1 = new HashMap();

        Vector propA = new Vector();
        Vector propB = new Vector();
        Vector propCa = new Vector();
        Vector propCh = new Vector();
        Vector propC = new Vector();
        Vector propCo = new Vector();
        Vector propDe = new Vector();
        Vector propD = new Vector();
        Vector propE = new Vector();
        Vector propFHC = new Vector();
        Vector propF = new Vector();
        Vector propFP = new Vector();
        Vector propFrP = new Vector();
        Vector propHE = new Vector();
        Vector propHH = new Vector();
        Vector propHF = new Vector();
        Vector propHR = new Vector();
        Vector propHPR = new Vector();
        Vector propH = new Vector();
        Vector propI = new Vector();
        Vector propIng = new Vector();
        Vector propInj = new Vector();
        Vector propMix = new Vector();
        Vector propM = new Vector();
        Vector propNM = new Vector();
        Vector propNH = new Vector();
        Vector propNF = new Vector();
        Vector propNR = new Vector();
        Vector propNS = new Vector();
        Vector propNC = new Vector();
        Vector propOH = new Vector();
        Vector propO = new Vector();
        Vector propP = new Vector();
        Vector propPh = new Vector();
        Vector propPS = new Vector();
        Vector propPo = new Vector();
        Vector propPC = new Vector();
        Vector propPy = new Vector();
        Vector propRa = new Vector();
        Vector propRC = new Vector();
        Vector propR = new Vector();
        Vector propS3A = new Vector();
        Vector propS3C = new Vector();
        Vector propS3F = new Vector();
        Vector propS3P = new Vector();
        Vector propS3R = new Vector();
        Vector propSW = new Vector();
        Vector propS = new Vector();
        Vector propSG = new Vector();
        Vector propSP = new Vector();
        Vector propSC = new Vector();
        Vector propST = new Vector();
        Vector propTO = new Vector();
        Vector propT = new Vector();
        Vector propTL = new Vector();
        Vector propVD = new Vector();
        Vector propVP = new Vector();
        Vector propVC = new Vector();
        Vector propVS = new Vector();
        Vector propVSL = new Vector();
        Vector propVSU = new Vector();
        Vector propVLW = new Vector();
        Vector propV = new Vector();
        Vector propVW = new Vector();
        Vector propVL = new Vector();
        Vector propVU = new Vector();
        Vector propWR = new Vector();
        Vector propDEC = new Vector();
        Vector propDE = new Vector();

        Collection<MsdsLocaleViewBean> propertyVersionColl = getPropertyVersions(materialId);
        for (MsdsLocaleViewBean propBean : propertyVersionColl) {
            //save revision date and locale display
            RevisionComparisionBean bean = new RevisionComparisionBean();
            bean.setRevisionDate(propBean.getRevisionDate());
            bean.setLocaleDisplay(propBean.getLocaleDisplay());
            revisionDateColl.add(bean);
            //get properties data
            /*propA.add(propBean.getAlloy());
            m1.put(library.getString("label.alloy"), propA);
            */propB.add(propBean.getBoilingPoint());
            m1.put(library.getString("label.boilingpoint"), propB);
            propCa.add(propBean.getCarcinogen());
            m1.put(library.getString("label.carcinogen"), propCa);
            propCh.add(propBean.getChronic());
            m1.put(library.getString("label.chronic"), propCh);
            propC.add(propBean.getCompatibility());
            m1.put(library.getString("label.compatibility"), propC);
            propCo.add(propBean.getCorrosive());
            m1.put(library.getString("label.corrosive"), propCo);
            propDe.add(propBean.getDensity());
            m1.put(library.getString("label.density"), propDe);
            propD.add(propBean.getDetonable());
            m1.put(library.getString("label.detonable"), propD);
            propE.add(propBean.getEvaporationRate());
            m1.put(library.getString("label.evaporationrate"), propE);
            propFHC.add(propBean.getFederalHazardClass());
            m1.put(library.getString("label.federalhazardclass"), propFHC);
            propF.add(propBean.getFireConditionsToAvoid());
            m1.put(library.getString("label.fireconditionstoavoid"), propF);
            propFP.add(propBean.getFlashPoint());
            m1.put(library.getString("label.flashpoint"), propFP);
            propFrP.add(propBean.getFreezingPoint());
            m1.put(library.getString("label.freezingpoint"), propFrP);
            propHE.add(propBean.getHealthEffects());
            m1.put(library.getString("label.healtheffects"), propHE);
            propHH.add(propBean.getHmisHealth());
            m1.put(library.getString("label.hmishealth"), propHH);
            propHF.add(propBean.getHmisFlammability());
            m1.put(library.getString("label.hmisflammability"), propHF);
            propHR.add(propBean.getHmisReactivity());
            m1.put(library.getString("label.hmisreactivity"), propHR);
            propHPR.add(propBean.getPersonalProtection());
            m1.put(library.getString("label.hmispersonalrotection"), propHPR);
            /*propH.add(propBean.getHydrocarbon());
            m1.put(library.getString("label.hydrocarbon"), propH);
            */propI.add(propBean.getIncompatible());
            m1.put(library.getString("label.incompatible"), propI);
            /*propIng.add(propBean.getIngestion());
            m1.put(library.getString("label.ingestion"), propIng);
            */propInj.add(propBean.getInjection());
            m1.put(library.getString("label.injection"), propInj);
            propMix.add(propBean.getMixture());
            m1.put(library.getString("label.mixture"), propMix);
            propM.add(propBean.getMiscible());
            m1.put(library.getString("label.miscible"), propM);
            /*propNM.add(propBean.getNanoMaterial());
            m1.put(library.getString("label.nanomaterial"), propNM);
            */propNH.add(propBean.getHealth());
            m1.put(library.getString("label.nfpahealth"), propNH);
            propNF.add(propBean.getFlammability());
            m1.put(library.getString("label.nfpaflammability"), propNF);
            propNR.add(propBean.getReactivity());
            m1.put(library.getString("label.nfpareactivity"), propNR);
            propNS.add(propBean.getPpe());
            m1.put(library.getString("label.nfpaspecial"), propNS);
            propNC.add(propBean.getNfpaFromCustomer());
            m1.put(library.getString("label.nfpafromcustomer"), propNC);
            /*propOH.add(propBean.getOshaHazard());
            m1.put(library.getString("label.oshahazard"), propOH);
            */propO.add(propBean.getOxidizer());
            m1.put(library.getString("label.oxidizer"), propO);
            propP.add(propBean.getPh());
            m1.put(library.getString("label.ph"), propP);
            propPh.add(propBean.getPhotoreactive());
            m1.put(library.getString("label.photoreactive"), propPh);
            propPS.add(propBean.getPhysicalState());
            m1.put(library.getString("label.physicalstate"), propPS);
            propPo.add(propBean.getPolymerize());
            m1.put(library.getString("label.polymerize"), propPo);
            propPC.add(propBean.getProductCode());
            m1.put(library.getString("report.label.productCode"), propPC);
            propPy.add(propBean.getPyrophoric());
            m1.put(library.getString("label.pyrophoric"), propPy);
            propRa.add(propBean.getRadioactive());
            m1.put(library.getString("label.radioactive"), propRa);
            propRC.add(propBean.getRadiaactiveCuries());
            m1.put(library.getString("label.radioactivecuries"), propRC);
            propR.add(propBean.getReactivity());
            m1.put(library.getString("label.reactivity"), propR);
            propS3A.add(propBean.getSara311312Acute());
            m1.put(library.getString("label.sara311312acute"), propS3A);
            propS3C.add(propBean.getSara311312Chronic());
            m1.put(library.getString("label.sara311312chronic"), propS3C);
            propS3F.add(propBean.getSara311312Fire());
            m1.put(library.getString("label.sara311312fire"), propS3F);
            propS3P.add(propBean.getSara311312Pressure());
            m1.put(library.getString("label.sara311312pressure"), propS3P);
            propS3R.add(propBean.getSara311312Reactivity());
            m1.put(library.getString("label.sara311312reactivity"), propS3R);
            propSW.add(propBean.getSignalWord());
            m1.put(library.getString("label.signalWord"), propSW);
            propS.add(propBean.getSolids());
            m1.put(library.getString("label.solids"), propS);
            propSG.add(propBean.getSpecificGravity());
            m1.put(library.getString("label.specificgravity"), propSG);
            propSP.add(propBean.getSpecificHazard());
            m1.put(library.getString("label.specifichazard"), propSP);
            propSC.add(propBean.getSpontaneouslyCombustible());
            m1.put(library.getString("label.spontaneouslycombustible"), propSC);
            propST.add(propBean.getStable());
            m1.put(library.getString("label.stable"), propST);
            /*propTO.add(propBean.getTargetOrgan());
            m1.put(library.getString("label.targetorgan"), propTO);
            */propT.add(propBean.getTsca12b());
            m1.put(library.getString("label.tsca12b"), propT);
            propTL.add(propBean.getTscaList());
            m1.put(library.getString("label.tscalist"), propTL);
            propVD.add(propBean.getVaporDensity());
            m1.put(library.getString("label.vapordensity"), propVD);
            propVP.add(propBean.getVaporPressure());
            m1.put(library.getString("label.vaporpressure"), propVP);
            propVC.add(propBean.getVocCompVaporPressureMmhg());
            m1.put(library.getString("label.voccompvaporpressuremmhg"), propVC);
            propVS.add(propBean.getVocLbPerSolidLb());
            m1.put(library.getString("label.voclbpersolidlb"), propVS);
            propVSL.add(propBean.getVocLbPerSolidLbLow());
            m1.put(library.getString("label.voclbpersolidlblow"), propVSL);
            propVSU.add(propBean.getVocLbPerSolidLbUp());
            m1.put(library.getString("label.voclbpersolidlbup"), propVSU);
            propVLW.add(propBean.getVocLessH2oExempt());
            m1.put(library.getString("label.voclesswaterandexempt"), propVLW);
            propV.add(propBean.getVoc());
            m1.put(library.getString("label.voc"), propV);
            propVW.add(propBean.getVocPercent());
            m1.put(library.getString("label.vocpercent"), propVW);
            propVL.add(propBean.getVocLowerPercent());
            m1.put(library.getString("label.voclower(%)"), propVL);
            propVU.add(propBean.getVocUpperPercent());
            m1.put(library.getString("label.vocupper(%)"), propVU);
            propWR.add(propBean.getWaterReactive());
            m1.put(library.getString("label.waterreactive"), propWR);
            propDEC.add(propBean.getDataEntryComplete());
            m1.put(library.getString("label.dataentrycomplete"), propDEC);
            propDE.add(propBean.getDateEntered());
            m1.put(library.getString("label.dateentered"), propDE);
        }
        return m1;
    } //end of method
} //end of class