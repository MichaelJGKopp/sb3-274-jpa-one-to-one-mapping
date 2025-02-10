package io.michaeljgkopp.github.cruddemo;

import io.michaeljgkopp.github.cruddemo.dao.InstructorRepository;
import io.michaeljgkopp.github.cruddemo.entity.Instructor;
import io.michaeljgkopp.github.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public static CommandLineRunner commandLineRunner(InstructorRepository instructorRepository) {

		return args -> {
			instructorRepository.save(
					Instructor.builder()
							.firstName("Michael")
							.lastName("Kopp")
							.email("example@gmail.com")
							.instructorDetail(
									InstructorDetail.builder()
											.hobby("coding")
											.youtubeChannel("https://www.youtube.com")
											.build()
							)
							.build()
			);

			System.out.println("Instructors\n" +
					"=".repeat(20));
			instructorRepository.findAll().forEach(System.out::println);
		};
	}
}
