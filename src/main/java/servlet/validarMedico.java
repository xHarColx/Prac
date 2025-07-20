package servlet;

import dao.DocenteJpaController;
import dto.Docente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "validarMedico", urlPatterns = {"/validarMedico"})
public class validarMedico extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try ( PrintWriter out = response.getWriter()) {

            String logi = request.getParameter("dni");
            String pass = request.getParameter("contra");

            try {

                DocenteJpaController usuDAO = new DocenteJpaController();

                Docente usuEncontrado = usuDAO.validarUsuario(new Docente(logi, pass));

                if (usuEncontrado == null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("resultado", "error");
                    jSONObject.put("mensaje", "Usuario o contraseña incorrectos");
                    out.print(jSONObject.toString());
                } else {
                    JSONObject jSONObject = new JSONObject();

                    jSONObject.put("suDNI", usuEncontrado.getDniDoce());
                    jSONObject.put("suLOGI", usuEncontrado.getUsuarioDoce());
                    jSONObject.put("suNOMB", usuEncontrado.getNombDoce());
                    jSONObject.put("idUsuario", usuEncontrado.getCodiDoce());

                    if (!usuEncontrado.getMfaEstado()) {
                        // MFA no configurado, enviar para configurar
                        jSONObject.put("resultado", "mfa_config");
                    } else {
                        // MFA activado, pedir segundo paso
                        jSONObject.put("resultado", "mfa_required");
                    }

                    out.print(jSONObject.toString());
                }
            } catch (Exception ex) {
                out.print(ex.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            String logi, pass, newpass;
            logi = request.getParameter("dni");
            pass = request.getParameter("contra");
            newpass = request.getParameter("newContra");

            DocenteJpaController aluDAO = new DocenteJpaController();
            String aluCambioPass = aluDAO.cambiarClave(new Docente(logi, pass), newpass);
            
            JSONObject jSONObject = new JSONObject();

            if (aluCambioPass == null) {
                jSONObject.put("resultado", "error");
                jSONObject.put("mensaje", "Dni o Contraseña Actual Incorrecto");
            } else {
                jSONObject.put("resultado", "ok");
                jSONObject.put("mensaje", "Se cambió la clave correctamente");
            }
            out.print(jSONObject.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
