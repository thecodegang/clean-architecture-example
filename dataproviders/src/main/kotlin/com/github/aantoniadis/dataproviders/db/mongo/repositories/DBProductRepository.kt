package com.github.aantoniadis.dataproviders.db.mongo.repositories

import com.github.aantoniadis.dataproviders.db.mongo.entities.ProductEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface DBProductRepository : MongoRepository<ProductEntity, String>