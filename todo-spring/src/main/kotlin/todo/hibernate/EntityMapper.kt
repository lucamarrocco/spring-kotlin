package todo.hibernate

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import todo.domain.model.Milestone
import todo.domain.model.Project
import todo.domain.model.WorkItem
import todo.domain.repository.UserRepository
import todo.hibernate.entity.MilestoneEntity
import todo.hibernate.entity.ProjectEntity
import todo.hibernate.entity.UserEntity
import todo.hibernate.entity.WorkItemEntity

@Mapper(componentModel = "spring")
abstract class EntityMapper {

    @Mapping(source = "authorUser", target = "authorUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "authorUser", target = "authorUserName", qualifiedByName = ["getUserName"])
    @Mapping(source = "createdByUser", target = "createdByUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "createdByUser", target = "createdByUserName", qualifiedByName = ["getUserName"])
    @Mapping(source = "updatedByUser", target = "updatedByUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedByUser", target = "updatedByUserName", qualifiedByName = ["getUserName"])
    abstract fun entityModel(from: ProjectEntity): Project

    @Mapping(target = "authorUser", expression = "java(userRepository.getUserByUserId(from.getAuthorUserId()))")
    @Mapping(target = "createdByUser", expression = "java(userRepository.getUserByUserId(from.getCreatedByUserId()))")
    @Mapping(target = "updatedByUser", expression = "java(userRepository.getUserByUserId(from.getUpdatedByUserId()))")
    abstract fun modelEntity(from: Project, userRepository: UserRepository): ProjectEntity

    @Mapping(source = "authorUser", target = "authorUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "authorUser", target = "authorUserName", qualifiedByName = ["getUserName"])
    @Mapping(source = "createdByUser", target = "createdByUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "createdByUser", target = "createdByUserName", qualifiedByName = ["getUserName"])
    @Mapping(source = "updatedByUser", target = "updatedByUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedByUser", target = "updatedByUserName", qualifiedByName = ["getUserName"])
    abstract fun entityModel(from: MilestoneEntity): Milestone

    @Mapping(target = "authorUser", expression = "java(userRepository.getUserByUserId(from.getAuthorUserId()))")
    @Mapping(target = "createdByUser", expression = "java(userRepository.getUserByUserId(from.getCreatedByUserId()))")
    @Mapping(target = "updatedByUser", expression = "java(userRepository.getUserByUserId(from.getUpdatedByUserId()))")
    abstract fun modelEntity(from: Milestone, userRepository: UserRepository): MilestoneEntity

    @Mapping(source = "project", target = "projectId", qualifiedByName = ["getProjectId"])
    @Mapping(source = "project", target = "projectName", qualifiedByName = ["getProjectName"])
    @Mapping(source = "milestone", target = "milestoneId", qualifiedByName = ["getMilestoneId"])
    @Mapping(source = "milestone", target = "milestoneName", qualifiedByName = ["getMilestoneName"])
    @Mapping(source = "createdByUser", target = "createdByUserId", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedByUser", target = "updatedByUserId", qualifiedByName = ["getUserId"])
    abstract fun entityModel(from: WorkItemEntity): WorkItem

    @Mapping(target = "authorUser", expression = "java(userRepository.getUserByUserId(from.getAuthorUserId()))")
    @Mapping(target = "assigneeUser", expression = "java(userRepository.getUserByUserId(from.getAssigneeUserId()))")
    @Mapping(target = "createdByUser", expression = "java(userRepository.getUserByUserId(from.getCreatedByUserId()))")
    @Mapping(target = "updatedByUser", expression = "java(userRepository.getUserByUserId(from.getUpdatedByUserId()))")
    abstract fun modelEntity(from: WorkItem, userRepository: UserRepository): WorkItemEntity

    @Named("getUserId")
    open fun getUserId(user: UserEntity?) = user?.id?.toString()

    @Named("getUserName")
    open fun getUserName(user: UserEntity?) = user?.title

    @Named("getProjectId")
    open fun getProjectId(project: ProjectEntity?) = project?.id

    @Named("getProjectName")
    open fun getProjectName(project: ProjectEntity?) = project?.title

    @Named("getMilestoneId")
    open fun getMilestoneId(milestone: MilestoneEntity?) = milestone?.id

    @Named("getMilestoneName")
    open fun getMilestoneName(milestone: MilestoneEntity?) = milestone?.title
}
