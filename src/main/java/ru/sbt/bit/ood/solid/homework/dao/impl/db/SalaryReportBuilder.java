package ru.sbt.bit.ood.solid.homework.dao.impl.db;

import ru.sbt.bit.ood.solid.homework.domain.SalaryReport;
import ru.sbt.bit.ood.solid.homework.domain.SalaryReportItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/11/2016.
 */
class SalaryReportBuilder {
    private double summary;
    private List<SalaryReportItem> items = new ArrayList<>();

    void appendItem(SalaryReportItem item) {
        items.add(item);
        summary += item.getSalary();
    }

    public SalaryReport build() {
        return new SalaryReport(items, summary);
    }
}
