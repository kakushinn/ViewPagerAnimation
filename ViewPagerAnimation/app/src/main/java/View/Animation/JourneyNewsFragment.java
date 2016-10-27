package View.Animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.R;

import org.w3c.dom.Text;

/**
 * Created by guochen on 2016/10/27.
 */
public class JourneyNewsFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("journey", "This is journey");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_test,null);
        TextView tv = (TextView)view.findViewById(R.id.test);
        tv.setText("journey");
        return view;
    }

}
