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
        <title>Create a new vet</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Register a new vet</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">                   
                    <form method="POST" action="VetDetail.htm">
                        <input type="hidden" id="vetid" name="vetid" value="${Vet.vetid}">
                        <input type="hidden" id="isactive" name="isactive" value="${Vet.isactive}">
                        <input type="hidden" id="employeeid" name="employeeid" value="${Employee.employeeid}">

                        <fieldset class="form-group">
                            <label for="_employeeid" class="bmd-label-floating">Employee ID </label>
                            <input type="text" name="_employeeid" class="form-control" disabled value="${Employee.employeeid}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="_employeeName" class="bmd-label-floating">Employee name </label>
                            <input type="text" name="_employeeName" class="form-control" disabled value="${Employee.name} ${Employee.surname}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="licenseid" class="bmd-label-floating">License ID </label>
                            <input type="text" name="licenseid" class="form-control" required onpaste="return false;" value="${Vet.licenseid}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="licenseissuedby" class="bmd-label-floating">License issued by </label>
                            <input type="text" name="licenseissuedby" class="form-control" required onpaste="return false;" value="${Vet.licenseissuedby}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="_licensetype" class="bmd-label-floating">License Type </label>
                            <select class="custom-select mr-sm-2 " name="_licensetype">
                                <option value="-" ></option>
                                <option value="1" ${Vet.licensetype == '1' ? 'selected' : ''}>Student</option>
                                <option value="2" ${Vet.licensetype == '2' ? 'selected' : ''}>Temporal</option>
                                <option value="3" ${Vet.licensetype == '3' ? 'selected' : ''}>Vet senior</option>
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
