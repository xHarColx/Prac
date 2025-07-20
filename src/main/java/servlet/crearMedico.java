package servlet;


import dao.DocenteJpaController;
import dto. Docente;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "crearMedico", urlPatterns = {"/crearMedico"})
public class crearMedico extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            String txtDNI, txtAPMA, txtNOMB, txtNACI, txtLOGI, txtPASS;

            txtDNI = request.getParameter("txtDNI");

            txtAPMA = request.getParameter("txtAPMA");
            txtNOMB = request.getParameter("txtNOMB");
            txtNACI = request.getParameter("txtNACI");
            txtLOGI = request.getParameter("txtLOGI");
            txtPASS = request.getParameter("txtPASS");

            try {
                DocenteJpaController usuDAO = new  DocenteJpaController();

                SimpleDateFormat miFormato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNEW = miFormato.parse(txtNACI);

                // Hashear la contraseña antes de guardarla
                String hashedContra = BCrypt.hashpw(txtPASS, BCrypt.gensalt(12));

                Docente p = new  Docente(0, txtDNI, txtNOMB, txtAPMA, fechaNEW, txtLOGI, hashedContra, "", false);

                usuDAO.create(p);

                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultado", "ok");
                jSONObject.put("mensaje", "Usuario creado exitosamente");
                out.print(jSONObject.toString());
            } catch (Exception ex) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultado", "error");
                jSONObject.put("mensaje", ex.getMessage());
                out.print(jSONObject.toString());
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
