package ru.sbt.bit.ood.solid.homework.dao;

import ru.sbt.bit.ood.solid.homework.domain.SalaryReport;
import ru.sbt.bit.ood.solid.homework.util.DateRange;

/**
 * Created by Ivan on 20/11/2016.
 */
public interface SalaryReportDao {
    SalaryReport getByDepartmentIdAndDateRange(String departmentId, DateRange range);
}