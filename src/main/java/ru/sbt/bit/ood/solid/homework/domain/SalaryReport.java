package ru.sbt.bit.ood.solid.homework.domain;

import ru.sbt.bit.ood.solid.homework.serializer.annotations.ValueList;
import ru.sbt.bit.ood.solid.homework.serializer.annotations.ValueName;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ivan on 20/11/2016.
 */
public class SalaryReport {
    @ValueList
    private final List<SalaryReportItem> items;

    @ValueName("Totals")
    private final double totals;

    public SalaryReport(List<SalaryReportItem> items, double totals) {
        this.totals = totals;
        this.items = items;
    }

    public List<SalaryReportItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return "SalaryReport{" +
                "items=" + items +
                '}';
    }
}
