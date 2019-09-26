package com.app.vetsystemweb.entities;


import java.util.Date;

public class Checkupreport  implements java.io.Serializable {


    private Integer checkupreportid;
    private Integer checkupid;
    private Checkup checkup;
    private Integer petid;
    private Pet pet;
    private byte[] reportnotes;
    private String reportnotestext;
    private byte[] reportattachment;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public Checkupreport() {
    }

	
    public Checkupreport(Integer checkupreportid, String isactive, Date creationdate, String createdby) {
        this.checkupreportid = checkupreportid;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Checkupreport(Integer checkupreportid, Checkup checkup, Pet pet, byte[] reportnotes, byte[] reportattachment, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
       this.checkupreportid = checkupreportid;
       this.checkup = checkup;
       this.pet = pet;
       this.reportnotes = reportnotes;
       this.reportattachment = reportattachment;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
    }
   
    public Integer getCheckupreportid() {
        return this.checkupreportid;
    }
    
    public void setCheckupreportid(Integer checkupreportid) {
        this.checkupreportid = checkupreportid;
    }

    public Integer getCheckupid() {
        return checkupid;
    }

    public void setCheckupid(Integer checkupid) {
        this.checkupid = checkupid;
    }

    public Integer getPetid() {
        return petid;
    }

    public void setPetid(Integer petid) {
        this.petid = petid;
    }
    
    
    public Checkup getCheckup() {
        return this.checkup;
    }
    
    public void setCheckup(Checkup checkup) {
        this.checkup = checkup;
    }
    public Pet getPet() {
        return this.pet;
    }
    
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public byte[] getReportnotes() {
        return this.reportnotes;
    }
    
    public void setReportnotes(byte[] reportnotes) {
        this.reportnotes = reportnotes;
    }
    public byte[] getReportattachment() {
        return this.reportattachment;
    }
    
    public void setReportattachment(byte[] reportattachment) {
        this.reportattachment = reportattachment;
    }
    public String getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
    public Date getCreationdate() {
        return this.creationdate;
    }
    
    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }
    public String getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    public Date getLastupdate() {
        return this.lastupdate;
    }
    
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }
    public String getUpdateby() {
        return this.updateby;
    }
    
    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getReportnotestext() {
        return reportnotestext;
    }

    public void setReportnotestext(String reportnotestext) {
        this.reportnotestext = reportnotestext;
    }
    
}


