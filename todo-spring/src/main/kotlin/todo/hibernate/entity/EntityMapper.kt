package todo.hibernate.entity

import org.mapstruct.Mapper
import todo.domain.model.Todo

@Mapper(componentModel = "spring")
abstract class EntityMapper {

    abstract fun entityModel(todo: TodoEntity): Todo

    abstract fun modelEntity(todo: Todo): TodoEntity
}
