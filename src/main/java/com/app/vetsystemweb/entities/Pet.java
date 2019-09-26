package com.app.vetsystemweb.entities;
// Generated 17-sep-2019 16:08:27 by Hibernate Tools 4.3.1


import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

public class Pet  implements java.io.Serializable {

    private Integer petid;
    private Integer ownerid;
    private Owner owner;
    private String name;
    private String animaltype;
    private String breedname;
    private String birthplace;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private byte[] photo;
    private String base64Image;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private Set<Checkupreport> checkupreports = new HashSet<>();

    public Pet() {
    }

	
    public Pet(Integer petid, Integer ownerid, String name, String animaltype, String breedname, String birthplace, Date birthdate, String isactive, Date creationdate, String createdby) {
        this.petid = petid;
        this.ownerid = ownerid;
        this.name = name;
        this.animaltype = animaltype;
        this.breedname = breedname;
        this.birthplace = birthplace;
        this.birthdate = birthdate;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
    }
    public Pet(Integer petid, Integer ownerid, Owner owner, String name, String animaltype, String breedname, String birthplace, Date birthdate, byte[] photo, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set checkupreports) {
       this.petid = petid;
       this.ownerid = ownerid;
       this.owner = owner;
       this.name = name;
       this.animaltype = animaltype;
       this.breedname = breedname;
       this.birthplace = birthplace;
       this.birthdate = birthdate;
       this.photo = photo;
       this.isactive = isactive;
       this.creationdate = creationdate;
       this.createdby = createdby;
       this.lastupdate = lastupdate;
       this.updateby = updateby;
       this.checkupreports = checkupreports;
       this.base64Image = Base64.getEncoder().encodeToString(photo);
    }
   
    public Integer getPetid() {
        return this.petid;
    }
    
    public void setPetid(Integer petid) {
        this.petid = petid;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }
    
    public Owner getOwner() {
        return this.owner;
    }
    
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAnimaltype() {
        return this.animaltype;
    }
    
    public void setAnimaltype(String animaltype) {
        this.animaltype = animaltype;
    }
    public String getBreedname() {
        return this.breedname;
    }
    
    public void setBreedname(String breedname) {
        this.breedname = breedname;
    }
    public String getBirthplace() {
        return this.birthplace;
    }
    
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    public Date getBirthdate() {
        return this.birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public byte[] getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
    
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}


