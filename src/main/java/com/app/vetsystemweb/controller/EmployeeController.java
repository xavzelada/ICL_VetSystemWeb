package com.app.vetsystemweb.controller;

import com.app.vetsystemweb.config.Util;
import com.app.vetsystemweb.entities.Branch;
import com.app.vetsystemweb.entities.Company;
import com.app.vetsystemweb.entities.Employee;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

    final static Logger LOG = Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    VetSystemRestClient restClient;

    @Autowired
    Util utilService;

    Employee myEmployee;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOG.info("Loading EmployeeController");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("employees/index.htm")
    public ModelAndView MenuHandler() {
        return EmployeeListHandler();
    }

    private ModelAndView getEmployeeList(String branchId, String source) {
        ModelAndView mav = new ModelAndView();
        try {
            System.out.println("ejecuta metodo getEmployeeList - EmployeeController");
            System.out.println("branchId -> " + branchId);

            Branch myBranch = new Branch();

            if (branchId != null && !branchId.isEmpty()) {
                myBranch.setBranchid(utilService.castInteger(branchId));
                myBranch = restClient.getBranch(myBranch);
            }
            if (myBranch != null) {
                List<Employee> myResponseList = restClient.ListEmployees(myBranch);
                if (myResponseList != null) {
                    if (myResponseList.size() > 1) {
                        Comparator<Employee> compareById = (Employee o1, Employee o2) -> o1.getEmployeeid().compareTo(o2.getEmployeeid());
                        Collections.sort(myResponseList, compareById);
                    }
                }

                mav.addObject("myBranch", myBranch);
                mav.addObject("listResponse", myResponseList);
                mav.addObject("source", source);
            } else {
                mav.addObject("errorDescription", "The branch selected doesn't exist");
            }
            mav.setViewName("employee/listEmployees");
            return mav;

        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("employees/newEmployee.htm");
            return mav;
        }
    }

    @RequestMapping(value = "employees/listEmployees.htm")
    public ModelAndView EmployeeListHandler() {
        return getEmployeeList(null, "listEmployees.htm");
    }

    @RequestMapping(value = "employees/adminBranch.htm", method = RequestMethod.GET)
    public ModelAndView EmployeeListHandler(String branchid) {
        return getEmployeeList(branchid, "adminBranch.htm?branchid=" + branchid);
    }

    /// change to get branch list
    private Map<Integer, String> getBranchList(Integer companyId) {

        Company parent = new Company();
        if (companyId != null) {
            parent.setCompanyid(companyId);
        }

        List<Branch> myResponseList = restClient.ListBranches(parent);
        Map<Integer, String> TempList = new LinkedHashMap<>();
        if (myResponseList != null) {
            if (myResponseList.size() > 1) {
                Comparator<Branch> compareByid = (Branch o1, Branch o2) -> o1.getBranchid().compareTo(o2.getBranchid());
                Collections.sort(myResponseList, compareByid);
            }

            for (Branch c : myResponseList) {
                if (c.getIsactive().equalsIgnoreCase("A")) {
                    TempList.put(c.getBranchid(), c.getName());
                }
            }
        }

        return TempList;
    }

    @RequestMapping(value = "employees/newEmployee.htm", method = RequestMethod.GET)
    public ModelAndView NewEmployeeHandler(String companyid) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("employee/newEmployee");

        Map<Integer, String> myList = getBranchList(utilService.castInteger(companyid));
        mav.addObject("BranchList", myList);

        Employee temp = new Employee();
        temp.setEmployeeid(-1);
        mav.addObject("Employee", temp);

        return mav;
    }

    @RequestMapping(value = "employees/editEmployee", method = RequestMethod.GET)
    public ModelAndView EditEmployeeHandler(@RequestParam String employeeid, String companyid) {
        //System.out.println("ejecuta metodo NewEmployeeHandler - EmployeeController, employee id " + employeeid);
        ModelAndView mav = new ModelAndView();
        if (employeeid != null) {

            Employee myEmployeeTmp = new Employee();

            myEmployeeTmp.setEmployeeid(utilService.castInteger(employeeid));

            myEmployee = restClient.getEmployee(myEmployeeTmp);
            if (myEmployee.getPhoto() != null) {
                myEmployee.setBase64Image(Base64.getEncoder().encodeToString(myEmployee.getPhoto()));
            }
            mav.addObject("Employee", myEmployee);
        } else {
            mav.addObject("Employee", new Employee());
        }

        Map<Integer, String> myList = getBranchList(utilService.castInteger(companyid));
        mav.addObject("BranchList", myList);

        mav.setViewName("employee/newEmployee");
        return mav;
    }

    @RequestMapping(value = "employees/newEmployee.htm", method = RequestMethod.POST)
    public ModelAndView NewEmployeeHandler(@RequestParam CommonsMultipartFile file, HttpSession session, Employee employee) {
        ModelAndView mav = new ModelAndView();
        System.out.println("Executing methor NewEmployeeHandler - EmployeeController");
        System.out.println("employee -> " + ReflectionToStringBuilder.toString(employee));
        //System.out.println("file -> " + file);

        try {
            if (myEmployee == null) {
                myEmployee = new Employee();
            }

            if (myEmployee.getPhoto() != null) {
                employee.setPhoto(myEmployee.getPhoto());
            }

            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                employee.setPhoto(bytes);
            }

            if (employee.getEmployeeid() == -1) {
                Branch tempBranch = new Branch();

                tempBranch.setBranchid(employee.getBranchid());
                employee.setBranch(tempBranch);
                employee.setIsactive("A");
                if (restClient.createEmployee(employee)) {
                    return new ModelAndView("redirect:/employees/listEmployees.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("employees/newEmployee.htm");
            } else {
                if (restClient.UpdateEmployee(employee)) {
                    return new ModelAndView("redirect:/employees/listEmployees.htm");
                }

                mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
                mav.setViewName("employees/newEmployee.htm");
            }
            return mav;
        } catch (RestClientException ex) {
            mav.addObject("errorDescription", "We know that an unexpected error has occurred, please try again later");
            LOG.warning(ex.getMessage());
            mav.setViewName("employees/newEmployee.htm");
            return mav;
        }
    }

    @RequestMapping(value = "employees/ChangeStatusEmployee", method = RequestMethod.GET)
    public ModelAndView ChangeStatusEmployeeHandler(@RequestParam String employeeid, @RequestParam String source) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("employee/listEmployees.htm");
        System.out.println("employeeid -> " + employeeid);
        System.out.println("source -> " + source);
        if (employeeid != null) {

            Employee tempEmp = new Employee();
            tempEmp.setEmployeeid(utilService.castInteger(employeeid));
            myEmployee = restClient.getEmployee(tempEmp);

            if (myEmployee.getIsactive().equalsIgnoreCase("A")) {
                myEmployee.setIsactive("I");
            } else {
                myEmployee.setIsactive("A");
            }

            if (restClient.UpdateEmployee(myEmployee)) {
                return new ModelAndView("redirect:/employees/" + source);
            }

        }

        return mav;
    }
}
