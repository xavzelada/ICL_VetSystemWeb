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
        <title>Checkup detail history</title>
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
                    <h4>Checkup detail history</h4>
                </div>
                <c:if test="${errorDescription!=null}">`
                    <div class="card-body">

                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </div>
                </c:if>
                <c:if test="${errorDescription==null}">

                    <div class="card-body">
                        <c:if test="${myCheckup != null}">
                            <a class="btn btn-primary btn-sm" href="newCheckupreport.htm?checkupid=${myCheckup.checkupid}">Create a new checkup</a>
                        </c:if>
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                        <div class="card">
                            <c:if test="${myCheckup != null}">
                                <h5 class="card-header">Checkup</h5>
                                <div class="card-body">
                                    <fmt:formatDate value="${myCheckup.checkupdate}" var="dateString" pattern="dd-MM-yyyy" />
                                    <p class="card-text">Vet name:
                                        <c:forEach items="${VetList}" var="c">
                                            ${c.key == myCheckup.vetid ? c.value : null}
                                        </c:forEach>
                                    </p>
                                    <p class="card-text">Checkup date: ${dateString}</p>
                                </div>
                            </c:if>
                            <c:if test="${myPet != null}">
                                <div class="row no-gutters" style="padding-left: 15px;">
                                    <div class="col-auto" style="margin: auto;">
                                        <c:if test="${myPet.base64Image!=null}">
                                            <img src="data:image/png;base64,${myPet.base64Image}" class="img-fluid" style="height:150px; object-fit: cover;"/>
                                        </c:if>
                                    </div>
                                    <div class="col">
                                        <div class="card-block px-2">
                                            <h4 class="card-title">${myPet.name}</h4>
                                            <fmt:formatDate value="${myPet.birthdate}" var="dateString" pattern="dd-MM-yyyy" />
                                            <p class="card-text">Animal type: 
                                                <c:choose>
                                                    <c:when test = "${myPet.animaltype == '1'}">Dog</c:when>
                                                    <c:when test = "${myPet.animaltype == '2'}">Cat</c:when>
                                                    <c:when test = "${myPet.animaltype == '3'}">Bird</c:when>
                                                    <c:when test = "${myPet.animaltype == '4'}">Snake</c:when>
                                                    <c:when test = "${myPet.animaltype == '5'}">Turtle</c:when>
                                                    <c:when test = "${myPet.animaltype == '6'}">Horse</c:when>
                                                    <c:otherwise>unknown</c:otherwise>
                                                </c:choose></p>
                                            <p class="card-text">Breed: ${myPet.breedname}</p>
                                            <p class="card-text">Birth place: ${myPet.birthplace}</p>
                                            <p class="card-text">Birth date: ${dateString}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div id="accordion">
                            <input type="hidden" id="source" name="source" value="${source}">
                            <c:forEach var="reg" items="${listResponse}" varStatus="counter">	
                                <div class="card">
                                    <div class="card-header" id="heading${counter.count}">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${counter.count}" aria-expanded="false" aria-controls="collapse${counter.count}">
                                                Checkup report ${reg.checkupreportid}
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapse${counter.count}" class="collapse" aria-labelledby="heading${counter.count}" data-parent="#accordion">
                                        <div class="card-body">
                                            <!-- p class="card-text">Checkup report ID: ${reg.checkupreportid}</p -->
                                            <p class="card-text">Vet name:
                                                <c:forEach items="${VetList}" var="c">
                                                    ${c.key == reg.checkup.vetid ? c.value :  null}
                                                </c:forEach>
                                            </p>
                                            <p class="card-text">Pet name: ${reg.pet.name}</p>
                                            <label for="reportnotes">Report notes</label>
                                            <textarea class="form-control" id="reportnotestext" rows="10" disabled>${reg.reportnotestext}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>
