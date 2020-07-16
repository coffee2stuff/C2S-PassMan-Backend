package com.peteralexbizjak.services

import db.repositories.IMongoRepo
import models.database.LoginModel
import models.database.NoteModel
import models.database.UserModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class MongoService : KoinComponent {

    private val repository by inject<IMongoRepo>()

    fun createLogin(login: LoginModel): Boolean = repository.createLogin(login)
    fun fetchLogin(id: String, token: String): LoginModel = repository.readLogin(id, token)
    fun fetchAllLogins(token: String): List<LoginModel> = repository.readLogins(token)
    fun updateLogin(login: LoginModel): Boolean = repository.updateLogin(login.id, login)
    fun deleteLoginById(id: String): Boolean = repository.deleteLogin(id)

    fun createNote(note: NoteModel): Boolean = repository.createNote(note)
    fun fetchNote(id: String, token: String): NoteModel = repository.readNote(id, token)
    fun fetchAllNotes(token: String): List<NoteModel> = repository.readNotes(token)
    fun updateNote(note: NoteModel): Boolean = repository.updateNote(note.id, note)
    fun deleteNoteById(id: String): Boolean = repository.deleteNote(id)

    fun createUser(user: UserModel): Boolean = repository.createUser(user)
    fun fetchUser(queryById: Boolean = false, vararg queryParams: String): UserModel = if (queryById) {
        repository.readUserById(queryParams[0])
    } else {
        repository.readUser(queryParams[0], queryParams[1])
    }
    fun updateUser(user: UserModel): Boolean = repository.updateUser(user.id, user)
    fun deleteUserById(id: String): Boolean = repository.deleteUser(id)
}