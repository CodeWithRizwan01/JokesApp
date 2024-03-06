package com.example.hindifunnyjokes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hindifunnyjokes.AdapterClasses.RecyclerViewJokesAdapter;
import com.example.hindifunnyjokes.DbHelper.DbHelper;
import com.example.hindifunnyjokes.ModelClasses.JokesModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class JokesActivity extends AppCompatActivity {

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private AdView mAdView;

    // For Interstitial Ad-App-Lovin
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private int retryAttempt;
    private int addCounter = 0;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView tvTitle;
    ArrayList<JokesModel> jokesList = new ArrayList<>();
    
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.mainToolBar);
        tvTitle = findViewById(R.id.toolbar_title);
        
        dbHelper = new DbHelper(this);

        mAdView = findViewById(R.id.adView);
        MobileAds.initialize(this);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // get data from intent
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (title.equals("Funny jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke01", "गुण मिलने पर शादी होती है..\n" +
                    "और\n" +
                    "अवगुण मिलने पर दोस्ती !\n" +
                    "Happy Freindship Day"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke02", "पुरानी हिन्दी फिल्मों में जवान लडकी के...\n" +
                    "या तो \"हाथ पीले\" होते थे या फिर \"मुंह काला\" होता था!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke03", "और हाँ सिंगल लौंडे 'जियो' फ्री नेट का फायदा 'लड़की पटाने के पाँच आसान तरीके ' जैसी चीजें सर्च कर के उठा रहे है"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke04", "Aj kal ki ladkiya bhi smart hone ka soch rhi h\n" +
                    "Friend request send krti h fir bolti sry galti sai hogya\n" +
                    "bhen ase kese request jati h ??"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke05", "आज सुबह कपड़े धोने डाले जेब मे 500 का नोट था।\n" +
                    "बीवी ने वापस लाकर दिया\n" +
                    "उसकी ईमानदारी देख कर आख भर आयी।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke06", "Wife -तुम्हें कुछ याद भी रहता है..... आज हमारी 'WEDDING ANNIVERSARY' है.\n" +
                    "Husband-ओह, मैं तो भूल ही गया था..... आओ 2 मिन्ट का मौन रखें.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke07", "जिस भी कपल (couple ) को हंसते, मुस्कुराते बाते करते देखता हूँ ,\n" +
                    "मन में बस एक ही ख्याल आता है.\n" +
                    "या तो\n" +
                    "बीबी ‘नई’ है…\n" +
                    "या\n" +
                    "बीबी नहीं' है...!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke08", "आजकल किसी को पीटने या गाली देने से भी ज़्यादा शान्ति उन्हें फेसबुक फ्रेंडलिस्ट से हटा देने पर मिल जाती है!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke09", "Girl: hiii..\n" +
                    "Boy: * died immediately *\n" +
                    "\n" +
                    "Why??\n" +
                    "\n" +
                    "Kyunki use ladki ki 'हाय' lag gyi.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke10", "अब ये अफवाह कौन फैला रहा है कि\n" +
                    "..\n" +
                    "..\n" +
                    "..\n" +
                    "..\n" +
                    "रिलाइंस ने jio sim सिर्फ इसलिए निकाली हैं,ताकि\n" +
                    "भारत में फोकटियो की गिनती की जा सकें......"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke11", "Khud ko buland kar itna ki himalaya ki choti par ja punch ,phir khuda tumse pucha abe gadha ab utrega kaise."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke12", "विश्व में केवल TV का रिमोट ही एक ऐसी चीज है...\n" +
                    "जिसकी काम न करने पर पीठ थपथपाई जाती है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke13", "बचपन में डराया जाता था कि....\n" +
                    "मेंढक को पत्थर मारोगे तो गूंगी पत्नी मिलेगी...\n" +
                    "कितना डरते थे तब..... अब लगता है काश मार ही दिया होता........"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke14", "ओलम्पिक में गोल्ड है इसलिए बहनें जीत रही हैं,\n" +
                    ".\n" +
                    ".\n" +
                    "अगर गोल्ड-फ्लैक होती तो अपने भाई भी हुनर दिखाते.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke15", "एक वक्त था जब रात 12 बजे के बाद भूत ,प्रेत ,चुड़ैल से डर लगने लगता था ,,, \n" +
                    "लेकिन ट्वीटर ,व्हाट्सअप और फेसबुक नें इनका रोजगार छीन लिया"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke16", "किस्मत मे लड़कियो का इतना अकाल है…\n" +
                    ".\n" +
                    "कि अगर कस्टमर केयर मे फोन लगा दूं…\n" +
                    ".\n" +
                    "… तो भी हमेशा लड़का ही उठाता है……"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke17", "The ultimate guru 4all hubby is his wife.after her arrival there is no need of learning anything new nor is there any use.Happy GuruPurnima"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke18", "माँ बच्चों के लिए हमेशा समझौता कर लेती है हालात से...\n" +
                    ".\n" +
                    ".\n" +
                    "लेकिन सीरियल के टाईम रिमोट मांग लो तो मारती है उलटे हाथ से"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke19", "ज्यादातर लौंडे इस लिये भी सिंगल रह जाते हैं क्योंकि वो अपने से कम सुन्दर लड़की को भाव देना नहीं चाहते, और उनसे ज्यादा सुन्दर लड़की उन्हें भाव देती नहीं।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "KidJoke20", "जो लडकियाँ School में मुझसे कहती थी कि शक्ल देखी है अपनी\n" +
                    ",\n" +
                    ",\n" +
                    ",\n" +
                    ",\n" +
                    "आज उन्हीं लडकियों का Husband देखकर\n" +
                    ",\n" +
                    "दिल को सुकून मिलता है"));

        } else if (title.equals("Husband Wife")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife01", "आज का महा ज्ञान\n" + "अगर “पत्नी” घर की लक्ष्मी है तो\n" + ".\n" + ".\n" + ".\n" + ".\n" + "“गर्ल फ्रेंड” काला धन है \uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife02", "पत्नी- अजी सुनते हो, आपका दोस्त एक पागल लड़की से शादी करने जा रहा है…\n" + "\n" + "उसे रोकते क्यों नहीं ?\n" + "\n" + "पति- क्यों रोकूं ?\n" + "\n" + "उसने मुझे रोका था क्या ?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife03", "पति (पत्नी से): ज़रा, पानी पीला दो…\n" + "पत्नी: क्या हुआ प्यास लगी है?\n" + "\n" + "पति (गुस्से से): नहीं गला चैक करना है,\n" + "\n" + "कि कहीं से लीक तो नहीं है…!!!\n" + "\n" + "\uD83E\uDD23\uD83E\uDD23\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife04", "पति: कहाँ गायब थी 4 घंटे से ?\n" + "\n" + "पत्नी: मोल में गयी थी शॉपिंग करने।\n" + "\n" + "पति: फिर क्या क्या लिया ?\n" + "\n" + "पत्नी: एक Hair band और साथ में\n" + "\n" + "          40 सेल्फी \uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife05", "कुछ रिसर्च के बाद पता चला कि,\n" + "\n" + "महिलाएं अपने बच्चों को\n" + "\n" + "तेज आवाज में इसीलिए डांटती\n" + "है क्योंकि…..\n" + "\n" + "पतिओं में खौफ बना रहे।\n" + "\n" + "\uD83D\uDE32\uD83D\uDE03\uD83D\uDE03\uD83D\uDE04\uD83D\uDE1C\uD83D\uDE0E\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife06", "पति (पत्नी से): ज़रा, पानी पीला दो…\n" + "\n" + "पत्नी: क्या हुआ प्यास लगी है?\n" + "\n" + "पति (गुस्से से): नहीं गला चैक करना है,\n" + "\n" + "कि कहीं से लीक तो नहीं है…!!!\n" + "\n" + "\uD83D\uDE32\uD83D\uDE03\uD83D\uDE03\uD83D\uDE04\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife07", "पत्नी : अजी सुनते हो, आपका दोस्त एक पागल लड़की से शादी करने जा रहा है…उसे रोकते क्यों नहीं ?\n" + "\n" + "पति – क्यों रोकूँ ? उस दोस्त ने मुझे रोका था क्या ?\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife08", "मुझे किसी ने सलाह दी की बीवी से बहस में नहीं जीता जा सकता इसलिए बस मुस्कुरा दिया करो\uD83D\uDE06\n" + "मैंने भी कोशिश की\n" + "\n" + "बीवी : बहुत हंसी आ रही है आजकल तुम्हे ? लगता है तुम्हारा भूत उतारना ही पड़ेगा\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife09", "पति को बाजार जाते हुए देख पत्नी ने पैसे देकर कहा “कुछ ऐसी चीज़ लाना जिस से मैं सुन्दर दिखूं”\n" + "\n" + "\n" + "पति खुद के लिए Whisky की दो बोतल ले आया।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife10", "ज्यादातर पत्नी अपने Husband की बहन को प्यार \n" + "से नहीं देखती, जबकि सारे Husband अपनी Wife की \n" + "बहन को प्यार से ही देखते हैं...\uD83D\uDE02\uD83D\uDE02\n" + "Husband वाकई महान होते हैं\n" + "बस कभी अपनी महानता \n" + "ये बात अलग है की हस्बैंड इस बात का ढिंढोरा नहीं पीटते.....\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife11", "जब शादीशुदा आदमी कहे कि \n" + "वो सोच कर बताएगा तो इसका सीधा सीधा \n" + "मतलब होता है..!!\n" + "\n" + "वो अपनी पत्नी से पूछ कर बताएगा"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife12", "सच्चे प्यार की निशानी\n" + "आज पुरानी गर्लफ्रेंड रास्ते में जाते हुए मिल गयी\n" + ".\n" + ".\n" + ".\n" + ".\n" + "उसने बच्चो से चाचा कहलवाया मामा नहीं\" यही होता है सच्चा प्यार\" \uD83D\uDE02\uD83D\uDE02\n"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife13", "कुछ रिसर्च के बाद पता चला कि,\n" + "महिलाएं अपने बच्चों को\n" + "तेज आवाज में इसीलिए डांटती\n" + "है क्योंकि…..\n" + ".\n" + ".\n" + "पतिओं में खौफ बना रहे।\n" + "\uD83D\uDE32\uD83D\uDE03\uD83D\uDE03\uD83D\uDE04\uD83D\uDE1C\uD83D\uDE0E\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife14", "पति (पत्नी से): ज़रा, पानी पीला दो…\n" + "\n" + "पत्नी: क्या हुआ प्यास लगी है?\n" + "\n" + "पति (गुस्से से): नहीं गला चैक करना है,\n" + "\n" + "कि कहीं से लीक तो नहीं है…!!!\n" + "\n" + "\uD83D\uDE32\uD83D\uDE03\uD83D\uDE03\uD83D\uDE04\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife15", "पत्नी: अजी सुनते हो ?\n" + "\n" + "ऊपर से वह बैग उतार देना !\n" + "\n" + "मेरा हाथ कुछ छोटा पड़ रहा है,\n" + ".\n" + ".\n" + "पति: तो जुबान से ट्राई कर ले।\n" + "पति आयी सी यु में है….।\n" + "\uD83D\uDE32\uD83D\uDE03\uD83D\uDE03\uD83D\uDE04\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife16", "\"शापिंग में व्यस्त बीवी का सब्र से\n" + "साथ देना भी मोहब्बत है,\n" + ".\n" + ".\n" + ".\n" + "ज़रूरी नहीं की हर कोई ताज महल बनवाता फिरे।\uD83D\uDE0F\uD83D\uDE06\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife17", "\"संता की बीवी – सुनिए जी,\n" + "रात नींद में आप मुझे गालियाँ दे रहे थे,\n" + "संता – ओ नहीं सोणिये,\n" + "ये तुम्हारा वहम है,\n" + ".\n" + ".\n" + "बीवी – क्या वहम है?\n" + "संता- यही कि मैं नींद में था.\n" + "\uD83D\uDE33\uD83D\uDE33\uD83D\uDE33\uD83D\uDE33\uD83D\uDE33\uD83D\uDE33\uD83D\uDE33\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife18", "\"संता: तूने iPhone 7 लिया क्या??\n" + "\n" + "बंता: नहीं!!! जिससे बात करनी होती है, ऑटो लेकर उसके घर चला जाता हूँ,\n" + ".\n" + ".\n" + "सस्ता पड़ता है।\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife19", "Wife (On phone, in a romantic mood):\n" + "“Bahu divas thi tamari ichchha hati ne,\n" + "to chalo aaje raat na hoon puri karish!”\n" + "Husband: “Thik chhe! To hoon pan aavta aavta shreekhand leto aavish!”"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife20", "Santa badi dheere dheere phone pe bat kar rha tha,\n" + "\n" + "Biwi : itni dheemi aawaj me kisse baat kar rhe ho?\n" + "Santa : Arrey bahen hai,\n" + "Biwi : toh itna dheere kyon baat kar rhe ho?\n" + "Santa : teri bahen hai.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife21", "Damaad umar me chhota hota hai fir bhi Sasural mein sabhi ushe\n" + "\n" + "Aap kar ke bulaate hai aur uske Sambodhan mein Ji jaroor lagate hai.. Kyonki\n" + "\n" + "Hmaare desh mein SAHIDO ka naam Samman se lene ki parampra hai.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife22", "(Pati or patni ghumne gaye Raaste mai 1 gadha ghaas kha raha tha.)\n" + ".\n" + ".\n" + "Patni – ye ji tumhara rishtedaar ghaas kha raha h, namaste karo.\n" + "Pati – Namaste Sasur Ji…."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife23", "Die laughing\n" + "Biwi Bathroom Se Naha Ke Nikli To Uska Pati Use Ghur Raha Tha!\n" + "Biwi Romantic Hokar Boli: Kya Irada Hai?\n" + ".\n" + ".\n" + "Pati Ne 2 Thappad Maare Or Bola “Mere Garam Pani Se Kyu Nahayi”\n" + "Happy winters.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife24", "Wife: Look at that man who has drunk alot…\n" + "Husband: Whu is he???\n" + "Wife: 10 years ago he was my boy friend and i denied him for marriage!!\n" + "Husband: oh my god..!!! He is still celebrating:-"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife25", "Husband found his wife’s old school report card\n" + "\n" + "FAINTED\n" + "\n" + "The comment written….\n" + ".\n" + ".\n" + "“most obedient and soft spoken student” :"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife26", "Wife :- I luv u… I can’t live without u.\n" + "Mar Jaungi..\n" + "Mit Jaungi..\n" + "Jahar Pee Lungi\n" + "Tere Pyaar Mai Fanna Ho Jaungi..\n" + ".\n" + ".\n" + "Husband:- Dekh Le Jaise Tujhe Theek Lage.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife27", "Wife was teaching eng grammer 2 her husband.\n" + "\n" + "WIFE:I M BEAUTIFUL. Which tense is this.\n" + ".\n" + ".\n" + ".\n" + ".\n" + "HUSBAND; PAST TENSE"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "HusbandWife28", "What is similarity between\n" + "Sun and Wife ……\n" + ".\n" + ".\n" + ".\n" + ".\n" + "Aap in dono ko\n" + "\n" + "Ghoor k nahi dekh sakte….."));
        }
        else if (title.equals("GF-BF")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf01", "प्रेमिका ने प्रेमी से कहा -अपनी शादी के लिए ! तुम मां से मिलकर देखों |\n" + ".\n" + ".\n" + "प्रेमी बोला - नहीं डियर ! अब तुम्हारे सिवाय कोई दूसरी मेरे मन में नहीं बस सकती |"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf02", "युवक - डार्लिंग , क्या मैं पहला शख्स हूं जिसने कि तुमसे प्यार किया हैं ?\n" + ".\n" + ".\n" + "युवती - हां , भई हां ! पता नहीं तुम सारे मर्द लोग यहीं एक सवाल क्यों पूछते हो |\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf03", "प्रेमिका ने प्रेमी से कहा -अपनी शादी के लिए ! तुम मां से मिलकर देखों |\n" + ".\n" + ".\n" + "प्रेमी बोला - नहीं डियर ! अब तुम्हारे सिवाय कोई दूसरी मेरे मन में नहीं बस सकती |"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf04", "लड़का : कल  तुम्हारे  घर  गया  था.\n" + "मुझे  नहीं  लगता  हमारी  शादी  होगी..!\n" + ".\n" + ".\n" + "लड़की : क्यों ..?? पापा  से  मिले??\n" + ".\n" + "लड़का : नहीं.. तुम्हारी  बहन  से  मिला..\n" + "सॉलिड  लगती  है..!!\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf05", "लड़का:- Whatsapp अपडेट करलो..\n" + ".\n" + "लड़की:- कैसे करते है..?\n" + ".\n" + "लड़का:- Play_Store पे\n" + "जाओ और वहाँ से करलो\n" + ".\n" + "लड़की:- हमारे गांव"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf06", "प्रेमी:  इंतजार की घड़ी बहुत लंबी होती है।\n" + ".\n" + ".\n" + "प्रेमिका:  तो किसी और कंपनी की Watch ⌚ खरीद लो..!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf07", "Me -तुम्हारी उम्र कितनी है?\n" + ".\n" + "She -25 साल!\n" + ".\n" + "Me - 5 साल पहले भी तुमने यही कहा था!\n" + ".\n" + "She -देखा हम लड़कियां जुबान की कितनी पक्की होती हैं!\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf08", "She: आज हमारे रिलेशनशिप को पूरा 1 हफ्ता हो गया। \n" + ".\n" + "He: हेल्लो बेबी\n" + ".\n" + "She: बोलते क्यों नहीं कुछ?\n" + ".\n" + ".\n" + "He: सॉरी, 2 मिनट के मौन पर था।\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf09", "लड़का  बोला :-  दिलरुबा\n" + "लड़की  बोली :-  पिज़्ज़ा  खिला\n" + "लड़का  बोला :-  पैसे  नहीं \n" + "लड़की  बोली : - कैसे  नहीं \n" + ".\n" + ".\n" + "लड़का बोला :-  महंगाई  है \uD83D\uDE0E\uD83D\uDE0E\n" + "लड़की  बोली : तो  फिर  आज से  तू  मेरा  भाई  है.\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf10", "प्रेमी : \"तुझ  में  रब  दिकता है, यारा  मै क्या  करूं\n" + ".\n" + ".\n" + ".\n" + "प्रेमिका : दर्शन  कर, दक्षिणा  दे, प्रसाद  ले और चला  जा, क्यू की  तेरे  पीछे  और  भी  भक्त  खड़े है \uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf11", "ब्वॉयफ्रेंड:- संगीत में इतनी ताकत होती है कि पानी तक गरम हो सकता है।\n" + ".\n" + ".\n" + "गर्लफ्रेंड: -  जब तुम्हारा गाना सुन कर मेरा खून खौल सकता है, तो पानी क्या चीज है…..\uD83E\uDD23\uD83E\uDD23\uD83D\uDE0E"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf12", "मोहन:- मेरी बीवी बढ़िया खाना नहीं बनाती यार |\n" + "सोहन:- अरे , यार मैं जिस दिन चाहूं बढ़िया खाना बनवा लेता हूं |\n" + ".\n" + ".\n" + ".\n" + "मोहन:- वो कैसे भाई .............. !\n" + "सोहन: - उस दिन सुबह-सुबह शाम के सिनेमा शो के टिकट ले आता हूं  बस"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf13", "एक लड़के ने एक लड़की को प्रपोज किया और कहा- i Love You ❤️\n" + ".\n" + ".\n" + "लड़की ने लड़के को एक जोरदार थप्पड मारा ओर बोली- क्या बोला रे तू ❓\n" + ".\n" + "रोते हुए लड़का बोला- जब सुना ही नहीं था, तो थप्पड क्यों मारा...।\uD83E\uDDD0\uD83D\uDE22"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf14", "मोनिका ( सविता सहेली से ) - सुनील से मैं शादी नहीं कर सकती |\n" + ".\n" + "सविता - क्यों , क्या तुमने किसी लड़की के साथ देख लिया ?\n" + ".\n" + "मोनिका - नहीं , उसने मुझे दूसरे लड़के के साथ प्यार करते देख लिया |"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf15", "लड़की:- डार्लिंग मेरा मोबाईल रिचार्ज करा दो|\n" + ".\n" + ".\n" + ".\n" + "लड़का:- डार्लिंग मै तेरा आशिक़ हूँ जियो वालो का जमाई नहीं ...\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf16", "लड़की: मैं शादी के बाद तुम्हारे सब दुःख बांट लूंगी।\n" + ".\n" + "लड़का: पर मैं दुखी कहां हूं ?\n" + ".\n" + "लड़की: मैं शादी के बाद की बात कर रही हूं  \uD83E\uDDD0\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf17", "Boy: I want to share everything with you.\n" + ".\n" + ".\n" + ".\n" + "Girl: Let’s start from your bank account"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf18", "Pinky Pinku se ..\n" + "Pinky: Agar mauka mila to tum mujh se shadi karoge??.\n" + "Pinku :\n" + ".\n" + ".\n" + ".\n" + "Agar mauka mil gaya to phir shadi\n" + "kerne ki kiya zaroorat hai.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf19", "Girlfriend Ki Sister Agar Missed Call\n" + "Kare To Usse Kya Kahenge….?\n" + ".\n" + ".\n" + ".\n" + "Betaaa… Mann Me Dusra Laddu\n" + "Phoota."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "GfBf20", "If “Muah” is a kiss, then\n" + "perhaps ‘Kalmuah’ is a promise to kiss tomorrow"));
        }
        else if (title.equals("Teacher Student")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher01", "अध्यापक -बताओ कुतुबमीनार कहां है?\n" + "छात्र -जी , नहीं मालूम |\n" + "अध्यापक -बेंच पर खडे हो जाओ |\n" + ".\n" + ".\n" + "छात्र- ( खडे होकर ) -सर, कुतुबमीनार अभी नहीं दिखाई दे रही ।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher02", "टीचर: वो कौन है जो आसमान में उड़ती है और बच्चे ज़मीन पर देती हैं?\n" + ".\n" + ".\n" + "पप्पू कुछ देर सोचने के बाद - \"एयर होस्टेस\"."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher03", "अध्यापक - तुम कैसे सिद्ध करोगे कि साग-पात खाने वाले की निगाहें तेज होती है |\n" + ".\n" + ".\n" + "छात्र - सर , आज तक किसी ने बकरे या घोडे को चश्मा लगाते देखा है क्या ?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher04", "मास्टर: मोहन किताबे देखकर तुझे क्या महसूस होता है। \n" + "\n" + " मोहन :  सर मुझे बस एक ही चीज महसूस होती है। मासटर: क्या । \n" + "\n" + "मोहन: बुलाती है मगर जाने का नही\uD83D\uDE0E\uD83D\uDE0E\uD83D\uDE0E। यह सुनते ही मास्टर बेहोश। ...."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher05", "टीचर: पप्पू तुम कॉलेज किसलिये आते हो?\n" + "पप्पू: विद्या पाने के लिये....!!\n" + ".\n" + ".\n" + "टीचर: तो अब सो क्यों रहे हो?\n" + "पप्पू: क्योंकि आज विद्या नही आई है सर."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher06", "टीचर: एक दिन ऐसा आयेगा जब पृथ्वी पर पानी नही रहेगा, जीव-जंतु नष्ट हो जायेंगे पृथ्वी तबाह हो जायेगी.\n" + ".\n" + "पप्पू :सर तो क्या उस दिन ट्यूशन आना है?\uD83D\uDE1D\uD83D\uDE0E\uD83E\uDDD0"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher07", "बच्चा (टीचर से)- मुझे आपसे प्यार है! मैं आपसे शादी करना चाहता हूं!\n" + "\n" + "टीचर- मुझे बच्चे पसंद नहीं\n" + "\n" + "बच्चा- ठीक है मैं कोशिश करूंगा कि बच्चे न हो!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher08", "अध्यापक- शाबाश चिंटू, मुझे खुशी है कि तुमने इतने अच्छे अंक लिए। आगे भी ऐसे ही अच्छे अंक लेना।\n" + ".\n" + ".\n" + "चिंटू - अच्छा सर, पर आप भी परचे भाई साहब के प्रेस में छपवाते रहिएगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher09", "टीचर- तीन ऐसी जगह बताओ, जहां इंसान मरता नहीं।\n" + ".\n" + ".\n" + "चिंटू- स्वर्ग, नरक और.. स्टार प्लस। \uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher10", "टीचर:- कल स्कूल क्यू नही आये .?\n" + "लड़का:- जी कल तो त्यौहार था ना\n" + ".\n" + "टीचर:- कौनसा त्यौहार.?\n" + "लड़का:- मैडम IPL, ये है इंडिया का त्यौहार"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher11", "अध्यापक (छात्र से)- जवानी और बुढ़ापे में फर्क बताओ?\n" + ".\n" + ".\n" + "छात्र- जवानी में मोबाइल में हसीनों के नंबर होते हैं, और बुढ़ापे में हकीमों के..!\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher12", "टीचर  ने  स्टूडेंट से कहा :  जो  दुसरे  को  अपनी  बात  न  समझा  सके  वोह गधा  होता  है ..... \n" + "\n" + "स्टूडेंट :  सर , क्या  मतलब  मैं  समझा  नहीं ....?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher13", "आज पप्पु ने पूरा SCHOOL हिला डाला,\n" + ".\n" + ".\n" + "Teacher:- छिपकली कौन है?\n" + ".\n" + ".\n" + ".\n" + "Pappu:- छिपकली एक गरीब मगरमच्छ है जिसे\n" + "बचपन\n" + "\n" + "मेँ 'Born Vita' वाला दूध नहीं मिला , जिस कारण \n" + "वो कुपोषण का शिकार हो गया।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher14", "अध्यापक- बताओ! सबसे ज्यादा नशा किस चीज में होता है?\n" + "एक बच्चा- किताबों में...।\n" + "अध्यापक- वो कैसे? मैं समझा नहीं।\n" + "बच्चा- किताब खोलते ही नींद जो आ जाती है....।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher15", "रसायन शास्त्र की कक्षा में\n" + "टीचर: पानी का फॉर्मूला बताओ\n" + "स्टूडेंट: H2O + MgCl2 + CaSO4 + AlCl3 + NaOH + KOH + HNO3 +HCl + CO2 …\n" + ".\n" + ".\n" + ".\n" + "टीचर: ये उत्तर गलत है .\n" + "स्टूडेंट: मैडम, ये नाले का पानी है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher16", "टीचर:\n" + "जिसको सुनाई नहीँ देता उसको क्या कहेँगे ?\n" + ".\n" + "शिष्य:\n" + "कुछ भी कह दो साले को!\n" + "कौनसा सुनाई देता है!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher17", "ग्रामर की टीचर पप्पू से –\n" + "“संदीप अब शराब नहीं पीता है।\n" + "“इसमें संदीप क्या है ??\n" + "\n" + ".\n" + "\n" + "पप्पू –\n" + "इसमें संदीप माता रानी का भगत है और\n" + "उसने नवरात्रा रखा हुआ है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher18", "टीचर :- देश के सबसे ईमानदार पुलिसवाले कहा पर पाए जाते है…???\n" + "\n" + ".\n" + "\n" + "पप्पू :- सावधान इंडिया और क्राइम पेट्रोल !"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher19", "टीचर:-\n" + "“क्लास में लड़ाई\n" + "क्यों नही करनी चाहिए..?”\n" + ".\n" + "पप्पू:-\n" + "“क्योंकि पता नही\n" + "एग्जाम में कब किसके पीछे\n" + "बैठना पड़ जाये..!”"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher20", "अध्यापक -तुम बडे मुर्ख हो बालक , मै तुम्हारी उम्र मे अच्छी तरह किताब पढ लेता था |\n" + "छात्र - श्रीमान आपको अच्छा मास्टर मिल गया होगा?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Teacher21", "अध्यापक ने बच्चों से कहा- बच्चों 2030 में कयामत आएगी | दुनिया तबाह हो जाएगी ... सब कुछ तबाह हो जाएगा |\n" + "एक बच्चा बोला - सर ! क्या उस दिन हमारे स्कूल की छूट्टी रहेगी ?"));
        }
        else if (title.equals("Friendship")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship01", "ज़िन्दगी में भले एक गर्लफ्रेंड तक ना हो \n" + ".\n" + "फिर भी...\n" + ".\n" + "जगजीत सिंह की ग़ज़लें सुनने बैठो तो  \n" + ".\n" + "लगता है \n" + "जैसे 10-12 छोड़ के चली गयी हों...\n" + "\uD83D\uDE1C \uD83D\uDE1C \uD83D\uDE1C \uD83D\uDE1C \uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship02", "एक लड़का दूसरे लड़के से कहता है- तूने मेरी गर्लफ्रेंड को प्रपोस क्यों किया?\n" + ".\n" + "लड़के ने मस्त जबाब दिया-- जब तक लड़की कुंवारी है,\n" + "न तुम्हारी है, न हमारी है, बस सरकारी है।\n" + "\uD83D\uDE00\uD83D\uDE04\uD83D\uDE00\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE04\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship03", "1 मुर्गी ने बत्तख् से शादी कर ली\n" + "Murga: हम मर गये थे क्या?\n" + "Murgi: मै तो तुमसे ही शादी करना चाहती थी पर\n" + "Mom-Dad चाहते थे लड़का नेवी में हो."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship04", "लडकी- Hello baby, tumhari yaad aa rahi thi.... \uD83D\uDE14\uD83D\uDE14\uD83D\uDE14\uD83D\uDE14\n" + ".\n" + ".\n" + "लड़का- Abhi salary nahi aayi hai meri... \uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\n" + ".\n" + "लडकी- Acha chalo....papa aa gaye,\n" + "bye.\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE06\uD83D\uDE06\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship05", "एक लड़की दुपट्टा मुँह पे लपेटे हुए स्कूटी\n" + "से जा रही थ\uD83D\uDE0D :-:\n" + ".       पास से असलम मियां बाइक से जाते हुए\n" + "बोला, जानेमन, ज़रा मुखड़ा तो दिखाती जाओ....... \uD83D\uDE18 \uD83D\uDE18 \n" + "लड़की: अब्बु , मैं हूँ! सलमा ...\"\n" + " \uD83D\uDE1D \uD83D\uDE1C \uD83D\uDE02 \uD83D\uDE02 \uD83D\uDCAA \uD83D\uDC4D \uD83D\uDC4D\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship06", "एक आदमी की मौत के बाद...\n" + ".\n" + "उसका दोस्त उसकी पत्नी के पास आया और बोला:- \n" + "क्या मैं उसकी जगह ले सकता हूं ?\n" + ".\n" + "पत्नी- मुझे कोई ऐतराज नही है।, \n" + ".n" + "कब्रिस्तान वालों से पूछ लो।\n" + ".\n" + "\uD83D\uDE33\uD83D\uDE44\uD83D\uDE15\uD83D\uDE14\uD83D\uDE27\uD83D\uDE26\uD83D\uDE2F\uD83D\uDE30\uD83D\uDE2D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship07", "दूध की थैली ले लो\n" + "\n" + "दूध की थैली ले लो\n" + ".\n" + ".\n" + "लड़की बाहर आती है और शरमा कर वापस अंदर चली जाती है\n" + ".\n" + ".\n" + "माँ : क्या हुआ ??\n" + ".\n" + "लड़की : कमीना ब्रा बेच रहा है।\n" + "\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship08", "टीचर -तुम आज लेट क्यों हो गई?\n" + ".\n" + " लड़की  -सर , एक लडका मेरा पीछा कर रहा था | \n" + ".\n" + "टीचर -पर ऐसे में तो तुम्हें जल्दी आना चाहिए था ,\n" + " फिर लेट कैसे हो गई ? \n" + ".\n" + "लडकी - सर , वो बहुत धीरे-धीरे चल रहा था \n" + "     \uD83D\uDE1C\uD83D\uDE1D\uD83D\uDE1B"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship09", "जो लड़का अपनी गर्लफ्रेंड के लिए \n" + "रात भर जागता है\n" + ".\n" + "वही लड़का\uD83D\uDE1C\n" + ".\n" + "बता सकता है की सबसे बढ़िया बैटरी \n" + "\"बैकअप\" वाला मोबाइल कौन सा \n" + "है!!!!! \uD83D\uDE02\n" + "\n" + "\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D\uD83D\uDE1D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship10", "संता :मैंने सुना है ,तेरी शादी तय हो गयी है। वह भी काफी छोटे कद की लड़की से ?और तुमने उसे पसन्द भी कर लिया है।\n" + "बंता : हां यार ,जीवन में मुसीबत जितनी छोटी हो उतना ही अच्छा होता है !!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship11", "Kareena (on phone) giving directions to her friend to reach the hospital\n" + ".\n" + "\"Han..pehle daimur...seedha aake baimur...Aur bass fir Taimur !!\"\n" + "----------------"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship12", "डॉक्टर: आप बिल्कुल मेरी तीसरी पत्नी की तरह लग रही हैं!\n" + "\n" + "लड़की: सच में...?\n" + "कितनी पत्नियां हैं आपकी....?\n" + "\n" + "डॉक्टर: दो! \uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship13", "संता बस में खड़ा था ,ब्रेक लगी तो वो एक लड़की पे जा   गिरा !!!\n" + ";\n" + ";\n" + "लड़की : क्या कर रहे हो ?\n" + ";\n" + ";\n" + "संता : में इंजीनियरिंग और आप ??"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship14", "Google, Microsoft, Facebook, Apple \n" + ".\n" + "सबके मालिक लड़के हैं,\n" + ".\n" + ".\n" + ".\n" + " आखिर लड़कियाँ इतने मार्क्स लेकर करती क्या हैं?\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE02\uD83D\uDE02\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship15", "एक आदमी था\n" + "उसने दारु पीना बंद कर दिया\n" + "उसका लिवर तो बच गया\n" + "परंतु\n" + "उसके पास कोई दोस्त नही बचा\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship16", "\uD83D\uDE09\uD83D\uDE0E\n" + "रिश्ते वाले: \"जी लड़की ने क्या किया हुआ है??\"\n" + ".\n" + ".\n" + ".\n" + ".\n" + "घरवाले: \"जी इसने नाक में दम किया हुआ है, इसे ले जाएँ बस....\"\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship17", "\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE19\uD83D\uDE17\uD83D\uDE00\uD83D\uDE0A\uD83D\uDE04\n" + "\uD83D\uDC69 लड़की : -\n" + "                   हे भगवान, मेरी शादी किसी समझदार आदमी से करवा दो,\n" + "\uD83C\uDF0Dभगवान : -\n" + "                    घर जाओ बेटी समझदार आदमी कभी शादी नहीं करते !\n" + "\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE19\uD83D\uDE0C\uD83D\uDE17\uD83D\uDE1C\uD83D\uDE03\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship18", "भगवान का दिया कूछ भी नही \n" + ".\n" + "ना दौलत , ना सौहरत ,और ना सेटिंग \n" + ".\n" + ".\n" + "\n" + "दो चार काले कलूटे दोस्त थे पर वो भी  Jio ki Sim मिलते मुँह फेर गये।\n" + "\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship19", "शादी के मंडप में बैठे\n" + "लड़के ने लड़की से कहा\n" + "लड़का:- मेरा 10 लड़कियों के साथ अफेयर था\n" + "लड़की:- मुझे पता था जब कुंडली मिली\n" + "है तो कैरेक्टर भी मिलेगा\n" + "लड़का आज तक सदमे में है\uD83D\uDE33\uD83D\uDE33"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship20", "उस दोस्त की हर गलती माफ हो\n" + "जाती है,\n" + ".\n" + ".\n" + ".\n" + "जब वो मुस्कुरा कर पूछता है\n" + ".\n" + ".\n" + ".\n" + "बैठना है आज?...\uD83C\uDF7B\uD83C\uDF7B\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship21", "बाप : शराब , \n" + "सिगरेट , लड़किया ये सब तुम्हारे\n" + "दुश्मन है......!!!\n" + ".\n" + ".\n" + ".\n" + ".\n" + "बेटा : पापा जो अपने दुश्मनो से डर जाये वो\n" + "मर्द नही....\uD83D\uDE0E\n" + "फिर क्या था दे चप्पल पे चप्पल"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Friendship22", "दोस्त - अरे तू इतना मोटा कैसे हो गया \uD83D\uDE33\uD83D\uDE33\n" + ".\n" + "पप्पु- हमारे घर में फ्रिज नहीं है ना \uD83D\uDE14\uD83D\uDE14\n" + ".\n" + "दोस्त- तो ? \uD83D\uDE33\uD83D\uDE33\n" + ".\n" + "पप्पु - कुछ बचा नहीं सकते, \n" + "सब खाना पड़ता है.\uD83D\uDE1D\uD83D\uDE1C\uD83D\uDE1B"));
        }
        else if (title.equals("Celebrity Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood01",  "बस 2 मिनट के लिए मै अपना फोन दोस्त के घर पर भूल आया था।\n" +
                    ".\n" +
                    ".\n" +
                    ".\n" +
                    "ऐसी Feeling आ रही थी जैसे मै अपनी मासूम गर्लफ्रेंड को शक्ति कपूर के पास छोड़ आया हूं।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood02",  "23 साल पहले पूजा भट्ट की फ़िल्म आई थी 'सड़क', और अब उसकी बहन आलिया भट्ट की फ़िल्म आई है 'हाईवे'।\n" +
                    "कौन कहता है देश का विकास नहीं हुआ?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood03",  "आख़िरकार हमें जवाब मिल गया, कि एक चुटकी सिंदूर की क़ीमत तुम क्या जानो रमेश बाबू?\n" +
                    "400 करोड़ रू जो ऋतिक दे रहा है सुज़ैन को।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood04",  "ह्रदय विशेषज्ञ (Cardiologist) और गब्बर सिंह के बीच सामान्य क्या है?\n" +
                    "दोनों यही कहते हैं, कि तूने नमक खाया था न अब।\n" +
                    ".\n" +
                    ". .\n" +
                    ". . .\n" +
                    "\"गोली खा।\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood05",  "आलिया (पुलिस वालों से): कहाँ जा रहे हो?\n" +
                    "पुलिस वाले: लाठी चार्ज करने।\n" +
                    "आलिया: अरे 'चार्जर' तो ले जाओ।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood06",  "अगर रोहित शेट्टी ने Interstellar बनाई होती तो,\n" +
                    "उसमे Spaceship की जगह Scorpio होती और स्पेस में इतने धमाके होते कि सूर्य की रौशनी भी कम लगती।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood07",  "धर्मेन्द्र ने जब हेमा मालिनी से शादी करने के लिए अपना धर्म बदला तो उनकी पहली पत्नी ने कहा:\n" +
                    "\"अब धरम बदल गया है।\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood08",  "पूरे साल में शायद दो ही बार अमीषा पटेल टी.वी पे आती है;\n" +
                    "1) वैलेंटाइन्स के दिन - \"कहो ना प्यार है।\"\n" +
                    "2) स्वतंत्रता (Independence) वाले दिन - 'ग़दर।'"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood09",  "सच में वक्त बदल रहा है:\n" +
                    "मामा आमिर खान: पापा कहते हैं बड़ा नाम करेगा।\n" +
                    "भांजा इमरान खान: डैडी मुझसे बोला, तू गलती है मेरी।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "Bollywood10",  "वक़्त और संजय दत्त\n" +
                    ".\n" +
                    ".\n" +
                    ".\n" +
                    ".\n" +
                    "कैसे निकल जाते हैं पता ही नहीं चलता।"));
        }  else if (title.equals("Desi Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke01",  "An angry boss-\n" +
                    "Tumne kabhi ullu dekha hai ..\n" +
                    "Employee(sar jhuka ke)-Nahi sar!\n" +
                    "Oss-Neeche kya dekh rhe ho idiot..\n" +
                    "Meri taraf Dekho...."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke02",  "औरतें इतनी चालाक होती जा रही हैं।\n" +
                    "कल मैंने मज़ाक में अपनी साली से कहा, \"साली तो आधी घरवाली होती है।\"\n" +
                    "अब वो आधी सैलरी मांग रही है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke03",  "Q. What is the difference between WATCH &amp; WIFE ..\n" +
                    "A. Ek bigarti hai to bandh ho jati hai...\n" +
                    ".\n" +
                    "Doosari bigarti hai to -SHURU- ho jati hai"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke04",  "Delhi ki sardi, U.P ki garmi, Mumbai ki barish, Patna ka coruption, kashmir ka terrorism, Africa ka saap aur chidiya ghar ke aap.. BAAP RE BAAP!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke05",  "Bus me sardar 1 ladki pe ja gira tab ladki boli:Battamiz kya kar rahe ho....\n" +
                    "Srdr bola:Ji PUNJAB UNIVERSITY se BCOM kar raha h.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke06",  "Jack:ek din jack bike par apne 2 friends ko bethakr le ja raha tha.\n" +
                    "Police:oi stop..\n" +
                    "Jack:bola bike par jagah nahi hai .."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke07",  "Beta: mummy ye school kya hota hai.\n" +
                    "Mummy: Ye wo jagah hoti hai jaha parent ko loota aur bachho ko pita jata hai."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke08",  "Ram yug mein Dudh mila..\n" +
                    ".\n" +
                    "Krishna yug mein ghee..\n" +
                    ".\n" +
                    "Kalug mein Chai mile,\n" +
                    ".\n" +
                    "Phuk mar ke Pee.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke09",  "अजब सी हालत है तेरे जाने के बाद\n" +
                    "मुझे भूख लगती नहीं खाना खाने के बाद\n" +
                    "मेरे पास दो ही समोसे थे जो मैंने खा लिए\n" +
                    "एक तेरे आने से पहले एक तेरे जाने के बाद।\uD83D\uDE06\uD83E\uDD23\uD83D\uDE06"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke10",  "Friend~यार काश पढ़ाई प्यार की तरह होती अपने आप ही हो जाती\n" +
                    "Me~अरे नही रे काश प्यार पढ़ाई की तरह होता घरवाले पीट-पीटकर करवाते।\uD83D\uDE01\uD83E\uDD23\uD83D\uDE06"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke11",  "छोरी :- हम भाग कर शादी करेंगे \n" +
                    "छोरा :- रै पागल\n" +
                    "टैंट की रजाई और भागी हुई लुगाई किसी की नहीं होती।\uD83D\uDE03\uD83D\uDE04\uD83D\uDE04\uD83D\uDE01\uD83E\uDD23\uD83D\uDE06"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke12",  "उसने मुझसे पूछा\n" +
                    "चाहोगे मुझे कब तक\n" +
                    "मैंने भी मुस्कुरा के कह दिया\n" +
                    "मेरी बिवी को न पता\n" +
                    "चले तब तक।\uD83D\uDE04\uD83E\uDD23\uD83E\uDD23\uD83D\uDE06\uD83D\uDE06\uD83D\uDE01\uD83D\uDE01\uD83D\uDE06"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke13",  "एक बहुत बडे शराबी के गाड़ी के पीछे लिखा हुआ था\n" +
                    "दारु पीयो ऐश करो रोटी तो साले कुते भी खाते है।\uD83D\uDE04\uD83D\uDE06\uD83D\uDE03\uD83D\uDE06\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01"));

            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke14",  "बड़ा कलयुग आ गया है\n" +
                    "जिस दोस्त की शादी में\n" +
                    "जी-जान से नाचे थे\n" +
                    "वो नालायक अपनी बीवी को\n" +
                    "एलबम दिखाते हुए बोला\n" +
                    "शराबी हैं साले।\uD83D\uDE03\uD83D\uDE06\uD83D\uDE04\uD83D\uDE06\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke15",  "एक लड़की ने पिज्जा शॉप में जा कर पिज्जा आर्डर किया\n" +
                    "वेटर : मैडम, इसके 4 पीस करूं या 8 पीस\n" +
                    "लड़की बोली : 4 पीस ही कर दो\n" +
                    "8 खाऊंगी तो मोटी हो जाऊंगी।\n" +
                    "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE1D\uD83D\uDE4A\uD83D\uDE4A\uD83D\uDE1D\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke16",  "तुम अपने आप को कितना भी बडा आशिक समझ लो लेकिन सबसे दर्द भरे गानों का कलेक्शन ट्रेक्टर और ट्रक चलाने वालों के पास ही मिलता है।\uD83E\uDD23\uD83D\uDE01\uD83D\uDE1F\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DesiJoke17",  "बाप ने बेटे की तलाशी ली और कोट से सिगरेट और लड़कियों के नंबर निकले\n" +
                    "बाप- बहुत मारा और पूछा कब से चल रहा है यह सब\n" +
                    "बेटा- पापा यह कोट तो आपका है।\uD83D\uDE06\uD83D\uDE0D\uD83D\uDE06\uD83D\uDE04\uD83D\uDE03\uD83E\uDD23\uD83D\uDE01"));
        } else if (title.equals("Naughty Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke01",  "मतदाता (चुनाव लड़ रहे नेता से) यह तो बताइए आप जीत गए तो क्या करेंगे?\n" +
                    "नेताः भाई, मुझे तो चिंता इस बात की है कि अगर हार गया तो क्या करूँगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke02",  "Ladki – Yaar yeh ladke aapas mein kitni gandi baat karte hai..\n" +
                    "Dusri Ladki – Kitni Gandi ?? Ladki – Arrey jitni gandi ham aapas mein karte hai..\n" +
                    "Dusri – Hey!! Bhagwaan ittni gandi."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke03",  "संता रोटी का एक निवाला खुद खा रहा था और एक पास बैठी मुर्गी को खिला रहा था…\n" +
                    " बंता :- “ये क्या कर रहा है?”\n" +
                    "संता :- “चिकन के साथ रोटी खा रहा हू.”"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke04",  "dekh pagle\n" +
                    ".\n" +
                    "agar koi ladki tujhe bhai bolde to…\n" +
                    ".\n" +
                    "tension na le…shadi se pehle sab bhai behen\n" +
                    ".\n" +
                    "hi hote hai yaar…"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke05",  "रमन ने रमेश से पूछा- ‘आखिर क्या कारण था कि आपने अपने बेटे की सगाई तोड़ दी।‘\n" +
                    "रमेश ने जवाब दिया- ‘क्या करता, मेरी होने वाली बहू बॉक्सिंग चैंपियन जो थी।‘"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke06",  "After Marriage!!\n" +
                    "\n" +
                    "Santa: Oye bhabi ka kya naam hain???\n" +
                    "Banta: google kaur!!\n" +
                    "Santa: Esa kyun??\n" +
                    "Banta: Sawal ek puchho jawab 10 milta hai"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke07",  "आंटी (ऑन फोन) : बेटा, मम्मी है?\n" +
                    "मे : हा जी एक मिनिट. मम्मी\n" +
                    "मम्मी : किसका फोन है?\n" +
                    "मे : फोन तो अपना ही है, उसपे किसी का कॉल आया है"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke08",  "dekh yaar…\n" +
                    ".\n" +
                    "khushi taqdeero mai honi chaiye..\n" +
                    ".\n" +
                    "warna tasveero mai har koi khush nazar aata hai.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke09",  "गर्ल: तुम क्या करते हो ?\n" +
                    "बॉय: टाइम्स ऑफ इंडिया मे जॉब करता था लेकिन अभी छोड़ड़ दिया\n" +
                    "गर्ल: क्यू?\n" +
                    "बॉय: एसी ठंडी मे कोण जल्दी उठ के पेपर देने जाएगा ?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke10",  "I dont want to be ur favrouite or ur best…\n" +
                    "I want to be ur only and forget the rest…\n" +
                    ".\n" +
                    ":Who-s-the-man:"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke11",  "Dekh Jaan\n" +
                    ".\n" +
                    "Tere Pyaar me kitna intezaar kiya\n" +
                    ".\n" +
                    "Aur ush intezaar me na jaane kitno se pyaar kiya.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke12",  "Tum mere sath sona chahte ho..?\n" +
                    "santa:- han,,lekin tum meri biwi ki trah krogi....?\n" +
                    "call gril wo kese..?\n" +
                    "santa:- free me....."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke13",  "What is similarity between\n" +
                    "Sun and Wife ……\n" +
                    ".\n" +
                    "Aap in dono ko\n" +
                    ".\n" +
                    "Ghoor k nahi dekh sakte….."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke14",  "Dil Me Sabko Aane deta hu..\n" +
                    "Per Shaq na kar,\n" +
                    "Tu Jha Basti Hai Waha\n" +
                    "Kisi Ko Jaane Nahi Deti.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke15",  "Pehle Barish\n" +
                    ".\n" +
                    "Phir Ole\n" +
                    ".\n" +
                    "Ab dhati Dole\n" +
                    ".\n" +
                    "Kya ho raha hai Bhole"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke16",  "When i drink alcohol…\n" +
                    "everyone says I’m alcoholic…\n" +
                    ".\n" +
                    ".\n" +
                    "But, When i drink Fanta…\n" +
                    "no one says I’m fantastic…;)\n" +
                    ".\n" +
                    ".\n" +
                    "jalte hain sab…."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "NaughtyJoke17",  "जज: आर्डर, आर्डर\n" +
                    "अभियुक्त: एक डोसा, तीन इडली, २ वड़ा पाव, और एक कोल्ड्रींक\n" +
                    "जज: शटअप\n" +
                    "अभियुक्त: नहीं, नहीं, सेवनअप"));
        } else if (title.equals("Lawyer Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "LawyerJoke01",  "जज: आर्डर, आर्डर\n" +
                    "अभियुक्त: एक डोसा, तीन इडली, २ वड़ा पाव, और एक कोल्ड्रींक\n" +
                    "जज: शटअप\n" +
                    "अभियुक्त: नहीं, नहीं, सेवनअप"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "LawyerJoke02",  "शर्मा जी (वकील से)-मुझे अपनी बीवी से तलाक चाहिए,\n" +
                    "\n" +
                    "वो मुझसे पिछले छ: महीने से बात ही नहीं कर रही है।\n" +
                    "वकील- एक बार फिर से सोच लो..ऐसी बीवी बार-बार नहीं मिलती..!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "LawyerJoke03",  "जज: तुम अपनी सीमा लांघ रहे हो।\n" +
                    "वकील: कौन साला ऐसा कहता है?\n" +
                    "जज: तुमने मुझे साला बोला?\n" +
                    "वकील: नहीं माई लार्ड! मैं पूछा कौन सा-ला ऐसा कहता है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "LawyerJoke04",  "वकील डॉक्टर से – किसी का इलाज करते समय क्या कभी आपसे कोई गलती हुई है ?\n" +
                    "डॉक्टर – हां एक बार मैंने एक मरीज से 100 रूपए फीस ली जबकि मुझे बाद में पता लगा कि उसके पास 500 रूपए थे!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "LawyerJoke05",  "नीलू- तुम्हारी बेटी की सगाई को पूरे 2 साल हो गये है, विवाह मे इतनी देरी क्यूं?\n" +
                    "संगीता- दरसल, लड़का एक वकील है। जैसे ही विवाह की तारीख पास आती है वह कोई ना कोई बहाना करके तारीख आगे बढ़ा देता है।"));
        } else if (title.equals("Doctor-patient")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke01",  "मरीज डॉक्टर साहब ऐसी बीमारी से तो मर जाना अच्छा \n" +
                    "डॉक्टर– हम कोशिश कर तो रहे हैं ।\uD83E\uDD23\uD83E\uDD23\uD83D\uDE01\uD83D\uDE03\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke02",  "डॉक्टर ने एक पागल से पूछा- तुम छत से\n" +
                    "क्यों लटक रहे हो\n" +
                    "पागल- मैं एक बल्ब हूं\n" +
                    "डॉक्टर- तुम जल क्यों नही रहे\n" +
                    "पागल- अभी लाइट गयी हुई है।\uD83D\uDE01\uD83D\uDE01\uD83E\uDD23\uD83D\uDE03\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke03",  "डॉक्टर आधी रात को उठा और पत्नी से\n" +
                    "बोला- मैं हॉस्पिटल जा रहा हूं फोन आया है इमरजेंसी है।\n" +
                    "पत्नी- किसी को तो अपनी मौत मरने दो।\uD83D\uDE04\uD83D\uDE01"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke04",  "डॉक्टर कैसे आना हुआ\n" +
                    "मरीज- डॉक्टर साहब तबियत ठीक नही है\n" +
                    "लीवर में बहुत दर्द हो रहा है डॉक्टर शराब पीते हो\n" +
                    "मरीज- हां पर छोटा पैग ही बनाना।\uD83D\uDE03\uD83D\uDE01\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke05",  "डॉक्टर (शर्मा जी से)- आपका और आपकी पत्नी का ब्लडग्रुप एक ही है?\n" +
                    "\n" +
                    "शर्मा जी- होगा, जरूर होगा 25 साल से मेरा खून जो पी रही है.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke06",  "एक बच्चा पैदा होते ही डॉक्टर से बोला- मोबाइल है क्या?\n" +
                    "\n" +
                    "डॉक्टर- क्यों?\n" +
                    "\n" +
                    "बच्चा- जरा भगवान को डिलीवरी मैसेज भेजना है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke07",  "डॉक्टर आधी रात को उठा और पत्नी से बोला- मैं हॉस्पिटल जा रहा हूं.. फोन आया है इमरजेंसी है।\n" +
                    "\n" +
                    "पत्नी- किसी को तो अपनी मौत मरने दो।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke08",  "डॉक्टर (मरीज से)- आप डरिये मत, मैं हूं ना।\n" +
                    "\n" +
                    "मरीज (डॉक्टर से)- वही मेरा सबसे बड़ा डर है डॉक्टर।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke09",  "नरेश अपनी बीवी को मार रहा था..\n" +
                    "\n" +
                    "एक आदमी ने पूछा क्यों मार रहे हो?\n" +
                    "\n" +
                    "नरेश- डॉक्टर ने कहा है, इसको दवाई कूट कर देनी है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DoctorJoke10",  "डॉक्टर (मरीज से)- रोज होटल में खाने से ही आपको अल्सर हुआ है?\n" +
                    "मरीज- ठीक है डॉक्टर साहब कल से मैं खाना घर के लिए पैक करवा लिया करूंगा।"));
        } else if (title.equals("Sharabi Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke01",  "रॉयल पैंट\n" +
                    "लगाए तो घर रंगीन दिखता है.\n" +
                    ".\n" +
                    ".\n" +
                    "अरे पागल 100 की रॉयल स्टॅग पियो,\n" +
                    "सारा शहर रंगीन दिखेगा..!!!\n" +
                    "\uD83D\uDE02\uD83D\uDE02\n" +
                    "अखिल भारतीय दारूखोर एवं बेबडा पार्टी जिंदाबाद ....\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE1D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke02",  "नवरात्री में दारु चिकन छोड़ने वाले भक्त जितनी शिद्दत से दशहरे का इंतज़ार करते है उतना तो नोकरीपेशा व्यक्ति अपनी सेलरी का भी नही करता\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke03",  "पप्पू को बीड़ी पीने की लत लग गयी।\n" +
                    ".\n" +
                    ".\n" +
                    "उसके पिता जी ने लत छुड़ाने के लिए बाबा रामदेव की योगा क्लास में भेजा।\n" +
                    "\n" +
                    "पप्पू अब पाँव से भी बीड़ी पी लेता है।\uD83D\uDE1D\uD83D\uDE1C\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke04",  "\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\n" +
                    "बारात में चाहे कितने ही तीस मार खान आये हो...\n" +
                    "\n" +
                    "कितने ही वी आई पी आये हो.....\n" +
                    "\n" +
                    "पर सबसे ज्यादा पूछ उसी की होती हैं.....\n" +
                    "\n" +
                    "जिसके पास दारु वाली गाडी की चाबी होती हैं \uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke05",  "ख़ुशी का कोई ठिकाना नहीं होता,\n" +
                    "\n" +
                    "ख़ुशी का सिर्फ ठेका होता है।\n" +
                    "\n" +
                    "सौजन्य: ठेका शराब\n" +
                    "\n" +
                    "\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke06",  "एक बहुत बडे शराबी के गाड़ी\n" +
                    "के पीछे लिखा हुआ था\n" +
                    "?\n" +
                    "?\n" +
                    "?\n" +
                    "?\n" +
                    "\"दारु पीयो ऐश करो\"\uD83D\uDE0B\n" +
                    "\"रोटी तो साले कुते भी खाते है\n" +
                    "☝\uD83D\uDE1C\uD83D\uDE46\uD83D\uDE1D\uD83D\uDE02\uD83D\uDC4D\uD83C\uDFFB"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke07",  "गली में बदनामी का आलम कुछ यूँ है कि .....\n" +
                    ".\n" +
                    "उपवास के लिए चिप्स लेने जाओ.....\n" +
                    ".\n" +
                    ".\n" +
                    "तो दुकानदार पूछता है ...\n" +
                    "\"आज भी पीने का प्रोग्राम है!!\"\n" +
                    "\uD83D\uDE1C\uD83D\uDC46\uD83D\uDE1D\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke08",  "दीवाली के Rocket को देख के यह एहसास हुआ कि\n" +
                    ".\n" +
                    ".\n" +
                    "अगर जीवन में ऊँचाइयों तक पहुँचना है तो,\n" +
                    "बोतल का सहारा तो लेना ही पड़ेगा\uD83D\uDE1B\uD83D\uDE0C\uD83D\uDE1C\uD83D\uDE1B\uD83C\uDF7B\uD83C\uDF7B\uD83C\uDF7A\uD83C\uDF77\uD83C\uDF77\uD83C\uDF7E\uD83C\uDF7E"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke09",  "ये इंडिया है प्यारे\n" +
                    "\n" +
                    "यहाँ धर्मेन्द्र को तो \n" +
                    "कुत्तों का खून पीना पसन्द है\n" +
                    "पर\n" +
                    "उसकी घरवाली \n" +
                    "केंट RO पानी पीने की सलाह देती है\n" +
                    "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke10",  "वा नयुं बोली दारू पीऊंगी ।\n" +
                    "मै बोल्या \n" +
                    "मखा रहण दै मैडम 1 घुंट मैं न्यूं हांडेगी\n" +
                    "ज्युकर चप्पल लाग्गै पाछै \n" +
                    "मुसी हंड्या करै।।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke11",  "\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\uD83D\uDC60\n" +
                    "लडकीयाँ 300 की सेँडल खरीद के पूरे घर मे कहती हैँ\n" +
                    "शाँपिँग करके आ रही हूँ”\n" +
                    "लडके 2000 की दारु पीकर आते,\n" +
                    "चुपचाप सो जाते हैं\n" +
                    "“सादा जीवन,उच्च विचार !\n" +
                    "    \uD83D\uDE1C\uD83D\uDE1C\n" +
                    "\uD83C\uDF7B\uD83C\uDF7B\uD83C\uDF78\uD83C\uDF78\uD83C\uDF7B\uD83C\uDF7B\uD83C\uDF78\uD83C\uDF78\uD83C\uDF7B"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke12",  "नागिन डांस का सबसे बड़ा फायदा ये है कि दारु पी कर नाचते-नाचते अगर आप उल्टी भी कर दो तो लोग समझेंगे कि...\n" +
                    ".\n" +
                    ".\n" +
                    ".\n" +
                    "जहर उगल रहा है।\n" +
                    "\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77\uD83C\uDF77"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke13",  "बड़ा कलयुग आ गया है...\n" +
                    ".\n" +
                    "जिस दोस्त की शादी में \n" +
                    "जी-जान से नाचे थे।\n" +
                    ".\n" +
                    "वो नालायक अपनी बीवी को \n" +
                    "एलबम दिखाते हुए बोला...\n" +
                    "शराबी हैं साले\"।। \uD83D\uDE1C\uD83D\uDE2D\uD83D\uDE1D\uD83D\uDE2D\uD83D\uDE02\uD83D\uDE1C\uD83D\uDE1D\uD83D\uDE23\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke14",  "ग्रामर की टीचर स्टूडेंट से - ';हरीश अब शराब नहीं\n" +
                    " पीता है।\n" +
                    "';इसमें हरीश  क्या है ??\n" +
                    " .\n" +
                    "स्टूडेंट -इसमें हरीश  माता रानी का भगत है और उसने नवरात्रा रखा हुआ है।..........\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke15",  "साबुन कितना महंगा क्यो न हो पर \n" +
                    ".\n" +
                    ".\n" +
                    ".लास्ट का बचा हुवा साबुन टट्टी के हाथ धोने में ही काम आता है \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\n" +
                    "\n" +
                    "---- चाणक्य का किरायेदार"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke16",  "सुनो जी, मैंने नए डिटर्जेन्ट से अपना नया सूट धोया और वो छोटा हो गया। अब क्या करूँ ? \"\n" +
                    ".\n" +
                    "पति---\" उसी डिटर्जेन्ट से नहा भी ले....फिट आ जाएगा। \"\n" +
                    "\uD83D\uDE1C\uD83D\uDE00\uD83D\uDE06\uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE06\uD83D\uDE00\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke17",  "मै बैंक  में नोट बदलवाने गया था bank के क्लर्क ने पूछा : खाता है ?\n" +
                    "\n" +
                    "मैने कहा  हां...पीता भी  हूँ । \uD83C\uDF7E\uD83C\uDF7A\n" +
                    "\n" +
                    " क्लर्क न जाने क्यो नाराज हो गया...\n" +
                    "✅\uD83C\uDF8E\uD83D\uDE4F\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "DrunkenJoke18",  "फेसबुक: - मैं सबको जानता हूं।\uD83D\uDE18\n" +
                    ".-'-\n" +
                    "गूगल: - मेरे पास सब कुछ है\uD83D\uDC4F\uD83D\uDD2B\n" +
                    ".\n" +
                    "इंटरनेट: - मेरे बिना तुम सब कुछ\n" +
                    "नहीं!\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDCAA\n" +
                    ".\n" +
                    "चार्जर : - आवाज नीचे!"));
        } else if (title.equals("Beggar Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke01", "टीचर- राजू, तुम परिंदो के बारे में सब जानते हो? राजू- जी सर. टीचर - अच्छा ये बताओ कौन सा परिंदा उड़ नहीं सकता? राजू- मरा हुआ परिंदा."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke02", "राजू- बस में बैठते हुए मैं किसी लड़की को खड़ा नहीं देख सकता. मंजू- तो फिर तुम क्या करते हो? राजू- मैं अपनी आंखें बंद कर लेता हूं!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke03", "भिखारी – साहेब, एक रुपया दे दे, तीन दिन से भूखा हूं. राजू – तीन दिन से भूखा है तो एक रुपए का क्या करेगा? भिखारी – वजन देखूंगा कितना घटा है!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke04", "भिखारी– साहब मैं अपने परिवार से बिछड़ गया हूं. मिलने के लिए 150 रुपये चाहिए. शर्मा जी(भावुक होकर) – कहां है तेरा परिवार?भिखारी– जी वो मल्टीप्लेक्स में सिनेमा देख रहे हैं."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke05", "एक औरत महँगी कार में बैठी हुई थी , तभी एक भिखारी वहां आया , बोला – मैडम 10 रुपये दे दो , औरत ने उसे पैसे दे दिए , जब भिखारी जाने लगा तो औरत ने उसे रोका , …"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke06", "भिखारी – भगवान के नाम पे कुछ दे दे बाबा लड़की – मैं बाबा नहीं बेटी हूँ भिखारी – भगवान के नाम पे कुछ दे दे बेटी लड़की – अरे मेरा नाम पूजा है भिखारी – भगवान के नाम पे …"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke07", "संता (भीख मांगते हुए) – कुछ दे दे बाबा बंता – ये लो 10 रुपये और मुझे आशीर्वाद दो कि मेरी नौकरी लग जाये संता – कौन सी पढाई की है बच्चा बंता – इंजीनियरिंग की संता – . . …"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke08", "भिखारी – बाबू मुझे 300 रूपए दे दो रमेश – अबे भिखारी, 300 रूपए क्यों मांग रहा है भिखारी – बाबू मैं अपने परिवार वालों से बिछड़ गया हूँ रमेश – अच्छा कहाँ है तेरा परिवार ?? भिखारी – वो …"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke09", "संता स्कूटर स्टार्ट कर रहा था भिखारी – भगवान के नाम पे कुछ दे दे बेटा संता – क्या दूँ बाबा ? भिखारी – जो तेरी इच्छा हो वो दे दे बाबा संता – . . . . तो नीचे …"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke10", "पप्पू मंदिर के बाहर टहल रहा था भिखारी – बेटा तेरी जोड़ी सलामत रहे पप्पू – अंकल मैं तो सिंगल हूँ भिखारी – मैं जूतों की जोड़ी की बात कर रहा हूँ बेटा पप्पू बेहोश"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke11", "जब पिंकू पैदा हुआ तो संता डॉक्टर से सलाह लेने गया कि बच्चों की देखभाल कैसे की जाए.\n" + ".\n" + "डॉक्टर: बच्चे को पानी देने से पहले उबाल लेना चाहिए.\n" + ".\n" + "संता: लेकिन डॉक्टर साहब, उबालने से बच्चा जल नहीं जाएगा !!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke12", "संता: मुझे जहर देना\n" + ".\n" + "दुकानदार: पहले डॉक्टर से लिखा के लाओ\n" + ".\n" + "संता अपनी शादी का कार्ड दिखाता है.\n" + ".\n" + "दुकानदार: बस कर यार रुलाएगा क्या बड़ी बोतल या छोटी."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke13", "भिखारी – माता जी , क्या इस गरीब को केक मिलेगा ?\n" + "महिला – क्यों , रोटी से काम नहीं चल सकता ?\n" + "भिखारी – नहीं माता जी , चल सकता है , लेकिन मेरा आज बर्थ डे है"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke14", "भिखारी (पिंकी से)- मैडम एक रुपया दे दो।\n" + "पिंकी- शर्म नहीं आती, इतने स्मार्ट, खूबसूरत, हैंडसम जवान लड़के हो और भीख मांगते हो?\n" + "भिखारी (खुश होकर)- ठीक है तो फिर एक झप्पी ही दे दो।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "BeggarJoke15", "एक भिखारी ने घर में आवाज लगाईः बाबू जी रोटी मिल जाएगी ?\n" + "अंदर से आवाज आईः मालकिन घर में नहीं है।\n" + "भिखारीः बाबू जी मुझे मालकिन नहीं रोटी चाहिए"));
        }
        else if (title.equals("Political Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke01", "नेता है घोटाला भाई\n" + "स्वार्थ है इसकी लुगाई\n" + "करो इनकी घर पे विदाई\n" + "यह है लोकतंत्र में अच्छाई \uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke02", "“तुम क्या गुल खिलाओगे खुद उजड़े गुलदान हो।\n" + "बीजेपी की जीत का हमेशा बहुत बड़ा योगदान हो।\n" + "हर इक हार के बाद भी नहीं सुधरे शहजादे।\n" + "सच बीजेपी के लिए तुम जीत का वरदान हो।” – मोदी जी"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke03", "सबको साईकील के साथ चलना है\n" + "नेता जी ईतना कह के कार मे चले गये!! \uD83D\uDE04\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke04", "पुत्र (संता से) : पिताजी नेताओं के कपड़ों का रंग सफेद क्यों होता है?\n" + ".\n" + "संता: बेटा, ताकि दल बदलने पर भी कपड़े न बदलने पड़ें.\uD83D\uDE06\uD83D\uDE06"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke05", "डॉलर की तुलना में रुपए को लगातार गिरते हुए देख मनमोहन सिंह चिल्ला पड़े: तुम इतना  गिर सकते हो मैंने कभी नहीं सोचा था!\n" + ".\n" + "रुपया: जब आपके मंत्री इतना नीचे गिर सकते हैं तो मैं क्यों नहीं????"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke06", "अगर रात को सफेद कपड़ो में कोई\n" + "आकर आप के पांव पकडे तो भूत\n" + "समझकर डरे नही वो विधायक पद\n" + "का उमीदवार भी हो सकता है!\n" + "ये सूचना जनहित में जारी\uD83E\uDD23\uD83E\uDD23"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke07", "बवासीर का इलाज करते हुए\n" + "डॉ. ने मरीज से पूछा..मोदी विरोधी हो क्या?\n" + ".\n" + "मरीज :- हां क्यु\n" + "डॉ.:- धुआं निकल रहा हैं.. इसलिए पुछा!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke08", "वोटर : यह जो आप उंगली पे स्याही लगाते हो.. ये कितने दिन में निकलेगी??\n" + "मतदान अधिकारी : करीब 4 महीने में।\n" + "वोटर (सिर आगे करते हुए) : यार मेरे सिर में लगादे। डाई सिर्फ 15 दिन ही चलती है।\n" + "अधिकारी : भाग साले"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke09", "प्रिय इवीएम मशीन..\n" + "सदा सुहागन रहो..!!!\n" + "गनीमत है.. तुम्हारे साथ केवल\n" + "छेड़ छाड़ की अफवाहें ही हैं,\n" + "मुझे तो सरेआम लूट लिया जाता था..!!\n" + "तुम्हारी बडी बहन\n" + "मतदान पेटी"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke10", "लोग कह रहे है दाऊद का हो गया खात्मा। मैं तब तक नहीं मानूँगा जब तक र NDTV का रविश कुमार छाती पीट पीटकर विधवा विलाप नहीं करता..."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke11", "कोरोना का कहर अभी खत्म नही हुआ है, इसलिए घर से बाहर केवल उतना ही दिखे जितना चुनाव के बाद राहुल_गांधी \"मंदिर\" मे मंदिर मे दिखते है \uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke12", "बीजेपी का समर्थन करना मेरी राजनीति नहीं है.. #गीता में लिखा है जो राष्ट्रहित में हो वही कार्य करना श्रेष्ठ है!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke13", "झूठ को सच्च बड़ी आसानी से बोलतें हैं,\n" + "चाँद सूरज हमने है बनाया ऐसी भी ये कहतें हैं,\n" + "सियासत के झूठे नेता जहाँ दिखें वोट ये वहीँ बिकते हैं…."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliticalJoke14", "माँ ने इसतीफा दीया बेटा अधयक्ष बन गया! \n" + "बेटा ने इसतीफा दीया माँ अधयक्ष बन गयी! \n" + "बाकि चमचे देखते के देखतें रह गए"));
        } else if (title.equals("Pathan Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke01", "पठान के तलाक़ का मुक़दमा अदालत में चल रहा था।\n" + "जज: मैंने इस मुक़दमे के बारे में यह फैंसला किया है कि तुम्हारी बेगम को हर महीने 20,000 रुपये मुआवजे के तौर पर मिलेंगे।\n" + "पठान: बहुत-बहुत मेहरबानी जज साहब, और हाँ कभी-कभी मेरे पास पैसे होंगे तो मैं भी थोड़े पैसे दे दिया करूँगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke02", "बिहारी मुल्ला: अरे वो डॉक्टर, कैसे नसबंदी किये हो हमार, बीवी फिर से माँ बनने वाली है?\n" + "\n" + "डॉक्टर: बुडबक! हम नसबंदी तुहार किये हैं, पूरे बिहार की नहीं।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke03", "पठान गुस्से से: वेटर, चिकन बिरयानी में चिकन है ही नहीं।\n" + "वेटर: साहब गुलाब जामुन में कौन सा गुलाब होता है?\n" + "पठान: हाँ यार सॉरी!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke04", "पठान एक बारात में गया। वहाँ उसे बार-बार पानी परोसा जा रहा था।\n" + "परेशान होकर पठान चिल्लाया: गले में पानी फंस गया है, थोड़ा गोष्ठ-चावल दे दो।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke05", "पठान हॉस्पिटल में अल्ट्रासाउंड करवाने के लिए गया।\n" + "डॉक्टर: कमीज़ उतारो, सलवार ढ़ीली करके उल्टा लेट जाओ।\n" + "पठान घबराकर बोला, \"डॉक्टर साहब पहले अल्ट्रासाउंड न कर लें?\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke06", "एक पठान दूसरे पठान से: यार एक नया अंडरवियर एक सस्ते देसी साबुन से धोया था, वो छोटा हो गया, अब क्या करूँ?\n" + "दूसरा पठान: उसी साबुन से नहा ले, पूरा आ जाएगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke07", "सिंधी: तुम ये ईंट लिए क्यों फिर रहे हो?\n" + "पठान: कुछ नहीं यार, मैं अपना घर बेचना चाहता हूँ और ये उसका नमूना है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke08", "चलती ट्रेन में टिकट चेकर ने पठान से टिकेट माँगा तो वह बोला: मेरा चेहरा ही टिकट है।\n" + "टिकट चेकर: तब तो आपको डबल जुर्माना देना होगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke09", "सलमा अपने खाविन्द इरफ़ान से- काश कि तुम एस एम एस का मैसेज होते तो मैं तुम्हें सेव कर लेती, जब चाहे पढ़ लेती !\n" + ".\n" + "इरफ़ान- कमीनी, कन्जूस ही रहियो ! सेव ही करके रखियो, अपनी किसी सहेली को फ़ोरवर्ड ना करियो !"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke10", "पठान की बेगम ने नमाज़ पढ़ कर दुआ के लिए हाथ उठाये पर कुछ नहीं माँगा और हाथ नीचे कर लिए।\n" + "पठान: यह क्या, तुमने दुआ क्यों नहीं माँगी?\n" + "बेगम: माँगने ही लगी थी कि अल्लाह आपकी तमाम मुश्किलें खत्म कर दे, फिर सोचा कहीं मैं ही ना खत्म हो जाऊं।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke11", "पठान ने ए.सी. लगवाया एक शख्स ने पूछा: आपको तो सर्दी बहुत लगती है?\n" + "पठान ये मैंने उल्टा लगवाया है, इससे गरम हवा अंदर और ठंडी हवा बाहर जाती है|"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke12", "पठान की टाँग में कट लग गया। नर्स ने कहा, “इसमें 10 टाँके लगेंगे।”\n" + "पठान- “कितना खर्चा लगेगा ?”\n" + "नर्स – “3000 रूपये ।”\n" + "पठान – “टाँका लगवाना है कोई कढ़ाई नहीं करवानी।”"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke13", "पठान की बेगम रात को: सुनिए ज़रा उठिए aur देखिये अलमारी के पास चोर खड़ा है।\n" + "पठान: उसके पास हथियार हुआ तो?\n" + "बेगम: अरे आप घबराओ मत, आपका तो इंश्योरेंस है, पर गहनों का नहीं है।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke14", "एक फ़क़ीर पठान के घर पर गया और बोला: मैं अल्लाह का मेहमान हूँ।\n" + "पठान उसका हाथ पकड़ कर मस्जिद में ले गया और बोला:\n" + "\"अल्लाह का घर ये है।\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke15", "एक पठान छिलके के साथ ही केला खा रहा था तो किसी ने कहा कि छिलका तो उतार लो।\n" + "पठान: ओये! छिलने की कोई ज़रूरत नहीं, हमको पता है इसके अंदर क्या है!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke16", "एक पठान दूसरे पठान से: तुम्हारे पापा की मौत कैसे हुई थी?\n" + "दूसरा पठान अफ़सोस करते हुए, \"यार उनकी यादाश्त बहुत कमज़ोर थी। दोपहर को सोते वक़्त सांस लेना भूल गये थे।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke17", "पठान कॉफ़ी शॉप में वेटर से: एक कॉफ़ी कितने की है?\n" + "वेटर: सर, 50 रुपये।\n" + "पठान: सामने वाली दुकान पर तो 1 रुपये लिखा है।\n" + "वेटर: अबे धयान से पढ़, कॉफ़ी नहीं कॉपी लिखा है, फोटो-स्टेट की दुकान है वो।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke18", "पठान: ये किस चीज़ का खेत है?\n" + "किसान: ये कपास का खेत है, जिससे कपड़े बनाये जाते हैं।\n" + "पठान: इसमें से सलवार वाला पौधा कौन सा है?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke19", "सिंधी(पठान से): और बताओ तुम्हारा भाई आज-कल क्या कर रहा है?\n" + "पठान: बस एक दुकान खोली थी, पर अब तो जेल में है।\n" + "सिंधी: जेल में, वो क्यों?\n" + "पठान: वो दुकान हथौड़े से खोली थी न।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PathanJoke20", "पठान ने चिड़ियाघर में नौकरी कर ली, उसने शेर के पिंजरे को ताला नहीं लगाया।\n" + "अफसर: तुमने शेर के पिंजरे को ताला क्यों नहीं लगाया?\n" + "पठान: क्या ज़रूरत है, इतने खतरनाक जानवर को कौन चुराएगा?"));
        } else if (title.equals("Police Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke01", "वकील-तुमने पुलिस ऑफिसर की जेब मे जलती हुई माचिस क्यू रखी....??\n" + "चिरकुट -ये मुझे बोला कि ज़मानत करवाना हो तो पहले जेब गरम करो.."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke02", "ट्रैफिक पुलिस: स्टॉप-स्टॉप तुम्हारी हेड लाइट्स काम नहीं कर रही, वो बंद है।\n" + "संता: जल्दी से हट जाओ ब्रेक्स भी काम नहीं कर रही हैं।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke03", "दद्दू- क्या बाबा रामदेव द्वारा जेल में कैदियों को योग का प्रशिक्षण देना उचित है? – सचिन वर्मा उत्तर : बिलकुल उचित है। यदि कैदी फिट रहेंगे तो पुलिस को भी फिट रहना होगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke04", "पुलिस का जलवा...\n" + "संता तालाब मे नंगा नहा रहा था..\n" + "पुलिस- चल ओये बाहर आकर कपड़े\n" + "पहन ले तेरी तलाशी लेनी है..."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke05", "सोचने पर मजबूर करने वाली बात -\n" + "अगर 40 % नंबर पाने वाला\n" + "पुलिस_अधिकारी बन जाता है,\n" + "और\n" + "80% नंबर पाने वाला रोजगार न मिलने के कारण चोर बन जाता है,\n" + "तब आप सोचिये|\n" + "क्या वो S.P साहब कभी उस चोर को पकड़ पायेगे?\n" + "जो उनसे जायदा दिमाग रखता है..."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke06", "एक सिपाही ने मौके से SHO को फ़ोन किया -\n" + "जनाब यहाँ एक औरत ने अपने पति को गोली मार दी।\n" + "SHO - क्यों?\n" + "सिपाही - क्योंकि आदमी पोचा मारे हुए फर्श पर चढ़ गया था।\n" + "SHO - गिरफ्तार कर लिया औरत को?\n" + "सिपाही -नहीं अभी पोचा नहीं सूखा..."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke07", "पुलिस: ओये तूने पपीते बेचने वाली को किस क्यूँ किया ?\n" + "सरदार: सर, में भी क्या करता वो इतनी देर से मेरे घर के बाहर चिल्ला रही थी,\n" + "पप्पी-ते ले लो,\n" + "पप्पी-ते ले लो...."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke08", "एक पुलिस इंस्\u200Dपेक्\u200Dटर के घर चोरी हो रही थी बीवी- उठो जी,\n" + ".\n" + ".\n" + "घर में चोरी हो रही है पति- मुझे सोने दो मै इस समय ड्यूटी पर नहीं हूं"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke09", "कर्नल- तुमने मुझे डूबने से बचाया है यह बात मैं कल सुबह की परेड में सबको बताऊंगा।\n" + "फौजी- ऐसा मत कीजिए। अगर दूसरे फौजियों को पता चल गया तो वो मुझे नदी में फेंक देंगे।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke10", "सुरेश-रमेश को रास्ते में दो बम मिले।\n" + "सुरेश (रमेश से)- चल पुलिस को देकर आते हैं।\n" + "रमेश(सुरेश)- अगर कोई बम रास्ते में फट गया तो?\n" + "सुरेश- झूठ बोल देंगे की एक ही मिला था..!!"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke11", "पुलिस: मैडम दरवाजा खोलो,\n" + "आपके पति ट्रक के नीचे आकर पापड़ बन गए हैं।\n" + ".\n" + ".\n" + ".\n" + ".\n" + ".\n" + "पत्नी: दरवाजा खोलने की क्या जरुरत है\n" + "नीचे से सरका दो।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke12", "थानेदार प्रेमसुख लाल (चोर घोंचू से) - क्यों रे...! कल थाने में तूने मेरी ही जेब से पर्स मार दिया। तुझे थोड़ी भी शर्म नहीं आई?\n" + "घोंचू लाल - थानेदार साब! शर्म तो आपको आनी चाहिए...., क्योंकि आपके पर्स में एक रुपया भी नहीं निकला।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke13", "एक बार पुलिस की नौकरी के लिए गए प्रेमसुख से इंटरव्यू में पूछा गया - बिना लाठी या गोली चलाए भीड़ को कैसे हटाओगे?\n" + "प्रेमसुख - बहुत आसान है साहब! मैं अपनी झोली फैलाकर चंदा मांगने लगूंगा।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke14", "संता शराब पीकर घर लौट रहा था।\n" + "पुलिस : कहां जा रहा है?\n" + "संता: दारू पीने से होने वाले नुकसान पर प्रवचन सुनने।\n" + "पुलिस : इतनी रात को कौन प्रवचन देगा?\n" + "संता: मेरी बीवी।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke15", "दो नये कैदी जेल के अन्दर गए...\n" + "\"तुम्हें कितने साल की सजा हुई?\" एक ने पू्छा...\n" + "\"14 साल की...\" उत्तर मिला-\"और तुम्हें?\"\n" + "\"मुझे 12 साल की...\"\n" + "\"तुम दरवाजे के पास वाली चटाई ले लो... तुम मुझसे पहले छूटोगे...\""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke16", "पुलिस: बीबी, आप बहुत बहादुर\n" + "हैं,आप ने डाकू को बहुत मारा.\n" + "महिला: मुझे\n" + "क्या पता था कि वो बेचारा डाकू है,\n" + "मै तो समझी कि मेरा शौहर है देर से\n" + "घर आया है..."));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke17", "पुलिस वाला (चम्पू से) - सब कुछ बता दो, वरना चड्\u200Dडी उतार कर मारूंगा।\n" + "चम्पू - सर, गलती मैंने की है तो आप क्यों अपनी चड्\u200Dडी उतारोगे?"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke18", "हवालदार : जनाब हमने शराब से भरा ट्रक पकड़ लिया है,\n" + "इंस्पेक्टर : शाबाश बहुत अच्छे..\n" + "हवालदार : आगे क्या हुकुम है जनाब ?\n" + "इंस्पेक्टर : अब एक ट्रक सोडा और एक ट्रक नमकीन का भी पकड़ लो |"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke19", "एक बस नहर में गिर गई। पुलिस: बस कैसे गिरी?\n" + "ड्राइवर: मुझे नहीं पता।\n" + "पुलिस: क्या?? तो तू क्या कर रहा था?\n" + "ड्राइवर: वो आज कंडक्टर आया नहीं था तो मैं खुद ही टिकट काटने पीछे चला गया।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke20", "4 लड़के  बाइक  पर जा रहे थे\n" + ".\n" + "पुलिस: ओये रुको तुम सब , तुम लोगो को पता नहीं हे की बाइक पर 3 लोगो से ज्यादा का सवारी करना मना है\n" + ".\n" + "बाइक चलाने वाला   पीछे देखके   चौका  और बोला ” ओये सालो पांचवा बाँदा कहा गिर गया ”"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "PoliceJoke21", "कॉन्स्टेबल - सर, कल रात सभी क़ैदियो ने जैल मे रामायण प्ले किया था...\n" + "जेलर - ये तो अच्छी बात हे, इसमे इतने परेशान क्यू हो रहे हो?\n" + "कॉन्स्टेबल - सर टेंसन ये हे की,\n" + "हनुमान बना कैदी अभी तक संजीवनी लेकर वापस नही आया..."));
        } else if (title.equals("Family Jokes")) {
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke01", "एक लड़के को खाँसी हो गयी \n" + "उसके दादाजी ने कहा एक पेग \uD83C\uDF77ले ले.....खाँसी चली जाएगी..\n" + "लड़का: दारु \uD83C\uDF7E\uD83C\uDF7Eसे खाँसी चली जाती है क्या....?????\n" + "दादाजी: दारु से 100बीघा जमीन चली गयी..तु खाँसी की बात करता है...\uD83D\uDE00\uD83D\uDE00\uD83D\uDE00\uD83D\uDE00\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke02", "वो भी क्या दिन थे....????\n" + "जब बच्चपन में कोई रिश्तेदार जाते समय 10 ₹ दे जाता था..\n" + ".\n" + "और माँ 8₹ टीडीएस काटकर 2₹ थमा देती थी....!!!\n" + "\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke03", "सास - कितनी बार कहा है,\uD83D\uDE21\n" + "बाहर जाओ तो बिन्दी लगाकर जाया करो।\uD83D\uDE21\uD83D\uDE20\n" + "आधुनिक बहु - पर जीन्स पर बिन्दी कौन लगाता है...?\uD83D\uDC83\uD83D\uDC83\uD83D\uDC83\n" + ".\n" + "सास - तो मैंने कब कहा जीन्स पर लगानी है,\n" + "माथे पर लगा चुड़ैल माथे पर..\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B\uD83D\uDE1B"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke04", "अमेरिका चाहे जितनी मर्जी खोज कर ले लेकिन कभी ये नहीं पता लगा सकते कि\n" + "?\n" + "?\n" + "?\n" + "?\n" + "साउथ की फिल्मों में बाप काला और बेटी गोरी कैसे हो जाती है..?\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE24\uD83D\uDE2C\uD83D\uDE01\uD83D\uDE1D\uD83D\uDE1B\uD83D\uDE1C\uD83D\uDE1B\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke05", "एक बुजुर्ग व्यक्ति- बेटा, कैसे हो ?\n" + "बच्चा- ठीक हूं।\n" + "बुजुर्ग- पढ़ाई कैसी चल रही है?\n" + "बच्चा- बिलकुल आपकी जिंदगी की तरह।\n" + "बुजुर्ग- मतलब ?\n" + "बच्चा- भगवान भरोसे।"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke06", "टीचर ने बच्चे की काँपी पर नोट लिख\n" + "कर भेजा\n" + ".\n" + "कृपया बच्चोँ को नहला कर भेजा करेँ \n" + "जवाब में बच्चे की माँ ने लिखा\n" + ".\n" + ".\n" + "कृपया बच्चों को पढ़ाया करें\n" + "सूंघा न करें |\n" + "✌\uD83C\uDFFB\uD83D\uDE1D✌\uD83C\uDFFB\uD83D\uDE1D✌\uD83C\uDFFB\uD83D\uDE1D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke07", "ससुर : आइए दामाद जी आज सुबह सुबह अचानक\n" + "कैसे दर्शन दे दिए ??\n" + ".\n" + ".\n" + "दामाद : आपकी बेटी से झगड़ा हो गया था,\n" + "वो बोली \"जहन्नुम में जाओ \""));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke08", "एक लेडी इंटर्व्यू देने गई \n" + ".\n" + "इंटर्व्यूअर - क्या करती हैं आप \n" + "लेडी - मैं पाइलट हूँ \n" + ".\n" + "इंटर्व्यूअर - <बहुत आदर के साथ> क्या उड़ाती हैं आप\n" + ".\n" + "लेडी - पति के पैसे \uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke09", "\uD83D\uDE03\uD83D\uDE2D\uD83D\uDE01\uD83D\uDE22\uD83D\uDE03\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\uD83D\uDE03\uD83D\uDE1C\n" + ".\n" + "पापा, मुझे स्कूल छोड़ने आप क्यों आते हो? \n" + "बाकी सब बच्चों को छोड़ने तो उनकी मम्मी आती हैं!!!\n" + ".\n" + "पापा: बस बेटा, \n" + "इसीलिए...\n" + "\uD83D\uDE01\uD83D\uDE03\uD83D\uDE1C\uD83D\uDE2D\uD83D\uDE03\uD83D\uDE01\uD83D\uDE01\uD83D\uDE03\uD83D\uDE03\uD83D\uDE03"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke10", "पत्नी: आप बहुत भोले हैं... आपको कोई भी बेवकूफ बना देता है\n" + ".\n" + ".\n" + "पति: शुरुआत तो तेरे बाप ने की थी .\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke11", "संता और बंता दोनों भाई एक ही क्लास में पढ़ते थे।\n" + ".\n" + "अध्यापिका: तुम दोनों ने अपने पापा का नाम अलग-अलग क्यों लिखा?\n" + ".\n" + "संता: मैडम फिर आप कहोगे\n" + ".\n" + "नक़ल मारी है।\n" + "\uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke12", "\uD83D\uDE00\uD83D\uDE00\uD83D\uDE1Cटीचर – “मेरे पापा काम पर गए हैं” – इसका future tense बताओ ?\n" + ".\n" + "स्टूडेंट – वो कल भी जायेंगे ….. किसी के बाप में हिम्मत हो तो रोक के दिखाए ….!!!\uD83D\uDC49\uD83E\uDD14\uD83D\uDE00\uD83D\uDE00\uD83D\uDE00\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE07\uD83D\uDE1D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke13", "बाप : शराब , \n" + "\n" + "सिगरेट , लड़किया ये सब तुम्हारे\n" + "दुश्मन है......!!!\n" + ".\n" + ".\n" + "बेटा : पापा जो अपने दुश्मनो से डर जाये वो\n" + "मर्द नही....\uD83D\uDE0E\n" + "फिर क्या था दे चप्पल पे चप्पल"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke14", "\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDC4F\n" + "ताऊ (अपने बेटे से) : रै \n" + "बेवकूफ, मेवालाल जी की \n" + "बिटिया नै देख, फर्स्ट आई \n" + "है स्कूल में\n" + ".\n" + ".\n" + "बेटा : और कितणा देख्यूं? \n" + "उसी नै देख-देख के तो फैल\n" + " होई गया  \uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke15", "एक छोटा बच्चा दुकानदार से :- अन्कल गोरा होने का\n" + "क्रिम है क्या ???\n" + ".\n" + "दुकानदार :- हा है ..!!!\n" + ".\n" + "बच्चा :- तो साले ...लगाते क्यों नहीं रोज डर जाता हूँ !\n" + "\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke16", "\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE19\uD83D\uDE17\uD83D\uDE00\uD83D\uDE0A\uD83D\uDE04\n" + "\uD83D\uDC69 लड़की : -\n" + "                   हे भगवान, मेरी शादी किसी समझदार आदमी से करवा दो,\n" + "\uD83C\uDF0Dभगवान : -\n" + "                    घर जाओ बेटी समझदार आदमी कभी शादी नहीं करते !\n" + "\uD83D\uDE04\uD83D\uDE00\uD83D\uDE00\uD83D\uDE19\uD83D\uDE0C\uD83D\uDE17\uD83D\uDE1C\uD83D\uDE03\uD83D\uDE04"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke17", "पत्नी:- सुनो जी !! शाम को घर पर\n" + "व्हाट्सअप जी की पूजा रखी हैं..\n" + "पास-पड़ोस की सभी लेडिस लोगों को\n" + "बुलाया हैं.. प्रसाद में क्या बाँटू..???\n" + "\uD83D\uDC66पति:- मेरा नम्बर बाँट देना..\n" + " पुण्य लगेगा..  \uD83D\uDE1C\uD83D\uDE1C\uD83D\uDE1C\uD83D\uDCAF\uD83D\uDC4C✅\uD83D\uDC4D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke18", "कुछ छोटे बच्चे तो इतने क्यूट होते हैं कि उन्हें देखने के बाद .......\n" + ".\n" + ".\n" + "उनकी मम्मी को देखने का मन करता है...\uD83D\uDE06\uD83D\uDE02\uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke19", "पत्नी:- आपको मेरी सुंदरता ज़्यादा अच्छी लगती है या मेरे संस्कार...?\n" + ".\n" + "पति:- मुझे तो तेरी ये मज़ाक करने की आदत  बहुत अच्छी लगती है।\n" + "\uD83D\uDE1C \uD83D\uDE1C \uD83D\uDE1C  \uD83D\uDE1C  \uD83D\uDE1C  \uD83D\uDE1C  \uD83D\uDE1C  \uD83D\uDE1C"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke20", "एक बे एक छोरी मैट्रो म काले चश्मे लगाके आपणे हरियाणे आली\n" + "ताई के टकरा गई।\n" + ".\n" + "ताई - आए बेटी छोटी सी न ए आँख बनवा ली दिखे।\uD83D\uDE1D"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke21", "पति: कहा गई थी\uD83D\uDE20\n" + ".\n" + "पत्नी : जी रक्त दान करने\uD83D\uDE14\n" + ".\n" + "पति : पीते पीते ओवरफ्लो होने लगा हे जो अब बाटने भी लगी हो\uD83D\uDE29\uD83D\uDE1C\uD83D\uDE02"));
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, "FamilyJoke22", "कसम से उस समय ऐसा लगता है कि बस धरती फट जाये और मैं उसमें समा \n" + "जाऊ ....\n" + ".\n" + ".\n" + "जब पत्नी कहती है, \n" + ".\n" + " \"एक मैं ही मिल गयी हूँ आपको…सीधी-सादी\"। \uD83D\uDE1C\uD83D\uDE1D\uD83D\uDE00\uD83D\uDE01"));
        }

        RecyclerViewJokesAdapter adapterJokes = new RecyclerViewJokesAdapter(this, jokesList);
        recyclerView.setAdapter(adapterJokes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        adapterJokes.setOnFavouriteButtonClickListener(new RecyclerViewJokesAdapter.onFavouriteButtonClickListener() {
            @Override
            public void onFavouriteButtonClick(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(JokesActivity.this);
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                boolean isExist = dbHelper.isJokeExist(jokesList.get(position).getJokeTitle());
                if (isExist){
                    dbHelper.deleteFavouriteJoke(jokesList.get(position).getJokeTitle());
                    Toast.makeText(JokesActivity.this, "Remove From Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertFavouriteJoke(jokesList.get(position).getJokeTitle(), jokesList.get(position).getTextView());
                    Toast.makeText(JokesActivity.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                }

                adapterJokes.notifyDataSetChanged();
            }
        });

        adapterJokes.setOnCopyButtonClickListener(new RecyclerViewJokesAdapter.onCopyButtonClickListener() {
            @Override
            public void onCopyButtonClickListener(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(JokesActivity.this);
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                String joke = jokesList.get(position).getTextView();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Joke", joke);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(JokesActivity.this, "Joke copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        adapterJokes.setOnShareButtonClickListener(new RecyclerViewJokesAdapter.onShareButtonClickListener() {
            @Override
            public void onShareButtonClickListener(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(JokesActivity.this);
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                String joke = jokesList.get(position).getTextView();
                String link = "https://play.google.com/store/apps/details?id=" + getPackageName();

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Check this awesome Joke: ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                myIntent.putExtra(Intent.EXTRA_TEXT, joke + "\n\n" + link);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        adapterJokes.setOnSaveButtonClickListener(new RecyclerViewJokesAdapter.onSaveButtonClickListener() {
            @Override
            public void onSaveButtonClick(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(JokesActivity.this);
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
               saveViewAsImage(view);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveViewAsImage(View view) {
        if (checkPermission()) {
            Bitmap bitmap = getBitmapFromView(view);
            saveBitmapToGallery(bitmap);
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    private Bitmap getBitmapFromView(View view) {
        // Create a Bitmap with the same size as the view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a Canvas to draw the view onto the Bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the view onto the Canvas
        view.draw(canvas);

        return bitmap;
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveBitmapToGalleryApi29AndAbove(bitmap);
        } else {
            saveBitmapToGalleryApi28AndBelow(bitmap);
        }
    }

    private void saveBitmapToGalleryApi29AndAbove(Bitmap bitmap) {
        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "joke_image");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream fos = resolver.openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Objects.requireNonNull(fos).close();
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBitmapToGalleryApi28AndBelow(Bitmap bitmap) {
        String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File image = new File(imagesDir, "joke_image.png");

        try {
            FileOutputStream fos = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // Notify gallery about the new image
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(image);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }
    void loadInterstitialAd(){
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }
}