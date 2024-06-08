package project.commands

import project.managers.{SheetManager, UserManager}
import project.{ReadOnly, Editable}

class ChangeSheetAccessRightCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val input = scala.io.StdIn.readLine().split(", ")
    try {
      val userName = input(0)
      val sheetName = input(1)
      val accessRight = input(2) match {
        case "ReadOnly" => ReadOnly
        case "Editable" => Editable
        case _ => ReadOnly
      }
      UserManager.getUser(userName) match {
        case Some(user) =>
          user.sheets.find(_.name == sheetName) match {
            case Some(sheet) =>
              SheetManager.setAccessRight(sheet, user, accessRight)
            case None => println(s"Sheet '$sheetName' not found for user '$userName'.")
          }
        case None => println(s"User '$userName' not found.")
      }
    }
    catch {
      case _: ArrayIndexOutOfBoundsException =>
        println("Invalid input. Please enter the command in the following format:")
        println("<userName>, <sheetName>, <accessRight>")
    }
  }
}
