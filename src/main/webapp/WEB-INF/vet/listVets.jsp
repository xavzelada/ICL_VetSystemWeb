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
        <title>Vets registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this vet?")) {
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
                    <h4>Vets registered</h4>
                </div>
                <div class="card-body">
                    <input type="hidden" id="branchid" name="source" value="${source}">
                    <table class="table table-hover">
                        <thead>
                            <tr>                               
                                <th></th>
                                <th>License ID</th>
                                <th>License Issued by</th>
                                <th>License Type</th>
                                <th>Employee ID</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reg" items="${listResponse}">
                                <tr>
                                    <td>
                                    </td>
                                    <td>${reg.licenseid}</td>
                                    <td>${reg.licenseissuedby}</td>
                                    <td>
                                        <select class="custom-select " disabled>
                                            <option value="1" ${reg.licensetype == '1' ? 'selected' : ''}>Student</option>
                                            <option value="2" ${reg.licensetype == '2' ? 'selected' : ''}>Temporal</option>
                                            <option value="3" ${reg.licensetype == '3' ? 'selected' : ''}>Vet senior</option>
                                        </select>
                                    </td>
                                    <td>${reg.employeeid}</td>
                                    <td>
                                        <div class="switch">
                                            <label>
                                                <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusVet.htm?vetid=${reg.vetid}&source=${source}'">
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editVet.htm?vetid=${reg.vetid}">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                </div>
            </div>
        </div>v
    </body>
</html>
