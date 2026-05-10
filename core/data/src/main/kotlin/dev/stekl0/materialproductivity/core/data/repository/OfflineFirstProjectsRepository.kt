package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.data.model.asWorkContext
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectFolderDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.model.Project
import dev.stekl0.materialproductivity.core.model.ProjectFolder
import dev.stekl0.materialproductivity.core.model.WorkContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal class OfflineFirstProjectsRepository(
    private val projectDao: ProjectDao,
    private val projectFolderDao: ProjectFolderDao,
    private val tagDao: TagDao,
) : ProjectsRepository {
    override fun getProjects(): Flow<List<Project>> =
        projectDao
            .getProjectEntities()
            .map { entities -> entities.map(ProjectEntity::asExternalModel) }

    override fun getProject(projectId: String): Flow<Project?> =
        projectDao
            .getProjectEntity(projectId)
            .map { entity -> entity?.asExternalModel() }

    override fun getProjectFolders(): Flow<List<ProjectFolder>> =
        projectFolderDao
            .getProjectFolderEntities()
            .map { entities -> entities.map(ProjectFolderEntity::asExternalModel) }

    override fun getWorkContexts(): Flow<List<WorkContext>> =
        combine(
            projectDao.getProjectEntities(),
            tagDao.getTagEntities(),
        ) { projects, tags ->
            projects.map(ProjectEntity::asExternalModel).map(Project::asWorkContext) +
                tags.map(TagEntity::asExternalModel).map { tag -> tag.asWorkContext() }
        }

    override suspend fun upsertProjects(projects: List<Project>) {
        projectDao.upsertProjects(projects.map(Project::asEntity))
    }

    override suspend fun upsertProjectFolders(projectFolders: List<ProjectFolder>) {
        projectFolderDao.upsertProjectFolders(projectFolders.map(ProjectFolder::asEntity))
    }

    override suspend fun deleteProjects(ids: List<String>) {
        projectDao.deleteProjects(ids)
    }

    override suspend fun deleteProjectFolders(ids: List<String>) {
        projectFolderDao.deleteProjectFolders(ids)
    }
}
