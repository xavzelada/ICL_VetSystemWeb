package com.app.vetsystemweb.entities;
// Generated 17-sep-2019 16:08:27 by Hibernate Tools 4.3.1


import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

public class Employee  implements java.io.Serializable {

    private Integer employeeid;
    private Integer branchid;
    private Branch branch;
    private String name;
    private String surname;
    private byte[] photo;
    private String base64Image;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private String address;
    private String phonenumber1;
    private String phonenumber2;
    private String personalemail;
    private String corporativeemail;
    private String role;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private Set<Vet> vets = new HashSet<>();
    
    public Employee() {
    }

	
    public Employee(int employeeid, int branchid, String name, String surname, String role, String isactive, Date creationdate, String createdby) {
        this.employeeid = employeeid;
        this.branchid = branchid;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Employee(int employeeid, int branchid, Branch branch, String name, String surname, byte[] photo, Date birthdate, String address, String phonenumber1, String phonenumber2, String personalemail, String corporativeemail, String role, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set vets) {
       this.employeeid = employeeid;
       this.branchid = branchid;
       this.branch = branch;
       this.name = name;
       this.surname = surname;
       this.photo = photo;
       this.birthdate = birthdate;
       this.address = address;
       this.phonenumber1 = phonenumber1;
       this.phonenumber2 = phonenumber2;
       this.personalemail = personalemail;
       this.corporativeemail = corporativeemail;
       this.role = role;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.vets = vets;
       this.base64Image = Base64.getEncoder().encodeToString(photo);
    }
   
    public Integer getEmployeeid() {
        return this.employeeid;
    }
    
    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }
    
    public Branch getBranch() {
        return this.branch;
    }
    
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return this.surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public byte[] getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public Date getBirthdate() {
        return this.birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhonenumber1() {
        return this.phonenumber1;
    }
    
    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }
    public String getPhonenumber2() {
        return this.phonenumber2;
    }
    
    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }
    public String getPersonalemail() {
        return this.personalemail;
    }
    
    public void setPersonalemail(String personalemail) {
        this.personalemail = personalemail;
    }
    public String getCorporativeemail() {
        return this.corporativeemail;
    }
    
    public void setCorporativeemail(String corporativeemail) {
        this.corporativeemail = corporativeemail;
    }
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
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
    public Set getVets() {
        return this.vets;
    }
    
    public void setVets(Set vets) {
        this.vets = vets;
    }
    
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

}


