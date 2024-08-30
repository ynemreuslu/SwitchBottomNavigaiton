package com.ynemreuslu.switchbottomnavigaiton.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {

    private val _switchStates = MutableLiveData<MutableMap<Int, Boolean>>()
    val switchStates: LiveData<MutableMap<Int, Boolean>> get() = _switchStates

    private val _egoSwitchState = MutableLiveData<Boolean>()
    val egoSwitchState: LiveData<Boolean> get() = _egoSwitchState

    init {
        _switchStates.value = mutableMapOf()
        _egoSwitchState.value = true
    }

    fun setEgoSwitchState(isChecked: Boolean) {
        _egoSwitchState.value = isChecked
        if (isChecked) {
            _switchStates.value?.clear()
            _switchStates.value = _switchStates.value
        }
    }

    fun addSwitch(id: Int) {
        _switchStates.value?.let { switchStates ->
            if (!switchStates.containsKey(id)) {
                switchStates[id] = true
                _switchStates.value = switchStates
            }
        }
    }

    fun removeSwitch(id: Int) {
        _switchStates.value?.let { switchStates ->
            if (switchStates.containsKey(id)) {
                switchStates.remove(id)
                _switchStates.value = switchStates
            }
        }
    }
}