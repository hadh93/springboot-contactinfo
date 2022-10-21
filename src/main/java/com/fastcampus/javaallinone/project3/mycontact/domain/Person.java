package com.fastcampus.javaallinone.project3.mycontact.domain;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
// Equivalent to {@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode}.
// @ToString(exclude = "phoneNumber")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NonNull 된 속성들은 Required Arguments로 취급된다.
    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NonNull
    @Min(1)
    private int age;

    private String hobby;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String bloodType;
    private String address;

    @Embedded
    @Valid
    private Birthday birthday;

    private String job;

    // 민감정보는 다음과 같이 은폐한다.
    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Block block;

    public void set(PersonDto personDto){
        if (personDto.getAge() != 0){
            this.setAge(personDto.getAge());
        }
        if (!StringUtils.hasText(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if (!StringUtils.hasText(personDto.getBloodType())){
            this.setBloodType(personDto.getBloodType());
        }
        if (!StringUtils.hasText(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if (!StringUtils.hasText(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if (!StringUtils.hasText(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }

    }

}
