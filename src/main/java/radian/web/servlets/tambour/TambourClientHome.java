package radian.web.servlets.tambour;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TambourClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Tambour";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TambourServerResourceBundle();
   }
}