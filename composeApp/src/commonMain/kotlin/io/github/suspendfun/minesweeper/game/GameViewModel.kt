package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.TimeSource

private const val MINES_LEFT_FIELD_WIDTH = 2
private const val TIMER_INTERVAL_MS = 1_000L
private const val SECONDS_PER_MINUTE = 60
private const val TIMER_FIELD_WIDTH = 2

@Immutable
data class GameUiState(
    private val _minesLeft: Int = 0,
) {
    val minesLeft: String by lazy {
        _minesLeft.format(MINES_LEFT_FIELD_WIDTH)
    }
}

@Immutable
data class TimerUiState(
    private val _seconds: Int = 0,
) {
    val timer: String by lazy {
        val m = (_seconds / SECONDS_PER_MINUTE).format(TIMER_FIELD_WIDTH)
        val s = (_seconds % SECONDS_PER_MINUTE).format(TIMER_FIELD_WIDTH)
        "$m:$s"
    }
}

@Stable
class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameUiState())
    val gameState: StateFlow<GameUiState> = _gameState.asStateFlow()

    private val _timerState = MutableStateFlow(TimerUiState())
    val timerState: StateFlow<TimerUiState> = _timerState.asStateFlow()

    private var timerJob: Job? = null

    fun restart() {
        stopTimer()
        // TODO: Must be called after the first cell is revealed, not now
        startTimerIfNeeded()
        _gameState.value = GameUiState()
        _timerState.value = TimerUiState()
    }

    private fun startTimerIfNeeded() {
        if (timerJob != null) return
        timerJob = viewModelScope.launch {
            val start = TimeSource.Monotonic.markNow()
            while (true) {
                delay(TIMER_INTERVAL_MS)
                val elapsed = start.elapsedNow().inWholeSeconds
                _timerState.update { it.copy(_seconds = elapsed.toInt()) }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}

private fun Int.format(padding: Int) =
    toString().padStart(padding, '0')
