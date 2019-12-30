package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.catalog.beans.CatalogItemDetailQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcViewBean;
import com.tcmis.internal.catalog.beans.ItemMatcherBean;

public class ItemMatcherProcess extends GenericProcess {

	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	
	public ItemMatcherProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

	public Collection<ItemMatcherBean> findMatches(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select distinct p.SIZE_VERIFIED,p.STOCKING_TYPE,p.ITEM_VERIFIED, p.item_id, p.part_id, p.material_id, p.GRADE,p.components_per_item ||' x'||' '|| p.PART_SIZE ||' '||p.SIZE_UNIT||' '||p.PKG_STYLE||' '||p.DIMENSION packaging, ");
		query.append(" p.part_id, p.dimension, p.SHELF_LIFE_DAYS,p.SHELF_LIFE_BASIS,v.jsp_label shelf_life_basis_disp, p.MIN_TEMP, p.MAX_TEMP, p.TEMP_UNITS,p.RECERT,p.SIZE_VARIES,p.MFG_PART_NO, p.MFG_PART_NO_EXTENSION, p.NET_WT, p.NET_WT_UNIT,it.ITEM_TYPE,it.REVISION_COMMENTS, ");
		query.append(" m.MATERIAL_DESC from global.part p,(select x.item_id from part x ,(select item_id  from part group by item_id  having count(*)=").append(input.getTotalComps()).append(") y  where x.item_id = y.item_id");

		StringBuilder compString = new StringBuilder();
		for (CatalogItemQcViewBean comp : components) {
			compString.append(",");
			compString.append(comp.getMaterialId());
		}
		if ( compString.length() > 2 ) {
			query.append(" and  x.material_id in (").append(compString.substring(1)).append(")) z");
	    }
	    else {
	    	query.append(" and  x.material_id=6544645684568465844) z");
	    }

	    query.append(",global.item it, vv_shelf_life_basis v, material m where p.material_id = m.material_id and p.item_id = z.item_id and z.item_id = it.item_id");
		query.append(" and it.item_type in (select item_type from global.vv_item_type where display_on_cat_add ='Y') ");
		query.append(" and p.STOCKING_TYPE = 'S' and p.shelf_life_basis = v.shelf_life_basis(+) order by p.item_id, p.part_id");
	    
	    Collection<ItemMatcherBean> itemCollection = factory.setBean(new ItemMatcherBean()).selectQuery(query.toString());
	    return itemCollection;
		
	}

	public String createNewItem() throws BaseException {
		String query="select seq_item_id.nextval NEWITEMID from dual";
		return factory.selectSingleValue(query);
	}
	
	public boolean updateItemId(CatalogItemDetailQcInputBean input,boolean newItem ) {
		boolean result=false;

		try {
			if (newItem) {
				String itemUpdate="insert into global.item (ITEM_ID) values ("+input.getItemId()+")";
				factory.deleteInsertUpdate(itemUpdate);
			}

			result=true;
		}
		catch ( Exception e ) {
			log.error(e.getStackTrace());
			result=false;
		}

		return result;
	}
}
