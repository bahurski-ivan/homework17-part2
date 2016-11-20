package ru.sbt.bit.ood.solid.homework.domain;

import ru.sbt.bit.ood.solid.homework.serializer.annotations.ValueName;

/**
 * Created by Ivan on 20/11/2016.
 */
public class SalaryReportItem {
    @ValueName("Employee")
    private final String employeeName;

    @ValueName("Salary")
    private final Double salary;


    public SalaryReportItem(String employeeName, Double salary) {
        this.employeeName = employeeName;
        this.salary = salary;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Double getSalary() {
        return salary;
    }


    @Override
    public String toString() {
        return "SalaryReportItem{" +
                "employeeName='" + employeeName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
