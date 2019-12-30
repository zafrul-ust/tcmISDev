package radian.web.servlets.iai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IAISearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ IAIsearchmsds check it is here....");
      return "IAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IAIServerResourceBundle();
   }
}