package servlet;

import com.warrenstrange.googleauth.GoogleAuthenticator;
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

@WebServlet(name = "verificarMFAInicial", urlPatterns = {"/verificarMFAInicial"})
public class verificarMFAInicial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        try (PrintWriter out = response.getWriter()) {

            String user = request.getParameter("user");
            String codigo1 = request.getParameter("codigo1");
            String codigo2 = request.getParameter("codigo2");

            DocenteJpaController usuDAO = new DocenteJpaController();
            Docente u = usuDAO.findDocente(Integer.parseInt(user));

            JSONObject json = new JSONObject();
            if (u == null) {
                json.put("resultado", "error");
                json.put("mensaje", "Usuario no encontrado");
            } else {
                GoogleAuthenticator gAuth = new GoogleAuthenticator();
                String secret = u.getMfaSecreto();

                try {
                    boolean valido1 = gAuth.authorize(secret, Integer.parseInt(codigo1));
                    boolean valido2 = gAuth.authorize(secret, Integer.parseInt(codigo2));

                    if (valido1 && valido2) {
                        u.setMfaEstado(true); // Ya activó MFA
                        usuDAO.edit(u);
                        json.put("resultado", "ok");
                    } else {
                        json.put("resultado", "error");
                        json.put("mensaje", "Codigos invalidos");
                    }
                } catch (NumberFormatException ex) {
                    json.put("resultado", "error");
                    json.put("mensaje", "Codigo(s) no valido(s)");
                } catch (Exception ex) {
                    json.put("resultado", "error");
                    json.put("mensaje", "Error al actualizar estado MFA");
                }
            }
            out.print(json.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
