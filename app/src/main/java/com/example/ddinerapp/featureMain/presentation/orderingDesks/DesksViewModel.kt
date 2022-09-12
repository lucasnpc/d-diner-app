package com.example.ddinerapp.featureMain.presentation.orderingDesks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesksViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {
    private val _desks = mutableStateListOf<Desk>()
    val desks: List<Desk> = _desks

    init {
        getDesks()
    }

    private fun getDesks() {
        viewModelScope.launch {
            mainUseCases.getDesksUseCase(storeManager.businessCnpj.first())
                .addSnapshotListener { snapshot, exception ->

                    if (exception != null) {
                        println(exception.message)
                        return@addSnapshotListener
                    }

                    snapshot?.documentChanges?.map { doc ->
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> doc.document.let {
                                _desks.add(
                                    Desk(
                                        id = it.id,
                                        description = it["description"] as String,
                                        isOccupied = it["isOccupied"] as Boolean
                                    )
                                )
                            }
                            DocumentChange.Type.MODIFIED -> doc.document.let {
                                _desks.run {
                                    val index =
                                        find { desk -> it.id == desk.id }
                                    remove(index)
                                    add(
                                        Desk(
                                            description = it["description"].toString(),
                                            isOccupied = it["isOccupied"] as Boolean
                                        )
                                    )
                                }
                            }
                            else -> Unit
                        }
                    }
                }
        }
    }

    fun selectDesk(desk: Desk) {
        viewModelScope.launch {
            storeManager.run {
                if (!desk.isOccupied)
                    mainUseCases.setOccupiedDeskUseCase(desk, businessCnpj.first())
                setSelectedDesk(desk.id)
            }
        }
    }
}