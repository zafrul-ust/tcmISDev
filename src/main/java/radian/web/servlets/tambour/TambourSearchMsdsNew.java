package radian.web.servlets.tambour;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TambourSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Tamboursearchmsds check it is here....");
      return "Tambour";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TambourServerResourceBundle();
   }
}