package servlet;

import dao. DocenteJpaController;
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

@WebServlet(name = "editarMedico", urlPatterns = {"/editarMedico"})
public class editarMedico extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            String ediCODI, ediDNI, ediAPPA, ediAPMA, ediNOMB, ediNACI, ediLOGI;

            ediCODI = request.getParameter("ediCODI");
            ediDNI = request.getParameter("ediDNI");;
            ediAPMA = request.getParameter("ediAPMA");
            ediNOMB = request.getParameter("ediNOMB");
            ediNACI = request.getParameter("ediNACI");
            ediLOGI = request.getParameter("ediLOGI");

            try {
                 DocenteJpaController usuDAO = new  DocenteJpaController();
                
                SimpleDateFormat miFormato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaEDIT = miFormato.parse(ediNACI);
                
                 Docente pes = usuDAO.findDocente(Integer.parseInt(ediCODI));
                
                pes.setDniDoce(ediDNI);
                pes.setEspecDoce(ediAPMA);
                pes.setNombDoce(ediNOMB);
                pes.setFechaIngrDoce(fechaEDIT);
                pes.setUsuarioDoce(ediLOGI);
                usuDAO.edit(pes);
                
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultado", "modificado");
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
