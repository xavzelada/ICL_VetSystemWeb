package com.app.vetsystemweb.entities;
// Generated 17-sep-2019 16:08:27 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

public class Checkup  implements java.io.Serializable {

    private Integer checkupid;
    private Vet vet;
    private Integer vetid;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkupdate;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private Set<Checkupreport> checkupreports = new HashSet<>();

    public Checkup() {
    }

	
    public Checkup(Integer checkupid, Integer vetid, String isactive, Date creationdate, String createdby) {
        this.checkupid = checkupid;
        this.vetid = vetid;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Checkup(Integer checkupid, Integer vetid, Vet vet, Date checkupdate, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set checkupreports) {
       this.checkupid = checkupid;
       this.vetid = vetid;
       this.vet = vet;
       this.checkupdate = checkupdate;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.checkupreports = checkupreports;
    }
   
    public Integer getCheckupid() {
        return this.checkupid;
    }
    
    public void setCheckupid(Integer checkupid) {
        this.checkupid = checkupid;
    }

    public Integer getVetid() {
        return vetid;
    }

    public void setVetid(Integer vetid) {
        this.vetid = vetid;
    }
    
    public Vet getVet() {
        return this.vet;
    }
    
    public void setVet(Vet vet) {
        this.vet = vet;
    }
    public Date getCheckupdate() {
        return this.checkupdate;
    }
    
    public void setCheckupdate(Date checkupdate) {
        this.checkupdate = checkupdate;
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
    public Set getCheckupreports() {
        return this.checkupreports;
    }
    
    public void setCheckupreports(Set checkupreports) {
        this.checkupreports = checkupreports;
    }
}


