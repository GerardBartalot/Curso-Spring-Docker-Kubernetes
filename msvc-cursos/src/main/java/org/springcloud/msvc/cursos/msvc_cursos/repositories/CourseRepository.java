package org.springcloud.msvc.cursos.msvc_cursos.repositories;

import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
