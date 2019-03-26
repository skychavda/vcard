package vcard.application.android.com.vcard.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vcard.application.android.com.vcard.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public String[] slide_heading = {
            "Scan Card",
            "Share Card",
            "Generate E-card"
    };

    public String[] slide_disc = {
            "",
            "",
            ""
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.welcome_screen_slider, container, false);

        TextView slideHeading = view.findViewById(R.id.welcome_slider_heading_tv);
        TextView slideDisc = view.findViewById(R.id.welcome_slider_disc_tv);

        slideHeading.setText(slide_heading[position]);
        slideDisc.setText(slide_disc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
