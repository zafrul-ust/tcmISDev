package radian.web.servlets.ommc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMMCClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "OMMC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMMCServerResourceBundle();
   }
}