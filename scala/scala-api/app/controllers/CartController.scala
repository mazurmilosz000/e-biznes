package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable.ListBuffer
import models.CartItem

@Singleton
class CartController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val cart = ListBuffer.empty[CartItem]

  implicit val cartItemFormat: OFormat[CartItem] = Json.format[CartItem]

  def getAll = Action {
    Ok(Json.toJson(cart))
  }

  def getById(id: Int) = Action {
    cart.find(_.id == id) match {
      case Some(item) => Ok(Json.toJson(item))
      case None       => NotFound(Json.obj("error" -> "Cart item not found"))
    }
  }

  def add = Action(parse.json) { request =>
    request.body.validate[CartItem].map { item =>
      cart += item
      Created(Json.toJson(item))
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[CartItem].map { newItem =>
      cart.indexWhere(_.id == id) match {
        case -1 => NotFound(Json.obj("error" -> "Cart item not found"))
        case index =>
          cart.update(index, newItem)
          Ok(Json.toJson(newItem))
      }
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def delete(id: Int) = Action {
    cart.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> "Cart item not found"))
      case index =>
        cart.remove(index)
        NoContent
    }
  }
}
