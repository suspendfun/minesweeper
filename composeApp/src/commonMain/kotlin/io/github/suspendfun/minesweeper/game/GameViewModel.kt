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

@ConsistentCopyVisibility
@Immutable
data class GameUiState private constructor(
    val board: Board,
    private val _minesLeft: Int,
) {
    val minesLeft: String by lazy {
        _minesLeft.format(MINES_LEFT_FIELD_WIDTH)
    }

    companion object {
        fun create(config: GameConfig = DefaultGameConfig) =
            GameUiState(
                board = Board.Builder(config)
                    .build(),
                _minesLeft = config.mines,
            )
    }
}

@ConsistentCopyVisibility
@Immutable
data class TimerUiState private constructor(
    private val _seconds: Int = 0,
) {
    val value: String by lazy {
        val m = (_seconds / SECONDS_PER_MINUTE).format(TIMER_FIELD_WIDTH)
        val s = (_seconds % SECONDS_PER_MINUTE).format(TIMER_FIELD_WIDTH)
        "$m:$s"
    }

    companion object {
        fun create(seconds: Int = 0) = TimerUiState(seconds)
    }
}

@Stable
class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameUiState.create())
    val gameState: StateFlow<GameUiState> = _gameState.asStateFlow()

    private val _timerState = MutableStateFlow(TimerUiState.create())
    val timerState: StateFlow<TimerUiState> = _timerState.asStateFlow()

    private var timerJob: Job? = null
    private var timerSeconds: Int = 0

    fun restart() {
        pauseTimer()
        timerSeconds = 0
        // TODO: Must be called after the first cell is revealed, not now
        startTimerIfNeeded()
        _gameState.value = GameUiState.create()
        _timerState.value = TimerUiState.create()
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun resumeTimer() = startTimerIfNeeded()

    private fun startTimerIfNeeded() {
        if (timerJob != null) return
        timerJob = viewModelScope.launch {
            val start = TimeSource.Monotonic.markNow()
            val offset = timerSeconds
            while (true) {
                delay(TIMER_INTERVAL_MS)
                timerSeconds = offset + start.elapsedNow().inWholeSeconds.toInt()
                _timerState.update { TimerUiState.create(timerSeconds) }
            }
        }
    }
}

private fun Int.format(padding: Int) =
    toString().padStart(padding, '0')
