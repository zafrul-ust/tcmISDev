package com.tcmis.internal.hub.factory;

import java.util.Collection;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.hub.beans.StorageRule;

public interface IStorageRulesDataMapper {

	Collection<StorageRule> listStorageRulesByHub(String branchPlant) throws BaseException;
	void updateStorageRules(Collection<StorageRule> rules) throws BaseException;
	Collection<String> listAcidBaseChoices() throws BaseException;
	Collection<String> listCorrosiveChoices() throws BaseException;
	Collection<String> listAerosolChoices() throws BaseException;
	Collection<String> listFlammableChoices() throws BaseException;
	Collection<String> listToxicChoices() throws BaseException;
	Collection<String> listOxidizerChoices() throws BaseException;
	Collection<String> listReactiveChoices() throws BaseException;
	Collection<String> listWaterReactiveChoices() throws BaseException;
	Collection<String> listOrganicPeroxideChoices() throws BaseException;
	Collection<String> listContainerChoices() throws BaseException;
	Collection<String> listHubStorageFamiliesByHub(String branchPlant) throws BaseException;
	Collection<String> listSizeUnits() throws BaseException;
	Collection<String> listGasChoices() throws BaseException;
	Collection<String> listStorageTempChoices() throws BaseException;
}
