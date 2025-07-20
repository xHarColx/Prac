package servlet;

import com.google.gson.Gson;

import dao.DocenteJpaController;

import dto.Docente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "listarMedico", urlPatterns = {"/listarMedico"})
public class listarMedico extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            String opcion = request.getParameter("opcion");

            switch (opcion) {
                case "1":
                    DocenteJpaController usuDAO = new DocenteJpaController();
                    List<Docente> lista = usuDAO.findDocenteEntities(); //listar clientes
                    Gson g = new Gson();
                    String resultado = g.toJson(lista);
                    out.print(resultado);
                    break;
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
