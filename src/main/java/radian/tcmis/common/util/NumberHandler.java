package radian.tcmis.common.util;

import java.math.BigDecimal;
/***************************************************************************
  * CLASSNAME: NumberHandler<BR>
***************************************************************************/

public class NumberHandler  {

    public NumberHandler() {
    }//end constructor

/******************************************************************************
  * Returns 0 if it's null, otherwise integer value.<BR>
  * @param b  BigDecimal to be checked.
  ****************************************************************************/
    public static synchronized int zeroIfNull(BigDecimal b) {
      if(b == null) {
        return 0;
      }
      else {
        return b.intValue();
      }
    }

}//end NumberHandler
