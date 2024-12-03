package com.leonovviktor.entrance_exams.servlet;

import com.leonovviktor.entrance_exams.service.ExamService;
import com.leonovviktor.entrance_exams.service.ResultService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebServlet("/results")
public class ResultServlet extends HttpServlet {
    private static final ResultService resultService = ResultService.getInstance();
    private static final ExamService examService = ExamService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Long examId = Long.valueOf(req.getParameter("ExamId"));
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Результаты для экзамена %s</h1>".formatted(examService.findById(examId).getExamType()));
            writer.write("""
                    <table>
                    <tr>
                    <td>ID</td>
                    <td>Проверяющий</td>
                    <td>ID поступающего</td>
                    <td>Оценка</td>
                    </tr>
                    """);
            resultService.findByExamId(examId).forEach(
                    resultDto -> {
                        writer.write("""
                                <tr>
                                <td>%d</td>
                                <td>%s</td>
                                <td>%d</td>
                                <td>%s</td>
                                </tr>
                                """.formatted(resultDto.getId(),
                                resultDto.getProfessorName(),
                                resultDto.getApplicantId(),
                                resultDto.getNoteType())
                        );
                    }
            );
            writer.write("</table>");
            writer.write("""
                    <a href="/departments">Вернуться обратно</a>
                    """);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
