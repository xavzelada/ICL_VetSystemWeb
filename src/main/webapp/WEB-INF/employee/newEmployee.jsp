<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js" integrity="sha384-fA23ZRQ3G/J53mElWqVJEGJzU0sTs+SvzG8fXVWP+kJQ1lwFAOkcUOysnlKJC33U" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js" integrity="sha384-CauSuKpEqAFajSpkdjv3z9t8E7RlpJ1UP0lKM/+NdtSarroVKu069AlsRPKkFBz9" crossorigin="anonymous"></script>
        <script type="text/javascript" lnaguage="javascript">
            $(function ()
            {
                $("#datepicker").datepicker(
                        {
                            showOn: "both",
                            buttonImage: "image.jpg",
                            dateFormat: "yy-mm-dd",
                            buttonImageOnly: false,
                            minDate: +0, //you do not want to show previous date.
                            maxDate: +0   // you do not want to show next day.
                        });
            });
        </script>

        <script>$(document).ready(function () {
                $('body').bootstrapMaterialDesign();
            });</script>
        <script type="text/javascript">
            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if ((charCode > 31 && charCode < 48) || charCode > 57) {
                    return false;
                }
                return true;
            }
        </script>
        <title>Create a new employee</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Register a new employee</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">                   
                    <form method="POST" action="newEmployee.htm" enctype="multipart/form-data">
                        <input type="hidden" id="employeeid" name="employeeid" value="${Employee.employeeid}">
                        <input type="hidden" id="isactive" name="isactive" value="${Employee.isactive}">

                        <fieldset class="form-group">
                            <label for="companyid" class="bmd-label-floating">Branch </label>
                            <c:if test="${Employee.branchid != null}">
                                <input type="hidden" id="branchid" name="branchid" value="${Employee.branchid}">
                            </c:if>
                            <select  class="form-control" ${Employee.branchid == null ? 'name="branchid"' : 'disabled'}>
                                <option value="${c.key}" ${Employee.branchid == '' ? 'selected' : ''}>${c.value}</option>
                                <c:forEach items="${BranchList}" var="c">
                                    <option value="${c.key}" ${c.key == Employee.branchid ? 'selected' : ''}>${c.value}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="name" class="bmd-label-floating">Name </label>
                            <input type="text" name="name" class="form-control" required onpaste="return false;" value="${Employee.name}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="surname" class="bmd-label-floating">Surname </label>
                            <input type="text" name="surname" class="form-control" required onpaste="return false;" value="${Employee.surname}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label>Employee photo </label>
                            <br>
                            <c:if test="${Employee.base64Image!=null}">
                                <img src="data:image/png;base64,${Employee.base64Image}"  style="height:100px; object-fit: cover; "/>
                                <br>
                                <br>
                            </c:if>
                            <input class="form-control-file" type="file" name="file" />
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="bdate" class="bmd-label-floating">Birthdate </label>
                            <fmt:formatDate value="${Employee.birthdate}" var="dateString" pattern="yyyy-MM-dd" />
                            <input type="date" name="birthdate" class="form-control" required  value="${dateString}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="address" class="bmd-label-floating">Address </label>
                            <input type="text" name="address" class="form-control" required onpaste="return false;" value="${Employee.address}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber1" class="bmd-label-floating">Phone number 1 </label>
                            <input type="tel" name="phonenumber1" class="form-control" required onpaste="return false;" value="${Employee.phonenumber1}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber2" class="bmd-label-floating">Phone number 2 </label>
                            <input type="tel" name="phonenumber2" class="form-control" required onpaste="return false;" value="${Employee.phonenumber2}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="personalemail" class="bmd-label-floating">Personal email </label>
                            <input type="email" name="personalemail" class="form-control" required onpaste="return false;" value="${Employee.personalemail}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="corporativeemail" class="bmd-label-floating">Corpotative email </label>
                            <input type="email" name="corporativeemail" class="form-control" required onpaste="return false;" value="${Employee.corporativeemail}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="role" class="bmd-label-floating">Role </label>
                            <select class="custom-select mr-sm-2 " name="role">
                                <option value="1" ${Employee.role == '1' ? 'selected' : ''}>Admin</option>
                                <option value="2" ${Employee.role == '2' ? 'selected' : ''}>Employee</option>
                                <option value="3" ${Employee.role == '3' ? 'selected' : ''}>Supervisor</option>
                                <option value="4" ${Employee.role == '4' ? 'selected' : ''}>Vet</option>
                                <option value="5" ${Employee.role == '5' ? 'selected' : ''}>Owner</option>
                            </select>
                        </fieldset>                        
                        <input type="submit" value="Submit" class="btn btn-primary btn-sm">
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
