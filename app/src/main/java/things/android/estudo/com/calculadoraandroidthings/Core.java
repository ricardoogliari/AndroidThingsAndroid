package things.android.estudo.com.calculadoraandroidthings;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ricardoogliari on 12/25/16.
 */

public class Core extends Application {

    public static DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }
}
