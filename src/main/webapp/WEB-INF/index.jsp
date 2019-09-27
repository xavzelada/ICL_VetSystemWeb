<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/resources/css/Menu.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous"
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js" integrity="sha384-fA23ZRQ3G/J53mElWqVJEGJzU0sTs+SvzG8fXVWP+kJQ1lwFAOkcUOysnlKJC33U" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js" integrity="sha384-CauSuKpEqAFajSpkdjv3z9t8E7RlpJ1UP0lKM/+NdtSarroVKu069AlsRPKkFBz9" crossorigin="anonymous"></script>
        <title>VetSystem</title>
    </head>
    <body>
        <div class="container mt-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">
                    <h4>Welcom to VetSystem</h4>
                </div>


                <div class="card-body">
                    <h6>Menu</h6>
                    <div class="row">
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="http://www.matrudev.com/files/register-a-company-720x340.jpg" alt="">
                                <div class="overlay">
                                    <h2>Companies</h2>
                                    <p>
                                        <a href="companies/listCompanies.htm">Administration</a><br/>
                                        <a href="companies/newCompany.htm">New company</a>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="http://www.furtunalawyers.pro/wp-content/uploads/2012/07/branch-office.jpg" alt="">
                                <div class="overlay">
                                    <h2>Branches</h2>
                                    <p>
                                        <a href="branches/listBranches.htm">Administration</a><br/>
                                        <a href="branches/newBranch.htm">New branch</a>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="https://www.lowdermilk-associates.com/wp-content/uploads/sites/2231/2018/07/Screen-Shot-2018-07-26-at-2.12.42-PM-e1532695190753.png" alt="">
                                <div class="overlay">
                                    <h2>Employees</h2>
                                    <p>
                                        <a href="employees/listEmployees.htm">Administration</a><br/>
                                        <a href="employees/newEmployee.htm">New employee</a><br/>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="https://creditkarma-cms.imgix.net/wp-content/uploads/2018/03/options-huge-vet-bill.jpg" alt="">
                                <div class="overlay">
                                    <h2>Vets</h2>
                                    <p>
                                        <a href="vets/listVets.htm">Administration</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="https://cf.ltkcdn.net/dying/images/std/242575-675x450-family-with-cat-and-dog.jpg" alt="">
                                <div class="overlay">
                                    <h2>Owners</h2>
                                    <p>
                                        <a href="owners/listOwners.htm">Administration</a><br/>
                                        <a href="owners/newOwner.htm">New owner</a>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="https://dotspetcenter.com/wp-content/uploads/2019/05/title-1024x570.jpeg" alt="">
                                <div class="overlay">
                                    <h2>Pets</h2>
                                    <p>
                                        <a href="pets/listPets.htm">Administration</a><br/>
                                        <a href="pets/newPet.htm">New pet</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="hovereffect">
                                <img class="img-responsive" src="https://petplaceimages-embracepetinsura1.netdna-ssl.com/wp-content/uploads/2017/09/dog-checkup-large.jpg" alt="">
                                <div class="overlay">
                                    <h2>Checkups</h2>
                                    <p>
                                        <a href="checkups/listCheckups.htm">Checkups history</a><br/>
                                        <a href="checkups/newCheckup.htm">New checkup</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- a class="btn btn-primary btn-sm" href="register.htm ">Register...</a -->
                </div>
            </div>
        </div>
    </body>
    <!--body>
        <h1>Hello World!</h1>
        <h3>${name}</h3>
    </body -->
</html>
