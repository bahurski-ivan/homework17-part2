package ru.sbt.bit.ood.solid.homework.dao.impl.db;

import ru.sbt.bit.ood.solid.homework.dao.SalaryReportDao;
import ru.sbt.bit.ood.solid.homework.domain.SalaryReport;
import ru.sbt.bit.ood.solid.homework.domain.SalaryReportItem;
import ru.sbt.bit.ood.solid.homework.util.DateRange;

import java.sql.*;

/**
 * Created by Ivan on 20/11/2016.
 */
public class SalaryReportDaoDb implements SalaryReportDao {
    private final PreparedStatement ps;

    public SalaryReportDaoDb(Connection connection) {
        try {
            this.ps = connection.prepareStatement(
                    "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary " +
                            "from employee emp " +
                            "left join salary_payments sp on emp.id = sp.employee_id " +
                            "where emp.department_id = ? and sp.date >= ? and sp.date <= ? " +
                            "group by emp.id, emp.name"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SalaryReport getByDepartmentIdAndDateRange(String departmentId, DateRange range) {
        SalaryReport result;
        SalaryReportBuilder builder = new SalaryReportBuilder();

        try {
            ps.setString(0, departmentId);
            ps.setString(1, new Date(range.getFrom().toEpochDay()).toString());
            ps.setString(2, new Date(range.getTo().toEpochDay()).toString());
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                String name = results.getString("emp_name");
                double salary = results.getDouble("salary");
                builder.appendItem(new SalaryReportItem(name, salary));
            }

            result = builder.build();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
