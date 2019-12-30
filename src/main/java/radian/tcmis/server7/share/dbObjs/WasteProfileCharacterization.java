package radian.tcmis.server7.share.dbObjs;

public class WasteProfileCharacterization {
   protected TcmISDBModel db;

   protected int profileId;
   protected String parameterId;
   protected String lowVal;
   protected String highVal;
   protected String characterizationDate;


   public WasteProfileCharacterization(TcmISDBModel db){
      this.db = db;
   }
   public WasteProfileCharacterization(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get methods
   public int getProfileId(){return profileId;}
   public String getParameterId(){return parameterId;}
   public String getLowVal(){return lowVal;}
   public String getHighVal(){return highVal;}
   public String getCharacterizationDate(){return characterizationDate;}

   // set methods
   public void setProfileId(int s){profileId = s;}
   public void setParameterId(String s){parameterId = s;}
   public void setLowVal(String s){lowVal = s;}
   public void setHighVal(String s){highVal = s;}
   public void setCharacterizationDate(String s){characterizationDate = s;}



}