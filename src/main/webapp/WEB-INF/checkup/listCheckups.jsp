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
        <title>Checkups registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this Checkups?")) {
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
                    <h4>Checkups registered</h4>
                    <c:if test="${myVet.vetid != null}">
                        <h8>
                            <c:forEach items="${VetList}" var="c">
                                ${c.key == myVet.vetid ? c.value : null}
                            </c:forEach>
                        </h8>
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
                        <a class="btn btn-primary btn-sm" href="newCheckup.htm">Create a new checkup</a>
                        <input type="hidden" id="source" name="source" value="${source}">

                        <table class="table table-hover">
                            <thead>
                                <tr style="text-align:center">
                                    <th>Checkup ID</th>
                                    <th>Vet name</th>
                                    <th>Checkup date</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reg" items="${listResponse}">
                                    <tr style="text-align:center">
                                        <td>${reg.checkupid}</td>
                                        <td>
                                            <c:forEach items="${VetList}" var="c">
                                                ${c.key == reg.vetid ? c.value : null}
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <fmt:formatDate type="date" value="${reg.checkupdate}" />
                                        </td>
                                        <td>
                                            <div class="switch">
                                                <label>
                                                    <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusCheckup.htm?checkupid=${reg.checkupid}&source=${source}'">
                                                </label>
                                            </div>

                                        </td>
                                        <td>

                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editCheckup.htm?checkupid=${reg.checkupid}">Edit</a>
                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../checkupreports/adminCheckup.htm?checkupid=${reg.checkupid}">Detail</a> 
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <a class="btn btn-primary btn-sm" href="newCheckup.htm">Create a new checkup</a>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
