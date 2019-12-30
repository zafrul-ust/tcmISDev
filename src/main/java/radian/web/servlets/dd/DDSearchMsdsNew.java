package radian.web.servlets.dd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DDSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ DDsearchmsds check it is here....");
      return "DD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DDServerResourceBundle();
   }
}