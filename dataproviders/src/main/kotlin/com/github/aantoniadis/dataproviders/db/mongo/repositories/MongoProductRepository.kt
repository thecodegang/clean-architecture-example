package com.github.aantoniadis.dataproviders.db.mongo.repositories

import com.github.aantoniadis.core.entities.Product
import com.github.aantoniadis.core.entities.ProductCode
import com.github.aantoniadis.dataproviders.db.mongo.entities.ProductEntity
import com.github.aantoniadis.dataproviders.db.mongo.entities.toProductEntity
import com.github.aantoniadis.dataproviders.db.mongo.entities.toProduct
import com.github.aantoniadis.delivery.usecases.core.gateways.ProductRepository

open class MongoProductRepository(private val dbProductRepository: DBProductRepository) :
    ProductRepository {
    override fun existsProductCode(productCode: ProductCode) = dbProductRepository.existsById(productCode.value)

    override fun getByProductCode(productCode: ProductCode) =
        dbProductRepository.findById(productCode.value).unwrap(ProductEntity::toProduct)

    override fun save(product: Product) {
        dbProductRepository.save(product.toProductEntity())
    }

}