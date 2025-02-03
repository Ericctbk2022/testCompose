package com.example.testcompose.customview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextFieldView(modifier: Modifier = Modifier) {
    SearchBar(mf = modifier)
}

@Composable
fun SearchBar(
    mf: Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = "Search")
        },
        modifier = mf
            .fillMaxWidth()
            .heightIn(min = 60.dp)
    )
}


@Preview(showBackground = true, name = "Test text filed view")
@Composable
fun PreViewCustomView() {
    CustomTextFieldView()
}