package com.example.ddinerapp.featureStartOrder.presentation.orderingDesks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_BUSINESS_CNPJ
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_SELECTED_DESK_ID
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_USER_CPF
import com.example.ddinerapp.featureHome.domain.model.Desk
import com.example.ddinerapp.featureStartOrder.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class DesksViewModel @Inject constructor(
    private val session: DDinerSession,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _desks = mutableStateListOf<Desk>()
    val desks: List<Desk> = _desks

    init {
        getDesks()
    }

    private fun getDesks() {
        _loading.value = true
        viewModelScope.launch {
            mainUseCases.getDesksUseCase(session.getField(PREF_BUSINESS_CNPJ).first())
                .addSnapshotListener { snapshot, exception ->

                    if (exception != null) {
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
                                    val find = find { desk -> desk.id == it.id }
                                    set(
                                        indexOf(find), Desk(
                                            id = it.id,
                                            description = it["description"].toString(),
                                            isOccupied = it["isOccupied"] as Boolean
                                        )
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                    _loading.value = false
                }
        }
    }

    fun selectDesk(desk: Desk) {
        viewModelScope.launch {
            session.run {
                val cnpj = getField(PREF_BUSINESS_CNPJ).first()
                val cpf = getField(PREF_USER_CPF).first()
                when (desk.description) {
                    "Delivery" -> {
                        mainUseCases.addOrderUseCase(desk.id, cnpj, cpf)
                    }

                    else -> {
                        if (!desk.isOccupied) {
                            mainUseCases.setOccupiedDeskUseCase(desk.id, cnpj)
                            mainUseCases.addOrderUseCase(desk.id, cnpj, cpf)
                        }
                    }
                }

                saveField(PREF_SELECTED_DESK_ID, desk.id)
            }
        }
    }

    fun disoccupyDesk() {
        viewModelScope.launch {
            session.run {
                mainUseCases.disoccupyDeskUseCase(
                    getField(PREF_BUSINESS_CNPJ).first(),
                    getField(PREF_SELECTED_DESK_ID).first()
                )
            }
        }
    }
}