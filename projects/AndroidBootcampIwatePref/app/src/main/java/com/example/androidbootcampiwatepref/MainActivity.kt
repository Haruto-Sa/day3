// MainActivity.kt
package com.example.androidbootcampiwatepref

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidbootcampiwatepref.data.api.NewApiImpl
import com.example.androidbootcampiwatepref.data.api.NewsResponse
import com.example.androidbootcampiwatepref.ui.theme.AndroidBootcampIwatePrefTheme
import com.example.androidbootcampiwatepref.viewmodel.MainViewModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import datastore.AppDataStore
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBootcampIwatePrefTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                    ) {
                        composable("home") {
                            HomeScreen(
                                modifier = Modifier.fillMaxSize(),
                                navigateToDetail = { index ->
                                    navController.navigate("detail/$index")
                                }
                            )
                        }
                        composable(
                            route = "detail/{index}",
                            arguments = listOf(navArgument("index") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: 0
                            DetailScreen(index = index, navigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    val context = LocalContext.current
    val newApi = remember { NewApiImpl() }
    var foodList by remember { mutableStateOf<List<NewsResponse.FoodItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // 非同期処理でAPIデータを取得
    LaunchedEffect(Unit) {
        isLoading = true
        val newsResponse = newApi.getNews()
        foodList = newsResponse.news
        isLoading = false
    }

    // タイトル
    Text(
        text = "TastyFoods",
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color.Blue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(bottom = 24.dp),
        textAlign = TextAlign.Center
    )

    if (isLoading) {
        // ローディング表示
        Text(
            text = "読み込み中...",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    } else {
        // 画像群
        LazyColumn(
            modifier = modifier
                .padding(top = 60.dp)
                .padding(24.dp)
                .clip(RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(foodList.size) { index ->
                Item(
                    foodItem = foodList[index],
                    index = index,
                    onDetailClick = { navigateToDetail(index) }
                )
            }
        }
    }
}


@Composable
fun DetailScreen(index: Int, navigateBack: () -> Unit) {
    val context = LocalContext.current
    val newApi = remember { NewApiImpl() }
    var foodList by remember { mutableStateOf<List<NewsResponse.FoodItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // 非同期処理でAPIデータを取得
    LaunchedEffect(Unit) {
        isLoading = true
        val newsResponse = newApi.getNews()
        foodList = newsResponse.news
        isLoading = false
    }

    if (isLoading) {
        // ローディング表示
        Text(
            text = "読み込み中...",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    } else {//データ取得後
        val foodItem = foodList.getOrNull(index)

        foodItem?.let {//例外処理
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("詳細画面: ${it.title}", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("写真No: ${it.id}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Images(it)

                Spacer(modifier = Modifier.height(16.dp))
                Text(it.textdes, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = navigateBack) {
                    Text("ホームへ")
                }
            }
        }
    }
}


@Composable
fun CountUp(index: Int) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel {
        MainViewModel(AppDataStore(context))
    }
    val count by viewModel.counts.collectAsState(initial = List(10) { 0 })//状態を監視

    // 範囲外アクセスを防ぐための処理
    if (index >= 0 && index < count.size) {
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                viewModel.setCount(index)
            }
        ) {
            Text("いいね:${count[index]}")
        }
    } else {
        // 無効な index の場合
        Text("無効な数値が検出されました")
    }
}


@Composable
fun Item(foodItem: NewsResponse.FoodItem,index: Int, onDetailClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    ) {
        Text("写真 " + foodItem.id, fontSize = 20.sp)
        Images(foodItem)
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CountUp(foodItem.id)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onDetailClick) {
                Text("詳細")
            }
        }
    }
}




@Composable
fun Images(foodItem: NewsResponse.FoodItem) {
    Text(foodItem.description, fontSize = 16.sp)
    val imageResId = LocalContext.current.resources.getIdentifier(//画像表示に必要な要素をまとめてimageResIdに格納
        foodItem.image, "drawable", LocalContext.current.packageName
    )
    Image(
        painter = painterResource(imageResId),
        contentDescription = foodItem.title,
        modifier = Modifier
            .size(350.dp)
            .border(1.dp, Color.Black),
        contentScale = ContentScale.Crop
    )
}

