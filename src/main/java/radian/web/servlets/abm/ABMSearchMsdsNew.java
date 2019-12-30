package radian.web.servlets.abm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABMSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ ABMsearchmsds check it is here....");
      return "ABM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ABMServerResourceBundle();
   }
}