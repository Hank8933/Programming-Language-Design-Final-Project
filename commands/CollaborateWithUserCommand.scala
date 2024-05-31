package project.commands

import project.managers.{SheetManager, UserManager}
import project.Editable

class CollaborateWithUserCommand extends Command {
  override def execute(): Unit = {
    print("> ")
    val input = scala.io.StdIn.readLine().split(" ")
    try {
      val ownerName = input(0)
      val sheetName = input(1)
      val collaboratorName = input(2)
      val result = for {
        owner <- UserManager.getUser(ownerName)
        collaborator <- UserManager.getUser(collaboratorName)
        sheet <- owner.sheets.find(_.name == sheetName)
      } yield {
        SheetManager.shareSheet(sheet, collaborator, Editable)
        println(s"""Share "$ownerName"'s "$sheetName" with "$collaboratorName".""")
      }

      if (result.isEmpty) {
        if (UserManager.getUser(ownerName).isEmpty) println(s"User '$ownerName' not found.")
        else if (UserManager.getUser(collaboratorName).isEmpty) println(s"Collaborator '$collaboratorName' not found.")
        else println(s"Sheet '$sheetName' not found for user '$ownerName'.")
      }
    }
    catch {
      case _: ArrayIndexOutOfBoundsException =>
        println("Invalid input. Please enter the command in the following format:")
        println("<ownerName> <sheetName> <collaboratorName>")
    }
    
  }
}
