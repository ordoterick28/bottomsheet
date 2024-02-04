package com.example.sheet2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sheet2.ui.theme.Sheet2Theme


import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sheet2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TutorialBottomSheetScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun TutorialBottomSheetScreen() {
    var showBottomSheetScaffold by rememberSaveable { mutableStateOf(false) }
    val showModalBottomSheet = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showBottomSheetScaffold = !showBottomSheetScaffold
        }) {
            Text(text = "Show/Hide BottomSheetScaffold")
        }
        Button(onClick = {
            showModalBottomSheet.value = !showModalBottomSheet.value
        }) {
            Text(text = "Show/Hide ModalBottomSheet")
        }
    }

if (showBottomSheetScaffold) TutorialModalBottomSheet(showModalBottomSheet)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialBottomSheetScaffold() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 340.dp,
        sheetContent = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start)
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share")
                Text(text = "Share")
            }

            Button(onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } }) {
                Text(text = "Expand BottomSheet")
            }
            Button(onClick = { scope.launch { scaffoldState.bottomSheetState.partialExpand() } }) {
                Text(text = "PartialExpand BottomSheet")
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.4f) )
            )
//            Text(
//                text = "Android",
//                modifier = Modifier
//                    .background(color = Color.Green)
//                    .padding(8.dp)
//            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialModalBottomSheet(showModalBottomSheet: MutableState<Boolean>) {
    val scope = rememberCoroutineScope()
    var skipPartially by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartially)

    if (showModalBottomSheet.value)
        ModalBottomSheet(
            onDismissRequest = { showModalBottomSheet.value = false },
            sheetState = bottomSheetState,
        ) {
            Column(Modifier.fillMaxSize()) {
                Button(
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                showModalBottomSheet.value = false
                            }
                        }
                    }
                ) {
                    Text("Hide Bottom Sheet")
                }
                Button(onClick = {
                    scope.launch {
                        showModalBottomSheet.value = false
                        skipPartially = !skipPartially
                        delay(500L)
                        showModalBottomSheet.value = true
                    }
                }) {
                    Text(text = "Skip Partially Expanded")
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Sheet2Theme {
        Greeting("Android")
    }
}