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

@WebServlet(name = "verificarMFACodigo", urlPatterns = {"/verificarMFACodigo"})
public class verificarMFACodigo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
           
            String user = request.getParameter("user");
            String codigo = request.getParameter("codigo");

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
                    boolean valido = gAuth.authorize(secret, Integer.parseInt(codigo));
                    if (valido) {
                        json.put("resultado", "ok");
                    } else {
                        json.put("resultado", "error");
                        json.put("mensaje", "Código MFA inválido");
                    }
                } catch (NumberFormatException ex) {
                    json.put("resultado", "error");
                    json.put("mensaje", "Código MFA no válido");
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
