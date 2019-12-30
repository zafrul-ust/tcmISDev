package radian.web.servlets.tambour;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TambourBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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