package com.nuguollim.data.repository.template

import com.nugu.nuguollim.common.data.model.template.AllMyWritingTemplateData
import com.nugu.nuguollim.common.data.model.template.AllTemplate
import com.nugu.nuguollim.common.data.model.template.FavoriteData
import com.nugu.nuguollim.common.data.model.template.Template
import com.nugu.nuguollim.common.data.model.template.Writing

interface TemplateRepository {

    suspend fun getTemplates(
        page: Int,
        sort: String,
        keyword: String? = null,
        themeId: Long? = null,
        targetId: Long? = null
    ): AllTemplate

    suspend fun addFavorite(id: Long): FavoriteData

    suspend fun removeFavorite(id: Long): FavoriteData

    suspend fun saveTemplate(writing: Writing): Writing

    suspend fun getMyWritingTemplates(page: Int): AllMyWritingTemplateData

    suspend fun getFavoriteTemplates(page: Int): AllTemplate

    suspend fun removeTemplate(id: Long)

    suspend fun getTemplate(id: Long): Template

}