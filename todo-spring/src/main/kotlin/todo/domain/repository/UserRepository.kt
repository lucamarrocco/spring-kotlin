package todo.domain.repository

import todo.hibernate.entity.UserEntity

interface UserRepository {

    fun getUserByUserId(userId: String?): UserEntity?
}
