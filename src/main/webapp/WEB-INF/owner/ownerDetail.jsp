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
        <title>Create a new owner</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Register a new owner</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">                   
                    <form method="POST" action="newOwner.htm" enctype="multipart/form-data">
                        <input type="hidden" name="ownerid" value="${Owner.ownerid}">
                        <input type="hidden" name="isactive" value="${Owner.isactive}">
                        <fieldset class="form-group">
                            <label for="name" class="bmd-label-floating">Name </label>
                            <input type="text" name="name" class="form-control" required onpaste="return false;" value="${Owner.name}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="surname" class="bmd-label-floating">Surname </label>
                            <input type="text" name="surname" class="form-control" required onpaste="return false;" value="${Owner.surname}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label>Owner photo </label>
                            <br>
                            <c:if test="${Owner.base64Image!=null}">
                                <img src="data:image/png;base64,${Owner.base64Image}"  style="height:100px; object-fit: cover; "/>
                                <br>
                                <br>
                            </c:if>
                            <input class="form-control-file" type="file" name="file" />
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="birthdate" class="bmd-label-floating">Birthdate </label>
                            <fmt:formatDate value="${Owner.birthdate}" var="dateString" pattern="yyyy-MM-dd" />
                            <input type="date" name="birthdate" class="form-control" required  value="${dateString}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="address1" class="bmd-label-floating">Address 1 </label>
                            <input type="text" name="address1" class="form-control" required onpaste="return false;" value="${Owner.address1}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="address2" class="bmd-label-floating">Address 2 </label>
                            <input type="text" name="address2" class="form-control" required onpaste="return false;" value="${Owner.address2}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber1" class="bmd-label-floating">Phone number 1 </label>
                            <input type="tel" name="phonenumber1" class="form-control" required onpaste="return false;" value="${Owner.phonenumber1}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="phonenumber2" class="bmd-label-floating">Phone number 2 </label>
                            <input type="tel" name="phonenumber2" class="form-control" required onpaste="return false;" value="${Owner.phonenumber2}">
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="email" class="bmd-label-floating">Personal email </label>
                            <input type="email" name="email" class="form-control" required onpaste="return false;" value="${Owner.email}">
                        </fieldset>                   
                        <input type="submit" value="Submit" class="btn btn-primary btn-sm">
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
