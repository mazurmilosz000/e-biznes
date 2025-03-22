package main

import (
    "api-project/controllers"
    "api-project/database"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()

	database.InitDB()

    e.GET("/products", controllers.GetAllProducts)
    e.GET("/products/:id", controllers.GetProductById)
    e.POST("/products", controllers.CreateProduct)
    e.PUT("/products/:id", controllers.UpdateProductById)
    e.DELETE("/products/:id", controllers.DeleteProduct)

    e.GET("/categories", controllers.GetAllCategories)
    e.GET("/categories/:id", controllers.GetCategoryById)
    e.POST("/categories", controllers.CreateCategory)
    e.PUT("/categories/:id", controllers.UpdateCategoryById)
    e.DELETE("/categories/:id", controllers.DeleteCategory)

    e.GET("/cart", controllers.GetAllCategories)
    e.GET("/cart/:id", controllers.GetCategoryById)
    e.POST("/cart", controllers.CreateCategory)
    e.PUT("/cart/:id", controllers.UpdateCategoryById)
    e.DELETE("/cart/:id", controllers.DeleteCategory)

	e.Logger.Fatal(e.Start(":8080"))
}