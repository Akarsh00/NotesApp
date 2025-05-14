package com.aki.notesapp.di

import com.aki.notesapp.feature.add_notes.data.repositoryimpl.AddNoteRepositoryImpl
import com.aki.notesapp.feature.add_notes.domain.repository.AddNoteRepository
import com.aki.notesapp.feature.show_notes.data.impl.NotesScreenRepositoryImpl
import com.aki.notesapp.feature.show_notes.domain.NotesScreenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAddNoteRepository(
        impl: AddNoteRepositoryImpl
    ): AddNoteRepository

    @Binds
    abstract fun bindShowNoteRepository(
        impl: NotesScreenRepositoryImpl
    ): NotesScreenRepository
}
