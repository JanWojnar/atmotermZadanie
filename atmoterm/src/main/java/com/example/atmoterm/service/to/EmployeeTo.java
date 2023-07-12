package com.example.atmoterm.service.to;

import com.example.atmoterm.persistance.entity.TeamEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class EmployeeTo {
    private Long id;
    private String name;
    private List<Long> teamIDs = new ArrayList<>();
}
