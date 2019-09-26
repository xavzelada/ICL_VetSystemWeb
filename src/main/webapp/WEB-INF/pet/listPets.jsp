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
        <title>Pets registered</title>
        <script type="text/javascript">
            function ConfirmACtion() {
                if (confirm("Are you sure you want to change the status to this pet?")) {
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
                    <h4>Pet registered</h4>
                    <c:if test="${myOwner.name != null}">
                        <h8>${myOwner.name}</h8>
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
                        <a class="btn btn-primary btn-sm" href="newPet.htm">Create a new Pet</a>
                        <input type="hidden" id="source" name="source" value="${source}">

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Pet ID</th>
                                    <th>Name</th>
                                    <th>Animal Type</th>
                                    <th>Breed name</th>
                                    <th>Birthdate</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reg" items="${listResponse}">
                                    <tr>
                                        <td>${reg.petid}</td>
                                        <td>${reg.name}</td>
                                        <td>
                                            <select class="custom-select mr-sm-2 " disabled>
                                                <option value="1" ${reg.animaltype == '1' ? 'selected' : ''}>Dog</option>
                                                <option value="2" ${reg.animaltype == '2' ? 'selected' : ''}>Cat</option>
                                                <option value="3" ${reg.animaltype == '3' ? 'selected' : ''}>Bird</option>
                                                <option value="4" ${reg.animaltype == '4' ? 'selected' : ''}>Snake</option>
                                                <option value="5" ${reg.animaltype == '5' ? 'selected' : ''}>Turtle</option>
                                                <option value="6" ${reg.animaltype == '6' ? 'selected' : ''}>Horse</option>
                                            </select>
                                        </td>
                                        <td>${reg.breedname}</td>
                                        <td>
                                            <fmt:formatDate type="date" value="${reg.birthdate}" />
                                        </td>
                                        <td>
                                            <div class="switch">
                                                <label>
                                                    <input type="checkbox" ${reg.isactive == 'A' ? 'checked': ''} onclick="location.href = 'ChangeStatusPet.htm?petid=${reg.petid}&source=${source}'">
                                                </label>
                                            </div>

                                        </td>
                                        <td>

                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="editPet.htm?petid=${reg.petid}">Edit</a>
                                        </td>
                                        <td>
                                            <a ${reg.isactive == 'A' ? 'class="btn btn-primary btn-sm" ': 'class="btn btn-primary btn-sm  disabled" aria-disabled="true" '}  class="btn btn-primary btn-sm" href="../checkupreports/adminPet.htm?petid=${reg.petid}">History</a> 
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <a class="btn btn-primary btn-sm" href="newPet.htm">Create a new pet</a>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
