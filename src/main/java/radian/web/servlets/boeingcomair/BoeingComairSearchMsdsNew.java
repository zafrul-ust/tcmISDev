package radian.web.servlets.boeingcomair;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingComairSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BoeingComairsearchmsds check it is here....");
      return "BOEING_COMAIR";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BoeingComairServerResourceBundle();
   }
}