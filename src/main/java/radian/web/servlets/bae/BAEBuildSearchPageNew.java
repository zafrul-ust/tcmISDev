package radian.web.servlets.bae;

import radian.tcmis.server7.share.helpers.*;

public class BAEBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BAE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAEServerResourceBundle();
   }
}