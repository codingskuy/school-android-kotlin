package io.codingskuy.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.codingskuy.todo.data.repositories.TaskRepositoryImpl
import io.codingskuy.todo.domain.repositories.TaskRepository
import io.codingskuy.todo.domain.usecase.AddTaskUseCase
import io.codingskuy.todo.domain.usecase.DeleteTaskUseCase
import io.codingskuy.todo.domain.usecase.GetTasksUseCase
import io.codingskuy.todo.domain.usecase.ToggleDoneUseCase
import io.codingskuy.todo.presentation.utils.TaskFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository = TaskRepositoryImpl

    @Provides
    fun provideGetTasksUseCase(repository: TaskRepository) = GetTasksUseCase(repository)

    @Provides
    fun provideAddTaskUseCase(repository: TaskRepository) = AddTaskUseCase(repository)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository) = DeleteTaskUseCase(repository)

    @Provides
    fun provideToggleDoneUseCase(repository: TaskRepository) = ToggleDoneUseCase(repository)

    @Provides
    fun provideTaskFormatter() = TaskFormatter()
}