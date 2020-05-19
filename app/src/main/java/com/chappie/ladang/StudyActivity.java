package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.chappie.ladang.adapter.StudyAdapter;
import com.chappie.ladang.model.Study;

import java.util.ArrayList;
import java.util.List;


public class StudyActivity extends AppCompatActivity {

    private ArrayList<Study> studies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        init();
        RecyclerView recyclerView = findViewById(R.id.rv_study);
        StudyAdapter adapter = new StudyAdapter(this,studies);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudyActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(i -> {
            Intent intent = new Intent(StudyActivity.this, DetailStudyActivity.class);
            intent.putExtra(DetailStudyActivity.EXTRA_STUDIES, studies.get(i));
            startActivity(intent);
        });
    }

    private void init() {
        studies.add(new Study("Gambar Sketsa", R.drawable.study1,"<p>Secara etimologi istilah sketsa berasal dari bahasa latin “skhedios extempore” yang berarti begitu saja tanpa persiapan. Sketsa dapat diartikan sebagai rencana, bagan, atau uraian singkat. Dalam seni rupa, sketsa (sketch) dapat diartikan sebagai gambar sederhana atau draf kasar yang dibuat secara global untuk melukiskan bagian-bagian pokok yang ingin diungkapkan oleh pembuatnya. Tujuan utama membuat sketsa adalah untuk menghasilkan bentuk dasar objek dengan posisi yang benar. Adapun manfaat membuat gambar sketsa diantaranya untuk memberi gambaran tema, meminimalisir kesalahan, mempertajam pengamatan, serta keterampilan tangan.</p>" +
                "<h4>Jenis sketsa</h4>" +
                "<p>Kusnadi mengatakan bahwa sketsa dalam seni rupa dibagi menjadi dua</p>" +
                "<ul><li>   1. sketsa sebagai seni murni atau sketsa yang berdiri sendiri dan sekaligus sebagai media ekspresi</li>" +
                "<li>   2. sketsa “voor studie”, yakni sebagai media untuk studi bentuk, proporsi, anatomi, komposisi dsb yang akan dibuat sebagai sketsa</li></ul>" +
                "<h4>Peralatan gambar sketsa</h4>" +
                "<ul><li>   1. media gambar berupa kertas gambar</li>" +
                "<li>   2. alat gambar manual berupa pensil dan rapido</li>" +
                "<li>   3. alat gambar digital berupa komputer dengan program CAD, digital pen, dan software desain grafis</li>" +
                "<li>   4. alat bantu gambar, lightbox, scanner, meja gambar, mesin gambar, mistar, busur, mal, sablon dan penghapus</li></ul>" +
                "<h4>Menggambar sketsa</h4>" +
                "<p>Menggambar sketsa dilakukan dengan 2 cara :</p>" +
                "<ul><li>   1. Mengamati objek langsung (life sketching)</li>" +
                "<li>     Melakukan pengamatan adalah langkah awal menggambar sketsa, selanjutnya langsung digambar dari hasil mengamati tersebut</li>" +
                "<li>   2. Berdasarkan daya ingat (sketcing from memory)</li>" +
                "<li>     Proses ini melibatkan daya ingat dan imajinasi, karena erat kaitannya dengan kesan suatu objek</li</ul>" +
                "<h4>Langkah langkah menggambar sketsa<h4>" +
                "<ul><li>   1. membuat kerangka gambar terdiri dari vertikal dan horizontal</li>" +
                "<li>   2. menggambar garis sekundernya</li>" +
                "<li>   3. menebarkan garis sketsa yang sudah benar</li></ul>"));
        studies.add(new Study("Gambar Ilustrasi",R.drawable.study2,"<p>Kata ilustrasi berasal dari bahasa belanda “ilustratie”, yang berarti hiasan dengan gambar atau pembuatan sesuatu yang jelas. Hal yang sama juga dapat diambil dari bahasa inggris “illustrate”, yang berarti menjelaskan dengan contoh gambar, kejadian, musik dan sebagainya. Sedangkan dari bahasa latin “illustrare”, berarti pelengkap sesuatu untuk memperjelas dengan menunjukkan contoh-contoh, khususnya melalui bentuk-bentuk, diagram dan gambar-gambar. Gambar ilustrasi adalah gambar yang menceritakan atau memberikan penjelasan pada cerita atau naskah tertulis.</p>" +
                "<h4>Fungsi gambar ilustrasi yaitu :</h4>" +
                "<ul><li>   1. menggambarkan suatu produk yang belum pernah ada</li>" +
                "<li>   2. menggambarkan kejadian atau peristiwa mustahil</li>" +
                "<li>   3. menggambarkan ide ide abstrak</li>" +
                "<li>   4. memperjelas suatu komentar</li>" +
                "<li>   5. memperjlas suatu artikel atau gambar</li>" +
                "<li>   6. menjelaskan sesuatu secara rinci</li>" +
                "<li>   7. membuat corak tertentu pada suatu tulisan</li><ul>" +
                "<h4>Unsur-unsur ilustrasi</h4>" +
                "<p>Unsur unsur ilustrasi berupa gambar yang dikemas sedemikian rupa untuk memperjelas pesan ataupun suatu cerita diantaranya</p>" +
                "<ul><li>   1. gambar manusia</li>" +
                "<li>   2. gambar binatang</li>" +
                "<li>   3. gambar tumbuhan</li>" +
                "<li>   4. gambar alam benda</li></ul>" +
                "<h4>Teknik pembuatan ilustrasi</h4>" +
                "<p>Ilustrasi dapat berupa gambar, foto ataupun grafis lainnya. Ilustrasi dapat dihasilkan melalui teknik gambar tangan serta melalui teknik fotografi</p>" +
                "<ul><li>   1. gambar tangan</li>" +
                "<li>    Teknik ini adalah teknik yang mengandalkan keterampilan tangan dalam menggunakan pena, kuas, pastel atau alat gambar lainnya dengan memberikan karakter dan ekspresi tertentu, dengan secara keseluruhan dibuat menggunakan tangan.</li>" +
                "<li>   2. fotografi</li>" +
                "<li>    Ilustrasi berupa foto dihasilkan dengan teknik fotografi menggunakan kamera, baik secara manual maupun digital.</li></ul>" +
                "<h4>Langkah-langkah menggambar ilustrasi</h4>" +
                "<ul><li>   1. temukan gagasannya</li>" +
                "<li>   2. buat sketsanya</li>" +
                "<li>   3. beri warna</li></ul>" +
                "<h4>Jenis-jenis ilustrasi</h4>" +
                "<ul><li>   1. gambar ilustrasi realis atau naturalis, gambar yang memiliki bentuk dan warna yan sama dengan kenyataan (realis) yang ada dialam</li>" +
                "<li>   2. gambar ilustrasi dekoratif, gambar yang berfungsi untuk menghias atau memperindah sesuatu dengan bentuk yang disederhanakan atau dilebihlebihkan</li>" +
                "<li>   3. gambar kartun, gambar yang menampilkan kelucuan atau humor</li>" +
                "<li>   4. gambar karikatur, gambar kritikan atau sindiran yang dalam penggambarananya telah menggalami penyimpangan bentuk proporsi tubuh</li>" +
                "<li>   5. cerita bergambar (cergam) adalah sejenis komik atau gambar yang diberi teks </li>" +
                "<li>   6. ilustrasi sampul (cover), gambar ilustrasi yang mempunyai fungsi untuk menggambarkan isi buku atau menambah daya tarik sebuah buku atau majalah.</li>" +
                "<li>   7. ilustrasi khayalan, gambar hasil pengolahan daya cipta secara imajinatif (khayal)</li></ul>"));
        studies.add(new Study("Gambar Bentuk",R.drawable.study3,"<p>Bentuk adalah istilah yang seringkali dicampuradukkan dengan raut, dengan kata lain semua unsur elemen rupa sekaligus disebut bentuk. Adapun yang dimaksud menggambar bentuk adalah suatu kegiatan memindahkan objek model yang dilihat langsung ke atas bidang gambar dengan lebih mengutamakan kemiripan terhadap model tersebut. Menggambar bentuk juga identik dengan fotografi, yaitu memindahkan objek yang ada didepan mata kebidang gambar. Adapun macam macam gambar bentuk ada : </p>" +
                "<ul><li>   1. bentuk kubistis, bentuknya menyerupai kubus atau benda yang bentuk dasarnya kubus</li>" +
                "<li>   2. bentuk silindris, bentuk dasarnya menyerupai silindris atau bulat</li>" +
                "<li>   3. bentuk bebas, benda yang bentuknya tak beraturan</li></ul>" +
                "<h4>Alat bahan dan media<h4>" +
                "<p>Alat :</p>" +
                "<ul><li>   1 Pensil</li>" +
                "<li>   2 Karet penghapus</li>" +
                "<li>   3 Alat penyangga</li>" +
                "<li>   4 Kain</li>" +
                "<li>   5 Lampu sorot</li></ul>" +
                "<p>Bahan</p>" +
                "<p>   Bahan utama yaitu kertas gambar, pemilihan kertas yang baik untuk menggambar bentuk sangat membantu kemudahan untuk berkarya</p>" +
                "<p>Media</p>" +
                "<ul><li>   1. media kering</li>" +
                "<li>    Media kering adalah peralatan yang digunakan pada bidang gambar dengan media warna dalam keadaan kering. Peralatan ini termasuk drawing pen, pensil, krayon, spidol, konte</li>" +
                "<li>   2. media basah</li>" +
                "<li>    Media basah adalah media yang digunakan pada bidang gambar dengan media warna dalam keadaan basah seperti cat air, cat poster, tinta bak, cat minyak dan ecolin</li></ul>" +
                "<h4>Prinsip-prinsip menggambar bentuk</h4>" +
                "<ul><li>   1 Perspektif, suatu kondisi keterbatasan kemampuan mata melihat suatu objek dimana benda yang dekat dengan mata akan terlihat lebih besar atau lebih tinggi daripada benda yang terletak lebih jauh dari mata</li>" +
                "<li>   2 Proporsi, ukuran perbandingan antara bagian yang satu dengan yang lain pada benda tersebut.</li>" +
                "<li>   3 Komposisi, tata letak atau susunan unsur unsur yang menjadi objek gambar sehingga objek tsb enak dilihat</li>" +
                "<li>   4 Gelap terang, unsur rupa yang berkenaan dengan cahaya</li>" +
                "<li>   5 Bayang bayang, sangat menentukan dalam menciptakan kesan tiga dimensi meskipun samarsamar</li></ul>" +
                "<h4>Teknik menggambar bentuk</h4>" +
                "<ul><li>   1. Teknik linier, cara menggambar objek dengan garis sebagai unsur yang paling menentukan </li>" +
                "<li>   2. Teknik arsir, dibuat dengan cara menggoreskan pensil, spidol, tinta atau alat lain berupa garis berulang</li>" +
                "<li>   3. Teknik pointilis, cara atau teknik dengan menggunakan titik titik hingga membentuk objek</li>" +
                "<li>   4. Teknik dussel, teknik menggosok gambar sehingga menimbulkan kesan gelap terang atau tebal tipis</li>" +
                "<li>   5. Teknik siluet, teknik menutup objek gambar dengan menggunakan satu warna sehingga menimbulkan kesan blok</li>" +
                "<li>   6. Teknik aquarel, teknik yang sering menggunakan air didalam penerapannya</li>" +
                "<li>   7. Teknik plakat, dibuat dengan bahan cat air atau cat poster dengan sapuan warna tebal</li></ul>"));
        studies.add(new Study("Gambar Perspektif",R.drawable.study4,"<p>Perspektif berasal dari bahasa italia yang berarti gambar pandangan atau sudut pandangan. Adapun yang dimaksud dengan gambar perspektif adalah gambar yang memperlihatkan objek seperti yang terekam atau terlihat oleh mata dan bersifat komunikatif dalam memberikan gambaran bentuk objek. Pada gambar perspektif, benda yang letaknya lebih dekat dengan mata akan tampak lebih besar, sedangkan benda yang letaknya jauh dari mata akan tampak lebih kecil. Semakin jauh benda, maka gambar yang dihasilkan akan semakin kecil dan akhirnya hanya berupa titik.</p>" +
                "<h4>Elemen penting dalam perspektif</h4>" +
                "<ul><li>   1 sudut pandang, yaitu posisi pengambilan ketika melihat objek atau pandangan dilingkungan sekitar. Sudut pandang terbagi menjadi beberapa posisi seperti berikut :</li>" +
                "<li>      a. sudut pandang mata burung</li>" +
                "<li>      b. sudut pandang mata manusia</li>" +
                "<li>      c. sudut pandang mata cacing</li>" +
                "<li>   2 level mata (eye level-EL), garis yang diambil dari sudut pengamat yang dapat menentukan posisi garis horizon pada bidang gambar</li>" +
                "<li>   3 garis horizon (horizon line-HL), garis yang menunjukkan ketinggian mata pengamat</li>" +
                "<li>   4 titik hilang (vanisihing pont-VP), titik titik yang berada di garis horizontal yang menjadi titik tumpuan untuk penarikan garis-garis dasar deskriptif. Titik hilang terdiri dari beberapa teknik sebagai berikut :</li>" +
                "<li>      a. teknik 1 titik hilang, digunakan melihat objek dari pandangan depan</li>" +
                "<li>      b. teknik 2 titik hilang, digunakan untuk  melihat suatu sudut ruangan / suasana ataupun objek</li>" +
                "<li>      c. teknik 3 titik hilang, digunakan untuk posisi objek</li></ul>" +
                "<h4>Macam gambar perspektif</h4>" +
                "<ul><li>   1. gambar perspektif 1 titik hilang, garis2 horizontal atau vertikal sejajar dengan pusat pandangan akantampak bertemu pada titik pusat </li>" +
                "<li>   2. gambar perspektif 2 titik hilang, gambar yang menggambarkan objek dengan menggunakan dua titik hilang yang terletak berjauhan dikiri dan kanan</li>" +
                "<li>   3. gambar perspektif 3 titik hilang, gambar yang menggambarkan objek dengan menggunakan tiga titik hilang yang terletak berjauhan dikiri dan kanan dan terbagi menjadi 2 sudut pandang yaitu eye view dan worm view</li></ul>"));
    }
}
