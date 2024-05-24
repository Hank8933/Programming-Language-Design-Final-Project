package project

sealed trait AccessRight
case object ReadOnly extends AccessRight
case object Editable extends AccessRight
