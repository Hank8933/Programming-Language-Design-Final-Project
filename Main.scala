package project

import project.commands._
import scala.collection.mutable

object Main extends App {
  val commands = mutable.Map[Int, Command](
    1 -> new CreateUserCommand,
    2 -> new CreateSheetCommand,
    3 -> new CheckSheetCommand,
    4 -> new ChangeSheetValueCommand,
    5 -> new ChangeSheetAccessRightCommand,
    6 -> new CollaborateWithUserCommand
  )

  def displayMenu(): Unit = {
    println("---------------Menu---------------")
    println("1. Create a user")
    println("2. Create a sheet")
    println("3. Check a sheet")
    println("4. Change a value in a sheet")
    println("5. Change a sheet's access right")
    println("6. Collaborate with an other user")
    println("----------------------------------")
    print("> ")
  }

  def mainMenu(): Unit = {
    var continue = true
    while (continue) {
      displayMenu()
      val choice = scala.io.StdIn.readInt()
      commands.get(choice) match {
        case Some(command) => command.execute()
        case None => println("Invalid choice. Please select again.")
      }
    }
  }

  mainMenu()
}
