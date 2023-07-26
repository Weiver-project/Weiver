package com.example.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "CASTING")

public class Casting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "casting_sequence_generator")
    @SequenceGenerator(name = "casting_sequence_generator", sequenceName = "casting_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTOR_ID", referencedColumnName = "ID")
    private Actor actorId;
    
    @Nullable
    @Column(name = "ROLE")
    private String role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUSICAL_ID", referencedColumnName = "ID")
    private Musical musicalId;
}
