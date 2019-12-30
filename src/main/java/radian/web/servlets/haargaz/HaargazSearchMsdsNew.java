package radian.web.servlets.haargaz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HaargazSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Haargazsearchmsds check it is here....");
      return "Haargaz";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HaargazServerResourceBundle();
   }
}