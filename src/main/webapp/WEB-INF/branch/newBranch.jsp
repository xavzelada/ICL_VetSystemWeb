<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Create a new branch</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Register a new Branch</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">
                    <form method="POST" action="newBranch.htm">
                        <input type="hidden" id="_companyid" name="_companyid" value="${Company.companyid}">
                        <input type="hidden" id="branchid" name="branchid" value="${Branch.branchid}">
                        <input type="hidden" id="isactive" name="isactive" value="${Branch.isactive}">
                        <fieldset class="form-group">
                            <label for="companyid" class="bmd-label-floating">Company: </label>
                            <c:if test="${Branch.companyid != null}">
                                <input type="hidden" id="isactive" name="companyid" value="${Branch.companyid}">
                            </c:if>
                            <select  class="form-control" ${Branch.companyid == null ? 'name="companyid"' : 'disabled'}>
                                <option value="${c.key}" ${Branch.companyid == '' ? 'selected' : ''}>${c.value}</option>
                                <c:forEach items="${CompanyList}" var="c">
                                    <option value="${c.key}" ${c.key == Branch.companyid ? 'selected' : ''}>${c.value}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="name" class="bmd-label-floating">Branch name: </label>
                            <input type="text" name="name" class="form-control" required onpaste="return false;" value="${Branch.name}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="address" class="bmd-label-floating">Branch address: </label>
                            <input type="text" name="address" class="form-control" required onpaste="return false;" value="${Branch.address}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="location" class="bmd-label-floating">Location: </label>
                            <input type="text" name="location" class="form-control" required onpaste="return false;" value="${Branch.location}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber1" class="bmd-label-floating">Phone number 1: </label>
                            <input type="text" name="phonenumber1" class="form-control" required onpaste="return false;" value="${Branch.phonenumber1}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber2" class="bmd-label-floating">Phone number 2: </label>
                            <input type="text" name="phonenumber2" class="form-control" required onpaste="return false;" value="${Branch.phonenumber2}">
                        </fieldset>
                        <input type="submit" value="Submit" class="btn btn-primary btn-sm">
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
