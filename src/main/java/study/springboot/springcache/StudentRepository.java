package study.springboot.springcache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepository {

    private final Map<String, Student> storage = new HashMap<>();

    @Cacheable("student")
    public Student getStudent(String name) {
        System.out.println("[repo] 접근");
        return storage.get(name);
    }

    public Student enroll(String name, Integer age, Student.Grade grade) {
        return storage.put(name, Student.of(name, age, grade));
    }
}
