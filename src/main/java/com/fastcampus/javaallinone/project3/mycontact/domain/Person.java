package com.fastcampus.javaallinone.project3.mycontact.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data // Equivalent to {@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode}.
// @ToString(exclude = "phoneNumber")
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    // @NonNull 된 속성들은 Required Arguments로 취급된다.
    @NonNull
    private String name;
    @NonNull
    private int age;

    private String hobby;

    @NotNull
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;

    // 민감정보는 다음과 같이 은폐한다.
    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Block block;

}
