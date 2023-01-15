package todo.hibernate.repository

import org.springframework.stereotype.Repository
import todo.domain.repository.UserRepository
import todo.hibernate.entity.UserEntity

@Repository
class UserRepositoryHibernate : UserRepository {

    override fun getUserByUserId(userId: String?): UserEntity? {
        return null
    }
}
