<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js" integrity="sha384-fA23ZRQ3G/J53mElWqVJEGJzU0sTs+SvzG8fXVWP+kJQ1lwFAOkcUOysnlKJC33U" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js" integrity="sha384-CauSuKpEqAFajSpkdjv3z9t8E7RlpJ1UP0lKM/+NdtSarroVKu069AlsRPKkFBz9" crossorigin="anonymous"></script>
        <script>$(document).ready(function () {
                $('body').bootstrapMaterialDesign();
            });</script>
        <title>Employees registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this employee?")) {
                    return true;
                    //document.getElementById('anchortag').href += "?isTrue=true";
                } else {
                    return false;
                    //document.getElementById('anchortag').href += "?isTrue=false";
                }
                //return true;
            }
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Employees registered</h4>
                    <c:if test="${myBranch.name != null}">
                        <h8>${myBranch.name}</h8>
                        </c:if>
                </div>
                <div class="card-body">
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </c:if>
                    <c:if test="${errorDescription==null}">
                        <a class="btn btn-primary btn-sm" href="../index.htm">Home</a>
                        <a class="btn btn-primary btn-sm" href="newEmployee.htm">Create a new Employee</a>
                        <input type="hidden" id="source" name="source" value="${source}">

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Name</th>
                                    <!-- th>Surname</th -->
                                    <th>Birthdate</th>
                                    <!-- th>Address</th -->
                                    <th>Phone number 1</th>
                                    <!-- th>Phone number 2</th-->
                                    <th>Personal email</th>
                                    <!-- th>Corporative email</th-->
                                    <th>role</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reg" items="${listResponse}">
                                    <tr>
                                        <td>
                                        </td>
                                        <td>${reg.name}&nbsp;${reg.surname}</td>
                                        <!-- td>${reg.surname}</td -->
                                        <td>
                                            <fmt:formatDate type="date" value="${reg.birthdate}"  pattern="dd-MM-yyyy"  />
                                        </td>
                                        <!-- td>${reg.address}</td -->
                                        <td>${reg.phonenumber1}</td>
                                        <!-- td>${reg.phonenumber2}</td -->
                                        <td>${reg.personalemail}</td>
                                        <!-- td>${reg.corporativeemail}</td -->
                                        <td>
                                            <select class="custom-select mr-sm-2 " disabled>
                                                <option value="1" ${reg.role == '1' ? 'selected' : ''}>Admin</option>
                                                <option value="2" ${reg.role == '2' ? 'selected' : ''}>Employee</option>
                                                <option value="3" ${reg.role == '3' ? 'selected' : ''}>Supervisor</option>
                                                <option value="4" ${reg.role == '4' ? 'selected' : ''}>Vet</option>
                                                <option value="5" ${reg.role == '5' ? 'selected' : ''}>Owner</option>
                                            </select>
                                        </td>
                                        <td>
                                            <div class="switch">
                                                <label>
                                                    <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusEmployee.htm?employeeid=${reg.employeeid}&source=${source}'">
                                                </label>
                                            </div>

                                        </td>
                                        <td>

                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editEmployee.htm?employeeid=${reg.employeeid}">Edit</a>
                                        </td>
                                        <td>
                                            <c:if test="${reg.role == '4'}">
                                                <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../vets/editVet.htm?vetid=&employeeid=${reg.employeeid}">Admin</a> 
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
