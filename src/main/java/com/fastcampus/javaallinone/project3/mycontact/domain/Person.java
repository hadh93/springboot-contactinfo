package com.fastcampus.javaallinone.project3.mycontact.domain;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
// Equivalent to {@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode}.
// @ToString(exclude = "phoneNumber")
@Where(clause = "deleted = false") // 실제로 데이터를 delete하는 대신, deleted 불리언으로 삭제 상태를 저장하고, 이 where절에서 '삭제된 값'들을 거른다. =
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NonNull 된 속성들은 Required Arguments로 취급된다.
    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

    private String address;

    @Embedded
    @Valid
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;


    public void set(PersonDto personDto){
        if (StringUtils.hasText(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if (StringUtils.hasText(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if (StringUtils.hasText(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if (StringUtils.hasText(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
        if (personDto.getBirthday() != null){
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }

    }

    public Integer getAge(){
        if (this.birthday != null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        } else {
            return null;
        }

    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(), this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }

}
