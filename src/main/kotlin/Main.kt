import dao.UserDao
import domain.User
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.HttpStatus
import service.UserService

fun main() {

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(HttpStatus.NOT_FOUND) { ctx -> ctx.json("not found") }
    }
        .routes {
            crud("/users/{id}", UserService())
        }
        .start(7070)

}