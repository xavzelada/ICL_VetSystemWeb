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
        <title>Create a new pet</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Register a new pet</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">                   
                    <form method="POST" action="newPet.htm" enctype="multipart/form-data">
                        <input type="hidden" name="petid" value="${Pet.petid}">
                        <input type="hidden" name="isactive" value="${Pet.isactive}">

                        <fieldset class="form-group">
                            <label for="ownerid" class="bmd-label-floating">Owner </label>
                            <c:if test="${Pet.ownerid != null}">
                                <input type="hidden" id="branchid" name="ownerid" value="${Pet.ownerid}">
                            </c:if>
                            <select  class="form-control" ${Pet.ownerid == null ? 'name="ownerid"' : 'disabled'}>
                                <option value="${c.key}" ${Pet.ownerid == '' ? 'selected' : ''}>${c.value}</option>
                                <c:forEach items="${OwnerList}" var="c">
                                    <option value="${c.key}" ${c.key == Pet.ownerid ? 'selected' : ''}>${c.value}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="name" class="bmd-label-floating">Name </label>
                            <input type="text" name="name" class="form-control" required onpaste="return false;" value="${Pet.name}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="animaltype" class="bmd-label-floating">Type </label>
                            <select class="form-control" name="animaltype" >
                                <option value="-" >Select</option>
                                <option value="1" ${Pet.animaltype == '1' ? 'selected' : ''}>Dog</option>
                                <option value="2" ${Pet.animaltype == '2' ? 'selected' : ''}>Cat</option>
                                <option value="3" ${Pet.animaltype == '3' ? 'selected' : ''}>Bird</option>
                                <option value="4" ${Pet.animaltype == '4' ? 'selected' : ''}>Snake</option>
                                <option value="5" ${Pet.animaltype == '5' ? 'selected' : ''}>Turtle</option>
                                <option value="6" ${Pet.animaltype == '6' ? 'selected' : ''}>Horse</option>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="breedname" class="bmd-label-floating">Breed name </label>
                            <input type="text" name="breedname" class="form-control" required onpaste="return false;" value="${Pet.breedname}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="birthplace" class="bmd-label-floating">Birth place </label>
                            <input type="text" name="birthplace" class="form-control" required onpaste="return false;" value="${Pet.birthplace}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="bdate" class="bmd-label-floating">Birthdate </label>
                            <fmt:formatDate value="${Pet.birthdate}" var="dateString" pattern="yyyy-MM-dd" />
                            <input type="date" name="birthdate" class="form-control" required  value="${dateString}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label>Pet photo </label>
                            <br>
                            <c:if test="${Pet.base64Image!=null}">
                                <img src="data:image/png;base64,${Pet.base64Image}"  style="height:100px; object-fit: cover; "/>
                                <br>
                                <br>
                            </c:if>
                            <input class="form-control-file" type="file" name="file" />
                        </fieldset>                                               
                        <input type="submit" value="Submit" class="btn btn-primary btn-sm">
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
