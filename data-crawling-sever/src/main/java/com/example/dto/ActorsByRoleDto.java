package com.example.dto;

import com.example.domain.actor.Actor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class ActorsByRoleDto {
    private List<Actor> actor;
    private String role;

    public ActorsByRoleDto() {
        this.actor = new ArrayList<>();
    }
}
