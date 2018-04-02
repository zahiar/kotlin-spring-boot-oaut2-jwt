package com.zahiar.resource

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ResourceController {

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = [RequestMethod.GET], value = ["/resource"])
    fun readPermission(): String {
        return "You have 'read' permission"

    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @RequestMapping(method = [RequestMethod.POST], value = ["/resource"])
    fun writePermission(): String {
        return "You have 'write' permission"
    }

}
