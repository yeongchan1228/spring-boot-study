package study.springboot.springcache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Student {
    private String name;
    private Integer age;
    private Grade grade;

    public enum Grade {
        A, B, C, D, E, F
    }
}
