package com.example.finalproject;

import static com.example.finalproject.R.drawable.absol;
import static com.example.finalproject.R.drawable.alakazam;
import static com.example.finalproject.R.drawable.ampharos;
import static com.example.finalproject.R.drawable.backcard;
import static com.example.finalproject.R.drawable.backside;
import static com.example.finalproject.R.drawable.blaziken;
import static com.example.finalproject.R.drawable.chim_lua;
import static com.example.finalproject.R.drawable.chim_set;
import static com.example.finalproject.R.drawable.gyarados;
import static com.example.finalproject.R.drawable.lucario;
import static com.example.finalproject.R.drawable.mewtwo;
import static com.example.finalproject.R.drawable.pidgeot;
import static com.example.finalproject.R.drawable.chim_tuyet;
import static com.example.finalproject.R.drawable.gengar;
import static com.example.finalproject.R.drawable.pink;
import static com.example.finalproject.R.drawable.pok;
import static com.example.finalproject.R.drawable.pok2;
import static com.example.finalproject.R.drawable.pokemon_card_transparent_background;
import static com.example.finalproject.R.drawable.rayquaza;
import static com.example.finalproject.R.drawable.salamence;
import static com.example.finalproject.R.drawable.sceptile;
import static com.example.finalproject.R.drawable.steelix;
import static com.example.finalproject.R.drawable.swampert;
import static com.example.finalproject.R.drawable.venusaur;
import static com.example.finalproject.R.drawable.tyranitar;
import static com.example.finalproject.R.drawable.charizard;
import static com.example.finalproject.R.drawable.blastoise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameplayActivity extends AppCompatActivity {
    private int countCard = 0;
    int card;
    int x = 0, y = 0;
    int preCardId;//bi???n gi??? t???m id c???a l?? v???a ch???n.
    private int[] countTag ;
    int result =0;
    public static int height;
    public static int width;
    private TextView scoreLabel, startLabel;
    private int score;
    private SoundPlayer soundPlayer;
    private CountDownTimer countDownTimer;
    private int multiScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int time = 0;
        Intent i = getIntent();

        //Nh???n ????? kh?? v?? set ????? r???ng m??n ch??i.
        switch (i.getStringExtra("Mode")){
            case "Easy":{
                x=4;y=4;
                time = 62000;
                break;
            }
            case "Medium":{
                x=6;y=4;
                time = 92000;
                break;
            }
            case "Hard":{
                x=8;y=5;
                time = 152000;
                break;
            }
        }
        card = (x*y)/2;

        countTag = new int[x*y];
        setCountTag();

        getHeightAndWidth();
        Toast.makeText(this,height +" "+width,Toast.LENGTH_LONG).show();

        createButtonList();

        createClock(time);
    }

    public void createButtonList() {//T???o t??? ?????ng b??? b??i theo s??? l?????ng ???? cho

        LinearLayout oLL = (LinearLayout) findViewById(R.id.outsideLinearLayout);
        for (int i = 0; i < x; i++) {
            // ?? t?????ng: -G???n constraintLayout v??o linearLayout t?????ng tr??ng cho m???i d??ng c???a b??? b??i.
            //          -Sau ???? g???n t???ng l?? b??i v??o t???ng d??ng t???o th??nh ma tr???n 2 chi???u.
            //          -Set h??nh g???c l?? l?? backside cho t???ng l?? b??i, setTag cho m???i l?? sao cho ch??? 2 l?? c?? th??? chung 1 tag.
            //          -onClick s??? g???i h??m flipUp ?????ng th???i thay ?????i h??nh background c???a l?? b??i th??nh 1 h??nh pokemon ??c g??n theo tag.
            //          -n???u l???t tr??ng th?? 2 l?? bi???n m???t, kh??ng tr??ng th?? g???i h??m flipDown.

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 10;//kho???ng c??ch m???i d??ng.

            LinearLayout linearLayout = new LinearLayout(GameplayActivity.this);
            linearLayout.setLayoutParams(params);//Apply setting cho constrainLayout.
            oLL.addView(linearLayout);//G???n th??m layout v??o layout g???c ????? t???o th??nh t???ng d??ng.

            for (int j = 1; j <= y; j++) {
                ImageButton imageButton = new ImageButton(GameplayActivity.this);
                LinearLayout.LayoutParams dotparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                dotparams.setMargins(10, 0, 10, 0);//Ch???nh kho???ng c??ch gi???a c??c l?? b??i
                dotparams.height =  ((height - 500 -(x-1)*10 )/x);//Ch???nh ????? d??i m???i l?? b??i.
                dotparams.width =  ((width -(y-1)*20)/y);//Ch???nh r???ng.
                imageButton.setLayoutParams(dotparams);//Apply setting cho m???i l?? b??i
                imageButton.setBackgroundResource(pok2);//Set h??nh cho l?? b??i
                int finalI = i;
                int finalJ = j;

                imageButton.setId(View.generateViewId());//T???o id cho m???i l?? b??i, id l?? s??? nguy??n 1, 2, 3,.... T??m l?? b??i b???ng findViewById(R.id.1),.....

                //T???o tag random t????ng ???ng v???i bi???n ?????m lo???i tag, n???u lo???i tag n??o ???? c?? 2 l?? th?? kh??ng t???o lo???i tag ???? n???a
                int random = new Random().nextInt((x * y) / 2);
                while (imageButton.getTag() == null) {
                    if (countTag[random] < 2) {
                        imageButton.setTag(random);
                        countTag[random]++;
                    } else {
                        random = new Random().nextInt(x * y / 2);
                    }
                }

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) findViewById(R.id.tv1);
                        tv.setText(String.valueOf(imageButton.getTag()));
                        flipUp(imageButton);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {//T???o delay ????? th??? b??i l???t l??n c??n th???y ??c :v
                            @Override
                            public void run() {
                                switch (countCard) {//H??m x??t xem ???? ????? 2 th??? b??i ????? x??t ch??a
                                    case 0: {
                                        countCard++;
                                        preCardId = imageButton.getId();//N???u ch??? c?? 1 l?? ???????c l???t th?? l???y id c???a l?? ???? ????? match v???i l?? sau.
                                        break;
                                    }
                                    case 1: {
                                        countCard = 0;
                                        ImageButton preImageButton = (ImageButton) findViewById(preCardId);
                                        if (imageButton.getTag() == preImageButton.getTag()) {
                                            disappear(imageButton);
                                            disappear(preImageButton);
                                            score+= (multiScore/1000);
                                            card--;
                                            scoreLabel.setText(getString(R.string.score, score));
                                            soundPlayer.playHitSound();
                                            if(card == 0){
                                                winGame();
                                            }
                                        }
                                        else {
                                            flipDown(imageButton);
                                            flipDown(preImageButton);
                                            preCardId = -1;
                                        }

                                        break;
                                    }
                                }
                            }
                        }, 1000);

                    }
                });
                linearLayout.addView(imageButton);//G???n l?? b??i v??o b??? b??i
            }
        }

    }

    private void disappear(ImageButton imageButton) {
        final ObjectAnimator oa = ObjectAnimator.ofFloat(imageButton, "scaleX", 1f, 0f);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.start();

    }

    private void flipUp(ImageButton imageButton) {//H??m l???p l?? b??i l??n.

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageButton, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageButton, "scaleX", 0f, 1f);

        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageButton.setBackgroundResource(getDrawableInt(imageButton));
                oa2.start();
            }
        });
        oa1.start();

        imageButton.setEnabled(false);//T???t kh??? n??ng b???m v??o th??? ???? l???t.
    }

    private void flipDown(ImageButton imageButton) {//H??m ??p l?? b??i xu???ng.

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageButton, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageButton, "scaleX", 0f, 1f);

        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.setInterpolator(new DecelerateInterpolator());

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                imageButton.setBackgroundResource(pok2);
                oa2.start();
            }
        });
        oa1.start();

        imageButton.setEnabled(true);//B???t kh??? n??ng b???m.
    }

    private void setCountTag() {
        // T???o 1 chu???i s??? nguy??n v???i t???ng ph???n t??? gi??? s??? ?????m c???a 1 lo???i tag.
        // T??c d???ng l?? ????? ?????m xem 1 tag ???? ????? 2 l?? b??i ch??a, n???u ????? th?? tag ???? kh??ng ???????c t???o ra th??m n???a.

        for (int i = 0; i < countTag.length; i++) {
            countTag[i] = 0;
        }
    }

    private int getDrawableInt(ImageButton imageButton) {
        int result = 0;
        switch (Integer.parseInt(String.valueOf(imageButton.getTag()))) {
            case 0:
                result = charizard;
                break;
            case 1:
                result = blastoise;
                break;
            case 2:
                result = tyranitar;
                break;
            case 3:
                result = venusaur;
                break;
            case 4:
                result = gengar;
                break;
            case 5:
                result = chim_tuyet;
                break;
            case 6:
                result = lucario;
                break;
            case 7:
                result = chim_set;
                break;
            case 8:
                result = chim_lua;
                break;
            case 9:
                result = alakazam;
                break;
            case 10:
                result = gyarados;
                break;
            case 11:
                result = mewtwo;
                break;
            case 12:
                result = ampharos;
                break;
            case 13:
                result = steelix;
                break;
            case 14:
                result = sceptile;
                break;
            case 15:
                result = blaziken;
                break;
            case 16:
                result = swampert;
                break;
            case 17:
                result = absol;
                break;
            case 18:
                result = salamence;
                break;
            case 19:
                result = rayquaza;
                break;

        }
        return result;

    }

    private void createClock(int time){
        soundPlayer = new SoundPlayer(this);
        scoreLabel = findViewById(R.id.scoreLabel);

        TextView mTextField = (TextView) findViewById(R.id.time_bar_text);

        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                multiScore = (int) millisUntilFinished;
                mTextField.setText("" + multiScore/ 1000);
            }

            public void onFinish() {
                mTextField.setText("done!");
                soundPlayer.playOverSound();
                finish();
                loseGame();
            }

        }.start();
    }

    //????? l???y khung m??y
    private void getHeightAndWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    @Override
    public void onBackPressed() { //H??m x??? l?? nh???ng ?????a tr??? ??ang ch??i t??? nhi??n b???m tho??t :v
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(this.RESULT_CANCELED, returnIntent);
        countDownTimer.cancel();//disable ?????ng h??? ?????m ng?????c
        finish();
    }
    private void winGame(){
        Intent i = new Intent(this,ResultActivity.class);
        i.putExtra("Result",1);
        i.putExtra("Score",score);
        startActivityForResult(i,result);
        countDownTimer.cancel();
    }
    private void loseGame(){
        Intent i = new Intent(this,ResultActivity.class);
        i.putExtra("Result",0);
        i.putExtra("Score",score);
        soundPlayer.playOverSound();
        startActivityForResult(i,result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_CANCELED,data);
        finish();
        countDownTimer.cancel();
    }
}