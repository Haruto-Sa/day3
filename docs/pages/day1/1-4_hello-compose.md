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
                    Greeting("Compose") // ← 書き換え
                }
            }
        }
    }
}
```

### 書き換えができたら実行
画面に `Hello, Compose!`が出ていれば書き換え成功です！

### 解説
MainActivity.ktのL33にGreetingという関数が定義されています。  
この関数はComposable関数と言ってJetpack ComposeでUIを構築する際のUIコンポーネントの一つになります。  
Composable関数の特徴としては `@Composable` が関数の前についています。  
このように `@Composable` をつけるだけでJetpack ComposeのUIコンポーネントを作成することができます。  
GreetingではTextというComposable関数が呼ばれており、内部では `"Hello $name!"` という文字列が入っています。  
nameというのはGreetingの引数のことで、今回 `World` から `Compose` に変更したことにより、表示される文言が変わりました。

> [!TIP]
> kotlinの関数はキャメルケースでの宣言が一般的ですが、Composable関数ではパスカルケースで定義します。
