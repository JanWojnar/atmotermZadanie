package com.example.atmoterm.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CREATE_DATE")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(name = "VERSION_NR", nullable = false)
    @Version
    private Long versionNumber;

}
