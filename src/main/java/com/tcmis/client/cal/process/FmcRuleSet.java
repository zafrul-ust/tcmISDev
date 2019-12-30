package com.tcmis.client.cal.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.xml.sax.Attributes;
import com.tcmis.client.cal.beans.FmcExtractBean;

/******************************************************************************
 * Rules for parsing the fmc data file using Digester.
 * @version 1.0
 *****************************************************************************/
public final class FmcRuleSet
    extends RuleSetBase {
  static int count = 1;
  static FmcExtractBean bean;
  static Collection c;

  public FmcRuleSet(Collection c) {
    super();
    this.c = c;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.RuleSet#addRuleInstances(org.apache.commons.digester.Digester)
   */
  public void addRuleInstances(Digester digester) {
    digester.addRule("dataset/data", new DataRule());
    digester.addRule("dataset/data/row", new RowRule());
    digester.addRule("dataset/data/row/value", new ValueRule());
  }

  private static class DataRule
      extends Rule {
  }

  private static class RowRule
      extends Rule {

    public void begin(String namespace, String name, Attributes attrs) {
      count = 1;
      bean = new FmcExtractBean();
    }
  }

  private static class ValueRule
      extends Rule {

    public void body(String namespace, String name, String text) throws
        Exception {
    	if (count == 1) {
    		bean.setTraOrg(text);
    	}
    	else if (count == 2) {
    		bean.setTrlType(text);
    	}
    	else if (count == 3) {
    		bean.setTrlCostcode(text);
    	}
    	else if (count == 4) {
    		bean.setCostCodeDescription(text);
    	}
    	else if (count == 5) {
    		SimpleDateFormat df =
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    		Date date = df.parse(text);
    		bean.setTrlDate(date);
    	}
    	// 6 work order
    	else if (count == 6) {
    		bean.setTrlEvent(text);
    	}
    	else if (count == 7) {
    		bean.setWorkOrderDescription(text);
    	}
    	else if (count == 8) {
    		bean.setParClass(text);
    	}
    	else if (count == 9) {
    		bean.setTrlPart(text);
    	}
    	else if (count == 10) {
    		bean.setPartDescription(text);
    	}
    	else if (count == 11) {
    		BigDecimal b = new BigDecimal(text);
    		bean.setTrlQuantity(b);
    	}
    	else if (count == 12) {
    		BigDecimal b = new BigDecimal(text);
    		bean.setTrlPrice(b);
    	}
    	else if (count == 13) {
    		bean.setParUom(text);
    	}
    	else if (count == 14) {
    		bean.setUnitOfMeasureDescription(text);
    		c.add(bean);
    	}
      count++;
    }
  }
}
