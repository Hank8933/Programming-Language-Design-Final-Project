package project.commands

import project.managers.UserManager

class CreateUserCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val name = scala.io.StdIn.readLine()
    UserManager.createUser(name)
    println(s"""Create a user named "$name".""")
  }
}
