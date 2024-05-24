package project.commands

import project.managers.SheetManager
import project.managers.UserManager

class CheckSheetCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val input = scala.io.StdIn.readLine().split(" ")
    val userName = input(0)
    val sheetName = input(1)
    UserManager.getUser(userName) match {
      case Some(user) =>
        user.sheets.find(_.name == sheetName) match {
          case Some(sheet) => SheetManager.printSheet(sheet)
          case None => println(s"Sheet '$sheetName' not found for user '$userName'.")
        }
      case None => println(s"User '$userName' not found.")
    }
  }
}
