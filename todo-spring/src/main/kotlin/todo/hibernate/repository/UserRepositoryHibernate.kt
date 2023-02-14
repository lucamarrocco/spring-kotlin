package todo.hibernate.repository

import org.springframework.stereotype.Repository
import todo.domain.repository.UserRepository
import todo.hibernate.entity.UserEntity
import java.util.*

@Repository
class UserRepositoryHibernate : UserRepository {

    override fun getUserByUserId(userId: UUID?): UserEntity? {
        return null
    }

    override fun getUserByUserId(userId: String?): UserEntity? {
        return null
    }
}
