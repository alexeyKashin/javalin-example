package service

import dao.UserDao
import domain.User
import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import io.javalin.http.HttpStatus

class UserService : CrudHandler {

    private val userDao = UserDao()

    override fun create(ctx: Context) {
        val body = ctx.bodyAsClass(Map::class.java)
        val user = userDao.save(name = body["name"] as String, email = body["email"] as String)
        ctx.status(HttpStatus.CREATED)
        ctx.json(user)
    }

    override fun delete(ctx: Context, resourceId: String) {
        val id = resourceId.toInt()
        userDao.delete(id = id)
        ctx.status(HttpStatus.NO_CONTENT)
    }

    override fun getAll(ctx: Context) {
        val params = ctx.queryParams("email")
        if (params.isEmpty()) {
            ctx.json(userDao.findAll())
            return
        }

        val email = params[0]
        ctx.json(userDao.findByEmail(email = email))
    }

    override fun getOne(ctx: Context, resourceId: String) {
        val id = resourceId.toInt()
        val user = userDao.findById(id)
        if (user == null) {
            ctx.status(HttpStatus.NOT_FOUND)
            ctx.json("not found")
            return
        }
        ctx.json(user)
    }

    override fun update(ctx: Context, resourceId: String) {
        val body = ctx.bodyAsClass(Map::class.java)
        val id = resourceId.toInt()
        userDao.update(id = id, user = User(name = body["name"] as String, email = body["email"] as String, id = id))
        ctx.status(HttpStatus.NO_CONTENT)
    }
}