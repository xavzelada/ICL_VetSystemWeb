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
        <title>Branches registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this branch?")) {
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
                    <h4>Branches registered</h4>
                    <c:if test="${myCompany.name != null}">
                        <h8>${myCompany.name}</h8>
                    </c:if>
                </div>
                <div class="card-body">
                    <a class="btn btn-primary btn-sm" href="../index.htm">Home</a>
                    <a class="btn btn-primary btn-sm" href="newBranch.htm">Create a new branch</a>
                    <input type="hidden" id="branchid" name="source" value="${source}">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Address</th>
                                <!-- th>Location</th -->
                                <th>Phone number 1</th>
                                <th>Phone number 2</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reg" items="${listResponse}">
                                <tr>
                                    <td>

                                    </td>
                                    <td>${reg.name}</td>
                                    <td>${reg.address}</td>
                                    <!-- td>${reg.location}</td -->
                                    <td>${reg.phonenumber1}</td>
                                    <td>${reg.phonenumber2}</td>
                                    <td>
                                        <div class="switch">
                                            <label>
                                                <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusBranch.htm?branchid=${reg.branchid}&source=${source}'">
                                            </label>
                                        </div>

                                    </td>
                                    <td>

                                    </td>
                                    <td>
                                        <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editBranch.htm?branchid=${reg.branchid}">Edit</a>
                                    </td>
                                    <td>
                                        <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../employees/adminBranch.htm?branchid=${reg.branchid}">Admin</a> 
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                </div>
            </div>
        </div>
    </body>
</html>
