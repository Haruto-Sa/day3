# Hello, Compose!
1-1. Hello, World!で使ったプロジェクトでHello, Compose!を実行してみましょう！

## Jetpack Composeとは
Jetpack Composeとは、AndroidのUIを宣言的に構築するためのUIライブラリです。  
AndroidのUI構築としてはかつてはAndroidViewというものが主流で主にXMLを使ってUIを構築していましたが、Jetpack Composeが出てからはComposable関数でUIを構築できるようになりました。  
Android公式のライブラリではありますが、最近ではJetbrainsからCompose MultiplatformというJetpack ComposeをベースとしたマルチプラットフォームでUIを構築するためのライブラリが公開されています。  
今回はこのJetpack Composeを使ってHello, Compose！を実行しましょう！  

## 既存コードの書き換え

MainActivityのコードを以下のように書き換えましょう。   
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBootcampIwatePrefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text(
                            text = "自己紹介",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "私の名前は〇〇です",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}
```

### 書き換えができたら実行
画面に自己紹介文が表示されれば、書き換え成功です！

### 解説
ここで`Column`という関数を利用しました。  
この関数はComposable関数と言ってJetpack ComposeでUIを構築する際のUIコンポーネントの一つになります。 

Composable関数の特徴としては `@Composable` が関数の前についています。  
そのため`Greeting`関数もComposable関数になっています。  
このように `@Composable` をつけるだけでJetpack ComposeのUIコンポーネントを作成することができます。  

> [!TIP]
> kotlinの関数はキャメルケースでの宣言が一般的ですが、Composable関数ではパスカルケースで定義します。
