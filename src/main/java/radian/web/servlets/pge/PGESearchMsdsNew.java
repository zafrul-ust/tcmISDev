package radian.web.servlets.pge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PGESearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ PGEsearchmsds check it is here....");
      return "PGE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new PGEServerResourceBundle();
   }
}