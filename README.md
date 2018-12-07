# Demo

## 架構圖

<img src=https://github.com/Timmysun/Demo/blob/master/Untitled%20Diagram.png >

1. 使用MVVM架構
2. ViewModel與View之間的溝通透過DataBinding的Observer機制，即時更新畫面資料
3. ViewModel與Activity/Fragment之間的溝通透過EventBus
4. Model與ViewModel之間透過EventBus溝通
5. Model內用RxJava串聯load cache與Web API呼叫
6. Web API 透過Retrofit撈取開放平台的資料
7. ImageView顯示的圖片透過Picasso作為管理
8. 畫面顯示以Fragment為主，Activity用來作為Fragment之間切換的中轉
