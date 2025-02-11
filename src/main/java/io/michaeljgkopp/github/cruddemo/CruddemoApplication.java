package io.michaeljgkopp.github.cruddemo;

import io.michaeljgkopp.github.cruddemo.dao.InstructorDetailRepository;
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
    public static CommandLineRunner commandLineRunner(
            InstructorRepository instructorRepository,
            InstructorDetailRepository instructorDetailRepository) {

        return args -> {
//			instructorRepository.save(
//					Instructor.builder()
//							.firstName("Michael")
//							.lastName("Kopp")
//							.email("example@gmail.com")
//							.instructorDetail(
//									InstructorDetail.builder()
//											.hobby("coding")
//											.youtubeChannel("https://www.youtube.com")
//											.build()
//							)
//							.build()
//			);

            int id = 2;
            Instructor instructor = instructorRepository.findById(id).orElse(null);
            System.out.println("findById(1): " + instructor + "\n");

            if (instructor != null) {
                instructorRepository.deleteById(id);
                System.out.printf("Deleted instructor with id %d%n%n", id);
            }

            // bidirectional mapping with instructorDetail
            id = 3;
            InstructorDetail instructorDetail = instructorDetailRepository.findById(id).orElse(null);
            System.out.println("findById(" + id + "): " + instructorDetail);

            instructor = instructorDetail != null ? instructorDetail.getInstructor() : null;
            System.out.println("corresponding Instructor: " + instructor + "\n");

            System.out.println("Instructors\n" +
                    "=".repeat(20));
            instructorRepository.findAll().forEach(System.out::println);
        };
    }
}
