package servlet;



import dao.exceptions.NonexistentEntityException;
import dao.DocenteJpaController;

import dto.Docente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "eliminarMedico", urlPatterns = {"/eliminarMedico"})
public class eliminarMedico extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String codiEliminar = request.getParameter("codiEli");
            try {
                DocenteJpaController aluDAO = new  DocenteJpaController();
                 Docente al = aluDAO.findDocente(Integer.valueOf(codiEliminar));
                JSONObject miJson = new JSONObject();
                if (al == null) {
                    miJson.put("resultado", "error");
                    miJson.put("mensaje", "Alumno no encontrado");
                } else {
                    aluDAO.destroy(Integer.valueOf(codiEliminar));
                    miJson.put("resultado", "Alumno eliminado");
                }
                out.print(miJson.toString());
            } catch (NonexistentEntityException | NumberFormatException | JSONException ex) {
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
    }

}
