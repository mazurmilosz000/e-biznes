package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable.ListBuffer
import models.Product

@Singleton
class ProductsController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val products = ListBuffer(
    Product(1, "Laptop", 3500.00),
    Product(2, "Mouse", 50.00)
  )

  implicit val productFormat: OFormat[Product] = Json.format[Product]

  def getAll = Action {
    Ok(Json.toJson(products))
  }

  def getById(id: Int) = Action {
    products.find(_.id == id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None          => NotFound(Json.obj("error" -> "Product not found"))
    }
  }

  def create = Action(parse.json) { request =>
    request.body.validate[Product].map { product =>
      products += product
      Created(Json.toJson(product))
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[Product].map { newProduct =>
      products.indexWhere(_.id == id) match {
        case -1 => NotFound(Json.obj("error" -> "Product not found"))
        case index =>
          products.update(index, newProduct)
          Ok(Json.toJson(newProduct))
      }
    }.recoverTotal { e =>
      BadRequest(Json.obj("error" -> "Invalid JSON"))
    }
  }

  def delete(id: Int) = Action {
    products.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> "Product not found"))
      case index =>
        products.remove(index)
        NoContent
    }
  }
}
