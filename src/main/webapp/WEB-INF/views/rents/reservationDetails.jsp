<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">
					<h3 class="profile-username text-center">Detail de la reservation </h3>
                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">${reservation.id} </h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Client(s)</b> <a class="pull-right">1</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Voiture(s)</b> <a class="pull-right">1</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        
                        <div class="tab-content">
                            <div class="active tab-pane" id="users">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                    
                                     <h3 class="profile-username text-center">Client </h3>
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th>Age</th>
                                            <th>Email</th>
                                        </tr>
                                        
                                        <tr>
                                         	<td>${client.id}</td>
                                            <td>${client.nom}</td>
                                            <td>${client.prenom}</td>
                                            <td>${client.naissance}</td>
                                            <td>${client.email}</td>
                                        </tr>
                                        </table>
                                        <table class="table table-striped">
                                        <h3 class="profile-username text-center">Vehicule </h3>
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Constructeur</th>
                                            <th>Modele</th>
                                            <th>Nombre de place</th>
                                        </tr>
                                         
                                        <tr>
                                            <td>${vehicle.id}</td>
                                            <td>${vehicle.constructeur}</td>
                                            <td>${vehicle.modele}</td>
                                            <td>${vehicle.nb_places}</td>
                                        </tr>
                                        
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>