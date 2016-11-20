package ru.sbt.bit.ood.solid.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.sbt.bit.ood.solid.homework.dao.SalaryReportDao;
import ru.sbt.bit.ood.solid.homework.dao.impl.db.SalaryReportDaoDb;
import ru.sbt.bit.ood.solid.homework.domain.SalaryReport;
import ru.sbt.bit.ood.solid.homework.notifiers.Notifier;
import ru.sbt.bit.ood.solid.homework.notifiers.impl.mail.MailNotifier;
import ru.sbt.bit.ood.solid.homework.serializer.impl.HtmlTableSerializer;
import ru.sbt.bit.ood.solid.homework.util.DateRange;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MailNotifier.class)
public class TestSalaryHtmlReportNotifier {

    @Test
    public void test() throws Exception {
        Connection connection = mockConnection();
        MimeMessageHelper mockMimeMessageHelper = getMockedMimeMessageHelper();

        LocalDate dateFrom = LocalDate.of(2014, Month.JANUARY, 1);
        LocalDate dateTo = LocalDate.of(2014, Month.DECEMBER, 31);

        SalaryReportDao salaryReportDao = new SalaryReportDaoDb(connection);
        SalaryReport report = salaryReportDao
                .getByDepartmentIdAndDateRange("10", DateRange.of(dateFrom, dateTo));

        Notifier notifier = new MailNotifier(
                "Monthly department salary report",
                "somebody@gmail.com",
                true, "mail.google.com"
        );

        notifier.doNotify(new HtmlTableSerializer<>().serialize(report));

        String expectedReportPath = "src/test/resources/expectedReport.html";
        assertActualReportEqualsTo(mockMimeMessageHelper, expectedReportPath);
    }

    private void assertActualReportEqualsTo(MimeMessageHelper mockMimeMessageHelper, String expectedReportPath) throws MessagingException, IOException {
        ArgumentCaptor<String> messageTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockMimeMessageHelper).setText(messageTextArgumentCaptor.capture(), anyBoolean());
        Path path = Paths.get(expectedReportPath);
        String expectedReportContent = new String(Files.readAllBytes(path));
        assertEquals(messageTextArgumentCaptor.getValue(), expectedReportContent);
    }

    private ResultSet getMockedResultSet(Connection someFakeConnection) throws SQLException {
        PreparedStatement someFakePreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(someFakePreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(someFakeConnection.prepareStatement(anyString())).thenReturn(someFakePreparedStatement);
        when(mockResultSet.next()).thenReturn(true, true, false);
        return mockResultSet;
    }

    private MimeMessageHelper getMockedMimeMessageHelper() throws Exception {
        JavaMailSenderImpl mockMailSender = mock(JavaMailSenderImpl.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mockMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        whenNew(JavaMailSenderImpl.class).withNoArguments().thenReturn(mockMailSender);
        MimeMessageHelper mockMimeMessageHelper = mock(MimeMessageHelper.class);
        whenNew(MimeMessageHelper.class)
                .withArguments(any(MimeMessage.class), any(boolean.class))
                .thenReturn(mockMimeMessageHelper);
        return mockMimeMessageHelper;
    }

    private Connection mockConnection() throws Exception {
        Connection someFakeConnection = mock(Connection.class);
        ResultSet mockResultSet = getMockedResultSet(someFakeConnection);
        when(mockResultSet.getString("emp_name")).thenReturn("John Doe", "Jane Dow");
        when(mockResultSet.getDouble("salary")).thenReturn(100.0, 50.0);
        return someFakeConnection;
    }
}
