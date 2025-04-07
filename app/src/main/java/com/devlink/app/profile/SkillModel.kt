package com.devlink.app.profile

import androidx.compose.runtime.MutableState

data class SkillRequest(
    val skills: List<String>
)

data class SkillResponse(
    val message: String
)
