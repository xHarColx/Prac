$(document).ready(function () {
    $("#btnIniciar").click(async function () {
        let logi = $("#validaUsuario").val().trim();
        let pass = $("#validaContra").val().trim();

        // Validar que los campos no estén vacíos
        if (!logi || !pass) {
            alert('Por favor, ingrese usuario y contraseña');
            return;
        }

        const url = `validarMedico?dni=${encodeURIComponent(logi)}&contra=${encodeURIComponent(pass)}`;

        try {
            const response = await fetch(url);
            if (!response.ok) {
                alert('Error en la conexión: ' + response.statusText);
                return;
            }
            const data = await response.json();

            if (data.resultado === "mfa_config") {
                sessionStorage.setItem("suDNI", data.suDNI);
                sessionStorage.setItem("username", data.suNOMB);

                // Usuario debe configurar MFA
                setCookie("idUsuarioBD", data.idUsuario, 1);
                setCookie("nombreUsuario", data.suNOMB, 1);
                window.location.href = "config_autentificacion_multifactor.html";

            } else if (data.resultado === "mfa_required") {
                sessionStorage.setItem("suDNI", data.suDNI);
                sessionStorage.setItem("username", data.suNOMB);

                // Usuario ya tiene MFA configurado, debe ingresar código MFA
                setCookie("idUsuarioBD", data.idUsuario, 1);
                setCookie("nombreUsuario", data.suNOMB, 1);

                window.location.href = "segundo_paso_autenticacion.html";

            } else if (data.resultado === "error") {
                alert("Usuario o contraseña incorrectos");

            } else {
                alert("Error inesperado en login");
            }
        } catch (error) {
            alert("Error de conexión al servidor");
            console.error(error);
        }
    });
});

//Función para obtner
function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(nameEQ) === 0)
            return decodeURIComponent(c.substring(nameEQ.length));
    }
    return null;
}
function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + d.toUTCString() + ";path=/";
}

