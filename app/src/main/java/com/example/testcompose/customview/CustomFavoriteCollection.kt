package com.example.testcompose.customview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.R
import com.example.testcompose.utils.FavoriteData


@Composable
fun MyFavoriteCollection() {
    FavoriteCollection(
        mf = Modifier.padding(8.dp)
    )
}

@Composable
fun FavoriteCollection(
    mf: Modifier = Modifier,
    name: String = "Nature meditations",
    imgId: Int = R.drawable.ic_launcher_foreground
) {
    Surface (
        shape = RoundedCornerShape(10),
        modifier = mf
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(255.dp)
                .background(color = colorResource(id = R.color.a11y_gray_150))
        ){
            Image(
                painter = painterResource(id = imgId),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(80.dp).padding(2.dp)
            )
            Text(
                text = name,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LazyFavoriteCollectionByFavoriteData(
    mf: Modifier = Modifier,
    datas: List<FavoriteData> = List(10) { FavoriteData() }
) {
    Surface (
        modifier = mf.padding(15.dp)
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = mf
                .height(168.dp)
                .padding(5.dp)
        ) {
            items(datas) {
                FavoriteCollection(name = it.name, imgId = it.img)
            }
        }
    }
}

@Composable
fun LazyFavoriteCollection(
    mf: Modifier = Modifier,
    names: List<String> = List(10) { "$it" }
) {
    Surface (
        modifier = mf.padding(15.dp)
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = mf
                .height(168.dp)
                .padding(5.dp)
        ) {
            items(names) {
                FavoriteCollection(name = it)
            }
        }
    }
}

@Preview(showBackground = true, name =  "Test favorite collection view", backgroundColor = 0xFFF5F0EE)
@Composable
fun PreViewFavoriteCollection() {
    MyFavoriteCollection()
}

@Preview(showBackground = true, name =  "Test favorite collection view", backgroundColor = 0xFFF5F0EE)
@Composable
fun PreViewLazyFavoriteCollection() {
//    LazyFavoriteCollection()
    LazyFavoriteCollectionByFavoriteData()
}