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
    public CommandLineRunner commandLineRunner(
            InstructorRepository instructorRepository,
            InstructorDetailRepository instructorDetailRepository) {

        return args -> {

            // reset database
            instructorRepository.deleteAll();
            instructorRepository.resetAutoIncrement();
            instructorDetailRepository.resetAutoIncrement();

            // persist 5 instructors with instructorDetail
            for (int i = 0; i < 5; i++) {
                instructorRepository.save(
                        Instructor.builder()
                                .firstName("Michael" + i)
                                .lastName("Kopp" + i)
                                .email("example@gmail.com" + i)
                                .instructorDetail(
                                        InstructorDetail.builder()
                                                .hobby("coding" + i)
                                                .youtubeChannel("https://www.youtube.com" + i)
                                                .build()
                                )
                                .build()
                );
            }

            // list all instructors
            System.out.println("Instructors\n" +
                    "=".repeat(20));
            instructorRepository.findAll().forEach(System.out::println);

            // find instructor by id and delete
            int id = 2;
            Instructor instructor = instructorRepository.findById(id).orElse(null);
            System.out.println("findById(" + id + "): " + instructor + "\n");

            if (instructor != null) {
                instructorRepository.deleteById(id);
                System.out.printf("Deleted instructor with id %d%n%n", id);
            }

            // bidirectional mapping with instructorDetail
            id = 5;
            InstructorDetail instructorDetail = instructorDetailRepository.findById(id).orElse(null);
            System.out.println("findById(" + id + "): " + instructorDetail);

            // get instructor from instructorDetail
            instructor = instructorDetail != null ? instructorDetail.getInstructor() : null;
            System.out.println("corresponding Instructor: " + instructor + "\n");

            // delete instructorDetail and check if instructor is deleted as well
            if (instructorDetail != null) {
                instructorDetailRepository.deleteById(id);
                System.out.printf("Deleted instructorDetail with id %d%n%n", id);

                instructor = instructorRepository.findById(instructor.getId()).orElse(null);
                System.out.println("Instructor after deleting instructorDetail: " + instructor + "\n");

                // check on the object level if the instructorDetail is still there
                System.out.println("Object InstructorDetail: " + instructorDetail);
                System.out.println("Object Instructor: " + (instructor = instructorDetail.getInstructor()));
            }


            // list all instructors
            System.out.println("\nInstructors\n" +
                    "=".repeat(20));
            instructorRepository.findAll().forEach(System.out::println);
        };
    }
}
