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
        <title>Create a new checkup</title>
    </head>
    <body>
        <div class="container mt-4 col-lg-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Create a new checkup</h4>
                    <c:if test="${errorDescription!=null}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            ${errorDescription}
                        </div>
                    </c:if>
                </div>
                <div class="card-body ">                   
                    <form method="POST" action="newCheckupreport.htm" enctype="multipart/form-data">
                        <input type="hidden" name="checkupid" value="${myCheckup.checkupid}">
                        <input type="hidden" name="checkupreportid" value="${Checkupreport.checkupreportid}">
                        <input type="hidden" name="isactive" value="${Checkupreport.isactive}">
                        <fieldset class="form-group">
                            <label for="vetid" class="bmd-label-floating">Vet name </label>
                            <c:if test="${myCheckup.vetid != null}">
                                <input type="hidden" id="branchid" name="vetid" value="${myCheckup.vetid}">
                            </c:if>
                            <select  class="form-control" ${myCheckup.vetid == null ? 'name="vetid"' : 'disabled'}>
                                <option value="${c.key}" ${myCheckup.vetid == '' ? 'selected' : ''}>${c.value}</option>
                                <c:forEach items="${VetList}" var="c">
                                    <option value="${c.key}" ${c.key == myCheckup.vetid ? 'selected' : ''}>${c.value}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="petid" class="bmd-label-floating">Pet name </label>
                            <c:if test="${Checkupreport.petid != null}">
                                <input type="hidden" id="branchid" name="petid" value="${Checkupreport.petid}">
                            </c:if>
                            <select  class="form-control" ${Checkupreport.petid == null ? 'name="petid"' : 'disabled'}>
                                <option value="${c.key}" ${Checkupreport.petid == '' ? 'selected' : ''}>${c.value}</option>
                                <c:forEach items="${PetList}" var="c">
                                    <option value="${c.key}" ${c.key == Checkup.petid ? 'selected' : ''}>${c.value}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="reportnotestext">Report notes</label>
                            <form:textarea class="form-control" path="Checkupreport.reportnotestext" rows="3" />
                            <!--textarea class="form-control" id="reportnotestext" rows="10">${Checkupreport.reportnotestext}</textarea -->
                        </fieldset>        
                        <input type="submit" value="Submit" class="btn btn-primary btn-sm">
                        <a class="btn btn-primary btn-sm" href="javascript:history.back()">Back</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
