package com.praxiti.etl;

import org.apache.spark.sql.SparkSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.praxiti.etl.pipeline.Pipeline;
import com.praxiti.etl.service.EmployeeService;

@SpringBootApplication
public class SpringbootSparkEtlApplication {
	
	private static final String FILE_PATH = "/Users/keerthikumar/git_clones/springboot-spark-etl/src/main/resources/employees.csv";

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSparkEtlApplication.class, args);
	}

	@Bean
    public CommandLineRunner performETL(EmployeeService empService) {
        return args -> {
            SparkSession sparkSession = SparkSession.builder()
                    .appName("SparkETLWithSpringboot")
                    .config("spark.master", "local[*]")
                    .config("spark.memory.offHeap.enabled", "false")
                    .getOrCreate();

            Pipeline pipeline = new Pipeline(sparkSession, empService);
            pipeline.execute(FILE_PATH);
        };
    }

}
