package todo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemsFilter
import todo.domain.repository.WorkItemRepository


@RestController
class WorkItemController {

    @Autowired
    private lateinit var workItemRepository: WorkItemRepository

    @Transactional
    @CrossOrigin("http://localhost:3000")
    @PostMapping("/work-item", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createWorkItem(@RequestBody workItem: WorkItem) = workItemRepository.save(workItem)

    @Transactional(readOnly = true)
    @CrossOrigin("http://localhost:3000")
    @PostMapping("/work-items", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findWorkItems(@RequestBody filter: WorkItemsFilter) = workItemRepository.find(filter)
}
