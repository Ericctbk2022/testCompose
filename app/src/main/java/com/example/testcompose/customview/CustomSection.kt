package com.example.testcompose.customview

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.R

@Composable
fun CustomSection(
    @StringRes title:Int,
    mf: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = mf
    ){
        Text(modifier = textModifier, text = stringResource(id = title))
        content()
    }
}

@Preview(showBackground = true, name = "Test Section", backgroundColor = 0xFFF5F0EE)
@Composable
fun PreviewSection(){
    CustomSection(
        title = R.string.align_your_body_element,
        textModifier = Modifier.padding(start = 50.dp)
    ) {
        LazyRowAlignYourBodyElement()
    }
}