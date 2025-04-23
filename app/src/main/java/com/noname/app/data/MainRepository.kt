package com.noname.app.data


import com.noname.app.data.Model.RepositoryDto
import com.noname.app.data.remote.RemoteData
import com.task.data.local.LocalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class MainRepository(
    private val serviceGenerator: RemoteData,
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun getUserRepositories(username: String, page: Int, perPage: Int): Flow<Resource<List<RepositoryDto>>> {
        return flow {
            emit(serviceGenerator.getUserRepositories(username, page, perPage))
        }.flowOn(ioDispatcher)
    }

}