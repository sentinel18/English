package com.example.administrator.izienglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity {
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private List<Question> mQuestions = new ArrayList<Question>();
    @ViewById(R.id.btnListen)
    Button mBtnListen;
    @ViewById(R.id.btnGrammar)
    Button mBtnGrammar;
    @ViewById(R.id.btnExercise)
    Button mBtnExercise;
    @ViewById(R.id.btnVerb)
    Button mBtnIrrVerb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AfterViews
    public void Init(){
        Firebase.setAndroidContext(this);
        mRoot = new Firebase("https://test-firebase-c80fc.firebaseio.com/");
        getFirebaseData();
        /*Khi co them splash*/
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
//        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
//        Log.i("MainACTIVITY",mQuestions.size()+"");
    }

    @Click(R.id.btnExercise)
    public void ClickExercise(){
        Intent intent = new Intent(MainActivity.this,QuestionActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_QUESTION,(ArrayList)mQuestions);
        intent.putExtra(KEY_BUNDLE,bundle);
        startActivity(intent);
    }

    @Click(R.id.btnVerb)
    public void ClickIrrVerb(){
        Intent intent = new Intent(MainActivity.this,IrregularVerbActivity_.class);
        startActivity(intent);
    }

    public void getFirebaseData() {
        Firebase firebase = mRoot.child(ROOT_CHILD).child(UNDER_CHILD);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question q = dataSnapshot.getValue(Question.class);
                mQuestions.add(q);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
