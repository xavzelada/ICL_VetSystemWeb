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
        <title>Owners registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this owner?")) {
                    return true;
                } else {
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Owners registered</h4>
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
                        <a class="btn btn-primary btn-sm" href="newOwner.htm">Create a new owner</a>
                        <input type="hidden" id="source" name="source" value="${source}">

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Owner ID</th>
                                    <th>Name</th>
                                    <th>Birthdate</th>
                                    <th>Phone number</th>
                                    <th>Email</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reg" items="${listResponse}">
                                    <tr>
                                        <td>${reg.ownerid}</td>
                                        <td>${reg.name}&nbsp;${reg.surname}</td>
                                        <td>
                                            <fmt:formatDate type="date" value="${reg.birthdate}"  pattern="dd-MM-yyyy"  />
                                        </td>
                                        <td>${reg.phonenumber1}</td>
                                        <td>${reg.email}</td>
                                        <td>
                                            <div class="switch">
                                                <label>
                                                    <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusOwner.htm?ownerid=${reg.ownerid}&source=${source}'">
                                                </label>
                                            </div>

                                        </td>
                                        <td>

                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editOwner.htm?ownerid=${reg.ownerid}">Edit</a>
                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../pets/adminOwner.htm?ownerid=${reg.ownerid}">Admin</a> 
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <a class="btn btn-primary btn-sm" href="newOwner.htm">Create a new owner</a>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
