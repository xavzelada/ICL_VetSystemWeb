package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.entities.Branch;
import com.app.vetsystemweb.entities.Checkup;
import com.app.vetsystemweb.entities.Checkupreport;
import com.app.vetsystemweb.entities.Company;
import com.app.vetsystemweb.entities.Employee;
import com.app.vetsystemweb.entities.Owner;
import com.app.vetsystemweb.entities.Pet;
import com.app.vetsystemweb.entities.Vet;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class VetSystemRestClient {

    final static Logger LOG = Logger.getLogger(VetSystemRestClient.class.getName());

    String BASEURL = "http://localhost:9090/ICL_VetSystemAPI";

    public VetSystemRestClient() throws NamingException {
        Context context = new InitialContext();
        BASEURL = (String) context.lookup("JNDI/VetSystemAPI");
    }
    public List<Company> ListCompanies() {
        LOG.info("executing method ListCompanies - VetSystemRestClient");
        String uri = BASEURL + "/company/";
        RestTemplate restTemplate = new RestTemplate();
        List<Company> myList = null;
        ResponseEntity<List<Company>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {
        });
        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            myList = response.getBody();

            for (Company c : myList) {
                if (c.getLogo() != null) {
                    c.setBase64Image(Base64.getEncoder().encodeToString(c.getLogo()));
                }
            }
            return response.getBody();
        }
        return null;
    }

    public Company getCompany(Company company) {
        LOG.info("executing method getCompany - VetSystemRestClient");

        String uri = BASEURL;

        Map<String, String> params = new HashMap<>();

        uri += "/company/byId?id={id}";
        params.put("id", company.getCompanyid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Company>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createCompany(Company company) {
        LOG.info("executing method createCompany - VetSystemRestClient");
        String uri = BASEURL + "/company";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();

        company.setIsactive("A");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, company, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdateCompany(Company company) {
        LOG.info("executing method UpdateCompany - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating company -> {0}", company.getCompanyid());
        String uri = BASEURL + "/company";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Company> entity = new HttpEntity<>(company);
        ResponseEntity<Company> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Company.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Company restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Branch> ListBranches(Company parent) {
        LOG.info("executing method ListBranches2 - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Branch>> response;

        if (parent.getCompanyid() == null) {
            uri += "/branch/";
        } else {
            uri += "/branch/byCompanyId?id={id}";
            params.put("id", parent.getCompanyid().toString());
        }
        LOG.info(uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Branch>>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public Branch getBranch(Branch myBranch) {
        LOG.info("executing method getBranch - VetSystemRestClient");
        String uri = BASEURL + "/branch/byId?id={id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", myBranch.getBranchid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Branch> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Branch>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public Boolean createBranch(Branch myBranch) {
        LOG.info("executing method createBranch - VetSystemRestClient");
        String uri = BASEURL + "/branch?ParentId={ParentId}";
       
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Company compTemp = new Company();

        compTemp.setCompanyid(myBranch.getCompanyid());
        myBranch.setCompany(compTemp);

        myBranch.setIsactive("A");

        params.put("ParentId", myBranch.getBranchid());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, myBranch, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public Boolean UpdateBranch(Branch myBranch) {
        LOG.info("executing method UpdateBranch - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating branch -> {0}", myBranch.getBranchid());
        String uri = BASEURL + "/branch";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Branch> entity = new HttpEntity<>(myBranch);
        ResponseEntity<Branch> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Branch.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Branch restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }
    
    
    
    // TODO: Create new method in ws
    public List<Employee> ListEmployees(Branch parent) {
        LOG.info("executing method ListEmployees - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Employee>> response;
        List<Employee> myResponseList = null;

        if (parent == null) {
            uri += "/employee/";
        } else {
            if (parent.getBranchid() != null) {
                uri += "/employee/byBranchId?id={id}";
                params.put("id", parent.getBranchid().toString());
            } else {
                uri += "/employee/";
            }
        }
        LOG.log(Level.INFO, "executing service -> {0}", uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }, params);

        HttpStatus status = response.getStatusCode();

        ///////////////////////////
        LOG.log(Level.INFO, "retrieving employees(Status) {0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            //TODO: Throw Exception
            return null;
        }
    }

    public Employee getEmployee(Employee employee) {
        LOG.info("executing method getEmployee - VetSystemRestClient");
        String uri = BASEURL + "/employee/byId?id={id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", employee.getEmployeeid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Employee>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            //TODO: Throw Exception
            return null;
        }
    }

    public Boolean createEmployee(Employee employee) {
        LOG.info("executing method createEmployee - VetSystemRestClient");
        String uri = BASEURL + "/employee?ParentId={ParentId}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Branch branchTemp = new Branch();

        branchTemp.setBranchid(employee.getBranchid());
        employee.setBranch(branchTemp);

        employee.setIsactive("A");

        params.put("ParentId", employee.getBranchid());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, employee, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public Boolean UpdateEmployee(Employee employee) {
        LOG.info("executing method UpdateEmployee - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating employee -> {0}", employee.getEmployeeid());
        String uri = BASEURL + "/employee";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Employee> entity = new HttpEntity<>(employee);
        ResponseEntity<Employee> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Employee.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Employee restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Vet> ListVets() {
        LOG.info("executing method ListVets - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Vet>> response;

        uri += "/vet/";
        LOG.info(uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Vet>>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public Vet getVet(Vet myVetTmp) {
        LOG.info("executing method getVet - VetSystemRestClient");

        String uri = BASEURL;

        Map<String, String> params = new HashMap<>();

        if (myVetTmp.getEmployeeid() == null) {
            uri += "/vet/byId?id={id}";
            params.put("id", myVetTmp.getVetid().toString());
        } else {
            uri += "/vet/byEmployeeId?id={id}";
            params.put("id", myVetTmp.getEmployeeid().toString());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Vet> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Vet>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createVet(Vet vet) {
        LOG.info("executing method createVet - VetSystemRestClient");
        String uri = BASEURL + "/vet?ParentId={ParentId}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Employee parentTemp = new Employee();

        parentTemp.setEmployeeid(vet.getEmployeeid());
        vet.setEmployee(parentTemp);

        vet.setIsactive("A");

        params.put("ParentId", vet.getEmployeeid());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, vet, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdateVet(Vet vet) {
        LOG.info("executing method UpdateVet - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating employee -> {0}", vet.getEmployeeid());
        String uri = BASEURL + "/vet";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Vet> entity = new HttpEntity<>(vet);
        ResponseEntity<Vet> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Vet.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Vet restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Owner> ListOwners() {
        LOG.info("executing method ListOwners - VetSystemRestClient");

        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Owner>> response;

        uri += "/owner/";
        LOG.info(uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Owner>>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public Owner getOwner(Owner myOwnerTmp) {
        LOG.info("executing method getOwner - VetSystemRestClient");

        String uri = BASEURL;

        Map<String, String> params = new HashMap<>();

        uri += "/owner/byId?id={id}";
        params.put("id", myOwnerTmp.getOwnerid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Owner> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Owner>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createOwner(Owner owner) {
        LOG.info("executing method createOwner - VetSystemRestClient");
        String uri = BASEURL + "/owner";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();

        owner.setIsactive("A");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, owner, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdateOwner(Owner owner) {
        LOG.info("executing method UpdateOwner - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating employee -> {0}", owner.getOwnerid());
        String uri = BASEURL + "/owner";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Owner> entity = new HttpEntity<>(owner);
        ResponseEntity<Owner> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Owner.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Owner restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Pet> ListPets(Owner myOwner) {
        LOG.info("executing method ListPets - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Pet>> response;
        List<Pet> myResponseList = null;

        if (myOwner == null) {
            uri += "/pet/";
        } else {
            if (myOwner.getOwnerid() != null) {
                uri += "/pet/byOwnerId?id={id}";
                params.put("id", myOwner.getOwnerid().toString());
            } else {
                uri += "/pet/";
            }
        }
        LOG.log(Level.INFO, "executing service -> {0}", uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pet>>() {
        }, params);

        HttpStatus status = response.getStatusCode();

        ///////////////////////////
        LOG.log(Level.INFO, "retrieving employees(Status) {0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            //TODO: Throw Exception
            return null;
        }
    }

    public Pet getPet(Pet myPetTmp) {
        LOG.info("executing method getPet - VetSystemRestClient");

        String uri = BASEURL;

        Map<String, String> params = new HashMap<>();

        uri += "/pet/byId?id={id}";
        params.put("id", myPetTmp.getPetid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Pet> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Pet>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createPet(Pet pet) {
        LOG.info("executing method createPet - VetSystemRestClient");
        String uri = BASEURL + "/pet?ParentId={ParentId}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Owner parentTemp = new Owner();

        parentTemp.setOwnerid(pet.getOwnerid());
        pet.setOwner(parentTemp);

        pet.setIsactive("A");

        params.put("ParentId", pet.getOwnerid());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, pet, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdatePet(Pet pet) {
        LOG.info("executing method UpdatePet - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating pet -> {0}", pet.getPetid());
        String uri = BASEURL + "/pet";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Pet> entity = new HttpEntity<>(pet);
        ResponseEntity<Pet> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Pet.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Pet restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Checkup> ListCheckups(Vet myVet) {
        LOG.info("executing method ListCheckups - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Checkup>> response;
        List<Checkup> myResponseList = null;

        if (myVet == null) {
            uri += "/checkup/";
        } else {
            if (myVet.getVetid() != null) {
                uri += "/checkup/byVetId?id={id}";
                params.put("id", myVet.getVetid().toString());
            } else {
                uri += "/checkup/";
            }
        }
        LOG.log(Level.INFO, "executing service -> {0}", uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Checkup>>() {
        }, params);

        HttpStatus status = response.getStatusCode();

        LOG.log(Level.INFO, "retrieving checkups(Status) {0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            //TODO: Throw Exception
            return null;
        }
    }

    public Checkup getCheckup(Checkup myCheckupTmp) {
        LOG.info("executing method getCheckup - VetSystemRestClient");

        String uri = BASEURL;
        Map<String, String> params = new HashMap<>();

        uri += "/checkup/byId?id={id}";
        params.put("id", myCheckupTmp.getCheckupid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Checkup> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Checkup>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createCheckup(Checkup checkup) {
        LOG.info("executing method createCheckup - VetSystemRestClient");
        String uri = BASEURL + "/checkup?ParentId={ParentId}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Vet parentTemp = new Vet();

        parentTemp.setVetid(checkup.getVetid());
        checkup.setVet(parentTemp);
        checkup.setIsactive("A");

        params.put("ParentId", checkup.getVetid());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, checkup, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdateCheckup(Checkup checkup) {
        LOG.info("executing method UpdateCheckup - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating checkup -> {0}", checkup.getCheckupid());
        String uri = BASEURL + "/checkup";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Checkup> entity = new HttpEntity<>(checkup);
        ResponseEntity<Checkup> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Checkup.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Checkup restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    
    
    public List<Checkupreport> ListCheckupreports(Checkup parent1, Pet parent2) {
        LOG.info("executing method ListCheckups - VetSystemRestClient");
        String uri = BASEURL;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ResponseEntity<List<Checkupreport>> response;
        List<Checkupreport> myResponseList = null;

        //@RequestMapping(value = "/checkupreport/byCheckupid", method = RequestMethod.GET)
        //@RequestMapping(value = "/checkupreport/byPetId", method = RequestMethod.GET)
        if (parent1 == null && parent2 == null) {
            uri += "/checkupreport/";
        } else {
            if (parent1 != null && parent1.getCheckupid() != null) {
                uri += "/checkupreport/byCheckupid?id={id}";
                params.put("id", parent1.getCheckupid().toString());
            } else if (parent2 != null && parent2.getPetid() != null) {
                uri += "/checkupreport/byPetId?id={id}";
                params.put("id", parent2.getPetid().toString());
            } else {
                uri += "/checkupreport/";
            }
        }
        LOG.log(Level.INFO, "executing service -> {0}", uri);
        response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Checkupreport>>() {
        }, params);

        HttpStatus status = response.getStatusCode();

        LOG.log(Level.INFO, "retrieving checkups(Status) {0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            //TODO: Throw Exception
            return null;
        }
    }

    public Checkupreport getCheckupreport(Checkupreport myCheckupreportTmp) {
        LOG.info("executing method getCheckupreport - VetSystemRestClient");

        String uri = BASEURL;
        Map<String, String> params = new HashMap<>();

        uri += "/checkupreport/byId?id={id}";
        params.put("id", myCheckupreportTmp.getCheckupreportid().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Checkupreport> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Checkupreport>() {
        }, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        if (status == HttpStatus.OK) {
            return response.getBody();
        }
        //TODO: Throw new exception
        return null;
    }

    public boolean createCheckupreport(Checkupreport checkupreport) {
        LOG.info("executing method createCheckupreport - VetSystemRestClient");
        String uri = BASEURL + "/checkupreport";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        Checkup parentTemp1 = new Checkup();
        parentTemp1.setCheckupid(checkupreport.getCheckupid());
        checkupreport.setCheckup(parentTemp1);

        Pet parentTemp2 = new Pet();
        parentTemp2.setPetid(checkupreport.getPetid());
        checkupreport.setPet(parentTemp2);

        checkupreport.setIsactive("A");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, checkupreport, String.class, params);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        String restCall = response.getBody();
        LOG.info(restCall);
        if (status == HttpStatus.CREATED) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

    public boolean UpdateCheckupreport(Checkupreport checkupreport) {
        LOG.info("executing method UpdateCheckupreport - VetSystemRestClient");
        LOG.log(Level.FINE, "Updating checkupreport -> {0}", checkupreport.getCheckupreportid());
        String uri = BASEURL + "/checkupreport";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Checkupreport> entity = new HttpEntity<>(checkupreport);
        ResponseEntity<Checkupreport> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Checkupreport.class);

        HttpStatus status = response.getStatusCode();
        LOG.log(Level.INFO, "status{0}", status);
        Checkupreport restCall = response.getBody();

        if (status == HttpStatus.OK) {
            return true;
        }
        //TODO: Throw new exception
        return false;
    }

}
