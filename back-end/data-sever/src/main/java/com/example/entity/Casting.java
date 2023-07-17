package com.example.entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "CASTING")
@SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "CASTING_SEQUENCE", initialValue = 1, allocationSize = 1)
public class Casting {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTOR_ID", referencedColumnName = "ID")
    private Actor actorId;
    
    @Column(name = "ROLE")
    private String role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUSICAL_ID", referencedColumnName = "ID")
    private Musical musicalId;
}
