package com.praxiti.etl.pipeline;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.to_date;
import static org.apache.spark.sql.functions.upper;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.praxiti.etl.model.Employee;
import com.praxiti.etl.service.EmployeeService;

public class Pipeline {

	private final SparkSession sparkSession;

	private final EmployeeService empService;

	public Pipeline(SparkSession sparkSession, EmployeeService empService) {
		this.sparkSession = sparkSession;
		this.empService = empService;
	}

	public void execute(String filePath) {
		// Extract
		Dataset<Row> rawData = sparkSession.read().option("header", true).option("inferSchema", "true")
				.option("sep", ",").csv(filePath);
		rawData.show();

		// Transform
		Dataset<Row> transformedData = rawData
				.withColumn("Experience", expr("cast(datediff(current_date(), HiringDate) / 365.0 as int)"))
				.withColumn("SalaryWithBonus", col("Salary").plus(expr("cast(Experience as int) * 500")))
				.withColumn("DepartmentUpperCase", upper(col("Department")))
				.withColumn("HiringDate", to_date(col("HiringDate"), "yyyy-MM-dd"));

		// Load
		List<Employee> employees = transformedData.as(Encoders.bean(Employee.class)).collectAsList();
		empService.saveAllEmployees(employees);
	}

}
