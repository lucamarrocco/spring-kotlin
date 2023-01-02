package spring.kotlin.hibernate.entity

import org.mapstruct.Mapper
import spring.kotlin.domain.model.Todo

@Mapper(componentModel = "spring")
abstract class EntityMapper {

    abstract fun entityModel(todo: TodoEntity): Todo

    abstract fun modelEntity(todo: Todo): TodoEntity
}
