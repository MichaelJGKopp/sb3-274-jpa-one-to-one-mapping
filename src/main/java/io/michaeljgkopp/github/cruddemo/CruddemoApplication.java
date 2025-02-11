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
            resetDatabase(instructorRepository, instructorDetailRepository);

            // persist 5 instructors with instructorDetail
            insertInstructors(instructorRepository);

            // list all instructors
            listInstructors(instructorRepository);

            // find instructor by id and delete
            int id = 2;
            findAndDeleteInstructor(instructorRepository, id);

            // bidirectional mapping with instructorDetail
            Instructor instructor;
            id = 5;
            testBirectionalMapping(instructorRepository, instructorDetailRepository, id);

            // delete instructorDetail without deleting instructor
            id = 1;
            deleteInstructorDetailWithoutDeletingInstructor(instructorRepository, instructorDetailRepository, id);

            // list all instructors
            listInstructors(instructorRepository);
        };
    }

    private static void deleteInstructorDetailWithoutDeletingInstructor(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository, int id) {
        Instructor instructor;
        // retrieve instructorDetail by id
        InstructorDetail instructorDetail = instructorDetailRepository.findById(id).orElse(null);
        if (instructorDetail != null) {
            // remove the associated object reference
            instructorDetail.getInstructor().setInstructorDetail(null);

            // delete the instructorDetail
            instructorDetailRepository.delete(instructorDetail);
            System.out.printf("Deleted instructorDetail with id %d%n%n", id);

            // show that the instructor is still there
            instructor = instructorRepository.findById(instructorDetail.getInstructor().getId()).orElse(null);
            System.out.println("Instructor after deleting instructorDetail: " + instructor);
        }
    }

    private static void resetDatabase(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository) {
        instructorRepository.deleteAll();
        instructorRepository.resetAutoIncrement();
        instructorDetailRepository.resetAutoIncrement();
    }

    private static void insertInstructors(InstructorRepository instructorRepository) {
        System.out.println("Inserting instructors\n");
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
    }

    private static void listInstructors(InstructorRepository instructorRepository) {
        System.out.println("Instructors\n" +
                "=".repeat(20));
        instructorRepository.findAll().forEach(System.out::println);
        System.out.println();
    }

    private static void findAndDeleteInstructor(InstructorRepository instructorRepository, int id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        System.out.println("findById(" + id + "): " + instructor);

        if (instructor != null) {
            instructorRepository.deleteById(id);
            System.out.printf("Deleted instructor with id %d%n%n", id);
        }
    }

    private static void testBirectionalMapping(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository, int id) {

        // find instructorDetail by id
        Instructor instructor;
        InstructorDetail instructorDetail = instructorDetailRepository.findById(id).orElse(null);
        System.out.println("findById(" + id + "): " + instructorDetail);

        // get instructor from instructorDetail
        instructor = instructorDetail != null ? instructorDetail.getInstructor() : null;
        System.out.println("corresponding Instructor: " + instructor);

        // delete instructorDetail and check if instructor is deleted as well
        if (instructorDetail != null) {
            instructorDetailRepository.deleteById(id);
            System.out.printf("Deleted instructorDetail with id %d%n%n", id);

            instructor = instructorRepository.findById(instructor.getId()).orElse(null);
            System.out.println("Instructor after deleting instructorDetail: " + instructor);

            // check on the object level if the instructorDetail is still there
            System.out.println("Object InstructorDetail: " + instructorDetail);
            System.out.println("Object Instructor: " + (instructor = instructorDetail.getInstructor()) + "\n");
        }
    }
}
