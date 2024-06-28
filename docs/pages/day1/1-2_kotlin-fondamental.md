# Kotlinの基礎

Kotlinは2017年[^1]にAndroidに公式サポートされたプログラミング言語です。  
それ以前はJavaでアプリ開発をしていましたが、現在ではKotlinで開発されるのが一般的です。

AndroidのUIフレームワークであるJetpack ComposeもKotlinで実装されていることから、今後のAndroidのAPIでもKotlinで開発される可能性があります。

このブートキャンプはKotlinでの実装を想定しているので、ここでKotlinの基本的なコーディングを学びましょう。

[^1]: [AndroidによるKotlinのサポート](https://developers-jp.googleblog.com/2017/06/android-announces-support-for-kotlin.html)

## 関数の定義と呼び出し

Kotlinの関数を定義する時は`fun`を仕様します。  
続けて`fun <関数名>: <戻り値の型>`と続けて書きます。

下のサンプルコードにおいては、`appendMessage`が関数名、`String`が戻り値の型になります。

```kotlin
fun appendMessage(
    name: String,
    message: String = "Nice to meet you!",
): String {
    return "$name\n$message"
}
```

また関数には引数を取ることができます。  
引数とは関数内の処理の入力のことです。それにより出力結果(戻り値)を変えることができます。

サンプルコードでは`name`と`message`が引数になります。  
また`message`ではデフォルトの引数を与えることもできます。

定義した関数は`<関数名>(...)`で呼び出すことができます。

`Greeting`関数から呼び出す場合は次のようになります。

```diff
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
_       text = "Hello $name!",
+       text = appendMessage("Hello $name!"),
        modifier = modifier
    )
}

fun appendMessage(
    name: String,
    message: String = "Nice to meet you!",
): String {
    return "$name\n$message"
}
```

## 変数

プログラム内の値の入れ物を変数と呼びます。

Kotlinでは`val`または`var`というキーワードを使って定義します。  
違いは変更できるか否かです。


```kotlin
val a: Int = 1  // 変更不可能
var b = 2   // 変更可能
```

※変数`b`のように型名は省力可能です。

では、先ほど追加した`appendMessage`関数内で変数を利用してみましょう。

```diff
fun appendMessage(
    name: String,
    message: String = "Nice to meet you!",
): String {
+   val date = "2024-01-01"
+   return "$name\n$message at $date"
}
```

ビルドして、テキストに日付が追加されていること確認してみましょう。

## クラスとインスタンス

クラスとはオブジェクト(物体)を型として定義したものです。

以下は、車のクラスの例です。

```kotlin
/**
 * 車のクラス
 * @property model 車種
 * @property manufacturer メーカー名
 */
class Car(
    val model: String,
    val manufacturer: String,
) {
    /**
     * @property fuel 燃料[L]
     */
    private var fuel: Int = 100

    /**
     * 燃料を追加する。
     * @param fuel 追加する燃料[L]
     */
    fun addFuel(fuel: Int) {
        this.fuel += fuel
    }
}
```

クラスは金型のようなものであり、同じ型のものをいくつも作ることができます。  
そのようにクラスから作られたものをインスタンスと呼びます。

インスタンスはコンストラクタを利用することで作成できます。  
コンストラクタはインスタンスを作成(初期化)するための処理で、初期化に必要なデータを引数で渡すこともできます。

下記のコードは、`Car`のインスタンスを作成する例です。

```kotlin
val nBox = Car(model = "N-BOX", manufacturer = "HONDA")
val note = Car(model = "NOTE", manufacturer = "NISSAN")
```

また、クラスはプロパティとメソッドを定義することができます。

プロパティは変数と同じく`var`または`val`を使って定義します。  
メソッドは関数と同じく`fun`を使って定義します。

以下のコードでは、`.`を使ってプロパティへのアクセスとメソッドの呼び出しをしています。

```kotlin
val nBox = Car(model = "N-BOX", manufacturer = "HONDA")
println(nBox.model) // modelプロパティにアクセス
nBox.addFuel(10) // addFuelメソッドを呼び出し
```

## data class

`data class`はデータを保持するために使われるクラスです。

通常インスタンス同士が等価かどうかを比較(`==`)すると、「等価ではない(`false`)」と判断されます。

下記のコードでは`User`クラスが等価かどうかを出力しています。

```kotlin
class User(val name: String, val age: Int)

val user1 = User(name = "YOUR_NAME", age = 20)
val user2 = User(name = "YOUR_NAME", age = 20)

println(user1 == user2)  // false
```

`User`クラスを`data class`に変更すると出力結果が変わります。

```kotlin
data class User(val name: String, val age: Int)

val user1 = User(name = "YOUR_NAME", age = 20)
val user2 = User(name = "YOUR_NAME", age = 20)

println(user1 == user2)  // true
```

このように同じデータを持つ場合に`data class`を使うと、別インスタンスでも等価として判断してくれます。

> [!TIP]
> `data class`の場合でも同じインスタンスかを確認するためには`===`で比較してください。