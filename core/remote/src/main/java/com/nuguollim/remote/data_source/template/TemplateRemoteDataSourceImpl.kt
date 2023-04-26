package com.nuguollim.remote.data_source.template

import com.nuguollim.remote.model.template.AllTemplateResponse
import com.nuguollim.remote.service.template.TemplateService
import javax.inject.Inject

class TemplateRemoteDataSourceImpl @Inject constructor(
    private val templateService: TemplateService
) : TemplateRemoteDataSource {

    override suspend fun getTemplates(page: Int, sort: String, keyword: String?): AllTemplateResponse =
        templateService.getTemplates(page = page, sort = sort, keyword = keyword)
}