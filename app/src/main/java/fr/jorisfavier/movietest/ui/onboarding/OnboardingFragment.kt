package fr.jorisfavier.movietest.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.databinding.OnboardingFragmentBinding
import fr.jorisfavier.movietest.utils.autoCleared
import fr.jorisfavier.movietest.utils.next
import fr.jorisfavier.movietest.utils.previous

class OnboardingFragment : Fragment(R.layout.onboarding_fragment) {

    private val binding: OnboardingFragmentBinding by autoCleared {
        OnboardingFragmentBinding.bind(requireView())
    }

    private val adapter = OnboardingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            pager.adapter = adapter
            nextButton.setOnClickListener {
                pager.next {
                    findNavController().navigate(R.id.movieListFragment)
                }
            }
            previousButton.setOnClickListener {
                pager.previous()
            }
        }
    }
}