package org.springcloud.msvc.cursos.msvc_cursos.commons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String name;

    private String email;

    private String password;
}
