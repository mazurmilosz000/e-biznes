package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable.ListBuffer
import models.Category

@Singleton
class CategoriesController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val categories = ListBuffer(
    Category(1, "Electronics"),
    Category(2, "Home Appliances")
  )

  implicit val categoryFormat: OFormat[Category] = Json.format[Category]

  def getAll = Action {
    Ok(Json.toJson(categories))
  }

  def getById(id: Int) = Action {
    categories.find(_.id == id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None           => NotFound(Json.obj("error" -> "Category not found"))
    }
  }

  def create = Action(parse.json) { request =>
    request.body.validate[Category].map { category =>
      categories += category
      Created(Json.toJson(category))
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[Category].map { newCategory =>
      categories.indexWhere(_.id == id) match {
        case -1 => NotFound(Json.obj("error" -> "Category not found"))
        case index =>
          categories.update(index, newCategory)
          Ok(Json.toJson(newCategory))
      }
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def delete(id: Int) = Action {
    categories.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> "Category not found"))
      case index =>
        categories.remove(index)
        NoContent
    }
  }
}
