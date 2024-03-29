﻿# Spring Boot-Flask Monorepo

## Teknologi yang digunakan
Aplikasi AuthApp :
- Bahasa pemrograman Python 3 (v3.10.3)
- Framework Flask (v2.2.2)
- File based database (SQLite)
- pip package manager
- Technical Layer architecture

Aplikasi FetchApp :
- Bahasa pemrograman Kotlin
- Framework Spring Boot (v3.0.2)
- JVM 17
- Caffeine internal cache
- gradle package manager (v7.6)
- Hexagonal architecture

## Langkah-langkah menjalankan aplikasi
Untuk menjalankan kedua aplikasi ini, Anda harus mengikuti tahap-tahap yang akan disebutkan berikut.

### Tahap 1 : Build FetchApp
Untuk membuild aplikasi FetchApp, dapat dilakukan dengan menggunakan 2 cara :
- Instalasi melalui gradlew
- Instalasi melalui gradle di IDE IntelliJ

Untuk menginstal melalui gradlew, langkah-langkah yang perlu Anda lakukan adalah sebagai berikut :
1. Pastikan `JAVA_HOME` yang ada pada enviroment laptop / PC Anda sudah sama dengan versi Java yang digunakan pada project ini (Java 17)
2. Masuk ke root dir project `fetchapp`.
3. Jika Anda menggunakan OS Linux, ganti mode gradlew menjadi executable dengan perintah `chmod +x gradlew`. Anda tidak perlu melakukan ini jika menggunakan OS Windows
4. Jalankan perintah `./gradlew clean build`.
5. Tampilan berikut akan Anda dapatkan pada terminal jika proses build berhasil

<p align="center">
  <img src="./img/fetchapp_success_build_gradlew.png">
</p>

Anda dapat melakukan proses build yang lebih mudah menggunakan IDE IntelliJ dengan cara sebagai berikut :
1. Muat project `fetchapp` ke IDE IntelliJ Anda sebagai project.
2. Jika Anda memuat project ini pertama kali, gradle akan secara otomatis memuat dependensi yang diperlukan. Tunggu hingga proses download dan indexing selesai.
3. Pada IntelliJ Anda, akan ada menu Gradle di pojok kanan atas. Klik menu tersebut. klik `build` ada pada `fetchapp` > `Tasks` > `build`

<p align="center">
  <img src="./img/fetchapp_build_with_intellij.png">
</p>

4. jika proses build berhasil, Anda akan menemukan file `fetchapp-0.0.1-SNAPSHOT.jar` di dalam direktori `build/libs/fetchapp-0.0.1-SNAPSHOT.jar`

Jika proses build aplikasi FetchApp sudah selesai, Anda sudah siap untuk berlanjut ke Tahap 2.

### Tahap 2: Tentang docker-compose.yml dan Dockerfile

FetchApp dan AuthApp masing-masing memiliki perintah *image assembling* di `fetchapp/Dockerfile` dan `authapp/Dockerfile`. Untuk mem-build container, kita akan menggunakan file `docker-compose.yml` yang terdapat di directori root repositori.

<p align="center">
  <img src="./img/project_root_dir.png">
</p>

Isi dari docker-compose.yml adalah sebagai berikut :
```yml
version: '3'
services:
  fetchapp:
    ports:
      - 8089:9090
    build:
      context: fetchapp
      dockerfile: Dockerfile
    container_name: efishery-fetchapp
    restart: always
    networks:
      - efishery-network

  authapp:
    ports:
      - 8090:5000
    build:
      context: authapp
      dockerfile: Dockerfile
    container_name: efishery-authapp
    restart: always
    networks:
      - efishery-network

networks:
  efishery-network:
```

Untuk port setiap aplikasi (`external_port`:`internal_port`) adalah sebagai berikut :
- fetchapp (8089:9090) -> Anda bisa mengganti port 8089 dengan port yang Anda mau / port yang available di laptop / PC Anda. 
- authapp (8090:9090) -> Anda bisa mengganti port 8090 dengan port yang Anda mau / port yang available di laptop / PC Anda.

### Tahap 3: Instalasi FetchApp dan AuthApp pada Docker Container
Untuk melakukan instalasi docker container, ikuti langkah berikut :
1. Pastikan terminal / command prompt Anda perada di direktori root project.
2. Gunakan perintah berikut pada terminal atau command prompt 
```bash
docker-compose up -d
```
3. Proses instalasi akan berjalan setelah Anda mengeksekusi perintah tersebut.
<p align="center">
  <img src="./img/docker_compose_progress.png">
</p>

4. Gunakan perintah `docker ps` untuk mengecek apakah container sudah terinstal. Jika Anda memperoleh output seperti gambar di bawah, tandanya instalasi container sudah berhasil
<p align="center">
  <img src="./img/container_check.png">
</p>

5. Anda bisa mengakses masing-masing aplikasi pada link berikut :
    - fetchapp : http://localhost:8089
    - authapp : http://localhost:8090

Anda bisa memperoleh Postman collection di folder postman. Contoh request memalui Postman seperti pada gambar di bawah.
<p align="center">
  <img width="460" height="300" src="./img/postman_example.png">
</p>

### Catatan tambahan :
- Berkas C4 Model dapat Anda peroleh di folder `c4 model`
- Anda bisa mengatur durasi berlakunya token dengan mengatur nilai `JWT_VALIDITY_TIME` pada `fetchapp/.env`.
- Video cara menjalankan applikasi ini dapat dilihat di https://youtu.be/biCl9Jli0TU (unlisted)

----------------------------------------------------------------------------------

Di buat oleh **Ahmad Mujahid Abdurrahman**.
Untuk keperluan Backend Engineer Task di eFishery.
