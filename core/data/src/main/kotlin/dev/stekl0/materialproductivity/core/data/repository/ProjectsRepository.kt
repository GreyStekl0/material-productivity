package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.Project
import dev.stekl0.materialproductivity.core.model.ProjectFolder
import dev.stekl0.materialproductivity.core.model.WorkContext
import kotlinx.coroutines.flow.Flow

public interface ProjectsRepository {
    public fun getProjects(): Flow<List<Project>>

    public fun getProject(projectId: String): Flow<Project?>

    public fun getProjectFolders(): Flow<List<ProjectFolder>>

    public fun getWorkContexts(): Flow<List<WorkContext>>

    public suspend fun upsertProjects(projects: List<Project>)

    public suspend fun upsertProjectFolders(projectFolders: List<ProjectFolder>)

    public suspend fun deleteProjects(ids: List<String>)

    public suspend fun deleteProjectFolders(ids: List<String>)
}
