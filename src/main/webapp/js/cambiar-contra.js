document.addEventListener("DOMContentLoaded", function () {
    //cargar el nombre de usuario desde sessionStorage
    const nombre = sessionStorage.getItem("username");
    if (nombre) {
        document.getElementById("modiNomb").value = nombre;
    } else {
        console.log('No se pudo guardar el nombre');
    }

    // Evento para el botón de cambio de contraseña
    document.getElementById("btnCambiarContraAlumno").addEventListener("click", cambiarPassword);
});

function cambiarPassword() {
    //Necesito obtnener el dni 
    let validarDNI = sessionStorage.getItem("suDNI");
    let pass = document.getElementById("modiPassActual").value;
    let newPass = document.getElementById("modiPassNew").value;
    let confNewPass = document.getElementById("modiConfNew").value;

    //validación básica
    if (!pass || !newPass || !confNewPass) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    //validación básica
    if (confNewPass !== newPass) {
        alert("Las nuevas contraseñas deben de ser iguales");
        return;
    }

    //Preparar datos para enviar
    const formData = new URLSearchParams();
    formData.append("dni", validarDNI);
    formData.append("contra", pass);
    formData.append("newContra", newPass);

    // Enviar la solicitud
    fetch("http://localhost:8080/PractiJalao/validarMedico", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: formData
    })
            .then(response => response.json())
            .then(data => {
                if (data.resultado === "ok") {
                    alert("SE CAMBIO LA CONTRASEÑA");
                    setTimeout(() => window.location.href = "segundo_paso_autenticacion.html", 2000);
                } else {
                    alert("DNI O CONTRA INCORRECTOS");
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
}
