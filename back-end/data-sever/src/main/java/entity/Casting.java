package entity;

import javax.persistence.*;

@Entity(name = "CASTING")
public class Casting {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")
    @SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTOR_ID", referencedColumnName = "ID")
    private Actor actorId;
    @Column(name = "ROLE")
    private String role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUSICAL_ID", referencedColumnName = "ID")
    private Musical musicalId;
}
