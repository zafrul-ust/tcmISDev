package radian.web.servlets.gm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GMSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ GMsearchmsds check it is here....");
      return "GM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GMServerResourceBundle();
   }
}