package radian.web.servlets.ablaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABLaeroSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ ABLaerosearchmsds check it is here....");
      return "ABLaero";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ABLaeroServerResourceBundle();
   }
}