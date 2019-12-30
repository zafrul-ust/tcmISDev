package radian.web.servlets.hans_sasserath;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HansSasserathSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ HansSasserathsearchmsds check it is here....");
      return "HansSasserath";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HansSasserathServerResourceBundle();
   }
}