package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab13_animaciones.ui.theme.Lab13animacionesTheme

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
                    label = { Text("Ejercicio 1") }
                )
                NavigationBarItem(
                    selected = currentScreen == 1,
                    onClick = { currentScreen = 1 },
                    icon = { Text("2") },
                    label = { Text("Ejercicio 2") }
                )
                NavigationBarItem(
                    selected = currentScreen == 2,
                    onClick = { currentScreen = 2 },
                    icon = { Text("3") },
                    label = { Text("Ejercicio 3") }
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
                    else -> "Ejercicio 3: Tamaño y Posición"
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Lab13animacionesTheme {
        MainScreen()
    }
}