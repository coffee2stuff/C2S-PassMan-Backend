package di

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import db.providers.MongoProvider
import db.repositories.IMongoRepo
import db.repositories.MongoRepo
import org.koin.dsl.module
import utils.helpers.retrieveConfigFile

/**
 * Configuration file retrieved as Map.
 */
private val configFile = retrieveConfigFile()

/**
 * Build an instance of a MongoDatabase object.
 */
private val dbInstance: MongoDatabase = MongoClients
    .create(configFile["mongo_connection_string"] as String)
    .getDatabase(configFile["mongo_db_name"] as String)

/**
 * Koin module definition. Each component is a singleton (provider and repository).
 */
val mongoModule = module {
    single { MongoProvider(database = dbInstance) }
    single<IMongoRepo> { MongoRepo(provider = get()) }
}