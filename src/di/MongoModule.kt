package di

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import db.providers.MongoProvider
import db.repositories.IMongoRepo
import db.repositories.MongoRepo
import org.koin.dsl.module


/**
 * Build an instance of a MongoDatabase object.
 */
private val dbInstance: MongoDatabase = MongoClients
    .create(System.getenv("MONGO_CONNECTION_STRING")?.toString() ?: "")
    .getDatabase(System.getenv("MONGO_DB_NAME")?.toString() ?: "")

/**
 * Koin module definition. Each component is a singleton (provider and repository).
 */
val mongoModule = module {
    single { MongoProvider(database = dbInstance) }
    single<IMongoRepo> { MongoRepo(provider = get()) }
}