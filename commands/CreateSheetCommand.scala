package project.commands

import project.managers.{SheetManager, UserManager}

class CreateSheetCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val input = scala.io.StdIn.readLine().split(" ")
    try {
      val userName = input(0)
      val sheetName = input(1)
      UserManager.getUser(userName) match {
        case Some(user) =>
          SheetManager.createSheet(user, sheetName)
          println(s"""Create a sheet named "$sheetName" for "$userName".""")
        case None => println(s"User '$userName' not found.")
      }
    }
    catch {
      case _: ArrayIndexOutOfBoundsException =>
        println("Invalid input. Please enter the command in the following format:")
        println("<userName> <sheetName>")
    }
  }
}
