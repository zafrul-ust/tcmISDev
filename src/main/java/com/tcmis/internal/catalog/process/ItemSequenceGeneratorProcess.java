package com.tcmis.internal.catalog.process;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.catalog.beans.ItemSequenceGeneratorBean;

public class ItemSequenceGeneratorProcess extends GenericProcess {

	private final int SEQUENCE_NAME = 0;
	private final int TABLE_NAME = 1;
	private final int COLUMN_NAME = 2;
	
	Log log = LogFactory.getLog(this.getClass());
	private final Map<String,String[]> sequenceMap;

	public ItemSequenceGeneratorProcess(String client) {
		super(client);
		sequenceMap = new HashMap<String, String[]>();
		sequenceMap.put("material_id", new String[]{"global.material_seq","global.material","MATERIAL_ID"});
		sequenceMap.put("item_id", new String[]{"seq_item_id","global.item","ITEM_ID"});
		sequenceMap.put("manufacturer", new String[]{"global.mfg_seq","global.manufacturer","MFG_ID"});
		sequenceMap.put("trade_secret", new String[]{"trade_secret_seq","global.chemical","CAS_NUMBER"});
	}
	
	public Collection<String> generateItems(ItemSequenceGeneratorBean bean) throws BaseException {
		Connection conn = factory.getDbManager().getConnection();
		List<String> ids = new ArrayList<String>();
		try {
			int count = bean.getHowMany()<=100?bean.getHowMany():100;
			for (int i = 0; i < count; i++) {
				ids.add(generateIdFromSequence(bean.getSearchLike().toLowerCase(),conn));
			}
		}
		finally {
			factory.getDbManager().returnConnection(conn);
		}
		return ids;
	}
	
	private String generateIdFromSequence(String sequence, Connection conn) throws BaseException {
		boolean foundMaterialId=false;
		String sequenceId = "";
		
		String sequenceName = sequenceMap.get(sequence)[SEQUENCE_NAME];
		String tableName = sequenceMap.get(sequence)[TABLE_NAME];
		String columnName = sequenceMap.get(sequence)[COLUMN_NAME];
		while ( ! foundMaterialId) {
			try {
				sequenceId = factory.selectSingleValue("select "+sequenceName+".nextval from dual",conn);
				
				if (sequence.equals("trade_secret")) {
					sequenceId = "TS"+StringUtils.leftPad(sequenceId, 5, "0");
				}
		
				int testNumber = Integer.parseInt(factory.selectSingleValue("select count(*) from "+tableName+" where "+columnName+" = '"+sequenceId+"'",conn));
		
				if (testNumber == 0) {
					foundMaterialId = true;
				}
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		return sequenceId;
	}
}
