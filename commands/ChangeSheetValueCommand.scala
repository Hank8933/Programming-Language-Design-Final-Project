package project.commands

import project.managers.{SheetManager, UserManager}

class ChangeSheetValueCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val input = scala.io.StdIn.readLine().split(" ")
    val userName = input(0)
    val sheetName = input(1)
    UserManager.getUser(userName) match {
      case Some(user) =>
        user.sheets.find(_.name == sheetName) match {
          case Some(sheet) =>
            SheetManager.printSheet(sheet)
            print("> ")
            val updateInput = scala.io.StdIn.readLine().split(" ")
            val row = updateInput(0).toInt
            val col = updateInput(1).toInt
            val expression = updateInput.drop(2).mkString(" ")
            val value = SheetManager.evaluateExpression(expression)
            if (value != "error" && value != "undefined") {
              SheetManager.updateCell(sheet, row, col, value, user)
            } else {
              println("Invalid expression")
            }
            SheetManager.printSheet(sheet)
          case None => println(s"Sheet '$sheetName' not found for user '$userName'.")
        }
      case None => println(s"User '$userName' not found.")
    }
  }
}
