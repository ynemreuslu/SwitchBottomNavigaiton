package com.ynemreuslu.switchbottomnavigaiton.screen.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ynemreuslu.switchbottomnavigaiton.utils.MenuManager
import com.ynemreuslu.switchbottomnavigaiton.R
import com.ynemreuslu.switchbottomnavigaiton.databinding.FragmentHomeScreenBinding
import com.ynemreuslu.switchbottomnavigaiton.utils.Constants.TIMEOUT_DURATION
import com.ynemreuslu.switchbottomnavigaiton.utils.snack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuManager: MenuManager
    private val viewModel: HomeScreenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        menuManager = MenuManager(bottomNav)


        viewModel.switchStates.observe(viewLifecycleOwner) { states ->
            updateSwitches(states)
        }
        viewModel.egoSwitchState.observe(viewLifecycleOwner) { isChecked ->
            setBottomNavigationVisibility(!isChecked)
            setSwitchesEnabled(!isChecked)
        }

        setupSwitchListeners()
        configureEgoSwitch()
    }


    private fun setupSwitchListeners() {
        binding.switchHappiness.setOnCheckedChangeListener { _, isChecked ->
            handleMenuItem(
                isChecked,
                R.id.happinessScreen,
                getString(R.string.happiness),
                R.drawable.happy
            )
        }

        binding.switchGiving.setOnCheckedChangeListener { _, isChecked ->
            handleMenuItem(
                isChecked,
                R.id.givingScreen,
                getString(R.string.giving),
                R.drawable.giving
            )
        }

        binding.switchRespect.setOnCheckedChangeListener { _, isChecked ->
            handleMenuItem(
                isChecked,
                R.id.respectScreen,
                getString(R.string.respect),
                R.drawable.respect
            )
        }

        binding.switchKindness.setOnCheckedChangeListener { _, isChecked ->
            handleMenuItem(
                isChecked,
                R.id.kindnessScreen,
                getString(R.string.kindness),
                R.drawable.charity
            )
        }

        binding.switchOptimism.setOnCheckedChangeListener { _, isChecked ->
            handleMenuItem(
                isChecked,
                R.id.optimismScreen,
                getString(R.string.optimism),
                R.drawable.optimism
            )
        }
    }

    private fun handleMenuItem(isChecked: Boolean, id: Int, title: String, iconResId: Int) {
        if (isChecked) {
            if (!menuManager.isItemPresent(id)) {
                if (menuManager.getMenuSize() < 5) {
                    menuManager.addItem(id, title, iconResId)
                    viewModel.addSwitch(id)
                } else {
                    showMaxItemsExceededMessage()
                    setSwitchChecked(id)

                }
            }
        } else {
            menuManager.removeItem(id)
            viewModel.removeSwitch(id)
        }
    }

    private fun showMaxItemsExceededMessage() {
        if (menuManager.getMenuSize() == 5) {
            binding.root.snack(getString(R.string.error_max_items_exceeded), Snackbar.LENGTH_SHORT)
        }
    }

    private fun setSwitchChecked(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            when (id) {
                R.id.happinessScreen -> {
                    binding.switchHappiness.isChecked = true
                    delay(timeMillis = TIMEOUT_DURATION)
                    binding.switchHappiness.isChecked = false
                }

                R.id.givingScreen -> {
                    binding.switchGiving.isChecked = true
                    delay(TIMEOUT_DURATION)
                    binding.switchGiving.isChecked = false
                }

                R.id.respectScreen -> {
                    binding.switchRespect.isChecked = true
                    delay(TIMEOUT_DURATION)
                    binding.switchRespect.isChecked = false
                }

                R.id.kindnessScreen -> {
                    binding.switchKindness.isChecked = true
                    delay(TIMEOUT_DURATION)
                    binding.switchKindness.isChecked = false
                }

                R.id.optimismScreen -> {
                    binding.switchOptimism.isChecked = true
                    delay(TIMEOUT_DURATION)
                    binding.switchOptimism.isChecked = false
                }
            }
        }
    }

    private fun configureEgoSwitch() {
        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            val isEnabled = !isChecked
            setSwitchesEnabled(isEnabled)
            if (isChecked) {
                resetSwitches()
                setBottomNavigationVisibility(isVisible = false)
            } else {
                setBottomNavigationVisibility(isVisible = true)
            }
            viewModel.setEgoSwitchState(isChecked)
        }
    }

    private fun setSwitchesEnabled(isEnabled: Boolean) {
        binding.switchHappiness.isClickable = isEnabled
        binding.switchOptimism.isClickable = isEnabled
        binding.switchGiving.isClickable = isEnabled
        binding.switchKindness.isClickable = isEnabled
        binding.switchRespect.isClickable = isEnabled
    }

    private fun resetSwitches() {
        binding.switchHappiness.isChecked = false
        binding.switchOptimism.isChecked = false
        binding.switchGiving.isChecked = false
        binding.switchKindness.isChecked = false
        binding.switchRespect.isChecked = false
    }

    private fun setBottomNavigationVisibility(isVisible: Boolean) {
        val bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun updateSwitches(states: Map<Int, Boolean>) {
        binding.switchHappiness.isChecked = states[R.id.happinessScreen] ?: false
        binding.switchOptimism.isChecked = states[R.id.optimismScreen] ?: false
        binding.switchGiving.isChecked = states[R.id.givingScreen] ?: false
        binding.switchKindness.isChecked = states[R.id.kindnessScreen] ?: false
        binding.switchRespect.isChecked = states[R.id.respectScreen] ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}