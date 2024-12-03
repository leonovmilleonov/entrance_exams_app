package com.leonovviktor.entrance_exams.servlet;

import com.leonovviktor.entrance_exams.dao.DepartmentDao;
import com.leonovviktor.entrance_exams.dto.DepartmentDto;
import com.leonovviktor.entrance_exams.entity.Department;
import com.leonovviktor.entrance_exams.service.DepartmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {
    private static final DepartmentService dpService = DepartmentService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Список факультетов и экзаменов для них: </h1>");
            writer.write("<ul>");
            dpService.findAll().forEach(
                    departmentDto -> {
                        writer.write("""
                                 <ul>
                                 %s. Экзамены:
                                 <li><a href="/results?ExamId=%d">%s</a></li>
                                 <li><a href="/results?ExamId=%d">%s</a></li>
                                <li><a href="/results?ExamId=%d">%s</a></li>
                                 </ul>
                                """.formatted(departmentDto.getName(),
                                departmentDto.getFirstExam().getId(),
                                departmentDto.getFirstExam().getExamType(),
                                departmentDto.getSecondExam().getId(),
                                departmentDto.getSecondExam().getExamType(),
                                departmentDto.getThirdExam().getId(),
                                departmentDto.getThirdExam().getExamType()));
                    }
            );
            writer.write("</ul>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
