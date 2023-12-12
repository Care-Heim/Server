package com.spring.careHeim.domain.users.entity;

import com.spring.careHeim.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "User")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;
    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    private String uuid;
    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String id;
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String name;

}
