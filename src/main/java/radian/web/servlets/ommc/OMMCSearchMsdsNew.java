package radian.web.servlets.ommc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMMCSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ OMMCsearchmsds check it is here....");
      return "OMMC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMMCServerResourceBundle();
   }
}