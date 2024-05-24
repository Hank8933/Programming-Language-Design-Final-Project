package project.managers

import project.{User, Sheet, AccessRight, ReadOnly, Editable}

object SheetManager {
  def createSheet(owner: User, sheetName: String): Sheet = {
    val newSheet = Sheet(sheetName, owner, Array.fill(3, 3)("0"), Map(owner -> Editable))
    owner.sheets :+= newSheet
    newSheet
  }

  def printSheet(sheet: Sheet): Unit = {
    sheet.content.foreach(row => println(row.mkString(", ")))
  }

  def updateCell(sheet: Sheet, row: Int, col: Int, value: String, user: User): Unit = {
    try {
      if (sheet.accessRights.getOrElse(user, ReadOnly) == Editable) {
        sheet.content(row)(col) = value
      } else {
        println("Access Denied: Read-Only Access")
      }
    } catch {
      case _: IndexOutOfBoundsException => println("Invalid cell coordinates")
    }
  }

  def evaluateExpression(expression: String): String = {
    val cleanedExpression = expression.replaceAll("\\s+", "")
    val pattern = """(\d+(\.\d+)?)([+\-*/])(\d+(\.\d+)?)""".r
    cleanedExpression match {
      case pattern(left, _, operator, right, _) =>
        try {
          val leftVal = left.toDouble
          val rightVal = right.toDouble
          operator match {
            case "+" => (leftVal + rightVal).toString
            case "-" => (leftVal - rightVal).toString
            case "*" => (leftVal * rightVal).toString
            case "/" => if (rightVal != 0) (leftVal / rightVal).toString else "undefined"
            case _ => "error"
          }
        } catch {
          case _: NumberFormatException => "error"
        }
      case _ => "error"
    }
  }

  def setAccessRight(sheet: Sheet, user: User, accessRight: AccessRight): Unit = {
    sheet.accessRights += (user -> accessRight)
  }

  def getAccessRight(sheet: Sheet, user: User): AccessRight = {
    sheet.accessRights.getOrElse(user, ReadOnly)
  }

  def shareSheet(sheet: Sheet, user: User, accessRight: AccessRight): Unit = {
    setAccessRight(sheet, user, accessRight)
  }

  def enableFeature(sheet: Sheet, feature: String): Unit = {
    feature match {
      case "sharing" => // No specific action needed as sharing is controlled by access rights
      case "edit" => sheet.accessRights = sheet.accessRights.map {
        case (user, _) => (user, Editable)
      }
      case _ => println("Feature not recognized")
    }
  }

  def disableFeature(sheet: Sheet, feature: String): Unit = {
    feature match {
      case "sharing" => sheet.accessRights = Map(sheet.owner -> Editable)
      case "edit" => sheet.accessRights = sheet.accessRights.map {
        case (user, _) => (user, ReadOnly)
      }
      case _ => println("Feature not recognized")
    }
  }
}
