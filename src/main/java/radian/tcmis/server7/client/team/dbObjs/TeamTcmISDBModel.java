package radian.tcmis.server7.client.team.dbObjs;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.team.helpers.*;

public class TeamTcmISDBModel  extends TcmISDBModel {
   public TeamTcmISDBModel(){
      this("Team");
   }

   public TeamTcmISDBModel(String client){
      super(client);
   }

   public TeamTcmISDBModel(String client,String logonVersion){
      super(client,logonVersion);
   }

   public TeamTcmISDBModel(String client,String logonVersion,String locale){
      super(client,logonVersion,locale);
   }

	protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new TeamServerResourceBundle();
   }

}