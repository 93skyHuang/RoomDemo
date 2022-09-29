//官方文档：https://developer.android.com/training/data-storage/room

1、引用 def room_version = "2.4.3"
implementation "androidx.room:room-runtime:$room_version"
annotationProcessor "androidx.room:room-compiler:$room_version"

kapt  "androidx.room:room-compiler:$room_version"
plugins { id 'kotlin-kapt' } 这2个一定要加不然无法实现穿件的RoomDatabase抽象类