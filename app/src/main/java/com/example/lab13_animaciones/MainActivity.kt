package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab13_animaciones.ui.theme.Lab13animacionesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13animacionesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen == 0,
                    onClick = { currentScreen = 0 },
                    icon = { Text("1") },
                )
                NavigationBarItem(
                    selected = currentScreen == 1,
                    onClick = { currentScreen = 1 },
                    icon = { Text("2") },
                )
                NavigationBarItem(
                    selected = currentScreen == 2,
                    onClick = { currentScreen = 2 },
                    icon = { Text("3") },
                )
                NavigationBarItem(
                    selected = currentScreen == 3,
                    onClick = { currentScreen = 3 },
                    icon = { Text("4") },
                )
                NavigationBarItem(
                    selected = currentScreen == 4,
                    onClick = { currentScreen = 4 },
                    icon = { Text("5") },
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = when (currentScreen) {
                    0 -> "Ejercicio 1: AnimatedVisibility"
                    1 -> "Ejercicio 2: Color Animation"
                    2 -> "Ejercicio 3: Tamaño y Posición"
                    3 -> "Ejercicio 4: Estados Animados"
                    else -> "Ejercicio 5: Animaciones Combinadas"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            
            when (currentScreen) {
                0 -> AnimationDemo()
                1 -> ColorAnimationDemo()
                2 -> SizeAndPositionDemo()
                3 -> StateTransitionDemo()
                4 -> CombinedAnimationsDemo()
            }
        }
    }
}

@Composable
fun AnimationDemo(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { isVisible = !isVisible },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = if (isVisible) "Ocultar" else "Mostrar")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(durationMillis = 1000)
            ) + slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = fadeOut(
                targetAlpha = 0f,
                animationSpec = tween(durationMillis = 1000)
            ) + slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun ColorAnimationDemo(modifier: Modifier = Modifier) {
    var isColorChanged by remember { mutableStateOf(false) }
    val targetColor = if (isColorChanged) Color.Green else Color.Blue
    
    val color by animateColorAsState(
        targetValue = targetColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "color"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { isColorChanged = !isColorChanged },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Cambiar Color")
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color)
        )
    }
}

@Composable
fun SizeAndPositionDemo(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    
    val size by animateDpAsState(
        targetValue = if (isExpanded) 300.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "size"
    )
    
    val offsetX by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offsetX"
    )
    
    val offsetY by animateDpAsState(
        targetValue = if (isExpanded) 50.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offsetY"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            // Contenedor para el cuadro animado con espacio suficiente
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = offsetX, y = offsetY)
                        .size(size)
                        .background(Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(9.dp))
            
            Button(
                onClick = { isExpanded = !isExpanded }
            ) {
                Text(text = if (isExpanded) "Contraer" else "Expandir")
            }

            Spacer(modifier = Modifier.height(7.dp))
            
            Text(
                text = "Orden:\n1. offset\n2. size\n3. background",
                textAlign = TextAlign.Center
            )
        }
    }
}

enum class ContentState {
    Loading,
    Content,
    Error
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StateTransitionDemo(modifier: Modifier = Modifier) {
    var currentState by remember { mutableStateOf(ContentState.Loading) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botones para cambiar estados
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        currentState = ContentState.Loading
                        delay(2000) // Simular carga
                        currentState = ContentState.Content
                    }
                }
            ) {
                Text("Cargar")
            }
            
            Button(
                onClick = { currentState = ContentState.Error }
            ) {
                Text("Error")
            }
        }

        // Contenedor animado
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            AnimatedContent(
                targetState = currentState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 500)) with
                    fadeOut(animationSpec = tween(durationMillis = 500)) using
                    SizeTransform { initialSize, targetSize ->
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    }
                },
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { state ->
                when (state) {
                    ContentState.Loading -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Cargando...",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    ContentState.Content -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Success",
                                modifier = Modifier.size(64.dp),
                                tint = Color.Green
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "¡Contenido Cargado!",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    ContentState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Error",
                                modifier = Modifier.size(64.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "¡Error al cargar!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CombinedAnimationsDemo(modifier: Modifier = Modifier) {
    var isDarkMode by remember { mutableStateOf(false) }
    var isBoxExpanded by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    
    // Animaciones para el cuadro
    val boxSize by animateDpAsState(
        targetValue = if (isBoxExpanded) 200.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "size"
    )
    
    val boxColor by animateColorAsState(
        targetValue = when {
            isDarkMode && isBoxExpanded -> Color.Cyan
            isDarkMode -> Color.Blue
            isBoxExpanded -> Color.Magenta
            else -> Color.Red
        },
        animationSpec = tween(durationMillis = 500),
        label = "color"
    )
    
    // Animación para el offset del botón
    val buttonOffset by animateDpAsState(
        targetValue = if (isButtonVisible) 0.dp else 200.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offset"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color.DarkGray else Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón de modo oscuro/claro con AnimatedContent
            AnimatedContent(
                targetState = isDarkMode,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 500)) with
                    fadeOut(animationSpec = tween(durationMillis = 500)) using
                    SizeTransform { _, _ ->
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    }
                }
            ) { dark ->
                IconButton(
                    onClick = { isDarkMode = !isDarkMode }
                ) {
                    Icon(
                        imageVector = if (dark) Icons.Outlined.Star else Icons.Outlined.Close,
                        contentDescription = if (dark) "Cambiar a modo claro" else "Cambiar a modo oscuro",
                        tint = if (dark) Color.White else Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Cuadro animado que cambia de tamaño y color
            Box(
                modifier = Modifier
                    .size(boxSize)
                    .background(boxColor)
                    .clickable { isBoxExpanded = !isBoxExpanded }
            )

            // Botón con AnimatedVisibility
            AnimatedVisibility(
                visible = isButtonVisible,
                enter = slideInVertically(
                    initialOffsetY = { it * 2 }, // Empieza desde más abajo
                    animationSpec = tween(durationMillis = 800, easing = EaseOutBack)
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 500)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it * 2 }, // Sale hacia abajo
                    animationSpec = tween(durationMillis = 800, easing = EaseInBack)
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Button(
                    onClick = { isButtonVisible = false },
                    modifier = Modifier.offset(y = buttonOffset)
                ) {
                    Text("¡Desaparecer!")
                }
            }

            // Botón para resetear con AnimatedVisibility
            AnimatedVisibility(
                visible = !isButtonVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it }, // Entra desde arriba
                    animationSpec = tween(durationMillis = 800, easing = EaseOutBack)
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 500)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { -it }, // Sale hacia arriba
                    animationSpec = tween(durationMillis = 800, easing = EaseInBack)
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Button(
                    onClick = {
                        isButtonVisible = true
                        isBoxExpanded = false
                    }
                ) {
                    Text("Resetear")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Lab13animacionesTheme {
        MainScreen()
    }
}