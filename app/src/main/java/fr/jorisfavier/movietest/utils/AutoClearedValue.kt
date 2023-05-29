package fr.jorisfavier.movietest.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw NPE.
 * Based on: https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedValue.kt
 */
class AutoClearedValue<T : Any>(
    private val fragment: Fragment,
    private val onClear: (T?) -> Unit,
    private val initializer: () -> T
) : ReadOnlyProperty<Fragment, T> {

    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            onClear(_value)
                            _value = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (_value == null) {
            ensureValidViewLifecycleState()
            _value = initializer()
        }

        return _value!!
    }

    private fun ensureValidViewLifecycleState() {
        val viewLifecycleOwner = checkNotNull(fragment.viewLifecycleOwnerLiveData.value) {
            "The view lifecycle is not available (yet). Did you access the auto-cleared-value before onCreateView()?"
        }

        val state = viewLifecycleOwner.lifecycle.currentState
        check(state.isAtLeast(Lifecycle.State.INITIALIZED)) {
            "The view lifecycle is in an invalid state: $state. Did you access the auto-cleared-value after onDestroyView()?"
        }
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared(onClear: (T?) -> kotlin.Unit = {}, initializer: () -> T) =
    AutoClearedValue(this, onClear, initializer)