package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.customview.CustomSection
import com.example.testcompose.customview.CustomSootheBottomNavigation
import com.example.testcompose.customview.CustomTextFieldView
import com.example.testcompose.customview.LazyFavoriteCollectionByFavoriteData
import com.example.testcompose.customview.LazyRowAlignYourBodyElementByFavoriteData
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.utils.FavoriteData
import org.junit.internal.runners.statements.ExpectException

class MainActivity : ComponentActivity() {
    private val mockList = listOf(
        FavoriteData(name = "Knight", img = R.mipmap.knight),
        FavoriteData(name = "Reynoid", img = R.mipmap.reynoid),
        FavoriteData(name = "Den", img = R.mipmap.den),
        FavoriteData(name = "Ellis", img = R.mipmap.ellis),
        FavoriteData(name = "Eric", img = R.mipmap.eric),
        FavoriteData(name = "Jason", img = R.mipmap.jason),
        FavoriteData(name = "Ranl", img = R.mipmap.ranl),
        FavoriteData(name = "Tim", img = R.mipmap.tim),
        FavoriteData(name = "Welsen", img = R.mipmap.welsen),
        FavoriteData()
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold (
                bottomBar = { CustomSootheBottomNavigation() }
            ){ paddingValues ->
                ContentView(
                    modifier = Modifier.padding(paddingValues),
                    alignYourBodyData = mockList,
                    favoriteCollectionData = mockList
                )
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

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestComposeTheme {
        Greeting("Android")
    }
}


@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    alignYourBodyData: List<FavoriteData>,
    favoriteCollectionData: List<FavoriteData>
) {
    Column(
        modifier
            .background(color = colorResource(id = R.color.white))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextFieldView(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .testTag("searchTextField")
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomSection(
            title = R.string.align_your_body_element,
            textModifier = Modifier.padding(start = 50.dp, bottom = 10.dp)
        ) {
            LazyRowAlignYourBodyElementByFavoriteData(
                datas = alignYourBodyData
            )
        }
        CustomSection(
            title = R.string.favorite_collection,
            textModifier = Modifier.padding(start = 50.dp, bottom = 5.dp)
        ) {
            LazyFavoriteCollectionByFavoriteData(
                datas = favoriteCollectionData
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun PreviewHomeScreen() {
    ContentView(
        alignYourBodyData = listOf(
            FavoriteData(name = "Knight", img = R.mipmap.knight),
            FavoriteData(name = "Reynoid", img = R.mipmap.reynoid),
            FavoriteData(name = "Den", img = R.mipmap.den),
            FavoriteData(name = "Ellis", img = R.mipmap.ellis),
            FavoriteData(name = "Eric", img = R.mipmap.eric),
            FavoriteData(name = "Jason", img = R.mipmap.jason),
            FavoriteData(name = "Ranl", img = R.mipmap.ranl),
            FavoriteData(name = "Tim", img = R.mipmap.tim),
            FavoriteData(name = "Welsen", img = R.mipmap.welsen),
            FavoriteData()
        ),
        favoriteCollectionData = listOf(
            FavoriteData(name = "Knight", img = R.mipmap.knight),
            FavoriteData(name = "Reynoid", img = R.mipmap.reynoid),
            FavoriteData(name = "Den", img = R.mipmap.den),
            FavoriteData(name = "Ellis", img = R.mipmap.ellis),
            FavoriteData(name = "Eric", img = R.mipmap.eric),
            FavoriteData(name = "Jason", img = R.mipmap.jason),
            FavoriteData(name = "Ranl", img = R.mipmap.ranl),
            FavoriteData(name = "Tim", img = R.mipmap.tim),
            FavoriteData(name = "Welsen", img = R.mipmap.welsen),
            FavoriteData()
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun PreviewTest() {
    Row {
        Text(text = "213222")
        Scaffold { paddingValues ->
            ContentView(
                modifier = Modifier.padding(paddingValues),
                alignYourBodyData = listOf(
                    FavoriteData(name = "Knight", img = R.mipmap.knight),
                    FavoriteData(name = "Reynoid", img = R.mipmap.reynoid),
                    FavoriteData(name = "Den", img = R.mipmap.den),
                    FavoriteData(name = "Ellis", img = R.mipmap.ellis),
                    FavoriteData(name = "Eric", img = R.mipmap.eric),
                    FavoriteData(name = "Jason", img = R.mipmap.jason),
                    FavoriteData(name = "Ranl", img = R.mipmap.ranl),
                    FavoriteData(name = "Tim", img = R.mipmap.tim),
                    FavoriteData(name = "Welsen", img = R.mipmap.welsen),
                    FavoriteData()
                ),
                favoriteCollectionData = listOf(
                    FavoriteData(name = "Knight", img = R.mipmap.knight),
                    FavoriteData(name = "Reynoid", img = R.mipmap.reynoid),
                    FavoriteData(name = "Den", img = R.mipmap.den),
                    FavoriteData(name = "Ellis", img = R.mipmap.ellis),
                    FavoriteData(name = "Eric", img = R.mipmap.eric),
                    FavoriteData(name = "Jason", img = R.mipmap.jason),
                    FavoriteData(name = "Ranl", img = R.mipmap.ranl),
                    FavoriteData(name = "Tim", img = R.mipmap.tim),
                    FavoriteData(name = "Welsen", img = R.mipmap.welsen),
                    FavoriteData()
                )
            )
        }
    }
}
