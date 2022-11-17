package dao

import domain.User
import java.util.concurrent.atomic.AtomicInteger

class UserDao {

    private val users = hashMapOf(
        0 to User(name = "Alice", email = "alice@alice.kt", id = 0),
        1 to User(name = "Bob", email = "bob@bob.kt", id = 1),
        2 to User(name = "Carol", email = "carol@carol.kt", id = 2),
        3 to User(name = "Dave", email = "dave@dave.kt", id = 3)
    )

    private var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun findAll(): List<User> {
        return users.values.toList();
    }

    fun save(name: String, email: String): User {
        val id = lastId.incrementAndGet()
        val user = User(name = name, email = email, id = id)
        users[id] = user
        return user
    }

    fun findById(id: Int): User? {
        return users[id]
    }

    fun findByEmail(email: String): List<User> {
        return users.values.filter { it.email == email } .toList()
    }

    fun update(id: Int, user: User) {
        users[id] = User(name = user.name, email = user.email, id = id)
    }

    fun delete(id: Int) {
        users.remove(id)
    }
}