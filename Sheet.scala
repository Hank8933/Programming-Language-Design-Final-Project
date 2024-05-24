package project

case class Sheet(name: String, owner: User, var content: Array[Array[String]], var accessRights: Map[User, AccessRight])
