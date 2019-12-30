package radian.tcmis.both1.beans;

public class WorkAreaCommentDataBean {

   private int id;
   private String catPartNo;
   private int partGroupNo;
   private String catalogId;
   private String facilityId;
   private String application;
   private String comments;
   private int personnelId;

   public WorkAreaCommentDataBean(){
   }

   //setters
   public void setId(int i) {
     this.id = i;
   }
   public void setCatPartNo(String s) {
     this.catPartNo = s;
   }
   public void setPartGroupNo(int i) {
     this.partGroupNo = i;
   }
   public void setCatalogId(String s) {
     this.catalogId = s;
   }
   public void setFacilityId(String s) {
     this.facilityId = s;
   }
   public void setApplication(String s) {
     this.application = s;
   }
   public void setComments(String s) {
     this.comments = s;
   }
   public void setPersonnelId(int i) {
     this.personnelId = i;
   }

   //getters
   public int getId() {
     return this.id;
   }
   public String getCatPartNo() {
     return this.catPartNo;
   }
   public int getPartGroupNo() {
     return this.partGroupNo;
   }
   public String getCatalogId() {
     return this.catalogId;
   }
   public String getFacilityId() {
     return this.facilityId;
   }
   public String getApplication() {
     return this.application;
   }
   public String getComments() {
     return this.comments;
   }
   public int getPersonnelId() {
     return this.personnelId;
   }

} //end of class






























