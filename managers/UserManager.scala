package project.managers

import project.User

object UserManager {
  private var users: List[User] = List()

  def createUser(name: String): User = {
    val newUser = User(name)
    users :+= newUser
    newUser
  }

  def getUser(name: String): Option[User] = {
    users.find(_.name == name)
  }
}
