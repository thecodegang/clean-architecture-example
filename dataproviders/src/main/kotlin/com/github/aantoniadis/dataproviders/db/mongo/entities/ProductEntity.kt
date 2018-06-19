package com.github.aantoniadis.dataproviders.db.mongo.entities

import com.github.aantoniadis.core.entities.Product
import com.github.aantoniadis.core.entities.ProductCode
import com.github.aantoniadis.dataproviders.db.mongo.entities.annotations.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
@Document
data class ProductEntity(
    @Id
    val code: String,
    val description: String,
    val price: BigDecimal,
    val createdAt: Long
)

// Mappers
fun ProductEntity.toProduct() =
    Product(
        code = ProductCode(this.code),
        description = this.description,
        price = this.price,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.createdAt), ZoneId.systemDefault())
    )

fun Product.toProductEntity() =
    ProductEntity(
        code = this.code.value,
        description = this.description,
        price = this.price,
        createdAt = this.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )