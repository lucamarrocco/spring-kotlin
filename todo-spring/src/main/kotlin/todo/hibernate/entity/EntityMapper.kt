package todo.hibernate.entity

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import todo.domain.model.Milestone
import todo.domain.model.Project
import todo.domain.model.WorkItem
import todo.domain.repository.UserRepository

@Mapper(componentModel = "spring")
abstract class EntityMapper {

    @Mapping(source = "author", target = "authorId", qualifiedByName = ["getUserId"])
    @Mapping(source = "author", target = "authorName", qualifiedByName = ["getUserName"])
    @Mapping(source = "createdBy", target = "createdById", qualifiedByName = ["getUserId"])
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = ["getUserName"])
    @Mapping(source = "updatedBy", target = "updatedById", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = ["getUserName"])
    abstract fun entityModel(from: ProjectEntity): Project

    @Mapping(target = "author", expression = "java(userRepository.getUserByUserId(from.getAuthorId()))")
    @Mapping(target = "createdBy", expression = "java(userRepository.getUserByUserId(from.getCreatedById()))")
    @Mapping(target = "updatedBy", expression = "java(userRepository.getUserByUserId(from.getUpdatedById()))")
    abstract fun modelEntity(from: Project, userRepository: UserRepository): ProjectEntity

    @Mapping(source = "author", target = "authorId", qualifiedByName = ["getUserId"])
    @Mapping(source = "author", target = "authorName", qualifiedByName = ["getUserName"])
    @Mapping(source = "createdBy", target = "createdById", qualifiedByName = ["getUserId"])
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = ["getUserName"])
    @Mapping(source = "updatedBy", target = "updatedById", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = ["getUserName"])
    abstract fun entityModel(from: MilestoneEntity): Milestone

    @Mapping(target = "author", expression = "java(userRepository.getUserByUserId(from.getAuthorId()))")
    @Mapping(target = "createdBy", expression = "java(userRepository.getUserByUserId(from.getCreatedById()))")
    @Mapping(target = "updatedBy", expression = "java(userRepository.getUserByUserId(from.getUpdatedById()))")
    abstract fun modelEntity(from: Milestone, userRepository: UserRepository): MilestoneEntity

    @Mapping(source = "createdBy", target = "createdById", qualifiedByName = ["getUserId"])
    @Mapping(source = "updatedBy", target = "updatedById", qualifiedByName = ["getUserId"])
    @Mapping(source = "project", target = "projectId", qualifiedByName = ["getProjectId"])
    @Mapping(source = "project", target = "projectName", qualifiedByName = ["getProjectName"])
    @Mapping(source = "milestone", target = "milestoneId", qualifiedByName = ["getMilestoneId"])
    @Mapping(source = "milestone", target = "milestoneName", qualifiedByName = ["getMilestoneName"])
    abstract fun entityModel(from: WorkItemEntity): WorkItem

    @Mapping(target = "author", expression = "java(userRepository.getUserByUserId(from.getAuthorId()))")
    @Mapping(target = "assignee", expression = "java(userRepository.getUserByUserId(from.getAssigneeId()))")
    @Mapping(target = "createdBy", expression = "java(userRepository.getUserByUserId(from.getCreatedById()))")
    @Mapping(target = "updatedBy", expression = "java(userRepository.getUserByUserId(from.getUpdatedById()))")
    abstract fun modelEntity(from: WorkItem, userRepository: UserRepository): WorkItemEntity

    @Named("getUserId")
    open fun getUserId(user: UserEntity?) = user?.id?.toString()

    @Named("getUserName")
    open fun getUserName(user: UserEntity?) = user?.name

    @Named("getProjectId")
    open fun getProjectId(project: ProjectEntity?) = project?.id

    @Named("getProjectName")
    open fun getProjectName(project: ProjectEntity?) = project?.name

    @Named("getMilestoneId")
    open fun getMilestoneId(milestone: MilestoneEntity?) = milestone?.id

    @Named("getMilestoneName")
    open fun getMilestoneName(milestone: MilestoneEntity?) = milestone?.name
}
