package com.example.ortho_shoulder_ai.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PatientStateManager {
    private val _needsAttentionMap = mutableMapOf<String, Boolean>()
    private val _needsAttentionCount = MutableStateFlow(0)
    
    val needsAttentionCount: StateFlow<Int> = _needsAttentionCount.asStateFlow()
    
    fun setNeedsAttention(patientId: String, needsAttention: Boolean) {
        val previousValue = _needsAttentionMap[patientId] ?: false
        _needsAttentionMap[patientId] = needsAttention
        
        // Update count
        if (needsAttention && !previousValue) {
            _needsAttentionCount.value++
        } else if (!needsAttention && previousValue) {
            _needsAttentionCount.value--
        }
    }
    
    fun getNeedsAttention(patientId: String): Boolean {
        return _needsAttentionMap[patientId] ?: false
    }
    
    fun getCount(): Int {
        return _needsAttentionCount.value
    }
}
