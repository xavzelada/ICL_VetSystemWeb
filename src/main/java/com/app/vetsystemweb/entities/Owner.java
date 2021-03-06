package com.app.vetsystemweb.entities;
// Generated 17-sep-2019 16:08:27 by Hibernate Tools 4.3.1


import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Owner generated by hbm2java
 */
public class Owner  implements java.io.Serializable {

    private Integer ownerid;
    private String name;
    private String surname;
    private String address1;
    private String address2;
    private String phonenumber1;
    private String phonenumber2;
    private String email;    
    private byte[] photo;
    private String base64Image;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private Set<Pet> pets = new HashSet<>();

    public Owner() {
    }

	
    public Owner(Integer ownerid, String name, String surname, String isactive, Date creationdate, String createdby) {
        this.ownerid = ownerid;
        this.name = name;
        this.surname = surname;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Owner(Integer ownerid, String name, String surname, String address1, String address2, String phonenumber1, String phonenumber2, String email, byte[] photo, Date birthdate, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set pets) {
       this.ownerid = ownerid;
       this.name = name;
       this.surname = surname;
       this.address1 = address1;
       this.address2 = address2;
       this.phonenumber1 = phonenumber1;
       this.phonenumber2 = phonenumber2;
       this.email = email;
       this.photo = photo;
       this.birthdate = birthdate;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.pets = pets;
       this.base64Image = Base64.getEncoder().encodeToString(photo);
    }
   
    public Integer getOwnerid() {
        return this.ownerid;
    }
    
    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
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
    public String getAddress1() {
        return this.address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return this.address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
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
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    public Set getPets() {
        return this.pets;
    }
    
    public void setPets(Set pets) {
        this.pets = pets;
    }
    
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}


