$(document).ready(function () {

    new DataTable('#medicos', {
        language: {
            decimal: "",
            emptyTable: "No hay datos",
            info: "Mostrando desde el _START_ al _END_ del total de _TOTAL_ registros",
            infoEmpty: "Mostrando desde el 0 al 0 del total de  0 registros",
            infoFiltered: "(Filtrados del total de _MAX_ registros)",
            infoPostFix: "",
            thousands: ",",
            lengthMenu: "Mostrar _MENU_ registros por página",
            loadingRecords: "Cargando...",
            processing: "Procesando...",
            search: "Buscar:",
            zeroRecords: "No se ha encontrado nada a través de ese filtrado.",
            paginate: {
                first: "Primero",
                last: "Última",
                next: "Siguiente",
                previous: "Anterior"
            },
            aria: {
                sortAscending: ": activar para ordenar la columna ascendente",
                sortDescending: ": activar para ordenar la columna descendente"
            }
        },
        ajax: {
            url: "http://localhost:8080/PractiJalao/listarMedico",
            type: "GET",
            data: {
                opcion: 1
            },
            dataSrc: ""
        },
        columns: [
            {data: 'codiEstdWeb'},
            {data: 'ndniEstdWeb'},
            {data: 'appaEstdWeb'},
            {data: 'apmaEstdWeb'},
            {data: 'nombEstdWeb'},
            {data: 'fechNaciEstdWeb',
                render: function (data) {
                    return convertirFecha(data);
                }
            },
            {data: 'logiEstd'},
            {
                data: 'codiEstdWeb', // Columna para colocar los botones
                render: function (data, type, row, meta) {
                    let para = {
                        'codi': row.codiEstdWeb,
                        'dni': row.ndniEstdWeb,
                        'appa': row.appaEstdWeb,
                        'apma': row.apmaEstdWeb,
                        'nomb': row.nombEstdWeb,
                        'naci': row.fechNaciEstdWeb,
                        'logi': row.logiEstd
                    };
                    let paratxt = JSON.stringify(para);
                    return "<button class='btn btn-warning' onclick=$.fn.editar('" + encodeURIComponent(paratxt) + "') \n\
                    data-toggle='modal' data-target='#exampleModal'><i class='fas fa-pencil-alt'></i></button>" +
                            "<button class='btn btn-danger' onclick=$.fn.eliminar('" + row.codiMedi + "') \n\
                            data-toggle='modal' data-target='#exampleModal'><i class='fas fa-trash-alt'></button>";
                }
            }
        ]
    });

    //Función para convertir la fecha
    function convertirFecha(fecha) {
        const fechaObj = new Date(fecha);
        return isNaN(fechaObj) ? '' : fechaObj.toISOString().split('T')[0];
    }

    // Botón de crear en la tabla
    $("#btnCrearAlumno").click(function () {
        event.preventDefault();

        // Obtener los datos del formulario
        let dniNEW = $("#txtDNI").val().trim();
        let appaNEW = $("#txtAPPA").val().trim();
        let apmaNEW = $("#txtAPMA").val().trim();
        let nombNEW = $("#txtNOMB").val().trim();
        let naciNEW = $("#txtNACI").val().trim();
        let logiNEW = $("#txtLOGI").val().trim();
        let passNEW = $("#txtPASS").val().trim();

        $.ajax({
            url: 'http://localhost:8080/PractiJalao/crearMedico',
            method: 'POST',
            data: {
                txtDNI: dniNEW,
                txtAPPA: appaNEW,
                txtAPMA: apmaNEW,
                txtNOMB: nombNEW,
                txtNACI: naciNEW,
                txtLOGI: logiNEW,
                txtPASS: passNEW
            },
            success: function (response) {
                console.log("Respuesta del servidor:", response);
                $("#crearModal").modal('hide');
                $('#medicos').DataTable().ajax.reload();
                alert('Medico creado exitosamente');

            },
            error: function (xhr, status, error) {
                console.error("Estado:", status, "Error:", error, "Respuesta:", xhr.responseText);
                alert("Hubo un error en la solicitud AJAX");
            }
        });
    });

    // Función para mostrar los datos del usuario en el modal de editar un registro
    $.fn.editar = function (code) {
        let obj = JSON.parse(decodeURIComponent(code));
        $("#ediCODI").val(obj.codi);
        $("#ediDNI").val(obj.dni);
        $("#ediAPPA").val(obj.appa);
        $("#ediAPMA").val(obj.apma);
        $("#ediNOMB").val(obj.nomb);
        $("#ediNACI").val(convertirFecha(obj.naci));
        $("#ediLOGI").val(obj.logi);
        $("#modificarModal").modal('show');
    };

    $("#btnModificarAlumno").click(function (event) {
        event.preventDefault();

        // Obtener los datos del formulario
        let codiEDIT = $("#ediCODI").val().trim();
        let dniEDIT = $("#ediDNI").val().trim();
        let appaEDIT = $("#ediAPPA").val().trim();
        let apmaEDIT = $("#ediAPMA").val().trim();
        let nombEDIT = $("#ediNOMB").val().trim();
        let naciEDIT = $("#ediNACI").val().trim();
        let logiEDIT = $("#ediLOGI").val().trim();

        $.ajax({
            url: 'http://localhost:8080/PractiJalao/editarMedico',
            method: 'POST',
            data: {
                ediCODI: codiEDIT,
                ediDNI: dniEDIT,
                ediAPPA: appaEDIT,
                ediAPMA: apmaEDIT,
                ediNOMB: nombEDIT,
                ediNACI: naciEDIT,
                ediLOGI: logiEDIT
            },
            success: function (response) {
                console.log("Respuesta del servidor:", response);
                $("#modificarModal").modal('hide');
                $('#medicos').DataTable().ajax.reload();
                alert('Medico editado exitosamente!');
            },
            error: function (jqXHR) {
                console.log("Error:", jqXHR.status, jqXHR.responseText); // Agregado para depurar
                alert('Error al modificar el registro.');
            }
        });
    });

    let codiEliminar = '';
    $.fn.eliminar = function (code) {
        codiEliminar = code;
        $("#eliminarCodi").text(code);
        $("#eliminarModal").modal('show');
    };

    $("#btnEliminarAlumno").click(function () {
        $.ajax({
            url: 'http://localhost:8080/PractiJalao/eliminarMedico',
            method: 'POST',
            data: {
                codiEli: codiEliminar
            },
            success: function (response) {
                console.log("Respuesta del servidor:", response);
                $("#eliminarModal").modal('hide');
                $('#medicos').DataTable().ajax.reload();
                alert('Medico eliminado exitosamente!');
            },
            error: function (jqXHR) {
                console.log("Error:", jqXHR.status, jqXHR.responseText); // Agregado para depurar
                alert('Error al eliminar el registro.');
            }
        });
    });
});


