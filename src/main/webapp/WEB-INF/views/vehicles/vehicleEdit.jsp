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
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Modifier un Vehicule
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/cars/edit">
                            <div class="box-body">
                            <div class="form-group">
                                <label for="id" class="col-sm-2 control-label">#</label>
                                 <div class="col-sm-10">
                                <input readonly =True type="text" class="col-sm-2 control-label" id="id" name="id" value=${vehicle.id}>
                               </div>
                               </div>
                                <div class="form-group">
                                
                                    <label for="last_name" class="col-sm-2 control-label">Marque</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="constructeur" name="constructeur" value=${vehicle.constructeur} required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Modele</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modele" name="modele" value=${vehicle.modele} required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Nombre de places</label>

                                    <div class="col-sm-10">
                                        <input type="number" step="1"  min="3" max="9" class="form-control" id="seats" name="seats" value=${vehicle.nb_places} required>
                                    </div>
                                </div>
                                <div>
                                ${errorMessage}
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>