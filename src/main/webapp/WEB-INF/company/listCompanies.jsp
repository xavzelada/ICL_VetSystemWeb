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
        <title>Companies registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this company?")) {
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
                    <h4>Companies registered</h4>
                </div>
                <div class="card-body">
                    <a class="btn btn-primary btn-sm" href="newCompany.htm">Create a new company</a>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Address</th>
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
                                        <c:if test="${reg.base64Image!=''}">
                                            <img src="data:image/png;base64,${reg.base64Image}"  style="height:25px; object-fit: cover; "/>
                                        </c:if>
                                    </td>
                                    <td>${reg.name}</td>
                                    <td>${reg.address}</td>
                                    <!-- td>${reg.logo}</td -->                                    
                                    <td>
                                        <div class="switch">
                                                <label>
                                                    <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusCompany.htm?companyid=${reg.companyid}'">
                                                </label>
                                            </div>
                                    </td>
                                    <td>
                                        <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editCompany.htm?companyid=${reg.companyid}">Edit</a>
                                    </td>
                                    <td>
                                        <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../branches/adminCompany.htm?companyid=${reg.companyid}">Admin</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <a class="btn btn-primary btn-sm" href="newCompany.htm">Create a new company</a>
                    <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                </div>
            </div>
        </div>
    </body>
</html>
