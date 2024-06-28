# 3. Androidのライフサイクル

## ライフサイクルに入る前に・・・
AndroidにはApplicationとActivityという登場人物がいます。  
これからAndroidアプリ開発を進めていく中でとても重要なものなのでしっかり覚えましょう！  
(他にもFragmentなどもあるので興味がある方は調べてみてください。)

### Application、Activity
Applicationとは、アプリ全体の状態を保持するためのクラスのことで、アプリが起動してから終了するまでの間生存しています。  
[参考](https://developer.android.com/reference/android/app/Application)

Activityとは、ユーザーが操作することができるように、アプリのUIを配置することのできるウィンドウを作成してくれるクラスのことです。  
[参考](https://developer.android.com/reference/android/app/Activity)

## ライフサイクルとは
ライフサイクルとは、ApplicationやActivityが生成されてから破棄されるまでのことです。  
ライフサイクルには生成されてから破棄されるまでの間にいくつか呼ばれる関数が存在します。  
今回押さえておきたいライフサイクルの関数は、onCreate、onResume、onPause、onDestroyの4つになります。

### onCreate
onCreateはApplicationやActivityが生成される時に呼ばれる関数です。  
基本的な変数などの初期化処理はこちらに記述します。

### onResume
onResumeは、Applicationでは、onCreateの処理が終わった後、バックグラウンドからフォアグランドに戻った時などに呼ばれます。  
ActivityはApplicationと同様にonCreateの処理が終わった後、バックグラウンドからフォアグランドに戻った時、遷移先の画面から戻ってきた時に呼ばれます。

### onPause
onPauseは、Applicationではアプリがバックグラウンドに遷移した時などに呼ばれます。  
ActivityもApplicationと同様にアプリがバックグラウンドに遷移した時や、別な画面に遷移する時など画面から離脱する時に呼ばれます。
一時停止しないといけないものなどはこの関数で停止させたりする必要があります。

### onDestroy
onDestroyは、Applicationではアプリが終了する時に呼ばれます。  
Activityもアプリが終了する時や、画面を閉じる時に呼ばれます。  
必ずリソースを解放する必要があるものなどはこの関数で行います。


### 参考
![](./images/activity_lifecycle.png)

アクティビティのライフサイクルより引用  
https://developer.android.com/guide/components/activities/activity-lifecycle
