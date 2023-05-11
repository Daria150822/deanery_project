package com.example.deanery.web;

import com.example.deanery.model.Group;
import com.example.deanery.model.Student;
import com.example.deanery.model.User;
import com.example.deanery.services.GroupService;
import com.example.deanery.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;


@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/do/*"})
public class FrontControllerServlet extends HttpServlet {

    GroupService groupService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        groupService = (GroupService) config.getServletContext().getAttribute("groupService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        try {
            switch (pathInfo) {
                case "/login":
                    login(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/createGroup":
                    createGroup(request, response);
                    break;
                case "/editGroup":
                    editGroup(request, response);
                    break;
                case "/deleteGroup":
                    deleteGroup(request, response);
                    break;
                case "/deleteStudent":
                    deleteStudent(request, response);
                    break;
                case "/createStudent":
                    createStudent(request, response);
                    break;
                case "/editStudent":
                    editStudent(request, response);
                    break;
                case "/group":
                    getGroup(request, response);
                    break;
                case "/":
                case "/search":
                    handleSearch(request, response);
                default:
                    getGroups(request, response);
                    break;
            }
        } catch (RuntimeException ex) {
            error(request, response, "Oops, " + ex.getMessage());
        }

    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        if (groupId != null && !groupId.isEmpty()) {
            getGroup(request, response);
        } else {
            getGroups(request, response);
        }
    }

    private void getGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer groupId = Integer.parseInt(request.getParameter("groupId"));
        String searchText = request.getParameter("searchText");

        Group group = groupService.getGroupById(groupId);
        List<Student> students = groupService.getStudents(groupId, searchText);
        request.setAttribute("group", group);
        request.setAttribute("students", students);
        request.setAttribute("searchText", searchText);
        request.getRequestDispatcher("/WEB-INF/jsp/group.jsp").forward(request, response);
    }

    protected void getGroups(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchText = request.getParameter("searchText");
        Collection<Group> groups = groupService.getGroups(searchText);
        request.setAttribute("groups", groups);
        request.setAttribute("searchText", searchText);
        request.getRequestDispatcher("/WEB-INF/jsp/groups.jsp").forward(request, response);
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        String login = request.getParameter("login");
        User user = userService.getByLogin(login);
        if (user == null) {
            error(request, response, "Sorry, user with login '" + login + "' not exists");
            return;
        }
        String password = request.getParameter("password");

        if (!userService.checkPassword(user, password)) {
            error(request, response, "Sorry, wrong password");
            return;
        }

        request.getSession().setAttribute("user", user);
        response.sendRedirect(".");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(".");
    }

    protected void createGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        String text = request.getParameter("text");
        groupService.addGroup(text);
        response.sendRedirect("./");
    }

    protected void editGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        Group group = null;
        if (!isEmpty(request.getParameter("groupId"))) {
            Integer groupId = Integer.parseInt(request.getParameter("groupId"));
            group = groupService.getGroupById(groupId);
        }

        String text = request.getParameter("text");
        groupService.editGroup(group, text);

        if (!isEmpty(request.getParameter("parentgroupId"))) {
            response.sendRedirect("./groupId?groupId=" + request.getParameter("parentgroupId"));
        } else {
            response.sendRedirect("./");
        }
    }

    protected void deleteGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        if (!isEmpty(request.getParameter("groupId"))) {
            Integer groupId = Integer.parseInt(request.getParameter("groupId"));
            Group group = groupService.getGroupById(groupId);
            groupService.deleteGroup(group);
//            if (group.getParentgroupId() != null) {
//                response.sendRedirect("./groupId?groupId=" + group.getParentgroupId());
//            } else {
            response.sendRedirect("./");
//            }
        }
    }

    protected void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = groupService.getStudentById(studentId);
        Integer groupId = Integer.parseInt(request.getParameter("groupId"));

        Group group = groupService.getGroupById(groupId);
        groupService.deleteStudent(group, student);
        response.sendRedirect("./group?groupId=" + group.getGroupId());
    }

    protected void createStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        Integer groupId = Integer.parseInt(request.getParameter("groupId"));
        Group group = groupService.getGroupById(groupId);

        String name = request.getParameter("name");
        groupService.addStudent(group, name);
        response.sendRedirect("./group?groupId=" + group.getGroupId());
    }

    protected void editStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        Integer groupId = Integer.parseInt(request.getParameter("groupId"));
        Group group = groupService.getGroupById(groupId);

        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = groupService.getStudentById(studentId);

        String name = request.getParameter("name");

        groupService.editStudent(student, name);
        response.sendRedirect("./group?groupId=" + group.getGroupId());
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

}
