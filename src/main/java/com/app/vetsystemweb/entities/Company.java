package com.app.vetsystemweb.entities;
// Generated 17-sep-2019 16:08:27 by Hibernate Tools 4.3.1

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Company  implements java.io.Serializable{

    private Integer companyid;
    private String name;
    private String address;
    private byte[] logo;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private String base64Image;
    private Set<Branch> branches = new HashSet();

    public Company() {
        this.isactive = "A";
    }

	
    public Company(int companyid, String name, String isactive, Date creationdate, String createdby) {
        this.companyid = companyid;
        this.name = name;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Company(int companyid, String name, String address, byte[] logo, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set branches) {
       this.companyid = companyid;
       this.name = name;
       this.address = address;
       this.logo = logo;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.branches = branches;
       this.base64Image = Base64.getEncoder().encodeToString(logo);
       
    }
   
    public Integer getCompanyid() {
        return this.companyid;
    }
    
    public void setCompanyid(int comanyid) {
        this.companyid = comanyid;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public byte[] getLogo() {
        return this.logo;
    }
    
    public void setLogo(byte[] logo) {
        this.logo = logo;
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
    
    public Set getBranches() {
        return this.branches;
    }
    
    public void setBranches(Set branches) {
        this.branches = branches;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    
    
}


