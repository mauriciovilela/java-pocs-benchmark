package com.msv.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String text;

    @Column(name = "creation_date")
    LocalDateTime creationDate;

}