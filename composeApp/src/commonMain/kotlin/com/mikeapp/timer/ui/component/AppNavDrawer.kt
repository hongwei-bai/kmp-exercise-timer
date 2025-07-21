
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikeapp.timer.data.model.TimerType
import kotlinx.coroutines.launch

@Composable
fun AppNavDrawer(
    modifier: Modifier = Modifier,
    drawerContent: @Composable (ColumnScope.() -> Unit),
    bodyContent: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val runningTimers = listOf(
        TimerType.MINIMALISM,
        TimerType.ALARM
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        runningTimers.forEach { timer ->
                            Icon(
                                imageVector = when (timer) {
                                    TimerType.MINIMALISM -> Icons.Default.AccessTime
                                    TimerType.GYM_REST -> Icons.Default.FitnessCenter
                                    TimerType.ALARM -> Icons.Default.Alarm
                                },
                                contentDescription = timer.name,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 4.dp
            )
        },
        drawerContent = drawerContent,
        modifier = modifier
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            bodyContent()
        }
    }
}
