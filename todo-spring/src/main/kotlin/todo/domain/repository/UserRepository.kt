package todo.domain.repository

import todo.hibernate.entity.UserEntity
import java.util.*

interface UserRepository {

    fun getUserByUserId(userId: UUID?): UserEntity?

    fun getUserByUserId(userId: String?): UserEntity?
}
