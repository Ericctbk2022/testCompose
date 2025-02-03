package com.example.testcompose.customview

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.R
import com.example.testcompose.utils.FavoriteData


@Composable
fun CustomAlignYourBodyElement() {
    AlignYourBodyElement()
}

@Composable
fun AlignYourBodyElement(
    mf: Modifier = Modifier,
    name: String = "Inversions",
    img: Int = R.drawable.ic_launcher_foreground
) {
    Column(
        modifier = mf,
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(color = Color.Gray)
        )
        Text(
            text = name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun LazyRowAlignYourBodyElement(
    mf: Modifier = Modifier,
    names: List<String> = List(10) { "$it" }
) {
    LazyRow (
        modifier = mf,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(10.dp)
    ){
        items(items = names) {
            AlignYourBodyElement(
                mf,
                it
            )
        }
    }
}

@Composable
fun LazyRowAlignYourBodyElementByFavoriteData(
    mf: Modifier = Modifier,
    datas: List<FavoriteData> = List(10) { FavoriteData() }
) {
    LazyRow (
        modifier = mf,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(10.dp)
    ){
        items(items = datas) {
            AlignYourBodyElement(
                mf,
                it.name,
                it.img
            )
        }
    }
}


@Preview(showBackground = true, name = "Test align your body element view", backgroundColor = 0xFFF5F0EE)
@Composable
fun PreViewAlignYourBodyElementView() {
    CustomAlignYourBodyElement()
}
@Preview(showBackground = true, name = "Test align your body element view", backgroundColor = 0xFFF5F0EE)
@Composable
fun PreViewLazyRowAlignYourBodyElementView() {
//    LazyRowAlignYourBodyElement()
    LazyRowAlignYourBodyElementByFavoriteData()
}

