package com.example.testcompose.customview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun lazyColumCustomTextView(
    md: Modifier = Modifier,
    names: List<String> = List(100) { "$it" }
) {
    LazyColumn(
        modifier = md.padding(vertical = 4.dp)
    ) {
        items(items = names) { name ->
            CardContent(name = name)
        }
    }
}

@Composable
fun myCustomTextView(
    name: String,
    listName: List<String> = arrayListOf("MMB", "MR" )
) {
    Column {
        for (names in listName) {
            ColumTextView(name = names)
        }
    }
}

@Composable
fun ColumTextView(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
//    val expanded = remember { mutableStateOf(false) }
    val color = if (expanded) {
        Color.Gray
    } else {
        Color.Red
    }

    val expandPadding by animateDpAsState(
        targetValue = if (expanded) {
            10.dp
        } else {
            0.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

//    val expandPadding = if (expanded) { 10.dp } else { 0.dp }

    Surface(
        modifier = Modifier,
        color = Color.Green
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(expandPadding)) {
                TextView(name = "Hello")
                TextView(
                    name = name,
                    modifer = Modifier.padding(horizontal = 50.dp, vertical = 10.dp)
                )
            }
            ElevatedButton(onClick = { expanded = !expanded }) {
                Text(
                    text = if (expanded) {
                        "show more"
                    } else {
                        "show less"
                    },
                    color = color
                )
            }
        }


//        Column(modifier = Modifier.padding(24.dp)) {
//            TextView(name = "Hello")
//            TextView(name = name, modifer = Modifier.padding(horizontal = 50.dp, vertical = 10.dp))
//            TextView(name = "!", modifer = Modifier.padding(start = 100.dp))
//        }
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(text = name)
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) {
                    "show less"
                } else {
                    "show more"
                }
            )
        }
    }
}



@Composable
fun TextView(name: String, modifer: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifer
    )
}

@Preview(showBackground = true, name = "Test custom view", widthDp = 320)
@Composable
fun preViewCustomTextView() {
//    myCustomTextView("MMB")
    lazyColumCustomTextView()
}