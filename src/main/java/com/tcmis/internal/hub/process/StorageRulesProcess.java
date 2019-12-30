package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.StorageRule;
import com.tcmis.internal.hub.factory.IStorageRulesDataMapper;

public class StorageRulesProcess {

	private IStorageRulesDataMapper dataMapper;
	
	public StorageRulesProcess(IStorageRulesDataMapper dataMapper) {
		Objects.requireNonNull(dataMapper);
		this.dataMapper = dataMapper;
	}
	
	protected IStorageRulesDataMapper getDataMapper() {
		return dataMapper;
	}

	public Collection<StorageRule> search(StorageRule input) throws BaseException {
		Collection<StorageRule> storageRules = null;
		if (input == null) {
			storageRules = Collections.emptyList();
		}
		else {
			storageRules = getDataMapper().listStorageRulesByHub(input.getBranchPlant());
		}
			
		return storageRules;
	}
		
	public void update(Collection<StorageRule> inputs) throws BaseException {
		getDataMapper().updateStorageRules(inputs);
	}
	
	public Map<String, Collection<String>> assignStorageRuleValidValues(String branchPlant) throws BaseException {
		Map<String, Collection<String>> vvTable = new HashMap<>();
		vvTable.put("acidBaseChoices",getDataMapper().listAcidBaseChoices());
		vvTable.put("corrosiveChoices",getDataMapper().listCorrosiveChoices());
		vvTable.put("aerosolChoices",getDataMapper().listAerosolChoices());
		vvTable.put("flammableChoices",getDataMapper().listFlammableChoices());
		vvTable.put("toxicChoices",getDataMapper().listToxicChoices());
		vvTable.put("oxidizerChoices",getDataMapper().listOxidizerChoices());
		vvTable.put("reactiveChoices",getDataMapper().listReactiveChoices());
		vvTable.put("waterReactiveChoices",getDataMapper().listWaterReactiveChoices());
		vvTable.put("containerChoices",getDataMapper().listContainerChoices());
		vvTable.put("sizeUnitChoices",getDataMapper().listSizeUnits());
		vvTable.put("organicPeroxideChoices",getDataMapper().listOrganicPeroxideChoices());
		vvTable.put("storageFamilyChoices",getDataMapper().listHubStorageFamiliesByHub(branchPlant));
		vvTable.put("gasChoices", getDataMapper().listGasChoices());
		vvTable.put("storageTempChoices", getDataMapper().listStorageTempChoices());
		return vvTable;
	}
	
	public ExcelHandler createExcelReport(StorageRule input, Locale locale) throws BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		String[] headerkeys = { "label.hub","label.ruleorder","label.flammable","label.watermiscible","label.aerosol","label.corrosive","label.acidbase","label.toxic","label.oxidizer","label.reactive","label.organicperoxide","label.waterreactive","label.pyrophoric","label.gas","label.containermaterial","label.containerpressure","label.ibc","label.minsize","label.maxsize","label.storagetemp","label.storagefamily","label.altstoragefamily" };
		int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] widths = { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
        Iterator<StorageRule> iter = search(input).iterator();
		while(iter.hasNext()) {
            StorageRule storageRule = iter.next();
			pw.addRow();
			pw.addCell(storageRule.getBranchPlant());
			pw.addCell(storageRule.getRuleOrder());
			pw.addCell(storageRule.getWmsFlammable());
			pw.addCell(storageRule.getWmsWaterMiscible());
			pw.addCell(storageRule.getWmsAerosol());
			pw.addCell(storageRule.getWmsCorrosive());
			pw.addCell(storageRule.getWmsAcidBase());
			pw.addCell(storageRule.getWmsToxic());
			pw.addCell(storageRule.getWmsOxidizer());
			pw.addCell(storageRule.getWmsReactive());
			pw.addCell(storageRule.getWmsOrganicPeroxide());
			pw.addCell(storageRule.getWmsWaterReactive());
			pw.addCell(storageRule.getWmsPyrophoric());
			pw.addCell(storageRule.getWmsGas());
			pw.addCell(storageRule.getWmsContainer());
			pw.addCell(storageRule.getWmsPressureRelieving());
			pw.addCell(storageRule.getIbc());
			if (storageRule.getMinSize() == null) {
				pw.addCell("");
			}
			else {
				pw.addCell(StringHandler.emptyIfNull(storageRule.getDetectMinSize()) 
						+ " " + StringHandler.emptyIfNull(storageRule.getMinSize()) 
						+ " " + StringHandler.emptyIfNull(storageRule.getSizeUnit()));
			}
			if (storageRule.getMaxSize() == null) {
				pw.addCell("");
			}
			else {
				pw.addCell(StringHandler.emptyIfNull(storageRule.getDetectMaxSize())
						+ " " + StringHandler.emptyIfNull(storageRule.getMaxSize())
						+ " " + StringHandler.emptyIfNull(storageRule.getSizeUnit()));
			}
			pw.addCell(storageRule.getWmsStorageTemp());
			pw.addCell(storageRule.getStorageFamily());
			pw.addCell(storageRule.getAltStorageFamily());
		}
		return pw;
	}
}
