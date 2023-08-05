package com.sina.core.uicomponents.base

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.sina.core.R
import com.sina.core.network.networkListener.NetworkListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    //    protected val viewModel: BaseViewModel by viewModels()
    @Inject
    lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupViews()
        viewLifecycleOwner.launchWhenStarted {
            networkListener.checkNetworkAvailability().collectLatest {
                if (!it) navigateTo(R.string.network_deep_link)
                else findNavController().backQueue
            }
        }
    }

    fun navigateWithArgsTo(destination: Int, argumentId: String, argumentValue: String) {
        findNavController().navigate(
            NavDeepLinkRequest.Builder.fromUri(
                Uri.parse(getString(destination).replace("{$argumentId}", argumentValue))
            ).build()
        )
    }

    fun navigateWithArgsTo(
        destination: Int,
        firstArgId: String,
        firstArgValue: String,
        secondArgId: String,
        secondArgValue: String,
    ) {
        val deepLinkUri = getString(destination)
            .replace("{$firstArgId}", firstArgValue)
            .replace("{$secondArgId}", secondArgValue)

        findNavController().navigate(
            NavDeepLinkRequest.Builder.fromUri(Uri.parse(deepLinkUri)).build()
        )
    }

    fun navigateTo(destination: Int) {
        findNavController().navigate(
            NavDeepLinkRequest.Builder.fromUri(
                Uri.parse(
                    getString(destination)
                )
            ).build()
        )
    }

    fun navigateToNetworkFragment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setupViews()
    abstract fun observers()

    protected fun back() {
        (requireActivity() as? AppCompatActivity)?.onBackPressed()
    }

    protected fun showToast(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    protected fun showSnack(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    protected fun showSnack(message: Int) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    fun <T> LifecycleOwner.launchWhenStarted(block: suspend CoroutineScope.() -> T) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }
}
