package todo.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemQuery
import todo.domain.repository.WorkItemRepository


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class WorkItemController {

    @Autowired
    private lateinit var workItemRepository: WorkItemRepository

    @Transactional
    @PostMapping("/work-item", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createWorkItem(@RequestBody workItem: WorkItem) = workItemRepository.save(workItem)

    @Transactional(readOnly = true)
    @GetMapping("/work-item")
    fun findWorkItems(query: WorkItemQuery) = workItemRepository.find(query)
}
