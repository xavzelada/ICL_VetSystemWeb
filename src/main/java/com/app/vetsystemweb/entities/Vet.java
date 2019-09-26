package com.app.vetsystemweb.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Vet  implements java.io.Serializable {

    private Integer vetid;
    private Integer employeeid;
    private Employee employee;
    private String licenseid;
    private String licenseissuedby;
    private String licensetype;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private Set<Checkup> checkups = new HashSet<>();
    

    public Vet() {
    }

	
    public Vet(int vetid, int employeeid, String licenseid, String licenseissuedby, String licensetype, String isactive, Date creationdate, String createdby) {
        this.vetid = vetid;
        this.employeeid = employeeid;
        this.licenseid = licenseid;
        this.licenseissuedby = licenseissuedby;
        this.licensetype = licensetype;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Vet(int vetid, int employeeid, Employee employee, String licenseid, String licenseissuedby, String licensetype, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set checkups) {
       this.vetid = vetid;
       this.employeeid = employeeid;
       this.employee = employee;
       this.licenseid = licenseid;
       this.licenseissuedby = licenseissuedby;
       this.licensetype = licensetype;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.checkups = checkups;
    }
   
    public Integer getVetid() {
        return this.vetid;
    }
    
    public void setVetid(Integer vetid) {
        this.vetid = vetid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }
    
    public Employee getEmployee() {
        return this.employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public String getLicenseid() {
        return this.licenseid;
    }
    
    public void setLicenseid(String licenseid) {
        this.licenseid = licenseid;
    }
    public String getLicenseissuedby() {
        return this.licenseissuedby;
    }
    
    public void setLicenseissuedby(String licenseissuedby) {
        this.licenseissuedby = licenseissuedby;
    }
    public String getLicensetype() {
        return this.licensetype;
    }
    
    public void setLicencetype(String licensetype) {
        this.licensetype = licensetype;
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
    public Set getCheckups() {
        return this.checkups;
    }
    
    public void setCheckups(Set checkups) {
        this.checkups = checkups;
    }

}


