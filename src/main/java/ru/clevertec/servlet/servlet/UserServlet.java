package ru.clevertec.servlet.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.servlet.dto.CreateUserDto;
import ru.clevertec.servlet.dto.ReadUserDto;
import ru.clevertec.servlet.entity.User;
import ru.clevertec.servlet.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;


@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    /**
     * @param req
     * @param resp
     * @throws IOException http://localhost:8080/servlet_war_exploded/user
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = new Gson().toJson(userService.getAll());
        try (PrintWriter out = resp.getWriter()) {
            out.println(json);
            resp.setStatus(SC_OK);
        }
    }

    /**
     * @param req
     * @param resp
     * @throws IOException http://localhost:8080/servlet_war_exploded/user
     *                     {
     *                     "name":"Maks",
     *                     "email":"maks@gmail.com",
     *                     "password":"pass",
     *                     "gender":"MALE"
     *                     }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contentType = req.getContentType();

        if (!("application/json").equals(contentType)) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type");
            return;
        }

        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            CreateUserDto dto = gson.fromJson(reader, CreateUserDto.class);
            User user = userService.create(dto);
            resp.getWriter()
                    .append("Added user with name: ")
                    .append(user.getName());
            resp.setStatus(SC_CREATED);
        } catch (IOException ex) {
            req.setAttribute("message", "There was an error: " + ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(SC_NOT_FOUND, "No parameter.");
            return;
        }
        userService.deleteById(Integer.parseInt(id));
        resp.setStatus(SC_NO_CONTENT);
    }
}
