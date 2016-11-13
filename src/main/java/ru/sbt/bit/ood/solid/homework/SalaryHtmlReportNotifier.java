package ru.sbt.bit.ood.solid.homework;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {
    private final Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public String generateHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary " +
                            "from employee emp left " +
                            "join salary_payments sp on emp.id = sp.employee_id " +
                            "where emp.department_id = ? and sp.date >= ? and sp.date <= ? " +
                            "group by emp.id, emp.name");

            // inject parameters to sql && execute query
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
            ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
            ResultSet results = ps.executeQuery();

            // build result html
            HtmlTableBuilder builder = new HtmlTableBuilder();
            builder.cell().append("Employee").cell().append("Salary");

            double totals = 0;
            while (results.next()) {
                builder.row()
                        .cell().append(results.getString("emp_name"))                   // appending employee name
                        .cell().append(Double.toString(results.getDouble("salary")));   // appending employee salary for period
                totals += results.getDouble("salary");                                  // add salary to totals
            }

            return builder.row().cell().append("Total").cell().append(Double.toString(totals)).build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlSalaryReport(String htmlReport, String recipients) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");

            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            helper.setText(htmlReport, true);                           // setting message text, last parameter 'true' says that it is HTML format
            helper.setSubject("Monthly department salary report");
            mailSender.send(message);                                   // send the message
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        String report = generateHtmlSalaryReport(departmentId, dateFrom, dateTo);
        sendHtmlSalaryReport(report, recipients);
    }
}
