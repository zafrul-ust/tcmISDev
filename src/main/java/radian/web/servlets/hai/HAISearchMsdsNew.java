package radian.web.servlets.hai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HAISearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ HAIsearchmsds check it is here....");
      return "HAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HAIServerResourceBundle();
   }
}